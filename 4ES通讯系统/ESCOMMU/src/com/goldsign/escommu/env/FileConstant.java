/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.escommu.env;

/**
 *
 * @author Administrator
 */
public class FileConstant {

    public static final String ES_BCP_LOG_FILE = "log/es_bcp.log";
    
    public static final int FILE_NAME_LENGTH = 20;//文件名长度
    public static final String FILE_NAME_PREFIX = "ES";//文件名前缀
    public static final String FILE_NAME_PREFIX2 = "MB";//文件名前缀
    public static final String FILE_NAME_DELIM = "\\.";//文件名分隔符
    /**
     * 文件错误列表************
     */
    public static final String FILE_ERRO_CODE_FILE_NAME = "00";//无效文件名
    public static final String FILE_ERRO_CODE_FILE_CONTENT = "01";//内容出错
    public static final String FILE_ERRO_CODE_FILE_FORMAT = "02";//格式出错
    public static final String FILE_ERRO_CODE_FILE_UNKOWN = "03";//其它未知错误
    public static final String FILE_ERRO_CODE_FILE_NOTEXIST = "04";//文件不存在
    public static final String FILE_ERRO_CODE_FILE_NOTSAME_TOTAL_DETAIL = "05";//汇总与明细不一致
    public static final String FILE_ERRO_CODE_FILE_REPEAT = "06";//记录重复
    public static final String FILE_ERRO_CODE_FILE_NAME_DESC = "无效文件名";//无效文件名
    public static final String FILE_ERRO_CODE_FILE_CONTENT_DESC = "内容出错";//内容出错
    public static final String FILE_ERRO_CODE_FILE_FORMAT_DESC = "格式出错";//格式出错
    public static final String FILE_ERRO_CODE_FILE_UNKOWN_DESC = "其它未知错误";//其它未知错误
    public static final String FILE_ERRO_CODE_FILE_NOTEXIST_DESC = "文件不存在";//文件不存在
    public static final String FILE_ERRO_CODE_FILE_NOTSAME_TOTAL_DETAIL_DESC = "汇总与明细不一致";//汇总与明细不一致
    public static final String FILE_ERRO_CODE_FILE_REPEAT_DESC = "记录重复";//记录重复
    //-------------------------------
    /**
     * 文件通知标志************
     */
    public static final String FILE_INFO_FLAG_NOT = "0";//文件没有通知ES
    public static final String FILE_INFO_FLAG_YES = "1";//文件已通知ES
    //------------------------
    /**
     * *文件内容相关**************
     */
    public static final String RECORD_PREFIX_STAT = "STAT:";
    public static final String RECORD_PREFIX_LISTNUM = "LISTNUM:";
    public static final String RECORD_PREFIX_LIST = "LIST:";
    public static final String RECORD_PREFIX_CRC = "CRC:";
    public static final String RECORD_DELIM = "\\t";//记录分隔符
    public static final String RECORD_LINE_DELIM = "\\r\\n";//行分隔符
    public static final int RECORD_LENGTH_STAT = 9;//汇总字段数量
    public static final int RECORD_LENGTH_STAT_NUM = 1;//明细总数量
    public static final int RECORD_LENGTH_LIST = 20;//明细字段数量  hwj modify 20160107增加卡商代码和手机号码
    public final static char[] CRLF = {0x0d, 0x0d, 0x0a};//换行符
    public final static char[] CRLF_1 = {0x0d, 0x0a};//换行符
    public final static char[] CRLF_2 = {0x0a};//换行符
    public final static char[] TAB = {0x09};//换行符
    public final static char[] N_SIGN = {0x0023};//#号
    //------------------------
    //
    /**
     * ****************生产类型*************************************
     */
    public static final String WORKTYPE_INITIAL = "00";//初始化
    public static final String WORKTYPE_HUNCH = "01";//预赋值
    public static final String WORKTYPE_AGAIN = "02";//重编码
    public static final String WORKTYPE_LOGOUT = "03";//注销
    //--------------------------------
    /**
     * ****************订单完成标志*************************************
     */
    public static final String HDL_FALG_FINISH = "3";//完整完成
    public static final String HDL_FALG_FINISH_NOCOMPLETE = "2";//未完整完成
    public static final String HDL_FALG_DOING = "1";//正在执行中
    //--------------------------------
    /**
     * ****************订单明细相关*************************************
     */
    public static final String STATUS_FLAG = "2";//状态标志
    //--------------------------------
    /**
     * ****************BCP导入表*************************************
     */
    public static final String TABLE_ES_INITIAL = AppConstant.COM_TK_P+"IC_ES_INITI_INFO_BUF";//初始化表
    public static final String TABLE_ES_HUNCH = AppConstant.COM_TK_P+"IC_ES_HUNCH_INFO_BUF";//预赋值表
    public static final String TABLE_ES_AGAIN = AppConstant.COM_TK_P+"IC_ES_AGAIN_INFO_BUF";//重编码表
    public static final String TABLE_ES_LOGOUT = AppConstant.COM_TK_P+"IC_ES_LOGOUT_INFO_BUF";//注销表
    
    public static final String TABLE_MB_INITIAL = AppConstant.COM_TK_P+"IC_MB_INITI_INFO";//空充初始化表
    public static final String TABLE_MB_HUNCH = AppConstant.COM_TK_P+"IC_MB_HUNCH_INFO";//空充预赋值表
    public static final String TABLE_MB_AGAIN = AppConstant.COM_TK_P+"IC_MB_AGAIN_INFO";//空充重编码表
    public static final String TABLE_MB_LOGOUT = AppConstant.COM_TK_P+"IC_MB_LOGOUT_INFO";//空充注销表
    //--------------------------------
    /**
     * ****************计划单相关*************************************
     */
    public static final String FLAG_FINISH = "1";//计划单所有订单已完成
    public static final String FLAG_FINISH_NOT = "0";//计划单存在订单未完成
    //*******审计错误文件消息相关******************************************/
    public static final String FLAG_AUDIT_NOTICE = "1";//已通知ES
    public static final String FLAG_AUDIT_NOTICE_NO = "0";//未通知ES
    public static final String AUDIT_FILE_ACC_DEVICE = "000000";//设备为ACC时，写000000
    public static final String AUDIT_FILE_NOTICE_OPER = "000000";//ACC向ES通知操作员
    public static final String AUDIT_FILE_PRE = "ESFTP";//审计文件前缀
    public static final String AUDIT_ERR_FILE_PRE = "ESER";//错误文件前缀
    public static final String SVER = "CRC:";
    //--------------------------------
    //*******物理卡号与逻辑卡号对照文件消息相关******************************************/
    public static final String PHY_LOGIC_FILE_PRE = "ESPLM";//物理卡号与逻辑卡号对照文件前缀
    //卡号段前辍
    public static final String CARD_LOGIC_FILE_PRE = "ESLOGIC";
    
    //空充生产类型前辍
    public static final String KC_PDU_TYPE_PRE = "MB802";
}
