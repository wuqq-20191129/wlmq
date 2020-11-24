package com.goldsign.acc.app.querysys.mapper;

import com.goldsign.acc.app.querysys.entity.StCardCheckResult;
import java.util.List;
import java.util.Map;

public interface StCardCheckResultMapper {
    int insert(StCardCheckResult record);

    int insertSelective(StCardCheckResult record);
    
    List<StCardCheckResult > carChkQry(StCardCheckResult con);
    
    List<Map > carChkQryToMap(StCardCheckResult con);

    public List<StCardCheckResult> cardQryByMap(Map map);
}