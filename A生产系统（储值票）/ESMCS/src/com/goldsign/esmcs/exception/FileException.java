/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.exception;

/**
 * 文件异常类
 * 处理文件发生异常时，抛出此异常类
 * 
 * @author lenovo
 */
public class FileException extends AppException{

    public FileException() {
    }

    public FileException(String msg) {
        super(msg);
    }

    public FileException(String msg, String code) {
        super(msg, code);
    }

    public FileException(String msg, String code, Throwable cause) {
        super(msg, code, cause);
    }

    public FileException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public FileException(Throwable cause) {
        super(cause);
    }

}
