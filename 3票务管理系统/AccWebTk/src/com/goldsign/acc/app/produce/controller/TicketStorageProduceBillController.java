/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.produce.controller;

import com.goldsign.acc.app.produce.entity.TicketStorageProduceBill;
import com.goldsign.acc.app.produce.mapper.TicketStorageProduceBillMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.controller.StorageOutInBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.CharUtil;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.util.PageControlUtil;
import com.goldsign.acc.frame.util.PubUtil;
import com.goldsign.acc.frame.vo.OperationResult;
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
public class TicketStorageProduceBillController extends StorageOutInBaseController {
    
    @Autowired
    private TicketStorageProduceBillMapper tsProduceBillMapper;
    

    @RequestMapping(value = "/ticketStorageProduceBill")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/produce/ticketStorageProduceBill.jsp");
        String command = request.getParameter("command");
        
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                
                if (command.equals(CommandConstant.COMMAND_QUERY)) {
                    opResult = this.query(request, this.tsProduceBillMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_MODIFY)) {
                    opResult = this.modify(request, this.tsProduceBillMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_AUDIT)) {
                    opResult = this.audit(request, this.tsProduceBillMapper, this.operationLogMapper);
                }
                
                if (this.isUpdateOp(command, null))//更新操作，增、删、改、审核,查询更新结果或原查询结果
                {
                    this.queryUpdateResult(command, request, tsProduceBillMapper, operationLogMapper, opResult, mv);
                }
//                if (command != null) {
//                    if (command.equals("add") || command.equals("delete") || command.equals("modify") || command.equals("audit") || command.equals("submit")) {
//                        if (!command.equals("add")) {
////                            this.saveQueryControlDefaultValues(request);//保存查询条件
//                            this.saveQueryControlDefaultValues(request, mv); //保存查询条件
//                        }
//
//                    }
//                }

                

            }
        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }

        String[] attrNames = this.getAttributeNames();
        this.setPageOptions(attrNames, mv, request, response); //
        this.setOperatorId(mv, request); //页面判断登记用户与ES操作员是否一致
