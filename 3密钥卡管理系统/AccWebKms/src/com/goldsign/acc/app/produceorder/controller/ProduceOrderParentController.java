package com.goldsign.acc.app.produceorder.controller;

import com.goldsign.acc.app.produceorder.entity.ProduceOrder;
import com.goldsign.acc.app.produceorder.mapper.ProduceOrderMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.SamCardConstant;
import com.goldsign.acc.frame.controller.SamBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.BillMapper;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.PageControlUtil;
import com.goldsign.acc.frame.util.PubUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import com.goldsign.login.vo.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

/**
 * 生产单制作
 *
 * @author xiaowu 20170828
 */
public class ProduceOrderParentController extends SamBaseController {

    protected int addPlanByTrans(HttpServletRequest request, ProduceOrderMapper mapper, BillMapper billMapper, ProduceOrder vo, OperationResult opResult, List<ProduceOrder> returnViewId) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            this.addVoBillNo(billMapper, vo);
            opResult.setAddPrimaryKey(vo.getOrder_no()); //增加操作记录的主健值
            returnViewId.add(vo);
            n = mapper.addPlan(vo);
            txMgr.commit(status);
        } catch (TransactionException e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected void addVoBillNo(BillMapper billMappe, ProduceOrder vo) {
        if (vo.getIs_bad().equals("2")) {
            String billNoPlan = this.getBillNoTemp(billMappe, SamCardConstant.ORDER_TYPE_CODE_RESET);
            vo.setOrder_no(billNoPlan);
        } else {
            String billNoPlan = this.getBillNoTemp(billMappe, SamCardConstant.ORDER_TYPE_CODE_PRODUCE);
            vo.setOrder_no(billNoPlan);
        }
    }

    protected int auditPlanByTrans(HttpServletRequest request, ProduceOrderMapper mapper, List<ProduceOrder> pos, List<ProduceOrder> returnViewId) throws Exception {
        TransactionStatus status = null;
        List<ProduceOrder> poList = new ArrayList<>();
        returnViewId.addAll(pos);
        int n = 0;
        try {
            //校验数据
            for (ProduceOrder po : pos) {
                ProduceOrder vo = new ProduceOrder();
                vo.setOrder_no(po.getOrder_no());
                List<ProduceOrder> voList = mapper.queryPlan(vo);//取订单详细信息
                List<PubFlag> samTypes = pubFlagMapper.getSamType();
                if (voList != null && !voList.isEmpty()) {
                    vo = voList.get(0);
                } else {
                    throw new Exception("单号" + po.getOrder_no() + "订单详细信息缺省！");
                }
                if (vo.getIs_bad().equals("2")) {

                    if (mapper.checkResetStore(vo.getStart_logic_no()) < 1) {   //检查库存
                        //更新逻辑卡号有误，库存不足
                        throw new Exception("sam卡类型：" + vo.getSam_type()
                                + "（" + DBUtil.getTextByCode(vo.getSam_type(), samTypes) + "）已经库存不足！");
                    }
                    poList.add(vo);
                } else {
                    ProduceOrder checkStoreTemp = new ProduceOrder();
                    checkStoreTemp.setSam_type(vo.getSam_type());
                    checkStoreTemp.setProduce_type(SamCardConstant.PRODUCE_TYPE_EMPTY);
                    checkStoreTemp.setIs_instock(SamCardConstant.STOCK_STATE_IN);
                    checkStoreTemp.setOrder_num(vo.getOrder_num());
                    if (mapper.checkStore(checkStoreTemp) < 1) {   //检查库存
                        //更新逻辑卡号有误，库存不足
                        throw new Exception("sam卡类型：" + vo.getSam_type()
                                + "（" + DBUtil.getTextByCode(vo.getSam_type(), samTypes) + "）已经库存不足！");
                    }
                    poList.add(vo);
                }
            }

            status = txMgr.getTransaction(this.def);
            //更新订单单据状态
            for (ProduceOrder vo : poList) {
                List<PubFlag> samTypes = pubFlagMapper.getSamType();
                //取开始逻辑卡号 
                String startLogicNo = null;//最小逻辑卡号
                List<ProduceOrder> logicNos = new ArrayList();//更新逻辑卡号集
                //按最小的逻辑卡号更新库存表w_ic_sam_stock表stock_state库存状态 为01:生产计划单
                for (int j = 0; j < Integer.valueOf(vo.getOrder_num()); j++) {
                    String logicNo = "";
                    if (vo.getIs_bad().equals("2")) {
                        logicNo = vo.getStart_logic_no();
                        this.updateReserLogicNo(mapper, vo);//更新库存表w_ic_sam_stock表
                    } else {
                        logicNo = this.updateMinLogicNo(mapper, vo);//更新库存表w_ic_sam_stock表,返回更新逻辑卡号
                    }
                    String startNO = "";
                    if (vo.getStart_logic_no() != null && !vo.getStart_logic_no().equals("")) {
                        startNO = "起始逻辑卡号：" + vo.getStart_logic_no();
                    }

                    if (logicNo == null || logicNo.equals("")) {
                        //更新逻辑卡号有误，库存不足
                        throw new Exception("sam卡类型：" + vo.getSam_type() + "(" + DBUtil.getTextByCode(vo.getSam_type(), samTypes) + ")已经库存不足！"
                                + startNO);
                    } else {
                        if (j == 0) {
                            startLogicNo = logicNo;//取最小逻辑卡号
//                            if (!startLogicNo.endsWith(vo.getStart_logic_no())) {
//                                //更新逻辑卡号有误，库存不足
//                                throw new Exception(startNO + "不在库存！");
//                            }
                        }
                        vo.setStart_logic_no(logicNo);
                        ProduceOrder poTemp = new ProduceOrder();
                        poTemp.setStLogicNo(logicNo);
                        logicNos.add(poTemp);//更新逻辑卡号集
                    }
                }
                //将更新逻辑卡号插入订单与逻辑卡号关联表
                this.insertSamLogicNos(mapper, vo.getOrder_no(), logicNos);

                User user = (User) request.getSession().getAttribute("User");
                String operatorID = user.getAccount();
                ProduceOrder poAudit = new ProduceOrder();
                poAudit.setFinish_flag(SamCardConstant.RECORD_FLAG_AUDITED);  //临时借用传参
                poAudit.setAudit_order_oper(operatorID);
                poAudit.setStart_logic_no(startLogicNo);
                poAudit.setOrder_no(vo.getOrder_no());
                poAudit.setOrder_state(SamCardConstant.RECORD_FLAG_UNAUDITED);
                poAudit.setSam_type(vo.getSam_type());
                if (vo.getIs_bad().equals("2")) {
                    poAudit.setProduce_type(SamCardConstant.PRODUCE_TYPE_PRODUCT);
                } else {
                    poAudit.setProduce_type(SamCardConstant.PRODUCE_TYPE_EMPTY);
                }
                poAudit.setIs_instock(SamCardConstant.STOCK_STATE_IN);
                poAudit.setOrder_num(vo.getOrder_num());
                int resultInt;
                if (vo.getIs_bad().equals("2")) {
                    resultInt = mapper.auditPlanForCz(poAudit);  //更新重置订单单据状态
                } else {
                    resultInt = mapper.auditPlan(poAudit);  //更新订单单据状态
                }
                if (resultInt == 0) {
                    //已经审核不能审核
                    throw new Exception("单号" + vo.getOrder_no() + "当前状态下不能修改或库存不足！");
                }
                n++;
            }
            txMgr.commit(status);
        } catch (Exception e) {
            e.printStackTrace();
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    /**
     * 插入 单号与逻辑卡号段到 ic_sam_logic_nos 表
     *
     * @param mapper
     * @param orderNo 单号
     * @param logicNos 逻辑卡号集
     * @return
     * @throws Exception
     */
    public int insertSamLogicNos(ProduceOrderMapper mapper, String orderNo, List logicNos) throws Exception {
        int n = 0;
        if (logicNos.size() < 1) {
            return n;
        }
        ProduceOrder nvo = (ProduceOrder) logicNos.get(0);
        String stLogicNo = nvo.getStLogicNo();//开始逻辑卡号
        String enLogicNo = nvo.getStLogicNo();//结束逻辑卡号

        try {
            for (int i = 0; i < logicNos.size(); i++) {
                if (i + 1 == logicNos.size()) {
                    ProduceOrder poTemp = new ProduceOrder();
                    poTemp.setOrderNo(orderNo);
                    poTemp.setStLogicNo(stLogicNo);
                    poTemp.setEnLogicNo(enLogicNo);
                    n = mapper.insertSamLogicNos(poTemp);
                } else {
                    ProduceOrder poTemp = (ProduceOrder) logicNos.get(i + 1);
                    String tmpLogicNo = poTemp.getStLogicNo();       //临时逻辑卡号
                    int tfLogicNo = Integer.valueOf(this.getStLogicNoSeven(tmpLogicNo));//取起始逻辑卡号后7位
                    String teLogicNo = this.getStLogicNoEle(tmpLogicNo);//取起始逻辑卡号前9位
                    int efLogicNo = Integer.valueOf(this.getStLogicNoSeven(enLogicNo));//取起始逻辑卡号后7位
                    String eeLogicNo = this.getStLogicNoEle(enLogicNo);//取起始逻辑卡号前11位
                    if (efLogicNo + 1 == tfLogicNo && teLogicNo.equals(eeLogicNo)) {
                        enLogicNo = tmpLogicNo;
                    } else {
                        ProduceOrder poTemp1 = new ProduceOrder();
                        poTemp1.setOrderNo(orderNo);
                        poTemp1.setStLogicNo(stLogicNo);
                        poTemp1.setEnLogicNo(enLogicNo);
                        n = mapper.insertSamLogicNos(poTemp1);
                        stLogicNo = poTemp.getStLogicNo();//开始逻辑卡号
                        enLogicNo = poTemp.getStLogicNo();//结束逻辑卡号
                    }
                }
            }
        } catch (NumberFormatException e) {
            throw e;
        }
        return n;
    }

    protected int deletePlanByTrans(HttpServletRequest request, ProduceOrderMapper mapper, List<ProduceOrder> pos) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (ProduceOrder po : pos) {
                po.setOrder_state(SamCardConstant.RECORD_FLAG_UNAUDITED);
                int temp = mapper.deletePlan(po);
                if (temp < 1) {
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

    protected int modifyPlanByTrans(HttpServletRequest request, ProduceOrderMapper mapper, ProduceOrder vo, OperationResult opResult, List<ProduceOrder> returnViewId) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = mapper.modifyPlan(vo);
            txMgr.commit(status);
            returnViewId.add(vo);
        } catch (TransactionException e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    protected ProduceOrder getQueryConditionForOpPlan(HttpServletRequest request, OperationResult opResult) {
        ProduceOrder qCon = new ProduceOrder();
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
            HashMap vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setOrder_no(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_order_no"));
                qCon.setSam_type(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_sam_type"));
                qCon.setOrder_state(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_order_state"));
            }
        }
        return qCon;
    }

    protected ProduceOrder getQueryConditionIn(HttpServletRequest request) {
        ProduceOrder qCon = new ProduceOrder();
        qCon.setOrder_no(FormUtil.getParameter(request, "q_order_no"));
        qCon.setSam_type(FormUtil.getParameter(request, "q_sam_type"));
        qCon.setOrder_state(FormUtil.getParameter(request, "q_order_state"));
        qCon.setIs_bad(FormUtil.getParameter(request, "q_is_Bad"));
        return qCon;
    }

    protected List<ProduceOrder> getReqAttributeForPlanAudit(HttpServletRequest request) {
        ProduceOrder po = new ProduceOrder();
        String selectIds = request.getParameter("allSelectedIDs");
        List<ProduceOrder> selectedItems = this.getPlanSelectIDs(selectIds, request);
        return selectedItems;
    }

    protected List<ProduceOrder> getReqAttributeForPlanDelete(HttpServletRequest request) {
        ProduceOrder po = new ProduceOrder();
        String selectIds = request.getParameter("allSelectedIDs");
        List<ProduceOrder> selectedItems = this.getPlanSelectIDs(selectIds, request);
        return selectedItems;
    }

    public ProduceOrder getReqAttributePlan(HttpServletRequest request) {
        ProduceOrder vo = new ProduceOrder();
        vo.setOrder_no(FormUtil.getParameter(request, "d_order_no"));
        vo.setSam_type(FormUtil.getParameter(request, "d_sam_type"));
        vo.setIs_bad(FormUtil.getParameter(request, "d_isBad"));
        vo.setStart_logic_no(FormUtil.getParameter(request, "d_start_logic_no"));
        vo.setOrder_num(FormUtil.getParameter(request, "d_order_num"));
        vo.setRemark(FormUtil.getParameter(request, "d_remark"));
        vo.setMake_order_oper(PageControlUtil.getOperatorFromSession(request));
        return vo;
    }

    protected List<ProduceOrder> getPlanSelectIDs(String selectedIds, HttpServletRequest request) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        List<ProduceOrder> sds = new ArrayList();
        ProduceOrder sd;
        String operatorId = PageControlUtil.getOperatorFromSession(request);
        while (st.hasMoreTokens()) {
            String strIds = st.nextToken();
            sd = this.getPlan(strIds, "#");
            sd.setAudit_order_oper(operatorId);
            sds.add(sd);
        }
        return sds;
    }

    protected ProduceOrder getPlan(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        int i = 0;
        ProduceOrder sd = new ProduceOrder();
        while (st.hasMoreTokens()) {
            i++;
            String tmp = st.nextToken();
            if (i == 1) {
                sd.setOrder_no(tmp);
            }
        }
        return sd;
    }

    /**
     * 取起始逻辑卡号后7位
     *
     * @param stLogicNo 起始逻辑卡号
     * @return
     */
    public String getStLogicNoSeven(String stLogicNo) {
        return stLogicNo.substring(stLogicNo.length() - 7);
    }

    /**
     * 取起始逻辑卡号前9位
     *
     * @param stLogicNo 起始逻辑卡号
     * @return
     */
    @Override
    public String getStLogicNoEle(String stLogicNo) {
        return stLogicNo.substring(0, 9);
    }

    /**
     * 取在库空白卡最小逻辑卡号
     *
     * @param vo sam卡类型,起始逻辑卡号
     * @param mapper
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = true)
    public String getMinLogicNo(ProduceOrder vo, ProduceOrderMapper mapper) throws Exception {
        ProduceOrder voTemp = new ProduceOrder();
        voTemp.setSam_type(vo.getSam_type());
        String startLogicNo = vo.getStart_logic_no();
        if (startLogicNo != null && !startLogicNo.equals("")) {
            voTemp.setStLogicNoEle(this.getStLogicNoEle(startLogicNo));
            voTemp.setStLogicNoSeven(this.getStLogicNoSeven(startLogicNo));
        }

        voTemp.setStock_state_first(SamCardConstant.STOCK_STATE_EMPTY_IN);
        voTemp.setStock_state_second(SamCardConstant.STOCK_STATE_PRODUCT_IN);
        voTemp.setProduce_type(SamCardConstant.PRODUCE_TYPE_EMPTY);
        voTemp.setIs_instock(SamCardConstant.STOCK_STATE_IN);

        return mapper.getMinLogicNo(voTemp);
    }

    /**
     * 按最小的逻辑卡号更新库存表ic_sam_stock表stock_state库存状态 为01:生产计划单
     *
     * @param mapper
     * @param vo
     * @return
     * @throws Exception
     */
    public String updateMinLogicNo(ProduceOrderMapper mapper, ProduceOrder vo) throws Exception {
        try {
            String startLogicNoTemp = this.getMinLogicNo(vo, mapper);
            if (startLogicNoTemp != null) {
                ProduceOrder temp = new ProduceOrder();
                temp.setStock_state_third(SamCardConstant.STOCK_STATE_PRODUCE_ORDER);
                temp.setStart_logic_no(startLogicNoTemp);
                temp.setSam_type(vo.getSam_type());
                temp.setStock_state_first(SamCardConstant.STOCK_STATE_EMPTY_IN);
                temp.setStock_state_second(SamCardConstant.STOCK_STATE_PRODUCT_IN);
                temp.setProduce_type(SamCardConstant.PRODUCE_TYPE_EMPTY);
                temp.setIs_instock(SamCardConstant.STOCK_STATE_IN);

                int n = this.updateMinLogicNoTransation(mapper, temp);
                if (n > 0) {
                    return startLogicNoTemp;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("更新出现异常");
        }
        return null;
    }

    @Transactional
    public int updateMinLogicNoTransation(ProduceOrderMapper mapper, ProduceOrder vo) throws Exception {
        return mapper.updateMinLogicNo(vo);
    }

    protected boolean isMin(ProduceOrder po) {
        return this.getStLogicNoSeven(po.getStart_logic_no()).equals("0000000");
    }

    private void updateReserLogicNo(ProduceOrderMapper mapper, ProduceOrder vo) throws Exception {
        try {

            if (vo.getStart_logic_no() != null) {
                ProduceOrder temp = new ProduceOrder();
                temp.setStock_state_third(SamCardConstant.STOCK_STATE_PRODUCE_ORDER);
                temp.setStart_logic_no(vo.getStart_logic_no());
                temp.setSam_type(vo.getSam_type());
                temp.setStock_state_first(SamCardConstant.STOCK_STATE_RECYCLE_IN);
                temp.setProduce_type(SamCardConstant.PRODUCE_TYPE_PRODUCT);
                temp.setIs_instock(SamCardConstant.STOCK_STATE_IN);

                mapper.updateResetLogicNo(temp);

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("更新出现异常");
        }

    }

}
