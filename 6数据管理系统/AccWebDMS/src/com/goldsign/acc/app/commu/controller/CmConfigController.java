/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.commu.controller;

import com.goldsign.acc.app.commu.entity.CmConfig;
import com.goldsign.acc.app.commu.mapper.CmConfigMapper;
import com.goldsign.acc.frame.controller.BaseController;
import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.util.ExpUtil;
import com.goldsign.acc.frame.util.FormUtil;
import com.goldsign.acc.frame.util.LogUtil;
import com.goldsign.acc.frame.vo.OperationResult;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @desc:配置信息
 * @author:chenzx
 * @create date: 2017-9-15
 */
@Controller
public class CmConfigController extends BaseController {

    @Autowired
    private CmConfigMapper mapper;

    @RequestMapping("/cmConfig")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/commu/cmConfig.jsp");
        String command = request.getParameter("command");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();
                if (command.equals(CommandConstant.COMMAND_MODIFY))//修改操作
                {
                    opResult = this.modify(request, this.mapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_QUERY))//查询操作
                {
                    opResult = this.query(request, this.mapper, this.operationLogMapper);
                }
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
        }

        this.baseHandler(request, response, mv);
        this.getResultSetText(opResult.getReturnResultSet(), mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult);

        return mv;
    }
    
    private void getResultSetText(List resultSet, ModelAndView mv) {
        this.getResultSetTextForPlan(resultSet, mv);
    }

    private OperationResult query(HttpServletRequest request, CmConfigMapper mapper, OperationLogMapper operationLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        CmConfig queryCondition;
        List<CmConfig> resultSet;
        try {
            queryCondition = this.getQueryCondition(request);
            resultSet = mapper.getCmConfigs(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, operationLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", operationLogMapper);
        return or;
    }

    public OperationResult queryForOp(HttpServletRequest request, CmConfigMapper mapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        CmConfig vo;
        List<CmConfig> resultSet;
        try {
            vo = this.getQueryConditionForOp(request);
            resultSet = mapper.getCmConfig(vo);
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    private CmConfig getQueryCondition(HttpServletRequest request) {
        CmConfig qCon = new CmConfig();
        qCon.setOrigin_table_name(FormUtil.getParameter(request, "q_origin_table_name"));
        return qCon;
    }

    private CmConfig getReqAttribute(HttpServletRequest request, String type) {
        CmConfig po = new CmConfig();
        po.setOrigin_table_name(FormUtil.getParameter(request, "d_origin_table_name"));
        po.setKeep_days(FormUtil.getParameter(request, "d_keep_days"));
        po.setDivide_recd_count(FormUtil.getParameter(request, "d_divide_recd_count"));
        return po;
    }

    public OperationResult modify(HttpServletRequest request, CmConfigMapper mapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        CmConfig po = this.getReqAttribute(request, CommandConstant.COMMAND_MODIFY);
        LogUtil logUtil = new LogUtil();
        int n = 0;
            try {
                n = this.modifyByTrans(request, mapper, po);
            } catch (Exception e) {
                return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
            }
            logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request,LogConstant.modifySuccessMsg(n), opLogMapper);
        
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    private CmConfig getQueryConditionForOp(HttpServletRequest request) {

        CmConfig qCon = new CmConfig();
        HashMap vQueryControlDefaultValues = null;
        String queryControlDefaultValues = request.getParameter("queryControlDefaultValues");
        if (FormUtil.isAddOrModifyMode(request)) {//操作处于添加或修改模式
            qCon.setOrigin_table_name(FormUtil.getParameter(request, "d_origin_table_name"));
        } else {//操作处于非添加模式
            vQueryControlDefaultValues = FormUtil.getQueryControlDefaultValues(queryControlDefaultValues);
            if (!vQueryControlDefaultValues.isEmpty()) {
                qCon.setOrigin_table_name(FormUtil.getQueryControlDefaultValue(vQueryControlDefaultValues, "d_origin_table_name"));
            }
        }
        return qCon;
    }

    private int modifyByTrans(HttpServletRequest request, CmConfigMapper mapper, CmConfig po) throws Exception {
        TransactionStatus status = null;
        int n = 0;
        try {
            status = txMgr.getTransaction(this.def);
            n = mapper.modifyCmConfig(po);
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            throw e;
        }
        return n;
    }
    
    private void getResultSetTextForPlan(List<CmConfig> resultSet, ModelAndView mv) {

        for (CmConfig sd : resultSet) {
            switch (sd.getClear_flag()) {
                case "0":
                    sd.setClear_flag("删除");
                    break;
                case "1":
                    sd.setClear_flag("迁移");
                    break;
            }
        }
    }
    
    @RequestMapping("/CmConfigExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List results = this.getBufferElements(request);
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults
                = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response,
                ExpUtil.exportExcel(queryResults, request));
    }

}

