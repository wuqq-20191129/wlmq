/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.escommu.exception;

/**
 *
 * @author Administrator
 */
public class FileCRCException extends Exception{

    public FileCRCException(Throwable cause) {
        super(cause);
    }

    public FileCRCException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileCRCException(String message) {
        super(message);
    }

    public FileCRCException() {
    }

}
