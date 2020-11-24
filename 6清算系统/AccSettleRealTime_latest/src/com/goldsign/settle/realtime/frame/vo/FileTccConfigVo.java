/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.vo;

/**
 *
 * @author hejj
 */
public class FileTccConfigVo {

    private String waterNo;
    private String id;//标识
    private String period;//生成周期 1：一天一次 9：人工控制，人工生效标识为1时生成，0时不生成
    private String manuValidFlag;//人工生效标识 0：不生效
    private String fileNameBase;//基础文件名
    private String remark;

    /**
     * @return the waterNo
     */
    public String getWaterNo() {
        return waterNo;
    }

    /**
     * @param waterNo the waterNo to set
     */
    public void setWaterNo(String waterNo) {
        this.waterNo = waterNo;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the period
     */
    public String getPeriod() {
        return period;
    }

    /**
     * @param period the period to set
     */
    public void setPeriod(String period) {
        this.period = period;
    }

    /**
     * @return the manuValidFlag
     */
    public String getManuValidFlag() {
        return manuValidFlag;
    }

    /**
     * @param manuValidFlag the manuValidFlag to set
     */
    public void setManuValidFlag(String manuValidFlag) {
        this.manuValidFlag = manuValidFlag;
    }

    /**
     * @return the fileNameBase
     */
    public String getFileNameBase() {
        return fileNameBase;
    }

    /**
     * @param fileNameBase the fileNameBase to set
     */
    public void setFileNameBase(String fileNameBase) {
        this.fileNameBase = fileNameBase;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

}
