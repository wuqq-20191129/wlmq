/*
 * 文件名：DevMonitorNodeVo
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.fm.vo;


/*
 * 当前车站设备状态结果实体
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-10-19
 */

public class DevMonitorNodeVo {
    
    private String nodeValue= "";//节点值
    private String nodeType="";//节点类型
    private String src = "";//图片路径
    private int posX = 0;//图片开始X坐标
    private int posY = 0;//图片开始Y坐标
    private int width = 0;//图片宽
    private int height = 0;//图片高
    private String text = "";//文字
    private String fontSize = "0";//文字大小
    private String fontColor = "";//文字颜色
    private String imageDirection = "";//
    private String alt = "";//
    private String imagFlag = "";//

    public String getNodeValue() {
        return nodeValue;
    }

    public void setNodeValue(String nodeValue) {
        this.nodeValue = nodeValue;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFontSize() {
        return fontSize;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public String getImageDirection() {
        return imageDirection;
    }

    public void setImageDirection(String imageDirection) {
        this.imageDirection = imageDirection;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getImagFlag() {
        return imagFlag;
    }

    public void setImagFlag(String imagFlag) {
        this.imagFlag = imagFlag;
    }

}
