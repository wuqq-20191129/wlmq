/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.samin.controller;

import com.goldsign.acc.app.samin.entity.ProduceSamIn;
import com.goldsign.acc.app.samin.mapper.EmptySamInMapper;
import com.goldsign.acc.app.samin.mapper.ProduceSamInMapper;
import com.goldsign.acc.app.samout.entity.IssueSamOut;
import com.goldsign.acc.app.samout.mapper.IssueSamOutMapper;
import com.goldsign.acc.frame.constant.CommandConstant;

import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.constant.SamCardConstant;

import com.goldsign.acc.frame.controller.SamBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.BillMapper;
import com.goldsign.acc.frame.mapper.OperationLogMapper;

import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.ExpUtil;

import com.goldsign.acc.frame.util.LogUtil;

import com.goldsign.acc.frame.vo.OperationResult;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * 卡制作入库
 *
 * @author luck
 */
@Controller
public class ProduceSamInController extends ProduceSamInParentController {

    @Autowired
    private EmptySamInMapper esiMapper;
    @Autowired
    private ProduceSamInMapper psiMapper;
    @Autowired
    private IssueSamOutMapper isoMapper;

    @RequestMapping(value = "/produceSamInAction")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/samin/produceSamIn.jsp");
        String command = request.getParameter("command");
        String operType = request.getParameter("operType");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (this.isOperForPlan(operType)) {
                    //入库单操作
                    if (command.equals(CommandConstant.COMMAND_QUERY)) {
                        opResult = this.queryPlan(request, this.psiMapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_ADD)) {
                        opResult = this.addPlan(request, this.psiMapper, this.billMapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_DELETE)) {
                        opResult = this.deletePlan(request, this.psiMapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_AUDIT)) {
                        opResult = this.auditPlan(request, this.psiMapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_MODIFY)) {
                        opResult = this.modifyPlan(request, this.psiMapper, this.operationLogMapper);
                    }
                }
                if (this.isUpdateOp(command, operType))//更新操作，增、删、改、审核,查询更新结果或原查询结果
                {

                    this.queryUpdateResult(command, operType, request, esiMapper, operationLogMapper, opResult, mv);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }

