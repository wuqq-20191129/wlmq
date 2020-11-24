/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.mapper;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageDistributeReserveManage;
import java.util.List;

/**
 *
 * @authorliduezeng
 */
public interface TicketStorageDistributeReserveManageMapper {

    public List<TicketStorageDistributeReserveManage> queryParm(TicketStorageDistributeReserveManage queryCondition);

    public List<TicketStorageDistributeReserveManage> getListForAdd(TicketStorageDistributeReserveManage po);

    public int addTicketStorageDistributeReserveManage(TicketStorageDistributeReserveManage po);

    public int modifyTicketStorageDistributeReserveManage(TicketStorageDistributeReserveManage po);

    public int deleteTicketStorageDistributeReserveManage(TicketStorageDistributeReserveManage po);
    
}
