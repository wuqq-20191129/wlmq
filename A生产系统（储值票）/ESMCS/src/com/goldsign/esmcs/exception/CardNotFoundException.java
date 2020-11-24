/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.exception;

/**
 * 卡片异常类
 * 调用ES 制卡，找不到卡片对照表时，抛出此异常类
 *
 * @author lenovo
 */
public class CardNotFoundException extends CardParamException{

    public CardNotFoundException(Throwable cause) {
        super(cause);
    }

    public CardNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CardNotFoundException(String message) {
        super(message);
    }

    public CardNotFoundException() {
    }

}
