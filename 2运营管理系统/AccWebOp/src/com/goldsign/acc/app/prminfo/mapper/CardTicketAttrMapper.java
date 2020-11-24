/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.mapper;

import com.goldsign.acc.app.prminfo.entity.CardTicketAttr;
import java.util.List;
import java.util.Map;

/**
 *
 * @author luck
 */
public interface CardTicketAttrMapper {

    public List<CardTicketAttr> getCardTicketAttrs(CardTicketAttr cardTicketAttr);

    public List<CardTicketAttr> getCardTicketAttrsBySubId(CardTicketAttr cardTicketAttr);

    public List<CardTicketAttr> getCardTicketAttrById(CardTicketAttr cardTicketAttr);

    public int addCardTicketAttr(CardTicketAttr cardTicketAttr);

    public int deleteCardTicketAttr(CardTicketAttr cardTicketAttr);

    public int modifyCardTicketAttr(CardTicketAttr cardTicketAttr);

    public int submitToOldFlag(CardTicketAttr cardTicketAttr);

    public int submitFromDraftToCurOrFur(CardTicketAttr cardTicketAttr);

    public int deleteCardTicketAttrsForClone(CardTicketAttr cardTicketAttr);
    
    public int cloneFromCurOrFurToDraft(CardTicketAttr cardTicketAttr);

    public List<Map> queryToMap(CardTicketAttr queryCondition);

}
