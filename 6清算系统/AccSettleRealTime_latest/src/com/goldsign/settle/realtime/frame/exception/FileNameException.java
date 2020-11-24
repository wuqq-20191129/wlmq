/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.exception;

/**
 *
 * @author hejj
 */
public class FileNameException extends BaseException{
    public FileNameException(String msg){
        super(msg);
    }
    public FileNameException(String msg,String errorCode){
        super(msg,errorCode);
    }
    
}
