/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.controller;

import com.goldsign.acc.app.basicinfo.mapper.TicketStorageCardSubTypeManageMapper;
import com.goldsign.acc.app.basicinfo.entity.TicketStorageCardSubTypeManage;
import com.goldsign.acc.frame.controller.StorageOutInBaseController;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.DBUtil;
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
public class TicketStorageCardSubTypeManageController extends StorageOutInBaseController {

    @Autowired
    private TicketStorageCardSubTypeManageMapper mapper;

    @RequestMapping("/ticketStorageCardSubTypeManage")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView("/jsp/basicinfo/ticketStorageCardSubType.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作
                {
                    opResult = this.modify(request, this.mapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE))//删除操作
                {
                    opResult = this.delete(request, this.mapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD))//增加操作
                {
                    opResult = this.add(request, this.mapper, this.operationLogMapper);
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

        String[] attrNames = {IC_CARD_MAIN,IC_CARD_MAIN_SUB};
        this.setPageOptions(attrNames, mv, request, response);
        this.getResultSetText((List<TicketStorageCardSubTypeManage>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult);

        return mv;
    }

    private void getResultSetText(List<TicketStorageCardSubTypeManage> resultSet, ModelAndView mv) throws Exception {
        List<PubFlag> ic_card_mains = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_MAIN);

        for (TicketStorageCardSubTypeManage vo : resultSet) {
            if (ic_card_mains != null && !ic_card_mains.isEmpty()) {
                vo.setIc_card_mainText(DBUtil.getTextByCode(vo.getIc_main_type(), ic_card_mains));
            }
        }
    }

    private OperationResult query(HttpServletRequest request, TicketStorageCardSubTypeManageMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageCardSubTypeManage queryCondition;
        List<TicketStorageCardSubTypeManage> resultSet;
        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = mapper.getTicketStorageCardSubTypeManage(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
    }

    public OperationResult queryForOp(HttpServletRequest request, TicketStorageCardSubTypeManageMapper mapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageCardSubTypeManage vo;
        List<TicketStorageCardSubTypeManage> resultSet;
        try {
            vo = this.getQueryConditionForOp(request);
            resultSet = mapper.getTicketStorageCardSubTypeManage(vo);
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    private TicketStorageCardSubTypeManage getQueryCondition(HttpServletRequest request) {
        TicketStorageCardSubTypeManage qCon = new TicketStorageCardSubTypeManage();
        qCon.setIc_main_type(request.getParameter("q_ic_main_type"));
        qCon.setIc_sub_type(request.getParameter("q_ic_sub_type"));
        return qCon;
    }

    private TicketStorageCardSubTypeManage getReqAttribute(HttpServletRequest request, String type) {
        TicketStorageCardSubTypeManage po = new TicketStorageCardSubTypeManage();
        po.setIc_main_type(FormUtil.getParameter(request, "d_icMainType"));
        po.setIc_sub_type(FormUtil.getParameter(request, "d_icSubType"));
        po.setIc_sub_desc(FormUtil.getParameter(request, "d_icSubDesc"));
        return po;
    }

    public OperationResult modify(HttpServletRequest request, TicketStorageCardSubTypeManageMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageCardSubTypeManage po = this.getReqAttribute(request, CommandConstant.COMMAND_MODIFY);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        if (this.existDefinition(po, mapper)) {
            rmsg.addMessage("票卡子类型名称已存在！");
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

    private TicketStorageCardSubTypeManage getQueryConditionForOp(HttpServletRequest request) {

        TicketStorageCardSubTypeManage qCon = new TicketStorageCardSubTypeManage();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            qCon.setIc_main_type(FormUtil.getParameter(request, "d_icMainType"));
            qCon.setIc_sub_type(FormUtil.getParameter(request, "d_icSubType"));
            qCon.setIc_sub_desc(FormUtil.getParameter(request, "d_icSubDesc"));
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
        }
        return qCon;
    }

    private int modifyByTrans(HttpServletRequest request, TicketStorageCardSubTypeManageMapper mapper, TicketStorageCardSubTypeManage po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = mapper.modifyTicketStorageCardSubTypeManage(po);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private OperationResult delete(HttpServletRequest request, TicketStorageCardSubTypeManageMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageCardSubTypeManage prmVo = this.getReqAttribute(request, CommandConstant.COMMAND_DELETE);
        Vector<TicketStorageCardSubTypeManage> pos = this.getReqAttributeForDelete(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            for (TicketStorageCardSubTypeManage vo : pos) {
                if (this.existStock(vo, mapper)) {
                    rmsg.addMessage("票库票卡子类型为“"+vo.getIc_sub_desc()+"”的库存不为空，删除失败！");
                    return rmsg;
                }
                if (this.existContras(vo, mapper)) {
                    rmsg.addMessage("票卡类型对照表中存在“"+vo.getIc_sub_desc()+"”类型，删除失败！");
                    return rmsg;
                }
                if (this.existChest(vo, mapper)) {
                    rmsg.addMessage("票柜中存在“"+vo.getIc_sub_desc()+"”类型，删除失败！");
                    return rmsg;
                }
            }
            n = this.deleteByTrans(request, mapper, pos, prmVo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), operationLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    private Vector<TicketStorageCardSubTypeManage> getReqAttributeForDelete(HttpServletRequest request) {
        TicketStorageCardSubTypeManage po = new TicketStorageCardSubTypeManage();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageCardSubTypeManage> selectedItems = this.getDeleteIDs(selectIds);
        return selectedItems;
    }

    private Vector<TicketStorageCardSubTypeManage> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageCardSubTypeManage> list = new Vector();
        TicketStorageCardSubTypeManage po;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            po = this.getTicketStorageCardSubTypeManages(strIds, "#");
            list.add(po);
        }
        return list;
    }

    private TicketStorageCardSubTypeManage getTicketStorageCardSubTypeManages(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageCardSubTypeManage po = new TicketStorageCardSubTypeManage();

        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                po.setIc_main_type(tmp);
                continue;
            }
            if (i == 2) {
                po.setIc_main_desc(tmp);
                continue;
            }
            if (i == 3) {
                po.setIc_sub_type(tmp);
                continue;
            }
            if (i == 4) {
                po.setIc_sub_desc(tmp);
                continue;
            }
        }
        return po;
    }

    private int deleteByTrans(HttpServletRequest request, TicketStorageCardSubTypeManageMapper mapper, Vector<TicketStorageCardSubTypeManage> pos, TicketStorageCardSubTypeManage prmVo) {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (TicketStorageCardSubTypeManage po : pos) {
                n += mapper.deleteTicketStorageCardSubTypeManage(po);
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

    private OperationResult add(HttpServletRequest request, TicketStorageCardSubTypeManageMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageCardSubTypeManage po = this.getReqAttribute(request, CommandConstant.COMMAND_ADD);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            if (this.existRecord(po, mapper)) {
                rmsg.addMessage("票卡子类型已存在！");
                return rmsg;
            } else {
                if (this.existName(po, mapper)) {
                    rmsg.addMessage("票卡子类型名称已存在！");
                    return rmsg;
                }
            }

            n = this.addByTrans(request, mapper, po);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, LogConstant.addSuccessMsg(n), operationLogMapper);

        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    private boolean existRecord(TicketStorageCardSubTypeManage vo, TicketStorageCardSubTypeManageMapper mapper) {
        List<TicketStorageCardSubTypeManage> list = mapper.getTicketStorageCardSubTypeManageById(vo);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean existName(TicketStorageCardSubTypeManage vo, TicketStorageCardSubTypeManageMapper mapper) {
        List<TicketStorageCardSubTypeManage> list = mapper.getTicketStorageCardSubTypeManageByName(vo);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean existDefinition(TicketStorageCardSubTypeManage vo, TicketStorageCardSubTypeManageMapper mapper) {
        List<TicketStorageCardSubTypeManage> list = mapper.getTicketStorageCardSubTypeManageByName(vo);
        if (list.isEmpty()) {
            return false;
        } else {
            List<TicketStorageCardSubTypeManage> l = mapper.getTicketStorageCardSubTypeManageByDefinition(vo);
            if (!l.isEmpty()) {
                return false;
            }
        }
        return true;
    }
    
    private boolean existStock(TicketStorageCardSubTypeManage vo, TicketStorageCardSubTypeManageMapper mapper) {
        List<TicketStorageCardSubTypeManage> list = mapper.getTicketStorageCardSubTypeManageByStock(vo);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean existContras(TicketStorageCardSubTypeManage vo, TicketStorageCardSubTypeManageMapper mapper) {
        List<TicketStorageCardSubTypeManage> list = mapper.getTicketStorageCardSubTypeManageByContras(vo);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }
    
    private boolean existChest(TicketStorageCardSubTypeManage vo, TicketStorageCardSubTypeManageMapper mapper) {
        List<TicketStorageCardSubTypeManage> list = mapper.getTicketStorageCardSubTypeManageByChest(vo);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }
    
    private int addByTrans(HttpServletRequest request, TicketStorageCardSubTypeManageMapper mapper, TicketStorageCardSubTypeManage vo) {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = mapper.addTicketStorageCardSubTypeManage(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }
    
    @RequestMapping("/TicketStorageCardSubTypeManageExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.basicinfo.entity.TicketStorageCardSubTypeManage");
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults
                = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response,
                ExpUtil.exportExcel(queryResults, request));
    }

}
