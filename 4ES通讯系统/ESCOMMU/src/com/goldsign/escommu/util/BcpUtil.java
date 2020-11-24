/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.escommu.util;

import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.env.FileConstant;
import com.goldsign.escommu.vo.BcpResult;
import com.goldsign.escommu.vo.ImportConfig;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class BcpUtil {

    private static Logger logger = Logger.getLogger(BcpUtil.class.getName());

    /**
     * 取得BCP命令
     * 
     * @param config
     * @param tableName
     * @param fileName
     * @param terminator
     * @return 
     */
    public String getBcp(ImportConfig config, String tableName,
            String fileName, String terminator) {
        String bcp = "";
        bcp = "sqlldr userid=" + config.getAccount() + "/" + config.getPassword() + "@" + config.getDb()
                + " control=" + AppConstant.appWorkDir + "/" + "control/" + tableName+".ctl"
                + " log=" + AppConstant.appWorkDir + "/" + FileConstant.ES_BCP_LOG_FILE
                + " data=" + fileName;
                

        return bcp;
    }

    /**
     * BCP导入文件
     * 
     * @param cmd
     * @param n
     * @return
     * @throws Exception 
     */
    public BcpResult bcpFile(String cmd, int n) throws Exception {
        InputStream in = null;
        InputStreamReader bin = null;
        BufferedReader r = null;
        BcpResult result = new BcpResult();
        Process p = null;

        try {

            logger.info(cmd);
            p = Runtime.getRuntime().exec(cmd, null);

            //in = p.getInputStream();
            in = p.getErrorStream();
            bin = new InputStreamReader(in);
            r = new BufferedReader(bin);
            String line;
            boolean bcpResult =true;
            while ((line = r.readLine()) != null) {
                logger.info(line);
                bcpResult=false;
            }
            if(!bcpResult){
                throw new Exception("BCP导入数据失败");
            }
            logger.info("BCP进程等待......");
        } catch (IOException e) {
            logger.error(e);
            throw e;
        } catch (Exception e) {
            logger.error(e);
            throw e;
        } finally {
            this.finalProcess(in, bin, r, p);
        }

        return result;

    }

    /**
     * 结束
     * 
     * @param in
     * @param bin
     * @param r
     * @param p 
     */
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
            if(p !=null){
                p.destroy();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
