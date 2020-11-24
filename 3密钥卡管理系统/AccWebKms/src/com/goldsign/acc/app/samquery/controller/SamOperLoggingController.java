/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.samquery.controller;

import com.goldsign.acc.app.samquery.entity.SamOperLogging;
import com.goldsign.acc.app.samquery.mapper.SamOperLoggingMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.constant.SamCardConstant;
import com.goldsign.acc.frame.controller.SamBaseController;
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
 * 操作日志
 * @author taidb
 */
@Controller
public class SamOperLoggingController extends SamBaseController{
    @Autowired 
    private SamOperLoggingMapper samOperLoggingMapper;
    
    @RequestMapping("/samOperLoggingAction")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv  = new ModelAndView("/jsp/samquery/samOperLogging.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if(command != null) {
                if(command.equals(CommandConstant.COMMAND_QUERY)){
                    opResult = this.query(request, this.samOperLoggingMapper, this.operationLogMapper);                   
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
	    opResult.addMessage(e.getMessage());
        }
        if(command ==  null)
            opResult.addMessage(LogConstant.OPERATIION_SUCCESS_LOG_MESSAGE);
        String[] attrNames = this.getReqAttributeNames();
        this.setSinglePageOptions(attrNames, mv, request, response);
        this.setPageOptions(attrNames, mv, request, response);//设置页面操作员选项值
        this.getResultSetText((List<SamOperLogging>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);
        
        return mv;
    }
    
    private OperationResult query(HttpServletRequest request, SamOperLoggingMapper samOperLoggingMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        SamOperLogging sol;
        List<SamOperLogging> resultSet;
        try {
            sol = this.getQueryCondition(request);
            resultSet = samOperLoggingMapper.getSamOperLogging(sol);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
    }
    
    private String[] getReqAttributeNames() {        
        String[] attrNames = {SamCardConstant.RESULT_NAME_SAM_OPER_TYPE,SamCardConstant.RESULT_NAME_SAM_SYS_TYPE,OPERATORS};
        return attrNames;
    }
    
    private void getResultSetText(List<SamOperLogging> resultSet, ModelAndView mv) {
        List<PubFlag> operators = (List<PubFlag>) mv.getModelMap().get(SamBaseController.OPERATORS);
        for (SamOperLogging vo : resultSet) {
            if(vo.getModuleId()!= null && !vo.getModuleId().equals("")){
                vo.setModuleName(samOperLoggingMapper.getModuleName(vo));
            }
            if (operators != null && !operators.isEmpty()) {
                vo.setOperatorName(DBUtil.getTextByCode(vo.getOperatorId(), operators));
            }
        }
    }
    
    private SamOperLogging getQueryCondition(HttpServletRequest request) {
        SamOperLogging qCon = new SamOperLogging(); 
        //操作处于非添加模式
        qCon.setOperatorId(FormUtil.getParameter(request, "q_operatorId"));
        String beginOpTime = FormUtil.getParameter(request, "q_beginTime");
        String endOpTime = FormUtil.getParameter(request, "q_endTime");
        if (!beginOpTime.equals("")) {
            qCon.setBeginOpTime(beginOpTime + " 00:00:00");
        }
        if (!beginOpTime.equals("")) {
            qCon.setEndOpTime(endOpTime + " 23:59:59");
        }
        qCon.setOperType(FormUtil.getParameter(request, "q_operType"));
        qCon.setSysType(FormUtil.getParameter(request, "q_sysType"));
        return qCon;
    }
    
    protected void setSinglePageOptions(String[] attrNames, ModelAndView mv, HttpServletRequest request, HttpServletResponse response) {

        List<SamOperLogging> options;
        String optionsSerial;

        for (String attrName : attrNames) {
            if (attrName.equals(SamCardConstant.RESULT_NAME_SAM_OPER_TYPE)) {//操作类型
                options = samOperLoggingMapper.getSamOperLoggingOperType();
                mv.addObject(attrName, options);
                continue;
            }
            if (attrName.equals(SamCardConstant.RESULT_NAME_SAM_SYS_TYPE)) {//系统类型
                options = samOperLoggingMapper.getSamOperLoggingSysType();
                mv.addObject(attrName, options);
                continue;
            }
        }

    }
    
    @RequestMapping("/SamOperLoggingExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElements(request);
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults
                = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response,
                ExpUtil.exportExcel(queryResults, request));
    }
}
