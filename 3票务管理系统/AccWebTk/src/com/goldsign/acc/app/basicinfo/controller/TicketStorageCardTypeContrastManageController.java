/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.controller;

import com.goldsign.acc.app.basicinfo.mapper.TicketStorageCardTypeContrastManageMapper;
import com.goldsign.acc.app.basicinfo.entity.TicketStorageCardTypeContrastManage;
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
 * @desc:票库票卡主类型
 * @author:mh
 * @create date: 2017-07-25
 */
@Controller
public class TicketStorageCardTypeContrastManageController extends StorageOutInBaseController {

    @Autowired
    private TicketStorageCardTypeContrastManageMapper mapper;

    @RequestMapping("/ticketStorageCardTypeContrastManage")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView("/jsp/basicinfo/ticketStorageCardTypeContrast.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.mapper, this.operationLogMapper);
                }
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

        String[] attrNames = {IC_CARD_MAIN, IC_CARD_SUB, IC_CARD_MAIN_SUB, AFC_CARD_MAIN, AFC_CARD_SUB, CARD_MAIN_SUB};
        this.setPageOptions(attrNames, mv, request, response);
        this.getResultSetText((List<TicketStorageCardTypeContrastManage>) opResult.getReturnResultSet(), mv);
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult);

        return mv;
    }

    private void getResultSetText(List<TicketStorageCardTypeContrastManage> resultSet, ModelAndView mv) throws Exception {
        List<PubFlag> ic_main_types = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_MAIN);
        List<PubFlag> ic_sub_types = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.IC_CARD_SUB);
        List<PubFlag> card_main_types = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.AFC_CARD_MAIN);
        List<PubFlag> card_sub_types = (List<PubFlag>) mv.getModelMap().get(StorageOutInBaseController.AFC_CARD_SUB);

        for (TicketStorageCardTypeContrastManage vo : resultSet) {

            if (ic_main_types != null && !ic_main_types.isEmpty()) {
                vo.setIc_main_typeText(DBUtil.getTextByCode(vo.getIc_main_type(), ic_main_types));
            }

            if (ic_sub_types != null && !ic_sub_types.isEmpty()) {
                vo.setIc_sub_typeText(DBUtil.getTextByCode(vo.getIc_sub_type(), vo.getIc_main_type(), ic_sub_types));
            }

            if (card_main_types != null && !card_main_types.isEmpty()) {
                vo.setCard_main_idText(DBUtil.getTextByCode(vo.getCard_main_type(), card_main_types));
            }

            if (card_sub_types != null && !card_sub_types.isEmpty()) {
                vo.setCard_sub_idText(DBUtil.getTextByCode(vo.getCard_sub_type(), vo.getCard_main_type(), card_sub_types));
            }
        }
    }

    private OperationResult query(HttpServletRequest request, TicketStorageCardTypeContrastManageMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageCardTypeContrastManage queryCondition;
        List<TicketStorageCardTypeContrastManage> resultSet;
        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = mapper.getTicketStorageCardTypeContrastManage(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
    }

    public OperationResult queryForOp(HttpServletRequest request, TicketStorageCardTypeContrastManageMapper mapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageCardTypeContrastManage vo;
        List<TicketStorageCardTypeContrastManage> resultSet;
        try {
            vo = this.getQueryConditionForOp(request);
            resultSet = mapper.getTicketStorageCardTypeContrastManage(vo);
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    private TicketStorageCardTypeContrastManage getQueryCondition(HttpServletRequest request) {
        TicketStorageCardTypeContrastManage qCon = new TicketStorageCardTypeContrastManage();
        qCon.setIc_main_type(FormUtil.getParameter(request, "q_icMainType"));
        qCon.setIc_sub_type(FormUtil.getParameter(request, "q_icSubType"));
        return qCon;
    }

    private TicketStorageCardTypeContrastManage getReqAttribute(HttpServletRequest request, String type) {
        TicketStorageCardTypeContrastManage po = new TicketStorageCardTypeContrastManage();
        po.setIc_main_type(FormUtil.getParameter(request, "d_icMainType"));
        po.setIc_sub_type(FormUtil.getParameter(request, "d_icSubType"));
        po.setCard_main_type(FormUtil.getParameter(request, "d_cardMainId"));
        po.setCard_sub_type(FormUtil.getParameter(request, "d_cardSubId"));
        po.setBox_unit(FormUtil.getParameter(request, "d_boxUnit"));
        po.setBase_flag(FormUtil.getParameter(request, "d_baseFlag"));
        return po;
    }

    public OperationResult modify(HttpServletRequest request, TicketStorageCardTypeContrastManageMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageCardTypeContrastManage po = this.getReqAttribute(request, CommandConstant.COMMAND_MODIFY);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        if (this.existStock(po, mapper)) {
            rmsg.addMessage("票卡类型的库存不为空！");
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

    private TicketStorageCardTypeContrastManage getQueryConditionForOp(HttpServletRequest request) {

        TicketStorageCardTypeContrastManage qCon = new TicketStorageCardTypeContrastManage();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            qCon.setIc_main_type(FormUtil.getParameter(request, "d_icMainType"));
            qCon.setIc_sub_type(FormUtil.getParameter(request, "d_icSubType"));
            qCon.setCard_main_type(FormUtil.getParameter(request, "d_cardMainId"));
            qCon.setCard_sub_type(FormUtil.getParameter(request, "d_cardSubId"));
            qCon.setBox_unit(FormUtil.getParameter(request, "d_boxUnit"));
            qCon.setBase_flag(FormUtil.getParameter(request, "d_baseFlag"));

        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
        }
        return qCon;
    }

    private int modifyByTrans(HttpServletRequest request, TicketStorageCardTypeContrastManageMapper mapper, TicketStorageCardTypeContrastManage po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = mapper.modifyTicketStorageCardTypeContrastManage(po);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private OperationResult delete(HttpServletRequest request, TicketStorageCardTypeContrastManageMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageCardTypeContrastManage prmVo = this.getReqAttribute(request, CommandConstant.COMMAND_DELETE);
        Vector<TicketStorageCardTypeContrastManage> pos = this.getReqAttributeForDelete(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            for (TicketStorageCardTypeContrastManage vo : pos) {
                if(this.existChest(vo, mapper)){
                    rmsg.addMessage("请先删除“票柜定义”中的“" + vo.getIc_sub_typeText() + "”记录，再删除此记录");
                    return rmsg;
                }
                if (this.existStock(vo, mapper)) {
                    rmsg.addMessage("票库票卡子类型为“" + vo.getIc_sub_typeText() + "”的库存不为空！");
                    return rmsg;
                }
            }
            n = this.deleteByTrans(request, mapper, pos, prmVo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request,  LogConstant.delSuccessMsg(n), operationLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }
    
    private boolean existChest(TicketStorageCardTypeContrastManage vo, TicketStorageCardTypeContrastManageMapper mapper) {
        List<TicketStorageCardTypeContrastManage> list = mapper.getTicketStorageCardTypeContrastManageByChect(vo);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }

    private Vector<TicketStorageCardTypeContrastManage> getReqAttributeForDelete(HttpServletRequest request) {
        TicketStorageCardTypeContrastManage po = new TicketStorageCardTypeContrastManage();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageCardTypeContrastManage> selectedItems = this.getDeleteIDs(selectIds);
        return selectedItems;
    }

    private Vector<TicketStorageCardTypeContrastManage> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageCardTypeContrastManage> list = new Vector();
        TicketStorageCardTypeContrastManage po;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            po = this.getTicketStorageCardTypeContrastManages(strIds, "#");
            list.add(po);
        }
        return list;
    }

    private TicketStorageCardTypeContrastManage getTicketStorageCardTypeContrastManages(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageCardTypeContrastManage po = new TicketStorageCardTypeContrastManage();

        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                po.setIc_main_type(tmp);
                continue;
            }
            if (i == 2) {
                po.setIc_sub_type(tmp);
                continue;
            }
            if (i == 3) {
                po.setIc_sub_typeText(tmp);
                continue;
            }
        }
        return po;
    }

    private int deleteByTrans(HttpServletRequest request, TicketStorageCardTypeContrastManageMapper mapper, Vector<TicketStorageCardTypeContrastManage> pos, TicketStorageCardTypeContrastManage prmVo) {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (TicketStorageCardTypeContrastManage po : pos) {
                n += mapper.deleteTicketStorageCardTypeContrastManage(po);
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

    private OperationResult add(HttpServletRequest request, TicketStorageCardTypeContrastManageMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageCardTypeContrastManage po = this.getReqAttribute(request, CommandConstant.COMMAND_ADD);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            if (this.existRecord(po, mapper)) {
                rmsg.addMessage("票卡类型已存在！");
                return rmsg;
            }
            if ((po.getBase_flag()).equals("1")) {
                if (this.existFlag(po, mapper)) {
                    rmsg.addMessage("该类型的基础标志已设置过，请设置为“0”！");
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

    private boolean existRecord(TicketStorageCardTypeContrastManage vo, TicketStorageCardTypeContrastManageMapper mapper) {
        List<TicketStorageCardTypeContrastManage> list = mapper.getTicketStorageCardTypeContrastManageById(vo);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean existFlag(TicketStorageCardTypeContrastManage vo, TicketStorageCardTypeContrastManageMapper mapper) {
        List<TicketStorageCardTypeContrastManage> list = mapper.getTicketStorageCardTypeContrastManageByFlag(vo);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean existStock(TicketStorageCardTypeContrastManage vo, TicketStorageCardTypeContrastManageMapper mapper) {
        List<TicketStorageCardTypeContrastManage> list = mapper.getTicketStorageCardTypeContrastManageByStock(vo);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }

    private int addByTrans(HttpServletRequest request, TicketStorageCardTypeContrastManageMapper mapper, TicketStorageCardTypeContrastManage vo) {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = mapper.addTicketStorageCardTypeContrastManage(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }
    
    @RequestMapping("/TicketStorageCardTypeContrastManageExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.basicinfo.entity.TicketStorageCardTypeContrastManage");
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults
                = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response,
                ExpUtil.exportExcel(queryResults, request));
    }
    
}
