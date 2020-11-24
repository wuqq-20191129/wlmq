/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.controller;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageBillTypeManage;
import com.goldsign.acc.app.basicinfo.mapper.TicketStorageBillTypeManageMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.controller.BaseController;
import com.goldsign.acc.frame.controller.StorageOutInBaseController;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.util.HashMap;
import java.util.List;
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
public class TicketStorageBillTypeManageController extends StorageOutInBaseController{
    
    @Autowired
    private TicketStorageBillTypeManageMapper ticketStorageBillTypeManageMapper;
    
    @RequestMapping(value="/ticketStorageBillTypeManage")
    public ModelAndView service (HttpServletRequest request, HttpServletResponse response)
    {
        ModelAndView mv = new ModelAndView("/jsp/basicinfo/ticketStorageBillTypeManage.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {         
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.ticketStorageBillTypeManageMapper, this.operationLogMapper);
                }
            }

            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)) {
                    if (!command.equals(CommandConstant.COMMAND_ADD)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                    }
                    this.queryForOp(request, this.ticketStorageBillTypeManageMapper, this.operationLogMapper, opResult);
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } 
        String[] attrNames = {BILL_NAMES};
        this.setPageOptions(attrNames, mv, request, response); //设置页面线路、车站、票卡、仓库等选项值、版本
        this.baseHandler(request, response, mv);
        this.SaveOperationResult(mv, opResult);
        return mv;
    }

    private OperationResult query(HttpServletRequest request, TicketStorageBillTypeManageMapper ticketStorageBillTypeManageMapper, OperationLogMapper operationLogMapper) throws Exception{
        OperationResult opResult = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageBillTypeManage queryCondition;
        List<TicketStorageBillTypeManage> resultSet;
        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = ticketStorageBillTypeManageMapper.queryTicketStorageBillTypeManage(queryCondition);
            opResult.setReturnResultSet(resultSet);
            opResult.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return opResult;
    }

    private TicketStorageBillTypeManage getQueryCondition(HttpServletRequest request) {
        TicketStorageBillTypeManage qCon = new TicketStorageBillTypeManage();
        qCon.setBill_type_id(FormUtil.getParameter(request, "q_billId"));
        qCon.setBill_main_type_id(FormUtil.getParameter(request, "q_billMainId"));
        qCon.setBill_name(FormUtil.getParameter(request, "q_billName"));
        return qCon;
    }
    
    public OperationResult queryForOp(HttpServletRequest request, TicketStorageBillTypeManageMapper ticketStorageBillTypeManageMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageBillTypeManage vo;
        List<TicketStorageBillTypeManage> resultSet;
        try {
            vo = this.getQueryConditionForOp(request);
            resultSet = ticketStorageBillTypeManageMapper.queryTicketStorageBillTypeManage(vo);
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;
    }
    
    private TicketStorageBillTypeManage getQueryConditionForOp(HttpServletRequest request) {
        TicketStorageBillTypeManage qCon = new TicketStorageBillTypeManage();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            qCon.setBill_type_id(FormUtil.getParameter(request, "d_billId"));
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if(!vQueryControlDefaultValues.isEmpty()){
                vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);             
            }
        }
        return qCon;
    }
}