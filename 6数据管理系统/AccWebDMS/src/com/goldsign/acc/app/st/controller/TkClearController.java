/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.acc.app.st.controller;


import com.goldsign.acc.app.st.entity.TkClear;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.goldsign.acc.app.st.mapper.TkClearMapper;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.util.ExpUtil;
import java.util.Map;

/**
 *
 * @author limj
 */
@Controller
public class TkClearController extends TkClearParentController{
    @Autowired
    private TkClearMapper pwcMapper;
    
    @RequestMapping(value="/tkClear")
    public ModelAndView service(HttpServletRequest request,HttpServletResponse response){
        ModelAndView mv = new ModelAndView("/jsp/st/tkClear.jsp");
        String command = request.getParameter("command");
        String operType = request.getParameter("operType");
        OperationResult opResult = new OperationResult();
        try{
            if(command != null){
                command = command.trim();
                if(CommandConstant.COMMAND_QUERY.equals(command)){
                    opResult = this.queryPlan(request,this.pwcMapper,this.operationLogMapper);
                }
                if(this.isUpdateOp(command, operType)){ //更新操作，增、删、改、审核,查询更新结果或原查询结果
                    this.queryUpdateResult(command, operType, request, pwcMapper, operationLogMapper, opResult, mv); 
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;
    }

    private OperationResult queryPlan(HttpServletRequest request, TkClearMapper pwcMapper, OperationLogMapper operationLogMapper) throws Exception {
       OperationResult or = new OperationResult();
       LogUtil logUtil = new LogUtil();
       TkClear queryCondition;
       List<TkClear> resultSet;
       int n = 0;
       try{
           queryCondition = this.getQueryConditionIn(request);
           resultSet = pwcMapper.queryPlan(queryCondition);
           or.setReturnResultSet(resultSet);
           n = resultSet.size();
           or.addMessage(LogConstant.querySuccessMsg(n));
       }catch(Exception e){
           e.printStackTrace();
           return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
       }
       logUtil.logOperation(CommandConstant.COMMAND_QUERY, request,LogConstant.querySuccessMsg(n), operationLogMapper);
       return or;
    }

    private void queryUpdateResult(String command, String operType, HttpServletRequest request, TkClearMapper pwcMapper,
            OperationLogMapper operationLogMapper, OperationResult opResult, ModelAndView mv) throws Exception {
        if(!command.equals(CommandConstant.COMMAND_ADD)){
            this.saveQueryControlDefaultValues(request, mv);//保存查询条件
        }
        this.queryForOpPlan(request,this.pwcMapper,this.operationLogMapper,opResult);
    }

    private OperationResult queryForOpPlan(HttpServletRequest request, TkClearMapper pwcMapper, OperationLogMapper operationLogMapper, OperationResult opResult) throws Exception{
        LogUtil logUtil = new LogUtil();
        TkClear queryCondition;
        List<TkClear> resultSet;
        int n = 0;
        try{
            queryCondition = this.getQueryConditionForOpPlan(request,opResult);
            if(queryCondition == null){
                return null;
            }
            resultSet = pwcMapper.queryPlan(queryCondition);
            opResult.setReturnResultSet(resultSet);
            n = resultSet.size();
        }catch(Exception e){
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request,CommandConstant.COMMAND_QUERY,e,operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY,request,LogConstant.querySuccessMsg(n),operationLogMapper);
        return opResult;
                
    }
    
    @RequestMapping("/tkClearExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // List results = this.getBufferElements(request);
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.st.entity.TkClear");
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults
                = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response,
                ExpUtil.exportExcel(queryResults, request));
    }
}
