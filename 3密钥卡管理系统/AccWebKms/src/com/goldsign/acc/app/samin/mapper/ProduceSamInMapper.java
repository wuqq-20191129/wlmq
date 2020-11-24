/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.samin.mapper;

import com.goldsign.acc.app.samin.entity.ProduceSamIn;
import java.util.List;

/**
 *
 * @author luck
 */
public interface ProduceSamInMapper {

    public List<ProduceSamIn> queryPlan(ProduceSamIn vo);

    public List<String> checkStock(ProduceSamIn vo);

    public int addPlan(ProduceSamIn vo);

    public int deletePlan(ProduceSamIn vo);

    public int modifyPlan(ProduceSamIn vo);

    public int insertLogicNo(ProduceSamIn vo);

    public int updateOrderState(ProduceSamIn vo);

    public int updateStock(ProduceSamIn vo);
    
    public int updateOutBill(ProduceSamIn vo);

}
