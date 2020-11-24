/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storageout.controller;

import com.goldsign.acc.app.storageout.entity.TicketStorageOutProduceOrder;
import com.goldsign.acc.app.storageout.entity.TicketStorageOutProduceOutBill;
import com.goldsign.acc.app.storageout.entity.TicketStorageOutProducePlan;
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
public class TicketStorageOutProduceParentController extends StorageOutInBaseController {

    protected int addPlanByTrans(HttpServletRequest request, TicketStorageOutProduceMapper tsopMapper, BillMapper billMapper, TicketStorageOutProducePlan vo, OperationResult opResult) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            this.addVoBillNo(billMapper, vo);
            opResult.setAddPrimaryKey(vo.getBill_no()); //增加操作记录的主健值
            n = tsopMapper.addPlan(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected int addPlanDetailByTrans(HttpServletRequest request, TicketStorageOutProduceMapper tsopMapper, TicketStorageOutProducePlan vo, OperationResult opResult) throws Exception {
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

    protected HashMap<String, Object> auditPlanByTrans(HttpServletRequest request, TicketStorageOutProduceMapper tsopMapper, TicketStorageOutProducePlan po) throws Exception {
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

    protected int deletePlanByTrans(HttpServletRequest request, TicketStorageOutProduceMapper tsopMapper, Vector<TicketStorageOutProducePlan> pos) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (TicketStorageOutProducePlan po : pos) {
                n += tsopMapper.deletePlan(po);
            }
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected int deletePlanDetailByTrans(HttpServletRequest request, TicketStorageOutProduceMapper tsopMapper, Vector<TicketStorageOutProducePlan> pos) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (TicketStorageOutProducePlan po : pos) {
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
    protected void addVoBillNo(BillMapper billMappe, TicketStorageOutProducePlan vo) {
        String billNoPlan = this.getBillNoTemp(billMappe, InOutConstant.TYPE_BILL_NO_PRODUCE_PLAN_TEMP);
        String billNoOut = this.getBillNoTemp(billMappe, InOutConstant.TYPE_BILL_NO_OUT_PRODUCE_TEMP);
        vo.setBill_no(billNoPlan);
        vo.setOut_bill_no(billNoOut);
    }

    protected void setMapParamsForOutBill(HashMap<String, Object> parmMap, TicketStorageOutProduceOutBill po) {
        parmMap.put("piBillNoOut", po.getBill_no());
        parmMap.put("piOperator", po.getOperator());
    }

    protected void setMapParamsForPlan(HashMap<String, Object> parmMap, TicketStorageOutProducePlan po) {
        parmMap.put("piBillNoPlanTemp", po.getBill_no());
        parmMap.put("piOperator", po.getOperator());
    }

    protected TicketStorageOutProducePlan getQueryConditionForOpPlanDetail(HttpServletRequest request, OperationResult opResult) {
        TicketStorageOutProducePlan qCon = new TicketStorageOutProducePlan();

        qCon.setBill_no(FormUtil.getParameter(request, "queryCondition"));
       
        return qCon;
    }
     protected TicketStorageOutProduceOutBill getQueryConditionForOpOutBill(HttpServletRequest request, OperationResult opResult) {
        TicketStorageOutProduceOutBill qCon = new TicketStorageOutProduceOutBill();

        qCon.setBill_no(FormUtil.getParameter(request, "queryCondition"));
       
        return qCon;
    }

    protected TicketStorageOutProducePlan getQueryConditionForOpPlan(HttpServletRequest request, OperationResult opResult) {
        TicketStorageOutProducePlan qCon = new TicketStorageOutProducePlan();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {
            //操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;
            }
            qCon.setBill_no(opResult.getAddPrimaryKey());
        } else {
            //操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setBill_no(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_billNo"));
                qCon.setBill_date_begin(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_beginTime"));
                qCon.setBill_date_end(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_endTime"));
                qCon.setOperator(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_operator"));
                qCon.setIc_main_type(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_cardMainCode"));
                qCon.setIc_sub_type(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_cardSubCode"));
                qCon.setReason_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_outReason"));
                qCon.setStorage_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_storage"));
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

    protected TicketStorageOutProducePlan getQueryConditionPlan(HttpServletRequest request) {
        TicketStorageOutProducePlan qCon = new TicketStorageOutProducePlan();
        qCon.setBill_no(FormUtil.getParameter(request, "q_billNo"));
        qCon.setBill_date_begin(FormUtil.getParameter(request, "q_beginTime"));
        qCon.setBill_date_end(FormUtil.getParameter(request, "q_endTime"));
        qCon.setOperator(FormUtil.getParameter(request, "q_operator"));
        qCon.setIc_main_type(FormUtil.getParameter(request, "q_cardMainCode"));
        qCon.setIc_sub_type(FormUtil.getParameter(request, "q_cardSubCode"));
        qCon.setReason_id(FormUtil.getParameter(request, "q_outReason"));
        qCon.setStorage_id(FormUtil.getParameter(request, "q_storage"));
        
        qCon.setRecord_flag(FormUtil.getParameter(request, "q_recordFlag"));
        qCon.setStorage_id(FormUtil.getParameter(request, "q_storage"));
        qCon.setIc_main_type(FormUtil.getParameter(request, "q_cardMainCode"));
        qCon.setIc_sub_type(FormUtil.getParameter(request, "q_cardSubCode"));
        return qCon;
    }

    protected TicketStorageOutProducePlan getQueryConditionPlanDetail(HttpServletRequest request) {
        TicketStorageOutProducePlan qCon = new TicketStorageOutProducePlan();
        qCon.setBill_no(FormUtil.getParameter(request, "queryCondition"));
        return qCon;
    }

    protected Vector<TicketStorageOutProduceOutBill> getReqAttributeForOutBillAudit(HttpServletRequest request) {
        TicketStorageOutProduceOutBill po = new TicketStorageOutProduceOutBill();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageOutProduceOutBill> selectedItems = this.getOutBillSelectIDs(selectIds, request);
        //this.getBaseParameters(request, po);
        return selectedItems;
    }

    protected Vector<TicketStorageOutProducePlan> getReqAttributeForPlanAudit(HttpServletRequest request) {
        TicketStorageOutProducePlan po = new TicketStorageOutProducePlan();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageOutProducePlan> selectedItems = this.getPlanSelectIDs(selectIds, request);
        //this.getBaseParameters(request, po);
        return selectedItems;
    }

    protected Vector<TicketStorageOutProducePlan> getReqAttributeForPlanDelete(HttpServletRequest request) {
        TicketStorageOutProducePlan po = new TicketStorageOutProducePlan();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageOutProducePlan> selectedItems = this.getPlanSelectIDs(selectIds, request);
        //this.getBaseParameters(request, po);
        return selectedItems;
    }

    protected Vector<TicketStorageOutProducePlan> getReqAttributeForPlanDetailDelete(HttpServletRequest request) {
        TicketStorageOutProducePlan po = new TicketStorageOutProducePlan();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageOutProducePlan> selectedItems = this.getPlanDetailDeleteIDs(selectIds);
        //this.getBaseParameters(request, po);
        return selectedItems;
    }

    public TicketStorageOutProducePlan getReqAttributePlan(HttpServletRequest request) {
        TicketStorageOutProducePlan vo = new TicketStorageOutProducePlan();
        vo.setOperator(FormUtil.getParameter(request, "d_esOperator")); //ES操作员
//        vo.setRemark(CharUtil.IsoToUTF8(FormUtil.getParameter(request, "d_remarks")));
        vo.setRemark(FormUtil.getParameter(request, "d_remarks"));
        vo.setForm_maker(PageControlUtil.getOperatorFromSession(request)); //制单员
        vo.setRecord_flag(InOutConstant.RECORD_FLAG_BILL_UNAUDIT);
        return vo;
    }

    public TicketStorageOutProducePlan getReqAttributePlanDetail(HttpServletRequest request) throws Exception {
        TicketStorageOutProducePlan vo = new TicketStorageOutProducePlan();
        vo.setBill_no(FormUtil.getParameter(request, "d_billNo")); //计划单号
        vo.setIc_main_type(FormUtil.getParameter(request, "d_cardMainType")); //票卡主类型
        vo.setIc_sub_type(FormUtil.getParameter(request, "d_cardSubType")); //票卡子类型
        vo.setCard_type(FormUtil.getParameter(request, "d_newOldFlag")); //记名卡标志
        vo.setDraw_quantity(FormUtil.getParameterIntVal(request, "d_drawAmount")); //领票数量
        vo.setMake_quantity(FormUtil.getParameterIntVal(request, "d_amount")); //生产数量
        vo.setStorage_id(FormUtil.getParameter(request, "d_storage")); //仓库
        vo.setArea_id(FormUtil.getParameter(request, "d_zone")); //票区
        vo.setEs_worktype_id(FormUtil.getParameter(request, "d_workType")); //工作类型
//        vo.setSale_flag(this.getSaleFlag(vo.getEs_worktype_id())); //发售标识
        vo.setSale_flag(this.getSaleFlag(vo.getEs_worktype_id(),vo.getIc_main_type())); //发售标识
        vo.setWater_no(FormUtil.getParameterIntVal(request, "d_waterNo")); //流水号
        vo.setReason_id(FormUtil.getParameter(request, "d_outReason")); //出库原因
        vo.setCard_money(FormUtil.getParameterIntVal(request, "d_cardMoney")); //面值
        vo.setValid_date(FormUtil.getParameter(request, "d_validDate")); //有效日期
        vo.setLine_id(FormUtil.getParameter(request, "d_lineId")); //线路
        vo.setStation_id(FormUtil.getParameter(request, "d_stationId")); //车站
        vo.setCard_money_produce(FormUtil.getParameterIntVal(request, "d_cardMoneyProduce")); //生产卡的面值
        vo.setMachine_no(FormUtil.getParameter(request, "d_machineNo")); //机器号
        vo.setCard_ava_days(FormUtil.getParameter(request, "d_cardAvaDays")); //乘次票有效天数
        vo.setExit_line_id(FormUtil.getParameter(request, "d_exitLineId")); //乘次票限制出站线路
        vo.setExit_station_id(FormUtil.getParameter(request, "d_exitStationId")); //乘次票限制出站车站
        vo.setModel(FormUtil.getParameter(request, "d_mode")); //乘次票模式
        vo.setTest_flag(FormUtil.getParameter(request, "d_testFlag")); //测试标志
        vo.setOrder_type(FormUtil.getParameter(request, "d_orderType")); //订单类型
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

    protected String getSaleFlag(String esWorkTypeId,String icMainType) {
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

    protected Vector<TicketStorageOutProducePlan> getPlanDetailDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageOutProducePlan> sds = new Vector();
        TicketStorageOutProducePlan sd;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getPlanDetail(strIds, "#");
            sds.add(sd);
        }
        return sds;
    }

    protected Vector<TicketStorageOutProducePlan> getPlanSelectIDs(String selectedIds, HttpServletRequest request) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageOutProducePlan> sds = new Vector();
        TicketStorageOutProducePlan sd;
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

    protected TicketStorageOutProducePlan getPlan(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageOutProducePlan sd = new TicketStorageOutProducePlan();
        ;
        Vector<TicketStorageOutProducePlan> sds = new Vector();
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

    protected TicketStorageOutProducePlan getPlanDetail(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageOutProducePlan sd = new TicketStorageOutProducePlan();
        ;
        Vector<TicketStorageOutProducePlan> sds = new Vector();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                sd.setBill_no(tmp);
                continue;
            }
            if (i == 2) {
                sd.setWater_no(Integer.parseInt(tmp));
                continue;
            }
        }
        return sd;
    }

    protected int modifyPlanDetailByTrans(HttpServletRequest request, TicketStorageOutProduceMapper tsopMapper, TicketStorageOutProducePlan vo, OperationResult opResult) throws Exception {
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
