package com.goldsign.acc.app.move.mapper;

import com.goldsign.acc.app.move.entity.TicketStorageTicketMove;
import java.util.List;
import java.util.Map;

public interface TicketStorageTicketMoveMapper {
    int insert(TicketStorageTicketMove record);

    int insertSelective(TicketStorageTicketMove record);

    public List<TicketStorageTicketMove> qryTktMove(TicketStorageTicketMove ticketMove);

    public int delete(String billNo);

    public int modify(TicketStorageTicketMove ticketMove);
    
    public void auditStorageMove(Map parmMap);
}