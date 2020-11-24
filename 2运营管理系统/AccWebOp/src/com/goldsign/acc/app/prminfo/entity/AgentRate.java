/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.entity;

/**
 *
 * @author mh
 */
public class AgentRate extends PrmVersion{
        private	String	agent_id;
	 
        private	String	service_rate;
        
	private	String	version_no;

        private String record_flag ;
        
        private String agent_id_name;

    public String getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(String agent_id) {
        this.agent_id = agent_id;
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

    public String getAgent_id_name() {
        return agent_id_name;
    }

    public void setAgent_id_name(String agent_id_name) {
        this.agent_id_name = agent_id_name;
    }
        
        
}
