package com.goldsign.acc.app.move.mapper;

import com.goldsign.acc.app.move.entity.TicketStorageIcOutBillDetail;
import java.util.List;
import java.util.Map;

public interface TicketStorageIcOutBillDetailMapper {
    int insert(TicketStorageIcOutBillDetail record);

    int insertSelective(TicketStorageIcOutBillDetail record);
    
    List<Map > qryOutBillDetail(TicketStorageIcOutBillDetail record);
}