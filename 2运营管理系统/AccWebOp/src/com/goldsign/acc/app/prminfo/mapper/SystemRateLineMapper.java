/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.mapper;
import com.goldsign.acc.app.prminfo.entity.SystemRateLine;
import java.util.List;

/**
 *
 * @author mh
 */
public interface SystemRateLineMapper {
     public List<SystemRateLine> getSystemRateLine(SystemRateLine systemRateLine);

    public int addSystemRateLine(SystemRateLine systemRateLine);

    public int modifySystemRateLine(SystemRateLine systemRateLine);

    public List<SystemRateLine> getSystemRateLineById(SystemRateLine systemRateLine);

    public int deleteSystemRateLine(SystemRateLine systemRateLine);

    public int submitToOldFlag(SystemRateLine systemRateLine);

    public int submitFromDraftToCurOrFur(SystemRateLine systemRateLine);

    public int deleteSystemRateLineForClone(SystemRateLine systemRateLine);

    public int cloneFromCurOrFurToDraft(SystemRateLine systemRateLine);
}
