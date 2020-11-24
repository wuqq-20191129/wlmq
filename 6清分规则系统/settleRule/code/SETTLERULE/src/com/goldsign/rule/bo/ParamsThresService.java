/*
 * 文件名：ParamsThresService
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.rule.bo;

import com.goldsign.rule.dao.ParamsThresDao;
import com.goldsign.rule.vo.OperResult;
import com.goldsign.rule.vo.ParamsThresVo;
import java.util.Vector;


/*
 * 阀值参数配置 
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-11-25
 */

public class ParamsThresService {
    
    ParamsThresDao dao = new ParamsThresDao();
    
    /**
     * 查询
     * @param vo
     * @return
     * @throws Exception 
     */
    public Vector query(ParamsThresVo vo) throws Exception {
        return dao.select(vo);
    }
    
    //增加
    public OperResult insert(ParamsThresVo vo) throws Exception {
        return dao.insert(vo);
    }
    
    //删除
    public int remove(Vector keyIDs) throws Exception {
        return dao.remove(keyIDs);
    }
    
    //修改
    public OperResult update(ParamsThresVo vo) throws Exception {
        return dao.update(vo);
    }
    
    //审核
    public OperResult audit(ParamsThresVo vo) throws Exception {
        return dao.auditUpdate(vo);
    }

}
