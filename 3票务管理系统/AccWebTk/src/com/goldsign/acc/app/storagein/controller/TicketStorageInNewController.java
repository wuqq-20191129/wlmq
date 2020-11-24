/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storagein.controller;

import com.goldsign.acc.app.storagein.entity.TicketStorageInBill;
import com.goldsign.acc.app.storagein.entity.TicketStorageInNew;
import com.goldsign.acc.app.storagein.entity.TicketStorageInNewDetail;
import com.goldsign.acc.app.storagein.mapper.TicketStorageInNewMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.controller.StorageOutInBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.util.PubUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import com.goldsign.login.vo.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author zhouy
 * 新票入库
 * 20170727
 */
@Controller
public class TicketStorageInNewController extends TicketStorageInNewParentController {

    @Autowired
    private TicketStorageInNewMapper tsinMapper;
    
    
    String inType = "XR";
    String command;
    String operatorID;

    @RequestMapping(value = "/ticketStorageInNewManage")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/storagein/ticketStorageInNew.jsp");
        command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        User user = (User) request.getSession().getAttribute("User");
        operatorID = user.getAccount();
        try {
            if (command != null) {
                command = command.trim();
                    //新票入库
                if (command.equals(CommandConstant.COMMAND_QUERY)) {
                    opResult =this.query(request,this.operationLogMapper,opResult);
                }
                if (command.equals(CommandConstant.COMMAND_ADD)) {
                    opResult = this.addTicketStorageInNew(request,this.operationLogMapper,operatorID,opResult);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE)) {
                    opResult = this.deleteTicketStorageInNew(request, this.operationLogMapper,opResult);
                }
                if (command.equals(CommandConstant.COMMAND_AUDIT)) {
                    opResult = this.auditNew(request, this.operationLogMapper,opResult);
                }
                if (this.isUpdateOp(command, inType))//更新操作，增、删、改、审核,查询更新结果或原查询结果
                {
                    opResult=this.queryUpdateResult(command, request, opResult, mv);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }

        String[] attrNames = {STORAGES,IC_CARD_MAIN,IC_CARD_MAIN_SERIAL,IC_CARD_MAIN_SUB,IC_CARD_SUB,IN_OUT_REASON_FOR_IN,BILL_STATUES};
        this.setPageOptions(attrNames, mv, request, response); //设置页面票卡、仓库、入库原因、单据状态等选项值、版本
        if(this.isOperTypeForXR(inType)&&!"add".equals(command) && !"audit".equals(command)){//设置入库单的页面信息
            this.getResultSetTextForQuery(opResult.getReturnResultSet(), mv);
        }
        if(this.isOperTypeForXR(inType)&&("add".equals(command) || "audit".equals(command))){
            this.getResultSetText(opResult.getReturnResultSet(), mv);
        }
        this.baseHandlerForOutIn(request, response, mv); //出入库基本回传参数
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集
        return mv;
    }
    
    private void getResultSetText(List<TicketStorageInNew> resultSet, ModelAndView mv){
         List<PubFlag> billStatues = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.BILL_STATUES);

