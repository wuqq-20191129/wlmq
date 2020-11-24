/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.mapper;

import com.goldsign.acc.app.prminfo.entity.FareName;
import com.goldsign.acc.app.prminfo.entity.FareZone;
import java.util.List;

/**
 *
 * @author mqf
 */
public interface FareNameMapper {

    public List<FareName> getFareNames();

    public int addFareName(FareName fareName);

    public int modifyFareName(FareName fareName);

    public List<FareName> getFareNameById(FareName fareName);
    
    public List<FareName> getFareNameByName(FareName fareName);
    
    public List<FareName> getFareNameByNameForModify(FareName fareName);

    public int deleteFareName(FareName fareName);
    
    public int getFareZoneCount(List<FareName> fareNames);
    
    public int getFareTableCount(List<FareName> fareNames);

}
