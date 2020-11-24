/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.st.controller;

import com.goldsign.acc.app.st.entity.StConfig;
import com.goldsign.acc.app.st.mapper.StConfigMapper;
import com.goldsign.acc.frame.constant.CommandConstant;

import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.controller.DMSBaseController;

import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;

import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.ExpUtil;

import com.goldsign.acc.frame.util.LogUtil;

import com.goldsign.acc.frame.vo.OperationResult;

import java.util.List;
import java.util.Map;

import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * 清算系统 - 配置信息
 *
 * @author luck
 */
@Controller
public class StConfigController extends StConfigParentController {

    @Autowired
    private StConfigMapper stcMapper;

    @RequestMapping(value = "/stConfig")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/st/stConfig.jsp");
        String command = request.getParameter("command");
        String operType = request.getParameter("operType");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_QUERY)) {
                    opResult = this.queryPlan(request, this.stcMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_MODIFY)) {
                    opResult = this.modifyPlan(request, this.stcMapper, this.operationLogMapper);
                }
                if (this.isUpdateOp(command, operType))//更新操作，增、删、改、审核,查询更新结果或原查询结果
                {
                    this.queryUpdateResult(command, operType, request, stcMapper, operationLogMapper, opResult, mv);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }

        String[] attrNames = this.getAttributeNames(command, operType);
        this.setPageOptions(attrNames, mv, request, response); //设置页面线路、车站、票卡、仓库等选项值、版本
        this.getResultSetText(opResult.getReturnResultSet(), mv, operType);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集
        return mv;
    }

    private String[] getAttributeNames(String command, String operType) {
        String[] attrNames = {TABLETYPES};
        return attrNames;
    }

    private void queryUpdateResult(String command, String operType, HttpServletRequest request, StConfigMapper stcMapper,
            OperationLogMapper opLogMapper, OperationResult opResult, ModelAndView mv) throws Exception {
        if (!command.equals(CommandConstant.COMMAND_ADD)) {
            this.saveQueryControlDefaultValues(request, mv); //保存查询条件
        }
        this.queryForOpPlan(request, this.stcMapper, this.operationLogMapper, opResult);

    }

    private void getResultSetText(List resultSet, ModelAndView mv, String operType) {
        this.getResultSetTextForPlan(resultSet, mv);

    }

    private void getResultSetTextForPlan(List<StConfig> resultSet, ModelAndView mv) {

        for (StConfig sd : resultSet) {
            switch (sd.getClearFlag()) {
                case 0:
                    sd.setClearFlagName("删除");
                    break;
                case 1:
                    sd.setClearFlagName("迁移");
                    break;
            }
        }
    }

    public OperationResult queryForOpPlan(HttpServletRequest request, StConfigMapper stcMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        StConfig queryCondition;
        List<StConfig> resultSet;

        try {
            queryCondition = this.getQueryConditionForOpPlan(request, opResult);
            if (queryCondition == null) {
                return null;
            }
            resultSet = stcMapper.queryPlan(queryCondition);
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    public OperationResult queryPlan(HttpServletRequest request, StConfigMapper stcMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        StConfig queryCondition;
        List<StConfig> resultSet;

        try {
            queryCondition = this.getQueryConditionIn(request);
            resultSet = stcMapper.queryPlan(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    public OperationResult modifyPlan(HttpServletRequest request, StConfigMapper stcMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        StConfig vo = this.getReqAttributePlan(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        String preMsg = "";
        try {

            n = this.modifyPlanByTrans(request, stcMapper, vo, rmsg);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), opLogMapper);
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    @RequestMapping("/StConfigExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        List results = this.getBufferElements(request);
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.st.entity.StConfig");
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults
                = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response,
                ExpUtil.exportExcel(queryResults, request));
    }

}
