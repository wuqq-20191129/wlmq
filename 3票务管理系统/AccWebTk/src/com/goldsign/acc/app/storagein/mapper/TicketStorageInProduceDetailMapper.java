/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storagein.mapper;

import java.util.List;
import java.util.Map;


/**
 *
 * @author mqf
 */
public interface TicketStorageInProduceDetailMapper {

    public int deleteInProduceDetail(String bill_no);
    
    public List getInOutDiffProduceList(Map queryMap);
    
    
}
