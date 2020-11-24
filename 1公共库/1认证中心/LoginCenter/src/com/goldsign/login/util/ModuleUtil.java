/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.login.util;

import com.goldsign.login.vo.ModuleDistrVo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mh
 */
public class ModuleUtil {

    public static List<ModuleDistrVo> getTopMenu(List<ModuleDistrVo> moduleDistrVoList, String sysFlag) {
        List<ModuleDistrVo> returnList = new ArrayList<ModuleDistrVo>();
        if (moduleDistrVoList != null) {
            for (ModuleDistrVo moduleDistrVo : moduleDistrVoList) {
                if (moduleDistrVo.getSysFlag().equals(sysFlag)) {
                    if (moduleDistrVo.getTopMenuId().equals("0")) {
                        returnList.add(moduleDistrVo);
                    }
                }
            }
        }
        return returnList;
    }

//    getUserMenu
    public static List<ModuleDistrVo> getUserMenu(List<ModuleDistrVo> moduleDistrVoList, String topMenuId, String sysFlag) {
        List<ModuleDistrVo> returnList = new ArrayList<ModuleDistrVo>();
        if (moduleDistrVoList != null) {
            for (ModuleDistrVo moduleDistrVo : moduleDistrVoList) {
                if (moduleDistrVo.getSysFlag().equals(sysFlag)) {
                    if (moduleDistrVo.getTopMenuId().equals(topMenuId)) {
                        returnList.add(moduleDistrVo);
                    }
                }
            }
        }
        return returnList;
    }
}
