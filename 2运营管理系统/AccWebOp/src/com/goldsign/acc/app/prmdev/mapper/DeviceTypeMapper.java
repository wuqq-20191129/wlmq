package com.goldsign.acc.app.prmdev.mapper;

import com.goldsign.acc.app.prmdev.entity.DeviceType;
import com.goldsign.acc.app.prmdev.entity.DeviceTypeKey;
import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import java.util.List;

public interface DeviceTypeMapper {
    int deleteByPrimaryKey(DeviceTypeKey key);

    int insert(DeviceType record);

    DeviceType selectByPrimaryKey(DeviceTypeKey key);
    
    List<DeviceType> selectDeviceTypes(DeviceType dt);

    int updateByPrimaryKeySelective(DeviceType record);

    int updateByPrimaryKey(DeviceType record);
    
    int submitToOldFlag(PrmVersion pv);

    int submitFromDraftToCurOrFur(PrmVersion pv);

    int deleteDeviceTypesForClone(PrmVersion pv);

    int cloneFromCurOrFurToDraft(PrmVersion pv);
}