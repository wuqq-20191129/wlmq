/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.fm.vo;

/**
 *
 * @author Administrator
 */
public class ScreenStationTrafficVO {
  private String lineCode ="";
  private String lineText ="";
  private String stationCode ="";
  private String stationText ="";
  private String stationEngText ="";
  private String trafficIn ="";
  private String trafficOut ="";
  private String isEnglishVersion ="";
  private boolean  isTotal =false;;



    /**
     * @return the lineCode
     */
    public String getLineCode() {
        return lineCode;
    }

    /**
     * @param lineCode the lineCode to set
     */
    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }

    /**
     * @return the lineText
     */
    public String getLineText() {
        return lineText;
    }

    /**
     * @param lineText the lineText to set
     */
    public void setLineText(String lineText) {
        this.lineText = lineText;
    }

    /**
     * @return the stationCode
     */
    public String getStationCode() {
        return stationCode;
    }

    /**
     * @param stationCode the stationCode to set
     */
    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    /**
     * @return the stationText
     */
    public String getStationText() {
        return stationText;
    }

    /**
     * @param stationText the stationText to set
     */
    public void setStationText(String stationText) {
        this.stationText = stationText;
    }

    /**
     * @return the stationEngText
     */
    public String getStationEngText() {
        return stationEngText;
    }

    /**
     * @param stationEngText the stationEngText to set
     */
    public void setStationEngText(String stationEngText) {
        this.stationEngText = stationEngText;
    }

    /**
     * @return the trafficIn
     */
    public String getTrafficIn() {
        return trafficIn;
    }

    /**
     * @param trafficIn the trafficIn to set
     */
    public void setTrafficIn(String trafficIn) {
        this.trafficIn = trafficIn;
    }

    /**
     * @return the trafficOut
     */
    public String getTrafficOut() {
        return trafficOut;
    }

    /**
     * @param trafficOut the trafficOut to set
     */
    public void setTrafficOut(String trafficOut) {
        this.trafficOut = trafficOut;
    }

    /**
     * @return the isEnglishVersion
     */
    public String getIsEnglishVersion() {
        return isEnglishVersion;
    }

    /**
     * @param isEnglishVersion the isEnglishVersion to set
     */
    public void setIsEnglishVersion(String isEnglishVersion) {
        this.isEnglishVersion = isEnglishVersion;
    }

    /**
     * @return the isTotal
     */
    public boolean  getIsTotal() {
        return isTotal;
    }

    /**
     * @param isTotal the isTotal to set
     */
    public void setIsTotal( boolean isTotal) {
        this.isTotal = isTotal;
    }

}
