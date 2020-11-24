/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.querysys.controller;

import com.goldsign.acc.app.querysys.entity.CommuteLog;
import com.goldsign.acc.app.querysys.mapper.CommuteLogMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.controller.PrmBaseController;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
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
 * 20170620
 * 文件接收日志
 */
@Controller
public class CommuteLogController extends PrmBaseController{
    
    @Autowired
    private CommuteLogMapper commuteLogMapper;


    @RequestMapping("/CommuteLog")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/querysys/commute_log.jsp");

        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.commuteLogMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_NEXT) || command.equals(CommandConstant.COMMAND_NEXTEND) || command.equals(CommandConstant.COMMAND_BACK)
                        || command.equals(CommandConstant.COMMAND_BACKEND)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setCode((List<CommuteLog>) opResult.getReturnResultSet(), mv);
        this.getResultSetText((List<CommuteLog>) opResult.getReturnResultSet());
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;

    }
    
    private void getResultSetText(List<CommuteLog> resultSet) {
        for (CommuteLog cl : resultSet) {
            String result = cl.getResult();
            if(result!=null && result.equals("0")){
                cl.setResultText("成功");
            }else if(result!=null && result.equals("1")){
                cl.setResultText("失败");
            }else{
                cl.setResultText(result);
            }
        }
    }
    
    private void setCode(List<CommuteLog> resultSet, ModelAndView mv) {
        int code = 0;
        for (CommuteLog dv : resultSet) {
            dv.setCode(code);
            code++;
            
        }
    }

    public OperationResult query(HttpServletRequest request, CommuteLogMapper clMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        CommuteLog commuteLog;
        List<CommuteLog> resultSet;

        try {
            commuteLog = this.getQueryCondition(request);
            resultSet = clMapper.getCommuteLogs(commuteLog);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }
    
     private CommuteLog getQueryCondition(HttpServletRequest request) {
        CommuteLog commuteLog = new CommuteLog();
        if(FormUtil.getParameter(request, "q_beginDate")!=null && !FormUtil.getParameter(request, "q_beginDate").equals("")){
            commuteLog.setBeginDate(FormUtil.getParameter(request, "q_beginDate") + " 00:00:00");
        }
        if(FormUtil.getParameter(request, "q_endDate")!=null && !FormUtil.getParameter(request, "q_endDate").equals("")){
            commuteLog.setEndDate(FormUtil.getParameter(request, "q_endDate") + " 23:59:59");
        }
        return commuteLog;
    }
     
    @RequestMapping("/CommuteLogExportAll")
    public void exportExcel(HttpServletRequest request,HttpServletResponse response) throws Exception{
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.querysys.entity.CommuteLog");
        List<Map<String,String>> queryResults = this.entityToMap(results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }
    
    private List<Map<String,String>> entityToMap(List results){
        List<Map<String,String>> list =  new ArrayList<>();
        int row = results.size();
        for(int n=0;n<row ;n++){
            CommuteLog vo = (CommuteLog)results.get(n);
            Map<String,String> map = new HashMap<>();
            map.put("datetime_ftp", vo.getDatetime_ftp());
            map.put("ip", vo.getIp());
            map.put("filename", vo.getFilename());
            map.put("resultText", vo.getResultText());
            list.add(map);
        }
        return list;
    }
}
