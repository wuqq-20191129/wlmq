/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storagein.entity;

import java.util.List;

/**
 * 生产入库明细
 * @author mqf
 */
public class TicketStorageInProduceDetail extends TicketStorageInDetailBase {
    
    //页面输入使用
    private String in_type;
    
    private String record_flag;

    public String getIn_type() {
        return in_type;
    }

    public void setIn_type(String in_type) {
        this.in_type = in_type;
    }
    

    public String getRecord_flag() {
        return record_flag;
    }

    public void setRecord_flag(String record_flag) {
        this.record_flag = record_flag;
    }
    
    

}
