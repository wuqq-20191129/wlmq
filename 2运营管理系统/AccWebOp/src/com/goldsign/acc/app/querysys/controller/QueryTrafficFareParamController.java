/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.querysys.controller;

import com.goldsign.acc.app.querysys.entity.QueryTrafficFareParam;
import com.goldsign.acc.app.querysys.mapper.QueryTrafficFareParamMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.ParameterConstant;

import com.goldsign.acc.frame.controller.PrmBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;

import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.ExpUtil;

import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 乘车费率参数
 *
 * @author luck
 */
@Controller
public class QueryTrafficFareParamController extends PrmBaseController {

    @Autowired
    private QueryTrafficFareParamMapper queryTrafficFareParamMapper;

    private static String CHECKBOXFLAG;
    private static String CHECKBOXFLAG1;

    @RequestMapping("/TrafficFareParam")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/querysys/traffic_fare_param.jsp");

        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();

                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.queryTrafficFareParamMapper, this.operationLogMapper);

                }
                if (command.equals(CommandConstant.COMMAND_NEXT) || command.equals(CommandConstant.COMMAND_NEXTEND) || command.equals(CommandConstant.COMMAND_BACK)
                        || command.equals(CommandConstant.COMMAND_BACKEND)) {
                    this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                }

            } else {
                CHECKBOXFLAG = "false";
                CHECKBOXFLAG1 = "false";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        request.getSession().setAttribute("checkboxflg", CHECKBOXFLAG);
        request.getSession().setAttribute("checkboxflg1", CHECKBOXFLAG1);
        String[] attrNames = {CARDMAINS, CARDMAIN_SUBS, CARDSUBS, FARERIDETYPES};

        this.setPageOptions(attrNames, mv, request, response);//设置页面
        this.getResultSetText((List<QueryTrafficFareParam>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;

    }

    private void getResultSetText(List<QueryTrafficFareParam> resultSet, ModelAndView mv) {
        List<PubFlag> cardMains = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.CARDMAINS);
        List<PubFlag> cardSubs = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.CARDSUBS);

        for (QueryTrafficFareParam sd : resultSet) {
            if (cardMains != null && !cardMains.isEmpty()) {
                sd.setCard_main_id_text(DBUtil.getTextByCode(sd.getCard_main_id(), cardMains));
            }
            if (cardSubs != null && !cardSubs.isEmpty()) {
                sd.setCard_sub_id_text(DBUtil.getTextByCode(sd.getCard_sub_id(), sd.getCard_main_id(), cardSubs));
            }

        }

    }

    private QueryTrafficFareParam getQueryCondition(HttpServletRequest request) {
        QueryTrafficFareParam qCon = new QueryTrafficFareParam();
//        qCon.setDistince("%" + FormUtil.getParameter(request, "distince") + "%"); 
        if (!FormUtil.getParameter(request, "distince").trim().equals("")) {
            qCon.setDistince(Double.parseDouble(FormUtil.getParameter(request, "distince")));
        }
        if (request.getParameterMap().containsKey("balaCheck")) {
            CHECKBOXFLAG = "true";
        } else {
            CHECKBOXFLAG = "false";
        }
        if (request.getParameterMap().containsKey("balaCheck1")) {
            CHECKBOXFLAG1 = "true";
        } else {
            CHECKBOXFLAG1 = "false";
        }
        request.getSession().setAttribute("checkboxflg", CHECKBOXFLAG);
        request.getSession().setAttribute("checkboxflg1", CHECKBOXFLAG1);
        qCon.setSign(FormUtil.getParameter(request, "q_rtnOper"));
        qCon.setSign1(FormUtil.getParameter(request, "q_rtnOper1"));

        qCon.setFare(FormUtil.getParameter(request, "fare"));

        qCon.setCard_main_id(FormUtil.getParameter(request, "q_cardMainID"));
        qCon.setCard_sub_id(FormUtil.getParameter(request, "q_cardSubID"));
        qCon.setFareRideType(FormUtil.getParameter(request, "q_fareRideType"));
        qCon.setRecord_flag(ParameterConstant.RECORD_FLAG_CURRENT);
        return qCon;
    }

    public OperationResult query(HttpServletRequest request, QueryTrafficFareParamMapper qtMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        QueryTrafficFareParam queryCondition;
        List<QueryTrafficFareParam> resultSet;

        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = qtMapper.getQueryTrafficFareParams(queryCondition);
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

    @RequestMapping("/QueryTrafficFareParamExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        QueryTrafficFareParam queryCondition = new QueryTrafficFareParam();

        if (request.getSession().getAttribute("queryCondition") != null) {
            Object obj = request.getSession().getAttribute("queryCondition");
            if (obj instanceof QueryTrafficFareParam) {
                queryCondition = (QueryTrafficFareParam) request.getSession().getAttribute("queryCondition");
            }else{
                queryCondition.setRecord_flag(ParameterConstant.RECORD_FLAG_CURRENT);
            }
            List<Map> queryResults = queryTrafficFareParamMapper.queryToMap(queryCondition);
            List<PubFlag> cardMains = pubFlagMapper.getCardMains();
            List<PubFlag> cardSubs = pubFlagMapper.getCardSubs();
            /* 查询结果部分内容转换中文 */
            for (Map vo : queryResults) {
                if (cardMains != null && !cardMains.isEmpty()) {
                    vo.put("CARD_MAIN_ID_TEXT", DBUtil.getTextByCode(vo.get("CARD_MAIN_ID").toString(), cardMains));
                }
                if (cardSubs != null && !cardSubs.isEmpty()) {
                    vo.put("CARD_SUB_ID_TEXT", DBUtil.getTextByCode(vo.get("CARD_SUB_ID").toString(), vo.get("CARD_MAIN_ID").toString(), cardSubs));
                }
            }
            ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
        }
    }
}
