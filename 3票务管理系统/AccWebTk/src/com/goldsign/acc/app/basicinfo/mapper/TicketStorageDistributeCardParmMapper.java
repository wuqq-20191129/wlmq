/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.mapper;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageDistributeCardParm;
import java.util.List;

/**
 *
 * @author liudezeng
 */
public interface TicketStorageDistributeCardParmMapper {

    public List<TicketStorageDistributeCardParm> queryParm(TicketStorageDistributeCardParm queryCondition);

    public List<TicketStorageDistributeCardParm> getListForAdd(TicketStorageDistributeCardParm po);

    public int addTicketStorageDistributeCardParm(TicketStorageDistributeCardParm po);

    public int modifyTicketStorageDistributeCardParm(TicketStorageDistributeCardParm po);

    public int deleteTicketStorageDistributeCardParm(TicketStorageDistributeCardParm po);
    
}
