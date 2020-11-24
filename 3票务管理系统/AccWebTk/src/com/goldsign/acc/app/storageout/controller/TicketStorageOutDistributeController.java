/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storageout.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.goldsign.acc.frame.constant.InOutConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.goldsign.acc.app.storageout.entity.TicketStorageOutDistributeBillDetail;
import com.goldsign.acc.app.storageout.entity.TicketStorageOutDistributeOutBillDetail;
import com.goldsign.acc.app.storageout.entity.TicketStorageOutDistributePlan;
import com.goldsign.acc.app.storageout.mapper.TicketStorageOutDistributeMapper;
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

/**
 * @desc:配票票出库
 * @author:zhongzqi
 * @create date: 2017-7-28
 */
@Controller
public class TicketStorageOutDistributeController extends TicketStorageOutDistributeParenttController {

    public static final String DISTRIBUTE_DETAIL = "distributeDetail";
    @Autowired
    private TicketStorageOutDistributeMapper mapper;

    @RequestMapping(value = "/ticketStorageOutDistributeManage")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/storageout/ticketStorageOutDistributePlan.jsp");
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
                    mv = new ModelAndView("/jsp/storageout/ticketStorageOutDistributePlanDetail.jsp");
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
                    //出库单操作
                    mv = new ModelAndView("/jsp/storageout/ticketStorageOutDistributeOutDetail.jsp");
                    if (command.equals(CommandConstant.COMMAND_QUERY)) {
                        //出库单明细
                        opResult = this.queryOutBillDetail(request, this.mapper, this.operationLogMapper);
                    }
                }
                if (DISTRIBUTE_DETAIL.equals(operType)) {
                    //出库单操作
                    mv = new ModelAndView("/jsp/storageout/ticketStorageOutDistributeDetail.jsp");
                    if (command.equals(CommandConstant.COMMAND_QUERY)) {
                        //出库单明细
                        opResult = this.queryDistributeBillDetail(request, this.mapper, this.operationLogMapper);
                    }
                }
                //更新操作，增、删、改、审核,查询更新结果或原查询结果
                if (this.isUpdateOp(command, operType))
                {
                    this.queryUpdateResult(command, operType, request, mapper, operationLogMapper, opResult, mv);
                }

            } else {
                if (this.isOperForLineContent(operType)) {
                    mv = new ModelAndView("/jsp/storageout/ticketStorageOutDistributePlanDetailLineContent.jsp");
                    String icMainType = request.getParameter("d_icMainType");
                    String icSubType = request.getParameter("d_icSubType");
                    String cardMoney = request.getParameter("d_cardMoney");
                    String validDate = request.getParameter("d_validDate");
                    String lineId = request.getParameter("d_lineId");
                    String stationId = request.getParameter("d_stationId");
                    String storageId = request.getParameter("d_storageId");
                    List<TicketStorageOutDistributePlan> list = this.getListForDistributeLine(icMainType, icSubType, cardMoney, validDate, lineId, stationId, storageId);
                    List<PubFlag> lines = this.getLinesByStorageId(storageId);
                    mv.addObject("result", list);
                    mv.addObject("icLines", lines);
                    return mv;
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
        this.SaveOperationResult(mv, opResult);
        return mv;
    }

    private String[] getAttributeNames(String command, String operType) {
        if (this.isOperForPlan(operType)) {
            //仓库/票卡主类型/票卡子类型/出库原因/计划单状态
            String[] attrNames = {STORAGES, IC_CARD_MAIN, IC_CARD_SUB, IC_CARD_MAIN_SUB,
                IN_OUT_REASON_DISTRIBUTE, BILL_STATUES};
            return attrNames;
        }
        if (this.isOperForPlanDetail(operType)) {
            //票卡主类型,票卡子类型,仓库,票区,出库原因,限站使用标志，出库卡面值,乘次票限制模式,进站线路,进站车站，仓库线路
            String[] attrNames = {IC_CARD_MAIN, IC_CARD_MAIN_SERIAL, IC_CARD_SUB, IC_CARD_MAIN_SUB,
                STORAGES, AREAS, STORAGE_AREAS, IN_OUT_REASON_DISTRIBUTE, LIMIT_STATION_FLAG, LIMIT_STATION_FLAG_SERIAL,
                CARD_MONEYS, MODES, IC_LINES, IC_STATIONS, IC_LINE_STATIONS, STORAGE_LINES_SERIAL, BILL_STATUES};
            return attrNames;
        }
        if (this.isOperForOutBill(operType)) {
            //票卡主类型,票卡子类型,仓库,票区,出库卡面值,乘次票限制模式,进站线路,进站车站，仓库线路
            String[] attrNames = {IC_CARD_MAIN_LIMIT, IC_CARD_MAIN, IC_CARD_SUB, IC_CARD_MAIN_SUB,
                STORAGES, AREAS, STORAGE_AREAS, CARD_MONEYS, MODES, STORAGE_LINES_SERIAL, IC_LINES, IC_STATIONS,
                IN_OUT_REASON_DISTRIBUTE, IC_LINE_STATIONS, BILL_STATUES};
            return attrNames;
        }
        if (DISTRIBUTE_DETAIL.equals(operType)) {
            //票卡主类型,票卡子类型,仓库,票区,出库卡面值,乘次票限制模式,进站线路,进站车站，仓库线路
            String[] attrNames = {IC_CARD_MAIN_LIMIT, IC_CARD_MAIN, IC_CARD_SUB, IC_CARD_MAIN_SUB,
                CARD_MONEYS, MODES, IC_LINES, IC_STATIONS,
                IN_OUT_REASON_DISTRIBUTE, IC_LINE_STATIONS, BILL_STATUES};
            return attrNames;
        }
        return new String[0];

    }

    private void queryUpdateResult(String command, String operType, HttpServletRequest request, TicketStorageOutDistributeMapper mapper,
            OperationLogMapper opLogMapper, OperationResult opResult, ModelAndView mv) throws Exception {
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

    private void getResultSetText(List resultSet, ModelAndView mv, String operType) {
        if (this.isOperForPlan(operType) || this.isOperForPlanDetail(operType)) {
            this.getResultSetTextForPlan(resultSet, mv);
            return;
        }
        if (this.isOperForOutBill(operType) || this.isOperForOutBillDetail(operType)) {
            this.getResultSetTextForOutBill(resultSet, mv);
            return;
        }
        if (DISTRIBUTE_DETAIL.equals(operType)) {
            this.getResultSetTextForDistributeBill(resultSet, mv);
            return;
        }
    }

    private void getResultSetTextForPlan(List<TicketStorageOutDistributePlan> resultSet, ModelAndView mv) {
        List<PubFlag> billStatues = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.BILL_STATUES);
        List<PubFlag> icLines = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_LINES);
        List<PubFlag> icStations = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_STATIONS);
        List<PubFlag> icCardMains = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_MAIN);
        List<PubFlag> icCardSubs = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_SUB);
        List<PubFlag> modes = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.MODES);
        List<PubFlag> storages = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.STORAGES);
        List<PubFlag> areaes = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.AREAS);
        List<PubFlag> outReasons = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IN_OUT_REASON_DISTRIBUTE);
        List<PubFlag> restrictFlags = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.LIMIT_STATION_FLAG);
        for (TicketStorageOutDistributePlan vo : resultSet) {
            if (billStatues != null && !billStatues.isEmpty()) {
                vo.setRecordFlagName(DBUtil.getTextByCode(vo.getRecordFlag(), billStatues));
            }

            if (icLines != null && !icLines.isEmpty()) {
                vo.setLineName(DBUtil.getTextByCode(vo.getLineId(), icLines));
                vo.setExitLineName(DBUtil.getTextByCode(vo.getExitLineId(), icLines));
                vo.setDistributeLineName(DBUtil.getTextByCode(vo.getDistributeLineId(), icLines));

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
            if (outReasons != null && !outReasons.isEmpty()) {
                vo.setReasonName(DBUtil.getTextByCode(vo.getReasonId(), outReasons));
            }
            if (restrictFlags != null && !restrictFlags.isEmpty()) {
                vo.setRestrictName(DBUtil.getTextByCode(vo.getRestrictFlag(), restrictFlags));
            }
        }
    }

    public OperationResult queryForOpPlan(HttpServletRequest request, TicketStorageOutDistributeMapper mapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageOutDistributePlan queryCondition;
        List<TicketStorageOutDistributePlan> resultSet;
        try {
            queryCondition = this.getQueryConditionForOpPlan(request, opResult);
            if (queryCondition == null) {
                return null;
            }
            resultSet = mapper.queryPlan(queryCondition);
            opResult.setReturnResultSet(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;
    }
//

    public OperationResult queryForOpPlanDetail(HttpServletRequest request, TicketStorageOutDistributeMapper mapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageOutDistributePlan queryCondition;
        List<TicketStorageOutDistributePlan> resultSet;
        try {
            queryCondition = this.getQueryConditionForOpPlanDetail(request, opResult);
            if (queryCondition == null) {
                return null;
            }
            resultSet = mapper.queryPlanDetail(queryCondition);
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    public OperationResult queryPlan(HttpServletRequest request, TicketStorageOutDistributeMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageOutDistributePlan queryCondition;
        List<TicketStorageOutDistributePlan> resultSet;
        try {
            queryCondition = this.getQueryConditionPlan(request);
            resultSet = mapper.queryPlan(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    public OperationResult queryPlanDetail(HttpServletRequest request, TicketStorageOutDistributeMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageOutDistributePlan queryCondition;
        List<TicketStorageOutDistributePlan> resultSet;
        try {
            queryCondition = this.getQueryConditionPlanDetail(request);
            resultSet = mapper.queryPlanDetail(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    public OperationResult addPlanDetail(HttpServletRequest request, TicketStorageOutDistributeMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        LogUtil logUtil = new LogUtil();
        int n = 0;
        int failNum = 0;
        String preMsg = "";
        String logicSec = "";
        try {
            TicketStorageOutDistributePlan vo = this.getReqAttributePlanDetail(request, CommandConstant.COMMAND_ADD);
            this.chekcSameOutReason(vo);
            this.checkMulStorage(vo);
            this.checkRepeatData(vo);
            if (StorageOutInBaseController.isPlanForLine(vo.getReasonId())) {
                n = this.addPlanDetailForLine(request, mapper, vo, rmsg);
            } else if (!this.isOutByBox(vo)) {
                n = this.addPlanDetailByTrans(request, mapper, vo, rmsg);
            } else {
                Vector boxes = this.divideBoxes(vo);
                 logicSec = " 盒号段：" + vo.getBoxId() + "-" + vo.getEndBoxId() + "共" + boxes.size() + "盒，";
                for (int i = 0; i < boxes.size(); i++) {
                    String boxId = (String) boxes.get(i);
                    vo.setBoxId(boxId);
                    if (this.isExistBox(vo)) {
                        int num = mapper.getBoxNum(vo.getBoxId());
                        vo.setDistributeQuantity(num);
                        n += this.addPlanDetailByTrans(request, mapper, vo, rmsg);
                    }else{
                        failNum++;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), opLogMapper);
        if(failNum==0){
          rmsg.addMessage(LogConstant.addSuccessMsg(n));
        }
        else{

          rmsg.addMessage(logicSec+LogConstant.addSuccessMsg(n)+"其他盒号可能不存在或存在但其他明细信息填写与盒号不符");
        }
        return rmsg;
    }

    public OperationResult modifyPlanDetail(HttpServletRequest request, TicketStorageOutDistributeMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        LogUtil logUtil = new LogUtil();
        int n = 0;
        String preMsg = "";
        try {
            TicketStorageOutDistributePlan vo = this.getReqAttributePlanDetail(request, CommandConstant.COMMAND_MODIFY);
            vo.setOperType("modify");
            this.chekcSameOutReason(vo);
            this.checkMulStorage(vo);
            this.checkRepeatData(vo);
            n = this.modifyPlanDetailByTrans(request, mapper, vo, rmsg);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), opLogMapper);
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));
        return rmsg;
    }

    public OperationResult addPlan(HttpServletRequest request, TicketStorageOutDistributeMapper mapper, BillMapper billMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        LogUtil logUtil = new LogUtil();
        int n = 0;
        String preMsg = "";
        try {
            TicketStorageOutDistributePlan vo = this.getReqAttributePlan(request);
            n = this.addPlanByTrans(request, mapper, billMapper, vo, rmsg);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), opLogMapper);
        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    public OperationResult deletePlan(HttpServletRequest request, TicketStorageOutDistributeMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            Vector<TicketStorageOutDistributePlan> pos = this.getReqAttributeForPlanDelete(request);
            n = this.deletePlanByTrans(request, mapper, pos);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    public OperationResult auditPlan(HttpServletRequest request, TicketStorageOutDistributeMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        LogUtil logUtil = new LogUtil();
        int n = 0;
        HashMap<String, Object> hmParm;
        try {
            Vector<TicketStorageOutDistributePlan> pos = this.getReqAttributeForPlanAudit(request);
            for (TicketStorageOutDistributePlan po : pos) {
                hmParm = this.auditPlanByTrans(request, mapper, po);
                this.handleResult(hmParm,request);
                String  formalBillNo = (String) hmParm.get("piBillNoPlanFormal");
                request.setAttribute("q_billNo",formalBillNo);
                n += 1;

            }

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_AUDIT, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_AUDIT, request, LogConstant.auditSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.auditSuccessMsg(n));

        return rmsg;
    }

    public OperationResult deletePlanDetail(HttpServletRequest request, TicketStorageOutDistributeMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            Vector<TicketStorageOutDistributePlan> pos = this.getReqAttributeForPlanDetailDelete(request);
            n = this.deletePlanDetailByTrans(request, mapper, pos);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);
        rmsg.addMessage(LogConstant.delSuccessMsg(n));
        return rmsg;
    }

    public OperationResult queryOutBillDetail(HttpServletRequest request, TicketStorageOutDistributeMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageOutDistributeOutBillDetail queryCondition;
        List<TicketStorageOutDistributeOutBillDetail> resultSet = new ArrayList<TicketStorageOutDistributeOutBillDetail>();
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

    public List<TicketStorageOutDistributePlan> getListForDistributeLine(String icMainType, String icSubType, String cardMoney, String validDate, String lineId, String stationId, String storageId) {
        List<TicketStorageOutDistributePlan> rs = new ArrayList<TicketStorageOutDistributePlan>();
        List<PubFlag> lines = pubFlagMapper.getLinesByStorageId(storageId);
        for (int i = 0; i < lines.size(); i++) {
            PubFlag pfVo = lines.get(i);
            TicketStorageOutDistributePlan vo = new TicketStorageOutDistributePlan();
            vo.setWaterNo(i + 1);
            vo.setDistributeLineId(pfVo.getCode());
            vo.setDistributeLineName(pfVo.getCode_text());
            vo.setDistributeQuantity(0);
            rs.add(vo);
        }
        return rs;
    }

    public List getLinesByStorageId(String storageId) {
        return pubFlagMapper.getLinesByStorageId(storageId);
    }

    private boolean isOperForLineContent(String operType) {
        if (operType == null || operType.length() == 0) {
            return false;
        }
        if (operType.equals(OPER_TYPE_OUT_LINE_CONTENT)) {
            return true;
        }
        return false;
    }

    private void checkMulStorage(TicketStorageOutDistributePlan vo) throws Exception {
        List<TicketStorageOutDistributePlan> exsitStorage = mapper.getExistStorage(vo);
        if (exsitStorage != null && exsitStorage.size() > 0) {
            TicketStorageOutDistributePlan tmp = exsitStorage.get(0);
            if (!vo.getStorageId().equals(tmp.getStorageId())) {
                throw new Exception("明细表不能存在多个仓库的明细.");
            }
        }
    }

    public void chekcSameOutReason(TicketStorageOutDistributePlan vo) throws Exception {
        List<TicketStorageOutDistributePlan> exsitReson = mapper.getExistReason(vo);
        if (exsitReson != null && exsitReson.size() > 0) {
            TicketStorageOutDistributePlan tmp = exsitReson.get(0);
            if (!tmp.getReasonId().equals(vo.getReasonId())) {
                throw new Exception("明细表不能存在多种出库原因的明细.");
            }
        }
    }

    private int addPlanDetailForLine(HttpServletRequest request, TicketStorageOutDistributeMapper mapper, TicketStorageOutDistributePlan vo, OperationResult opResult) throws Exception {
        int n = 0;
        Vector lineDistribute = this.getLineDistribute(vo.getContent());
        for (int i = 0; i < lineDistribute.size(); i++) {
            TicketStorageOutDistributePlan tmp = (TicketStorageOutDistributePlan) lineDistribute.get(i);
            vo.setDistributeLineId(tmp.getDistributeLineId());
            vo.setDistributeQuantity(tmp.getDistributeQuantity());
            n += this.addPlanDetailForLineByTrans(request, mapper, vo, opResult);
        }
        return n;

    }

    private Vector getLineDistribute(String content) {
        Vector v = new Vector();
        if (content == null || content.length() == 0) {
            return v;
        }
        StringTokenizer st = new StringTokenizer(content, ";");
        String token = "";
        String lineId = "";
        String distributeQuantity = "";
        int index = -1;
        String tmp;
        TicketStorageOutDistributePlan vo;
        while (st.hasMoreTokens()) {
            token = st.nextToken();
            index = token.indexOf("#");
            if (index == -1) {
                continue;
            }
            tmp = token.substring(0, index);
            distributeQuantity = token.substring(index + 1);
            lineId = tmp.substring(0, index);
            vo = new TicketStorageOutDistributePlan();
            vo.setDistributeLineId(lineId);
            vo.setDistributeQuantity(Integer.parseInt(distributeQuantity));
            v.add(vo);
        }
        return v;
    }

    private boolean isOutByBox(TicketStorageOutDistributePlan vo) {
        String boxId = vo.getBoxId();
        String endBoxId = vo.getEndBoxId();
        if (boxId == null || boxId.length() == 0 || endBoxId == null || endBoxId.length() == 0) {
            return false;
        }
//        if (endBoxId.equals(boxId)) {
//            return false;
//        }
        return true;
    }

//    private int addDetailEach(HttpServletRequest request, TicketStorageOutDistributeMapper mapper, TicketStorageOutDistributePlan vo, OperationResult rmsg) throws Exception {
//        int n = 0;
//
//        n = this.addPlanDetailByTrans(request, mapper, vo, rmsg);
//        return n;
//    }

    private Vector divideBoxes(TicketStorageOutDistributePlan vo) {
        BigDecimal iBoxId = new BigDecimal(vo.getBoxId());
        BigDecimal eBoxId = new BigDecimal(vo.getEndBoxId());
        String boxId;
        Vector v = new Vector();
        while (iBoxId.compareTo(eBoxId) <= 0) {
            boxId = formatNumber(iBoxId.toString(), 14);
            v.add(boxId);
            iBoxId = iBoxId.add(new BigDecimal("1"));
        }
        return v;
    }

    public static String formatNumber(String number, int len) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < len - number.length(); i++) {
            buf.append("0");
        }
        return buf.append(number).toString();
    }

    private List<TicketStorageOutDistributeOutBillDetail> getOutBillDetailWithoutLogical(List<TicketStorageOutDistributeOutBillDetail> resultSetTotal) {
        List<TicketStorageOutDistributeOutBillDetail> v = new ArrayList<TicketStorageOutDistributeOutBillDetail>();
        TicketStorageOutDistributeOutBillDetail vo;
        for (int i = 0; i < resultSetTotal.size(); i++) {
            vo = (TicketStorageOutDistributeOutBillDetail) resultSetTotal.get(i);
            if (!PubUtil.isNeedDetailPlace(vo.getAreaId(), vo.getIcMainType())) {
                v.add(vo);
            }
        }
        return v;
    }

    private void getResultSetTextForOutBill(List<TicketStorageOutDistributeOutBillDetail> resultSet, ModelAndView mv) {
        List<PubFlag> icLines = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_LINES);
        List<PubFlag> icStations = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_STATIONS);
        List<PubFlag> icCardMains = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_MAIN);
        List<PubFlag> icCardSubs = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_SUB);
        List<PubFlag> modes = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.MODES);
        List<PubFlag> storages = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.STORAGES);
        List<PubFlag> areaes = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.AREAS);
        List<PubFlag> reasons = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IN_OUT_REASON_DISTRIBUTE);
        for (TicketStorageOutDistributeOutBillDetail vo : resultSet) {
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
            if (reasons != null && !reasons.isEmpty()) {
                vo.setReasonName(DBUtil.getTextByCode(vo.getReasonId(), reasons));
            }
        }
    }

    private OperationResult queryDistributeBillDetail(HttpServletRequest request, TicketStorageOutDistributeMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageOutDistributeBillDetail queryCondition;
        List<TicketStorageOutDistributeBillDetail> resultSet;
        try {
            queryCondition = this.getQueryConditionDistributeBill(request);
            resultSet = mapper.queryDistributeBill(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
    }

    private void getResultSetTextForDistributeBill(List<TicketStorageOutDistributeBillDetail> resultSet, ModelAndView mv) {
        List<PubFlag> icLines = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_LINES);
        List<PubFlag> icStations = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_STATIONS);
        List<PubFlag> icCardMains = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_MAIN);
        List<PubFlag> icCardSubs = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_SUB);
        List<PubFlag> modes = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.MODES);
        for (TicketStorageOutDistributeBillDetail vo : resultSet) {
            if (icLines != null && !icLines.isEmpty()) {
                vo.setLineName(DBUtil.getTextByCode(vo.getLineId(), icLines));
                vo.setExitLineName(DBUtil.getTextByCode(vo.getExitLineId(), icLines));
                vo.setDistributeLineName(DBUtil.getTextByCode(vo.getDistributeLineId(), icLines));
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
        }
    }

    private void checkRepeatData(TicketStorageOutDistributePlan vo) throws Exception {
        if (!"".equals(vo.getStartLogicalId()) || !"".equals(vo.getEndLogicalId())) {
            List<TicketStorageOutDistributePlan> containData = mapper.getContainDataByLogicalId(vo);
            if (containData.size() > 0) {
                StringBuffer containLogicalID = new StringBuffer();
                for (TicketStorageOutDistributePlan po : containData) {
                    containLogicalID.append(po.getStartLogicalId()).append("-").append(po.getEndLogicalId()).append("  ");
                }
                throw new Exception("存在重复数据,插入数据：" + vo.getStartLogicalId() + "-" + vo.getEndLogicalId() + "库中存在重复数据：" + containLogicalID.toString());
            }
        }
        if (!"".equals(vo.getBoxId())) {
            List<TicketStorageOutDistributePlan> containData = mapper.getContainDataByBox(vo);
            if (containData.size() > 0) {
//                String containString = "";
                StringBuffer containBoxID = new StringBuffer();
                for (TicketStorageOutDistributePlan po : containData) {
                    containBoxID.append(po.getBoxId()).append("  ");
//                    containString = containString + po.getBoxId() + "  ";
                }
                if (!"".equals(vo.getEndBoxId())) {
                    throw new Exception("存在重复数据,插入数据：" + vo.getBoxId() + "-" + vo.getEndBoxId() + "库中存在重复数据：" + containBoxID.toString());
                } else {
                    throw new Exception("存在重复数据,插入数据：" + vo.getBoxId() + "库中存在重复数据：" + containBoxID.toString());
                }
            }
        }
    }

    private boolean isBoxDetail(TicketStorageOutDistributePlan vo) {
        boolean result = false;
        if (!"".equals(vo.getBoxId())) {
            if (vo.getBoxId().length() == InOutConstant.LEN_BOX_EFFECTIVE) {
                result = true;
            }
        }
        return result;
    }

    private boolean isExistBox(TicketStorageOutDistributePlan vo) {
        boolean result = false;
        int recordNum = mapper.getBoxRecordNum(vo);
        if (recordNum == 1) {
            result = true;
        }
        return result;
    }
    @RequestMapping("/TicketStorageOutDistributeExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElementsForCurClass(request,TicketStorageOutDistributePlan.class.getName());
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields,results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }
}

