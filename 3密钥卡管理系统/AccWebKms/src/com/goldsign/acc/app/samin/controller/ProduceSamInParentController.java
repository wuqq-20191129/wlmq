/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.samin.controller;

import com.goldsign.acc.app.samin.entity.EmptySamIn;
import com.goldsign.acc.app.samin.entity.ProduceSamIn;
import com.goldsign.acc.app.samin.mapper.EmptySamInMapper;
import com.goldsign.acc.app.samin.mapper.ProduceSamInMapper;
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
 * 卡制作入库
 *
 * @author luck
 */
public class ProduceSamInParentController extends SamBaseController {

    protected int addPlanByTrans(HttpServletRequest request, ProduceSamInMapper esiMapper, BillMapper billMapper, ProduceSamIn vo, OperationResult opResult) throws Exception {
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

    //逻辑卡号段（取起始逻辑卡号后5位+订单数量）是否超出最大值99999
    protected boolean isOverMax(EmptySamIn po) {
        String startLogicNo = po.getStartLogicNo();
        if (Integer.valueOf(getStLogicNoFive(startLogicNo)) + Integer.valueOf(po.getOrderNum()) - 1 > SamCardConstant.MAX_SAM_CARD_LOGIC_NUM) {
            return true;
        }
        return false;
    }

    protected void addVoBillNo(BillMapper billMappe, ProduceSamIn vo) {
        String billNoPlan = this.getBillNoTemp(billMappe, SamCardConstant.ORDER_TYPE_CODE_MAKE_IN);
        vo.setOrderNo(billNoPlan);
    }

    protected int auditPlanByTrans(HttpServletRequest request, ProduceSamInMapper esiMapper, ProduceSamIn po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        
        try {
            status = txMgr.getTransaction(this.def);
            //更新订单单据状态
//            for (ProduceSamIn po : pos) {
                
                //取订单详细信息
                List<ProduceSamIn> voList = esiMapper.queryPlan(po);
                ProduceSamIn vo = new ProduceSamIn();
                if (voList != null && !voList.isEmpty()) {
                    vo = (ProduceSamIn) voList.get(0);
                } else {
                    throw new Exception("单号" + po.getOrderNo() + "订单详细信息缺省！");
                }
                String fStartLogicNo = this.getStLogicNoFive(vo.getStartLogicNo());//取起始逻辑卡号后5位
                String eStartLogicNo = this.getStLogicNoEle(vo.getStartLogicNo());//取起始逻辑卡号前11位
                vo.seteStartLogicNo(eStartLogicNo);
                vo.setfStartLogicNo(fStartLogicNo);
                //插入卡制作入库单号和逻辑卡号 到 关联表 w_ic_sam_logic_nos
                List<String> vLogicNos = esiMapper.checkStock(vo);
                if (vLogicNos.size() > 0) {
                    insertLogicNos(vLogicNos, esiMapper, vo);
                } else {
                    throw new Exception("单号" + po.getOrderNo() + "库存不足！");
                }
                //更新制作卡入库订单单据状态 最小逻辑卡号为开始逻辑卡号
                vo.setOrderState(SamCardConstant.RECORD_FLAG_AUDITED);
                vo.setStartLogicNo(vLogicNos.get(0));
                System.out.println(vo.getStartLogicNo());
                vo.setAuditOrderOper(po.getAuditOrderOper());
                int updateOrderState = esiMapper.updateOrderState(vo);
                if (updateOrderState == 0) {
                    //已经审核不能审核
                    n = -2;
                    throw new Exception("单号" + vo.getOrderNo() + "当前状态下不能审核或库存不足！");
                }
                //更新逻辑卡号库存状态,更新数量与入库单数据相同，库存满足
                vo.setLogicNos(vLogicNos);
                vo.setIsInstock(SamCardConstant.STOCK_STATE_IN);
                vo.setStockState(SamCardConstant.STOCK_STATE_PRODUCT_IN);
                int m = esiMapper.updateStock(vo);
                if (m != vo.getOrderNum()) {
                    n = -1;
                    throw new Exception("单号" + vo.getOrderNo() + "库存不足！");
                }
                //更新卡发行出库单 回库状态（条件：卡发行出库单对应的卡逻辑号是否全为入库状态）
                int updateOutBill = esiMapper.updateOutBill(vo);
                System.out.println("updateOutBill====>" + updateOutBill);
                n += updateOrderState;

//            }
           
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        } finally {
            return n;
        }
    }

    private int insertLogicNos(List<String> logicNos, ProduceSamInMapper esiMapper, ProduceSamIn vo) {
        int n = 0;
        if (logicNos.size() < 1) {
            return n;
        }
        String stLogicNo = logicNos.get(0);//开始逻辑卡号
        String enLogicNo = logicNos.get(0);//结束逻辑卡号
        String tmpLogicNo = null;//临时逻辑卡号
        for (int i = 0; i < logicNos.size(); i++) {
            if (i + 1 == logicNos.size()) {
                vo.setEndLogicNo(enLogicNo);
                vo.setStartLogicNo(stLogicNo);
                n = esiMapper.insertLogicNo(vo);
            } else {
                tmpLogicNo = logicNos.get(i + 1);
                int tfLogicNo = Integer.valueOf(getStLogicNoFive(tmpLogicNo));//取逻辑卡号后5位
                String teLogicNo = getStLogicNoEle(tmpLogicNo);//取逻辑卡号前11位
                int efLogicNo = Integer.valueOf(getStLogicNoFive(enLogicNo));//取逻辑卡号后5位
                String eeLogicNo = getStLogicNoEle(enLogicNo);//取逻辑卡号前11位
                if (efLogicNo + 1 == tfLogicNo && teLogicNo.equals(eeLogicNo)) {
                    enLogicNo = tmpLogicNo;
                } else {
                    vo.setStartLogicNo(stLogicNo);
                    vo.setEndLogicNo(enLogicNo);
                    n = esiMapper.insertLogicNo(vo);
                    stLogicNo = tmpLogicNo;//开始逻辑卡号
                    enLogicNo = tmpLogicNo;//结束逻辑卡号
                }
            }

        }
        return n;
    }

    protected int deletePlanByTrans(HttpServletRequest request, ProduceSamInMapper esiMapper, Vector<ProduceSamIn> pos) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (ProduceSamIn po : pos) {
                po.setOrderState(SamCardConstant.RECORD_FLAG_UNAUDITED);
                n += esiMapper.deletePlan(po);
            }
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected int modifyPlanByTrans(HttpServletRequest request, ProduceSamInMapper esiMapper, ProduceSamIn vo, OperationResult opResult) throws Exception {
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

    protected ProduceSamIn getQueryConditionForOpPlan(HttpServletRequest request, OperationResult opResult) {
        ProduceSamIn qCon = new ProduceSamIn();
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

    protected ProduceSamIn getQueryConditionIn(HttpServletRequest request) {
        ProduceSamIn qCon = new ProduceSamIn();
        qCon.setOrderNo(FormUtil.getParameter(request, "q_orderNo"));
        qCon.setSamType(FormUtil.getParameter(request, "q_samType"));
        qCon.setOrderState(FormUtil.getParameter(request, "q_orderState"));
        return qCon;
    }

    protected Vector<ProduceSamIn> getReqAttributeForPlanAudit(HttpServletRequest request) {
        ProduceSamIn po = new ProduceSamIn();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<ProduceSamIn> selectedItems = this.getPlanSelectIDs(selectIds, request);
        return selectedItems;
    }

    protected Vector<ProduceSamIn> getReqAttributeForPlanDelete(HttpServletRequest request) {
        ProduceSamIn po = new ProduceSamIn();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<ProduceSamIn> selectedItems = this.getPlanSelectIDs(selectIds, request);
        //this.getBaseParameters(request, po);
        return selectedItems;
    }

    public ProduceSamIn getReqAttributePlan(HttpServletRequest request) {
        ProduceSamIn vo = new ProduceSamIn();
        vo.setOrderNo(FormUtil.getParameter(request, "d_orderNo"));
        vo.setIssueOrderNo(FormUtil.getParameter(request, "d_issueOrderNo"));
        vo.setGetCardOper(FormUtil.getParameter(request, "d_getCardOper"));
        vo.setRemark(FormUtil.getParameter(request, "d_remark"));
        vo.setInStockOper(PageControlUtil.getOperatorFromSession(request));
        vo.setOrderState(SamCardConstant.RECORD_FLAG_UNAUDITED);
        return vo;
    }

    protected Vector<ProduceSamIn> getPlanSelectIDs(String selectedIds, HttpServletRequest request) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<ProduceSamIn> sds = new Vector();
        ProduceSamIn sd;
        String operatorId = PageControlUtil.getOperatorFromSession(request);
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getPlan(strIds, "#");
            sd.setAuditOrderOper(operatorId);
            sds.add(sd);
        }
        return sds;
    }

    protected ProduceSamIn getPlan(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        ProduceSamIn sd = new ProduceSamIn();
        ;
        Vector<ProduceSamIn> sds = new Vector();
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
