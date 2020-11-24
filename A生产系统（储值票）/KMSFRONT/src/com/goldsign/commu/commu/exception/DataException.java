package com.goldsign.commu.commu.exception;

public class DataException extends Exception{

    public DataException(Throwable cause) {
        super(cause);
    }

    public DataException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataException(String message) {
        super(message);
    }

    public DataException() {
    }

}