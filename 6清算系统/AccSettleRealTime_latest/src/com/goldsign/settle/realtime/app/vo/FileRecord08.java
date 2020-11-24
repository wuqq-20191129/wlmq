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
public class FileRecord08 extends FileRecordBase{

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

    private int sjtNum;// 普通成人单程票数量
    private int sjtDiscountNum;//优惠单程票数量

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
     * @return the sjtNum
     */
    public int getSjtNum() {
        return sjtNum;
    }

    /**
     * @param sjtNum the sjtNum to set
     */
    public void setSjtNum(int sjtNum) {
        this.sjtNum = sjtNum;
    }

    /**
     * @return the sjtDiscountNum
     */
    public int getSjtDiscountNum() {
        return sjtDiscountNum;
    }

    /**
     * @param sjtDiscountNum the sjtDiscountNum to set
     */
    public void setSjtDiscountNum(int sjtDiscountNum) {
        this.sjtDiscountNum = sjtDiscountNum;
    }
    
}
