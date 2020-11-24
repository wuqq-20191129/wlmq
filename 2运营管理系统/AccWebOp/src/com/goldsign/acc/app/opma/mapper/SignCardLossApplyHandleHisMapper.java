package com.goldsign.acc.app.opma.mapper;

import com.goldsign.acc.app.opma.entity.SignCardLossApplyHandle;
import com.goldsign.acc.app.opma.entity.SignCardLossApplyHandleHis;
import java.util.List;
import java.util.Map;

public interface SignCardLossApplyHandleHisMapper {

    public List<SignCardLossApplyHandleHis> getHisResult(SignCardLossApplyHandle selectVo);

    public void deletByCardLogicalId(SignCardLossApplyHandle vo);

	public List<Map> queryToMap(SignCardLossApplyHandle queryCondition);
}