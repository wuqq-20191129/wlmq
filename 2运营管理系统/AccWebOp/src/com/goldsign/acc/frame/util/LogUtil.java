/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.util;

import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.WebConstant;
import com.goldsign.acc.frame.entity.OperationLog;
import com.goldsign.acc.frame.entity.OperationLogForPrm;
import com.goldsign.acc.frame.mapper.OperationLogMapper;
import com.goldsign.acc.frame.vo.OperationResult;
import com.goldsign.login.vo.User;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author hejj
 */
public class LogUtil {

    public OperationResult operationExceptionHandle(HttpServletRequest request, String command, Exception e, OperationLogMapper mapper) throws Exception {

        this.logOperation(command, request, "操作失败", mapper);
        OperationResult rsgm;
        if (e.getMessage() != null) {
            rsgm = new OperationResult(e.getMessage());
        } else {
            rsgm = new OperationResult("操作失败");

        }
        return rsgm;
    }

    private String getOpMsgForLimitLen(String opResult) {
        if (opResult.length() > 256) {
            int indexNum = opResult.indexOf("主键：");
            int successIndex = opResult.indexOf("操作成功");
            if (indexNum > 0) {
                opResult = opResult.substring(0, indexNum + 3) + "...";
                if (successIndex > 0) {
                    opResult = opResult + ":操作成功";
                }
            } else {
                indexNum = opResult.indexOf("：");
                if (indexNum > 0) {
                    opResult = opResult.substring(0, indexNum + 1) + "...";
                    if (successIndex > 0) {
                        opResult = opResult + ":操作成功";
                    }
                }
            }
        }
        return opResult;
    }

    private boolean isNotNeedLog(String command) {
        if(command ==null || command.length() ==0)
            return true;
        for (String str : CommandConstant.COMMANDS_NO_LOG) {
            if (str.equals(command)) {
                return true;
            }
        }
        return false;
    }

