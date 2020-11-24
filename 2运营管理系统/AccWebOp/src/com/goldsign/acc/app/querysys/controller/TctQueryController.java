/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.querysys.controller;

import com.goldsign.acc.app.querysys.entity.TctQuery;

import com.goldsign.acc.app.querysys.mapper.TctQueryMapper;

import com.goldsign.acc.frame.constant.CommandConstant;

import com.goldsign.acc.frame.controller.PrmBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;

import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.FormUtil;

import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
 * 次票信息查询
 *
 * @author luck
 */
@Controller
public class TctQueryController extends PrmBaseController {

    @Autowired
    private TctQueryMapper tctQueryMapper;

    @RequestMapping("/TctQuery")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) throws ParseException {

        ModelAndView mv = new ModelAndView("/jsp/querysys/tct_query.jsp");

        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();

                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.tctQueryMapper, this.operationLogMapper);

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
        this.getResultSetText((List<TctQuery>) opResult.getReturnResultSet(), mv, this.tctQueryMapper);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;

    }

    private void getResultSetText(List<TctQuery> resultSet, ModelAndView mv, TctQueryMapper qcMapper) throws ParseException {
        List<PubFlag> cardSubs = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.CARDSUBS);

        for (TctQuery sd : resultSet) {
            if (cardSubs != null && !cardSubs.isEmpty()) {
                sd.setCardSubName(DBUtil.getTextByCode(sd.getCardSubId(), sd.getCardMainId(), cardSubs));
            }
            //判断账号状态
            int judgeStates = judgeStates(sd);
            switch (judgeStates) {
                case 0:
                    sd.setState("已激活");
                    break;
                case 1:
                    sd.setState("未激活");
                    break;
                case 2:
                    sd.setState("已过期");
                    break;
            }

            //累计消费金额
            if (sd.getSaleCount() != sd.getConsumeCount()) { 
                if (sd.getSaleCount() != 0) {
                    double avgFee = getDouble(sd.getSaleFee() / sd.getSaleCount());
                    sd.setConsumeFee(getDouble(avgFee * sd.getConsumeCount()));
                }
                //剩余金额
                sd.setRemainFee(getDouble(sd.getSaleFee() - sd.getConsumeFee()));
            }else{ //次数用完
                sd.setConsumeFee(sd.getSaleFee());
                sd.setRemainFee(0);
            }


            if (sd.getBalanceStatues() != null) {
                switch (sd.getBalanceStatues()) {
                    case "0":
                        sd.setBalanceStatuesString("未清算");
                        break;
                    case "1":
                        sd.setBalanceStatuesString("已清算");
                        break;
                }
            }
        }

    }

    private double getDouble(double fee) {
        BigDecimal b = new BigDecimal(fee);
        double doubleValue = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return doubleValue;
    }

    private String getBeijingTime() {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        Date date = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = dateFormat.format(date);
        return time;
    }

    private String getSysTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String time = dateFormat.format(new Date());
        return time;
    }

    private boolean compareTime(String time1, String time2, long validDay) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateTime1 = df.parse(time1);
        Date dateTime2 = df.parse(time2);
        long diff = dateTime1.getTime() - dateTime2.getTime();
        long days = diff / (1000 * 60 * 60 * 24);
        if (validDay < days) {
            return true;
        } else {
            return false;
        }
    }

    private TctQuery getQueryCondition(HttpServletRequest request) {
        TctQuery qCon = new TctQuery();
        qCon.setCardLogicalId(FormUtil.getParameter(request, "q_cardLogicalId"));
        String beginDateTime = request.getParameter("q_beginTime");
        String endDateTime = request.getParameter("q_endTime");
        if (!"".equals(beginDateTime)) {
            beginDateTime = beginDateTime + " 00:00:00";
            qCon.setValidDateStart(beginDateTime);
        }
        if (!"".equals(endDateTime)) {
            endDateTime = endDateTime + " 23:59:59";
            qCon.setValidDateEnd(endDateTime);
        }
        qCon.setBalanceStatues(FormUtil.getParameter(request, "q_status"));
        qCon.setCardMainId(FormUtil.getParameter(request, "q_cardMainID"));
        qCon.setCardSubId(FormUtil.getParameter(request, "q_cardSubID"));
        String sysTime = getSysTime();
        qCon.setCurDate(sysTime);
        qCon.setState(FormUtil.getParameter(request, "q_account_status"));

        return qCon;
    }

    public OperationResult query(HttpServletRequest request, TctQueryMapper qcMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TctQuery queryCondition;
        List<TctQuery> resultSet;
        List<TctQuery> resuList = null;

        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = qcMapper.getTct(queryCondition);
            request.getSession().setAttribute("queryCondition", queryCondition);
            resuList = selectByAccountState(resultSet, queryCondition);
            or.setReturnResultSet(resuList);
            or.addMessage("成功查询" + resuList.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resuList.size() + "条记录", opLogMapper);
        return or;

    }

    /*
        根据账户状态查询
     */
    public List<TctQuery> selectByAccountState(List<TctQuery> tctQuerys, TctQuery queryCondition) throws ParseException {
        List<TctQuery> result = new ArrayList<>();
        String state = queryCondition.getState();
        if (state == null || state.equals("")) {
            result = tctQuerys;
        } else {
            if (tctQuerys.size() > 0) {
                if (state.equals("0")) { //已激活
                    for (TctQuery t : tctQuerys) {
                        if (judgeStates(t) == 0) {
                            result.add(t);
                        }
                    }
                } else if (state.equals("1")) {
                    //未激活
                    for (TctQuery t : tctQuerys) {
                        if (judgeStates(t) == 1) {
                            result.add(t);
                        }
                    }
                } else { //过期
                    for (TctQuery t : tctQuerys) {
                        if (judgeStates(t) == 2) {
                            result.add(t);
                        }
                    }
                }
            }
        }
        return result;
    }

    /*
        判断账户状态 
     */
    public int judgeStates(TctQuery tctQuery) throws ParseException {
        int state = 0;
        long validDay = tctQuery.getValidDay();
        String saleDate = tctQuery.getSaleDate();
        String beijingTime = getBeijingTime();
        if (tctQuery.getValidDateStart() != null && tctQuery.getValidDateEnd() != null) {
            return state;
        }
        if (compareTime(beijingTime, saleDate, validDay)) {
            state = 2; //已过期
        } else {
            state = 1; // 未激活
        }
        return state;
    }

    @RequestMapping("/TctQueryExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElementsForCurClass(request, TctQuery.class.getName());
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

}
