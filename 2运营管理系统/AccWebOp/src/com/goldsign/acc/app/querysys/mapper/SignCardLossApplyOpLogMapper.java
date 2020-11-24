package com.goldsign.acc.app.querysys.mapper;

import com.goldsign.acc.app.querysys.entity.CodePubFlag;
import com.goldsign.acc.app.querysys.entity.SignCardLossApplyOpLog;
import java.util.List;
import java.util.Map;

public interface SignCardLossApplyOpLogMapper {

    public List<SignCardLossApplyOpLog> getSignCardLossApplyOpLog(SignCardLossApplyOpLog queryCondition);

    public List<CodePubFlag> getOperatorName ();

    public List<CodePubFlag> getOperationType();

    public List<CodePubFlag> getIdTypes();

    public int insertLog(SignCardLossApplyOpLog vo);

	public List<Map> queryToMap(SignCardLossApplyOpLog queryCondition);
}