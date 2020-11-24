/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.st.controller;


import com.goldsign.acc.app.st.entity.StRptIndex;
import com.goldsign.acc.app.st.entity.StTrxIndex;

import com.goldsign.acc.app.st.mapper.StTrxIndexMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.controller.DMSBaseController;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
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
 * @author liudz 交易表索引表查询
 */
@Controller
public class StTrxIndexController extends DMSBaseController {

    @Autowired
    private StTrxIndexMapper stiMapper;

    @RequestMapping(value = "/stTrxIndex")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/st/stTrxIndex.jsp");
        String command = request.getParameter("command");
        String operType = request.getParameter("operType");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_QUERY)) {
                    opResult = this.queryPlan(request, this.stiMapper, this.operationLogMapper);
                }
                if (this.isUpdateOp(command, operType))//更新操作，增、删、改、审核,查询更新结果或原查询结果
                {
                    this.queryUpdateResult(command, operType, request, stiMapper, operationLogMapper, opResult, mv);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }

        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集
        return mv;
    }

    private void queryUpdateResult(String command, String operType, HttpServletRequest request, StTrxIndexMapper stiMapper,
            OperationLogMapper opLogMapper, OperationResult opResult, ModelAndView mv) throws Exception {
        if (!command.equals(CommandConstant.COMMAND_ADD)) {
            this.saveQueryControlDefaultValues(request, mv); //保存查询条件
        }
        this.queryForOpPlan(request, this.stiMapper, this.operationLogMapper, opResult);

    }

    public OperationResult queryForOpPlan(HttpServletRequest request, StTrxIndexMapper stiMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        StTrxIndex queryCondition;
        List<StTrxIndex> resultSet;

        try {
            queryCondition = this.getQueryConditionForOpPlan(request, opResult);
            if (queryCondition == null) {
                return null;
            }
            resultSet = stiMapper.queryPlan(queryCondition);
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    public OperationResult queryPlan(HttpServletRequest request, StTrxIndexMapper stiMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        StTrxIndex queryCondition;
        List<StTrxIndex> resultSet;

        try {
            queryCondition = this.getQueryConditionIn(request);
            resultSet = stiMapper.queryPlan(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    protected StTrxIndex getQueryConditionIn(HttpServletRequest request) {
        StTrxIndex qCon = new StTrxIndex();
        qCon.setTable_name(FormUtil.getParameter(request, "q_table_name"));

        return qCon;
    }

    protected StTrxIndex getQueryConditionForOpPlan(HttpServletRequest request, OperationResult opResult) {
        StTrxIndex qCon = new StTrxIndex();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {
            //操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;

            }
            qCon.setTable_name(opResult.getAddPrimaryKey());

        } else {
            //操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setTable_name(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_table_name"));

            }
        }
        return qCon;
    }
    
    @RequestMapping("/StTrxIndexExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.st.entity.StTrxIndex");
        List<Map<String,String>> queryResults = this.entityToMap(results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }

    private List<Map<String, String>> entityToMap(List results) {
        List<Map<String,String>> list =  new ArrayList<>();
        int row = results.size();
        for(int n=0;n<row ;n++){
            StTrxIndex vo = (StTrxIndex)results.get(n);
            Map<String,String> map = new HashMap<>();
            map.put("table_name", vo.getTable_name());
            map.put("begin_date", vo.getBegin_date());
            map.put("end_date", vo.getEnd_date());
            map.put("recd_count", vo.getRecd_count());

           
            list.add(map);
        }
        return list;
    }

}
