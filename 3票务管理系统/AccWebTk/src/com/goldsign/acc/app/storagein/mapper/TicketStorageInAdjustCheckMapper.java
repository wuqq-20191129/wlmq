/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storagein.mapper;

import com.goldsign.acc.app.storagein.entity.TicketStorageIcChkStorageForCheckIn;
import java.util.List;


/**
 *
 * @author mqf
 */
public interface TicketStorageInAdjustCheckMapper {

//    public int deleteInProduceDetail(String bill_no);
    
    public List<TicketStorageIcChkStorageForCheckIn> getChkStoragesList(TicketStorageIcChkStorageForCheckIn chkStorage);
    
    public List<TicketStorageIcChkStorageForCheckIn> getChkStoragesListForSelect(TicketStorageIcChkStorageForCheckIn chkStorage);
    
    
}
