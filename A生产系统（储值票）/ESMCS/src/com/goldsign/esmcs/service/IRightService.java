/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.service;

import com.goldsign.csfrm.service.IBaseService;
import com.goldsign.csfrm.vo.SysModuleVo;
import com.goldsign.csfrm.vo.SysUserVo;
import java.util.List;

/**
 *
 * @author lenovo
 */
public interface IRightService extends IBaseService{

    /**
     * 取系统模块
     * 
     * @param sysUserVo
     * @return 
     */
    List<SysModuleVo> getSysModules(SysUserVo sysUserVo);
}
