package com.goldsign.acc.app.query.entity;

import java.io.Serializable;
import java.util.Date;

public class TicketStorageUchkBill implements Serializable {
    private String billNo;

    private String billName;

    private Date billDate;
    
    private String strBillDate;

  

    private String recordFlag;

    private String storageId;

    private static final long serialVersionUID = 1L;

    public TicketStorageUchkBill(String billNo, String billName, Date billDate, String recordFlag, String storageId) {
        this.billNo = billNo;
        this.billName = billName;
        this.billDate = billDate;
        this.recordFlag = recordFlag;
        this.storageId = storageId;
    }

    public TicketStorageUchkBill() {
        super();
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo == null ? null : billNo.trim();
    }

    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName == null ? null : billName.trim();
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }
    
    public String getStrBillDate() {
        return strBillDate;
    }

    public void setStrBillDate(String strBillDate) {
        this.strBillDate = strBillDate;
    }

    public String getRecordFlag() {
        return recordFlag;
    }

    public void setRecordFlag(String recordFlag) {
        this.recordFlag = recordFlag == null ? null : recordFlag.trim();
    }

    public String getStorageId() {
        return storageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId == null ? null : storageId.trim();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TicketStorageUchkBill other = (TicketStorageUchkBill) that;
        return (this.getBillNo() == null ? other.getBillNo() == null : this.getBillNo().equals(other.getBillNo()))
            && (this.getBillName() == null ? other.getBillName() == null : this.getBillName().equals(other.getBillName()))
            && (this.getBillDate() == null ? other.getBillDate() == null : this.getBillDate().equals(other.getBillDate()))
            && (this.getRecordFlag() == null ? other.getRecordFlag() == null : this.getRecordFlag().equals(other.getRecordFlag()))
            && (this.getStorageId() == null ? other.getStorageId() == null : this.getStorageId().equals(other.getStorageId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getBillNo() == null) ? 0 : getBillNo().hashCode());
        result = prime * result + ((getBillName() == null) ? 0 : getBillName().hashCode());
        result = prime * result + ((getBillDate() == null) ? 0 : getBillDate().hashCode());
        result = prime * result + ((getRecordFlag() == null) ? 0 : getRecordFlag().hashCode());
        result = prime * result + ((getStorageId() == null) ? 0 : getStorageId().hashCode());
        return result;
    }
}