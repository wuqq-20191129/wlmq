/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.escommu.vo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lenovo
 */
public class CardSectionRspVo {

    private String deviceId;
    private String reqDatetime;
    private String reqResult;
    private String fileName;
    private List<CardSectionVo> cardSectionVos = new ArrayList<CardSectionVo>();
    private boolean resResult = false;

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the deviceId
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * @param deviceId the deviceId to set
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * @return the reqDatetime
     */
    public String getReqDatetime() {
        return reqDatetime;
    }

    /**
     * @param reqDatetime the reqDatetime to set
     */
    public void setReqDatetime(String reqDatetime) {
        this.reqDatetime = reqDatetime;
    }

    /**
     * @return the reqResult
     */
    public String getReqResult() {
        return reqResult;
    }

    /**
     * @param reqResult the reqResult to set
     */
    public void setReqResult(String reqResult) {
        this.reqResult = reqResult;
    }

    /**
     * @return the cardSectionVos
     */
    public List<CardSectionVo> getCardSectionVos() {
        return cardSectionVos;
    }

    /**
     * @param cardSectionVos the cardSectionVos to set
     */
    public void setCardSectionVos(List<CardSectionVo> cardSectionVos) {
        this.cardSectionVos = cardSectionVos;
    }

    /**
     * @return the resResult
     */
    public boolean isResResult() {
        return resResult;
    }

    /**
     * @param resResult the resResult to set
     */
    public void setResResult(boolean resResult) {
        this.resResult = resResult;
    }

}
