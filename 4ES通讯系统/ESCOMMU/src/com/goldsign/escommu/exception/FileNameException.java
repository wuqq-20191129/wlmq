/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.escommu.exception;

/**
 *
 * @author Administrator
 */
public class FileNameException extends Exception{

    public FileNameException(Throwable cause) {
        super(cause);
    }

    public FileNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileNameException(String message) {
        super(message);
    }

    public FileNameException() {
    }

}
