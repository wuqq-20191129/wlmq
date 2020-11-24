/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.opma.entity;

import java.io.Serializable;
import java.util.Vector;

/**
 *
 * @author mqf
 */
public class ReportQryCfg  implements Serializable {
    
    private String report_module;
    private String qry_con_code;
    private String qry_con_pos;
    
    
    
    private String report_name;
    
    private String qryConCodeString;
    
    private String qryConPosString;
    
//    private Vector qryConCodes = new Vector();

    public String getReport_module() {
        return report_module;
    }

    public void setReport_module(String report_module) {
        this.report_module = report_module;
    }

    public String getReport_name() {
        return report_name;
    }

    public void setReport_name(String report_name) {
        this.report_name = report_name;
    }

    public String getQry_con_code() {
        return qry_con_code;
    }

    public void setQry_con_code(String qry_con_code) {
        this.qry_con_code = qry_con_code;
    }

    public String getQry_con_pos() {
        return qry_con_pos;
    }

    public void setQry_con_pos(String qry_con_pos) {
        this.qry_con_pos = qry_con_pos;
    }

    public String getQryConCodeString() {
        return qryConCodeString;
    }

    public void setQryConCodeString(String qryConCodeString) {
        this.qryConCodeString = qryConCodeString;
    }

    public String getQryConPosString() {
        return qryConPosString;
    }

    public void setQryConPosString(String qryConPosString) {
        this.qryConPosString = qryConPosString;
    }

     
    
    
}
