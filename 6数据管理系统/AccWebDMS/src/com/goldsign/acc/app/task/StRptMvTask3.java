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
public class StRptMvTask3 extends BaskTask {

    private static Logger logger = Logger.getLogger(StRptMvTask3.class);
    private String taskName;

    public StRptMvTask3() {
        this.taskName = getClass().getSimpleName();
    }

    @Override
    public void run() {
        int status = 0;
        int waterNo = 0;
        TaskService service = (TaskService) SpringApplicationContextHolder.getSpringBean("taskService");
        try {
            if (judgeRun()) {
                status = service.queryTaskStatus(getTaskName());
                if (0 == status) {
                    Map<String, Object> hmParm = new HashMap(10);
                    getLogger().info("开始执行------------------------------>StRptMvTask,时间:" + new Date());
                    waterNo = service.inserTaskBeginInfo(this.taskName, FLAG_EXECUTING);
                    service.updateTaskStatus(getTaskName(), STATUS_EXECUTING);
                    hmParm = service.doTask(DBConstant.OP_USER + "w_up_dm_st_rpt_mv3", AppCodeConstant.TYPE_RESULT);
                    getLogger().info("结束执行------------------------------>StRptMvTask,时间:" + new Date());
                    service.updateTaskStatus(getTaskName(), STATUS_EXECUTABLE);
                    updateTaskLogAfterDoTask(waterNo, service, hmParm);
                }
            }
        } catch (Exception ex) {
            doTaskExceptionHandler(waterNo, service, ex, getLogger());
        }
    }

    @Override
    public String getTaskName() {
        return this.taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger aLogger) {
        logger = aLogger;
    }

}
