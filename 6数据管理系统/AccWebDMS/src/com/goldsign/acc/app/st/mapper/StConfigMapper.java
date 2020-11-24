/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.st.mapper;

import com.goldsign.acc.app.st.entity.StConfig;
import java.util.List;

/**
 *
 * @author luck
 */
public interface StConfigMapper {

    public List<StConfig> queryPlan(StConfig vo);

    public int modifyPlan(StConfig vo);
    
}
