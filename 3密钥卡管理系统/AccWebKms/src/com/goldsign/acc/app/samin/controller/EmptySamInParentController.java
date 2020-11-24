/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.samin.controller;

import com.goldsign.acc.app.samin.entity.EmptySamIn;
import com.goldsign.acc.app.samin.mapper.EmptySamInMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.SamCardConstant;
import com.goldsign.acc.frame.controller.SamBaseController;
import com.goldsign.acc.frame.mapper.BillMapper;
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
import javax.servlet.http.HttpServletResponse;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 新购卡入库
 *
 * @author luck
 */
public class EmptySamInParentController extends SamBaseController {

    protected int addPlanByTrans(HttpServletRequest request, EmptySamInMapper esiMapper, BillMapper billMapper, EmptySamIn vo, OperationResult opResult) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            this.addVoBillNo(billMapper, vo);
            opResult.setAddPrimaryKey(vo.getOrderNo()); //增加操作记录的主健值
            n = esiMapper.addPlan(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    //判断增加的订单SAM卡类型是否存在
    protected boolean isExitSamTyep(EmptySamIn po, EmptySamInMapper esiMapper) {
        int samType = esiMapper.getSamType(po);
        if (samType <= 0) {
            return false;
        }
        return true;

    }

    //判断增加订单逻辑卡号是否已经在其他订单使用
    protected boolean isExitLogicalNoInOrder(EmptySamIn po, EmptySamInMapper esiMapper) {
        String startLogicNo = po.getStartLogicNo();
        po.seteStartLogicNo(getStLogicNoEle(startLogicNo));
        po.setfStartLogicNo(getStLogicNoFive(startLogicNo));
        int n = esiMapper.isExitLogicalNoInOrder(po);
        if (n > 0) {
            return true;
        }
        return false;
    }

    //判断增加订单逻辑卡号在库存表是否存在 
    protected boolean isExitLogicalNoInStock(EmptySamIn po, EmptySamInMapper esiMapper) {
        String startLogicNo = po.getStartLogicNo();
        po.seteStartLogicNo(startLogicNo.substring(0, 11));
        po.setfStartLogicNo(startLogicNo.substring(startLogicNo.length() - 5));
        int n = esiMapper.isExitLogicalNoInStock(po);
        if (n > 0) {
            return true;
        }
        return false;
    }

    //逻辑卡号段（取起始逻辑卡号后5位+订单数量）是否超出最大值99999
    protected boolean isOverMax(EmptySamIn po) {
        String startLogicNo = po.getStartLogicNo();
        if (Integer.valueOf(getStLogicNoFive(startLogicNo)) + Integer.valueOf(po.getOrderNum()) - 1 > SamCardConstant.MAX_SAM_CARD_LOGIC_NUM) {
            return true;
        }
        return false;
    }
    protected boolean isMin(EmptySamIn po) {
        String startLogicNo = po.getStartLogicNo();
        if (getStLogicNoFive(startLogicNo).equals("0000000")) {
            return true;
        }
        return false;
    }

    protected void addVoBillNo(BillMapper billMappe, EmptySamIn vo) {
        String billNoPlan = this.getBillNoTemp(billMappe, SamCardConstant.ORDER_TYPE_CODE_EMPTY_IN);
        vo.setOrderNo(billNoPlan);
    }

    protected int auditPlanByTrans(HttpServletRequest request, EmptySamInMapper esiMapper, Vector<EmptySamIn> pos) throws Exception {
        TransactionStatus status = null;
        List<String> orderNoList = new ArrayList<>();
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            //更新订单单据状态
            for (EmptySamIn po : pos) {
                orderNoList.add(po.getOrderNo());
                int updateOrderState = esiMapper.updateOrderState(po);
                //取订单详细信息
                if (updateOrderState > 0) {
                    EmptySamIn planInfo = esiMapper.getPlanInfo(po);
                    //按逻辑卡号插入库存表w_ic_sam_stock表
                    String logicNo = planInfo.getStartLogicNo();
                    planInfo.seteStartLogicNo(getStLogicNoEle(logicNo)); //取起始逻辑卡号前11位
                    planInfo.setfStartLogicNo(getStLogicNoFive(logicNo)); //取起始逻辑卡号后5位
                    planInfo.setProduceType(SamCardConstant.PRODUCE_TYPE_EMPTY);
                    planInfo.setStockState(SamCardConstant.STOCK_STATE_EMPTY_IN);
                    planInfo.setIsInstock(SamCardConstant.STOCK_STATE_IN);
                    planInfo.setIsBad(SamCardConstant.SAM_CARD_STATE_OK);
                    for (int j = 0; j < planInfo.getOrderNum(); j++) {
                        planInfo.setNum(j);
                        int addLogicNoIntoStock = esiMapper.addLogicNoIntoStock(planInfo);
                        if (addLogicNoIntoStock == 0) {
                            n = -1;
                            throw new Exception("逻辑卡号" + planInfo.geteStartLogicNo() + (Integer.valueOf(planInfo.getfStartLogicNo()) + j) + "已经存在或对应卡类型不存在");
                        }
                    }
                }
                n += updateOrderState;
            }
            request.setAttribute("orderNoList", orderNoList);
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        } finally {
            return n;
        }
    }

