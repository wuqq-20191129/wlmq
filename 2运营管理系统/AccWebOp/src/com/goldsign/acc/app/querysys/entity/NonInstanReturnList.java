/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.querysys.entity;

import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import java.io.Serializable;

/**
 *
 * @author luck
 */
public class NonInstanReturnList extends PrmVersion implements Serializable{
    
    private String water_no;	
    private String line_id;
    private String line_id_text;
    private String station_id;
    private String station_id_text;
    private String dev_type_id;
    private String dev_type_id_text;
    private String device_id;
    private String device_id_text;
    private String card_main_id;
    private String card_main_id_text;
    private String card_sub_id;
    private String card_sub_id_text;
    private String card_logical_id;//sam卡逻辑卡号
    private String card_physical_id;//sam卡物理卡号
    private String card_print_id;//打印号
    private String apply_datetime;//日期时间
    private String receipt_id;// 凭证id
    private String operator_id;//操作员id
    private String apply_name;//乘客姓名
    private String tel_no;//乘客电话
    private String identity_type;//证件类型
    private String identity_id;//证件号码
    private String is_broken;//损坏标志
    private String shift_id;//bom班次序号
    private String card_app_flag;//卡应用标识
    private String balance_water_no;//清算流水号
    private String hdl_flag;//处理标志 
    private String hdl_flag_text;
    private String deposit_fee;//成本押金
    private String card_balance_fee;//卡余额
    private String system_balance_fee;//系统余额
    private String penalty_fee;//罚款
    private Double return_bala;//应退金额
    private String remark;//备注
    private String return_line_id;//退款线路ID
    private String return_line_id_text;
    private String return_station_id;//退款车站id
    private String return_station_id_text;
    private String return_dev_type_id;//退款设备类型id
    private String return_device_id;//退款设备id
    private Double actual_return_bala;//实际退款金额
    private String audit_flag;//审核标志
    private String penalty_reason;//罚款原因id
    private String return_type;//0：即时退款；1：非即时退款；2：单程票退款；3：非正常模式单程票退款 4：挂失非即时退款
    private String business_receipt_id;
    
    private String begin_time;
    private String end_time;
    
    private String receipt_no;//凭证号
    private String rtnOper;//选择的符号> >= < <=
    private String checkBoxFlag;
    private String isOverTime;
    private String isOverNoAduit; //到期未审核

    public String getWater_no() {
        return water_no;
    }

    public void setWater_no(String water_no) {
        this.water_no = water_no;
    }

    public String getLine_id() {
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }

    public String getLine_id_text() {
        return line_id_text;
    }

    public void setLine_id_text(String line_id_text) {
        this.line_id_text = line_id_text;
    }

    public String getStation_id() {
        return station_id;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }

    public String getStation_id_text() {
        return station_id_text;
    }

    public void setStation_id_text(String station_id_text) {
        this.station_id_text = station_id_text;
    }

    public String getDev_type_id() {
        return dev_type_id;
    }

    public void setDev_type_id(String dev_type_id) {
        this.dev_type_id = dev_type_id;
    }

    public String getDev_type_id_text() {
        return dev_type_id_text;
    }

