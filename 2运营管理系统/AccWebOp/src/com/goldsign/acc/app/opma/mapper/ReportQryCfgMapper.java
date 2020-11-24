/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.opma.mapper;

import com.goldsign.acc.app.opma.entity.ReportCfgModuleMapping;
import com.goldsign.acc.app.opma.entity.ReportQryCfg;
import com.goldsign.acc.app.opma.entity.SysModule;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author mqf
 */
public interface ReportQryCfgMapper {

    public List<ReportQryCfg> getReportQryCfgs(ReportQryCfg reportQryCfg);

    public int addReportQryCfg(ReportQryCfg reportQryCfg);

    public List<ReportQryCfg> getReportQryCfgById(ReportQryCfg reportQryCfg);

    public int deleteReportQryCfg(ReportQryCfg reportQryCfg);
    
    public List<ReportQryCfg> getReportQryCfgsForGenFile(Vector<ReportQryCfg> reportQryCfgs);
    
    public List<ReportCfgModuleMapping> getReportModuleDess(Vector<ReportQryCfg> reportQryCfgs);
    
    public List<SysModule> getSysModulesForReportTitle(); 
    
    public List<SysModule> getSysModulesForActionName(); 
    


}
