package com.goldsign.acc.app.prminfo.mapper;

import java.util.List;
import java.util.Map;

import com.goldsign.acc.app.prminfo.entity.BlackListMtrSec;

public interface BlackListMtrSecMapper {

    public List<BlackListMtrSec> getBlackListMtrSec(BlackListMtrSec queryCondition);

    public List<BlackListMtrSec> getAllBlackListMtrSecForCheck();

    public int updateWithModel(BlackListMtrSec vo);

    public int insertIntoLog(BlackListMtrSec vo);

    public String getMaxBalanceWaterNo();

    public int deleteByCardLogicalId(BlackListMtrSec queryCondition);

    public int addWithModel(BlackListMtrSec vo);

    public int queryNum();

    public String checkInBlackListMtr(BlackListMtrSec vo);

	public List<BlackListMtrSec> getDistinctOperatorId();
}
