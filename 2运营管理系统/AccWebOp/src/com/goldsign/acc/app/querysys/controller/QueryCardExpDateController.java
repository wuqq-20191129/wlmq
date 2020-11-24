/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.querysys.controller;

import com.goldsign.acc.app.querysys.entity.QueryCardExpDate;

import com.goldsign.acc.app.querysys.mapper.QueryCardExpDateMapper;

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
 * 票卡有效期
 *
 * @author luck
 */
@Controller
public class QueryCardExpDateController extends PrmBaseController {

    @Autowired
    private QueryCardExpDateMapper queryCardExpDateMapper;

    @RequestMapping("/CardExpDate")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/querysys/card_exp_date.jsp");

        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();

                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.queryCardExpDateMapper, this.operationLogMapper);

                }
                if (command.equals(CommandConstant.COMMAND_NEXT) || command.equals(CommandConstant.COMMAND_NEXTEND) || command.equals(CommandConstant.COMMAND_BACK)
                        || command.equals(CommandConstant.COMMAND_BACKEND)) {
                    this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] attrNames = {CARDMAINS, CARDMAIN_SUBS, CARDSUBS};

        this.setPageOptions(attrNames, mv, request, response);
        this.getResultSetText((List<QueryCardExpDate>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;

    }

    private void getResultSetText(List<QueryCardExpDate> resultSet, ModelAndView mv) {
        List<PubFlag> cardMains = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.CARDMAINS);
        List<PubFlag> cardSubs = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.CARDSUBS);

        for (QueryCardExpDate sd : resultSet) {
            if (cardMains != null && !cardMains.isEmpty()) {
                sd.setCard_main_id_text(DBUtil.getTextByCode(sd.getCard_main_id(), cardMains));
            }
            if (cardSubs != null && !cardSubs.isEmpty()) {
                sd.setCard_sub_id_text(DBUtil.getTextByCode(sd.getCard_sub_id(), sd.getCard_main_id(), cardSubs));
            }
        }

    }

    private QueryCardExpDate getQueryCondition(HttpServletRequest request) {
        QueryCardExpDate qCon = new QueryCardExpDate();
        qCon.setCard_main_id(FormUtil.getParameter(request, "q_cardMainID"));
        qCon.setCard_sub_id(FormUtil.getParameter(request, "q_cardSubID"));
        qCon.setExp_date(FormUtil.getParameter(request, "exp_date"));
        qCon.setRecord_flag(ParameterConstant.RECORD_FLAG_CURRENT);
        return qCon;
    }

    public OperationResult query(HttpServletRequest request, QueryCardExpDateMapper qcMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        QueryCardExpDate queryCondition;
        List<QueryCardExpDate> resultSet;

        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = qcMapper.getQueryCardExpDates(queryCondition);
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

    @RequestMapping("/QueryCardExpDateExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        QueryCardExpDate queryCondition = new QueryCardExpDate();

        if (request.getSession().getAttribute("queryCondition") != null) {
            Object obj = request.getSession().getAttribute("queryCondition");
            if (obj instanceof QueryCardExpDate) {
                queryCondition = (QueryCardExpDate) request.getSession().getAttribute("queryCondition");
            } else {
                queryCondition.setRecord_flag(ParameterConstant.RECORD_FLAG_CURRENT);
            }
            List<PubFlag> cardMains = pubFlagMapper.getCardMains();
            List<PubFlag> cardSubs = pubFlagMapper.getCardSubs();
            List<Map> queryResults = queryCardExpDateMapper.queryToMap(queryCondition);
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
