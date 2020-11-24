package com.goldsign.acc.app.system.mapper;

import com.goldsign.acc.app.system.entity.Disk;
import java.util.List;
import java.util.Map;

/**
 *  服务器硬盘
 *
 * @author xiaowu
 */
public interface DiskMapper {

    public List<Disk> queryPlan();
    
    public int updatePlan(Disk disk);
    
    public int insertPlanOne(Disk disk);
    
    public int insertPlanTwo(Disk disk);
    
    public void updateMenuStatus(Map parmMap);
    
}
