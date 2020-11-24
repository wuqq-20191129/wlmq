/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.env;

/**
 *
 * @author lenovo
 */
public class LocalFileConstant extends AppConstant{

    //审计和错误文件前缀
    public static final String ES_FTP_AUDIT_FILE_PRE = "ESFTP";//审计
    public static final String ES_FTP_ERROR_FILE_PRE = "ESER";//错误
    
    //错误文件
    public static final String ES_FTP_ERROR_FILE_RESON_ERROR_FILE_NAME = "00";
    public static final String ES_FTP_ERROR_FILE_RESON_ERROR_CONTENT = "01";
    public static final String ES_FTP_ERROR_FILE_RESON_ERROR_FORMAT = "02";
    public static final String ES_FTP_ERROR_FILE_RESON_ERROR_OTHER = "03";
    public static final String ES_FTP_ERROR_FILE_RESON_ERROR_FILE_NOEXITS = "04";
    public static final String ES_FTP_ERROR_FILE_RESON_ERROR_STA_DET_NOT_SAME = "05";
    
    public static final String ES_FTP_ERROR_FILE_RESON_ERROR_FILE_NAME_DES = "无效文件名";
    public static final String ES_FTP_ERROR_FILE_RESON_ERROR_CONTENT_DES = "内容出错";
    public static final String ES_FTP_ERROR_FILE_RESON_ERROR_FORMAT_DES = "格式出错";
    public static final String ES_FTP_ERROR_FILE_RESON_ERROR_OTHER_DES = "其它未知错误";
    public static final String ES_FTP_ERROR_FILE_RESON_ERROR_FILE_NOEXITS_DES = "文件不存在";
    public static final String ES_FTP_ERROR_FILE_RESON_ERROR_STA_DET_NOT_SAME_DES = "汇总与明细不一致";
}
