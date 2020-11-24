/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.service.impl;

import com.goldsign.csfrm.service.impl.BaseService;
import com.goldsign.csfrm.vo.SysModuleVo;
import com.goldsign.csfrm.vo.SysUserVo;
import com.goldsign.esmcs.env.CommuConstant;
import com.goldsign.esmcs.service.IRightService;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lenovo
 */
public class RightService extends BaseService implements IRightService{

    /**
     * 取系统模块资源
     * 
     * @param sysUserVo
     * @return 
     */
    @Override
    public List<SysModuleVo> getSysModules(SysUserVo sysUserVo) {
        
        List<SysModuleVo> modules = new ArrayList<SysModuleVo>();
        
        String employeeLevel = sysUserVo.getEmployeeLevel();
        
        SysModuleVo firstModule01 = SysModuleVo.createOneLevelModule("71", "制卡服务");
        if(CommuConstant.OPER_TYPE_ADMIN.equals(employeeLevel)
                || CommuConstant.OPER_TYPE_COMM.equals(employeeLevel)
                || CommuConstant.OPER_TYPE_MEMD.equals(employeeLevel)){
            modules.add(firstModule01);
        }
        SysModuleVo secondModule0105 = SysModuleVo.createTwoLevelModule("9102", "71", "票卡分析", 
                "com.goldsign.esmcs.ui.panel.ReadCardPanel");
        if(CommuConstant.OPER_TYPE_ADMIN.equals(employeeLevel)){
            modules.add(secondModule0105);
        }
        SysModuleVo firstModule02 = SysModuleVo.createOneLevelModule("72", "报表统计");
        if(CommuConstant.OPER_TYPE_ADMIN.equals(employeeLevel)
                || CommuConstant.OPER_TYPE_COMM.equals(employeeLevel)){
            modules.add(firstModule02);
        }
        SysModuleVo secondModule0201 = SysModuleVo.createTwoLevelModule("7201", "72", "制卡查询", 
                "com.goldsign.esmcs.ui.panel.MadeCardQueryPanel");
        if(CommuConstant.OPER_TYPE_ADMIN.equals(employeeLevel)
                || CommuConstant.OPER_TYPE_COMM.equals(employeeLevel)){
            modules.add(secondModule0201);
        }
        SysModuleVo secondModule0202 = SysModuleVo.createTwoLevelModule("7202", "72", "文件通知", 
                "com.goldsign.esmcs.ui.panel.EsFileNoticePanel");
        if(CommuConstant.OPER_TYPE_ADMIN.equals(employeeLevel)
                || CommuConstant.OPER_TYPE_COMM.equals(employeeLevel)){
            modules.add(secondModule0202);
        }
        SysModuleVo firstModule03 = SysModuleVo.createOneLevelModule("73", "本地参数");
        if(CommuConstant.OPER_TYPE_ADMIN.equals(employeeLevel)){
            modules.add(firstModule03);
        }
        
        SysModuleVo secondModule0301 = SysModuleVo.createTwoLevelModule("7301", "73", "参数查询", 
                "com.goldsign.esmcs.ui.panel.ParamQueryPanel");
        if(CommuConstant.OPER_TYPE_ADMIN.equals(employeeLevel)){
            modules.add(secondModule0301);
        }
        SysModuleVo secondModule0302 = SysModuleVo.createTwoLevelModule("7302", "73", "票价查询", 
                "com.goldsign.esmcs.ui.panel.TicketPriceQueryPanel");
        if(CommuConstant.OPER_TYPE_ADMIN.equals(employeeLevel)){
            modules.add(secondModule0302);
        }
        SysModuleVo secondModule0303 = SysModuleVo.createTwoLevelModule("7303", "73", "票种查询", 
                "com.goldsign.esmcs.ui.panel.TicketTypeQueryPanel");
        if(CommuConstant.OPER_TYPE_ADMIN.equals(employeeLevel)){
            modules.add(secondModule0303);
        }
        SysModuleVo secondModule0304 = SysModuleVo.createTwoLevelModule("7304", "73", "记名卡查询", 
                "com.goldsign.esmcs.ui.panel.SignCardQueryPanel");
        if(CommuConstant.OPER_TYPE_ADMIN.equals(employeeLevel)){
            modules.add(secondModule0304);
        }
        SysModuleVo secondModule0305 = SysModuleVo.createTwoLevelModule("7305", "73", "黑名单查询", 
                "com.goldsign.esmcs.ui.panel.BlacklistQueryPanel");
        if(CommuConstant.OPER_TYPE_ADMIN.equals(employeeLevel)){
            modules.add(secondModule0305);
        }
        SysModuleVo secondModule0306 = SysModuleVo.createTwoLevelModule("7306", "73", "SAM卡查询", 
                "com.goldsign.esmcs.ui.panel.SamCardQueryPanel");
        if(CommuConstant.OPER_TYPE_ADMIN.equals(employeeLevel)){
            modules.add(secondModule0306);
        }
        SysModuleVo secondModule0307 = SysModuleVo.createTwoLevelModule("7307", "73", "审计文件", 
                "com.goldsign.esmcs.ui.panel.AuditFileQueryPanel");
        if(CommuConstant.OPER_TYPE_ADMIN.equals(employeeLevel)){
            modules.add(secondModule0307);
        }
        return modules;
    }

}