//        this.setPageOptionsForProduce(operType, mv, request, response); //生产使用参数
        this.getResultSetText(opResult.getReturnResultSet(), mv);
        this.baseHandlerForOutIn(request, response, mv); //出入库基本回传参数
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集
        return mv;
    }
    
    private String[] getAttributeNames() {
        
        
        String[] attrNames = {BILL_STATUES,PDU_PRODUCE_BILLS,IN_OUT_REASON_FOR_IN_PRODUCES,
            IC_CARD_MAIN,IC_CARD_SUB,IC_CARD_MAIN_SUB,STORAGES, ES_WORK_TYPES, DIFF_REASONS};
        return attrNames;

    }
    
    private void getResultSetText(List<TicketStorageProduceBill> resultSet, ModelAndView mv) {
        List<PubFlag> billStatues = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.BILL_STATUES);
        List<PubFlag> diffReasons = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.DIFF_REASONS);
        List<PubFlag> esWorkTypes = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.ES_WORK_TYPES);
        
        for (TicketStorageProduceBill produceBill : resultSet) {
            if (billStatues != null && !billStatues.isEmpty()) {
                produceBill.setRecord_flag_name(DBUtil.getTextByCode(produceBill.getRecord_flag(), billStatues));
            }
            if (diffReasons != null && !diffReasons.isEmpty()) {
                produceBill.setDiff_id_name(DBUtil.getTextByCode(produceBill.getDiff_id(), diffReasons));
            }
            if (esWorkTypes != null && !esWorkTypes.isEmpty()) {
                produceBill.setEs_worktype_id_name(DBUtil.getTextByCode(produceBill.getEs_worktype_id(), esWorkTypes));
            }
        }

    }
    
    private void queryUpdateResult(String command, HttpServletRequest request, TicketStorageProduceBillMapper tsProduceBillMapper,
            OperationLogMapper opLogMapper, OperationResult opResult, ModelAndView mv) throws Exception {
        if (!command.equals(CommandConstant.COMMAND_ADD)) {
            this.saveQueryControlDefaultValues(request, mv); //保存查询条件
        }
//        this.query(request, tsProduceBillMapper, opLogMapper);
        this.queryForOp(request, tsProduceBillMapper, opLogMapper, opResult);

    }
   
    public OperationResult query(HttpServletRequest request, TicketStorageProduceBillMapper tsProduceBillMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageProduceBill queryCondition;
        List<TicketStorageProduceBill> resultSet;

        try {
            queryCondition = this.getQueryConditionProduceBill(request);
            resultSet = tsProduceBillMapper.getProduceBillList(queryCondition);
            
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }
    
    public OperationResult queryForOp(HttpServletRequest request, TicketStorageProduceBillMapper tsProduceBillMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageProduceBill queryCondition;
        List<TicketStorageProduceBill> resultSet;

        try {
            queryCondition = this.getQueryConditionForOp(request, opResult);
            if (queryCondition == null) {
                return null;
            }
            resultSet = tsProduceBillMapper.getProduceBillList(queryCondition);
            opResult.setReturnResultSet(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }
    
    protected TicketStorageProduceBill getQueryConditionForOp(HttpServletRequest request, OperationResult opResult) {
        TicketStorageProduceBill qCon = new TicketStorageProduceBill();

        qCon.setBill_no(FormUtil.getParameter(request, "d_bill_no").trim());
       
        return qCon;
    }
    
     public OperationResult modify(HttpServletRequest request, TicketStorageProduceBillMapper tsProduceBillMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageProduceBill vo = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        String retMsg = "";

        try {
            n = this.modifyProduceBillByTrans(request, tsProduceBillMapper, vo);
//            修改成功,审核后有效票数量为:" + (drawnum - mannum)

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, LogConstant.modifySuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }
     
    protected int modifyProduceBillByTrans(HttpServletRequest request, TicketStorageProduceBillMapper tsProduceBillMapper, TicketStorageProduceBill vo) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = tsProduceBillMapper.modifyProduceBill(vo);
            if (n <= 0) {
                throw new Exception("修改失败!");
            }
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }
    
    protected Vector<TicketStorageProduceBill> getProduceBillSelectIDs(String selectedIds, HttpServletRequest request) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageProduceBill> pbs = new Vector();
        TicketStorageProduceBill pb;
        String operatorId = PageControlUtil.getOperatorFromSession(request);
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            pb = this.getProduceBill(strIds, "#");
            pb.setOperator(operatorId);
            pbs.add(pb);
        }
        return pbs;
    }
    
    protected TicketStorageProduceBill getProduceBill(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageProduceBill pb = new TicketStorageProduceBill();
        ;
        Vector<TicketStorageProduceBill> pbs = new Vector();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                pb.setBill_no(tmp);
                continue;
            }
        }
        return pb;
    }
    
    protected TicketStorageProduceBill getQueryConditionProduceBill(HttpServletRequest request) {
        TicketStorageProduceBill qCon = new TicketStorageProduceBill();
        qCon.setBill_no(request.getParameter("q_billNo"));  
        qCon.setBill_date_begin(FormUtil.getParameter(request, "q_beginTime"));
        qCon.setBill_date_end(FormUtil.getParameter(request, "q_endTime"));
        qCon.setEs_worktype_id(FormUtil.getParameter(request, "q_esWorkType"));
        qCon.setRecord_flag(request.getParameter("q_recordFlag"));
        
        return qCon;
    }
    
    public TicketStorageProduceBill getReqAttribute(HttpServletRequest request) {
        
        TicketStorageProduceBill vo = new TicketStorageProduceBill();

        vo.setBill_no(FormUtil.getParameter(request, "d_bill_no").trim());
        vo.setMan_useless_num(Integer.valueOf(FormUtil.getParameterIntVal(request, "d_man_useless_num")));
        vo.setLost_num(Integer.valueOf(FormUtil.getParameterIntVal(request, "d_lost_num")));
        vo.setReal_balance(Integer.valueOf(FormUtil.getParameterIntVal(request, "d_real_balance")));
        vo.setEs_useless_num(Integer.valueOf(FormUtil.getParameterIntVal(request, "d_es_useless_num")));
        
//        vo.setHand_man(CharUtil.IsoToUTF8(FormUtil.getParameter(request, "d_hand_man")));
//        vo.setReceive_man(CharUtil.IsoToUTF8(FormUtil.getParameter(request, "d_receive_man")));
//        vo.setRemarks(CharUtil.IsoToUTF8(FormUtil.getParameter(request, "d_remarks")));
        
        vo.setHand_man(FormUtil.getParameter(request, "d_hand_man"));
        vo.setReceive_man(FormUtil.getParameter(request, "d_receive_man"));
        vo.setRemarks(FormUtil.getParameter(request, "d_remarks"));
        vo.setDiff_id(FormUtil.getParameter(request, "d_diff_id"));

        return vo;
    }
      
    public OperationResult audit(HttpServletRequest request, TicketStorageProduceBillMapper tsProduceBillMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<TicketStorageProduceBill> pos = this.getReqAttributeForAudit(request);
        LogUtil logUtil = new LogUtil();
//        int n = 0;
        HashMap<String, Object> hmParm;
        String retMsg = "";

        try {
            TicketStorageProduceBill po = pos.get(0);
//            for (TicketStorageProduceBill po : pos) {
            hmParm = this.auditProduceBillByTrans(request, tsProduceBillMapper, po);
            retMsg = this.handleResultForAudit(hmParm);  
//                n += 1;

//            }

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_AUDIT, e, opLogMapper);
        }
        //+LogConstant.auditSuccessMsg(n)
        logUtil.logOperation(CommandConstant.COMMAND_AUDIT, request, retMsg, opLogMapper);

