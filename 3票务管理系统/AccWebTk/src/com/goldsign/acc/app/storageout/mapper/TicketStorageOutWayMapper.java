package com.goldsign.acc.app.storageout.mapper;

import com.goldsign.acc.app.storageout.entity.TicketStorageOutStorageInfo;
import java.util.List;

public interface TicketStorageOutWayMapper {

    public List<TicketStorageOutStorageInfo> getExistLogicalSection(TicketStorageOutStorageInfo vo);

}