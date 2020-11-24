/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.constant;

/**
 *
 * @author hejj
 */
public class CommandConstant {
    //默认搜索

    public static final String COMMAND_LIST = "list";
//修改
    public static final String COMMAND_MODIFY = "modify";
    // 删除命令
    public static final String COMMAND_DELETE = "delete";
    // 增加命令
    public static final String COMMAND_ADD = "add";
    // 提交命令
    public static final String COMMAND_SUBMIT = "submit";
    // 克隆命令
    public static final String COMMAND_CLONE = "clone";
    // 查询命令
    public static final String COMMAND_QUERY = "query";
    // 查询明细命令
    public static final String COMMAND_QUERY_DETAIL = "queryDetail";
//	 审核命令
    public static final String COMMAND_AUDIT = "audit";
    // 分配命令
    public static final String COMMAND_DISTRIBUTE = "distribute";
//	 导入命令
    public static final String COMMAND_IMPORT = "import";

    public static final String[] COMMANDS_NO_LOG = {COMMAND_LIST, COMMAND_QUERY};
//下一页
    public static String COMMAND_NEXT = "next";
    //尾页
    public static String COMMAND_NEXTEND = "nextEnd";
    //前一页
    public static String COMMAND_BACK = "back";
    //始页
    public static String COMMAND_BACKEND = "backEnd";
    //跳转
    public static String COMMAND_GOPAGE = "goPage";

    public static String COMMAND_CHECK1 = "check1";
    
    public static String COMMAND_CARD = "queryCard";
    
    //报表查询
    public static String COMMAND_REPORT_QUERY = "reportQuery";

}
