/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.escommu.exception;

/**
 *
 * @author Administrator
 */
public class FileContentException extends Exception{

    public FileContentException(Throwable cause) {
        super(cause);
    }

    public FileContentException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileContentException(String message) {
        super(message);
    }

    public FileContentException() {
    }

}
