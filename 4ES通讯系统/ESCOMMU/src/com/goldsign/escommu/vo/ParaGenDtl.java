package com.goldsign.escommu.vo;

public class ParaGenDtl {

    private int waterNo;
    private String deviceId;
    private String parmTypeId;
    private String verNum;
    private String genResult;
    private String verType;

    public int getWaterNo() {
        return this.waterNo;
    }

    public void setWaterNo(int aWaterNo) {
        this.waterNo = aWaterNo;
    }

    public String getVerNum() {
        return this.verNum;
    }

    public void setVerNum(String aVerNum) {
        this.verNum = aVerNum;
    }

    public String getParmTypeId() {
        return this.parmTypeId;
    }

    public void setParmTypeId(String aParmTypeId) {
        this.parmTypeId = aParmTypeId;
    }

    public String getGenResult() {
        return this.genResult;
    }

    public String getVerType() {
        return verType;
    }

    public void setGenResult(String aGenResult) {
        this.genResult = aGenResult;
    }

    public void setVerType(String verType) {
        this.verType = verType;
    }

    /**
     * @return the deviceId
     */
    public String getDeviceId() {
        if (null == deviceId) {
            return "";
        }
        return deviceId;
    }

    /**
     * @param deviceId the deviceId to set
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
