/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.entity;

import java.io.Serializable;

/**
 *
 * @author:mh
 * @create date: 2017-6-16
 */
public class Contc implements Serializable{

    /**
     * @return the contc_id
     */
    public String getContc_id() {
        return contc_id;
    }

    /**
     * @param contc_id the contc_id to set
     */
    public void setContc_id(String contc_id) {
        this.contc_id = contc_id;
    }

    /**
     * @return the contc_name
     */
    public String getContc_name() {
        return contc_name;
    }

    /**
     * @param contc_name the contc_name to set
     */
    public void setContc_name(String contc_name) {
        this.contc_name = contc_name;
    }

    /**
     * @return the link_man
     */
    public String getLink_man() {
        return link_man;
    }

    /**
     * @param link_man the link_man to set
     */
    public void setLink_man(String link_man) {
        this.link_man = link_man;
    }

    /**
     * @return the tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * @param tel the tel to set
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * @return the fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * @param fax the fax to set
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * @return the sequence
     */
    public String getSequence() {
        return sequence;
    }

    /**
     * @param Sequence the sequence to set
     */
    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    /**
     * @return the record_flag
     */
    public String getRecord_flag() {
        return record_flag;
    }

    /**
     * @param record_flag the record_flag to set
     */
    public void setRecord_flag(String record_flag) {
        this.record_flag = record_flag;
    }

    private String contc_id;
    private String contc_name;
    private String link_man;
    private String tel;
    private String fax;
    private String sequence;
    private String record_flag;


}
