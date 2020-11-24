package com.goldsign.acc.app.prminfo.mapper;

import com.goldsign.acc.app.prminfo.entity.PhyLogicList;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 物理逻辑卡号对照表
 * @author xiaowu
 * @version 20170619
 */
public interface PhyLogicListMapper {

    public List<PhyLogicList> getPhyLogicLists(PhyLogicList phyLogicList); 
    
    public List<PhyLogicList> getPhyLogicListsByInWaterNos(@Param("waterNoList")List waterNoList);
    
    public List<PhyLogicList> getPhyLogicListsByInPhysicNos(@Param("physicNoList")List physicNoList);
    
    public List<PhyLogicList> getPhyLogicListsByInLogicNos(@Param("logicNoList")List logicNoList);

    public int addPhyLogicList(PhyLogicList phyLogicList);

    public int deletePhyLogicList(PhyLogicList phyLogicList);

}
