/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storagein.controller;

import com.goldsign.acc.app.storagein.entity.TicketStorageInAdjustDetail;
import com.goldsign.acc.app.storagein.entity.TicketStorageInDetailBase;
import com.goldsign.acc.app.storagein.mapper.TicketStorageInAdjustDetailMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.controller.StorageOutInBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;
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
public class TicketStorageInAdjustDetailController extends TicketStorageInDetailParentController {

    @Autowired
    private TicketStorageInAdjustDetailMapper tsInAdjustDetailMapper;

    @RequestMapping(value = "/ticketStorageInAdjustDetail")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        String command = request.getParameter("command");
//        String inType = this.getInType(request); 
        //入库类型:调账入库 "TR"
        ModelAndView mv = new ModelAndView("/jsp/storagein/ticketStorageInAdjustDetail.jsp");

        OperationResult opResult = new OperationResult();
        try {
            if (command == null) {
                command = CommandConstant.COMMAND_QUERY;
            }
            command = command.trim();

            if (command.equals(CommandConstant.COMMAND_QUERY)) {
                opResult = this.query(request, this.tsInAdjustDetailMapper, this.operationLogMapper);
            }
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                opResult = this.delete(request, this.tsInAdjustDetailMapper, this.operationLogMapper);
            }

            if (this.isUpdateOp(command, null))//更新操作，增、删、改、审核,查询更新结果或原查询结果
            {
                this.queryUpdateResult(command, request, tsInAdjustDetailMapper, operationLogMapper, opResult, mv);
            }

        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }

        String[] attrNames = this.getAttributeNames();
        this.setPageOptions(attrNames, mv, request, response); 
        this.setOperatorId(mv, request);
        this.getResultSetText(opResult.getReturnResultSet(), mv);
        this.baseHandlerForOutIn(request, response, mv); //出入库基本回传参数
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集
        return mv;
    }
    
//    private String getInType(HttpServletRequest request) {
//        String inType = "";
//        String billNo = (String) request.getParameter("queryCondition");
////        String inType = request.getParameter("inType"); //入库类型:生产入库 "SR";生产入出库差额:"DF"
//        if (((String) request.getParameter("in_type")) != null) {
//            inType = (String) request.getParameter("in_type");
//        } else {
//            inType = billNo.substring(0, 2);
//        }
//        return inType;
//    }


    private String[] getAttributeNames() {

        String[] attrNames = {IN_OUT_REASON_FOR_IN,STORAGES,AREAS,IC_CARD_MAIN,IC_CARD_SUB,IC_LINES,IC_STATIONS,MODES};
        return attrNames;

    }

    
    private void getResultSetText(List<TicketStorageInDetailBase> resultSet, ModelAndView mv) {
        
        List<PubFlag> inOutReasonForIn = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IN_OUT_REASON_FOR_IN);
        List<PubFlag> storages = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.STORAGES);
        //STORAGE_AREAS是字符串，使用AREAS
        List<PubFlag> areas = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.AREAS);
        List<PubFlag> icCardMainTypes = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_MAIN);
        List<PubFlag> icCardSubTypes = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_SUB);
        List<PubFlag> icLines = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_LINES);
        List<PubFlag> icStations = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_STATIONS);
        List<PubFlag> modes = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.MODES);

        for (TicketStorageInDetailBase inDetailBase : resultSet) {
            if (inOutReasonForIn != null && !inOutReasonForIn.isEmpty()) {
                inDetailBase.setReason_id_name(DBUtil.getTextByCode(inDetailBase.getReason_id(), inOutReasonForIn));
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
            if (modes != null && !modes.isEmpty()) {
                inDetailBase.setModel_name(DBUtil.getTextByCode(inDetailBase.getModel(), modes));
            }
            
        }
        
    }
    

    private void queryUpdateResult(String command, HttpServletRequest request, TicketStorageInAdjustDetailMapper tsInAdjustDetailMapper,
            OperationLogMapper opLogMapper, OperationResult opResult, ModelAndView mv) throws Exception {
        if (!command.equals(CommandConstant.COMMAND_ADD)) {
            this.saveQueryControlDefaultValues(request, mv); //保存查询条件
        }
        this.query(request, tsInAdjustDetailMapper, opLogMapper);

    }

    public OperationResult query(HttpServletRequest request, TicketStorageInAdjustDetailMapper tsInAdjustDetailMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageInAdjustDetail queryCondition;
        List<TicketStorageInDetailBase> resultSet = new ArrayList<TicketStorageInDetailBase>();

        try {
            queryCondition = this.getQueryConditionInDetail(request);
//            resultSet = this.queryForInDetail(queryCondition.getRecord_flag(), queryCondition.getBill_no());
            this.queryForInDetail(request, queryCondition, or);

//            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + or.getReturnResultSet().size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + or.getReturnResultSet().size() + "条记录", opLogMapper);
        return or;

    }

    private void queryForInDetail(HttpServletRequest request, TicketStorageInAdjustDetail queryCondition, OperationResult or) {
//        String inType = queryCondition.getIn_type();
        List<TicketStorageInDetailBase> resultSet = this.queryForInDetail(queryCondition.getRecord_flag(), queryCondition.getBill_no());
        or.setReturnResultSet(resultSet);
    }


    public OperationResult delete(HttpServletRequest request, TicketStorageInAdjustDetailMapper tsInAdjustDetailMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<TicketStorageInAdjustDetail> pos = this.getReqAttributeForDelete(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            //明细表有没有记录
//            if (isHasInBillDetail(pos)) {
//                rmsg.addMessage("删除失败,请先删除明细！");
//                return rmsg;
//            }
            n = this.deleteInDetailByTrans(request, tsInAdjustDetailMapper, pos);
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

    protected int deleteInDetailByTrans(HttpServletRequest request, TicketStorageInAdjustDetailMapper tsInAdjustDetailMapper, Vector<TicketStorageInAdjustDetail> pos) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            //删除入库单明细
            n = tsInAdjustDetailMapper.deleteInAdjustDetail(pos.get(0).getBill_no());

            txMgr.commit(status);

        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected Vector<TicketStorageInAdjustDetail> getReqAttributeForDelete(HttpServletRequest request) {
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageInAdjustDetail> selectedItems = this.getInBillDetailSelectIDs(selectIds, request);
        return selectedItems;
    }

    protected Vector<TicketStorageInAdjustDetail> getInBillDetailSelectIDs(String selectedIds, HttpServletRequest request) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageInAdjustDetail> inAdjustDetails = new Vector();
        TicketStorageInAdjustDetail ipd;
        String operatorId = PageControlUtil.getOperatorFromSession(request);
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            ipd = this.getInDetail(strIds, "#");
//            ipd.setOperator(operatorId);
            inAdjustDetails.add(ipd);
        }
        return inAdjustDetails;
    }

    protected TicketStorageInAdjustDetail getInDetail(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageInAdjustDetail inAdjustDetail = new TicketStorageInAdjustDetail();
        ;
//        Vector<TicketStorageInAdjustDetail> ipds = new Vector();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                inAdjustDetail.setBill_no(tmp);
                continue;
            }
        }
        return inAdjustDetail;
    }

    protected TicketStorageInAdjustDetail getQueryConditionInDetail(HttpServletRequest request) {
        TicketStorageInAdjustDetail qCon = new TicketStorageInAdjustDetail();
        qCon.setBill_no(request.getParameter("queryCondition"));  //
        qCon.setRecord_flag(request.getParameter("billRecordFlag"));
        return qCon;
    }
    

}
