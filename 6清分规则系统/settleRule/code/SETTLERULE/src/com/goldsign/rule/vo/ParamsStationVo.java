/*
 * 文件名：ParamsStationVo
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.rule.vo;


/*
 *清分规则系统 参数设置实体类
 * @author     wangkejia
 * @version    V1.0
 */

public class ParamsStationVo {
    
    private String presentStation;//当前站点
    private String nextStation;//下一站点
    private String line;//线路
    private String nextTransferStation;//一下换乘站
    private String mileage;//里程
    private String version;//版本
    private String recordFlag;//参数标志
    private String createTime;//创建时间
    private String operator;//创建人
    private String beginCreateTime;//创建时间开始段
    private String endCreateTime;//创建时间结束段
    private String recordFlagText;//参数标志-中文
    private String presentStationTxt;//当前站点-中文
    private String nextStationTxt;//下一站点-中文
    private String lineTxt;//线路-中文
    private String nextTransferStationTxt;//一下换乘站-中文

    public String getPresentStationTxt() {
        return presentStationTxt;
    }

    public void setPresentStationTxt(String presentStationTxt) {
        this.presentStationTxt = presentStationTxt;
    }

    public String getNextStationTxt() {
        return nextStationTxt;
    }

    public void setNextStationTxt(String nextStationTxt) {
        this.nextStationTxt = nextStationTxt;
    }

    public String getLineTxt() {
        return lineTxt;
    }

    public void setLineTxt(String lineTxt) {
        this.lineTxt = lineTxt;
    }

    public String getNextTransferStationTxt() {
        return nextTransferStationTxt;
    }

    public void setNextTransferStationTxt(String nextTransferStationTxt) {
        this.nextTransferStationTxt = nextTransferStationTxt;
    }

   public void setPresentStation(String presentStation) {
        this.presentStation = presentStation;
    }
     public String getPresentStation() {
        return presentStation;
    }
        public void setNextStation(String nextStation) {
        this.nextStation = nextStation;
    }
     public String getNextStation() {
        return nextStation;
    }
        public void setLine(String line) {
        this.line = line;
    }
     public String getLine() {
        return line;
    }
        public void setNextTransferStation(String nextTransferStation) {
        this.nextTransferStation = nextTransferStation;
    }
     public String getNextTransferStation() {
        return nextTransferStation;
    }
        public void setMileage(String mileage) {
        this.mileage = mileage;
    }
     public String getMileage() {
        return mileage;
    }
        public void setVersion(String version) {
        this.version = version;
    }
     public String getVersion() {
        return version;
    }
        public void setCreateTime(String createtime) {
        this.createTime = createtime;
    }
     public String getCreateTime() {
        return createTime;
    }
        public void setOperator(String operator) {
        this.operator = operator;
    }
     public String getOperator() {
        return operator;
    }
    public void setRecordFlag(String recordflag) {
        this.recordFlag = recordflag;
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
    public String getRecordFlagText() {
        return recordFlagText;
    }

    public void setRecordFlagText(String recordFlagText) {
        this.recordFlagText = recordFlagText;
    }

}

