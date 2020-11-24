/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.handler;

import com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;
import com.goldsign.settle.realtime.frame.constant.FrameSynConstant;
import com.goldsign.settle.realtime.frame.dao.BufferToQueueBaseDao;
import com.goldsign.settle.realtime.frame.dao.ConfigBcpDao;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.message.MessageBcp;
import com.goldsign.settle.realtime.frame.util.TradeUtil;
import com.goldsign.settle.realtime.frame.vo.BcpAttribute;
import com.goldsign.settle.realtime.frame.vo.BcpResult;
import com.goldsign.settle.realtime.frame.vo.ConfigBcpVo;
import com.goldsign.settle.realtime.frame.vo.SynchronizedControl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class HandlerBcp extends HandlerBase {

    private static Logger logger = Logger.getLogger(HandlerBcp.class.getName());
    private static ConfigBcpVo CONFIG_BCP;

    @Override
    public void handleMessage(MessageBase msg) {
        try {
              ConfigBcpVo config = this.getConfigBcp();
           // ConfigBcpVo config = this.getConfigBcpForTest();//测试数据库并发问题
            MessageBcp msgBcp = (MessageBcp) msg;
            String bcpTradeType = this.getBcpTradeType(msgBcp);
           // String cmd = this.getBcp(config, msgBcp.getBcpFileName(), msgBcp.getTradType());
             String cmd = this.getBcp(config, msgBcp.getBcpFileName(), bcpTradeType);
            int n = 0;
            BcpResult result = this.bcpFile(cmd, n);





        } catch (Exception ex) {
        }
    }
    private String getBcpTradeType(MessageBcp msgBcp){
        
        BcpAttribute battr = msgBcp.getAttr();
        String tradeType =msgBcp.getTradType();
        if(battr==null)
            return tradeType;
        if(battr.isAddSubTradeType()){
            tradeType=msgBcp.getTradType()+battr.getSubTradeType();
            return tradeType;
        }
        return tradeType;
        
        
    }

    private ConfigBcpVo getConfigBcp() throws Exception {

        synchronized (FrameSynConstant.SYN_BCP_CONFIG) {
            if (HandlerBcp.CONFIG_BCP != null) {
                return HandlerBcp.CONFIG_BCP;
            }

            ConfigBcpDao dao = new ConfigBcpDao();
            HandlerBcp.CONFIG_BCP = dao.getConfigBcp();

            return HandlerBcp.CONFIG_BCP;
        }
    }

    private ConfigBcpVo getConfigBcpForTest() throws Exception {
        int n = 10000;
        long startTime=System.currentTimeMillis();
        long endTime =0;
        for (int i = 0; i < n; i++) {
            ConfigBcpDao dao = new ConfigBcpDao();
            // HandlerBcp.CONFIG_BCP = dao.getConfigBcp();
            //HandlerBcp.CONFIG_BCP = dao.getConfigBcpForTran();
            HandlerBcp.CONFIG_BCP = dao.getConfigBcpForAutoCommit();
           //  HandlerBcp.CONFIG_BCP = dao.getConfigBcpForSynControl();
            if ((i + 1) % 100 == 0) {
                logger.info("运行获取配置次数：" + i);
            }
        }
        endTime = System.currentTimeMillis();
        logger.info("运行获取配置次数：" + n+" 运行时间："+(endTime-startTime)/1000+"秒");
        return HandlerBcp.CONFIG_BCP;

    }

    private String getBcp(ConfigBcpVo config, String inFileName, String tradType) {
        // String tem= this.getBcpTerminater(terminator);
        String inFilePath =TradeUtil.getDirForBalanceWaterNo(FrameCodeConstant.PATH_FILE_TRX_BCP, false);
        String badFilePath=TradeUtil.getDirForBalanceWaterNo(FrameCodeConstant.PATH_FILE_TRX_BCP_LOG_BAD, true);
        String bcpFilePath=TradeUtil.getDirForBalanceWaterNo(FrameCodeConstant.PATH_FILE_TRX_BCP_LOG, true);
        //String inFileFull = FrameCodeConstant.PATH_FILE_TRX_BCP + "/" + inFileName;
        String inFileFull =inFilePath + "/" + inFileName;
        String ctlFile = FrameCodeConstant.PATH_FILE_TRX_BCP_CTL + "/" + tradType + ".ctl";
        //String logFile =FrameCodeConstant.PATH_FILE_TRX_BCP_LOG + "/" + tradType + ".log";
        String logFile =bcpFilePath + "/" + inFileName + ".log";
        //String badFile =FrameCodeConstant.PATH_FILE_TRX_BCP_LOG_BAD + "/" + inFileName + ".bad";
        String badFile =badFilePath + "/" + inFileName + ".bad";
        String bcp = "sqlldr userid=" + config.getDbAccount() + "/"
                + config.getDbPassword() + "@"
                + config.getDbSid() + " "
                + "data='" + inFileFull + "' "
                + "control='" + ctlFile + "' "
                + "log='" + logFile + "' "
                + "bad='" + badFile + "' "
                ;

        return bcp;
    }

    public BcpResult bcpFile(String cmd, int n) throws Exception {
        /**
         * 线程进程返回
         */
        InputStream in = null;
        InputStreamReader bin = null;
        BufferedReader r = null;
        /**
         * 执行结果中关键字
         */
        String rowMsg = "logical record count";
        String rowMsg_1 = "逻辑记录计数";
        /**
         * 返回结果
         */
        BcpResult result = new BcpResult();
        Process p = null;

        try {
            logger.info(cmd);
            p = Runtime.getRuntime().exec(cmd, null);

            in = p.getInputStream();
            bin = new InputStreamReader(in, "GBK");
            r = new BufferedReader(bin);
            String line;
            String s = "";
            int index;
            boolean bcpResult = false;
            while ((line = r.readLine()) != null) {
                logger.info(line);
                if ((index = line.indexOf(rowMsg)) != -1
                        || (index = line.indexOf(rowMsg_1)) != -1) {
                    s = "" + n;
                    bcpResult = true;
                }
            }
            /*
             if (!bcpResult) {
             throw new Exception("BCP导入数据失败");
             }
             */
            //logger.info("BCP进程等待......");
           // logger.info("BCP进程退出代码：" + p.exitValue());
            //p.wait();
            result.setTotal(s);
            result.setExitNo("0");
            //result.setExitNo(new Integer(p.exitValue()).toString());
            if (result.getExitNo().equals("-1")) {
                throw new Exception("BCP导入数据失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            this.finalProcess(in, bin, r, p);

        }

        return result;

    }

    private void finalProcess(InputStream in, InputStreamReader bin,
            BufferedReader r, Process p) {
        try {
            if (r != null) {
                r.close();
            }
            if (bin != null) {
                bin.close();
            }
            if (in != null) {
                in.close();
            }
            //if(p !=null)

            //p.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
