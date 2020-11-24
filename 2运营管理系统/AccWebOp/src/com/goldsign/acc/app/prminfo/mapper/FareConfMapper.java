/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.mapper;

import com.goldsign.acc.app.prminfo.entity.FareConf;
import com.goldsign.acc.app.prminfo.entity.FareTable;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author mqf
 */
public interface FareConfMapper {

    public List<FareConf> getFareConfs(FareConf fareConf);

    public int addFareConf(FareConf fareConf);

    public int modifyFareConf(FareConf fareConf);

    public List<FareConf> getFareConfById(FareConf fareConf);

    public int deleteFareConf(FareConf fareConf);

    public int submitToOldFlag(FareConf fareConf);

    public int submitFromDraftToCurOrFur(FareConf fareConf);

    public int deleteFareConfsForClone(FareConf fareConf);

    public int cloneFromCurOrFurToDraft(FareConf fareConf);
    
    public List<FareConf> getFareConfByFareTableId(Vector<FareTable> pos);
    
    public int getFareConfForRepeatTimes(FareConf fareConf);
    
    
    
}
