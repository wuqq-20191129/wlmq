/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.download;


import com.goldsign.settle.realtime.app.dao.ExportTccStationInfoDao;
import com.goldsign.settle.realtime.frame.dao.FileLogTccDao;
import com.goldsign.settle.realtime.frame.file.FileTccBase;
import com.goldsign.settle.realtime.frame.vo.ResultFromProc;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class TccStationInfo extends FileTccBase{
     private static Logger logger = Logger.getLogger(TccLineInfo.class.getName());
    private static String EXPORT_FILE_NAME = "车站信息";
     private static String PROCEDURE_NAME="w_up_st_tcc_StationInfo";

    @Override
    public String genFileTcc(String balanceWaterNo, String fileNameBase) throws Exception {
        //获取数据
        ExportTccStationInfoDao dao = new ExportTccStationInfoDao();
        ResultFromProc result = dao.getRecords(balanceWaterNo, PROCEDURE_NAME);
        if (result.getRetCode() != 0) {
            //logger.error("清算流水号："+balanceWaterNo+" 导出TCC文件"+EXPORT_FILE_NAME+"错误");
            throw new Exception("清算流水号：" + balanceWaterNo + " 导出TCC文件" + EXPORT_FILE_NAME + "错误");

        }
         if (result.getRetValues()==null || result.getRetValues().isEmpty()) {
            throw new Exception("清算流水号：" + balanceWaterNo + " 导出TCC文件" + EXPORT_FILE_NAME + "记录为空！");

        }
        //生成文件名称
        String fileName = this.getFileNameTcc(fileNameBase);
        //写入对应目录
        this.writeDataToFile(result.getRetValues(), fileName, balanceWaterNo);
        //记录日志
        FileLogTccDao daoLog = new FileLogTccDao();
        daoLog.insert(fileName, balanceWaterNo,EXPORT_FILE_NAME);
        return fileName;

    }
    
}
