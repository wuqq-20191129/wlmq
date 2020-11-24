package com.goldsign.acc.app.prmdev.entity;

import com.goldsign.acc.app.prminfo.entity.PrmVersion;

public class DeviceType extends DeviceTypeKey{
    private String devTypeName;
    private String installLocation;
    private String isSale;
    private String isCharge;
    private String installLocationTxt;
    private String isSaleTxt;
    private String isChargeTxt;
    private PrmVersion prmVersion;

    public DeviceType(String devTypeId, String versionNo, String recordFlag, String devTypeName, String installLocation, String isSale, String isCharge) {
        super(devTypeId, versionNo, recordFlag);
        this.devTypeName = devTypeName;
        this.installLocation=installLocation;
        this.isSale=isSale;
        this.isCharge=isCharge;
        switch(isCharge){
            case "0":
                this.isChargeTxt="不可充值";
                break;
            case "1":
                this.isChargeTxt="可充值";
                break;
            default:
                this.isChargeTxt=isCharge;
                break;
        }

        switch(isSale){
            case "0":
                this.isSaleTxt="不可发售";
                break;
            case "1":
                this.isSaleTxt="可发售";
                break;
            default:
                this.isSaleTxt=isSale;
                break;
        }

        switch(installLocation){
            case "1":
                this.installLocationTxt="车站";
                break;
            case "2":
                this.installLocationTxt="LC";
                break;
            case "3":
                this.installLocationTxt="制票室";
                break;
            case "4":
                this.installLocationTxt="ACC";
                break;
            default:
                this.installLocationTxt=installLocation;
                break;
        }
    }

    public String getInstallLocationTxt() {
        return installLocationTxt;
    }

    public void setInstallLocationTxt(String installLocationTxt) {
        this.installLocationTxt = installLocationTxt;
    }

    public String getIsSaleTxt() {
        return isSaleTxt;
    }

    public void setIsSaleTxt(String isSaleTxt) {
        this.isSaleTxt = isSaleTxt;
    }

    public String getIsChargeTxt() {
        return isChargeTxt;
    }

    public void setIsChargeTxt(String isChargeTxt) {
        this.isChargeTxt = isChargeTxt;
    }

    public String getInstallLocation() {
        return installLocation;
    }

    public void setInstallLocation(String installLocation) {
        this.installLocation = installLocation;
    }

    public String getIsSale() {
        return isSale;
    }

    public void setIsSale(String isSale) {
        this.isSale = isSale;
    }

    public String getIsCharge() {
        return isCharge;
    }

    public void setIsCharge(String isCharge) {
        this.isCharge = isCharge;
    }

    public DeviceType() {
        super();
    }

    public String getDevTypeName() {
        return devTypeName;
    }

    public void setDevTypeName(String devTypeName) {
        this.devTypeName = devTypeName == null ? null : devTypeName.trim();
    }

    public PrmVersion getPrmVersion() {
        return prmVersion;
    }

    public void setPrmVersion(PrmVersion prmVersion) {
        this.prmVersion = prmVersion;
    }
    
}