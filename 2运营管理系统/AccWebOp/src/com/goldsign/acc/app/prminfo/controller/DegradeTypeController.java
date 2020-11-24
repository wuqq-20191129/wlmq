/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.controller;

import com.goldsign.acc.app.prminfo.entity.DegradeType;
import com.goldsign.acc.app.prminfo.mapper.DegradeTypeMapper;
import com.goldsign.acc.frame.controller.BaseController;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.CharUtil;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
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
 * @desc:降级运营模式
 * @author:mh
 * @create date: 2017-6-16
 */
@Controller
public class DegradeTypeController extends BaseController {

    @Autowired
    private DegradeTypeMapper degradeTypeMapper;

    @RequestMapping("/DegradeType")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/prminfo/degrade_type.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作
                {
                    opResult = this.modify(request, this.degradeTypeMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE))//删除操作
                {
                    opResult = this.delete(request, this.degradeTypeMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD))//增加操作
                {
                    opResult = this.add(request, this.degradeTypeMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.degradeTypeMapper, this.operationLogMapper);
                }
            }

            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)) {
                    if (!command.equals(CommandConstant.COMMAND_ADD)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                    }
                    this.queryForOp(request, this.degradeTypeMapper, this.operationLogMapper, opResult);
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

    private OperationResult query(HttpServletRequest request, DegradeTypeMapper degradeTypeMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        DegradeType queryCondition;
        List<DegradeType> resultSet;
        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = degradeTypeMapper.getDegradeType(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
    }

    public OperationResult queryForOp(HttpServletRequest request, DegradeTypeMapper degradeTypeMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        DegradeType vo;
        List<DegradeType> resultSet;
        try {
            vo = this.getQueryConditionForOp(request);
            resultSet = degradeTypeMapper.getDegradeType(vo);
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    private DegradeType getQueryCondition(HttpServletRequest request) {
        DegradeType qCon = new DegradeType();
        qCon.setHdl_flag("0");
        return qCon;
    }

    private DegradeType getReqAttribute(HttpServletRequest request, String type) {
        DegradeType po = new DegradeType();
        po.setStatus_id(FormUtil.getParameter(request, "d_statusId"));
        po.setStatus_name(FormUtil.getParameter(request, "d_statusName"));
        po.setHdl_flag("0");
        return po;
    }

    public OperationResult modify(HttpServletRequest request, DegradeTypeMapper degradeTypeMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        DegradeType po = this.getReqAttribute(request, CommandConstant.COMMAND_MODIFY);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "模式ID为" + po.getStatus_id() + "的";
        if (CharUtil.getDBLenth(po.getStatus_name()) > 30) {
            rmsg.addMessage("运营商名称最大值不能超过30位(中文字符为两位)");
            return rmsg;
        } else if (this.existName(po, degradeTypeMapper)) {
            preMsg = "模式名称为" + po.getStatus_name() + "的";
            rmsg.addMessage(preMsg + "记录已存在！");
            return rmsg;
        } else {
            try {
                n = this.modifyByTrans(request, degradeTypeMapper, po);
            } catch (Exception e) {
                return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
            }
            logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), opLogMapper);
        }
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private DegradeType getQueryConditionForOp(HttpServletRequest request) {

        DegradeType qCon = new DegradeType();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            qCon.setStatus_id(FormUtil.getParameter(request, "d_statusId"));
            qCon.setStatus_name(FormUtil.getParameter(request, "d_statusName"));
            qCon.setHdl_flag("0");
        } else {//操作处于非添加模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                qCon.setHdl_flag("0");
            }
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
        }
        return qCon;
    }

    private int modifyByTrans(HttpServletRequest request, DegradeTypeMapper degradeTypeMapper, DegradeType po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = degradeTypeMapper.modifyDegradeType(po);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private OperationResult delete(HttpServletRequest request, DegradeTypeMapper degradeTypeMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        DegradeType prmVo = this.getReqAttribute(request, CommandConstant.COMMAND_DELETE);
        Vector<DegradeType> pos = this.getReqAttributeForDelete(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            n = this.deleteByTrans(request, degradeTypeMapper, pos, prmVo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), operationLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    private Vector<DegradeType> getReqAttributeForDelete(HttpServletRequest request) {
        DegradeType po = new DegradeType();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<DegradeType> selectedItems = this.getDeleteIDs(selectIds);
        return selectedItems;
    }

    private Vector<DegradeType> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<DegradeType> list = new Vector();
        DegradeType po;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            po = this.getDegradeTypes(strIds, "#");
            list.add(po);
        }
        return list;
    }

    private DegradeType getDegradeTypes(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        DegradeType po = new DegradeType();

        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                po.setStatus_id(tmp);
                continue;
            }
            if (i == 2) {
                po.setHdl_flag(tmp);
                continue;
            }

        }
        return po;
    }

    private int deleteByTrans(HttpServletRequest request, DegradeTypeMapper degradeTypeMapper, Vector<DegradeType> pos, DegradeType prmVo) {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (DegradeType po : pos) {
                n += degradeTypeMapper.deleteDegradeType(po);
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

    private OperationResult add(HttpServletRequest request, DegradeTypeMapper degradeTypeMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        DegradeType po = this.getReqAttribute(request, CommandConstant.COMMAND_ADD);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "模式ID为" + po.getStatus_id() + "的";
        if (CharUtil.getDBLenth(po.getStatus_name()) > 30) {
            rmsg.addMessage("模式名称最大值不能超过30位(中文字符为两位)");
            return rmsg;
        } else {
            try {
                if (this.existRecord(po, degradeTypeMapper)) {
                    rmsg.addMessage(preMsg + "记录已存在！");
                    return rmsg;
                } else {
                    preMsg = "模式名称为" + po.getStatus_name() + "的";
                    if (this.existName(po, degradeTypeMapper)) {
                        rmsg.addMessage(preMsg + "记录已存在！");
                        return rmsg;
                    }
                }

                n = this.addByTrans(request, degradeTypeMapper, po);

            } catch (Exception e) {
                return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, operationLogMapper);
            }
            logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), operationLogMapper);
        }
        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    private boolean existRecord(DegradeType vo, DegradeTypeMapper degradeTypeMapper) {
        List<DegradeType> list = degradeTypeMapper.getDegradeTypeById(vo);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean existName(DegradeType vo, DegradeTypeMapper degradeTypeMapper) {
        List<DegradeType> list = degradeTypeMapper.getDegradeTypeByName(vo);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }

    private int addByTrans(HttpServletRequest request, DegradeTypeMapper degradeTypeMapper, DegradeType vo) {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = degradeTypeMapper.addDegradeType(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }
    
    @RequestMapping("/DegradeTypeExportAll")
    public void exportExcel(HttpServletRequest request,HttpServletResponse response) throws Exception{
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.prminfo.entity.DegradeType");
        List<Map<String,String>> queryResults = this.entityToMap(results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }
    
    private List<Map<String,String>> entityToMap(List results){
        List<Map<String,String>> list =  new ArrayList<>();
        int row = results.size();
        for(int n=0;n<row ;n++){
            DegradeType vo = (DegradeType)results.get(n);
            Map<String,String> map = new HashMap<>();
            map.put("status_id", vo.getStatus_id());
            map.put("status_name", vo.getStatus_name());
            list.add(map);
        }
        return list;
    }

}
