package com.goldsign.acc.app.querysys.entity;

public class CodePubFlag extends CodePubFlagKey {
    private String codeText;

    private String description;

    public String getCodeText() {
        return codeText;
    }

    public void setCodeText(String codeText) {
        this.codeText = codeText == null ? null : codeText.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}