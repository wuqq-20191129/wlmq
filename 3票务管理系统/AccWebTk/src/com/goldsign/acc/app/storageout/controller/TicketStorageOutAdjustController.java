/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storageout.controller;

import com.goldsign.acc.app.storagein.entity.TicketStorageIcChkStorageForCheckIn;
import com.goldsign.acc.app.storageout.entity.TicketStorageOutAdjustBill;
import com.goldsign.acc.app.storageout.entity.TicketStorageOutAdjustBillDetail;
import com.goldsign.acc.app.storageout.mapper.TicketStorageOutAdjustMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.controller.StorageOutInBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
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
 *
 * @author mqf
 */
@Controller
public class TicketStorageOutAdjustController extends TicketStorageOutAdjustParentController {
    
    @Autowired
    private TicketStorageOutAdjustMapper tsOutAdjustMapper;
    

    @RequestMapping(value = "/ticketStorageOutAdjustManage")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/storageout/ticketStorageOutAdjust.jsp");
        String command = request.getParameter("command");
        String operType = request.getParameter("operType");
        
        OperationResult opResult = new OperationResult();
        List<String> billNoListForAdd = new ArrayList<String>(); //取临时单号作回显
        try {
            if (command != null) {
                command = command.trim();
                
                if (operType == null || isOperForOutBill(operType)) { //出库单操作
                    
                    if (command.equals(CommandConstant.COMMAND_QUERY)) {
                        opResult = this.queryOutBill(request, this.tsOutAdjustMapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_ADD)) {
                        opResult = this.addOutBill(request, this.tsOutAdjustMapper, this.operationLogMapper, billNoListForAdd);
                    }
                    if (command.equals(CommandConstant.COMMAND_DELETE)) {
                        opResult = this.deleteOutBill(request, this.tsOutAdjustMapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_AUDIT)) {
                        opResult = this.auditOutBill(request, this.tsOutAdjustMapper, this.operationLogMapper);
                    }
                }
                
                if (isOperForOutBillDetail(operType)) { //出库单明细操作
                    mv = new ModelAndView("/jsp/storageout/ticketStorageOutAdjustDetail.jsp");
                    if (command.equals(CommandConstant.COMMAND_QUERY)) {
                        opResult = this.queryOutBillDetail(request, this.tsOutAdjustMapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_DELETE)) {
                        opResult = this.deleteOutBillDetail(request, this.tsOutAdjustMapper, this.operationLogMapper);
                    }
                }
                if (isOperForCheckOut(operType)) { //调账出库盘点单
                    mv = new ModelAndView("/jsp/storageout/ticketStorageOutAdjustCheck.jsp");
                    opResult = this.queryCheckOut(request, this.tsOutAdjustMapper, this.operationLogMapper);
                } 
                if (isOperForCheckOutSelect(operType)) { //调账出库选择盘点单
                    mv = new ModelAndView("/jsp/storageout/ticketStorageOutAdjustSelectCheck.jsp");
                    opResult = this.queryCheckOutSelect(request, this.tsOutAdjustMapper, this.operationLogMapper);
                }
                
                if (this.isUpdateOp(command, null))//更新操作，增、删、改、审核,查询更新结果或原查询结果
                {
                    this.queryUpdateResult(command, operType, request, tsOutAdjustMapper, operationLogMapper, opResult, mv, billNoListForAdd);
                }
                

            }
        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }

