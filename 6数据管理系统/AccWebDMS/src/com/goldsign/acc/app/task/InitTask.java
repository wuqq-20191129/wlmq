/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.task;
//import com.goldsign.app.service.TaskConfigService;
//import com.goldsign.app.vo.TaskConfigVo;

import com.goldsign.acc.app.init.service.TaskConfigService;
import com.goldsign.acc.app.sysconfig.entity.TaskConfig;
import com.goldsign.acc.app.util.SpringApplicationContextHolder;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @desc:
 * @author:zhongzqi
 * @create date: 2017-12-12
 */
public class InitTask {
    /**
     * 初次执行延迟时间 单位毫秒
     */
    public static final long INITIAL_DELAY = 1000L;
    /**
     * 任务执行间隔时间 单位毫秒
     */
    public static final long PERIOD = 60000L;

    private static Logger logger = Logger.getLogger(InitTask.class);

    private final static ScheduledExecutorService service = Executors.newScheduledThreadPool(10);

    /**
     * 启动任务
     *
     * @throws Exception
     */
    public static void run()
            throws Exception {
        TaskConfigService tcService = (TaskConfigService) SpringApplicationContextHolder.getSpringBean("taskConfigService");
        List<TaskConfig> configs = tcService.findByConditions();
        for (TaskConfig vo : configs) {
            String className = vo.getClassName();
            TimerTask task = (TimerTask) Class.forName(className).newInstance();
            service.scheduleAtFixedRate(task, INITIAL_DELAY, PERIOD, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * 关闭连接
     */
    public static void closeResource() {
        //试图停止所有正在执行的活动任务，暂停处理正在等待的任务，并返回等待执行的任务列表
        logger.info("关闭线程池中各个任务的数据库连接...");
        service.shutdownNow();
        BaskTask.setCancelFlag(true);
    }
}
