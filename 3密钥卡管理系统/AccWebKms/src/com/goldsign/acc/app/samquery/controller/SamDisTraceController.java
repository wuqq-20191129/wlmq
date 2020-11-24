/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.samquery.controller;

import com.goldsign.acc.app.samquery.entity.SamDisTrace;
import com.goldsign.acc.app.samquery.mapper.SamDisTraceMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
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
 *
 * @author zhouyang
 * 卡分发跟踪
 * 20170831
 */
@Controller
public class SamDisTraceController  extends SamBaseController{
    @Autowired 
    private SamDisTraceMapper mapper;
    
    @RequestMapping("/samDisTraceAction")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response)  throws Exception {
        ModelAndView mv  = new ModelAndView("/jsp/samquery/samDisTrace.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_QUERY)){//查询操作
                    opResult = this.query(request,this.operationLogMapper);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] attrNames = {SAM_TYPES,DISTRIBUTE_PLACE};
        this.setPageOptions(attrNames, mv, request, response);
        this.getResultSetText((List<SamDisTrace>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);
        return mv;
    }

    private OperationResult query(HttpServletRequest request, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        SamDisTrace queryCondition;
        List<SamDisTrace> resultSet;
        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = mapper.getSamDisTrace(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
    }

    private SamDisTrace getQueryCondition(HttpServletRequest request) {
        SamDisTrace vo = new SamDisTrace();
        
        vo.setSam_type(FormUtil.getParameter(request, "q_samType"));
        vo.setBeginLogicNo(FormUtil.getParameter(request, "q_beginLogicNo"));
        vo.setEndLogicNo(FormUtil.getParameter(request, "q_endLogicNo"));
        vo.setDistribute_place(FormUtil.getParameter(request, "q_distributePlace"));
        
        return vo;
    }

    private void getResultSetText(List<SamDisTrace> resultSet, ModelAndView mv) throws Exception {
        List<PubFlag> samTypes = (List<PubFlag>) mv.getModelMap().get(SamBaseController.SAM_TYPES);
        List<PubFlag> distributePlaces = (List<PubFlag>) mv.getModelMap().get(SamBaseController.DISTRIBUTE_PLACE);
        
        for (SamDisTrace vo : resultSet) {
            if (samTypes != null && !samTypes.isEmpty()) {
                vo.setSam_type_text(DBUtil.getTextByCode(vo.getSam_type(), samTypes));
            }
            if (distributePlaces != null && !distributePlaces.isEmpty()) {
                vo.setDistribute_place_text(DBUtil.getTextByCode(vo.getDistribute_place(), distributePlaces));
            }
        }
    }
    
    @RequestMapping("/SamDisTraceExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElements(request);
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults
                = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response,
                ExpUtil.exportExcel(queryResults, request));
    }
    
}
