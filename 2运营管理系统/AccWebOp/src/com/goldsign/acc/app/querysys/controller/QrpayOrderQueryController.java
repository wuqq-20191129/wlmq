/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.querysys.controller;

import com.goldsign.acc.app.querysys.entity.QrpayOrderQuery;

import com.goldsign.acc.app.querysys.mapper.QrpayOrderQueryMapper;

import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;

import com.goldsign.acc.frame.controller.PrmBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;

import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.FormUtil;

import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import com.goldsign.login.vo.User;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 二维码支付订单查询
 *
 * @author luck
 */
@Controller
public class QrpayOrderQueryController extends PrmBaseController {

    @Autowired
    private QrpayOrderQueryMapper qrpayOrderQueryMapper;

    private String returnViewId;

    @RequestMapping("/QrpayQrderQuery")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/querysys/qrpay_order_query.jsp");

        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();

                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.qrpayOrderQueryMapper, this.operationLogMapper);

                }
                if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作
                {
                    opResult = this.modify(request, this.qrpayOrderQueryMapper, this.operationLogMapper);

                }
                if (command.equals(CommandConstant.COMMAND_AUDIT))//审核操作
                {
                    opResult = this.audit(request, this.qrpayOrderQueryMapper, this.operationLogMapper);

                }
                if (command != null) {
                    if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY) || command.equals(CommandConstant.COMMAND_AUDIT)) {
                        if (!command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_NEXT) || command.equals(CommandConstant.COMMAND_NEXTEND) || command.equals(CommandConstant.COMMAND_BACK)
                                || command.equals(CommandConstant.COMMAND_BACKEND)) {
                            this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                        }
                        this.queryForOp(request, this.qrpayOrderQueryMapper, this.operationLogMapper, opResult, command);
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] attrNames = {CARDMAINS, CARDMAIN_SUBS, CARDSUBS};

        this.setPageOptions(attrNames, mv, request, response);
        this.getResultSetText((List<QrpayOrderQuery>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;

    }

    private void getResultSetText(List<QrpayOrderQuery> resultSet, ModelAndView mv) {
//        List<PubFlag> cardMains = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.CARDMAINS);
//        List<PubFlag> cardSubs = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.CARDSUBS);

        for (QrpayOrderQuery sd : resultSet) {
            if (sd.getStatus() != null) {
                switch (sd.getStatus()) {
                    case "0":
                        sd.setStatusString("未支付");
                        break;
                    case "1":
                        sd.setStatusString("已支付");
                        break;
                    case "4":
                        sd.setStatusString("订单取消");
                        break;
                    case "5":
                        sd.setStatusString("订单支付失败");
                        break;
                    case "6":
                        sd.setStatusString("订单已退款");
                        break;
                }
            }
//            String cardType = sd.getCardType();
//            if (cardType != null) {
//                if (cardSubs != null && !cardSubs.isEmpty()) {
//                    sd.setCardTypeString(DBUtil.getTextByCode(cardType.substring(2, 4), cardType.substring(0, 2), cardSubs));
//                }
//            }
//            if (sd.getPayStatus() != null) {
//                switch (sd.getPayStatus()) {
//                    case "00":
//                        sd.setPayStatusString("支付成功");
//                        break;
//                    case "01":
//                        sd.setPayStatusString("余额不足");
//                        break;
//                    case "02":
//                        sd.setPayStatusString("黑名单账户");
//                        break;
//                    case "10":
//                        sd.setPayStatusString("支付通道通讯异常");
//                        break;
//                    case "99":
//                        sd.setPayStatusString("其他异常");
//                        break;
//                }
//            }
//            if (sd.getPayChannelType() != null) {
//                switch (sd.getPayChannelType()) {
//                    case "01":
//                        sd.setPayChannelTypeString("银行");
//                        break;
//                    case "02":
//                        sd.setPayChannelTypeString("银联");
//                        break;
//                    case "03":
//                        sd.setPayChannelTypeString("微信支付");
//                        break;
//                    case "04":
//                        sd.setPayChannelTypeString("支付宝支付");
//                        break;
//                    case "09":
//                        sd.setPayChannelTypeString("其他第三方支付");
//                        break;
//                    case "99":
//                        sd.setPayChannelTypeString("其他");
//                        break;
//                }
//            }
//            String cardTypeTotal = sd.getCardTypeTotal();
//            if (cardTypeTotal != null) {
//                if (cardSubs != null && !cardSubs.isEmpty()) {
//                    sd.setCardTypeTotalString(DBUtil.getTextByCode(cardTypeTotal.substring(2, 4), cardTypeTotal.substring(0, 2), cardSubs));
//                }
//            }
//            if (sd.getLastStatus()!= null) {
//                switch (sd.getLastStatus()) {
//                    case "0":
//                        sd.setLastStatusString("未支付");
//                        break;
//                    case "1":
//                        sd.setLastStatusString("已支付");
//                        break;
//                    case "4":
//                        sd.setLastStatusString("订单取消");
//                        break;
//                    case "5":
//                        sd.setLastStatusString("订单支付失败");
//                        break;
//                    case "6":
//                        sd.setLastStatusString("订单已退款");
//                        break;
//                }
//            }

        }

    }

    private QrpayOrderQuery getQueryCondition(HttpServletRequest request) {
        QrpayOrderQuery qCon = new QrpayOrderQuery();
        qCon.setOrderNo(FormUtil.getParameter(request, "q_orderNo"));
        String beginDateTime = request.getParameter("q_beginTime");
        String endDateTime = request.getParameter("q_endTime");
        if (!"".equals(beginDateTime)) {
            beginDateTime = beginDateTime + " 00:00:00";
            qCon.setBeginTime(beginDateTime);
        }
        if (!"".equals(beginDateTime)) {
            endDateTime = endDateTime + " 23:59:59";
            qCon.setEndTime(endDateTime);
        }
        qCon.setStatus(FormUtil.getParameter(request, "q_status"));
        qCon.setQrpayData("%" + FormUtil.getParameter(request, "q_qrpayData") + "%");
        qCon.setUnusual(FormUtil.getParameter(request, "q_unusual"));
        return qCon;
    }

    public OperationResult query(HttpServletRequest request, QrpayOrderQueryMapper qcMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        QrpayOrderQuery queryCondition;
        List<QrpayOrderQuery> resultSet;

        try {
            queryCondition = this.getQueryCondition(request);

            resultSet = qcMapper.getQrpayOrder(queryCondition);

            request.getSession().setAttribute("queryCondition", queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    @RequestMapping("/QrpayOrderQueryExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElementsForCurClass(request, QrpayOrderQuery.class.getName());
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

    public OperationResult modify(HttpServletRequest request, QrpayOrderQueryMapper qcMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        QrpayOrderQuery queryCondition = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        User user = (User) request.getSession().getAttribute("User");
        queryCondition.setModify_operate(user.getAccount());
        queryCondition.setAudit_operate("");
        String preMsg = "订单号为" + queryCondition.getOrderNo() + "的";
        String status = queryCondition.getStatusString();
        int isbuf = qcMapper.getdate(queryCondition.getOrderNo());
        if (isbuf == 1) {
            rmsg.addMessage(preMsg + "记录存在于临时表，不允许修改！");
            return rmsg;
        }
        if (!"1".equals(status)) {
            rmsg.addMessage(preMsg + "记录不是锁卡订单，不允许修改！");
            return rmsg;
        } else if (queryCondition.getSaleTimes().equals("") || queryCondition.getSaleTimes().equals("0")) {//已支付，未出票||已支付，无票
            try {

                n = this.modifyByTrans(request, qcMapper, queryCondition);
            } catch (Exception e) {
                return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
            }
            logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), opLogMapper);

            rmsg.addMessage(LogConstant.modifySuccessMsg(n));

            return rmsg;
        }
        rmsg.addMessage(preMsg + "记录不是锁卡订单，不允许修改！");
        return rmsg;

    }

    private QrpayOrderQuery getReqAttribute(HttpServletRequest request) {
        QrpayOrderQuery po = new QrpayOrderQuery();
        po.setOrderNo(FormUtil.getParameter(request, "d_orderNo"));
        po.setSaleTimes(FormUtil.getParameter(request, "d_saleTimes"));
        po.setStatusString(FormUtil.getParameter(request, "d_statusString"));
        po.setOrderIp(FormUtil.getParameter(request, "d_orderIp"));
        if (FormUtil.getParameter(request, "d_remark").equals("0")) {
            po.setRemark("itm未出票，手工处理为取消订单");
        } else {
            po.setRemark(FormUtil.getParameter(request, "d_remark"));
        }

        return po;
    }

    private int modifyByTrans(HttpServletRequest request, QrpayOrderQueryMapper qcMapper, QrpayOrderQuery po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = qcMapper.modifyQrpayOrder(po);
            qcMapper.insertModifyOpe(po);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    public OperationResult queryForOp(HttpServletRequest request, QrpayOrderQueryMapper qrpayOrderQueryMapper, OperationLogMapper operationLogMapper, OperationResult opResult, String command) throws Exception {
        LogUtil logUtil = new LogUtil();
        QrpayOrderQuery qrpay;
        List<QrpayOrderQuery> resultSet;

        try {

            qrpay = this.getQueryConditionForOp(request);
            if (qrpay == null) {
                return null;
            }
            resultSet = qrpayOrderQueryMapper.getQrpayOrder(qrpay);

            opResult.setReturnResultSet(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return opResult;
    }

    private QrpayOrderQuery getQueryConditionForOp(HttpServletRequest request) {
        QrpayOrderQuery qCon = new QrpayOrderQuery();
        HashMap vQueryControlDefaultValues = null;
        List<QrpayOrderQuery> resultSet;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            qCon.setOrderNo(FormUtil.getParameter(request, "d_orderNo"));

        } else if (command.equals(CommandConstant.COMMAND_AUDIT)) {
            qCon.setOrderNo(returnViewId);
//                QrpayOrderQuery ts = new QrpayOrderQuery();
//                ts.setOrderNo(returnViewId);
//                resultSet = qrpayOrderQueryMapper.getQrpayOrder(ts);

        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {

            }
        }

        return qCon;
    }

    private OperationResult audit(HttpServletRequest request, QrpayOrderQueryMapper qrpayOrderQueryMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();

        List<QrpayOrderQuery> resultSet;
        QrpayOrderQuery qo = this.getReqAttributeForDelete(request);
        String preMsg = "订单号为" + qo.getOrderNo() + "的";
        resultSet = qrpayOrderQueryMapper.getQrpayOrderForAudit(qo);
        if (resultSet.isEmpty()) {
            returnViewId = qo.getOrderNo();
            rmsg.addMessage(preMsg + "记录存在于临时表，不能进行审核操作！");
            return rmsg;
            
        }else{
        QrpayOrderQuery queryCondition = new QrpayOrderQuery();
        queryCondition = resultSet.get(0);

        User user = (User) request.getSession().getAttribute("User");
        queryCondition.setModify_operate("");
        queryCondition.setAudit_operate(user.getAccount());

        LogUtil logUtil = new LogUtil();
        int n = 0;

        String status = queryCondition.getStatus();

        if (!"1".equals(status)) {
            returnViewId = qo.getOrderNo();
            rmsg.addMessage(preMsg + "记录不是锁卡订单，不能进行审核操作！");
            return rmsg;
        } else if (queryCondition.getSaleTimes() == null || queryCondition.getSaleTimes().equals("0")) {//已支付，未出票||已支付，无票
            if (queryCondition.getRemark() == null) {
                returnViewId = qo.getOrderNo();
                rmsg.addMessage(preMsg + "记录为锁卡订单，应先修改备注信息再进行审核！");
                return rmsg;
            }
            try {

                n = this.auditByTrans(request, qrpayOrderQueryMapper, queryCondition);
            } catch (Exception e) {
                return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_AUDIT, e, operationLogMapper);
            }
            logUtil.logOperation(CommandConstant.COMMAND_AUDIT, request, preMsg + LogConstant.auditSuccessMsg(n), operationLogMapper);

            rmsg.addMessage(LogConstant.auditSuccessMsg(n));

            return rmsg;
        }
        rmsg.addMessage(preMsg + "记录不是锁卡订单，不能进行审核操作！");
        return rmsg;
        }
    }

    private int auditByTrans(HttpServletRequest request, QrpayOrderQueryMapper qrpayOrderQueryMapper, QrpayOrderQuery queryCondition) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = qrpayOrderQueryMapper.auditQrpayOrder(queryCondition);
            qrpayOrderQueryMapper.insertModifyOpe(queryCondition);
            returnViewId = queryCondition.getOrderNo();

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private QrpayOrderQuery getReqAttributeForDelete(HttpServletRequest request) {
        String selectIds = request.getParameter("allSelectedIDs");
        QrpayOrderQuery selectedItems = this.getDeleteIDs(selectIds);
        return selectedItems;
    }

    private QrpayOrderQuery getDeleteIDs(String selectIds) {
        StringTokenizer st = new StringTokenizer(selectIds, ";");
        String strIds = null;
        QrpayOrderQuery sds = new QrpayOrderQuery();

        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sds = this.getQrpayOrderQuery(strIds, "#");

        }
        return sds;
    }

    private QrpayOrderQuery getQrpayOrderQuery(String strIds, String delim) {
        StringTokenizer st = new StringTokenizer(strIds, delim);
        String tmp = null;
        int i = 0;
        QrpayOrderQuery sd = new QrpayOrderQuery();

        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 2) {
                sd.setOrderNo(tmp);
                continue;
            }

        }
        return sd;
    }

}
