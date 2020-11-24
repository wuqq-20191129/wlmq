/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.vo;

/**
 *
 * @author hejj
 */
public class BcpResult {
    private String total;
    private String exitNo;
    private String msg;

    /**
     * @return the total
     */
    public String getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(String total) {
        this.total = total;
    }

    /**
     * @return the exitNo
     */
    public String getExitNo() {
        return exitNo;
    }

    /**
     * @param exitNo the exitNo to set
     */
    public void setExitNo(String exitNo) {
        this.exitNo = exitNo;
    }

    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg the msg to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
}
