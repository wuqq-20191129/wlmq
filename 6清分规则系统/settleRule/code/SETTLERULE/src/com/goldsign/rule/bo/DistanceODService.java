/*
 * 文件名：DistanceODService
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.rule.bo;

import com.goldsign.rule.dao.DistanceODDao;
import com.goldsign.rule.vo.DistanceODVo;
import java.util.Vector;


/*
 * 路径查询 
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-11-11
 */

public class DistanceODService {
    
    DistanceODDao dao = new DistanceODDao();
    
    /**
     * 查询
     * @param vo
     * @return
     * @throws Exception 
     */
    public Vector query(DistanceODVo vo) throws Exception {
        return dao.select(vo);
    }

}
