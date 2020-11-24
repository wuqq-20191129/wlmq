package com.goldsign.acc.app.samout.mapper;

import com.goldsign.acc.app.samout.entity.IssueSamOut;
import java.util.Collection;
import java.util.List;

/**
 * 卡发行出库
 * @author xiaowu   20170829
 */
public interface IssueSamOutMapper {
    
    public List<IssueSamOut> queryPlan(IssueSamOut vo);
    
    public List<IssueSamOut> saveResultProduceOrder(IssueSamOut vo);

    public int addPlan(IssueSamOut vo);

    public int deletePlan(IssueSamOut vo);

    public int modifyPlan(IssueSamOut vo);
    
    public int auditPlan(IssueSamOut vo);
    
    public int updateLogicNosForPlanOrder(IssueSamOut vo);

    public List<IssueSamOut> queryPlanForModify(IssueSamOut po);

    

}
