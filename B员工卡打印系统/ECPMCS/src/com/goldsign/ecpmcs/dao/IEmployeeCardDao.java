/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.ecpmcs.dao;

import com.goldsign.csfrm.dao.IBaseDao;
import com.goldsign.ecpmcs.vo.AnalyzeVo;
import com.goldsign.ecpmcs.vo.EmployeeCardVo;
import java.util.List;

/**
 *
 * @author lind
 */
public interface IEmployeeCardDao extends IBaseDao{
    
    List<EmployeeCardVo> getEmployeeCardVos(EmployeeCardVo employeeCardVo);

    public boolean getEmployeeCardVo(AnalyzeVo analyzeVo);
    
}
