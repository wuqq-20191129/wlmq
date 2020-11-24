/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.mapper;

import com.goldsign.acc.app.prminfo.entity.DealAssignContc;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author liudz
 */
public interface DealAssignContcMapper {

    public List<DealAssignContc> getDealAssignContc(DealAssignContc dealAssigncontc);

    public int addDealAssignContc(DealAssignContc dealAssigncontc);

    public int modifyDealAssignContc(DealAssignContc dealAssigncontc);

    public List<DealAssignContc> getDealAssignContcById(DealAssignContc dealAssigncontc);

    public int deleteDealAssignContc(DealAssignContc dealAssigncontc);

    public int submitToOldFlag(DealAssignContc dealAssigncontc);

    public int submitFromDraftToCurOrFur(DealAssignContc dealAssigncontc);

    public int deleteDealAssignContcForClone(DealAssignContc dealAssigncontc);

    public int cloneFromCurOrFurToDraft(DealAssignContc dealAssigncontc);

    public Vector<String> getLines();

    public Vector<String> getContcs();
}
