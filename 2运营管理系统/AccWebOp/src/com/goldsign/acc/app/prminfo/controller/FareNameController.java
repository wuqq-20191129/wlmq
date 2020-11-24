/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.controller;

import com.goldsign.acc.app.prminfo.entity.FareName;
import com.goldsign.acc.app.prminfo.mapper.FareNameMapper;
import com.goldsign.acc.app.prminfo.mapper.PrmVersionMapper;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.controller.BaseController;

import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.CharUtil;
import com.goldsign.acc.frame.util.DBUtil;

import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author mqf
 */
@Controller
public class FareNameController extends BaseController {

    @Autowired
    private FareNameMapper fareNameMapper;


    @RequestMapping("/FareName")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mv = new ModelAndView("/jsp/prminfo/fare_name.jsp");

        String command = request.getParameter("command");
        if (command == null) {
            command = CommandConstant.COMMAND_QUERY;
        } 
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作
                {
                    opResult = this.modify(request, this.fareNameMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE))//删除操作
                {
                    opResult = this.delete(request, this.fareNameMapper, this.prmVersionMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD))//增加操作
                {
                    opResult = this.add(request, this.fareNameMapper, this.operationLogMapper);
                }

                if (command.equals(CommandConstant.COMMAND_QUERY) )//查询操作
                {
                    opResult = this.query(request, this.fareNameMapper, this.operationLogMapper);

                }
            }

            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) 
                        || command.equals(CommandConstant.COMMAND_MODIFY))
                    {
                    this.queryForOp(request, this.fareNameMapper, this.operationLogMapper, opResult);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);//返回结果集
        return mv;

    }


    public OperationResult query(HttpServletRequest request, FareNameMapper fnMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        FareName queryCondition;
        List<FareName> resultSet;

        try {
            resultSet = fnMapper.getFareNames();
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    public OperationResult queryForOp(HttpServletRequest request, FareNameMapper fnMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        // OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        FareName queryCondition;
        List<FareName> resultSet;

        try {
            resultSet = fnMapper.getFareNames();
            opResult.setReturnResultSet(resultSet);
            //or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    public OperationResult modify(HttpServletRequest request, FareNameMapper fnMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        FareName po = this.getReqAttribute(request);
      
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            if (this.existRecordByNameForModify(po, fnMapper)) {
                rmsg.addMessage("修改失败，收费区段名称已存在！");
                return rmsg;
            }
            n = this.modifyByTrans(request, fnMapper, po);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, LogConstant.modifySuccessMsg(n), opLogMapper);
        
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private boolean existRecordById(FareName po, FareNameMapper fnMapper) {
        List<FareName> list = fnMapper.getFareNameById(po);
        if (list.isEmpty()) {
            return false;
        }
        return true;

    }
    
    private boolean existRecordByName(FareName po, FareNameMapper fnMapper) {
        List<FareName> list = fnMapper.getFareNameByName(po);
        if (list.isEmpty()) {
            return false;
        }
        return true;

    }
    
    private boolean existRecordByNameForModify(FareName po, FareNameMapper fnMapper) {
        List<FareName> list = fnMapper.getFareNameByNameForModify(po);
        if (list.isEmpty()) {
            return false;
        }
        return true;

    }

    private int addByTrans(HttpServletRequest request, FareNameMapper fnMapper, FareName po) throws Exception {
//        DataSourceTransactionManager txMgr = null;
        TransactionStatus status = null;
        int n = 0;
        try {

//            txMgr = DBUtil.getDataSourceTransactionManager(request);
//            status = txMgr.getTransaction(DBUtil.getTransactionDefinition(request));
            status = txMgr.getTransaction(this.def);
            n = fnMapper.addFareName(po);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private int modifyByTrans(HttpServletRequest request, FareNameMapper fnMapper, FareName po) throws Exception {
//        DataSourceTransactionManager txMgr = null;
        TransactionStatus status = null;
        int n = 0;
        try {

//            txMgr = DBUtil.getDataSourceTransactionManager(request);
//            status = txMgr.getTransaction(DBUtil.getTransactionDefinition(request));
            status = txMgr.getTransaction(this.def);
            n = fnMapper.modifyFareName(po);

            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private int deleteByTrans(HttpServletRequest request, FareNameMapper fnMapper, Vector<FareName> pos) throws Exception {

        TransactionStatus status = null;
        int n = 0;
        try {


            status = txMgr.getTransaction(this.def);
            for (FareName po : pos) {
                n += fnMapper.deleteFareName(po);
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

    public OperationResult add(HttpServletRequest request, FareNameMapper fnMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        FareName po = this.getReqAttribute(request);
       // User user = (User) request.getSession().getAttribute("User");
        // String operatorID = user.getAccount();
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            if (this.existRecordById(po, fnMapper)) {
                rmsg.addMessage("增加失败，收费区段ID已存在！");
                return rmsg;
            }
            if (this.existRecordByName(po, fnMapper)) {
                rmsg.addMessage("增加失败，收费区段名称已存在！");
                return rmsg;
            }

            n = this.addByTrans(request, fnMapper, po);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, LogConstant.addSuccessMsg(n), opLogMapper);
        
        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    public OperationResult delete(HttpServletRequest request, FareNameMapper fnMapper, PrmVersionMapper pvMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<FareName> pos = this.getReqAttributeForDelete(request);
        
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            if (this.existFareTableRecord(pos, fnMapper)) {
                rmsg.addMessage("删除失败，票价表有关联数据！");
                return rmsg;
            }
            if (this.existFareZoneRecord(pos, fnMapper)) {
                rmsg.addMessage("删除失败，收费区段有关联数据！");
                return rmsg;
            }
            
            n = this.deleteByTrans(request, fnMapper, pos);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    public FareName getReqAttribute(HttpServletRequest request) {
        FareName po = new FareName();

        
        po.setFare_id(FormUtil.getParameter(request, "d_fare_id").trim());
        po.setFare_name(FormUtil.getParameter(request, "d_fare_name").trim());
       
        return po;
    }

    private Vector<FareName> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<FareName> fns = new Vector();
        FareName fn;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            fn = this.getFareName(strIds, "#");
            fns.add(fn);
        }
        return fns;

    }

    private FareName getFareName(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        FareName fn = new FareName();;
        Vector<FareName> fns = new Vector();
        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                fn.setFare_id(tmp);
                continue;
            }

        }
        return fn;

    }

    private Vector<FareName> getReqAttributeForDelete(HttpServletRequest request) {
        FareName po = new FareName();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<FareName> selectedItems = this.getDeleteIDs(selectIds);

        return selectedItems;
    }
    
    private boolean existFareZoneRecord(Vector<FareName> pos, FareNameMapper fnMapper) {
        int fareZoneCount = fnMapper.getFareZoneCount(pos);
        if (fareZoneCount <=0) {
            return false;
        }
        return true;

    }
    
    private boolean existFareTableRecord(Vector<FareName> pos, FareNameMapper fnMapper) {
        int fareTableCount = fnMapper.getFareTableCount(pos);
        if (fareTableCount <=0) {
            return false;
        }
        return true;

    }

}
