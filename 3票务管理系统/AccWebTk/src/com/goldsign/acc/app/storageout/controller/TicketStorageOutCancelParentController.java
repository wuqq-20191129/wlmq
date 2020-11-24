/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storageout.controller;

import com.goldsign.acc.app.storageout.entity.TicketStorageOutCancelPlan;
import com.goldsign.acc.app.storageout.entity.TicketStorageOutProduceOrder;
import com.goldsign.acc.app.storageout.entity.TicketStorageOutProduceOutBill;
import com.goldsign.acc.app.storageout.entity.TicketStorageOutProducePlan;
import com.goldsign.acc.app.storageout.mapper.TicketStorageOutCancelMapper;
import com.goldsign.acc.app.storageout.mapper.TicketStorageOutProduceMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.InOutConstant;
import com.goldsign.acc.frame.controller.StorageOutInBaseController;
import com.goldsign.acc.frame.mapper.BillMapper;
import com.goldsign.acc.frame.util.CharUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.PageControlUtil;
import com.goldsign.acc.frame.util.PubUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author hejj
 */
public class TicketStorageOutCancelParentController extends StorageOutInBaseController {

    protected int addPlanByTrans(HttpServletRequest request, TicketStorageOutCancelMapper tsopMapper, BillMapper billMapper, TicketStorageOutCancelPlan vo, OperationResult opResult) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            this.addVoBillNo(billMapper, vo);
            opResult.setAddPrimaryKey(vo.getBillNo()); //增加操作记录的主健值
            n = tsopMapper.addPlan(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected int addPlanDetailByTrans(HttpServletRequest request, TicketStorageOutCancelMapper tsopMapper, TicketStorageOutCancelPlan vo, OperationResult opResult) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = tsopMapper.addPlanDetail(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected boolean isSameTypeDetail(TicketStorageOutCancelMapper tsopMapper, TicketStorageOutCancelPlan vo) { //判断票卡类型是否相同
        List<TicketStorageOutCancelPlan> queryPlanDetail = tsopMapper.queryPlanDetail(vo);
        if (queryPlanDetail.isEmpty()) {
            return false;
        }
        String icMainType = queryPlanDetail.get(0).getIcMainType();
        String icSubType = queryPlanDetail.get(0).getIcSubType();
        if (icMainType.equals(vo.getIcMainType()) && icSubType.equals(vo.getIcSubType())) {
            return false;
        }
        return true;
    }

    protected boolean checkMulStorage(TicketStorageOutCancelPlan po, TicketStorageOutCancelMapper tsocMapper) { //判断是否多个仓库

        String storage_id = tsocMapper.getDistinctStorage(po);
        if (storage_id == null || storage_id.isEmpty()) {
            return false;
        } else {
            if (storage_id.equals(po.getStorageId())) {
                return false;
            }
        }
        return true;
    }

    protected HashMap<String, Object> auditOutBillByTrans(HttpServletRequest request, TicketStorageOutProduceMapper tsopMapper, TicketStorageOutProduceOutBill po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        HashMap<String, Object> parmMap = new HashMap();
        try {
            status = txMgr.getTransaction(this.def);
            this.setMapParamsForOutBill(parmMap, po);
            tsopMapper.auditOutBill(parmMap);
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        } finally {
            return parmMap;
        }
    }

    protected HashMap<String, Object> auditPlanByTrans(HttpServletRequest request, TicketStorageOutCancelMapper tsopMapper, TicketStorageOutCancelPlan po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        HashMap<String, Object> parmMap = new HashMap();
        try {
            status = txMgr.getTransaction(this.def);
            this.setMapParamsForPlan(parmMap, po);
            tsopMapper.auditPlan(parmMap);
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        } finally {
            return parmMap;
        }
    }

    protected int deletePlanByTrans(HttpServletRequest request, TicketStorageOutCancelMapper tsopMapper, Vector<TicketStorageOutCancelPlan> pos) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (TicketStorageOutCancelPlan po : pos) {
                n += tsopMapper.deletePlan(po);
            }
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected boolean isExistingPlanDetial(TicketStorageOutCancelPlan po, TicketStorageOutCancelMapper tsocMapper) { //判断是否存在明细

        List<TicketStorageOutCancelPlan> list = tsocMapper.queryPlanDetail(po);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }

    protected int deletePlanDetailByTrans(HttpServletRequest request, TicketStorageOutCancelMapper tsopMapper, Vector<TicketStorageOutCancelPlan> pos) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (TicketStorageOutCancelPlan po : pos) {
                n += tsopMapper.deletePlanDetail(po);
            }
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    public String getBillNoTemp(BillMapper billMapper, String billType) {
        HashMap<String, String> parmMap = new HashMap();
        parmMap.put("piBillMainType", billType);
        billMapper.getBillNoTemp(parmMap);
        String billNo = parmMap.get("poBillNo");
        return billNo;
        // parmMap.put("piBillMainType", InOutConstant.TYPE_BILL_NO_PRODUCE_PLAN_TEMP);
    }

    //判断多日票。。。。。。
    protected void addVoBillNo(BillMapper billMappe, TicketStorageOutCancelPlan vo) {
        String billNoPlan = this.getBillNoTemp(billMappe, InOutConstant.TYPE_BILL_NO_CANCEL_PLAN);
        String billNoOut = this.getBillNoTemp(billMappe, InOutConstant.TYPE_BILL_NO_OUT_CANCEL_TEMP);
        vo.setBillNo(billNoPlan);
        vo.setOutBillNo(billNoOut);
    }

    protected void setMapParamsForOutBill(HashMap<String, Object> parmMap, TicketStorageOutProduceOutBill po) {
        parmMap.put("piBillNoOut", po.getBill_no());
        parmMap.put("piOperator", po.getOperator());
    }

    protected void setMapParamsForPlan(HashMap<String, Object> parmMap, TicketStorageOutCancelPlan po) {
        parmMap.put("piBillNoPlanTemp", po.getBillNo());
        parmMap.put("piOperator", po.getOperator());
    }

    protected TicketStorageOutCancelPlan getQueryConditionForOpPlanDetail(HttpServletRequest request, OperationResult opResult) {
        TicketStorageOutCancelPlan qCon = new TicketStorageOutCancelPlan();

        qCon.setBillNo(FormUtil.getParameter(request, "queryCondition"));

        return qCon;
    }

    protected TicketStorageOutProduceOutBill getQueryConditionForOpOutBill(HttpServletRequest request, OperationResult opResult) {
        TicketStorageOutProduceOutBill qCon = new TicketStorageOutProduceOutBill();

        qCon.setBill_no(FormUtil.getParameter(request, "queryCondition"));

        return qCon;
    }

    protected TicketStorageOutCancelPlan getQueryConditionForOpPlan(HttpServletRequest request, OperationResult opResult) {
        TicketStorageOutCancelPlan qCon = new TicketStorageOutCancelPlan();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request) || command.equals(CommandConstant.COMMAND_AUDIT)) {
            //操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;
            } else if (command.equals(CommandConstant.COMMAND_AUDIT)) {
                String bill_no = (String) request.getAttribute("bill_no");
                if (bill_no != null) {
                    qCon.setBillNo(bill_no);
                    return qCon;
                }
            }
            qCon.setBillNo(opResult.getAddPrimaryKey());
        } else {
            //操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setBillNo(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_billNo"));
                String begin_date = FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_beginTime");
                String end_date = FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_beginTime");
                if (begin_date != null && !begin_date.trim().equals("")) {
                    qCon.setBeginDate(begin_date + " 00:00:00");
                }
                if (end_date != null && !end_date.trim().equals("")) {
                    qCon.setEndDate(end_date + " 23:59:59");
                }
                qCon.setBeginDate(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_beginTime"));
                qCon.setEndDate(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_endTime"));
                qCon.setFormMaker(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_operator"));
                qCon.setIcMainType(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_cardMainCode"));
                qCon.setIcSubType(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_cardSubCode"));
//                qCon.setReasonId(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_outReason"));
                qCon.setStorageId(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_storage"));
            }
        }
        return qCon;
    }

    protected TicketStorageOutProduceOrder getQueryConditionOrder(HttpServletRequest request) {
        TicketStorageOutProduceOrder qCon = new TicketStorageOutProduceOrder();
        qCon.setBill_no(FormUtil.getParameter(request, "queryCondition"));
        return qCon;
    }

    protected TicketStorageOutProduceOutBill getQueryConditionOutBill(HttpServletRequest request) {
        TicketStorageOutProduceOutBill qCon = new TicketStorageOutProduceOutBill();
        qCon.setBill_no(FormUtil.getParameter(request, "queryCondition"));
        return qCon;
    }

    protected TicketStorageOutProduceOutBill getQueryConditionOutBillDetail(HttpServletRequest request) {
        TicketStorageOutProduceOutBill qCon = new TicketStorageOutProduceOutBill();
        qCon.setBill_no(FormUtil.getParameter(request, "queryCondition"));
        return qCon;
    }

    protected TicketStorageOutCancelPlan getQueryConditionPlan(HttpServletRequest request) {
        TicketStorageOutCancelPlan qCon = new TicketStorageOutCancelPlan();
        qCon.setBillNo(FormUtil.getParameter(request, "q_billNo"));

        String begin_date = FormUtil.getParameter(request, "q_beginTime");
        String end_date = FormUtil.getParameter(request, "q_endTime");
        if (begin_date != null && !begin_date.trim().equals("")) {
            qCon.setBeginDate(begin_date + " 00:00:00");
        }
        if (end_date != null && !end_date.trim().equals("")) {
            qCon.setEndDate(end_date + " 23:59:59");
        }

        qCon.setFormMaker(FormUtil.getParameter(request, "q_operator")); //制单人
        qCon.setIcMainType(FormUtil.getParameter(request, "q_cardMainCode"));
        qCon.setIcSubType(FormUtil.getParameter(request, "q_cardSubCode"));
//        qCon.setReasonId(FormUtil.getParameter(request, "q_outReason"));
        qCon.setStorageId(FormUtil.getParameter(request, "q_storage"));
        qCon.setRecordFlag(FormUtil.getParameter(request, "q_recordFlag"));
        qCon.setUser(PageControlUtil.getOperatorFromSession(request));
        return qCon;
    }

    protected TicketStorageOutCancelPlan getQueryConditionPlanDetail(HttpServletRequest request) {
        TicketStorageOutCancelPlan qCon = new TicketStorageOutCancelPlan();
        qCon.setBillNo(FormUtil.getParameter(request, "queryCondition"));
        return qCon;
    }

    protected Vector<TicketStorageOutProduceOutBill> getReqAttributeForOutBillAudit(HttpServletRequest request) {
        TicketStorageOutProduceOutBill po = new TicketStorageOutProduceOutBill();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageOutProduceOutBill> selectedItems = this.getOutBillSelectIDs(selectIds, request);
        //this.getBaseParameters(request, po);
        return selectedItems;
    }

    protected Vector<TicketStorageOutCancelPlan> getReqAttributeForPlanAudit(HttpServletRequest request) {
        TicketStorageOutCancelPlan po = new TicketStorageOutCancelPlan();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageOutCancelPlan> selectedItems = this.getPlanSelectIDs(selectIds, request);
        //this.getBaseParameters(request, po);
        return selectedItems;
    }

    protected Vector<TicketStorageOutCancelPlan> getReqAttributeForPlanDelete(HttpServletRequest request) {
        TicketStorageOutCancelPlan po = new TicketStorageOutCancelPlan();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageOutCancelPlan> selectedItems = this.getPlanSelectIDs(selectIds, request);
        //this.getBaseParameters(request, po);
        return selectedItems;
    }

    protected Vector<TicketStorageOutCancelPlan> getReqAttributeForPlanDetailDelete(HttpServletRequest request) {
        TicketStorageOutCancelPlan po = new TicketStorageOutCancelPlan();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageOutCancelPlan> selectedItems = this.getPlanDetailDeleteIDs(selectIds);
        //this.getBaseParameters(request, po);
        return selectedItems;
    }

    public TicketStorageOutCancelPlan getReqAttributePlan(HttpServletRequest request) {
        TicketStorageOutCancelPlan vo = new TicketStorageOutCancelPlan();
        vo.setOperator(FormUtil.getParameter(request, "d_esOperator")); //ES操作员
        vo.setRemark(FormUtil.getParameter(request, "d_remarks"));
        vo.setFormMaker(PageControlUtil.getOperatorFromSession(request)); //制单员
        vo.setRecordFlag(InOutConstant.RECORD_FLAG_BILL_UNAUDIT);
        return vo;
    }

    public TicketStorageOutCancelPlan getReqAttributePlanDetail(HttpServletRequest request) throws Exception {
        TicketStorageOutCancelPlan vo = new TicketStorageOutCancelPlan();
        vo.setBillNo(FormUtil.getParameter(request, "d_billNo")); //计划单号
        vo.setIcMainType(FormUtil.getParameter(request, "d_cardMainType")); //票卡主类型
        vo.setIcSubType(FormUtil.getParameter(request, "d_cardSubType")); //票卡子类型

        vo.setDrawQuantity(FormUtil.getParameterIntVal(request, "d_drawAmount")); //领票数量

        vo.setStorageId(FormUtil.getParameter(request, "d_storage")); //仓库
        vo.setAreaId(FormUtil.getParameter(request, "d_zone")); //票区
        vo.setEsWorktypeId(FormUtil.getParameter(request, "d_workType")); //工作类型
        vo.setWaterNo(FormUtil.getParameterIntVal(request, "d_waterNo")); //流水号
        vo.setReasonId(FormUtil.getParameter(request, "d_outReason")); //出库原因

        //多日票按有效天数计算生产卡的面值 面值=有效天数×500
        //多日票有效期=输入有效期+使用有效期天数
        //多日票计算生产卡面值 ，如果有手动输入，则不计算 面值=有效天数×500
//        if (this.isTicketForMulDays(vo.getIc_sub_type(), vo.getEs_worktype_id())) {
//            if (vo.getCard_money_produce() == 0) {
//                int cardMoneyForMulDays = this.getCardMoneyForMulDays(vo.getCard_ava_days());
//                vo.setCard_money_produce(cardMoneyForMulDays);
//            }
//            String validDate = this.getCardValidDate(vo.getValid_date(), vo.getCard_ava_days());
//            vo.setValid_date(validDate);
//        }
        return vo;
    }

    protected String getSaleFlag(String esWorkTypeId, String icMainType) {
        if (esWorkTypeId.equals("00")) {
            return "0";
        } else if (esWorkTypeId.equals("01")) {
            //20200403 moqf 乘次票 预赋值 发售标志为 0未发售
            if (icMainType.equals(InOutConstant.IC_CARD_TYPE_TCT)) {
                return "0";
            } else {
                return "1";
            }
            
        }
        ;
        return "0";
    }

    protected Vector<TicketStorageOutProduceOutBill> getOutBillSelectIDs(String selectedIds, HttpServletRequest request) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageOutProduceOutBill> sds = new Vector();
        TicketStorageOutProduceOutBill sd;
        String operatorId = PageControlUtil.getOperatorFromSession(request);
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getOutBill(strIds, "#");
            sd.setOperator(operatorId);
            sds.add(sd);
        }
        return sds;
    }

    protected Vector<TicketStorageOutCancelPlan> getPlanDetailDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageOutCancelPlan> sds = new Vector();
        TicketStorageOutCancelPlan sd;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getPlanDetail(strIds, "#");
            sds.add(sd);
        }
        return sds;
    }

