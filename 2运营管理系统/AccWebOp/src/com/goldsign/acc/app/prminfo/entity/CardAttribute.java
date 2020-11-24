/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.entity;

import java.io.Serializable;

/**
 *
 * @author luck
 */
public class CardAttribute extends PrmVersion implements Serializable {

    private String card_main_id;
    private String card_main_id_text;
    private String card_sub_id;
    private String card_sub_id_text;
    private String purse_value_type; //钱包值类型（0无值类型，1金额，2次数，3天数）
    private String purse_value_type_text;
    private String max_store_val; //车票余额/次上限
    private String is_overdraw; //是否允许透支（0禁止，1允许）
    private String is_overdraw_text;
    private String overdraw_limit;//透支额度
    private String is_recharge;//是否允许充值（0禁止，1允许）
    private String is_recharge_text;
    private String max_recharge_val; //每次充值上限
    private String update_fee_type;//更新时收费方式（0现金，1卡，2卡或现金）
    private String update_fee_type_text;
    private String is_chk_cur_station;//付费区非本站更新检查标志（0不检查，1检查）
    private String is_chk_cur_station_text;
    private String is_chk_cur_date;//付费区非本日更新检查标志（0不检查，1检查）
    private String is_chk_cur_date_text;
    private String is_refund; //是否允许退款
    private String is_refund_text;
    private String refund_limit;//退款时使用次数限制（000表示不限制，使用次数未达到此限制时不允许退款）
    private String daily_trip_cnt_lmt;//日乘坐次数上限（00表示不限制）
    private String month_trip_cnt_lmt;//月乘坐次数上限（0000表示不限制）
    private String exp_date;//有效期（单位：分钟，从发售时开始多少天内有效，以分钟计）
    private String is_allow_postpone;//是否允许延期（0禁止，1允许）	
    private String is_allow_postpone_text;
    private String extend_expire_day;//可延长时间（单位：天，每次延长有效期时可延长的时间）
    private String deposit_amnt;	//押金
    private String card_cost; //售价
    private String auxiliary_expenses; //发售手续费
    private String is_activation;//使用前是否需激活（0不需要，1需要）
    private String is_activation_text; 
    private String is_chk_blk;//是否检查黑名单（0不检查，1检查）
    private String is_chk_blk_text;
    private String is_chk_remain; //是否检查余额余次（0不检查，1检查）
    private String is_chk_remain_text;
    private String is_chk_valid_phy_logic;//逻辑及物理有效期检查（0不检查逻辑及物理有效期，1检查逻辑及物理有效期，2检查逻辑但不检查物理有效期，3不检查逻辑但检查物理有效期）
    private String is_chk_valid_phy_logic_text;
    private String is_chk_sequ; //进出站次序检查（0进出站均不检查进出站次序，1进出站均检查进出站次序，2进站不检查进出站次序，出战检查进出站次序，3进站检查进出站次序，出战不检查进出站次序）
    private String is_chk_sequ_text;
    private String is_chk_exce_time; //是否检查超时（0不检查，1检查）
    private String is_chk_exce_time_text;
    private String is_chk_exce_st; //是否检查超乘（0不检查，1检查）
    private String is_chk_exce_st_text;
    private String is_limit_entry_exit; //是否限制进出站点（0否，1是。具体限制的进出站信息制票时写在票卡上）
    private String is_limit_entry_exit_text;
    private String is_limit_station; //是否只允许本站进出（0否，1是。即本站进的只能本站出，不能在其他站出）
    private String is_limit_station_text;
    private String add_val_equi_type; //充值设备（设备类的字符信息代码，0不可以，1可以） 
    private String add_val_equi_type_text;
    private String use_on_equi;//哪种设备可用（设备类的字符信息代码，0不可以，1可以）
    private String use_on_equi_text;
    private String is_limit_sale_entry;//本站发售控制标记（0表示只有在本站购买的单程票才能进站，1表示从其他车站购买的单程票，可以在本站进入）
    private String is_limit_sale_entry_text;
    private String refund_limit_balance;//退款余额上限
    private String refund_auxiliary_expense;//退款手续费
    private String version_no;
    private String record_flag;
    private String is_lmt_daily_trip;
    private String is_preexamine;
    private String preexamine_cnt_lmt;
    private String is_chk_exp_date;
    private String is_limit_line;
    private String limit_intime;
    private String is_limit_intime;
    private String entry_control;
    private String sale_equi_type; //发售设备
    private String sale_equi_type_text;
    private String is_deposit_refund;//是否允许退押金（工本费）0：禁止 1：允许
    private String is_deposit_refund_text;
    private String is_chk_valid_day;//是否检查票卡发行后有效天数(0：不检查，1：检查)
    private String is_chk_valid_day_text;
    private String valid_day;//票卡发行后有效天数(单位：分钟。从发行时开始多少天内有效，以分钟计，如预制单程票发行后有效天数为 N，配售到 BOM，在 N天前（含第 N 天）发售的单程票可以正常进出闸，在 N++1 天后则不能进出闸)

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

