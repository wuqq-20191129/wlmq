/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.mapper;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageZoneDefManage;

import java.util.List;

/**
 * @desc:票区定义
 * @author:xiaowu
 * @create date: 2017-08-01
 */
public interface TicketStorageZoneDefManageMapper {
    
    public List<TicketStorageZoneDefManage> getTicketStorageZoneDefManage(TicketStorageZoneDefManage vo);
    
    public List<TicketStorageZoneDefManage> isExistRecord(TicketStorageZoneDefManage vo);
    
    public String getAreaIdCount();
    
    public String getAreaIdCountNew();
    
    public List<TicketStorageZoneDefManage> getRealNum(TicketStorageZoneDefManage vo);

    public int addTicketStorageZoneDefManage(TicketStorageZoneDefManage vo);

    public int modifyTicketStorageZoneDefManage(TicketStorageZoneDefManage vo);
    
    public List<TicketStorageZoneDefManage> getTicketStorageZoneDefManageById(TicketStorageZoneDefManage vo);
    
    public List<TicketStorageZoneDefManage> getTicketStorageZoneDefManageByName(TicketStorageZoneDefManage vo);

    public int deleteTicketStorageZoneDefManage(TicketStorageZoneDefManage vo);
}
