/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.exception;

/**
 * ES JNI异常类
 * 调用ES JNI发生异常时，抛出此异常类
 *
 * @author lenovo
 */
public class EsJniException extends AppException{

    public EsJniException() {
    }

    public EsJniException(String msg) {
        super(msg);
    }

    public EsJniException(String msg, String code) {
        super(msg, code);
    }

    public EsJniException(String msg, String code, Throwable cause) {
        super(msg, code, cause);
    }

    public EsJniException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public EsJniException(Throwable cause) {
        super(cause);
    }

}
