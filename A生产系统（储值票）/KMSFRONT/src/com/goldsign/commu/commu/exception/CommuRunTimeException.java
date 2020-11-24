package com.goldsign.commu.commu.exception;

public class CommuRunTimeException extends RuntimeException{

    public CommuRunTimeException(Throwable cause) {
        super(cause);
    }

    public CommuRunTimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommuRunTimeException(String message) {
        super(message);
    }

    public CommuRunTimeException() {
    }

}