    public void logOperation(String command, HttpServletRequest request, String opResult, OperationLogMapper mapper) throws Exception {
        if(this.isNotNeedLog(command))
            return ;
        
        String operatorID = ((User) request.getSession().getAttribute(WebConstant.PARM_USER)).getAccount();
        String moduleID = null;
        moduleID = request.getParameter(WebConstant.PARM_MODULE_ID);
        OperationLog opLog = null;
        OperationLogForPrm opLogForPrm = null;
        String opType = null;
        //获得参数模块映射缓存
        // HashMap paramModuleMapping = null;//--del this.getParamTypeModueMapping("paramModuleMapping.properties", request);
        String paramTypeID = "";
        boolean isParamOperation = this.isParamOperation(moduleID, mapper);
        if (isParamOperation) {
            paramTypeID = mapper.getParamTypeId(moduleID);
        }
        opResult = this.getOpMsgForLimitLen(opResult);//全选等操作opResult长度超过256

        if (command != null) {
            command = command.trim();
            opType = this.getOperationTypeForCommand(mapper, command);
            opLog = new OperationLog(operatorID, opType, moduleID, opResult);
            mapper.addLog(opLog);
            if (isParamOperation) {
                opLogForPrm = new OperationLogForPrm(operatorID, opType, paramTypeID, opResult);
                mapper.addLogForPrm(opLogForPrm);
            }
            /*
            if (command.equals("modify")) {
                opLog = new OperationLog(operatorID, LogConstant.OP_MODIFY, moduleID, opResult);
                mapper.addLog(opLog);
                if (isParamOperation) {
                    opLogForPrm = new OperationLogForPrm(operatorID, LogConstant.OP_MODIFY, paramTypeID, opResult);
                    mapper.addLogForPrm(opLogForPrm);
                }
                AccLogger.logger(operatorID, moduleID, AccLogger.OP_MODIFY, opResult);
                if (this.isParamOperation(moduleID, mapper)) {
                    paramTypeID = (String) paramModuleMapping.get(moduleID);
                    AccLogger.paramlogger(operatorID, paramTypeID, AccLogger.OP_MODIFY, opResult);
                }
                return;
            }
            if (command.equals("delete")) {
                AccLogger.logger(operatorID, moduleID, AccLogger.OP_DELETE, opResult);
                if (this.isParamOperation(moduleID, paramModuleMapping)) {
                    paramTypeID = (String) paramModuleMapping.get(moduleID);
                    AccLogger.paramlogger(operatorID, paramTypeID, AccLogger.OP_DELETE, opResult);
                }

                return;
            }
            if (command.equals("add")) {
                AccLogger.logger(operatorID, moduleID, AccLogger.OP_ADD, opResult);
                if (this.isParamOperation(moduleID, paramModuleMapping)) {
                    paramTypeID = (String) paramModuleMapping.get(moduleID);
                    //记录参数增加日志
                    AccLogger.paramlogger(operatorID, paramTypeID, AccLogger.OP_ADD, opResult);
                }

                return;
            }
            if (command.equals("query")) {
                //           ICCSLogger.logger(operatorID,moduleID,ICCSLogger.OP_QUERY,opResult);
                return;
            }
            if (command.equals("reportQuery")) {
                //            ICCSLogger.logger(operatorID,moduleID,ICCSLogger.OP_REPORTQUERY,opResult);
                return;
            }
            if (command.equals("statistic")) {
                //            ICCSLogger.logger(operatorID,moduleID,ICCSLogger.OP_STATISTIC,opResult);
                return;
            }

            if (command.equals("submit")) {
                AccLogger.logger(operatorID, moduleID, AccLogger.OP_SUBMIT, opResult);
                if (this.isParamOperation(moduleID, paramModuleMapping)) {
                    paramTypeID = (String) paramModuleMapping.get(moduleID);
                    //记录参数提交日志
                    AccLogger.paramlogger(operatorID, paramTypeID, AccLogger.OP_SUBMIT, opResult);
                }

                return;
            }
            if (command.equals("clone")) {
                AccLogger.logger(operatorID, moduleID, AccLogger.OP_CLONE, opResult);
                if (this.isParamOperation(moduleID, paramModuleMapping)) {
                    paramTypeID = (String) paramModuleMapping.get(moduleID);
                    //记录参数克隆日志
                    AccLogger.paramlogger(operatorID, paramTypeID, AccLogger.OP_CLONE, opResult);
                }

                return;
            }
            if (command.equals("download")) {
                AccLogger.logger(operatorID, moduleID, AccLogger.OP_DOWNLOAD, opResult);
                return;
            }

            if (command.equals("distribute")) {
                AccLogger.logger(operatorID, moduleID, AccLogger.OP_DISTRIBUTE, opResult);
                return;
            }
            if (command.equals("save")) {     //盘点
                AccLogger.logger(operatorID, moduleID, AccLogger.OP_CHECK, opResult);
                return;
            }
            if (command.equals("import")) {       //导入
                AccLogger.logger(operatorID, moduleID, AccLogger.OP_IMPORT, opResult);
                return;
            }
            if (command.equals("update")) {    //确认退款
                AccLogger.logger(operatorID, moduleID, AccLogger.OP_UPDATE, opResult);
                return;
            }
            if (command.equals("hmd")) {   //拒绝退款
                AccLogger.logger(operatorID, moduleID, AccLogger.OP_HMD, opResult);
                return;
            }
            if (command.equals("audit")) {   //审核
                AccLogger.logger(operatorID, moduleID, AccLogger.OP_AUDIT, opResult);
                return;
            }
             */
        }
    }

    private String getOperationTypeForCommand(OperationLogMapper mapper, String command) {
        String opType = mapper.getButtonOperationType(command);
        return opType;

    }

    public boolean isParamOperation(String moduleID, OperationLogMapper mapper) {
        int n = mapper.getParamTypeIdForNum(moduleID);
        if (n == 0) {
            return false;
        }
        return true;

    }

}
