package com.goldsign.acc.app.prmdev.mapper;

import com.goldsign.acc.app.prmdev.entity.SamList;
import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import java.util.List;

public interface SamListMapper {
    int deleteByPrimaryKey(SamList key);

    int insert(SamList record);

    int insertSelective(SamList record);

    SamList selectByPrimaryKey(SamList record);
    
    List<SamList> selectSamLists(SamList record);

    int updateByPrimaryKeySelective(SamList record);

    int updateByPrimaryKey(SamList record);
    
    int submitToOldFlag(PrmVersion pv);

    int submitFromDraftToCurOrFur(PrmVersion pv);

    int deleteDeviceTypesForClone(PrmVersion pv);

    int cloneFromCurOrFurToDraft(PrmVersion pv);
}