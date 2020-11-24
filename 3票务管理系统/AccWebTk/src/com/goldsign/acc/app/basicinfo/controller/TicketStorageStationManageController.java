/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.controller;


import com.goldsign.acc.app.basicinfo.mapper.TicketStorageStationManageMapper;
import com.goldsign.acc.app.basicinfo.entity.TicketStorageStationManage;
import com.goldsign.acc.frame.controller.StorageOutInBaseController;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;
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
 * @desc:票库车站
 * @author:taidb
 * @create date: 2017-7-29
 */
@Controller
public class TicketStorageStationManageController extends StorageOutInBaseController {

    @Autowired
    private TicketStorageStationManageMapper ticketStorageStationManageMapper;

    @RequestMapping("/ticketStorageStationManage")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView("/jsp/basicinfo/ticketStorageStationManage.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作
                {
                    opResult = this.modify(request, this.ticketStorageStationManageMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE))//删除操作
                {
                    opResult = this.delete(request, this.ticketStorageStationManageMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD))//增加操作
                {
                    opResult = this.add(request, this.ticketStorageStationManageMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.ticketStorageStationManageMapper, this.operationLogMapper);
                }
            }

            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)) {
                    if (!command.equals(CommandConstant.COMMAND_ADD)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                    }
                    this.queryForOp(request, this.ticketStorageStationManageMapper, this.operationLogMapper, opResult);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
	    opResult.addMessage(e.getMessage());
        }

        String[] attrNames = {IC_LINES};
        this.setPageOptions(attrNames, mv, request, response);
        this.getResultSetText((List<TicketStorageStationManage>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
	
        this.SaveOperationResult(mv, opResult);
        new PageControlUtil().putBuffer(request,opResult.getReturnResultSet());

        return mv;
    }

    private void getResultSetText(List<TicketStorageStationManage> resultSet, ModelAndView mv) throws Exception {
        List<PubFlag> icLines = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_LINES);

        for (TicketStorageStationManage vo : resultSet) {
            if (icLines != null && !icLines.isEmpty()) {
                vo.setPub_lineText(DBUtil.getTextByCode(vo.getLine_id(), icLines));
            }
        }
    }
    private OperationResult query(HttpServletRequest request, TicketStorageStationManageMapper ticketStorageStationManageMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageStationManage queryCondition;
        List<TicketStorageStationManage> resultSet;
        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = ticketStorageStationManageMapper.getTicketStorageStationManage(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
    }

    public OperationResult queryForOp(HttpServletRequest request, TicketStorageStationManageMapper ticketStorageStationManageMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageStationManage vo;
        List<TicketStorageStationManage> resultSet;
        try {
            vo = this.getQueryConditionForOp(request);
            resultSet = ticketStorageStationManageMapper.getTicketStorageStationManage(vo);
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    private TicketStorageStationManage getQueryCondition(HttpServletRequest request) {
        TicketStorageStationManage qCon = new TicketStorageStationManage();
        return qCon;
    }

    private TicketStorageStationManage getReqAttribute(HttpServletRequest request, String type) {
        TicketStorageStationManage po = new TicketStorageStationManage();
        po.setLine_id(FormUtil.getParameter(request, "d_lineId"));
        po.setStation_id(FormUtil.getParameter(request, "d_stationId"));
        po.setChinese_name(FormUtil.getParameter(request, "d_chineseName"));
        return po;
    }

    public OperationResult modify(HttpServletRequest request, TicketStorageStationManageMapper ticketStorageStationManageMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageStationManage po = this.getReqAttribute(request, CommandConstant.COMMAND_MODIFY);
        LogUtil logUtil = new LogUtil();
        int n = 0;


	if (this.existName(po, ticketStorageStationManageMapper)) {
            rmsg.addMessage("票库车站名称记录已存在！");
            return rmsg;
        } else {
            try {
                n = this.modifyByTrans(request, ticketStorageStationManageMapper, po);
            } catch (Exception e) {
                return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
            }
            logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, LogConstant.modifySuccessMsg(n), opLogMapper);
        }
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private TicketStorageStationManage getQueryConditionForOp(HttpServletRequest request) {

        TicketStorageStationManage qCon = new TicketStorageStationManage();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            qCon.setLine_id(FormUtil.getParameter(request, "d_lineId"));
            qCon.setStation_id(FormUtil.getParameter(request, "d_stationId"));
            qCon.setChinese_name(FormUtil.getParameter(request, "d_chineseName"));
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
        }
        return qCon;
    }

    private int modifyByTrans(HttpServletRequest request, TicketStorageStationManageMapper ticketStorageStationManageMapper, TicketStorageStationManage po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = ticketStorageStationManageMapper.modifyTicketStorageStationManage(po);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private OperationResult delete(HttpServletRequest request, TicketStorageStationManageMapper ticketStorageStationManageMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageStationManage tslmVo = this.getReqAttribute(request, CommandConstant.COMMAND_DELETE);
        Vector<TicketStorageStationManage> pos = this.getReqAttributeForDelete(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            n = this.deleteByTrans(request, ticketStorageStationManageMapper, pos, tslmVo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, operationLogMapper);
        }
        if(n == -1) {
            rmsg.addMessage("无法删除，该票库车站关联车站对照表");
            return rmsg;
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), operationLogMapper);
        rmsg.addMessage(LogConstant.delSuccessMsg(n));
        return rmsg;
    }

    private Vector<TicketStorageStationManage> getReqAttributeForDelete(HttpServletRequest request) {
        TicketStorageStationManage po = new TicketStorageStationManage();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageStationManage> selectedItems = this.getDeleteIDs(selectIds);
        return selectedItems;
    }

    private Vector<TicketStorageStationManage> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageStationManage> list = new Vector();
        TicketStorageStationManage po;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            po = this.getTicketStorageStationManages(strIds, "#");
            list.add(po);
        }
        return list;
    }

    private TicketStorageStationManage getTicketStorageStationManages(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageStationManage po = new TicketStorageStationManage();

        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                po.setLine_id(tmp);
                continue;
            }
            if (i == 2) {
                po.setLine_name(tmp);
                continue;
            }
            if (i == 3) {
                po.setStation_id(tmp);
                continue;
            }
            if (i == 4) {
                po.setChinese_name(tmp);
                continue;
            }
        }
        return po;
    }

    private int deleteByTrans(HttpServletRequest request, TicketStorageStationManageMapper ticketStorageStationManageMapper, Vector<TicketStorageStationManage> pos, TicketStorageStationManage tslmVo) {
        TransactionStatus status = null;
        int n = 0;
        List<TicketStorageStationManage> temp = null;
        try {
            status = txMgr.getTransaction(this.def);
            for (TicketStorageStationManage po : pos) {
                temp = ticketStorageStationManageMapper.checkTicketStationContrastManage(po);
                if(temp.size() > 0) {
                    n = -1;
                    break;//检查票库车站在车站对照表有无关联的票库车站
                }
            }
            if(n == -1) {
                return n;
            } else{
                for (TicketStorageStationManage po : pos) {
                n += ticketStorageStationManageMapper.deleteTicketStorageStationManage(po);
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

    private OperationResult add(HttpServletRequest request, TicketStorageStationManageMapper ticketStorageStationManageMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageStationManage po = this.getReqAttribute(request, CommandConstant.COMMAND_ADD);
        LogUtil logUtil = new LogUtil();
        int n = 0;


    	try {
            if (this.existRecord(po, ticketStorageStationManageMapper)) {
            	rmsg.addMessage("票务车站记录已存在！");
                return rmsg;
            } else {
                if (this.existName(po, ticketStorageStationManageMapper)) {
                    rmsg.addMessage("该线路已存在相同的票务车站中文名称！");
                    return rmsg;
                }
            }

            n = this.addByTrans(request, ticketStorageStationManageMapper, po);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, LogConstant.addSuccessMsg(n), operationLogMapper);
        
        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    private boolean existRecord(TicketStorageStationManage vo, TicketStorageStationManageMapper ticketStorageStationManageMapper) {
        List<TicketStorageStationManage> list = ticketStorageStationManageMapper.getTicketStorageStationManageById(vo);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean existName(TicketStorageStationManage vo, TicketStorageStationManageMapper mapper) {
        List<TicketStorageStationManage> list = mapper.getTicketStorageStationManageByName(vo);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean existDefinition(TicketStorageStationManage vo, TicketStorageStationManageMapper mapper) {
        List<TicketStorageStationManage> list = mapper.getTicketStorageStationManageByName(vo);
        if (list.isEmpty()) {
            return false;
        } else {
            List<TicketStorageStationManage> l = mapper.getTicketStorageStationManageByDefinition(vo);
            if (!l.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private int addByTrans(HttpServletRequest request, TicketStorageStationManageMapper ticketStorageStationManageMapper, TicketStorageStationManage vo) {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = ticketStorageStationManageMapper.addTicketStorageStationManage(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }
    
    @RequestMapping("/TicketStorageStationManageExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.basicinfo.entity.TicketStorageStationManage");
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults
                = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response,
                ExpUtil.exportExcel(queryResults, request));
    }

}