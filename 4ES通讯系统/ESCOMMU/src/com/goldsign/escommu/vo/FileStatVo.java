/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.escommu.vo;

import java.util.Vector;

/**
 *
 * @author Administrator
 */
public class FileStatVo {

    private String esWorktypeId;//操作类型
    private String orderNo;//订单编号
    private int drawNum=0;//领票数量
    private int finiPronum=0;//成品数量
    private int surplusNum=0;//节余票数量
    private int trashyNum=0;//废票数量
    private String achieveTime;//完成日期时间
    private String hdlFlag ;//完成标志
    private String orderMemo;//备注
    private String mBPduType;

   // private String esSamno;//es SAM卡号

    private int statNum;
    private String esSamNo;
    private String CRC;
    private String fileName;
    private int statNumDetail =0;
    private boolean isResetNum =false;
    private int resetNum=0;
    private int resetNumBefore=0;

     private boolean isExistReapeat =false;
     private Vector reapeatRecords= new Vector();

    /**
     * @return the drawNum
     */
    public int getDrawNum() {
        return drawNum;
    }

    /**
     * @param drawNum the drawNum to set
     */
    public void setDrawNum(int drawNum) {
        this.drawNum = drawNum;
    }

    /**
     * @return the finiPronum
     */
    public int getFiniPronum() {
        return finiPronum;
    }

    /**
     * @param finiPronum the finiPronum to set
     */
    public void setFiniPronum(int finiPronum) {
        this.finiPronum = finiPronum;
    }

    /**
     * @return the surplusNum
     */
    public int getSurplusNum() {
        return surplusNum;
    }

    /**
     * @param surplusNum the surplusNum to set
     */
    public void setSurplusNum(int surplusNum) {
        this.surplusNum = surplusNum;
    }

    /**
     * @return the trashyNum
     */
    public int getTrashyNum() {
        return trashyNum;
    }

    /**
     * @param trashyNum the trashyNum to set
     */
    public void setTrashyNum(int trashyNum) {
        this.trashyNum = trashyNum;
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
     * @return the hdlFlag
     */
    public String getHdlFlag() {
        return hdlFlag;
    }

    /**
     * @param hdlFlag the hdlFlag to set
     */
    public void setHdlFlag(String hdlFlag) {
        this.hdlFlag = hdlFlag;
    }

    /**
     * @return the achieveTime
     */
    public String getAchieveTime() {
        return achieveTime;
    }

    /**
     * @param achieveTime the achieveTime to set
     */
    public void setAchieveTime(String achieveTime) {
        this.achieveTime = achieveTime;
    }

    /**
     * @return the orderMemo
     */
    public String getOrderMemo() {
        return orderMemo;
    }

    /**
     * @param orderMemo the orderMemo to set
     */
    public void setOrderMemo(String orderMemo) {
        this.orderMemo = orderMemo;
    }

    /**
     * @return the esSamno
     */


    /**
     * @return the statNum
     */
    public int getStatNum() {
        return statNum;
    }

    /**
     * @param statNum the statNum to set
     */
    public void setStatNum(int statNum) {
        this.statNum = statNum;
    }

    /**
     * @return the esSamNo
     */
    public String getEsSamNo() {
        return esSamNo;
    }

    /**
     * @param esSamNo the esSamNo to set
     */
    public void setEsSamNo(String esSamNo) {
        this.esSamNo = esSamNo;
    }

    /**
     * @return the CRC
     */
    public String getCRC() {
        return CRC;
    }

    /**
     * @param CRC the CRC to set
     */
    public void setCRC(String CRC) {
        this.CRC = CRC;
    }

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
     * @return the statNumDetail
     */
    public int getStatNumDetail() {
        return statNumDetail;
    }

    /**
     * @param statNumDetail the statNumDetail to set
     */
    public void setStatNumDetail(int statNumDetail) {
        this.statNumDetail = statNumDetail;
    }

    /**
     * @return the isRetNum
     */
    public boolean isResetNum() {
        return isIsResetNum();
    }

    /**
     * @param isRetNum the isRetNum to set
     */
    public void setResetNum(boolean isRetNum) {
        this.setIsResetNum(isRetNum);
    }

    /**
     * @return the resetNum
     */
    public int getResetNum() {
        return resetNum;
    }

    /**
     * @param resetNum the resetNum to set
     */
    public void setResetNum(int resetNum) {
        this.resetNum = resetNum;
    }

    /**
     * @return the resetNumBefore
     */
    public int getResetNumBefore() {
        return resetNumBefore;
    }

    /**
     * @param resetNumBefore the resetNumBefore to set
     */
    public void setResetNumBefore(int resetNumBefore) {
        this.resetNumBefore = resetNumBefore;
    }

    /**
     * @return the isResetNum
     */
    public boolean isIsResetNum() {
        return isResetNum;
    }

    /**
     * @param isResetNum the isResetNum to set
     */
    public void setIsResetNum(boolean isResetNum) {
        this.isResetNum = isResetNum;
    }

    /**
     * @return the isExistReapeat
     */
    public boolean isIsExistReapeat() {
        return isExistReapeat;
    }

    /**
     * @param isExistReapeat the isExistReapeat to set
     */
    public void setIsExistReapeat(boolean isExistReapeat) {
        this.isExistReapeat = isExistReapeat;
    }

    /**
     * @return the reapeatRecords
     */
    public Vector getReapeatRecords() {
        return reapeatRecords;
    }

    /**
     * @param reapeatRecords the reapeatRecords to set
     */
    public void setReapeatRecords(Vector reapeatRecords) {
        this.reapeatRecords = reapeatRecords;
    }

    /**
     * @return the mBPduType
     */
    public String getmBPduType() {
        return mBPduType;
    }

    /**
     * @param mBPduType the mBPduType to set
     */
    public void setmBPduType(String mBPduType) {
        this.mBPduType = mBPduType;
    }

}

