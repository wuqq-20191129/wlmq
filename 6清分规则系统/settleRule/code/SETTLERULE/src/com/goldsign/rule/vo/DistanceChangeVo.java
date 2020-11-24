/*
 * 文件名：DistanceChangeVo
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.rule.vo;


/*
 * 换乘乘距实体类
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-11-12
 */

public class DistanceChangeVo {
    
    private String id;//id
    private String pChangeStationId;//上一换乘站
    private String nChangeStationId;//下一换乘站
    private String passLineOut;//转出线路
    private String passLineIn;//转入线路
    private String passDistance;//转出线路乘距
    
    private String pChangeStationIdText;     //
    private String nChangeStationIdText;     //
    private String passLineOutText;   // 
    private String passLineInText;   // 

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpChangeStationId() {
        return pChangeStationId;
    }

    public void setpChangeStationId(String pChangeStationId) {
        this.pChangeStationId = pChangeStationId;
    }

    public String getnChangeStationId() {
        return nChangeStationId;
    }

    public void setnChangeStationId(String nChangeStationId) {
        this.nChangeStationId = nChangeStationId;
    }

    public String getPassLineOut() {
        return passLineOut;
    }

    public void setPassLineOut(String passLineOut) {
        this.passLineOut = passLineOut;
    }

    public String getPassLineIn() {
        return passLineIn;
    }

    public void setPassLineIn(String passLineIn) {
        this.passLineIn = passLineIn;
    }

    public String getPassDistance() {
        return passDistance;
    }

    public void setPassDistance(String passDistance) {
        this.passDistance = passDistance;
    }

    public String getpChangeStationIdText() {
        return pChangeStationIdText;
    }

    public void setpChangeStationIdText(String pChangeStationIdText) {
        this.pChangeStationIdText = pChangeStationIdText;
    }

    public String getnChangeStationIdText() {
        return nChangeStationIdText;
    }

    public void setnChangeStationIdText(String nChangeStationIdText) {
        this.nChangeStationIdText = nChangeStationIdText;
    }

    public String getPassLineOutText() {
        return passLineOutText;
    }

    public void setPassLineOutText(String passLineOutText) {
        this.passLineOutText = passLineOutText;
    }

    public String getPassLineInText() {
        return passLineInText;
    }

    public void setPassLineInText(String passLineInText) {
        this.passLineInText = passLineInText;
    }

}
