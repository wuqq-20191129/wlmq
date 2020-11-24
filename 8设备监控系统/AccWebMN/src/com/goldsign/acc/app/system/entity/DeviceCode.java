package com.goldsign.acc.app.system.entity;

public class DeviceCode {

    private String name;

    private String type;

    private int ipValue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public int getIpValue() {
        return ipValue;
    }

    public void setIpValue(int ipValue) {
        this.ipValue = ipValue;
    }
}