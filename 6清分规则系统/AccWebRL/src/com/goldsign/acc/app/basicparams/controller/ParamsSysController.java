/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicparams.controller;

import com.goldsign.acc.app.basicparams.entity.ParamsSys;
import com.goldsign.acc.app.basicparams.mapper.ParamsSysMapper;
import com.goldsign.acc.app.basicparams.mapper.ParamsThresMapper;
import com.goldsign.acc.frame.constant.CommandConstant;

import com.goldsign.acc.frame.constant.LogConstant;
import com.goldsign.acc.frame.controller.RLBaseController;

import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.mapper.OperationLogMapper;

import com.goldsign.acc.frame.util.DBUtil;
import com.goldsign.acc.frame.util.ExpUtil;

import com.goldsign.acc.frame.util.LogUtil;

import com.goldsign.acc.frame.vo.OperationResult;

import java.util.List;
import java.util.Map;

import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * 系统参数设置
 *
 * @author luck
 */
@Controller
public class ParamsSysController extends ParamsSysParentController {

    @Autowired
    private ParamsThresMapper ptMapper;
    @Autowired
    private ParamsSysMapper psMapper;

    @RequestMapping(value = "/paramsAction")
    public ModelAndView service(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/jsp/basicparams/ParamsSys.jsp");
        String command = request.getParameter("command");
        String operType = request.getParameter("operType");
        OperationResult opResult = new OperationResult();
        try {
            if (command != null) {
                command = command.trim();

                if (command.equals(CommandConstant.COMMAND_QUERY)) {
                    opResult = this.queryPlan(request, this.psMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_ADD)) {
                    opResult = this.addPlan(request, this.psMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_DELETE)) {
                    opResult = this.deletePlan(request, this.psMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_AUDIT)) {
                    opResult = this.auditPlan(request, this.psMapper, this.operationLogMapper);
                }
                if (command.equals(CommandConstant.COMMAND_MODIFY)) {
                    opResult = this.modifyPlan(request, this.psMapper, this.operationLogMapper);
                }

                if (this.isUpdateOp(command, operType))//更新操作，增、删、改、审核,查询更新结果或原查询结果
                {

                    this.queryUpdateResult(command, operType, request, psMapper, operationLogMapper, opResult, mv);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            opResult.addMessage(e.getMessage());
        }

        String[] attrNames = this.getAttributeNames(command, operType);
        this.setPageOptions(attrNames, mv, request, response); //设置页面线路、车站、票卡、仓库等选项值、版本
        this.getResultSetText(opResult.getReturnResultSet(), mv, operType);
        this.baseHandlerForOutIn(request, response, mv); //出入库基本回传参数
        this.baseHandler(request, response, mv);
        this.divideResultSet(request, mv, opResult); //结果集分页
        this.SaveOperationResult(mv, opResult); //返回结果集
        return mv;
    }

    private String[] getAttributeNames(String command, String operType) {
        String[] attrNames = {RECORDFLAG};
        return attrNames;
    }

    private void queryUpdateResult(String command, String operType, HttpServletRequest request, ParamsSysMapper ptMapper,
            OperationLogMapper opLogMapper, OperationResult opResult, ModelAndView mv) throws Exception {
        if (!command.equals(CommandConstant.COMMAND_ADD)) {
            this.saveQueryControlDefaultValues(request, mv); //保存查询条件
        }
        this.queryForOpPlan(request, this.psMapper, this.operationLogMapper, opResult);

    }

    private void getResultSetText(List resultSet, ModelAndView mv, String operType) {
        this.getResultSetTextForPlan(resultSet, mv);

    }

    private void getResultSetTextForPlan(List<ParamsSys> resultSet, ModelAndView mv) {
        List<PubFlag> recordFlags = (List<PubFlag>) mv.getModelMap().get(RLBaseController.RECORDFLAG);

        for (ParamsSys sd : resultSet) {
            if (recordFlags != null && !recordFlags.isEmpty()) {
                sd.setRecordFlagName(DBUtil.getTextByCode(sd.getRecordFlag(), recordFlags));
            }
        }

    }

    public OperationResult queryForOpPlan(HttpServletRequest request, ParamsSysMapper ptMapper, OperationLogMapper opLogMapper, OperationResult opResult) throws Exception {
        LogUtil logUtil = new LogUtil();
        ParamsSys queryCondition;
        List<ParamsSys> resultSet;

        try {
            queryCondition = this.getQueryConditionForOpPlan(request, opResult);
//            if (queryCondition == null) {
//                return null;
//            }
            resultSet = ptMapper.queryPlan(queryCondition);
            opResult.setReturnResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return opResult;

    }

    public OperationResult queryPlan(HttpServletRequest request, ParamsSysMapper ptMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult or = new OperationResult();
        LogUtil logUtil = new LogUtil();
        ParamsSys queryCondition;
        List<ParamsSys> resultSet;

        try {
            queryCondition = this.getQueryConditionIn(request);
            resultSet = ptMapper.queryPlan(queryCondition);
            or.setReturnResultSet(resultSet);
            or.addMessage("成功查询" + resultSet.size() + "条记录");
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_QUERY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_QUERY, request, "成功查询" + resultSet.size() + "条记录", opLogMapper);
        return or;

    }

    public OperationResult addPlan(HttpServletRequest request, ParamsSysMapper ptMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        ParamsSys vo = this.getReqAttributePlan(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        String preMsg = "";
        try {
            List<ParamsSys> typeDescriptionByTypeCode = psMapper.getTypeDescriptionByTypeCode(vo);
            if (typeDescriptionByTypeCode.size() > 0) {
                if (!typeDescriptionByTypeCode.get(0).getTypeDescription().equals(vo.getTypeDescription())) {
                    rmsg.addMessage("添加失败！类型代码:" + vo.getTypeCode() + "的类型值为:" + typeDescriptionByTypeCode.get(0).getTypeDescription() + "!");
                    return rmsg;
                }
            }
            List<ParamsSys> typeCodeByTypeDes = psMapper.getTypeCodeByTypeDes(vo);
            if (typeCodeByTypeDes.size() > 0) {
                if (!typeCodeByTypeDes.get(0).getTypeCode().equals(vo.getTypeCode())) {
                    rmsg.addMessage("添加失败！类型值:" + vo.getTypeDescription() + "的类型代码为:" + typeCodeByTypeDes.get(0).getTypeCode() + "!");
                    return rmsg;
                }
            }
            List<ParamsSys> valueByCode = psMapper.getValueByCode(vo);
            if (valueByCode.size() > 0) {
                if (!valueByCode.get(0).getValue().equals(vo.getValue())) {
                    rmsg.addMessage("添加失败！参数代码:" + vo.getCode() + "的参数值为:" + valueByCode.get(0).getValue() + "!");
                    return rmsg;
                }
            }
            List<ParamsSys> codeByValue = psMapper.getCodeByValue(vo);
            if (codeByValue.size() > 0) {
                if (!codeByValue.get(0).getCode().equals(vo.getCode())) {
                    rmsg.addMessage("添加失败！参数值:" + vo.getValue() + "的参数代码为:" + codeByValue.get(0).getCode() + "!");
                    return rmsg;
                }
            }
            n = this.addPlanByTrans(request, ptMapper, vo, rmsg);
            if (n == 0) {
                rmsg.addMessage("添加失败！类型代码:" + vo.getTypeCode() + ",参数代码:" + vo.getCode() + "已经添加！");
                return rmsg;
            }
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_ADD, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_ADD, request, preMsg + LogConstant.addSuccessMsg(n), opLogMapper);
        rmsg.addMessage(LogConstant.addSuccessMsg(n));

        return rmsg;
    }

    public OperationResult deletePlan(HttpServletRequest request, ParamsSysMapper ptMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<ParamsSys> pos = this.getReqAttributeForPlanDelete(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;

        try {
            n = this.deletePlanByTrans(request, ptMapper, pos);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_DELETE, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_DELETE, request, LogConstant.delSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.delSuccessMsg(n));

        return rmsg;
    }

    public OperationResult auditPlan(HttpServletRequest request, ParamsSysMapper ptMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        Vector<ParamsSys> pos = this.getReqAttributeForPlanAudit(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        try {
            n = this.auditPlanByTrans(request, ptMapper, pos, rmsg);
        } catch (Exception e) {
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_AUDIT, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_AUDIT, request, LogConstant.auditSuccessMsg(n), opLogMapper);

        rmsg.addMessage(LogConstant.auditSuccessMsg(n));
        return rmsg;
    }

    public OperationResult modifyPlan(HttpServletRequest request, ParamsSysMapper ptMapper, OperationLogMapper opLogMapper) throws Exception {
        OperationResult rmsg = new OperationResult();
        ParamsSys vo = this.getReqAttributePlan(request);
        LogUtil logUtil = new LogUtil();
        int n = 0;
        String preMsg = "";
        try {
            List<ParamsSys> typeDescriptionByTypeCode = psMapper.getTypeDescriptionByTypeCode(vo);
            if (typeDescriptionByTypeCode.size() > 0) {
                if (!typeDescriptionByTypeCode.get(0).getTypeDescription().equals(vo.getTypeDescription())) {
                    rmsg.addMessage("修改失败！类型代码:" + vo.getTypeCode() + "的类型值为:" + typeDescriptionByTypeCode.get(0).getTypeDescription() + "!");
                    return rmsg;
                }
            }
            List<ParamsSys> valueByCode = psMapper.getValueByCode(vo);
            if (valueByCode.size() > 0) {
                if (!valueByCode.get(0).getValue().equals(vo.getValue())) {
                    rmsg.addMessage("修改失败！参数代码:" + vo.getCode() + "的参数值为:" + valueByCode.get(0).getValue() + "!");
                    return rmsg;
                }
            }
            n = this.modifyPlanByTrans(request, ptMapper, vo, rmsg);
        } catch (Exception e) {
            e.printStackTrace();
            return logUtil.operationExceptionHandle(request, CommandConstant.COMMAND_MODIFY, e, opLogMapper);
        }
        logUtil.logOperation(CommandConstant.COMMAND_MODIFY, request, preMsg + LogConstant.modifySuccessMsg(n), opLogMapper);
        rmsg.addMessage(LogConstant.modifySuccessMsg(n));

        return rmsg;
    }

    @RequestMapping("/ParamsSysExportAll")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        List results = this.getBufferElements(request);
        List results = this.getBufferElementsForCurClass(request, "com.goldsign.acc.app.basicparams.entity.ParamsSys");
        String expAllFields = request.getParameter("expAllFields");
        List<Map<String, Object>> queryResults
                = ExpUtil.entityToMap(expAllFields, results);
        ExpUtil.renderExportExcelPath(response,
                ExpUtil.exportExcel(queryResults, request));
    }

}
