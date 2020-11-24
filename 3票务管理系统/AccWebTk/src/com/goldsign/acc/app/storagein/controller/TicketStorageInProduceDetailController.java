/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storagein.controller;

import com.goldsign.acc.app.storagein.entity.TicketStorageInBill;
import com.goldsign.acc.app.storagein.entity.TicketStorageInDetailBase;
import com.goldsign.acc.app.storagein.entity.TicketStorageInOutProduceDiff;
import com.goldsign.acc.app.storagein.entity.TicketStorageInProduceDetail;
import com.goldsign.acc.app.storagein.mapper.TicketStorageInProduceDetailMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.controller.StorageOutInBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.util.PageControlUtil;
import com.goldsign.acc.frame.util.PubUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author mqf
 */
@Controller
public class TicketStorageInProduceDetailController extends TicketStorageInDetailParentController {

    @Autowired
    private TicketStorageInProduceDetailMapper tsInProduceDetailMapper;

    @RequestMapping(value = "/ticketStorageInProduceDetail")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

//        ModelAndView mv = new ModelAndView("/jsp/storagein/ticketStorageInProduceDetail.jsp");
        String command = request.getParameter("command");
//        String inType = request.getParameter("inType"); //入库类型:生产入库 "SR";生产入出库差额:"DF"
        String inType = this.getInType(request);
        ModelAndView mv = this.getModelAndView(inType);

//        billNo = (String) request.getParameter("bill_no");
//recordFlag = (String) request.getParameter("record_flag");
//vo.setInTypeText("生产入库");
//            saveResult(request, "topic_useflag", FrameCharUtil.GbkToIso("生产类别"));
//        saveResult(request, "bill_no", billNo);
//saveResult(request, "record_flag", recordFlag);
//
//        saveResult(request, "in_type", inType);
//        saveResult(request, "topic", FrameCharUtil.GbkToIso(vo.getInTypeText()));

////HZ：入库汇总查询 ，ID：新票入库卡号段录入，DF：生产入出库差额，CF：核查入出差额，QF：清洗入出差额
//        if (inType.equals("HZ") || inType.equals("ID") || (billNo != null && !billNo.equals("")
//                && (billNo.substring(0, 2).equals("DF") || billNo.substring(0, 2).equals("CF") || billNo.substring(0, 2).equals("QF")))) {
//            this.billNo = billNo.substring(2, 14);
//        }
        OperationResult opResult = new OperationResult();
        try {
            if (command == null) {
                command = CommandConstant.COMMAND_QUERY;
            }
            command = command.trim();

            if (command.equals(CommandConstant.COMMAND_QUERY)) {
                opResult = this.query(request, this.tsInProduceDetailMapper, this.operationLogMapper, inType);
            }
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                opResult = this.delete(request, this.tsInProduceDetailMapper, this.operationLogMapper);
            }

