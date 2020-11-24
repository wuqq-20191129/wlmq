/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.query.mapper;

import com.goldsign.acc.app.query.entity.TicketStorageLineQry;
import java.util.List;

/**
 *
 * @author luck
 */
public interface TicketStorageLineQryMapper {

    public List<TicketStorageLineQry> query(TicketStorageLineQry queryCondition);
    
}
