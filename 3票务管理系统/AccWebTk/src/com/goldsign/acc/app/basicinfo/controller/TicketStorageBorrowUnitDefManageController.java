/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.controller;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageBorrowUnitDefManage;
import com.goldsign.acc.app.basicinfo.mapper.TicketStorageBorrowUnitDefManageMapper;
import com.goldsign.acc.app.storageout.controller.TicketStorageOutProduceParentController;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.util.ArrayList;
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
 * @author liudezeng
 */
@Controller
public class TicketStorageBorrowUnitDefManageController extends TicketStorageOutProduceParentController {

    @Autowired
    private TicketStorageBorrowUnitDefManageMapper tsbudmMapper;

    @RequestMapping(value = "/ticketStorageBorrowUnitDefManage")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/basicinfo/ticketStorageBorrowUnitDefManage.jsp");
        String command = request.getParameter("command");
        List<TicketStorageBorrowUnitDefManage> tsbudm = new ArrayList<TicketStorageBorrowUnitDefManage>();
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作
                {
                    opResult = this.modify(request, this.tsbudmMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE))//删除操作
                {
                    opResult = this.delete(request, this.tsbudmMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD))//增加操作
                {
                    opResult = this.add(request, this.tsbudmMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = queryByUpdate(request, this.tsbudmMapper, tsbudm,
                            this.operationLogMapper);
                }

            } else {

                opResult = queryByUpdate(request, this.tsbudmMapper, tsbudm,
                        this.operationLogMapper);

            }

            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)) {
                    if (!command.equals(CommandConstant.COMMAND_ADD)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                    }
                    this.queryForOp(request, this.tsbudmMapper, this.operationLogMapper, opResult);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }
        this.baseHandler(request, response, mv);
