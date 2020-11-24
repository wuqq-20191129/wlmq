/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storageout.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.TransactionStatus;

import com.goldsign.acc.app.storageout.entity.TicketStorageOutDestroyOutBill;
import com.goldsign.acc.app.storageout.entity.TicketStorageOutDestroyOutBillDetail;
import com.goldsign.acc.app.storageout.mapper.TicketStorageOutDestroyMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.InOutConstant;
import com.goldsign.acc.frame.mapper.BillMapper;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.PageControlUtil;
import com.goldsign.acc.frame.util.PubUtil;
import com.goldsign.acc.frame.vo.OperationResult;

/**
 * @desc:销毁出库
 * @author:zhongzqi
 * @create date: 2017-8-9
 */
public class TicketStorageOutDestroyParentController extends TicketStorageOutWayController {

    protected int addOutBilDetailByTrans(HttpServletRequest request, TicketStorageOutDestroyMapper mapper, TicketStorageOutDestroyOutBillDetail vo, OperationResult opResult) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = mapper.addOutBillDetail(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected int modifyOutBillDetailByTrans(HttpServletRequest request, TicketStorageOutDestroyMapper mapper, TicketStorageOutDestroyOutBillDetail vo, OperationResult opResult) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = mapper.modifyOutBillDetail(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected HashMap<String, Object> auditOutBillByTrans(HttpServletRequest request, TicketStorageOutDestroyMapper mapper, TicketStorageOutDestroyOutBill po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        HashMap<String, Object> parmMap = new HashMap(10);
        try {
            status = txMgr.getTransaction(this.def);
            this.setMapParamsForOutBill(parmMap, po);
            mapper.auditOutBill(parmMap);
            txMgr.commit(status);
        } catch (Exception e) {
            e.printStackTrace();
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
            return parmMap;

    }

    protected int deleteOutBillByTrans(HttpServletRequest request, TicketStorageOutDestroyMapper mapper, Vector<TicketStorageOutDestroyOutBill> pos) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (TicketStorageOutDestroyOutBill po : pos) {
                this.checkDetailRecord(po, mapper);
                n += mapper.deleteOutBill(po);
            }
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected int deletePlanDetailByTrans(HttpServletRequest request, TicketStorageOutDestroyMapper mapper, Vector<TicketStorageOutDestroyOutBillDetail> pos) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (TicketStorageOutDestroyOutBillDetail po : pos) {
                n += mapper.deleteOutBillDetail(po);
            }
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected void addVoBillNo(BillMapper billMappe, TicketStorageOutDestroyOutBill vo) {
        String billNoOut = this.getBillNoTemp(billMappe, InOutConstant.TYPE_BILL_NO_OUT_DESTROY_TEMP);
        vo.setBillNo(billNoOut);
    }

    protected void setMapParamsForOutBill(HashMap<String, Object> parmMap, TicketStorageOutDestroyOutBill po) {
        parmMap.put("piBillNoPlanTemp", po.getBillNo());
        parmMap.put("piOperator", po.getFormMaker());
    }

    protected TicketStorageOutDestroyOutBill getQueryConditionForOpOutBillDetail(HttpServletRequest request, OperationResult opResult) {
        TicketStorageOutDestroyOutBill qCon = new TicketStorageOutDestroyOutBill();
        qCon.setBillNo(FormUtil.getParameter(request, "queryCondition"));
        return qCon;
    }

    protected TicketStorageOutDestroyOutBill getQueryConditionOutBill(HttpServletRequest request) {
        TicketStorageOutDestroyOutBill qCon = new TicketStorageOutDestroyOutBill();
        //操作处于非添加模式
        qCon.setBillNo(FormUtil.getParameter(request, "q_billNo"));
        String beginDateTime = FormUtil.getParameter(request, "q_beginTime");
        String endDateTime = FormUtil.getParameter(request, "q_endTime");
        if (!"".equals(beginDateTime)) {
            qCon.setBeginDateTime(beginDateTime + " 00:00:00");
        }
        if (!"".equals(endDateTime)) {
            qCon.setEndDateTime(endDateTime + " 23:59:59");
        }
        qCon.setFormMaker(request.getParameter("q_formMaker"));
        qCon.setIcMainType(FormUtil.getParameter(request, "q_cardMainCode"));
        qCon.setIcSubType(FormUtil.getParameter(request, "q_cardSubCode"));
        qCon.setStorageId(FormUtil.getParameter(request, "q_storage"));
        qCon.setRecordFlag(FormUtil.getParameter(request, "q_planStatu"));
        //根据操作员的仓库权限设置仓库列表
        List storageIdList = getStorageIdListForQueryCondition(request, qCon.getStorageId());
        qCon.setStorageIdList(storageIdList);
        return qCon;
    }

    protected TicketStorageOutDestroyOutBill getQueryConditionForOpOutBill(HttpServletRequest request, OperationResult opResult) {
        TicketStorageOutDestroyOutBill qCon = new TicketStorageOutDestroyOutBill();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {
            //操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;
            }
            qCon.setBillNo(opResult.getAddPrimaryKey());
            //根据操作员的仓库权限设置仓库列表
            List storageIdList = getStorageIdListForQueryCondition(request, qCon.getStorageId());
            qCon.setStorageIdList(storageIdList);
        } else if(CommandConstant.COMMAND_AUDIT.equals(command)){
            qCon.setBillNo((String) request.getAttribute("q_billNo"));
            //根据操作员的仓库权限设置仓库列表
            List storageIdList = getStorageIdListForQueryCondition(request, qCon.getStorageId());
            qCon.setStorageIdList(storageIdList);
        }else {
            //操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setBillNo(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_billNo"));
                String beginDateTime = FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_beginTime");
                String endDateTime = FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_endTime");
                if (!"".equals(beginDateTime)) {
                    qCon.setBeginDateTime(beginDateTime + " 00:00:00");
                }
                if (!"".equals(endDateTime)) {
                    qCon.setEndDateTime(endDateTime + " 23:59:59");
                }
                qCon.setIcMainType(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_cardMainCode"));
                qCon.setIcSubType(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_cardSubCode"));
                qCon.setStorageId(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_storage"));
                qCon.setRecordFlag(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_planStatu"));
                //根据操作员的仓库权限设置仓库列表
                List storageIdList = getStorageIdListForQueryCondition(request, qCon.getStorageId());
                qCon.setStorageIdList(storageIdList);
            }
        }
        return qCon;
    }

    protected TicketStorageOutDestroyOutBill getQueryConditionOutBillDetail(HttpServletRequest request) {
        TicketStorageOutDestroyOutBill vo = new TicketStorageOutDestroyOutBill();
        vo.setBillNo(request.getParameter("queryCondition"));
        return vo;
    }

    protected Vector<TicketStorageOutDestroyOutBill> getReqAttributeForOutBillAudit(HttpServletRequest request) {
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageOutDestroyOutBill> selectedItems = this.getOutBillSelectIDs(selectIds, request);
        return selectedItems;
    }

    protected Vector<TicketStorageOutDestroyOutBill> getReqAttributeForOutBillDelete(HttpServletRequest request) {
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageOutDestroyOutBill> selectedItems = this.getOutBillSelectIDs(selectIds, request);
        return selectedItems;
    }

    protected Vector<TicketStorageOutDestroyOutBillDetail> getReqAttributeForOutBillDetailDelete(HttpServletRequest request) throws Exception {
    	this.checkAudit(request);
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageOutDestroyOutBillDetail> selectedItems = this.getOutBillDetailDeleteIDs(selectIds);
        return selectedItems;
    }

    public TicketStorageOutDestroyOutBill getReqAttributeOutBill(HttpServletRequest request) {
        TicketStorageOutDestroyOutBill vo = new TicketStorageOutDestroyOutBill();
        vo.setDrawer(FormUtil.getParameter(request, "d_drawer"));
        vo.setAdminister(FormUtil.getParameter(request, "d_administer"));
        vo.setAccounter(FormUtil.getParameter(request, "d_accounter"));
        vo.setRemark(FormUtil.getParameter(request, "d_remarks"));
        Date d = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        vo.setBillDate(f.format(d));
        //制单员
        vo.setFormMaker(PageControlUtil.getOperatorFromSession(request));
        vo.setBillType(InOutConstant.TYPE_BILL_NO_OUT_DESTROY);
        vo.setRecordFlag(InOutConstant.RECORD_FLAG_BILL_UNAUDIT);
        return vo;
    }

    public TicketStorageOutDestroyOutBillDetail getReqAttributeOutBillDetail(HttpServletRequest request) throws Exception {
    	this.checkAudit(request);
    	TicketStorageOutDestroyOutBillDetail vo = new TicketStorageOutDestroyOutBillDetail();
        //流水号
        vo.setWaterNo(FormUtil.getParameterIntVal(request, "d_waterNo"));
        //计划单号
        vo.setBillNo(FormUtil.getParameter(request, "d_billNo"));
        //票卡主类型
        vo.setIcMainType(FormUtil.getParameter(request, "d_cardMainType"));
        //票卡子类型
        vo.setIcSubType(FormUtil.getParameter(request, "d_cardSubType"));
        //仓库
        vo.setStorageId(FormUtil.getParameter(request, "d_storage"));
        //票区
        vo.setAreaId(FormUtil.getParameter(request, "d_zone"));
        vo.setOutNum(FormUtil.getParameterIntVal(request, "d_amount"));
        vo.setEsWorktypeId(InOutConstant.WORK_TYPE_DESTROY);
        vo.setCardType(InOutConstant.CARD_TYPE_NO_USE);
        vo.setCardMoney(0);
        vo.setReasonId(InOutConstant.REASON_ID_DESTROY);
        return vo;
    }

    protected Vector<TicketStorageOutDestroyOutBillDetail> getOutBillDetailDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageOutDestroyOutBillDetail> sds = new Vector();
        TicketStorageOutDestroyOutBillDetail sd;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getOutBillDetail(strIds, "#");
            sds.add(sd);
        }
        return sds;
    }

    protected Vector<TicketStorageOutDestroyOutBill> getOutBillSelectIDs(String selectedIds, HttpServletRequest request) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageOutDestroyOutBill> sds = new Vector();
        TicketStorageOutDestroyOutBill sd;
        String operatorId = PageControlUtil.getOperatorFromSession(request);
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getOutBill(strIds, "#");
            sd.setFormMaker(operatorId);
            sds.add(sd);
        }
        return sds;
    }

    protected TicketStorageOutDestroyOutBill getOutBill(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageOutDestroyOutBill sd = new TicketStorageOutDestroyOutBill();
        ;
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                sd.setBillNo(tmp);
                continue;
            }
        }
        return sd;
    }

    protected TicketStorageOutDestroyOutBillDetail getOutBillDetail(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageOutDestroyOutBillDetail sd = new TicketStorageOutDestroyOutBillDetail();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                sd.setBillNo(tmp);
                continue;
            }
            if (i == 2) {
                sd.setWaterNo(Integer.parseInt(tmp));
                continue;
            }
        }
        return sd;
    }

    protected void handleResult(HashMap<String, Object> resultReturn,HttpServletRequest request) throws Exception {
        if (resultReturn == null || resultReturn.isEmpty()) {
            throw new Exception("执行存储过程没有返回结果！");
        }
        String pre = "审核失败，";
        String  tempBillNo = (String) resultReturn.get("piBillNoPlanTemp");
        request.setAttribute("q_billNo", tempBillNo);
        int retCode = (Integer) resultReturn.get("poRetCode");
        String retMsg = pre + (String) resultReturn.get("poRetMsg");
        if (retCode == 0) {
            return;
        }
        throw new Exception(retMsg);
    }

    protected int addOutBillByTrans(HttpServletRequest request, TicketStorageOutDestroyMapper mapper, BillMapper billMapper, TicketStorageOutDestroyOutBill vo, OperationResult opResult) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            this.addVoBillNo(billMapper, vo);
            //增加操作记录的主健值
            opResult.setAddPrimaryKey(vo.getBillNo());
            n = mapper.addBill(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    public void checkMulStorage(TicketStorageOutDestroyOutBillDetail vo, TicketStorageOutDestroyMapper mapper) throws Exception {
        List<TicketStorageOutDestroyOutBillDetail> exsitStorage = mapper.getExistStorage(vo);
        if (exsitStorage != null && exsitStorage.size() > 0) {
            TicketStorageOutDestroyOutBillDetail tmp = exsitStorage.get(0);
            if (!vo.getStorageId().equals(tmp.getStorageId())) {
                throw new Exception("明细表不能存在多个仓库的明细.");
            }
        }
    }

    public void checkDetailRecord(TicketStorageOutDestroyOutBill vo, TicketStorageOutDestroyMapper mapper) throws Exception {
        String num = mapper.getDetailRecordNum(vo);
        if (!InOutConstant.RECORD_NUM_ZREO.equals(num)) {
            throw new Exception("出库单:" + vo.getBillNo() + " 含明细记录.不能删除");
        }
    }
}
