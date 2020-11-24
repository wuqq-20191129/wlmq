/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.init.service;

import com.goldsign.acc.app.sysconfig.controller.TaskConfigController;
import com.goldsign.acc.app.sysconfig.entity.TaskConfig;
import com.goldsign.acc.app.sysconfig.mapper.TaskConfigMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @desc:分离出TaskConfigController一些系统初启动所使用的代码
 * @author:zhongzqi
 * @create date: 2017-12-12
 */
@Service
public class TaskConfigService {

    private static Map<String, TaskConfig> configMaps = new HashMap();
    private static List<TaskConfig> configs = new ArrayList();
    private final static Logger logger = Logger.getLogger(TaskConfigService.class);
    @Autowired
    private TaskConfigMapper taskConfigMapper;

    public static synchronized Map<String, TaskConfig> getConfigMaps() {
        return configMaps;
    }

    public static synchronized void setConfigMaps(Map<String, TaskConfig> aconfigMaps) {
        configMaps = aconfigMaps;
    }

    public static synchronized List<TaskConfig> getConfigs() {
        return configs;
    }

    public static synchronized void setConfigs(List<TaskConfig> aconfigs) {
        configs = aconfigs;
    }

    public void updateMemory(int result) {
        if (result > 0) {
            try {
                setConfigs(this.findByConditions());
                Map maps = new HashMap(20);
                for (TaskConfig vo : getConfigs()) {
                    String taskname = vo.getTaskName();
                    maps.put(taskname, vo);
                    logger.info(vo.getTaskName() + "||" + vo.getStartTime() + "||" + vo.getStatus());
                }
                setConfigMaps(maps);
            } catch (Exception ex) {
                logger.error("查询任务配置信息出现异常", ex);
            }
        }
    }

    public List<TaskConfig> findByConditions() {
        return taskConfigMapper.queryTaskConfig(new TaskConfig());
    }
}
