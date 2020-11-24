/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.samin.controller;

import com.goldsign.acc.app.samin.entity.RecycleSamIn;
import com.goldsign.acc.app.samin.mapper.EmptySamInMapper;
import com.goldsign.acc.app.samin.mapper.RecycleSamInMapper;
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
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * 卡回收入库
 *
 * @author luck
 */
@Controller
public class RecycleSamInController extends RecycleSamInParentController {

    @Autowired
    private EmptySamInMapper esiMapper;
    @Autowired
    private RecycleSamInMapper rsiMapper;

    @RequestMapping(value = "/recycleSamInAction")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/samin/recycleSamIn.jsp");
        String command = request.getParameter("command");
        String operType = request.getParameter("operType");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (this.isOperForPlan(operType)) {
                    //入库单操作
                    if (command.equals(CommandConstant.COMMAND_QUERY)) {
                        opResult = this.queryPlan(request, this.rsiMapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_ADD)) {
                        opResult = this.addPlan(request, this.rsiMapper, this.billMapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_DELETE)) {
                        opResult = this.deletePlan(request, this.rsiMapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_AUDIT)) {
                        opResult = this.auditPlan(request, this.rsiMapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_MODIFY)) {
                        opResult = this.modifyPlan(request, this.rsiMapper, this.operationLogMapper);
                    }
                } else if (this.isOperForPlanDetail(operType)) {
                    mv = new ModelAndView("/jsp/samin/recycleSamInDetail.jsp");
                    opResult = this.queryPlanDetail(request, this.rsiMapper, this.operationLogMapper);

                }
                if (this.isUpdateOp(command, operType))//更新操作，增、删、改、审核,查询更新结果或原查询结果
                {

                    this.queryUpdateResult(command, operType, request, rsiMapper, operationLogMapper, opResult, mv);
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

            String[] attrNames = {BILL_STATUES, SAM_TYPES, LINE_ES, ISBADS};
            return attrNames;

        }

