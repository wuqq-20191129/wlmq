/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.samin.mapper;

import com.goldsign.acc.app.samin.entity.EmptySamIn;
import com.goldsign.acc.app.samin.entity.RecycleSamIn;
import java.util.List;

/**
 *
 * @author luck
 */
public interface RecycleSamInMapper {

    public int addPlan(RecycleSamIn vo);

    public List<String> checkStock(RecycleSamIn po);

    public List<RecycleSamIn> queryPlan(RecycleSamIn vo);
    
    public int deletePlan(RecycleSamIn vo);

    public int insertLogicNo(RecycleSamIn vo);

    public int updateOrderState(RecycleSamIn vo);

    public int updateStock(RecycleSamIn vo);

    public int modifyPlan(RecycleSamIn vo);

    public List<RecycleSamIn> queryPlanDetail(RecycleSamIn vo);

}
