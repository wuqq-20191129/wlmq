/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.exception;

/**
 *
 * @author hejj
 */
public class RecordParseForFileException extends BaseException{
    
    public RecordParseForFileException(String s) {
        super(s);
    }
    public RecordParseForFileException(String s,String errorCode) {
        super(s,errorCode);

    }

   
}
