/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storagein.controller;

import com.goldsign.acc.app.storagein.entity.TicketStorageInBill;
import com.goldsign.acc.app.storagein.mapper.TicketStorageInAdjustMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.controller.StorageOutInBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.ExpUtil;
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
public class TicketStorageInAdjustController extends TicketStorageInParentController {
    
    @Autowired
    private TicketStorageInAdjustMapper tsInAdjustMapper;
    

    @RequestMapping(value = "/ticketStorageInAdjustManage")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/storagein/ticketStorageInAdjust.jsp");
        String command = request.getParameter("command");
        
        
//        String inType = "TR";   //入库的类型:调账入库
        
        OperationResult opResult = new OperationResult();
        List<String> billNoListForAdd = new ArrayList<String>(); //取临时单号作回显
        try {
            if (command != null) {
                command = command.trim();
                
                if (command.equals(CommandConstant.COMMAND_QUERY)) {
                    opResult = this.query(request, this.tsInAdjustMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD)) {
                    opResult = this.add(request, this.tsInAdjustMapper, this.operationLogMapper, billNoListForAdd);
                }
//                    if (command.equals(CommandConstant.COMMAND_MODIFY)) {
//                        opResult = this.modify(request, this.tsInAdjustMapper, this.operationLogMapper);
//                    }
                    if (command.equals(CommandConstant.COMMAND_DELETE)) {
                        opResult = this.delete(request, this.tsInAdjustMapper, this.operationLogMapper);
                    }
                    if (command.equals(CommandConstant.COMMAND_AUDIT)) {
                        opResult = this.audit(request, this.tsInAdjustMapper, this.operationLogMapper, billNoListForAdd);
                    }
                
                if (this.isUpdateOp(command, null))//更新操作，增、删、改、审核,查询更新结果或原查询结果
                {
                    this.queryUpdateResult(command, request, tsInAdjustMapper, operationLogMapper, opResult, mv, billNoListForAdd);
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
        this.setPageOptions(attrNames, mv, request, response); //设置页面线路、车站、票卡、仓库等选项值、版本
        this.setOperatorId(mv, request);
//        this.setPageOptionsForProduce(operType, mv, request, response); //生产使用参数
        this.getResultSetText(opResult.getReturnResultSet(), mv);
        this.baseHandlerForOutIn(request, response, mv); //出入库基本回传参数
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集
        return mv;
    }
    
    private String[] getAttributeNames() {
        
        String[] attrNames = {BILL_STATUES,IC_CARD_MAIN,IC_CARD_SUB,IC_CARD_MAIN_SUB,STORAGES,IN_OUT_REASON_FOR_IN};
        return attrNames;

    }
    
    private void getResultSetText(List<TicketStorageInBill> resultSet, ModelAndView mv) {
        List<PubFlag> billStatues = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.BILL_STATUES);
        
        for (TicketStorageInBill inBill : resultSet) {
            if (billStatues != null && !billStatues.isEmpty()) {
                inBill.setRecord_flag_name(DBUtil.getTextByCode(inBill.getRecord_flag(), billStatues));
            }
        }

    }
    
    private void queryUpdateResult(String command, HttpServletRequest request, TicketStorageInAdjustMapper tsInAdjustMapper,
            OperationLogMapper opLogMapper, OperationResult opResult, ModelAndView mv, List<String> billNoListForAdd) throws Exception {
        if (!command.equals(CommandConstant.COMMAND_ADD)) {
            this.saveQueryControlDefaultValues(request, mv); //保存查询条件
        }
        this.queryForOp(request, tsInAdjustMapper, opLogMapper, opResult, billNoListForAdd);

    }
   
    public OperationResult query(HttpServletRequest request, TicketStorageInAdjustMapper tsInAdjustMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageInBill queryCondition;
        List<TicketStorageInBill> resultSet = new ArrayList<TicketStorageInBill>();

        try {
            queryCondition = this.getQueryConditionIn(request);
            //查询索引表的分表信息,并设置查询queryCondition
//            setIcIdxHisListForQueryCondition(request,queryCondition);
            
//            String operatorId = PageControlUtil.getOperatorFromSession(request);
//            //查询当前操作员的有权限的仓库
//            List<PubFlag> storageIdOps =this.getStorages(operatorId);
//            List <String> storageIdList = new ArrayList<String>();
//            if (queryCondition.getStorage_id() == null || queryCondition.getStorage_id().isEmpty()) {
//                for (PubFlag op:storageIdOps) {
//                    storageIdList.add(op.getCode_text());
//                }
//            } else { 
//                storageIdList.add(queryCondition.getStorage_id());
//            }
//            queryCondition.setStorageIdList(storageIdList);
//            setStorageIdListForQueryCondition(request,queryCondition);
            
//            resultSet.addAll(tsInAdjustMapper.queryForNotExistsSubTb(queryCondition));
//            resultSet.addAll(tsInAdjustMapper.queryForNotExistsSubTb(queryCondition));
            
            resultSet = this.queryForInBill(request, queryCondition);
            
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }
    
    public OperationResult queryForOp(HttpServletRequest request, TicketStorageInAdjustMapper tsInAdjustMapper, 
            OperationLogMapper opLogMapper, OperationResult opResult, List<String> billNoListForAdd) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageInBill queryCondition;
//        List<TicketStorageInBill> resultSet;
        List<TicketStorageInBill> resultSet = new ArrayList<TicketStorageInBill>();

        try {
            if (!billNoListForAdd.isEmpty()) {
                for (String billNo : billNoListForAdd) {
                    opResult.setAddPrimaryKey(billNo);
//                    queryCondition = this.getQueryConditionForOp(request, opResult, billNo);
                    queryCondition = this.getQueryConditionForOp(request, opResult);
//                    if (queryCondition == null) {
//                        return null;
//                    }
    //                resultSet = this.queryForInBill(request, queryCondition);
                    resultSet.addAll(this.queryForInBill(request, queryCondition));

                }
                
            } else {
                queryCondition = this.getQueryConditionForOp(request, opResult, null);
//                    if (queryCondition == null) {
//                        return null;
//                    }
                    resultSet = this.queryForInBill(request, queryCondition);
            }
            opResult.setReturnResultSet(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }
    
    protected TicketStorageInBill getQueryConditionForOp(HttpServletRequest request, OperationResult opResult) {
        TicketStorageInBill qCon = new TicketStorageInBill();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        //增加审核，审核时只回显当前记录
        if (FormUtil.isAddOrModifyMode(request) || (command != null && command.equals(CommandConstant.COMMAND_AUDIT))) {
            //操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;
            }
            qCon.setIn_type("TR");
            qCon.setBill_no(opResult.getAddPrimaryKey());
        } else {
            //操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setIn_type("TR");
                qCon.setBill_date_begin(FormUtil.getParameter(request, "q_beginTime"));
                qCon.setBill_date_end(FormUtil.getParameter(request, "q_endTime"));

                qCon.setBill_no(request.getParameter("q_billNo"));  //q_form_no
                qCon.setRecord_flag(request.getParameter("q_recordFlag"));
                qCon.setRelated_bill_no(request.getParameter("q_relatedBillNo")); //盘点单号
                qCon.setIc_main_type(request.getParameter("q_cardMainCode"));
                qCon.setIc_sub_type(request.getParameter("q_cardSubCode"));
                qCon.setReason_id(request.getParameter("q_inReason"));
                qCon.setStorage_id(request.getParameter("q_storage"));
            }
        }
        return qCon;
    }
    
    
    public OperationResult add(HttpServletRequest request, TicketStorageInAdjustMapper tsInAdjustMapper, 
            OperationLogMapper opLogMapper, List<String> billNoListForAdd) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageInBill po = this.getReqAttributeForAdd(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        HashMap<String, Object> hmParm;
        String retMsg = "";

        try {
                StringTokenizer st = new StringTokenizer(po.getRelated_bill_no(), ";");
                String relatedBillNo = null;
                //调账入库可以一次选择多个盘点单，生成多个调账入库单
                int i =0;
                while (st.hasMoreTokens()) {
                    relatedBillNo = st.nextToken();
                    hmParm = this.addInBillByTrans(request, tsInAdjustMapper, po, relatedBillNo);
                    if (i == 0) {
                        retMsg += this.handleResultForAdd(hmParm, po);
                    } else {
                        retMsg += "," +this.handleResultForAdd(hmParm, po);
                    }
                    
                    billNoListForAdd.add(po.getBill_no()); //取临时单号作回显
                    i++;
                }

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
        }
//        +LogConstant.addSuccessMsg(n)
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, retMsg, opLogMapper);

//        rmsg.addMessage(LogConstant.addSuccessMsg(n));
        rmsg.addMessage(retMsg);

        return rmsg;
    }
    
    
//    private HashMap<String, Object> addInBillByTrans(HttpServletRequest request, TicketStorageInAdjustMapper tsInAdjustMapper, TicketStorageInBill po) throws Exception {
//        TransactionStatus status = null;
//        int n = 0;
//        HashMap<String, Object> parmMap = new HashMap();
//        try {
//            status = txMgr.getTransaction(this.def);
//            this.setMapParamsForAddInBill(parmMap, po);
//            tsInAdjustMapper.addInBill(parmMap);
//            txMgr.commit(status);
//        } catch (Exception e) {
//            PubUtil.handExceptionForTran(e, txMgr, status);
//        } finally {
//            return parmMap;
//        }
//    }
    
