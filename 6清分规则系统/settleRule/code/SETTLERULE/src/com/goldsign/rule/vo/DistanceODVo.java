/*
 * 文件名：DistanceODVo
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.rule.vo;


/*
 * OD路径实例类
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-11-12
 */

public class DistanceODVo {

    private String id;//id
    private String oLineId;//开始线路
    private String oStationId;//开始站点
    private String eLineId;//结束线路
    private String eStationId;//结束站点
    private String passStations;//经过站点
    private String passTime;//乘坐时间
    private String changeTimes;//换乘次数
    private String stationsNum;//经过站点数
    private String distance;//转出线路乘距
    private String version;//版本
    private String recordFlag;//参数标志
    private String createTime;//创建时间
    private String createOperator;//创建人
    
    private String oStationIdText;    //'开始站点'
    private String oLineIdText;   //'开始路线';
    private String eStationIdText;   //'结束站点';
    private String eLineIdText;   // '结束线路';
    private String recordFlagText;    //'审核状态 0:有效状态'
    
    private String isValid; //是否为有效路径标志
    private String isValidText;     //是否为有效路径
    private String minDistance;     //最短里程
    
    private String preId;   //临时记录上一记录ID

    public String getPreId() {
        return preId;
    }

    public void setPreId(String preId) {
        this.preId = preId;
    }
    
    public String getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(String minDistance) {
        this.minDistance = minDistance;
    }

    public String getPassStations() {
        return passStations;
    }

    public void setPassStations(String passStations) {
        this.passStations = passStations;
    }

    public String getPassTime() {
        return passTime;
    }

    public void setPassTime(String passTime) {
        this.passTime = passTime;
    }

    public String getChangeTimes() {
        return changeTimes;
    }

    public void setChangeTimes(String changeTimes) {
        this.changeTimes = changeTimes;
    }

    public String getStationsNum() {
        return stationsNum;
    }

    public void setStationsNum(String stationsNum) {
        this.stationsNum = stationsNum;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getIsValidText() {
        return isValidText;
    }

    public void setIsValidText(String isValidText) {
        this.isValidText = isValidText;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getoLineId() {
        return oLineId;
    }

    public void setoLineId(String oLineId) {
        this.oLineId = oLineId;
    }

    public String getoStationId() {
        return oStationId;
    }

    public void setoStationId(String oStationId) {
        this.oStationId = oStationId;
    }

    public String geteLineId() {
        return eLineId;
    }

    public void seteLineId(String eLineId) {
        this.eLineId = eLineId;
    }

    public String geteStationId() {
        return eStationId;
    }

    public void seteStationId(String eStationId) {
        this.eStationId = eStationId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRecordFlag() {
        return recordFlag;
    }

    public void setRecordFlag(String recordFlag) {
        this.recordFlag = recordFlag;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateOperator() {
        return createOperator;
    }

    public void setCreateOperator(String createOperator) {
        this.createOperator = createOperator;
    }

    public String getoStationIdText() {
        return oStationIdText;
    }

    public void setoStationIdText(String oStationIdText) {
        this.oStationIdText = oStationIdText;
    }

    public String getoLineIdText() {
        return oLineIdText;
    }

    public void setoLineIdText(String oLineIdText) {
        this.oLineIdText = oLineIdText;
    }

    public String geteStationIdText() {
        return eStationIdText;
    }

    public void seteStationIdText(String eStationIdText) {
        this.eStationIdText = eStationIdText;
    }

    public String geteLineIdText() {
        return eLineIdText;
    }

    public void seteLineIdText(String eLineIdText) {
        this.eLineIdText = eLineIdText;
    }

    public String getRecordFlagText() {
        return recordFlagText;
    }

    public void setRecordFlagText(String recordFlagText) {
        this.recordFlagText = recordFlagText;
    }
}
