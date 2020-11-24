/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.produceorder.mapper;

import com.goldsign.acc.app.produceorder.entity.LogicNos;
import java.util.List;

/**
 * 生产单制作
 * @author xiaowu   20170828
 */
public interface LogicNosMapper {
    
    public List<LogicNos> getLogicNos(LogicNos vo);
}
