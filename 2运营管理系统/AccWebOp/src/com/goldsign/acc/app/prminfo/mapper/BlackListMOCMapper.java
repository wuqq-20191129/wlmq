package com.goldsign.acc.app.prminfo.mapper;

import java.util.List;
import java.util.Map;

import com.goldsign.acc.app.prminfo.entity.BlackListMOC;

public interface BlackListMOCMapper {

	List<BlackListMOC> getBlackListMOC(BlackListMOC queryCondition);

	int queryNum();

	public List<BlackListMOC> getDistinctOperatorId();
}