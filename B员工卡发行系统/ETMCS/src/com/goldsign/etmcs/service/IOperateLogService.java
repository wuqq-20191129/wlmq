/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.etmcs.service;

import com.goldsign.csfrm.service.IBaseService;
import com.goldsign.etmcs.vo.OperateLogParam;
import com.goldsign.etmcs.vo.OperateLogVo;
import java.util.List;

/**
 *
 * @author lenovo
 */
public interface IOperateLogService extends IBaseService{

    //查询操作日志
    List<Object[]> getOperaterLogs(OperateLogParam operateLogParam);
    
    //插入操作日志
    /**
     *
     * @param vo
     */
    void insertOperaterLogs(OperateLogVo vo);
}
