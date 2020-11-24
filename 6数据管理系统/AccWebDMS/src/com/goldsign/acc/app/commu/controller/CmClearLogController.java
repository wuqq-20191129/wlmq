/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.commu.controller;

import com.goldsign.acc.app.commu.entity.CmClearLog;
import com.goldsign.acc.app.commu.mapper.CmClearLogMapper;
import com.goldsign.acc.frame.controller.BaseController;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
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
 * @desc:配置信息
 * @author:chenzx
 * @create date: 2017-9-15
 */
@Controller
public class CmClearLogController extends BaseController {

    @Autowired
    private CmClearLogMapper mapper;
    

    @RequestMapping("/cmClearLog")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/commu/cmClearLog.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
               
                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.mapper, this.operationLogMapper);
                }
            } 

        } catch (Exception e) {
            e.printStackTrace();
        }

        this.setResultId((List<CmClearLog>) opResult.getReturnResultSet());//设置每行数据id
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult);

        return mv;
    }

    private OperationResult query(HttpServletRequest request, CmClearLogMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        CmClearLog queryCondition;
        List<CmClearLog> resultSet;
        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = mapper.getCmClearLog(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
    }
    
    private void setResultId(List<CmClearLog> resultSet) {
        int id=0;
        for (CmClearLog ccl : resultSet) {
            if (ccl != null) {
                ccl.setId(id);
            }
            id++;
        }

    }
    
    private CmClearLog getQueryCondition(HttpServletRequest request) {
        CmClearLog qCon = new CmClearLog();
        qCon.setOrigin_table_name(FormUtil.getParameter(request, "q_origin_table_name"));
        qCon.setBegin_clear_datetime(FormUtil.getParameter(request, "q_begin_clear_datetime"));
        qCon.setEnd_clear_datetime(FormUtil.getParameter(request, "q_end_clear_datetime"));
        return qCon;
    }
    
    @RequestMapping("/CmClearLogExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElements(request);
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults
                = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response,
                ExpUtil.exportExcel(queryResults, request));
    }

}

