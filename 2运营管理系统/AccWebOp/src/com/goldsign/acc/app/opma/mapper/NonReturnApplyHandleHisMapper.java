package com.goldsign.acc.app.opma.mapper;

import com.goldsign.acc.app.opma.entity.NonReturnApplyHandle;
import com.goldsign.acc.app.opma.entity.NonReturnApplyHandleHis;
import java.util.List;
import java.util.Map;

public interface NonReturnApplyHandleHisMapper {

    public List<NonReturnApplyHandleHis> getHisResult(NonReturnApplyHandle selectVo);

    public void deletByCardLogicalId(NonReturnApplyHandle vo);

	public List<Map> queryToMap(NonReturnApplyHandle queryCondition);
}