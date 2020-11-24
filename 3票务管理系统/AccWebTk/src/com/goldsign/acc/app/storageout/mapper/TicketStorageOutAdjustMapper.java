/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storageout.mapper;

import com.goldsign.acc.app.storagein.entity.TicketStorageIcChkStorageForCheckIn;
import com.goldsign.acc.app.storageout.entity.TicketStorageOutAdjustBill;
import com.goldsign.acc.app.storageout.entity.TicketStorageOutAdjustBillDetail;
import com.goldsign.acc.app.storageout.entity.TicketStorageOutProduceOrder;
import com.goldsign.acc.app.storageout.entity.TicketStorageOutProduceOutBill;
import com.goldsign.acc.app.storageout.entity.TicketStorageOutProducePlan;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public interface TicketStorageOutAdjustMapper {
    
    public List<TicketStorageOutAdjustBill> queryOutBill(TicketStorageOutAdjustBill vo);
    
    public void addOutBill(HashMap<String, Object> params);
    
    public int getOutDetailCountForDeleteOutBill(Vector<TicketStorageOutAdjustBill> vo);
    
    public int modifyChkStorageForDeleteOutBill(TicketStorageOutAdjustBill vo);
    
    public int deleteOutBill(TicketStorageOutAdjustBill vo);
    
    public void auditOutBill(HashMap<String, Object> params);
    
    
    public List<TicketStorageOutAdjustBillDetail> queryOutBillDetail(TicketStorageOutAdjustBillDetail vo);
    
    public int deleteOutAdjustDetail(String bill_no);
    
    public List<TicketStorageIcChkStorageForCheckIn> getChkStoragesList(TicketStorageIcChkStorageForCheckIn chkStorage);
    
    public List<TicketStorageIcChkStorageForCheckIn> getChkStoragesListForSelect(TicketStorageIcChkStorageForCheckIn chkStorage);
    
    


}
