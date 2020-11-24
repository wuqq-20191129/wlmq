/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.querysys.controller;

import com.goldsign.acc.app.querysys.entity.BalanceLog;
import com.goldsign.acc.app.querysys.mapper.BalanceLogMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.controller.PrmBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author zhouyang
 * 20170616
 * 日志查询——清算日志
 */
@Controller
public class BalanceLogController extends PrmBaseController{
    
    @Autowired
    private BalanceLogMapper balanceLogMapper;
    private final String ERRLEVEL = "errLevels";//清算日志错误级别


    @RequestMapping("/BalanceLog")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/querysys/balance_log.jsp");

        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.balanceLogMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_NEXT) || command.equals(CommandConstant.COMMAND_NEXTEND) || command.equals(CommandConstant.COMMAND_BACK)
                        || command.equals(CommandConstant.COMMAND_BACKEND)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.setErrLevelForPage(mv);//设置页面错误级别下拉选项
        this.getResultSetText((List<BalanceLog>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;

    }
    
    private void getResultSetText(List<BalanceLog> resultSet, ModelAndView mv) {
        List<PubFlag> errlevels = (List<PubFlag>) mv.getModelMap().get(ERRLEVEL);
        for (BalanceLog bl : resultSet) {
            if (errlevels != null && !errlevels.isEmpty()) {
                bl.setErr_level_name(DBUtil.getTextByCode(bl.getErr_level(), errlevels));
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            bl.setDate(format.format(bl.getGen_datetime()));
        }
    }
    
    private void setErrLevelForPage( ModelAndView mv){
        List<PubFlag> options;
        options = balanceLogMapper.getErrLevelForPage();
        mv.addObject(ERRLEVEL, options);
    }

    public OperationResult query(HttpServletRequest request, BalanceLogMapper blMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        BalanceLog balanceLog;
        List<BalanceLog> resultSet;

        try {
            balanceLog = this.getQueryCondition(request);
            resultSet = blMapper.getBalanceLogs(balanceLog);
            request.getSession().setAttribute("queryCondition", resultSet);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }
    
     private BalanceLog getQueryCondition(HttpServletRequest request) {
        BalanceLog balanceLog = new BalanceLog();
        if(FormUtil.getParameter(request, "q_beginDate") != null && !FormUtil.getParameter(request, "q_beginDate").equals("")){
            balanceLog.setBegin_date(FormUtil.getParameter(request, "q_beginDate") + " 00:00:00");
        }
        if(FormUtil.getParameter(request, "q_endDate") != null && !FormUtil.getParameter(request, "q_endDate").equals("")){
            balanceLog.setEnd_date(FormUtil.getParameter(request, "q_endDate") + " 23:59:59");
        }
        balanceLog.setErr_level(FormUtil.getParameter(request, "q_errLevel"));
        return balanceLog;
    }
     
     @RequestMapping("/BalanceLogExportAll")
    public void exportExcel(HttpServletRequest request,HttpServletResponse response) throws Exception{
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.querysys.entity.BalanceLog");
        List<Map<String,String>> queryResults = this.entityToMap(results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }
    
    private List<Map<String,String>> entityToMap(List results){
        List<Map<String,String>> list =  new ArrayList<>();
        int row = results.size();
        for(int n=0;n<row ;n++){
            BalanceLog vo = (BalanceLog)results.get(n);
            Map<String,String> map = new HashMap<>();
            map.put("water_no", vo.getWater_no());
            map.put("balance_water_no", vo.getBalance_water_no());
            map.put("oper_object", vo.getOper_object());
            map.put("err_level_name", vo.getErr_level_name());
            map.put("remark", vo.getRemark());
            map.put("operator_id", vo.getOperator_id());
            map.put("gen_datetime", vo.getDate());
            list.add(map);
        }
        return list;
    }
}
