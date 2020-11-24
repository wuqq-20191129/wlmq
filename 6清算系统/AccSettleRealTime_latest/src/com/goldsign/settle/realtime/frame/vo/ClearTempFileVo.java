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
public class ClearTempFileVo {
     private int id;
    private int clearType;
    private String pathClear;
    private int reserveDays;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the clearType
     */
    public int getClearType() {
        return clearType;
    }

    /**
     * @param clearType the clearType to set
     */
    public void setClearType(int clearType) {
        this.clearType = clearType;
    }

    /**
     * @return the pathClear
     */
    public String getPathClear() {
        return pathClear;
    }

    /**
     * @param pathClear the pathClear to set
     */
    public void setPathClear(String pathClear) {
        this.pathClear = pathClear;
    }

    /**
     * @return the reserveDays
     */
    public int getReserveDays() {
        return reserveDays;
    }

    /**
     * @param reserveDays the reserveDays to set
     */
    public void setReserveDays(int reserveDays) {
        this.reserveDays = reserveDays;
    }
    
}
