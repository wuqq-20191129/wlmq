/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.mapper;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageAdjustReasonManage;
import java.util.List;

/**
 *
 * @author mh
 */
public interface TicketStorageAdjustReasonManageMapper {
    
    public List<TicketStorageAdjustReasonManage> queryTicketStorageAdjustReasonManage(TicketStorageAdjustReasonManage adjustReasonManage);
    
    public int addTicketStorageAdjustReasonManage (TicketStorageAdjustReasonManage adjustReasonManage);
    
    public int modifyTicketStorageAdjustReasonManage (TicketStorageAdjustReasonManage adjustReasonManage);
    
    public List<TicketStorageAdjustReasonManage> queryTicketStorageAdjustReasonManageById(TicketStorageAdjustReasonManage adjustReasonManage);
    
    public List<TicketStorageAdjustReasonManage> queryTicketStorageAdjustReasonManageByName(TicketStorageAdjustReasonManage adjustReasonManage);
    
    public int deleteTicketStorageAdjustReasonManage (TicketStorageAdjustReasonManage adjustReasonManage);
}
