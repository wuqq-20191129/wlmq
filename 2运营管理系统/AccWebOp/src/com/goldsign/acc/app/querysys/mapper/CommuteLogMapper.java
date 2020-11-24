/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.querysys.mapper;

import com.goldsign.acc.app.querysys.entity.CommuteLog;
import java.util.List;

/**
 *
 * @author zhouyang
 * 20170621
 * 数据交换日志
 */
public interface CommuteLogMapper {
    public List<CommuteLog> getCommuteLogs(CommuteLog commuteLog);
}
