/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.produce.mapper;

import com.goldsign.acc.app.produce.entity.TicketStorageProduceBillDetail;
import java.util.List;


/**
 *
 * @author mqf
 */
public interface TicketStorageProduceBillDetailMapper {

    
    public List<TicketStorageProduceBillDetail> getProduceBillDetailList(TicketStorageProduceBillDetail vo);
    
    
    
}
