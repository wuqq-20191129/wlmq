package com.goldsign.acc.frame.entity;

public class EmailContent {
    private String module;

    private String name;

    private String ip;

    private String pasreTime;

    private String status;

    private String message;

    private String isSend = "0";

    private String isHandle = "0";

    private String handleTime;

    private String classSimpleName;
    //add by zhongzq 20190905
    private String key;

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module == null ? null : module.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getPasreTime() {
        return pasreTime;
    }

    public void setPasreTime(String pasreTime) {
        this.pasreTime = pasreTime == null ? null : pasreTime.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public String getIsSend() {
        return isSend;
    }

    public void setIsSend(String isSend) {
        this.isSend = isSend == null ? null : isSend.trim();
    }

    public String getIsHandle() {
        return isHandle;
    }

    public void setIsHandle(String isHandle) {
        this.isHandle = isHandle == null ? null : isHandle.trim();
    }

    public String getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(String handleTime) {
        this.handleTime = handleTime == null ? null : handleTime.trim();
    }

    public String getClassSimpleName() {
        return classSimpleName;
    }

    public void setClassSimpleName(String classSimpleName) {
        this.classSimpleName = classSimpleName == null ? null : classSimpleName.trim();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "EmailContent{" +
                "module='" + module + '\'' +
                ", name='" + name + '\'' +
                ", ip='" + ip + '\'' +
                ", pasreTime='" + pasreTime + '\'' +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", isSend='" + isSend + '\'' +
                ", isHandle='" + isHandle + '\'' +
                ", handleTime='" + handleTime + '\'' +
                ", classSimpleName='" + classSimpleName + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}