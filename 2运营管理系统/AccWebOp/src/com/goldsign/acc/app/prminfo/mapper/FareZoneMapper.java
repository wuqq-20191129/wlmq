/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.mapper;

import com.goldsign.acc.app.prminfo.mapper.*;
import com.goldsign.acc.app.prminfo.entity.FareZone;
import java.util.List;

/**
 *
 * @author mqf
 */
public interface FareZoneMapper {

    public List<FareZone> getFareZones(FareZone fareZone);

    public int addFareZone(FareZone fareZone);

    public int modifyFareZone(FareZone fareZone);

    public List<FareZone> getFareZoneById(FareZone fareZone);

    public int deleteFareZone(FareZone fareZone);

    public int submitToOldFlag(FareZone fareZone);

    public int submitFromDraftToCurOrFur(FareZone fareZone);

    public int deleteFareZonesForClone(FareZone fareZone);

    public int cloneFromCurOrFurToDraft(FareZone fareZone);
}
