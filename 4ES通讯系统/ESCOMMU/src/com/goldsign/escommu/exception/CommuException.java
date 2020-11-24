package com.goldsign.escommu.exception;

public class CommuException extends Exception{

    public CommuException(Throwable cause) {
        super(cause);
    }

    public CommuException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommuException(String message) {
        super(message);
    }

    public CommuException() {
    }

}