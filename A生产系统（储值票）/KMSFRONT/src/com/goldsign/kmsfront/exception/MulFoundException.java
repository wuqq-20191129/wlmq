package com.goldsign.kmsfront.struct.exception;

/**
 *
 * @author Administrator
 */
public class MulFoundException extends RuntimeException{

    public MulFoundException(String message) {
        super(message);
    }

    public MulFoundException(Throwable cause) {
        super(cause);
    }

    public MulFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MulFoundException() {
    }
 
}
