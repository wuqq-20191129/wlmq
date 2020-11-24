/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.mapper;

import com.goldsign.acc.app.prminfo.entity.CardAttribute;
import java.util.List;
import java.util.Map;

/**
 *
 * @author luck
 */
public interface CardAttributeMapper {

    public List<CardAttribute> getCardAttributesBySubId(CardAttribute cardAttribute);

    public List<CardAttribute> getCardAttributes(CardAttribute cardAttribute);

    public List<CardAttribute> getCardAttributeById(CardAttribute cardAttribute);
    
    public int addCardAttributes(CardAttribute cardAttribute);
    
    public int deleteCardAttributes(CardAttribute cardAttribute);
    
    public int modifyCardAttribute(CardAttribute cardAttribute);
    
    public int submitToOldFlag(CardAttribute cardAttribute);
    
    public int submitFromDraftToCurOrFur(CardAttribute cardAttribute);
    
    public int deleteCardAttributesForClone(CardAttribute cardAttribute);
    
    public int cloneFromCurOrFurToDraft(CardAttribute cardAttribute);

    public List<Map> queryToMap(CardAttribute queryCondition);
}
