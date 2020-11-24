/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.st.mapper;

import com.goldsign.acc.app.st.entity.TkConfig;
import java.util.List;

/**
 *
 * @author limj
 */
public interface TkConfigMapper {
    public List<TkConfig> queryPlan (TkConfig vo);
    public int modifyPlan (TkConfig vo);
}
