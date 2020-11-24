/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.commu.controller;

import com.goldsign.acc.app.commu.entity.CmIdxHis;
import com.goldsign.acc.app.commu.mapper.CmIdxHisMapper;
import com.goldsign.acc.app.st.entity.StClear;
import com.goldsign.acc.app.st.mapper.StClearMapper;
import com.goldsign.acc.frame.constant.CommandConstant;

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
 * 数据交换系统 - 分表记录
 *
 * @author luck
 */
@Controller
public class CmIdxHisController extends CmIdxHisParentController {

    @Autowired
    private CmIdxHisMapper cmIdxHisMapper;

    @RequestMapping(value = "/cmIdxHis")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/commu/cmIdxHis.jsp");
        String command = request.getParameter("command");
        String operType = request.getParameter("operType");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_QUERY)) {
                    opResult = this.queryPlan(request, this.cmIdxHisMapper, this.operationLogMapper);
                }
                if (this.isUpdateOp(command, operType))//更新操作，增、删、改、审核,查询更新结果或原查询结果
                {
                    this.queryUpdateResult(command, operType, request, cmIdxHisMapper, operationLogMapper, opResult, mv);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }
        this.getResultSetText(opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集
        return mv;
    }

    private void queryUpdateResult(String command, String operType, HttpServletRequest request, CmIdxHisMapper cihMapper,
            OperationLogMapper opLogMapper, OperationResult opResult, ModelAndView mv) throws Exception {
        if (!command.equals(CommandConstant.COMMAND_ADD)) {
            this.saveQueryControlDefaultValues(request, mv); //保存查询条件
        }
        this.queryForOpPlan(request, this.cmIdxHisMapper, this.operationLogMapper, opResult);

    }

    private void getResultSetText(List<CmIdxHis> resultSet, ModelAndView mv) {
        for (CmIdxHis sd : resultSet) {
            if (sd.getBeginDate() != null && !sd.getBeginDate().isEmpty()) {
                String beginDate = sd.getBeginDate();
                sd.setBeginDate(beginDate.substring(0, 4) + "-" + beginDate.substring(4, 6) + "-" + beginDate.substring(6, 8) + " " + beginDate.substring(8, 10) + ":" + beginDate.substring(10, 12));
            }
            if (sd.getEndDate() != null && !sd.getEndDate().isEmpty()) {
                String endDate = sd.getEndDate();
                sd.setEndDate(endDate.substring(0, 4) + "-" + endDate.substring(4, 6) + "-" + endDate.substring(6, 8) + " " + endDate.substring(8, 10) + ":" + endDate.substring(10, 12));
            }
        }

    }

    public OperationResult queryForOpPlan(HttpServletRequest request, CmIdxHisMapper cihMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        CmIdxHis queryCondition;
        List<CmIdxHis> resultSet;

        try {
            queryCondition = this.getQueryConditionForOpPlan(request, opResult);
            if (queryCondition == null) {
                return null;
            }
            resultSet = cmIdxHisMapper.queryPlan(queryCondition);
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    public OperationResult queryPlan(HttpServletRequest request, CmIdxHisMapper cihMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        CmIdxHis queryCondition;
        List<CmIdxHis> resultSet;

        try {
            queryCondition = this.getQueryConditionIn(request);
            resultSet = cmIdxHisMapper.queryPlan(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    @RequestMapping("/CmIdxHisExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        List results = this.getBufferElements(request);
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.commu.entity.CmIdxHis");
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults
                = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response,
                ExpUtil.exportExcel(queryResults, request));
    }

}
