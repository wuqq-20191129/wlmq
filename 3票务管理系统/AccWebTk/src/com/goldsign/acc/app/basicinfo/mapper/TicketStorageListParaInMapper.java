/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.mapper;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageListParaIn;
import java.util.List;

/**
 *
 * @author liudezeng
 */
public interface TicketStorageListParaInMapper {

    public List<TicketStorageListParaIn> queryParm(TicketStorageListParaIn queryCondition);

    public List<TicketStorageListParaIn> getListForAdd(TicketStorageListParaIn po);

    public int addTicketStorageListParaIn(TicketStorageListParaIn po);

    public int modifyTicketStorageListParaIn(TicketStorageListParaIn po);

    public int deleteTicketStorageListParaIn(TicketStorageListParaIn po);

    public List<TicketStorageListParaIn> getUserStoreSet(TicketStorageListParaIn vo);

    public List<TicketStorageListParaIn> getListForModify(TicketStorageListParaIn queryCondition);

    public String getInfoId();
    
}
