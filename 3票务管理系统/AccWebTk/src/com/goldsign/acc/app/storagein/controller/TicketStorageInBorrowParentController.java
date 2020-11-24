/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storagein.controller;

import com.goldsign.acc.app.storagein.entity.TicketStorageInBill;
import com.goldsign.acc.app.storagein.entity.TicketStorageInBorrowBill;
import com.goldsign.acc.app.storagein.entity.TicketStorageInBorrowBillDetail;
import com.goldsign.acc.app.storagein.entity.TicketStorageInBorrowInDetail;
import com.goldsign.acc.app.storagein.mapper.TicketStorageInBorrowMapper;
import com.goldsign.acc.app.storageout.controller.TicketStorageOutWayController;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.controller.StorageOutInBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.PageControlUtil;
import com.goldsign.acc.frame.util.PubUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;

/**
 * @desc:
 * @author:zhongzqi
 * @create date: 2017-9-6
 */
@Controller
public class TicketStorageInBorrowParentController extends StorageOutInBaseController {

    private String inType = "GH";   //借票归还

    protected TicketStorageInBorrowBill getQueryConditionIn(HttpServletRequest request) {
        TicketStorageInBorrowBill qCon = new TicketStorageInBorrowBill();
        qCon.setBill_no(request.getParameter("q_billNo"));//归还单
        qCon.setIn_bill_no(request.getParameter("q_inBillNo"));
        qCon.setLend_bill_no(request.getParameter("q_lendBillNo"));
        qCon.setStorage_id(request.getParameter("q_storage"));
        String beginDateTime = FormUtil.getParameter(request, "q_beginTime");
        String endDateTime = FormUtil.getParameter(request, "q_endTime");
        if (!"".equals(beginDateTime)) {
            qCon.setBill_date_begin(beginDateTime + " 00:00:00");
        }
        if (!"".equals(endDateTime)) {
            qCon.setBill_date_end(endDateTime + " 23:59:59");
        }
//        qCon.setBill_date_begin(FormUtil.getParameter(request, "q_beginTime"));
//        qCon.setBill_date_end(FormUtil.getParameter(request, "q_endTime"));
        qCon.setIc_main_type(request.getParameter("q_cardMainCode"));
        qCon.setIc_sub_type(request.getParameter("q_cardSubCode"));
        qCon.setRecord_flag(request.getParameter("q_recordFlag"));
        List storageIdList = getStorageIdListForQueryCondition(request, qCon.getStorage_id()); //根据操作员的仓库权限设置仓库列表
        qCon.setStorageIdList(storageIdList);
        return qCon;
    }

