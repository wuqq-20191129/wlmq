package com.goldsign.acc.app.st.mapper;

import com.goldsign.acc.app.st.entity.SynLog;
import java.util.List;

/**
 * 票务管理系统 - 同步日志
 *
 * @author xiaowu
 */
public interface SynLogMapper {

    public List<SynLog> queryPlan(SynLog vo);
    
}
