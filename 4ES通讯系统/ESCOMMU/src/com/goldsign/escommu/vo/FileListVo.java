/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.escommu.vo;

/**
 *
 * @author Administrator
 */
public class FileListVo {

    private String esWorktypeId;//操作类型
    private String orderNo;//订单编号
    private String cardMainCode;//票卡类型(主)
    private String cardSubCode;//票卡类型(子)
    private String reqNo;//申请编号
    private String logiId;//逻辑卡号
    private String printId;//印刻卡号
    private String phyId;   //物理卡号
    private String manuTime;//制票日期时间
    private String cardMon;//面值
    private String periAvadate;//有效期
    private String lineCode;//线路代码
    private String stationCode;//站点代码
    private String cardStartAva;//乘次票应用有效期开始时间
    private String cardAvaDays;//乘次票使用有效期
    private String exitLineCode;//限制出站线路代码
    private String exitStationCode;//限制出站站点代码
    private String mode;//限制模式
    private String kdcVersion;
    private String hdlTime;
    private String statusFlag;
    private String cardType;
    
    private String cardProducerCode;//hwj add 20160107增加卡商代码
    private String phoneNo;//hwj add 20160107增加手机号码

    /**
     * @return the esWorktypeId
     */
    public String getEsWorktypeId() {
        return esWorktypeId;
    }

    /**
     * @param esWorktypeId the esWorktypeId to set
     */
    public void setEsWorktypeId(String esWorktypeId) {
        this.esWorktypeId = esWorktypeId;
    }

    /**
     * @return the orderNo
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * @param orderNo the orderNo to set
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * @return the cardMainCode
     */
    public String getCardMainCode() {
        return cardMainCode;
    }

    /**
     * @param cardMainCode the cardMainCode to set
     */
    public void setCardMainCode(String cardMainCode) {
        this.cardMainCode = cardMainCode;
    }

    /**
     * @return the cardSubCode
     */
    public String getCardSubCode() {
        return cardSubCode;
    }

    /**
     * @param cardSubCode the cardSubCode to set
     */
    public void setCardSubCode(String cardSubCode) {
        this.cardSubCode = cardSubCode;
    }

    /**
     * @return the reqNo
     */
    public String getReqNo() {
        return reqNo;
    }

    /**
     * @param reqNo the reqNo to set
     */
    public void setReqNo(String reqNo) {
        this.reqNo = reqNo;
    }

    /**
     * @return the logiId
     */
    public String getLogiId() {
        return logiId;
    }

    /**
     * @param logiId the logiId to set
     */
    public void setLogiId(String logiId) {
        this.logiId = logiId;
    }

    /**
     * @return the printId
     */
    public String getPrintId() {
        return printId;
    }

    /**
     * @param printId the printId to set
     */
    public void setPrintId(String printId) {
        this.printId = printId;
    }

    /**
     * @return the phyId
     */
    public String getPhyId() {
        return phyId;
    }

    /**
     * @param phyId the phyId to set
     */
    public void setPhyId(String phyId) {
        this.phyId = phyId;
    }

    /**
     * @return the manuTime
     */
    public String getManuTime() {
        return manuTime;
    }

    /**
     * @param manuTime the manuTime to set
     */
    public void setManuTime(String manuTime) {
        this.manuTime = manuTime;
    }

    /**
     * @return the cardMon
     */
    public String getCardMon() {
        return cardMon;
    }

    /**
     * @param cardMon the cardMon to set
     */
    public void setCardMon(String cardMon) {
        this.cardMon = cardMon;
    }

    /**
     * @return the periAvadate
     */
    public String getPeriAvadate() {
        return periAvadate;
    }

    /**
     * @param periAvadate the periAvadate to set
     */
    public void setPeriAvadate(String periAvadate) {
        this.periAvadate = periAvadate;
    }

    /**
     * @return the lineCode
     */
    public String getLineCode() {
        return lineCode;
    }

    /**
     * @param lineCode the lineCode to set
     */
    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }

    /**
     * @return the stationCode
     */
    public String getStationCode() {
        return stationCode;
    }

    /**
     * @param stationCode the stationCode to set
     */
    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    /**
     * @return the cardStartAva
     */
    public String getCardStartAva() {
        return cardStartAva;
    }

    /**
     * @param cardStartAva the cardStartAva to set
     */
    public void setCardStartAva(String cardStartAva) {
        this.cardStartAva = cardStartAva;
    }

    /**
     * @return the cardAvaDays
     */
    public String getCardAvaDays() {
        return cardAvaDays;
    }

    /**
     * @param cardAvaDays the cardAvaDays to set
     */
    public void setCardAvaDays(String cardAvaDays) {
        this.cardAvaDays = cardAvaDays;
    }

    /**
     * @return the exitLineCode
     */
    public String getExitLineCode() {
        return exitLineCode;
    }

    /**
     * @param exitLineCode the exitLineCode to set
     */
    public void setExitLineCode(String exitLineCode) {
        this.exitLineCode = exitLineCode;
    }

    /**
     * @return the exitStationCode
     */
    public String getExitStationCode() {
        return exitStationCode;
    }

    /**
     * @param exitStationCode the exitStationCode to set
     */
    public void setExitStationCode(String exitStationCode) {
        this.exitStationCode = exitStationCode;
    }

    /**
     * @return the mode
     */
    public String getMode() {
        return mode;
    }

    /**
     * @param mode the mode to set
     */
    public void setMode(String mode) {
        this.mode = mode;
    }

    /**
     * @return the kdcVersion
     */
    public String getKdcVersion() {
        return kdcVersion;
    }

    /**
     * @param kdcVersion the kdcVersion to set
     */
    public void setKdcVersion(String kdcVersion) {
        this.kdcVersion = kdcVersion;
    }

    /**
     * @return the hdlTime
     */
    public String getHdlTime() {
        return hdlTime;
    }

    /**
     * @param hdlTime the hdlTime to set
     */
    public void setHdlTime(String hdlTime) {
        this.hdlTime = hdlTime;
    }

    /**
     * @return the statusFlag
     */
    public String getStatusFlag() {
        return statusFlag;
    }

    /**
     * @param statusFlag the statusFlag to set
     */
    public void setStatusFlag(String statusFlag) {
        this.statusFlag = statusFlag;
    }

    /**
     * @return the cardType
     */
    public String getCardType() {
        return cardType;
    }

    /**
     * @param cardType the cardType to set
     */
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    /**
     * @return the cardProducerCode
     */
    public String getCardProducerCode() {
        return cardProducerCode;
    }

    /**
     * @param cardProducerCode the cardProducerCode to set
     */
    public void setCardProducerCode(String cardProducerCode) {
        this.cardProducerCode = cardProducerCode;
    }

    /**
     * @return the phoneNo
     */
    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     * @param phoneNo the phoneNo to set
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
