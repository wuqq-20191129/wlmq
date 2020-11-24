/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.mapper;

import com.goldsign.acc.app.prminfo.entity.DealAssignLine;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author liudz
 */
public interface DealAssignLineMapper {


    public List<DealAssignLine> getDealAssignLine(DealAssignLine dealAssignLine);

    public int addDealAssignLine(DealAssignLine dealAssignLine);

    public int modifyDealAssignLine(DealAssignLine dealAssignLine);

    public List<DealAssignLine> getDealAssignLineById(DealAssignLine dealAssignLine);

    public int deleteDealAssignLine(DealAssignLine dealAssignLine);

    public int submitToOldFlag(DealAssignLine dealAssignLine);

    public int submitFromDraftToCurOrFur(DealAssignLine dealAssignLine);

    public int deleteDealAssignLineForClone(DealAssignLine dealAssignLine);
    
    public int cloneFromCurOrFurToDraft(DealAssignLine dealAssignLine);
    
    public Vector<String> getLines();
    
    public Vector<String> getStations();


    public int deleteVersionRecordWithTran(String tableName, String versonNo);


}
