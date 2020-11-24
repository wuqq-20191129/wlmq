/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.report.mapper;

import com.goldsign.acc.app.opma.entity.ReportCfgAttr;
import com.goldsign.acc.app.opma.mapper.*;
import com.goldsign.acc.app.opma.entity.ReportCfgModuleMapping;
import com.goldsign.acc.app.opma.entity.ReportQryCfg;
import com.goldsign.acc.app.opma.entity.SysModule;
import com.goldsign.acc.frame.entity.PubFlag;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author mqf
 */
public interface ReportMapper {
    
//    public List<ReportCfgModuleMapping> getReportModuleByModuleId(String moduleId);
    
    public Vector <ReportCfgModuleMapping> getReportModule();
    
//    public List<PubFlag> getReportModules(); //报表模板
    
    public Vector <ReportCfgAttr> getReportGfgAttrs(String reportCode);
    


}
