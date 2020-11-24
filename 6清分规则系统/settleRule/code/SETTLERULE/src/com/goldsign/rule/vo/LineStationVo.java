/*
 * 文件名：LineStationVo
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.rule.vo;


/*
 *清分规则系统 参数设置实体类
 * @author     wangkejia
 * @version    V1.0
 */

public class LineStationVo {
    
    private String line;//所属线路
    private String lineName;//所属线路中文名
    private String chineseStation;//站点中文名
    private String englishStation;//站点英文名
    private String stationId;//站点ID
    private String stationName;//站点
    private String version;//版本
    private String recordFlag;//参数标志
    private String createTime;//创建时间
    private String operator;//创建人
    private String beginCreateTime;//创建时间开始段
    private String endCreateTime;//创建时间结束段
    private String recordFlagText;//参数标志中文

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setVersion(String version) {
        this.version = version;
    }
     public String getVersion() {
        return version;
    }
       
             public void setRecordFlag(String RecordFlag) {
        this.recordFlag = RecordFlag;
    }
     public String getRecordFlag() {
        return recordFlag;
    }
     public void setBeginCreateTime(String beginCreateTime) {
        this.beginCreateTime = beginCreateTime;
    }
     public String getBeginCreateTime() {
        return beginCreateTime;
    }
     public void setEndCreateTime(String endCreateTime) {
        this.endCreateTime = endCreateTime;
    }
     public String getEndCreateTime() {
        return endCreateTime;
    }
     public void setLine(String line) {
        this.line = line;
    }
     public String getLine() {
        return line;
    }
     public void setLineName(String lineName) {
        this.lineName = lineName;
    }
     public String getLineName() {
        return lineName;
    }
      public void setStationId(String stationId) {
        this.stationId = stationId;
    }
     public String getStationId() {
        return stationId;
    }
      public void setStationName(String stationName) {
        this.stationName = stationName;
    }
     public String getStationName() {
        return stationName;
    }
          public void setChineseStation(String chineseStation) {
        this.chineseStation = chineseStation;
    }
     public String getChineseStation() {
        return chineseStation;
    }
          public void setEnglishStation(String englishStation) {
        this.englishStation = englishStation;
    }
     public String getEnglishStation() {
        return englishStation;
    }
    public String getRecordFlagText() {
        return recordFlagText;
    }

    public void setRecordFlagText(String recordFlagText) {
        this.recordFlagText = recordFlagText;
    }

}

