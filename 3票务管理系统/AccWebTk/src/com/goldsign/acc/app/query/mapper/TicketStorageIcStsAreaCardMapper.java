package com.goldsign.acc.app.query.mapper;

import com.goldsign.acc.app.query.entity.TicketStorageIcStsAreaCard;
import java.util.List;
import java.util.Map;

public interface TicketStorageIcStsAreaCardMapper {
    int insert(TicketStorageIcStsAreaCard record);

    int insertSelective(TicketStorageIcStsAreaCard record);

    public List<TicketStorageIcStsAreaCard> qryCardArea(Map queryCondition);
    
    public List<Map> qryCardBox(Map queryCondition);
}