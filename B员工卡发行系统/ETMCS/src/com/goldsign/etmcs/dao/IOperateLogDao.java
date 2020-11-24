/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.etmcs.dao;

import com.goldsign.csfrm.dao.IBaseDao;
import com.goldsign.etmcs.vo.OperateLogParam;
import com.goldsign.etmcs.vo.OperateLogVo;
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
