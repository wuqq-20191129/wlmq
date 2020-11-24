/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.csfrm.exception;

/**
 *
 * @author lenovo
 * 
 * 授权异常类
 * 授权失败时，抛出此异常类
 */
public class AuthorException extends BaseException{
    
    public AuthorException(){
        super();
    }
    
    public AuthorException(String msg){
        super(msg);
    }
    
    public AuthorException(String msg, String code){
        super(msg, code);
    }
    
    public AuthorException(String msg, String code, Throwable cause) {
        super(msg, code, cause);
    }
        
    public AuthorException(String msg, Throwable cause) {
        super(msg, cause);
    }
    
    public AuthorException(Throwable cause) {
        super(cause);
    }

}
