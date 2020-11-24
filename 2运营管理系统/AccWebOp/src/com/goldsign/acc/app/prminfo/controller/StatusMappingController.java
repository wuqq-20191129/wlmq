/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.controller;

import com.goldsign.acc.app.prminfo.entity.StatusMapping;
import com.goldsign.acc.app.prminfo.mapper.StatusMappingMapper;
import com.goldsign.acc.frame.controller.PrmBaseController;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
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
 * @desc:设备运营状态
 * @author:mh
 * @create date: 2017-6-19
 */
@Controller
public class StatusMappingController extends PrmBaseController {

    @Autowired
    private StatusMappingMapper statusMappingMapper;

    @RequestMapping("/StatusMapping")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/prminfo/status_mapping.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作
                {
                    opResult = this.modify(request, this.statusMappingMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE))//删除操作
                {
                    opResult = this.delete(request, this.statusMappingMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD))//增加操作
                {
                    opResult = this.add(request, this.statusMappingMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.statusMappingMapper, this.operationLogMapper);
                }
            } 
            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)) 
                {
                    if (!command.equals(CommandConstant.COMMAND_ADD)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                    }
                    this.queryForOp(request, this.statusMappingMapper, this.operationLogMapper, opResult);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] attrNames = {ACC_STATUS_VALUES};

        this.setPageOptions(attrNames, mv, request, response);//设置ACC状态选项值
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult);//结果集分页
        this.SaveOperationResult(mv, opResult);

        return mv;
    }
    
    private OperationResult query(HttpServletRequest request, StatusMappingMapper statusMappingMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        StatusMapping queryCondition;
        List<StatusMapping> resultSet;
        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = statusMappingMapper.getStatusMapping(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
    }

    public OperationResult queryForOp(HttpServletRequest request, StatusMappingMapper statusMappingMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        StatusMapping vo;
        List<StatusMapping> resultSet;
        try {
            vo = this.getQueryConditionForOp(request);
            resultSet = statusMappingMapper.getStatusMapping(vo);
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    private StatusMapping getQueryCondition(HttpServletRequest request) {
        StatusMapping qCon = new StatusMapping();
        qCon.setStatus_id(FormUtil.getParameter(request, "q_statusId"));
        qCon.setStatus_value(FormUtil.getParameter(request, "q_statusValue"));
        qCon.setAcc_status_value(FormUtil.getParameter(request, "q_accStatusValue"));
        return qCon;
    }

    private StatusMapping getReqAttribute(HttpServletRequest request, String type) {
        StatusMapping po = new StatusMapping();
        po.setStatus_id(FormUtil.getParameter(request, "d_statusId"));
        po.setStatus_value(FormUtil.getParameter(request, "d_statusValue"));
        po.setStatus_name(FormUtil.getParameter(request, "d_statusName"));   
        po.setAcc_status_value(FormUtil.getParameter(request, "d_accStatusValue"));
        List<PubFlag> pfs = this.pubFlagMapper.getAccStatusValues();
        for(PubFlag pf : pfs){
            if(pf.getCode().equals(po.getAcc_status_value())){
                po.setAcc_status_name(pf.getCode_text());
                break;
            }
        }
        return po;
    }

    public OperationResult modify(HttpServletRequest request, StatusMappingMapper statusMappingMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        StatusMapping po = this.getReqAttribute(request, CommandConstant.COMMAND_MODIFY);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.modifyByTrans(request, statusMappingMapper, po);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, LogConstant.modifySuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private StatusMapping getQueryConditionForOp(HttpServletRequest request) {

        StatusMapping qCon = new StatusMapping();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            if (command.equals(CommandConstant.COMMAND_DELETE)) {
                return null;
            }
            qCon.setStatus_id(FormUtil.getParameter(request, "d_statusId"));
            qCon.setStatus_value(FormUtil.getParameter(request, "d_statusValue"));
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
        }
        return qCon;
    }

    private int modifyByTrans(HttpServletRequest request, StatusMappingMapper statusMappingMapper, StatusMapping po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = statusMappingMapper.modifyStatusMapping(po);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private OperationResult delete(HttpServletRequest request, StatusMappingMapper statusMappingMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        StatusMapping prmVo = this.getReqAttribute(request, CommandConstant.COMMAND_DELETE);
        Vector<StatusMapping> pos = this.getReqAttributeForDelete(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            n = this.deleteByTrans(request, statusMappingMapper, pos, prmVo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), operationLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    private Vector<StatusMapping> getReqAttributeForDelete(HttpServletRequest request) {
        StatusMapping po = new StatusMapping();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<StatusMapping> selectedItems = this.getDeleteIDs(selectIds);
        return selectedItems;
    }

    private Vector<StatusMapping> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<StatusMapping> list = new Vector();
        StatusMapping po;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            po = this.getStatusMappings(strIds, "#");
            list.add(po);
        }
        return list;
    }

    private StatusMapping getStatusMappings(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        StatusMapping po = new StatusMapping();

        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                po.setStatus_id(tmp);
                continue;
            }
            if (i == 2) {
                po.setStatus_value(tmp);
                continue;
            }

        }
        return po;
    }

    private int deleteByTrans(HttpServletRequest request, StatusMappingMapper statusMappingMapper, Vector<StatusMapping> pos, StatusMapping prmVo) {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (StatusMapping po : pos) {
                n += statusMappingMapper.deleteStatusMapping(po);
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

    private OperationResult add(HttpServletRequest request, StatusMappingMapper statusMappingMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        StatusMapping po = this.getReqAttribute(request, CommandConstant.COMMAND_ADD);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            if (this.existRecord(po, statusMappingMapper)) {
                rmsg.addMessage( "设备运营状态记录已存在！");
                return rmsg;
            }

            n = this.addByTrans(request, statusMappingMapper, po);

        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, LogConstant.addSuccessMsg(n), operationLogMapper);
        
        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    private boolean existRecord(StatusMapping vo, StatusMappingMapper statusMappingMapper) {
        List<StatusMapping> list = statusMappingMapper.getStatusMappingById(vo);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }

    private int addByTrans(HttpServletRequest request, StatusMappingMapper statusMappingMapper, StatusMapping vo) {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = statusMappingMapper.addStatusMapping(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }
    
    
    @RequestMapping("/StatusMappingExportAll")
    public void exportExcel(HttpServletRequest request,HttpServletResponse response) throws Exception{
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.prminfo.entity.StatusMapping");
        List<Map<String,String>> queryResults = this.entityToMap(results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }
    
    private List<Map<String,String>> entityToMap(List results){
        List<Map<String,String>> list =  new ArrayList<>();
        int row = results.size();
        for(int n=0;n<row ;n++){
            StatusMapping vo = (StatusMapping)results.get(n);
            Map<String,String> map = new HashMap<>();
            map.put("status_id", vo.getStatus_id());
            map.put("status_value", vo.getStatus_value());
            map.put("status_name", vo.getStatus_name());
            map.put("acc_status_name", vo.getAcc_status_name());
            list.add(map);
        }
        return list;
    }

    
}
