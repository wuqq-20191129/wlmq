package com.goldsign.acc.app.querysys.mapper;

import com.goldsign.acc.app.querysys.entity.RpLogDetail;
import java.util.List;

public interface RpLogDetailMapper {
    int deleteByPrimaryKey(Long waterNo);

    int insert(RpLogDetail record);

    int insertSelective(RpLogDetail record);

    RpLogDetail selectByPrimaryKey(Long waterNo);

    int updateByPrimaryKeySelective(RpLogDetail record);

    int updateByPrimaryKey(RpLogDetail record);
    
    List rpLogDetailQry(RpLogDetail record);
    
    List rpLogDetailQryToMap(RpLogDetail record);
}