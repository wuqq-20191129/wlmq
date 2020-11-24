/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.mapper;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageLineManage;
import java.util.List;
/**
 *
 * @author taidb
 */
public interface TicketStorageLineManageMapper {
    
    public List<TicketStorageLineManage> getTicketStorageLineManage(TicketStorageLineManage tslm);

    public int addTicketStorageLineManage(TicketStorageLineManage tslm);

    public int modifyTicketStorageLineManage(TicketStorageLineManage tslm);

    public List<TicketStorageLineManage> getTicketStorageLineManageById(TicketStorageLineManage tslm);
    
    public List<TicketStorageLineManage> getTicketStorageLineManageByName(TicketStorageLineManage tslm);

    public int deleteTicketStorageLineManage(TicketStorageLineManage tslm);
    
    public List<TicketStorageLineManage> checkTicketStorageLineStationsManage(TicketStorageLineManage tslm); //检查票库线路是否有对应的票库车站 
}
