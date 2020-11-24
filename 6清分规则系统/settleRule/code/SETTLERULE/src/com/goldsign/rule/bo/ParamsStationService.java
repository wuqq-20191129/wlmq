/*
 * 文件名：PramsStationService
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.rule.bo;

import com.goldsign.rule.dao.ParamsStationDao;
import com.goldsign.rule.vo.OperResult;
import com.goldsign.rule.vo.ParamsStationVo;
import java.util.Vector;


/*
 * 清分规则系统 参数设置服务层
 * @author     wangkejia
 * @version    V1.0
 */

public class ParamsStationService {
    
    public ParamsStationService(){
        super();
    }
    
   ParamsStationDao  paramsStationDao = new ParamsStationDao();
    
    //查询
    public Vector query(ParamsStationVo vo) throws Exception {
      return paramsStationDao.select(vo);
    }
    
    //增加
   public OperResult insert(ParamsStationVo vo,String operatorID) throws Exception {
        return paramsStationDao.insert(vo,operatorID);
    }
    
    //删除
    public int remove(Vector keyIDs) throws Exception {
      return paramsStationDao.remove(keyIDs);
    }

    
    //修改
   public OperResult update(Vector keyIDs,ParamsStationVo vo,String operatorID) throws Exception {
       return paramsStationDao.update(keyIDs,vo,operatorID);
   }
   
   
   //审核
   public OperResult audit(Vector keyIDs,String operatorID) throws Exception {
       return paramsStationDao.audit(keyIDs,operatorID);
   }

    
}
