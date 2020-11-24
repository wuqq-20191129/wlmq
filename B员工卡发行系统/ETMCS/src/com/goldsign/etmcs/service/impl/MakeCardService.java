/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.etmcs.service.impl;

import com.goldsign.csfrm.service.impl.BaseService;
import com.goldsign.etmcs.dao.IMakeCardDao;
import com.goldsign.etmcs.dao.impl.MakeCardDao;
import com.goldsign.etmcs.service.IMakeCardService;
import com.goldsign.etmcs.vo.MakeCardParam;
import com.goldsign.etmcs.vo.MakeCardVo;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lenovo
 */
public class MakeCardService extends BaseService implements IMakeCardService{

    private IMakeCardDao makeCardDao;
    
    public MakeCardService(){
        makeCardDao = new MakeCardDao();
    }

    @Override
    public List<Object[]> getMakeCards(MakeCardParam makeCardParam) {
        
        List<MakeCardVo> makeCardVoRets = makeCardDao.getMakeCards(makeCardParam);
        
        List<Object[]> makeCardRets = new ArrayList<Object[]>();
        for(MakeCardVo makeCardVoRet: makeCardVoRets){
            Object[] makeCardRet = new Object[]{
                makeCardVoRet.getEmployeeId(), makeCardVoRet.getEmployeeName(), 
                makeCardVoRet.getGenderDesc(), makeCardVoRet.getLogicId(),
                makeCardVoRet.getMakeOper(), makeCardVoRet.getMakeTime(),
                makeCardVoRet.getUseStateDesc(),
                makeCardVoRet.getEmployeeDepartmentTxt(),
                makeCardVoRet.getEmployeePositionsTxt(),
                makeCardVoRet.getEmployeeClassTxt(),
            };
            makeCardRets.add(makeCardRet);
        }
        
        return makeCardRets;
    }
    
    /*
     * 按工号查询员工发卡信息
     */
    @Override
    public MakeCardVo getMakeCardsByEmployeeId(MakeCardVo vo){
        return makeCardDao.getMakeCardsByEmployeeId(vo);
    }

    @Override
    public int isExistsMakeCard(MakeCardVo vo) {
        return makeCardDao.isExistsMakeCard(vo);
    }
    
    @Override
    public MakeCardVo getEmployeeInfoService(MakeCardVo vo) {
        return makeCardDao.getEmployeeInfo(vo);
    }

    @Override
    public Boolean writeMakeCard(MakeCardVo vo) {
        return makeCardDao.writeMakeCard(vo);
    }

    @Override
    public Boolean writeReturnCard(MakeCardVo vo) {
        return makeCardDao.writeReturnCard(vo);
    }

    @Override
    public List<Object[]> getMakeCardsCount(MakeCardParam makeCardParam) {
        List<Map<String,String>> maps = makeCardDao.getMakeCardsCount(makeCardParam);
        
        List<Object[]> makeCardRets = new ArrayList<Object[]>();
        for(Map<String,String> map: maps){
            Object[] makeCardRet = new Object[]{ makeCardParam.getBeginDate(),makeCardParam.getEndDate()
                                                ,map.get("useStateDesc"),map.get("count")};
            makeCardRets.add(makeCardRet);
        }
        
        return makeCardRets;
    }

    @Override
    public File writeLocalFile(MakeCardVo vo) throws IOException {
        return makeCardDao.writeLocalFile(vo);
    }

    @Override
    public Boolean readLocalFile(File file) throws IOException {
        return makeCardDao.readLocalFile(file);
    }
    
    @Override
    public Boolean editCard(MakeCardVo vo) {
        return makeCardDao.editCard(vo);
    }
    
    @Override
    public Boolean isCardReturned(MakeCardVo vo) {
        return makeCardDao.isCardReturned(vo);
    }
}
