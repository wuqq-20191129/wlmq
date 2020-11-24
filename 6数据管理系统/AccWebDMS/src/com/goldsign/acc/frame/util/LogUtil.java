/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.util;

import com.goldsign.acc.frame.constant.CommandConstant;
import com.goldsign.acc.frame.constant.WebConstant;
import com.goldsign.acc.frame.entity.OperationLog;
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
        String opType = null;
        opResult = this.getOpMsgForLimitLen(opResult);//全选等操作opResult长度超过256

        if (command != null) {
            command = command.trim();
            opType = this.getOperationTypeForCommand(mapper, command);
            opLog = new OperationLog(operatorID, opType, moduleID, opResult);
            mapper.addLog(opLog);
        }
    }

    private String getOperationTypeForCommand(OperationLogMapper mapper, String command) {
        String opType = mapper.getButtonOperationType(command);
        return opType;

    }

}
