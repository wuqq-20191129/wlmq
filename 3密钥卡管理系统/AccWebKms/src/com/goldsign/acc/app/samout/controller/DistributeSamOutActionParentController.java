/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.samout.controller;

import com.goldsign.acc.app.samin.entity.RecycleSamIn;
import com.goldsign.acc.app.samout.entity.DistributeSamOutAction;
import com.goldsign.acc.app.samout.entity.SamStock;
import com.goldsign.acc.app.samout.mapper.DistributeSamOutActionMapper;
import com.goldsign.acc.app.samout.mapper.SamStockMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.SamCardConstant;
import com.goldsign.acc.frame.controller.SamBaseController;
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
 *
 * @author liudz 卡分发出库
 */
public class DistributeSamOutActionParentController extends SamBaseController {
    public static int m;
    public static String oldFirstStartLogicNo;

    protected int addPlanByTrans(HttpServletRequest request, DistributeSamOutActionMapper dsoaMapper, BillMapper billMapper, DistributeSamOutAction vo, OperationResult opResult) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            this.addVoBillNo(billMapper, vo);
            opResult.setAddPrimaryKey(vo.getOrder_no()); //增加操作记录的主健值
            
            n = dsoaMapper.addPlan(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected void addVoBillNo(BillMapper billMappe, DistributeSamOutAction vo) {
        String billNoPlan = this.getBillNoTemp(billMappe, SamCardConstant.ORDER_TYPE_CODE_DISTRIBUTE_OUT);
        vo.setOrder_no(billNoPlan);
    }

    protected int auditPlanByTrans(HttpServletRequest request, DistributeSamOutActionMapper dsoaMapper, SamStockMapper ssMapper, DistributeSamOutAction po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            m = 0;
            status = txMgr.getTransaction(this.def);

//            for (DistributeSamOutAction po : pos) {
                //取订单详细信息
                List<DistributeSamOutAction> voList = dsoaMapper.queryPlan(po);

                DistributeSamOutAction vo = new DistributeSamOutAction();
                if (voList != null && !voList.isEmpty()) {
                    vo = (DistributeSamOutAction) voList.get(0);
                } else {
                    throw new Exception("单号" + po.getOrder_no() + "订单详细信息缺省！");
                }
                oldFirstStartLogicNo = vo.getStart_logic_no();

                vo.setE_start_logic_no(FormUtil.getStLogicNoNine(vo.getStart_logic_no()));//取起始逻辑卡号前9位
                vo.setF_start_logic_no(FormUtil.getStLogicNoSeven(vo.getStart_logic_no()));//取起始逻辑卡号后7位
                
                vo.setIs_bad(SamCardConstant.SAM_CARD_STATE_OK);
                vo.setIs_instock(SamCardConstant.STOCK_STATE_IN);
                vo.setProduce_type(SamCardConstant.PRODUCE_TYPE_PRODUCT);

                List<String> vLogicNos = dsoaMapper.checkLogicNoForAudit(vo);
                //插入卡分发出库单号和逻辑卡号 到 关联表ic_sam_logic_nos
                int insertLogicNosNum = insertLogicNos(vLogicNos, dsoaMapper, vo);
               //更新订单单据状态,订单起始逻辑卡号
               if(!oldFirstStartLogicNo.equals(vLogicNos.get(0))){                
                    m = 1;
                }
               vo.setOrder_state(SamCardConstant.RECORD_FLAG_AUDITED);
               vo.setAudit_order_oper(po.getAudit_order_oper());
               vo.setOrder_no(po.getOrder_no());
               vo.setIs_instock(SamCardConstant.STOCK_STATE_IN);
               vo.setIs_bad(SamCardConstant.SAM_CARD_STATE_OK);
               vo.setStart_logic_no(vLogicNos.get(0));//更新页面起始逻辑卡号
               vo.setE_start_logic_no(FormUtil.getStLogicNoNine(vo.getStart_logic_no()));
               vo.setF_start_logic_no(FormUtil.getStLogicNoSeven(vo.getStart_logic_no()));
               
                
                try {
                    n = dsoaMapper.updateOrderStock(vo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
              if(n==0){
                    //已经审核不能审核
                    throw new Exception("单号"+vo.getOrder_no()+"当前状态下不能审核或库存不足！");
                }
              
               vo.setStock_state(SamCardConstant.STOCK_STATE_DISTR_OUT);
                vo.setIs_instock(SamCardConstant.STOCK_STATE_OUT);
                vo.setLogic_no(vLogicNos);
                int m = dsoaMapper.updateStock(vo);
//                if (m != Integer.parseInt(vo.getOrder_num())) { 
//                    throw new Exception("单号" + vo.getOrder_no()+ "库存不足！");
//                }
//            }
            txMgr.commit(status);
        } catch (Exception e) { 
            PubUtil.handExceptionForTran(e, txMgr, status);
        } finally {
            return n;
        }
    }

    protected int deletePlanByTrans(HttpServletRequest request, DistributeSamOutActionMapper dsoaMapper, Vector<DistributeSamOutAction> pos) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (DistributeSamOutAction po : pos) {
                po.setOrder_state(SamCardConstant.RECORD_FLAG_UNAUDITED);
                n += dsoaMapper.deletePlan(po);
            }
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected int modifyPlanByTrans(HttpServletRequest request, DistributeSamOutActionMapper dsoaMapper, DistributeSamOutAction vo, OperationResult opResult) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = dsoaMapper.modifyPlan(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected DistributeSamOutAction getQueryConditionForOpPlan(HttpServletRequest request, OperationResult opResult) {
        DistributeSamOutAction qCon = new DistributeSamOutAction();
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
     protected DistributeSamOutAction getQueryConditionPlanDetail(HttpServletRequest request) {
        DistributeSamOutAction qCon = new DistributeSamOutAction();
        qCon.setOrder_no(FormUtil.getParameter(request, "queryCondition"));
        return qCon;
    }

    protected DistributeSamOutAction getQueryConditionPlan(HttpServletRequest request) {
        DistributeSamOutAction qCon = new DistributeSamOutAction();
        qCon.setOrder_no(FormUtil.getParameter(request, "q_order_no"));
        qCon.setSam_type(FormUtil.getParameter(request, "q_sam_type"));
        qCon.setOrder_state(FormUtil.getParameter(request, "q_order_state"));
        return qCon;
    }

    protected Vector<DistributeSamOutAction> getReqAttributeForPlanAudit(HttpServletRequest request) {
        DistributeSamOutAction po = new DistributeSamOutAction();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<DistributeSamOutAction> selectedItems = this.getPlanSelectIDs(selectIds, request);
        return selectedItems;
    }

    protected Vector<DistributeSamOutAction> getReqAttributeForPlanDelete(HttpServletRequest request) {
        DistributeSamOutAction po = new DistributeSamOutAction();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<DistributeSamOutAction> selectedItems = this.getPlanSelectIDs(selectIds, request);
        //this.getBaseParameters(request, po);
        return selectedItems;
    }

    public DistributeSamOutAction getReqAttributePlan(HttpServletRequest request) {
        DistributeSamOutAction po = new DistributeSamOutAction();
       

        //检验对应逻辑卡号库存是否足够(逻辑卡号段内、产品卡(03:成品卡入库、05:卡回收入库)、在库、好卡)
        po.setIs_bad(SamCardConstant.SAM_CARD_STATE_OK);
        po.setIs_instock(SamCardConstant.STOCK_STATE_IN);
        //(03:成品卡入库、05:卡回收入库)
        po.setStock_state("'" + SamCardConstant.STOCK_STATE_PRODUCT_IN + "','" + SamCardConstant.STOCK_STATE_RECYCLE_IN + "'");
        po.setProduce_type(SamCardConstant.PRODUCE_TYPE_PRODUCT);
//        po.setOrder_no(FormUtil.getParameter(request, "d_order_no"));
        po.setStart_logic_no(FormUtil.getParameter(request, "d_start_logic_no"));
        po.setE_start_logic_no(FormUtil.getStLogicNoNine(po.getStart_logic_no()));//取逻辑卡号前9位
        po.setF_start_logic_no(FormUtil.getStLogicNoSeven(po.getStart_logic_no()));//取逻辑卡号后7位
        po.setOrder_num(FormUtil.getParameter(request, "d_order_num"));
        po.setGet_card_oper(FormUtil.getParameter(request, "d_get_card_oper"));
        po.setLine_es(FormUtil.getParameter(request, "d_line_es"));
        po.setSam_type(FormUtil.getParameter(request, "d_sam_type"));
        po.setRemark(FormUtil.getParameter(request, "d_remark"));
        po.setOut_stock_oper(PageControlUtil.getOperatorFromSession(request));
        po.setOrder_state(SamCardConstant.RECORD_FLAG_UNAUDITED);
        return po;
    }
     public DistributeSamOutAction getReqAttributePlanForUpdate(HttpServletRequest request) {
        DistributeSamOutAction po = new DistributeSamOutAction();
       

        //检验对应逻辑卡号库存是否足够(逻辑卡号段内、产品卡(03:成品卡入库、05:卡回收入库)、在库、好卡)
        po.setIs_bad(SamCardConstant.SAM_CARD_STATE_OK);
        po.setIs_instock(SamCardConstant.STOCK_STATE_IN);
        //(03:成品卡入库、05:卡回收入库)
        po.setStock_state("'" + SamCardConstant.STOCK_STATE_PRODUCT_IN + "','" + SamCardConstant.STOCK_STATE_RECYCLE_IN + "'");
        po.setProduce_type(SamCardConstant.PRODUCE_TYPE_PRODUCT);
        po.setOrder_no(FormUtil.getParameter(request, "d_order_no"));
        po.setStart_logic_no(FormUtil.getParameter(request, "d_start_logic_no"));
        po.setE_start_logic_no(FormUtil.getStLogicNoNine(po.getStart_logic_no()));//取逻辑卡号前9位
        po.setF_start_logic_no(FormUtil.getStLogicNoSeven(po.getStart_logic_no()));//取逻辑卡号后7位
        po.setOrder_num(FormUtil.getParameter(request, "d_order_num"));
        po.setGet_card_oper(FormUtil.getParameter(request, "d_get_card_oper"));
        po.setLine_es(FormUtil.getParameter(request, "d_line_es"));
        po.setSam_type(FormUtil.getParameter(request, "d_sam_type"));
        po.setRemark(FormUtil.getParameter(request, "d_remark"));
        po.setOut_stock_oper(PageControlUtil.getOperatorFromSession(request));
        po.setOrder_state(SamCardConstant.RECORD_FLAG_UNAUDITED);
        return po;
    }

    protected Vector<DistributeSamOutAction> getPlanSelectIDs(String selectedIds, HttpServletRequest request) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<DistributeSamOutAction> sds = new Vector();
        DistributeSamOutAction sd;
        String operatorId = PageControlUtil.getOperatorFromSession(request);
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getPlan(strIds, "#");
            sd.setAudit_order_oper(operatorId);
            sds.add(sd);
        }
        return sds;
    }

    protected DistributeSamOutAction getPlan(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        DistributeSamOutAction sd = new DistributeSamOutAction();
        ;
        Vector<DistributeSamOutAction> sds = new Vector();
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


       private int insertLogicNos(List<String> logicNos, DistributeSamOutActionMapper dsoaMapper, DistributeSamOutAction vo) {
        int n = 0;
        if (logicNos.size() < 1) { 
            return n;
        }
        String stLogicNo = logicNos.get(0);//开始逻辑卡号
        String enLogicNo = logicNos.get(0);//结束逻辑卡号
        String tmpLogicNo = null;//临时逻辑卡号
        for (int i = 0; i < logicNos.size(); i++) {
            if (i + 1 == logicNos.size()) {
                vo.setF_start_logic_no(enLogicNo);
                vo.setE_start_logic_no(stLogicNo);
                n = dsoaMapper.insertLogicNo(vo);
            } else {
                tmpLogicNo = logicNos.get(i + 1);
                int tfLogicNo = Integer.valueOf(getStLogicNoFive(tmpLogicNo));//取逻辑卡号后5位
                String teLogicNo = getStLogicNoEle(tmpLogicNo);//取逻辑卡号前11位
                int efLogicNo = Integer.valueOf(getStLogicNoFive(enLogicNo));//取逻辑卡号后5位
                String eeLogicNo = getStLogicNoEle(enLogicNo);//取逻辑卡号前11位
                if (efLogicNo + 1 == tfLogicNo && teLogicNo.equals(eeLogicNo)) {
                    enLogicNo = tmpLogicNo;
                } else {
                    vo.setE_start_logic_no(stLogicNo);
                    vo.setF_start_logic_no(enLogicNo);
                    n = dsoaMapper.insertLogicNo(vo);
                    stLogicNo = tmpLogicNo;//开始逻辑卡号
                    enLogicNo = tmpLogicNo;//结束逻辑卡号
                }
            }

        }
        return n;
    }
       
       protected boolean checkContinuity(List<String> logicNos, DistributeSamOutAction vo) {
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
                vo.setEnd_logic_no(enLogicNo);
                vo.setStart_logic_no(stLogicNo);
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


}
