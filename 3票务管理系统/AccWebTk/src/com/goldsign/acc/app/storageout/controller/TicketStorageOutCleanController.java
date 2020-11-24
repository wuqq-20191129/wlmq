/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storageout.controller;

import com.goldsign.acc.app.storageout.entity.TicketStorageOutCleanOutBill;

import com.goldsign.acc.app.storageout.entity.TicketStorageOutProducePlan;
import com.goldsign.acc.app.storageout.mapper.TicketStorageOutCleanMapper;
import com.goldsign.acc.app.storageout.mapper.TicketStorageOutProduceMapper;
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
 * 清洗出库
 *
 * @author luck
 */
@Controller
public class TicketStorageOutCleanController extends TicketStorageOutCleanParentController {

    @Autowired
    private TicketStorageOutCleanMapper tsocMapper;

    List<String> storageIds = new ArrayList<>();  //仓库（操作)
    List<String> storageAllIds = new ArrayList<>();

    @RequestMapping(value = "/ticketStorageOutCleanManage")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/storageout/ticketStorageOutCleanOutBill.jsp");
        String command = request.getParameter("command");
        String operType = request.getParameter("operType");

        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (this.isOperForPlan(operType)) {
                    //出库单操作
                    if (command.equals(CommandConstant.COMMAND_QUERY)) {
                        opResult = this.queryPlan(request, this.tsocMapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_ADD)) {
                        opResult = this.addPlan(request, this.tsocMapper, this.billMapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_DELETE)) {
                        opResult = this.deletePlan(request, this.tsocMapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_AUDIT)) {
                        opResult = this.auditPlan(request, this.tsocMapper, this.operationLogMapper);
                    }
                }
                if (this.isOperForPlanDetail(operType)) {
                    //出库单明细操作
                    mv = new ModelAndView("/jsp/storageout/ticketStorageOutCleanOutBillDetial.jsp");
                    if (command.equals(CommandConstant.COMMAND_QUERY_DETAIL)) {
                        opResult = this.queryPlanDetail(request, this.tsocMapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_ADD)) {
                        opResult = this.addPlanDetail(request, this.tsocMapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_MODIFY)) {
                        opResult = this.modifyPlanDetail(request, this.tsocMapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_DELETE)) {
                        opResult = this.deletePlanDetail(request, this.tsocMapper, this.operationLogMapper);
                    }
                }

