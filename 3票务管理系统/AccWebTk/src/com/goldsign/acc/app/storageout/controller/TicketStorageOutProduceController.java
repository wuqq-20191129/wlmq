/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storageout.controller;

import com.goldsign.acc.app.storageout.entity.TicketStorageOutProduceOrder;
import com.goldsign.acc.app.storageout.entity.TicketStorageOutProduceOutBill;
import com.goldsign.acc.app.storageout.entity.TicketStorageOutProducePlan;
import com.goldsign.acc.app.storageout.mapper.TicketStorageOutProduceMapper;
import com.goldsign.acc.frame.constant.CommandConstant;

import com.goldsign.acc.frame.constant.LogConstant;

import com.goldsign.acc.frame.controller.StorageOutInBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.BillMapper;
import com.goldsign.acc.frame.mapper.OperationLogMapper;

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
 *
 * @author hejj
 */
@Controller
public class TicketStorageOutProduceController extends TicketStorageOutProduceParentController {

    @Autowired
    private TicketStorageOutProduceMapper tsopMapper;

    @RequestMapping(value = "/ticketStorageOutProduceManage")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/storageout/ticketStorageOutProducePlan.jsp");
        String command = request.getParameter("command");
        String operType = request.getParameter("operType");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (this.isOperForPlan(operType)) {
                    //计划单操作
                    if (command.equals(CommandConstant.COMMAND_QUERY)) {
                        opResult = this.queryPlan(request, this.tsopMapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_ADD)) {
                        opResult = this.addPlan(request, this.tsopMapper, this.billMapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_DELETE)) {
                        opResult = this.deletePlan(request, this.tsopMapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_AUDIT)) {
                        opResult = this.auditPlan(request, this.tsopMapper, this.operationLogMapper);
                    }
                }
                if (this.isOperForPlanDetail(operType)) {
                    //计划单明细操作
                    mv = new ModelAndView("/jsp/storageout/ticketStorageOutProducePlanDetail.jsp");
                    if (command.equals(CommandConstant.COMMAND_QUERY_DETAIL)) {
                        opResult = this.queryPlanDetail(request, this.tsopMapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_ADD)) {
                        opResult = this.addPlanDetail(request, this.tsopMapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_MODIFY)) {
                        opResult = this.modifyPlanDetail(request, this.tsopMapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_DELETE)) {
                        opResult = this.deletePlanDetail(request, this.tsopMapper, this.operationLogMapper);
                    }
                }
                if (this.isOperForOutBill(operType)) {
                    //出库单操作
                    mv = new ModelAndView("/jsp/storageout/ticketStorageOutProduceOutBill.jsp");
                    if (command.equals(CommandConstant.COMMAND_QUERY)) {
                        //查询操作出库单
                        opResult = this.queryOutBill(request, this.tsopMapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_AUDIT)) {
                        //审核出库单
                        opResult = this.auditOutBill(request, this.tsopMapper, this.operationLogMapper);
                    }
                }
                if (this.isOperForOutBillDetail(operType)) {
                    mv = new ModelAndView("/jsp/storageout/ticketStorageOutProduceOutBillDetail.jsp");
                    if (command.equals(CommandConstant.COMMAND_QUERY)) {
                        //出库单明细
                        opResult = this.queryOutBillDetail(request, this.tsopMapper, this.operationLogMapper);
                    }
                }
                if (this.isOperForOrder(operType)) {
                    mv = new ModelAndView("/jsp/storageout/ticketStorageOutProduceOrder.jsp");
                    if (command.equals(CommandConstant.COMMAND_QUERY)) {
                        opResult = this.queryOrder(request, tsopMapper, operationLogMapper);
                    }
                }
                if (this.isUpdateOp(command, operType))//更新操作，增、删、改、审核,查询更新结果或原查询结果
                {
                    this.queryUpdateResult(command, operType, request, tsopMapper, operationLogMapper, opResult, mv);
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
                IN_OUT_REASON_PRODUCE, IN_OUT_REASON_PRODUCE_SERIAL, ES_OPERATORS, BILL_STATUES};
            return attrNames;

        }
        if (this.isOperForPlanDetail(operType)) {
            //工作类型,票卡主类型,票卡子类型,记名卡标志,仓库,票区,出库原因,出库卡面值,乘次票限制模式,进站线路,进站车站,测试标志,订单类型
            String[] attrNames = {ES_WORK_TYPES, IC_CARD_MAIN, IC_CARD_MAIN_SERIAL, IC_CARD_SUB, IC_CARD_MAIN_SUB,
                STORAGES, AREAS, STORAGE_AREAS, IN_OUT_REASON_PRODUCE, IN_OUT_REASON_PRODUCE_SERIAL,
                CARD_MONEYS, MODES, IC_LINES, IC_STATIONS, IC_LINE_STATIONS, TEST_FLAGS, ORDER_TYPES, SIGN_CARDS,DEV_CODE_ES};
            return attrNames;

        }
        if (this.isOperForOutBill(operType)) {
            //单据状态
            String[] attrNames = {BILL_STATUES};
            return attrNames;

        }
        if (this.isOperForOutBillDetail(operType)) {
            //票卡主类型、票卡子类型、出库原、仓库、票区
            String[] attrNames = {IC_CARD_MAIN, IC_CARD_MAIN_SERIAL, IC_CARD_SUB, IC_CARD_MAIN_SUB,
                IN_OUT_REASON_PRODUCE, IN_OUT_REASON_PRODUCE_SERIAL, STORAGES, AREAS,
                STORAGE_AREAS};
            return attrNames;

        }
        

        if (this.isOperForOrder(operType)) {
            //票卡主类型，票卡子类型，记名卡标志，执行标志，乘次票限制模式，AFC线路，AFC车站
            //发售激活标志,测试标志
            String[] attrNames = {AFC_CARD_MAIN, AFC_CARD_SUB,
                SIGN_CARDS,HANDLE_FLAGS, MODES, AFC_LINES, AFC_STATIONS,
                SALE_FLAGS,TEST_FLAGS
               };
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

    private void queryUpdateResult(String command, String operType, HttpServletRequest request, TicketStorageOutProduceMapper tsopMapper,
            OperationLogMapper opLogMapper, OperationResult opResult, ModelAndView mv) throws Exception {
        if (!command.equals(CommandConstant.COMMAND_ADD)) {
            this.saveQueryControlDefaultValues(request, mv); //保存查询条件
        }
        if (this.isOperForPlan(operType)) { //计划单查询      
            this.queryForOpPlan(request, this.tsopMapper, this.operationLogMapper, opResult);
        }
        if (this.isOperForPlanDetail(operType)) {//计划单明细
            this.queryForOpPlanDetail(request, this.tsopMapper, this.operationLogMapper, opResult);
        }

        if (this.isOperForOutBill(operType)) {//出库单审核
            this.queryForOpOutBill(request, this.tsopMapper, this.operationLogMapper, opResult);
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
        if (this.isOperForOutBill(operType) || this.isOperForOutBillDetail(operType)) {
            this.getResultSetTextForOutBill(resultSet, mv);
            return;
        }
        if (this.isOperForOrder(operType)) {
            this.getResultSetTextForOrder(resultSet, mv);
            return;
        }
    }

    private void getResultSetTextForOutBill(List<TicketStorageOutProduceOutBill> resultSet, ModelAndView mv) {
        List<PubFlag> billStatues = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.BILL_STATUES);
        List<PubFlag> icCardMains = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_MAIN);
        List<PubFlag> icCardSubs = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_SUB);
        List<PubFlag> storages = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.STORAGES);
        List<PubFlag> areaes = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.AREAS);
        List<PubFlag> outReasons = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IN_OUT_REASON_PRODUCE);



 /*

            if (attrName.equals(NEW_OLD_FLAGS)) {//新旧票标识
                options = pubFlagMapper.getCodesByType(PubFlagConstant.PUB_FLAG_TYPE_NEW_OLD_CARD_FLAG);
                mv.addObject(attrName, options);
                continue;
            }

         */
        for (TicketStorageOutProduceOutBill sd : resultSet) {
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
            if (areaes != null && !areaes.isEmpty()) {
                sd.setArea_id_name(DBUtil.getTextByCode(sd.getArea_id(), sd.getStorage_id(), areaes));

            }
            if (outReasons != null && !outReasons.isEmpty()) {
                sd.setReason_id_name(DBUtil.getTextByCode(sd.getReason_id(), outReasons));

            }

        }

    }

    private void getResultSetTextForOrder(List<TicketStorageOutProduceOrder> resultSet, ModelAndView mv) {

        List<PubFlag> afcMainTypes = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.AFC_CARD_MAIN);
        List<PubFlag> afcSubTypes = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.AFC_CARD_SUB);
        List<PubFlag> afcLines = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.AFC_LINES);
        List<PubFlag> afcStations = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.AFC_STATIONS);
        List<PubFlag> testFlags = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.TEST_FLAGS);
        List<PubFlag> signFlags = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.SIGN_CARDS);
        List<PubFlag> saleFlags = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.SALE_FLAGS);
        List<PubFlag> handleFlags = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.HANDLE_FLAGS);
        List<PubFlag> modes = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.MODES);

        /*

            if (attrName.equals(NEW_OLD_FLAGS)) {//新旧票标识
                options = pubFlagMapper.getCodesByType(PubFlagConstant.PUB_FLAG_TYPE_NEW_OLD_CARD_FLAG);
                mv.addObject(attrName, options);
                continue;
            }

         */
        for (TicketStorageOutProduceOrder sd : resultSet) {
            if (afcMainTypes != null && !afcMainTypes.isEmpty()) {
                sd.setCard_main_code_name(DBUtil.getTextByCode(sd.getCard_main_code(), afcMainTypes));
            }
            if (afcSubTypes != null && !afcSubTypes.isEmpty()) {
                sd.setCard_sub_code_name(DBUtil.getTextByCode(sd.getCard_sub_code(), sd.getCard_main_code(), afcSubTypes));

            }
            if (modes != null && !modes.isEmpty()) {
                sd.setModel_name(DBUtil.getTextByCode(sd.getModel(), modes));

            }
            if (afcLines != null && !afcLines.isEmpty()) {
                sd.setLine_code_name(DBUtil.getTextByCode(sd.getLine_code(), afcLines));
                sd.setExit_line_code_name(DBUtil.getTextByCode(sd.getExit_line_code(), afcLines));

            }
            if (afcStations != null && !afcStations.isEmpty()) {
                sd.setStation_code_name(DBUtil.getTextByCode(sd.getStation_code(), sd.getLine_code(), afcStations));
                sd.setExit_station_code_name(DBUtil.getTextByCode(sd.getExit_station_code(), sd.getExit_line_code(), afcStations));

            }
            if (handleFlags != null && !handleFlags.isEmpty()) {
                sd.setHdl_flag_name(DBUtil.getTextByCode(sd.getHdl_flag(), handleFlags));

            }
            if (testFlags != null && !testFlags.isEmpty()) {
                sd.setTest_flag_name(DBUtil.getTextByCode(sd.getTest_flag(), testFlags));

            }
            if (saleFlags != null && !saleFlags.isEmpty()) {
                sd.setSale_flag_name(DBUtil.getTextByCode(sd.getSale_flag(), saleFlags));

            }
            if (signFlags != null && !signFlags.isEmpty()) {
                sd.setCard_type_name(DBUtil.getTextByCode(sd.getCard_type(), signFlags));

            }

        }

    }

    private void getResultSetTextForPlan(List<TicketStorageOutProducePlan> resultSet, ModelAndView mv) {
        List<PubFlag> billStatues = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.BILL_STATUES);
        List<PubFlag> icLines = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_LINES);
        List<PubFlag> icStations = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_STATIONS);
        List<PubFlag> icCardMains = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_MAIN);
        List<PubFlag> icCardSubs = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_SUB);
        List<PubFlag> testFlags = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.TEST_FLAGS);
        List<PubFlag> signFlags = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.SIGN_CARDS);
        List<PubFlag> modes = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.MODES);
        List<PubFlag> storages = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.STORAGES);
        List<PubFlag> areaes = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.AREAS);
        List<PubFlag> orderTypes = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.ORDER_TYPES);
        List<PubFlag> wordTypes = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.ES_WORK_TYPES);
        List<PubFlag> outReasons = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IN_OUT_REASON_PRODUCE);

        /*

            if (attrName.equals(NEW_OLD_FLAGS)) {//新旧票标识
                options = pubFlagMapper.getCodesByType(PubFlagConstant.PUB_FLAG_TYPE_NEW_OLD_CARD_FLAG);
                mv.addObject(attrName, options);
                continue;
            }

         */
        for (TicketStorageOutProducePlan sd : resultSet) {
            if (billStatues != null && !billStatues.isEmpty()) {
                sd.setRecord_flag_name(DBUtil.getTextByCode(sd.getRecord_flag(), billStatues));
            }

            if (icLines != null && !icLines.isEmpty()) {
                sd.setLine_id_name(DBUtil.getTextByCode(sd.getLine_id(), icLines));
                sd.setExit_line_id_name(DBUtil.getTextByCode(sd.getExit_line_id(), icLines));

            }
            if (icStations != null && !icStations.isEmpty()) {
                sd.setStation_id_name(DBUtil.getTextByCode(sd.getStation_id(), sd.getLine_id(), icStations));
                sd.setExit_station_id_name(DBUtil.getTextByCode(sd.getExit_station_id(), sd.getExit_line_id(), icStations));

            }
            if (icCardMains != null && !icCardMains.isEmpty()) {
                sd.setIc_main_type_name(DBUtil.getTextByCode(sd.getIc_main_type(), icCardMains));

            }
            if (icCardSubs != null && !icCardSubs.isEmpty()) {
                sd.setIc_sub_type_name(DBUtil.getTextByCode(sd.getIc_sub_type(), sd.getIc_main_type(), icCardSubs));

            }
            if (testFlags != null && !testFlags.isEmpty()) {
                sd.setTest_flag_name(DBUtil.getTextByCode(sd.getTest_flag(), testFlags));

            }
            if (signFlags != null && !signFlags.isEmpty()) {
                sd.setCard_type_name(DBUtil.getTextByCode(sd.getCard_type(), signFlags));

            }
            if (modes != null && !modes.isEmpty()) {
                sd.setModel_name(DBUtil.getTextByCode(sd.getModel(), modes));

            }
            if (storages != null && !storages.isEmpty()) {
                sd.setStorage_id_name(DBUtil.getTextByCode(sd.getStorage_id(), storages));

            }
            if (areaes != null && !areaes.isEmpty()) {
                sd.setArea_id_name(DBUtil.getTextByCode(sd.getArea_id(), sd.getStorage_id(), areaes));

            }
            if (orderTypes != null && !orderTypes.isEmpty()) {
                sd.setOrder_type_name(DBUtil.getTextByCode(sd.getOrder_type(), orderTypes));

            }
            if (wordTypes != null && !wordTypes.isEmpty()) {
                sd.setEs_worktype_id_name(DBUtil.getTextByCode(sd.getEs_worktype_id(), wordTypes));

            }
            if (outReasons != null && !outReasons.isEmpty()) {
                sd.setReason_id_name(DBUtil.getTextByCode(sd.getReason_id(), outReasons));

            }

        }

    }

    public OperationResult queryForOpPlan(HttpServletRequest request, TicketStorageOutProduceMapper tsopMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageOutProducePlan queryCondition;
        List<TicketStorageOutProducePlan> resultSet;

        try {
            queryCondition = this.getQueryConditionForOpPlan(request, opResult);
            if (queryCondition == null) {
                return null;
            }
            resultSet = tsopMapper.queryPlan(queryCondition);
            opResult.setReturnResultSet(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    public OperationResult queryForOpOutBill(HttpServletRequest request, TicketStorageOutProduceMapper tsopMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageOutProduceOutBill queryCondition;
        List<TicketStorageOutProduceOutBill> resultSet;

        try {
            queryCondition = this.getQueryConditionForOpOutBill(request, opResult);
            if (queryCondition == null) {
                return null;
            }
            resultSet = tsopMapper.queryOutBill(queryCondition);
            opResult.setReturnResultSet(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    public OperationResult queryForOpPlanDetail(HttpServletRequest request, TicketStorageOutProduceMapper tsopMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageOutProducePlan queryCondition;
        List<TicketStorageOutProducePlan> resultSet;

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

    public OperationResult queryPlan(HttpServletRequest request, TicketStorageOutProduceMapper tsopMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageOutProducePlan queryCondition;
        List<TicketStorageOutProducePlan> resultSet;

        try {
            queryCondition = this.getQueryConditionPlan(request);
            resultSet = tsopMapper.queryPlan(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    public OperationResult queryPlanDetail(HttpServletRequest request, TicketStorageOutProduceMapper tsopMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageOutProducePlan queryCondition;
        List<TicketStorageOutProducePlan> resultSet;

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

    public OperationResult addPlanDetail(HttpServletRequest request, TicketStorageOutProduceMapper tsopMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageOutProducePlan vo = this.getReqAttributePlanDetail(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        String preMsg = "";
        try {
//            限制一个生产计划单只允许一条生产计划明细记录
            if (this.existPlanDetail(vo, tsopMapper, opLogMapper)) {
                rmsg.addMessage("添加失败，只允许添加一条生产计划明细记录！");
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

    public OperationResult modifyPlanDetail(HttpServletRequest request, TicketStorageOutProduceMapper tsopMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageOutProducePlan vo = this.getReqAttributePlanDetail(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        String preMsg = "";
        try {
            n = this.modifyPlanDetailByTrans(request, tsopMapper, vo, rmsg);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), opLogMapper);
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    public OperationResult addPlan(HttpServletRequest request, TicketStorageOutProduceMapper tsopMapper, BillMapper billMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageOutProducePlan vo = this.getReqAttributePlan(request);
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

    public OperationResult deletePlan(HttpServletRequest request, TicketStorageOutProduceMapper tsopMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<TicketStorageOutProducePlan> pos = this.getReqAttributeForPlanDelete(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            //明细表有没有记录
            if (this.isHasOutBillDetail(pos)) {
                rmsg.addMessage("删除失败,请先删除明细！");
                return rmsg;
            }
            
            n = this.deletePlanByTrans(request, tsopMapper, pos);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    public OperationResult auditPlan(HttpServletRequest request, TicketStorageOutProduceMapper tsopMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<TicketStorageOutProducePlan> pos = this.getReqAttributeForPlanAudit(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        HashMap<String, Object> hmParm;

        try {
//            for (TicketStorageOutProducePlan po : pos) {
                TicketStorageOutProducePlan po = pos.get(0);
                hmParm = this.auditPlanByTrans(request, tsopMapper, po);
                this.handleResult(hmParm);
                n += 1;

//            }

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_AUDIT, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_AUDIT, request, LogConstant.auditSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.auditSuccessMsg(n));

        return rmsg;
    }

    public OperationResult auditOutBill(HttpServletRequest request, TicketStorageOutProduceMapper tsopMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<TicketStorageOutProduceOutBill> pos = this.getReqAttributeForOutBillAudit(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        HashMap<String, Object> hmParm;

        try {
            for (TicketStorageOutProduceOutBill po : pos) {
                hmParm = this.auditOutBillByTrans(request, tsopMapper, po);
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

    public OperationResult deletePlanDetail(HttpServletRequest request, TicketStorageOutProduceMapper tsopMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<TicketStorageOutProducePlan> pos = this.getReqAttributeForPlanDetailDelete(request);
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

    public OperationResult queryOutBill(HttpServletRequest request, TicketStorageOutProduceMapper tsopMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageOutProduceOutBill queryCondition;
        List<TicketStorageOutProduceOutBill> resultSet;

        try {
            queryCondition = this.getQueryConditionOutBill(request);
            resultSet = tsopMapper.queryOutBill(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    public OperationResult queryOutBillDetail(HttpServletRequest request, TicketStorageOutProduceMapper tsopMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageOutProduceOutBill queryCondition;
        List<TicketStorageOutProduceOutBill> resultSet;

        try {
            queryCondition = this.getQueryConditionOutBill(request);
            resultSet = tsopMapper.queryOutBillDetail(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    public OperationResult queryOrder(HttpServletRequest request, TicketStorageOutProduceMapper tsopMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageOutProduceOrder queryCondition;
        List<TicketStorageOutProduceOrder> resultSet;

        try {
            queryCondition = this.getQueryConditionOrder(request);
            resultSet = tsopMapper.queryOrder(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }
    
    private boolean existPlanDetail(TicketStorageOutProducePlan vo, TicketStorageOutProduceMapper tsopMapper, OperationLogMapper opLogMapper) {
        boolean result = false;
        List<TicketStorageOutProduceOrder> planDetailList;
        int count = tsopMapper.getPlanDetailListCount(vo.getBill_no());
        if (count >0 ) {
            result = true;
        }
        return result;
    }
    
    @RequestMapping("/ticketStorageOutProduceManageExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        List results = this.getBufferElements(request);
        List results = this.getBufferElementsForCurClass(request,"com.goldsign.acc.app.storageout.entity.TicketStorageOutProducePlan");
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields,results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }
    
    private boolean isHasOutBillDetail(Vector<TicketStorageOutProducePlan> pos) {
        int detailCount = tsopMapper.getOutDetailCountForDeleteOutBill(pos);
        if (detailCount > 0) {
            return true;
        } else {
            return false;
        }
    }

}
