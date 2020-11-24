/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.st.mapper;

import com.goldsign.acc.app.st.entity.StRptIndex;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author liudz
 * 中间表分表
 */
public interface StRptIndexMapper {

//    public List<StRptIndex> queryPlan(@Param("tableType")List list,@Param("stRptIndex")StRptIndex queryCondition);

    public List getTableTye();

    public List<StRptIndex> queryPlan(Map queryCondition);

    public List<StRptIndex> queryPlanForShow(StRptIndex queryCondition);

    public List getTableTye1();

    public List getTableTye2();
    
    
}
