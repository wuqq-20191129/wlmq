/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.mapper;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageDistributeStationParm;
import java.util.List;

/**
 *
 * @author liudezeng
 */
public interface TicketStorageDistributeStationParmMapper {

    public List<TicketStorageDistributeStationParm> queryParm(TicketStorageDistributeStationParm queryCondition);

    public List<TicketStorageDistributeStationParm> getListForAdd(TicketStorageDistributeStationParm po);

    public int addTicketStorageDistributeStationParm(TicketStorageDistributeStationParm po);

    public int modifyTicketStorageDistributeStationParm(TicketStorageDistributeStationParm po);
    
    public int deleteTicketStorageDistributeStationParm(TicketStorageDistributeStationParm po);
    
}
