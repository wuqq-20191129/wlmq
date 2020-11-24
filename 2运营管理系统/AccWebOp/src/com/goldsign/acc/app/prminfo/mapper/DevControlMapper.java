package com.goldsign.acc.app.prminfo.mapper;

import com.goldsign.acc.app.prminfo.entity.DevControl;
import java.util.List;
import java.util.Map;

/**
 * 设备控制----设备控制
 * @author xiaowu
 * @version 20170613
 */
public interface DevControlMapper {
    
    public List<DevControl> getDevControls(DevControl transferStationCode);

    public int addDevControl(DevControl transferStationCode);

    public int modifyDevControl(DevControl transferStationCode);

    public List<DevControl> getDevControlById(DevControl transferStationCode);

    public int deleteDevControl(DevControl transferStationCode);

    public int submitToOldFlag(DevControl transferStationCode);

    public int submitFromDraftToCurOrFur(DevControl transferStationCode);

    public int deleteDevControlForClone(DevControl transferStationCode);

    public int cloneFromCurOrFurToDraft(DevControl transferStationCode);
    
    public List<Map> queryToMap(DevControl queryCondition);
}
