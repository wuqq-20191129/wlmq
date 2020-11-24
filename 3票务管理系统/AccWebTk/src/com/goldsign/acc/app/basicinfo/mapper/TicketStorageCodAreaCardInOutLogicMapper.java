/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.mapper;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageCodAreaCardInOutLogic;
import java.util.List;

/**
 *
 * @author mh
 */
public interface TicketStorageCodAreaCardInOutLogicMapper {

    public List<TicketStorageCodAreaCardInOutLogic> queryParm(TicketStorageCodAreaCardInOutLogic queryCondition);
    
//    查询管理到盒的记录
    public int getAreaCardInOutLogicCountForLogic(TicketStorageCodAreaCardInOutLogic vo);
    
}
