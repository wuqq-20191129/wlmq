/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storagein.controller;

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

import com.goldsign.acc.app.storagein.entity.TicketStorageInBorrowBill;
import com.goldsign.acc.app.storagein.entity.TicketStorageInBorrowBillDetail;
import com.goldsign.acc.app.storagein.entity.TicketStorageInBorrowInDetail;
import com.goldsign.acc.app.storagein.mapper.TicketStorageInBorrowMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.controller.StorageOutInBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;

/**
 * @desc:借票归还
 * @author:zhongzqi
 * @create date: 2017-9-1
 */
@Controller
public class TicketStorageInBorrowController extends TicketStorageInBorrowParentController {

    @Autowired
    private TicketStorageInBorrowMapper mapper;

    private static final String OPER_TYPE_RETURN_DETAIL = "returnDetail";
    private static final String OPER_TYPE_IN_BILL_DETAIL = "inBillDetail";
    private static final String COMMAND_QUERY_RETURN_DETAIL = "queryReturnDetail" ;


    @RequestMapping(value = "/ticketStorageInBorrowManage")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView("/jsp/storagein/ticketStorageInBorrow.jsp");
        String command = request.getParameter("command");
        String operType = request.getParameter("operType");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (this.isOperForPlan(operType)) {
                    if (command.equals(CommandConstant.COMMAND_QUERY)) {
                        opResult = this.queryPlan(request, this.mapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_ADD)) {
                        opResult = this.addPlan(request, this.mapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_DELETE)) {
                        opResult = this.deletePlan(request, this.mapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_AUDIT)) {
                        opResult = this.auditPlan(request, this.mapper, this.operationLogMapper);
                    }
                }
                if (this.isOperForPlanDetail(operType)) {
                    //计划单明细操作
                    mv = new ModelAndView("/jsp/storagein/ticketStorageInBorrowDetail.jsp");
                    if (command.equals(CommandConstant.COMMAND_QUERY_DETAIL)) {
                        opResult = this.queryPlanDetail(request, this.mapper, this.operationLogMapper);
                    }

                    if (command.equals(CommandConstant.COMMAND_MODIFY)) {
                        opResult = this.modifyPlanDetail(request, this.mapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_DELETE)) {
                        opResult = this.deletePlanDetail(request, this.mapper, this.operationLogMapper);
                    }
                }//计划单明细流水
                if (OPER_TYPE_RETURN_DETAIL.equals(operType)) {
                    mv = new ModelAndView("/jsp/storagein/ticketStorageInBorrowWaterNoDetail.jsp");
                    if (COMMAND_QUERY_RETURN_DETAIL.equals(command)) {
                        opResult = this.queryReturnDetail(request, this.mapper, this.operationLogMapper);
                    }
                }//入库单
                if (OPER_TYPE_IN_BILL_DETAIL.equals(operType)) {
                    mv = new ModelAndView("/jsp/storagein/ticketStorageInBorrowInDetail.jsp");
                    if (command.equals(CommandConstant.COMMAND_QUERY)) {
                        //出库单明细
                        opResult = this.queryInBillDetail(request, this.mapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_AUDIT)) {
                        opResult = this.auditInBill(request, this.mapper, this.operationLogMapper);
                    }
                }
            }

            if (this.isUpdateOp(command, null))//更新操作，增、删、改、审核,查询更新结果或原查询结果
            {
                this.queryUpdateResult(command, operType, request, mapper, operationLogMapper, opResult, mv);
            }
        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }

        String[] attrNames = this.getAttributeNames(operType);
        this.setPageOptions(attrNames, mv, request, response); //设置页面线路、车站、票卡、仓库等选项值、版本
        this.getResultSetText(opResult, mv, operType);
        this.baseHandlerForOutIn(request, response, mv); //出入库基本回传参数
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集//返回结果集
        return mv;
    }

    private String[] getAttributeNames(String operType) {
        if (this.isOperForPlan(operType)) {
            String[] attrNames = {BILL_STATUES, LEND_BILL_NOS,
                IC_CARD_MAIN, IC_CARD_SUB, IC_CARD_MAIN_SUB, STORAGES, BORROW_UNIT};
            return attrNames;
        }
        if (this.isOperForPlanDetail(operType) || OPER_TYPE_RETURN_DETAIL.equals(operType)) {
            String[] attrNames = {BILL_STATUES, STORAGES, AREAS, STORAGE_AREAS_BORROW_IN, CARD_MONEYS, MODES, IC_LINES, IC_STATIONS,
                IC_CARD_MAIN, IC_CARD_SUB, IC_CARD_MAIN_SUB, BORROW_UNIT};
            return attrNames;
        }

        if (OPER_TYPE_IN_BILL_DETAIL.equals(operType)) {
            String[] attrNames = {BILL_STATUES, STORAGES, AREAS, STORAGE_AREAS_BORROW_IN, CARD_MONEYS, MODES, IC_LINES, IC_STATIONS,
                IC_CARD_MAIN, IC_CARD_SUB, IC_CARD_MAIN_SUB, IN_OUT_REASON_FOR_IN};
            return attrNames;
        }
        return new String[0];
    }

    private void queryUpdateResult(String command, String operType, HttpServletRequest request, TicketStorageInBorrowMapper mapper,
            OperationLogMapper opLogMapper, OperationResult opResult, ModelAndView mv) throws Exception {
        if (!command.equals(CommandConstant.COMMAND_ADD)) {
            this.saveQueryControlDefaultValues(request, mv); //保存查询条件
        }
        if (this.isOperForPlan(operType)) {
            this.queryForOpPlan(request, mapper, opLogMapper, opResult);
        }
        if (this.isOperForPlanDetail(operType)) {
            this.queryForOPlanDetail(request, mapper, opLogMapper, opResult);
        }
        if (OPER_TYPE_IN_BILL_DETAIL.equals(operType)) {
            this.queryForOpInBillDetail(request, this.mapper, this.operationLogMapper, opResult);
        }

    }

    public OperationResult queryPlan(HttpServletRequest request, TicketStorageInBorrowMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        List<TicketStorageInBorrowBill> resultSet = new ArrayList<TicketStorageInBorrowBill>();
        try {
            resultSet = mapper.queryBorrowBill(this.getQueryConditionIn(request));
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    public OperationResult addPlan(HttpServletRequest request, TicketStorageInBorrowMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageInBorrowBill po = this.getReqAttributeForAdd(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        HashMap<String, Object> hmParm;
        String retMsg = "";
        try {
            hmParm = this.addInBillByTrans(request, mapper, po);
            retMsg = this.handleResultForAdd(hmParm, po);
            rmsg.setAddPrimaryKey(po.getBill_no());
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, retMsg, opLogMapper);
        rmsg.addMessage(retMsg);
        return rmsg;
    }

    public OperationResult deletePlan(HttpServletRequest request, TicketStorageInBorrowMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<TicketStorageInBorrowBill> pos = this.getReqAttributeForDelete(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            n = this.deleteInBillByTrans(request, mapper, pos);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    private OperationResult queryPlanDetail(HttpServletRequest request, TicketStorageInBorrowMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageInBorrowBillDetail queryCondition;
        List<TicketStorageInBorrowBillDetail> resultSet;
        try {
            queryCondition = this.getQueryConditionPlanDetail(request);
            resultSet = mapper.queryBorrowBillDetail(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
    }

    private void getResultSetText(OperationResult opResult, ModelAndView mv, String operType) {
        List<PubFlag> billStatues = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.BILL_STATUES);
        List<PubFlag> icLines = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_LINES);
        List<PubFlag> icStations = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_STATIONS);
        List<PubFlag> icCardMains = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_MAIN);
        List<PubFlag> icCardSubs = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_SUB);
        List<PubFlag> modes = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.MODES);
        List<PubFlag> storages = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.STORAGES);
        List<PubFlag> areaes = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.AREAS);
        List<PubFlag> units = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.BORROW_UNIT);
        List<PubFlag> reasons = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IN_OUT_REASON_FOR_IN);
        if (this.isOperForPlan(operType)) {
            List<TicketStorageInBorrowBill> resultSet = opResult.getReturnResultSet();
            for (TicketStorageInBorrowBill vo : resultSet) {
                if (billStatues != null && !billStatues.isEmpty()) {
                    vo.setRecord_flag_name(DBUtil.getTextByCode(vo.getRecord_flag(), billStatues));
                    vo.setRecord_in_flag_name(DBUtil.getTextByCode(vo.getRecord_in_flag(), billStatues));
                }
                if (units != null && !units.isEmpty()) {
                    vo.setUnit_name(DBUtil.getTextByCode(vo.getUnit_id(), units));
                }
            }
        }
        if (this.isOperForPlanDetail(operType) || OPER_TYPE_RETURN_DETAIL.equals(operType)) {
            List<TicketStorageInBorrowBillDetail> resultSet = opResult.getReturnResultSet();
            for (TicketStorageInBorrowBillDetail vo : resultSet) {
                if (billStatues != null && !billStatues.isEmpty()) {
                    vo.setRecord_flag_name(DBUtil.getTextByCode(vo.getRecord_flag(), billStatues));
                }
                if (icLines != null && !icLines.isEmpty()) {
                    vo.setLine_name(DBUtil.getTextByCode(vo.getLine_id(), icLines));
                    vo.setExit_line_name(DBUtil.getTextByCode(vo.getExit_line_id(), icLines));

                }
                if (icStations != null && !icStations.isEmpty()) {
                    vo.setStation_name(DBUtil.getTextByCode(vo.getStation_id(), vo.getLine_id(), icStations));
                    vo.setExit_station_name(DBUtil.getTextByCode(vo.getExit_station_id(), vo.getExit_line_id(), icStations));

                }
                if (icCardMains != null && !icCardMains.isEmpty()) {
                    vo.setIc_main_type_name(DBUtil.getTextByCode(vo.getIc_main_type(), icCardMains));
                }
                if (icCardSubs != null && !icCardSubs.isEmpty()) {
                    vo.setIc_sub_type_name(DBUtil.getTextByCode(vo.getIc_sub_type(), vo.getIc_main_type(), icCardSubs));
                }
                if (modes != null && !modes.isEmpty()) {
                    vo.setModel_name(DBUtil.getTextByCode(vo.getModel(), modes));
                }
                if (storages != null && !storages.isEmpty()) {
                    vo.setStorage_name(DBUtil.getTextByCode(vo.getStorage_id(), storages));
                }
                if (areaes != null && !areaes.isEmpty()) {
                    vo.setArea_name(DBUtil.getTextByCode(vo.getArea_id(), vo.getStorage_id(), areaes));
                }
            }
        }
        if (OPER_TYPE_IN_BILL_DETAIL.equals(operType)) {
            List<TicketStorageInBorrowInDetail> resultSet = opResult.getReturnResultSet();
            for (TicketStorageInBorrowInDetail vo : resultSet) {
                if (icLines != null && !icLines.isEmpty()) {
                    vo.setLine_name(DBUtil.getTextByCode(vo.getLine_id(), icLines));
                    vo.setExit_line_name(DBUtil.getTextByCode(vo.getExit_line_id(), icLines));

                }
                if (icStations != null && !icStations.isEmpty()) {
                    vo.setStation_name(DBUtil.getTextByCode(vo.getStation_id(), vo.getLine_id(), icStations));
                    vo.setExit_station_name(DBUtil.getTextByCode(vo.getExit_station_id(), vo.getExit_line_id(), icStations));

                }
                if (icCardMains != null && !icCardMains.isEmpty()) {
                    vo.setIc_main_type_name(DBUtil.getTextByCode(vo.getIc_main_type(), icCardMains));
                }
                if (icCardSubs != null && !icCardSubs.isEmpty()) {
                    vo.setIc_sub_type_name(DBUtil.getTextByCode(vo.getIc_sub_type(), vo.getIc_main_type(), icCardSubs));
                }
                if (modes != null && !modes.isEmpty()) {
                    vo.setModel_name(DBUtil.getTextByCode(vo.getModel(), modes));
                }
                if (storages != null && !storages.isEmpty()) {
                    vo.setStorage_name(DBUtil.getTextByCode(vo.getStorage_id(), storages));
                }
                if (areaes != null && !areaes.isEmpty()) {
                    vo.setArea_name(DBUtil.getTextByCode(vo.getArea_id(), vo.getStorage_id(), areaes));
                }
                if (reasons != null && !reasons.isEmpty()) {
                    vo.setReason_name(DBUtil.getTextByCode(vo.getReason_id(), reasons));
                }
            }
        }
    }

    private OperationResult modifyPlanDetail(HttpServletRequest request, TicketStorageInBorrowMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        LogUtil logUtil = new LogUtil();
        int n = 0;
        HashMap<String, Object> hmParm;
        String preMsg = "";
        try {
            TicketStorageInBorrowBillDetail vo = this.getReqAttributePlanDetail(request);
            n = this.modifyDetailByTrans(request, mapper, vo, rmsg);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), operationLogMapper);
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private OperationResult queryForOpPlan(HttpServletRequest request, TicketStorageInBorrowMapper mapper, OperationLogMapper operationLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageInBorrowBill queryCondition;
        List<TicketStorageInBorrowBill> resultSet;

        try {
            queryCondition = this.getQueryConditionForOpPlan(request, opResult);
            if (queryCondition == null) {
                return null;
            }
            resultSet = mapper.queryBorrowBill(queryCondition);
            opResult.setReturnResultSet(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return opResult;
    }

    private OperationResult queryForOPlanDetail(HttpServletRequest request, TicketStorageInBorrowMapper mapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageInBorrowBillDetail queryCondition;
        List<TicketStorageInBorrowBillDetail> resultSet;
        try {
            queryCondition = this.getQueryConditionForOpPlanDetail(request, opResult);
            if (queryCondition == null) {
                return null;
            }
            resultSet = mapper.queryBorrowBillDetail(queryCondition);
            opResult.setReturnResultSet(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return opResult;
    }

    private OperationResult queryReturnDetail(HttpServletRequest request, TicketStorageInBorrowMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        String waterNo = request.getParameter("queryConditionWaterNo");
        String lendWaterNo = request.getParameter("queryConditionLendWaterNo");
        int lendNum = 0;
        int returnTotal = 0;
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        List<TicketStorageInBorrowBillDetail> resultSet;
        try {
            String boxFlag = mapper.getBoxFlag(waterNo);
            //如果 box_flag == "1" 则从 ic_out_bill_detail_box 取流水号
            //如果 box_flag == "0" 则从 ic_out_bill_detail 取流水号
            if ("1".equals(boxFlag)) {
                lendNum = mapper.getLendNumFromDetailBox(lendWaterNo);
            } else {
                lendNum = mapper.getLendNumFromDetail(lendWaterNo);
            }
            returnTotal = mapper.getRetunTotal(lendWaterNo);
            resultSet = mapper.queryReturnDetail(lendWaterNo);
            request.setAttribute("lendNum", lendNum);
            request.setAttribute("returnTotal", returnTotal);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
    }

    private OperationResult deletePlanDetail(HttpServletRequest request, TicketStorageInBorrowMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();

        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            Vector<TicketStorageInBorrowBillDetail> pos = this.getReqAttributeForPlanDetailDelete(request);
            n = this.deletePlanDetailByTrans(request, mapper, pos);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), operationLogMapper);
        rmsg.addMessage(LogConstant.delSuccessMsg(n));
        return rmsg;
    }

    private OperationResult auditPlan(HttpServletRequest request, TicketStorageInBorrowMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        LogUtil logUtil = new LogUtil();
        int n = 0;
        HashMap<String, Object> hmParm;
        try {
            Vector<TicketStorageInBorrowBill> pos = this.getReqAttributeForDelete(request);
            for (TicketStorageInBorrowBill po : pos) {

                hmParm = this.auditInBorrowBillByTrans(request, mapper, po);
                this.handleResultForAudit(hmParm,request);
                String  formalBillNo = (String) hmParm.get("po_bill_no");
                request.setAttribute("q_billNo",formalBillNo);
                n += 1;
            }
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_AUDIT, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_AUDIT, request, LogConstant.auditSuccessMsg(n), operationLogMapper);

        rmsg.addMessage("归还单审核成功，请点击对应的入库单号，审核对应的入库单");

        return rmsg;
    }

    private OperationResult queryInBillDetail(HttpServletRequest request, TicketStorageInBorrowMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageInBorrowInDetail queryCondition;
        List<TicketStorageInBorrowInDetail> resultSet = null;
        try {
            queryCondition = this.getQueryConditionInBill(request);
            resultSet = mapper.getInBillDetail(queryCondition);//获取没有逻辑卡号段的出库明细
            List<TicketStorageInBorrowInDetail> histables = mapper.getHistory(queryCondition);
            if(histables.size()>0){
                if(resultSet==null){
                    resultSet = new ArrayList<TicketStorageInBorrowInDetail>();
                }
                for(TicketStorageInBorrowInDetail vo:histables){
                    List<TicketStorageInBorrowInDetail>  resutHis = mapper.getQueryHis(vo);
                    resultSet.addAll(resutHis);
                }
            }
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
    }

    private OperationResult auditInBill(HttpServletRequest request, TicketStorageInBorrowMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        LogUtil logUtil = new LogUtil();
        int n = 0;
        HashMap<String, Object> hmParm;
        try {
            hmParm = this.auditInBillByTrans(request, mapper);
                this.handleResultForAuditIn(hmParm,request);
            n += 1;
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_AUDIT, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_AUDIT, request, LogConstant.auditSuccessMsg(n), operationLogMapper);

        rmsg.addMessage(LogConstant.auditSuccessMsg(n));

        return rmsg;
    }

    private OperationResult queryForOpInBillDetail(HttpServletRequest request, TicketStorageInBorrowMapper mapper, OperationLogMapper operationLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageInBorrowInDetail queryCondition;
        List<TicketStorageInBorrowInDetail> resultSet = null;
        try {
            queryCondition = this.getQueryConditionInBillForOp(request);
            resultSet = mapper.getInBillDetail(queryCondition);//获取没有逻辑卡号段的出库明细
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return opResult;
    }
    @RequestMapping("/TicketStorageInBorrowExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElementsForCurClass(request,TicketStorageInBorrowBill.class.getName());
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields,results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }
}
