/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.init.component;

import com.goldsign.acc.app.init.service.TaskConfigService;
import com.goldsign.acc.app.init.service.TaskService;
import com.goldsign.acc.app.task.InitTask;
import com.goldsign.acc.app.util.SpringApplicationContextHolder;
import org.apache.log4j.Logger;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

/**
 * @desc:
 * @author:zhongzqi
 * @create date: 2017-12-7
 */

public class DataManServer implements ServletContextAware {

    private ServletContext servletContext;
    private boolean stoped = false;
    private static Logger logger = Logger.getLogger(DataManServer.class.getName());

    public void init() {
        getLogger().info("数据管理系统正在启动...");
        new DataManServer().start();
        getLogger().info("数据管理系统启动成功.");
    }

    public void destroy() {
        getLogger().info("数据管理系统正在准备停止...");

        getLogger().info("后台任务正在关闭...");
        InitTask.closeResource();
        getLogger().info("数据管理系统停止!");
    }

    public boolean isStoped() {
        return stoped;
    }

    public static Logger getLogger() {
        return logger;
    }

    public void setStoped(boolean stoped) {
        this.stoped = stoped;
    }

    public static void setLogger(Logger logger) {
        DataManServer.logger = logger;
    }

    @Override
    public void setServletContext(ServletContext sc) {
        this.servletContext = sc;
    }
    public ServletContext getServletContext() {
        return servletContext;
    }

    private void start() {
        try {
            getLogger().info("初始化定时任务信息...");
            initTaskConfig();
            getLogger().info("定时任务正在启动...");
            InitTask.run();
        } catch (Exception e) {
            getLogger().error("启动出错 - ", e);
        }

    }

    private void initTaskConfig() throws Exception {
//        //将所有任务置为停止状态
        TaskService service = (TaskService) SpringApplicationContextHolder.getSpringBean("taskService");
        service.updateTaskStatus(null, 0);
//        //将所有任务加载到内存中
        if (TaskConfigService.getConfigMaps().isEmpty()) {
            logger.info("更新内存：定时任务配置信息...");
            try {
                TaskConfigService taskConfigService = (TaskConfigService) SpringApplicationContextHolder.getSpringBean("taskConfigService");
                taskConfigService.updateMemory(1);
            } catch (Exception ex) {
                logger.info("exception", ex);
                throw ex;
            }
        }
    }

}
