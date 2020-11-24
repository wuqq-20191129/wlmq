/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.csfrm.exception;

/**
 *
 * @author lenovo
 * 
 * 基类异常类
 * 子类异常类，应继承此类
 * 
 */
public class BaseException extends Exception{
    
    protected String code;     //异常代码
    
    public BaseException(){
        super();
    }
    
    public BaseException(String msg){
        super(msg);
    }
    
    public BaseException(String msg, String code){
        super(msg);
        this.code = code;
    }
    
    public BaseException(String msg, String code, Throwable cause) {
        super(msg, cause);
    }
        
    public BaseException(String msg, Throwable cause) {
        super(msg, cause);
    }
    
    public BaseException(Throwable cause) {
        super(cause);
    }
    
    public String getCode(){
        
        return this.code;
    }
    
    public void setCode(String code){
        this.code = code;
    }
}
