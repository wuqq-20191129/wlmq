/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.sysconfig.mapper;

import com.goldsign.acc.app.sysconfig.entity.TaskConfig;
import java.util.List;

/**
 *
 * @author mh
 */
public interface TaskConfigMapper {
    List<TaskConfig> queryTaskConfig(TaskConfig taskConfig);
    public int modifyTaskConfig(TaskConfig taskConfig);
    public int modifyStatus(TaskConfig taskConfig);
    public int addTaskConfig(TaskConfig taskConfig);
    //add by zhongzq 20171211
    public int updateTaskStatus(TaskConfig taskConfig);

    public List<TaskConfig> queryTaskStatus(TaskConfig vo);
    
}