        return new String[0];

    }

    private void queryUpdateResult(String command, String operType, HttpServletRequest request, RecycleSamInMapper esiMapper,
            OperationLogMapper opLogMapper, OperationResult opResult, ModelAndView mv) throws Exception {
        if (!command.equals(CommandConstant.COMMAND_ADD)) {
            this.saveQueryControlDefaultValues(request, mv); //保存查询条件
        }
        if (this.isOperForPlan(operType)) { //计划单查询      
            this.queryForOpPlan(request, this.rsiMapper, this.operationLogMapper, opResult);
        }

    }

    private void getResultSetText(List resultSet, ModelAndView mv, String operType) {
        if (this.isOperForPlan(operType)) {
            this.getResultSetTextForPlan(resultSet, mv);
            return;

        }
    }

    private void getResultSetTextForPlan(List<RecycleSamIn> resultSet, ModelAndView mv) {
        List<PubFlag> billStatues = (List<PubFlag>) mv.getModelMap().get(SamBaseController.BILL_STATUES);
        List<PubFlag> samTypes = (List<PubFlag>) mv.getModelMap().get(SamBaseController.SAM_TYPES);
        List<PubFlag> isBads = (List<PubFlag>) mv.getModelMap().get(SamBaseController.ISBADS);
        List<PubFlag> line_es = (List<PubFlag>) mv.getModelMap().get(SamBaseController.LINE_ES);

        for (RecycleSamIn sd : resultSet) {
            if (billStatues != null && !billStatues.isEmpty()) {
                sd.setOrderStateName(DBUtil.getTextByCode(sd.getOrderState(), billStatues));
            }
        }
        for (RecycleSamIn sd : resultSet) {
            if (samTypes != null && !samTypes.isEmpty()) {
                sd.setSamTypeName(DBUtil.getTextByCode(sd.getSamType(), samTypes));
            }
        }
        for (RecycleSamIn sd : resultSet) {
            if (isBads != null && !isBads.isEmpty()) {
                sd.setIsBadName(DBUtil.getTextByCode(sd.getIsBad(), isBads));
            }
        }
        for (RecycleSamIn sd : resultSet) {
            if (line_es != null && !line_es.isEmpty()) {
                sd.setLineEsName(DBUtil.getTextByCode(sd.getLineEs(), line_es));
            }
        }
    }

    public OperationResult queryForOpPlan(HttpServletRequest request, RecycleSamInMapper esiMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        RecycleSamIn queryCondition;
        List<RecycleSamIn> resultSet;

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

    public OperationResult queryPlan(HttpServletRequest request, RecycleSamInMapper esiMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        RecycleSamIn queryCondition;
        List<RecycleSamIn> resultSet;

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

    public OperationResult addPlan(HttpServletRequest request, RecycleSamInMapper esiMapper, BillMapper billMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        RecycleSamIn vo = this.getReqAttributePlan(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        String preMsg = "";
        try {
            List<String> voList = checkStock(vo, esiMapper);
            if (voList.size() < vo.getOrderNum()) {
                rmsg.addMessage("所选线路或SAM卡类型的逻辑卡库存不足！");
                return rmsg;
            }
            if (!vo.getStartLogicNo().equals(voList.get(0))) {
                rmsg.addMessage("添加失败,输入的起始逻辑卡号已经存在库存或所选线路与卡号不符！");
                return rmsg;
            }
            n = this.addPlanByTrans(request, esiMapper, billMapper, vo, rmsg);
            if (n <= 0) {
                rmsg.addMessage("sam卡类型、ES线路类型不存在！");
                return rmsg;
            }
            if (checkContinuity(voList, vo)) {
                rmsg.addMessage("该逻辑卡号段不连续，具体请查看库存！");
            }
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), opLogMapper);
        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    public OperationResult deletePlan(HttpServletRequest request, RecycleSamInMapper esiMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<RecycleSamIn> pos = this.getReqAttributeForPlanDelete(request);
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

    public OperationResult auditPlan(HttpServletRequest request, RecycleSamInMapper esiMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        String check = null;
        check = request.getParameter("check");
        HttpSession session = request.getSession();
        Vector<RecycleSamIn> pos = null;
        if (check == null || !check.trim().equals("checked")) {
            pos = this.getReqAttributeForPlanAudit(request);
            session.setAttribute("pos", pos);
        } else {
            pos = (Vector<RecycleSamIn>) session.getAttribute("pos");
        }

        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            List<String> orderNoList = new ArrayList<>();
            for (RecycleSamIn po : pos) {
                orderNoList.add(po.getOrderNo());
                if (check == null || !check.equals("checked")) {
                    List<RecycleSamIn> voList = esiMapper.queryPlan(po);
                    RecycleSamIn vo = new RecycleSamIn();
                    if (voList != null && !voList.isEmpty()) {
                        vo = (RecycleSamIn) voList.get(0);
                    } else {
                        throw new Exception("单号" + po.getOrderNo() + "订单详细信息缺省！");
                    }
                    String oldNo = vo.getStartLogicNo();
                    String fStartLogicNo = this.getStLogicNoFive(vo.getStartLogicNo());//取起始逻辑卡号后5位
                    String eStartLogicNo = this.getStLogicNoEle(vo.getStartLogicNo());//取起始逻辑卡号前11位
                    vo.seteStartLogicNo(eStartLogicNo);
                    vo.setfStartLogicNo(fStartLogicNo);
                    //检验对应逻辑卡号库存是否足够(逻辑卡号段内、产品卡、出库)              
                    vo.setStockState(SamCardConstant.STOCK_STATE_DISTR_OUT);
                    vo.setIsInstock(SamCardConstant.STOCK_STATE_OUT);
                    vo.setProduceType(SamCardConstant.PRODUCE_TYPE_PRODUCT);
                    List<String> vLogicNos = esiMapper.checkStock(vo);
                    if (checkContinuity(vLogicNos, po) || !oldNo.equals(vLogicNos.get(0))) {
                        if (checkContinuity(vLogicNos, po)) {
                            request.setAttribute("flag", "1");
                        }
                        request.setAttribute("No", po.getOrderNo());
                        if (!oldNo.equals(vLogicNos.get(0))) {
                            request.setAttribute("flag1", "1");
                        }
                        return rmsg;
                    }
                }
                int auditPlanByTrans = this.auditPlanByTrans(request, esiMapper, po);
                if (auditPlanByTrans == -1) {
                    rmsg.addMessage("单号：" + po.getOrderNo() + "库存不足，审核失败！");
                    return rmsg;
                }
                if (RecycleSamInController.m == 1) {
                    rmsg.addMessage("单号:" + po.getOrderNo() + "起始逻辑卡号:" + RecycleSamInController.oldFirstStartLogicNo + "已存在库存,系统已自动更新。");
                }
                request.setAttribute("orderNoList", orderNoList);
                n += auditPlanByTrans;
                session.removeAttribute("pos");
                request.removeAttribute("flag");
                request.removeAttribute("flag1");
            }
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_AUDIT, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_AUDIT, request, LogConstant.auditSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.auditSuccessMsg(n));
        return rmsg;
    }

    public OperationResult modifyPlan(HttpServletRequest request, RecycleSamInMapper esiMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        RecycleSamIn vo = this.getReqAttributePlan(request);

        LogUtil logUtil = new LogUtil();
        int n = 0;
        String preMsg = "";
        try {
            List<String> voList = checkStock(vo, esiMapper);
            if (voList.size() < vo.getOrderNum()) {
                rmsg.addMessage("修改失败,对应逻辑卡库存不足！");
                return rmsg;
            }
            if (!vo.getStartLogicNo().equals(voList.get(0))) {
                rmsg.addMessage("修改失败,输入的起始逻辑卡号已经存在库存！");
                return rmsg;
            }
            n = this.modifyPlanByTrans(request, esiMapper, vo, rmsg);
            if (n <= 0) {
                rmsg.addMessage("sam卡类型、ES线路类型不存在！");
                return rmsg;
            }
            if (checkContinuity(voList, vo)) {
                rmsg.addMessage("该逻辑卡号段不连续，具体请查看库存！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), opLogMapper);
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    public OperationResult queryPlanDetail(HttpServletRequest request, RecycleSamInMapper tsopMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        RecycleSamIn queryCondition;
        List<RecycleSamIn> resultSet;

        try {
            queryCondition = this.getQueryConditionPlanDetail(request);
            resultSet = tsopMapper.queryPlanDetail(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    @RequestMapping("/RecycleSamInExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        List results = this.getBufferElements(request);
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.samin.entity.RecycleSamIn");
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults
                = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response,
                ExpUtil.exportExcel(queryResults, request));
    }

}
