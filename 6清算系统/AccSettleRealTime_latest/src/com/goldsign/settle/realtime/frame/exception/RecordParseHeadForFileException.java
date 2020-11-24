/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.exception;

/**
 *
 * @author hejj
 */
public class RecordParseHeadForFileException extends BaseException {

    private int line = 0;

    public RecordParseHeadForFileException(String msg) {
        super(msg);
    }

    public RecordParseHeadForFileException(String s, String errorCode, int line) {
        super(s, errorCode);
        this.line = line;

    }
    public RecordParseHeadForFileException(String s, String errorCode) {
        super(s, errorCode);


    }

    /**
     * @return the line
     */
    public int getLine() {
        return line;
    }

    /**
     * @param line the line to set
     */
    public void setLine(int line) {
        this.line = line;
    }

}
