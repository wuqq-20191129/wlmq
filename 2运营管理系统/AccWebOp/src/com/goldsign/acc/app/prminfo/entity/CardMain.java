/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.entity;

import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import java.io.Serializable;

/**
 *
 * @author luck
 */
public class CardMain extends PrmVersion implements Serializable{
    private String card_main_id;
    private String card_main_name;
    private String version_no;
    private String record_flag;

    public String getCard_main_id() {
        return card_main_id;
    }

    public void setCard_main_id(String card_main_id) {
        this.card_main_id = card_main_id;
    }

    public String getCard_main_name() {
        return card_main_name;
    }

    public void setCard_main_name(String card_main_name) {
        this.card_main_name = card_main_name;
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
    
}
