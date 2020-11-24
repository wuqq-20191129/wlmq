package com.goldsign.acc.app.prminfo.entity;

import java.io.Serializable;

/**
 *
 * @author:mh
 * @create date: 2017-6-12
 */
public class Store implements Serializable{

    /**
     * @return the store_id
     */
    public String getStore_id() {
        return store_id;
    }

    /**
     * @param store_id the store_id to set
     */
    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    /**
     * @return the store_name
     */
    public String getStore_name() {
        return store_name;
    }

    /**
     * @param store_name the store_name to set
     */
    public void setStore_name(String store_name) {
        this.store_name = store_name;
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
     * @return the terminal_no
     */
    public String getTerminal_no() {
        return terminal_no;
    }

    /**
     * @param terminal_no the terminal_no to set
     */
    public void setTerminal_no(String terminal_no) {
        this.terminal_no = terminal_no;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
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

    private String store_id;
    private String store_name;
    private String link_man;
    private String tel;
    private String fax;
    private String terminal_no;
    private String address;
    private String record_flag;


}
