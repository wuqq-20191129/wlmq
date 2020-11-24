package com.goldsign.acc.app.samout.controller;

import com.goldsign.acc.app.samout.entity.IssueSamOut;
import com.goldsign.acc.app.samout.mapper.IssueSamOutMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.SamCardConstant;
import com.goldsign.acc.frame.controller.SamBaseController;
import com.goldsign.acc.frame.mapper.BillMapper;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.PageControlUtil;
import com.goldsign.acc.frame.util.PubUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import com.goldsign.login.vo.User;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.springframework.transaction.TransactionStatus;

/**
 * 生产单制作
 *
 * @author xiaowu 20170828
 */
public class IssueSamOutParentController extends SamBaseController {

    protected int addPlanByTrans(HttpServletRequest request, IssueSamOutMapper mapper, BillMapper billMapper, IssueSamOut vo,
            OperationResult opResult, List<IssueSamOut> returnViewId) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            this.addVoBillNo(billMapper, vo);
            opResult.setAddPrimaryKey(vo.getOrder_no()); //增加操作记录的主健值
            n = mapper.addPlan(vo);
            txMgr.commit(status);
            returnViewId.add(vo);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected void addVoBillNo(BillMapper billMappe, IssueSamOut vo) {
        String billNoPlan = this.getBillNoTemp(billMappe, SamCardConstant.ORDER_TYPE_CODE_ISSUE_OUT);
        vo.setOrder_no(billNoPlan);
    }

