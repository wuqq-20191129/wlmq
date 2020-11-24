package com.goldsign.acc.app.prminfo.exception;

public class RecordParseException extends BaseException {

    public RecordParseException(String s) {
        super(s);
    }

    public RecordParseException(String s, String errorCode) {
        super(s, errorCode);

    }
}
