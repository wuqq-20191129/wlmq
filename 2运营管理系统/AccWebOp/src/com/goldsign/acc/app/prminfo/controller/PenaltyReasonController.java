/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.controller;

import com.goldsign.acc.app.prminfo.entity.PenaltyReason;
import com.goldsign.acc.app.prminfo.mapper.PenaltyReasonMapper;
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
 * @desc:罚款原因
 * @author:mh
 * @create date: 2017-6-19
 */
@Controller
public class PenaltyReasonController extends BaseController {

    @Autowired
    private PenaltyReasonMapper penaltyReasonMapper;

    @RequestMapping("/PenaltyReason")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/prminfo/penalty_reason.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作
                {
                    opResult = this.modify(request, this.penaltyReasonMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE))//删除操作
                {
                    opResult = this.delete(request, this.penaltyReasonMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD))//增加操作
                {
                    opResult = this.add(request, this.penaltyReasonMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.penaltyReasonMapper, this.operationLogMapper);
                }
            }

            if (command != null) {
                if (command.equals(CommandConstant.COMMAND_ADD) || command.equals(CommandConstant.COMMAND_DELETE) || command.equals(CommandConstant.COMMAND_MODIFY)) {
                    if (!command.equals(CommandConstant.COMMAND_ADD)) {
                        this.saveQueryControlDefaultValues(request, mv);//保存查询条件
                    }
                    this.queryForOp(request, this.penaltyReasonMapper, this.operationLogMapper, opResult);
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

    private OperationResult query(HttpServletRequest request, PenaltyReasonMapper penaltyReasonMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        PenaltyReason queryCondition;
        List<PenaltyReason> resultSet;
        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = penaltyReasonMapper.getPenaltyReason(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
    }

    public OperationResult queryForOp(HttpServletRequest request, PenaltyReasonMapper penaltyReasonMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        PenaltyReason vo;
        List<PenaltyReason> resultSet;
        try {
            vo = this.getQueryConditionForOp(request);
            resultSet = penaltyReasonMapper.getPenaltyReason(vo);
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    private PenaltyReason getQueryCondition(HttpServletRequest request) {
        PenaltyReason qCon = new PenaltyReason();
        qCon.setDelete_flag("0");
        return qCon;
    }

    private PenaltyReason getReqAttribute(HttpServletRequest request, String type) {
        PenaltyReason po = new PenaltyReason();
        po.setPenalty_id(FormUtil.getParameter(request, "d_penaltyId"));
        po.setPenalty_name(FormUtil.getParameter(request, "d_penaltyName"));
        po.setDelete_flag("0");
        return po;
    }

    public OperationResult modify(HttpServletRequest request, PenaltyReasonMapper penaltyReasonMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        PenaltyReason po = this.getReqAttribute(request, CommandConstant.COMMAND_MODIFY);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "ID为" + po.getPenalty_id() + "的";
        if (CharUtil.getDBLenth(po.getPenalty_name()) > 200) {
            rmsg.addMessage("运营商名称最大值不能超过200位(中文字符为两位)");
            return rmsg;
        } else if (this.existName(po, penaltyReasonMapper)) {
            rmsg.addMessage("罚款原因记录已存在！");
            return rmsg;
        } else {
            try {
                n = this.modifyByTrans(request, penaltyReasonMapper, po);
            } catch (Exception e) {
                return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
            }
            logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), opLogMapper);
        }
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private PenaltyReason getQueryConditionForOp(HttpServletRequest request) {

        PenaltyReason qCon = new PenaltyReason();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        String command = request.getParameter("command");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            qCon.setPenalty_id(FormUtil.getParameter(request, "d_penaltyId"));
            qCon.setPenalty_name(FormUtil.getParameter(request, "d_penaltyName"));
            qCon.setDelete_flag("0");
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
        }
        return qCon;
    }

    private int modifyByTrans(HttpServletRequest request, PenaltyReasonMapper penaltyReasonMapper, PenaltyReason po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = penaltyReasonMapper.modifyPenaltyReason(po);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }

    private OperationResult delete(HttpServletRequest request, PenaltyReasonMapper penaltyReasonMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        PenaltyReason prmVo = this.getReqAttribute(request, CommandConstant.COMMAND_DELETE);
        Vector<PenaltyReason> pos = this.getReqAttributeForDelete(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            n = this.deleteByTrans(request, penaltyReasonMapper, pos, prmVo);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), operationLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    private Vector<PenaltyReason> getReqAttributeForDelete(HttpServletRequest request) {
        PenaltyReason po = new PenaltyReason();
        String selectIds = request.getParameter("allSelectedIDs");
        Vector<PenaltyReason> selectedItems = this.getDeleteIDs(selectIds);
        return selectedItems;
    }

    private Vector<PenaltyReason> getDeleteIDs(String selectedIds) {
        StringTokenizer st = new StringTokenizer(selectedIds, ";");
        String strIds = null;
        Vector<PenaltyReason> list = new Vector();
        PenaltyReason po;
        while (st.hasMoreTokens()) {
            strIds = st.nextToken();
            po = this.getPenaltyReasons(strIds, "#");
            list.add(po);
        }
        return list;
    }

    private PenaltyReason getPenaltyReasons(String strIDs, String delim) {
        StringTokenizer st = new StringTokenizer(strIDs, delim);
        String tmp = null;
        int i = 0;
        PenaltyReason po = new PenaltyReason();

        while (st.hasMoreTokens()) {
            i++;
            tmp = st.nextToken();
            if (i == 1) {
                po.setPenalty_id(tmp);
                continue;
            }
        }
        return po;
    }

    private int deleteByTrans(HttpServletRequest request, PenaltyReasonMapper penaltyReasonMapper, Vector<PenaltyReason> pos, PenaltyReason prmVo) {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            for (PenaltyReason po : pos) {
                n += penaltyReasonMapper.deletePenaltyReason(po);
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

    private OperationResult add(HttpServletRequest request, PenaltyReasonMapper penaltyReasonMapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        PenaltyReason po = this.getReqAttribute(request, CommandConstant.COMMAND_ADD);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        String preMsg = "ID为" + po.getPenalty_id() + "的";
        if (CharUtil.getDBLenth(po.getPenalty_name()) > 200) {
            rmsg.addMessage("罚款原因最大值不能超过200位(中文字符为两位)");
            return rmsg;
        } else {
            try {
                if (this.existRecord(po, penaltyReasonMapper)) {
                    rmsg.addMessage(preMsg + "记录已存在！");
                    return rmsg;
                } else {
                    if (this.existName(po, penaltyReasonMapper)) {
                        rmsg.addMessage("罚款原因已存在！");
                        return rmsg;
                    }
                }

                n = this.addByTrans(request, penaltyReasonMapper, po);

            } catch (Exception e) {
                return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, operationLogMapper);
            }
            logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), operationLogMapper);
        }
        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    private boolean existRecord(PenaltyReason vo, PenaltyReasonMapper penaltyReasonMapper) {
        List<PenaltyReason> list = penaltyReasonMapper.getPenaltyReasonById(vo);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean existName(PenaltyReason vo, PenaltyReasonMapper penaltyReasonMapper) {
        List<PenaltyReason> list = penaltyReasonMapper.getPenaltyReasonByName(vo);
        if (list.isEmpty()) {
            return false;
        }
        return true;
    }

    private int addByTrans(HttpServletRequest request, PenaltyReasonMapper penaltyReasonMapper, PenaltyReason vo) {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = penaltyReasonMapper.addPenaltyReason(vo);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }
    
    
    @RequestMapping("/PenaltyReasonExportAll")
    public void exportExcel(HttpServletRequest request,HttpServletResponse response) throws Exception{
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.prminfo.entity.PenaltyReason");
        List<Map<String,String>> queryResults = this.entityToMap(results);
        ExpUtil.renderExportExcelPath(response, ExpUtil.exportExcel(queryResults, request));
    }
    
    private List<Map<String,String>> entityToMap(List results){
        List<Map<String,String>> list =  new ArrayList<>();
        int row = results.size();
        for(int n=0;n<row ;n++){
            PenaltyReason vo = (PenaltyReason)results.get(n);
            Map<String,String> map = new HashMap<>();
            map.put("penalty_id", vo.getPenalty_id());
            map.put("penalty_name", vo.getPenalty_name());
            list.add(map);
        }
        return list;
    }


}
