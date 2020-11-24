package com.goldsign.acc.app.system.entity;

/**
 * Description:
 *
 * @author: zhongziqi
 * @Date: 2019-04-19
 * @Time: 10:01
 */
public class BaseMessage {
    private String name;
    private String ip;
    private String status;
    private String statusDate;
    private String classSimpleName;
    private String keyMessage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;
    }

    public String getClassSimpleName() {
        return classSimpleName;
    }

    public void setClassSimpleName(String classSimpleName) {
        this.classSimpleName = classSimpleName;
    }

    public String getKeyMessage() {
        return keyMessage;
    }

    public void setKeyMessage(String keyMessage) {
        this.keyMessage = keyMessage;
    }
}
