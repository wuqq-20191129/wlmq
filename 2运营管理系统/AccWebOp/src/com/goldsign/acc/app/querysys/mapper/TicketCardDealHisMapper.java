package com.goldsign.acc.app.querysys.mapper;

import com.goldsign.acc.app.querysys.entity.TicketCardDealHis;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface TicketCardDealHisMapper {
    public void getTicketCardDealHisList(Map map);

	public void queryToMap(Map<String, Object> queryCondition);
}