/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicparams.mapper;

import com.goldsign.acc.app.basicparams.entity.ParamsSys;
import java.util.List;

/**
 *
 * @author luck
 */
public interface ParamsSysMapper {

    public int addPlan(ParamsSys vo);

    public List<ParamsSys> queryPlan(ParamsSys vo);

    public int deletePlan(ParamsSys vo);

    public int modifyPlan(ParamsSys vo);

    public int updateCurrentToHistory(ParamsSys po);

    public int auditUpdate(ParamsSys po);

    public List<ParamsSys> getTypeDescriptionByTypeCode(ParamsSys po);

    public List<ParamsSys> getTypeCodeByTypeDes(ParamsSys po);

    public List<ParamsSys> getValueByCode(ParamsSys po);

    public List<ParamsSys> getCodeByValue(ParamsSys po);

}
