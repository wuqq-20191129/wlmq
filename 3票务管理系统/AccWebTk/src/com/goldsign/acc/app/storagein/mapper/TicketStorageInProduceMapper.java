/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storagein.mapper;

import com.goldsign.acc.app.storagein.entity.TicketStorageInBill;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;


/**
 *
 * @author mqf
 */
public interface TicketStorageInProduceMapper {

    
    public void addInBill(HashMap<String, Object> params);
    
    public int modifyInBill(TicketStorageInBill vo);
    
    public int modifyPduProduceBillForDeleteInBill(TicketStorageInBill vo);
    
    public int deleteInBill(TicketStorageInBill vo);
    
    public int deleteInOutDiffForDeleteInBill(TicketStorageInBill vo);
    
    public int getInDetailCountForDeleteInBill(Vector<TicketStorageInBill> vo);
    
    public int getInOutDiffCount(TicketStorageInBill vo);
    
    public void auditInBill(HashMap<String, Object> params);
    
    
}
