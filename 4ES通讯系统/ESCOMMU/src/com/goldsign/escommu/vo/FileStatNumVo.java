/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.escommu.vo;

/**
 *
 * @author Administrator
 */
public class FileStatNumVo {

    private int statNum;
    private String esSamNo;
    private String CRC;
    private String fileName;
    //

    /**
     * @return the statNum
     */
    public int getStatNum() {
        return statNum;
    }

    /**
     * @param statNum the statNum to set
     */
    public void setStatNum(int statNum) {
        this.statNum = statNum;
    }

    /**
     * @return the esSamNo
     */
    public String getEsSamNo() {
        return esSamNo;
    }

    /**
     * @param esSamNo the esSamNo to set
     */
    public void setEsSamNo(String esSamNo) {
        this.esSamNo = esSamNo;
    }

    /**
     * @return the CRC
     */
    public String getCRC() {
        return CRC;
    }

    /**
     * @param CRC the CRC to set
     */
    public void setCRC(String CRC) {
        this.CRC = CRC;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
