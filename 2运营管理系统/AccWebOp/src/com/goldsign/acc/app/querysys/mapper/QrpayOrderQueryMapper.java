/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.querysys.mapper;

import com.goldsign.acc.app.querysys.entity.QrpayOrderQuery;
import java.util.List;

/**
 *
 * @author luck
 */
public interface QrpayOrderQueryMapper {

    public List<QrpayOrderQuery> getQrpayOrder(QrpayOrderQuery queryCondition);

    public int modifyQrpayOrder(QrpayOrderQuery po);

    public int auditQrpayOrder(QrpayOrderQuery queryCondition);

    public List<QrpayOrderQuery> getQrpayOrderForAudit(QrpayOrderQuery qo);

    public void insertModifyOpe(QrpayOrderQuery po);

    public int getdate(String orderNo);
    
    
    
}
