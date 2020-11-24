/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.ecpmcs.dao;

import com.goldsign.csfrm.dao.IBaseDao;
import com.goldsign.ecpmcs.vo.SignCardPrintVo;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface IEmployeePrintDao extends IBaseDao{
    
    List<SignCardPrintVo> getPrintList(SignCardPrintVo signCardPrintParam);
    
    List<SignCardPrintVo> countPrintList(SignCardPrintVo signCardPrintParam);

    Boolean insertPrint(SignCardPrintVo vo);
    
    String getEmployeeClass(String logicNo);
}
