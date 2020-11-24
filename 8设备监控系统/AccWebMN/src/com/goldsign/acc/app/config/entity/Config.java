package com.goldsign.acc.app.config.entity;

public class Config extends ConfigKey {
    private String typeName;

    private String typeSubName;

    private String configValue;

    private String remark;

    public Config(String type, String typeSub, String configName, String typeName, String typeSubName, String configValue, String remark) {
        super(type, typeSub, configName);
        this.typeName = typeName;
        this.typeSubName = typeSubName;
        this.configValue = configValue;
        this.remark = remark;
    }

    public Config() {
        super();
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public String getTypeSubName() {
        return typeSubName;
    }

    public void setTypeSubName(String typeSubName) {
        this.typeSubName = typeSubName == null ? null : typeSubName.trim();
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue == null ? null : configValue.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}