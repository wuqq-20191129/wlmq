/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.kmscommu.vo;

/**
 * JNI调用，封装返回类
 * 
 * @author lenovo
 */
public class CardKeyResult {

    private byte[] code;//返回代码
    
    private byte[] msg;//返回信息

    /**
     * @return the code
     */
    public byte[] getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(byte[] code) {
        this.code = code;
    }

    /**
     * @return the msg
     */
    public byte[] getMsg() {
        return msg;
    }

    /**
     * @param msg the msg to set
     */
    public void setMsg(byte[] msg) {
        this.msg = msg;
    }
    
    
}
