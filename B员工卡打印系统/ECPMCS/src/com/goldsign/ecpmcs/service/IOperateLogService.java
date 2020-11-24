/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.ecpmcs.service;

import com.goldsign.csfrm.service.IBaseService;
import com.goldsign.ecpmcs.vo.OperateLogParam;
import com.goldsign.ecpmcs.vo.OperateLogVo;
import java.util.List;

/**
 *
 * @author lenovo
 */
public interface IOperateLogService extends IBaseService{

    //查询操作日志
    List<Object[]> getOperaterLogs(OperateLogParam operateLogParam);
    
    /**
     * 插入操作日志
     * @param vo
     */
    void insertOperaterLogs(OperateLogVo vo);
}
