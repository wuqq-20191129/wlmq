/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.st.mapper;

import com.goldsign.acc.app.st.entity.StClear;
import java.util.List;

/**
 *
 * @author luck
 */
public interface StClearMapper {

    public List<StClear> queryPlan(StClear vo);
    
}
