/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.frame.bo;
import com.goldsign.frame.dao.ModuleDistrDao;
import java.util.Vector;
import com.goldsign.frame.vo.ModuleDistrVo;

/**
 *
 * @author hejj
 */
public class ModuleDistrBo {
    public Vector getModulesByGroupID(String groupID) throws Exception{
    ModuleDistrDao mda = new ModuleDistrDao();
    return mda.getModulesByGroupID(groupID);
  }
  public int distrModules(String groupID,Vector modules) throws Exception{
    ModuleDistrDao mda = new ModuleDistrDao();
    return mda.distrModules(groupID,modules);
  }
  public Vector getModulesByOperator(String operatorID) throws Exception{
    ModuleDistrDao mda = new ModuleDistrDao();
    return mda.getModulesByOperator(operatorID);
  }
  public Vector getThirdModulesByOperator(String operatorID) throws Exception{
    ModuleDistrDao mda = new ModuleDistrDao();
    return mda.getThirdModulesByOperator(operatorID);

  }

    
}
