/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.produce.mapper;

import com.goldsign.acc.app.produce.entity.TicketStorageProduceBillDetail;
import com.goldsign.acc.app.produce.entity.TicketStorageProduceUselessDetail;
import java.util.HashMap;
import java.util.List;


/**
 *
 * @author mqf
 */
public interface TicketStorageProduceBillLostDetailMapper {

    
    public List<TicketStorageProduceUselessDetail> getProduceUselessDetailList(TicketStorageProduceUselessDetail vo);
    
    public int getProduceBillAuditCount(String billNo);
    
    public void addLostDetail(HashMap<String, Object> params);
    
    public List<TicketStorageProduceUselessDetail> getUselessDetailByCardNo(TicketStorageProduceUselessDetail vo);
    
    public int deleteUselessDetailForBox(TicketStorageProduceUselessDetail po);
    
    public int deleteUselessDetailForCardNo(TicketStorageProduceUselessDetail po);
    
    public int deleteUselessDetailForCardType(TicketStorageProduceUselessDetail po);
    
    
    
    
    
    
    
    
}
