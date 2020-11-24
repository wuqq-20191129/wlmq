/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.produce.mapper;

import com.goldsign.acc.app.produce.entity.TicketStorageProduceBill;
import java.util.HashMap;
import java.util.List;


/**
 *
 * @author mqf
 */
public interface TicketStorageProduceBillMapper {

    
    public List<TicketStorageProduceBill> getProduceBillList(TicketStorageProduceBill vo);
    
    public int modifyProduceBill(TicketStorageProduceBill vo);
    
    
    public void auditProduceBill(HashMap<String, Object> params);
    
    
}