        String[] attrNames = this.getAttributeNames(command, operType);
        this.setPageOptions(attrNames, mv, request, response); //设置页面线路、车站、票卡、仓库等选项值、版本
        this.getResultSetText(opResult.getReturnResultSet(), mv, operType);
        this.baseHandlerForOutIn(request, response, mv); //出入库基本回传参数
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集
        return mv;
    }

    private String[] getAttributeNames(String command, String operType) {
        if (this.isOperForPlan(operType)) {

            String[] attrNames = {BILL_STATUES, SAM_TYPES, ISSUE_ORDER_NO};
            return attrNames;

        }

        return new String[0];

    }

    private void queryUpdateResult(String command, String operType, HttpServletRequest request, EmptySamInMapper esiMapper,
            OperationLogMapper opLogMapper, OperationResult opResult, ModelAndView mv) throws Exception {
        if (!command.equals(CommandConstant.COMMAND_ADD)) {
            this.saveQueryControlDefaultValues(request, mv); //保存查询条件
        }
        if (this.isOperForPlan(operType)) { //计划单查询      
            this.queryForOpPlan(request, this.psiMapper, this.operationLogMapper, opResult);
        }

    }

    private void getResultSetText(List resultSet, ModelAndView mv, String operType) {
        if (this.isOperForPlan(operType)) {
            this.getResultSetTextForPlan(resultSet, mv);
        }
    }

    private void getResultSetTextForPlan(List<ProduceSamIn> resultSet, ModelAndView mv) {
        List<PubFlag> billStatues = (List<PubFlag>) mv.getModelMap().get(SamBaseController.BILL_STATUES);
        List<PubFlag> samTypes = (List<PubFlag>) mv.getModelMap().get(SamBaseController.SAM_TYPES);

        for (ProduceSamIn sd : resultSet) {
            if (billStatues != null && !billStatues.isEmpty()) {
                sd.setOrderStateName(DBUtil.getTextByCode(sd.getOrderState(), billStatues));
            }
        }
        for (ProduceSamIn sd : resultSet) {
            if (samTypes != null && !samTypes.isEmpty()) {
                sd.setSamTypeName(DBUtil.getTextByCode(sd.getSamType(), samTypes));
            }
        }
    }

    public OperationResult queryForOpPlan(HttpServletRequest request, ProduceSamInMapper esiMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        ProduceSamIn queryCondition;
        List<ProduceSamIn> resultSet;

        try {
            queryCondition = this.getQueryConditionForOpPlan(request, opResult);
            if (queryCondition == null) {
                return null;
            }
            resultSet = esiMapper.queryPlan(queryCondition);
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    public OperationResult queryPlan(HttpServletRequest request, ProduceSamInMapper esiMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        ProduceSamIn queryCondition;
        List<ProduceSamIn> resultSet;

        try {
            queryCondition = this.getQueryConditionIn(request);
            resultSet = esiMapper.queryPlan(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    public OperationResult addPlan(HttpServletRequest request, ProduceSamInMapper esiMapper, BillMapper billMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        ProduceSamIn vo = this.getReqAttributePlan(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        String preMsg = "";
        try {
            //通过出库单号查询 起始逻辑卡号 与 入库数量 
            IssueSamOut ivo = new IssueSamOut();
            ivo.setOrder_no(vo.getIssueOrderNo());
            List<IssueSamOut> iResults = isoMapper.queryPlan(ivo);
            if (iResults.size() > 0) {
                ivo = (IssueSamOut) iResults.get(0);
                vo.setStartLogicNo(ivo.getStart_logic_no());
                vo.setOrderNum(Integer.valueOf(ivo.getOrder_num()));
            } else {
                rmsg.addMessage("添加失败！卡发行出库单号：" + vo.getIssueOrderNo() + "无法查询数据！");
                return rmsg;
            }
            //检测是否已经入库（包括未审核订单）
            vo.setOrderState(null);
            if (psiMapper.queryPlan(vo).size() > 0) {
                rmsg.addMessage("添加失败！卡发行出库单号：" + vo.getIssueOrderNo() + "已经存在入库单！");
                return rmsg;
            }
            vo.setOrderState(SamCardConstant.RECORD_FLAG_UNAUDITED);
            String fStartLogicNo = getStLogicNoFive(vo.getStartLogicNo());//取起始逻辑卡号后5位
            String eStartLogicNo = getStLogicNoEle(vo.getStartLogicNo());//取起始逻辑卡号前11位
            vo.setfStartLogicNo(fStartLogicNo);
            vo.seteStartLogicNo(eStartLogicNo);
            List<String> logicNos = psiMapper.checkStock(vo);
            //检验出库单对应逻辑卡号库存是否足够
            //卡发行出库单号 对应逻辑卡号数量 大于等于 卡制作数量
            if (logicNos.size() >= vo.getOrderNum()) {
//                vo.setStartLogicNo(logicNos.get(0));
//                vo.setOrderNum(logicNos.size());
                n = this.addPlanByTrans(request, esiMapper, billMapper, vo, rmsg);
                if (n == 0) {
                    rmsg.addMessage("添加失败！卡发行出库单号：" + vo.getIssueOrderNo() + "库存不足！");
                    return rmsg;
                }
            } else {
                rmsg.addMessage("添加失败！卡发行出库单号：" + vo.getIssueOrderNo() + "库存不足！");
                return rmsg;
            }
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), opLogMapper);
        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    public OperationResult deletePlan(HttpServletRequest request, ProduceSamInMapper esiMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<ProduceSamIn> pos = this.getReqAttributeForPlanDelete(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.deletePlanByTrans(request, esiMapper, pos);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    public OperationResult auditPlan(HttpServletRequest request, ProduceSamInMapper esiMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<ProduceSamIn> pos = this.getReqAttributeForPlanAudit(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            List<String> orderNoList = new ArrayList<>();
            for (ProduceSamIn po : pos) {
                orderNoList.add(po.getOrderNo());
                int auditPlanByTrans = this.auditPlanByTrans(request, esiMapper, po);
                if (auditPlanByTrans == -2) {
                    rmsg.addMessage("单号" + po.getOrderNo() + "当前状态下不能审核或库存不足！");
                    return rmsg;
                }
                if (auditPlanByTrans == -1) {
                    rmsg.addMessage("单号" + po.getOrderNo() + "库存不足！");
                    return rmsg;
                }
                request.setAttribute("orderNoList", orderNoList);
                n += auditPlanByTrans;
            }
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_AUDIT, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_AUDIT, request, LogConstant.auditSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.auditSuccessMsg(n));
        return rmsg;
    }

    public OperationResult modifyPlan(HttpServletRequest request, ProduceSamInMapper esiMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        ProduceSamIn vo = this.getReqAttributePlan(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        String preMsg = "";
        try {
            //通过出库单号查询 起始逻辑卡号 与 入库数量 
            IssueSamOut ivo = new IssueSamOut();
            ivo.setOrder_no(vo.getIssueOrderNo());
            List<IssueSamOut> iResults = isoMapper.queryPlan(ivo);
            if (iResults.size() > 0) {
                ivo = (IssueSamOut) iResults.get(0);
                vo.setOrderNum(Integer.valueOf(ivo.getOrder_num()));
            } else {
                rmsg.addMessage("修改失败！卡发行出库单号：" + vo.getIssueOrderNo() + "无法查询数据！");
                return rmsg;
            }
            List<String> logicNos = psiMapper.checkStock(vo);
            //检验出库单对应逻辑卡号库存是否足够
            //卡发行出库单号 对应逻辑卡号数量 大于等于 卡制作数量
            if (logicNos.size() >= vo.getOrderNum()) {
                if (vo.getStartLogicNo() == null || vo.getStartLogicNo().trim().equals("")) {
                    vo.setStartLogicNo(logicNos.get(0));
                }
//                vo.setOrderNum(logicNos.size());
                n = this.modifyPlanByTrans(request, esiMapper, vo, rmsg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), opLogMapper);
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    @RequestMapping("/ProduceSamInExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        List results = this.getBufferElements(request);
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.samin.entity.ProduceSamIn");
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults
                = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response,
                ExpUtil.exportExcel(queryResults, request));
    }

}
