/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.exception;

/**
 *
 * @author hejj
 */
public class RecordParseIgnoreException extends BaseException{
    public RecordParseIgnoreException(String s) {
        super(s);
    }
    public RecordParseIgnoreException(String s,String errorCode) {
        super(s,errorCode);

    }
    
}
