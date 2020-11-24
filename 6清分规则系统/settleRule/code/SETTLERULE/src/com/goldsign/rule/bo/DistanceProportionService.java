/*
 * 文件名：DistanceProportionService
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.rule.bo;

import com.goldsign.rule.dao.DistanceProportionDao;
import com.goldsign.rule.vo.DistanceProportionVo;
import com.goldsign.rule.vo.OperResult;
import java.util.Vector;


/*
 * 线路里程权重比例
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-11-7
 */

public class DistanceProportionService {
    
    DistanceProportionDao dao = new DistanceProportionDao();

    /**
     * 查询
     * @param vo
     * @return
     * @throws Exception 
     */
    public Vector query(DistanceProportionVo vo) throws Exception {
        return dao.select(vo);
    }

    /**
     * 实时查询
     * @param vo
     * @return 
     */
    public OperResult queryStore(DistanceProportionVo vo) throws Exception {
        return dao.queryStore(vo);
    }

}
