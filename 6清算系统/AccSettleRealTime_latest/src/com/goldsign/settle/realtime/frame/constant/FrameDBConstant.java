/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.constant;

import com.goldsign.lib.db.util.DbcpHelper;

/**
 *
 * @author hejj
 */
public class FrameDBConstant {

    public static DbcpHelper DATA_DBCPHELPER;
    public final static String DB_ST = "W_ACC_ST.";//数据库前缀
    public final static String DB_CM = "W_ACC_CM.";//数据库前缀(通讯）
    public final static String TABLE_SYS = "W_";//表的系统前缀
    public final static String DB_PRE = DB_ST + TABLE_SYS;//存储过程、表的前缀
    public final static String DB_CM_PRE = DB_CM + TABLE_SYS;//存储过程、表的前缀

}
