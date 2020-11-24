
package com.goldsign.acc.app.basicinfo.mapper;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageStationContrastManage;

import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @desc:车站对照表
 * @author:xiaowu
 * @create date: 2017-08-02
 */
public interface TicketStorageStationContrastManageMapper {
    
    public List<TicketStorageStationContrastManage> getTicketStorageStationContrastManage(TicketStorageStationContrastManage vo);
    
    public List<TicketStorageStationContrastManage> getUserStoreSet(TicketStorageStationContrastManage vo);
    
    public List<TicketStorageStationContrastManage> getLincodeCondition(@Param("storageIdList") List storageIdList);
    
    public List<TicketStorageStationContrastManage> getLinesID(@Param("lineIdList") List lineIdList);
    
    public List<TicketStorageStationContrastManage> getStorageVector();
    
    public int ifExistCardType(TicketStorageStationContrastManage vo);
    
    public int ifExistIcCardType(TicketStorageStationContrastManage vo);

    public int addTicketStorageStationContrastManage(TicketStorageStationContrastManage vo);

    public int modifyTicketStorageStationContrastManage(TicketStorageStationContrastManage vo);

    public int deleteTicketStorageStationContrastManage(TicketStorageStationContrastManage vo);
}
