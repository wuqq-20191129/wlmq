/*
 * 文件名：GenerateDataService
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.rule.bo;

import com.goldsign.rule.dao.GenerateDataDao;
import com.goldsign.rule.vo.OperResult;


/*
 * 路径查询 
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-11-11
 */

public class GenerateDataService {
    
    GenerateDataDao dao = new GenerateDataDao();
    
    /**
     * 查询
     * @param vo
     * @return
     * @throws Exception 
     */
    public OperResult generate() throws Exception {
        return dao.generate();
    }

}
