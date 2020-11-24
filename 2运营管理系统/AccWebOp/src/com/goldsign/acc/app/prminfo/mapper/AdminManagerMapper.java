package com.goldsign.acc.app.prminfo.mapper;

import com.goldsign.acc.app.prminfo.entity.AdminManager;
import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import java.util.List;

public interface AdminManagerMapper {
    int deleteByPrimaryKey(AdminManager record);

    int insert(AdminManager record);

    int insertSelective(AdminManager record);

    AdminManager selectByPrimaryKey(AdminManager record);

    int updateByPrimaryKeySelective(AdminManager record);

    int updateByPrimaryKey(AdminManager record);
    
    List<AdminManager> selectAdminManagers(AdminManager record);

    int submitToOldFlag(PrmVersion pv);

    int submitFromDraftToCurOrFur(PrmVersion pv);

    int deleteDeviceTypesForClone(PrmVersion pv);

    int cloneFromCurOrFurToDraft(PrmVersion pv);
}