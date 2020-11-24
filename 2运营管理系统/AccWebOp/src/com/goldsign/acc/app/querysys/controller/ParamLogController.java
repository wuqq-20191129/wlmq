/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.querysys.controller;

import com.goldsign.acc.app.querysys.entity.ParamLog;
import com.goldsign.acc.app.querysys.mapper.ParamLogMapper;
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
 * 20170621
 * 参数操作日志
 */
@Controller
public class ParamLogController extends PrmBaseController{
    
    @Autowired
    private ParamLogMapper paramLogMapper;


    @RequestMapping("/ParamLog")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/querysys/param_log.jsp");

        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.paramLogMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_NEXT) || command.equals(CommandConstant.COMMAND_NEXTEND) || command.equals(CommandConstant.COMMAND_BACK)
                        || command.equals(CommandConstant.COMMAND_BACKEND)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] attrNames = {OPERATORS,PARAMLOG_PARAM_TYPE,OP_TYPE};
        
        this.setPageOptions(attrNames, mv, request, response);//设置页面操作员选项值
        this.getResultSetText((List<ParamLog>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;

    }
    
    public OperationResult query(HttpServletRequest request, ParamLogMapper plMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        ParamLog paramLog;
        List<ParamLog> resultSet;

        try {
            paramLog = this.getQueryCondition(request);
            resultSet = plMapper.getParamLogs(paramLog);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }
    
    private void getResultSetText(List<ParamLog> resultSet, ModelAndView mv) {
        List<PubFlag> operators = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.OPERATORS);
        List<PubFlag> paramTypes = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.PARAMLOG_PARAM_TYPE);
        List<PubFlag> opTypes = (List<PubFlag>) mv.getModelMap().get(PrmBaseController.OP_TYPE);
        for (ParamLog pl : resultSet) {
            if (operators != null && !operators.isEmpty()) {
                pl.setOperator_name(DBUtil.getTextByCode(pl.getOperator_id(), operators));
            }
            if (paramTypes != null && !paramTypes.isEmpty()) {
                pl.setParam_type_name(DBUtil.getTextByCode(pl.getParam_type_id(), paramTypes));
            }
            if (opTypes != null && !opTypes.isEmpty()) {
                pl.setOp_type_name(DBUtil.getTextByCode(pl.getOp_type(), opTypes));
            }
        }
    }

    
    
     private ParamLog getQueryCondition(HttpServletRequest request) {
        ParamLog paramLog = new ParamLog();
        if(FormUtil.getParameter(request, "q_beginDate") != null && !FormUtil.getParameter(request, "q_beginDate").equals("")){
            paramLog.setBeginDate(FormUtil.getParameter(request, "q_beginDate") + " 00:00:00");
        }
        if(FormUtil.getParameter(request, "q_endDate") != null && !FormUtil.getParameter(request, "q_endDate").equals("")){
            paramLog.setEndDate(FormUtil.getParameter(request, "q_endDate") + " 23:59:59");
        }
        paramLog.setOperator_id(FormUtil.getParameter(request, "q_operator"));
        paramLog.setParam_type_id(FormUtil.getParameter(request, "q_param_type_id"));
        return paramLog;
    }
     
    @RequestMapping("/ParamLogExportAll")
    public void exportExcel(HttpServletRequest request,HttpServletResponse response) throws Exception{
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.querysys.entity.ParamLog");
        List<Map<String,String>> queryResults = this.entityToMap(results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }
    
    private List<Map<String,String>> entityToMap(List results){
        List<Map<String,String>> list =  new ArrayList<>();
        int row = results.size();
        for(int n=0;n<row ;n++){
            ParamLog vo = (ParamLog)results.get(n);
            Map<String,String> map = new HashMap<>();
            map.put("water_no",vo.getWater_no()+"");
            map.put("operator_name", vo.getOperator_name());
            map.put("op_time", vo.getOp_time());
            map.put("param_type_name", vo.getParam_type_name());
            map.put("op_type_name", vo.getOp_type_name());
            map.put("remark", vo.getRemark());
            list.add(map);
        }
        return list;
    }
}
