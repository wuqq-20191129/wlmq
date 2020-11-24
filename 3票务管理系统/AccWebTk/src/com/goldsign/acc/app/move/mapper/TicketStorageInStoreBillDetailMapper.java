package com.goldsign.acc.app.move.mapper;

import com.goldsign.acc.app.move.entity.TicketStorageInStoreBillDetail;
import java.util.List;

public interface TicketStorageInStoreBillDetailMapper {
    int deleteByPrimaryKey(Long waterNo);

    int insert(TicketStorageInStoreBillDetail record);

    int insertSelective(TicketStorageInStoreBillDetail record);

    TicketStorageInStoreBillDetail selectByPrimaryKey(Long waterNo);

    int updateByPrimaryKeySelective(TicketStorageInStoreBillDetail record);

    int updateByPrimaryKey(TicketStorageInStoreBillDetail record);

    public List<TicketStorageInStoreBillDetail> qryInBillDetail(TicketStorageInStoreBillDetail record);
}