/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.sysconfig.controller;

import com.goldsign.acc.app.init.service.TaskConfigService;
import com.goldsign.acc.app.sysconfig.entity.TaskConfig;
import com.goldsign.acc.app.sysconfig.mapper.TaskConfigMapper;
import com.goldsign.acc.app.util.SpringApplicationContextHolder;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.controller.DMSBaseController;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.util.PageControlUtil;
import com.goldsign.acc.frame.util.PubUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author mh
 */
@Controller
public class TaskConfigController extends DMSBaseController {
    //分离代码至TaskConfigService  modify by zhongzq 20171212
//    private static Map<String, TaskConfig> configMaps = new HashMap();

    @Autowired
    TaskConfigMapper tcMapper;

    @RequestMapping(value = "/taskConfig")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/sysconfig/taskConfig.jsp");
        String command = request.getParameter("command");
        String operType = request.getParameter("operType");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_MODIFY)) {
                    opResult = this.modify(request, this.tcMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_QUERY)) {
                    opResult = this.query(request, this.tcMapper, this.operationLogMapper);
                }
                //更新操作，增、删、改、审核,查询更新结果或原查询结果
                if (this.isUpdateOp(command, operType))
                {
                    this.queryUpdateResult(command, operType, request, tcMapper, operationLogMapper, opResult, mv);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.baseHandler(request, response, mv);
        this.SaveOperationResult(mv, opResult);
        new PageControlUtil().putBuffer(request, opResult.getReturnResultSet());
        return mv;
    }

    private OperationResult query(HttpServletRequest request, TaskConfigMapper tcMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult op = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TaskConfig queryConditon;
        List<TaskConfig> resultSet;
        try {
            queryConditon = this.getQueryConditon(request);
            resultSet = tcMapper.queryTaskConfig(queryConditon);
            setStatusText(resultSet);
            op.setReturnResultSet(resultSet);
            op.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return op;
    }

    private TaskConfig getQueryConditon(HttpServletRequest request) {
        TaskConfig vo = new TaskConfig();
        vo.setTaskName(FormUtil.getParameter(request, "q_taskName"));
        vo.setStatus((FormUtil.getParameterIntVal(request, "q_status")));
        return vo;
    }

    private OperationResult modify(HttpServletRequest request, TaskConfigMapper tcMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TaskConfig vo = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        String preMsg = "";
        try {
            n = this.modifyByTrans(request, tcMapper, vo, rmsg);
            TaskConfigService taskConfigService = (TaskConfigService) SpringApplicationContextHolder.getSpringBean("taskConfigService");
            taskConfigService.updateMemory(1);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), opLogMapper);
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private TaskConfig getReqAttribute(HttpServletRequest request) {
        TaskConfig vo = new TaskConfig();
        vo.setTaskName(FormUtil.getParameter(request, "d_taskName"));
        vo.setStartTime(FormUtil.getParameter(request, "d_startTime"));
        return vo;
    }

    private int modifyByTrans(HttpServletRequest request, TaskConfigMapper tcMapper, TaskConfig vo, OperationResult rmsg) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            request.setAttribute("taskName", vo.getTaskName());
            request.setAttribute("status", vo.getStatus());
            status = txMgr.getTransaction(this.def);
            n = tcMapper.modifyTaskConfig(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            PubUtil.handExceptionForTran(e, txMgr, status);
        }
        return n;
    }

    private void queryUpdateResult(String command, String operType, HttpServletRequest request, TaskConfigMapper tcMapper, OperationLogMapper operationLogMapper, OperationResult opResult, ModelAndView mv) throws Exception {
        if (!command.equals(CommandConstant.COMMAND_ADD)) {
            //保存查询条件
            this.saveQueryControlDefaultValues(request, mv);
        }
        this.queryForOp(request, this.tcMapper, this.operationLogMapper, opResult);
    }

    private OperationResult queryForOp(HttpServletRequest request, TaskConfigMapper tcMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        TaskConfig queryCondition;
        List<TaskConfig> resultSet;
        try {
            queryCondition = this.getQueryConditionForOp(request, opResult);
            resultSet = tcMapper.queryTaskConfig(queryCondition);
            setStatusText(resultSet);
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;
    }

    private void setStatusText(List<TaskConfig> resultSet) {
        for (int i = 0; i < resultSet.size(); i++) {
            int status = resultSet.get(i).getStatus();
            if (status == 0) {
                resultSet.get(i).setStatusName("待执行");
            } else if (status == 1) {
                resultSet.get(i).setStatusName("执行中");
            }
        }
    }

    private TaskConfig getQueryConditionForOp(HttpServletRequest request, OperationResult opResult) {
        TaskConfig qCon = new TaskConfig();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        if (FormUtil.isAddOrModifyMode(request)) {
            //操作处于添加或修改模式
            qCon.setTaskName(FormUtil.getParameter(request, "d_taskName"));
        } else {
            //操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setTaskName(FormUtil.getParameter(request, "q_taskName"));
                qCon.setStatus(FormUtil.getParameterIntVal(request, "q_status"));
            }
        }
        return qCon;
    }
//    分离代码至TaskConfigService modify by zhongzq 20171212
//    public static synchronized Map<String, TaskConfig> getConfigMaps() {
//        return configMaps;
//    }
//
//    public static synchronized void setConfigMaps(Map<String, TaskConfig> configMaps) {
//        TaskConfigController.configMaps = configMaps;
//    }

    @RequestMapping("/TaskConfigExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElementsForCurClass(request,TaskConfig.class.getName());
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }
}
