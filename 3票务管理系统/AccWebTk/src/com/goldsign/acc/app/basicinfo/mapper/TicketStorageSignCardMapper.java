package com.goldsign.acc.app.basicinfo.mapper;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageSignCard;
import java.util.List;

public interface TicketStorageSignCardMapper {
    int deleteByPrimaryKey(String reqNo);

    int insert(TicketStorageSignCard record);

    int insertSelective(TicketStorageSignCard record);

    TicketStorageSignCard selectByPrimaryKey(String reqNo);

    int updateByPrimaryKeySelective(TicketStorageSignCard record);

    int updateByPrimaryKey(TicketStorageSignCard record);

    public List<TicketStorageSignCard> qrySignCard(TicketStorageSignCard signCard);
}