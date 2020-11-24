package com.goldsign.acc.app.task;

import com.goldsign.acc.app.constant.AppCodeConstant;
import com.goldsign.acc.app.init.service.TaskService;
import com.goldsign.acc.app.util.SpringApplicationContextHolder;
import com.goldsign.acc.frame.constant.DBConstant;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @desc:
 * @author:zhongzqi
 * @create date: 2017-12-21
 */
public class IcStsGenInTask extends BaskTask {

    private static Logger logger = Logger.getLogger(IcStsGenInTask.class);

    private String taskName;

    public IcStsGenInTask() {
        this.taskName = getClass().getSimpleName();
    }

    @Override
    public void run() {
        //判断是否终止任务
        if (isCancelFlag()) {
            cancel();
            getLogger().info("关闭线程:" + getTaskName());
        }
        int status = 0;
        int waterNo = 0;
        TaskService service = (TaskService) SpringApplicationContextHolder.getSpringBean("taskService");
        try {
            if (judgeRun()) {
                status = service.queryTaskStatus(getTaskName());
                if (0 == status) {
                    Map<String, Object> hmParm = new HashMap(10);
                    Date now = new Date();
                    getLogger().info("开始执行------------------------------>任务:" + getTaskName() + ",时间:" + now);
                    waterNo = service.inserTaskBeginInfo(this.getTaskName(), FLAG_EXECUTING);
                    service.updateTaskStatus(getTaskName(), STATUS_EXECUTING);
                    hmParm = service.doTask(DBConstant.TK_USER + "w_up_ic_sts_gen_in", AppCodeConstant.TYPE_RESULT);
                    service.updateTaskStatus(getTaskName(), STATUS_EXECUTABLE);
                    updateTaskLogAfterDoTask(waterNo, service, hmParm);
                    getLogger().info("结束执行------------------------------>任务:" + getTaskName() + ",时间:" + now);
                }
            }
        } catch (Exception ex) {
            doTaskExceptionHandler(waterNo, service, ex, getLogger());
        }
    }

    /**
     * @return the logger
     */
    public static Logger getLogger() {
        return logger;
    }

    /**
     * @param aLogger the logger to set
     */
    public static void setLogger(Logger aLogger) {
        logger = aLogger;
    }

    @Override
    protected String getTaskName() {
        return this.taskName;
    }
}
