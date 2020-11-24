package com.goldsign.acc.app.storagechk.mapper;

import com.goldsign.acc.app.storagechk.entity.TicketStorageIcCodBoxDetailKey;

import java.util.List;

public interface TicketStorageIcCodBoxDetailMapper {
    int deleteByPrimaryKey(TicketStorageIcCodBoxDetailKey key);

    int insert(TicketStorageIcCodBoxDetailKey record);

    int insertSelective(TicketStorageIcCodBoxDetailKey record);
    
    List<TicketStorageIcCodBoxDetailKey> selectByPrimaryKey(TicketStorageIcCodBoxDetailKey record);
    
    int count(TicketStorageIcCodBoxDetailKey record);
}