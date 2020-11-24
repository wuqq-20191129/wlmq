/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storagein.controller;

import com.goldsign.acc.app.storagein.entity.TicketStorageInBill;
import com.goldsign.acc.app.storagein.entity.TicketStorageInCleanInBill;
import com.goldsign.acc.app.storagein.mapper.TicketStorageInCleanInBillMapper;
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
 * 清洗入库
 *
 * @author luck
 */
public class TicketStorageInCleanParentController extends TicketStorageInParentController {

    protected String inType = "QR";

    protected int addPlanByTrans(HttpServletRequest request, TicketStorageInCleanInBillMapper tsopMapper, BillMapper billMapper, TicketStorageInCleanInBill vo, OperationResult opResult) throws Exception {
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

    protected boolean isExistInBill(TicketStorageInCleanInBill vo, TicketStorageInCleanInBillMapper tsocMapper) {
        int existBill = tsocMapper.getFlag(vo);
        if (existBill > 0) {
            return false;
        }
        return true;
    }

    protected int addPlanDetailByTrans(HttpServletRequest request, TicketStorageInCleanInBillMapper tsopMapper, TicketStorageInCleanInBill vo, OperationResult opResult) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        int in_num = 0; //差额
        try {
            status = txMgr.getTransaction(this.def);
            //获取出库单号，入库时间
            TicketStorageInCleanInBill inBillInfo = tsopMapper.getInBillInfo(vo);
            vo.setRelated_bill_no(inBillInfo.getRelated_bill_no()); //对应出库单
            vo.setBill_date(inBillInfo.getBill_date()); //制单日期
            //判断对应出库单是否有已审核入库，统计入库数量
            int auditedDetials = tsopMapper.getAuditedDetials(vo);
//            System.out.println("audits====>" + auditedDetials);
            if (auditedDetials > 0) {
                //判断当前明细表
                int detialsFromDetials = tsopMapper.getDetialsFromDetials(vo);
//                System.out.println("detialsFromDetials====>" + detialsFromDetials);
                if (detialsFromDetials > 0) {  //如果当前明细表有，查找当前明细表
                    in_num += tsopMapper.getInNumFromDetial(vo);
//                    System.out.println("in_num1====>" + in_num);
                }
                //从历史明细统计表统计数量
                int detialsFromHistory = tsopMapper.getDetialsFromHistory(vo);
                if (detialsFromHistory > 0) {
                    in_num += tsopMapper.getInNumFromHistory(vo);
                }
            }
            //该出库单有未审核入库的情况，统计入库数量
            int noAuditDetials = tsopMapper.getNoAuditDetials(vo);
            if (noAuditDetials > 0) {
                in_num += tsopMapper.getInNumFromNoAuditDetials(vo);
            }
            //获取对应出库单明细中的出库数量统计，票卡类型，仓库
            TicketStorageInCleanInBill outBillFinfo = tsopMapper.getOutBillFinfo(vo);
            vo.setIcMainType(outBillFinfo.getIcMainType());
            vo.setIcSubType(outBillFinfo.getIcSubType());
            vo.setStorageId(outBillFinfo.getStorageId());
            vo.setOutNum(outBillFinfo.getOutNum());
            System.out.println(vo.getIcMainType() + "-" + vo.getIcSubType() + "-" + vo.getStorageId() + "-" + vo.getOutNum());
            //计算差额
            int in_out_diff = in_num + vo.getValidNum() + vo.getManUselessNum() + vo.getRealBalance() - vo.getOutNum();
//            System.out.println("in_out_diff====>"+in_out_diff);
            //有效票插入
            if (vo.getValidNum() > 0) {
                n += tsopMapper.addPlanDetailByvalid(vo);
            }
            //未完成票插入
            if (vo.getRealBalance() > 0) {
                n += tsopMapper.addPlanDetailByRealBalance(vo);
            }
            // 人工废票插入 
            if (vo.getManUselessNum() > 0) {
                n += tsopMapper.addPlanDetailByManUselessNum(vo);
            }
            // 入出差额表插入
            vo.setOutInDiff(in_out_diff);
            vo.setPreInNum(in_num);
            int addInOutDiff = tsopMapper.addInOutDiff(vo);
            //更改出库单入库标志
//            System.out.println("inFlag====>"+vo.getInFlag());
            if (vo.getInFlag().equals("1")) {
                int updateOutBillInFlag = tsopMapper.updateOutBillInFlagByVo(vo);
            }

            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected boolean checkInflag(TicketStorageInCleanInBill vo, TicketStorageInCleanInBillMapper tsocMapper) {
        TicketStorageInCleanInBill inBillInfo = tsocMapper.getInBillInfo(vo);
        int flag = tsocMapper.getFlag(inBillInfo);
        if (flag > 0) {
            return false;
        }
        return true;
    }

    protected HashMap<String, Object> auditPlanByTrans(HttpServletRequest request, TicketStorageInCleanInBillMapper tsopMapper, TicketStorageInCleanInBill po) throws Exception {
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

    protected int deletePlanByTrans(HttpServletRequest request, TicketStorageInCleanInBillMapper tsopMapper, Vector<TicketStorageInCleanInBill> pos) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (TicketStorageInCleanInBill po : pos) {
                n += tsopMapper.deletePlan(po);
            }
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected boolean isExistingPlanDetial(TicketStorageInCleanInBill po, TicketStorageInCleanInBillMapper tsopMapper) { //判断是否存在明细
        TicketStorageInCleanInBill ticketStorageInCleanInBill = new TicketStorageInCleanInBill();
        ticketStorageInCleanInBill.setBillNo(po.getBill_no());
        int numFromPlanDetial = tsopMapper.getNumFromPlanDetial(ticketStorageInCleanInBill);  //获取明细表主表中是否有数据，没有查询分表
        if (numFromPlanDetial > 0) {
            ticketStorageInCleanInBill.setTable("w_acc_tk.w_ic_in_store_detail");
        } else {
            String tableName = tsopMapper.getTableName(ticketStorageInCleanInBill);
            if (tableName != null && tableName.length() > 0) {
                ticketStorageInCleanInBill.setTable("w_acc_tk." + tableName);
            } else {
                ticketStorageInCleanInBill.setTable("w_acc_tk.w_ic_in_store_detail");
            }
        }
        List<TicketStorageInCleanInBill> list = tsopMapper.queryPlanDetail(ticketStorageInCleanInBill);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }

    protected int deletePlanDetailByTrans(HttpServletRequest request, TicketStorageInCleanInBillMapper tsopMapper, TicketStorageInCleanInBill po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        int m = 0;
        try {
            status = txMgr.getTransaction(this.def);
            //删除明细表
            n = tsopMapper.deletePlanDetail(po);
//            System.out.println("n====>" + n);
            //删除余额
            if (n > 0) {
                m = tsopMapper.deletePlanDiff(po);
//                System.out.println("m====>" + m);
                //获取对应出库单
                String outBillNo = tsopMapper.getRelatedBillNo(po);
                //对应出库单in_flag更新为0
                int updateOutBillInFlag = tsopMapper.updateOutBillInFlag(outBillNo);
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
    protected void addVoBillNo(BillMapper billMappe, TicketStorageInCleanInBill vo) {
        String billNoPlan = this.getBillNoTemp(billMappe, InOutConstant.TYPE_BILL_NO_IN_CLEAN_TEMP);
//        String billNoOut = this.getBillNoTemp(billMappe, InOutConstant.TYPE_BILL_NO_OUT_PRODUCE_TEMP);
        vo.setBill_no(billNoPlan);
//        vo.setOut_bill_no(billNoOut);
    }

    protected void setMapParamsForPlan(HashMap<String, Object> parmMap, TicketStorageInCleanInBill po) {
        parmMap.put("p_bill_no", po.getBill_no());
        parmMap.put("p_operator_id", po.getOperator());
    }

    protected TicketStorageInCleanInBill getQueryConditionForOpPlanDetail(HttpServletRequest request, OperationResult opResult) {
        TicketStorageInCleanInBill qCon = new TicketStorageInCleanInBill();

        qCon.setBillNo(FormUtil.getParameter(request, "queryCondition"));
        qCon.setTable("w_acc_tk.w_ic_in_store_detail");

        return qCon;
    }

    protected TicketStorageInBill getQueryConditionForOpPlan(HttpServletRequest request, OperationResult opResult) {
        TicketStorageInBill qCon = new TicketStorageInBill();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request) || command.equals(CommandConstant.COMMAND_AUDIT)) {
            //操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;

            }
            else if (command.endsWith(CommandConstant.COMMAND_AUDIT)){
                String bill_no = (String) request.getAttribute("bill_no");
                if(bill_no != null){
                    qCon.setBill_no(bill_no);
                    return qCon;
                }
            }

            qCon.setBill_no(opResult.getAddPrimaryKey());
            System.out.println("bill_no ====>"+qCon.getBill_no());
        } else {
            //操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setBill_no(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_billNo"));
                qCon.setBill_date_begin(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_beginTime"));
                qCon.setBill_date_end(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_endTime"));
                qCon.setForm_maker(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_operator"));
                qCon.setIc_main_type(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_cardMainCode"));
                qCon.setIc_sub_type(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_cardSubCode"));
                qCon.setStorage_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_storage"));
                qCon.setRecord_flag(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_recordFlag"));
                qCon.setRelated_bill_no(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_relatedBillno"));
            }
        }
        return qCon;
    }

//    protected TicketStorageInCleanInBill getQueryConditionPlan(HttpServletRequest request) {
//        TicketStorageInCleanInBill qCon = new TicketStorageInCleanInBill();
//        qCon.setBillNo(FormUtil.getParameter(request, "q_billNo"));
//        qCon.setBill_no(FormUtil.getParameter(request, "q_billNo"));
//        qCon.setRelated_bill_no(FormUtil.getParameter(request, "q_relatedBillo"));
//        qCon.setBegin_date(FormUtil.getParameter(request, "q_beginTime"));
//        qCon.setEnd_date(FormUtil.getParameter(request, "q_endTime"));
//        qCon.setStorageId(FormUtil.getParameter(request, "q_storage"));
//        qCon.setIcMainType(FormUtil.getParameter(request, "q_cardMainCode"));
//        qCon.setIcSubType(FormUtil.getParameter(request, "q_cardSubCode"));
//        qCon.setRecord_flag(FormUtil.getParameter(request, "q_recordFlag"));
//        return qCon;
//    }
    protected TicketStorageInBill getQueryConditionIn(HttpServletRequest request) {
        TicketStorageInBill qCon = new TicketStorageInBill();

//        qCon.setIn_type(FormUtil.getParameter(request, "inType"));
        qCon.setIn_type(inType);
        String begin_date = FormUtil.getParameter(request, "q_beginTime");
        String end_date = FormUtil.getParameter(request, "q_endTime");
        if (begin_date != null && !begin_date.equals("")) {
            qCon.setBill_date_begin(begin_date + " 00:00:00");
        }
        if (end_date != null && !end_date.equals("")) {
            qCon.setBill_date_end(end_date + " 23:59:59");
        }

        qCon.setBill_no(request.getParameter("q_billNo"));  //q_form_no
//        qCon.setLendNo1(request.getParameter("q_lend_no")); //没用
//        qCon.setInNo1(request.getParameter("q_in_no"));
//        qCon.setInBillNo1(request.getParameter("q_in_no")); //没用
        qCon.setRecord_flag(request.getParameter("q_recordFlag"));
        qCon.setRelated_bill_no(request.getParameter("q_relatedBillno"));
        qCon.setIc_main_type(request.getParameter("q_cardMainCode"));
        qCon.setIc_sub_type(request.getParameter("q_cardSubCode"));
//        qCon.setReason_id(request.getParameter("q_inReason"));
        qCon.setStorage_id(request.getParameter("q_storage"));
        return qCon;
    }

    protected TicketStorageInCleanInBill getQueryConditionPlanDetail(HttpServletRequest request) {
        TicketStorageInCleanInBill qCon = new TicketStorageInCleanInBill();
        qCon.setBillNo(FormUtil.getParameter(request, "queryCondition"));
        return qCon;
    }

    protected TicketStorageInCleanInBill getQueryConditionPlanDiff(HttpServletRequest request) {
        TicketStorageInCleanInBill qCon = new TicketStorageInCleanInBill();
        qCon.setInBillNo(FormUtil.getParameter(request, "queryCondition_in"));
        qCon.setOutBillNo(FormUtil.getParameter(request, "queryCondition_out"));
        return qCon;
    }

    protected Vector<TicketStorageInCleanInBill> getReqAttributeForPlanAudit(HttpServletRequest request) {
        TicketStorageInCleanInBill po = new TicketStorageInCleanInBill();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageInCleanInBill> selectedItems = this.getPlanSelectIDs(selectIds, request);
        //this.getBaseParameters(request, po);
        return selectedItems;
    }

    protected Vector<TicketStorageInCleanInBill> getReqAttributeForPlanDelete(HttpServletRequest request) {
        TicketStorageInCleanInBill po = new TicketStorageInCleanInBill();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageInCleanInBill> selectedItems = this.getPlanSelectIDs(selectIds, request);
        //this.getBaseParameters(request, po);
        return selectedItems;
    }

    protected Vector<TicketStorageInCleanInBill> getReqAttributeForPlanDetailDelete(HttpServletRequest request) {
        TicketStorageInCleanInBill po = new TicketStorageInCleanInBill();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageInCleanInBill> selectedItems = this.getPlanDetailDeleteIDs(selectIds);

        //this.getBaseParameters(request, po);
        return selectedItems;
    }

    public TicketStorageInCleanInBill getReqAttributePlan(HttpServletRequest request) {
        TicketStorageInCleanInBill vo = new TicketStorageInCleanInBill();
        vo.setRelated_bill_no(FormUtil.getParameter(request, "d_related_bill_no"));
        vo.setHand_man(FormUtil.getParameter(request, "d_hand_man"));
        vo.setAdminister(FormUtil.getParameter(request, "d_administer"));
        vo.setAccounter(FormUtil.getParameter(request, "d_accounter"));
        vo.setRemark(FormUtil.getParameter(request, "d_remarks"));
        vo.setForm_maker(PageControlUtil.getOperatorFromSession(request));
        vo.setRecord_flag(InOutConstant.RECORD_FLAG_BILL_UNAUDIT);
        return vo;
    }

    public TicketStorageInCleanInBill getReqAttributePlanDetail(HttpServletRequest request) throws Exception {
        TicketStorageInCleanInBill vo = new TicketStorageInCleanInBill();
        vo.setBillNo(FormUtil.getParameter(request, "d_billNo")); //计划单号
        vo.setValidNum(FormUtil.getParameterIntVal(request, "d_valid_num")); //有效票
        vo.setManUselessNum(FormUtil.getParameterIntVal(request, "d_man_useless_num")); //废  票
        vo.setRealBalance(FormUtil.getParameterIntVal(request, "d_real_balance"));//未完成
        vo.setInFlag(FormUtil.getParameter(request, "d_inFlag"));//入库标志

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

    protected Vector<TicketStorageInCleanInBill> getPlanDetailDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageInCleanInBill> sds = new Vector();
        TicketStorageInCleanInBill sd;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getPlanDetail(strIds, "#");
            sds.add(sd);
        }
        return sds;
    }

    protected Vector<TicketStorageInCleanInBill> getPlanSelectIDs(String selectedIds, HttpServletRequest request) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageInCleanInBill> sds = new Vector();
        TicketStorageInCleanInBill sd;
        String operatorId = PageControlUtil.getOperatorFromSession(request);
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getPlan(strIds, "#");
            sd.setOperator(operatorId);
            sds.add(sd);
        }
        return sds;
    }

    protected TicketStorageInCleanInBill getPlan(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageInCleanInBill sd = new TicketStorageInCleanInBill();
        ;
        Vector<TicketStorageInCleanInBill> sds = new Vector();
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

    protected TicketStorageInCleanInBill getPlanDetail(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageInCleanInBill sd = new TicketStorageInCleanInBill();
        ;
        Vector<TicketStorageInCleanInBill> sds = new Vector();
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

    protected void handleResult(HashMap<String, Object> resultReturn, OperationResult operationResult) throws Exception {
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
