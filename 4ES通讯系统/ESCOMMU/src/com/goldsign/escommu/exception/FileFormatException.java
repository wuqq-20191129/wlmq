/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.escommu.exception;

/**
 *
 * @author Administrator
 */
public class FileFormatException extends Exception{

    public FileFormatException(Throwable cause) {
        super(cause);
    }

    public FileFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileFormatException(String message) {
        super(message);
    }

    public FileFormatException() {
    }

}