    private HashMap<String, Object> addInBillByTrans(HttpServletRequest request, TicketStorageInAdjustMapper tsInAdjustMapper, 
            TicketStorageInBill po, String relatedBillNo) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        HashMap<String, Object> parmMap = new HashMap();
        try {
            status = txMgr.getTransaction(this.def);
            this.setMapParamsForAddInBill(parmMap, po, relatedBillNo);
            tsInAdjustMapper.addInBill(parmMap);
        
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        } finally {
            return parmMap;
        }
    }
    
     protected void setMapParamsForAddInBill(HashMap<String, Object> parmMap, TicketStorageInBill po, String relatedBillNo) {
//        parmMap.put("pi_related_bill_no", po.getRelated_bill_no());
        parmMap.put("pi_related_bill_no", relatedBillNo);
        parmMap.put("pi_in_type", po.getIn_type());
        parmMap.put("pi_form_maker", po.getForm_maker());
        parmMap.put("pi_hand_man", po.getHand_man());
        parmMap.put("pi_administer", po.getAdminister());
        parmMap.put("pi_accounter", po.getAccounter());
        parmMap.put("pi_remark", po.getRemark());
    }
     
     public OperationResult modify(HttpServletRequest request, TicketStorageInAdjustMapper tsInAdjustMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageInBill po = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
//        HashMap<String, Object> hmParm;
        String retMsg = "";

        try {
                n = this.modifyInBillByTrans(request, tsInAdjustMapper, po);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, LogConstant.modifySuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }
     
    protected int modifyInBillByTrans(HttpServletRequest request, TicketStorageInAdjustMapper tsInAdjustMapper, TicketStorageInBill vo) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = tsInAdjustMapper.modifyInBill(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }
    
    public OperationResult delete(HttpServletRequest request, TicketStorageInAdjustMapper tsInAdjustMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<TicketStorageInBill> pos = this.getReqAttributeForDelete(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            //明细表有没有记录
            if (isHasInBillDetail(pos)) {
                rmsg.addMessage("删除失败,请先删除明细！");
                return rmsg;
            }
            n = this.deleteInBillByTrans(request, tsInAdjustMapper, pos);
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
    
    protected int deleteInBillByTrans(HttpServletRequest request, TicketStorageInAdjustMapper tsInAdjustMapper, Vector<TicketStorageInBill> pos) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        int result = 0;
        try {
            status = txMgr.getTransaction(this.def);
            
            for (TicketStorageInBill po : pos) {
                //更新盘点单
                result = tsInAdjustMapper.modifyChkStorageForDeleteInBill(po);
                if (result <=0) {
                    break;
                }
                //删除入库单
                result = tsInAdjustMapper.deleteInBill(po);
                if (result <=0) {
                    break;
                }
                //统计删除记录数
                n = n + result;
                
//                int inOutDiffCount = tsInAdjustMapper.getInOutDiffCount(po);
//                if (inOutDiffCount>0) {
//                    //更新出入库异表
//                    result = tsInAdjustMapper.deleteInOutDiffForDeleteInBill(po);
//                    if (result <=0) {
//                        break;
//                    }
//                }   
                
            }
            if (result > 0) {
                txMgr.commit(status);
            } else {
                txMgr.rollback(status);
            }
            
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }
    
    private boolean isHasInBillDetail(Vector<TicketStorageInBill> pos) {
        int detailCount = tsInAdjustMapper.getInDetailCountForDeleteInBill(pos);
        if (detailCount > 0) {
            return true;
        } else {
            return false;
        }
    }
    
    protected Vector<TicketStorageInBill> getReqAttributeForDelete(HttpServletRequest request) {
        TicketStorageInBill po = new TicketStorageInBill();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageInBill> selectedItems = this.getInBillSelectIDs(selectIds, request);
        return selectedItems;
    }
    
    protected Vector<TicketStorageInBill> getInBillSelectIDs(String selectedIds, HttpServletRequest request) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageInBill> ibs = new Vector();
        TicketStorageInBill ib;
        String operatorId = PageControlUtil.getOperatorFromSession(request);
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            ib = this.getInBill(strIds, "#");
            ib.setOperator(operatorId);
            ibs.add(ib);
        }
        return ibs;
    }
    
    protected TicketStorageInBill getInBill(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageInBill ib = new TicketStorageInBill();
        ;
        Vector<TicketStorageInBill> ibs = new Vector();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                ib.setBill_no(tmp);
                continue;
            }
        }
        return ib;
    }
    
    protected TicketStorageInBill getQueryConditionIn(HttpServletRequest request) {
        TicketStorageInBill qCon = new TicketStorageInBill();
        
        qCon.setIn_type("TR");
//        qCon.setBill_date_begin(FormUtil.getParameter(request, "q_beginTime"));
//        qCon.setBill_date_end(FormUtil.getParameter(request, "q_endTime"));
        String beginTime = FormUtil.getParameter(request, "q_beginTime");
        String endTime = FormUtil.getParameter(request, "q_endTime");
        if (beginTime != null && !"".equals(beginTime)) {
            qCon.setBill_date_begin(beginTime + " 00:00:00");
        }
        if (endTime != null && !"".equals(endTime)) {
            qCon.setBill_date_end(endTime + " 23:59:59");
        }

        qCon.setBill_no(request.getParameter("q_billNo"));  //q_form_no
//        qCon.setLendNo1(request.getParameter("q_lend_no")); //没用
//        qCon.setInNo1(request.getParameter("q_in_no"));
//        qCon.setInBillNo1(request.getParameter("q_in_no")); //没用
        qCon.setRecord_flag(request.getParameter("q_recordFlag"));
        qCon.setRelated_bill_no(request.getParameter("q_relatedBillNo")); //盘点单号
        qCon.setIc_main_type(request.getParameter("q_cardMainCode"));
        qCon.setIc_sub_type(request.getParameter("q_cardSubCode"));
        qCon.setReason_id(request.getParameter("q_inReason"));
        qCon.setStorage_id(request.getParameter("q_storage"));
        return qCon;
    }
    
    public TicketStorageInBill getReqAttribute(HttpServletRequest request) {
        
        TicketStorageInBill vo = new TicketStorageInBill();

        vo.setIn_type("TR");
        
        vo.setHand_man(FormUtil.getParameter(request, "d_hand_man"));
        vo.setRelated_bill_no(FormUtil.getParameter(request, "d_related_bill_no"));
        vo.setRemark(FormUtil.getParameter(request, "d_remark"));
        vo.setReason_id(FormUtil.getParameter(request, "d_reason_id"));
        

        return vo;
    }
    
    public TicketStorageInBill getReqAttributeForAdd(HttpServletRequest request) {
        TicketStorageInBill po = this.getReqAttribute(request);
        String operatorId = PageControlUtil.getOperatorFromSession(request);
        po.setForm_maker(operatorId);
        return  po;
    }
    
    protected TicketStorageInBill getQueryConditionForOp(HttpServletRequest request, OperationResult opResult, String billNo) {
        TicketStorageInBill qCon = new TicketStorageInBill();

        qCon.setIn_type("TR");
//        qCon.setRelated_bill_no(FormUtil.getParameter(request, "d_related_bill_no"));
        qCon.setBill_no(billNo);
       
        return qCon;
    }
      
    public OperationResult audit(HttpServletRequest request, TicketStorageInAdjustMapper tsInAdjustMapper, OperationLogMapper opLogMapper, 
            List<String> billNoListForAdd) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<TicketStorageInBill> pos = this.getReqAttributeForInBillAudit(request);
        LogUtil logUtil = new LogUtil();
