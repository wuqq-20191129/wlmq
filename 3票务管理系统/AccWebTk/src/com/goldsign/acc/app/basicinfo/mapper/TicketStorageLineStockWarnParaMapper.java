/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.basicinfo.mapper;

import com.goldsign.acc.app.basicinfo.entity.TicketStorageLineStockWarnPara;
import java.util.List;

/**
 *
 * @author mh
 */
public interface TicketStorageLineStockWarnParaMapper {
    public List<TicketStorageLineStockWarnPara> queryLineStockWarnPara(TicketStorageLineStockWarnPara queryCondition);
    public List<TicketStorageLineStockWarnPara> queryLineStockWarnParaById(TicketStorageLineStockWarnPara po);
    public int addLineStockWarnPara(TicketStorageLineStockWarnPara po);
    public int modifyLineStockWarnPara(TicketStorageLineStockWarnPara po);
    public int deleteLineStockWarnPara(TicketStorageLineStockWarnPara po);
}
