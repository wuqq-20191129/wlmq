package com.goldsign.acc.app.prminfo.mapper;

import java.util.List;
import java.util.Map;

import com.goldsign.acc.app.prminfo.entity.BlackListOctSec;

public interface BlackListOctSecMapper {
    public int queryNum();

    public List<BlackListOctSec> getBlackListOctSec(BlackListOctSec queryCondition);

	public List<BlackListOctSec> getDistinctOperatorId();

}