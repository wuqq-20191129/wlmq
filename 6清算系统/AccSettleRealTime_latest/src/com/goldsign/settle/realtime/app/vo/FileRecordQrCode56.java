/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.vo;

import com.goldsign.settle.realtime.frame.vo.FileRecordBase;

/**
 *
 * @author hejj
 */
public class FileRecordQrCode56 extends  FileRecord56{
    private String businessWaterNo;//业务流水
    private String businessWaterNoRel;//关联业务流水

    /**
     * @return the businessWaterNo
     */
    public String getBusinessWaterNo() {
        return businessWaterNo;
    }

    /**
     * @param businessWaterNo the businessWaterNo to set
     */
    public void setBusinessWaterNo(String businessWaterNo) {
        this.businessWaterNo = businessWaterNo;
    }

    /**
     * @return the businessWaterNoRel
     */
    public String getBusinessWaterNoRel() {
        return businessWaterNoRel;
    }

    /**
     * @param businessWaterNoRel the businessWaterNoRel to set
     */
    public void setBusinessWaterNoRel(String businessWaterNoRel) {
        this.businessWaterNoRel = businessWaterNoRel;
    }
    
    
}
