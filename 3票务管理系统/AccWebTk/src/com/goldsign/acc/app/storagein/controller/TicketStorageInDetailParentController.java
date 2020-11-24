/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storagein.controller;

import com.goldsign.acc.app.storagein.entity.TicketStorageIcIdxHistory;
import com.goldsign.acc.app.storagein.entity.TicketStorageInBill;
import com.goldsign.acc.app.storagein.entity.TicketStorageInDetailBase;
import com.goldsign.acc.app.storagein.entity.TicketStorageInProduceDetail;
import com.goldsign.acc.app.storagein.mapper.TicketStorageInDetailParentMapper;
import com.goldsign.acc.app.storagein.mapper.TicketStorageInParentMapper;
import com.goldsign.acc.app.storagein.mapper.TicketStorageInProduceDetailMapper;
import com.goldsign.acc.frame.controller.StorageOutInBaseController;
import com.goldsign.acc.frame.entity.PubFlag;
import com.goldsign.acc.frame.util.PageControlUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;



/**
 *
 * @author mh
 */
public class TicketStorageInDetailParentController  extends StorageOutInBaseController{
    
    @Autowired
    private TicketStorageInDetailParentMapper tsInDetailParentMapper;
    
    
    protected List<TicketStorageInDetailBase> queryForInDetail(String recordFlag, String billNo) {
        String inDetailHisTable = this.getInDetailHisTable(recordFlag, billNo);
        Map<String,String> queryInDetailMap = new HashMap<String,String>();
        queryInDetailMap.put("tableName", inDetailHisTable);
        queryInDetailMap.put("billNo", billNo);
        List<TicketStorageInDetailBase> inDetailList = tsInDetailParentMapper.getInDetailList(queryInDetailMap);
        return inDetailList;
                
    }
    
    protected String getInDetailHisTable(String recordFlag, String billNo) {
        String inDetailHisTable = "w_ic_in_store_detail ";
        if (recordFlag.equals("0")) {
            int inDetailCount = tsInDetailParentMapper.getInDetailCount(billNo);
            if (inDetailCount <=0) {
                List<Map> hisTableList = tsInDetailParentMapper.getHisTableFromIdxHistory(billNo);
                if (!hisTableList.isEmpty()) {
                    Map  hisTableMap = hisTableList.get(0);
                    inDetailHisTable = (String)hisTableMap.get("HIS_TABLE");
                }
            }
        }
        return inDetailHisTable;
        
    }
    
}
