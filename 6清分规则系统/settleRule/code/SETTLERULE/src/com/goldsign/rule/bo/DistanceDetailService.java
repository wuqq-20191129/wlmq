/*
 * 文件名：DistanceDetailService
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.rule.bo;

import com.goldsign.rule.dao.DistanceDetailDao;
import com.goldsign.rule.vo.DistanceChangeVo;
import java.util.Vector;


/*
 * 路径详细查询 
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-11-11
 */

public class DistanceDetailService {
    
    DistanceDetailDao dao = new DistanceDetailDao();
    
    /**
     * 查询
     * @param vo
     * @return
     * @throws Exception 
     */
    public Vector query(DistanceChangeVo vo) throws Exception {
        return dao.select(vo);
    }

}
