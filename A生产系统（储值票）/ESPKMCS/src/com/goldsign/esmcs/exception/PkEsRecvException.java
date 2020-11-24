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
public class PkEsRecvException extends PkEsJniException{

    public PkEsRecvException() {
    }

    public PkEsRecvException(String msg) {
        super(msg);
    }

    public PkEsRecvException(String msg, String code) {
        super(msg, code);
    }

    public PkEsRecvException(String msg, String code, Throwable cause) {
        super(msg, code, cause);
    }

    public PkEsRecvException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public PkEsRecvException(Throwable cause) {
        super(cause);
    }

}
