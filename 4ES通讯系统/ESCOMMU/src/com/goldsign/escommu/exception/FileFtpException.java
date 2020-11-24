/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.escommu.exception;

/**
 *
 * @author Administrator
 */
public class FileFtpException extends Exception{

    public FileFtpException(Throwable cause) {
        super(cause);
    }

    public FileFtpException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileFtpException(String message) {
        super(message);
    }

    public FileFtpException() {
    }

}
