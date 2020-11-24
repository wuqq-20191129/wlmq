/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.escommu.exception;

/**
 *
 * @author Administrator
 */
public class FileRecordReapeatException extends Exception{

    public FileRecordReapeatException(Throwable cause) {
        super(cause);
    }

    public FileRecordReapeatException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileRecordReapeatException(String message) {
        super(message);
    }

    public FileRecordReapeatException() {
    }

}
