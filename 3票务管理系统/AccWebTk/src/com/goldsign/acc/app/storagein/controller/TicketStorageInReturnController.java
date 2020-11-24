/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storagein.controller;

import com.goldsign.acc.app.storagein.entity.TicketStorageInBill;
import com.goldsign.acc.app.storagein.entity.TicketStorageInReturn;
import com.goldsign.acc.app.storagein.mapper.TicketStorageInReturnMapper;
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
 * @author zhouyang
 * 回收入库
 * 20170811
 */
@Controller
public class TicketStorageInReturnController extends TicketStorageInReturnParentController {

    @Autowired
    private TicketStorageInReturnMapper tsirMapper;
    
    String inType = "HR";
    String command;
    String operatorID;

    @RequestMapping(value = "/ticketStorageInReturnManage")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/storagein/ticketStorageInReturn.jsp");
        command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        User user = (User) request.getSession().getAttribute("User");
        operatorID = user.getAccount();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_QUERY)) {
                    opResult =this.query(request,this.operationLogMapper,opResult);
                }
                if (command.equals(CommandConstant.COMMAND_ADD)) {
                    opResult = this.add(request,this.operationLogMapper,operatorID,opResult);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE)) {
                    opResult = this.delete(request, this.operationLogMapper,opResult);
                }
                if (command.equals(CommandConstant.COMMAND_AUDIT)) {
                    opResult = this.audit(request, this.operationLogMapper,opResult);
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

        String[] attrNames = {STORAGES,CARD_MONEYS,IC_CARD_MAIN,IC_CARD_MAIN_SERIAL,IC_CARD_MAIN_SUB,IC_CARD_SUB,IN_OUT_REASON_FOR_IN,BILL_STATUES};
        this.setPageOptions(attrNames, mv, request, response); //设置页面票卡、仓库、入库原因、单据状态等选项值、版本
        if(!"add".equals(command) && !"audit".equals(command)){//设置入库单的页面信息
            this.getResultSetTextForQuery(opResult.getReturnResultSet(), mv);
        }
        if(("add".equals(command) || "audit".equals(command))){
            this.getResultSetText(opResult.getReturnResultSet(), mv);
        }
        this.baseHandlerForOutIn(request, response, mv); //出入库基本回传参数
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集
        return mv;
    }
    
    private void getResultSetText(List<TicketStorageInReturn> resultSet, ModelAndView mv){
         List<PubFlag> billStatues = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.BILL_STATUES);

        for (TicketStorageInReturn sd : resultSet) {
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

    
    public OperationResult add(HttpServletRequest request, OperationLogMapper opLogMapper,String operatorID,OperationResult or) throws Exception {
        TransactionStatus status = null;
        LogUtil logUtil = new LogUtil();
        Map<String,Object> map;
        TicketStorageInReturn ticketStorageInReturn = new TicketStorageInReturn();
        List<TicketStorageInReturn> resultSet = new ArrayList<>();
        int n =0;

        try {
            status = txMgr.getTransaction(this.def);
            map = this.getQueryCondition(request,operatorID);
            tsirMapper.addTicketStorageInReturn(map);
            ticketStorageInReturn.setBill_no((String) map.get("bill_no"));
            ticketStorageInReturn.setP_memo((String) map.get("p_memo"));
            ticketStorageInReturn.setP_result((String) map.get("p_result"));
            if(ticketStorageInReturn.getP_result().equals("0")){
                resultSet=tsirMapper.getTicketStorageInReturnByBillNo(ticketStorageInReturn.getBill_no());
                n=resultSet.size();
            }
            or.setReturnResultSet(resultSet);
            if(ticketStorageInReturn.getP_memo()!=null && ticketStorageInReturn.getP_memo()!=""){
                or.addMessage(ticketStorageInReturn.getP_memo());
                 logUtil.logOperation(CommandConstant.COMMAND_ADD, request, LogConstant.addSuccessMsg(n), opLogMapper);
            }else{
                or.addMessage("增加操作失败");
                 logUtil.logOperation(CommandConstant.COMMAND_ADD, request, "增加操作失败！", opLogMapper);
            }
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
//        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;
    }
    
    private Map<String,Object> getQueryCondition(HttpServletRequest request,String operatorID) {
        Map<String,Object> ticketStorageInReturn = new HashMap<String,Object>();
        String bill_no = this.getBillNoTemp(billMapper, inType);
        ticketStorageInReturn.put("p_bill_no",bill_no);
        ticketStorageInReturn.put("p_type","HR");
        ticketStorageInReturn.put("p_form_maker",operatorID);
        ticketStorageInReturn.put("p_hand_man",FormUtil.getParameter(request, "d_handMan"));
        ticketStorageInReturn.put("p_administer",FormUtil.getParameter(request, "d_administer"));
        ticketStorageInReturn.put("p_accounter",FormUtil.getParameter(request, "d_accounter"));
        ticketStorageInReturn.put("p_remark",FormUtil.getParameter(request, "d_remarks"));
        return ticketStorageInReturn;
    }
    public OperationResult delete(HttpServletRequest request, OperationLogMapper opLogMapper,OperationResult or) throws Exception {
        
        List<TicketStorageInReturn> tsin = this.getReqAttributeForNew(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.deleteByTrans(request, tsin);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }

        if(n==-2){
            or.addMessage("删除失败！请先删除“入库明细”中的记录");
            logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, "删除失败！明细中有记录！", opLogMapper);
        }else if(n<0){
            or.addMessage("删除失败!");
            logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, "删除失败！", opLogMapper);
        }else{
            or.addMessage(LogConstant.delSuccessMsg(n));
            logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);
        }
        return or;
    }

    public OperationResult audit(HttpServletRequest request, OperationLogMapper opLogMapper,OperationResult or) throws Exception {
        
        List<TicketStorageInReturn> tsin = this.getReqAttributeForNew(request);
        LogUtil logUtil = new LogUtil();
        
        List<TicketStorageInReturn> results = new ArrayList();
        int n = 0;
        HashMap<String, Object> hmParm;

        try {
            for (TicketStorageInReturn po : tsin) {
                List<TicketStorageInReturn> resultSet = new ArrayList();
                po.setOperator(operatorID);
                hmParm = this.auditNewByTrans(request, po);
                this.handleResult(hmParm);
                n += 1;
                resultSet=tsirMapper.getTicketStorageInReturnByBillNo((String)hmParm.get("bill_no"));
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
    
    @RequestMapping("/TicketStorageInReturnExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElements(request);
        String expAllFields = request.getParameter("expAllFields");
//        List<Map<String, Object>> queryResults
//                = ExpUtil.entityToMap(expAllFields, results);
        List<Map<String, Object>> queryResults = ExpUtil.entityToMapForInBill(expAllFields, results,inType);
        ExpUtil.renderExportExcelPath(response,
                ExpUtil.exportExcel(queryResults, request));
    }
}
