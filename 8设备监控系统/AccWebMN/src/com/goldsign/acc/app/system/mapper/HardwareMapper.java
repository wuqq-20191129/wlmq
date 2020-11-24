/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.system.mapper;

import com.goldsign.acc.app.system.entity.Hardware;
import java.util.List;

/**
 *
 * @author luck
 */
public interface HardwareMapper {

    public List<Hardware> queryPlan();

    public int update(Hardware vo);

    public int insertIntoCur(Hardware vo);

    public int insertIntoHis(Hardware vo);
    
}