//        int n = 0;
        HashMap<String, Object> hmParm;
        String retMsg = "";

        try {
            //检查入库数量是否超库存上限
            String vszMsg = this.validStorageZone(pos);
            if (!vszMsg.equals("")) {
                rmsg.addMessage(vszMsg);
                return rmsg;
            }
            
//            for (TicketStorageInBill po : pos) {
            TicketStorageInBill po = pos.get(0);
            hmParm = this.auditInBillByTrans(request, tsInAdjustMapper, po);
            retMsg = this.handleResultForAudit(hmParm, billNoListForAdd);  
//            n += 1;

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
    
    protected HashMap<String, Object> auditInBillByTrans(HttpServletRequest request, TicketStorageInAdjustMapper tsInAdjustMapper, TicketStorageInBill po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        HashMap<String, Object> parmMap = new HashMap();
        try {
            status = txMgr.getTransaction(this.def);
            this.setMapParamsForAuditInBill(parmMap, po);
            tsInAdjustMapper.auditInBill(parmMap);
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        } finally {
            return parmMap;
        }
    }
    
    protected String handleResultForAdd(HashMap<String, Object> resultReturn, TicketStorageInBill po) throws Exception {
        if (resultReturn == null || resultReturn.isEmpty()) {
            throw new Exception("执行存储过程没有返回结果！");
        }
        String billNo = (String) resultReturn.get("po_bill_no");        
        String retMsg = (String) resultReturn.get("po_retMsg");
        int retCode = (Integer) resultReturn.get("po_retCode");
        
        //入库单临时单据号
        po.setBill_no(billNo);
        
        if (retCode == 0) {
            return retMsg;
        } else {
            if (retCode == -1) {
                retMsg = "添加出错，信息请查看操作日志";
            } else if (retCode == -7) {
                retMsg = "添加出错,赋值区/编码区必须有盒号!";
            } else if (retCode == -8) {
                retMsg = "添加出错,起始卡号和结束卡号必须共存!";
//            } else if (retCode == -9) {
//                retMsg = "添加出错,起始盒号必须小于结束盒号!";
            } else if (retCode == -10) {
                retMsg = "添加出错,起始卡号必须小于结束卡号!";
//            } else if (retCode == -11) {
//                retMsg = "添加出错,卡号与数量不等!";
            } else if (retCode == -12) {
                retMsg = "添加出错,含有不存在的托!";
            } else if (retCode == -13) {  
                retMsg = "添加出错,含有不存在的盒号!";
//            } else if (retCode == -14) {
//                retMsg = "添加出错,找不到相应的柜!";
            } else if (retCode == -15) {
                retMsg = "添加出错,单据已经存在!";
//            } else if (retCode == -16) {
//                retMsg = "添加出错,单据已经无剩余!";
//            } else if (retCode == -17) {
//                retMsg = "添加出错,单据号为空!";
            } else if (retCode == -18) {
                retMsg = "添加出错,单据不存在或没有明细!";
//            } else if (retCode == -19) {
//                retMsg = "添加出错,起始盒号和结束盒号必须共存!";
//            } else if (retCode == -20) {
//                retMsg = "添加出错,入库数与盒数不匹配!";
//            } else if (retCode == -21) {
//                retMsg = "添加出错,没有对应的出库明细!";
//            } else if (retCode == -22) {
//                retMsg = "添加出错,盒号已经存在!";
//            } else if (retCode == -23) {
//                retMsg = "添加出错,有车票ID没有盒号!";
//            } else if (retCode == -24) {
//                retMsg = "添加出错,车票ID已经存在 !";
//            } else if (retCode == -25) {
//                retMsg = "添加出错,必须有盒号!";
//            } else if (retCode == -26) {
//                retMsg = "添加出错,生产工作单明细只能有一种票卡!";
            } else if (retCode == -27) {
                retMsg = "添加出错,入库数量超过区上限最大剩余!";
//            } else if (retCode == -28) {
//                retMsg = "添加出错,存在未审核记录!";
//            } else if (retCode == -29) {
//                retMsg = "添加出错,找不到车票ID!";
//            } else if (retCode == -30) {
//                retMsg = "添加出错,制票结果数量与生产单数量不等!";
            } else if (retCode == -31) {
                retMsg = "添加出错,入库数量超过盒最大剩余数量!";
//            } else if (retCode == -32) {
//                retMsg = "添加出错,找不到该票卡每盒最大车票数!";
//            } else if (retCode == -33) {
//                retMsg = "添加出错,单程票入赋值区必须要有效期!";
            } else {
                retMsg = "添加出错!";
            }
        }
        throw new Exception(retMsg);
    }
    
    protected String handleResultForAudit(HashMap<String, Object> resultReturn, List<String> billNoListForAdd) throws Exception {
        if (resultReturn == null || resultReturn.isEmpty()) {
            throw new Exception("执行存储过程没有返回结果！");
        }
//        String retMsg = "";
        String retMsg = (String) resultReturn.get("po_retMsg");     
        int retCode = (Integer) resultReturn.get("po_retCode");
        String billNo = (String) resultReturn.get("po_bill_no"); 
        billNoListForAdd.add(billNo); //取单号作回显
        if (retCode == 1) {
            return retMsg;
        }
        if (retCode == -1) {
            retMsg = billNo + "修改数据出错，详情请查询操作日志!";
        } else if (retCode == -2) {
            retMsg = billNo + "无法审核，没有入库明细!";
        } else if (retCode == -3) {
            retMsg = billNo + "无法审核，入库数量超过区上限!";
        } else if (retCode == -4) {
            retMsg = billNo + "无法审核，找不到该票卡面值可存放的托!";
        } else if (retCode == -6) {
            retMsg = billNo + "无法审核，没有对应该票卡面值的柜!";
        } else if (retCode == -7) {
            retMsg = billNo + "无法审核，入库明细盒号不为空与票区票卡类型按数量管理不一致!";
        } else if (retCode == -8) {
            retMsg = billNo + "无法审核，入库明细盒号为空与票区票卡类型按盒号管理不一致!";
            
        } else if (retCode == -20) {
            retMsg = "无法审核,当前位置的柜与柜设置不符!";
        } else if (retCode == -30) {
            retMsg = "无法审核,找不到这条未审核记录!";
        } else if (retCode == -40) {
            retMsg = billNo + "无法审核,入库明细中有重复数据!";
        } else if (retCode <0) {
            retMsg = billNo + "审核,操作失败!";
        } 
        
        throw new Exception(retMsg);
    }
    
    protected Vector<TicketStorageInBill> getReqAttributeForInBillAudit(HttpServletRequest request) {
        TicketStorageInBill po = new TicketStorageInBill();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageInBill> selectedItems = this.getInBillSelectIDs(selectIds, request);
        return selectedItems;
    }
    
    protected void setMapParamsForAuditInBill(HashMap<String, Object> parmMap, TicketStorageInBill po) {
        parmMap.put("pi_bill_no", po.getBill_no());
        parmMap.put("pi_operator_id", po.getOperator()); //??检查取值 
//        parmMap.put("pi_module_id", po.getModule_Id());  //??检查取值 
    }
    
    @RequestMapping("/ticketStorageInAdjustManageExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        List results = this.getBufferElements(request);
        List results = this.getBufferElementsForCurClass(request,"com.goldsign.acc.app.storagein.entity.TicketStorageInBill");
        String expAllFields = request.getParameter("expAllFields");
//        List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields,results);
        List<Map<String, Object>> queryResults = ExpUtil.entityToMapForInBill(expAllFields,results,"TR");
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }
}
