package com.goldsign.acc.app.basicinfo.mapper;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageChestDefManage;
import java.util.List;

/**
 * 票柜定义
 * @author xiaowu
 * 20170726
 */
public interface TicketStorageChestDefManageMapper {
    
    public List<TicketStorageChestDefManage> getTicketStorageChestDefManages(TicketStorageChestDefManage vo);
    
    public List<TicketStorageChestDefManage> getTicketStorageChestDefManageDetails(TicketStorageChestDefManage vo);
    
    public List<TicketStorageChestDefManage> getAreaId(TicketStorageChestDefManage vo);
    
    public List<TicketStorageChestDefManage> checkChestId(TicketStorageChestDefManage vo);
    
    public List<TicketStorageChestDefManage> getTicketUnit(TicketStorageChestDefManage vo);
    
    public List<TicketStorageChestDefManage> getStoreyNum(TicketStorageChestDefManage vo);
    
    public List<TicketStorageChestDefManage> getBaseNum(TicketStorageChestDefManage vo);
    
    public List<TicketStorageChestDefManage> getMaxBoxNum(TicketStorageChestDefManage vo);
    
    public List<TicketStorageChestDefManage> getBoxUnit(TicketStorageChestDefManage vo);
    
    public List<TicketStorageChestDefManage> getChestName(TicketStorageChestDefManage vo);
    
    public List<TicketStorageChestDefManage> isEmptyForChest(TicketStorageChestDefManage vo);
    
    public List<TicketStorageChestDefManage> getChestMaxNum(TicketStorageChestDefManage vo);
    
    public List<TicketStorageChestDefManage> getCardMoney(TicketStorageChestDefManage vo);
    
    public List<TicketStorageChestDefManage> checkBase(TicketStorageChestDefManage vo);
    
    public List<TicketStorageChestDefManage> getStatus(TicketStorageChestDefManage vo);
    
    public List<TicketStorageChestDefManage> getCheckPlace(TicketStorageChestDefManage vo);
    
    public List<TicketStorageChestDefManage> getPutPlace(TicketStorageChestDefManage vo);
    
    public int addTicketStorageChestDefManage(TicketStorageChestDefManage vo);
    
    public int addStorey(TicketStorageChestDefManage vo);
    
    public int addBase(TicketStorageChestDefManage vo);

    public int modifyTicketStorageChestDefManage(TicketStorageChestDefManage vo);
    
    public int upperAreaNum(TicketStorageChestDefManage vo);
    
    public int updateCodeChest(TicketStorageChestDefManage vo);
    
    public int updateNextPlace(TicketStorageChestDefManage vo);
    
    public int lowerAreaNum(TicketStorageChestDefManage vo);

//    public List<TicketStorageChestDefManage> getTicketStorageChestDefManageById(TicketStorageChestDefManage vo);
    
//    public List<TicketStorageChestDefManage> getTicketStorageChestDefManageByName(TicketStorageChestDefManage vo);
    
//    public List<TicketStorageChestDefManage> getTicketStorageChestDefManageByDefinition(TicketStorageChestDefManage vo);

    public int deleteTicketStorageChestDefManage(TicketStorageChestDefManage vo);
    
    public int deleteBase(TicketStorageChestDefManage vo);
    
    public int deleteCodBoxInfo(TicketStorageChestDefManage vo);
    
    public int deleteStsStorage(TicketStorageChestDefManage vo);
    
    public int deleteCodeStorey(TicketStorageChestDefManage vo);
    
    public int deleteCodeChest(TicketStorageChestDefManage vo);
    
    public int deletePutPlace(TicketStorageChestDefManage vo);
}

