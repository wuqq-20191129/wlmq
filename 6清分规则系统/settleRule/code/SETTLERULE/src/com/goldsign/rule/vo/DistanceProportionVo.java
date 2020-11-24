/*
 * 文件名：DistanceProportionVo
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.rule.vo;


/*
 * OD线路权重比例实例
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-11-7
 */

public class DistanceProportionVo {
    
    private String version;     //'版本'

    private String recordFlag;    //'审核状态 0:有效状态'

    private String oStationId;    //'开始站点'

    private String oLineId;   //'开始路线';

    private String dStationId;   //'结束站点';

    private String dLineId;   // '结束线路';

    private String dispartLineId;     //'权重路线';

    private String inPrecent;      //'权重比例';

    private String createTime;     //'创建时间';

    private String createOperator;     //'创建人';
    
    private String oStationIdText;    //'开始站点'

    private String oLineIdText;   //'开始路线';

    private String dStationIdText;   //'结束站点';

    private String dLineIdText;   // '结束线路';
    
    private String dispartLineIdText;     //'权重路线';
    
    private String recordFlagText;    //'审核状态 0:有效状态'

    public String getRecordFlagText() {
        return recordFlagText;
    }

    public void setRecordFlagText(String recordFlagText) {
        this.recordFlagText = recordFlagText;
    }

    public String getDispartLineIdText() {
        return dispartLineIdText;
    }

    public void setDispartLineIdText(String dispartLineIdText) {
        this.dispartLineIdText = dispartLineIdText;
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

    public String getdStationIdText() {
        return dStationIdText;
    }

    public void setdStationIdText(String dStationIdText) {
        this.dStationIdText = dStationIdText;
    }

    public String getdLineIdText() {
        return dLineIdText;
    }

    public void setdLineIdText(String dLineIdText) {
        this.dLineIdText = dLineIdText;
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

    public String getoStationId() {
        return oStationId;
    }

    public void setoStationId(String oStationId) {
        this.oStationId = oStationId;
    }

    public String getoLineId() {
        return oLineId;
    }

    public void setoLineId(String oLineId) {
        this.oLineId = oLineId;
    }

    public String getdStationId() {
        return dStationId;
    }

    public void setdStationId(String dStationId) {
        this.dStationId = dStationId;
    }

    public String getdLineId() {
        return dLineId;
    }

    public void setdLineId(String dLineId) {
        this.dLineId = dLineId;
    }

    public String getDispartLineId() {
        return dispartLineId;
    }

    public void setDispartLineId(String dispartLineId) {
        this.dispartLineId = dispartLineId;
    }

    public String getInPrecent() {
        return inPrecent;
    }

    public void setInPrecent(String inPrecent) {
        this.inPrecent = inPrecent;
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
}
