/*
 * 文件名：DevMonitorVo
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.fm.vo;


/*
 * 设备状态监控表Vo
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-10-18
 */

public class DevMonitorVo {
    
    private String nodeID ="";
    private String nodeType ="";//类型：01动态图片，02，静态图片，03文字，04线条
    private String nodeDescription ="";
    private String imageURL ="";//图片路径
    private int posX =0;//x坐标
    private int posY =0;//y坐标
    private int startX =0;//线条开始x坐标
    private int startY =0;//线条开始y坐标
    private int endX =0;//线条结束x坐标
    private int endY =0;//线条结束y坐标

    private String lineID ="";//线路
    private String stationID ="";//车站
    private String deviceType ="";//设备类型
    private String deviceID ="";//设备ID
    private String fontSize = "";//文字大小
    private String imageDirection = "";//
    private String state = "4";

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNodeID() {
        return nodeID;
    }

    public void setNodeID(String nodeID) {
        this.nodeID = nodeID;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getNodeDescription() {
        return nodeDescription;
    }

    public void setNodeDescription(String nodeDescription) {
        this.nodeDescription = nodeDescription;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getEndX() {
        return endX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public int getEndY() {
        return endY;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    public String getLineID() {
        return lineID;
    }

    public void setLineID(String lineID) {
        this.lineID = lineID;
    }

    public String getStationID() {
        return stationID;
    }

    public void setStationID(String stationID) {
        this.stationID = stationID;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getFontSize() {
        return fontSize;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public String getImageDirection() {
        return imageDirection;
    }

    public void setImageDirection(String imageDirection) {
        this.imageDirection = imageDirection;
    }

}