    protected int auditPlanByTrans(HttpServletRequest request, IssueSamOutMapper mapper, Vector<IssueSamOut> pos, List<IssueSamOut> returnViewId) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            //更新订单单据状态
            for (IssueSamOut po : pos) {
                IssueSamOut vo = new IssueSamOut();//取订单详细信息
                String orderNo = po.getOrder_no();
                vo.setOrder_no(orderNo);
                List<IssueSamOut> voList = mapper.queryPlan(vo);//取订单详细信息
                if (voList != null && !voList.isEmpty()) {
                    vo = (IssueSamOut) voList.get(0);
                } else {
                    throw new Exception("单号" + orderNo + "订单详细信息缺省！");
                }

                vo.setOrder_state(SamCardConstant.RECORD_FLAG_AUDITED);
                User user = (User) request.getSession().getAttribute("User");
                String operatorID = user.getAccount();
                vo.setAudit_order_oper(operatorID);
                vo.setOrder_state_text(SamCardConstant.RECORD_FLAG_UNAUDITED);   //临时借用字段
                vo.setStockState(SamCardConstant.STOCK_STATE_PRODUCE_ORDER);
                if (vo.getIs_bad().equals("2")) {
                    vo.setProduceType(SamCardConstant.PRODUCE_TYPE_PRODUCT);
                } else {
                    vo.setProduceType(SamCardConstant.PRODUCE_TYPE_EMPTY);
                }
                vo.setIsInstock(SamCardConstant.STOCK_STATE_IN);

                int result = mapper.auditPlan(vo);

                if (result == 0) {
                    //已经审核不能审核
                    throw new Exception("单号" + orderNo + "当前状态下不能审核或库存不足！");
                }

                //更新逻辑卡号库存状态,更新数量与出库单数据相同，库存满足
                IssueSamOut updateLogicNosTemp = new IssueSamOut();
                updateLogicNosTemp.setOrder_state(SamCardConstant.STOCK_STATE_ISSUE_OUT);   //临时借用字段
                updateLogicNosTemp.setOrder_state_text(SamCardConstant.STOCK_STATE_OUT);     //临时借用字段
                if (vo.getIs_bad().equals("2")) {
                     updateLogicNosTemp.setProduceType(SamCardConstant.PRODUCE_TYPE_PRODUCT);
                } else {
                    updateLogicNosTemp.setProduceType(SamCardConstant.PRODUCE_TYPE_EMPTY);
                }
               
                updateLogicNosTemp.setIsInstock(SamCardConstant.STOCK_STATE_IN);
                updateLogicNosTemp.setStockState(SamCardConstant.STOCK_STATE_PRODUCE_ORDER);
                updateLogicNosTemp.setOrder_no(vo.getPdu_order_no());
                int updateLogicNosResult = mapper.updateLogicNosForPlanOrder(updateLogicNosTemp);
                if (updateLogicNosResult != Integer.valueOf(vo.getOrder_num())) {
                    throw new Exception("单号" + orderNo + "库存不足！");
                }
                n++;
            }
            txMgr.commit(status);
            returnViewId.addAll(pos);
        } catch (Exception e) {
            e.printStackTrace();
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected int deletePlanByTrans(HttpServletRequest request, IssueSamOutMapper mapper, Vector<IssueSamOut> pos) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (IssueSamOut po : pos) {
                po.setOrder_state(SamCardConstant.RECORD_FLAG_UNAUDITED);
                int result = mapper.deletePlan(po);
                if (result == 0) {
                    //已经审核不能删除
                    throw new Exception("单号" + po.getOrder_no() + "当前状态下不能删除");
                }
                n++;
            }
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected int modifyPlanByTrans(HttpServletRequest request, IssueSamOutMapper mapper, IssueSamOut vo, OperationResult opResult, List<IssueSamOut> returnViewId) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            vo.setOrder_state(SamCardConstant.RECORD_FLAG_UNAUDITED);
            n = mapper.modifyPlan(vo);
            if (n == 0) {
                //已经审核不能修改
                throw new Exception("单号" + vo.getOrder_no() + "当前状态下不能修改！");
            }
            txMgr.commit(status);
            returnViewId.add(vo);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected IssueSamOut getQueryConditionForOpPlan(HttpServletRequest request, OperationResult opResult) {
        IssueSamOut qCon = new IssueSamOut();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request) || command.equals(CommandConstant.COMMAND_AUDIT)) {
            //操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;
            }
            qCon.setOrder_no(opResult.getAddPrimaryKey());
        } else {
            //操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setOrder_no(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_order_no"));
                qCon.setSam_type(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_sam_type"));
                qCon.setOrder_state(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_order_state"));
            }
        }
        return qCon;
    }

    protected IssueSamOut getQueryConditionIn(HttpServletRequest request) {
        IssueSamOut qCon = new IssueSamOut();
        qCon.setOrder_no(FormUtil.getParameter(request, "q_order_no"));
        qCon.setSam_type(FormUtil.getParameter(request, "q_sam_type"));
        qCon.setOrder_state(FormUtil.getParameter(request, "q_order_state"));
        qCon.setIs_bad(FormUtil.getParameter(request, "q_is_Bad"));
        return qCon;
    }

    protected Vector<IssueSamOut> getReqAttributeForPlanAudit(HttpServletRequest request) {
        IssueSamOut po = new IssueSamOut();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<IssueSamOut> selectedItems = this.getPlanSelectIDs(selectIds, request);
        return selectedItems;
    }

    protected Vector<IssueSamOut> getReqAttributeForPlanDelete(HttpServletRequest request) {
        IssueSamOut po = new IssueSamOut();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<IssueSamOut> selectedItems = this.getPlanSelectIDs(selectIds, request);
        return selectedItems;
    }

    public IssueSamOut getReqAttributePlan(HttpServletRequest request) {
        IssueSamOut vo = new IssueSamOut();
        vo.setOrder_no(FormUtil.getParameter(request, "d_order_no"));
        vo.setPdu_order_no(FormUtil.getParameter(request, "d_pdu_order_no").trim());
        vo.setGet_card_oper(FormUtil.getParameter(request, "d_get_card_oper"));
        vo.setIs_bad(FormUtil.getParameter(request, "d_isBad"));
        vo.setRemark(FormUtil.getParameter(request, "d_remark"));
        vo.setOut_stock_oper(PageControlUtil.getOperatorFromSession(request));
        return vo;
    }

    protected Vector<IssueSamOut> getPlanSelectIDs(String selectedIds, HttpServletRequest request) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<IssueSamOut> sds = new Vector();
        IssueSamOut sd;
        String operatorId = PageControlUtil.getOperatorFromSession(request);
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getPlan(strIds, "#");
            sd.setAudit_order_oper(operatorId);
            sds.add(sd);
        }
        return sds;
    }

    protected IssueSamOut getPlan(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        IssueSamOut sd = new IssueSamOut();
        Vector<IssueSamOut> sds = new Vector();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                sd.setOrder_no(tmp);
                continue;
            }
        }
        return sd;
    }

}