    public String getPurse_value_type() {
        return purse_value_type;
    }

    public void setPurse_value_type(String purse_value_type) {
        this.purse_value_type = purse_value_type;
    }

    public String getPurse_value_type_text() {
        return purse_value_type_text;
    }

    public void setPurse_value_type_text(String purse_value_type_text) {
        this.purse_value_type_text = purse_value_type_text;
    }

    public String getMax_store_val() {
        return max_store_val;
    }

    public void setMax_store_val(String max_store_val) {
        this.max_store_val = max_store_val;
    }

    public String getIs_overdraw() {
        return is_overdraw;
    }

    public void setIs_overdraw(String is_overdraw) {
        this.is_overdraw = is_overdraw;
    }

    public String getIs_overdraw_text() {
        return is_overdraw_text;
    }

    public void setIs_overdraw_text(String is_overdraw_text) {
        this.is_overdraw_text = is_overdraw_text;
    }

    public String getOverdraw_limit() {
        return overdraw_limit;
    }

    public void setOverdraw_limit(String overdraw_limit) {
        this.overdraw_limit = overdraw_limit;
    }

    public String getIs_recharge() {
        return is_recharge;
    }

    public void setIs_recharge(String is_recharge) {
        this.is_recharge = is_recharge;
    }

    public String getIs_recharge_text() {
        return is_recharge_text;
    }

    public void setIs_recharge_text(String is_recharge_text) {
        this.is_recharge_text = is_recharge_text;
    }

    public String getMax_recharge_val() {
        return max_recharge_val;
    }

    public void setMax_recharge_val(String max_recharge_val) {
        this.max_recharge_val = max_recharge_val;
    }

    public String getUpdate_fee_type() {
        return update_fee_type;
    }

    public void setUpdate_fee_type(String update_fee_type) {
        this.update_fee_type = update_fee_type;
    }

    public String getUpdate_fee_type_text() {
        return update_fee_type_text;
    }

    public void setUpdate_fee_type_text(String update_fee_type_text) {
        this.update_fee_type_text = update_fee_type_text;
    }

    public String getIs_chk_cur_station() {
        return is_chk_cur_station;
    }

    public void setIs_chk_cur_station(String is_chk_cur_station) {
        this.is_chk_cur_station = is_chk_cur_station;
    }

    public String getIs_chk_cur_station_text() {
        return is_chk_cur_station_text;
    }

    public void setIs_chk_cur_station_text(String is_chk_cur_station_text) {
        this.is_chk_cur_station_text = is_chk_cur_station_text;
    }

    public String getIs_chk_cur_date() {
        return is_chk_cur_date;
    }

    public void setIs_chk_cur_date(String is_chk_cur_date) {
        this.is_chk_cur_date = is_chk_cur_date;
    }

