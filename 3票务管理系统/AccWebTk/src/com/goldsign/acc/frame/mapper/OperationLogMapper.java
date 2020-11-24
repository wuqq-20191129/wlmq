/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.mapper;

import com.goldsign.acc.frame.entity.OperationLog;
import com.goldsign.acc.frame.entity.OperationLogForPrm;

/**
 *
 * @author hejj
 */
public interface OperationLogMapper {

    public int addLog(OperationLog opLog);

    public int addLogForPrm(OperationLogForPrm opLog);

    public int getParamTypeIdForNum(String moduleId);

    public String getParamTypeId(String moduleId);
    
    public String getButtonOperationType(String commandName);

}
