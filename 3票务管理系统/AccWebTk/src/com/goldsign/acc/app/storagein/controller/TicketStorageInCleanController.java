/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storagein.controller;

import com.goldsign.acc.app.storagein.entity.TicketStorageInBill;
import com.goldsign.acc.app.storagein.entity.TicketStorageInCleanInBill;
import com.goldsign.acc.app.storagein.mapper.TicketStorageInCleanInBillMapper;

import com.goldsign.acc.frame.constant.CommandConstant;

import com.goldsign.acc.frame.constant.LogConstant;

import com.goldsign.acc.frame.controller.StorageOutInBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.BillMapper;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.CharUtil;

import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.ExpUtil;

import com.goldsign.acc.frame.util.LogUtil;

import com.goldsign.acc.frame.vo.OperationResult;
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
 * 清洗入库
 *
 * @author luck
 */
@Controller
public class TicketStorageInCleanController extends TicketStorageInCleanParentController {

    @Autowired
    private TicketStorageInCleanInBillMapper tsicMapper;

    @RequestMapping(value = "/ticketStorageInCleanManage")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/storagein/ticketStorageInCleanOutBill.jsp");
        String command = request.getParameter("command");
        String operType = request.getParameter("operType");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (this.isOperForPlan(operType)) {
                    //入库单操作
                    if (command.equals(CommandConstant.COMMAND_QUERY)) {
                        opResult = this.queryPlan(request, this.tsicMapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_ADD)) {
                        opResult = this.addPlan(request, this.tsicMapper, this.billMapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_DELETE)) {
                        opResult = this.deletePlan(request, this.tsicMapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_AUDIT)) {
                        opResult = this.auditPlan(request, this.tsicMapper, this.operationLogMapper);
                    }
                } else if (this.isOperForPlanDetail(operType)) {
                    //入库单明细操作
                    mv = new ModelAndView("/jsp/storagein/ticketStorageInCleanInBillDetial.jsp");
                    if (command.equals(CommandConstant.COMMAND_QUERY_DETAIL)) {
                        opResult = this.queryPlanDetail(request, this.tsicMapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_ADD)) {
                        opResult = this.addPlanDetail(request, this.tsicMapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_DELETE)) {
                        opResult = this.deletePlanDetail(request, this.tsicMapper, this.operationLogMapper);
                    }
                } else if (operType.equals("diff")) {
                    //差额
                    mv = new ModelAndView("/jsp/storagein/ticketStorageOutCleanInBillDiff.jsp");
                    opResult = this.queryPlanDiff(request, this.tsicMapper, this.operationLogMapper);
                }

                if (this.isUpdateOp(command, operType))//更新操作，增、删、改、审核,查询更新结果或原查询结果
                {

                    this.queryUpdateResult(command, operType, request, tsicMapper, operationLogMapper, opResult, mv);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }

