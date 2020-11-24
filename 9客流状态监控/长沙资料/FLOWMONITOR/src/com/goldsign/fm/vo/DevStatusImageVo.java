/*
 * 文件名：DevStatusImageVo
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.fm.vo;


/*
 * 设备状态图片对照实体类
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-10-18
 */

public class DevStatusImageVo {
    private String deviceTypeID = "";
    private String status = "";
    private String imagURL = "";
    private String description = "";
    private String imageDirection = "";

    public String getDeviceTypeID() {
        return deviceTypeID;
    }

    public void setDeviceTypeID(String deviceTypeID) {
        this.deviceTypeID = deviceTypeID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImagURL() {
        return imagURL;
    }

    public void setImagURL(String imagURL) {
        this.imagURL = imagURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageDirection() {
        return imageDirection;
    }

    public void setImageDirection(String imageDirection) {
        this.imageDirection = imageDirection;
    }
}
