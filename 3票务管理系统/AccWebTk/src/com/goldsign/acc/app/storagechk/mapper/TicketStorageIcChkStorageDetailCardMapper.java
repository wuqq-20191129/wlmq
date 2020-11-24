package com.goldsign.acc.app.storagechk.mapper;

import com.goldsign.acc.app.storagechk.entity.TicketStorageIcChkStorageDetailCard;
import java.util.List;

public interface TicketStorageIcChkStorageDetailCardMapper {
    int deleteByPrimaryKey(String chkBillNo);

    int insert(TicketStorageIcChkStorageDetailCard record);
    
    int delete(TicketStorageIcChkStorageDetailCard record);

    int insertSelective(TicketStorageIcChkStorageDetailCard record);
    
    int count(TicketStorageIcChkStorageDetailCard record);

    TicketStorageIcChkStorageDetailCard selectByPrimaryKey(Short waterNo);

    int updateByPrimaryKeySelective(TicketStorageIcChkStorageDetailCard record);

    int updateByPrimaryKey(TicketStorageIcChkStorageDetailCard record);

    public List<TicketStorageIcChkStorageDetailCard> select(TicketStorageIcChkStorageDetailCard icChkStorageDetailCard);
}