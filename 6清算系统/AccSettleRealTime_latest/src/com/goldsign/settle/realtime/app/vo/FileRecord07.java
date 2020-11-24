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
public class FileRecord07 extends FileRecordBase{

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
    private String popDatetime;//票箱取出时间
    private String popDatetime_s;//票箱取出时间
    private int waterNoOp;//流水号
    private String boxId;// 票箱编号
    private int num;//车票数量

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

    /**
     * @return the boxId
     */
    public String getBoxId() {
        return boxId;
    }

    /**
     * @param boxId the boxId to set
     */
    public void setBoxId(String boxId) {
        this.boxId = boxId;
    }

    /**
     * @return the num
     */
    public int getNum() {
        return num;
    }

    /**
     * @param num the num to set
     */
    public void setNum(int num) {
        this.num = num;
    }

   
    
}
