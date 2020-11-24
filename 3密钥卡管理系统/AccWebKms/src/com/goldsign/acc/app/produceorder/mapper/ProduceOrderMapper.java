/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.produceorder.mapper;

import com.goldsign.acc.app.produceorder.entity.ProduceOrder;
import java.util.Collection;
import java.util.List;

/**
 * 生产单制作
 * @author xiaowu   20170828
 */
public interface ProduceOrderMapper {
    
    public List<ProduceOrder> queryPlan(ProduceOrder vo);
    
    public List<ProduceOrder> queryPlanWithStartLN(ProduceOrder vo);
    
    public List<ProduceOrder> checkLogicNosPlanOrder(ProduceOrder vo);
    
    public String checkAddInfoExist(ProduceOrder vo);
    
    public int checkStore(ProduceOrder vo);

    public int addPlan(ProduceOrder vo);
    
    public int getCountStock(ProduceOrder vo);

    public int deletePlan(ProduceOrder vo);

    public int modifyPlan(ProduceOrder vo);
    
    public int auditPlan(ProduceOrder vo);
    
    public int updateOrderState(ProduceOrder vo);
    
    public int updateMinLogicNo(ProduceOrder vo);
    
    public int insertSamLogicNos(ProduceOrder vo);
    
    public String getMinLogicNo(ProduceOrder vo);

    public String checkResetInfoExist(ProduceOrder poTemp);

    public int checkResetStore(String enLogicNo);

    public void updateResetLogicNo(ProduceOrder vo);

    public String checkInfo(String start_logic_no);

    public int auditPlanForCz(ProduceOrder poAudit);

//    public int isExitsInStock(String start_logic_no);

    public List<ProduceOrder> queryPlanForModify(ProduceOrder po);
}
