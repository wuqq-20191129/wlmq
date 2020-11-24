/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.mapper;

import com.goldsign.acc.app.prminfo.entity.FareTimeInterval;
import java.util.List;
import java.util.Map;

/**
 * 联乘时间间隔
 * @author luck
 */
public interface FareTimeIntervalMapper {

    public int add(FareTimeInterval po);

    public List<FareTimeInterval> query(FareTimeInterval queryCondition);
    
     public int delete(FareTimeInterval po);

    public int modify(FareTimeInterval po);

    public void submitToOldFlag(FareTimeInterval po);

    public int submitFromDraftToCurOrFur(FareTimeInterval po);

    public void deleteForClone(FareTimeInterval po);

    public int cloneFromCurOrFurToDraft(FareTimeInterval po);

    public List<Map> queryToMap(FareTimeInterval queryCondition);
    
}
