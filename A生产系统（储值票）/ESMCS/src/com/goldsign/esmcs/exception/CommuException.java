/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.exception;

/**
 * 通讯异常类
 * 通讯发生异常时，抛出此异常类
 *
 * @author lenovo
 */
public class CommuException extends AppException{

    public CommuException() {
    }

    public CommuException(String msg) {
        super(msg);
    }

    public CommuException(String msg, String code) {
        super(msg, code);
    }

    public CommuException(String msg, String code, Throwable cause) {
        super(msg, code, cause);
    }

    public CommuException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public CommuException(Throwable cause) {
        super(cause);
    }

}
