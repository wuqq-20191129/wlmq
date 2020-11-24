/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storageout.controller;

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

import com.goldsign.acc.app.storageout.entity.TicketStorageOutDestroyOutBill;
import com.goldsign.acc.app.storageout.entity.TicketStorageOutDestroyOutBillDetail;
import com.goldsign.acc.app.storageout.mapper.TicketStorageOutDestroyMapper;
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
 * @desc:销毁出库
 * @author:zhongzqi
 * @create date: 2017-8-9
 */
@Controller
public class TicketStorageOutDestroyController extends TicketStorageOutDestroyParentController {

    public static final String QUERY_OUT_BILL_DETAIL = "queryOutBillDetail";
    public static final String OUT_BILL_DETAIL = "outBillDetail";
    @Autowired
    private TicketStorageOutDestroyMapper mapper;

    @RequestMapping(value = "/ticketStorageOutDestroyManage")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/storageout/ticketStorageOutDestroytoOutBill.jsp");
        String command = request.getParameter("command");
        String operType = request.getParameter("operType");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (this.isOperForPlan(operType)) {
                    //出库单操作
                    if (command.equals(CommandConstant.COMMAND_QUERY)) {
                        //查询操作出库单
                        opResult = this.queryOutBill(request, this.mapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_ADD)) {
                        opResult = this.addOutBill(request, this.mapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_DELETE)) {
                        opResult = this.deleteOutBill(request, this.mapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_AUDIT)) {
                        //审核出库单
                        opResult = this.auditOutBill(request, this.mapper, this.operationLogMapper);
                    }
                }
                if (this.isOperForOutBillDetail(operType)) {
                    mv = new ModelAndView("/jsp/storageout/ticketStorageOutDestroytoOutBillDetail.jsp");
                    if (QUERY_OUT_BILL_DETAIL.equals(command)) {
                        //出库单明细
                        opResult = this.queryOutBillDetail(request, this.mapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_ADD)) {
                        opResult = this.addOutBillDetail(request, this.mapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_MODIFY)) {
                        opResult = this.modifyOutBillDetail(request, this.mapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_DELETE)) {
                        opResult = this.deleteOutBillDetail(request, this.mapper, this.operationLogMapper);
                    }
                }
                //更新操作，增、删、改、审核,查询更新结果或原查询结果
                if (this.isUpdateOp(command, operType))
                {
                    this.queryUpdateResult(command, operType, request, mapper, operationLogMapper, opResult, mv);
                }
            }
        } catch (Exception e) {
            opResult.addMessage(e.getMessage());
        }
        String[] attrNames = this.getAttributeNames(command, operType);
        //设置页面线路、车站、票卡、仓库等选项值、版本
        this.setPageOptions(attrNames, mv, request, response);
        this.getResultSetText(opResult.getReturnResultSet(), mv, operType);
        //出入库基本回传参数
        this.baseHandlerForOutIn(request, response, mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);
        this.SaveOperationResult(mv, opResult);
        return mv;
    }

    private String[] getAttributeNames(String command, String operType) {
        if (this.isOperForOutBillDetail(operType)) {
            //票卡主类型、票卡子类型、出库原、仓库、票区
            String[] attrNames = {IC_CARD_MAIN_LIMIT, IC_CARD_MAIN, IC_CARD_SUB, IC_CARD_MAIN_SUB,
                STORAGES, STORAGE_AREAS_DESTROY, AREAS, BILL_STATUES};
            return attrNames;
        } else {
            String[] attrNames = {STORAGES, IC_CARD_MAIN, IC_CARD_SUB, IC_CARD_MAIN_SUB,
                BILL_STATUES};
            return attrNames;
        }
    }

    private void queryUpdateResult(String command, String operType, HttpServletRequest request, TicketStorageOutDestroyMapper mapper,
            OperationLogMapper opLogMapper, OperationResult opResult, ModelAndView mv) throws Exception {
        if (!command.equals(CommandConstant.COMMAND_ADD)) {
            //保存查询条件
            this.saveQueryControlDefaultValues(request, mv);
        }
        //出库单查询
        if (this.isOperForPlan(operType)) {
            this.queryForOpOutBill(request, this.mapper, this.operationLogMapper, opResult);
        }
        //出库单明细
        if (OUT_BILL_DETAIL.equals(operType)) {
            this.queryForOpOutBillDetail(request, this.mapper, this.operationLogMapper, opResult);
        }
    }

    private void getResultSetText(List resultSet, ModelAndView mv, String operType) {
        if (operType == null || "".equals(operType)) {
            this.getResultSetTextForOutBill(resultSet, mv);
        }
        if (this.isOperForOutBillDetail(operType)) {
            this.getResultSetTextForOutBillDetail(resultSet, mv);
        }
    }

    private void getResultSetTextForOutBill(List<TicketStorageOutDestroyOutBill> resultSet, ModelAndView mv) {
        List<PubFlag> billStatues = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.BILL_STATUES);
        for (TicketStorageOutDestroyOutBill vo : resultSet) {
            if (billStatues != null && !billStatues.isEmpty()) {
                vo.setRecordFlagName(DBUtil.getTextByCode(vo.getRecordFlag(), billStatues));
            }
        }
    }

    private void getResultSetTextForOutBillDetail(List<TicketStorageOutDestroyOutBill> resultSet, ModelAndView mv) {
        List<PubFlag> billStatues = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.BILL_STATUES);
        List<PubFlag> icCardMains = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_MAIN);
        List<PubFlag> icCardSubs = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_SUB);
        List<PubFlag> storages = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.STORAGES);
        List<PubFlag> areaes = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.AREAS);
        for (TicketStorageOutDestroyOutBill vo : resultSet) {
            if (billStatues != null && !billStatues.isEmpty()) {
                vo.setRecordFlagName(DBUtil.getTextByCode(vo.getRecordFlag(), billStatues));
            }
            if (icCardMains != null && !icCardMains.isEmpty()) {
                vo.setIcMainName(DBUtil.getTextByCode(vo.getIcMainType(), icCardMains));

            }
            if (icCardSubs != null && !icCardSubs.isEmpty()) {
                vo.setIcSubName(DBUtil.getTextByCode(vo.getIcSubType(), vo.getIcMainType(), icCardSubs));

            }
            if (storages != null && !storages.isEmpty()) {
                vo.setStorageName(DBUtil.getTextByCode(vo.getStorageId(), storages));

            }
            if (areaes != null && !areaes.isEmpty()) {
                vo.setAreaName(DBUtil.getTextByCode(vo.getAreaId(), vo.getStorageId(), areaes));
            }
        }
    }

    public OperationResult deleteOutBill(HttpServletRequest request, TicketStorageOutDestroyMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<TicketStorageOutDestroyOutBill> pos = this.getReqAttributeForOutBillDelete(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            n = this.deleteOutBillByTrans(request, mapper, pos);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);
        rmsg.addMessage(LogConstant.delSuccessMsg(n));
        return rmsg;
    }

    public OperationResult auditOutBill(HttpServletRequest request, TicketStorageOutDestroyMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<TicketStorageOutDestroyOutBill> pos = this.getReqAttributeForOutBillAudit(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        HashMap<String, Object> hmParm;

        try {
            for (TicketStorageOutDestroyOutBill po : pos) {
                hmParm = this.auditOutBillByTrans(request, mapper, po);
                this.handleResult(hmParm,request);
                String  formalBillNo = (String) hmParm.get("piBillNoPlanFormal");
                request.setAttribute("q_billNo", formalBillNo);
                n += 1;

            }

        } catch (Exception e) {

            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_AUDIT, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_AUDIT, request, LogConstant.auditSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.auditSuccessMsg(n));

        return rmsg;
    }

    public OperationResult queryOutBill(HttpServletRequest request, TicketStorageOutDestroyMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageOutDestroyOutBill queryCondition;
        List<TicketStorageOutDestroyOutBill> resultSet;

        try {
            queryCondition = this.getQueryConditionOutBill(request);
            resultSet = mapper.queryOutBill(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    public OperationResult queryOutBillDetail(HttpServletRequest request, TicketStorageOutDestroyMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageOutDestroyOutBill queryCondition;
        List<TicketStorageOutDestroyOutBill> resultSet;
        try {
            queryCondition = this.getQueryConditionOutBillDetail(request);
            resultSet = mapper.queryOutBillDetail(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    private OperationResult addOutBill(HttpServletRequest request, TicketStorageOutDestroyMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageOutDestroyOutBill vo = this.getReqAttributeOutBill(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        String preMsg = "";
        try {
            n = this.addOutBillByTrans(request, mapper, this.billMapper, vo, rmsg);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), operationLogMapper);
        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    public OperationResult queryForOpOutBill(HttpServletRequest request, TicketStorageOutDestroyMapper mapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageOutDestroyOutBill queryCondition;
        List<TicketStorageOutDestroyOutBill> resultSet;

        try {
            queryCondition = this.getQueryConditionForOpOutBill(request, opResult);
            if (queryCondition == null) {
                return null;
            }
            resultSet = mapper.queryOutBill(queryCondition);
            opResult.setReturnResultSet(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    private OperationResult addOutBillDetail(HttpServletRequest request, TicketStorageOutDestroyMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        LogUtil logUtil = new LogUtil();
        int n = 0;
        String preMsg = "";
        try {
            TicketStorageOutDestroyOutBillDetail vo = this.getReqAttributeOutBillDetail(request);
            this.checkMulStorage(vo, mapper);
            n = this.addOutBilDetailByTrans(request, mapper, vo, rmsg);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), operationLogMapper);
        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    private OperationResult queryForOpOutBillDetail(HttpServletRequest request, TicketStorageOutDestroyMapper mapper, OperationLogMapper operationLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageOutDestroyOutBill queryCondition;
        List<TicketStorageOutDestroyOutBill> resultSet;

        try {
            queryCondition = this.getQueryConditionForOpOutBillDetail(request, opResult);

            resultSet = mapper.queryOutBillDetail(queryCondition);
            opResult.setReturnResultSet(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return opResult;
    }

    public OperationResult modifyOutBillDetail(HttpServletRequest request, TicketStorageOutDestroyMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();

        LogUtil logUtil = new LogUtil();
        int n = 0;
        String preMsg = "";
        try {
            TicketStorageOutDestroyOutBillDetail vo = this.getReqAttributeOutBillDetail(request);
            vo.setOperType("modify");
            if(vo.getWaterNo()==0){
                throw new Exception("修改失败，流水号空");
            }
            this.checkMulStorage(vo, mapper);
            n = this.modifyOutBillDetailByTrans(request, mapper, vo, rmsg);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), opLogMapper);
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private OperationResult deleteOutBillDetail(HttpServletRequest request, TicketStorageOutDestroyMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<TicketStorageOutDestroyOutBillDetail> pos = this.getReqAttributeForOutBillDetailDelete(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            n = this.deletePlanDetailByTrans(request, mapper, pos);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), operationLogMapper);
        rmsg.addMessage(LogConstant.delSuccessMsg(n));
        return rmsg;
    }

    @RequestMapping("/TicketStorageOutDestroyExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElementsForCurClass(request,TicketStorageOutDestroyOutBill.class.getName());
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields,results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }
}
