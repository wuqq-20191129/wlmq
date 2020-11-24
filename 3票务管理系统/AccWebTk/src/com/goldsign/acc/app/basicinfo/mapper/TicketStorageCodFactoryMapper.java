/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.mapper;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageCodFactory;
import java.util.List;

/**
 *
 * @author mh
 */
public interface TicketStorageCodFactoryMapper {
    List<TicketStorageCodFactory> queryIcCodFactory(TicketStorageCodFactory icCodFactory);
    
    List<TicketStorageCodFactory> queryIcCodFactoryById(TicketStorageCodFactory icCodFactory);
    
    List<TicketStorageCodFactory> queryIcCodFactoryByName(TicketStorageCodFactory icCodFactory);
    
    public int addIcCodFactory(TicketStorageCodFactory icCodFactory);
    
    public int modifyIcCodFactory(TicketStorageCodFactory icCodFactory);
    
    public int deleteIcCodFactory(TicketStorageCodFactory icCodFactory);

    public List<TicketStorageCodFactory> getListForAddName(TicketStorageCodFactory po);
}
