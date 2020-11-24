/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.samin.mapper;

import com.goldsign.acc.app.samin.entity.EmptySamIn;
import java.util.List;

/**
 *
 * @author luck
 */
public interface EmptySamInMapper {

    public int addPlan(EmptySamIn vo);

    public int getSamType(EmptySamIn vo);

    public int isExitLogicalNoInOrder(EmptySamIn vo);

    public int isExitLogicalNoInStock(EmptySamIn vo);

    public List<EmptySamIn> queryPlan(EmptySamIn vo);

    public int deletePlan(EmptySamIn vo);

    public int modifyPlan(EmptySamIn vo);
    
    public int updateOrderState(EmptySamIn vo);
    
    public EmptySamIn getPlanInfo(EmptySamIn vo);
    
    public int addLogicNoIntoStock(EmptySamIn vo);

}
