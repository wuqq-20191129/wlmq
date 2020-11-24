/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.mapper;

import com.goldsign.acc.app.prminfo.entity.CardMain;
import java.util.List;
import java.util.Map;

/**
 *
 * @author luck
 */
public interface CardMainMapper {

    public List<CardMain> getCardMains(CardMain cardMain);

    public int addCardMain(CardMain cardMain);

    public List<CardMain> getCardMainById(CardMain cardMain);

    public List<CardMain> getCardMainByName(CardMain cardMain);

    public int deleteCardMain(CardMain cardMain);
    
    public int modifyCardMain(CardMain cardMain);
     
    public int deleteCardMainForClone(CardMain cardMain);
    
    public int cloneFromCurOrFurToDraft(CardMain cardMain);
    
    public int submitToOldFlag(CardMain cardMain);
    
    public int submitFromDraftToCurOrFur(CardMain cardMain);

    public List<Map> queryToMap(CardMain queryCondition);
}
