/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.querysys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.goldsign.acc.app.querysys.entity.CodePubFlag;
import com.goldsign.acc.app.querysys.entity.QrcodeOrderQuery;
import com.goldsign.acc.app.querysys.mapper.QrcodeOrderQueryMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import com.goldsign.login.vo.User;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.StringTokenizer;
import org.springframework.transaction.TransactionStatus;

/**
 * @desc:二维码取票订单
 * @author:wuqq
 * @create date: 2019-7-11
 */
@Controller
public class QrcodeOrderQueryController extends CodePubFlagController {

    @Autowired
    private QrcodeOrderQueryMapper qrcodeOrderQueryMapper;

    private String returnViewId;

    @RequestMapping("/QrcodeOrderQuery")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/querysys/qrocde_order_query.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_QUERY)) {
                    opResult = this.query(request, this.qrcodeOrderQueryMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作
                {
                    opResult = this.modify(request, this.qrcodeOrderQueryMapper, this.operationLogMapper);

                }
                if (command.equals(CommandConstant.COMMAND_AUDIT))//审核操作
                {
                    opResult = this.audit(request, this.qrcodeOrderQueryMapper, this.operationLogMapper);

                }
                if (command != null) {
                    if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY) || command.equals(CommandConstant.COMMAND_AUDIT)) {
                        if (!command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_NEXT) || command.equals(CommandConstant.COMMAND_NEXTEND) || command.equals(CommandConstant.COMMAND_BACK)
                                || command.equals(CommandConstant.COMMAND_BACKEND)) {
                            this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                        }
                        this.queryForOp(request, this.qrcodeOrderQueryMapper, this.operationLogMapper, opResult, command);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//                String[] attrNames = {LINE_STATIONS};
//
//                this.setPageOptions(attrNames, mv, request, response);
        this.getResultSetText((List<QrcodeOrderQuery>) opResult.getReturnResultSet(), mv, command);
        this.baseHandler(request, response, mv);//获取可用按钮
        this.divideResultSet(request, mv, opResult);
        this.SaveOperationResult(mv, opResult);
        return mv;
    }

    private void getResultSetText(List<QrcodeOrderQuery> resultSet, ModelAndView mv, String command) {
        this.setStation(mv, qrcodeOrderQueryMapper);
        if (resultSet == null || resultSet.isEmpty()) {
            return;
        }
        if (command == null || command.equals(CommandConstant.COMMAND_NEXT)
                || command.equals(CommandConstant.COMMAND_NEXTEND) || command.equals(CommandConstant.COMMAND_BACK)
                || command.equals(CommandConstant.COMMAND_BACKEND) || command.equals(CommandConstant.COMMAND_GOPAGE)) {
            return;

        }
        List<CodePubFlag> stations = (List<CodePubFlag>) mv.getModelMap().get("stations");
        for (QrcodeOrderQuery qr : resultSet) {
            if (stations != null && !stations.isEmpty()) {
                qr.setStart_station_text(getTextByCode(qr.getStart_station(), stations));
                qr.setEnd_station_text(getTextByCode(qr.getEnd_station(), stations));
            }
            if (qr.getStatus() != null) {
                switch (qr.getStatus()) {
                    case "0":
                        qr.setStatusString("未支付");
                        break;
                    case "1":
                        qr.setStatusString("已支付");
                        break;
                }
            }
            if (qr.getTicket_status() != null) {
                switch (qr.getTicket_status()) {
                    case "00":
                        qr.setTicket_statusString("订单未执行");
                        break;
                    case "01":
                        qr.setTicket_statusString("订单部分完成");
                        break;
                    case "02":
                        qr.setTicket_statusString("订单取消");
                        break;
                    case "80":
                        qr.setTicket_statusString("未购票或不存在");
                        break;
                    case "81":
                        qr.setTicket_statusString("订单已完成(已取票)");
                        break;
                    case "82":
                        qr.setTicket_statusString("二维码已过有效期");
                        break;
                    case "83":
                        qr.setTicket_statusString("购票、取票始发站不一致");
                        break;
                    case "84":
                        qr.setTicket_statusString("订单锁定");
                        break;
                }
            }
        }
    }
    //20190906 wuqq 增加查询w_ol_buf_qrcode_order

    public OperationResult query(HttpServletRequest request, QrcodeOrderQueryMapper qcMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        QrcodeOrderQuery queryCondition;
        List<QrcodeOrderQuery> resultSet;
        List<QrcodeOrderQuery> resultSet1;

        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = qcMapper.getQrcodeOrder(queryCondition);
            //resultSet1 = qcMapper.getQrcodeOrderadd(queryCondition);
//            resultSet.addAll(resultSet);
//            Collections.sort(resultSet);
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

    private QrcodeOrderQuery getQueryCondition(HttpServletRequest request) {
        QrcodeOrderQuery qCon = new QrcodeOrderQuery();
        qCon.setOrderNo(FormUtil.getParameter(request, "q_orderNo"));
        String beginDateTime = request.getParameter("q_beginTime");
        String endDateTime = request.getParameter("q_endTime");
        if (!"".equals(beginDateTime)) {
            beginDateTime = beginDateTime + " 00:00:00";
            qCon.setBeginDatetime(beginDateTime);
        }
        if (!"".equals(beginDateTime)) {
            endDateTime = endDateTime + " 23:59:59";
            qCon.setEndDatetime(endDateTime);
        }
        qCon.setStatus(FormUtil.getParameter(request, "q_orderStatus"));
        qCon.setTicket_status(FormUtil.getParameter(request, "q_ticeketStatus"));
        return qCon;
    }

    public void setStation(ModelAndView mv, QrcodeOrderQueryMapper qrcodeOrderQueryMapper) {
        List<CodePubFlag> stations = qrcodeOrderQueryMapper.getStation();
        mv.addObject("stations", stations);
    }

    public static String getTextByCode(String code, List tableFlags) {
        CodePubFlag pv = null;
        for (int i = 0; i < tableFlags.size(); i++) {
            pv = (CodePubFlag) tableFlags.get(i);
            if (pv.getCode().equals(code)) {
                return pv.getCodeText();
            }
        }
        return code;
    }

    @RequestMapping("/QrcodeOrderQueryExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElementsForCurClass(request, QrcodeOrderQuery.class.getName());
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

    private OperationResult modify(HttpServletRequest request, QrcodeOrderQueryMapper qrcodeOrderQueryMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        QrcodeOrderQuery queryCondition = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        User user = (User) request.getSession().getAttribute("User");
        queryCondition.setModify_operate(user.getAccount());
        queryCondition.setAudit_operate("");
        String preMsg = "订单号为" + queryCondition.getOrderNo() + "的";
        String status = queryCondition.getTicket_status();
        int isbuf = qrcodeOrderQueryMapper.getdate(queryCondition.getOrderNo());
        if (isbuf == 1) {
            returnViewId = queryCondition.getOrderNo();
            rmsg.addMessage(preMsg + "记录存在于临时表，不允许修改！"); 
            return rmsg;
        }

        if (!"84".equals(status)) {
            rmsg.addMessage(preMsg + "记录不是锁卡订单，不允许修改！");
            return rmsg;
        } else {
            try {

                n = this.modifyByTrans(request, qrcodeOrderQueryMapper, queryCondition);
            } catch (Exception e) {
                return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, operationLogMapper);
            }
            logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), operationLogMapper);

            rmsg.addMessage(LogConstant.modifySuccessMsg(n));

            return rmsg;
        }

    }

    private QrcodeOrderQuery getReqAttribute(HttpServletRequest request) {
        QrcodeOrderQuery po = new QrcodeOrderQuery();
        po.setOrderNo(FormUtil.getParameter(request, "d_orderNo"));
        po.setTicket_status(FormUtil.getParameter(request, "d_ticket_statusString"));
        po.setLock_dev(FormUtil.getParameter(request, "d_lock_dev"));
        po.setInsert_date(FormUtil.getParameter(request, "d_insert_date"));
        if (FormUtil.getParameter(request, "d_remark").equals("0")) {
            po.setRemark("手工处理为已出票");
        } else if (FormUtil.getParameter(request, "d_remark").equals("1")) {
            po.setRemark("手工处理为未出票");
        } else {
            po.setRemark(FormUtil.getParameter(request, "d_remark"));
        }

        return po;
    }

    private int modifyByTrans(HttpServletRequest request, QrcodeOrderQueryMapper qrcodeOrderQueryMapper, QrcodeOrderQuery queryCondition) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = qrcodeOrderQueryMapper.modifyQrcodOrder(queryCondition);
            qrcodeOrderQueryMapper.insertModifyOpe(queryCondition);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private OperationResult audit(HttpServletRequest request, QrcodeOrderQueryMapper qrcodeOrderQueryMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        List<QrcodeOrderQuery> resultSet;
        QrcodeOrderQuery qo = this.getReqAttributeForDelete(request);
//        resultSet = qrcodeOrderQueryMapper.getQrcodOrderForAudit(qo);
//
//        QrcodeOrderQuery queryCondition = new QrcodeOrderQuery();
//        queryCondition = resultSet.get(0);

        User user = (User) request.getSession().getAttribute("User");
        qo.setModify_operate("");
        qo.setAudit_operate(user.getAccount());

        LogUtil logUtil = new LogUtil();
        int n = 0;
        String preMsg = "订单号为" + qo.getOrderNo() + "的";

        String status = qo.getTicket_status();
        int isbuf = qrcodeOrderQueryMapper.getdate(qo.getOrderNo());
        if (isbuf == 1) {
            returnViewId = qo.getOrderNo();
            rmsg.addMessage(preMsg + "记录存在于临时表，不允许审核！");
            return rmsg;
        }
        if (!"84".equals(status)) {
            returnViewId = qo.getOrderNo();
            rmsg.addMessage(preMsg + "记录不是锁卡订单，不能进行审核操作！");
            return rmsg;
        } else {
            if (qo.getRemark() == null) {
                returnViewId = qo.getOrderNo();
                rmsg.addMessage(preMsg + "记录为锁卡订单，应先修改备注信息再进行审核！");
                return rmsg;
            }
            try {

                n = this.auditByTrans(request, qrcodeOrderQueryMapper, qo);
            } catch (Exception e) {
                return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_AUDIT, e, operationLogMapper);
            }
            logUtil.logOperation(CommandConstant.COMMAND_AUDIT, request, preMsg + LogConstant.auditSuccessMsg(n), operationLogMapper);

            rmsg.addMessage(LogConstant.auditSuccessMsg(n));

            return rmsg;
        }

    }

    private QrcodeOrderQuery getReqAttributeForDelete(HttpServletRequest request) {
        String selectIds = request.getParameter("allSelectedIDs");
        QrcodeOrderQuery selectedItems = this.getDeleteIDs(selectIds);
        return selectedItems;
    }

    private QrcodeOrderQuery getDeleteIDs(String selectIds) {
        StringTokenizer st = new StringTokenizer(selectIds, ";");
        String strIds = null;
        QrcodeOrderQuery sds = new QrcodeOrderQuery();

        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sds = this.getQrcodeOrderQuery(strIds, "#");

        }
        return sds;
    }

    private QrcodeOrderQuery getQrcodeOrderQuery(String strIds, String delim) {

        StringTokenizer st = new StringTokenizer(strIds, delim);
        String tmp = null;
        int i = 0;
        QrcodeOrderQuery sd = new QrcodeOrderQuery();

        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 2) {
                sd.setOrderNo(tmp);

            } else if (i == 3) {
                if (tmp.equals("订单锁定")) {
                    sd.setTicket_status("84");
                } else {
                    sd.setTicket_status(tmp);
                }

            } else if (i == 4) {
                sd.setLock_dev(tmp);

            } else if (i == 5) {
                sd.setRemark(tmp);

            }

        }
        return sd;
    }

    private int auditByTrans(HttpServletRequest request, QrcodeOrderQueryMapper qrcodeOrderQueryMapper, QrcodeOrderQuery qo) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            if (qo.getRemark().equals("手工处理为已出票"))//已出票
            {

                n = qrcodeOrderQueryMapper.auditQrcodOrderForY(qo);

            }
            if (qo.getRemark().equals("手工处理为未出票"))//未出票
            {

                n = qrcodeOrderQueryMapper.auditQrcodOrderForNo(qo);

            }
            qrcodeOrderQueryMapper.insertModifyOpe(qo);

            returnViewId = qo.getOrderNo();

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    public OperationResult queryForOp(HttpServletRequest request, QrcodeOrderQueryMapper qrcodeOrderQueryMapper, OperationLogMapper operationLogMapper, OperationResult opResult, String command) throws Exception {
        LogUtil logUtil = new LogUtil();
        QrcodeOrderQuery qrpay;
        List<QrcodeOrderQuery> resultSet;

        try {

            qrpay = this.getQueryConditionForOp(request);
            if (qrpay == null) {
                return null;
            }
            resultSet = qrcodeOrderQueryMapper.getQrcodeOrder(qrpay);

            opResult.setReturnResultSet(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return opResult;
    }

    private QrcodeOrderQuery getQueryConditionForOp(HttpServletRequest request) {
        QrcodeOrderQuery qCon = new QrcodeOrderQuery();
        HashMap vQueryControlDefaultValues = null;
        List<QrcodeOrderQuery> resultSet;
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

}
