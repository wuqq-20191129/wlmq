/*
 * 文件名：IPrintService
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.ecpmcs.service;

import com.goldsign.csfrm.service.IBaseService;
import com.goldsign.ecpmcs.vo.SignCardPrintVo;
import java.util.List;


/*
 * 〈打印记录服务〉
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2014-4-10
 */

public interface IPrintService extends IBaseService{
    
    Boolean insertPrint(SignCardPrintVo vo);
    
    List<Object[]> getPrintListAll(SignCardPrintVo SignCardPrintParam);
    
    List<Object[]> getPrintList(SignCardPrintVo SignCardPrintParam);

    List<Object[]> getCountList(SignCardPrintVo SignCardPrintParam);
}
