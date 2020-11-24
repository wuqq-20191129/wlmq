/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.mapper;

import com.goldsign.acc.app.prminfo.entity.SystemRateContc;
import java.util.List;

/**
 *
 * @author ldz
 */
public interface SystemRateContcMapper {
     public List<SystemRateContc> getSystemRateContc(SystemRateContc systemRateContc);

    public int addSystemRateContc(SystemRateContc systemRateContc);

    public int modifySystemRateContc(SystemRateContc systemRateContc);

    public List<SystemRateContc> getSystemRateContcById(SystemRateContc systemRateContc);

    public int deleteSystemRateContc(SystemRateContc systemRateContc);

    public int submitToOldFlag(SystemRateContc systemRateContc);

    public int submitFromDraftToCurOrFur(SystemRateContc systemRateContc);

    public int deleteSystemRateContcForClone(SystemRateContc systemRateContc);
    
    public int cloneFromCurOrFurToDraft(SystemRateContc systemRateContc);
}
