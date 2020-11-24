/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.entity;

/**
 *
 * @author liudz
 * 
 */
public class SystemRateLine extends PrmVersion{
        private	String	line_id;
	 
        private	String	service_rate;
        
	private	String	version_no;

        private String record_flag ;
        
        private String line_id_name;

    public String getLine_id_name() {
        return line_id_name;
    }

    public void setLine_id_name(String line_id_name) {
        this.line_id_name = line_id_name;
    }
        
        

    public String getLine_id() {
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }

        
       
    public String getVersion_no() {
        return version_no;
    }

        
    public void setVersion_no(String version_no) {
        this.version_no = version_no;
    }

    public String getService_rate() {
        return service_rate;
    }

    public void setService_rate(String service_rate) {
        this.service_rate = service_rate;
    }

        
    public String getRecord_flag() {
        return record_flag;
    }

        
    public void setRecord_flag(String record_flag) {
        this.record_flag = record_flag;
    }

   
        
}
