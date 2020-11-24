/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.querysys.controller;

import com.goldsign.acc.app.querysys.entity.OperatorLog;
import com.goldsign.acc.app.querysys.mapper.OperatorLogMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.controller.PrmBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
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
 * 安全日志
 */
@Controller
public class OperatorLogController extends PrmBaseController{
    
    @Autowired
    private OperatorLogMapper operatorLogMapper;


    @RequestMapping("/OperatorLog")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/querysys/operator_log.jsp");

        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.operatorLogMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_NEXT) || command.equals(CommandConstant.COMMAND_NEXTEND) || command.equals(CommandConstant.COMMAND_BACK)
                        || command.equals(CommandConstant.COMMAND_BACKEND)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] attrNames = {OPERATORS};
        
        this.setPageOptions(attrNames, mv, request, response);//设置页面操作员选项值
        this.getResultSetText((List<OperatorLog>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;

    }
    
    private void getResultSetText(List<OperatorLog> resultSet, ModelAndView mv) {
        List<PubFlag> operators = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.OPERATORS);
        for (OperatorLog ol : resultSet) {
            if (operators != null && !operators.isEmpty()) {
                ol.setSys_operator_name(DBUtil.getTextByCode(ol.getSys_operator_id(), operators));
            }
        }
    }

    public OperationResult query(HttpServletRequest request, OperatorLogMapper olMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        OperatorLog operatorLog;
        List<OperatorLog> resultSet;

        try {
            operatorLog = this.getQueryCondition(request);
            resultSet = olMapper.getOperatorLogs(operatorLog);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }
    
     private OperatorLog getQueryCondition(HttpServletRequest request) {
        OperatorLog operatorLog = new OperatorLog();
         if(FormUtil.getParameter(request, "q_beginDate")!=null && !FormUtil.getParameter(request, "q_beginDate").equals("")){
            operatorLog.setBeginDate(FormUtil.getParameter(request, "q_beginDate") + " 00:00:00");
        }
        if(FormUtil.getParameter(request, "q_endDate")!=null && !FormUtil.getParameter(request, "q_endDate").equals("")){
            operatorLog.setEndDate(FormUtil.getParameter(request, "q_endDate") + " 23:59:59");
        }
        operatorLog.setSys_operator_id(FormUtil.getParameter(request, "q_operator"));
        return operatorLog;
    }
     
    @RequestMapping("/OperatorLogExportAll")
    public void exportExcel(HttpServletRequest request,HttpServletResponse response) throws Exception{
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.querysys.entity.OperatorLog");
        List<Map<String,String>> queryResults = this.entityToMap(results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }
    
    private List<Map<String,String>> entityToMap(List results){
        List<Map<String,String>> list =  new ArrayList<>();
        int row = results.size();
        for(int n=0;n<row ;n++){
            OperatorLog vo = (OperatorLog)results.get(n);
            Map<String,String> map = new HashMap<>();
            map.put("flow_id", vo.getFlow_id());
            map.put("sys_operator_name", vo.getSys_operator_name());
            map.put("login_time", vo.getLogin_time());
            map.put("logout_time", vo.getLogout_time());
            map.put("remark", vo.getRemark());
            map.put("check", "查看");
            list.add(map);
        }
        return list;
    }
}