    protected int deletePlanByTrans(HttpServletRequest request, EmptySamInMapper esiMapper, Vector<EmptySamIn> pos) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (EmptySamIn po : pos) {
                po.setOrderState(SamCardConstant.RECORD_FLAG_UNAUDITED);
                n += esiMapper.deletePlan(po);
            }
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected int modifyPlanByTrans(HttpServletRequest request, EmptySamInMapper esiMapper, EmptySamIn vo, OperationResult opResult) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = esiMapper.modifyPlan(vo);
            opResult.setAddPrimaryKey(vo.getOrderNo()); //增加操作记录的主健值
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected EmptySamIn getQueryConditionForOpPlan(HttpServletRequest request, OperationResult opResult) {
        EmptySamIn qCon = new EmptySamIn();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {
            //操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;

            }

            qCon.setOrderNo(opResult.getAddPrimaryKey());
        } else if (command.equals(CommandConstant.COMMAND_AUDIT)) {
           List<String> orderNoList = (List<String>) request.getAttribute("orderNoList");
           qCon.setOrderNolist(orderNoList);
        } else {
            //操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setOrderNo(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_orderNo"));
                qCon.setSamType(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_samType"));
                qCon.setOrderState(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_orderState"));
            }
        }
        return qCon;
    }

    protected EmptySamIn getQueryConditionIn(HttpServletRequest request) {
        EmptySamIn qCon = new EmptySamIn();
        qCon.setOrderNo(FormUtil.getParameter(request, "q_orderNo"));
        qCon.setSamType(FormUtil.getParameter(request, "q_samType"));
        qCon.setOrderState(FormUtil.getParameter(request, "q_orderState"));
        return qCon;
    }

    protected Vector<EmptySamIn> getReqAttributeForPlanAudit(HttpServletRequest request) {
        EmptySamIn po = new EmptySamIn();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<EmptySamIn> selectedItems = this.getPlanSelectIDs(selectIds, request);
        return selectedItems;
    }

    protected Vector<EmptySamIn> getReqAttributeForPlanDelete(HttpServletRequest request) {
        EmptySamIn po = new EmptySamIn();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<EmptySamIn> selectedItems = this.getPlanSelectIDs(selectIds, request);
        //this.getBaseParameters(request, po);
        return selectedItems;
    }

    public EmptySamIn getReqAttributePlan(HttpServletRequest request) {
        EmptySamIn vo = new EmptySamIn();
//        vo.setOrderNo(FormUtil.getParameter(request, "d_orderNo"));
        vo.setSamType(FormUtil.getParameter(request, "d_samType"));
        vo.setGetCardOper(FormUtil.getParameter(request, "d_getCardOper"));
        vo.setOrderNum(FormUtil.getParameterIntVal(request, "d_orderNum"));
        vo.setStartLogicNo(FormUtil.getParameter(request, "d_startLogicNo"));
        vo.setRemark(FormUtil.getParameter(request, "d_remark"));
        vo.setInStockOper(PageControlUtil.getOperatorFromSession(request));
        vo.setOrderState(SamCardConstant.RECORD_FLAG_UNAUDITED);
        return vo;
    }

    protected Vector<EmptySamIn> getPlanSelectIDs(String selectedIds, HttpServletRequest request) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<EmptySamIn> sds = new Vector();
        EmptySamIn sd;
        String operatorId = PageControlUtil.getOperatorFromSession(request);
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getPlan(strIds, "#");
            sd.setAuditOrderOper(operatorId);
            sds.add(sd);
        }
        return sds;
    }

    protected EmptySamIn getPlan(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        EmptySamIn sd = new EmptySamIn();
        ;
        Vector<EmptySamIn> sds = new Vector();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                sd.setOrderNo(tmp);
                continue;
            }
        }
        return sd;
    }

}
