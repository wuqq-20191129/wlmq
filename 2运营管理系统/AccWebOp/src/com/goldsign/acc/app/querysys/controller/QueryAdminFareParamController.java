/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.querysys.controller;

import com.goldsign.acc.app.querysys.entity.QueryAdminFareParam;

import com.goldsign.acc.app.querysys.mapper.QueryAdminFareParamMapper;

import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.ParameterConstant;

import com.goldsign.acc.frame.controller.PrmBaseController;

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
 * 行政收费费率参数
 *
 * @author luck
 */
@Controller
public class QueryAdminFareParamController extends PrmBaseController {

    @Autowired
    private QueryAdminFareParamMapper queryAdminFareParamMapper;

    @RequestMapping("/AdminFareParam")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/querysys/admin_fare_param.jsp");

        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.queryAdminFareParamMapper, this.operationLogMapper);

                }
                if (command.equals(CommandConstant.COMMAND_NEXT) || command.equals(CommandConstant.COMMAND_NEXTEND) || command.equals(CommandConstant.COMMAND_BACK)
                        || command.equals(CommandConstant.COMMAND_BACKEND)) {
                    this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                }
            }
//            else{
//                request.getSession().removeAttribute("admin_manager_name");
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] attrNames = {ADMINHANDLEREASONS};
        this.setPageOptions(attrNames, mv, request, response);//设置页面线路、车站等选项值、版本
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;

    }

    private QueryAdminFareParam getQueryCondition(HttpServletRequest request) {
        QueryAdminFareParam qCon = new QueryAdminFareParam();
        qCon.setAdmin_manager_name(FormUtil.getParameter(request, "admin_manager_name"));
//        request.getSession().setAttribute("admin_manager_name", FormUtil.getParameter(request, "admin_manager_name"));
        qCon.setAdmin_charge("%" + FormUtil.getParameter(request, "admin_charge") + "%");
        qCon.setRecord_flag(ParameterConstant.RECORD_FLAG_CURRENT);
        return qCon;
    }

    public OperationResult query(HttpServletRequest request, QueryAdminFareParamMapper qaMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        QueryAdminFareParam queryCondition;
        List< QueryAdminFareParam> resultSet;

        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = qaMapper.getQueryAdminFareParams(queryCondition);
            request.getSession().setAttribute("queryCondition", queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    @RequestMapping("/QueryAdminFareParamExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        QueryAdminFareParam queryCondition = new QueryAdminFareParam();

        if (request.getSession().getAttribute("queryCondition") != null) {
            Object obj = request.getSession().getAttribute("queryCondition");
            if (obj instanceof QueryAdminFareParam) {
                queryCondition = (QueryAdminFareParam) request.getSession().getAttribute("queryCondition");
            } else {
                queryCondition.setRecord_flag(ParameterConstant.RECORD_FLAG_CURRENT);
            }

            List<Map> queryResults = queryAdminFareParamMapper.queryToMap(queryCondition);

            ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
        }
    }
}
