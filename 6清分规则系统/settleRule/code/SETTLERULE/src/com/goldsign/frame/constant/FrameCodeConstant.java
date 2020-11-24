/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.frame.constant;

import com.goldsign.rule.util.Util;

/**
 * 系统级别常量
 * @author lindaquan
 */
public class FrameCodeConstant {
    
    //系统代码 op_sys_menu 表
    public static final String SYS_FLAG = "09";
    
    //操作员
    public static String OPERATER_ID = "system";
    
    // 记录参数类型
    static final public String RECORD_FLAG_CURRENT = "0";// 记录标志代码（当前参数）
    static final public String RECORD_FLAG_FUTURE = "1";// 记录标志代码（未来参数）
    static final public String RECORD_FLAG_HISTORY = "2";// 记录标志代码（历史参数）
    static final public String RECORD_FLAG_DRAFT = "3";// 记录标志代码（草稿参数）
    static final public String RECORD_FLAG_DELETED = "4";// 未来参数记录标志代码（删除标志）
    static final public String RECORD_FLAG_DELETED_CURRENT = "5";// 当前参数记录标志代码（删除标志）
    //end 记录参数类型
    
    //FrameDBUtil
    public static final int FLAG_YEAR = 1;//年标志
    public static final int FLAG_MONTH = 2;//月标志
    public static final int FLAG_DATE = 3;//日期标志
    public static final int FLAG_WEEK = 4;//周标志
    //end FrameDBUtil
    
    //LoginManager
    public static String CONFIG_TYPE_SYS = "1";//配置类型:1系统级
    public static int MAX_DB_ROW_NUMBER = 3000;
    public static int MAX_PAGE_NUMBER = 300;//页面列表最大显示行数 
    public static int MAX_BLACKLIST_SECTION_NUMBER = 20;// 最大黑名单段数量
    public static int MAX_BLACKLIST_NUMBER = 30000;// 最大黑名单数量
    public static int MAX_METRO_BLACKLIST_NUMBER = 10000;
    public static int MAX_OUT_NUMBER = 100;
    public static int ApplicationFlag = 1;
    //end LoginManager
}
