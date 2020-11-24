/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.commu.mapper;

import com.goldsign.acc.app.commu.entity.CmIdxHis;
import java.util.List;

/**
 * 数据交换系统 - 分表记录
 * @author luck
 */
public interface CmIdxHisMapper {

    public List<CmIdxHis> queryPlan(CmIdxHis queryCondition);
    
}
