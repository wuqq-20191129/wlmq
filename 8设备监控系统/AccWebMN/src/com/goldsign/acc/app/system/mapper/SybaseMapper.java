package com.goldsign.acc.app.system.mapper;

import com.goldsign.acc.app.system.entity.Sybase;
import com.goldsign.acc.frame.entity.PubFlag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 *  ORACLE监视
 *
 * @author xiaowu
 */
public interface SybaseMapper {

    public List<Sybase> queryPlan();
    
    public void getDbMessageList(Map resultMap);
    
    public String queryCapacityConfig();
    
    public int updatePlan(Sybase info);
    
    public int insertPlanOne(Sybase info);
    
    public int insertPlanTwo(Sybase info);
    
    public int updatePlanField(Sybase info);
    
    public int insertPlanOneField(Sybase info);
    
    public int insertPlanTwoField(Sybase info);

    public List<PubFlag> getTableSpaceNameList();

    public Sybase getDbMessage(String tablespaceName);

    //add by zhongzq 20191017
    public Sybase getRecoveryMsg();

}
