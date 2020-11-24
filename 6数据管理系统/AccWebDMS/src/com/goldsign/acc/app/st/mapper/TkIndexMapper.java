package com.goldsign.acc.app.st.mapper;

import com.goldsign.acc.app.st.entity.TkIndex;
import java.util.List;

/**
 * 票务管理系统 - 分表记录
 *
 * @author xiaowu
 */
public interface TkIndexMapper {

    public List<TkIndex> queryPlan(TkIndex vo);
    
}
