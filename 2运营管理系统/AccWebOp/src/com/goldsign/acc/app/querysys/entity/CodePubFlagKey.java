package com.goldsign.acc.app.querysys.entity;



public class CodePubFlagKey {
    private Integer type;

    private String code;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }
}