/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.exception;

/**
 * 通讯消息异常类
 * 通讯消息发生异常时，抛出此异常类
 * 
 * @author lenovo
 */
public class MessageException extends AppException{

    public MessageException() {
    }

    public MessageException(String msg) {
        super(msg);
    }

    public MessageException(String msg, String code) {
        super(msg, code);
    }

    public MessageException(String msg, String code, Throwable cause) {
        super(msg, code, cause);
    }

    public MessageException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public MessageException(Throwable cause) {
        super(cause);
    }

}
