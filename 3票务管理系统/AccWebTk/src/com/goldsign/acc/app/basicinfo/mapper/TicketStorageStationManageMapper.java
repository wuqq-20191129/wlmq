/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.mapper;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageStationManage;
import java.util.List;
/**
 *
 * @author taidb
 */
public interface TicketStorageStationManageMapper {
    
    public List<TicketStorageStationManage> getTicketStorageStationManage(TicketStorageStationManage tssm);

    public int addTicketStorageStationManage(TicketStorageStationManage tssm);

    public int modifyTicketStorageStationManage(TicketStorageStationManage tssm);

    public List<TicketStorageStationManage> getTicketStorageStationManageById(TicketStorageStationManage tssm);
    
    public List<TicketStorageStationManage> getTicketStorageStationManageByName(TicketStorageStationManage tssm);
    
    public List<TicketStorageStationManage> getTicketStorageStationManageByDefinition(TicketStorageStationManage tssm);

    public int deleteTicketStorageStationManage(TicketStorageStationManage tssm);
    
    //检查票库车站在车站对照表有无关联的票库车站
    public List<TicketStorageStationManage> checkTicketStationContrastManage(TicketStorageStationManage tssm);
}
