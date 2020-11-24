/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.csfrm.exception;

/**
 *
 * @author lenovo
 * 
 * 加载异常类
 * 加载异常时，抛出此异常类
 * 
 */
public class LoadException extends BaseException{
    
    public LoadException(){
        super();
    }
    
    public LoadException(String msg){
        super(msg);
    }
    
    public LoadException(String msg, String code){
        super(msg, code);
    }
    
    public LoadException(String msg, String code, Throwable cause) {
        super(msg, code, cause);
    }
        
    public LoadException(String msg, Throwable cause) {
        super(msg, cause);
    }
    
    public LoadException(Throwable cause) {
        super(cause);
    }

}
