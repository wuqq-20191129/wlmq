/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.service.impl;

import com.goldsign.csfrm.vo.SysModuleVo;
import com.goldsign.csfrm.vo.SysUserVo;
import com.goldsign.esmcs.env.CommuConstant;
import java.util.List;

/** 
 *
 * @author lenovo
 */
public class PkRightService extends RightService{

    /**
     * 取储值票权限
     * 
     * @param sysUserVo
     * @return 
     */
    @Override
    public List<SysModuleVo> getSysModules(SysUserVo sysUserVo) {
        
        List<SysModuleVo> modules = super.getSysModules(sysUserVo);
        
        String employeeLevel = sysUserVo.getEmployeeLevel();
        
        SysModuleVo secondModule0101 = SysModuleVo.createTwoLevelModule("7101", "71", "票卡制作",
                "com.goldsign.esmcs.ui.panel.PKMakeCardPanel");
        if(CommuConstant.OPER_TYPE_ADMIN.equals(employeeLevel)
                || CommuConstant.OPER_TYPE_COMM.equals(employeeLevel)){
            modules.add(secondModule0101);
        }
        SysModuleVo secondModule0102 = SysModuleVo.createTwoLevelModule("7102", "71", "票卡分拣",
                "com.goldsign.esmcs.ui.dialog.PKSortCardDialog");
        if(CommuConstant.OPER_TYPE_ADMIN.equals(employeeLevel)
                || CommuConstant.OPER_TYPE_COMM.equals(employeeLevel)){
            modules.add(secondModule0102);
        }
        SysModuleVo secondModule0103 = SysModuleVo.createTwoLevelModule("7103", "71", "设备诊断",
                "com.goldsign.esmcs.ui.panel.PKDeviceCheckPanel");
        if(CommuConstant.OPER_TYPE_ADMIN.equals(employeeLevel)
                || CommuConstant.OPER_TYPE_MEMD.equals(employeeLevel)){
            modules.add(secondModule0103);
        }
        return modules;
    }

}
