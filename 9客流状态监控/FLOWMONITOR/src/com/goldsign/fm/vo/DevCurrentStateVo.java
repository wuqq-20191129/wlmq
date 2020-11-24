/*
 * 文件名：DevCurrentStateVo
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.fm.vo;

import java.util.Date;


/*
 * 当前设备状态信息实例
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-10-18
 */

public class DevCurrentStateVo {
    
    private String lineId = "";
    private String stationID ="";
    private String deviceTypeId ="";
    private String deviceID ="";
    private String accStatusValue = "";
    private String statusId = "";
    private String statusValue = "";
    private String statusName = "";
    private Date statusDateTime;
    private String accStatusName = "";

    public String getAccStatusName() {
        return accStatusName;
    }

    public void setAccStatusName(String accStatusName) {
        this.accStatusName = accStatusName;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getStationID() {
        return stationID;
    }

    public void setStationID(String stationID) {
        this.stationID = stationID;
    }

    public String getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(String deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getAccStatusValue() {
        return accStatusValue;
    }

    public void setAccStatusValue(String accStatusValue) {
        this.accStatusValue = accStatusValue;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(String statusValue) {
        this.statusValue = statusValue;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Date getStatusDateTime() {
        return statusDateTime;
    }

    public void setStatusDateTime(Date statusDateTime) {
        this.statusDateTime = statusDateTime;
    }
    
}
