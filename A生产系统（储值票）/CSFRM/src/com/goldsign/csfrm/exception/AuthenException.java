/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.csfrm.exception;

/**
 *
 * @author lenovo
 * 
 * 验证异常类
 * 验证失败时，抛出此异常类
 */
public class AuthenException extends BaseException{
    
    public AuthenException(){
        super();
    }
    
    public AuthenException(String msg){
        super(msg);
    }
    
    public AuthenException(String msg, String code){
        super(msg, code);
    }
    
    public AuthenException(String msg, String code, Throwable cause) {
        super(msg, code, cause);
    }
        
    public AuthenException(String msg, Throwable cause) {
        super(msg, cause);
    }
    
    public AuthenException(Throwable cause) {
        super(cause);
    }

}
