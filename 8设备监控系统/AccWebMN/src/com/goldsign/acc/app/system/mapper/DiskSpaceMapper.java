package com.goldsign.acc.app.system.mapper;

import com.goldsign.acc.app.system.entity.DiskSpace;
import java.util.List;
import java.util.Map;

/**
 *  服务器磁盘空间
 *
 * @author xiaowu
 */
public interface DiskSpaceMapper {

    public List<DiskSpace> queryPlan();
    
    public String queryCapacityConfig();
    
    public int updatePlan(DiskSpace diskSpace);
    
    public int insertPlanOne(DiskSpace diskSpace);
    
    public int insertPlanTwo(DiskSpace diskSpace);
    
    public void updateMenuStatus(Map parmMap);
}