    public void setDev_type_id_text(String dev_type_id_text) {
        this.dev_type_id_text = dev_type_id_text;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getDevice_id_text() {
        return device_id_text;
    }

    public void setDevice_id_text(String device_id_text) {
        this.device_id_text = device_id_text;
    }
    
    
    
    public String getCard_main_id() {
        return card_main_id;
    }

    public void setCard_main_id(String card_main_id) {
        this.card_main_id = card_main_id;
    }

    public String getCard_main_id_text() {
        return card_main_id_text;
    }

    public void setCard_main_id_text(String card_main_id_text) {
        this.card_main_id_text = card_main_id_text;
    }

    public String getCard_sub_id() {
        return card_sub_id;
    }

    public void setCard_sub_id(String card_sub_id) {
        this.card_sub_id = card_sub_id;
    }

    public String getCard_sub_id_text() {
        return card_sub_id_text;
    }

    public void setCard_sub_id_text(String card_sub_id_text) {
        this.card_sub_id_text = card_sub_id_text;
    }

    public String getCard_logical_id() {
        return card_logical_id;
    }

    public void setCard_logical_id(String card_logical_id) {
        this.card_logical_id = card_logical_id;
    }

    public String getCard_physical_id() {
        return card_physical_id;
    }

    public void setCard_physical_id(String card_physical_id) {
        this.card_physical_id = card_physical_id;
    }

    public String getCard_print_id() {
        return card_print_id;
    }

    public void setCard_print_id(String card_print_id) {
        this.card_print_id = card_print_id;
    }

    public String getApply_datetime() {
        return apply_datetime;
    }

    public void setApply_datetime(String apply_datetime) {
        this.apply_datetime = apply_datetime;
    }

    public String getReceipt_id() {
        return receipt_id;
    }

    public void setReceipt_id(String receipt_id) {
        this.receipt_id = receipt_id;
    }

    public String getOperator_id() {
        return operator_id;
    }

    public void setOperator_id(String operator_id) {
        this.operator_id = operator_id;
    }

    public String getApply_name() {
        return apply_name;
    }

    public void setApply_name(String apply_name) {
        this.apply_name = apply_name;
    }

    public String getTel_no() {
        return tel_no;
    }

    public void setTel_no(String tel_no) {
        this.tel_no = tel_no;
    }

    public String getIdentity_type() {
        return identity_type;
    }

    public void setIdentity_type(String identity_type) {
        this.identity_type = identity_type;
    }

    public String getIdentity_id() {
        return identity_id;
    }

    public void setIdentity_id(String identity_id) {
        this.identity_id = identity_id;
    }

    public String getIs_broken() {
        return is_broken;
    }

    public void setIs_broken(String is_broken) {
        this.is_broken = is_broken;
    }

    public String getShift_id() {
        return shift_id;
    }

    public void setShift_id(String shift_id) {
        this.shift_id = shift_id;
    }

    public String getCard_app_flag() {
        return card_app_flag;
    }

    public void setCard_app_flag(String card_app_flag) {
        this.card_app_flag = card_app_flag;
    }

    public String getBalance_water_no() {
        return balance_water_no;
    }

    public void setBalance_water_no(String balance_water_no) {
        this.balance_water_no = balance_water_no;
    }

    public String getHdl_flag() {
        return hdl_flag;
    }

    public void setHdl_flag(String hdl_flag) {
        this.hdl_flag = hdl_flag;
    }

    public String getHdl_flag_text() {
        return hdl_flag_text;
    }

    public void setHdl_flag_text(String hdl_flag_text) {
        this.hdl_flag_text = hdl_flag_text;
    }

    public String getDeposit_fee() {
        return deposit_fee;
    }

    public void setDeposit_fee(String deposit_fee) {
        this.deposit_fee = deposit_fee;
    }

    public String getCard_balance_fee() {
        return card_balance_fee;
    }

    public void setCard_balance_fee(String card_balance_fee) {
        this.card_balance_fee = card_balance_fee;
    }

    public String getSystem_balance_fee() {
        return system_balance_fee;
    }

    public void setSystem_balance_fee(String system_balance_fee) {
        this.system_balance_fee = system_balance_fee;
    }

    public String getPenalty_fee() {
        return penalty_fee;
    }

    public void setPenalty_fee(String penalty_fee) {
        this.penalty_fee = penalty_fee;
    }

    public Double getReturn_bala() {
        return return_bala;
    }

    public void setReturn_bala(Double return_bala) {
        this.return_bala = return_bala;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReturn_line_id() {
        return return_line_id;
    }

    public void setReturn_line_id(String return_line_id) {
        this.return_line_id = return_line_id;
    }

    public String getReturn_station_id() {
        return return_station_id;
    }

    public void setReturn_station_id(String return_station_id) {
        this.return_station_id = return_station_id;
    }

    public String getReturn_dev_type_id() {
        return return_dev_type_id;
    }

    public void setReturn_dev_type_id(String return_dev_type_id) {
        this.return_dev_type_id = return_dev_type_id;
    }

    public String getReturn_device_id() {
        return return_device_id;
    }

    public void setReturn_device_id(String return_device_id) {
        this.return_device_id = return_device_id;
    }

    public Double getActual_return_bala() {
        return actual_return_bala;
    }

    public void setActual_return_bala(Double actual_return_bala) {
        this.actual_return_bala = actual_return_bala;
    }

    public String getAudit_flag() {
        return audit_flag;
    }

    public void setAudit_flag(String audit_flag) {
        this.audit_flag = audit_flag;
    }

    public String getPenalty_reason() {
        return penalty_reason;
    }

    public void setPenalty_reason(String penalty_reason) {
        this.penalty_reason = penalty_reason;
    }

    public String getReturn_type() {
        return return_type;
    }

    public void setReturn_type(String return_type) {
        this.return_type = return_type;
    }

    public String getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(String begin_time) {
        this.begin_time = begin_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getReceipt_no() {
        return receipt_no;
    }

    public void setReceipt_no(String receipt_no) {
        this.receipt_no = receipt_no;
    }

    public String getRtnOper() {
        return rtnOper;
    }

    public void setRtnOper(String rtnOper) {
        this.rtnOper = rtnOper;
    }

    public String getCheckBoxFlag() {
        return checkBoxFlag;
    }

    public void setCheckBoxFlag(String checkBoxFlag) {
        this.checkBoxFlag = checkBoxFlag;
    }

    public String getBusiness_receipt_id() {
        return business_receipt_id;
    }

    public void setBusiness_receipt_id(String business_receipt_id) {
        this.business_receipt_id = business_receipt_id;
    }

    public String getIsOverTime() {
        return isOverTime;
    }

    public void setIsOverTime(String isOverTime) {
        this.isOverTime = isOverTime;
    }

    public String getReturn_line_id_text() {
        return return_line_id_text;
    }

    public void setReturn_line_id_text(String return_line_id_text) {
        this.return_line_id_text = return_line_id_text;
    }

    public String getReturn_station_id_text() {
        return return_station_id_text;
    }

    public void setReturn_station_id_text(String return_station_id_text) {
        this.return_station_id_text = return_station_id_text;
    }

    public String getIsOverNoAduit() {
        return isOverNoAduit;
    }

    public void setIsOverNoAduit(String isOverNoAduit) {
        this.isOverNoAduit = isOverNoAduit;
    }
    
    
    
    
}
