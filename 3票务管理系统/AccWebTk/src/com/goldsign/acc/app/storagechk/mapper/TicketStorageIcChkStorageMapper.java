package com.goldsign.acc.app.storagechk.mapper;

import com.goldsign.acc.app.storagechk.entity.TicketStorageIcChkStorage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface TicketStorageIcChkStorageMapper {
    int deleteByPrimaryKey(String checkBillNo);

    int insert(TicketStorageIcChkStorage record);

    int insertSelective(TicketStorageIcChkStorage record);

    List<TicketStorageIcChkStorage> selectByPrimaryKey(TicketStorageIcChkStorage record);
    
    List<TicketStorageIcChkStorage> getIcChkStorages(TicketStorageIcChkStorage record);

    int updateByPrimaryKeySelective(TicketStorageIcChkStorage record);

    int updateByPrimaryKey(TicketStorageIcChkStorage record);
    
//    void audit(Map<String,String> map);
    void audit(HashMap<String, Object> map);
}