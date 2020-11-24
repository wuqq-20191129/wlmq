/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.mapper;

import com.goldsign.acc.app.prminfo.entity.PrmVersion;
import java.util.List;

/**
 *
 * @author mqf
 */
public interface FareSubmitMapper {


    public List<PrmVersion> selectPrmVersionForDraft(String parmTypeId);
            
            
    public int submitToOldFlagForFareZone(PrmVersion prmVersion);
    
    public int submitToOldFlagForFareTable(PrmVersion prmVersion);
    
    public int submitToOldFlagForHolidayPara(PrmVersion prmVersion);
    
    public int submitToOldFlagForOffPeakHours(PrmVersion prmVersion);
    
    public int submitToOldFlagForFareConf(PrmVersion prmVersion);
    
    public int submitToOldFlagForFareTimeInterval(PrmVersion prmVersion);
    
    public int submitToOldFlagForFareDealToal(PrmVersion prmVersion);
            
    
    

    public int submitFromDraftToCurOrFurForFareZone(PrmVersion prmVersion);
    
    public int submitFromDraftToCurOrFurForFareTable(PrmVersion prmVersion);
    
    public int submitFromDraftToCurOrFurForHolidayPara(PrmVersion prmVersion);
    
    public int submitFromDraftToCurOrFurForOffPeakHours(PrmVersion prmVersion);
    
    public int submitFromDraftToCurOrFurForFareConf(PrmVersion prmVersion);
    
    public int submitFromDraftToCurOrFurForFareTimeInterval(PrmVersion prmVersion);
    
    public int submitFromDraftToCurOrFurForFareDealToal(PrmVersion prmVersion);
    

}
