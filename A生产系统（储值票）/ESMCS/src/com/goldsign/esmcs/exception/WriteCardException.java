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
public class WriteCardException extends RuntimeException{

    public WriteCardException(Throwable cause) {
        super(cause);
    }

    public WriteCardException(String message, Throwable cause) {
        super(message, cause);
    }

    public WriteCardException(String message) {
        super(message);
    }

    public WriteCardException() {
    }

}
