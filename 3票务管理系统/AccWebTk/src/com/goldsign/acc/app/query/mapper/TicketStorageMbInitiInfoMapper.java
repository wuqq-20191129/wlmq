package com.goldsign.acc.app.query.mapper;

import com.goldsign.acc.app.query.entity.TicketStorageMbInitiInfo;
import com.goldsign.acc.app.query.entity.TicketStorageMbInitiInfo;
import java.util.List;

public interface TicketStorageMbInitiInfoMapper {
    int insert(TicketStorageMbInitiInfo record);

    int insertSelective(TicketStorageMbInitiInfo record);
    
    List<TicketStorageMbInitiInfo> qryInitInfo(TicketStorageMbInitiInfo record);
}