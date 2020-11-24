/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.mapper;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageCardSubTypeManage;
import java.util.List;
/**
 *
 * @author chenzx
 */
public interface TicketStorageCardSubTypeManageMapper {
    
    public List<TicketStorageCardSubTypeManage> getTicketStorageCardSubTypeManage(TicketStorageCardSubTypeManage vo);
    
    public int addTicketStorageCardSubTypeManage(TicketStorageCardSubTypeManage vo);

    public int modifyTicketStorageCardSubTypeManage(TicketStorageCardSubTypeManage vo);

    public List<TicketStorageCardSubTypeManage> getTicketStorageCardSubTypeManageById(TicketStorageCardSubTypeManage vo);
    
    public List<TicketStorageCardSubTypeManage> getTicketStorageCardSubTypeManageByName(TicketStorageCardSubTypeManage vo);
    
    public List<TicketStorageCardSubTypeManage> getTicketStorageCardSubTypeManageByStock(TicketStorageCardSubTypeManage vo);
    
    public List<TicketStorageCardSubTypeManage> getTicketStorageCardSubTypeManageByContras(TicketStorageCardSubTypeManage vo);

    public List<TicketStorageCardSubTypeManage> getTicketStorageCardSubTypeManageByChest(TicketStorageCardSubTypeManage vo);
    
    public List<TicketStorageCardSubTypeManage> getTicketStorageCardSubTypeManageByDefinition(TicketStorageCardSubTypeManage vo);

    public int deleteTicketStorageCardSubTypeManage(TicketStorageCardSubTypeManage vo);
}

