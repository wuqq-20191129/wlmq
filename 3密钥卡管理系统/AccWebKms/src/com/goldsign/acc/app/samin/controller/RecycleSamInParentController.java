/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.samin.controller;

import com.goldsign.acc.app.samin.entity.EmptySamIn;
import com.goldsign.acc.app.samin.entity.RecycleSamIn;
import com.goldsign.acc.app.samin.mapper.EmptySamInMapper;
import com.goldsign.acc.app.samin.mapper.RecycleSamInMapper;
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
 * 卡回收入库
 *
 * @author luck
 */
public class RecycleSamInParentController extends SamBaseController {

    public static int m;
    public static String oldFirstStartLogicNo;
    
    protected int addPlanByTrans(HttpServletRequest request, RecycleSamInMapper esiMapper, BillMapper billMapper, RecycleSamIn vo, OperationResult opResult) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            this.addVoBillNo(billMapper, vo);
            opResult.setAddPrimaryKey(vo.getOrderNo()); //增加操作记录的主健值
            String startLogicNo = vo.getStartLogicNo();
            vo.seteStartLogicNo(getStLogicNoEle(startLogicNo)); //取起始逻辑卡号前11位
            vo.setfStartLogicNo(getStLogicNoFive(startLogicNo)); //取起始逻辑卡号后5位
            n = esiMapper.addPlan(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    //检验对应逻辑卡号库存是否足够
    protected List<String> checkStock(RecycleSamIn po, RecycleSamInMapper esiMapper) {
        String startLogicNo = po.getStartLogicNo();
        po.seteStartLogicNo(getStLogicNoEle(startLogicNo));
        po.setfStartLogicNo(getStLogicNoFive(startLogicNo));
        List<String> logicNos = esiMapper.checkStock(po);
        return logicNos;
    }

    protected void addVoBillNo(BillMapper billMappe, RecycleSamIn vo) {
        String billNoPlan = this.getBillNoTemp(billMappe, SamCardConstant.ORDER_TYPE_CODE_RECYCLE_IN);
        vo.setOrderNo(billNoPlan);
    }

    protected int auditPlanByTrans(HttpServletRequest request, RecycleSamInMapper esiMapper, RecycleSamIn po) throws Exception {
        TransactionStatus status = null;
      
        int n = 0;
        try {
            m = 0;
            status = txMgr.getTransaction(this.def);
            //审核订单条件：未审核订单对应 Sam卡类型, 产品类型为产品卡
            //库存状态卡分发状态，出库的 逻辑卡号大于起始逻辑卡号库存数据大于订单出库数量
//            for (RecycleSamIn po : pos) {
               
                //取订单详细信息
                List<RecycleSamIn> voList = esiMapper.queryPlan(po);
                RecycleSamIn vo = new RecycleSamIn();
                if (voList != null && !voList.isEmpty()) {
                    vo = (RecycleSamIn) voList.get(0);
                } else {
                    throw new Exception("单号" + po.getOrderNo() + "订单详细信息缺省！");
                }
                oldFirstStartLogicNo = vo.getStartLogicNo();
                
                String fStartLogicNo = this.getStLogicNoFive(vo.getStartLogicNo());//取起始逻辑卡号后5位
                String eStartLogicNo = this.getStLogicNoEle(vo.getStartLogicNo());//取起始逻辑卡号前11位
                vo.seteStartLogicNo(eStartLogicNo);
                vo.setfStartLogicNo(fStartLogicNo);
                //检验对应逻辑卡号库存是否足够(逻辑卡号段内、产品卡、出库)              
                vo.setStockState(SamCardConstant.STOCK_STATE_DISTR_OUT);
                vo.setIsInstock(SamCardConstant.STOCK_STATE_OUT);
                vo.setProduceType(SamCardConstant.PRODUCE_TYPE_PRODUCT);
                List<String> vLogicNos = esiMapper.checkStock(vo);
                //插入 单号与逻辑卡号段到 ic_sam_logic_nos 表
                int insertLogicNosNum = insertLogicNos(vLogicNos, esiMapper, vo);
                System.out.println("insertLogicNosNum" + insertLogicNosNum);
                //更新订单单据状态，订单起始逻辑卡号

                if(!oldFirstStartLogicNo.equals(vLogicNos.get(0))){                
                    m = 1;
                }
                vo.setOrderState(SamCardConstant.RECORD_FLAG_AUDITED);
                vo.setStartLogicNo(vLogicNos.get(0));
                System.out.println(vo.getStartLogicNo());
                vo.setAuditOrderOper(po.getAuditOrderOper());

                int updateOrderState = esiMapper.updateOrderState(vo);
                if (updateOrderState == 0) {
                    //已经审核不能审核
                    n = -1;
                    throw new Exception("单号" + vo.getOrderNo() + "当前状态下不能审核或库存不足！");
                }
                //更新逻辑卡号库存状态,更新数量与出库单数据相同，库存满足
                vo.setStockState(SamCardConstant.STOCK_STATE_RECYCLE_IN);
                vo.setIsInstock(SamCardConstant.STOCK_STATE_IN);
                vo.setLogicNos(vLogicNos);
                int m = esiMapper.updateStock(vo);
                if (m != vo.getOrderNum()) {
                    n = -1;
                    throw new Exception("单号" + vo.getOrderNo() + "库存不足！");
                }
                n += updateOrderState;
//            }
            
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        } finally {
            return n;
        }
    }

    private int insertLogicNos(List<String> logicNos, RecycleSamInMapper esiMapper, RecycleSamIn vo) {
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

    protected boolean checkContinuity(List<String> logicNos, RecycleSamIn vo) {
        boolean n = false;
        if (logicNos.size() < 1) {
            n = true;
            return n;
        }
        String stLogicNo = logicNos.get(0);//开始逻辑卡号
        String enLogicNo = logicNos.get(0);//结束逻辑卡号
        String tmpLogicNo = null;//临时逻辑卡号
        for (int i = 0; i < logicNos.size(); i++) {
            if (i + 1 == logicNos.size()) {
                vo.setEndLogicNo(enLogicNo);
                vo.setStartLogicNo(stLogicNo);
            } else {
                tmpLogicNo = logicNos.get(i + 1);
                int tfLogicNo = Integer.valueOf(getStLogicNoFive(tmpLogicNo));//取逻辑卡号后5位
                String teLogicNo = getStLogicNoEle(tmpLogicNo);//取逻辑卡号前11位
                int efLogicNo = Integer.valueOf(getStLogicNoFive(enLogicNo));//取逻辑卡号后5位
                String eeLogicNo = getStLogicNoEle(enLogicNo);//取逻辑卡号前11位
                if (efLogicNo + 1 == tfLogicNo && teLogicNo.equals(eeLogicNo)) {
                    enLogicNo = tmpLogicNo;
                } else {
                    n = true;
                    break;
                }
            }

        }
        return n;
    }

    protected int deletePlanByTrans(HttpServletRequest request, RecycleSamInMapper esiMapper, Vector<RecycleSamIn> pos) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (RecycleSamIn po : pos) {
                po.setOrderState(SamCardConstant.RECORD_FLAG_UNAUDITED);
                n += esiMapper.deletePlan(po);
            }
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected int modifyPlanByTrans(HttpServletRequest request, RecycleSamInMapper esiMapper, RecycleSamIn vo, OperationResult opResult) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            opResult.setAddPrimaryKey(vo.getOrderNo()); //增加操作记录的主健值
            String startLogicNo = vo.getStartLogicNo();
            vo.seteStartLogicNo(getStLogicNoEle(startLogicNo));
            vo.setfStartLogicNo(getStLogicNoFive(startLogicNo));
            n = esiMapper.modifyPlan(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected RecycleSamIn getQueryConditionForOpPlan(HttpServletRequest request, OperationResult opResult) {
        RecycleSamIn qCon = new RecycleSamIn();
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
                qCon.setOrderNo(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_billNo"));
                qCon.setSamType(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_samType"));
                qCon.setOrderState(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_orderState"));
            }
        }
        return qCon;
    }

    protected RecycleSamIn getQueryConditionIn(HttpServletRequest request) {
        RecycleSamIn qCon = new RecycleSamIn();
        qCon.setOrderNo(FormUtil.getParameter(request, "q_orderNo"));
        qCon.setSamType(FormUtil.getParameter(request, "q_samType"));
        qCon.setOrderState(FormUtil.getParameter(request, "q_orderState"));
        qCon.setIsBad(FormUtil.getParameter(request, "q_isBad"));
        return qCon;
    }

    protected Vector<RecycleSamIn> getReqAttributeForPlanAudit(HttpServletRequest request) {
        RecycleSamIn po = new RecycleSamIn();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<RecycleSamIn> selectedItems = this.getPlanSelectIDs(selectIds, request);
        return selectedItems;
    }

    protected Vector<RecycleSamIn> getReqAttributeForPlanDelete(HttpServletRequest request) {
        EmptySamIn po = new EmptySamIn();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<RecycleSamIn> selectedItems = this.getPlanSelectIDs(selectIds, request);
        //this.getBaseParameters(request, po);
        return selectedItems;
    }

    public RecycleSamIn getReqAttributePlan(HttpServletRequest request) {
        RecycleSamIn vo = new RecycleSamIn();
        vo.setOrderNo(FormUtil.getParameter(request, "d_orderNo"));
        vo.setLineEs(FormUtil.getParameter(request, "d_lineEs"));
        vo.setSamType(FormUtil.getParameter(request, "d_samType"));
        vo.setGetCardOper(FormUtil.getParameter(request, "d_getCardOper"));
        vo.setOrderNum(FormUtil.getParameterIntVal(request, "d_orderNum"));
        vo.setStartLogicNo(FormUtil.getParameter(request, "d_startLogicNo"));
        vo.setIsBad(FormUtil.getParameter(request, "d_isBad"));
        vo.setRemark(FormUtil.getParameter(request, "d_remark"));
        vo.setInStockOper(PageControlUtil.getOperatorFromSession(request));
        vo.setOrderState(SamCardConstant.RECORD_FLAG_UNAUDITED);
        vo.setStockState(SamCardConstant.STOCK_STATE_DISTR_OUT);
        vo.setIsInstock(SamCardConstant.STOCK_STATE_OUT);
        vo.setProduceType(SamCardConstant.PRODUCE_TYPE_PRODUCT);

        return vo;
    }

    protected RecycleSamIn getQueryConditionPlanDetail(HttpServletRequest request) {
        RecycleSamIn qCon = new RecycleSamIn();
        qCon.setOrderNo(FormUtil.getParameter(request, "queryCondition"));
        return qCon;
    }

    protected Vector<RecycleSamIn> getPlanSelectIDs(String selectedIds, HttpServletRequest request) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<RecycleSamIn> sds = new Vector();
        RecycleSamIn sd;
        String operatorId = PageControlUtil.getOperatorFromSession(request);
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getPlan(strIds, "#");
            sd.setAuditOrderOper(operatorId);
            sds.add(sd);
        }
        return sds;
    }

    protected RecycleSamIn getPlan(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        RecycleSamIn sd = new RecycleSamIn();
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
