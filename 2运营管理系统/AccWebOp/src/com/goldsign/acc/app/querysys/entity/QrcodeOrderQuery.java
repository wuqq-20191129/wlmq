package com.goldsign.acc.app.querysys.entity;

import java.math.BigDecimal;
import java.util.Date;

public class QrcodeOrderQuery implements Comparable<QrcodeOrderQuery>{

    private BigDecimal waterNo;

    private String orderNo;

    private String phoneNo;

    private String status;
    private String statusString;

    private String ticket_status;
    private String ticket_statusString;

    private String start_station;

    private String end_station;

    private String start_station_text;
    
    private String end_station_text;
    
    private String deal_time;

    private String update_date;
    
    private String insert_date;

    private BigDecimal sale_fee;

    private BigDecimal sale_times;
    
    private BigDecimal deal_fee;

    private BigDecimal sale_fee_total;

    private BigDecimal sale_times_total;

    private BigDecimal deal_fee_total;

    private String qrcode;
    
    private String valid_time;
    
    private String tkcode;
    
    private String lock_dev;
    
    private String remark;
    
    private String beginDatetime;
    
    private String endDatetime;
    
     private String modify_operate;//修改人
     private String audit_operate;//审核人

    public String getModify_operate() {
        return modify_operate;
    }

    public void setModify_operate(String modify_operate) {
        this.modify_operate = modify_operate;
    }

    public String getAudit_operate() {
        return audit_operate;
    }

    public void setAudit_operate(String audit_operate) {
        this.audit_operate = audit_operate;
    }

    

     
     

    public BigDecimal getWaterNo() {
        return waterNo;
    }

    public void setWaterNo(BigDecimal waterNo) {
        this.waterNo = waterNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTicket_status() {
        return ticket_status;
    }

    public void setTicket_status(String ticket_status) {
        this.ticket_status = ticket_status;
    }

        public String getStatusString() {
        return statusString;
    }

    public void setStatusString(String statusString) {
        this.statusString = statusString;
    }

    public String getTicket_statusString() {
        return ticket_statusString;
    }

    public void setTicket_statusString(String ticket_statusString) {
        this.ticket_statusString = ticket_statusString;
    }
    public String getStart_station() {
        return start_station;
    }

    public void setStart_station(String start_station) {
        this.start_station = start_station;
    }

    public String getEnd_station() {
        return end_station;
    }

    public void setEnd_station(String end_station) {
        this.end_station = end_station;
    }

        public String getStart_station_text() {
        return start_station_text;
    }

    public void setStart_station_text(String start_station_text) {
        this.start_station_text = start_station_text;
    }

    public String getEnd_station_text() {
        return end_station_text;
    }

    public void setEnd_station_text(String end_station_text) {
        this.end_station_text = end_station_text;
    }
    public String getDeal_time() {
        return deal_time;
    }

    public void setDeal_time(String deal_time) {
        this.deal_time = deal_time;
    }

    public String getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }

    public String getInsert_date() {
        return insert_date;
    }

    public void setInsert_date(String insert_date) {
        this.insert_date = insert_date;
    }

    public BigDecimal getSale_fee() {
        return sale_fee;
    }

    public void setSale_fee(BigDecimal sale_fee) {
        this.sale_fee = sale_fee;
    }

    public BigDecimal getSale_times() {
        return sale_times;
    }

    public void setSale_times(BigDecimal sale_times) {
        this.sale_times = sale_times;
    }

    public BigDecimal getDeal_fee() {
        return deal_fee;
    }

    public void setDeal_fee(BigDecimal deal_fee) {
        this.deal_fee = deal_fee;
    }

    public BigDecimal getSale_fee_total() {
        return sale_fee_total;
    }

    public void setSale_fee_total(BigDecimal sale_fee_total) {
        this.sale_fee_total = sale_fee_total;
    }

    public BigDecimal getSale_times_total() {
        return sale_times_total;
    }

    public void setSale_times_total(BigDecimal sale_times_total) {
        this.sale_times_total = sale_times_total;
    }

    public BigDecimal getDeal_fee_total() {
        return deal_fee_total;
    }

    public void setDeal_fee_total(BigDecimal deal_fee_total) {
        this.deal_fee_total = deal_fee_total;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getValid_time() {
        return valid_time;
    }

    public void setValid_time(String valid_time) {
        this.valid_time = valid_time;
    }

    public String getTkcode() {
        return tkcode;
    }

    public void setTkcode(String tkcode) {
        this.tkcode = tkcode;
    }

    public String getLock_dev() {
        return lock_dev;
    }

    public void setLock_dev(String lock_dev) {
        this.lock_dev = lock_dev;
    }

        public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public String getBeginDatetime() {
        return beginDatetime;
    }

    public void setBeginDatetime(String beginDatetime) {
        this.beginDatetime = beginDatetime;
    }

    public String getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(String endDatetime) {
        this.endDatetime = endDatetime;
    }
    public int compareTo(QrcodeOrderQuery arg0) {  
         return this.getWaterNo().compareTo(arg0.getWaterNo());  
     } 
    
}