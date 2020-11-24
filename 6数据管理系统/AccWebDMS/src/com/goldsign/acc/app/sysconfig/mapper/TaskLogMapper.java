/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.sysconfig.mapper;

import com.goldsign.acc.app.sysconfig.entity.TaskLog;
import java.util.List;

/**
 *
 * @author mh
 */
public interface TaskLogMapper {
    List<TaskLog> queryTaskLog(TaskLog taskLog);
    public int addTaskLog (TaskLog taskLog);
    public int modifyTaskLog(TaskLog taskLog);
    //add by zhongzq 20171214
    public int getWaterNo();
}
