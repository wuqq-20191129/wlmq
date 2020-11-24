/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.entity;

/**
 *
 * @author ldz
 */
public class SystemRateContc extends PrmVersion{
        private	String	contc_id;
	 
        private	String	service_rate;
        
	private	String	version_no;

        private String record_flag ;
        
        private String contc_id_name;

    public String getContc_id() {
        return contc_id;
    }

    public void setContc_id(String contc_id) {
        this.contc_id = contc_id;
    }

    public String getService_rate() {
        return service_rate;
    }

    public void setService_rate(String service_rate) {
        this.service_rate = service_rate;
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

    public String getContc_id_name() {
        return contc_id_name;
    }

    public void setContc_id_name(String contc_id_name) {
        this.contc_id_name = contc_id_name;
    }
    
}
