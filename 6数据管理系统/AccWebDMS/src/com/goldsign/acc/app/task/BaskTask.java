/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.acc.app.task;

import com.goldsign.acc.app.init.service.TaskConfigService;
import com.goldsign.acc.app.init.service.TaskService;
import com.goldsign.acc.app.sysconfig.entity.TaskConfig;
import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.Map;
import java.util.TimerTask;

/**
 * @desc:
 * @author:zhongzqi
 * @create date: 2017-12-12
 */
public abstract class BaskTask extends TimerTask {
    /**
     * 日志状态--正在执行
     */
    public static final int FLAG_EXECUTING = 2;
    /**
     * 日志状态--执行成功
     */
    public static final int FLAG_EXECUTE_SUCCESS = 0;
    /**
     * 日志状态--执行失败
     */
    public static final int FLAG_EXECUTE_FAILURE = 1;
    /**
     * 状态--任务正在执行 控制是否执行存储过程等相关业务
     */
    public static final int STATUS_EXECUTING = 1;
    /**
     * 状态--任务可执行 控制是否执行存储过程等相关业务
     */
    public static final int STATUS_EXECUTABLE = 0;
    /**
     * Calendar获取小时的索引
     */
    public static final int INDEX_HOUR = 11;
    /**
     * Calendar获取分钟的索引
     */
    public static final int INDEX_MINUTE = 12;
    /**
     * 判断左补值界限
     */
    public static final int LPAD_JUDGE = 10;
    /**
     * 左补值
     */
    public static final String LPAD_VALUE = "0";
    /**
     * 存储过程执行成功代码
     */
    public static final String TASK_EXECUTE_CODE = "0";

    /**
     * 获取任务名称
     * @return
     */
    protected abstract String getTaskName();

    /**
     * 任务终止标志
     */
    private static boolean CANCEL_FLAG = false;

    boolean judgeRun() {
        String taskname = getTaskName();
        TaskConfig vo = (TaskConfig) TaskConfigService.getConfigMaps().get(taskname);
        boolean result = false;
        String startTime = vo.getStartTime();
        if ("".equals(startTime) || startTime == null) {
            return result;
        }
        String[] startTimeVector = startTime.split(",");
        Calendar currentDate = Calendar.getInstance();
        int currentHour = currentDate.get(INDEX_HOUR);
        int currentMinute = currentDate.get(INDEX_MINUTE);
        String currentMinuteStr = String.valueOf(currentMinute);
        String currentHourStr = String.valueOf(currentHour);
        if (currentMinute < LPAD_JUDGE) {
            currentMinuteStr = LPAD_VALUE + currentMinute;
        }
        if (currentHour < LPAD_JUDGE) {
            currentHourStr = LPAD_VALUE + currentHour;
        }
        String nowHHMM = currentHourStr + currentMinuteStr;
        for (int index = 0; index < startTimeVector.length; index++) {
            if (nowHHMM.equals(startTimeVector[index])) {
                result = true;
                break;
            }
        }
        return result;
    }

    public static boolean isCancelFlag() {
        return CANCEL_FLAG;
    }

    public static void setCancelFlag(boolean cancelFlag) {
        CANCEL_FLAG = cancelFlag;
    }

    protected void doTaskExceptionHandler(int water_no, TaskService service, Exception ex, Logger logger) {
        try {
            logger.error("执行" + getTaskName() + "报错：", ex);
            service.updateTaskStatus(getTaskName(), STATUS_EXECUTABLE);
            service.updateTaskBeginInfo(water_no, FLAG_EXECUTE_FAILURE, ex.getMessage());
        } catch (Exception ex1) {
            logger.error("执行" + getTaskName() + "报错：", ex1);
        }
    }

    protected void updateTaskLogAfterDoTask(int water_no, TaskService service, Map<String, Object> hmParm) throws Exception {
        String retCode = TASK_EXECUTE_CODE;
        if (hmParm.get(TaskService.RETURN_CODE) != null) {
            retCode = hmParm.get(TaskService.RETURN_CODE).toString();
        }
        if (TASK_EXECUTE_CODE.equals(retCode)) {
            service.updateTaskBeginInfo(water_no, FLAG_EXECUTE_SUCCESS, TaskService.RESULT_DESC_MAP.get(FLAG_EXECUTE_SUCCESS));
        } else {
            service.updateTaskBeginInfo(water_no, FLAG_EXECUTE_FAILURE,TaskService.RESULT_DESC_MAP.get(FLAG_EXECUTE_FAILURE) );
        }
    }
}
