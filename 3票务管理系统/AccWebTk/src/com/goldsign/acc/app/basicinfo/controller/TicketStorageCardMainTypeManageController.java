/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.controller;

import com.goldsign.acc.app.basicinfo.mapper.TicketStorageCardMainTypeManageMapper;
import com.goldsign.acc.app.basicinfo.entity.TicketStorageCardMainTypeManage;
import com.goldsign.acc.frame.controller.BaseController;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @desc:票库票卡主类型
 * @author:mh
 * @create date: 2017-07-25
 */
@Controller
public class TicketStorageCardMainTypeManageController extends BaseController {

    @Autowired
    private TicketStorageCardMainTypeManageMapper mapper;

    @RequestMapping("/ticketStorageCardMainTypeManage")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/basicinfo/ticketStorageCardMainType.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作
                {
                    opResult = this.modify(request, this.mapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.mapper, this.operationLogMapper);
                }
            } else {
                opResult = this.query(request, this.mapper, this.operationLogMapper);
            }

            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)) {
                    if (!command.equals(CommandConstant.COMMAND_ADD)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                    }
                    this.queryForOp(request, this.mapper, this.operationLogMapper, opResult);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }

        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult);

        return mv;
    }

    private OperationResult query(HttpServletRequest request, TicketStorageCardMainTypeManageMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageCardMainTypeManage queryCondition;
        List<TicketStorageCardMainTypeManage> resultSet;
        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = mapper.getTicketStorageCardMainTypeManage(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
    }

    public OperationResult queryForOp(HttpServletRequest request, TicketStorageCardMainTypeManageMapper mapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageCardMainTypeManage vo;
        List<TicketStorageCardMainTypeManage> resultSet;
        try {
            vo = this.getQueryConditionForOp(request);
            resultSet = mapper.getTicketStorageCardMainTypeManage(vo);
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    private TicketStorageCardMainTypeManage getQueryCondition(HttpServletRequest request) {
        TicketStorageCardMainTypeManage qCon = new TicketStorageCardMainTypeManage();
        return qCon;
    }

    private TicketStorageCardMainTypeManage getReqAttribute(HttpServletRequest request, String type) {
        TicketStorageCardMainTypeManage po = new TicketStorageCardMainTypeManage();
        po.setIc_main_type(FormUtil.getParameter(request, "d_icMainType"));
        po.setIc_main_desc(FormUtil.getParameter(request, "d_icMainDesc"));
        return po;
    }

    public OperationResult modify(HttpServletRequest request, TicketStorageCardMainTypeManageMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageCardMainTypeManage po = this.getReqAttribute(request, CommandConstant.COMMAND_MODIFY);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        if (this.existName(po, mapper)) {
            rmsg.addMessage("票卡主类型名称已存在！");
            return rmsg;
        } else {
            try {
                n = this.modifyByTrans(request, mapper, po);
            } catch (Exception e) {
                return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
            }
            logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, LogConstant.modifySuccessMsg(n), opLogMapper);
        }
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private TicketStorageCardMainTypeManage getQueryConditionForOp(HttpServletRequest request) {

        TicketStorageCardMainTypeManage qCon = new TicketStorageCardMainTypeManage();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            qCon.setIc_main_type(FormUtil.getParameter(request, "d_icMainType"));
            qCon.setIc_main_desc(FormUtil.getParameter(request, "d_icMainDesc"));
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
        }
        return qCon;
    }

    private int modifyByTrans(HttpServletRequest request, TicketStorageCardMainTypeManageMapper mapper, TicketStorageCardMainTypeManage po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = mapper.modifyTicketStorageCardMainTypeManage(po);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private boolean existName(TicketStorageCardMainTypeManage vo, TicketStorageCardMainTypeManageMapper mapper) {
        List<TicketStorageCardMainTypeManage> list = mapper.getTicketStorageCardMainTypeManageByName(vo);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }
    
    @RequestMapping("/TicketStorageCardMainTypeManageExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.basicinfo.entity.TicketStorageCardMainTypeManage");
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults
                = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response,
                ExpUtil.exportExcel(queryResults, request));
    }

}
