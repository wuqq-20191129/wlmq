/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.controller;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageAdjustReasonManage;
import com.goldsign.acc.app.basicinfo.mapper.TicketStorageAdjustReasonManageMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.controller.BaseController;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.CharUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.util.HashMap;
import java.util.List;
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
 *
 * @author mh
 */
@Controller
public class TicketStorageAdjustReasonManageController extends BaseController{
    
    @Autowired
    private TicketStorageAdjustReasonManageMapper ticketStorageAdjustReasonManageMapper;
    
    @RequestMapping(value = "/ticketStorageAdjustReasonManage")
    public ModelAndView service (HttpServletRequest request, HttpServletResponse response)
    {
        ModelAndView mv = new ModelAndView("/jsp/basicinfo/ticketStorageAdjustReasonManage.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {         
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作
                {
                    opResult = this.modify(request, this.ticketStorageAdjustReasonManageMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE))//删除操作
                {
                    opResult = this.delete(request, this.ticketStorageAdjustReasonManageMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD))//增加操作
                {
                    opResult = this.add(request, this.ticketStorageAdjustReasonManageMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.ticketStorageAdjustReasonManageMapper, this.operationLogMapper);
                }
            }

            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)) {
                    if (!command.equals(CommandConstant.COMMAND_ADD)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                    }
                    this.queryForOp(request, this.ticketStorageAdjustReasonManageMapper, this.operationLogMapper, opResult);
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        
        this.baseHandler(request, response, mv);
        this.SaveOperationResult(mv, opResult);

        return mv;
    }

    private OperationResult query(HttpServletRequest request, TicketStorageAdjustReasonManageMapper ticketStorageAdjustReasonManageMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageAdjustReasonManage queryCondition;
        List<TicketStorageAdjustReasonManage> resultSet;
        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = ticketStorageAdjustReasonManageMapper.queryTicketStorageAdjustReasonManage(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
        
    }

    public OperationResult queryForOp(HttpServletRequest request, TicketStorageAdjustReasonManageMapper ticketStorageAdjustReasonManageMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageAdjustReasonManage vo;
        List<TicketStorageAdjustReasonManage> resultSet;
        try {
            vo = this.getQueryConditionForOp(request);
            resultSet = ticketStorageAdjustReasonManageMapper.queryTicketStorageAdjustReasonManage(vo);
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;
    }

    private TicketStorageAdjustReasonManage getQueryCondition(HttpServletRequest request) {
        TicketStorageAdjustReasonManage qCon = new TicketStorageAdjustReasonManage();
        return qCon;
    }

    private TicketStorageAdjustReasonManage getReqAttribute(HttpServletRequest request, String type) {
        TicketStorageAdjustReasonManage po = new TicketStorageAdjustReasonManage();
        po.setAdjust_id(FormUtil.getParameter(request, "d_adjustId"));
        po.setAdjust_reason(FormUtil.getParameter(request, "d_adjustReason"));
        return po;
    }

    public OperationResult modify(HttpServletRequest request, TicketStorageAdjustReasonManageMapper ticketStorageAdjustReasonManageMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageAdjustReasonManage po = this.getReqAttribute(request, CommandConstant.COMMAND_MODIFY);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "ID为" + po.getAdjust_id() + "的";
        if (CharUtil.getDBLenth(po.getAdjust_reason()) > 20) {
            rmsg.addMessage("调账原因最大值不能超过20位(中文字符为两位)");
            return rmsg;
        } else if (this.existName(po, ticketStorageAdjustReasonManageMapper)) {
            rmsg.addMessage("调账原因记录已存在！");
            return rmsg;
        } else {
            try {
                n = this.modifyByTrans(request, ticketStorageAdjustReasonManageMapper, po);
            } catch (Exception e) {
                return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
            }
            logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), opLogMapper);
        }
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private TicketStorageAdjustReasonManage getQueryConditionForOp(HttpServletRequest request) {
        TicketStorageAdjustReasonManage qCon = new TicketStorageAdjustReasonManage();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            qCon.setAdjust_id(FormUtil.getParameter(request, "d_adjustId"));
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
        }
        return qCon;
    }

    private int modifyByTrans(HttpServletRequest request, TicketStorageAdjustReasonManageMapper ticketStorageAdjustReasonManageMapper, TicketStorageAdjustReasonManage po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = ticketStorageAdjustReasonManageMapper.modifyTicketStorageAdjustReasonManage(po);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private OperationResult delete(HttpServletRequest request, TicketStorageAdjustReasonManageMapper ticketStorageAdjustReasonManageMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageAdjustReasonManage prmVo = this.getReqAttribute(request, CommandConstant.COMMAND_DELETE);
        Vector<TicketStorageAdjustReasonManage> pos = this.getReqAttributeForDelete(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            n = this.deleteByTrans(request, ticketStorageAdjustReasonManageMapper, pos, prmVo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), operationLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    private Vector<TicketStorageAdjustReasonManage> getReqAttributeForDelete(HttpServletRequest request) {
        TicketStorageAdjustReasonManage po = new TicketStorageAdjustReasonManage();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageAdjustReasonManage> selectedItems = this.getDeleteIDs(selectIds);
        return selectedItems;
    }

    private Vector<TicketStorageAdjustReasonManage> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageAdjustReasonManage> list = new Vector();
        TicketStorageAdjustReasonManage po;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            po = this.getTicketStorageAdjustReasonManages(strIds, "#");
            list.add(po);
        }
        return list;
    }

    private TicketStorageAdjustReasonManage getTicketStorageAdjustReasonManages(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageAdjustReasonManage po = new TicketStorageAdjustReasonManage();

        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                po.setAdjust_id(tmp);
                continue;
            }
        }
        return po;
    }

    private int deleteByTrans(HttpServletRequest request, TicketStorageAdjustReasonManageMapper ticketStorageAdjustReasonManageMapper, Vector<TicketStorageAdjustReasonManage> pos, TicketStorageAdjustReasonManage prmVo) {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (TicketStorageAdjustReasonManage po : pos) {
                n += ticketStorageAdjustReasonManageMapper.deleteTicketStorageAdjustReasonManage(po);
            }
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private OperationResult add(HttpServletRequest request, TicketStorageAdjustReasonManageMapper ticketStorageAdjustReasonManageMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageAdjustReasonManage po = this.getReqAttribute(request, CommandConstant.COMMAND_ADD);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "ID为" + po.getAdjust_id() + "的";
        if (CharUtil.getDBLenth(po.getAdjust_reason()) > 20) {
            rmsg.addMessage("调账原因最大值不能超过20位(中文字符为两位)");
            return rmsg;
        } else {
            try {
                if (this.existRecord(po, ticketStorageAdjustReasonManageMapper)) {
                    rmsg.addMessage(preMsg + "记录已存在！");
                    return rmsg;
                } else {
                    if (this.existName(po, ticketStorageAdjustReasonManageMapper)) {
                        rmsg.addMessage("调账原因已存在！");
                        return rmsg;
                    }
                }

                n = this.addByTrans(request, ticketStorageAdjustReasonManageMapper, po);

            } catch (Exception e) {
                return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, operationLogMapper);
            }
            logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), operationLogMapper);
        }
        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    private boolean existRecord(TicketStorageAdjustReasonManage vo, TicketStorageAdjustReasonManageMapper ticketStorageAdjustReasonManageMapper) {
        List<TicketStorageAdjustReasonManage> list = ticketStorageAdjustReasonManageMapper.queryTicketStorageAdjustReasonManageById(vo);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean existName(TicketStorageAdjustReasonManage vo, TicketStorageAdjustReasonManageMapper ticketStorageAdjustReasonManageMapper) {
        List<TicketStorageAdjustReasonManage> list = ticketStorageAdjustReasonManageMapper.queryTicketStorageAdjustReasonManageByName(vo);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }

    private int addByTrans(HttpServletRequest request, TicketStorageAdjustReasonManageMapper ticketStorageAdjustReasonManageMapper, TicketStorageAdjustReasonManage vo) {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = ticketStorageAdjustReasonManageMapper.addTicketStorageAdjustReasonManage(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

}
