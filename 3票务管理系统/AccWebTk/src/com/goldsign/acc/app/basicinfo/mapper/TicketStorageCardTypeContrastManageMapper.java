/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.mapper;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageCardTypeContrastManage;
import java.util.List;
/**
 *
 * @author chenzx
 */
public interface TicketStorageCardTypeContrastManageMapper {
  
    public List<TicketStorageCardTypeContrastManage> getTicketStorageCardTypeContrastManage(TicketStorageCardTypeContrastManage vo);
    
    public int addTicketStorageCardTypeContrastManage(TicketStorageCardTypeContrastManage vo);

    public int modifyTicketStorageCardTypeContrastManage(TicketStorageCardTypeContrastManage vo);

    public List<TicketStorageCardTypeContrastManage> getTicketStorageCardTypeContrastManageById(TicketStorageCardTypeContrastManage vo);
    
    public List<TicketStorageCardTypeContrastManage> getTicketStorageCardTypeContrastManageByStock(TicketStorageCardTypeContrastManage vo);
    
    public List<TicketStorageCardTypeContrastManage> getTicketStorageCardTypeContrastManageByChect(TicketStorageCardTypeContrastManage vo);
    
    public List<TicketStorageCardTypeContrastManage> getTicketStorageCardTypeContrastManageByFlag(TicketStorageCardTypeContrastManage vo);
    
    public int deleteTicketStorageCardTypeContrastManage(TicketStorageCardTypeContrastManage vo);
}

