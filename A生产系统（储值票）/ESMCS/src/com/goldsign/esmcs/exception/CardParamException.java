/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.exception;

/**
 * 卡片参数异常类
 * 调用ES 制卡，封装卡参数异常时，抛出此异常类
 *
 * @author lenovo
 */
public class CardParamException extends RuntimeException{

    public CardParamException(Throwable cause) {
        super(cause);
    }

    public CardParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public CardParamException(String message) {
        super(message);
    }

    public CardParamException() {
    }

}
