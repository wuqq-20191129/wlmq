/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.application;

import com.goldsign.settle.realtime.frame.audit.AuditBase;
import com.goldsign.settle.realtime.frame.constant.FrameCheckConstant;
import com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;
import com.goldsign.settle.realtime.frame.constant.FrameFlowConstant;
import com.goldsign.settle.realtime.frame.constant.FrameSysCfgConstant;
import com.goldsign.settle.realtime.frame.constant.FrameTacConstant;
import com.goldsign.settle.realtime.frame.constant.FrameThreadConstant;
import com.goldsign.settle.realtime.frame.dao.BalanceWaterNoDao;
import com.goldsign.settle.realtime.frame.dao.BlackListDao;
import com.goldsign.settle.realtime.frame.dao.BusinessSettleDao;
import com.goldsign.settle.realtime.frame.dao.CheckConfigDao;
import com.goldsign.settle.realtime.frame.dao.FileLogFtpDao;
import com.goldsign.settle.realtime.frame.dao.FileTccConfigDao;
import com.goldsign.settle.realtime.frame.dao.FlowDao;
import com.goldsign.settle.realtime.frame.dao.SysConfigDao;
import com.goldsign.settle.realtime.frame.dao.SysVersionDao;
import com.goldsign.settle.realtime.frame.download.DownloadAudit;
import com.goldsign.settle.realtime.frame.download.DownloadFileTcc;
import com.goldsign.settle.realtime.frame.export.ExportFile;
import com.goldsign.settle.realtime.frame.util.BusinessUtil;
import com.goldsign.settle.realtime.frame.util.LoggerUtil;
import com.goldsign.settle.realtime.frame.util.TaskUtil;
import com.goldsign.settle.realtime.frame.util.TradeUtil;
import com.goldsign.settle.realtime.frame.vo.FileTccConfigVo;
import com.goldsign.settle.realtime.frame.vo.ResultFromProc;
import com.goldsign.settle.realtime.frame.vo.TaskFinishControl;
import com.goldsign.settle.realtime.frame.vo.ThreadAttrVo;
import com.goldsign.settle.realtime.test.vo.SysVersionVo;
import java.io.File;
import java.util.HashMap;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class AppSettleBase {

    private static Logger logger = Logger.getLogger(AppSettleBase.class.getName());

    protected boolean isCanDownloadAudFile() {
        return TaskUtil.isCanDownloadAuditFile();
    }

    protected boolean isCanDownloadErrFile() {
        if (FrameCodeConstant.SYS_FLOW_CTR_DW_AUDITFILE.equals(FrameCodeConstant.SYS_FLOW_CTR_YES)) {
            return true;
        }
        else
            return false;

    }
    protected boolean isCanExportOctTrx() {
        if (FrameCodeConstant.SYS_FLOW_CTR_EXP_OCT_TRX.equals(FrameCodeConstant.SYS_FLOW_CTR_YES)) {
            return true;
        }
        else
            return false;

    }

    protected boolean isCanDownloadBlackList() {
        if (FrameCodeConstant.SYS_FLOW_CTR_DW_BL.equals(FrameCodeConstant.SYS_FLOW_CTR_YES)) {
            return true;
        }
        return false;
    }

    protected boolean isCanDownloadLccReconciliationFile() {
        if (FrameCodeConstant.SYS_FLOW_CTR_DW_AUDITLCC.equals(FrameCodeConstant.SYS_FLOW_CTR_YES)) {
            return true;
        }
        return false;
    }

    protected boolean isCanDownloadLccReconciliationFileForMobile() {
        if (FrameCodeConstant.SYS_FLOW_CTR_DW_AUDITLCC_MOBILE.equals(FrameCodeConstant.SYS_FLOW_CTR_YES)) {
            return true;
        }
        return false;
    }

    protected boolean isCanExport() {
        return true;
    }

    protected boolean isCanHandleOctImportSettle() {
        return TaskUtil.isCanHandleOctImportSettle();
    }

    protected boolean isCanHandleOctImportTrx() {
        return TaskUtil.isCanHandleOctImportTrx();
    }

    protected boolean isCanSettleForBatch(String balanceWaterNo, int balanceWaterNoSub, String finalSettleFlag) throws Exception {
        BusinessSettleDao dao = new BusinessSettleDao();
        return dao.canSettleBatch(balanceWaterNo, balanceWaterNoSub, finalSettleFlag);

    }

    protected boolean isNeedHandleBusSettle() {
        if (FrameCodeConstant.SETTLE_BUS_CONTROL_SETTLE == null || FrameCodeConstant.SETTLE_BUS_CONTROL_SETTLE.length() == 0) {
            return false;
        }
        if (FrameCodeConstant.SETTLE_BUS_CONTROL_SETTLE.equals(FrameCodeConstant.BUS_CONTROL_YES)) {
            return true;
        }
        return false;
    }

    protected boolean isLastSettle(String lastSettleFlag) {
        if (lastSettleFlag.equals(FrameCodeConstant.SETTLE_FINISH_YES)) {
            return true;
        }
        return false;
    }

    protected boolean isNeedHandleBusTrx() {
        if (FrameCodeConstant.SETTLE_BUS_CONTROL_TRX == null || FrameCodeConstant.SETTLE_BUS_CONTROL_TRX.length() == 0) {
            return false;
        }
        if (FrameCodeConstant.SETTLE_BUS_CONTROL_TRX.equals(FrameCodeConstant.BUS_CONTROL_YES)) {
            return true;
        }
        return false;
    }

    protected boolean isFinishForFileProcess(String balanceWaterNo) {
        return TaskUtil.isFinishFileHandled();
        /*
         String balanceDate = balanceWaterNo.substring(0, 8);
         String dateCur = DateHelper.dateOnlyToString(new Date());
         String hhmm = DateHelper.curentDateToHHMM();
         if (dateCur.compareTo(balanceDate) <= 0)//与清算日相同或小于
         {
         return false;
         }
         if (hhmm.compareTo(FrameCodeConstant.SETTLE_BEGIN_TIME) < 0)//大于清算日但小于设定的结算时间
         {
         return false;
         }
         return true;//大于清算日且大于等于设定的结算时间
         */
    }

    protected boolean isUnFinishedStep(String balanceWaterNo, String step) {
        boolean isUnFinished = true;
        try {
            isUnFinished = FlowDao.isUnFinishedStep(balanceWaterNo, step);
        } catch (Exception ex) {
            logger.error(ex);
        }
        return isUnFinished;
    }

    protected boolean isUnFinishedSteps(String balanceWaterNo, String[] steps) {
        boolean isUnFinished = true;
        try {
            isUnFinished = FlowDao.isUnFinishedSteps(balanceWaterNo, steps);
        } catch (Exception ex) {
            logger.error(ex);
        }
        return isUnFinished;
    }

    protected void waitFinish(Vector<TaskFinishControl> tfcs, String balanceWaterNo, int balanceWaterNoSub) {
        TaskUtil.waitAllTaskFinish(tfcs, true, LoggerUtil.getBalanceWaterInfo(balanceWaterNo, balanceWaterNoSub) + ":任务：交易文件及收益文件等预处理");
    }

    public void writeLogFtp(String fileName, int fileSize, String balanceWaterNo, int balanceWaterNoSub) throws Exception {
        FileLogFtpDao dao = new FileLogFtpDao();
        dao.insert(fileName, fileSize, balanceWaterNo, balanceWaterNo, balanceWaterNoSub);
    }

    public void writeLogFtpForOct(String fileName, int fileSize, String balanceWaterNo) throws Exception {
        FileLogFtpDao dao = new FileLogFtpDao();
        dao.insertForOct(fileName, fileSize, balanceWaterNo, balanceWaterNo);
    }

    protected Vector<TaskFinishControl> getTaskControl(int n) {
        return TaskUtil.getTaskControl(n);
    }

    protected void setTaskFinishControlByManu(Vector<TaskFinishControl> tfcs, int n, boolean isFinished) {
        TaskFinishControl tfc = tfcs.get(n);
        tfc.setFinished(isFinished);
    }

    protected void postHandleForStep(String balanceWaterNo, String step, String stepDesc) throws Exception {
        LoggerUtil.loggerLineForSectAll(logger, "清算流水号：" + balanceWaterNo + ":" + stepDesc);
        FlowDao.updateFinishFlag(balanceWaterNo, step, FrameFlowConstant.FLAG_FINISH, FrameFlowConstant.FLAG_OK);
    }

    protected boolean isSettleByManu() {
        if (FrameCodeConstant.CONTROL_MANU.equals(FrameCodeConstant.CONTROL_MANU_YES)) {
            return true;
        }
        return false;
    }

    protected void postHandleForStepForManu(String balanceWaterNo, String stepDesc) throws Exception {
        if (isSettleByManu()) {
            LoggerUtil.loggerLineForSectAll(logger, "清算流水号：" + balanceWaterNo + ":" + stepDesc);
            FlowDao.updateFinishFlagForManu(balanceWaterNo, FrameFlowConstant.FLAG_FINISH);
        }
    }

    protected void postHandleForStepForClearInValidDir(String balanceWaterNo) throws Exception {
        String badFilePath = TradeUtil.getDirForBalanceWaterNo(FrameCodeConstant.PATH_FILE_TRX_BCP_LOG_BAD, false);
        File path = new File(badFilePath);
        if (!path.isDirectory()) {
            return;
        }
        if (!path.exists()) {
            return;
        }
        File[] files = path.listFiles();
        if (files == null || files.length == 0) {
            path.delete();
            LoggerUtil.loggerLineForSectAll(logger, "清算流水号：" + balanceWaterNo + ":" + "目录：" + path + " 没有文件，删除该目录。");

        }

    }

    protected void postHandleForStepForFinish(String balanceWaterNo) throws Exception {
        this.postHandleForStepForManu(balanceWaterNo, "完成人工结算完成标识更新");
        this.postHandleForStepForClearInValidDir(balanceWaterNo);
    }

    protected void postHandleForStepSettle(String balanceWaterNo, String step, String stepDesc) throws Exception {
        LoggerUtil.loggerLineForSectAll(logger, "清算流水号：" + balanceWaterNo + ":" + stepDesc);
    }

    protected boolean preHandleForStep(String balanceWaterNo, String step) {
        boolean isUnfinished = this.isUnFinishedStep(balanceWaterNo, step);
        if (!isUnfinished) {
            LoggerUtil.loggerLineForSectAll(logger, "清算流水号：" + balanceWaterNo + ":已完成" + FrameFlowConstant.getStepKeyName(step) + "无需再重新处理");
        }
        if (isUnfinished) {
            this.updateFinishFlagForStart(balanceWaterNo, step);
        }
        return isUnfinished;
    }

    protected boolean preHandleForStepNoUpdate(String balanceWaterNo, String step) {
        boolean isUnfinished = this.isUnFinishedStep(balanceWaterNo, step);
        if (!isUnfinished) {
            LoggerUtil.loggerLineForSectAll(logger, "清算流水号：" + balanceWaterNo + ":已完成" + FrameFlowConstant.getStepKeyName(step) + "无需再重新处理");
        }
        /*
         if (isUnfinished) {
         this.updateFinishFlagForStart(balanceWaterNo, step);
         }
         * */
        return isUnfinished;
    }

    /*
     private boolean preHandleForStepBeforeWhile(String balanceWaterNo, String step) {
     boolean isUnfinished = this.isUnFinishedStep(balanceWaterNo, step);
     if (!isUnfinished) {
     LoggerUtil.loggerLineForSectAll(logger, "清算流水号：" + balanceWaterNo + ":已完成" + FrameFlowConstant.getStepKeyName(step) + "，无需再重新处理");
     } else {
     this.updateFinishFlagForStart(balanceWaterNo, step);
     }
     return isUnfinished;
     }
     */
    protected boolean preHandleForStepsSettle(String balanceWaterNo, String[] steps) {
        boolean isUnfinished = this.isUnFinishedSteps(balanceWaterNo, steps);
        if (!isUnfinished) {
            LoggerUtil.loggerLineForSectAll(logger, "清算流水号：" + balanceWaterNo + ":已完成" + FrameFlowConstant.getStepKeyNamesForSettle() + "无需再重新处理");
        }
        return isUnfinished;
    }

    protected int updateFinishFlagForStart(String balanceWaterNo, String step) {
        int n = 0;
        try {
            n = FlowDao.updateFinishFlagForStart(balanceWaterNo, step);
        } catch (Exception ex) {
            logger.error(ex);
        }
        return n;
    }

    protected void downloadAuditError(String balanceWaterNo) throws Exception {
        try {
            DownloadAudit da = new DownloadAudit();
            da.download(balanceWaterNo, AuditBase.DATA_TYPE_ERROR);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    protected void downloadAuditFtp(String balanceWaterNo) throws Exception {
        try {
            DownloadAudit da = new DownloadAudit();
            da.download(balanceWaterNo, AuditBase.DATA_TYPE_FTP);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    protected void downloadAuditSDF(String balanceWaterNo) throws Exception {
        try {
            DownloadAudit da = new DownloadAudit();
            da.download(balanceWaterNo, AuditBase.DATA_TYPE_SDF);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    protected void downloadAuditSDFMobile(String balanceWaterNo) throws Exception {
        try {
            DownloadAudit da = new DownloadAudit();
            da.download(balanceWaterNo, AuditBase.DATA_TYPE_SDF_MOBILE);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    protected void downloadAuditSDFNetPaid(String balanceWaterNo) throws Exception {
        try {
            DownloadAudit da = new DownloadAudit();
            da.download(balanceWaterNo, AuditBase.DATA_TYPE_SDF_NETPAID);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
     protected void downloadAuditSDFQrCode(String balanceWaterNo) throws Exception {
        try {
            DownloadAudit da = new DownloadAudit();
            da.download(balanceWaterNo, AuditBase.DATA_TYPE_SDF_QRCODE);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
      protected void downloadAuditSDFQrCodeMtr(String balanceWaterNo) throws Exception {
        try {
            DownloadAudit da = new DownloadAudit();
            da.download(balanceWaterNo, AuditBase.DATA_TYPE_SDF_QRCODE_MTR);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
      protected void downloadTccFilesOne(String balanceWaterNo,String dataType,String fileNameBase) throws Exception {
        try {
            DownloadFileTcc da = new DownloadFileTcc();
            da.download(balanceWaterNo,dataType,fileNameBase);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
       protected void downloadTccFiles(String balanceWaterNo) throws Exception {
        try {
            //获取须下发的TCC文件配置
            FileTccConfigDao dao = new FileTccConfigDao();
            Vector< FileTccConfigVo> configs =dao.getConfigs();
            if(configs ==null || configs.isEmpty()){
                logger.info("清算流水号："+balanceWaterNo+" TCC配置文件没有配置需要下发的文件。");
                return;
            }
             //生成及下发TCC文件
             
            for(FileTccConfigVo config:configs){
               this.downloadTccFilesOne(balanceWaterNo, config.getId(), config.getFileNameBase());
            }
           
            

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void downloadAuditTrxMobile(String balanceWaterNo) throws Exception {
        try {
            DownloadAudit da = new DownloadAudit();
            da.downloadMobileTrx(balanceWaterNo, AuditBase.DATA_TYPE_TRX_MOBILE);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    protected void downloadBlackList(String balanceWaterNo) throws Exception {
        try {
            BlackListDao dao = new BlackListDao();
            dao.downloadBlackList(balanceWaterNo);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    protected void exportForExternalSysOct(String balanceWaterNo, int balanceWaterNoSub) throws Exception {
        try {
            ExportFile ef = new ExportFile();
            ef.exportForOctTRX(balanceWaterNo, balanceWaterNoSub);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    protected void exportForExternalSysMobile(String balanceWaterNo, int balanceWaterNoSub) throws Exception {
        try {
            ExportFile ef = new ExportFile();
            ef.exportForMobileTRX(balanceWaterNo, balanceWaterNoSub);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    protected void exportForExternalSysForSettle(String balanceWaterNo, int balanceWaterNoSub) throws Exception {
        try {
            ExportFile ef = new ExportFile();
            ef.exportForOctForSettle(balanceWaterNo, balanceWaterNoSub);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    protected ResultFromProc getBalanceWater(String opId) throws Exception {
        ResultFromProc result;
        BalanceWaterNoDao dao = new BalanceWaterNoDao();
        result = dao.getBalanceWaterNo(opId);
        if (result.getRetCode() == 0) {
            FrameCodeConstant.BALANCE_WATER_NO = result.getRetValue();
            //必须有有效的清算流水号

        }
        TradeUtil.waitForValidBalanceWaterNo();
        return result;
    }

    protected ResultFromProc getBalanceWaterSub(String opId, String balanceWaterNo) throws Exception {
        ResultFromProc result;
        BalanceWaterNoDao dao = new BalanceWaterNoDao();
        result = dao.getBalanceWaterNoSub(opId, balanceWaterNo);
        if (result.getRetCode() == 0) {
            FrameCodeConstant.BALANCE_WATER_NO_SUB = result.getiRetValue();
            //必须有有效的清算流水号

        }
        return result;
    }

    protected void getSysConfigVersion() throws Exception {
        SysVersionDao dao = new SysVersionDao();
        SysVersionVo vo = dao.getSysVersion();
        String versionNo = vo.getVersionNo();
        LoggerUtil.loggerLineForSectAll(logger, "清算系统版本号：" + versionNo);

    }

    protected boolean isExistVariable(String path) {
        if (path == null || path.length() == 0) {
            return false;
        }
        if (path.contains("#")) {
            return true;
        }
        return false;
    }

    protected boolean isExistVariableHomeApp(String path) {
        if (path == null || path.length() == 0) {
            return false;
        }
        if (path.contains(FrameCodeConstant.VAR_PATH_BASE_HOME_APP)) {
            return true;
        }
        return false;
    }

    protected boolean isExistVariableHomeBusinessWork(String path) {
        if (path == null || path.length() == 0) {
            return false;
        }
        if (path.contains(FrameCodeConstant.VAR_PATH_BASE_HOME_BUSINESS_WORK)) {
            return true;
        }
        return false;
    }

    protected boolean isExistVariableHomeBusinessArchive(String path) {
        if (path == null || path.length() == 0) {
            return false;
        }
        if (path.contains(FrameCodeConstant.VAR_PATH_BASE_HOME_BUSINESS_ARCH)) {
            return true;
        }
        return false;
    }

    protected String getSysConfigPath(String path) throws Exception {
        if (!this.isExistVariable(path)) {
            return path;
        }
        if (this.isExistVariableHomeApp(path)) {
            path = path.replaceAll(FrameCodeConstant.VAR_PATH_BASE_HOME_APP, FrameCodeConstant.PATH_BASE_HOME_APP);
            return path;
        }
        if (this.isExistVariableHomeBusinessWork(path)) {
            path = path.replaceAll(FrameCodeConstant.VAR_PATH_BASE_HOME_BUSINESS_WORK, FrameCodeConstant.PATH_BASE_HOME_BUSINESS_WORK);
            return path;
        }
        if (this.isExistVariableHomeBusinessArchive(path)) {
            path = path.replaceAll(FrameCodeConstant.VAR_PATH_BASE_HOME_BUSINESS_ARCH, FrameCodeConstant.PATH_BASE_HOME_BUSINESS_ARCH);
            return path;
        }
        return path;

    }

    protected void getSysConfig() throws Exception {
        SysConfigDao dao = new SysConfigDao();
        HashMap<String, String> hm = dao.getSysConfig();
        /**
         * 基本目录
         */
        if (hm.containsKey(FrameSysCfgConstant.KEY_PATH_BASE_HOME_APP)) {
            //应用主目录
            FrameCodeConstant.PATH_BASE_HOME_APP = hm.get(FrameSysCfgConstant.KEY_PATH_BASE_HOME_APP);
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_PATH_BASE_HOME_BUSINESS_WORK)) {
            //业务工作目录
            FrameCodeConstant.PATH_BASE_HOME_BUSINESS_WORK = hm.get(FrameSysCfgConstant.KEY_PATH_BASE_HOME_BUSINESS_WORK);
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_PATH_BASE_HOME_BUSINESS_ARCH)) {
            //业务归档目录
            FrameCodeConstant.PATH_BASE_HOME_BUSINESS_ARCH = hm.get(FrameSysCfgConstant.KEY_PATH_BASE_HOME_BUSINESS_ARCH);
        }
        /**
         * 交易文件、BCP文件、审计文件相关相关
         */
        if (hm.containsKey(FrameSysCfgConstant.KEY_PATH_TRX)) {
            //上传的交易文件目录
            FrameCodeConstant.PATH_FILE_TRX = hm.get(FrameSysCfgConstant.KEY_PATH_TRX);
            FrameCodeConstant.PATH_FILE_TRX = this.getSysConfigPath(FrameCodeConstant.PATH_FILE_TRX);
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_PATH_TRX_HIS)) {
            //上传的交易文件历史目录
            FrameCodeConstant.PATH_FILE_TRX_HIS = hm.get(FrameSysCfgConstant.KEY_PATH_TRX_HIS);
            FrameCodeConstant.PATH_FILE_TRX_HIS = this.getSysConfigPath(FrameCodeConstant.PATH_FILE_TRX_HIS);
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_PATH_TRX_ERROR)) {
            //上传的交易文件错误历史目录
            FrameCodeConstant.PATH_FILE_TRX_HIS_ERROR = hm.get(FrameSysCfgConstant.KEY_PATH_TRX_ERROR);
            FrameCodeConstant.PATH_FILE_TRX_HIS_ERROR = this.getSysConfigPath(FrameCodeConstant.PATH_FILE_TRX_HIS_ERROR);
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_PATH_BCP)) {
            //BCP的交易文件目录
            FrameCodeConstant.PATH_FILE_TRX_BCP = hm.get(FrameSysCfgConstant.KEY_PATH_BCP);
            FrameCodeConstant.PATH_FILE_TRX_BCP = this.getSysConfigPath(FrameCodeConstant.PATH_FILE_TRX_BCP);
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_PATH_BCP_CTL)) {
            ///BCP的控制文件目录
            FrameCodeConstant.PATH_FILE_TRX_BCP_CTL = hm.get(FrameSysCfgConstant.KEY_PATH_BCP_CTL);
            FrameCodeConstant.PATH_FILE_TRX_BCP_CTL = this.getSysConfigPath(FrameCodeConstant.PATH_FILE_TRX_BCP_CTL);
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_PATH_BCP_LOG)) {
            ///BCP的日志文件目录
            FrameCodeConstant.PATH_FILE_TRX_BCP_LOG = hm.get(FrameSysCfgConstant.KEY_PATH_BCP_LOG);
            FrameCodeConstant.PATH_FILE_TRX_BCP_LOG = this.getSysConfigPath(FrameCodeConstant.PATH_FILE_TRX_BCP_LOG);
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_PATH_BCP_BAD)) {
            ///BCP的错误记录日志文件目录
            FrameCodeConstant.PATH_FILE_TRX_BCP_LOG_BAD = hm.get(FrameSysCfgConstant.KEY_PATH_BCP_BAD);
            FrameCodeConstant.PATH_FILE_TRX_BCP_LOG_BAD = this.getSysConfigPath(FrameCodeConstant.PATH_FILE_TRX_BCP_LOG_BAD);
        }

        if (hm.containsKey(FrameSysCfgConstant.KEY_PATH_AUDIT)) {
            ///审计文件目录
            FrameCodeConstant.PATH_FILE_AUDIT = hm.get(FrameSysCfgConstant.KEY_PATH_AUDIT);
            FrameCodeConstant.PATH_FILE_AUDIT = this.getSysConfigPath(FrameCodeConstant.PATH_FILE_AUDIT);
        }
        /**
         * TAC校验相关
         */
        String socketServerProduct = "";
        int socketPortProduct = 8;
        String socketServerTest = "";
        int socketPortTest = 8;
        if (hm.containsKey(FrameSysCfgConstant.KEY_TAC_KEY_TEST_FLAG)) {
            ///tac生成使用测试密钥还是正式密钥 0：正式密钥 1：测试密钥
            FrameTacConstant.VALUE_KEY_TEST_FLAG = hm.get(FrameSysCfgConstant.KEY_TAC_KEY_TEST_FLAG);
        }

        if (hm.containsKey(FrameSysCfgConstant.KEY_TAC_SOCKET_SERVER_PRODUCT)) {
            ///tac校验加密机地址
            socketServerProduct = hm.get(FrameSysCfgConstant.KEY_TAC_SOCKET_SERVER_PRODUCT);
            // FrameTacConstant.SOCKET_SERVER = hm.get(FrameSysCfgConstant.KEY_TAC_SOCKET_SERVER_PRODUCT);
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_TAC_SOCKET_PORT_PRODUCT)) {
            ///tac校验加密机端口
            socketPortProduct = Integer.parseInt(hm.get(FrameSysCfgConstant.KEY_TAC_SOCKET_PORT_PRODUCT));
            // FrameTacConstant.SOCKET_PORT = Integer.parseInt(hm.get(FrameSysCfgConstant.KEY_TAC_SOCKET_PORT_PRODUCT));
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_TAC_SOCKET_SERVER_TEST)) {
            ///tac校验加密机地址
            socketServerTest = hm.get(FrameSysCfgConstant.KEY_TAC_SOCKET_SERVER_TEST);
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_TAC_SOCKET_PORT_TEST)) {
            ///tac校验加密机端口
            socketPortTest = Integer.parseInt(hm.get(FrameSysCfgConstant.KEY_TAC_SOCKET_PORT_TEST));
        }
        //根据密钥使用控制标识设置加密机及端口
        this.setSocketServerAndPort(FrameTacConstant.VALUE_KEY_TEST_FLAG, socketServerProduct, socketPortProduct, socketServerTest, socketPortTest);

        if (hm.containsKey(FrameSysCfgConstant.KEY_TAC_SOCKET_TIMEOUT)) {
            ///tac校验加密机连接超时
            FrameTacConstant.SOCKET_TIME_OUT = Integer.parseInt(hm.get(FrameSysCfgConstant.KEY_TAC_SOCKET_TIMEOUT));
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_TAC_KEY_ALG)) {
            ///tac校验密钥算法
            FrameTacConstant.VALUE_KEY_ALG = hm.get(FrameSysCfgConstant.KEY_TAC_KEY_ALG);
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_TAC_KEY_VER)) {
            ///tac校验密钥版本
            FrameTacConstant.VALUE_KEY_VER = hm.get(FrameSysCfgConstant.KEY_TAC_KEY_VER);
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_TAC_KEY_GRP)) {
            ///tac校验密钥组
            FrameTacConstant.VALUE_KEY_GRP = hm.get(FrameSysCfgConstant.KEY_TAC_KEY_GRP);
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_TAC_KEY_ID)) {
            ///tac校验密钥索引
            FrameTacConstant.VALUE_KEY_ID = hm.get(FrameSysCfgConstant.KEY_TAC_KEY_ID);
        }
//add by hejj 20180625

        if (hm.containsKey(FrameSysCfgConstant.KEY_TAC_IDX_TEST_CPU)) {
            ///cpu卡测试密钥索引
            FrameTacConstant.VALUE_KEY_IDX_TEST_CPU = hm.get(FrameSysCfgConstant.KEY_TAC_IDX_TEST_CPU);
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_TAC_IDX_TEST_M1)) {
            ///m1或单程票卡测试密钥索引
            FrameTacConstant.VALUE_KEY_IDX_TEST_M1 = hm.get(FrameSysCfgConstant.KEY_TAC_IDX_TEST_M1);
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_TAC_IDX_PRODUCT_CPU)) {
            ///cpu卡正式密钥索引
            FrameTacConstant.VALUE_KEY_IDX_PRODUCT_CPU = hm.get(FrameSysCfgConstant.KEY_TAC_IDX_PRODUCT_CPU);
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_TAC_IDX_PRODUCT_M1)) {
            ///cpu卡正式密钥索引
            FrameTacConstant.VALUE_KEY_IDX_PRODUCT_M1 = hm.get(FrameSysCfgConstant.KEY_TAC_IDX_PRODUCT_M1);
        }

        /**
         * 流程控制相关
         */
        if (hm.containsKey(FrameSysCfgConstant.KEY_SYS_SETTLE_TIME)) {
            ///系统开始结算时间
            FrameCodeConstant.SETTLE_BEGIN_TIME = hm.get(FrameSysCfgConstant.KEY_SYS_SETTLE_TIME);
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_SYS_SETTLE_TIME_DOWNLOAD)) {
            ///系统下发审计文件开始时间
            FrameCodeConstant.SETTLE_DOWNLOAD_TIME = hm.get(FrameSysCfgConstant.KEY_SYS_SETTLE_TIME_DOWNLOAD);
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_FLOW_INTERVAL)) {
            ///清算流程整体控制时间间隔
            FrameCodeConstant.SETTLE_FLOW_SLEEP_TIME = Integer.parseInt(hm.get(FrameSysCfgConstant.KEY_FLOW_INTERVAL));
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_FLOW_INTERVAL_FILE)) {
            ///清算流程文件处理时间间隔
            FrameCodeConstant.SETTLE_FLOW_SLEEP_TIME_FILE = Integer.parseInt(hm.get(FrameSysCfgConstant.KEY_FLOW_INTERVAL_FILE));
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_FLOW_BUS_CONTROL_TRX)) {
            ///是否处理与公交交互交易数据
            FrameCodeConstant.SETTLE_BUS_CONTROL_TRX = hm.get(FrameSysCfgConstant.KEY_FLOW_BUS_CONTROL_TRX);
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_FLOW_BUS_CONTROL_SETTLE)) {
            ///是否处理与公交交互结算数据
            FrameCodeConstant.SETTLE_BUS_CONTROL_SETTLE = hm.get(FrameSysCfgConstant.KEY_FLOW_BUS_CONTROL_SETTLE);
        }

        if (hm.containsKey(FrameSysCfgConstant.KEY_FLOW_CONTROL_MANU)) {
            ///是否人工处理结算数据
            FrameCodeConstant.CONTROL_MANU = hm.get(FrameSysCfgConstant.KEY_FLOW_CONTROL_MANU);
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_FLOW_CONTROL_PRESETTLE_ONLY)) {
            ///是否仅预处理
            FrameCodeConstant.CONTROL_PRESETTLE_ONLY = hm.get(FrameSysCfgConstant.KEY_FLOW_CONTROL_PRESETTLE_ONLY);
        }

        //人工清算,交易文件的基本目录应该保存。hejj 20140520
        if (this.isSettleByManu()) {
            FrameCodeConstant.PATH_FILE_TRX_MANU = FrameCodeConstant.PATH_FILE_TRX;
            FrameCodeConstant.PATH_FILE_TRX_MANU = this.getSysConfigPath(FrameCodeConstant.PATH_FILE_TRX_MANU);
        }
        //

        //JMS相关
        /*
        if (hm.containsKey(FrameSysCfgConstant.KEY_JMS_URL)) {
            ///JMS连接ULR
            FrameCodeConstant.JMS_URL = hm.get(FrameSysCfgConstant.KEY_JMS_URL);
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_JMS_CONTEXT_FACTORY)) {
            ///JMS语境工厂
            FrameCodeConstant.JMS_CONTEXT_FACTORY = hm.get(FrameSysCfgConstant.KEY_JMS_CONTEXT_FACTORY);
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_JMS_CONNECT_FACTORY)) {
            ///JMS连接工厂
            FrameCodeConstant.JMS_CONNECTION_FACTORY = hm.get(FrameSysCfgConstant.KEY_JMS_CONNECT_FACTORY);
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_JMS_QUEUE)) {
            ///JMS队列
            FrameCodeConstant.JMS_QUEUE_NAME = hm.get(FrameSysCfgConstant.KEY_JMS_QUEUE);
        }
         */
        //LCC对账文件下发
        if (hm.containsKey(FrameSysCfgConstant.KEY_LCC_DOWNLOAD_PATH)) {
            ///下发的LCC审计文件目录
            FrameCodeConstant.PATH_FILE_AUDIT_LCC = hm.get(FrameSysCfgConstant.KEY_LCC_DOWNLOAD_PATH);
            FrameCodeConstant.PATH_FILE_AUDIT_LCC = this.getSysConfigPath(FrameCodeConstant.PATH_FILE_AUDIT_LCC);
        }
        //公交IC卡数据导出、返回相关
        //交易导出相关
        if (hm.containsKey(FrameSysCfgConstant.KEY_OCT_EXPORT_TRX_PATH)) {
            ///公交IC卡交易导出文件目录
            FrameCodeConstant.PATH_FILE_OCT_EXPORT_TRX_FILE = hm.get(FrameSysCfgConstant.KEY_OCT_EXPORT_TRX_PATH);
            FrameCodeConstant.PATH_FILE_OCT_EXPORT_TRX_FILE = this.getSysConfigPath(FrameCodeConstant.PATH_FILE_OCT_EXPORT_TRX_FILE);
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_OCT_EXPORT_TRX_PATH_ZIP)) {
            ///公交IC卡交易导出压缩文件目录
            FrameCodeConstant.PATH_FILE_OCT_EXPORT_TRX_ZIP = hm.get(FrameSysCfgConstant.KEY_OCT_EXPORT_TRX_PATH_ZIP);
            FrameCodeConstant.PATH_FILE_OCT_EXPORT_TRX_ZIP = this.getSysConfigPath(FrameCodeConstant.PATH_FILE_OCT_EXPORT_TRX_ZIP);
        }

        if (hm.containsKey(FrameSysCfgConstant.KEY_OCT_EXPORT_TRX_HIS_PATH)) {
            ///公交IC卡交易导出历史文件目录
            FrameCodeConstant.PATH_FILE_OCT_EXPORT_TRX_FILE_HIS = hm.get(FrameSysCfgConstant.KEY_OCT_EXPORT_TRX_HIS_PATH);
            FrameCodeConstant.PATH_FILE_OCT_EXPORT_TRX_FILE_HIS = this.getSysConfigPath(FrameCodeConstant.PATH_FILE_OCT_EXPORT_TRX_FILE_HIS);
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_OCT_EXPORT_TRX_ERR_PATH)) {
            ///公交IC卡交易导出错误文件目录
            FrameCodeConstant.PATH_FILE_OCT_EXPORT_TRX_FILE_ERROR = hm.get(FrameSysCfgConstant.KEY_OCT_EXPORT_TRX_ERR_PATH);
            FrameCodeConstant.PATH_FILE_OCT_EXPORT_TRX_FILE_ERROR = this.getSysConfigPath(FrameCodeConstant.PATH_FILE_OCT_EXPORT_TRX_FILE_ERROR);
        }
        /**
         * *********************************************************************************
         */
        //结算导出相关
        if (hm.containsKey(FrameSysCfgConstant.KEY_OCT_EXPORT_SETTLE_PATH)) {
            ///结算导出文件目录
            FrameCodeConstant.PATH_FILE_OCT_EXPORT_SETTLE_FILE = hm.get(FrameSysCfgConstant.KEY_OCT_EXPORT_SETTLE_PATH);
            FrameCodeConstant.PATH_FILE_OCT_EXPORT_SETTLE_FILE = this.getSysConfigPath(FrameCodeConstant.PATH_FILE_OCT_EXPORT_SETTLE_FILE);
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_OCT_EXPORT_SETTLE_PATH_ZIP)) {
            ///结算导出压缩文件目录
            FrameCodeConstant.PATH_FILE_OCT_EXPORT_SETTLE_ZIP = hm.get(FrameSysCfgConstant.KEY_OCT_EXPORT_SETTLE_PATH_ZIP);
            FrameCodeConstant.PATH_FILE_OCT_EXPORT_SETTLE_ZIP = this.getSysConfigPath(FrameCodeConstant.PATH_FILE_OCT_EXPORT_SETTLE_ZIP);
        }

        if (hm.containsKey(FrameSysCfgConstant.KEY_OCT_EXPORT_SETTLE_HIS_PATH)) {
            ///结算导出历史文件目录
            FrameCodeConstant.PATH_FILE_OCT_EXPORT_SETTLE_FILE_HIS = hm.get(FrameSysCfgConstant.KEY_OCT_EXPORT_SETTLE_HIS_PATH);
            FrameCodeConstant.PATH_FILE_OCT_EXPORT_SETTLE_FILE_HIS = this.getSysConfigPath(FrameCodeConstant.PATH_FILE_OCT_EXPORT_SETTLE_FILE_HIS);
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_OCT_EXPORT_SETTLE_ERR_PATH)) {
            ///结算导出错误文件目录
            FrameCodeConstant.PATH_FILE_OCT_EXPORT_SETTLE_FILE_ERROR = hm.get(FrameSysCfgConstant.KEY_OCT_EXPORT_SETTLE_ERR_PATH);
            FrameCodeConstant.PATH_FILE_OCT_EXPORT_SETTLE_FILE_ERROR = this.getSysConfigPath(FrameCodeConstant.PATH_FILE_OCT_EXPORT_SETTLE_FILE_ERROR);
        }
        /**
         * ******************************************************************************
         */
        //结算导入相关
        if (hm.containsKey(FrameSysCfgConstant.KEY_OCT_IMPORT_SETTLE_PATH)) {
            ///公交IC卡交易返回文件目录
            FrameCodeConstant.PATH_FILE_OCT_IMPORT_SETTLE_FILE = hm.get(FrameSysCfgConstant.KEY_OCT_IMPORT_SETTLE_PATH);
            FrameCodeConstant.PATH_FILE_OCT_IMPORT_SETTLE_FILE = this.getSysConfigPath(FrameCodeConstant.PATH_FILE_OCT_IMPORT_SETTLE_FILE);
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_OCT_IMPORT_SETTLE_PATH_ZIP)) {
            ///公交IC卡交易返回压缩文件目录
            FrameCodeConstant.PATH_FILE_OCT_IMPORT_SETTLE_ZIP = hm.get(FrameSysCfgConstant.KEY_OCT_IMPORT_SETTLE_PATH_ZIP);
            FrameCodeConstant.PATH_FILE_OCT_IMPORT_SETTLE_ZIP = this.getSysConfigPath(FrameCodeConstant.PATH_FILE_OCT_IMPORT_SETTLE_ZIP);
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_OCT_IMPORT_SETTLE_PATH_HIS)) {
            ///公交IC卡交易返回文件历史目录
            FrameCodeConstant.PATH_FILE_OCT_IMPORT_SETTLE_FILE_HIS = hm.get(FrameSysCfgConstant.KEY_OCT_IMPORT_SETTLE_PATH_HIS);
            FrameCodeConstant.PATH_FILE_OCT_IMPORT_SETTLE_FILE_HIS = this.getSysConfigPath(FrameCodeConstant.PATH_FILE_OCT_IMPORT_SETTLE_FILE_HIS);
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_OCT_IMPORT_SETTLE_PATH_ERR)) {
            ///公交IC卡交易返回文件错误目录
            FrameCodeConstant.PATH_FILE_OCT_IMPORT_SETTLE_FILE_ERROR = hm.get(FrameSysCfgConstant.KEY_OCT_IMPORT_SETTLE_PATH_ERR);
            FrameCodeConstant.PATH_FILE_OCT_IMPORT_SETTLE_FILE_ERROR = this.getSysConfigPath(FrameCodeConstant.PATH_FILE_OCT_IMPORT_SETTLE_FILE_ERROR);
        }
        /**
         * *********************************************************
         */
        //交易导入相关
        if (hm.containsKey(FrameSysCfgConstant.KEY_OCT_IMPORT_TRX_PATH)) {
            ///公交上传交易文件目录
            FrameCodeConstant.PATH_FILE_OCT_IMPORT_TRX_FILE = hm.get(FrameSysCfgConstant.KEY_OCT_IMPORT_TRX_PATH);
            FrameCodeConstant.PATH_FILE_OCT_IMPORT_TRX_FILE = this.getSysConfigPath(FrameCodeConstant.PATH_FILE_OCT_IMPORT_TRX_FILE);
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_OCT_IMPORT_TRX_PATH_ZIP)) {
            ///公交上传交易压缩文件目录
            FrameCodeConstant.PATH_FILE_OCT_IMPORT_TRX_ZIP = hm.get(FrameSysCfgConstant.KEY_OCT_IMPORT_TRX_PATH_ZIP);
            FrameCodeConstant.PATH_FILE_OCT_IMPORT_TRX_ZIP = this.getSysConfigPath(FrameCodeConstant.PATH_FILE_OCT_IMPORT_TRX_ZIP);
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_OCT_IMPORT_TRX_PATH_HIS)) {
            ///公交上传交易文件历史目录
            FrameCodeConstant.PATH_FILE_OCT_IMPORT_TRX_FILE_HIS = hm.get(FrameSysCfgConstant.KEY_OCT_IMPORT_TRX_PATH_HIS);
            FrameCodeConstant.PATH_FILE_OCT_IMPORT_TRX_FILE_HIS = this.getSysConfigPath(FrameCodeConstant.PATH_FILE_OCT_IMPORT_TRX_FILE_HIS);
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_OCT_IMPORT_TRX_PATH_ERR)) {
            ///公交上传交易文件错误目录
            FrameCodeConstant.PATH_FILE_OCT_IMPORT_TRX_FILE_ERROR = hm.get(FrameSysCfgConstant.KEY_OCT_IMPORT_TRX_PATH_ERR);
            FrameCodeConstant.PATH_FILE_OCT_IMPORT_TRX_FILE_ERROR = this.getSysConfigPath(FrameCodeConstant.PATH_FILE_OCT_IMPORT_TRX_FILE_ERROR);
        }

        //与公交交换相关
        if (hm.containsKey(FrameSysCfgConstant.KEY_FILE_OCT_UPLOAD)) {
            ///上传目录
            FrameCodeConstant.PATH_FILE_OCT_UPLOAD = hm.get(FrameSysCfgConstant.KEY_FILE_OCT_UPLOAD);
            FrameCodeConstant.PATH_FILE_OCT_UPLOAD = this.getSysConfigPath(FrameCodeConstant.PATH_FILE_OCT_UPLOAD);
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_FILE_OCT_DOWNLOAD)) {
            ///下载目录
            FrameCodeConstant.PATH_FILE_OCT_DOWNLOAD = hm.get(FrameSysCfgConstant.KEY_FILE_OCT_DOWNLOAD);
            FrameCodeConstant.PATH_FILE_OCT_DOWNLOAD = this.getSysConfigPath(FrameCodeConstant.PATH_FILE_OCT_DOWNLOAD);
        }
        //公交文件上传相关时间
        if (hm.containsKey(FrameSysCfgConstant.KEY_FILE_OCT_UPLOAD)) {
            ///上传目录
            FrameCodeConstant.PATH_FILE_OCT_UPLOAD = hm.get(FrameSysCfgConstant.KEY_FILE_OCT_UPLOAD);
            FrameCodeConstant.PATH_FILE_OCT_UPLOAD = this.getSysConfigPath(FrameCodeConstant.PATH_FILE_OCT_UPLOAD);
        }

        //手机支付控制 added by hejj 20160107
        if (hm.containsKey(FrameSysCfgConstant.KEY_BUSINESS_MOBILE_CONTROL)) {
            ///手机支付控制
            FrameCodeConstant.BUSINESS_MOBILE_CONTROL = hm.get(FrameSysCfgConstant.KEY_BUSINESS_MOBILE_CONTROL);
        }

        if (hm.containsKey(FrameSysCfgConstant.KEY_MOBILE_PATH_MTX)) {
            ///手机支付交易下发路径 added by hejj 20160313
            FrameCodeConstant.PATH_MOBILE_MTX = hm.get(FrameSysCfgConstant.KEY_MOBILE_PATH_MTX);
            FrameCodeConstant.PATH_MOBILE_MTX = this.getSysConfigPath(FrameCodeConstant.PATH_MOBILE_MTX);
        }

        if (hm.containsKey(FrameSysCfgConstant.KEY_MOBILE_PATH_EXPORT_TRX_HIS)) {
            ///手机支付交易归档历史路径
            FrameCodeConstant.MOBILE_PATH_EXPORT_TRX_HIS = hm.get(FrameSysCfgConstant.KEY_MOBILE_PATH_EXPORT_TRX_HIS);
            FrameCodeConstant.MOBILE_PATH_EXPORT_TRX_HIS = this.getSysConfigPath(FrameCodeConstant.MOBILE_PATH_EXPORT_TRX_HIS);
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_MOBILE_PATH_EXPORT_TRX_ERR)) {
            ///手机支付交易归档错误路径
            FrameCodeConstant.MOBILE_PATH_EXPORT_TRX_ERR = hm.get(FrameSysCfgConstant.KEY_MOBILE_PATH_EXPORT_TRX_ERR);
            FrameCodeConstant.MOBILE_PATH_EXPORT_TRX_ERR = this.getSysConfigPath(FrameCodeConstant.MOBILE_PATH_EXPORT_TRX_ERR);
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_MOBILE_PATH_EXPORT_TRX_FILE)) {
            ///手机支付交易文件生成临时路径
            FrameCodeConstant.MOBILE_PATH_EXPORT_TRX_FILE = hm.get(FrameSysCfgConstant.KEY_MOBILE_PATH_EXPORT_TRX_FILE);
            FrameCodeConstant.MOBILE_PATH_EXPORT_TRX_FILE = this.getSysConfigPath(FrameCodeConstant.MOBILE_PATH_EXPORT_TRX_FILE);
        }

        //手机支付消费下发控制 added by hejj 20160324
        if (hm.containsKey(FrameSysCfgConstant.KEY_BUSINESS_MOBILE_CONTROL_TRX_DOWNLOAD)) {
            ///手机支付消费下发控制
            FrameCodeConstant.BUSINESS_MOBILE_CONTROL_TRX_DOWNLOAD = hm.get(FrameSysCfgConstant.KEY_BUSINESS_MOBILE_CONTROL_TRX_DOWNLOAD);
        }

        //公交文件上传最迟时间点
        if (hm.containsKey(FrameSysCfgConstant.KEY_SYS_SETTLE_OCT_IMPORT_TRX_LIMIT_TIME)) {
            ///公交上传交易最迟时间点
            FrameCodeConstant.SYS_SETTLE_OCT_IMPORT_TRX_LIMIT_TIME = hm.get(FrameSysCfgConstant.KEY_SYS_SETTLE_OCT_IMPORT_TRX_LIMIT_TIME);
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_SYS_SETTLE_OCT_IMPORT_SETTLE_LIMIT_TIME)) {
            ///公交上传结算结果最迟时间点
            FrameCodeConstant.SYS_SETTLE_OCT_IMPORT_SETTLE_LIMIT_TIME = hm.get(FrameSysCfgConstant.KEY_SYS_SETTLE_OCT_IMPORT_SETTLE_LIMIT_TIME);
        }

        //互联网支付控制 added by hejj 20161124
        if (hm.containsKey(FrameSysCfgConstant.KEY_BUSINESS_NETPAID_CONTROL)) {
            ///互联网支付控制
            FrameCodeConstant.BUSINESS_NETPAID_CONTROL = hm.get(FrameSysCfgConstant.KEY_BUSINESS_NETPAID_CONTROL);
        }

        //文件处理与文件生成时间间隔 added by hejj 20161226
        if (hm.containsKey(FrameSysCfgConstant.KEY_FILE_PROCESSED_BEFORE_TIME)) {
            ///文件处理与文件生成时间间隔
            FrameCodeConstant.FILE_PROCESSED_BEFORE = Long.parseLong(hm.get(FrameSysCfgConstant.KEY_FILE_PROCESSED_BEFORE_TIME));
        }

        //批次文件数量限制 
        if (hm.containsKey(FrameSysCfgConstant.KEY_SYS_SETTLE_FILE_ONCE_LIMIT_FLAG)) {
            ///批次文件数量限制标识0：不限制 1：限制
            FrameCodeConstant.SYS_SETTLE_FILE_ONCE_LIMIT_FLAG = hm.get(FrameSysCfgConstant.KEY_SYS_SETTLE_FILE_ONCE_LIMIT_FLAG);
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_SYS_SETTLE_FILE_ONCE_LIMIT_NUMBER)) {
            ///批次文件数量限制数量
            FrameCodeConstant.SYS_SETTLE_FILE_ONCE_LIMIT_NUMBER = Integer.parseInt(hm.get(FrameSysCfgConstant.KEY_SYS_SETTLE_FILE_ONCE_LIMIT_NUMBER));
        }

        //流程控制文件下发（FTP审计、错误审计、LCC对账、手机支付对账、一卡通交易导出）20181212 by hejj
        if (hm.containsKey(FrameSysCfgConstant.KEY_SYS_FLOW_CTR_DOWNLOAD_BLACKLIST)) {
            ///是否下发黑名单控制
            FrameCodeConstant.SYS_FLOW_CTR_DW_BL = hm.get(FrameSysCfgConstant.KEY_SYS_FLOW_CTR_DOWNLOAD_BLACKLIST);
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_SYS_FLOW_CTR_DOWNLOAD_AUDITFILE)) {
            ///是否下发FTP审计、错误文件
            FrameCodeConstant.SYS_FLOW_CTR_DW_AUDITFILE = hm.get(FrameSysCfgConstant.KEY_SYS_FLOW_CTR_DOWNLOAD_AUDITFILE);
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_SYS_FLOW_CTR_DOWNLOAD_AUDITLCC)) {
            ///是否下发LCC对账文件
            FrameCodeConstant.SYS_FLOW_CTR_DW_AUDITLCC = hm.get(FrameSysCfgConstant.KEY_SYS_FLOW_CTR_DOWNLOAD_AUDITLCC);
        }
        if (hm.containsKey(FrameSysCfgConstant.KEY_SYS_FLOW_CTR_DOWNLOAD_AUDITLCC_MOBILE)) {
            ///是否下发手机支付对账文件
            FrameCodeConstant.SYS_FLOW_CTR_DW_AUDITLCC_MOBILE = hm.get(FrameSysCfgConstant.KEY_SYS_FLOW_CTR_DOWNLOAD_AUDITLCC_MOBILE);
        }
        
         if (hm.containsKey(FrameSysCfgConstant.KEY_SYS_FLOW_CTR_EXP_OCT_TRX)) {
            ///是否导出一卡通消费
            FrameCodeConstant.SYS_FLOW_CTR_EXP_OCT_TRX = hm.get(FrameSysCfgConstant.KEY_SYS_FLOW_CTR_EXP_OCT_TRX);
        }
         
          //二维码平台控制 added by hejj 20180614
        if (hm.containsKey(FrameSysCfgConstant.KEY_BUSINESS_QRCODE_CONTROL)) {
            ///二维码平台是否启用控制
            FrameCodeConstant.BUSINESS_QRCODE_CONTROL = hm.get(FrameSysCfgConstant.KEY_BUSINESS_QRCODE_CONTROL);
        }
        
          //地铁二维码平台控制 added by hejj 20200706
        if (hm.containsKey(FrameSysCfgConstant.KEY_BUSINESS_QRCODE_MTR_CONTROL)) {
            ///二维码平台是否启用控制
            FrameCodeConstant.BUSINESS_QRCODE_MTR_CONTROL = hm.get(FrameSysCfgConstant.KEY_BUSINESS_QRCODE_MTR_CONTROL);
        }
        
        
            //TCC平台控制 added by hejj 20180620
        if (hm.containsKey(FrameSysCfgConstant.KEY_BUSINESS_TCC_CONTROL)) {
            ///TCC平台是否启用控制
            FrameCodeConstant.BUSINESS_TCC_CONTROL = hm.get(FrameSysCfgConstant.KEY_BUSINESS_TCC_CONTROL);
        }
          //TCC历史数据下发路径 added by hejj 20180620
        if (hm.containsKey(FrameSysCfgConstant.KEY_FILE_TCC_DOWNLOAD)) {
            FrameCodeConstant.PATH_FILE_TCC_DOWNLOAD = this.getSysConfigPath(hm.get(FrameSysCfgConstant.KEY_FILE_TCC_DOWNLOAD));
        }


        /*
         //交易文件行分隔符控制
         if (hm.containsKey(FrameSysCfgConstant.KEY_BUSINESS_FILE_DELIMIT_CONTROL)) {
         ///易文件行分隔符控制
         FrameCodeConstant.DELIMIT_CONTROL = hm.get(FrameSysCfgConstant.KEY_BUSINESS_FILE_DELIMIT_CONTROL);
         if( FrameCodeConstant.DELIMIT_CONTROL.equals(FrameCodeConstant.DELIMIT_CONTROL_TEXT)){
                
                
         }
         }
         */
        /**
         * *********************************************************
         */
    }

    private void setSocketServerAndPort(String flag, String socketServerProduct, int socketPortProduct, String socketServerTest, int socketPortTest) {
        if (flag.equals(FrameTacConstant.KEY_TEST_FLAG)) {
            FrameTacConstant.SOCKET_SERVER = socketServerTest;
            FrameTacConstant.SOCKET_PORT = socketPortTest;
        } else {
            FrameTacConstant.SOCKET_SERVER = socketServerProduct;
            FrameTacConstant.SOCKET_PORT = socketPortProduct;
        }

    }

    protected ThreadAttrVo getThreadAttrVoForBcp() {
        ThreadAttrVo vo = new ThreadAttrVo();
        vo.setMaxSearchNum(FrameThreadConstant.maxSearchNumForBcp);
        vo.setMaxThreadNumber(FrameThreadConstant.maxThreadNumberForBcp);
        vo.setMsgHandleClass(FrameThreadConstant.CLASS_HANDLE_BCP);
        vo.setPriorityThreadBufferCapacity(FrameThreadConstant.priorityThreadBufferCapacityForBcp);
        vo.setPriorityThreadBufferIncrement(FrameThreadConstant.priorityThreadBufferIncrementForBcp);
        vo.setReadThreadPriorityAdd(FrameThreadConstant.readThreadPriorityAddForBcp);
        vo.setThreadBufferCapacity(FrameThreadConstant.threadBufferCapacityForBcp);
        vo.setThreadBufferIncrement(FrameThreadConstant.threadBufferIncrementForBcp);
        vo.setThreadPoolId(FrameThreadConstant.THREAD_POOL_BCP_ID);
        vo.setThreadPoolName(FrameThreadConstant.THREAD_POOL_BCP_NAME);
        vo.setThreadPriority(FrameThreadConstant.threadPriorityForBcp);
        vo.setUnHanledMsgLogDir(FrameThreadConstant.unHanledMsgLogDirForBcp);
        return vo;
    }

    protected ThreadAttrVo getThreadAttrVoForFile() {
        ThreadAttrVo vo = new ThreadAttrVo();
        vo.setMaxSearchNum(FrameThreadConstant.maxSearchNumForFile);
        vo.setMaxThreadNumber(FrameThreadConstant.maxThreadNumberForFile);
        vo.setMsgHandleClass(FrameThreadConstant.CLASS_HANDLE_FILE);
        vo.setPriorityThreadBufferCapacity(FrameThreadConstant.priorityThreadBufferCapacityForFile);
        vo.setPriorityThreadBufferIncrement(FrameThreadConstant.priorityThreadBufferIncrementForFile);
        vo.setReadThreadPriorityAdd(FrameThreadConstant.readThreadPriorityAddForFile);
        vo.setThreadBufferCapacity(FrameThreadConstant.threadBufferCapacityForFile);
        vo.setThreadBufferIncrement(FrameThreadConstant.threadBufferIncrementForFile);
        vo.setThreadPoolId(FrameThreadConstant.THREAD_POOL_FILE_ID);
        vo.setThreadPoolName(FrameThreadConstant.THREAD_POOL_FILE_NAME);
        vo.setThreadPriority(FrameThreadConstant.threadPriorityForFile);
        vo.setUnHanledMsgLogDir(FrameThreadConstant.unHanledMsgLogDirForFile);
        return vo;
    }

    protected ThreadAttrVo getThreadAttrVoForFileMobile() {
        ThreadAttrVo vo = new ThreadAttrVo();
        vo.setMaxSearchNum(FrameThreadConstant.maxSearchNumForFileMobile);
        vo.setMaxThreadNumber(FrameThreadConstant.maxThreadNumberForFileMobile);
        vo.setMsgHandleClass(FrameThreadConstant.CLASS_HANDLE_FILE_MOBILE);
        vo.setPriorityThreadBufferCapacity(FrameThreadConstant.priorityThreadBufferCapacityForFileMobile);
        vo.setPriorityThreadBufferIncrement(FrameThreadConstant.priorityThreadBufferIncrementForFileMobile);
        vo.setReadThreadPriorityAdd(FrameThreadConstant.readThreadPriorityAddForFileMobile);
        vo.setThreadBufferCapacity(FrameThreadConstant.threadBufferCapacityForFileMobile);
        vo.setThreadBufferIncrement(FrameThreadConstant.threadBufferIncrementForFileMobile);
        vo.setThreadPoolId(FrameThreadConstant.THREAD_POOL_FILE_MOBILE_ID);
        vo.setThreadPoolName(FrameThreadConstant.THREAD_POOL_FILE_NAME);
        vo.setThreadPriority(FrameThreadConstant.threadPriorityForFileMobile);
        vo.setUnHanledMsgLogDir(FrameThreadConstant.unHanledMsgLogDirForFileMobile);
        return vo;
    }
    protected ThreadAttrVo getThreadAttrVoForFileQrCode() {
        ThreadAttrVo vo = new ThreadAttrVo();
        vo.setMaxSearchNum(FrameThreadConstant.maxSearchNumForFileQrCode);
        vo.setMaxThreadNumber(FrameThreadConstant.maxThreadNumberForFileQrCode);
        vo.setMsgHandleClass(FrameThreadConstant.CLASS_HANDLE_FILE_QRCODE);
        vo.setPriorityThreadBufferCapacity(FrameThreadConstant.priorityThreadBufferCapacityForFileQrCode);
        vo.setPriorityThreadBufferIncrement(FrameThreadConstant.priorityThreadBufferIncrementForFileQrCode);
        vo.setReadThreadPriorityAdd(FrameThreadConstant.readThreadPriorityAddForFileQrCode);
        vo.setThreadBufferCapacity(FrameThreadConstant.threadBufferCapacityForFileQrCode);
        vo.setThreadBufferIncrement(FrameThreadConstant.threadBufferIncrementForFileQrCode);
        vo.setThreadPoolId(FrameThreadConstant.THREAD_POOL_FILE_QRCODE_ID);
        vo.setThreadPoolName(FrameThreadConstant.THREAD_POOL_FILE_NAME);
        vo.setThreadPriority(FrameThreadConstant.threadPriorityForFileQrCode);
        vo.setUnHanledMsgLogDir(FrameThreadConstant.unHanledMsgLogDirForFileQrCode);
        return vo;
    }

    protected ThreadAttrVo getThreadAttrVoForFileNetPaid() {
        ThreadAttrVo vo = new ThreadAttrVo();
        vo.setMaxSearchNum(FrameThreadConstant.maxSearchNumForFileNetPaid);
        vo.setMaxThreadNumber(FrameThreadConstant.maxThreadNumberForFileNetPaid);
        vo.setMsgHandleClass(FrameThreadConstant.CLASS_HANDLE_FILE_NETPAID);
        vo.setPriorityThreadBufferCapacity(FrameThreadConstant.priorityThreadBufferCapacityForFileNetPaid);
        vo.setPriorityThreadBufferIncrement(FrameThreadConstant.priorityThreadBufferIncrementForFileNetPaid);
        vo.setReadThreadPriorityAdd(FrameThreadConstant.readThreadPriorityAddForFileNetPaid);
        vo.setThreadBufferCapacity(FrameThreadConstant.threadBufferCapacityForFileNetPaid);
        vo.setThreadBufferIncrement(FrameThreadConstant.threadBufferIncrementForFileNetPaid);
        vo.setThreadPoolId(FrameThreadConstant.THREAD_POOL_FILE_NETPAID_ID);
        vo.setThreadPoolName(FrameThreadConstant.THREAD_POOL_FILE_NETPAID_NAME);
        vo.setThreadPriority(FrameThreadConstant.threadPriorityForFileNetPaid);
        vo.setUnHanledMsgLogDir(FrameThreadConstant.unHanledMsgLogDirForFileNetPaid);
        return vo;
    }

    protected ThreadAttrVo getThreadAttrVoForOther() {
        ThreadAttrVo vo = new ThreadAttrVo();
        vo.setMaxSearchNum(FrameThreadConstant.maxSearchNumForOther);
        vo.setMaxThreadNumber(FrameThreadConstant.maxThreadNumberForOther);
        vo.setMsgHandleClassPrifix(FrameThreadConstant.CLASS_HANDLE_OTHER_PREFIX);
        vo.setPriorityThreadBufferCapacity(FrameThreadConstant.priorityThreadBufferCapacityForOther);
        vo.setPriorityThreadBufferIncrement(FrameThreadConstant.priorityThreadBufferIncrementForOther);
        vo.setReadThreadPriorityAdd(FrameThreadConstant.readThreadPriorityAddForOther);
        vo.setThreadBufferCapacity(FrameThreadConstant.threadBufferCapacityForOther);
        vo.setThreadBufferIncrement(FrameThreadConstant.threadBufferIncrementForOther);
        vo.setThreadPoolId(FrameThreadConstant.THREAD_POOL_OTHER_ID);
        vo.setThreadPoolName(FrameThreadConstant.THREAD_POOL_OTHER_NAME);
        vo.setThreadPriority(FrameThreadConstant.threadPriorityForOther);
        vo.setUnHanledMsgLogDir(FrameThreadConstant.unHanledMsgLogDirForOther);
        return vo;
    }

    protected ThreadAttrVo getThreadAttrVoForOtherMobile() {
        ThreadAttrVo vo = new ThreadAttrVo();
        vo.setMaxSearchNum(FrameThreadConstant.maxSearchNumForOtherMobile);
        vo.setMaxThreadNumber(FrameThreadConstant.maxThreadNumberForOtherMobile);
        vo.setMsgHandleClassPrifix(FrameThreadConstant.CLASS_HANDLE_OTHER_MOBILE_PREFIX);
        vo.setPriorityThreadBufferCapacity(FrameThreadConstant.priorityThreadBufferCapacityForOtherMobile);
        vo.setPriorityThreadBufferIncrement(FrameThreadConstant.priorityThreadBufferIncrementForOtherMobile);
        vo.setReadThreadPriorityAdd(FrameThreadConstant.readThreadPriorityAddForOtherMobile);
        vo.setThreadBufferCapacity(FrameThreadConstant.threadBufferCapacityForOtherMobile);
        vo.setThreadBufferIncrement(FrameThreadConstant.threadBufferIncrementForOtherMobile);
        vo.setThreadPoolId(FrameThreadConstant.THREAD_POOL_OTHER_MOBILE_ID);
        vo.setThreadPoolName(FrameThreadConstant.THREAD_POOL_OTHER_MOBILE_NAME);
        vo.setThreadPriority(FrameThreadConstant.threadPriorityForOtherMobile);
        vo.setUnHanledMsgLogDir(FrameThreadConstant.unHanledMsgLogDirForOtherMobile);
        return vo;
    }
    
    protected ThreadAttrVo getThreadAttrVoForOtherQrCode() {
        ThreadAttrVo vo = new ThreadAttrVo();
        vo.setMaxSearchNum(FrameThreadConstant.maxSearchNumForOtherQrCode);
        vo.setMaxThreadNumber(FrameThreadConstant.maxThreadNumberForOtherQrCode);
        vo.setMsgHandleClassPrifix(FrameThreadConstant.CLASS_HANDLE_OTHER_QRCODE_PREFIX);
        vo.setPriorityThreadBufferCapacity(FrameThreadConstant.priorityThreadBufferCapacityForOtherQrCode);
        vo.setPriorityThreadBufferIncrement(FrameThreadConstant.priorityThreadBufferIncrementForOtherQrCode);
        vo.setReadThreadPriorityAdd(FrameThreadConstant.readThreadPriorityAddForOtherQrCode);
        vo.setThreadBufferCapacity(FrameThreadConstant.threadBufferCapacityForOtherQrCode);
        vo.setThreadBufferIncrement(FrameThreadConstant.threadBufferIncrementForOtherQrCode);
        vo.setThreadPoolId(FrameThreadConstant.THREAD_POOL_OTHER_QRCODE_ID);
        vo.setThreadPoolName(FrameThreadConstant.THREAD_POOL_OTHER_QRCODE_NAME);
        vo.setThreadPriority(FrameThreadConstant.threadPriorityForOtherQrCode);
        vo.setUnHanledMsgLogDir(FrameThreadConstant.unHanledMsgLogDirForOtherQrCode);
        return vo;
    }

    protected ThreadAttrVo getThreadAttrVoForOtherNetPaid() {
        ThreadAttrVo vo = new ThreadAttrVo();
        vo.setMaxSearchNum(FrameThreadConstant.maxSearchNumForOtherNetPaid);
        vo.setMaxThreadNumber(FrameThreadConstant.maxThreadNumberForOtherNetPaid);
        vo.setMsgHandleClassPrifix(FrameThreadConstant.CLASS_HANDLE_OTHER_NETPAID_PREFIX);
        vo.setPriorityThreadBufferCapacity(FrameThreadConstant.priorityThreadBufferCapacityForOtherNetPaid);
        vo.setPriorityThreadBufferIncrement(FrameThreadConstant.priorityThreadBufferIncrementForOtherNetPaid);
        vo.setReadThreadPriorityAdd(FrameThreadConstant.readThreadPriorityAddForOtherNetPaid);
        vo.setThreadBufferCapacity(FrameThreadConstant.threadBufferCapacityForOtherNetPaid);
        vo.setThreadBufferIncrement(FrameThreadConstant.threadBufferIncrementForOtherNetPaid);
        vo.setThreadPoolId(FrameThreadConstant.THREAD_POOL_OTHER_NETPAID_ID);
        vo.setThreadPoolName(FrameThreadConstant.THREAD_POOL_OTHER_NETPAID_NAME);
        vo.setThreadPriority(FrameThreadConstant.threadPriorityForOtherNetPaid);
        vo.setUnHanledMsgLogDir(FrameThreadConstant.unHanledMsgLogDirForOtherNetPaid);
        return vo;
    }

    protected ThreadAttrVo getThreadAttrVoForTac() {
        ThreadAttrVo vo = new ThreadAttrVo();
        vo.setMaxSearchNum(FrameThreadConstant.maxSearchNumForTac);
        vo.setMaxThreadNumber(FrameThreadConstant.maxThreadNumberForTac);
        vo.setMsgHandleClass(FrameThreadConstant.CLASS_HANDLE_TAC);
        vo.setPriorityThreadBufferCapacity(FrameThreadConstant.priorityThreadBufferCapacityForTac);
        vo.setPriorityThreadBufferIncrement(FrameThreadConstant.priorityThreadBufferIncrementForTac);
        vo.setReadThreadPriorityAdd(FrameThreadConstant.readThreadPriorityAddForTac);
        vo.setThreadBufferCapacity(FrameThreadConstant.threadBufferCapacityForTac);
        vo.setThreadBufferIncrement(FrameThreadConstant.threadBufferIncrementForTac);
        vo.setThreadPoolId(FrameThreadConstant.THREAD_POOL_TAC_ID);
        vo.setThreadPoolName(FrameThreadConstant.THREAD_POOL_TAC_NAME);
        vo.setThreadPriority(FrameThreadConstant.threadPriorityForTac);
        vo.setUnHanledMsgLogDir(FrameThreadConstant.unHanledMsgLogDirForTac);
        return vo;
    }

    protected void setAuditFtpDownloadFlag() {
        TaskUtil.setDownloadAuditFile(false);
    }

    protected void getCheckLimit() throws Exception {
        HashMap<String, String> hm = new CheckConfigDao().getCheckConfigForCheckLimits();
        if (hm.containsKey(FrameCheckConstant.KEY_CHK_TIME_MAX_BEFOREDAYS)) {
            ///距离当前时间最大天数
            FrameCheckConstant.CHK_TIME_MAX_BEFOREDAYS = Integer.parseInt(hm.get(FrameCheckConstant.KEY_CHK_TIME_MAX_BEFOREDAYS));
        }
        if (hm.containsKey(FrameCheckConstant.KEY_CHK_TIME_FORMAL_ONLINE)) {
            ///正式运营上线时间20181017
            FrameCheckConstant.CHK_TIME_FORMAL_ONLINE = hm.get(FrameCheckConstant.KEY_CHK_TIME_FORMAL_ONLINE);
        }

        if (hm.containsKey(FrameCheckConstant.KEY_CHK_FEE_MAX_BALANCE)) {
            ///最大余额，单位分
            FrameCheckConstant.CHK_FEE_MAX_BALANCE = Integer.parseInt(hm.get(FrameCheckConstant.KEY_CHK_FEE_MAX_BALANCE));
        }
        if (hm.containsKey(FrameCheckConstant.KEY_CHK_FEE_MAX_BALANCE_CARD_OCT)) {
            ///公交卡最大余额，单位分
            FrameCheckConstant.CHK_FEE_MAX_BALANCE_CARD_OCT = Integer.parseInt(hm.get(FrameCheckConstant.KEY_CHK_FEE_MAX_BALANCE_CARD_OCT));
        }
        if (hm.containsKey(FrameCheckConstant.KEY_CHK_FEE_MAX_DEAL)) {
            ///最大交易金额，单位分
            FrameCheckConstant.CHK_FEE_MAX_DEAL = Integer.parseInt(hm.get(FrameCheckConstant.KEY_CHK_FEE_MAX_DEAL));
        }
        if (hm.containsKey(FrameCheckConstant.KEY_CHK_FEE_MAX_DEAL_ZONE_OCT)) {
            ///公交消费，最大交易金额，单位分
            FrameCheckConstant.CHK_FEE_MAX_DEAL_ZONE_OCT = Integer.parseInt(hm.get(FrameCheckConstant.KEY_CHK_FEE_MAX_DEAL_ZONE_OCT));
        }
        if (hm.containsKey(FrameCheckConstant.KEY_CHK_FEE_MAX_DEAL_ZONE_MAG)) {
            ///磁浮消费，最大交易金额，单位分
            FrameCheckConstant.CHK_FEE_MAX_DEAL_ZONE_MAG = Integer.parseInt(hm.get(FrameCheckConstant.KEY_CHK_FEE_MAX_DEAL_ZONE_MAG));
        }

        if (hm.containsKey(FrameCheckConstant.KEY_CHK_FEE_MAX_CHARGE)) {
            ///最大单次充值金额，单位分
            FrameCheckConstant.CHK_FEE_MAX_CHARGE = Integer.parseInt(hm.get(FrameCheckConstant.KEY_CHK_FEE_MAX_CHARGE));
        }
        if (hm.containsKey(FrameCheckConstant.KEY_CHK_FEE_MAX_BUY_TK)) {
            ///最大购单程票金额，单位分 20180930
            FrameCheckConstant.CHK_FEE_MAX_BUY_TK = Integer.parseInt(hm.get(FrameCheckConstant.KEY_CHK_FEE_MAX_BUY_TK));
        }

    }
}