        String[] attrNames = this.getAttributeNames(operType);
        this.setPageOptions(attrNames, mv, request, response); //设置页面线路、车站、票卡、仓库等选项值、版本
        this.setOperatorId(mv, request);
//        this.setPageOptionsForProduce(operType, mv, request, response); //生产使用参数
        this.getResultSetText(opResult.getReturnResultSet(), mv, operType);
        this.baseHandlerForOutIn(request, response, mv); //出入库基本回传参数
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集
        return mv;
    }
    
    private String[] getAttributeNames(String operType) {
        if (operType == null || this.isOperForOutBill(operType)) {
            String[] attrNames = {BILL_STATUES,IC_CARD_MAIN,IC_CARD_SUB,IC_CARD_MAIN_SUB,STORAGES,IN_OUT_REASON_PRODUCE};
            return attrNames;
        }
//        BILL_STATUES,IC_CARD_MAIN_SUB
        if (this.isOperForOutBillDetail(operType)) {
            String[] attrNames = {IC_CARD_MAIN,IC_CARD_SUB,STORAGES,AREAS,IN_OUT_REASON_PRODUCE,IC_LINES, IC_STATIONS,MODES};
            return attrNames;
        }
        if (this.isOperForCheckOut(operType)) {
            String[] attrNames = {STORAGES,AREAS,IC_CARD_MAIN,IC_CARD_SUB};
            return attrNames;
        }
        if (operType.equals(OPER_TYPE_CHECK_OUT_SELECT)) {//
            String[] attrNames = {STORAGES,AREAS,IC_CARD_MAIN,IC_CARD_SUB};
            return attrNames;
        }
        
        return new String[0];

    }
    
    private void getResultSetText(List resultSet, ModelAndView mv, String operType) {
        
        if (operType == null || isOperForOutBill(operType)) { //出库单操作
            this.getResultSetTextForOutBill(resultSet, mv);
        }

        if (isOperForOutBillDetail(operType)) { //出库单明细操作
            this.getResultSetTextForOutBillDetail(resultSet, mv);
        }
        
        if (isOperForCheckOut(operType) 
                || (operType != null && operType.equals(OPER_TYPE_CHECK_OUT_SELECT))) { //调账出库盘点单、调账出库选择盘点单
            this.getResultSetTextForCheckOut(resultSet, mv);
        } 
        

    }
    
    private void getResultSetTextForOutBill(List<TicketStorageOutAdjustBill> resultSet, ModelAndView mv) {
        List<PubFlag> billStatues = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.BILL_STATUES);

        for (TicketStorageOutAdjustBill outAdjustBill : resultSet) {
            if (billStatues != null && !billStatues.isEmpty()) {
                outAdjustBill.setRecord_flag_name(DBUtil.getTextByCode(outAdjustBill.getRecord_flag(), billStatues));
            }
        }
    }
    
    private void getResultSetTextForOutBillDetail(List<TicketStorageOutAdjustBillDetail> resultSet, ModelAndView mv) {
//String[] attrNames = {IC_CARD_MAIN,IC_CARD_SUB,STORAGES,AREAS,IN_OUT_REASON_PRODUCE,IC_LINES, IC_STATIONS,MODES};
        List<PubFlag> icCardMainTypes = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_MAIN);
        List<PubFlag> icCardSubTypes = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_SUB);
        List<PubFlag> storages = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.STORAGES);
        List<PubFlag> zones = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.AREAS);
        List<PubFlag> inOutReasonProduces = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IN_OUT_REASON_PRODUCE);
        List<PubFlag> icLines = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_LINES);
        List<PubFlag> icStations = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_STATIONS);
        List<PubFlag> modes = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.MODES);
        
        for (TicketStorageOutAdjustBillDetail outAdjustBillDetail : resultSet) {
            if (icCardMainTypes != null && !icCardMainTypes.isEmpty()) {
                outAdjustBillDetail.setIc_main_type_name(DBUtil.getTextByCode(outAdjustBillDetail.getIc_main_type(), icCardMainTypes));
            }
            
            if (icCardSubTypes != null && !icCardSubTypes.isEmpty()) {
                outAdjustBillDetail.setIc_sub_type_name(DBUtil.getTextByCode(outAdjustBillDetail.getIc_sub_type(), outAdjustBillDetail.getIc_main_type(), icCardSubTypes));
            }
            
            if (storages != null && !storages.isEmpty()) {
                outAdjustBillDetail.setStorage_id_name(DBUtil.getTextByCode(outAdjustBillDetail.getStorage_id(), storages));
            }
            
            if (zones != null && !zones.isEmpty()) {
                outAdjustBillDetail.setArea_id_name(DBUtil.getTextByCode(outAdjustBillDetail.getArea_id(), zones));
            }
            
            if (inOutReasonProduces != null && !inOutReasonProduces.isEmpty()) {
                outAdjustBillDetail.setAdjust_id_name(DBUtil.getTextByCode(outAdjustBillDetail.getAdjust_id(), inOutReasonProduces));
            }
            
            if (icLines != null && !icLines.isEmpty()) {
                outAdjustBillDetail.setLine_id_name(DBUtil.getTextByCode(outAdjustBillDetail.getLine_id(), icLines));
                outAdjustBillDetail.setExit_line_id_name(DBUtil.getTextByCode(outAdjustBillDetail.getExit_line_id(), icLines));
            }
            
            if (icStations != null && !icStations.isEmpty()) {
                outAdjustBillDetail.setStation_id_name(DBUtil.getTextByCode(outAdjustBillDetail.getStation_id(), outAdjustBillDetail.getLine_id(), icStations));
                outAdjustBillDetail.setExit_station_id_name(DBUtil.getTextByCode(outAdjustBillDetail.getExit_station_id(), outAdjustBillDetail.getExit_line_id(), icStations));
            }
            
            if (modes != null && !modes.isEmpty()) {
                outAdjustBillDetail.setModel_name(DBUtil.getTextByCode(outAdjustBillDetail.getModel(), modes));
            }
        }
    }
    
    private void getResultSetTextForCheckOut(List<TicketStorageIcChkStorageForCheckIn> resultSet, ModelAndView mv) {
        List<PubFlag> storages = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.STORAGES);
        
        List<PubFlag> areas = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.AREAS);
        List<PubFlag> icCardMainTypes = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_MAIN);
        List<PubFlag> icCardSubTypes = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_SUB);

        for (TicketStorageIcChkStorageForCheckIn chkStorage : resultSet) {
            if (storages != null && !storages.isEmpty()) {
                chkStorage.setStorageName(DBUtil.getTextByCode(chkStorage.getStorageId(), storages));
            }
            if (areas != null && !areas.isEmpty()) {
                chkStorage.setAreaName(DBUtil.getTextByCode(chkStorage.getAreaId(), areas));
            }
            if (icCardMainTypes != null && !icCardMainTypes.isEmpty()) {
                chkStorage.setIcMainName(DBUtil.getTextByCode(chkStorage.getIcMainType(), icCardMainTypes));
            }
            if (icCardSubTypes != null && !icCardSubTypes.isEmpty()) {
                chkStorage.setIcSubName(DBUtil.getTextByCode(chkStorage.getIcSubType(), icCardSubTypes));
            }

        }
    }
    
    private void queryUpdateResult(String command, String operType, HttpServletRequest request, TicketStorageOutAdjustMapper tsOutAdjustMapper,
            OperationLogMapper opLogMapper, OperationResult opResult, ModelAndView mv, List<String> billNoListForAdd) throws Exception {
        if (!command.equals(CommandConstant.COMMAND_ADD)) {
            this.saveQueryControlDefaultValues(request, mv); //保存查询条件
        }
        
        if (isOperForOutBill(operType)) { //出库单操作
            this.queryForOpForOutBill(request, tsOutAdjustMapper, opLogMapper, opResult, billNoListForAdd);
        }

        if (isOperForOutBillDetail(operType)) { //出库单明细操作
            this.queryForOpOutBillDetail(request, tsOutAdjustMapper, opLogMapper, opResult);
        }
        if (isOperForCheckOut(operType)) { //调账出库盘点单
        } 
        if (operType.equals(OPER_TYPE_CHECK_OUT_SELECT)) {//调账出库选择盘点单
        }

    }
   
    public OperationResult queryOutBill(HttpServletRequest request, TicketStorageOutAdjustMapper tsOutAdjustMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageOutAdjustBill queryCondition;
        List<TicketStorageOutAdjustBill> resultSet = new ArrayList<TicketStorageOutAdjustBill>();

        try {
            queryCondition = this.getQueryConditionOutBill(request);
            resultSet = tsOutAdjustMapper.queryOutBill(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
            
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }
    
    public OperationResult queryForOpForOutBill(HttpServletRequest request, TicketStorageOutAdjustMapper tsOutAdjustMapper, 
            OperationLogMapper opLogMapper, OperationResult opResult, List<String> billNoListForAdd) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageOutAdjustBill queryCondition;
        List<TicketStorageOutAdjustBill> resultSet = new ArrayList<TicketStorageOutAdjustBill>();

        try {
            //出库单 增加
            if (!billNoListForAdd.isEmpty()) {
                for (String billNo : billNoListForAdd) {
                    opResult.setAddPrimaryKey(billNo);
                    queryCondition = this.getQueryConditionForOp(request, opResult);
                    resultSet.addAll(tsOutAdjustMapper.queryOutBill(queryCondition));

                }
                
            } else {
                //出库单 删除、审核
                queryCondition = this.getQueryConditionForOp(request, opResult);
                if (queryCondition == null) {
                    return null;
                }
                resultSet = tsOutAdjustMapper.queryOutBill(queryCondition);
                
            }
            
            opResult.setReturnResultSet(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }
    
    public OperationResult queryForOpOutBillDetail(HttpServletRequest request, TicketStorageOutAdjustMapper tsOutAdjustMapper, 
            OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageOutAdjustBillDetail queryCondition;
        List<TicketStorageOutAdjustBillDetail> resultSet;

        try {
            queryCondition = this.getQueryConditionForOpOutBillDetail(request, opResult);
            if (queryCondition == null) {
                return null;
            }
            resultSet = tsOutAdjustMapper.queryOutBillDetail(queryCondition);
            opResult.setReturnResultSet(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }
    
    public OperationResult addOutBill(HttpServletRequest request, TicketStorageOutAdjustMapper tsOutAdjustMapper, 
            OperationLogMapper opLogMapper, List<String> billNoListForAdd) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageOutAdjustBill po = this.getReqAttributeForAdd(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        HashMap<String, Object> hmParm;
        String retMsg = "";

        try {
                hmParm = this.addOutBillByTrans(request, tsOutAdjustMapper, po);
                retMsg = this.handleResultForAdd(hmParm, po);
                //取增加调账出库的临时单号列表
                this.getbillNoList(po.getBill_no(),";",billNoListForAdd);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
        }
//        +LogConstant.addSuccessMsg(n)
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, retMsg, opLogMapper);

//        rmsg.addMessage(LogConstant.addSuccessMsg(n));
        rmsg.addMessage(retMsg);

        return rmsg;
    }
    
    public OperationResult deleteOutBill(HttpServletRequest request, TicketStorageOutAdjustMapper tsOutAdjustMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<TicketStorageOutAdjustBill> pos = this.getReqAttributeForDelete(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            //明细表有没有记录
            if (isHasOutBillDetail(tsOutAdjustMapper, pos)) {
                rmsg.addMessage("删除失败,请先删除明细！");
                return rmsg;
            }
            n = this.deleteOutBillByTrans(request, tsOutAdjustMapper, pos);
            if (n<=0) {
                rmsg.addMessage("删除失败,删除记录出错！");
                return rmsg;
            }
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }
    
    public OperationResult auditOutBill(HttpServletRequest request, TicketStorageOutAdjustMapper tsOutAdjustMapper, 
            OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<TicketStorageOutAdjustBill> pos = this.getReqAttributeForOutBillAudit(request);
        LogUtil logUtil = new LogUtil();
//        int n = 0;
        HashMap<String, Object> hmParm;
        String retMsg = "";

        try {
            //检查入库数量是否超库存上限
//            String vszMsg = this.validStorageZone(pos);
//            if (!vszMsg.equals("")) {
//                rmsg.addMessage(vszMsg);
//                return rmsg;
//            }
            
            TicketStorageOutAdjustBill po = pos.get(0);
            hmParm = this.auditOutBillByTrans(request, tsOutAdjustMapper, po);
            retMsg = this.handleResultForAudit(hmParm);  

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_AUDIT, e, opLogMapper);
        }
        //+LogConstant.auditSuccessMsg(n)
        logUtil.logOperation(CommandConstant.COMMAND_AUDIT, request, retMsg, opLogMapper);

//        rmsg.addMessage(LogConstant.auditSuccessMsg(n));
        rmsg.addMessage(retMsg);

        return rmsg;
    }
    
    public OperationResult queryOutBillDetail(HttpServletRequest request, TicketStorageOutAdjustMapper tsOutAdjustMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageOutAdjustBillDetail queryCondition;
        List<TicketStorageOutAdjustBillDetail> resultSet = new ArrayList<TicketStorageOutAdjustBillDetail>();

        try {
            queryCondition = this.getQueryConditionOutBillDetail(request);
            
            resultSet = tsOutAdjustMapper.queryOutBillDetail(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
            
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }
    
    public OperationResult deleteOutBillDetail(HttpServletRequest request, TicketStorageOutAdjustMapper tsOutAdjustMapper, 
            OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<TicketStorageOutAdjustBillDetail> pos = this.getReqAttributeForDeleteOutBillDetail(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.deleteOutDetailByTrans(request, tsOutAdjustMapper, pos);
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
    
    public OperationResult queryCheckOut(HttpServletRequest request, TicketStorageOutAdjustMapper tsOutAdjustMapper, 
            OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageIcChkStorageForCheckIn queryCondition;
        List<TicketStorageIcChkStorageForCheckIn> resultSet = new ArrayList<TicketStorageIcChkStorageForCheckIn>();
        String preMsg = "调账出库明细:";

        try {
            queryCondition = this.getQueryConditionCheckOut(request);
            resultSet = tsOutAdjustMapper.getChkStoragesList(queryCondition);

            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + or.getReturnResultSet().size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + or.getReturnResultSet().size() + "条记录", opLogMapper);
        return or;

    }
    
    public OperationResult queryCheckOutSelect(HttpServletRequest request, TicketStorageOutAdjustMapper tsOutAdjustMapper,
            OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageIcChkStorageForCheckIn queryCondition = new TicketStorageIcChkStorageForCheckIn();
        List<TicketStorageIcChkStorageForCheckIn> resultSet = new ArrayList<TicketStorageIcChkStorageForCheckIn>();
        String preMsg = "调账出库明细:";

        try {
            List storageIdList = getStorageIdListForQueryCondition(request,null); //根据操作员的仓库权限设置仓库列表
            queryCondition.setStorageIdList(storageIdList);
            
            resultSet = tsOutAdjustMapper.getChkStoragesListForSelect(queryCondition);

            or.setReturnResultSet(resultSet);
//            or.addMessage("成功查询" + or.getReturnResultSet().size() + "条记录");
            or.addMessage("");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + or.getReturnResultSet().size() + "条记录", opLogMapper);
        return or;

    }
    
    @RequestMapping("/ticketStorageOutAdjustManageExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        List results = this.getBufferElements(request);
        List results = this.getBufferElementsForCurClass(request,"com.goldsign.acc.app.storageout.entity.TicketStorageOutAdjustBill");
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields,results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }
    
    
    
    
    
    
    
    
   
    

      
   
    
  
    
  
}
