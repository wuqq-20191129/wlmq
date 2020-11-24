package com.goldsign.kmsfront.struct.exception;

/**
 *
 * @author Administrator
 */
public class OtherFoundException extends RuntimeException{

    public OtherFoundException(String message) {
        super(message);
    }

    public OtherFoundException(Throwable cause) {
        super(cause);
    }

    public OtherFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public OtherFoundException() {
    }
 
}
