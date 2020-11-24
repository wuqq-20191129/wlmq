/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.vo;

/**
 *
 * @author hejj
 */
public class VersionCompareResult {

    private boolean diff = false;
    private int cutNum = 0;

    /**
     * @return the diff
     */
    public boolean isDiff() {
        return diff;
    }

    /**
     * @param diff the diff to set
     */
    public void setDiff(boolean diff) {
        this.diff = diff;
    }

    /**
     * @return the cutNum
     */
    public int getCutNum() {
        return cutNum;
    }

    /**
     * @param cutNum the cutNum to set
     */
    public void setCutNum(int cutNum) {
        this.cutNum = cutNum;
    }

}
