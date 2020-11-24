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
public class TokenKeyResult extends CardKeyResult{
    
    private byte[] mac;//mac
    
    private byte[] key;//key
    
    /**
     * @return the mac
     */
    public byte[] getMac() {
        return mac;
    }

    /**
     * @param mac the mac to set
     */
    public void setMac(byte[] mac) {
        this.mac = mac;
    }

    /**
     * @return the key
     */
    public byte[] getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(byte[] key) {
        this.key = key;
    }
    
    
}
