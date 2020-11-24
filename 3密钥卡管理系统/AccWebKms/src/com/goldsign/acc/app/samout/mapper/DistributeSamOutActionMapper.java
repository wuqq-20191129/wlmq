/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.samout.mapper;

import com.goldsign.acc.app.samout.entity.DistributeSamOutAction;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author liudezeng
 * 卡分发出库
 * 
 */
public interface DistributeSamOutActionMapper {

    public List<DistributeSamOutAction> queryPlan(DistributeSamOutAction queryCondition);

    public int addPlan(DistributeSamOutAction vo);

    public int deletePlan(DistributeSamOutAction po);

    public int modifyPlan(DistributeSamOutAction vo);
    
    public DistributeSamOutAction querySinglePlan(DistributeSamOutAction queryCondition);

    public int insertLogicNo(DistributeSamOutAction vo);

    public int updateOrderStock(DistributeSamOutAction vo);

    public int updateStock(DistributeSamOutAction vo);

    public Vector<String> checkLogicNo(DistributeSamOutAction vo);

    public List<DistributeSamOutAction> getLogicNos(DistributeSamOutAction queryCondition);

    public List<DistributeSamOutAction> queryPlanDetail(DistributeSamOutAction queryCondition);

    public List<String> checkLogicNoForAudit(DistributeSamOutAction vo);
    
}
