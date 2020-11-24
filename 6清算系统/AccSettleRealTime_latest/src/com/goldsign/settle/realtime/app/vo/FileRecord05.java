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
public class FileRecord05 extends FileRecordBase {

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

    private int hopperId;//单程票Hopper
    private String putDatetime;//存入时间
    private String putDatetime_s;//存入时间
    private int waterNoOp;//流水号
    private String boxId;//票箱编号
    private int num;//数量

    /**
     * @return the hopperId
     */
    public int getHopperId() {
        return hopperId;
    }

    /**
     * @param hopperId the hopperId to set
     */
    public void setHopperId(int hopperId) {
        this.hopperId = hopperId;
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
