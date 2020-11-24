package com.goldsign.acc.app.prminfo.mapper;

import java.util.List;
import java.util.Map;

import com.goldsign.acc.app.prminfo.entity.BlackListOct;

public interface BlackListOctMapper {

    public List<BlackListOct> getBlackListOct(BlackListOct queryCondition);

    public int queryNum();

	public List<BlackListOct> getDistinctOperatorId();
}