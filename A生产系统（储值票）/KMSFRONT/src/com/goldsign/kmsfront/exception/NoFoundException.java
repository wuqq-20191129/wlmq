package com.goldsign.kmsfront.struct.exception;

/**
 *
 * @author Administrator
 */
public class NoFoundException extends RuntimeException{

    public NoFoundException(String message) {
        super(message);
    }

    public NoFoundException(Throwable cause) {
        super(cause);
    }

    public NoFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoFoundException() {
    }
 
}
