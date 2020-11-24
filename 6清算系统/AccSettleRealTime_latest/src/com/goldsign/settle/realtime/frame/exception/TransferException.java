/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.exception;

/**
 *
 * @author hejj
 */
public class TransferException extends BaseException{
     public TransferException(String s) {
        super(s);
    }
    public TransferException(String s,String errorCode) {
        super(s,errorCode);

    }
    
}
