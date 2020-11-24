/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.system.mapper;

import com.goldsign.acc.app.system.entity.NetCard;
import java.util.List;

/**
 *
 * @author luck
 */
public interface NetCardMapper {

    public List<NetCard> queryPlan();

    public int update(NetCard vo);

    public int insertIntoCur(NetCard vo);

    public int insertIntoHis(NetCard vo);
    
}
