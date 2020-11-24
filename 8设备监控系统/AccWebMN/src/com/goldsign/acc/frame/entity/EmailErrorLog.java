package com.goldsign.acc.frame.entity;

public class EmailErrorLog {
    private String paserTime;

    private String sender;

    private String errorMsg;

    private String insertTime;

    public String getPaserTime() {
        return paserTime;
    }

    public void setPaserTime(String paserTime) {
        this.paserTime = paserTime == null ? null : paserTime.trim();
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender == null ? null : sender.trim();
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg == null ? null : errorMsg.trim();
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime == null ? null : insertTime.trim();
    }
}