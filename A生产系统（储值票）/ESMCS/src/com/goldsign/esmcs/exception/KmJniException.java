/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.exception;

/**
 * 加密机 JNI异常类
 * 调用加密机 JNI发生异常时，抛出此异常类
 * 
 * @author lenovo
 */
public class KmJniException extends AppException{

    public KmJniException() {
    }

    public KmJniException(String msg) {
        super(msg);
    }

    public KmJniException(String msg, String code) {
        super(msg, code);
    }

    public KmJniException(String msg, String code, Throwable cause) {
        super(msg, code, cause);
    }

    public KmJniException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public KmJniException(Throwable cause) {
        super(cause);
    }

}
