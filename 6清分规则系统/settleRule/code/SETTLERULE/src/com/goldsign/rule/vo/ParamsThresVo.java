/*
 * 文件名：ParamsThresVo
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.rule.vo;


/*
 * 阀值参数实体类
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-11-25
 */

public class ParamsThresVo {
    
    private String id;
    private String distanceThres;//里程差比例阀值
    private String stationThres;//站点差阀值
    private String changeThres;//换乘次数阀值
    private String timeThres;//乘车时间（s）阀值
    private String description;//描述
    private String version;//版本
    private String recordFlag;//参数标志
    private String updateTime;//修改时间
    private String updateOperator;//修改人
    
    private String recordFlagText;    //'审核状态 0:有效状态'

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDistanceThres() {
        return distanceThres;
    }

    public void setDistanceThres(String distanceThres) {
        this.distanceThres = distanceThres;
    }

    public String getStationThres() {
        return stationThres;
    }

    public void setStationThres(String stationThres) {
        this.stationThres = stationThres;
    }

    public String getChangeThres() {
        return changeThres;
    }

    public void setChangeThres(String changeThres) {
        this.changeThres = changeThres;
    }

    public String getTimeThres() {
        return timeThres;
    }

    public void setTimeThres(String timeThres) {
        this.timeThres = timeThres;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateOperator() {
        return updateOperator;
    }

    public void setUpdateOperator(String updateOperator) {
        this.updateOperator = updateOperator;
    }

    public String getRecordFlagText() {
        return recordFlagText;
    }

    public void setRecordFlagText(String recordFlagText) {
        this.recordFlagText = recordFlagText;
    }
}
