/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.querysys.controller;

import com.goldsign.acc.app.querysys.entity.QueryPhyLogicList;

import com.goldsign.acc.app.querysys.mapper.QueryPhyLogicListMapper;
import com.goldsign.acc.app.querysys.mapper.QueryTrafficFareParamMapper;
import com.goldsign.acc.frame.constant.CommandConstant;

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
 * 物理逻辑卡号对照表查询
 *
 * @author luck
 */
@Controller
public class QueryPhyLogicListController extends PrmBaseController {

    @Autowired
    private QueryPhyLogicListMapper queryPhyLogicListMapper;

    @RequestMapping("/QueryPhyLogicList")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/querysys/phy_logic_list.jsp");

        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();

                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.queryPhyLogicListMapper, this.operationLogMapper);

                }
                if (command.equals(CommandConstant.COMMAND_NEXT) || command.equals(CommandConstant.COMMAND_NEXTEND) || command.equals(CommandConstant.COMMAND_BACK)
                        || command.equals(CommandConstant.COMMAND_BACKEND)) {
                    this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;

    }

    private QueryPhyLogicList getQueryCondition(HttpServletRequest request) {
        QueryPhyLogicList qCon = new QueryPhyLogicList();
        qCon.setPhysic_no(FormUtil.getParameter(request, "q_physicNo"));
        qCon.setLogic_no(FormUtil.getParameter(request, "q_logicNo"));
        qCon.setWater_no(FormUtil.getParameter(request, "q_waterNo"));

        return qCon;
    }

    public OperationResult query(HttpServletRequest request, QueryPhyLogicListMapper qpMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        QueryPhyLogicList queryCondition;
        List<QueryPhyLogicList> resultSet;

        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = qpMapper.getQueryPhyLogicLists(queryCondition);
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

    @RequestMapping("/QueryPhyLogicListExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        QueryPhyLogicList queryCondition = new QueryPhyLogicList();

        if (request.getSession().getAttribute("queryCondition") != null) {
            Object obj = request.getSession().getAttribute("queryCondition");
            if (obj instanceof QueryPhyLogicList) {
                queryCondition = (QueryPhyLogicList) request.getSession().getAttribute("queryCondition");
            }
            List<Map> queryResults = queryPhyLogicListMapper.queryToMap(queryCondition);

            ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
        }
    }
}
