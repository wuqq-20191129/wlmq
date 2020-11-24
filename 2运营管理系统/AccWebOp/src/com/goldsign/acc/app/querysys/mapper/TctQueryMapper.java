/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.querysys.mapper;

import com.goldsign.acc.app.querysys.entity.TctQuery;
import java.util.List;

/**
 *
 * @author luck
 * 次票信息查询
 */
public interface TctQueryMapper {

    public List<TctQuery> getTct(TctQuery queryCondition);
    
    public String getSaleTime(String id);
    
    public long getValidDay(TctQuery s);
    
}
