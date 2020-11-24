/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.controller;

import com.goldsign.acc.app.prminfo.entity.Store;
import com.goldsign.acc.app.prminfo.mapper.StoreMapper;
import com.goldsign.acc.frame.controller.BaseController;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.CharUtil;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import com.goldsign.login.vo.User;
import java.util.ArrayList;
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
 * @desc:商户定义
 * @author:mh
 * @create date: 2017-6-15
 */
@Controller
public class StoreController extends BaseController {

    @Autowired
    private StoreMapper storeMapper;

    @RequestMapping("/Store")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/prminfo/store.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作
                {
                    opResult = this.modify(request, this.storeMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE))//删除操作
                {
                    opResult = this.delete(request, this.storeMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD))//增加操作
                {
                    opResult = this.add(request, this.storeMapper, this.operationLogMapper);
                }

                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.storeMapper, this.operationLogMapper);

                }
            } else {
                opResult = this.query(request, this.storeMapper, this.operationLogMapper);
            }

            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)) {
                    if (!command.equals(CommandConstant.COMMAND_ADD)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                    }
                    this.queryForOp(request, this.storeMapper, this.operationLogMapper, opResult);
                }
                if (command.equals(CommandConstant.COMMAND_NEXT) || command.equals(CommandConstant.COMMAND_NEXTEND) || command.equals(CommandConstant.COMMAND_BACK)
                        || command.equals(CommandConstant.COMMAND_BACKEND)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);

        return mv;
    }

    private OperationResult query(HttpServletRequest request, StoreMapper storeMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        Store queryCondition;
        List<Store> resultSet;
        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = storeMapper.getStore(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
    }

    public OperationResult queryForOp(HttpServletRequest request, StoreMapper storeMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        Store vo;
        List<Store> resultSet;
        try {
            vo = this.getQueryConditionForOp(request);
            resultSet = storeMapper.getStore(vo);
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    private Store getQueryCondition(HttpServletRequest request) {
        Store qCon = new Store();
        qCon.setStore_id(FormUtil.getParameter(request, "q_storeId"));
        qCon.setStore_name(FormUtil.getParameter(request, "q_storeName"));
        qCon.setRecord_flag("0");
        return qCon;
    }

    private Store getReqAttribute(HttpServletRequest request, String type) {
        Store po = new Store();
        po.setStore_id(FormUtil.getParameter(request, "d_storeId"));
        po.setStore_name(FormUtil.getParameter(request, "d_storeName"));
        po.setLink_man(FormUtil.getParameter(request, "d_linkMan"));
        po.setTel(FormUtil.getParameter(request, "d_tel"));
        po.setFax(FormUtil.getParameter(request, "d_fax"));
        po.setTerminal_no(FormUtil.getParameter(request, "d_terminalNo"));
        po.setAddress(FormUtil.getParameter(request, "d_address"));
        po.setRecord_flag("0");
        return po;
    }

    public OperationResult modify(HttpServletRequest request, StoreMapper storeMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Store po = this.getReqAttribute(request, CommandConstant.COMMAND_MODIFY);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "商户ID为" + po.getStore_id() + "的";
        if (CharUtil.getDBLenth(po.getStore_name()) > 50) {
            rmsg.addMessage("商户名称最大值不能超过50位(中文字符为两位)");
            return rmsg;
        } else if (this.existName(po, storeMapper)) {
            preMsg = "商户名称为" + po.getStore_name() + "的";
            rmsg.addMessage(preMsg + "记录已存在！");
            return rmsg;
        } else {
            try {
                n = this.modifyByTrans(request, storeMapper, po);
            } catch (Exception e) {
                return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
            }
            logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), opLogMapper);
        }
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private Store getQueryConditionForOp(HttpServletRequest request) {

        Store qCon = new Store();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            qCon.setStore_id(FormUtil.getParameter(request, "d_storeId"));
            qCon.setRecord_flag("0");
        } else {//操作处于非添加模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                qCon.setRecord_flag("0");
            }
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setStore_id(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "q_storeId"));
            }
        }
        return qCon;
    }

    private int modifyByTrans(HttpServletRequest request, StoreMapper storeMapper, Store po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = storeMapper.modifyStore(po);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private OperationResult delete(HttpServletRequest request, StoreMapper storeMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Store prmVo = this.getReqAttribute(request, CommandConstant.COMMAND_DELETE);
        Vector<Store> pos = this.getReqAttributeForDelete(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            n = this.deleteByTrans(request, storeMapper, pos, prmVo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), operationLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    private Vector<Store> getReqAttributeForDelete(HttpServletRequest request) {
        Store po = new Store();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<Store> selectedItems = this.getDeleteIDs(selectIds);
        return selectedItems;
    }

    private Vector<Store> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<Store> list = new Vector();
        Store po;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            po = this.getStores(strIds, "#");
            list.add(po);
        }
        return list;
    }

    private Store getStores(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        Store po = new Store();

        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                po.setStore_id(tmp);
                continue;
            }
            if (i == 2) {
                po.setRecord_flag(tmp);
                continue;
            }

        }
        return po;
    }

    private int deleteByTrans(HttpServletRequest request, StoreMapper storeMapper, Vector<Store> pos, Store prmVo) {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (Store po : pos) {
                n += storeMapper.deleteStore(po);
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

    private OperationResult add(HttpServletRequest request, StoreMapper storeMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Store po = this.getReqAttribute(request, CommandConstant.COMMAND_ADD);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "商户ID为" + po.getStore_id() + "的";
        if (CharUtil.getDBLenth(po.getStore_name()) > 50) {
            rmsg.addMessage("商户名称最大值不能超过50位(中文字符为两位)");
            return rmsg;
        } else {
            try {
                if (this.existRecord(po, storeMapper)) {
                    rmsg.addMessage(preMsg + "记录已存在！");
                    return rmsg;
                } else {
                    preMsg = "商户名称为" + po.getStore_name() + "的";
                    if (this.existName(po, storeMapper)) {
                        rmsg.addMessage(preMsg + "记录已存在！");
                        return rmsg;
                    }
                }

                n = this.addByTrans(request, storeMapper, po);

            } catch (Exception e) {
                return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, operationLogMapper);
            }
            logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), operationLogMapper);
        }
        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    private boolean existRecord(Store vo, StoreMapper storeMapper) {
        List<Store> list = storeMapper.getStoreById(vo);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean existName(Store vo, StoreMapper storeMapper) {
        List<Store> list = storeMapper.getStoreByName(vo);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }

    private int addByTrans(HttpServletRequest request, StoreMapper storeMapper, Store vo) {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = storeMapper.addStore(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }
    
    @RequestMapping("/StoreExportAll")
    public void exportExcel(HttpServletRequest request,HttpServletResponse response) throws Exception{
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.prminfo.entity.Store");
        List<Map<String,String>> queryResults = this.entityToMap(results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }
    
    private List<Map<String,String>> entityToMap(List results){
        List<Map<String,String>> list =  new ArrayList<>();
        int row = results.size();
        for(int n=0;n<row ;n++){
            Store vo = (Store)results.get(n);
            Map<String,String> map = new HashMap<>();
            map.put("store_id", vo.getStore_id());
            map.put("store_name", vo.getStore_name());
            map.put("link_man", vo.getLink_man());
            map.put("tel", vo.getTel());
            map.put("fax", vo.getFax());
            map.put("terminal_no", vo.getTerminal_no());
            map.put("address", vo.getAddress());
            list.add(map);
        }
        return list;
    }


}
