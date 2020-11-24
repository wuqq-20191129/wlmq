/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.escommu.exception;

/**
 *
 * @author Administrator
 */
public class ConfigFileException extends Exception{

    public ConfigFileException(Throwable cause) {
        super(cause);
    }

    public ConfigFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigFileException(String message) {
        super(message);
    }

    public ConfigFileException() {
    }

}
