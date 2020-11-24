/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.ecpmcs.dao;

import com.goldsign.csfrm.dao.IBaseDao;
import com.goldsign.ecpmcs.vo.OperateLogParam;
import com.goldsign.ecpmcs.vo.OperateLogVo;
import java.util.List;

/**
 *
 * @author lenovo
 */
public interface IOperateLogDao extends IBaseDao{

    //查询
    List<OperateLogVo> getOperaterLogs(OperateLogParam operateLogParam);

    //插入操作日志
    public void insertOperaterLogs(OperateLogVo vo);

}
