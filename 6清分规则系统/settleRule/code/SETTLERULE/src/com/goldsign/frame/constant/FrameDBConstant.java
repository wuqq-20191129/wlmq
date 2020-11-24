/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.frame.constant;

/**
 *
 * @author hejj
 */
public class FrameDBConstant {
    /**
    * The data source reference name for the application main database
    */
   public static String MAIN_DATASOURCE = AppConfig.getJndiPrefix() + "jdbc/db";

    // 增加命令
    public static final String COMMAND_ADD = "add";
    // 删除命令
    public static final String COMMAND_DELETE = "delete";
    // 审核命令
    public static final String COMMAND_AUDIT = "audit";
    // 修改命令
    public static final String COMMAND_MODIFY = "modify";
    // 克隆命令
    public static final String COMMAND_CLONE = "clone";
    // 提交命令
    public static final String COMMAND_SUBMIT = "submit";
    // 分配命令
    public static final String COMMAND_DISTRIBUTE = "distribute";
    // 查询命令
    public static final String COMMAND_QUERY = "query";
    // 下载命令
    public static final String COMMAND_DOWNLOAD = "download";
    // 生成权重命令
    public static final String COMMAND_GENERATE = "generate";
    // 生成OD命令
    public static final String COMMAND_GENERATE_OD = "generateOD";
    
    // 日志消息提示
    public static final String OPERATIION_SUCCESS_LOG_MESSAGE = "操作成功";
    public static final String OPERATIION_FAIL_LOG_MESSAGE = "操作失败";
    
}
