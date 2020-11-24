/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.timer;

import com.goldsign.settle.realtime.frame.dao.OctReportDao;
import com.goldsign.settle.realtime.frame.dao.RealtimeReportDao;
import com.goldsign.settle.realtime.frame.vo.ClearTempFileVo;
import com.goldsign.settle.realtime.frame.vo.ResultFromProc;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class ActionRealtimeReport extends ActionBase {

    private static Logger logger = Logger.getLogger(ActionRealtimeReport.class.getName());
    private static int ERR_CODE_NO_BALANCE_WATER_NO = 1;
    private static int ERR_CODE_DOING = 2;
    private static int SLEEP_TIME = 60000;

    @Override
    public void action() throws Exception {
        logger.info("实时报表配置设置开始");
        this.genRealtimeReport();
        logger.info("实时报表配置设置结束");

    }

    private void genRealtimeReport() throws Exception {

        RealtimeReportDao dao = new RealtimeReportDao();
        ResultFromProc result = dao.genRealtimeReport();
        int retCode = result.getRetCode();
        if (retCode != 0) {
            //没有清算流水或当前清算流水正在生成报表，等待及重试
            while (retCode == ERR_CODE_NO_BALANCE_WATER_NO || retCode == ERR_CODE_DOING) {
                logger.info("当前清算流水号："+" 正在生成报表，等候"+(SLEEP_TIME/1000)+"秒再设置实时报表配置");
                Thread.sleep(SLEEP_TIME);//等待当前清算流水或正在生成报表完成
                result = dao.genRealtimeReport();
                retCode = result.getRetCode();
            }

            if (retCode !=0) {
                throw new Exception("设置实时报表报表生成配置错误：" + result.getRetMsg());
            }
        }
    }

}
