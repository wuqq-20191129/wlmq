package com.goldsign.acc.app.querysys.mapper;

import com.goldsign.acc.app.querysys.entity.AdminHandleQuery;
import java.util.List;
import java.util.Map;

public interface AdminHandleQueryMapper {

    public List<AdminHandleQuery> getAdminHandleQuery(AdminHandleQuery queryCondition);

	public List<Map> queryToMap(AdminHandleQuery queryCondition);
}