            if (this.isUpdateOp(command, null))//更新操作，增、删、改、审核,查询更新结果或原查询结果
            {
                this.queryUpdateResult(command, request, tsInProduceDetailMapper, operationLogMapper, opResult, mv, inType);
            }

        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }

        String[] attrNames = this.getAttributeNames(inType);
        this.setPageOptions(attrNames, mv, request, response); 
        this.setOperatorId(mv, request);
        this.getResultSetText(opResult.getReturnResultSet(), mv, inType);
        this.baseHandlerForOutIn(request, response, mv); //出入库基本回传参数
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集
        return mv;
    }
    
    private String getInType(HttpServletRequest request) {
        String inType = "";
        String billNo = (String) request.getParameter("queryCondition");
//        String inType = request.getParameter("inType"); //入库类型:生产入库 "SR";生产入出库差额:"DF"
        if (((String) request.getParameter("in_type")) != null) {
            inType = (String) request.getParameter("in_type");
        } else {
            inType = billNo.substring(0, 2);
        }
        return inType;
    }

    private ModelAndView getModelAndView(String inType) {
        if (inType.equals("SR")) {
            return new ModelAndView("/jsp/storagein/ticketStorageInProduceDetail.jsp");
        } else if (inType.equals("DF")) {
            //生产入出库差额
            return new ModelAndView("/jsp/storagein/ticketStorageInOutProduceDiff.jsp");
        }
        return new ModelAndView("");
    }

    private String[] getAttributeNames(String inType) {

        if (inType.equals("SR")) {
            String[] attrNames = {IN_OUT_REASON_FOR_IN_PRODUCES, IN_OUT_REASON_PRODUCE,
            IC_CARD_MAIN, IC_CARD_MAIN_SUB, STORAGES,STORAGE_AREAS,IC_LINES_SERIAL,IC_LINE_STATIONS, MODES,AREAS,IC_CARD_SUB,IC_LINES,IC_STATIONS};
            return attrNames;
        } else if (inType.equals("DF")) {
            //生产入出库差额
            String[] attrNames = {DIFF_REASONS};
            return attrNames;
        }
        return new String[0];

    }

    private void getResultSetText(List resultSet, ModelAndView mv,String inType) {
        if (inType.equals("SR")) {
            this.getResultSetTextForSR(resultSet,mv);
        } else if (inType.equals("DF")) {
            this.getResultSetTextForDF(resultSet,mv);
        }

    }
    
    private void getResultSetTextForSR(List<TicketStorageInDetailBase> resultSet, ModelAndView mv) {
//        reason_id_name、storage_id_name、area_id_name、ic_main_type_name、ic_sub_type_name、
//        line_id_name、station_id_name、use_flag_name、exit_line_id_name、exit_station_id_name、mode_name
        List<PubFlag> inOutReasonForInProduces = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IN_OUT_REASON_FOR_IN_PRODUCES);
        List<PubFlag> storages = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.STORAGES);
        //STORAGE_AREAS是字符串，使用AREAS
        List<PubFlag> areas = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.AREAS);
        List<PubFlag> icCardMainTypes = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_MAIN);
        List<PubFlag> icCardSubTypes = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_SUB);
        List<PubFlag> icLines = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_LINES);
        List<PubFlag> icStations = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_STATIONS);
        List<PubFlag> useFlags = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.USE_FLAGS);
        List<PubFlag> modes = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.MODES);

        for (TicketStorageInDetailBase inDetailBase : resultSet) {
            if (inOutReasonForInProduces != null && !inOutReasonForInProduces.isEmpty()) {
                inDetailBase.setReason_id_name(DBUtil.getTextByCode(inDetailBase.getReason_id(), inOutReasonForInProduces));
            }
            if (storages != null && !storages.isEmpty()) {
                inDetailBase.setStorage_id_name(DBUtil.getTextByCode(inDetailBase.getStorage_id(), storages));
            }
            if (areas != null && !areas.isEmpty()) {
                inDetailBase.setArea_id_name(DBUtil.getTextByCode(inDetailBase.getArea_id(), areas));
            }
            if (icCardMainTypes != null && !icCardMainTypes.isEmpty()) {
                inDetailBase.setIc_main_type_name(DBUtil.getTextByCode(inDetailBase.getIc_main_type(), icCardMainTypes));
            }
            if (icCardSubTypes != null && !icCardSubTypes.isEmpty()) {
//                inDetailBase.setIc_sub_type_name(DBUtil.getTextByCode(inDetailBase.getIc_sub_type(), icCardSubTypes));
                inDetailBase.setIc_sub_type_name(DBUtil.getTextByCode(inDetailBase.getIc_sub_type(), inDetailBase.getIc_main_type(), icCardSubTypes));
            }
            if (icLines != null && !icLines.isEmpty()) {
                inDetailBase.setLine_id_name(DBUtil.getTextByCode(inDetailBase.getLine_id(), icLines));
                inDetailBase.setExit_line_id_name(DBUtil.getTextByCode(inDetailBase.getExit_line_id(), icLines));
            }
            if (icStations != null && !icStations.isEmpty()) {
                inDetailBase.setStation_id_name(DBUtil.getTextByCode(inDetailBase.getStation_id(), inDetailBase.getLine_id(), icStations));
                inDetailBase.setExit_station_id_name(DBUtil.getTextByCode(inDetailBase.getExit_station_id(), inDetailBase.getExit_line_id(), icStations));
            }
            if (useFlags != null && !useFlags.isEmpty()) {
                inDetailBase.setUse_flag_name(DBUtil.getTextByCode(inDetailBase.getUse_flag(), useFlags));
            }
            if (modes != null && !modes.isEmpty()) {
                inDetailBase.setModel_name(DBUtil.getTextByCode(inDetailBase.getModel(), modes));
            }
            
        }
        
    }
    
    private void getResultSetTextForDF(List<TicketStorageInOutProduceDiff> resultSet, ModelAndView mv) {
        List<PubFlag> diffReasons = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.DIFF_REASONS);

        for (TicketStorageInOutProduceDiff inOutProduceDiff : resultSet) {
            if (diffReasons != null && !diffReasons.isEmpty()) {
                inOutProduceDiff.setDiff_id_name(DBUtil.getTextByCode(inOutProduceDiff.getDiff_id(), diffReasons));
            }
        }
        
    }
    

    private void queryUpdateResult(String command, HttpServletRequest request, TicketStorageInProduceDetailMapper tsInProduceDetailMapper,
            OperationLogMapper opLogMapper, OperationResult opResult, ModelAndView mv, String inType) throws Exception {
        if (!command.equals(CommandConstant.COMMAND_ADD)) {
            this.saveQueryControlDefaultValues(request, mv); //保存查询条件
        }
        this.query(request, tsInProduceDetailMapper, opLogMapper, inType);

    }

    public OperationResult query(HttpServletRequest request, TicketStorageInProduceDetailMapper tsInProduceDetailMapper, OperationLogMapper opLogMapper, String inType) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageInProduceDetail queryCondition;
        List<TicketStorageInDetailBase> resultSet = new ArrayList<TicketStorageInDetailBase>();

        try {
            queryCondition = this.getQueryConditionInDetail(request,inType);
//            resultSet = this.queryForInDetail(queryCondition.getRecord_flag(), queryCondition.getBill_no());
            this.queryForInDetailOrDiff(request, queryCondition, or);

//            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + or.getReturnResultSet().size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + or.getReturnResultSet().size() + "条记录", opLogMapper);
        return or;

    }

    private void queryForInDetailOrDiff(HttpServletRequest request, TicketStorageInProduceDetail queryCondition, OperationResult or) {
//        String inType = FormUtil.getParameter(request, "inType");
        String inType = queryCondition.getIn_type();
        if (inType.equals("SR")) {
            List<TicketStorageInDetailBase> resultSet = this.queryForInDetail(queryCondition.getRecord_flag(), queryCondition.getBill_no());
            or.setReturnResultSet(resultSet);
        } else if (inType.equals("DF")) {
            List<TicketStorageInOutProduceDiff> resultSet = this.queryForInOutProduceDiff(queryCondition.getBill_no());
            or.setReturnResultSet(resultSet);
        }
    }

    private List<TicketStorageInOutProduceDiff> queryForInOutProduceDiff(String billNo) {
        Map<String, String> queryInDetailMap = new HashMap<String, String>();
        queryInDetailMap.put("billNo", billNo);
        List<TicketStorageInOutProduceDiff> inOutDiffProduceList = tsInProduceDetailMapper.getInOutDiffProduceList(queryInDetailMap);
        return inOutDiffProduceList;

    }

    public OperationResult delete(HttpServletRequest request, TicketStorageInProduceDetailMapper tsInProduceDetailMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<TicketStorageInProduceDetail> pos = this.getReqAttributeForDelete(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            //明细表有没有记录
//            if (isHasInBillDetail(pos)) {
//                rmsg.addMessage("删除失败,请先删除明细！");
//                return rmsg;
//            }
            n = this.deleteInDetailByTrans(request, tsInProduceDetailMapper, pos);
            if (n <= 0) {
//                rmsg.addMessage("删除失败！");
                rmsg.addMessage(pos.get(0).getBill_no() + "删除失败！");
                return rmsg;
            }
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    protected int deleteInDetailByTrans(HttpServletRequest request, TicketStorageInProduceDetailMapper tsInProduceDetailMapper, Vector<TicketStorageInProduceDetail> pos) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            //删除入库单明细
            n = tsInProduceDetailMapper.deleteInProduceDetail(pos.get(0).getBill_no());

            txMgr.commit(status);

        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected Vector<TicketStorageInProduceDetail> getReqAttributeForDelete(HttpServletRequest request) {
        TicketStorageInBill po = new TicketStorageInBill();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageInProduceDetail> selectedItems = this.getInBillDetailSelectIDs(selectIds, request);
        return selectedItems;
    }

    protected Vector<TicketStorageInProduceDetail> getInBillDetailSelectIDs(String selectedIds, HttpServletRequest request) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageInProduceDetail> ipds = new Vector();
        TicketStorageInProduceDetail ipd;
        String operatorId = PageControlUtil.getOperatorFromSession(request);
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            ipd = this.getInDetail(strIds, "#");
//            ipd.setOperator(operatorId);
            ipds.add(ipd);
        }
        return ipds;
    }

    protected TicketStorageInProduceDetail getInDetail(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageInProduceDetail ipd = new TicketStorageInProduceDetail();
        ;
//        Vector<TicketStorageInProduceDetail> ipds = new Vector();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                ipd.setBill_no(tmp);
                continue;
            }
        }
        return ipd;
    }

    protected TicketStorageInProduceDetail getQueryConditionInDetail(HttpServletRequest request, String inType) {
        TicketStorageInProduceDetail qCon = new TicketStorageInProduceDetail();

//        qCon.setIn_type(FormUtil.getParameter(request, "inType"));
        qCon.setIn_type(inType);
//        qCon.setBill_date_begin(FormUtil.getParameter(request, "q_beginTime"));
//        qCon.setBill_date_end(FormUtil.getParameter(request, "q_endTime"));

        qCon.setBill_no(request.getParameter("queryCondition"));  //
        qCon.setRecord_flag(request.getParameter("billRecordFlag"));
        return qCon;
    }
    

}
