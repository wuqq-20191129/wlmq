package com.goldsign.acc.app.prminfo.mapper;

import com.goldsign.acc.app.prminfo.entity.StationCode;
import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

/**
 * 线路车站--车站表
 * @author xiaowu
 * @version 20170611
 */
public interface StationCodeMapper {
    
    //查询车站信息
    public List<StationCode> getStationCodes(StationCode stationCode);
    
    public List<StationCode> getStationCodesByLineIds(@Param("lineIdList")List lineIdList);

    public int addStationCode(StationCode stationCode);

    public int modifyStationCode(StationCode stationCode);

    public List<StationCode> getStationCodeById(StationCode stationCode);

    public int deleteStationCode(StationCode stationCode);

    public int submitToOldFlag(StationCode stationCode);

    public int submitFromDraftToCurOrFur(StationCode stationCode);

    public int deleteStationCodeForClone(StationCode stationCode);

    public int cloneFromCurOrFurToDraft(StationCode stationCode);
    
    public List<StationCode> getStationCodesRecordFlagIs3();
    
    public List<Map> queryToMap(StationCode queryCondition);
}
