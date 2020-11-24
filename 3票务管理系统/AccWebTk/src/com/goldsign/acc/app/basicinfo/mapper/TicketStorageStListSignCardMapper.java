package com.goldsign.acc.app.basicinfo.mapper;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageStListSignCard;

public interface TicketStorageStListSignCardMapper {
    int deleteByPrimaryKey(String reqNo);

    int insert(TicketStorageStListSignCard record);

    int insertSelective(TicketStorageStListSignCard record);
    
    int qryStListSignCard(TicketStorageStListSignCard record);

    TicketStorageStListSignCard selectByPrimaryKey(String reqNo);

    int updateByPrimaryKeySelective(TicketStorageStListSignCard record);

    int updateByPrimaryKey(TicketStorageStListSignCard record);
}