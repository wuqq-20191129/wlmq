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
import com.goldsign.acc.frame.controller.StorageOutInBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.PageControlUtil;
import com.goldsign.acc.frame.util.PubUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.springframework.transaction.TransactionStatus;

/**
 *
 * @author mqf
 */
public class TicketStorageOutAdjustParentController extends StorageOutInBaseController {
    
    protected HashMap<String, Object> addOutBillByTrans(HttpServletRequest request, TicketStorageOutAdjustMapper tsOutAdjustMapper, TicketStorageOutAdjustBill po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        HashMap<String, Object> parmMap = new HashMap();
        try {
            status = txMgr.getTransaction(this.def);
            this.setMapParamsForAddOutBill(parmMap, po);
            tsOutAdjustMapper.addOutBill(parmMap);
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        } finally {
            return parmMap;
        }
    }
    
    private void setMapParamsForAddOutBill(HashMap<String, Object> parmMap, TicketStorageOutAdjustBill po) {
        parmMap.put("pi_related_bill_no", po.getRelated_bill_no());
        parmMap.put("pi_operator", po.getOperator());
        parmMap.put("pi_administer", po.getAdminister());
        parmMap.put("pi_remark", po.getRemark());
    }
    
    protected String handleResultForAdd(HashMap<String, Object> resultReturn, TicketStorageOutAdjustBill po) throws Exception {
        if (resultReturn == null || resultReturn.isEmpty()) {
            throw new Exception("执行存储过程没有返回结果！");
        }
        String billNo = (String) resultReturn.get("po_bill_no");        
        String retMsg = (String) resultReturn.get("po_retMsg");
        int retCode = (Integer) resultReturn.get("po_retCode");
        
        //billNo 多个调账出库临时单号
        po.setBill_no(billNo);
        
        if (retCode == 0) {
            return retMsg;
        } 
        throw new Exception(retMsg);
    }
    
