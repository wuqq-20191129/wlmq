/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.querysys.mapper;

import com.goldsign.acc.app.querysys.entity.OperatorLog;
import java.util.List;

/**
 *
 * @author zhouyang
 * 20170617
 * 安全日志
 */
public interface OperatorLogMapper {
    public List<OperatorLog> getOperatorLogs(OperatorLog operatorLog);
}
