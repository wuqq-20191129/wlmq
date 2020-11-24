/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storageout.controller;

import com.goldsign.acc.app.storageout.entity.TicketStorageOutBorrowOutBillDetail1;
import com.goldsign.acc.app.storageout.entity.TicketStorageOutBorrowPlan;
import com.goldsign.acc.app.storageout.mapper.TicketStorageOutBorrowMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.controller.StorageOutInBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.BillMapper;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.util.PubUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @desc:借票出库
 * @author:zhongzqi
 * @create date: 2017-8-15
 */
@Controller
public class TicketStorageOutBorrowController extends TicketStorageOutBorrowParentController {

    @Autowired
    private TicketStorageOutBorrowMapper mapper;

    @RequestMapping(value = "/ticketStorageOutBorrowManage")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/storageout/ticketStorageOutBorrowPlan.jsp");
        String command = request.getParameter("command");
        String operType = request.getParameter("operType");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (this.isOperForPlan(operType)) {
                    //计划单操作
                    if (command.equals(CommandConstant.COMMAND_QUERY)) {
                        opResult = this.queryPlan(request, this.mapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_ADD)) {
                        opResult = this.addPlan(request, this.mapper, this.billMapper, this.operationLogMapper);
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
                    mv = new ModelAndView("/jsp/storageout/ticketStorageOutBorrowPlanDetail.jsp");
                    if (command.equals(CommandConstant.COMMAND_QUERY_DETAIL)) {
                        opResult = this.queryPlanDetail(request, this.mapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_ADD)) {
                        opResult = this.addPlanDetail(request, this.mapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_MODIFY)) {
                        opResult = this.modifyPlanDetail(request, this.mapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_DELETE)) {
                        opResult = this.deletePlanDetail(request, this.mapper, this.operationLogMapper);
                    }
                }
                if (this.isOperForOutBill(operType)) {
                    mv = new ModelAndView("/jsp/storageout/ticketStorageOutBorrowOutBillDetail.jsp");
                    if (command.equals(CommandConstant.COMMAND_QUERY)) {
                        //出库单明细
                        opResult = this.queryOutBillDetail(request, this.mapper, this.operationLogMapper);
                    }
                }
                //更新操作，增、删、改、审核,查询更新结果或原查询结果
                if (this.isUpdateOp(command, operType))
                {
                    this.queryUpdateResult(command, operType, request, mapper, operationLogMapper, opResult, mv);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
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
        //返回结果集
        this.SaveOperationResult(mv, opResult);
        return mv;
    }

    private OperationResult queryPlan(HttpServletRequest request, TicketStorageOutBorrowMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageOutBorrowPlan queryCondition;
        List<TicketStorageOutBorrowPlan> resultSet;

        try {
            queryCondition = this.getQueryConditionPlan(request);
            resultSet = mapper.queryPlan(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
    }

    private OperationResult addPlan(HttpServletRequest request, TicketStorageOutBorrowMapper mapper, BillMapper billMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        LogUtil logUtil = new LogUtil();
        int n = 0;
        String preMsg = "";
        try {
            TicketStorageOutBorrowPlan vo = this.getReqAttributePlan(request);
            n = this.addPlanByTrans(request, mapper, billMapper, vo, rmsg);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), operationLogMapper);
        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    private OperationResult deletePlan(HttpServletRequest request, TicketStorageOutBorrowMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            Vector<TicketStorageOutBorrowPlan> pos = this.getReqAttributeForPlanDelete(request);
            n = this.deletePlanByTrans(request, mapper, pos);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), operationLogMapper);
        rmsg.addMessage(LogConstant.delSuccessMsg(n));
        return rmsg;
    }

    private OperationResult auditPlan(HttpServletRequest request, TicketStorageOutBorrowMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        LogUtil logUtil = new LogUtil();
        int n = 0;
        HashMap<String, Object> hmParm;
        try {
            Vector<TicketStorageOutBorrowPlan> pos = this.getReqAttributeForPlanDelete(request);
            for (TicketStorageOutBorrowPlan po : pos) {
                hmParm = this.auditPlanByTrans(request, mapper, po);
                this.handleResult(hmParm,request);
                String  formalBillNo = (String) hmParm.get("piBillNoPlanFormal");
                request.setAttribute("q_billNo",formalBillNo);
                n += 1;
            }
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_AUDIT, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_AUDIT, request, LogConstant.auditSuccessMsg(n), operationLogMapper);
        rmsg.addMessage(LogConstant.auditSuccessMsg(n));
        return rmsg;
    }

    private String[] getAttributeNames(String command, String operType) {
        if (this.isOperForPlan(operType)) {
            //仓库/票卡主类型/票卡子类型/出库原因/计划单状态//借票单位
            String[] attrNames = {STORAGES, IC_CARD_MAIN, IC_CARD_SUB, IC_CARD_MAIN_SUB,
                BILL_STATUES, BORROW_UNIT};
            return attrNames;
        }
        if (this.isOperForPlanDetail(operType)) {
            //票卡主类型,票卡子类型,仓库,票区,出库卡面值,乘次票限制模式,进站线路,进站车站，仓库线路
            String[] attrNames = {IC_CARD_MAIN_LIMIT, IC_CARD_MAIN, IC_CARD_SUB, IC_CARD_MAIN_SUB,
                STORAGES, AREAS, STORAGE_AREAS, CARD_MONEYS, MODES, STORAGE_LINES_SERIAL, IC_LINES, IC_STATIONS,
                IC_LINE_STATIONS, BILL_STATUES};
            return attrNames;

        }
        if (this.isOperForOutBill(operType)) {
            //票卡主类型,票卡子类型,仓库,票区,出库卡面值,乘次票限制模式,进站线路,进站车站，仓库线路
            String[] attrNames = {IC_CARD_MAIN_LIMIT, IC_CARD_MAIN, IC_CARD_SUB, IC_CARD_MAIN_SUB,
                STORAGES, AREAS, STORAGE_AREAS, CARD_MONEYS, MODES, STORAGE_LINES_SERIAL, IC_LINES, IC_STATIONS,
                IC_LINE_STATIONS, BILL_STATUES};
            return attrNames;
        }

        if (this.isOperForOrder(operType)) {
            //票卡主类型，票卡子类型，记名卡标志，执行标志，乘次票限制模式，AFC线路，AFC车站
            //发售激活标志,测试标志
            String[] attrNames = {AFC_CARD_MAIN, AFC_CARD_SUB,
                SIGN_CARDS, HANDLE_FLAGS, MODES, AFC_LINES, AFC_STATIONS,
                SALE_FLAGS, TEST_FLAGS
            };
            return attrNames;
        }
        return new String[0];

    }

    private void getResultSetText(List returnResultSet, ModelAndView mv, String operType) {
        if (this.isOperForPlan(operType) || this.isOperForPlanDetail(operType)) {
            this.getResultSetTextForPlan(returnResultSet, mv);
            return;
        }
        if (this.isOperForOutBill(operType) || this.isOperForOutBillDetail(operType)) {
            this.getResultSetTextForOutBill(returnResultSet, mv);
            return;
        }
    }

    private void queryUpdateResult(String command, String operType, HttpServletRequest request, TicketStorageOutBorrowMapper mapper, OperationLogMapper operationLogMapper, OperationResult opResult, ModelAndView mv) throws Exception {
        if (!command.equals(CommandConstant.COMMAND_ADD)) {
            //保存查询条件
            this.saveQueryControlDefaultValues(request, mv);
        }
        //计划单查询
        if (this.isOperForPlan(operType)) {
            this.queryForOpPlan(request, this.mapper, this.operationLogMapper, opResult);
        }
        //计划单明细
        if (this.isOperForPlanDetail(operType)) {
            this.queryForOpPlanDetail(request, this.mapper, this.operationLogMapper, opResult);
        }
    }

    private void getResultSetTextForPlan(List<TicketStorageOutBorrowPlan> returnResultSet, ModelAndView mv) {
        List<PubFlag> billStatues = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.BILL_STATUES);
        List<PubFlag> icLines = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_LINES);
        List<PubFlag> icStations = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_STATIONS);
        List<PubFlag> icCardMains = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_MAIN);
        List<PubFlag> icCardSubs = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_SUB);
        List<PubFlag> modes = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.MODES);
        List<PubFlag> storages = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.STORAGES);
        List<PubFlag> areaes = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.AREAS);
        List<PubFlag> units = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.BORROW_UNIT);
        for (TicketStorageOutBorrowPlan vo : returnResultSet) {
            if (billStatues != null && !billStatues.isEmpty()) {
                vo.setRecordFlagName(DBUtil.getTextByCode(vo.getRecordFlag(), billStatues));
            }
            if (units != null && !units.isEmpty()) {
                vo.setUnitName(DBUtil.getTextByCode(vo.getUnitId(), units));
            }
            if (icLines != null && !icLines.isEmpty()) {
                vo.setLineName(DBUtil.getTextByCode(vo.getLineId(), icLines));
                vo.setExitLineName(DBUtil.getTextByCode(vo.getExitLineId(), icLines));
            }
            if (icStations != null && !icStations.isEmpty()) {
                vo.setStationName(DBUtil.getTextByCode(vo.getStationId(), vo.getLineId(), icStations));
                vo.setExitStationName(DBUtil.getTextByCode(vo.getExitStationId(), vo.getExitLineId(), icStations));
            }
            if (icCardMains != null && !icCardMains.isEmpty()) {
                vo.setIcMainTypeName(DBUtil.getTextByCode(vo.getIcMainType(), icCardMains));
            }
            if (icCardSubs != null && !icCardSubs.isEmpty()) {
                vo.setIcSubTypeName(DBUtil.getTextByCode(vo.getIcSubType(), vo.getIcMainType(), icCardSubs));
            }
            if (modes != null && !modes.isEmpty()) {
                vo.setModelName(DBUtil.getTextByCode(vo.getModel(), modes));
            }
            if (storages != null && !storages.isEmpty()) {
                vo.setStorageName(DBUtil.getTextByCode(vo.getStorageId(), storages));
            }
            if (areaes != null && !areaes.isEmpty()) {
                vo.setAreaName(DBUtil.getTextByCode(vo.getAreaId(), vo.getStorageId(), areaes));
            }

        }
    }

    private OperationResult queryForOpPlan(HttpServletRequest request, TicketStorageOutBorrowMapper mapper, OperationLogMapper operationLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageOutBorrowPlan queryCondition;
        List<TicketStorageOutBorrowPlan> resultSet;
        try {
            queryCondition = this.getQueryConditionForOpPlan(request, opResult);
            if (queryCondition == null) {
                return null;
            }
            resultSet = mapper.queryPlan(queryCondition);
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return opResult;
    }

    private OperationResult queryPlanDetail(HttpServletRequest request, TicketStorageOutBorrowMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageOutBorrowPlan queryCondition;
        List<TicketStorageOutBorrowPlan> resultSet;
        try {
            queryCondition = this.getQueryConditionPlanDetail(request);
            resultSet = mapper.queryPlanDetail(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
    }

    private OperationResult addPlanDetail(HttpServletRequest request, TicketStorageOutBorrowMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        LogUtil logUtil = new LogUtil();
        int n = 0;
        String preMsg = "";
        try {
            TicketStorageOutBorrowPlan vo = this.getReqAttributePlanDetail(request, CommandConstant.COMMAND_ADD);
            this.checkMulStorage(vo);
            this.checkContainData(vo);
            n = this.addPlanDetailByTrans(request, mapper, vo, rmsg);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), operationLogMapper);
        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    private OperationResult modifyPlanDetail(HttpServletRequest request, TicketStorageOutBorrowMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        LogUtil logUtil = new LogUtil();
        int n = 0;
        String preMsg = "";
        try {
            TicketStorageOutBorrowPlan vo = this.getReqAttributePlanDetail(request, CommandConstant.COMMAND_MODIFY);
            this.checkMulStorage(vo);
            this.checkContainData(vo);
            n = this.modifyPlanDetailByTrans(request, mapper, vo, rmsg);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), operationLogMapper);
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private OperationResult deletePlanDetail(HttpServletRequest request, TicketStorageOutBorrowMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            Vector<TicketStorageOutBorrowPlan> pos = this.getReqAttributeForPlanDetailDelete(request);
            n = this.deletePlanDetailByTrans(request, mapper, pos);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), operationLogMapper);
        rmsg.addMessage(LogConstant.delSuccessMsg(n));
        return rmsg;
    }

    private void checkMulStorage(TicketStorageOutBorrowPlan vo) throws Exception {
        String exsitStorage = mapper.getExistStorage(vo);
        if (exsitStorage != null) {
            if (!vo.getStorageId().equals(exsitStorage)) {
                throw new Exception("明细表不能存在多个仓库的明细.");
            }
        }
    }

    private OperationResult queryForOpPlanDetail(HttpServletRequest request, TicketStorageOutBorrowMapper mapper, OperationLogMapper operationLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageOutBorrowPlan queryCondition;
        List<TicketStorageOutBorrowPlan> resultSet;

        try {
            queryCondition = this.getQueryConditionForOpPlanDetail(request, opResult);
            if (queryCondition == null) {
                return null;
            }
            resultSet = mapper.queryPlanDetail(queryCondition);
            opResult.setReturnResultSet(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return opResult;
    }

    private OperationResult queryOutBillDetail(HttpServletRequest request, TicketStorageOutBorrowMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageOutBorrowOutBillDetail1 queryCondition;
        List<TicketStorageOutBorrowOutBillDetail1> resultSet = new ArrayList<TicketStorageOutBorrowOutBillDetail1>();
        try {
            queryCondition = this.getQueryConditionOutBill(request);
            resultSet = mapper.queryOutBillDetailByTotal(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
    }

    private void getResultSetTextForOutBill(List<TicketStorageOutBorrowOutBillDetail1> returnResultSet, ModelAndView mv) {
        List<PubFlag> icLines = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_LINES);
        List<PubFlag> icStations = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_STATIONS);
        List<PubFlag> icCardMains = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_MAIN);
        List<PubFlag> icCardSubs = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_SUB);
        List<PubFlag> modes = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.MODES);
        List<PubFlag> storages = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.STORAGES);
        List<PubFlag> areaes = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.AREAS);
        for (TicketStorageOutBorrowOutBillDetail1 vo : returnResultSet) {
            if (icLines != null && !icLines.isEmpty()) {
                vo.setLineName(DBUtil.getTextByCode(vo.getLineId(), icLines));
                vo.setExitLineName(DBUtil.getTextByCode(vo.getExitLineId(), icLines));
            }
            if (icStations != null && !icStations.isEmpty()) {
                vo.setStationName(DBUtil.getTextByCode(vo.getStationId(), vo.getLineId(), icStations));
                vo.setExitStationName(DBUtil.getTextByCode(vo.getExitStationId(), vo.getExitLineId(), icStations));
            }
            if (icCardMains != null && !icCardMains.isEmpty()) {
                vo.setIcMainTypeName(DBUtil.getTextByCode(vo.getIcMainType(), icCardMains));
            }
            if (icCardSubs != null && !icCardSubs.isEmpty()) {
                vo.setIcSubTypeName(DBUtil.getTextByCode(vo.getIcSubType(), vo.getIcMainType(), icCardSubs));
            }
            if (modes != null && !modes.isEmpty()) {
                vo.setModelName(DBUtil.getTextByCode(vo.getModel(), modes));
            }
            if (storages != null && !storages.isEmpty()) {
                vo.setStorageName(DBUtil.getTextByCode(vo.getStorageId(), storages));
            }
            if (areaes != null && !areaes.isEmpty()) {
                vo.setAreaName(DBUtil.getTextByCode(vo.getAreaId(), vo.getStorageId(), areaes));
            }
        }
    }

    private List<TicketStorageOutBorrowOutBillDetail1> getOutBillDetailWithoutLogical(List<TicketStorageOutBorrowOutBillDetail1> resultSetTotal) {
        List<TicketStorageOutBorrowOutBillDetail1> v = new ArrayList<TicketStorageOutBorrowOutBillDetail1>();
        TicketStorageOutBorrowOutBillDetail1 vo;
        for (int i = 0; i < resultSetTotal.size(); i++) {
            vo = (TicketStorageOutBorrowOutBillDetail1) resultSetTotal.get(i);
            if (!PubUtil.isNeedDetailPlace(vo.getAreaId(), vo.getIcMainType())) {
                v.add(vo);
            }
        }
        return v;
    }

    private void checkContainData(TicketStorageOutBorrowPlan vo) throws Exception {
        if (!"".equals(vo.getStartLogicalId()) || !"".equals(vo.getEndLogicalId())) {
            List<TicketStorageOutBorrowPlan> containData = mapper.getContainData(vo);
            if (containData.size() > 0) {
                StringBuffer containLogicalID = new StringBuffer();
                for (TicketStorageOutBorrowPlan po : containData) {
                    containLogicalID.append(po.getStartLogicalId()).append("-").append(po.getEndLogicalId()).append(" ");
                }
                throw new Exception("存在重复数据,插入数据：" + vo.getStartLogicalId() + "-" + vo.getEndLogicalId()+"库中存在重复数据："+containLogicalID.toString());
            }
        }
    }
    @RequestMapping("/TicketStorageOutBorrowExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElementsForCurClass(request,TicketStorageOutBorrowPlan.class.getName());
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));

    }

}
