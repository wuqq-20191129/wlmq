package com.goldsign.acc.app.query.mapper;

import com.goldsign.acc.app.query.entity.TicketStoragePduOrder;
import com.goldsign.acc.app.query.entity.TicketStoragePduOrder;
import java.util.List;

public interface TicketStoragePduOrderMapper {
    int insert(TicketStoragePduOrder record);

    int insertSelective(TicketStoragePduOrder record);
    
    List<TicketStoragePduOrder> qryPduOrder(TicketStoragePduOrder record);
}