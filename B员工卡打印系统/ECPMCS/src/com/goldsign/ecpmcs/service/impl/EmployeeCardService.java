/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.ecpmcs.service.impl;

import com.goldsign.csfrm.service.impl.BaseService;
import com.goldsign.ecpmcs.dao.IEmployeeCardDao;
import com.goldsign.ecpmcs.dao.impl.EmployeeCardDao;
import com.goldsign.ecpmcs.service.IEmployeeCardService;
import com.goldsign.ecpmcs.vo.AnalyzeVo;
import com.goldsign.ecpmcs.vo.EmployeeCardVo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lind
 */
public class EmployeeCardService extends BaseService implements IEmployeeCardService{
    
    private IEmployeeCardDao employeeCardDao;
    
    public EmployeeCardService(){
        employeeCardDao = new EmployeeCardDao();
    }

    @Override
    public List<Object[]> getEmployeeCardVos(EmployeeCardVo employeeCardVo) {
        List<EmployeeCardVo> makeCardVoRets = employeeCardDao.getEmployeeCardVos(employeeCardVo);
        
        List<Object[]> makeCardRets = new ArrayList<Object[]>();
        for(EmployeeCardVo makeCardVoRet: makeCardVoRets){
            Object[] makeCardRet = new Object[]{
                makeCardVoRet.getEmployeeId(), makeCardVoRet.getEmployeeName(), 
                makeCardVoRet.getGenderDesc(), makeCardVoRet.getLogicId(),
                makeCardVoRet.getUseStateDesc(),makeCardVoRet.getEmployeeDepartmentTxt(),
                makeCardVoRet.getEmployeePositionsTxt(),makeCardVoRet.getEmployeeClassTxt(),
                makeCardVoRet.getImgDir(),
                makeCardVoRet.getMakeOper(), makeCardVoRet.getMakeTime(),
                makeCardVoRet.getReturnOper(),makeCardVoRet.getReturnTime(),
            };
            makeCardRets.add(makeCardRet);
        }
        
        return makeCardRets;
    }

    @Override
    public boolean getEmployeeCardVo(AnalyzeVo analyzeVo) {
        return employeeCardDao.getEmployeeCardVo(analyzeVo);
    }
    
}
