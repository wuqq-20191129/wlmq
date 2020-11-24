/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.vo;

/**
 *
 * @author hejj
 */
public class TacReturnResult {

    private int encode;
    private int keyIndex;
    private int msgSeq;
    private int msgLen;
    private int reserve;
    private String retCode;
    private String errCode;
    private String dataLen;
    private String data;

    /**
     * @return the encode
     */
    public int getEncode() {
        return encode;
    }

    /**
     * @param encode the encode to set
     */
    public void setEncode(int encode) {
        this.encode = encode;
    }

    /**
     * @return the keyIndex
     */
    public int getKeyIndex() {
        return keyIndex;
    }

    /**
     * @param keyIndex the keyIndex to set
     */
    public void setKeyIndex(int keyIndex) {
        this.keyIndex = keyIndex;
    }

    /**
     * @return the msgSeq
     */
    public int getMsgSeq() {
        return msgSeq;
    }

    /**
     * @param msgSeq the msgSeq to set
     */
    public void setMsgSeq(int msgSeq) {
        this.msgSeq = msgSeq;
    }

    /**
     * @return the msgLen
     */
    public int getMsgLen() {
        return msgLen;
    }

    /**
     * @param msgLen the msgLen to set
     */
    public void setMsgLen(int msgLen) {
        this.msgLen = msgLen;
    }

    /**
     * @return the reserve
     */
    public int getReserve() {
        return reserve;
    }

    /**
     * @param reserve the reserve to set
     */
    public void setReserve(int reserve) {
        this.reserve = reserve;
    }

    /**
     * @return the retCode
     */
    public String getRetCode() {
        return retCode;
    }

    /**
     * @param retCode the retCode to set
     */
    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    /**
     * @return the errCode
     */
    public String getErrCode() {
        return errCode;
    }

    /**
     * @param errCode the errCode to set
     */
    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    /**
     * @return the dataLen
     */
    public String getDataLen() {
        return dataLen;
    }

    /**
     * @param dataLen the dataLen to set
     */
    public void setDataLen(String dataLen) {
        this.dataLen = dataLen;
    }

    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(String data) {
        this.data = data;
    }

}
