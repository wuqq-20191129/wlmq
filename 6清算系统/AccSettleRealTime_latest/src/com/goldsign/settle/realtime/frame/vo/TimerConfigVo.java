/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.vo;

import java.util.Vector;



/**
 *
 * @author hejj
 */
public class TimerConfigVo {

    private String timerId;
    private Vector years;
    private Vector months;
    private Vector days;
    private Vector hours;
    private Vector mins;
    private String remark;
    private String controlFlag;



    /**
     * @return the years
     */
    public Vector getYears() {
        return years;
    }

    /**
     * @param years the years to set
     */
    public void setYears(Vector years) {
        this.years = years;
    }

    /**
     * @return the months
     */
    public Vector getMonths() {
        return months;
    }

    /**
     * @param months the months to set
     */
    public void setMonths(Vector months) {
        this.months = months;
    }

    /**
     * @return the days
     */
    public Vector getDays() {
        return days;
    }

    /**
     * @param days the days to set
     */
    public void setDays(Vector days) {
        this.days = days;
    }

    /**
     * @return the hours
     */
    public Vector getHours() {
        return hours;
    }

    /**
     * @param hours the hours to set
     */
    public void setHours(Vector hours) {
        this.hours = hours;
    }

    /**
     * @return the mins
     */
    public Vector getMins() {
        return mins;
    }

    /**
     * @param mins the mins to set
     */
    public void setMins(Vector mins) {
        this.mins = mins;
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

    /**
     * @return the controlFlag
     */
    public String getControlFlag() {
        return controlFlag;
    }

    /**
     * @param controlFlag the controlFlag to set
     */
    public void setControlFlag(String controlFlag) {
        this.controlFlag = controlFlag;
    }

    /**
     * @return the timerId
     */
    public String getTimerId() {
        return timerId;
    }

    /**
     * @param timerId the timerId to set
     */
    public void setTimerId(String timerId) {
        this.timerId = timerId;
    }
}
