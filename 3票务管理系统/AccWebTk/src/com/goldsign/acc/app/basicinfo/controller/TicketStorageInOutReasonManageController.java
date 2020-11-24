/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.controller;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageInOutReasonManage;
import com.goldsign.acc.app.basicinfo.mapper.TicketStorageInOutReasonManageMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.controller.StorageOutInBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.CharUtil;
import com.goldsign.acc.frame.util.DBUtil;
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
public class TicketStorageInOutReasonManageController  extends StorageOutInBaseController{
    
    @Autowired
    private TicketStorageInOutReasonManageMapper ticketStorageInOutReasonManageMapper;
    
    @RequestMapping(value = "/ticketStorageInOutReasonManage")
    public ModelAndView service (HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        ModelAndView mv = new ModelAndView("/jsp/basicinfo/ticketStorageInOutReasonManage.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {         
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作
                {
                    opResult = this.modify(request, this.ticketStorageInOutReasonManageMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE))//删除操作
                {
                    opResult = this.delete(request, this.ticketStorageInOutReasonManageMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD))//增加操作
                {
                    opResult = this.add(request, this.ticketStorageInOutReasonManageMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.ticketStorageInOutReasonManageMapper, this.operationLogMapper);
                }
            }

            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)) {
                    if (!command.equals(CommandConstant.COMMAND_ADD)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                    }
                    this.queryForOp(request, this.ticketStorageInOutReasonManageMapper, this.operationLogMapper, opResult);
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }   
        String[] attrNames = {IN_OUT_TYPES, IN_OUT_SUB_TYPES, IN_OUT_MAIN_SUB_TYPES};
        this.setPageOptions(attrNames, mv, request, response);
        this.getResultSetText((List<TicketStorageInOutReasonManage>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.SaveOperationResult(mv, opResult);
        
        

        return mv;
    }
    private OperationResult query(HttpServletRequest request, TicketStorageInOutReasonManageMapper ticketStorageInOutReasonManageMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageInOutReasonManage queryCondition;
        List<TicketStorageInOutReasonManage> resultSet;
        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = ticketStorageInOutReasonManageMapper.queryTicketStorageInOutReasonManage(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
        
    }

    public OperationResult queryForOp(HttpServletRequest request, TicketStorageInOutReasonManageMapper ticketStorageInOutReasonManageMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageInOutReasonManage vo;
        List<TicketStorageInOutReasonManage> resultSet;
        try {
            vo = this.getQueryConditionForOp(request);
            resultSet = ticketStorageInOutReasonManageMapper.queryTicketStorageInOutReasonManage(vo);
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;
    }

    private TicketStorageInOutReasonManage getQueryCondition(HttpServletRequest request) {
        TicketStorageInOutReasonManage qCon = new TicketStorageInOutReasonManage(); 
//        qCon.setEs_worktype_id(FormUtil.getParameter(request, "q_esWorkType")); 
        qCon.setIn_out_flag(FormUtil.getParameter(request, "q_inOutFlag"));
        qCon.setIn_out_sub_type(FormUtil.getParameter(request, "q_inOutSubType"));
        qCon.setReason_describe(FormUtil.getParameter(request, "q_reasonDescribe"));
        return qCon;
    }
    
    private TicketStorageInOutReasonManage getQueryConditionForOp(HttpServletRequest request) {
        TicketStorageInOutReasonManage qCon = new TicketStorageInOutReasonManage();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            qCon.setReason_id(FormUtil.getParameter(request, "d_reasonId"));
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if(!vQueryControlDefaultValues.isEmpty()){
                qCon.setIn_out_flag(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_inOutFlag"));
                qCon.setIn_out_sub_type(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_inOutSubType"));
                qCon.setReason_describe(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_reasonDescribe"));
            }
        }
        return qCon;
    }

    private TicketStorageInOutReasonManage getReqAttribute(HttpServletRequest request, String type) {
        TicketStorageInOutReasonManage po = new TicketStorageInOutReasonManage();
        po.setReason_id(FormUtil.getParameter(request, "d_reasonId"));
        po.setReason_describe(FormUtil.getParameter(request, "d_reasonDescribe"));
        po.setIn_out_flag(FormUtil.getParameter(request, "d_inOutFlag"));
        po.setEs_worktype_id(FormUtil.getParameter(request, "d_esWorkType"));
        return po;
    }

    public OperationResult modify(HttpServletRequest request, TicketStorageInOutReasonManageMapper ticketStorageInOutReasonManageMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageInOutReasonManage po = this.getReqAttribute(request, CommandConstant.COMMAND_MODIFY);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "ID为" + po.getReason_id()+ "的";
        if (CharUtil.getDBLenth(po.getReason_describe()) > 60) {
            rmsg.addMessage("原因描述最大值不能超过60位(中文字符为两位)");
            return rmsg;
        } else if (this.existName(po, ticketStorageInOutReasonManageMapper)) {
            rmsg.addMessage("原因描述记录已存在！");
            return rmsg;
        } else {
            try {
                n = this.modifyByTrans(request, ticketStorageInOutReasonManageMapper, po);
            } catch (Exception e) {
                return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
            }
            logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), opLogMapper);
        }
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

   
    private int modifyByTrans(HttpServletRequest request, TicketStorageInOutReasonManageMapper ticketStorageInOutReasonManageMapper, TicketStorageInOutReasonManage po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = ticketStorageInOutReasonManageMapper.modifyTicketStorageInOutReasonManage(po);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private OperationResult delete(HttpServletRequest request, TicketStorageInOutReasonManageMapper ticketStorageInOutReasonManageMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageInOutReasonManage prmVo = this.getReqAttribute(request, CommandConstant.COMMAND_DELETE);
        Vector<TicketStorageInOutReasonManage> pos = this.getReqAttributeForDelete(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            n = this.deleteByTrans(request, ticketStorageInOutReasonManageMapper, pos, prmVo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), operationLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    private Vector<TicketStorageInOutReasonManage> getReqAttributeForDelete(HttpServletRequest request) {
        TicketStorageInOutReasonManage po = new TicketStorageInOutReasonManage();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageInOutReasonManage> selectedItems = this.getDeleteIDs(selectIds);
        return selectedItems;
    }

    private Vector<TicketStorageInOutReasonManage> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageInOutReasonManage> list = new Vector();
        TicketStorageInOutReasonManage po;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            po = this.getTicketStorageInOutReasonManages(strIds, "#");
            list.add(po);
        }
        return list;
    }

    private TicketStorageInOutReasonManage getTicketStorageInOutReasonManages(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageInOutReasonManage po = new TicketStorageInOutReasonManage();

        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                po.setReason_id(tmp);
                continue;
            }
        }
        return po;
    }

    private int deleteByTrans(HttpServletRequest request, TicketStorageInOutReasonManageMapper ticketStorageInOutReasonManageMapper, Vector<TicketStorageInOutReasonManage> pos, TicketStorageInOutReasonManage prmVo) {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (TicketStorageInOutReasonManage po : pos) {
                n += ticketStorageInOutReasonManageMapper.deleteTicketStorageInOutReasonManage(po);
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

    private OperationResult add(HttpServletRequest request, TicketStorageInOutReasonManageMapper ticketStorageInOutReasonManageMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageInOutReasonManage po = this.getReqAttribute(request, CommandConstant.COMMAND_ADD);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "ID为" + po.getReason_id() + "的";
        if (CharUtil.getDBLenth(po.getReason_describe()) > 60) {
            rmsg.addMessage("原因描述最大值不能超过60位(中文字符为两位)");
            return rmsg;
        } else {
            try {
                if (this.existRecord(po, ticketStorageInOutReasonManageMapper)) {
                    rmsg.addMessage(preMsg + "记录已存在！");
                    return rmsg;
                } else {
                    if (this.existName(po, ticketStorageInOutReasonManageMapper)) {
                        rmsg.addMessage("原因描述已存在！");
                        return rmsg;
                    }
                }

                n = this.addByTrans(request, ticketStorageInOutReasonManageMapper, po);

            } catch (Exception e) {
                return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, operationLogMapper);
            }
            logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), operationLogMapper);
        }
        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    private boolean existRecord(TicketStorageInOutReasonManage vo, TicketStorageInOutReasonManageMapper ticketStorageInOutReasonManageMapper) {
        List<TicketStorageInOutReasonManage> list = ticketStorageInOutReasonManageMapper.queryTicketStorageInOutReasonManageById(vo);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean existName(TicketStorageInOutReasonManage vo, TicketStorageInOutReasonManageMapper ticketStorageInOutReasonManageMapper) {
        List<TicketStorageInOutReasonManage> list = ticketStorageInOutReasonManageMapper.queryTicketStorageInOutReasonManageByName(vo);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }

    private int addByTrans(HttpServletRequest request, TicketStorageInOutReasonManageMapper ticketStorageInOutReasonManageMapper, TicketStorageInOutReasonManage vo) {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = ticketStorageInOutReasonManageMapper.addTicketStorageInOutReasonManage(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }
   
//    private String[] getReqAttributeNames(HttpServletRequest request, String command) {        
//        String[] attrNames = {IN_OUT_TYPES, ES_WORK_TYPES};
//        return attrNames;
//    }

       private void getResultSetText(List<TicketStorageInOutReasonManage> resultSet, ModelAndView mv) throws Exception {
        List<PubFlag> in_out_types = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IN_OUT_TYPES);
        List<PubFlag> in_out_sub_types = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IN_OUT_SUB_TYPES);
        

        for (TicketStorageInOutReasonManage vo : resultSet) {

            if (in_out_types != null && !in_out_types.isEmpty()) {
                vo.setIn_out_desc(DBUtil.getTextByCode(vo.getIn_out_flag(), in_out_types));
            }

            if (in_out_sub_types != null && !in_out_sub_types.isEmpty()) {
                vo.setIn_out_sub_type_desc(DBUtil.getTextByCode(vo.getIn_out_sub_type(), vo.getIn_out_flag(), in_out_sub_types));
            }
 
        }
    }
    
}
