/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storageout.controller;

import com.goldsign.acc.app.storageout.entity.TicketStorageOutCleanOutBill;
import com.goldsign.acc.app.storageout.mapper.TicketStorageOutCleanMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.InOutConstant;
import com.goldsign.acc.frame.controller.StorageOutInBaseController;
import com.goldsign.acc.frame.mapper.BillMapper;
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
 * 清洗出库
 *
 * @author luck
 */
public class TicketStorageOutCleanParentController extends StorageOutInBaseController {

    protected int addPlanByTrans(HttpServletRequest request, TicketStorageOutCleanMapper tsopMapper, BillMapper billMapper, TicketStorageOutCleanOutBill vo, OperationResult opResult) throws Exception {
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

    protected int addPlanDetailByTrans(HttpServletRequest request, TicketStorageOutCleanMapper tsopMapper, TicketStorageOutCleanOutBill vo, OperationResult opResult) throws Exception {
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

    protected boolean checkMulStorage(TicketStorageOutCleanOutBill po, TicketStorageOutCleanMapper tsocMapper) { //判断是否多个仓库
        TicketStorageOutCleanOutBill ticketStorageOutCleanOutBill = new TicketStorageOutCleanOutBill();
        ticketStorageOutCleanOutBill.setBill_no(po.getBill_no());
        ticketStorageOutCleanOutBill.setWater_no(po.getWater_no());
        ticketStorageOutCleanOutBill.setFlag(po.getFlag());
        String storage_id = tsocMapper.getDistinctStorage(ticketStorageOutCleanOutBill);
        if (storage_id == null || storage_id.isEmpty()) {
            return false;
        } else {
            if (storage_id.equals(po.getStorage_id())) {
                return false;
            }
        }
        return true;
    }

    protected HashMap<String, Object> auditPlanByTrans(HttpServletRequest request, TicketStorageOutCleanMapper tsopMapper, TicketStorageOutCleanOutBill po) throws Exception {
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

    protected int deletePlanByTrans(HttpServletRequest request, TicketStorageOutCleanMapper tsopMapper, Vector<TicketStorageOutCleanOutBill> pos) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (TicketStorageOutCleanOutBill po : pos) {
                n += tsopMapper.deletePlan(po);
            }
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected boolean isExistingPlanDetial(TicketStorageOutCleanOutBill po, TicketStorageOutCleanMapper tsocMapper) { //判断是否存在明细
        TicketStorageOutCleanOutBill ticketStorageOutCleanOutBill = new TicketStorageOutCleanOutBill();
        ticketStorageOutCleanOutBill.setBill_no(po.getBill_no());
        List<TicketStorageOutCleanOutBill> list = tsocMapper.queryPlanDetail(ticketStorageOutCleanOutBill);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }

    protected int deletePlanDetailByTrans(HttpServletRequest request, TicketStorageOutCleanMapper tsopMapper, Vector<TicketStorageOutCleanOutBill> pos) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (TicketStorageOutCleanOutBill po : pos) {
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
    protected void addVoBillNo(BillMapper billMappe, TicketStorageOutCleanOutBill vo) {
        String billNoPlan = this.getBillNoTemp(billMappe, InOutConstant.TYPE_BILL_NO_OUT_CLEAN_TEMP);
//        String billNoOut = this.getBillNoTemp(billMappe, InOutConstant.TYPE_BILL_NO_OUT_PRODUCE_TEMP);
        vo.setBill_no(billNoPlan);
//        vo.setOut_bill_no(billNoOut);
    }

    protected void setMapParamsForPlan(HashMap<String, Object> parmMap, TicketStorageOutCleanOutBill po) {
        parmMap.put("piBillNoPlanTemp", po.getBill_no());
        parmMap.put("piOperator", po.getOperator());
    }

    protected TicketStorageOutCleanOutBill getQueryConditionForOpPlanDetail(HttpServletRequest request, OperationResult opResult) {
        TicketStorageOutCleanOutBill qCon = new TicketStorageOutCleanOutBill();

        qCon.setBill_no(FormUtil.getParameter(request, "queryCondition"));

        return qCon;
    }

    protected TicketStorageOutCleanOutBill getQueryConditionForOpPlan(HttpServletRequest request, OperationResult opResult) {
        TicketStorageOutCleanOutBill qCon = new TicketStorageOutCleanOutBill();
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
                    qCon.setBill_no(bill_no);
                    return qCon;
                }
            }
            qCon.setBill_no(opResult.getAddPrimaryKey());
        } else {
            //操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setBill_no(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_billNo"));
                
                String begin_date = FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_beginTime");
                String end_date = FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_endTime");
                if (begin_date != null && !begin_date.trim().equals("")) {
                    qCon.setBegin_date(begin_date + " 00:00:00");
                }
                if (end_date != null && !end_date.trim().equals("")) {
                    qCon.setEnd_date(end_date + " 23:59:59");
                }
               
                qCon.setForm_maker(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_operator"));
                qCon.setIc_main_type(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_cardMainCode"));
                qCon.setIc_sub_type(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_cardSubCode"));
                qCon.setStorage_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_storage"));
                qCon.setRecord_flag(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_recordFlag"));
            }
        }
        return qCon;
    }

    protected TicketStorageOutCleanOutBill getQueryConditionPlan(HttpServletRequest request) {
        TicketStorageOutCleanOutBill qCon = new TicketStorageOutCleanOutBill();
        qCon.setBill_no(FormUtil.getParameter(request, "q_billNo"));
        qCon.setForm_maker(FormUtil.getParameter(request, "q_operator"));

        String begin_date = FormUtil.getParameter(request, "q_beginTime");
        String end_date = FormUtil.getParameter(request, "q_endTime");
        if (begin_date != null && !begin_date.trim().equals("")) {
            qCon.setBegin_date(begin_date + " 00:00:00");
        }
        if (end_date != null && !end_date.trim().equals("")) {
            qCon.setEnd_date(end_date + " 23:59:59");
        }
        qCon.setStorage_id(FormUtil.getParameter(request, "q_storage"));
        qCon.setIc_main_type(FormUtil.getParameter(request, "q_cardMainCode"));
        qCon.setIc_sub_type(FormUtil.getParameter(request, "q_cardSubCode"));
        qCon.setRecord_flag(FormUtil.getParameter(request, "q_recordFlag"));
        qCon.setOperator(PageControlUtil.getOperatorFromSession(request));
        return qCon;
    }

    protected TicketStorageOutCleanOutBill getQueryConditionPlanDetail(HttpServletRequest request) {
        TicketStorageOutCleanOutBill qCon = new TicketStorageOutCleanOutBill();
        qCon.setBill_no(FormUtil.getParameter(request, "queryCondition"));
        return qCon;
    }

    protected Vector<TicketStorageOutCleanOutBill> getReqAttributeForPlanAudit(HttpServletRequest request) {
        TicketStorageOutCleanOutBill po = new TicketStorageOutCleanOutBill();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageOutCleanOutBill> selectedItems = this.getPlanSelectIDs(selectIds, request);
        //this.getBaseParameters(request, po);
        return selectedItems;
    }

    protected Vector<TicketStorageOutCleanOutBill> getReqAttributeForPlanDelete(HttpServletRequest request) {
        TicketStorageOutCleanOutBill po = new TicketStorageOutCleanOutBill();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageOutCleanOutBill> selectedItems = this.getPlanSelectIDs(selectIds, request);
        //this.getBaseParameters(request, po);
        return selectedItems;
    }

    protected Vector<TicketStorageOutCleanOutBill> getReqAttributeForPlanDetailDelete(HttpServletRequest request) {
        TicketStorageOutCleanOutBill po = new TicketStorageOutCleanOutBill();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageOutCleanOutBill> selectedItems = this.getPlanDetailDeleteIDs(selectIds);
        //this.getBaseParameters(request, po);
        return selectedItems;
    }

    public TicketStorageOutCleanOutBill getReqAttributePlan(HttpServletRequest request) {
        TicketStorageOutCleanOutBill vo = new TicketStorageOutCleanOutBill();
        vo.setDrawer(FormUtil.getParameter(request, "d_drawer"));
        vo.setAdminister(FormUtil.getParameter(request, "d_administer"));
        vo.setAccounter(FormUtil.getParameter(request, "d_accounter"));
        vo.setRemark(FormUtil.getParameter(request, "d_remarks"));
        vo.setForm_maker(PageControlUtil.getOperatorFromSession(request));
        vo.setRecord_flag(InOutConstant.RECORD_FLAG_BILL_UNAUDIT);
        return vo;
    }

    public TicketStorageOutCleanOutBill getReqAttributePlanDetail(HttpServletRequest request) throws Exception {
        TicketStorageOutCleanOutBill vo = new TicketStorageOutCleanOutBill();
        vo.setBill_no(FormUtil.getParameter(request, "d_billNo")); //计划单号
        vo.setIc_main_type(FormUtil.getParameter(request, "d_cardMainType")); //票卡主类型
        vo.setIc_sub_type(FormUtil.getParameter(request, "d_cardSubType")); //票卡子类型
        vo.setOut_num(FormUtil.getParameterIntVal(request, "d_amount"));//出库数量
        vo.setStorage_id(FormUtil.getParameter(request, "d_storage")); //仓库
        vo.setArea_id(FormUtil.getParameter(request, "d_zone")); //票区       
        vo.setWater_no(FormUtil.getParameterIntVal(request, "d_waterNo")); //流水号
        vo.setReason_id("08"); //出库原因

        vo.setCard_money(FormUtil.getParameterIntVal(request, "d_cardMoney")); //面值
        vo.setValid_date(FormUtil.getParameter(request, "d_validDate")); //有效日期
        vo.setLine_id(FormUtil.getParameter(request, "d_lineId")); //线路
        vo.setStation_id(FormUtil.getParameter(request, "d_stationId")); //车站
        vo.setExit_line_id(FormUtil.getParameter(request, "d_exitLineId")); //乘次票限制出站线路
        vo.setExit_station_id(FormUtil.getParameter(request, "d_exitStationId")); //乘次票限制出站车站
        vo.setModel(FormUtil.getParameter(request, "d_mode")); //乘次票模式

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

    protected Vector<TicketStorageOutCleanOutBill> getPlanDetailDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageOutCleanOutBill> sds = new Vector();
        TicketStorageOutCleanOutBill sd;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getPlanDetail(strIds, "#");
            sds.add(sd);
        }
        return sds;
    }

    protected Vector<TicketStorageOutCleanOutBill> getPlanSelectIDs(String selectedIds, HttpServletRequest request) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageOutCleanOutBill> sds = new Vector();
        TicketStorageOutCleanOutBill sd;
        String operatorId = PageControlUtil.getOperatorFromSession(request);
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getPlan(strIds, "#");
            sd.setOperator(operatorId);
            sds.add(sd);
        }
        return sds;
    }

    protected TicketStorageOutCleanOutBill getPlan(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageOutCleanOutBill sd = new TicketStorageOutCleanOutBill();
        ;
        Vector<TicketStorageOutCleanOutBill> sds = new Vector();
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

    protected TicketStorageOutCleanOutBill getPlanDetail(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageOutCleanOutBill sd = new TicketStorageOutCleanOutBill();
        ;
        Vector<TicketStorageOutCleanOutBill> sds = new Vector();
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

    protected int modifyPlanDetailByTrans(HttpServletRequest request, TicketStorageOutCleanMapper tsopMapper, TicketStorageOutCleanOutBill vo, OperationResult opResult) throws Exception {
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
