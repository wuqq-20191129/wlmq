/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.opma.mapper;

import com.goldsign.acc.app.opma.entity.ReportCfgAttr;
import com.goldsign.acc.frame.entity.PubFlag;
import java.util.List;
import java.util.Map;

/**
 * 报表属性配置
 *
 * @author luck
 */
public interface ReportCfgAttrMapper {

    public List<ReportCfgAttr> getReportGfgAttrs(ReportCfgAttr queryCondition);

    public List<PubFlag> getResources();

    public int modify(ReportCfgAttr po);

    public List<Map> queryToMap(ReportCfgAttr queryCondition);

}
