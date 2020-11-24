package com.goldsign.acc.app.querysys.mapper;

import com.goldsign.acc.app.querysys.entity.CodePubFlag;
import com.goldsign.acc.app.querysys.entity.SignCardReturnQuery;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface SignCardReturnQueryMapper {
    public List<SignCardReturnQuery> getSignCardReturnQuery(SignCardReturnQuery queryCondition);
    public List<CodePubFlag> getIdTypes();
	public List<Map> queryToMap(SignCardReturnQuery queryCondition);
}