    public String getIs_chk_cur_date_text() {
        return is_chk_cur_date_text;
    }

    public void setIs_chk_cur_date_text(String is_chk_cur_date_text) {
        this.is_chk_cur_date_text = is_chk_cur_date_text;
    }

    public String getIs_refund() {
        return is_refund;
    }

    public void setIs_refund(String is_refund) {
        this.is_refund = is_refund;
    }

    public String getIs_refund_text() {
        return is_refund_text;
    }

    public void setIs_refund_text(String is_refund_text) {
        this.is_refund_text = is_refund_text;
    }

    public String getRefund_limit() {
        return refund_limit;
    }

    public void setRefund_limit(String refund_limit) {
        this.refund_limit = refund_limit;
    }

    public String getDaily_trip_cnt_lmt() {
        return daily_trip_cnt_lmt;
    }

    public void setDaily_trip_cnt_lmt(String daily_trip_cnt_lmt) {
        this.daily_trip_cnt_lmt = daily_trip_cnt_lmt;
    }

    public String getMonth_trip_cnt_lmt() {
        return month_trip_cnt_lmt;
    }

    public void setMonth_trip_cnt_lmt(String month_trip_cnt_lmt) {
        this.month_trip_cnt_lmt = month_trip_cnt_lmt;
    }

    public String getExp_date() {
        return exp_date;
    }

    public void setExp_date(String exp_date) {
        this.exp_date = exp_date;
    }

    public String getIs_allow_postpone() {
        return is_allow_postpone;
    }

    public void setIs_allow_postpone(String is_allow_postpone) {
        this.is_allow_postpone = is_allow_postpone;
    }

    public String getIs_allow_postpone_text() {
        return is_allow_postpone_text;
    }

    public void setIs_allow_postpone_text(String is_allow_postpone_text) {
        this.is_allow_postpone_text = is_allow_postpone_text;
    }

    public String getExtend_expire_day() {
        return extend_expire_day;
    }

    public void setExtend_expire_day(String extend_expire_day) {
        this.extend_expire_day = extend_expire_day;
    }

    public String getDeposit_amnt() {
        return deposit_amnt;
    }

    public void setDeposit_amnt(String deposit_amnt) {
        this.deposit_amnt = deposit_amnt;
    }

    public String getCard_cost() {
        return card_cost;
    }

    public void setCard_cost(String card_cost) {
        this.card_cost = card_cost;
    }

    public String getAuxiliary_expenses() {
        return auxiliary_expenses;
    }

    public void setAuxiliary_expenses(String auxiliary_expenses) {
        this.auxiliary_expenses = auxiliary_expenses;
    }

    public String getIs_activation() {
        return is_activation;
    }

    public void setIs_activation(String is_activation) {
        this.is_activation = is_activation;
    }

    public String getIs_activation_text() {
        return is_activation_text;
    }

    public void setIs_activation_text(String is_activation_text) {
        this.is_activation_text = is_activation_text;
    }

    public String getIs_chk_blk() {
        return is_chk_blk;
    }

    public void setIs_chk_blk(String is_chk_blk) {
        this.is_chk_blk = is_chk_blk;
    }

    public String getIs_chk_blk_text() {
        return is_chk_blk_text;
    }

    public void setIs_chk_blk_text(String is_chk_blk_text) {
        this.is_chk_blk_text = is_chk_blk_text;
    }

    public String getIs_chk_remain() {
        return is_chk_remain;
    }

    public void setIs_chk_remain(String is_chk_remain) {
        this.is_chk_remain = is_chk_remain;
    }

    public String getIs_chk_remain_text() {
        return is_chk_remain_text;
    }

    public void setIs_chk_remain_text(String is_chk_remain_text) {
        this.is_chk_remain_text = is_chk_remain_text;
    }

    public String getIs_chk_valid_phy_logic() {
        return is_chk_valid_phy_logic;
    }

