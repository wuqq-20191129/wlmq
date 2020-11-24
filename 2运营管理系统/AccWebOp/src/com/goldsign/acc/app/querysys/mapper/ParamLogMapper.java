/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.querysys.mapper;

import com.goldsign.acc.app.querysys.entity.ParamLog;
import java.util.List;

/**
 *
 * @author zhouyang
 * 20170621
 * 参数操作日志
 */
public interface ParamLogMapper {
     public List<ParamLog> getParamLogs(ParamLog paramLog);
}
