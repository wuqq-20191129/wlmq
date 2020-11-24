/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicparams.mapper;

import com.goldsign.acc.app.basicparams.entity.ParamsThres;
import java.util.List;

/**
 *
 * @author luck
 */
public interface ParamsThresMapper {

    public int addPlan(ParamsThres vo);

    public List<ParamsThres> queryPlan(ParamsThres vo);

    public String getMaxId();
    
    public int deletePlan(ParamsThres vo);

    public int modifyPlan(ParamsThres vo);
    
    public int updateCurrentToHistory(ParamsThres vo);
    
    public int auditUpdate(ParamsThres vo);
    
    
}
