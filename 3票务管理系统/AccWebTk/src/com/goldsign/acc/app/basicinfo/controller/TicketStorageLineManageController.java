/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.controller;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageLineManage;
import com.goldsign.acc.app.basicinfo.mapper.TicketStorageLineManageMapper;
import com.goldsign.acc.frame.controller.BaseController;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.util.PageControlUtil;
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
 * @desc:票库线路
 * @author:taidb
 * @create date: 2017-7-28
 */
@Controller
public class TicketStorageLineManageController extends BaseController {

    @Autowired
    private TicketStorageLineManageMapper ticketStorageLineManageMapper;

    @RequestMapping("/ticketStorageLineManage")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/basicinfo/ticketStorageLineManage.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作
                {
                    opResult = this.modify(request, this.ticketStorageLineManageMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE))//删除操作
                {
                    opResult = this.delete(request, this.ticketStorageLineManageMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD))//增加操作
                {
                    opResult = this.add(request, this.ticketStorageLineManageMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.ticketStorageLineManageMapper, this.operationLogMapper);
                }
            }

            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)) {
                    if (!command.equals(CommandConstant.COMMAND_ADD)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                    }
                    this.queryForOp(request, this.ticketStorageLineManageMapper, this.operationLogMapper, opResult);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.baseHandler(request, response, mv);
        this.SaveOperationResult(mv, opResult);
        new PageControlUtil().putBuffer(request,opResult.getReturnResultSet());
        return mv;
    }

    private OperationResult query(HttpServletRequest request, TicketStorageLineManageMapper ticketStorageLineManageMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageLineManage queryCondition;
        List<TicketStorageLineManage> resultSet;
        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = ticketStorageLineManageMapper.getTicketStorageLineManage(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
    }

    public OperationResult queryForOp(HttpServletRequest request, TicketStorageLineManageMapper ticketStorageLineManageMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageLineManage vo;
        List<TicketStorageLineManage> resultSet;
        try {
            vo = this.getQueryConditionForOp(request);
            resultSet = ticketStorageLineManageMapper.getTicketStorageLineManage(vo);
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    private TicketStorageLineManage getQueryCondition(HttpServletRequest request) {
        TicketStorageLineManage qCon = new TicketStorageLineManage();
        
        return qCon;
    }

    private TicketStorageLineManage getReqAttribute(HttpServletRequest request, String type) {
        TicketStorageLineManage po = new TicketStorageLineManage();
        po.setLine_id(FormUtil.getParameter(request, "d_lineId"));
        po.setLine_name(FormUtil.getParameter(request, "d_lineName"));
        return po;
    }

    public OperationResult modify(HttpServletRequest request, TicketStorageLineManageMapper ticketStorageLineManageMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageLineManage po = this.getReqAttribute(request, CommandConstant.COMMAND_MODIFY);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        String preMsg = "ID为" + po.getLine_id() + "的";
        if (this.existName(po, ticketStorageLineManageMapper)) {
            rmsg.addMessage("票库线路名称记录已存在！");
            return rmsg;
        } else {
            try {
                n = this.modifyByTrans(request, ticketStorageLineManageMapper, po);
            } catch (Exception e) {
                return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
            }
            logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), opLogMapper);
        }
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private TicketStorageLineManage getQueryConditionForOp(HttpServletRequest request) {

        TicketStorageLineManage qCon = new TicketStorageLineManage();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            qCon.setLine_id(FormUtil.getParameter(request, "d_lineId"));
            qCon.setLine_name(FormUtil.getParameter(request, "d_lineName"));
          
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
        }
        return qCon;
    }

    private int modifyByTrans(HttpServletRequest request, TicketStorageLineManageMapper ticketStorageLineManageMapper, TicketStorageLineManage po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = ticketStorageLineManageMapper.modifyTicketStorageLineManage(po);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private OperationResult delete(HttpServletRequest request, TicketStorageLineManageMapper ticketStorageLineManageMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageLineManage tslmVo = this.getReqAttribute(request, CommandConstant.COMMAND_DELETE);
        Vector<TicketStorageLineManage> pos = this.getReqAttributeForDelete(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            n = this.deleteByTrans(request, ticketStorageLineManageMapper, pos, tslmVo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, operationLogMapper);
        }
        if(n == -1) {
            rmsg.addMessage("无法删除，该票库线路有关联的票库车站");
            return rmsg;
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), operationLogMapper);
        rmsg.addMessage(LogConstant.delSuccessMsg(n));        
        return rmsg;
    }

    private Vector<TicketStorageLineManage> getReqAttributeForDelete(HttpServletRequest request) {
        TicketStorageLineManage po = new TicketStorageLineManage();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageLineManage> selectedItems = this.getDeleteIDs(selectIds);
        return selectedItems;
    }

    private Vector<TicketStorageLineManage> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageLineManage> list = new Vector();
        TicketStorageLineManage po;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            po = this.getTicketStorageLineManages(strIds, "#");
            list.add(po);
        }
        return list;
    }

    private TicketStorageLineManage getTicketStorageLineManages(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageLineManage po = new TicketStorageLineManage();

        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                po.setLine_id(tmp);
                continue;
            }
        }
        return po;
    }

    private int deleteByTrans(HttpServletRequest request, TicketStorageLineManageMapper ticketStorageLineManageMapper, Vector<TicketStorageLineManage> pos, TicketStorageLineManage tslmVo) {
        TransactionStatus status = null;
        int n = 0;
        List<TicketStorageLineManage>  temp = null;
        try {
            status = txMgr.getTransaction(this.def);
            for (TicketStorageLineManage po : pos) {           
                temp = ticketStorageLineManageMapper.checkTicketStorageLineStationsManage(po);              
                if(temp.size() > 0) {
                    n = -1; //检查票库线路是否有对应的票库车站
                    break;
                }
            }
            if( n == -1) {
                return n;
            } else{
                for (TicketStorageLineManage po : pos) {
                    n += ticketStorageLineManageMapper.deleteTicketStorageLineManage(po);
                }
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

    private OperationResult add(HttpServletRequest request, TicketStorageLineManageMapper ticketStorageLineManageMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageLineManage po = this.getReqAttribute(request, CommandConstant.COMMAND_ADD);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        String preMsg = "ID为" + po.getLine_id() + "的";
	    try {
	        if (this.existRecord(po, ticketStorageLineManageMapper)) {
	            rmsg.addMessage(preMsg + "记录已存在！");
	            return rmsg;
	        } else {
	            if (this.existName(po, ticketStorageLineManageMapper)) {
	                rmsg.addMessage("票库线路名称已存在！");
	                return rmsg;
	            }
	        }
                n = this.addByTrans(request, ticketStorageLineManageMapper, po);
            } catch (Exception e) {
                return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, operationLogMapper);
            }
            logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), operationLogMapper);
        
        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    private boolean existRecord(TicketStorageLineManage vo, TicketStorageLineManageMapper ticketStorageLineManageMapper) {
        List<TicketStorageLineManage> list = ticketStorageLineManageMapper.getTicketStorageLineManageById(vo);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean existName(TicketStorageLineManage vo, TicketStorageLineManageMapper ticketStorageLineManageMapper) {
        List<TicketStorageLineManage> list = ticketStorageLineManageMapper.getTicketStorageLineManageByName(vo);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }

    private int addByTrans(HttpServletRequest request, TicketStorageLineManageMapper ticketStorageLineManageMapper, TicketStorageLineManage vo) {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = ticketStorageLineManageMapper.addTicketStorageLineManage(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }
    
    @RequestMapping("/TicketStorageLineManageExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.basicinfo.entity.TicketStorageLineManage");
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults
                = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response,
                ExpUtil.exportExcel(queryResults, request));
    }

}
