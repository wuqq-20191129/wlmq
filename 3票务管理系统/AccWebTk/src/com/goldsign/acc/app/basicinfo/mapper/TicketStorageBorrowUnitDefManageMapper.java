/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.mapper;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageBorrowUnitDefManage;
import java.util.List;

/**
 *
 * @author liudz
 */
public interface TicketStorageBorrowUnitDefManageMapper {
//
//    public List<TicketStorageBorrowUnitDefManage> getTicketStorageBorrowUnitDefManage();
    
    public List<TicketStorageBorrowUnitDefManage> getListForAdd(TicketStorageBorrowUnitDefManage po);

    public int modifyTicketStorageBorrowUnitDefManage(TicketStorageBorrowUnitDefManage po);

    public int addTicketStorageBorrowUnitDefManage(TicketStorageBorrowUnitDefManage po);
    
     public int deleteTicketStorageBorrowUnitDefManage(TicketStorageBorrowUnitDefManage po);

    public List<TicketStorageBorrowUnitDefManage> getListForAddName(TicketStorageBorrowUnitDefManage po);

    public List<TicketStorageBorrowUnitDefManage> getListForModify(TicketStorageBorrowUnitDefManage po);

    public List getListForModifyName(TicketStorageBorrowUnitDefManage po);

    public List<TicketStorageBorrowUnitDefManage> getBorrowUnitDefManageForAdd(TicketStorageBorrowUnitDefManage queryCondition);

    public List<TicketStorageBorrowUnitDefManage> getTicketStorageBorrowUnitDefManage(TicketStorageBorrowUnitDefManage queryCondition);

    public List<String> getOutLendBillName();
                                                  

    
}
