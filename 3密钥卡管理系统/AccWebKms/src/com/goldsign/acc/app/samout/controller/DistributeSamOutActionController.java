/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.samout.controller;

import com.goldsign.acc.app.samout.entity.DistributeSamOutAction;
import com.goldsign.acc.app.samout.entity.SamStock;
import com.goldsign.acc.app.samout.mapper.DistributeSamOutActionMapper;
import com.goldsign.acc.app.samout.mapper.SamStockMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.constant.SamCardConstant;
import com.goldsign.acc.frame.controller.SamBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.BillMapper;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.util.ArrayList;
import java.util.HashMap;
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
 * @author liudz 20170824 卡分发出库
 */
@Controller
public class DistributeSamOutActionController extends DistributeSamOutActionParentController {

    @Autowired
    private DistributeSamOutActionMapper dsoaMapper;

    @Autowired
    private SamStockMapper ssMapper;

    List<String> storageIds = new ArrayList<>();  //仓库（操作)
    List<String> storageAllIds = new ArrayList<>();

    @RequestMapping(value = "/distributeSamOutAction")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/samout/distributeSamOutAction.jsp");
        String command = request.getParameter("command");
        String operType = request.getParameter("operType");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (this.isOperForPlan(operType)) {
                    //计划单操作
                    if (command.equals(CommandConstant.COMMAND_QUERY)) {
                        opResult = this.queryPlan(request, this.dsoaMapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_ADD)) {
                        opResult = this.addPlan(request, this.dsoaMapper, this.billMapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_DELETE)) {
                        opResult = this.deletePlan(request, this.dsoaMapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_AUDIT)) {
                        opResult = this.auditPlan(request, this.dsoaMapper, this.ssMapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_MODIFY)) {
                        opResult = this.modifyPlan(request, this.dsoaMapper, this.operationLogMapper);
                    }
                } else if (this.isOperForPlanDetail(operType)) {
                    mv = new ModelAndView("/jsp/samout/distributeSamOutActionDetail.jsp");
                    opResult = this.queryPlanDetail(request, this.dsoaMapper, this.operationLogMapper);

                }
                if (this.isUpdateOp(command, operType))//更新操作，增、删、改、审核,查询更新结果或原查询结果
                {

                    this.queryUpdateResult(command, operType, request, dsoaMapper, operationLogMapper, opResult, mv);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }

