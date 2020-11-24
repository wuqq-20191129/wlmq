/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.prminfo.mapper;

import com.goldsign.acc.app.prminfo.entity.CardSub;
import java.util.List;
import java.util.Map;

/**
 *
 * @author luck
 */
public interface CardSubMapper {

    public List<CardSub> getCardSubs(CardSub cardSub);

    public List<CardSub> getCardSubByCardMainId(CardSub cardSub);

    public List<CardSub> getCardSubById(CardSub cardSub);

    public List<CardSub> getCardSubByName(CardSub cardSub);

    public int addCardSub(CardSub cardSub);

    public int deleteCardSub(CardSub cardSub);

    public int modifyCardSub(CardSub cardSub);

    public int deleteCardSubForClone(CardSub cardSub);

    public int cloneFromCurOrFurToDraft(CardSub cardSub);

    public int submitToOldFlag(CardSub cardSub);

    public int submitFromDraftToCurOrFur(CardSub cardSub);

    public List<Map> queryToMap(CardSub queryCondition);

    public List<CardSub> getPublisherOnly(CardSub po);
}
