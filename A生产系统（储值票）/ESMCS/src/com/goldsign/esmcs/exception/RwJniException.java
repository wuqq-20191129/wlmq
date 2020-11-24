/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.exception;

/**
 * RW JNI异常类
 * 调用RW JNI发生异常时，抛出此异常类
 * 
 * @author lenovo
 */
public class RwJniException extends AppException{

    public RwJniException() {
    }

    public RwJniException(String msg) {
        super(msg);
    }

    public RwJniException(String msg, String code) {
        super(msg, code);
    }

    public RwJniException(String msg, String code, Throwable cause) {
        super(msg, code, cause);
    }

    public RwJniException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public RwJniException(Throwable cause) {
        super(cause);
    }

}
