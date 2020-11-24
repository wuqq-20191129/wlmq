package com.goldsign.acc.app.system.mapper;

import com.goldsign.acc.app.system.entity.CpuDegrees;

import java.util.List;

public interface CpuDegreesMapper {
//    int insert(CpuDegrees record);
//
//    int insertSelective(CpuDegrees record);

    List<CpuDegrees> queryPlan();

    int insertIntoCur(CpuDegrees vo);

    int insertIntoHis(CpuDegrees vo);

    int update(CpuDegrees vo);
}