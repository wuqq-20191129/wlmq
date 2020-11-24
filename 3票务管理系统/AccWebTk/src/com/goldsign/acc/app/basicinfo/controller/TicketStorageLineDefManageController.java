/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.controller;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageLineDefManage;
import com.goldsign.acc.app.basicinfo.mapper.TicketStorageLineDefManageMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.controller.StorageOutInBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;
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
public class TicketStorageLineDefManageController  extends StorageOutInBaseController{
    @Autowired
    private TicketStorageLineDefManageMapper storageLineDefMapper;
    
    @RequestMapping(value="/ticketStorageLineDefManage")
    public ModelAndView service(HttpServletRequest request,HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/basicinfo/ticketStorageLineDefManage.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {         
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.storageLineDefMapper, this.operationLogMapper);
                }
            }
            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)) {
                    if (!command.equals(CommandConstant.COMMAND_ADD)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                    }
                    this.queryForOp(request, this.storageLineDefMapper, this.operationLogMapper, opResult);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.baseHandler(request, response, mv);
        this.SaveOperationResult(mv, opResult);
        String[] attrNames = this.getReqAttributeNames(request, command); //显示下拉内容
        this.setPageOptions(attrNames, mv, request, response);
        this.getResultSetText(opResult.getReturnResultSet(), mv);
        return mv;
    }

    private OperationResult query(HttpServletRequest request, TicketStorageLineDefManageMapper storageLineDefMapper, OperationLogMapper operationLogMapper) throws Exception{
        OperationResult opResult = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageLineDefManage queryCondition;
        List<TicketStorageLineDefManage> resultSet;
        try {
            queryCondition = this.getQueryConditon(request);
            resultSet = storageLineDefMapper.queryTicketStorageLineDefManage(queryCondition);
            opResult.setReturnResultSet(resultSet);
            opResult.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request,  CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        return opResult;
    }

    private TicketStorageLineDefManage getQueryConditon(HttpServletRequest request) {
        TicketStorageLineDefManage qCon = new TicketStorageLineDefManage();
        qCon.setStorageId(FormUtil.getParameter(request, "q_storageId"));
        return qCon;
    }
    
    private OperationResult queryForOp(HttpServletRequest request, TicketStorageLineDefManageMapper storageLineDefMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception{
        LogUtil logUtil = new LogUtil();
        TicketStorageLineDefManage vo;
        List<TicketStorageLineDefManage> resultSet;
        try {
            vo = this.getQueryConditionForOp(request);
            resultSet = storageLineDefMapper.queryTicketStorageLineDefManage(vo);
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;    
    }

    private TicketStorageLineDefManage getQueryConditionForOp(HttpServletRequest request) {
        TicketStorageLineDefManage qCon = new TicketStorageLineDefManage();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            qCon.setStorageId(FormUtil.getParameter(request, "d_storageId"));
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if(!vQueryControlDefaultValues.isEmpty()){
                vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);             
            }
        }
        return qCon;    
    }

    private String[] getReqAttributeNames(HttpServletRequest request, String command) {
        String[] attrNames = {STORAGES,IC_LINES};
        return attrNames;
    }

    private void getResultSetText(List resultSet, ModelAndView mv) {
        this.getResultSetTextForStorage(resultSet, mv);
    }

    private void getResultSetTextForStorage(List<TicketStorageLineDefManage> resultSet, ModelAndView mv) {
        List<PubFlag> storages = (List<PubFlag>)mv.getModel().get(StorageOutInBaseController.STORAGES);
        List<PubFlag> lines = (List<PubFlag>)mv.getModel().get(StorageOutInBaseController.IC_LINES);
        
        for (TicketStorageLineDefManage sl : resultSet) {
            if (storages != null && !storages.isEmpty() ) {
                sl.setStorageName(DBUtil.getTextByCode(sl.getStorageId(), storages));
            }
            if (lines != null && !lines.isEmpty()) {
                sl.setLineName(DBUtil.getTextByCode(sl.getLineId(), lines));
            }
        }
    }
    
}
