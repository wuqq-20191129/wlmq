/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.vo;


import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class FileRecord01 extends FileRecordTVMCoinBase{

    /**
     * @return the detail
     */


    /**
     * @return the putDatetime_s
     */
    public String getPutDatetime_s() {
        return putDatetime_s;
    }

    /**
     * @param putDatetime_s the putDatetime_s to set
     */
    public void setPutDatetime_s(String putDatetime_s) {
        this.putDatetime_s = putDatetime_s;
    }

    /**
     * @return the popDatetime_s
     */
    public String getPopDatetime_s() {
        return popDatetime_s;
    }

    /**
     * @param popDatetime_s the popDatetime_s to set
     */
    public void setPopDatetime_s(String popDatetime_s) {
        this.popDatetime_s = popDatetime_s;
    }
    private String coinBoxId;//硬币箱编号
    //private String operatorId;//操作员的员工号
    private String putDatetime;//硬币箱放入时间
    private String popDatetime;//硬币箱取出时间
     private String putDatetime_s;//硬币箱放入时间
    private String popDatetime_s;//硬币箱取出时间
    private int waterNoOp;//流水号
    
    

    /**
     * @return the coinBoxId
     */
    public String getCoinBoxId() {
        return coinBoxId;
    }

    /**
     * @param coinBoxId the coinBoxId to set
     */
    public void setCoinBoxId(String coinBoxId) {
        this.coinBoxId = coinBoxId;
    }

    /**
     * @return the putDatetime
     */
    public String getPutDatetime() {
        return putDatetime;
    }

    /**
     * @param putDatetime the putDatetime to set
     */
    public void setPutDatetime(String putDatetime) {
        this.putDatetime = putDatetime;
    }

    /**
     * @return the popDatetime
     */
    public String getPopDatetime() {
        return popDatetime;
    }

    /**
     * @param popDatetime the popDatetime to set
     */
    public void setPopDatetime(String popDatetime) {
        this.popDatetime = popDatetime;
    }

    /**
     * @return the waterNoOp
     */
    public int getWaterNoOp() {
        return waterNoOp;
    }

    /**
     * @param waterNoOp the waterNoOp to set
     */
    public void setWaterNoOp(int waterNoOp) {
        this.waterNoOp = waterNoOp;
    }
   
    

    
}
