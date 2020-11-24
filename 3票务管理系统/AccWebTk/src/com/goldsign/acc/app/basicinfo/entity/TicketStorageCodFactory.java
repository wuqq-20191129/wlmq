/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.entity;

/**
 *
 * @author mh
 */
public class TicketStorageCodFactory {
    public String getFactory_code() {
        return factory_code;
    }

    public void setFactory_code(String factory_code) {
        this.factory_code = factory_code;
    }

    public String getFactory_name() {
        return factory_name;
    }

    public void setFactory_name(String factory_name) {
        this.factory_name = factory_name;
    }

    public String getFactory_description() {
        return factory_description;
    }

    public void setFactory_description(String factory_description) {
        this.factory_description = factory_description;
    }
       
    private String factory_code;
    private String factory_name;
    private String factory_description;
}
