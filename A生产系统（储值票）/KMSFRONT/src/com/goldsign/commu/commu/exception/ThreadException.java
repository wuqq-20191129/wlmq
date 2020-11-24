package com.goldsign.commu.commu.exception;

/**
 *
 * @author Administrator
 */
public class ThreadException extends Exception{

    public ThreadException(String message) {
        super(message);
    }

    public ThreadException(Throwable cause) {
        super(cause);
    }

    public ThreadException(String message, Throwable cause) {
        super(message, cause);
    }

    public ThreadException() {
    }
 
}
