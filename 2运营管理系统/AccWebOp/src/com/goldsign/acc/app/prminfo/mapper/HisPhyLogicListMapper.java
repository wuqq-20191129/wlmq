package com.goldsign.acc.app.prminfo.mapper;

import com.goldsign.acc.app.prminfo.entity.HisPhyLogicList;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 物理逻辑卡号对照表历史表
 * @author xiaowu
 * @version 20170622
 */
public interface HisPhyLogicListMapper {

    public List<HisPhyLogicList> getHisPhyLogicLists(HisPhyLogicList hisPhyLogicList);
    
    public List<HisPhyLogicList> getHisPhyLogicListsByInWaterNos(@Param("waterNoList")List waterNoList);
    
    public List<HisPhyLogicList> getHisPhyLogicListsByInPhysicNos(@Param("physicNoList")List physicNoList);
    
    public List<HisPhyLogicList> getHisPhyLogicListsByInLogicNos(@Param("logicNoList")List logicNoList);

    public int addHisPhyLogicList(HisPhyLogicList hisPhyLogicList);

    public int deleteHisPhyLogicList(HisPhyLogicList hisPhyLogicList);

}