//        rmsg.addMessage(LogConstant.auditSuccessMsg(n));
        rmsg.addMessage(retMsg);

        return rmsg;
    }
    
    protected HashMap<String, Object> auditProduceBillByTrans(HttpServletRequest request, TicketStorageProduceBillMapper tsProduceBillMapper, TicketStorageProduceBill po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        HashMap<String, Object> parmMap = new HashMap();
        try {
            status = txMgr.getTransaction(this.def);
            this.setMapParamsForAuditProduceBill(parmMap, po);
            tsProduceBillMapper.auditProduceBill(parmMap);
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        } finally {
            return parmMap;
        }
    }
    
    
    protected String handleResultForAudit(HashMap<String, Object> resultReturn) throws Exception {
        if (resultReturn == null || resultReturn.isEmpty()) {
            throw new Exception("执行存储过程没有返回结果！");
        }
        int retCode = (Integer) resultReturn.get("po_retCode");
        String retMsg = (String) resultReturn.get("po_retMsg");
        if (retCode == 0) {
            return retMsg;
        }
        throw new Exception(retMsg);
    }
    
    protected Vector<TicketStorageProduceBill> getReqAttributeForAudit(HttpServletRequest request) {
        TicketStorageProduceBill po = new TicketStorageProduceBill();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageProduceBill> selectedItems = this.getProduceBillSelectIDs(selectIds, request);
        return selectedItems;
    }
    
    protected void setMapParamsForAuditProduceBill(HashMap<String, Object> parmMap, TicketStorageProduceBill po) {
        parmMap.put("pi_bill_no", po.getBill_no());
        parmMap.put("pi_operator", po.getOperator()); //??检查取值 
    }
    
    @RequestMapping("/ticketStorageProduceBillExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        List results = this.getBufferElements(request);
        List results = this.getBufferElementsForCurClass(request,"com.goldsign.acc.app.produce.entity.TicketStorageProduceBill");
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields,results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }
}
