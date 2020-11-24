/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.sammcs.service;

import com.goldsign.csfrm.service.IBaseService;
import com.goldsign.sammcs.vo.OperateLogVo;
import java.util.List;

/**
 *
 * @author lenovo
 */
public interface IOperateLogService extends IBaseService{

    //插入操作日志
    /**
     *
     * @param vo
     */
    void insertOperaterLogs(OperateLogVo vo);
}
