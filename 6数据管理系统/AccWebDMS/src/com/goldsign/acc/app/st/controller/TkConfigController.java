/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.st.controller;

import com.goldsign.acc.app.st.entity.TkConfig;
import com.goldsign.acc.app.st.mapper.TkConfigMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.ExpUtil;
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
 *
 * @author limj
 */
@Controller
public class TkConfigController extends TkConfigParentController {
    @Autowired
    private TkConfigMapper tkMapper;
    @RequestMapping(value="/tkConfig")
    public ModelAndView service (HttpServletRequest request,HttpServletResponse response){
        ModelAndView mv = new ModelAndView("/jsp/st/tkConfig.jsp");
        String command = request.getParameter("command");
        String operType = request.getParameter("operType");
        OperationResult opResult = new OperationResult();
        try{
            if(command != null){
                command = command.trim();
                if(command.equals(CommandConstant.COMMAND_QUERY)){
                    opResult = this.queryPlan(request,this.tkMapper,this.operationLogMapper);
                }else if(command.equals(CommandConstant.COMMAND_MODIFY)){
                    opResult = this.modifyPlan(request,this.tkMapper,this.operationLogMapper);
                }
                if(this.isUpdateOp(command, operType)){
                    this.queryUpdateResult(command,operType,request,this.tkMapper,this.operationLogMapper,opResult,mv);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }
        this.baseHandler(request,response,mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集
        return mv;
    }

    private OperationResult queryPlan(HttpServletRequest request, TkConfigMapper tkMapper,
            OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TkConfig queryCondition;
        List<TkConfig> resultSet;
        int n = 0;
        try{
            queryCondition = this.getQueryConditionIn(request);
            resultSet = tkMapper.queryPlan(queryCondition);
            or.setReturnResultSet(resultSet);
            n = resultSet.size();
            or.setMessage(LogConstant.querySuccessMsg(n));
        }catch(Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request,CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request,  LogConstant.querySuccessMsg(n), operationLogMapper);
        return or;
    }

    private OperationResult modifyPlan(HttpServletRequest request, TkConfigMapper tkMapper,
            OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TkConfig vo;
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try{
            vo = this.getReAttributePlan(request);
            n = this.modifyPlanByTrans(request,tkMapper,vo,rmsg);
        }catch(Exception e){
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request,CommandConstant.COMMAND_MODIFY,e,operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, LogConstant.modifySuccessMsg(n), operationLogMapper);
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));
        return rmsg;
    }

    private void queryUpdateResult(String command, String operType, HttpServletRequest request, TkConfigMapper tkMapper, OperationLogMapper operationLogMapper, 
            OperationResult opResult, ModelAndView mv) throws Exception {
        if(!command.equals(CommandConstant.COMMAND_ADD)){
            this.saveQueryControlDefaultValues(request, mv); //保存查询条件
        }
        this.queryForOpPlan(request,this.tkMapper,this.operationLogMapper,opResult);
    }

    private OperationResult queryForOpPlan(HttpServletRequest request, TkConfigMapper tkMapper,
            OperationLogMapper operationLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        TkConfig queryCondition;
        List<TkConfig> resultSet;
        int n = 0;
        try{
            queryCondition = this.getQueryConditionForOpPlan(request,opResult);
            if(queryCondition == null){
                return null;
            }
            resultSet = tkMapper.queryPlan(queryCondition);
            opResult.setReturnResultSet(resultSet);
            n = resultSet.size();
        }catch(Exception e){
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, LogConstant.querySuccessMsg(n), operationLogMapper);
        return opResult;
    }
    
     @RequestMapping("/tkConfigExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
       // List results = this.getBufferElements(request);
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.st.entity.TkConfig");
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults
                = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response,
                ExpUtil.exportExcel(queryResults, request));
    }
}