    protected TicketStorageOutAdjustBill getQueryConditionForOp(HttpServletRequest request, OperationResult opResult) {
        TicketStorageOutAdjustBill qCon = new TicketStorageOutAdjustBill();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {
            //操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;
            }
            qCon.setBill_no(opResult.getAddPrimaryKey());
            List storageIdList = getStorageIdListForQueryCondition(request, qCon.getStorage_id()); //根据操作员的仓库权限设置仓库列表
            qCon.setStorageIdList(storageIdList);
        } else {
            //操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
        
                qCon.setBill_date_begin(FormUtil.getParameter(request, "q_beginTime"));
                qCon.setBill_date_end(FormUtil.getParameter(request, "q_endTime"));

                qCon.setBill_no(request.getParameter("q_billNo")); 

                qCon.setAdminister(request.getParameter("q_administer")); 

                qCon.setRecord_flag(request.getParameter("q_recordFlag"));
                qCon.setIc_main_type(request.getParameter("q_cardMainCode"));
                qCon.setIc_sub_type(request.getParameter("q_cardSubCode"));
                qCon.setAdjust_id(request.getParameter("q_outReason")); //出库原因
                qCon.setStorage_id(request.getParameter("q_storage"));

                List storageIdList = getStorageIdListForQueryCondition(request, qCon.getStorage_id()); //根据操作员的仓库权限设置仓库列表
                qCon.setStorageIdList(storageIdList);
            }
        }
        return qCon;
    }
    
    protected TicketStorageOutAdjustBill getQueryConditionForOpByBillNo(HttpServletRequest request, OperationResult opResult) {
        TicketStorageOutAdjustBill qCon = new TicketStorageOutAdjustBill();

//        qCon.setIn_type("TR");
//        qCon.setRelated_bill_no(FormUtil.getParameter(request, "d_related_bill_no"));
       
        return qCon;
    }
    
    protected TicketStorageOutAdjustBillDetail getQueryConditionForOpOutBillDetail(HttpServletRequest request, OperationResult opResult) {
        TicketStorageOutAdjustBillDetail qCon = new TicketStorageOutAdjustBillDetail();

        qCon.setBill_no(request.getParameter("queryCondition"));  //
        qCon.setRecord_flag(request.getParameter("billRecordFlag"));
       
        return qCon;
    }
    
    
 
    
    protected Vector<TicketStorageOutAdjustBill> getReqAttributeForDelete(HttpServletRequest request) {
        TicketStorageOutAdjustBill po = new TicketStorageOutAdjustBill();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageOutAdjustBill> selectedItems = this.getOutBillSelectIDs(selectIds, request);
        return selectedItems;
    }
    
    protected Vector<TicketStorageOutAdjustBill> getOutBillSelectIDs(String selectedIds, HttpServletRequest request) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageOutAdjustBill> ibs = new Vector();
        TicketStorageOutAdjustBill ib;
        String operatorId = PageControlUtil.getOperatorFromSession(request);
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            ib = this.getOutBill(strIds, "#");
            ib.setOperator(operatorId);
            ibs.add(ib);
        }
        return ibs;
    }
    
    protected TicketStorageOutAdjustBill getOutBill(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageOutAdjustBill ib = new TicketStorageOutAdjustBill();
        ;
        Vector<TicketStorageOutAdjustBill> ibs = new Vector();
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
    
    protected TicketStorageOutAdjustBill getQueryConditionOutBill(HttpServletRequest request) {
        TicketStorageOutAdjustBill qCon = new TicketStorageOutAdjustBill();
        
        qCon.setBill_date_begin(FormUtil.getParameter(request, "q_beginTime"));
        qCon.setBill_date_end(FormUtil.getParameter(request, "q_endTime"));

        qCon.setBill_no(request.getParameter("q_billNo")); 

        qCon.setAdminister(request.getParameter("q_administer")); 

        qCon.setRecord_flag(request.getParameter("q_recordFlag"));
        qCon.setIc_main_type(request.getParameter("q_cardMainCode"));
        qCon.setIc_sub_type(request.getParameter("q_cardSubCode"));
        qCon.setAdjust_id(request.getParameter("q_outReason")); //出库原因
        qCon.setStorage_id(request.getParameter("q_storage"));
        
        List storageIdList = getStorageIdListForQueryCondition(request, qCon.getStorage_id()); //根据操作员的仓库权限设置仓库列表
        qCon.setStorageIdList(storageIdList);
        
        return qCon;
    }
    
    public TicketStorageOutAdjustBill getReqAttributeForAdd(HttpServletRequest request) {
        
        TicketStorageOutAdjustBill vo = new TicketStorageOutAdjustBill();
        
        vo.setAdjust_id(FormUtil.getParameter(request, "d_adjust_id"));
        vo.setAdminister(FormUtil.getParameter(request, "d_administer")); //盘点人
        vo.setRelated_bill_no(FormUtil.getParameter(request, "d_related_bill_no")); //盘点单字符串
        vo.setRemark(FormUtil.getParameter(request, "d_remark"));
        
        String operatorId = PageControlUtil.getOperatorFromSession(request);
        vo.setOperator(operatorId);
        return  vo;
    }
    
    protected Vector<TicketStorageOutAdjustBill> getReqAttributeForOutBillAudit(HttpServletRequest request) {
        TicketStorageOutAdjustBill po = new TicketStorageOutAdjustBill();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageOutAdjustBill> selectedItems = this.getOutBillSelectIDs(selectIds, request);
        return selectedItems;
    }
    
    protected boolean isHasOutBillDetail(TicketStorageOutAdjustMapper tsOutAdjustMapper, Vector<TicketStorageOutAdjustBill> pos) {
        int detailCount = tsOutAdjustMapper.getOutDetailCountForDeleteOutBill(pos);
        if (detailCount > 0) {
            return true;
        } else {
            return false;
        }
    }
    
    protected int deleteOutBillByTrans(HttpServletRequest request, TicketStorageOutAdjustMapper tsOutAdjustMapper, 
            Vector<TicketStorageOutAdjustBill> pos) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        int result = 0;
        try {
            status = txMgr.getTransaction(this.def);
            
            for (TicketStorageOutAdjustBill po : pos) {
                //更新盘点单
                result = tsOutAdjustMapper.modifyChkStorageForDeleteOutBill(po);
                if (result <=0) {
                    break;
                }
                //删除入库单
                result = tsOutAdjustMapper.deleteOutBill(po);
                if (result <=0) {
                    break;
                }
                //统计删除记录数
                n = n + result;
                
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
    
    protected HashMap<String, Object> auditOutBillByTrans(HttpServletRequest request, TicketStorageOutAdjustMapper tsOutAdjustMapper, 
            TicketStorageOutAdjustBill po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        HashMap<String, Object> parmMap = new HashMap();
        try {
            status = txMgr.getTransaction(this.def);
            this.setMapParamsForAuditInBill(parmMap, po);
            tsOutAdjustMapper.auditOutBill(parmMap);
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        } finally {
            return parmMap;
        }
    }
    
    protected void setMapParamsForAuditInBill(HashMap<String, Object> parmMap, TicketStorageOutAdjustBill po) {
        parmMap.put("pi_bill_no", po.getBill_no());
        parmMap.put("pi_operator_id", po.getOperator()); //??检查取值 
//        parmMap.put("pi_module_id", po.getModule_Id());  //??检查取值 
    }
    
    protected String handleResultForAudit(HashMap<String, Object> resultReturn) throws Exception {
        if (resultReturn == null || resultReturn.isEmpty()) {
            throw new Exception("执行存储过程没有返回结果！");
        }
//        String retMsg = "";
        String retMsg = (String) resultReturn.get("po_retMsg");     
        int retCode = (Integer) resultReturn.get("po_retCode");
        String billNo = (String) resultReturn.get("po_bill_no");    
        if (retCode == 0) {
            return retMsg;
        }
        
        throw new Exception(retMsg);
    }
    
    protected TicketStorageOutAdjustBillDetail getQueryConditionOutBillDetail(HttpServletRequest request) {
        TicketStorageOutAdjustBillDetail qCon = new TicketStorageOutAdjustBillDetail();

        qCon.setBill_no(request.getParameter("queryCondition"));  //
//        qCon.setRecord_flag(request.getParameter("billRecordFlag"));
        return qCon;
    }
    
    protected Vector<TicketStorageOutAdjustBillDetail> getReqAttributeForDeleteOutBillDetail(HttpServletRequest request) {
        TicketStorageOutAdjustBillDetail po = new TicketStorageOutAdjustBillDetail();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageOutAdjustBillDetail> selectedItems = this.getOutBillDetailSelectIDs(selectIds, request);
        return selectedItems;
    }
    
    
    protected Vector<TicketStorageOutAdjustBillDetail> getOutBillDetailSelectIDs(String selectedIds, HttpServletRequest request) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageOutAdjustBillDetail> oads = new Vector();
        TicketStorageOutAdjustBillDetail oad;
        String operatorId = PageControlUtil.getOperatorFromSession(request);
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            oad = this.getOutDetail(strIds, "#");
//            ipd.setOperator(operatorId);
            oads.add(oad);
        }
        return oads;
    }
    
    protected TicketStorageOutAdjustBillDetail getOutDetail(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageOutAdjustBillDetail oad = new TicketStorageOutAdjustBillDetail();
        ;
//        Vector<TicketStorageInProduceDetail> ipds = new Vector();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                oad.setBill_no(tmp);
                continue;
            }
        }
        return oad;
    }
    
    protected int deleteOutDetailByTrans(HttpServletRequest request, TicketStorageOutAdjustMapper tsOutAdjustMapper, Vector<TicketStorageOutAdjustBillDetail> pos) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            //删除全部出库单明细
            n = tsOutAdjustMapper.deleteOutAdjustDetail(pos.get(0).getBill_no());

            txMgr.commit(status);

        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }
    
    protected TicketStorageIcChkStorageForCheckIn getQueryConditionCheckOut(HttpServletRequest request) {
        TicketStorageIcChkStorageForCheckIn qCon = new TicketStorageIcChkStorageForCheckIn();

//        qCon.setRecord_flag(request.getParameter("billRecordFlag"));
        qCon.setCheckBillNo(request.getParameter("queryCondition"));  
        return qCon;
    }
    
    /**
     * 取增加调账出库的临时单号列表
     * @param billNoStr
     * @param delim
     * @param billNoListForAdd 
     */
    protected void getbillNoList(String billNoStr, String delim, List<String> billNoListForAdd) {
        StringTokenizer st = new StringTokenizer(billNoStr, delim);
        String tmp = null;
        int i = 0;
        TicketStorageOutAdjustBillDetail oad = new TicketStorageOutAdjustBillDetail();
        ;
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            billNoListForAdd.add(tmp);
        }
    }
    
    /**
     * 根据操作员的仓库权限设置仓库列表
     * @param request
     * @return 
     */
//    protected List getStorageIdListForQueryCondition(HttpServletRequest request, String storage_id) {
//
//       String operatorId = PageControlUtil.getOperatorFromSession(request);
//        //查询当前操作员的有权限的仓库
//        List<PubFlag> storageIdOps =this.getStorages(operatorId);
//        List <String> storageIdList = new ArrayList<String>();
//        if (storage_id == null || storage_id.isEmpty()) {
//            for (PubFlag op:storageIdOps) {
//                storageIdList.add(op.getCode());
//            }
//        } else {
//            storageIdList.add(storage_id);
//        }
//        
//        return storageIdList;
//    }
    
    
}