        for (TicketStorageInNew sd : resultSet) {
            if (billStatues != null && !billStatues.isEmpty()) {
                sd.setRecord_flag_name(DBUtil.getTextByCode(sd.getRecord_flag(), billStatues));
            }
        }
    }
    
    private void getResultSetTextForQuery(List<TicketStorageInBill> resultSet, ModelAndView mv){
         List<PubFlag> billStatues = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.BILL_STATUES);

        for (TicketStorageInBill sd : resultSet) {
            if (billStatues != null && !billStatues.isEmpty()) {
                sd.setRecord_flag_name(DBUtil.getTextByCode(sd.getRecord_flag(), billStatues));
            }
        }
    }

    public TicketStorageInNew getQueryConditionNew(HttpServletRequest request) {
        TicketStorageInNew qCon = new TicketStorageInNew();
        qCon.setBill_no(FormUtil.getParameter(request, "q_billNo"));
        qCon.setBegin_time(FormUtil.getParameter(request, "q_beginTime"));
        qCon.setEnd_time(FormUtil.getParameter(request, "q_endTime"));
        qCon.setStorage_id(FormUtil.getParameter(request, "q_storage"));
        qCon.setIc_main_type(FormUtil.getParameter(request, "q_cardMainCode"));
        qCon.setIc_sub_type(FormUtil.getParameter(request, "q_cardSubCode"));
        qCon.setReason_id(FormUtil.getParameter(request, "q_inReason"));
        qCon.setRecord_flag(FormUtil.getParameter(request, "q_recordFlag"));
        qCon.setInType("XR");
        
        return qCon;
    }
    
    public OperationResult addTicketStorageInNew(HttpServletRequest request, OperationLogMapper opLogMapper,String operatorID,OperationResult or) throws Exception {
        
        LogUtil logUtil = new LogUtil();
        Map<String,Object> map;
        TicketStorageInNew ticketStorageInNew = new TicketStorageInNew();
        List<TicketStorageInNew> resultSet = new ArrayList<>();
        TransactionStatus status = null;
        String preMsg = "";
        int n =0;

        try {
            status = txMgr.getTransaction(this.def);
            map = this.getQueryCondition(request,operatorID);
            tsinMapper.addTicketStorageInNew(map);
            ticketStorageInNew.setBill_no((String) map.get("bill_no"));
            ticketStorageInNew.setP_memo((String) map.get("p_memo"));
            ticketStorageInNew.setP_result((String) map.get("p_result"));
            if(ticketStorageInNew.getP_result().equals("0")){
                resultSet=tsinMapper.getTicketStorageInNewByBillNo(ticketStorageInNew.getBill_no());
                n=resultSet.size();
            }
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        or.setReturnResultSet(resultSet);
        if(ticketStorageInNew.getP_memo()!=null && ticketStorageInNew.getP_memo()!=""){
            or.addMessage(ticketStorageInNew.getP_memo());
            logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), opLogMapper);
        }else{
            or.addMessage("增加操作失败");
            logUtil.logOperation(CommandConstant.COMMAND_ADD, request, "增加操作失败", opLogMapper);
        }
        return or;
    }
    
    private Map<String,Object> getQueryCondition(HttpServletRequest request,String operatorID) {
        Map<String,Object> ticketStorageInNew = new HashMap<String,Object>();
        String bill_no = this.getBillNoTemp(billMapper, inType);
        ticketStorageInNew.put("p_bill_no",bill_no);
        ticketStorageInNew.put("p_type","XR");
        ticketStorageInNew.put("p_form_maker",operatorID);
        ticketStorageInNew.put("p_hand_man",FormUtil.getParameter(request, "d_handMan"));
        ticketStorageInNew.put("p_administer",FormUtil.getParameter(request, "d_administer"));
        ticketStorageInNew.put("p_accounter",FormUtil.getParameter(request, "d_accounter"));
        ticketStorageInNew.put("p_remark",FormUtil.getParameter(request, "d_remarks"));
        return ticketStorageInNew;
    }
    
    public OperationResult queryNewDetail(HttpServletRequest request,OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageInNewDetail queryCondition;
        List<TicketStorageInNewDetail> resultSet;

        try {
            queryCondition = this.getQueryConditionNewDetail(request);
            
            resultSet = this.getTicketStorageInNewDetails(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    public OperationResult deleteTicketStorageInNew(HttpServletRequest request, OperationLogMapper opLogMapper,OperationResult or) throws Exception {
        
        List<TicketStorageInNew> tsin = this.getReqAttributeForNew(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.deleteNewByTrans(request, tsin);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        

        if(n==-2){
            or.addMessage("删除失败！请先删除“入库明细”中的记录");
            logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, "删除失败，“入库明细”有记录。", opLogMapper);
        }else if(n<0){
            or.addMessage("删除失败!");
            logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, "删除失败！", opLogMapper);
        }else{
            or.addMessage(LogConstant.delSuccessMsg(n));
            logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);
        }
        return or;
    }

    public OperationResult auditNew(HttpServletRequest request, OperationLogMapper opLogMapper,OperationResult or) throws Exception {
        
        List<TicketStorageInNew> tsin = this.getReqAttributeForNew(request);
        LogUtil logUtil = new LogUtil();
        
        List<TicketStorageInNew> results = new ArrayList();
        int n = 0;
        HashMap<String, Object> hmParm;

        try {
            for (TicketStorageInNew po : tsin) {
                List<TicketStorageInNew> resultSet = new ArrayList();
                po.setOperator(operatorID);
                hmParm = this.auditNewByTrans(request, po);
                this.handleResult(hmParm);
                n += 1;
                resultSet=tsinMapper.getTicketStorageInNewByBillNo((String)hmParm.get("bill_no"));
                results.addAll(resultSet);
            }

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_AUDIT, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_AUDIT, request, LogConstant.auditSuccessMsg(n), opLogMapper);
        
        or.setReturnResultSet(results);
        or.addMessage(LogConstant.auditSuccessMsg(n));

        return or;
    }
    
    public OperationResult query(HttpServletRequest request,OperationLogMapper opLogMapper,OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageInBill queryCondition;
        List<TicketStorageInBill> resultSet = new ArrayList();

        try {
            if("query".equals(command)){
                queryCondition = this.getQueryConditionIn(request,inType);
            }else{
                queryCondition=this.getQueryConditionForOpPlan(request, opResult,inType);
            }
            resultSet = this.queryForInBill(request, queryCondition);
            
            opResult.setReturnResultSet(resultSet);
            if("query".equals(command))
                opResult.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }
    
    private OperationResult queryUpdateResult(String command, HttpServletRequest request,OperationResult opResult, ModelAndView mv) throws Exception {
        if (!command.equals(CommandConstant.COMMAND_ADD) && !command.equals(CommandConstant.COMMAND_AUDIT)) {
            this.saveQueryControlDefaultValues(request, mv); //保存查询条件
            opResult=this.query(request,this.operationLogMapper,opResult);
        }   
        return opResult;

    }
    
    @RequestMapping("/TicketStorageInNewExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElements(request);
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults = ExpUtil.entityToMapForInBill(expAllFields, results,inType);
        ExpUtil.renderExportExcelPath(response,
                ExpUtil.exportExcel(queryResults, request));
    }

}