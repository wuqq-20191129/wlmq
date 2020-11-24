/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.timer;

import com.goldsign.settle.realtime.frame.dao.BalanceWaterNoDao;
import com.goldsign.settle.realtime.frame.dao.ClearTempFileDao;
import com.goldsign.settle.realtime.frame.filter.DirectoryFilter;
import com.goldsign.settle.realtime.frame.util.DateHelper;
import com.goldsign.settle.realtime.frame.vo.ClearTempFileVo;
import com.goldsign.settle.realtime.frame.vo.ResultFromProc;
import java.io.File;
import java.util.Date;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class ActionClearTmpFile extends ActionBase{
     private final static int CLEAR_TYPE_BALANCE_DAY = 1;//按清算日清理
    private final static String CLEAR_SUCESS = "0";//成功清理
    private static Logger logger = Logger.getLogger(ActionClearTmpFile.class.getName());

    @Override
    public void action() throws Exception {
        String balanceWaterNoCur;
        logger.info("准备清理中间文件、日志文件");
        //获取当前清算日
        balanceWaterNoCur = this.getBalanceWater("system");
        //获取清理配置信息（清理类型、路径、保留天数等）
        Vector clearInfos = this.getClearInfos();
        ClearTempFileVo vo;
        if (clearInfos.isEmpty()) {
            logger.info("没有配置文件清理相关数据");
        }
        for (int i = 0; i < clearInfos.size(); i++) {
            vo = (ClearTempFileVo) clearInfos.get(i);
            //按配置清理文件
            this.clearTempFiles(balanceWaterNoCur, vo);
        }
    }

    protected String getBalanceWater(String opId) throws Exception {
        ResultFromProc result;
        BalanceWaterNoDao dao = new BalanceWaterNoDao();
        result = dao.getBalanceWaterNoForClear(opId);
        return result.getRetValue();

    }

    private void clearTempFiles(String balanceWaterNo, ClearTempFileVo vo) throws Exception {
        int clearType = vo.getClearType();

        switch (clearType) {
            case CLEAR_TYPE_BALANCE_DAY:
                this.clearByBalanceDay(balanceWaterNo, vo);
                break;
            default:
                ;
        }



    }

    private void clearByBalanceDay(String balanceWaterNo, ClearTempFileVo vo) throws Exception {
        int reserveDays = vo.getReserveDays();
        String path = vo.getPathClear();
        File[] dirSubs = this.getDirClear(path, balanceWaterNo, reserveDays);
        int n = 0;
        int n1 = 0;
        Date startTime;
        for (File dir : dirSubs) {
            startTime = new Date();
            n1 = this.clearFiles(dir);//删除目录下的文件
            n += n1;
            //删除目录
            dir.delete();
            logger.info("清算流水号：" + balanceWaterNo + " 清理目录：" + this.getDirFullName(path, dir.getName()) + " 文件数量：" + n1);
            //记录清理结果
            this.insertLog(balanceWaterNo, this.getDirFullName(path, dir.getName()), DateHelper.dateToString(startTime), n1, CLEAR_SUCESS, "");

        }
        logger.info("清算流水号：" + balanceWaterNo + " 目录：" + path + " 本次累计清理：" + " 文件数量：" + n);




    }

    private int insertLog(String balanceWaterNo, String clearPath, String beginDate, int clearNum, String clearResult, String remark) throws Exception {
        ClearTempFileDao dao = new ClearTempFileDao();
        int n = dao.insertLog(balanceWaterNo, clearPath, beginDate, clearNum, clearResult, remark);
        return n;

    }

    private String getDirFullName(String path, String subDir) {
        return path + "/" + subDir;
    }

    private File[] getDirClear(String path, String balanceWaterNo, int reserveDays) throws Exception {

        File dir = new File(path);
        if (!dir.isDirectory()) {
            new Exception(path + "目录不存在");
        }
        DirectoryFilter filter = new DirectoryFilter(balanceWaterNo, reserveDays);
        File[] dirSubsArr = dir.listFiles(filter);
        return dirSubsArr;


    }

    protected Vector<ClearTempFileVo> getClearInfos() throws Exception {
        ClearTempFileDao dao = new ClearTempFileDao();
        Vector<ClearTempFileVo> cfgs = dao.getClearTempFileCfgs();

        return cfgs;
    }

    private int clearFiles(File dir) {
        File[] files = dir.listFiles();
        for (File file : files) {
            file.delete();
        }
        return files.length;
    }
    
}
