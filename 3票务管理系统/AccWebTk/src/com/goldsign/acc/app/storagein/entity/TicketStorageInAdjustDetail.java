/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storagein.entity;


/**
 * 调账入库明细
 * @author mqf
 */
public class TicketStorageInAdjustDetail extends TicketStorageInDetailBase {
    
    //页面输入使用
//    private String in_type;
    
    private String record_flag;


    public String getRecord_flag() {
        return record_flag;
    }

    public void setRecord_flag(String record_flag) {
        this.record_flag = record_flag;
    }
    
    

}
