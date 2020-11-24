/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storageout.controller;

import com.goldsign.acc.app.storageout.entity.TicketStorageOutBorrowOutBillDetail1;
import com.goldsign.acc.app.storageout.entity.TicketStorageOutBorrowPlan;
import com.goldsign.acc.app.storageout.entity.TicketStorageOutDistributePlan;
import com.goldsign.acc.app.storageout.entity.TicketStorageOutStorageInfo;
import com.goldsign.acc.app.storageout.mapper.TicketStorageOutBorrowMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.InOutConstant;
import com.goldsign.acc.frame.mapper.BillMapper;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.PageControlUtil;
import com.goldsign.acc.frame.util.PubUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.springframework.transaction.TransactionStatus;

/**
 * @desc:借票出库
 * @author:zhongzqi
 * @create date: 2017-8-15
 */
public class TicketStorageOutBorrowParentController extends TicketStorageOutWayController {

    public TicketStorageOutBorrowPlan getReqAttributePlan(HttpServletRequest request) {
        TicketStorageOutBorrowPlan vo = new TicketStorageOutBorrowPlan();
        //制单员
        vo.setFormMaker(PageControlUtil.getOperatorFromSession(request));
        //车票管理员
        vo.setDistributeMan(FormUtil.getParameter(request, "d_distributeMan"));
        //领票人
        vo.setReceiveMan(FormUtil.getParameter(request, "d_receiveMan"));
        vo.setRemark(FormUtil.getParameter(request, "d_remarks"));
        vo.setUnitId(FormUtil.getParameter(request, "d_unitId"));
        vo.setReturnFlag(InOutConstant.RETURN_FLAG_NOT);
        Date d = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        vo.setBillDate(f.format(d));
        vo.setRecordFlag(InOutConstant.RECORD_FLAG_BILL_UNAUDIT);
        return vo;
    }