        String[] attrNames = {LINE_ES, SAM_TYPES, BILL_STATUES};
        this.setPageOptions(attrNames, mv, request, response); //设置页面线路、车站、票卡、仓库等选项值、版本
        this.setPageOptionsForProduce(operType, mv, request, response); //生产使用参数
        this.getResultSetText(opResult.getReturnResultSet(), mv, operType);
        this.baseHandlerForOutIn(request, response, mv); //出入库基本回传参数
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集
        return mv;
    }

    protected void setPageOptionsForProduce(String operType, ModelAndView mv, HttpServletRequest request, HttpServletResponse response) {
        if (this.isOperForPlanDetail(operType)) {
            String validDays = "100";// bo.getCardValidDays();
            if (validDays != null) {
                mv.addObject("validDays", validDays);//票卡类型有效天数

            }
        }
    }

    private void getResultSetText(List resultSet, ModelAndView mv, String operType) {
        if (this.isOperForPlan(operType) || this.isOperForPlanDetail(operType)) {
            this.getResultSetTextForPlan(resultSet, mv);
            return;

        }
//        if (this.isOperForOutBill(operType) || this.isOperForOutBillDetail(operType)) {
//            this.getResultSetTextForOutBill(resultSet, mv);
//            return;
//        }
//        if (this.isOperForOrder(operType)) {
//            this.getResultSetTextForOrder(resultSet, mv);
//            return;
//        }
    }

    private void getResultSetTextForPlan(List<DistributeSamOutAction> resultSet, ModelAndView mv) {

        List<PubFlag> samTypes = (List<PubFlag>) mv.getModelMap().get(SamBaseController.SAM_TYPES);
        List<PubFlag> billStatus = (List<PubFlag>) mv.getModelMap().get(SamBaseController.BILL_STATUES);
        List<PubFlag> linees = (List<PubFlag>) mv.getModelMap().get(SamBaseController.LINE_ES);

        for (DistributeSamOutAction sd : resultSet) {
            if (samTypes != null && !samTypes.isEmpty()) {
                sd.setSam_type_name(DBUtil.getTextByCode(sd.getSam_type(), samTypes));
            }

            if (billStatus != null && !billStatus.isEmpty()) {
                sd.setOrder_state_name(DBUtil.getTextByCode(sd.getOrder_state(), billStatus));

            }
            if (linees != null && !linees.isEmpty()) {
                sd.setLine_es_name(DBUtil.getTextByCode(sd.getLine_es(), linees));

            }

        }

    }

    public OperationResult queryPlan(HttpServletRequest request, DistributeSamOutActionMapper dsoaMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        DistributeSamOutAction queryCondition;
        List<DistributeSamOutAction> resultSet;

        try {
            queryCondition = this.getQueryConditionPlan(request);

            resultSet = dsoaMapper.queryPlan(queryCondition);

            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    public OperationResult addPlan(HttpServletRequest request, DistributeSamOutActionMapper dsoaMapper, BillMapper billMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        DistributeSamOutAction vo = this.getReqAttributePlan(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        String preMsg = "";
        try {
            Vector<String> v = null;
            try {
                v = dsoaMapper.checkLogicNo(vo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (v.size() >= Integer.valueOf(vo.getOrder_num())) {
                n = this.addPlanByTrans(request, dsoaMapper, billMapper, vo, rmsg);
                if (n == 0) {
                    rmsg.addMessage("Sam卡类型" + vo.getSam_type() + " 或ES线路" + vo.getLine_es() + "不存在！");
                    return rmsg;
                }
            } else {
                logUtil.logOperation(CommandConstant.COMMAND_ADD, request, "卡分发出库"
                        + LogConstant.OPERATIION_FAIL_LOG_MESSAGE, opLogMapper);
                rmsg.addMessage("添加失败！库存不足或所选卡号的卡类型与库存不一致！");
                return rmsg;

            }
            List<String> vLogicNos = dsoaMapper.checkLogicNoForAudit(vo);
            if (checkContinuity(vLogicNos, vo)) {
                rmsg.addMessage("成功添加一条记录，但是所选逻辑卡号段不连续，存在断号情况，具体断号情况请查看库存！");
                return rmsg;
            }
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), opLogMapper);
        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    public OperationResult deletePlan(HttpServletRequest request, DistributeSamOutActionMapper dsoaMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<DistributeSamOutAction> pos = this.getReqAttributeForPlanDelete(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.deletePlanByTrans(request, dsoaMapper, pos);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    public OperationResult modifyPlan(HttpServletRequest request, DistributeSamOutActionMapper dsoaMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        DistributeSamOutAction vo = this.getReqAttributePlanForUpdate(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        String preMsg = "";
        try {
            Vector<String> v = dsoaMapper.checkLogicNo(vo);
            //检验对应逻辑卡号库存是否足够(逻辑卡号段内、产品卡(03:成品卡入库、05:卡回收入库)、在库、好卡)
            if (v.size() >= Integer.valueOf(vo.getOrder_num())) {
                n = this.modifyPlanByTrans(request, dsoaMapper, vo, rmsg);//修改卡分发出库单
            } else {
                logUtil.logOperation(CommandConstant.COMMAND_ADD, request, "卡分发出库"
                        + LogConstant.OPERATIION_FAIL_LOG_MESSAGE, opLogMapper);
                rmsg.addMessage("修改失败！库存不足！");
                return rmsg;

            }
            List<String> vLogicNos = dsoaMapper.checkLogicNoForAudit(vo);
            if (checkContinuity(vLogicNos, vo)) {
                rmsg.addMessage("成功修改一条记录，但是所选逻辑卡号段不连续，存在断号，具体断号情况请查看库存！");
                return rmsg;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), opLogMapper);
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    public DistributeSamOutAction getReqAttributeForCheck(DistributeSamOutAction vo) {

        vo.setE_start_logic_no(FormUtil.getStLogicNoNine(vo.getStart_logic_no()));//取逻辑卡号前9位
        vo.setF_start_logic_no(FormUtil.getStLogicNoSeven(vo.getStart_logic_no()));//取逻辑卡号后7位

        //检验对应逻辑卡号库存是否足够(逻辑卡号段内、产品卡(03:成品卡入库、05:卡回收入库)、在库、好卡)
        vo.setIs_bad(SamCardConstant.SAM_CARD_STATE_OK);
        vo.setIs_instock(SamCardConstant.STOCK_STATE_IN);
        //(03:成品卡入库、05:卡回收入库)
        vo.setStock_state("'" + SamCardConstant.STOCK_STATE_PRODUCT_IN + "','" + SamCardConstant.STOCK_STATE_RECYCLE_IN + "'");
        vo.setProduce_type(SamCardConstant.PRODUCE_TYPE_PRODUCT);
        return vo;
    }

    public OperationResult auditPlan(HttpServletRequest request, DistributeSamOutActionMapper dsoaMapper, SamStockMapper ssMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<DistributeSamOutAction> pos = this.getReqAttributeForPlanAudit(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            List<String> orderNoList = new ArrayList<>();
            for (DistributeSamOutAction po : pos) {
                orderNoList.add(po.getOrder_no());
                int auditPlanByTrans = this.auditPlanByTrans(request, dsoaMapper, ssMapper, po);
                if (n == -1) {
                    rmsg.addMessage("单号：" + po.getOrder_no() + "库存不足，审核失败！");
                    return rmsg;
                }
                if (DistributeSamOutActionController.m == 1) {
                    rmsg.addMessage("成功审核"+(n+1)+"条记录。"+"单号:" + po.getOrder_no() + "的起始逻辑卡号:" + DistributeSamOutActionController.oldFirstStartLogicNo + "已存在,系统已为您自动更新起始逻辑卡号。");
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

    private void queryUpdateResult(String command, String operType, HttpServletRequest request, DistributeSamOutActionMapper dsoaMapper,
            OperationLogMapper opLogMapper, OperationResult opResult, ModelAndView mv) throws Exception {
        if (!command.equals(CommandConstant.COMMAND_ADD)) {
            this.saveQueryControlDefaultValues(request, mv); //保存查询条件
        }
        if (this.isOperForPlan(operType)) { //计划单查询      
            this.queryForOpPlan(request, this.dsoaMapper, this.operationLogMapper, opResult);
        }

    }

    public OperationResult queryForOpPlan(HttpServletRequest request, DistributeSamOutActionMapper dsoaMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        DistributeSamOutAction queryCondition;
        List<DistributeSamOutAction> resultSet;

        try {
            queryCondition = this.getQueryConditionForOpPlan(request, opResult);
            if (queryCondition == null) {
                return null;
            }
            resultSet = dsoaMapper.queryPlan(queryCondition);
            opResult.setReturnResultSet(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    public OperationResult queryPlanDetail(HttpServletRequest request, DistributeSamOutActionMapper dsoaMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        DistributeSamOutAction queryCondition;
        List<DistributeSamOutAction> resultSet;

        try {
            queryCondition = this.getQueryConditionPlanDetail(request);
            resultSet = dsoaMapper.queryPlanDetail(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }
    
    @RequestMapping("/DistributeSamOutActionExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.samout.entity.DistributeSamOutAction");
        List<Map<String,String>> queryResults = this.entityToMap(results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

    private List<Map<String, String>> entityToMap(List results) {
        List<Map<String,String>> list =  new ArrayList<>();
        int row = results.size();
        for(int n=0;n<row ;n++){
            DistributeSamOutAction vo = (DistributeSamOutAction)results.get(n);
            Map<String,String> map = new HashMap<>();
            map.put("order_no", vo.getOrder_no());
            map.put("line_es_name", vo.getLine_es_name());
            map.put("sam_type_name", vo.getSam_type_name());
            map.put("start_logic_no", vo.getStart_logic_no());
            map.put("order_num", vo.getOrder_num());
            map.put("order_state_name", vo.getOrder_state_name());
            map.put("out_stock_oper", vo.getOut_stock_oper());
            map.put("out_stock_time", vo.getOut_stock_time());
            map.put("audit_order_oper", vo.getAudit_order_oper());
            map.put("audit_order_time", vo.getAudit_order_time());
            map.put("remark", vo.getRemark());
            map.put("get_card_oper", vo.getGet_card_oper());

           
            list.add(map);
        }
        return list;
    }
}
