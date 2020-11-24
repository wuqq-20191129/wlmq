/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.mapper;

import com.goldsign.acc.frame.entity.OperationLog;

/**
 *
 * @author hejj
 */
public interface OperationLogMapper {

    public int addLog(OperationLog opLog);

    public String getButtonOperationType(String commandName);

}
