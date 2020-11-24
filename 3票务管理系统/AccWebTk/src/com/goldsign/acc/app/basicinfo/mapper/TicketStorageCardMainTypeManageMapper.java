/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.mapper;
import com.goldsign.acc.app.basicinfo.entity.TicketStorageCardMainTypeManage;
import java.util.List;
/**
 *
 * @author mh
 */
public interface TicketStorageCardMainTypeManageMapper {
    
    public List<TicketStorageCardMainTypeManage> getTicketStorageCardMainTypeManage(TicketStorageCardMainTypeManage vo);

    public int modifyTicketStorageCardMainTypeManage(TicketStorageCardMainTypeManage vo);
  
    public List<TicketStorageCardMainTypeManage> getTicketStorageCardMainTypeManageByName(TicketStorageCardMainTypeManage vo);

}
