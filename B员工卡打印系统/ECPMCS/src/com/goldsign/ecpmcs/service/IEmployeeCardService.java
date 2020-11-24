/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.ecpmcs.service;

import com.goldsign.csfrm.service.IBaseService;
import com.goldsign.ecpmcs.vo.AnalyzeVo;
import com.goldsign.ecpmcs.vo.EmployeeCardVo;
import java.util.List;

/**
 *
 * @author lind
 */
public interface IEmployeeCardService extends IBaseService {
    
        //查询
        List<Object[]> getEmployeeCardVos(EmployeeCardVo employeeCardVo);
        
        //查询
        boolean getEmployeeCardVo(AnalyzeVo analyzeVo);
    
}