    public void setIs_chk_valid_phy_logic(String is_chk_valid_phy_logic) {
        this.is_chk_valid_phy_logic = is_chk_valid_phy_logic;
    }

    public String getIs_chk_valid_phy_logic_text() {
        return is_chk_valid_phy_logic_text;
    }

    public void setIs_chk_valid_phy_logic_text(String is_chk_valid_phy_logic_text) {
        this.is_chk_valid_phy_logic_text = is_chk_valid_phy_logic_text;
    }

    public String getIs_chk_sequ() {
        return is_chk_sequ;
    }

    public void setIs_chk_sequ(String is_chk_sequ) {
        this.is_chk_sequ = is_chk_sequ;
    }

    public String getIs_chk_sequ_text() {
        return is_chk_sequ_text;
    }

    public void setIs_chk_sequ_text(String is_chk_sequ_text) {
        this.is_chk_sequ_text = is_chk_sequ_text;
    }

    public String getIs_chk_exce_time() {
        return is_chk_exce_time;
    }

    public void setIs_chk_exce_time(String is_chk_exce_time) {
        this.is_chk_exce_time = is_chk_exce_time;
    }

    public String getIs_chk_exce_time_text() {
        return is_chk_exce_time_text;
    }

    public void setIs_chk_exce_time_text(String is_chk_exce_time_text) {
        this.is_chk_exce_time_text = is_chk_exce_time_text;
    }

    public String getIs_chk_exce_st() {
        return is_chk_exce_st;
    }

    public void setIs_chk_exce_st(String is_chk_exce_st) {
        this.is_chk_exce_st = is_chk_exce_st;
    }

    public String getIs_chk_exce_st_text() {
        return is_chk_exce_st_text;
    }

    public void setIs_chk_exce_st_text(String is_chk_exce_st_text) {
        this.is_chk_exce_st_text = is_chk_exce_st_text;
    }

    public String getIs_limit_entry_exit() {
        return is_limit_entry_exit;
    }

    public void setIs_limit_entry_exit(String is_limit_entry_exit) {
        this.is_limit_entry_exit = is_limit_entry_exit;
    }

    public String getIs_limit_entry_exit_text() {
        return is_limit_entry_exit_text;
    }

    public void setIs_limit_entry_exit_text(String is_limit_entry_exit_text) {
        this.is_limit_entry_exit_text = is_limit_entry_exit_text;
    }

    public String getIs_limit_station() {
        return is_limit_station;
    }

    public void setIs_limit_station(String is_limit_station) {
        this.is_limit_station = is_limit_station;
    }

    public String getIs_limit_station_text() {
        return is_limit_station_text;
    }

    public void setIs_limit_station_text(String is_limit_station_text) {
        this.is_limit_station_text = is_limit_station_text;
    }

    public String getAdd_val_equi_type() {
        return add_val_equi_type;
    }

    public void setAdd_val_equi_type(String add_val_equi_type) {
        this.add_val_equi_type = add_val_equi_type;
    }

    public String getAdd_val_equi_type_text() {
        return add_val_equi_type_text;
    }

    public void setAdd_val_equi_type_text(String add_val_equi_type_text) {
        this.add_val_equi_type_text = add_val_equi_type_text;
    }

    public String getUse_on_equi() {
        return use_on_equi;
    }

    public void setUse_on_equi(String use_on_equi) {
        this.use_on_equi = use_on_equi;
    }

    public String getUse_on_equi_text() {
        return use_on_equi_text;
    }

    public void setUse_on_equi_text(String use_on_equi_text) {
        this.use_on_equi_text = use_on_equi_text;
    }

    public String getIs_limit_sale_entry() {
        return is_limit_sale_entry;
    }

    public void setIs_limit_sale_entry(String is_limit_sale_entry) {
        this.is_limit_sale_entry = is_limit_sale_entry;
    }

    public String getIs_limit_sale_entry_text() {
        return is_limit_sale_entry_text;
    }