    protected TicketStorageInBorrowBill getQueryConditionForOpPlan(HttpServletRequest request, OperationResult opResult) {
        TicketStorageInBorrowBill qCon = new TicketStorageInBorrowBill();
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
        }else if(CommandConstant.COMMAND_AUDIT.equals(command)){
            qCon.setBill_no((String) request.getAttribute("q_billNo"));
            List storageIdList = getStorageIdListForQueryCondition(request, qCon.getStorage_id()); //根据操作员的仓库权限设置仓库列表
            qCon.setStorageIdList(storageIdList);
        } else {
            //操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setBill_no(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_billNo"));
                qCon.setLend_bill_no(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_lendBillNo"));
                qCon.setIn_bill_no(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_inBillNo"));
                qCon.setStorage_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_storage"));
                String beginDateTime = FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_beginTime");
                String endDateTime = FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_endTime");
                if (!"".equals(beginDateTime)) {
                    qCon.setBill_date_begin(beginDateTime + " 00:00:00");
                }
                if (!"".equals(endDateTime)) {
                    qCon.setBill_date_end(endDateTime + " 23:59:59");
                }
                qCon.setIc_main_type(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_cardMainCode"));
                qCon.setIc_sub_type(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_cardSubCode"));
                qCon.setRecord_flag(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_recordFlag"));
                List storageIdList = getStorageIdListForQueryCondition(request, qCon.getStorage_id()); //根据操作员的仓库权限设置仓库列表
                qCon.setStorageIdList(storageIdList);
            }
        }
        return qCon;
    }

    protected TicketStorageInBorrowBill getReqAttributeForAdd(HttpServletRequest request) {
        TicketStorageInBorrowBill vo = new TicketStorageInBorrowBill();
        //页面传入 新增信息
        vo.setIn_type(inType);
        vo.setLend_bill_no(request.getParameter("d_lend_bill_no"));
        vo.setReturn_man(request.getParameter("d_return_man"));
        vo.setReceive_man(request.getParameter("d_receive_man"));
        vo.setRemark(request.getParameter("d_remark"));
        return vo;
    }

    protected HashMap<String, Object> addInBillByTrans(HttpServletRequest request, TicketStorageInBorrowMapper mapper, TicketStorageInBorrowBill po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        HashMap<String, Object> parmMap = new HashMap();
        try {
            status = txMgr.getTransaction(this.def);
            this.setMapParamsForAddInBill(parmMap, po);
            mapper.addInBill(parmMap);
            txMgr.commit(status);
        } catch (Exception e) {
            e.printStackTrace();
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return parmMap;

    }

    protected void setMapParamsForAddInBill(HashMap<String, Object> parmMap, TicketStorageInBorrowBill po) {
        parmMap.put("pi_lend_bill_no", po.getLend_bill_no());
        parmMap.put("pi_in_type", po.getIn_type());
        parmMap.put("pi_receive_man", po.getReceive_man());
        parmMap.put("pi_return_man", po.getReturn_man());
        parmMap.put("pi_remark", po.getRemark());
    }

    protected String handleResultForAdd(HashMap<String, Object> resultReturn, TicketStorageInBill po) throws Exception {
        if (resultReturn == null || resultReturn.isEmpty()) {
            throw new Exception("执行存储过程没有返回结果！");
        }
        String billNo = (String) resultReturn.get("po_bill_no");
        String retMsg = (String) resultReturn.get("po_errmsg");
        int retCode = (Integer) resultReturn.get("po_result");
        String memo = (String) resultReturn.get("po_memo");
        //入库单临时单据号
        po.setBill_no(billNo);

        if (retCode == 0) {
            return memo;
        } else {
            retMsg = "添加出错，信息请查看操作日志";
        }
        throw new Exception(retMsg);
    }

    protected Vector<TicketStorageInBorrowBill> getReqAttributeForDelete(HttpServletRequest request) {
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageInBorrowBill> selectedItems = this.getInBillSelectIDs(selectIds, request);
        return selectedItems;
    }

    protected Vector<TicketStorageInBorrowBill> getInBillSelectIDs(String selectedIds, HttpServletRequest request) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageInBorrowBill> ibs = new Vector();
        TicketStorageInBorrowBill ib;
        String operatorId = PageControlUtil.getOperatorFromSession(request);
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            ib = this.getInBill(strIds, "#");
            ib.setOperator(operatorId);
            ibs.add(ib);
        }
        return ibs;
    }

    protected TicketStorageInBorrowBill getInBill(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageInBorrowBill ib = new TicketStorageInBorrowBill();
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

    protected void checkDetailRecord(TicketStorageInBorrowBill po, TicketStorageInBorrowMapper mapper) throws Exception {
        String num = mapper.getDetailRecordNum(po);
        if (!"0".equals(num)) {
            throw new Exception("借票归还单:" + po.getBill_no() + " 含明细记录.不能删除");
        }
    }

    protected TicketStorageInBorrowBillDetail getQueryConditionPlanDetail(HttpServletRequest request) {
        TicketStorageInBorrowBillDetail qCon = new TicketStorageInBorrowBillDetail();
        qCon.setBill_no(FormUtil.getParameter(request, "queryCondition"));
        qCon.setRecord_flag(FormUtil.getParameter(request, "billRecordFlag"));

        return qCon;
    }

    protected int deleteInBillByTrans(HttpServletRequest request, TicketStorageInBorrowMapper mapper, Vector<TicketStorageInBorrowBill> pos) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (TicketStorageInBorrowBill po : pos) {
                this.checkDetailRecord(po, mapper);
                int result = 0;
                //更新出库单归还标志
                result = mapper.updateReturnFlag(po.getBill_no());
                if (result <= 0) {
                    throw new Exception("更新出库单归还标志失败，单号:" + po.getBill_no());
                }
                //更新入库单删除标志
                result = mapper.updateDeleteFlag(po);
                if (result <= 0) {
                    throw new Exception("删除归还单失败，单号:" + po.getBill_no());
                }
                //统计删除记录数
                n = n + result;
            }
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected TicketStorageInBorrowBillDetail getReqAttributePlanDetail(HttpServletRequest request) throws Exception {
    	this.checkAudit(request);
    	TicketStorageInBorrowBillDetail qCon = new TicketStorageInBorrowBillDetail();
        qCon.setBill_no(FormUtil.getParameter(request, "queryCondition"));
        qCon.setWater_no(FormUtil.getParameterIntVal(request, "d_waterNo"));
        qCon.setLend_water_no(FormUtil.getParameterIntVal(request, "d_lendWaterNo"));
        qCon.setIc_main_type(FormUtil.getParameter(request, "d_icMainType"));
        qCon.setIc_sub_type(FormUtil.getParameter(request, "d_icSubType"));
        qCon.setStorage_id(FormUtil.getParameter(request, "d_storageId"));
        qCon.setArea_id(FormUtil.getParameter(request, "d_areaId"));
        qCon.setReturn_quantity(FormUtil.getParameterIntVal(request, "d_returnQuantity"));
        qCon.setNot_quantity(FormUtil.getParameterIntVal(request, "d_notQuantity"));
        qCon.setLost_quantity(FormUtil.getParameterIntVal(request, "d_lostQuantity"));
        qCon.setStart_logical_id(FormUtil.getParameter(request, "d_startLogicalId"));
        qCon.setEnd_logical_id(FormUtil.getParameter(request, "d_endLogicalId"));
        return qCon;
    }

    protected boolean isValidForData(TicketStorageInBorrowMapper mapper, TicketStorageInBorrowBillDetail vo) throws Exception {
        boolean flag = false;
        HashMap<String, Object> parmMap = new HashMap();
        parmMap = getMaxRetrunNumByTrans(mapper, vo);
        if (parmMap == null || parmMap.isEmpty()) {
            throw new Exception("执行存储过程没有返回结果！");
        }
        int leftNum = (Integer) parmMap.get("po_result");
        if (leftNum < vo.getReturn_quantity() + vo.getNot_quantity() + vo.getLost_quantity()) {
            return flag;
        } else {
            flag = true;
            return flag;
        }
    }

    protected HashMap<String, Object> getMaxRetrunNumByTrans(TicketStorageInBorrowMapper mapper, TicketStorageInBorrowBillDetail po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        HashMap<String, Object> parmMap = new HashMap();
        try {
            status = txMgr.getTransaction(this.def);
            this.setMapParamsForValidDate(parmMap, po);
            mapper.getMaxReturnNum(parmMap);
            txMgr.commit(status);
        } catch (Exception e) {
            e.printStackTrace();
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
            return parmMap;

    }

    protected void setMapParamsForValidDate(HashMap<String, Object> parmMap, TicketStorageInBorrowBillDetail po) {
        parmMap.put("pi_waterNo", po.getWater_no());
    }

    protected int modifyPlanDetailByTrans(HttpServletRequest request, TicketStorageInBorrowMapper mapper, TicketStorageInBorrowBillDetail vo, OperationResult rmsg) throws Exception {
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

    protected int modifyPlanDetailForIdByTrans(HttpServletRequest request, TicketStorageInBorrowMapper mapper, TicketStorageInBorrowBillDetail vo, OperationResult rmsg) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = mapper.modifyPlanDetailForId(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected int insertNewEndSectionForDetailByTrans(HttpServletRequest request, TicketStorageInBorrowMapper mapper, TicketStorageInBorrowBillDetail vo, OperationResult rmsg) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = mapper.insertNewEndSectionForDetail(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    TransactionStatus status = null;

    protected int insertNewStartSectionForDetailByTrans(HttpServletRequest request, TicketStorageInBorrowMapper mapper, TicketStorageInBorrowBillDetail vo, OperationResult rmsg) throws Exception {
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = mapper.insertNewStartSectionForDetail(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected TicketStorageInBorrowBillDetail getQueryConditionForOpPlanDetail(HttpServletRequest request, OperationResult opResult) {
        TicketStorageInBorrowBillDetail qCon = new TicketStorageInBorrowBillDetail();
        qCon.setBill_no(FormUtil.getParameter(request, "queryCondition"));
        qCon.setRecord_flag(FormUtil.getParameter(request, "billRecordFlag"));
        return qCon;
    }

    protected int modifyDetailByTrans(HttpServletRequest request, TicketStorageInBorrowMapper mapper, TicketStorageInBorrowBillDetail vo, OperationResult rmsg) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            //1归还加上 不归还数量和遗失数量的判断
            if (!this.isValidForData(mapper, vo)) {
                n = -6;
                throw new Exception("修改明细出错，数量超出剩余借出数量!");
            }
            //2校验输入数量和逻辑卡号段是否匹配
            if (!"".equals(vo.getStart_logical_id()) && !"".equals(vo.getEnd_logical_id())) {
                n = TicketStorageOutWayController.calculateSection(vo.getStart_logical_id(), vo.getEnd_logical_id());
                if (n != (vo.getReturn_quantity() + vo.getNot_quantity() + vo.getLost_quantity())) {
                    n = -14;
                    throw new Exception("修改明细出错，输入数量与逻辑卡号段对应数量不一致!");
                }
            }
            //3数量不能输入 负数
            if (vo.getReturn_quantity() < 0 || vo.getNot_quantity() < 0 || vo.getLost_quantity() < 0) {
                n = -18;
                throw new Exception("修改明细出错，归还数量或不归还数量或遗失数量不能为负数!");
            }
            List<TicketStorageInBorrowBillDetail> list = mapper.getCurrentData(vo);
            TicketStorageInBorrowBillDetail dataVo = new TicketStorageInBorrowBillDetail();
            if (list != null && !list.isEmpty()) {
                dataVo = list.get(0);
            }
            if (dataVo.getStart_logical_id() == null) {
                dataVo.setStart_logical_id("");
            }
            if (dataVo.getEnd_logical_id() == null) {
                dataVo.setEnd_logical_id("");
            }
            //4判断有车票ID时，修改后没有车票ID时，不能作修改
            if (!"".equals(dataVo.getStart_logical_id()) && !"".equals(dataVo.getEnd_logical_id())
                    && ("".equals(vo.getStart_logical_id()) || "".equals(vo.getEnd_logical_id()))) {
                n = -20;
                throw new Exception("修改明细出错，原车票ID不为空时,修改后的车票ID也不能为空!");
            }
            //5判断无车票ID时，修改后有车票ID时，不能作修改
            if (("".equals(dataVo.getStart_logical_id()) || "".equals(dataVo.getEnd_logical_id()))
                    && !"".equals(vo.getStart_logical_id()) && !"".equals(vo.getEnd_logical_id())) {
                n = -21;
                throw new Exception("修改明细出错，原车票ID为空时,修改后的车票ID也为空!");
            }
            //6校验输入数量和逻辑卡号段是否匹配 拆分增加记录开始
            //6.1 判断录入逻辑卡号位数且开始逻辑卡号或结束逻辑卡号不能库中
            if (isValidCard(vo.getStart_logical_id()) && isValidCard(vo.getEnd_logical_id())
                    && (!dataVo.getStart_logical_id().equals(vo.getStart_logical_id()))
                    || !dataVo.getEnd_logical_id().equals(vo.getEnd_logical_id())) {
                //6.2校验有ID时，三个数量只有一个大于0
                int numTotal = 0;
                if (0 != vo.getReturn_quantity()) {
                    numTotal += 1;
                }
                if (0 != vo.getNot_quantity()) {
                    numTotal += 1;
                }
                if (0 != vo.getLost_quantity()) {
                    numTotal += 1;
                }
                if (numTotal > 1) {
                    n = -19;
                    throw new Exception("修改明细出错，车票ID不为空时,归还数量或不归还数量或遗失数量只能有一个大于0!");
                }
                BigInteger newSid = new BigInteger(vo.getStart_logical_id());
                BigInteger newEid = new BigInteger(vo.getEnd_logical_id());
                BigInteger oldSid = null;
                BigInteger oldEid = null;
                //6.3修改的卡号不在原先的卡号段里
                if (isValidCard(dataVo.getStart_logical_id()) && isValidCard(dataVo.getEnd_logical_id())) {
                    oldSid = new BigInteger(dataVo.getStart_logical_id());
                    oldEid = new BigInteger(dataVo.getEnd_logical_id());
                    if (newSid.intValue() < oldSid.intValue() || newSid.intValue() > oldEid.intValue()
                            || newEid.intValue() < oldSid.intValue() || newEid.intValue() > oldEid.intValue()) {
                        n = -17;
                        throw new Exception("修改明细出错，输入逻辑卡号段不在原先的范围之内!");
                    }
                }
                String newStartId = null;
                String newEndId = null;
                //6.4 卡号拆分 四种情况
                //6.4.1库中本身无逻辑卡号
                if ("".equals(dataVo.getStart_logical_id()) || "".equals(dataVo.getEnd_logical_id())) {

                }//6.4.2开始不变 结束改变
                else if (newSid.intValue() == oldSid.intValue() && newEid.intValue() != oldEid.intValue()) {
                    int num = oldEid.intValue() - newEid.intValue();
                    newStartId = PubUtil.addLogicalIdEffective(vo.getEnd_logical_id(), "1");
                    TicketStorageInBorrowBillDetail newStartVo = new TicketStorageInBorrowBillDetail();
                    newStartVo.setWater_no(vo.getWater_no());
                    newStartVo.setReturn_quantity(num);
                    newStartVo.setSum_quantity(num);
                    newStartVo.setStart_logical_id(newStartId);
                    mapper.insertNewStartSectionForDetail(newStartVo);
                }//6.4.3开始卡号、结束卡号改变
                else if (newSid.intValue() != oldSid.intValue() && newEid.intValue() != oldEid.intValue()) {
                    int num = newSid.intValue() - oldSid.intValue();
                    newEndId = PubUtil.addLogicalIdEffective(vo.getStart_logical_id(), "-1");
                    TicketStorageInBorrowBillDetail newEndVo = new TicketStorageInBorrowBillDetail();
                    newEndVo.setWater_no(vo.getWater_no());
                    newEndVo.setReturn_quantity(num);
                    newEndVo.setSum_quantity(num);
                    newEndVo.setEnd_logical_id(newEndId);
                    n = mapper.insertNewEndSectionForDetail(newEndVo);
                    num = oldEid.intValue() - newEid.intValue();
                    newStartId = PubUtil.addLogicalIdEffective(vo.getEnd_logical_id(), "1");
                    TicketStorageInBorrowBillDetail newStartVo = new TicketStorageInBorrowBillDetail();
                    newStartVo.setWater_no(vo.getWater_no());
                    newStartVo.setReturn_quantity(num);
                    newStartVo.setSum_quantity(num);
                    newStartVo.setStart_logical_id(newStartId);
                    n = mapper.insertNewStartSectionForDetail(newStartVo);
                }//6.4.4开始变 结束不变
                else if (newSid.intValue() != oldSid.intValue() && newEid.intValue() == oldEid.intValue()) {
                    int num = newSid.intValue() - oldSid.intValue();
                    newEndId = PubUtil.addLogicalIdEffective(vo.getStart_logical_id(), "-1");
                    TicketStorageInBorrowBillDetail newEndVo = new TicketStorageInBorrowBillDetail();
                    newEndVo.setWater_no(vo.getWater_no());
                    newEndVo.setReturn_quantity(num);
                    newEndVo.setSum_quantity(num);
                    newEndVo.setEnd_logical_id(newEndId);
                    n = mapper.insertNewEndSectionForDetail(newEndVo);
                }
            }
            //7卡号段有拆分的情况，更新记录总数量为归还数量
            if (isValidCard(vo.getStart_logical_id()) && isValidCard(vo.getEnd_logical_id())
                    && (!vo.getStart_logical_id().equals(dataVo.getStart_logical_id())
                    || !vo.getEnd_logical_id().equals(dataVo.getEnd_logical_id()))) {
                n = mapper.modifyPlanDetailForId(vo);
            } else {
                n = mapper.modifyPlanDetail(vo);
            }
            //8更新归还标志
            int lendNum = mapper.getLendQuantity(vo);
            String lendBillNo = mapper.getLendBillNo(vo);
            int returnNum = mapper.getReturnQuantity(lendBillNo);
            if (returnNum >= lendNum) {
                n = mapper.updateReturnFlagTrue(lendBillNo);
            } else {
                n = mapper.updateReturnFlagFalse(lendBillNo);
            }
            txMgr.commit(status);

        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected Vector<TicketStorageInBorrowBillDetail> getReqAttributeForPlanDetailDelete(HttpServletRequest request) throws Exception {
    	this.checkAudit(request);
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageInBorrowBillDetail> selectedItems = this.getPlanDetailDeleteIDs(selectIds);
        return selectedItems;
    }

    protected int deletePlanDetailByTrans(HttpServletRequest request, TicketStorageInBorrowMapper mapper, Vector<TicketStorageInBorrowBillDetail> pos) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (TicketStorageInBorrowBillDetail po : pos) {
                int result = 0;
                result += mapper.deletePlanDetail(po);
                if (result == 0) {
                    throw new Exception("删除借票明细失败,流水号：" + po.getWater_no());
                }
                n += result;
                int returnNum = mapper.getReturnNum(po);
                if (returnNum > 0) {
                    result = mapper.updateReturnFlag(po.getBill_no());
                    if (result == 0) {
                        throw new Exception("更新出库单归还标志失败，单号：" + po.getBill_no());
                    }
                }
            }
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;

    }

    private Vector<TicketStorageInBorrowBillDetail> getPlanDetailDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageInBorrowBillDetail> sds = new Vector();
        TicketStorageInBorrowBillDetail sd;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getPlanDetail(strIds, "#");
            sds.add(sd);
        }
        return sds;
    }

    private TicketStorageInBorrowBillDetail getPlanDetail(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageInBorrowBillDetail sd = new TicketStorageInBorrowBillDetail();
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

    protected HashMap<String, Object> auditInBorrowBillByTrans(HttpServletRequest request, TicketStorageInBorrowMapper mapper, TicketStorageInBill po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        HashMap<String, Object> parmMap = new HashMap();
        try {
            status = txMgr.getTransaction(this.def);
            this.setMapParamsForAuditInBorrowBill(parmMap, po);
            if (parmMap.get("pi_related_bill_no").toString().length() > 12) {
                parmMap.put("po_result", "-19");
                return parmMap;
            }
            mapper.auditInBorrowBill(parmMap);
            txMgr.commit(status);
        } catch (Exception e) {
            e.printStackTrace();
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
            return parmMap;

    }

    protected String handleResultForAudit(HashMap<String, Object> resultReturn,HttpServletRequest request) throws Exception {
        if (resultReturn == null || resultReturn.isEmpty()) {
            throw new Exception("执行存储过程没有返回结果！");
        }
        String retMsg = "";
        String memo = (String) resultReturn.get("po_memo");
        int retCode = (Integer) resultReturn.get("po_result");
        String relatedBillNo = (String) resultReturn.get("pi_related_bill_no");
        request.setAttribute("q_billNo",relatedBillNo);
        String errMsg = (String) resultReturn.get("po_errmsg");
        String errMsgPre = "无法审核，";
        String errMsgAll = errMsgPre + errMsg;
        if (retCode == 0) {
            return memo;
        }
        if (retCode == -1) {
            retMsg = relatedBillNo + "修改数据出错，详情请查询操作日志!";
        } else if (retCode == -17) {
            retMsg = relatedBillNo + errMsgAll;
        } else if (retCode == -18) {
            retMsg = relatedBillNo + errMsgAll;
        } else if (retCode == -3) {
            retMsg = relatedBillNo + errMsgAll;
        } else if (retCode == -31) {
            retMsg = relatedBillNo + errMsgAll;
        } else if (retCode < 0) {
            retMsg = relatedBillNo + "审核,操作失败!";
        }
        throw new Exception(retMsg);
    }

    protected Vector<TicketStorageInBorrowBill> getReqAttributeForInBillAudit(HttpServletRequest request) {
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageInBorrowBill> selectedItems = this.getInBillSelectIDs(selectIds, request);
        return selectedItems;
    }

    protected void setMapParamsForAuditInBorrowBill(HashMap<String, Object> parmMap, TicketStorageInBill po) {
        parmMap.put("pi_type", "JR");
        parmMap.put("pi_related_bill_no", po.getBill_no());
        parmMap.put("pi_operator_id", po.getOperator()); //??检查取值
    }

    protected TicketStorageInBorrowInDetail getQueryConditionInBill(HttpServletRequest request) {
        TicketStorageInBorrowInDetail qCon = new TicketStorageInBorrowInDetail();
        qCon.setBill_no(FormUtil.getParameter(request, "queryCondition"));
        qCon.setRecord_flag(FormUtil.getParameter(request, "billRecordFlag"));
        request.setAttribute("recordFlag", qCon.getRecord_flag());
        return qCon;
    }

    protected HashMap<String, Object> auditInBillByTrans(HttpServletRequest request, TicketStorageInBorrowMapper mapper) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        HashMap<String, Object> parmMap = new HashMap();
        try {
            status = txMgr.getTransaction(this.def);
            this.setMapParamsForAuditInBill(parmMap, request);
            mapper.auditInBill(parmMap);
            txMgr.commit(status);
        } catch (Exception e) {
            e.printStackTrace();
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return parmMap;
    }

    protected void setMapParamsForAuditInBill(HashMap<String, Object> parmMap, HttpServletRequest request) {
        String inBillNo = FormUtil.getParameter(request, "queryCondition");
        String operatorId = PageControlUtil.getOperatorFromSession(request);
        parmMap.put("pi_bill_no", inBillNo);
        parmMap.put("pi_operator_id", operatorId); //??检查取值
    }


    protected TicketStorageInBorrowInDetail getQueryConditionInBillForOp(HttpServletRequest request) {
        TicketStorageInBorrowInDetail qCon = new TicketStorageInBorrowInDetail();
        String formalNo = (String) request.getAttribute("formalNo");
        if ("".equals(formalNo) || formalNo == null) {
            qCon.setBill_no(FormUtil.getParameter(request, "queryCondition"));
            qCon.setRecord_flag(FormUtil.getParameter(request, "billRecordFlag"));
        } else {
            qCon.setBill_no(formalNo);
            qCon.setRecord_flag("0");
        }
        return qCon;
    }

    protected String handleResultForAuditIn(HashMap<String, Object> resultReturn, HttpServletRequest request) throws Exception {
        if (resultReturn == null || resultReturn.isEmpty()) {
            throw new Exception("执行存储过程没有返回结果！");
        }
        String retMsg = "";
        String memo = (String) resultReturn.get("po_memo");
        int retCode = (Integer) resultReturn.get("po_result");
        String relatedBillNo = (String) resultReturn.get("po_bill_no");
        String errMsg = (String) resultReturn.get("po_errmsg");
        String errMsgPre = "无法审核，";
        String errMsgAll = errMsgPre + errMsg;
        request.setAttribute("recordFlag", "3");
        if (retCode == 1) {
            request.setAttribute("formalNo", relatedBillNo);
            request.setAttribute("recordFlag", "0");
            return memo;
        }
        if (retCode == -1) {
            retMsg = relatedBillNo + "修改数据出错，详情请查询操作日志!";
        } else if (retCode == -2) {
            retMsg = relatedBillNo + errMsgAll;
        } else if (retCode == -3) {
            retMsg = relatedBillNo + errMsgAll;
        } else if (retCode == -30) {
            retMsg = relatedBillNo + errMsgAll;
        } else if (retCode < 0) {
            retMsg = relatedBillNo + "审核,操作失败!";
        }
        throw new Exception(retMsg);
    }
    public  void checkAudit(HttpServletRequest request) throws Exception {
	   	 String recodeFlag = FormUtil.getParameter(request, "billRecordFlag");
	   	 if("0".equals(recodeFlag)) {
	   		 throw new Exception("明细单已审核，无法操作");
	   	 }
    }
}
