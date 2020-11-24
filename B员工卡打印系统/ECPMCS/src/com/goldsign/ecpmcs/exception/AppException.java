/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.ecpmcs.exception;

import com.goldsign.csfrm.exception.BaseException;

/**
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
