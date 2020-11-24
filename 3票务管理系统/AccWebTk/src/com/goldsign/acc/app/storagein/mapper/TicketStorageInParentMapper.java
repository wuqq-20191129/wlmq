/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storagein.mapper;

import com.goldsign.acc.app.storagein.entity.TicketStorageIcIdxHistory;
import com.goldsign.acc.app.storagein.entity.TicketStorageInBill;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 * @author mqf
 */
public interface TicketStorageInParentMapper {

    
    public List<TicketStorageIcIdxHistory> getIcIdxHistoryListForIn(); 
    
    public int getIcInDetailCount(String billNo);
    
    public  List<TicketStorageInBill> queryForNotExistsSubTb(TicketStorageInBill vo); 
   
    public  List<TicketStorageInBill> queryForExistsSubTb(TicketStorageInBill vo); 
    
    public List<Map> validStorageZone(String billNo);
    
    
}
