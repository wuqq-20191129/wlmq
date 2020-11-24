/*
 * 文件名：IPrintService
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.ecpmcs.service;

import com.goldsign.csfrm.service.IBaseService;
import com.goldsign.ecpmcs.vo.SignCardPrintVo;
import java.util.List;


/*
 * 〈员工卡打印记录查询〉
 * @author     lindaquan
 * @version    V1.1
 * @createTime 2014-12-18
 */

public interface IEmployeePrintService extends IBaseService{
    
    Boolean insertPrint(SignCardPrintVo vo);
    
    List<Object[]> getPrintListAll(SignCardPrintVo SignCardPrintParam);
    
    List<Object[]> getPrintList(SignCardPrintVo SignCardPrintParam);

    List<Object[]> getCountList(SignCardPrintVo SignCardPrintParam);
    
    String getEmployeeClassByLogicNo(String logicNo);
}
