/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.mapper;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageParaAbandon;
import java.util.List;

/**
 *
 * @author mh
 */
public interface TicketStorageParaAbandonMapper {

    public List<TicketStorageParaAbandon> queryParm(TicketStorageParaAbandon queryCondition);

    public int addTicketStorageParaAbandon(TicketStorageParaAbandon po);

    public List<TicketStorageParaAbandon> getListForAdd(TicketStorageParaAbandon po);

    public int modifyTicketStorageParaAbandon(TicketStorageParaAbandon po);

    public int deleteTicketStorageParaAbandon(TicketStorageParaAbandon po);

    public List<TicketStorageParaAbandon> getUserStoreSet(TicketStorageParaAbandon vo);

    public String getWaterNo();
    
}
