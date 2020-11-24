/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.constant;

/**
 *
 * @author hejj
 */
public class FrameOctFileImportConstant {

    public static String OCT_IMPORT_NORMAL = "0";//oct文件正常上传
    public static String OCT_IMPORT_EXCEPTION = "9";//oct文件非正常上传
    public static String OCT_IMPORT_NORMAL_NAME = "oct文件正常上传";//oct文件正常上传
    public static String OCT_IMPORT_EXCEPTION_NAME = "oct文件非正常上传";//oct文件非正常上传
    public static String OCT_IMPORT_FILE_TYPE_TRX = "10";//交易文件
    public static String OCT_IMPORT_FILE_TYPE_SETTLE = "11";//结算文件
    public static String SYS_SETTLE_OCT_IMPORT_SETTLE_TIME_UNLIMIT = "0";//不限制清算一直等待

    //一卡通导出、导入日志
    public static String DO_EXPORT = "0";//导出
    public static String DO_IMPORT = "1";//导入
    public static String FINISH_NOT = "0";//0:未完成 1:已完成 8：正在进行 9：出现错误
    public static String FINISH_DONE = "1";
    public static String FINISH_DOING = "8";//导出文件但未上传或已下载但未导入，处理完成的中间状态
    public static String FINISH_ERROR = "9";

}
