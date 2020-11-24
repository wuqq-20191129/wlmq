package com.goldsign.kmsfront.struct.exception;

/**
 *
 * @author Administrator
 */
public class ErrCmdException extends RuntimeException{

    public ErrCmdException(String message) {
        super(message);
    }

    public ErrCmdException(Throwable cause) {
        super(cause);
    }

    public ErrCmdException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrCmdException() {
    }
 
}
