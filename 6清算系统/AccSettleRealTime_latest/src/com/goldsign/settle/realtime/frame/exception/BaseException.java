/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.exception;

/**
 *
 * @author hejj
 */
public class BaseException extends Exception {

    /**
     * 加密机消息相关101-110
     */
    //代码
    public static String EC_MSG_FIELD_LEN_NULL = "101";//消息长度为0
    public static String EC_MSG_FIELD_LEN = "102";//消息长度不符合规定;
    
    
    //消息名称
    public static String EC_MSG_FIELD_LEN_NULL_NAME = "消息长度为0";//消息长度为0
    public static String EC_MSG_FIELD_LEN_NAME = "消息长度不符合规定";//消息长度不符合规定;
    
    
    
    private String errorCode;

    public BaseException(String msg) {
        super(msg);
    }

    public BaseException(String msg, String errorCode) {
        super(msg);
        this.setErrorCode(errorCode);
    }

    /**
     * @return the errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
