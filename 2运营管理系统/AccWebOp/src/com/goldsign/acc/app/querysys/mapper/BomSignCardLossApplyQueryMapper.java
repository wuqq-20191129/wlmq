package com.goldsign.acc.app.querysys.mapper;

import com.goldsign.acc.app.querysys.entity.BomSignCardLossApplyQuery;
import com.goldsign.acc.app.querysys.entity.CodePubFlag;
import java.util.List;
import java.util.Map;

public interface BomSignCardLossApplyQueryMapper {

    public List<BomSignCardLossApplyQuery> getBomSignCardLossApplyQuery(BomSignCardLossApplyQuery queryCondition);

     public List<CodePubFlag> getIdTypes();

	public List<Map> queryToMap(BomSignCardLossApplyQuery queryCondition);
}