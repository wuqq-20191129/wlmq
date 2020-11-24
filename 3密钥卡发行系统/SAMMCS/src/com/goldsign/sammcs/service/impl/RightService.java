/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.sammcs.service.impl;

import com.goldsign.csfrm.service.impl.BaseService;
import com.goldsign.csfrm.vo.SysModuleVo;
import com.goldsign.csfrm.vo.SysUserVo;
import com.goldsign.sammcs.dao.IMakeCardDao;
import com.goldsign.sammcs.dao.impl.MakeCardDao;
import com.goldsign.sammcs.service.IRightService;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lenovo
 */
public class RightService extends BaseService implements IRightService{
    
    private IMakeCardDao makeCardDao;
    public RightService(){
        makeCardDao = new MakeCardDao();
    }
    
    @Override
    public List<SysModuleVo> getSysModules(SysUserVo sysUserVo) {
        
        List<SysModuleVo> modules = new ArrayList<SysModuleVo>();
        List<SysModuleVo> modulesTmp = sysUserVo.getSysModuleVos();
        
        for(int i=0; i<modulesTmp.size(); i++){
            if(modulesTmp.get(i).getModuleLevel().equals("1")){
                SysModuleVo firstModule = 
                        SysModuleVo.createOneLevelModule(modulesTmp.get(i).getModuleId(), modulesTmp.get(i).getName());
                modules.add(firstModule);
            }
            if(modulesTmp.get(i).getModuleLevel().equals("2")){
                SysModuleVo secondModule = SysModuleVo.createTwoLevelModule(modulesTmp.get(i).getModuleId(), modulesTmp.get(i).getParentId(),
                        modulesTmp.get(i).getName(),  modulesTmp.get(i).getHandleClassName());
                modules.add(secondModule);
            }
        }
            
        return modules;
    }

    @Override
    public boolean getDeviceConfigureCheck(String ip, String deviceType, String deviceNo) {
        boolean flag = false;
        flag = makeCardDao.isDeviceConfigureRight(ip, deviceType, deviceNo);
        return flag;
    }
}
