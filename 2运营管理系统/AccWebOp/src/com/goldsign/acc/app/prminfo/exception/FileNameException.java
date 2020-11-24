package com.goldsign.acc.app.prminfo.exception;

public class FileNameException extends BaseException {

    public FileNameException(String msg) {
        super(msg);
    }

    public FileNameException(String msg, String errorCode) {
        super(msg, errorCode);
    } 
}
