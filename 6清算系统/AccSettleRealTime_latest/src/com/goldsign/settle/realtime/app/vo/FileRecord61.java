/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.vo;

import com.goldsign.settle.realtime.frame.vo.FileRecordBase;

/**
 *
 * @author hejj
 */
public class FileRecord61 extends FileRecordBase{

    /**
     * @return the returnFee_s
     */
    public String getReturnFee_s() {
        return returnFee_s;
    }

    /**
     * @param returnFee_s the returnFee_s to set
     */
    public void setReturnFee_s(String returnFee_s) {
        this.returnFee_s = returnFee_s;
    }

    /**
     * @return the penaltyFee_s
     */
    public String getPenaltyFee_s() {
        return penaltyFee_s;
    }

    /**
     * @param penaltyFee_s the penaltyFee_s to set
     */
    public void setPenaltyFee_s(String penaltyFee_s) {
        this.penaltyFee_s = penaltyFee_s;
    }

    /**
     * @return the adminDatetime_s
     */
    public String getAdminDatetime_s() {
        return adminDatetime_s;
    }

    /**
     * @param adminDatetime_s the adminDatetime_s to set
     */
    public void setAdminDatetime_s(String adminDatetime_s) {
        this.adminDatetime_s = adminDatetime_s;
    }

    private String adminDatetime;
    private String adminWayId;//6	事务代码
    private int returnFee;//8	退给乘客的金额
    private int penaltyFee;//9	收取的罚金
    private String adminReasonId;//10	行政处理原因
    private String describe;//11	行政处理描述
    private String passengerName;//12	乘客姓名
    
    private String returnFee_s;//8	退给乘客的金额
    private String penaltyFee_s;//9	收取的罚金
    private String adminDatetime_s;

    /**
     * @return the adminDatetime
     */
    public String getAdminDatetime() {
        return adminDatetime;
    }

    /**
     * @param adminDatetime the adminDatetime to set
     */
    public void setAdminDatetime(String adminDatetime) {
        this.adminDatetime = adminDatetime;
    }

    /**
     * @return the adminWayId
     */
    public String getAdminWayId() {
        return adminWayId;
    }

    /**
     * @param adminWayId the adminWayId to set
     */
    public void setAdminWayId(String adminWayId) {
        this.adminWayId = adminWayId;
    }

    /**
     * @return the returnFee
     */
    public int getReturnFee() {
        return returnFee;
    }

    /**
     * @param returnFee the returnFee to set
     */
    public void setReturnFee(int returnFee) {
        this.returnFee = returnFee;
    }

    /**
     * @return the penaltyFee
     */
    public int getPenaltyFee() {
        return penaltyFee;
    }

    /**
     * @param penaltyFee the penaltyFee to set
     */
    public void setPenaltyFee(int penaltyFee) {
        this.penaltyFee = penaltyFee;
    }

    /**
     * @return the adminReasonId
     */
    public String getAdminReasonId() {
        return adminReasonId;
    }

    /**
     * @param adminReasonId the adminReasonId to set
     */
    public void setAdminReasonId(String adminReasonId) {
        this.adminReasonId = adminReasonId;
    }

    /**
     * @return the describe
     */
    public String getDescribe() {
        return describe;
    }

    /**
     * @param describe the describe to set
     */
    public void setDescribe(String describe) {
        this.describe = describe;
    }

    /**
     * @return the passengerName
     */
    public String getPassengerName() {
        return passengerName;
    }

    /**
     * @param passengerName the passengerName to set
     */
    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }
}
