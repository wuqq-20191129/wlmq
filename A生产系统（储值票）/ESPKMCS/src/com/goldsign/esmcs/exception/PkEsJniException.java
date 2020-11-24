/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.exception;

/**
 * 储值票JNI异常类
 * 继承EsJniException类 
 * 调用储值票ES JNI发生异常时，抛出此异常类
 * 
 * @author lenovo
 */
public class PkEsJniException extends EsJniException{

    public PkEsJniException() {
    }

    public PkEsJniException(String msg) {
        super(msg);
    }

    public PkEsJniException(String msg, String code) {
        super(msg, code);
    }

    public PkEsJniException(String msg, String code, Throwable cause) {
        super(msg, code, cause);
    }

    public PkEsJniException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public PkEsJniException(Throwable cause) {
        super(cause);
    }

}
