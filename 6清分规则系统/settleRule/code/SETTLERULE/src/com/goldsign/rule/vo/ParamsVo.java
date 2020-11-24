/*
 * 文件名：ParamsVo
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.rule.vo;


/*
 *清分规则系统 参数设置实体类
 * @author     wangkejia
 * @version    V1.0
 */

public class ParamsVo {
    
    private String typeCode;//类型代码
    private String typeName;//类型描述
    private String code;//参数代码
    private String value;//参数值
    private String description;//参数描述
    private String version;//版本
    private String recordFlag;//参数标志
    private String createTime;//创建时间
    private String operator;//创建人
    private String beginCreateTime;//创建时间开始段
    private String endCreateTime;//创建时间结束段
    private String recordFlagText;//参数标志中文

 
 

   public void setTypeCode(String typecode) {
        this.typeCode = typecode;
    }
     public String getTypeCode() {
        return typeCode;
    }
        public void setTypeName(String typename) {
        this.typeName = typename;
    }
     public String getTypeName() {
        return typeName;
    }
        public void setCode(String code) {
        this.code = code;
    }
     public String getCode() {
        return code;
    }
        public void setValue(String value) {
        this.value = value;
    }
     public String getDescription() {
        return description;
    }
        public void setDescription(String description) {
        this.description = description;
    }
     public String getValue() {
        return value;
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
        public void setOperator(String createtime) {
        this.operator = createtime;
    }
     public String getOperator() {
        return operator;
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
    public String getRecordFlagText() {
        return recordFlagText;
    }

    public void setRecordFlagText(String recordFlagText) {
        this.recordFlagText = recordFlagText;
    }

}

