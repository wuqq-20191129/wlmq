/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.st.mapper;

import com.goldsign.acc.app.st.entity.StTrxIndex;
import java.util.List;

/**
 *
 * @author liudz
 * 交易表索引表查询
 */
public interface StTrxIndexMapper {

    public List<StTrxIndex> queryPlan(StTrxIndex queryCondition);
    
}
