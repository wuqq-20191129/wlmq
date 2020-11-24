/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.mapper;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageInOutReasonManage;
import java.util.List;

/**
 *
 * @author mh
 */
public interface TicketStorageInOutReasonManageMapper {
    public List<TicketStorageInOutReasonManage> queryTicketStorageInOutReasonManage(TicketStorageInOutReasonManage inOutReasonManage);
    
    public int addTicketStorageInOutReasonManage (TicketStorageInOutReasonManage inOutReasonManage);
    
    public int modifyTicketStorageInOutReasonManage (TicketStorageInOutReasonManage inOutReasonManage);
    
    public List<TicketStorageInOutReasonManage> queryTicketStorageInOutReasonManageById(TicketStorageInOutReasonManage inOutReasonManage);
    
    public List<TicketStorageInOutReasonManage> queryTicketStorageInOutReasonManageByFlag(TicketStorageInOutReasonManage inOutReasonManage);
    
    public List<TicketStorageInOutReasonManage> queryTicketStorageInOutReasonManageByEs(TicketStorageInOutReasonManage inOutReasonManage);
    
    public List<TicketStorageInOutReasonManage> queryTicketStorageInOutReasonManageByName(TicketStorageInOutReasonManage inOutReasonManage);
    
    public int deleteTicketStorageInOutReasonManage (TicketStorageInOutReasonManage inOutReasonManage);
    
}
