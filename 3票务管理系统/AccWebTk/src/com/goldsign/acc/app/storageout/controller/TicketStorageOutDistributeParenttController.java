/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storageout.controller;

import com.goldsign.acc.app.storageout.entity.TicketStorageOutDistributeBillDetail;
import com.goldsign.acc.app.storageout.entity.TicketStorageOutDistributeOutBillDetail;
import com.goldsign.acc.app.storageout.entity.TicketStorageOutProduceOrder;
import com.goldsign.acc.app.storageout.entity.TicketStorageOutProduceOutBill;
import com.goldsign.acc.app.storageout.entity.TicketStorageOutDistributePlan;
import com.goldsign.acc.app.storageout.entity.TicketStorageOutStorageInfo;
import com.goldsign.acc.app.storageout.mapper.TicketStorageOutDistributeMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.InOutConstant;
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
import org.springframework.transaction.TransactionStatus;

/**
 * @desc:配票出库
 * @author:zhongzqi
 * @create date: 2017-7-28
 */
public class TicketStorageOutDistributeParenttController extends TicketStorageOutWayController {

    protected int addPlanByTrans(HttpServletRequest request, TicketStorageOutDistributeMapper mapper, BillMapper billMapper, TicketStorageOutDistributePlan vo, OperationResult opResult) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            this.addVoBillNo(billMapper, vo);
            //增加操作记录的主健值
            opResult.setAddPrimaryKey(vo.getBillNo());
            n = mapper.addPlan(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected int addPlanDetailForLineByTrans(HttpServletRequest request, TicketStorageOutDistributeMapper mapper, TicketStorageOutDistributePlan vo, OperationResult opResult) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = mapper.addPlanDetailForLine(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected int addPlanDetailByTrans(HttpServletRequest request, TicketStorageOutDistributeMapper mapper, TicketStorageOutDistributePlan vo, OperationResult opResult) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = mapper.addPlanDetail(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }


    protected HashMap<String, Object> auditPlanByTrans(HttpServletRequest request, TicketStorageOutDistributeMapper mapper, TicketStorageOutDistributePlan po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        HashMap<String, Object> parmMap = new HashMap(10);
        try {
            status = txMgr.getTransaction(this.def);
            this.setMapParamsForPlan(parmMap, po);
            mapper.auditPlan(parmMap);
            txMgr.commit(status);
        } catch (Exception e) {
            e.printStackTrace();
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
            return parmMap;

    }

    protected int deletePlanByTrans(HttpServletRequest request, TicketStorageOutDistributeMapper mapper, Vector<TicketStorageOutDistributePlan> pos) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (TicketStorageOutDistributePlan po : pos) {
                this.checkDetailRecord(po, mapper);
                n += mapper.deletePlan(po);
            }
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected int deletePlanDetailByTrans(HttpServletRequest request, TicketStorageOutDistributeMapper mapper, Vector<TicketStorageOutDistributePlan> pos) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (TicketStorageOutDistributePlan po : pos) {
                n += mapper.deletePlanDetail(po);
            }
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }


    protected void addVoBillNo(BillMapper billMappe, TicketStorageOutDistributePlan vo) {
        String billNoPlan = this.getBillNoTemp(billMappe, InOutConstant.TYPE_BILL_NO_DISTRIBUTE_PLAN_TEMP);
        String billNoOut = this.getBillNoTemp(billMappe, InOutConstant.TYPE_BILL_NO_OUT_DISTRIBUTE_TEMP);
        String billNoDistribute = this.getBillNoTemp(billMappe, InOutConstant.TYPE_BILL_NO_DISTRIBUTE_TEMP);
        vo.setBillNo(billNoPlan);
        vo.setOutBillNo(billNoOut);
        vo.setDistributeBillNo(billNoDistribute);
    }

    protected void setMapParamsForOutBill(HashMap<String, Object> parmMap, TicketStorageOutProduceOutBill po) {
        parmMap.put("piBillNoOut", po.getBill_no());
        parmMap.put("piOperator", po.getOperator());
    }

    protected void setMapParamsForPlan(HashMap<String, Object> parmMap, TicketStorageOutDistributePlan po) {
        parmMap.put("piBillNoPlanTemp", po.getBillNo());
        parmMap.put("piOperator", po.getFormMaker());
    }

    protected TicketStorageOutDistributePlan getQueryConditionForOpPlanDetail(HttpServletRequest request, OperationResult opResult) {
        TicketStorageOutDistributePlan qCon = new TicketStorageOutDistributePlan();

        qCon.setBillNo(FormUtil.getParameter(request, "queryCondition"));

        return qCon;
    }

    protected TicketStorageOutProduceOutBill getQueryConditionForOpOutBill(HttpServletRequest request, OperationResult opResult) {
        TicketStorageOutProduceOutBill qCon = new TicketStorageOutProduceOutBill();

        qCon.setBill_no(FormUtil.getParameter(request, "queryCondition"));

        return qCon;
    }

    protected TicketStorageOutDistributePlan getQueryConditionForOpPlan(HttpServletRequest request, OperationResult opResult) {
        TicketStorageOutDistributePlan qCon = new TicketStorageOutDistributePlan();
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
                qCon.setReasonId(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_outReason"));
                qCon.setStorageId(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_storage"));
                qCon.setRecordFlag(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_planStatu"));
                //根据操作员的仓库权限设置仓库列表
                List storageIdList = getStorageIdListForQueryCondition(request, qCon.getStorageId());
                qCon.setStorageIdList(storageIdList);
            }
        }
        return qCon;
    }

    protected TicketStorageOutProduceOrder getQueryConditionOrder(HttpServletRequest request) {
        TicketStorageOutProduceOrder qCon = new TicketStorageOutProduceOrder();
        qCon.setBill_no(FormUtil.getParameter(request, "queryCondition"));
        return qCon;
    }

    protected TicketStorageOutProduceOutBill getQueryConditionOutBillDetail(HttpServletRequest request) {
        TicketStorageOutProduceOutBill qCon = new TicketStorageOutProduceOutBill();
        qCon.setBill_no(FormUtil.getParameter(request, "queryCondition"));
        return qCon;
    }

    protected TicketStorageOutDistributePlan getQueryConditionPlan(HttpServletRequest request) {
        TicketStorageOutDistributePlan qCon = new TicketStorageOutDistributePlan();
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
        qCon.setReasonId(FormUtil.getParameter(request, "q_outReason"));
        qCon.setStorageId(FormUtil.getParameter(request, "q_storage"));
        //计划单状态
        qCon.setRecordFlag(FormUtil.getParameter(request, "q_planStatu"));
        //根据操作员的仓库权限设置仓库列表
        List storageIdList = getStorageIdListForQueryCondition(request, qCon.getStorageId());
        qCon.setStorageIdList(storageIdList);
        return qCon;
    }

    protected TicketStorageOutDistributePlan getQueryConditionPlanDetail(HttpServletRequest request) {
        TicketStorageOutDistributePlan qCon = new TicketStorageOutDistributePlan();
        qCon.setBillNo(FormUtil.getParameter(request, "queryCondition"));
        return qCon;
    }


    protected Vector<TicketStorageOutDistributePlan> getReqAttributeForPlanAudit(HttpServletRequest request) {
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageOutDistributePlan> selectedItems = this.getPlanSelectIDs(selectIds, request);
        return selectedItems;
    }

    protected Vector<TicketStorageOutDistributePlan> getReqAttributeForPlanDelete(HttpServletRequest request) {
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageOutDistributePlan> selectedItems = this.getPlanSelectIDs(selectIds, request);
        return selectedItems;
    }

    protected Vector<TicketStorageOutDistributePlan> getReqAttributeForPlanDetailDelete(HttpServletRequest request) throws Exception {
    	this.checkAudit(request);
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageOutDistributePlan> selectedItems = this.getPlanDetailDeleteIDs(selectIds);
        return selectedItems;
    }

    public TicketStorageOutDistributePlan getReqAttributePlan(HttpServletRequest request) {
        TicketStorageOutDistributePlan vo = new TicketStorageOutDistributePlan();
        //车票管理员
        vo.setDistributeMan(FormUtil.getParameter(request, "d_distributeMan"));
        //领票人
        vo.setReceiveMan(FormUtil.getParameter(request, "d_receiveMan"));
        vo.setRemark(FormUtil.getParameter(request, "d_remarks"));
        //制单员
        vo.setFormMaker(PageControlUtil.getOperatorFromSession(request));
        vo.setRecordFlag(InOutConstant.RECORD_FLAG_BILL_UNAUDIT);
        return vo;
    }

    public TicketStorageOutDistributePlan getReqAttributePlanDetail(HttpServletRequest request, String op) throws Exception {
        this.checkAudit(request);
    	TicketStorageOutDistributePlan vo = new TicketStorageOutDistributePlan();
        if (CommandConstant.COMMAND_MODIFY.equals(op)) {
            //流水号
            vo.setWaterNo(FormUtil.getParameterIntVal(request, "d_waterNo"));
            if (vo.getWaterNo() == 0) {
                throw new Exception("修改失败，流水号空");
            }
        }//计划单号
        vo.setBillNo(FormUtil.getParameter(request, "d_billNo"));
        //出库原因
        vo.setReasonId(FormUtil.getParameter(request, "d_reasonId"));
        //票卡主类型
        vo.setIcMainType(FormUtil.getParameter(request, "d_icMainType"));
        //票卡子类型
        vo.setIcSubType(FormUtil.getParameter(request, "d_icSubType"));
        //仓库
        vo.setStorageId(FormUtil.getParameter(request, "d_storageId"));
        //票区
        vo.setAreaId(FormUtil.getParameter(request, "d_areaId"));
        if (InOutConstant.AREA_VALUE.equals(vo.getAreaId())) {
            //面值
            vo.setCardMoney(FormUtil.getParameterIntVal(request, "d_cardMoney"));
            //模式
            vo.setModel(FormUtil.getParameter(request, "d_mode"));
        } else {
            vo.setCardMoney(0);
            vo.setModel("000");
        }
        //盒管理区才有有效期
        boolean boxManagerAreaCheck = (InOutConstant.AREA_VALUE.equals(vo.getAreaId()) || (InOutConstant.AREA_ENCODE.equals(vo.getAreaId()) && !InOutConstant.IC_MAIN_TYPE_SJT.equals(vo.getIcMainType())));
        if (boxManagerAreaCheck) {
            //有效日期
            vo.setValidDate(FormUtil.getParameter(request, "d_validDate"));
        } else {
            vo.setValidDate("");
        }

        vo.setLineId("");
        vo.setStationId("");
        vo.setExitLineId("");
        vo.setExitStationId("");
        if (InOutConstant.MODE_LIMIT_ENTRY.equals(vo.getModel()) || InOutConstant.MODE_LIMIT_ENTRY_OUT.equals(vo.getModel())) {
            //进站线路
            vo.setLineId(FormUtil.getParameter(request, "d_lineId"));
            //进站车站
            vo.setStationId(FormUtil.getParameter(request, "d_stationId"));
        }
        if (InOutConstant.MODE_LIMIT_OUT.equals(vo.getModel()) || InOutConstant.MODE_LIMIT_ENTRY_OUT.equals(vo.getModel())) {
            //出站线路
            vo.setExitLineId(FormUtil.getParameter(request, "d_exitLineId"));
            //出站车站
            vo.setExitStationId(FormUtil.getParameter(request, "d_exitStationId"));
        }// 数量
        vo.setDistributeQuantity(FormUtil.getParameterIntVal(request, "d_distributeQuantity"));
        vo.setBoxId(FormUtil.getParameter(request, "d_boxId"));
        vo.setEndBoxId(FormUtil.getParameter(request, "d_endBoxId"));
        vo.setStartLogicalId(FormUtil.getParameter(request, "d_startLogicalId"));
        vo.setEndLogicalId(FormUtil.getParameter(request, "d_endLogicalId"));
        // 配发线路
        vo.setDistributeLineId(FormUtil.getParameter(request, "d_distributeLineId"));
        if("".equals(vo.getDistributeLineId())||vo.getDistributeLineId()==null) {
            //是否限站使用
        	vo.setRestrictFlag("0");
        }else {
            //是否限站使用
        	vo.setRestrictFlag("1");
        }// 配发车站
        vo.setDistributeStationId("");
        vo.setContent(request.getParameter("d_content"));
        if (InOutConstant.DISTRIBUTE_REASON_STATION.equals(vo.getReasonId()) && !CommandConstant.COMMAND_MODIFY.equals(op)) {
            if (vo.getContent() == null || "".equals(vo.getContent())) {
                throw new Exception("配票到线路配票内容未填写");
            }
        }//按逻辑卡号出库时，重新计算出库数量
        if (this.isOutByLogical(vo.getStartLogicalId(), vo.getEndLogicalId())) {
            this.checkAreaAndCardMianType(vo.getAreaId(), vo.getIcMainType());
            TicketStorageOutStorageInfo calculateVo = this.initEntity(vo);
            int outNum = this.calculateNumForLogicalWay(calculateVo);
            vo.setDistributeQuantity(outNum);
        }
        return vo;
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

    protected Vector<TicketStorageOutDistributePlan> getPlanDetailDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageOutDistributePlan> sds = new Vector();
        TicketStorageOutDistributePlan sd;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getPlanDetail(strIds, "#");
            sds.add(sd);
        }
        return sds;
    }

    protected Vector<TicketStorageOutDistributePlan> getPlanSelectIDs(String selectedIds, HttpServletRequest request) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageOutDistributePlan> sds = new Vector();
        TicketStorageOutDistributePlan sd;
        String operatorId = PageControlUtil.getOperatorFromSession(request);
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getPlan(strIds, "#");
            sd.setFormMaker(operatorId);
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

    protected TicketStorageOutDistributePlan getPlan(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageOutDistributePlan sd = new TicketStorageOutDistributePlan();
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

    protected TicketStorageOutDistributePlan getPlanDetail(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageOutDistributePlan sd = new TicketStorageOutDistributePlan();
        ;
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

    protected int modifyPlanDetailByTrans(HttpServletRequest request, TicketStorageOutDistributeMapper mapper, TicketStorageOutDistributePlan vo, OperationResult opResult) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = mapper.modifyPlanDetail(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
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

    private TicketStorageOutStorageInfo initEntity(TicketStorageOutDistributePlan vo) {
        TicketStorageOutStorageInfo po = new TicketStorageOutStorageInfo();
        //A
        po.setAreaId(vo.getAreaId());
        po.setStorageId(vo.getStorageId());
        //B
        po.setIcMainType(vo.getIcMainType());
        po.setIcSubType(vo.getIcSubType());
        po.setCardMoney(vo.getCardMoney());
        po.setValidDate(vo.getValidDate());
        po.setModel(vo.getModel());
        po.setLineId(vo.getLineId());
        po.setStationId(vo.getStationId());
        po.setExitLineId(vo.getExitLineId());
        po.setExitStationId(vo.getExitStationId());
        //非应急票
        po.setFlag("0");

        //C
        po.setStartLogicalId(vo.getStartLogicalId());
        po.setEndLogicalId(vo.getEndLogicalId());

        return po;
    }

    protected TicketStorageOutDistributeOutBillDetail getQueryConditionOutBill(HttpServletRequest request) {
        TicketStorageOutDistributeOutBillDetail qCon = new TicketStorageOutDistributeOutBillDetail();
        qCon.setBillNo(FormUtil.getParameter(request, "queryCondition"));
        return qCon;
    }

    protected TicketStorageOutDistributeBillDetail getQueryConditionDistributeBill(HttpServletRequest request) {
        TicketStorageOutDistributeBillDetail qCon = new TicketStorageOutDistributeBillDetail();
        qCon.setBillNo(FormUtil.getParameter(request, "queryCondition"));
        return qCon;
    }

    private void checkDetailRecord(TicketStorageOutDistributePlan po, TicketStorageOutDistributeMapper mapper) throws Exception {
        String num = mapper.getDetailRecordNum(po);
        if (!InOutConstant.RECORD_NUM_ZREO.equals(num)) {
            throw new Exception("配票计划单:" + po.getBillNo() + " 含明细记录.不能删除");
        }
    }

}
