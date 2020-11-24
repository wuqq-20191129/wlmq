/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.ecpmcs.service.impl;

import com.goldsign.csfrm.service.impl.BaseService;
import com.goldsign.ecpmcs.dao.IParamDao;
import com.goldsign.ecpmcs.dao.impl.ParamDao;
import com.goldsign.ecpmcs.service.IParamService;
import com.goldsign.ecpmcs.vo.CardTypeVo;
import com.goldsign.ecpmcs.vo.PubFlagVo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lenovo
 */
public class ParamService extends BaseService implements IParamService{

    private IParamDao paramDao;
    
    public ParamService(){
        paramDao = new ParamDao();
    }

    @Override
    public List<Object[]> getCardType() {
        
        List<CardTypeVo> cardTypeVoRets = paramDao.getCardTypeVos();
        
        List<Object[]> cardTypeRets = new ArrayList<Object[]>();
        for(CardTypeVo cardTypeVoRet: cardTypeVoRets){
            Object[] cardTypeRet = new Object[]{
                cardTypeVoRet.getCardMainId(), cardTypeVoRet.getCardMainName(), 
                cardTypeVoRet.getCardSubId(), cardTypeVoRet.getCardSubName()
            };
            cardTypeRets.add(cardTypeRet);
        }
        
        return cardTypeRets;
    }

    @Override
    public List<Object[]> getIdentifyType() {
        List<PubFlagVo> pubFlagVoRets = paramDao.getIdentifyTypeVos();
        
        List<Object[]> pubFlagRets = new ArrayList<Object[]>();
        for(PubFlagVo pubFlagVoRet: pubFlagVoRets){
            Object[] pubFlagRet = new Object[]{
                pubFlagVoRet.getCode(), pubFlagVoRet.getCodeText()};
            pubFlagRets.add(pubFlagRet);
        }
        
        return pubFlagRets;
    }

}
