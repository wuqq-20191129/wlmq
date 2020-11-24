/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.task;

import com.goldsign.acc.app.opma.entity.ReportCfgModuleMapping;
import com.goldsign.acc.app.report.mapper.ReportMapper;
import com.goldsign.acc.frame.util.ReportUtil;
import java.util.Vector;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 报表配置更新
 * @author moqf
 */

@Component
public class ReportConfigTask {
    
    private static Logger logger = Logger.getLogger(ReportConfigTask.class);

    @Autowired
    private ReportMapper reportMapper;

    /*
    报表配置更新,每天凌晨5：00执行一次
    */
//    @Scheduled(fixedRate = 1000*60*60*2)
    @Scheduled(cron = "0 00 05 * * ?")
    public void autoRefresh(){
        
        Vector<ReportCfgModuleMapping> moduleMappings = reportMapper.getReportModule();

        if (!moduleMappings.isEmpty()) {
            ReportUtil.MODULE_REPORT_TEMPLATE_MAPPING.addAll(moduleMappings);
            logger.info("定时获取报表配置表W_RP_CFG_MODULE_MAPPING更新缓存");
        }
        

    }
}