    protected Vector<TicketStorageOutCancelPlan> getPlanSelectIDs(String selectedIds, HttpServletRequest request) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageOutCancelPlan> sds = new Vector();
        TicketStorageOutCancelPlan sd;
        String operatorId = PageControlUtil.getOperatorFromSession(request);
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getPlan(strIds, "#");
            sd.setOperator(operatorId);
            sds.add(sd);
        }
        return sds;
    }

    protected TicketStorageOutProduceOutBill getOutBill(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageOutProduceOutBill sd = new TicketStorageOutProduceOutBill();
        ;
        Vector<TicketStorageOutProduceOutBill> sds = new Vector();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                sd.setBill_no(tmp);
                continue;
            }
        }
        return sd;
    }

    protected TicketStorageOutCancelPlan getPlan(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageOutCancelPlan sd = new TicketStorageOutCancelPlan();
        ;
        Vector<TicketStorageOutCancelPlan> sds = new Vector();
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

    protected TicketStorageOutCancelPlan getPlanDetail(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageOutCancelPlan sd = new TicketStorageOutCancelPlan();
        ;
        Vector<TicketStorageOutProducePlan> sds = new Vector();
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

    protected int modifyPlanDetailByTrans(HttpServletRequest request, TicketStorageOutCancelMapper tsopMapper, TicketStorageOutCancelPlan vo, OperationResult opResult) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = tsopMapper.modifyPlanDetail(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected void handleResult(HashMap<String, Object> resultReturn) throws Exception {
        if (resultReturn == null || resultReturn.isEmpty()) {
            throw new Exception("执行存储过程没有返回结果！");
        }
        int retCode = (Integer) resultReturn.get("poRetCode");
        String retMsg = (String) resultReturn.get("poRetMsg");

        if (retCode == 0) {
            return;
        }
        throw new Exception(retMsg);
    }

}
