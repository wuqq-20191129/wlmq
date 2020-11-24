package com.goldsign.systemmonitor.web;

import java.io.IOException;



import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.goldsign.systemmonitor.dao.ConfigDao;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.frame.util.FrameUtil;
import com.goldsign.systemmonitor.thread.FileParseThread;
import com.goldsign.systemmonitor.thread.FtpThread;


/**
 * Servlet implementation class for Servlet: InitServlet
 *
 */
public class InitServlet extends javax.servlet.http.HttpServlet implements
        javax.servlet.Servlet {

    static Logger logger = Logger.getLogger(InitServlet.class);
    /* (non-Java-doc)
     * @see javax.servlet.http.HttpServlet#HttpServlet()
     */

    public InitServlet() {
        super();
    }

    /* (non-Java-doc)
     * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    }

    /* (non-Java-doc)
     * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    }

    /* (non-Javadoc)
     * @see javax.servlet.GenericServlet#init()
     */
    public void init() throws ServletException {
        // TODO Auto-generated method stub
        super.init();
        try {
            this.initDbConfig();
            this.initFtpThread();
            this.initFileParseThread();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void initDbConfig() throws Exception {
        ConfigDao dao = new ConfigDao();
        dao.getAppConfigs();
        FrameUtil util = new FrameUtil();
        /*
         * FTP配置参数
         */

        FrameDBConstant.FtpTimeout = util.getConfigIntValue("FtpTimeout", FrameDBConstant.config_type_ftp, FrameDBConstant.APP_CONFIGS);
        FrameDBConstant.FtpRetryWaiting = util.getConfigIntValue("FtpRetryWaiting", FrameDBConstant.config_type_ftp, FrameDBConstant.APP_CONFIGS);;
        FrameDBConstant.FtpRetryTime = util.getConfigIntValue("FtpRetryTime", FrameDBConstant.config_type_ftp, FrameDBConstant.APP_CONFIGS);;
        FrameDBConstant.FtpLocalDir = util.getConfigStringValue("FtpLocalDir", FrameDBConstant.config_type_ftp, FrameDBConstant.APP_CONFIGS);;
        FrameDBConstant.FtpTmpFilePrefix = util.getConfigStringValue("FtpTmpFilePrefix", FrameDBConstant.config_type_ftp, FrameDBConstant.APP_CONFIGS);;
        FrameDBConstant.FtpInterval = util.getConfigIntValue("FtpInterval", FrameDBConstant.config_type_ftp, FrameDBConstant.APP_CONFIGS);;

        FrameDBConstant.FtpRemoteDir = util.getConfigStringValue("FtpRemoteDir", FrameDBConstant.config_type_ftp, FrameDBConstant.APP_CONFIGS);;
        FrameDBConstant.FtpServer = util.getConfigStringValue("FtpServer", FrameDBConstant.config_type_ftp, FrameDBConstant.APP_CONFIGS);;
        FrameDBConstant.FtpUser = util.getConfigStringValue("FtpUser", FrameDBConstant.config_type_ftp, FrameDBConstant.APP_CONFIGS);;
        FrameDBConstant.FtpPassword = util.getConfigStringValue("FtpPassword", FrameDBConstant.config_type_ftp, FrameDBConstant.APP_CONFIGS);;

        logger.info("*****设置FTP配置参数开始******");
        logger.info("FtpTimeout=" + FrameDBConstant.FtpTimeout);
        logger.info("FtpRetryWaiting=" + FrameDBConstant.FtpRetryWaiting);
        logger.info("FtpRetryTime=" + FrameDBConstant.FtpRetryTime);
        logger.info("FtpLocalDir=" + FrameDBConstant.FtpLocalDir);
        logger.info("FtpTmpFilePrefix=" + FrameDBConstant.FtpTmpFilePrefix);
        logger.info("FtpInterval=" + FrameDBConstant.FtpInterval);

        logger.info("FtpRemoteDir=" + FrameDBConstant.FtpRemoteDir);
        logger.info("FtpServer=" + FrameDBConstant.FtpServer);
        logger.info("FtpUser=" + FrameDBConstant.FtpUser);
        logger.info("FtpPassword=" + FrameDBConstant.FtpPassword);
        logger.info("*****设置FTP配置参数结束******");
        logger.info("\n");

        /*
         * 命令处理
         */

        FrameDBConstant.FileLineCommandStart = util.getConfigStringValue("FileLineCommandStart",
                FrameDBConstant.config_type_linecommand,
                FrameDBConstant.APP_CONFIGS);//命令开始符前缀
        FrameDBConstant.FileLineCommandEnd = util.getConfigStringValue("FileLineCommandEnd",
                FrameDBConstant.config_type_linecommand,
                FrameDBConstant.APP_CONFIGS);//命令结束符前缀
        FrameDBConstant.FileLineCommandSeperator = util.getConfigStringValue("FileLineCommandSeperator",
                FrameDBConstant.config_type_linecommand,
                FrameDBConstant.APP_CONFIGS);//命令分隔符

        FrameDBConstant.FileHandlerClassPrix = util.getConfigStringValue("FileHandlerClassPrix",
                FrameDBConstant.config_type_linecommand,
                FrameDBConstant.APP_CONFIGS);//命令处理类前缀
        FrameDBConstant.FileParseInterval = util.getConfigIntValue("FileParseInterval",
                FrameDBConstant.config_type_linecommand,
                FrameDBConstant.APP_CONFIGS);//命令分析的时间间隔

        FrameDBConstant.FileWaitFtpInterval = util.getConfigIntValue("FileWaitFtpInterval",
                FrameDBConstant.config_type_linecommand,
                FrameDBConstant.APP_CONFIGS);//命令分析等候FTP完成的轮询时间间隔

        FrameDBConstant.FileNameSeperator = util.getConfigStringValue("FileNameSeperator",
                FrameDBConstant.config_type_linecommand,
                FrameDBConstant.APP_CONFIGS);;//文件名分隔符




        logger.info("*****命令处理开始******");
        logger.info("FileLineCommandStart=" + FrameDBConstant.FileLineCommandStart);
        logger.info("FileLineCommandEnd=" + FrameDBConstant.FileLineCommandEnd);
        logger.info("FileLineCommandSeperator=" + FrameDBConstant.FileLineCommandSeperator);
        logger.info("FileHandlerClassPrix=" + FrameDBConstant.FileHandlerClassPrix);
        logger.info("FileParseInterval=" + FrameDBConstant.FileParseInterval);
        logger.info("FileWaitFtpInterval=" + FrameDBConstant.FileWaitFtpInterval);
        logger.info("FileNameSeperator=" + FrameDBConstant.FileNameSeperator);
        logger.info("*****命令处理结束******");
        logger.info("\n");



        //		
        /**
         * 命令prtdiag6800查找关键字

         */
        FrameDBConstant.Command_find_prtdiag6800_key = util.getConfigStringValue("Command_find_prtdiag6800_key",
                FrameDBConstant.config_type_prtdiag6800,
                FrameDBConstant.APP_CONFIGS);//
        FrameDBConstant.Command_find_prtdiag6800_key_start = util.getConfigStringValue("Command_find_prtdiag6800_key_start",
                FrameDBConstant.config_type_prtdiag6800,
                FrameDBConstant.APP_CONFIGS);//
        FrameDBConstant.Command_find_prtdiag6800_key_end = util.getConfigStringValue("Command_find_prtdiag6800_key_end",
                FrameDBConstant.config_type_prtdiag6800,
                FrameDBConstant.APP_CONFIGS);//

        logger.info("*****命令prtdiag6800查找关键字开始******");
        logger.info("Command_find_prtdiag6800_key=" + FrameDBConstant.Command_find_prtdiag6800_key);
        logger.info("Command_find_prtdiag6800_key_start=" + FrameDBConstant.Command_find_prtdiag6800_key_start);
        logger.info("Command_find_prtdiag6800_key_end=" + FrameDBConstant.Command_find_prtdiag6800_key_end);
        logger.info("*****命令prtdiag6800查找关键字结束******");
        logger.info("\n");

        //		
        /**
         * 命令prtdiag880查找关键字

         */
        FrameDBConstant.Command_find_prtdiag880_keys = util.getConfigStringArrayValue(
                util.getConfigStringValue("Command_find_prtdiag880_keys",
                FrameDBConstant.config_type_prtdiag880,
                FrameDBConstant.APP_CONFIGS));;// 	

        logger.info("*****prtdiag880查找关键字开始******");
        logger.info("Command_find_prtdiag880_keys=" + this.getArrayValue(FrameDBConstant.Command_find_prtdiag880_keys));
        logger.info("*****prtdiag880查找关键字结束******");
        logger.info("\n");

        //						

        /**
         * 命令prtdiag480查找关键字

         */
        FrameDBConstant.Command_find_prtdiag480_key = util.getConfigStringValue("Command_find_prtdiag480_key",
                FrameDBConstant.config_type_prtdiag480,
                FrameDBConstant.APP_CONFIGS);//
        FrameDBConstant.Command_find_prtdiag480_key_1 = util.getConfigStringValue("Command_find_prtdiag480_key_1",
                FrameDBConstant.config_type_prtdiag480,
                FrameDBConstant.APP_CONFIGS);;//

        logger.info("*****命令prtdiag480查找关键字开始******");
        logger.info("Command_find_prtdiag480_key=" + FrameDBConstant.Command_find_prtdiag480_key);
        logger.info("Command_find_prtdiag480_key_1=" + FrameDBConstant.Command_find_prtdiag480_key_1);
        logger.info("*****命令prtdiag480查找关键字结束******");
        logger.info("\n");



        /*
         * 命令commlcc查找关键字

         */
        FrameDBConstant.Command_find_commlcc_key_ip = util.getConfigStringValue("Command_find_commlcc_key_ip",
                FrameDBConstant.config_type_commlcc,
                FrameDBConstant.APP_CONFIGS);;

        FrameDBConstant.Command_find_commlcc_key_status = util.getConfigStringValue("Command_find_commlcc_key_status",
                FrameDBConstant.config_type_commlcc,
                FrameDBConstant.APP_CONFIGS);;

        logger.info("*****命令commlcc查找关键字开始******");
        logger.info("Command_find_commlcc_key_ip=" + FrameDBConstant.Command_find_commlcc_key_ip);
        logger.info("Command_find_commlcc_key_status=" + FrameDBConstant.Command_find_commlcc_key_status);
        logger.info("*****命令commlcc查找关键字结束******");
        logger.info("\n");




        /*
         * 命令metastat查找关键字

         */

        FrameDBConstant.Command_find_metastat_key = util.getConfigStringValue("Command_find_metastat_key",
                FrameDBConstant.config_type_metastat,
                FrameDBConstant.APP_CONFIGS);;

        logger.info("*****命令metastat查找关键字开始******");
        logger.info("Command_find_metastat_key=" + FrameDBConstant.Command_find_metastat_key);
        logger.info("*****命令metastat查找关键字结束******");
        logger.info("\n");
        /*
         * 命令scstat查找关键字

         */
        FrameDBConstant.Command_find_scstat_keys = util.getConfigStringArrayValue(
                util.getConfigStringValue("Command_find_scstat_keys",
                FrameDBConstant.config_type_scstat,
                FrameDBConstant.APP_CONFIGS));;
        FrameDBConstant.Command_find_scstat_common = util.getConfigStringValue("Command_find_scstat_common",
                FrameDBConstant.config_type_scstat,
                FrameDBConstant.APP_CONFIGS);;

        logger.info("*****命令scstat查找关键字开始******");
        logger.info("Command_find_scstat_keys=" + this.getArrayValue(FrameDBConstant.Command_find_scstat_keys));
        logger.info("Command_find_scstat_common=" + FrameDBConstant.Command_find_scstat_common);
        logger.info("*****命令scstat查找关键字结束******");
        logger.info("\n");


        /*
         * 状态判断关键字
         */
        FrameDBConstant.Command_status_scstat_key = util.getConfigStringValue("Command_status_scstat_key",
                FrameDBConstant.config_type_status,
                FrameDBConstant.APP_CONFIGS);;//命令scstat
        FrameDBConstant.Command_status_df_key = util.getConfigIntValue("Command_status_df_key",
                FrameDBConstant.config_type_status,
                FrameDBConstant.APP_CONFIGS);;//命令df使用率



        logger.info("*****状态判断关键字开始******");
        logger.info("Command_status_scstat_key=" + FrameDBConstant.Command_status_scstat_key);
        logger.info("Command_status_df_key=" + FrameDBConstant.Command_status_df_key);
        logger.info("*****状态判断关键字结束******");
        logger.info("\n");

        /*
         * 命令df查找关键字

         */

        FrameDBConstant.Command_find_df_keys_mounted = util.getConfigStringArrayValue(
                util.getConfigStringValue("Command_find_df_keys_mounted",
                FrameDBConstant.config_type_df,
                FrameDBConstant.APP_CONFIGS));//安装点

        FrameDBConstant.Command_find_df_keys_mounted_complete = util.getConfigStringArrayValue(
                util.getConfigStringValue("Command_find_df_keys_mounted_complete",
                FrameDBConstant.config_type_df,
                FrameDBConstant.APP_CONFIGS));//安装点完全匹配

        FrameDBConstant.Command_find_df_keys_filesystem = util.getConfigStringArrayValue(
                util.getConfigStringValue("Command_find_df_keys_filesystem",
                FrameDBConstant.config_type_df,
                FrameDBConstant.APP_CONFIGS));//文件系统

        logger.info("*****命令df查找关键字开始******");
        logger.info("Command_find_df_keys_mounted=" + this.getArrayValue(FrameDBConstant.Command_find_df_keys_mounted));
        logger.info("Command_find_df_keys_mounted_complete=" + this.getArrayValue(FrameDBConstant.Command_find_df_keys_mounted_complete));
        logger.info("Command_find_df_keys_filesystem=" + this.getArrayValue(FrameDBConstant.Command_find_df_keys_filesystem));
        logger.info("*****命令df查找关键字结束******");
        logger.info("\n");

        /*
         * 命令cisco3350查找关键字

         */
        FrameDBConstant.Command_find_cisco3350_keys_device = util.getConfigStringArrayValue(
                util.getConfigStringValue("Command_find_cisco3350_keys_device",
                FrameDBConstant.config_type_cisco3350,
                FrameDBConstant.APP_CONFIGS));;

        FrameDBConstant.Command_find_cisco3350_keys_ip = util.getConfigStringArrayValue(
                util.getConfigStringValue("Command_find_cisco3350_keys_ip",
                FrameDBConstant.config_type_cisco3350,
                FrameDBConstant.APP_CONFIGS));;
        FrameDBConstant.Command_find_cisco3350_key_status = util.getConfigStringValue("Command_find_cisco3350_key_status",
                FrameDBConstant.config_type_cisco3350,
                FrameDBConstant.APP_CONFIGS);;



        logger.info("*****命令cisco3350查找关键字开始******");
        logger.info("Command_find_cisco3350_keys_device=" + this.getArrayValue(FrameDBConstant.Command_find_cisco3350_keys_device));
        logger.info("Command_find_cisco3350_keys_ip=" + this.getArrayValue(FrameDBConstant.Command_find_cisco3350_keys_ip));
        logger.info("Command_find_cisco3350_key_status=" + FrameDBConstant.Command_find_cisco3350_key_status);
        logger.info("*****命令cisco3350查找关键字结束******");
        logger.info("\n");

        /*
         * 命令cisco3360查找关键字

         */
        FrameDBConstant.Command_find_cisco3360_keys_device = util.getConfigStringArrayValue(
                util.getConfigStringValue("Command_find_cisco3360_keys_device",
                FrameDBConstant.config_type_cisco3360,
                FrameDBConstant.APP_CONFIGS));;
        FrameDBConstant.Command_find_cisco3360_keys_ip = util.getConfigStringArrayValue(
                util.getConfigStringValue("Command_find_cisco3360_keys_ip",
                FrameDBConstant.config_type_cisco3360,
                FrameDBConstant.APP_CONFIGS));;

        FrameDBConstant.Command_find_cisco3360_key_status_1 = util.getConfigStringValue("Command_find_cisco3360_key_status_1",
                FrameDBConstant.config_type_cisco3360,
                FrameDBConstant.APP_CONFIGS);;

        FrameDBConstant.Command_find_cisco3360_key_status_2 = util.getConfigStringValue("Command_find_cisco3360_key_status_2",
                FrameDBConstant.config_type_cisco3360,
                FrameDBConstant.APP_CONFIGS);;

        FrameDBConstant.Command_find_cisco3360_key = util.getConfigStringValue("Command_find_cisco3360_key",
                FrameDBConstant.config_type_cisco3360,
                FrameDBConstant.APP_CONFIGS);;

        logger.info("*****命令cisco3360查找关键字开始******");
        logger.info("Command_find_cisco3360_keys_device=" + this.getArrayValue(FrameDBConstant.Command_find_cisco3360_keys_device));
        logger.info("Command_find_cisco3360_keys_ip=" + this.getArrayValue(FrameDBConstant.Command_find_cisco3360_keys_ip));
        logger.info("Command_find_cisco3360_key_status_1=" + FrameDBConstant.Command_find_cisco3360_key_status_1);
        logger.info("Command_find_cisco3360_key_status_2=" + FrameDBConstant.Command_find_cisco3360_key_status_2);
        logger.info("Command_find_cisco3360_key=" + FrameDBConstant.Command_find_cisco3360_key);

        logger.info("*****命令cisco3360查找关键字结束******");
        logger.info("\n");


        /*
         * 命令ASA5520查找关键字

         */
        FrameDBConstant.Command_find_ASA5520_key = util.getConfigStringValue("Command_find_ASA5520_key",
                FrameDBConstant.config_type_ASA5520,
                FrameDBConstant.APP_CONFIGS);;

        FrameDBConstant.Command_find_ASA5520_key_1 = util.getConfigStringValue("Command_find_ASA5520_key_1",
                FrameDBConstant.config_type_ASA5520,
                FrameDBConstant.APP_CONFIGS);;

        FrameDBConstant.Command_find_ASA5520_key_2 = util.getConfigStringValue("Command_find_ASA5520_key_2",
                FrameDBConstant.config_type_ASA5520,
                FrameDBConstant.APP_CONFIGS);;

        FrameDBConstant.Command_find_ASA5520_key_status = util.getConfigStringValue("Command_find_ASA5520_key_status",
                FrameDBConstant.config_type_ASA5520,
                FrameDBConstant.APP_CONFIGS);;

        FrameDBConstant.Command_ASA5520_ips = util.getConfigStringArrayValue(
                util.getConfigStringValue("Command_ASA5520_ips",
                FrameDBConstant.config_type_ASA5520,
                FrameDBConstant.APP_CONFIGS));;

        FrameDBConstant.Command_find_ASA5520_key_redundaney = util.getConfigStringArrayValue(util.getConfigStringValue("Command_find_ASA5520_key_redundaney",
                FrameDBConstant.config_type_ASA5520,
                FrameDBConstant.APP_CONFIGS));;

        FrameDBConstant.Command_find_ASA5520_keys_ip = util.getConfigStringArrayValue(
                util.getConfigStringValue("Command_find_ASA5520_keys_ip",
                FrameDBConstant.config_type_ASA5520,
                FrameDBConstant.APP_CONFIGS));;



        //a 
        logger.info("*****命令ASA5520查找关键字开始******");
        logger.info("Command_find_ASA5520_key=" + FrameDBConstant.Command_find_ASA5520_key);
        logger.info("Command_find_ASA5520_key_1=" + FrameDBConstant.Command_find_ASA5520_key_1);
        logger.info("Command_find_ASA5520_key_2=" + FrameDBConstant.Command_find_ASA5520_key_2);
        logger.info("Command_find_ASA5520_key_status=" + FrameDBConstant.Command_find_ASA5520_key_status);
        logger.info("Command_find_ASA5520_key=" + FrameDBConstant.Command_find_ASA5520_key);
        logger.info("Command_ASA5520_ips=" + this.getArrayValue(FrameDBConstant.Command_ASA5520_ips));

        logger.info("Command_find_ASA5520_key_redundaney=" + this.getArrayValue(FrameDBConstant.Command_find_ASA5520_key_redundaney));
        logger.info("Command_find_ASA5520_keys_ip=" + this.getArrayValue(FrameDBConstant.Command_find_ASA5520_keys_ip));
        logger.info("*****命令ASA5520查找关键字结束******");
        logger.info("\n");
        /*
         * 命令sybasedump查找关键字

         */
        FrameDBConstant.Command_find_sybasedump_key = util.getConfigStringValue("Command_find_sybasedump_key",
                FrameDBConstant.config_type_sybasedump,
                FrameDBConstant.APP_CONFIGS);;

        logger.info("****命令sybasedump查找关键字开始******");
        logger.info("Command_find_sybasedump_key=" + FrameDBConstant.Command_find_sybasedump_key);
        logger.info("*****命令sybasedump查找关键字结束******");
        logger.info("\n");

        /*
         * 命令sybasetime查找关键字

         */

        FrameDBConstant.Command_sybasetime_ips = util.getConfigStringArrayValue(
                util.getConfigStringValue("Command_sybasetime_ips",
                FrameDBConstant.config_type_sybasetime,
                FrameDBConstant.APP_CONFIGS));;

        logger.info("*****命令sybasetime查找关键字开始******");
        logger.info("Command_sybasetime_ips=" + this.getArrayValue(FrameDBConstant.Command_sybasetime_ips));
        logger.info("*****命令sybasetime查找关键字结束******");
        logger.info("\n");
        /*
         * 命令iq查找关键字

         */
        FrameDBConstant.Command_find_iq_key_start = util.getConfigStringValue("Command_find_iq_key_start",
                FrameDBConstant.config_type_iq,
                FrameDBConstant.APP_CONFIGS);;
        FrameDBConstant.Command_find_iq_key_finish = util.getConfigStringValue("Command_find_iq_key_finish",
                FrameDBConstant.config_type_iq,
                FrameDBConstant.APP_CONFIGS);;
        FrameDBConstant.Command_find_iq_key_size = util.getConfigStringValue("Command_find_iq_key_size",
                FrameDBConstant.config_type_iq,
                FrameDBConstant.APP_CONFIGS);;

        //修改2012-10-15 by lml
        FrameDBConstant.Command_find_iq_key_size_1 = util.getConfigStringValue("Command_find_iq_key_size_1",
                FrameDBConstant.config_type_iq,
                FrameDBConstant.APP_CONFIGS);;

        FrameDBConstant.Command_iq_ip = util.getConfigStringValue("Command_iq_ip",
                FrameDBConstant.config_type_iq,
                FrameDBConstant.APP_CONFIGS);;
        FrameDBConstant.Command_iq_db_key = util.getConfigStringValue("Command_iq_db_key",
                FrameDBConstant.config_type_iq,
                FrameDBConstant.APP_CONFIGS);;
        FrameDBConstant.Command_iq_db_key_start = util.getConfigStringValue("Command_iq_db_key_start",
                FrameDBConstant.config_type_iq,
                FrameDBConstant.APP_CONFIGS);;
        FrameDBConstant.Command_iq_db_key_end = util.getConfigStringValue("Command_iq_db_key_end",
                FrameDBConstant.config_type_iq,
                FrameDBConstant.APP_CONFIGS);;
        FrameDBConstant.Command_iq_db_key_end_1 = util.getConfigStringValue("Command_iq_db_key_end_1",
                FrameDBConstant.config_type_iq,
                FrameDBConstant.APP_CONFIGS);;

        FrameDBConstant.Command_iq_ip_jobs = util.getConfigStringArrayValue(
                util.getConfigStringValue("Command_iq_ip_jobs",
                FrameDBConstant.config_type_iq,
                FrameDBConstant.APP_CONFIGS));;
        FrameDBConstant.Command_iq_db_key_job_failure = util.getConfigStringValue("Command_iq_db_key_job_failure",
                FrameDBConstant.config_type_iq,
                FrameDBConstant.APP_CONFIGS);;


        logger.info("*****命令iq查找关键字开始******");
        logger.info("Command_find_iq_key_start=" + FrameDBConstant.Command_find_iq_key_start);
        logger.info("Command_find_iq_key_finish=" + FrameDBConstant.Command_find_iq_key_finish);
        logger.info("Command_find_iq_key_size=" + FrameDBConstant.Command_find_iq_key_size);

        //修改2012-10-15 by lml
        logger.info("Command_find_iq_key_size_1=" + FrameDBConstant.Command_find_iq_key_size_1);

        logger.info("Command_iq_ip" + FrameDBConstant.Command_iq_ip);
        logger.info("Command_iq_db_key=" + FrameDBConstant.Command_iq_db_key);
        logger.info("Command_iq_db_key_start=" + FrameDBConstant.Command_iq_db_key_start);
        logger.info("Command_iq_db_key_end=" + FrameDBConstant.Command_iq_db_key_end);
        logger.info("Command_iq_db_key_end_1=" + FrameDBConstant.Command_iq_db_key_end_1);
//	2012-03-12增加 by hejj	
        logger.info("Command_iq_ip_jobs=" + this.getArrayValue(FrameDBConstant.Command_iq_ip_jobs));
        logger.info("Command_iq_db_key_job_failure=" + FrameDBConstant.Command_iq_db_key_job_failure);

        logger.info("*****命令iq查找关键字结束******");
        logger.info("\n");
        /*
         * 数据块名称

         */
        FrameDBConstant.Segment_name_default = util.getConfigStringValue("Segment_name_default",
                FrameDBConstant.config_type_block,
                FrameDBConstant.APP_CONFIGS);;//数据块

        FrameDBConstant.Segment_name_index = util.getConfigStringValue("Segment_name_index",
                FrameDBConstant.config_type_block,
                FrameDBConstant.APP_CONFIGS);;//索引块

        logger.info("*****数据块名称开始******");
        logger.info("Segment_name_default=" + FrameDBConstant.Segment_name_default);
        logger.info("Segment_name_index=" + FrameDBConstant.Segment_name_index);
        logger.info("*****数据块名称结束******");
        logger.info("\n");
        /*
         * IP的前缀
         */
        FrameDBConstant.Ip_prefix = util.getConfigStringValue("Ip_prefix",
                FrameDBConstant.config_type_ip,
                FrameDBConstant.APP_CONFIGS);;
        logger.info("*****IP的前缀开始******");
        logger.info("Ip_prefix=" + FrameDBConstant.Ip_prefix);
        logger.info("*****IP的前缀结束******");
        logger.info("\n");

        FrameDBConstant.style_colors = this.getColors(util.getConfigStringArrayValue(
                util.getConfigStringValue("style_colors",
                FrameDBConstant.config_type_style_color,
                FrameDBConstant.APP_CONFIGS)));;
        logger.info("*****行颜色开始******");
        logger.info("style_colors=" + getArrayValue(FrameDBConstant.style_colors));
        logger.info("*****行颜色结束******");
        logger.info("\n");

        /*
         * 命令dbcc查找关键字

         */

        FrameDBConstant.Command_find_dbcc_keys_ip = util.getConfigStringArrayValue(
                util.getConfigStringValue("Command_find_dbcc_keys_ip",
                FrameDBConstant.config_type_dbcc,
                FrameDBConstant.APP_CONFIGS));//{"Connected to"};
        FrameDBConstant.Command_find_dbcc_key_space_used = util.getConfigStringValue("Command_find_dbcc_key_space_used",
                FrameDBConstant.config_type_dbcc,
                FrameDBConstant.APP_CONFIGS);
        FrameDBConstant.Command_find_dbcc_key_space_free = util.getConfigStringValue("Command_find_dbcc_key_space_free",
                FrameDBConstant.config_type_dbcc,
                FrameDBConstant.APP_CONFIGS);
        FrameDBConstant.Command_find_dbcc_key_unit = util.getConfigStringValue("Command_find_dbcc_key_unit",
                FrameDBConstant.config_type_dbcc,
                FrameDBConstant.APP_CONFIGS);

        logger.info("*****dbcc命令查找关键字开始******");
        logger.info("Command_find_dbcc_keys_ip=" + getArrayValue(FrameDBConstant.Command_find_dbcc_keys_ip));
        logger.info("Command_find_dbcc_key_space_used=" + FrameDBConstant.Command_find_dbcc_key_space_used);
        logger.info("Command_find_dbcc_key_space_free=" + FrameDBConstant.Command_find_dbcc_key_space_free);
        logger.info("Command_find_dbcc_key_unit=" + FrameDBConstant.Command_find_dbcc_key_unit);
        logger.info("*****dbcc命令查找关键字结束******");
        logger.info("\n");

        /*
         * 命令NTP配置
         */
        FrameDBConstant.SynMaxDiffInterval = util.getConfigIntValue("SynMaxDiffInterval",
                FrameDBConstant.config_type_ntp,
                FrameDBConstant.APP_CONFIGS);//最大的同步差异时间
        logger.info("*****NTP配置开始******");
        logger.info("SynMaxDiffInterval=" + FrameDBConstant.SynMaxDiffInterval);
        logger.info("*****NTP配置结束******");
        logger.info("\n");


    }

    private void initFtpThread() {
        FtpThread t = new FtpThread();
        t.start();
        logger.info("启动FTP定时线程");

    }

    private void initFileParseThread() {
        FileParseThread t = new FileParseThread();
        t.start();
        logger.info("启动文件分析定时线程");

    }

    private String getArrayValue(String[] arrayValue) {
        String value = "";
        for (int i = 0; i < arrayValue.length; i++) {
            value += arrayValue[i] + ",";
        }
        if (value.length() != 0) {
            value = value.substring(0, value.length() - 1);
        }
        return value;
    }

    private String[] getColors(String[] colors) {
        if (colors == null) {
            return colors;
        }
        for (int i = 0; i < colors.length; i++) {
            colors[i] = "#" + colors[i];
        }
        return colors;
    }
}