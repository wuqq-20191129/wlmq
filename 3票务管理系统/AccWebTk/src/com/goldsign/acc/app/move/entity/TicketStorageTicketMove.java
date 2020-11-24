package com.goldsign.acc.app.move.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TicketStorageTicketMove implements Serializable {
    private String billNo;
    
    private String normalBillNo;

    private String formMaker;

    private Date billDate;
    
    private String strBillDate;  
    
    private String startBillDate;
    
    private String endBillDate;    

    private String recordFlag;
    
    private String recordFlagText;  

    private Date verifyDate;
    
    private String strVerifyDate;

    private String verifyPerson;

    private String distributeMan;

    private String receiveUnit;

    private String receiveMan;

    private String remark;

    private String icMainType;
    
    private String icMainTypeText;

//   private String oldIcSubType;
//
//    public String getOldIcSubType() {
//        return oldIcSubType;
//    }
//
//    public void setOldIcSubType(String oldIcSubType) {
//        this.oldIcSubType = oldIcSubType;
//    }

    private String icSubType;
    
    private String icSubTypeText;

//    private Short cardMoney;
    private BigDecimal cardMoney;

    private String outStorageId;
    
    private String outStorageIdText;

//    private String oldOutAreaId;
//
//    public String getOldOutAreaId() {
//        return oldOutAreaId;
//    }
//
//    public void setOldOutAreaId(String oldOutAreaId) {
//        this.oldOutAreaId = oldOutAreaId;
//    }

//    public String getOldInAreaId() {
//        return oldInAreaId;
//    }
//
//    public void setOldInAreaId(String oldInAreaId) {
//        this.oldInAreaId = oldInAreaId;
//    }
    
    private String outAreaId;
    
    private String outAreaIdText;

    private String inStorageId;
    
    private String inStorageIdText;

//   private String oldInAreaId;

    private String inAreaId;
    
    private String inAreaIdText;

    private Integer quantity;

    private String startLogicalId;

    private String endLogicalId;

    private String startBoxId;

    private String endBoxId;

    private String outBillNo;

    private String inBillNo;

    private Date validDate;
    
    private String strValidDate;
    
     public String getOutStorageIdText() {
        return outStorageIdText;
    }

    public void setOutStorageIdText(String outStorageIdText) {
        this.outStorageIdText = outStorageIdText;
    }

    public String getInStorageIdText() {
        return inStorageIdText;
    }

    public void setInStorageIdText(String inStorageIdText) {
        this.inStorageIdText = inStorageIdText;
    }
    
      public String getIcMainTypeText() {
        return icMainTypeText;
    }

    public void setIcMainTypeText(String icMainTypeText) {
        this.icMainTypeText = icMainTypeText;
    }
    
    public String getStrBillDate() {
        return strBillDate;
    }

    public void setStrBillDate(String strBillDate) {
        this.strBillDate = strBillDate;
    }
    
     public String getStartBillDate() {
        return startBillDate;
    }

    public void setStartBillDate(String startBillDate) {
        this.startBillDate = startBillDate;
    }

    public String getEndBillDate() {
        return endBillDate;
    }

    public void setEndBillDate(String endBillDate) {
        this.endBillDate = endBillDate;
    }

    public String getStrVerifyDate() {
        return strVerifyDate;
    }

    public void setStrVerifyDate(String strVerifyDate) {
        this.strVerifyDate = strVerifyDate;
    }

    public String getNormalBillNo() {
        return normalBillNo;
    }

    public void setNormalBillNo(String normalBillNo) {
        this.normalBillNo = normalBillNo;
    }

    public String getStrValidDate() {
        return strValidDate;
    }

    public void setStrValidDate(String strValidDate) {
        this.strValidDate = strValidDate;
    }

    private static final long serialVersionUID = 1L;

    public TicketStorageTicketMove(String billNo, String formMaker, Date billDate, String recordFlag, Date verifyDate, String verifyPerson, String distributeMan, String receiveUnit, String receiveMan, String remark, String icMainType, String icSubType, BigDecimal cardMoney, String outStorageId, String outAreaId, String inStorageId, String inAreaId, Integer quantity, String startLogicalId, String endLogicalId, String startBoxId, String endBoxId, String outBillNo, String inBillNo, Date validDate) {
        this.billNo = billNo;
        this.formMaker = formMaker;
        this.billDate = billDate;
        this.recordFlag = recordFlag;
        this.verifyDate = verifyDate;
        this.verifyPerson = verifyPerson;
        this.distributeMan = distributeMan;
        this.receiveUnit = receiveUnit;
        this.receiveMan = receiveMan;
        this.remark = remark;
        this.icMainType = icMainType;
        this.icSubType = icSubType;
        this.cardMoney = cardMoney;
        this.outStorageId = outStorageId;
        this.outAreaId = outAreaId;
        this.inStorageId = inStorageId;
        this.inAreaId = inAreaId;
        this.quantity = quantity;
        this.startLogicalId = startLogicalId;
        this.endLogicalId = endLogicalId;
        this.startBoxId = startBoxId;
        this.endBoxId = endBoxId;
        this.outBillNo = outBillNo;
        this.inBillNo = inBillNo;
        this.validDate = validDate;
    }

    public TicketStorageTicketMove() {
        super();
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo == null ? null : billNo.trim();
    }

    public String getFormMaker() {
        return formMaker;
    }

    public void setFormMaker(String formMaker) {
        this.formMaker = formMaker == null ? null : formMaker.trim();
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public String getRecordFlag() {
        return recordFlag;
    }

    public void setRecordFlag(String recordFlag) {
        this.recordFlag = recordFlag == null ? null : recordFlag.trim();
    }
    
    public String getRecordFlagText() {
        return recordFlagText;
    }

    public void setRecordFlagText(String recordFlagText) {
        this.recordFlagText = recordFlagText;
    }

    public Date getVerifyDate() {
        return verifyDate;
    }

    public void setVerifyDate(Date verifyDate) {
        this.verifyDate = verifyDate;
    }

    public String getVerifyPerson() {
        return verifyPerson;
    }

    public void setVerifyPerson(String verifyPerson) {
        this.verifyPerson = verifyPerson == null ? null : verifyPerson.trim();
    }

    public String getDistributeMan() {
        return distributeMan;
    }

    public void setDistributeMan(String distributeMan) {
        this.distributeMan = distributeMan == null ? null : distributeMan.trim();
    }

    public String getReceiveUnit() {
        return receiveUnit;
    }

    public void setReceiveUnit(String receiveUnit) {
        this.receiveUnit = receiveUnit == null ? null : receiveUnit.trim();
    }

    public String getReceiveMan() {
        return receiveMan;
    }

    public void setReceiveMan(String receiveMan) {
        this.receiveMan = receiveMan == null ? null : receiveMan.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getIcMainType() {
        return icMainType;
    }

    public void setIcMainType(String icMainType) {
        this.icMainType = icMainType == null ? null : icMainType.trim();
    }

    public String getIcSubType() {
        return icSubType;
    }

    public void setIcSubType(String icSubType) {
        this.icSubType = icSubType == null ? null : icSubType.trim();
    }


    public BigDecimal getCardMoney() {
        return cardMoney;
    }

    public void setCardMoney(BigDecimal cardMoney) {
        this.cardMoney = cardMoney;
    }
    
    

    public String getOutStorageId() {
        return outStorageId;
    }

    public void setOutStorageId(String outStorageId) {
        this.outStorageId = outStorageId == null ? null : outStorageId.trim();
    }

    public String getOutAreaId() {
        return outAreaId;
    }

    public void setOutAreaId(String outAreaId) {
        this.outAreaId = outAreaId == null ? null : outAreaId.trim();
    }

    public String getInStorageId() {
        return inStorageId;
    }

    public void setInStorageId(String inStorageId) {
        this.inStorageId = inStorageId == null ? null : inStorageId.trim();
    }

    public String getInAreaId() {
        return inAreaId;
    }

    public void setInAreaId(String inAreaId) {
        this.inAreaId = inAreaId == null ? null : inAreaId.trim();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getStartLogicalId() {
        return startLogicalId;
    }

    public void setStartLogicalId(String startLogicalId) {
        this.startLogicalId = startLogicalId == null ? null : startLogicalId.trim();
    }

    public String getEndLogicalId() {
        return endLogicalId;
    }

    public void setEndLogicalId(String endLogicalId) {
        this.endLogicalId = endLogicalId == null ? null : endLogicalId.trim();
    }

    public String getStartBoxId() {
        return startBoxId;
    }

    public void setStartBoxId(String startBoxId) {
        this.startBoxId = startBoxId == null ? null : startBoxId.trim();
    }

    public String getEndBoxId() {
        return endBoxId;
    }

    public void setEndBoxId(String endBoxId) {
        this.endBoxId = endBoxId == null ? null : endBoxId.trim();
    }

    public String getOutBillNo() {
        return outBillNo;
    }

    public void setOutBillNo(String outBillNo) {
        this.outBillNo = outBillNo == null ? null : outBillNo.trim();
    }

    public String getInBillNo() {
        return inBillNo;
    }

    public void setInBillNo(String inBillNo) {
        this.inBillNo = inBillNo == null ? null : inBillNo.trim();
    }

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }
    
    

//    @Override
//    public boolean equals(Object that) {
//        if (this == that) {
//            return true;
//        }
//        if (that == null) {
//            return false;
//        }
//        if (getClass() != that.getClass()) {
//            return false;
//        }
//        TicketStorageTicketMove other = (TicketStorageTicketMove) that;
//        return (this.getBillNo() == null ? other.getBillNo() == null : this.getBillNo().equals(other.getBillNo()))
//            && (this.getFormMaker() == null ? other.getFormMaker() == null : this.getFormMaker().equals(other.getFormMaker()))
//            && (this.getBillDate() == null ? other.getBillDate() == null : this.getBillDate().equals(other.getBillDate()))
//            && (this.getRecordFlag() == null ? other.getRecordFlag() == null : this.getRecordFlag().equals(other.getRecordFlag()))
//            && (this.getVerifyDate() == null ? other.getVerifyDate() == null : this.getVerifyDate().equals(other.getVerifyDate()))
//            && (this.getVerifyPerson() == null ? other.getVerifyPerson() == null : this.getVerifyPerson().equals(other.getVerifyPerson()))
//            && (this.getDistributeMan() == null ? other.getDistributeMan() == null : this.getDistributeMan().equals(other.getDistributeMan()))
//            && (this.getReceiveUnit() == null ? other.getReceiveUnit() == null : this.getReceiveUnit().equals(other.getReceiveUnit()))
//            && (this.getReceiveMan() == null ? other.getReceiveMan() == null : this.getReceiveMan().equals(other.getReceiveMan()))
//            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
//            && (this.getIcMainType() == null ? other.getIcMainType() == null : this.getIcMainType().equals(other.getIcMainType()))
//            && (this.getIcSubType() == null ? other.getIcSubType() == null : this.getIcSubType().equals(other.getIcSubType()))
//            && (this.getCardMoney() == null ? other.getCardMoney() == null : this.getCardMoney().equals(other.getCardMoney()))
//            && (this.getOutStorageId() == null ? other.getOutStorageId() == null : this.getOutStorageId().equals(other.getOutStorageId()))
//            && (this.getOutAreaId() == null ? other.getOutAreaId() == null : this.getOutAreaId().equals(other.getOutAreaId()))
//            && (this.getInStorageId() == null ? other.getInStorageId() == null : this.getInStorageId().equals(other.getInStorageId()))
//            && (this.getInAreaId() == null ? other.getInAreaId() == null : this.getInAreaId().equals(other.getInAreaId()))
//            && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
//            && (this.getStartLogicalId() == null ? other.getStartLogicalId() == null : this.getStartLogicalId().equals(other.getStartLogicalId()))
//            && (this.getEndLogicalId() == null ? other.getEndLogicalId() == null : this.getEndLogicalId().equals(other.getEndLogicalId()))
//            && (this.getStartBoxId() == null ? other.getStartBoxId() == null : this.getStartBoxId().equals(other.getStartBoxId()))
//            && (this.getEndBoxId() == null ? other.getEndBoxId() == null : this.getEndBoxId().equals(other.getEndBoxId()))
//            && (this.getOutBillNo() == null ? other.getOutBillNo() == null : this.getOutBillNo().equals(other.getOutBillNo()))
//            && (this.getInBillNo() == null ? other.getInBillNo() == null : this.getInBillNo().equals(other.getInBillNo()))
//            && (this.getValidDate() == null ? other.getValidDate() == null : this.getValidDate().equals(other.getValidDate()));
//    }
//
//    @Override
//    public int hashCode() {
//        final int prime = 31;
//        int result = 1;
//        result = prime * result + ((getBillNo() == null) ? 0 : getBillNo().hashCode());
//        result = prime * result + ((getFormMaker() == null) ? 0 : getFormMaker().hashCode());
//        result = prime * result + ((getBillDate() == null) ? 0 : getBillDate().hashCode());
//        result = prime * result + ((getRecordFlag() == null) ? 0 : getRecordFlag().hashCode());
//        result = prime * result + ((getVerifyDate() == null) ? 0 : getVerifyDate().hashCode());
//        result = prime * result + ((getVerifyPerson() == null) ? 0 : getVerifyPerson().hashCode());
//        result = prime * result + ((getDistributeMan() == null) ? 0 : getDistributeMan().hashCode());
//        result = prime * result + ((getReceiveUnit() == null) ? 0 : getReceiveUnit().hashCode());
//        result = prime * result + ((getReceiveMan() == null) ? 0 : getReceiveMan().hashCode());
//        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
//        result = prime * result + ((getIcMainType() == null) ? 0 : getIcMainType().hashCode());
//        result = prime * result + ((getIcSubType() == null) ? 0 : getIcSubType().hashCode());
//        result = prime * result + ((getCardMoney() == null) ? 0 : getCardMoney().hashCode());
//        result = prime * result + ((getOutStorageId() == null) ? 0 : getOutStorageId().hashCode());
//        result = prime * result + ((getOutAreaId() == null) ? 0 : getOutAreaId().hashCode());
//        result = prime * result + ((getInStorageId() == null) ? 0 : getInStorageId().hashCode());
//        result = prime * result + ((getInAreaId() == null) ? 0 : getInAreaId().hashCode());
//        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
//        result = prime * result + ((getStartLogicalId() == null) ? 0 : getStartLogicalId().hashCode());
//        result = prime * result + ((getEndLogicalId() == null) ? 0 : getEndLogicalId().hashCode());
//        result = prime * result + ((getStartBoxId() == null) ? 0 : getStartBoxId().hashCode());
//        result = prime * result + ((getEndBoxId() == null) ? 0 : getEndBoxId().hashCode());
//        result = prime * result + ((getOutBillNo() == null) ? 0 : getOutBillNo().hashCode());
//        result = prime * result + ((getInBillNo() == null) ? 0 : getInBillNo().hashCode());
//        result = prime * result + ((getValidDate() == null) ? 0 : getValidDate().hashCode());
//        return result;
//    }

    public String getIcSubTypeText() {
        return icSubTypeText;
    }

    public void setIcSubTypeText(String icSubTypeText) {
        this.icSubTypeText = icSubTypeText;
    }

    public String getOutAreaIdText() {
        return outAreaIdText;
    }

    public void setOutAreaIdText(String outAreaIdText) {
        this.outAreaIdText = outAreaIdText;
    }

    public String getInAreaIdText() {
        return inAreaIdText;
    }

    public void setInAreaIdText(String inAreaIdText) {
        this.inAreaIdText = inAreaIdText;
    }
}