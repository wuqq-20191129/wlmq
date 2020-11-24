/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.sammcs.service.impl;

import com.goldsign.csfrm.service.impl.BaseService;
import com.goldsign.sammcs.dao.IOperateLogDao;
import com.goldsign.sammcs.dao.impl.OperateLogDao;
import com.goldsign.sammcs.service.IOperateLogService;
import com.goldsign.sammcs.vo.OperateLogVo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lenovo
 */
public class OperateLogService extends BaseService implements IOperateLogService{

    private IOperateLogDao operateLogDao;
    
    public OperateLogService(){
        operateLogDao = new OperateLogDao();
    }


    @Override
    public void insertOperaterLogs(OperateLogVo vo) {
        operateLogDao.insertOperaterLogs(vo);
    }
}
