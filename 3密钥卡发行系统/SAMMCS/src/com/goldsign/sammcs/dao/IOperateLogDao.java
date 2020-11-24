/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.sammcs.dao;

import com.goldsign.csfrm.dao.IBaseDao;
import com.goldsign.sammcs.vo.OperateLogVo;
import java.util.List;

/**
 *
 * @author lenovo
 */
public interface IOperateLogDao extends IBaseDao{


    //插入操作日志
    public void insertOperaterLogs(OperateLogVo vo);

}