    public void setIs_limit_sale_entry_text(String is_limit_sale_entry_text) {
        this.is_limit_sale_entry_text = is_limit_sale_entry_text;
    }

    public String getRefund_limit_balance() {
        return refund_limit_balance;
    }

    public void setRefund_limit_balance(String refund_limit_balance) {
        this.refund_limit_balance = refund_limit_balance;
    }

    public String getRefund_auxiliary_expense() {
        return refund_auxiliary_expense;
    }

    public void setRefund_auxiliary_expense(String refund_auxiliary_expense) {
        this.refund_auxiliary_expense = refund_auxiliary_expense;
    }

    public String getVersion_no() {
        return version_no;
    }

    public void setVersion_no(String version_no) {
        this.version_no = version_no;
    }

    public String getRecord_flag() {
        return record_flag;
    }

    public void setRecord_flag(String record_flag) {
        this.record_flag = record_flag;
    }

    public String getIs_lmt_daily_trip() {
        return is_lmt_daily_trip;
    }

    public void setIs_lmt_daily_trip(String is_lmt_daily_trip) {
        this.is_lmt_daily_trip = is_lmt_daily_trip;
    }

    public String getIs_preexamine() {
        return is_preexamine;
    }

    public void setIs_preexamine(String is_preexamine) {
        this.is_preexamine = is_preexamine;
    }

    public String getPreexamine_cnt_lmt() {
        return preexamine_cnt_lmt;
    }

    public void setPreexamine_cnt_lmt(String preexamine_cnt_lmt) {
        this.preexamine_cnt_lmt = preexamine_cnt_lmt;
    }

    public String getIs_chk_exp_date() {
        return is_chk_exp_date;
    }

    public void setIs_chk_exp_date(String is_chk_exp_date) {
        this.is_chk_exp_date = is_chk_exp_date;
    }

    public String getIs_limit_line() {
        return is_limit_line;
    }

    public void setIs_limit_line(String is_limit_line) {
        this.is_limit_line = is_limit_line;
    }

    public String getLimit_intime() {
        return limit_intime;
    }

    public void setLimit_intime(String limit_intime) {
        this.limit_intime = limit_intime;
    }

    public String getIs_limit_intime() {
        return is_limit_intime;
    }

    public void setIs_limit_intime(String is_limit_intime) {
        this.is_limit_intime = is_limit_intime;
    }

    public String getEntry_control() {
        return entry_control;
    }

    public void setEntry_control(String entry_control) {
        this.entry_control = entry_control;
    }

    public String getSale_equi_type() {
        return sale_equi_type;
    }

    public void setSale_equi_type(String sale_equi_type) {
        this.sale_equi_type = sale_equi_type;
    }

    public String getSale_equi_type_text() {
        return sale_equi_type_text;
    }

    public void setSale_equi_type_text(String sale_equi_type_text) {
        this.sale_equi_type_text = sale_equi_type_text;
    }

    public String getIs_deposit_refund() {
        return is_deposit_refund;
    }

    public void setIs_deposit_refund(String is_deposit_refund) {
        this.is_deposit_refund = is_deposit_refund;
    }

    public String getIs_deposit_refund_text() {
        return is_deposit_refund_text;
    }

    public void setIs_deposit_refund_text(String is_deposit_refund_text) {
        this.is_deposit_refund_text = is_deposit_refund_text;
    }

    public String getIs_chk_valid_day() {
        return is_chk_valid_day;
    }

    public void setIs_chk_valid_day(String is_chk_valid_day) {
        this.is_chk_valid_day = is_chk_valid_day;
    }

    public String getIs_chk_valid_day_text() {
        return is_chk_valid_day_text;
    }

    public void setIs_chk_valid_day_text(String is_chk_valid_day_text) {
        this.is_chk_valid_day_text = is_chk_valid_day_text;
    }

    public String getValid_day() {
        return valid_day;
    }

    public void setValid_day(String valid_day) {
        this.valid_day = valid_day;
    }
    
    
}
