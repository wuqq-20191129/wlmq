/*
 * 文件名：ParamsService
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.rule.bo;

import com.goldsign.rule.dao.ParamsDao;
import com.goldsign.rule.vo.OperResult;
import com.goldsign.rule.vo.ParamsVo;
import java.util.Vector;


/*
 * 清分规则系统 参数设置服务层
 * @author     wangkejia
 * @version    V1.0
 */

public class ParamsService {
    
    public ParamsService(){
        super();
    }
    
    ParamsDao  paramseDao = new ParamsDao();
    
    //查询
    public Vector query(ParamsVo vo) throws Exception {
      return paramseDao.select(vo);
    }
    
    //增加
   public OperResult insert(ParamsVo vo,String operatorID) throws Exception {
        return paramseDao.insert(vo,operatorID);
    }
    
    //删除
    public int remove(Vector keyIDs) throws Exception {
      return paramseDao.remove(keyIDs);
    }

    
    //修改
   public OperResult update(Vector keyIDs,ParamsVo vo,String operatorID) throws Exception {
       return paramseDao.update(keyIDs,vo,operatorID);
   }
   
   
   //审核
   public OperResult audit(Vector keyIDs,String operatorID) throws Exception {
       return paramseDao.audit(keyIDs,operatorID);
   }

    
}