        String[] attrNames = this.getAttributeNames(command, operType);
        this.setPageOptions(attrNames, mv, request, response); //设置页面线路、车站、票卡、仓库等选项值、版本
        this.setPageOptionsForProduce(operType, mv, request, response); //生产使用参数
        this.getResultSetText(opResult.getReturnResultSet(), mv, operType);
        this.baseHandlerForOutIn(request, response, mv); //出入库基本回传参数
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集
        return mv;
    }

    private String[] getAttributeNames(String command, String operType) {
        if (this.isOperForPlan(operType)) {
            //仓库/票卡主类型/票卡子类型/出库原因/计划单状态/ES操作员
            String[] attrNames = {STORAGES, IC_CARD_MAIN, IC_CARD_MAIN_SERIAL, IC_CARD_SUB, IC_CARD_MAIN_SUB,
                IN_OUT_REASON_PRODUCE, IN_OUT_REASON_PRODUCE_SERIAL, ES_OPERATORS, BILL_STATUES, CLEAN_OUT_BILL};
            return attrNames;

        }
        if (this.isOperForPlanDetail(operType)) {
            //工作类型,票卡主类型,票卡子类型,记名卡标志,仓库,票区,出库原因,出库卡面值,乘次票限制模式,进站线路,进站车站,测试标志,订单类型
            String[] attrNames = {ES_WORK_TYPES, IC_CARD_MAIN, IC_CARD_MAIN_SERIAL, IC_CARD_SUB, IC_CARD_MAIN_SUB,
                STORAGES, AREAS, AREAS_RECOVER, STORAGE_AREAS_RECOVER, IN_OUT_REASON_PRODUCE, IN_OUT_REASON_PRODUCE_SERIAL,
                CARD_MONEYS, MODES, IC_LINES, IC_STATIONS, IC_LINE_STATIONS, TEST_FLAGS, ORDER_TYPES, SIGN_CARDS};
            return attrNames;

        }

        /*
        String[] attrNames = {IC_CARD_MAIN, IC_CARD_MAIN_SERIAL, IC_CARD_SUB, IC_CARD_MAIN_SUB,
            IN_OUT_REASON_PRODUCE, IN_OUT_REASON_PRODUCE_SERIAL, ES_OPERATORS, STORAGES, AREAS,
            BILL_STATUES, IC_LINES, IC_LINE_STATIONS, STORAGE_AREAS, NEW_OLD_FLAGS,
            TEST_FLAGS, ORDER_TYPES, MODES, SIGN_CARDS,
            CARD_MONEYS, ES_WORK_TYPES, AFC_LINES, AFC_STATIONS,
            AFC_CARD_MAIN, AFC_CARD_SUB, SALE_FLAGS, HANDLE_FLAGS, IC_STATIONS};
         */
        return new String[0];

    }

    private void queryUpdateResult(String command, String operType, HttpServletRequest request, TicketStorageInCleanInBillMapper tsopMapper,
            OperationLogMapper opLogMapper, OperationResult opResult, ModelAndView mv) throws Exception {
        if (!command.equals(CommandConstant.COMMAND_ADD)) {
            this.saveQueryControlDefaultValues(request, mv); //保存查询条件
        }
        if (this.isOperForPlan(operType)) { //计划单查询      
            this.queryForOpPlan(request, this.tsicMapper, this.operationLogMapper, opResult);
        }
        if (this.isOperForPlanDetail(operType)) {//计划单明细
            this.queryForOpPlanDetail(request, this.tsicMapper, this.operationLogMapper, opResult);
        }

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
        if (this.isOperForPlan(operType)) {

            this.getResultSetTextForPlan(resultSet, mv);
            return;

        }
        if (this.isOperForPlanDetail(operType)) {
            this.getResultSetTextForPlanDetail(resultSet, mv);
            return;
        }

    }

    private void getResultSetTextForPlanDetail(List<TicketStorageInCleanInBill> resultSet, ModelAndView mv) {
        System.out.println("***come into getResultSetTextForPlan***");
        List<PubFlag> billStatues = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.BILL_STATUES);
        List<PubFlag> icCardMains = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_MAIN);
        List<PubFlag> icCardSubs = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_SUB);
        List<PubFlag> storages = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.STORAGES);
        List<PubFlag> areas = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.AREAS);


        /*

            if (attrName.equals(NEW_OLD_FLAGS)) {//新旧票标识
                options = pubFlagMapper.getCodesByType(PubFlagConstant.PUB_FLAG_TYPE_NEW_OLD_CARD_FLAG);
                mv.addObject(attrName, options);
                continue;
            }

         */
        for (TicketStorageInCleanInBill sd : resultSet) {
            if (billStatues != null && !billStatues.isEmpty()) {
                sd.setRecord_flag_name(DBUtil.getTextByCode(sd.getRecord_flag(), billStatues));
            }

            if (icCardMains != null && !icCardMains.isEmpty()) {
                sd.setIcMainTypeName(DBUtil.getTextByCode(sd.getIcMainType(), icCardMains));

            }
            if (icCardSubs != null && !icCardSubs.isEmpty()) {
                sd.setIcSubTypeName(DBUtil.getTextByCode(sd.getIcSubType(), sd.getIcMainType(), icCardSubs));

            }

            if (storages != null && !storages.isEmpty()) {
                sd.setStorageName(DBUtil.getTextByCode(sd.getStorageId(), storages));
            }

            if (areas != null && !areas.isEmpty()) {
                sd.setAreaName(DBUtil.getTextByCode(sd.getAreaId(), sd.getStorageId(), areas));
                switch (sd.getAreaId()) {
                    case "02":
                        sd.setCardType("有效票");
                        break;
                    case "07":
                        sd.setCardType("实际结余");
                        break;
                    case "06":
                        sd.setCardType("人工废票");
                        break;
                }
            }
        }

    }

    private void getResultSetTextForPlan(List<TicketStorageInBill> resultSet, ModelAndView mv) {
        List<PubFlag> billStatues = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.BILL_STATUES);

        /*

            if (attrName.equals(NEW_OLD_FLAGS)) {//新旧票标识
                options = pubFlagMapper.getCodesByType(PubFlagConstant.PUB_FLAG_TYPE_NEW_OLD_CARD_FLAG);
                mv.addObject(attrName, options);
                continue;
            }

         */
        for (TicketStorageInBill sd : resultSet) {
            if (billStatues != null && !billStatues.isEmpty()) {
                sd.setRecord_flag_name(DBUtil.getTextByCode(sd.getRecord_flag(), billStatues));
            }

        }
    }

    public OperationResult queryForOpPlan(HttpServletRequest request, TicketStorageInCleanInBillMapper tsocMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageInBill queryCondition;
        List<TicketStorageInBill> resultSet;

        try {
            queryCondition = this.getQueryConditionForOpPlan(request, opResult);
            queryCondition.setIn_type(inType);
//            if (queryCondition == null) {
//                return null;
//            }
            resultSet = this.queryForInBill(request, queryCondition);
            opResult.setReturnResultSet(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    public OperationResult queryForOpPlanDetail(HttpServletRequest request, TicketStorageInCleanInBillMapper tsopMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageInCleanInBill queryCondition;
        List<TicketStorageInCleanInBill> resultSet;

        try {
            queryCondition = this.getQueryConditionForOpPlanDetail(request, opResult);
            if (queryCondition == null) {
                return null;
            }
            resultSet = tsopMapper.queryPlanDetail(queryCondition);
            if (!resultSet.isEmpty()) {
                request.setAttribute("delFlag", "true");
            }

            opResult.setReturnResultSet(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    public OperationResult queryPlan(HttpServletRequest request, TicketStorageInCleanInBillMapper tsocMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageInBill queryCondition;
        List<TicketStorageInBill> resultSet;

        try {
            queryCondition = this.getQueryConditionIn(request);
            resultSet = this.queryForInBill(request, queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    public OperationResult queryPlanDetail(HttpServletRequest request, TicketStorageInCleanInBillMapper tsopMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageInCleanInBill queryCondition;
        List<TicketStorageInCleanInBill> resultSet;
        int i;

        try {
            queryCondition = this.getQueryConditionPlanDetail(request);
            int numFromPlanDetial = tsopMapper.getNumFromPlanDetial(queryCondition);  //获取明细表主表中是否有数据，没有查询分表
            if (numFromPlanDetial > 0) {
                queryCondition.setTable("w_acc_tk.w_ic_in_store_detail");
            } else {
                String tableName = tsopMapper.getTableName(queryCondition);
                if (tableName != null && tableName.length() > 0) {
                    queryCondition.setTable("w_acc_tk." + tableName);
                } else {
                    queryCondition.setTable("w_acc_tk.w_ic_in_store_detail");
                }
            }
            resultSet = tsopMapper.queryPlanDetail(queryCondition);
            if (!resultSet.isEmpty()) {
                request.setAttribute("delFlag", "true");
            }
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    public OperationResult queryPlanDiff(HttpServletRequest request, TicketStorageInCleanInBillMapper tsopMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageInCleanInBill queryCondition;
        List<TicketStorageInCleanInBill> resultSet;

        try {
            queryCondition = this.getQueryConditionPlanDiff(request);
            resultSet = tsopMapper.queryPlanDiff(queryCondition);

            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    public OperationResult addPlanDetail(HttpServletRequest request, TicketStorageInCleanInBillMapper tsopMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageInCleanInBill vo = this.getReqAttributePlanDetail(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        String preMsg = "";
        try {

            if (this.checkInflag(vo, tsopMapper)) {
                rmsg.addMessage("添加明细出错，出库单已全部入库!");
                return rmsg;
            }
            n = this.addPlanDetailByTrans(request, tsopMapper, vo, rmsg);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), opLogMapper);
        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    public OperationResult addPlan(HttpServletRequest request, TicketStorageInCleanInBillMapper tsopMapper, BillMapper billMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageInCleanInBill vo = this.getReqAttributePlan(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        String preMsg = "";
        try {
            if (this.isExistInBill(vo, tsopMapper)) {
                rmsg.addMessage("添加出错，出库单已全部入库，请再查询检查！");
                return rmsg;
            }
            n = this.addPlanByTrans(request, tsopMapper, billMapper, vo, rmsg);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), opLogMapper);
        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    public OperationResult deletePlan(HttpServletRequest request, TicketStorageInCleanInBillMapper tsopMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<TicketStorageInCleanInBill> pos = this.getReqAttributeForPlanDelete(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            for (TicketStorageInCleanInBill po : pos) {
                if (this.isExistingPlanDetial(po, tsopMapper)) {
                    rmsg.addMessage("删除失败,请先删除明细！");
                    return rmsg;
                }
            }
            n = this.deletePlanByTrans(request, tsopMapper, pos);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    public OperationResult auditPlan(HttpServletRequest request, TicketStorageInCleanInBillMapper tsopMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<TicketStorageInCleanInBill> pos = this.getReqAttributeForPlanAudit(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        HashMap<String, Object> hmParm;

        try {
            for (TicketStorageInCleanInBill po : pos) {
                hmParm = this.auditPlanByTrans(request, tsopMapper, po);
                String bill_no = (String) hmParm.get("bill_no");
                request.setAttribute("bill_no", bill_no);
                this.handleResult(hmParm, rmsg);
                n += 1;

            }

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_AUDIT, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_AUDIT, request, LogConstant.auditSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.auditSuccessMsg(n));

        return rmsg;
    }

    public OperationResult deletePlanDetail(HttpServletRequest request, TicketStorageInCleanInBillMapper tsopMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<TicketStorageInCleanInBill> pos = this.getReqAttributeForPlanDetailDelete(request);
        TicketStorageInCleanInBill po = new TicketStorageInCleanInBill();
        po.setBillNo(pos.get(0).getBillNo());
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.deletePlanDetailByTrans(request, tsopMapper, po);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    @RequestMapping("/TicketStorageInCleanExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        List results = this.getBufferElements(request);
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.storagein.entity.TicketStorageInBill");
        String expAllFields = request.getParameter("expAllFields");
//        List<Map<String, Object>> queryResults
//                = ExpUtil.entityToMap(expAllFields, results);
        List<Map<String, Object>> queryResults = ExpUtil.entityToMapForInBill(expAllFields, results, "QR");
        ExpUtil.renderExportExcelPath(response,
                ExpUtil.exportExcel(queryResults, request));
    }

}
