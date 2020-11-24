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
public class CardTicketAttr extends PrmVersion implements Serializable{
    private String card_main_id;
    private String card_sub_id;
    private String version_no;
    private String record_flag;
    private String once_charge_money;
    private String once_charge_count;
    private String add_begin_day;
    private String add_valid_day;
    private String exit_timeout_count;
    private String exit_timeout_money;
    private String entry_timeout_count;
    private String entry_timeout_money;
    private String field1;
    private String field2;
    private String field3;
    private String package_id;
    private String card_main_name;
    private String card_sub_name;

    public String getCard_main_id() {
        return card_main_id;
    }

    public void setCard_main_id(String card_main_id) {
        this.card_main_id = card_main_id;
    }

    public String getCard_sub_id() {
        return card_sub_id;
    }

    public void setCard_sub_id(String card_sub_id) {
        this.card_sub_id = card_sub_id;
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

    public String getOnce_charge_money() {
        return once_charge_money;
    }

    public void setOnce_charge_money(String once_charge_money) {
        this.once_charge_money = once_charge_money;
    }

    public String getOnce_charge_count() {
        return once_charge_count;
    }

    public void setOnce_charge_count(String once_charge_count) {
        this.once_charge_count = once_charge_count;
    }

    public String getAdd_begin_day() {
        return add_begin_day;
    }

    public void setAdd_begin_day(String add_begin_day) {
        this.add_begin_day = add_begin_day;
    }

    public String getAdd_valid_day() {
        return add_valid_day;
    }

    public void setAdd_valid_day(String add_valid_day) {
        this.add_valid_day = add_valid_day;
    }

    public String getExit_timeout_count() {
        return exit_timeout_count;
    }

    public void setExit_timeout_count(String exit_timeout_count) {
        this.exit_timeout_count = exit_timeout_count;
    }

    public String getExit_timeout_money() {
        return exit_timeout_money;
    }

    public void setExit_timeout_money(String exit_timeout_money) {
        this.exit_timeout_money = exit_timeout_money;
    }

    public String getEntry_timeout_count() {
        return entry_timeout_count;
    }

    public void setEntry_timeout_count(String entry_timeout_count) {
        this.entry_timeout_count = entry_timeout_count;
    }

    public String getEntry_timeout_money() {
        return entry_timeout_money;
    }

    public void setEntry_timeout_money(String entry_timeout_money) {
        this.entry_timeout_money = entry_timeout_money;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3;
    }

    public String getPackage_id() {
        return package_id;
    }

    public void setPackage_id(String package_id) {
        this.package_id = package_id;
    }

    public String getCard_main_name() {
        return card_main_name;
    }

    public void setCard_main_name(String card_main_name) {
        this.card_main_name = card_main_name;
    }

    public String getCard_sub_name() {
        return card_sub_name;
    }

    public void setCard_sub_name(String card_sub_name) {
        this.card_sub_name = card_sub_name;
    }
    
}
