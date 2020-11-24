/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicparams.mapper;

import com.goldsign.acc.app.basicparams.entity.ParamsStation;
import java.util.List;

/**
 *
 * @author mh
 */
public interface ParamsStationMapper {
    List<ParamsStation> queryParamsStation (ParamsStation paramsStation);
    public int addParamsStation (ParamsStation paramsStation);
    public int deleteParamsStation (ParamsStation paramsStation);
    public int modifyParamsStation (ParamsStation paramsStation);
    public int updateCurrentToHistory (ParamsStation paramsStation);
    public int auditUpdate(ParamsStation paramsStation);
    
}
