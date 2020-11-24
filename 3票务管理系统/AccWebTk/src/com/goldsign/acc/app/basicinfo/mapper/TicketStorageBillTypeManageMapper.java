/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.mapper;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageBillTypeManage;
import java.util.List;

/**
 *
 * @author mh
 */
public interface TicketStorageBillTypeManageMapper {
    
    List<TicketStorageBillTypeManage> queryTicketStorageBillTypeManage(TicketStorageBillTypeManage billTypeManage);
    
    List<TicketStorageBillTypeManage> queryTicketStorageBillTypeManageByName (TicketStorageBillTypeManage billTypeManage);
    
}