                if (this.isUpdateOp(command, operType))//更新操作，增、删、改、审核,查询更新结果或原查询结果
                {

                    this.queryUpdateResult(command, operType, request, tsocMapper, operationLogMapper, opResult, mv);
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
                BILL_STATUES};
            return attrNames;

        }
        if (this.isOperForPlanDetail(operType)) {
            //工作类型,票卡主类型,票卡子类型,记名卡标志,仓库,票区,出库原因,出库卡面值,乘次票限制模式,进站线路,进站车站,测试标志,订单类型
            String[] attrNames = {ES_WORK_TYPES, IC_CARD_MAIN, IC_CARD_MAIN_SERIAL, IC_CARD_SUB, IC_CARD_MAIN_SUB,
                STORAGES, AREAS_RECOVER, STORAGE_AREAS_RECOVER};
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

    private void queryUpdateResult(String command, String operType, HttpServletRequest request, TicketStorageOutCleanMapper tsopMapper,
            OperationLogMapper opLogMapper, OperationResult opResult, ModelAndView mv) throws Exception {
        if (!command.equals(CommandConstant.COMMAND_ADD)) {
            this.saveQueryControlDefaultValues(request, mv); //保存查询条件
        }
        if (this.isOperForPlan(operType)) { //计划单查询      
            this.queryForOpPlan(request, this.tsocMapper, this.operationLogMapper, opResult);
        }
        if (this.isOperForPlanDetail(operType)) {//计划单明细
            this.queryForOpPlanDetail(request, this.tsocMapper, this.operationLogMapper, opResult);
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
        if (this.isOperForPlan(operType) || this.isOperForPlanDetail(operType)) {
            this.getResultSetTextForPlan(resultSet, mv);
            return;

        }

    }

    private void getResultSetTextForPlan(List<TicketStorageOutCleanOutBill> resultSet, ModelAndView mv) {
        List<PubFlag> billStatues = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.BILL_STATUES);
        List<PubFlag> icCardMains = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_MAIN);
        List<PubFlag> icCardSubs = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_SUB);
        List<PubFlag> storages = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.STORAGES);
        List<PubFlag> areas = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.AREAS_RECOVER);


        /*

            if (attrName.equals(NEW_OLD_FLAGS)) {//新旧票标识
                options = pubFlagMapper.getCodesByType(PubFlagConstant.PUB_FLAG_TYPE_NEW_OLD_CARD_FLAG);
                mv.addObject(attrName, options);
                continue;
            }

         */
        for (TicketStorageOutCleanOutBill sd : resultSet) {
            if (billStatues != null && !billStatues.isEmpty()) {
                sd.setRecord_flag_name(DBUtil.getTextByCode(sd.getRecord_flag(), billStatues));
            }

            if (icCardMains != null && !icCardMains.isEmpty()) {
                sd.setIc_main_type_name(DBUtil.getTextByCode(sd.getIc_main_type(), icCardMains));

            }
            if (icCardSubs != null && !icCardSubs.isEmpty()) {
                sd.setIc_sub_type_name(DBUtil.getTextByCode(sd.getIc_sub_type(), sd.getIc_main_type(), icCardSubs));

            }

            if (storages != null && !storages.isEmpty()) {
                sd.setStorage_id_name(DBUtil.getTextByCode(sd.getStorage_id(), storages));
            }

            if (areas != null && !areas.isEmpty()) {
                sd.setArea_id_name(DBUtil.getTextByCode(sd.getArea_id(), sd.getStorage_id(), areas));
            }

        }

    }

    public OperationResult queryForOpPlan(HttpServletRequest request, TicketStorageOutCleanMapper tsocMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageOutCleanOutBill queryCondition;
        List<TicketStorageOutCleanOutBill> resultSet = null;

        try {
            queryCondition = this.getQueryConditionForOpPlan(request, opResult);
            queryCondition.setStorageIds(storageIds);
            queryCondition.setStorageAllIds(storageAllIds);
            if (queryCondition == null) {
                return null;
            }
            resultSet = tsocMapper.queryPlan(queryCondition);
            opResult.setReturnResultSet(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    public OperationResult queryForOpPlanDetail(HttpServletRequest request, TicketStorageOutCleanMapper tsopMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageOutCleanOutBill queryCondition;
        List<TicketStorageOutCleanOutBill> resultSet;

        try {
            queryCondition = this.getQueryConditionForOpPlanDetail(request, opResult);
            if (queryCondition == null) {
                return null;
            }
            resultSet = tsopMapper.queryPlanDetail(queryCondition);
            opResult.setReturnResultSet(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    public OperationResult queryPlan(HttpServletRequest request, TicketStorageOutCleanMapper tsocMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageOutCleanOutBill queryCondition;
        List<TicketStorageOutCleanOutBill> resultSet;
//        HashMap<String ,Object> params = new HashMap();
        storageIds.clear();
        storageAllIds.clear();

        try {
            queryCondition = this.getQueryConditionPlan(request);
            List<PubFlag> storages = this.getStorages(queryCondition.getOperator());
            for (PubFlag p : storages) {
                storageIds.add(p.getCode());
            }
            List<PubFlag> storagesAll = pubFlagMapper.getStoragesAll();
            for (PubFlag p : storagesAll) {
                storageAllIds.add(p.getCode());
            }
            queryCondition.setStorageIds(storageIds);
            queryCondition.setStorageAllIds(storageAllIds);
            resultSet = tsocMapper.queryPlan(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    public OperationResult queryPlanDetail(HttpServletRequest request, TicketStorageOutCleanMapper tsopMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageOutCleanOutBill queryCondition;
        List<TicketStorageOutCleanOutBill> resultSet;

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

    public OperationResult addPlanDetail(HttpServletRequest request, TicketStorageOutCleanMapper tsopMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageOutCleanOutBill vo = this.getReqAttributePlanDetail(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        String preMsg = "";
        try {
            if (this.checkMulStorage(vo, tsopMapper)) {
                rmsg.addMessage("明细表不能存在多个仓库的明细！");
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

    public OperationResult modifyPlanDetail(HttpServletRequest request, TicketStorageOutCleanMapper tsopMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageOutCleanOutBill vo = this.getReqAttributePlanDetail(request);
        vo.setFlag("modify");
        LogUtil logUtil = new LogUtil();
        int n = 0;
        String preMsg = "";
        try {
            if (this.checkMulStorage(vo, tsopMapper)) {
                rmsg.addMessage("明细表不能存在多个仓库的明细！");
                return rmsg;
            }
            n = this.modifyPlanDetailByTrans(request, tsopMapper, vo, rmsg);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), opLogMapper);
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    public OperationResult addPlan(HttpServletRequest request, TicketStorageOutCleanMapper tsopMapper, BillMapper billMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageOutCleanOutBill vo = this.getReqAttributePlan(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        String preMsg = "";
        try {
            n = this.addPlanByTrans(request, tsopMapper, billMapper, vo, rmsg);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), opLogMapper);
        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    public OperationResult deletePlan(HttpServletRequest request, TicketStorageOutCleanMapper tsopMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<TicketStorageOutCleanOutBill> pos = this.getReqAttributeForPlanDelete(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            for (TicketStorageOutCleanOutBill po : pos) {
                if (this.isExistingPlanDetial(po, tsopMapper)) {
                    rmsg.addMessage("不能删除含明细的生产计划单，请先删除生产计划明细单！");
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

    public OperationResult auditPlan(HttpServletRequest request, TicketStorageOutCleanMapper tsopMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<TicketStorageOutCleanOutBill> pos = this.getReqAttributeForPlanAudit(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        HashMap<String, Object> hmParm;

        try {
            for (TicketStorageOutCleanOutBill po : pos) {
                hmParm = this.auditPlanByTrans(request, tsopMapper, po);
                String bill_no = (String) hmParm.get("piBillNoPlanFormal");
                request.setAttribute("bill_no", bill_no);
                this.handleResult(hmParm);
                n += 1;

            }

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_AUDIT, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_AUDIT, request, LogConstant.auditSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.auditSuccessMsg(n));

        return rmsg;
    }

    public OperationResult deletePlanDetail(HttpServletRequest request, TicketStorageOutCleanMapper tsopMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<TicketStorageOutCleanOutBill> pos = this.getReqAttributeForPlanDetailDelete(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.deletePlanDetailByTrans(request, tsopMapper, pos);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    @RequestMapping("/TicketStorageOutCleanExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        List results = this.getBufferElements(request);
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.storageout.entity.TicketStorageOutCleanOutBill");
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults
                = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response,
                ExpUtil.exportExcel(queryResults, request));
    }

}
