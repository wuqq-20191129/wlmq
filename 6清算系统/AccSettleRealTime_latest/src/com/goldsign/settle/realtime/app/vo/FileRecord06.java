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
public class FileRecord06 extends FileRecordBase {

    /**
     * @return the clearDatetime_s
     */
    public String getClearDatetime_s() {
        return clearDatetime_s;
    }

    /**
     * @param clearDatetime_s the clearDatetime_s to set
     */
    public void setClearDatetime_s(String clearDatetime_s) {
        this.clearDatetime_s = clearDatetime_s;
    }

    private String clearDatetime;//清空时间
    private String clearDatetime_s;//清空时间
    private int waterNoOp;//流水号
    private String cardMainIdHopper1;//Hopper 1单程票主类型
    private String cardSubIdHopper1;//Hopper 1单程票子类型
    private String cardMainIdHopper2;//Hopper 2单程票主类型
    private String cardSubIdHopper2;//Hopper 2单程票子类型
    private int hopper1Num;//Hopper 1单程票数量
    private int hopper2Num;//Hopper 2单程票数量

    /**
     * @return the clearDatetime
     */
    public String getClearDatetime() {
        return clearDatetime;
    }

    /**
     * @param clearDatetime the clearDatetime to set
     */
    public void setClearDatetime(String clearDatetime) {
        this.clearDatetime = clearDatetime;
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
     * @return the cardMainIdHopper1
     */
    public String getCardMainIdHopper1() {
        return cardMainIdHopper1;
    }

    /**
     * @param cardMainIdHopper1 the cardMainIdHopper1 to set
     */
    public void setCardMainIdHopper1(String cardMainIdHopper1) {
        this.cardMainIdHopper1 = cardMainIdHopper1;
    }

    /**
     * @return the cardSubIdHopper1
     */
    public String getCardSubIdHopper1() {
        return cardSubIdHopper1;
    }

    /**
     * @param cardSubIdHopper1 the cardSubIdHopper1 to set
     */
    public void setCardSubIdHopper1(String cardSubIdHopper1) {
        this.cardSubIdHopper1 = cardSubIdHopper1;
    }

    /**
     * @return the cardMainIdHopper2
     */
    public String getCardMainIdHopper2() {
        return cardMainIdHopper2;
    }

    /**
     * @param cardMainIdHopper2 the cardMainIdHopper2 to set
     */
    public void setCardMainIdHopper2(String cardMainIdHopper2) {
        this.cardMainIdHopper2 = cardMainIdHopper2;
    }

    /**
     * @return the cardSubIdHopper2
     */
    public String getCardSubIdHopper2() {
        return cardSubIdHopper2;
    }

    /**
     * @param cardSubIdHopper2 the cardSubIdHopper2 to set
     */
    public void setCardSubIdHopper2(String cardSubIdHopper2) {
        this.cardSubIdHopper2 = cardSubIdHopper2;
    }

    /**
     * @return the hopper1Num
     */
    public int getHopper1Num() {
        return hopper1Num;
    }

    /**
     * @param hopper1Num the hopper1Num to set
     */
    public void setHopper1Num(int hopper1Num) {
        this.hopper1Num = hopper1Num;
    }

    /**
     * @return the hopper2Num
     */
    public int getHopper2Num() {
        return hopper2Num;
    }

    /**
     * @param hopper2Num the hopper2Num to set
     */
    public void setHopper2Num(int hopper2Num) {
        this.hopper2Num = hopper2Num;
    }

}
