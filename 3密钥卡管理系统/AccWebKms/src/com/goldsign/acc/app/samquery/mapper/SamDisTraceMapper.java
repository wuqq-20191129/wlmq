/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.samquery.mapper;

import com.goldsign.acc.app.samquery.entity.SamDisTrace;
import java.util.List;

/**
 *
 * @author zhouyang
 * 卡分发跟踪
 * 20170831
 */
public interface SamDisTraceMapper {
    public List<SamDisTrace> getSamDisTrace(SamDisTrace vo);
}
