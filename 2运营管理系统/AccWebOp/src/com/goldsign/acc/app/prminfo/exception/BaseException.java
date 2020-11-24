package com.goldsign.acc.app.prminfo.exception;

public class BaseException extends Exception {

    private static final long serialVersionUID = -3462860641019885857L;
    private String errorCode;

    public BaseException(String msg) {
            super(msg);
    }

    public BaseException(String msg, String errorCode) {
            super(msg);
            this.errorCode = errorCode;
    }
    
    public String getErrorCode() {
            return errorCode;
    }

    public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
    }
}
