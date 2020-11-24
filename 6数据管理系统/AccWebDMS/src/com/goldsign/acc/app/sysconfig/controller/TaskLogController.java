/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.sysconfig.controller;

import com.goldsign.acc.app.sysconfig.entity.TaskLog;
import com.goldsign.acc.app.sysconfig.mapper.TaskLogMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.controller.BaseController;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.util.PageControlUtil;
import com.goldsign.acc.frame.vo.OperationResult;
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
 * @author mh
 */
@Controller
public class TaskLogController extends BaseController {

    @Autowired
    TaskLogMapper tlMapper;

    @RequestMapping(value = "/taskLog")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/sysconfig/taskLog.jsp");
        String command = request.getParameter("command");
        String operType = request.getParameter("operType");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_QUERY)) {
                    opResult = this.query(request, this.tlMapper, this.operationLogMapper);
                }
            }
            //更新操作，增、删、改、审核,查询更新结果或原查询结果
            if (this.isUpdateOp(command, operType))
            {
                this.queryUpdateResult(command, operType, request, tlMapper, operationLogMapper, opResult, mv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.baseHandler(request, response, mv);
        // 结果集分页
        this.divideResultSet(request, mv, opResult);
        this.SaveOperationResult(mv, opResult);
        return mv;
    }

    private OperationResult query(HttpServletRequest request, TaskLogMapper tlMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult op = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TaskLog queryConditon;
        List<TaskLog> resultSet;
        try {
            queryConditon = this.getQueryConditon(request);
            resultSet = tlMapper.queryTaskLog(queryConditon);
            for (int i = 0; i < resultSet.size(); i++) {
                int taskResult = resultSet.get(i).getTaskResult();
                if (taskResult == 0) {
                    resultSet.get(i).setResultName("正常结束");
                }
                if (taskResult == 1) {
                    resultSet.get(i).setResultName("异常结束");
                }
                if (taskResult == 2) {
                    resultSet.get(i).setResultName("线程挂起");
                }
            }
            op.setReturnResultSet(resultSet);
            op.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return op;
    }

    private TaskLog getQueryConditon(HttpServletRequest request) {
        TaskLog vo = new TaskLog();
        vo.setTaskName(FormUtil.getParameter(request, "q_taskName"));
        vo.setTaskResult(FormUtil.getParameterIntVal(request, "q_taskResult"));
        vo.setStartTime(FormUtil.getParameter(request, "q_startTime"));
        vo.setEndTime(FormUtil.getParameter(request, "q_endTime"));
        return vo;
    }

    private void queryUpdateResult(String command, String operType, HttpServletRequest request, TaskLogMapper tlMapper, OperationLogMapper operationLogMapper, OperationResult opResult, ModelAndView mv) throws Exception {
        if (!command.equals(CommandConstant.COMMAND_ADD)) {
            //保存查询条件
            this.saveQueryControlDefaultValues(request, mv);
        }
    }



    @RequestMapping("/TaskLogExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElementsForCurClass(request,TaskLog.class.getName());
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields,results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }
}
