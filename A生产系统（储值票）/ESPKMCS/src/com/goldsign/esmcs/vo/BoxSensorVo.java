/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.vo;

/**
 *
 * @author lenovo
 */
public class BoxSensorVo {
    
    private byte s1;  //票箱升降满检测--未满,满
    
    private byte s2;  //票箱升降将满检测--未在此位置,票箱将满
    
    private byte s3;  //票箱上限位检测--未到达,到达
    
    private byte s4;  //票箱进票空间检测--有空间进票,无空间进票
    
    private byte s5;  //票箱锁检测--打开,未打开
    
    private byte s6;  //票箱票箱盖检测--未打开,打开

    /**
     * @return the s1
     */
    public byte getS1() {
        return s1;
    }

    /**
     * @param s1 the s1 to set
     */
    public void setS1(byte s1) {
        this.s1 = s1;
    }

    /**
     * @return the s2
     */
    public byte getS2() {
        return s2;
    }

    /**
     * @param s2 the s2 to set
     */
    public void setS2(byte s2) {
        this.s2 = s2;
    }

    /**
     * @return the s3
     */
    public byte getS3() {
        return s3;
    }

    /**
     * @param s3 the s3 to set
     */
    public void setS3(byte s3) {
        this.s3 = s3;
    }

    /**
     * @return the s4
     */
    public byte getS4() {
        return s4;
    }

    /**
     * @param s4 the s4 to set
     */
    public void setS4(byte s4) {
        this.s4 = s4;
    }

    /**
     * @return the s5
     */
    public byte getS5() {
        return s5;
    }

    /**
     * @param s5 the s5 to set
     */
    public void setS5(byte s5) {
        this.s5 = s5;
    }

    /**
     * @return the s6
     */
    public byte getS6() {
        return s6;
    }

    /**
     * @param s6 the s6 to set
     */
    public void setS6(byte s6) {
        this.s6 = s6;
    }
    
}
