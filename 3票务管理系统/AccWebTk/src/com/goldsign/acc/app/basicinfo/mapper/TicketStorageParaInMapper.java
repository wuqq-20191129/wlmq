/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.mapper;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageParaIn;
import com.goldsign.acc.app.basicinfo.entity.TicketStorageStationContrastManage;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author liudezeng
 * 
 */
public interface TicketStorageParaInMapper {

    public List<TicketStorageParaIn> queryParm(TicketStorageParaIn queryCondition);

    public List<TicketStorageParaIn> getListForAdd(TicketStorageParaIn po);

    public int addTicketStorageParaIn(TicketStorageParaIn po);

    public int modifyTicketStorageParaIn(TicketStorageParaIn po);

    public int deleteTicketStorageParaIn(TicketStorageParaIn po);

    public List<TicketStorageParaIn> getUserStoreSet(TicketStorageParaIn vo);

    public Vector getStorageVector();

    public List<TicketStorageParaIn> getListForModify(TicketStorageParaIn queryCondition);

    public String getWaterNo();
    
}