    protected int addPlanByTrans(HttpServletRequest request, TicketStorageOutBorrowMapper mapper, BillMapper billMapper, TicketStorageOutBorrowPlan vo, OperationResult opResult) throws Exception {
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

    protected void addVoBillNo(BillMapper billMappe, TicketStorageOutBorrowPlan vo) {
        String billNoPlan = this.getBillNoTemp(billMappe, InOutConstant.TYPE_BILL_NO_BORROW_TEMP);
        String billNoOut = this.getBillNoTemp(billMappe, InOutConstant.TYPE_BILL_NO_OUT_BORROW_TEMP);
        vo.setBillNo(billNoPlan);
        vo.setOutBillNo(billNoOut);
    }

    protected TicketStorageOutBorrowPlan getQueryConditionPlan(HttpServletRequest request) {
        TicketStorageOutBorrowPlan qCon = new TicketStorageOutBorrowPlan();
        qCon.setBillNo(FormUtil.getParameter(request, "q_billNo"));
        qCon.setReceiveMan(FormUtil.getParameter(request, "q_operator"));
        String beginDateTime = FormUtil.getParameter(request, "q_beginTime");
        String endDateTime = FormUtil.getParameter(request, "q_endTime");
        if (!"".equals(beginDateTime)) {
            qCon.setBeginDateTime(beginDateTime + " 00:00:00");
        }
        if (!"".equals(endDateTime)) {
            qCon.setEndDateTime(endDateTime + " 23:59:59");
        }
        qCon.setStorageId(FormUtil.getParameter(request, "q_storage"));
        qCon.setIcMainType(FormUtil.getParameter(request, "q_cardMainCode"));
        qCon.setIcSubType(FormUtil.getParameter(request, "q_cardSubCode"));
        qCon.setUnitId(FormUtil.getParameter(request, "q_unitId"));
        //计划单状态
        qCon.setRecordFlag(FormUtil.getParameter(request, "q_recordFlag"));
        //根据操作员的仓库权限设置仓库列表
        List storageIdList = getStorageIdListForQueryCondition(request, qCon.getStorageId());
        qCon.setStorageIdList(storageIdList);
        return qCon;
    }

    protected TicketStorageOutBorrowPlan getQueryConditionForOpPlan(HttpServletRequest request, OperationResult opResult) {
        TicketStorageOutBorrowPlan qCon = new TicketStorageOutBorrowPlan();
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
        }else if(CommandConstant.COMMAND_AUDIT.equals(command)){
            qCon.setBillNo((String) request.getAttribute("q_billNo"));
            //根据操作员的仓库权限设置仓库列表
            List storageIdList = getStorageIdListForQueryCondition(request, qCon.getStorageId());
            qCon.setStorageIdList(storageIdList);
        }
        else {
            //操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setBillNo(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_billNo"));
                qCon.setReceiveMan(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_operator"));
                String beginDateTime = FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_beginTime");
                String endDateTime = FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_endTime");
                if (!"".equals(beginDateTime)) {
                    qCon.setBeginDateTime(beginDateTime + " 00:00:00");
                }
                if (!"".equals(endDateTime)) {
                    qCon.setEndDateTime(endDateTime + " 23:59:59");
                }
                qCon.setUnitId(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_unitId"));
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

    protected Vector<TicketStorageOutBorrowPlan> getReqAttributeForPlanDelete(HttpServletRequest request) {
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageOutBorrowPlan> selectedItems = this.getPlanSelectIDs(selectIds, request);
        return selectedItems;
    }

    protected int deletePlanByTrans(HttpServletRequest request, TicketStorageOutBorrowMapper mapper, Vector<TicketStorageOutBorrowPlan> pos) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (TicketStorageOutBorrowPlan po : pos) {
                this.checkDetailRecord(po, mapper);
                n += mapper.deletePlan(po);
            }
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    private Vector<TicketStorageOutBorrowPlan> getPlanSelectIDs(String selectedIds, HttpServletRequest request) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageOutBorrowPlan> sds = new Vector();
        TicketStorageOutBorrowPlan sd;
        String operatorId = PageControlUtil.getOperatorFromSession(request);
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getPlan(strIds, "#");
            sd.setFormMaker(operatorId);
            sds.add(sd);
        }
        return sds;
    }

    private TicketStorageOutBorrowPlan getPlan(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageOutBorrowPlan sd = new TicketStorageOutBorrowPlan();
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

    private void checkDetailRecord(TicketStorageOutBorrowPlan po, TicketStorageOutBorrowMapper mapper) throws Exception {
        String num = mapper.getDetailRecordNum(po);
        if (!InOutConstant.RECORD_NUM_ZREO.equals(num)) {
            throw new Exception("借票计划单:" + po.getBillNo() + " 含明细记录.不能删除");
        }
    }

    protected TicketStorageOutBorrowPlan getQueryConditionPlanDetail(HttpServletRequest request) {
        TicketStorageOutBorrowPlan qCon = new TicketStorageOutBorrowPlan();
        qCon.setBillNo(FormUtil.getParameter(request, "queryCondition"));
        return qCon;
    }

    public TicketStorageOutBorrowPlan getReqAttributePlanDetail(HttpServletRequest request, String op) throws Exception {
        TicketStorageOutBorrowPlan vo = new TicketStorageOutBorrowPlan();
        this.checkAudit(request);
        if (CommandConstant.COMMAND_MODIFY.equals(op)) {
            //流水号
            vo.setWaterNo(FormUtil.getParameterIntVal(request, "d_waterNo"));
            vo.setOperType("modify");
            if (vo.getWaterNo() == 0) {
                throw new Exception("修改失败，流水号空");
            }
        }
        //计划单号
        vo.setBillNo(FormUtil.getParameter(request, "d_billNo"));
        //票卡主类型
        vo.setIcMainType(FormUtil.getParameter(request, "d_icMainType"));
        //票卡子类型
        vo.setIcSubType(FormUtil.getParameter(request, "d_icSubType"));
        //仓库
        vo.setStorageId(FormUtil.getParameter(request, "d_storageId"));
        //票区
        vo.setAreaId(FormUtil.getParameter(request, "d_areaId"));
        vo.setReasonId(InOutConstant.REASON_ID_BORROW);
        if (InOutConstant.AREA_VALUE.equals(vo.getAreaId())) {
            //面值
            vo.setCardMoney(FormUtil.getParameterIntVal(request, "d_cardMoney"));
        } else {
            vo.setCardMoney(0);
        }
        //盒管理区才有有效期
        boolean boxManagerAreaCheck = (InOutConstant.AREA_VALUE.equals(vo.getAreaId()) || (InOutConstant.AREA_ENCODE.equals(vo.getAreaId()) && !InOutConstant.IC_CARD_TYPE_SJT.equals(vo.getIcMainType())));
        if (boxManagerAreaCheck) {
            //有效日期
            vo.setValidDate(FormUtil.getParameter(request, "d_validDate"));
            vo.setModel(FormUtil.getParameter(request, "d_mode"));
        } else {
            vo.setValidDate("");
            vo.setModel("000");
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
            vo.setExitLineId(FormUtil.getParameter(request, "d_exitLineId"));
            vo.setExitStationId(FormUtil.getParameter(request, "d_exitStationId"));
        }
        vo.setLendQuantity(FormUtil.getParameterIntVal(request, "d_lendQuantity"));
        vo.setStartLogicalId(FormUtil.getParameter(request, "d_startLogicalId"));
        vo.setEndLogicalId(FormUtil.getParameter(request, "d_endLogicalId"));
        //按数量出库 数量不能为0且没有输入逻辑卡号
        boolean outByNumCheck = (vo.getLendQuantity() == 0 && ("".equals(vo.getStartLogicalId()) || "".equals(vo.getEndLogicalId())));
        if (outByNumCheck) {
            throw new Exception("借票数量不能为空");
        }//按逻辑卡号出库时，重新计算出库数量
        if (this.isOutByLogical(vo.getStartLogicalId(), vo.getEndLogicalId())) {
            this.checkAreaAndCardMianType(vo.getAreaId(), vo.getIcMainType());
            TicketStorageOutStorageInfo calculateVo = this.initEntity(vo);
            int outNum = this.calculateNumForLogicalWay(calculateVo);
            vo.setLendQuantity(outNum);
        }
        vo.setRemark(FormUtil.getParameter(request, "d_remark"));

        return vo;
    }

    protected int addPlanDetailByTrans(HttpServletRequest request, TicketStorageOutBorrowMapper mapper, TicketStorageOutBorrowPlan vo, OperationResult opResult) throws Exception {
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

    protected TicketStorageOutBorrowPlan getQueryConditionForOpPlanDetail(HttpServletRequest request, OperationResult opResult) {
        TicketStorageOutBorrowPlan qCon = new TicketStorageOutBorrowPlan();
        qCon.setBillNo(FormUtil.getParameter(request, "queryCondition"));
        return qCon;
    }

    protected Vector<TicketStorageOutBorrowPlan> getReqAttributeForPlanDetailDelete(HttpServletRequest request) throws Exception {
        this.checkAudit(request);
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageOutBorrowPlan> selectedItems = this.getPlanDetailDeleteIDs(selectIds);
        return selectedItems;
    }

    private Vector<TicketStorageOutBorrowPlan> getPlanDetailDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageOutBorrowPlan> sds = new Vector();
        TicketStorageOutBorrowPlan sd;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getPlanDetail(strIds, "#");
            sds.add(sd);
        }
        return sds;
    }

    private TicketStorageOutBorrowPlan getPlanDetail(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageOutBorrowPlan sd = new TicketStorageOutBorrowPlan();
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

    protected int deletePlanDetailByTrans(HttpServletRequest request, TicketStorageOutBorrowMapper mapper, Vector<TicketStorageOutBorrowPlan> pos) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (TicketStorageOutBorrowPlan po : pos) {
                n += mapper.deletePlanDetail(po);
            }
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected int modifyPlanDetailByTrans(HttpServletRequest request, TicketStorageOutBorrowMapper mapper, TicketStorageOutBorrowPlan vo, OperationResult opResult) throws Exception {
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

    protected HashMap<String, Object> auditPlanByTrans(HttpServletRequest request, TicketStorageOutBorrowMapper mapper, TicketStorageOutBorrowPlan po) throws Exception {
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

    private void setMapParamsForPlan(HashMap<String, Object> parmMap, TicketStorageOutBorrowPlan po) {
        parmMap.put("piBillNoPlanTemp", po.getBillNo());
        parmMap.put("piOperator", po.getFormMaker());
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

    protected TicketStorageOutBorrowOutBillDetail1 getQueryConditionOutBill(HttpServletRequest request) {
        TicketStorageOutBorrowOutBillDetail1 qCon = new TicketStorageOutBorrowOutBillDetail1();
        qCon.setBillNo(FormUtil.getParameter(request, "queryCondition"));
        return qCon;
    }

    private TicketStorageOutStorageInfo initEntity(TicketStorageOutBorrowPlan vo) {
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

}
