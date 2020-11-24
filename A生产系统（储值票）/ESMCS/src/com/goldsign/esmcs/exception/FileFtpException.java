/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.exception;

/**
 * FTP文件异常类
 * FTP操作发生异常时，抛出此异常类
 *
 * @author Administrator
 */
public class FileFtpException extends AppException{

    public FileFtpException(Throwable cause) {
        super(cause);
    }

    public FileFtpException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public FileFtpException(String msg, String code, Throwable cause) {
        super(msg, code, cause);
    }

    public FileFtpException(String msg, String code) {
        super(msg, code);
    }

    public FileFtpException(String msg) {
        super(msg);
    }

    public FileFtpException() {
    }
   
}
