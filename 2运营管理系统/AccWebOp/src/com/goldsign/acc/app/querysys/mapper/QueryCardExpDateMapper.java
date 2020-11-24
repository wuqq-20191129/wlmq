/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.querysys.mapper;

import com.goldsign.acc.app.querysys.entity.QueryCardExpDate;
import java.util.List;
import java.util.Map;

/**
 *
 * @author luck
 */
public interface QueryCardExpDateMapper {
    
    public List<QueryCardExpDate> getQueryCardExpDates(QueryCardExpDate queryCardExpDate);

    public List<Map> queryToMap(QueryCardExpDate queryCondition);
    
}
