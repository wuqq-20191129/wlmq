/*
 * 文件名：IPhyLogicDao
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.esmcs.dao;

import com.goldsign.csfrm.dao.IBaseDao;
import java.util.Map;


/*
 * 物理卡号逻辑卡号对照DAO接口类
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-9-16
 */

public interface IPhyLogicDao extends IBaseDao{
    
    /**
     * 通过物理卡号查询逻辑卡号
     * @param phyId
     * @return 
     */
    String findLogicId(String phyId) throws Exception; 
    
    /**
     * 取物理对照表
     * 
     * @return
     * @throws Exception 
     */
    Map<String, String> getPhyLogicMap() throws Exception;
    
}
