/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.controller;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageCodFactory;
import com.goldsign.acc.app.basicinfo.mapper.TicketStorageCodFactoryMapper;
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
public class TicketStorageCodFactoryController extends BaseController {
    @Autowired
    private TicketStorageCodFactoryMapper  icCodFactoryMapper;
    
    @RequestMapping(value = "/icCodFactory")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response)
    {
        ModelAndView mv = new ModelAndView("/jsp/basicinfo/ticketStorageCodFactory.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {         
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作
                {
                    opResult = this.modify(request, this.icCodFactoryMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE))//删除操作
                {
                    opResult = this.delete(request, this.icCodFactoryMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD))//增加操作
                {
                    opResult = this.add(request, this.icCodFactoryMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.icCodFactoryMapper, this.operationLogMapper);
                }
            }

            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)) {
                    if (!command.equals(CommandConstant.COMMAND_ADD)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                    }
                    this.queryForOp(request, this.icCodFactoryMapper, this.operationLogMapper, opResult);
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        
        this.baseHandler(request, response, mv);
        this.SaveOperationResult(mv, opResult);

        return mv;
    }
    
    private OperationResult query(HttpServletRequest request, TicketStorageCodFactoryMapper icCodFactoryMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        TicketStorageCodFactory queryCondition;
        List<TicketStorageCodFactory> resultSet;
        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = icCodFactoryMapper.queryIcCodFactory(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
        
    }
    
    private TicketStorageCodFactory getQueryCondition(HttpServletRequest request) {
        TicketStorageCodFactory qCon = new TicketStorageCodFactory();
        qCon.setFactory_name(FormUtil.getParameter(request, "q_factoryName"));
        return qCon;
    }
    
    public OperationResult queryForOp(HttpServletRequest request, TicketStorageCodFactoryMapper icCodFactoryMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        TicketStorageCodFactory vo;
        List<TicketStorageCodFactory> resultSet;
        try {
            vo = this.getQueryConditionForOp(request);
            resultSet = icCodFactoryMapper.queryIcCodFactory(vo);
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;
    }

    private TicketStorageCodFactory getReqAttribute(HttpServletRequest request, String type) {
        TicketStorageCodFactory po = new TicketStorageCodFactory();
        po.setFactory_code(FormUtil.getParameter(request, "d_factoryCode"));
        po.setFactory_name(FormUtil.getParameter(request, "d_factoryName"));
        po.setFactory_description(FormUtil.getParameter(request, "d_factoryDescription"));
        return po;
    }

    public OperationResult modify(HttpServletRequest request, TicketStorageCodFactoryMapper icCodFactoryMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageCodFactory po = this.getReqAttribute(request, CommandConstant.COMMAND_MODIFY);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "ID为" + po.getFactory_code() + "的";
        if (CharUtil.getDBLenth(po.getFactory_description()) > 100) {
            rmsg.addMessage("代码描述最大值不能超过100位(中文字符为两位)");
            return rmsg;
        } else {
            try {
                if (this.existName(po, icCodFactoryMapper)) {
                    rmsg.addMessage("修改失败！代码名称为" + "\"" + po.getFactory_name()+ "\"" +"已经定义！");
                    return rmsg;
                }
                n = this.modifyByTrans(request, icCodFactoryMapper, po);
            } catch (Exception e) {
                return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
            }
            logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), opLogMapper);
        }
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private TicketStorageCodFactory getQueryConditionForOp(HttpServletRequest request) {
        TicketStorageCodFactory qCon = new TicketStorageCodFactory();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;
            }
            qCon.setFactory_code(FormUtil.getParameter(request, "d_factoryCode"));
            qCon.setFactory_name(FormUtil.getParameter(request, "d_factoryName"));
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
        }
        return qCon;
    }

    private int modifyByTrans(HttpServletRequest request, TicketStorageCodFactoryMapper icCodFactoryMapper, TicketStorageCodFactory po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = icCodFactoryMapper.modifyIcCodFactory(po);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private OperationResult delete(HttpServletRequest request, TicketStorageCodFactoryMapper icCodFactoryMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageCodFactory prmVo = this.getReqAttribute(request, CommandConstant.COMMAND_DELETE);
        Vector<TicketStorageCodFactory> pos = this.getReqAttributeForDelete(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            n = this.deleteByTrans(request, icCodFactoryMapper, pos, prmVo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), operationLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    private Vector<TicketStorageCodFactory> getReqAttributeForDelete(HttpServletRequest request) {
        TicketStorageCodFactory po = new TicketStorageCodFactory();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<TicketStorageCodFactory> selectedItems = this.getDeleteIDs(selectIds);
        return selectedItems;
    }

    private Vector<TicketStorageCodFactory> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<TicketStorageCodFactory> list = new Vector();
        TicketStorageCodFactory po;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            po = this.getTicketStorageCodFactorys(strIds, "#");
            list.add(po);
        }
        return list;
    }

    private TicketStorageCodFactory getTicketStorageCodFactorys(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        TicketStorageCodFactory po = new TicketStorageCodFactory();

        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                po.setFactory_code(tmp);
                continue;
            }
        }
        return po;
    }

    private int deleteByTrans(HttpServletRequest request, TicketStorageCodFactoryMapper icCodFactoryMapper, Vector<TicketStorageCodFactory> pos, TicketStorageCodFactory prmVo) {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (TicketStorageCodFactory po : pos) {
                n += icCodFactoryMapper.deleteIcCodFactory(po);
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

    private OperationResult add(HttpServletRequest request, TicketStorageCodFactoryMapper icCodFactoryMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        TicketStorageCodFactory po = this.getReqAttribute(request, CommandConstant.COMMAND_ADD);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "ID为" + po.getFactory_code()+ "的";
        if (CharUtil.getDBLenth(po.getFactory_description()) > 100) {
            rmsg.addMessage("最大值不能超过100位(中文字符为两位)");
            return rmsg;
        } else {
            try {
                if (this.existRecord(po, icCodFactoryMapper)) {
                    rmsg.addMessage(preMsg + "记录已存在！");
                    return rmsg;
                } 
                if (this.existNameForAdd(po, icCodFactoryMapper)) {
                    rmsg.addMessage("添加失败！代码名称为" + "\"" + po.getFactory_name()+ "\"" +"已经定义！");
                    return rmsg;
                }
                n = this.addByTrans(request, icCodFactoryMapper, po);

            } catch (Exception e) {
                return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, operationLogMapper);
            }
            logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), operationLogMapper);
        }
        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    private boolean existRecord(TicketStorageCodFactory vo, TicketStorageCodFactoryMapper icCodFactoryMapper) {
        List<TicketStorageCodFactory> list = icCodFactoryMapper.queryIcCodFactoryById(vo);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean existName(TicketStorageCodFactory vo, TicketStorageCodFactoryMapper icCodFactoryMapper) {
        List<TicketStorageCodFactory> list = icCodFactoryMapper.queryIcCodFactoryById(vo);
        if(list.size()!=0){
            TicketStorageCodFactory list1 = new TicketStorageCodFactory();
            list1 = (TicketStorageCodFactory) list.get(0);
            if(list1.getFactory_name().equals(vo.getFactory_name()))
            {
                return false;
            }
            else{
               List list2 = icCodFactoryMapper.queryIcCodFactoryByName(vo);
               if(list2.contains(vo.getFactory_name()))
               {
                   return true;
               }
               else {
                   return false;
               }
            }
        }
//        if (list.isEmpty()) {
//            return false;
//        }
        return false;
    }

    private int addByTrans(HttpServletRequest request, TicketStorageCodFactoryMapper icCodFactoryMapper, TicketStorageCodFactory vo) {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = icCodFactoryMapper.addIcCodFactory(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private boolean existNameForAdd(TicketStorageCodFactory po, TicketStorageCodFactoryMapper icCodFactoryMapper) {
        List<TicketStorageCodFactory> list = icCodFactoryMapper.getListForAddName(po);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }
}
