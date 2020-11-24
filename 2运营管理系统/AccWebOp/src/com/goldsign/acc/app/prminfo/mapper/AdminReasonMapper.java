package com.goldsign.acc.app.prminfo.mapper;

import com.goldsign.acc.app.prminfo.entity.AdminReason;
import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import java.util.List;

public interface AdminReasonMapper {
    int deleteByPrimaryKey(AdminReason record);

    int insert(AdminReason record);

    int insertSelective(AdminReason record);

    AdminReason selectByPrimaryKey(AdminReason record);
    
    List<AdminReason> selectAdminReasons(AdminReason record);

    int updateByPrimaryKeySelective(AdminReason record);

    int updateByPrimaryKey(AdminReason record);
    
    int submitToOldFlag(PrmVersion pv);

    int submitFromDraftToCurOrFur(PrmVersion pv);

    int deleteDeviceTypesForClone(PrmVersion pv);

    int cloneFromCurOrFurToDraft(PrmVersion pv);
}