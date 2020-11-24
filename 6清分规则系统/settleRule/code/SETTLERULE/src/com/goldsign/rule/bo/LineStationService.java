/*
 * 文件名：LineStationService
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.rule.bo;

import com.goldsign.rule.dao.LineStationDao;
import com.goldsign.rule.vo.LineStationVo;
import java.util.Vector;


/*
 * 清分规则系统 参数设置服务层
 * @author     wangkejia
 * @version    V1.0
 */

public class LineStationService {
    
    public LineStationService(){
        super();
    }
    
    LineStationDao  lineStationDao = new LineStationDao();
    
    //查询
    public Vector query(LineStationVo vo) throws Exception {
      return lineStationDao.select(vo);
    }
    
}
