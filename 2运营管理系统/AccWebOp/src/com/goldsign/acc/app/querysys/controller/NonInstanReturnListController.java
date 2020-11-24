/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.querysys.controller;

import com.goldsign.acc.app.querysys.entity.NonInstanReturnList;
import com.goldsign.acc.app.querysys.mapper.NonInstanReturnListMapper;
import com.goldsign.acc.frame.constant.CommandConstant;

import com.goldsign.acc.frame.controller.PrmBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;

import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.ExpUtil;

import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 非即时退款查询
 *
 * @author luck
 */
@Controller
public class NonInstanReturnListController extends PrmBaseController {

    @Autowired
    private NonInstanReturnListMapper nonInstanReturnListMapper;

    private static String CHECKBOXFLAG;

    @RequestMapping("/NonInstanReturnList")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) throws ParseException {

        ModelAndView mv = new ModelAndView("/jsp/querysys/non_instan_retrun_list.jsp");

        String command = request.getParameter("command");

        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.nonInstanReturnListMapper, this.operationLogMapper);

                }
                if (command.equals(CommandConstant.COMMAND_NEXT) || command.equals(CommandConstant.COMMAND_NEXTEND) || command.equals(CommandConstant.COMMAND_BACK)
                        || command.equals(CommandConstant.COMMAND_BACKEND)) {
                    this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                }

            } else {
                CHECKBOXFLAG = "false";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.getSession().setAttribute("checkboxflg", CHECKBOXFLAG);
        String[] attrNames = {LINES, STATIONS, LINE_STATIONS, DEV_TYPES, CARDMAINS, CARDMAIN_SUBS, CARDSUBS};

        this.setPageOptions(attrNames, mv, request, response);//设置页面
        this.getResultSetText((List<NonInstanReturnList>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;

    }

    private void getResultSetText(List<NonInstanReturnList> resultSet, ModelAndView mv) throws ParseException {
        List<PubFlag> lines = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.LINES);
        List<PubFlag> stations = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.STATIONS);
        List<PubFlag> cardMains = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.CARDMAINS);
        List<PubFlag> cardSubs = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.CARDSUBS);
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        Date date = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currTime = dateFormat.format(date);
        Date currDate = null;
        Date applyDate = null;

        for (NonInstanReturnList sd : resultSet) {
            if (lines != null && !lines.isEmpty()) {
                sd.setLine_id_text(DBUtil.getTextByCode(sd.getLine_id(), lines));
                sd.setReturn_line_id_text(DBUtil.getTextByCode(sd.getReturn_line_id(), lines));
            }
            if (stations != null && !stations.isEmpty()) {
                sd.setStation_id_text(DBUtil.getTextByCode(sd.getStation_id(), sd.getLine_id(), stations));
                sd.setReturn_station_id_text(DBUtil.getTextByCode(sd.getReturn_station_id(), sd.getReturn_line_id(), stations));
            }
            if (cardMains != null && !cardMains.isEmpty()) {
                sd.setCard_main_id_text(DBUtil.getTextByCode(sd.getCard_main_id(), cardMains));
            }
            if (cardSubs != null && !cardSubs.isEmpty()) {
                sd.setCard_sub_id_text(DBUtil.getTextByCode(sd.getCard_sub_id(), sd.getCard_main_id(), cardSubs));
            }
            if(sd.getReturn_line_id()== null || sd.getReturn_line_id().equals("")){
                sd.setActual_return_bala(null);
            }
            String apply_datetime = sd.getApply_datetime();
            currDate = dateFormat.parse(currTime);
            applyDate = dateFormat.parse(apply_datetime);
            int days = (int) ((currDate.getTime() - applyDate.getTime()) / (24 * 60 * 60 * 1000));
            int nonReturnDay = nonInstanReturnListMapper.getNonReturnDay();
            if (days > nonReturnDay && sd.getHdl_flag().equals("2")) {
                sd.setIsOverTime("1");
            }
            if (sd.getAudit_flag() == null && sd.getHdl_flag().equals("1")) {
                sd.setIsOverNoAduit("1");
            }

            switch (sd.getHdl_flag()) {
                case "1":
                    sd.setHdl_flag_text("已办理退款");
                    break;
                case "2":
                    sd.setHdl_flag_text("车票未处理");
                    break;
                case "3":
                    sd.setHdl_flag_text("车票已有退款结果");
                    break;
                case "4":
                    sd.setHdl_flag_text("黑名单车票，不能办理退款");
                    break;
                case "5":
                    sd.setHdl_flag_text("凭证号或卡号输入错误，重新输入");
                    break;
                case "6":
                    sd.setHdl_flag_text("退款申请已撤消");
                    break;
                case "7":
                    sd.setHdl_flag_text("退款申请得到许可");
                    break;
                case "8":
                    sd.setHdl_flag_text("卡号非法");
                    break;

            }

        }

    }

    private NonInstanReturnList getQueryCondition(HttpServletRequest request) {
        NonInstanReturnList qCon = new NonInstanReturnList();
        qCon.setCard_logical_id("%" + FormUtil.getParameter(request, "q_tkLogicNo") + "%");
        qCon.setBusiness_receipt_id("%" + FormUtil.getParameter(request, "q_receiptId") + "%");
        qCon.setCard_main_id(FormUtil.getParameter(request, "q_cardMainID"));
        qCon.setCard_sub_id(FormUtil.getParameter(request, "q_cardSubID"));
        qCon.setLine_id(FormUtil.getParameter(request, "q_lineID"));
        qCon.setStation_id(FormUtil.getParameter(request, "q_stationID"));
        qCon.setBegin_time(FormUtil.getParameter(request, "q_beginTime"));
        qCon.setEnd_time(FormUtil.getParameter(request, "q_endTime"));
        qCon.setHdl_flag(FormUtil.getParameter(request, "q_hdlFlag"));
        if (request.getParameterMap().containsKey("balaCheck")) {
            CHECKBOXFLAG = "true";
        } else {
            CHECKBOXFLAG = "false";
        }
        request.getSession().setAttribute("checkboxflg", CHECKBOXFLAG);
        qCon.setRtnOper(FormUtil.getParameter(request, "q_rtnOper"));
        if (!FormUtil.getParameter(request, "q_rtnBala").trim().equals("")) {
            qCon.setReturn_bala(Double.parseDouble(FormUtil.getParameter(request, "q_rtnBala")));
        }

        return qCon;
    }

    public OperationResult query(HttpServletRequest request, NonInstanReturnListMapper nMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        NonInstanReturnList queryCondition;
        List<NonInstanReturnList> resultSet;

        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = nMapper.getNonInstanReturnLists(queryCondition);
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

    @RequestMapping("/NonInstanReturnListExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        NonInstanReturnList queryCondition = new NonInstanReturnList();

        if (request.getSession().getAttribute("queryCondition") != null) {
            Object obj = request.getSession().getAttribute("queryCondition");
            if (obj instanceof NonInstanReturnList) {
                queryCondition = (NonInstanReturnList) request.getSession().getAttribute("queryCondition");
            }
        }
        List<Map> queryResults = nonInstanReturnListMapper.queryToMap(queryCondition);
        List<PubFlag> cardMains = pubFlagMapper.getCardMains();
        List<PubFlag> cardSubs = pubFlagMapper.getCardSubs();
        List<PubFlag> lines = pubFlagMapper.getLines();
        List<PubFlag> stations = pubFlagMapper.getStations();
        /* 查询结果部分内容转换中文 */
        for (Map vo : queryResults) {
            if (cardMains != null && !cardMains.isEmpty()) {
                vo.put("CARD_MAIN_ID_TEXT", DBUtil.getTextByCode(vo.get("CARD_MAIN_ID").toString(), cardMains));
            }
            if (cardSubs != null && !cardSubs.isEmpty()) {
                vo.put("CARD_SUB_ID_TEXT", DBUtil.getTextByCode(vo.get("CARD_SUB_ID").toString(), vo.get("CARD_MAIN_ID").toString(), cardSubs));
            }
            if (lines != null && !lines.isEmpty()) {
                vo.put("LINE_ID_TEXT", DBUtil.getTextByCode(vo.get("LINE_ID").toString(), lines));
                if (vo.get("RETURN_LINE_ID") != null) {
                    vo.put("RETURN_LINE_ID_TEXT", DBUtil.getTextByCode(vo.get("RETURN_LINE_ID").toString(), lines));
                }
            }
            if (stations != null && !stations.isEmpty()) {
                vo.put("STATION_ID_TEXT", DBUtil.getTextByCode(vo.get("STATION_ID").toString(), vo.get("LINE_ID").toString(), stations));
                if (vo.get("RETURN_STATION_ID") != null) {
                    vo.put("RETURN_STATION_ID_TEXT", DBUtil.getTextByCode(vo.get("RETURN_STATION_ID").toString(), vo.get("RETURN_LINE_ID").toString(), stations));
                }
            }
            String APPLY_DATETIME = vo.get("APPLY_DATETIME").toString();
            vo.put("TIME", APPLY_DATETIME.substring(0, APPLY_DATETIME.length() - 2));
            switch (vo.get("HDL_FLAG").toString()) {
                case "1":
                    vo.put("HDL_FLAG_TEXT", "已办理退款");
                    break;
                case "2":
                    vo.put("HDL_FLAG_TEXT", "车票未处理");
                    break;
                case "3":
                    vo.put("HDL_FLAG_TEXT", "车票已有退款结果");
                    break;
                case "4":
                    vo.put("HDL_FLAG_TEXT", "黑名单车票，不能办理退款");
                    break;
                case "5":
                    vo.put("HDL_FLAG_TEXT", "凭证号或卡号输入错误，重新输入");
                    break;
                case "6":
                    vo.put("HDL_FLAG_TEXT", "退款申请已撤消");
                    break;
                case "7":
                    vo.put("HDL_FLAG_TEXT", "退款申请得到许可");
                    break;
                case "8":
                    vo.put("HDL_FLAG_TEXT", "卡号非法");
                    break;

            }
        }

        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }
}
