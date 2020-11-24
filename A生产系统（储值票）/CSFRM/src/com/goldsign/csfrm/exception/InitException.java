/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.csfrm.exception;

/**
 *
 * @author lenovo
 * 
 * 初始化异常类
 * 初始化失败时，抛出此异常类
 * 
 */
public class InitException extends BaseException{
    
    public InitException(){
        super();
    }
    
    public InitException(String msg){
        super(msg);
    }
    
    public InitException(String msg, String code){
        super(msg, code);
    }
    
    public InitException(String msg, String code, Throwable cause) {
        super(msg, code, cause);
    }
        
    public InitException(String msg, Throwable cause) {
        super(msg, cause);
    }
    
    public InitException(Throwable cause) {
        super(cause);
    }

}
