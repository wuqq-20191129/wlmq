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
public class ParameterConstant {
    static final public String RECORD_FLAG_CURRENT = "0";
    /**
     * 记录标志代码（未来参数） 1
     */
    static final public String RECORD_FLAG_FUTURE = "1";
    /**
     * 记录标志代码（历史参数） 2
     */
    static final public String RECORD_FLAG_HISTORY = "2";
    /**
     * 记录标志代码（草稿参数） 3
     */
    static final public String RECORD_FLAG_DRAFT = "3";
    /**
     * 未来参数记录标志代码（删除标志） 4
     */
    static final public String RECORD_FLAG_DELETED = "4";
    /**
     * 当前参数记录标志代码（删除标志） 5
     */
    static final public String RECORD_FLAG_DELETED_CURRENT = "5";
    
    /**
     * 草稿参数版本号
     */
    static final public String VERSION_NO_DRAFT = "0000000000";
    
    
    //----参数版本记录标识号--------------------------------------//
    /**
     * 票价参数
     */
    public static final String FARE_ALL = "0400";
    /**
     * 收费区段 0401
     */
    public static final String FARE_ZONE = "0401";
    /**
     * 收费配置 0402
     */
    public static final String FARE_CONF = "0402";
    /**
     * 票价表 0403
     */
    public static final String FARE_TABLE = "0403";
    /**
     * 节假日 0404
     */
    public static final String HOLIDAY_TABLE = "0404";
    /**
     * 非繁忙时间定义 0405
     */
    public static final String OFF_PEAK_HOURS = "0405";
    /**
     * TVM地图参数
     */
    public static final String TVM_MAP = "8100";
    /**
     * 充值终端通讯参数
     */
    public static final String CHARGE_SERVER_CONF = "8101";
        /**
     * AGM读卡器程序
     */
    public static final String DEV_PROGRAM_AGM = "9120";
    /**
     * TVM读卡器程序
     */
    public static final String DEV_PROGRAM_TVM = "9220";
    /**
     * BOM读卡器程序
     */
    public static final String DEV_PROGRAM_BOM = "9320";
    /**
     * TCM读卡器程序
     */
    public static final String DEV_PROGRAM_TCM = "9420";
    /**
     * PCA读卡器程序
     */
    public static final String DEV_PROGRAM_PCA = "9520";
    
}
