/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.exception;

import com.goldsign.csfrm.exception.BaseException;

/**
 * 应用异常类
 * 应用出现异常时，抛出此异常类
 * 
 * @author lenovo
 */
public class AppException extends BaseException{

    public AppException(Throwable cause) {
        super(cause);
    }

    public AppException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public AppException(String msg, String code, Throwable cause) {
        super(msg, code, cause);
    }

    public AppException(String msg, String code) {
        super(msg, code);
    }

    public AppException(String msg) {
        super(msg);
    }

    public AppException() {
    }

}
