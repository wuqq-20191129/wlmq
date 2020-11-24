/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.mapper;

import com.goldsign.acc.app.prminfo.entity.BlackListMtr;
import com.goldsign.acc.app.prminfo.entity.BlackListMtrSec;
import java.util.List;
import java.util.Map;

/**
 * @desc:
 * @author:zhongzqi
 * @create date: 2017-6-7
 */
public interface BlackListMtrMapper {

    public List<BlackListMtr> getBlackListMtr(BlackListMtr queryCondition);

    public List<BlackListMtr> checkInBlackListMtr(BlackListMtr queryCondition);

    public int updateWithModel(BlackListMtr vo);

    public int insertIntoLog(BlackListMtr vo);

    public String getMaxBalanceWaterNo();

    public int deleteByCardLogicalId(BlackListMtr queryCondition);

    public int addWithModel(BlackListMtr vo);

    public int queryNum();

    public List<BlackListMtrSec> getAllBlackListMtrForCheckSec();

	public List<BlackListMtr> getDistinctOperatorId();

}
