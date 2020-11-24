/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.mapper;

import com.goldsign.acc.app.prminfo.entity.FareDealTotal;
import java.util.List;

/**
 *
 * @author mh
 */
public interface FareDealTotalMapper {

    public List<FareDealTotal> getFareDealTotal(FareDealTotal queryCondition);

    public int modifyFareDealTotal(FareDealTotal po);
    
    public int deleteFareDealTotal(FareDealTotal po);

    public int addFareDealTotal(FareDealTotal po);

    public void submitToOldFlag(FareDealTotal po);

    public int submitFromDraftToCurOrFur(FareDealTotal po);

    public void deleteFareDealTotalForClone(FareDealTotal po);

    public int cloneFromCurOrFurToDraft(FareDealTotal po);

    public List<FareDealTotal> getFareDealTotalById(FareDealTotal queryCondition);

    
}
