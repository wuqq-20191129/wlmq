package com.goldsign.acc.app.storagechk.mapper;

import com.goldsign.acc.app.storagechk.entity.TicketStorageIcCodCardTypeContrast;
import com.goldsign.acc.app.storagechk.entity.TicketStorageIcCodCardTypeContrastKey;

public interface TicketStorageIcCodCardTypeContrastMapper {
    int deleteByPrimaryKey(TicketStorageIcCodCardTypeContrastKey key);

    int insert(TicketStorageIcCodCardTypeContrast record);

    int insertSelective(TicketStorageIcCodCardTypeContrast record);

    TicketStorageIcCodCardTypeContrast selectByPrimaryKey(TicketStorageIcCodCardTypeContrastKey key);

    int updateByPrimaryKeySelective(TicketStorageIcCodCardTypeContrast record);

    int updateByPrimaryKey(TicketStorageIcCodCardTypeContrast record);
}