/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.querysys.mapper;

import com.goldsign.acc.app.querysys.entity.BalanceLog;
import com.goldsign.acc.frame.entity.PubFlag;
import java.util.List;

/**
 *
 * @author zhouyang
 * 20170617
 */
public interface BalanceLogMapper {
    public List<PubFlag> getErrLevelForPage();
    
    public List<BalanceLog> getBalanceLogs(BalanceLog balanceLog);
}
