package com.goldsign.acc.app.config.entity;

public class ConfigKey {
    private String type;

    private String typeSub;

    private String configName;

    public ConfigKey(String type, String typeSub, String configName) {
        this.type = type;
        this.typeSub = typeSub;
        this.configName = configName;
    }

    public ConfigKey() {
        super();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getTypeSub() {
        return typeSub;
    }

    public void setTypeSub(String typeSub) {
        this.typeSub = typeSub == null ? null : typeSub.trim();
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName == null ? null : configName.trim();
    }
}