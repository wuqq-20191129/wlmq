package com.goldsign.acc.app.prminfo.mapper;

import com.goldsign.acc.app.prminfo.entity.DegradeModeRecd;
import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import java.util.List;

public interface DegradeModeRecdMapper {
    int deleteByPrimaryKey(Long waterNo);

    int insert(DegradeModeRecd record);

    int insertSelective(DegradeModeRecd record);

    DegradeModeRecd selectByPrimaryKey(Long waterNo);
    
    List<DegradeModeRecd> selectDegradeModeRecds(DegradeModeRecd record);

    int updateByPrimaryKeySelective(DegradeModeRecd record);

    int updateByPrimaryKey(DegradeModeRecd record);
    
    long selectSeqNextval();

    public DegradeModeRecd existselectRecord(DegradeModeRecd dmr);
}