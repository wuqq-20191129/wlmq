/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.mapper;

import com.goldsign.acc.app.prminfo.entity.FareTable;
import java.util.List;

/**
 *
 * @author mqf
 */
public interface FareTableMapper {

    public List<FareTable> getFareTables(FareTable fareTable);

    public int addFareTable(FareTable fareTable);

    public int modifyFareTable(FareTable fareTable);

    public List<FareTable> getFareTableById(FareTable fareTable);

    public int deleteFareTable(FareTable fareTable);

    public int submitToOldFlag(FareTable fareTable);

    public int submitFromDraftToCurOrFur(FareTable fareTable);

    public int deleteFareTablesForClone(FareTable fareTable);

    public int cloneFromCurOrFurToDraft(FareTable fareTable);
}
