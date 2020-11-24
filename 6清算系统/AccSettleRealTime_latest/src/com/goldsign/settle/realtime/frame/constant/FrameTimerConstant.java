/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.constant;

/**
 *
 * @author hejj
 */
public class FrameTimerConstant {
     /**
     * **定时运行****
     */
    public static String CONTROL_RUN_Not = "0";//不运行
    public static String CONTROL_RUN = "1";//运行
    public static String TIME_TYPE_YEAR = "1";//年
    public static String TIME_TYPE_MONTH = "2";//月
    public static String TIME_TYPE_DAY = "3";//日
    public static String TIME_TYPE_HOUR = "4";//时
    public static String TIME_TYPE_MIN = "5";//分
    public static String TIME_VALUE_TYPE_All = "1";//所有时间
    public static String TIME_VALUE_TYPE_RANGE = "2";//时间范围
    public static String TIME_VALUE_TYPE_SIGLE = "3";//单时间
    public static String TIME_VALUE_All = "*";//时间通配符所有
    public static String TIME_VALUE_RANGE = "-";//时间通配符范围
    public static long THREAD_SLEEP_TIME = 80000;//定时时间间隔
    
    public static String ActionClassPrefix="com.goldsign.settle.realtime.frame.timer.Action";
    
}