//        request.setAttribute("_divideShow","1"); 
//        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集
        return mv;
    }

    private OperationResult queryByUpdate(HttpServletRequest request, TicketStorageBorrowUnitDefManageMapper tsbudmMapper, List<TicketStorageBorrowUnitDefManage> tsbudm, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
//        this.getQueryConditionForOp(request);
        LogUtil logUtil = new LogUtil();
//        SystemRateContc po = new SystemRateContc();
        TicketStorageBorrowUnitDefManage queryCondition;
        List<TicketStorageBorrowUnitDefManage> resultSet;

        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = tsbudmMapper.getTicketStorageBorrowUnitDefManage(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);

        return or;
    }

    public OperationResult modify(HttpServletRequest request, TicketStorageBorrowUnitDefManageMapper tsbudmMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageBorrowUnitDefManage po = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            List<TicketStorageBorrowUnitDefManage> list = tsbudmMapper.getListForModify(po);
            if (list.size() != 0) {
                TicketStorageBorrowUnitDefManage list1 = new TicketStorageBorrowUnitDefManage();
                list1 = (TicketStorageBorrowUnitDefManage) list.get(0);
                if (list1.getUnit_name().equals(po.getUnit_name())) {
                    rmsg.addMessage("成功修改一条记录");
                    return rmsg;
                } else {
                    List list2 = tsbudmMapper.getListForModifyName(po);
                    if (list2.contains(po.getUnit_name())) {
                        rmsg.addMessage("修改失败，借票单位名称已存在！");
                        return rmsg;
                    }
                    List<String> list3 = tsbudmMapper.getOutLendBillName();
                    for (int i = 0; i < list3.size(); i++) {
                        String list4 = list3.get(i).trim();
                        if (list4.equals(po.getUnit_id())) {
                            rmsg.addMessage("修改失败，借票出库单据中存在该借票单位,请确认后修改。");
                            return rmsg;
                        }
                    }

                    n = this.modifyByTrans(request, tsbudmMapper, po);

                }
            }

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, LogConstant.modifySuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    public OperationResult delete(HttpServletRequest request, TicketStorageBorrowUnitDefManageMapper tsbudmMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<TicketStorageBorrowUnitDefManage> pos = this.getReqAttributeForDelete(request);
        TicketStorageBorrowUnitDefManage prmVo = new TicketStorageBorrowUnitDefManage();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            
            List<String> list3 = tsbudmMapper.getOutLendBillName();
            for(int j = 0;j<pos.size();j++){
                TicketStorageBorrowUnitDefManage po = pos.get(j);
                    for (int i = 0; i < list3.size(); i++) {
                        String list4 = list3.get(i).trim();
                        if (list4.equals(po.getUnit_id().trim())) {
                            rmsg.addMessage("删除失败，借票出库单据中存在要删除的借票单位,请确认后删除。");
                            return rmsg;
                        }
                    }
            }
            n = this.deleteByTrans(request, tsbudmMapper, pos, prmVo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    public OperationResult add(HttpServletRequest request, TicketStorageBorrowUnitDefManageMapper tsbudmMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageBorrowUnitDefManage po = this.getReqAttribute(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

//        String preMsg = "服务费率：" + "主键：" + po.getAgent_id()+ ":";
        try {
            if (this.existRecord(po, tsbudmMapper, opLogMapper)) {
                rmsg.addMessage("添加失败，借票单位代码已存在！");
                return rmsg;
            } else if (this.existRecordForAddName(po, tsbudmMapper, opLogMapper)) {
                rmsg.addMessage("添加失败，借票单位名称已存在！");
                return rmsg;
            }

            n = this.addByTrans(request, tsbudmMapper, po);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, LogConstant.addSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    public OperationResult queryForOp(HttpServletRequest request, TicketStorageBorrowUnitDefManageMapper tsbudmMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageBorrowUnitDefManage queryCondition;
        List<TicketStorageBorrowUnitDefManage> resultSet;

        try {
            queryCondition = this.getQueryConditionForOp(request);
            resultSet = tsbudmMapper.getBorrowUnitDefManageForAdd(queryCondition);
            opResult.setReturnResultSet(resultSet);
            //or.addMessage("成功查询" + resultSet.size() + "条记录"); 
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    public TicketStorageBorrowUnitDefManage getReqAttribute(HttpServletRequest request) {
        TicketStorageBorrowUnitDefManage po = new TicketStorageBorrowUnitDefManage();
        po.setUnit_id(FormUtil.getParameter(request, "d_unitID"));
        po.setUnit_name(FormUtil.getParameter(request, "d_unitName"));
        return po;
    }

    private int modifyByTrans(HttpServletRequest request, TicketStorageBorrowUnitDefManageMapper tsbudmMapper, TicketStorageBorrowUnitDefManage po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            n = tsbudmMapper.modifyTicketStorageBorrowUnitDefManage(po);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private int addByTrans(HttpServletRequest request, TicketStorageBorrowUnitDefManageMapper tsbudmMapper, TicketStorageBorrowUnitDefManage po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {

            status = txMgr.getTransaction(this.def);
            try {
                n = tsbudmMapper.addTicketStorageBorrowUnitDefManage(po);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            pvMapper.modifyPrmVersionForDraft(po);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private Vector<TicketStorageBorrowUnitDefManage> getReqAttributeForDelete(HttpServletRequest request) {
        TicketStorageBorrowUnitDefManage po = new TicketStorageBorrowUnitDefManage();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageBorrowUnitDefManage> selectedItems = this.getDeleteIDs(selectIds);
        return selectedItems;
    }

    private Vector<TicketStorageBorrowUnitDefManage> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageBorrowUnitDefManage> sds = new Vector();
        TicketStorageBorrowUnitDefManage sd;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            sd = this.getTicketStorageBorrowUnitDefManage(strIds, "#");
            sds.add(sd);
        }
        return sds;

    }

    private TicketStorageBorrowUnitDefManage getTicketStorageBorrowUnitDefManage(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageBorrowUnitDefManage sd = new TicketStorageBorrowUnitDefManage();
        Vector<TicketStorageBorrowUnitDefManage> sds = new Vector();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                sd.setUnit_id(tmp);
                continue;
            }
            if (i == 2) {
                sd.setUnit_name(tmp);
                continue;
            }

        }
        return sd;

    }

    private int deleteByTrans(HttpServletRequest request, TicketStorageBorrowUnitDefManageMapper tsbudmMapper, Vector<TicketStorageBorrowUnitDefManage> pos, TicketStorageBorrowUnitDefManage prmVo) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (TicketStorageBorrowUnitDefManage po : pos) {
                n += tsbudmMapper.deleteTicketStorageBorrowUnitDefManage(po);
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

    private TicketStorageBorrowUnitDefManage getQueryConditionForOp(HttpServletRequest request) {

        TicketStorageBorrowUnitDefManage qCon = new TicketStorageBorrowUnitDefManage();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;
            }
            qCon.setUnit_id(FormUtil.getParameter(request, "d_unitID"));
            qCon.setUnit_name(FormUtil.getParameter(request, "d_unitName"));
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
            }
        }

        return qCon;
    }

    private boolean existRecord(TicketStorageBorrowUnitDefManage po, TicketStorageBorrowUnitDefManageMapper tsbudmMapper, OperationLogMapper opLogMapper) {

        List<TicketStorageBorrowUnitDefManage> list = tsbudmMapper.getListForAdd(po);
        if (list.isEmpty()) {
            return false;
        }
        return true;

    }

    private boolean existRecordForAddName(TicketStorageBorrowUnitDefManage po, TicketStorageBorrowUnitDefManageMapper tsbudmMapper, OperationLogMapper opLogMapper) {
        List<TicketStorageBorrowUnitDefManage> list = tsbudmMapper.getListForAddName(po);
        if (list.isEmpty()) {
            return false;
        }
        return true;

    }

    //获取查询参数
    private TicketStorageBorrowUnitDefManage getQueryCondition(HttpServletRequest request) {
        TicketStorageBorrowUnitDefManage tsdsp = new TicketStorageBorrowUnitDefManage();
        String q_unit_id = FormUtil.getParameter(request, "q_unit_id");
        String q_unit_name = FormUtil.getParameter(request, "q_unit_name");

        tsdsp.setUnit_id(q_unit_id);
        tsdsp.setUnit_name(q_unit_name);

        return tsdsp;
    }

}
