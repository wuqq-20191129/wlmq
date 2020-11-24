package com.goldsign.acc.app.prminfo.entity;

import java.io.Serializable;

/**
 * 充值终端通讯参数
 * @author xiaowu
 * @version 20170614
 */
public class ChargeServer extends PrmVersion  implements Serializable{
    
    private String server_ip;         //设备IP
    private String server_port;       //设备端口
    private String server_desc;       //设备描述
    private String server_level;       //优先级
    private String record_flag ;       
    private String version_no;       
    
    private String server_lever_name;

    public String getServer_lever_name() {
        return server_lever_name;
    }

    public void setServer_lever_name(String server_lever_name) {
        this.server_lever_name = server_lever_name;
    }

    public String getServer_ip() {
        return server_ip;
    }

    public void setServer_ip(String server_ip) {
        this.server_ip = server_ip;
    }

    public String getServer_port() {
        return server_port;
    }

    public void setServer_port(String server_port) {
        this.server_port = server_port;
    }

    public String getServer_desc() {
        return server_desc;
    }

    public void setServer_desc(String server_desc) {
        this.server_desc = server_desc;
    }

    public String getServer_level() {
        return server_level;
    }

    public void setServer_level(String server_level) {
        this.server_level = server_level;
    }

    public String getRecord_flag() {
        return record_flag;
    }

    public void setRecord_flag(String record_flag) {
        this.record_flag = record_flag;
    }

    public String getVersion_no() {
        return version_no;
    }

    public void setVersion_no(String version_no) {
        this.version_no = version_no;
    }
    
    
}
