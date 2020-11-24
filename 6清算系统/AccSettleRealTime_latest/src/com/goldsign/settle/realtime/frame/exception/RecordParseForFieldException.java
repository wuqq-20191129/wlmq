/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.exception;

/**
 *
 * @author hejj
 */
public class RecordParseForFieldException extends BaseException{
    public RecordParseForFieldException(String s) {
        super(s);
    }
    public RecordParseForFieldException(String s,String errorCode) {
        super(s,errorCode);

    }
    
}
