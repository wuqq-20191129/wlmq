/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.export;

import com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;
import com.goldsign.settle.realtime.frame.constant.FrameOctFileImportConstant;
import com.goldsign.settle.realtime.frame.dao.BlackListDao;
import com.goldsign.settle.realtime.frame.dao.FileOctExportAndImportLog;
import com.goldsign.settle.realtime.frame.dao.FileOctSeqDao;
import com.goldsign.settle.realtime.frame.util.FileUtil;
import com.goldsign.settle.realtime.frame.util.TradeUtil;
import com.goldsign.settle.realtime.frame.vo.OctExportAndImport;
import com.goldsign.settle.realtime.frame.vo.ResultFromProc;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class ExportFile extends ExportBase {
    
    private static Logger logger = Logger.getLogger(ExportFile.class.getName());
    private static String CLASS_PREFIX = "com.goldsign.settle.realtime.app.export.Export";
    
    private String export(String balanceWaterNo,int balanceWaterNoSub, String sysName, String dataType,String seq) throws Exception {
        //导出文件
        String className = CLASS_PREFIX + sysName + dataType;
        ExportBase ab = (ExportBase) Class.forName(className).newInstance();
        String fileName = ab.genExportFiles(balanceWaterNo,balanceWaterNoSub,seq);
        return fileName;
        
    }

    private String exportSpecial(String balanceWaterNo,int balanceWaterNoSub, String sysName, String dataType) throws Exception {
        //导出文件
        String className = CLASS_PREFIX + sysName + dataType;
        ExportBase ab = (ExportBase) Class.forName(className).newInstance();
        String fileName = ab.genExportFilesSpecial(balanceWaterNo,balanceWaterNoSub,"");
        return fileName;
        
    }
    
    private void addFileName(Vector<String> fileNames, String fileName) {
        if (fileName == null || fileName.length() == 0) {
            return;
        }
        fileNames.add(fileName);
    }
    private String getFileOctSeq(String balanceWaterNo ) throws Exception{
        FileOctSeqDao dao = new FileOctSeqDao();
        ResultFromProc ret = dao.getFileOctSeq(balanceWaterNo);
        String seq = ret.getRetValue().trim();
        return seq;
    }
    
    public void exportForOctTRX(String balanceWaterNo,int balanceWaterNoSub) throws Exception {
        Vector<String> fileNames = new Vector();
        //获取文件序号号
        String seq = this.getFileOctSeq(balanceWaterNo);
        
        String fileName = this.export(balanceWaterNo,balanceWaterNoSub, ExportBase.SYS_OCT, ExportBase.DATA_TYPE_TRX,seq);//导出交易
        this.addFileName(fileNames, fileName);
        fileName = this.export(balanceWaterNo,balanceWaterNoSub, ExportBase.SYS_OCT, ExportBase.DATA_TYPE_BLA,seq);//导出锁卡记录
        this.addFileName(fileNames, fileName);
        
        fileName = this.export(balanceWaterNo,balanceWaterNoSub, ExportBase.SYS_OCT, ExportBase.DATA_TYPE_AUF,seq);//导出文件审计记录
        this.addFileName(fileNames, fileName);
        
        if (fileNames == null || fileNames.size() == 0) {
            logger.info("没有公交卡的交易、名单申请数据，"
                    + "不需生成上传的交易压缩文件。");
            return;
        }

        //生成压缩文件
        String zipFileName = this.getExportFileNameForZip(ExportBase.DATA_TYPE_TCM,
                ExportBase.SERVICER_MTR, ExportBase.PLACE_MTR, balanceWaterNo,seq);
        this.zipFiles(FrameCodeConstant.PATH_FILE_OCT_EXPORT_TRX_ZIP, zipFileName, FrameCodeConstant.PATH_FILE_OCT_EXPORT_TRX_FILE,
                fileNames);
        logger.info("压缩文件"+zipFileName+"生成,放至"+FrameCodeConstant.PATH_FILE_OCT_EXPORT_TRX_ZIP);
        //原始文件备份移至历史
        for (String fn : fileNames) {
            FileUtil.moveFile(fn, FrameCodeConstant.PATH_FILE_OCT_EXPORT_TRX_FILE,
                    fn, FrameCodeConstant.PATH_FILE_OCT_EXPORT_TRX_FILE_HIS, balanceWaterNo);
        }
        
        //上传压缩文件至交换目录及创建控制文件
        //目录添加日期
        String pathExport =FrameCodeConstant.PATH_FILE_OCT_UPLOAD;
        /*
        String pathExport =TradeUtil.getDirForBalanceWaterNo( FrameCodeConstant.PATH_FILE_OCT_UPLOAD,
                FrameCodeConstant.BALANCE_WATER_NO, true);
        */
        FileUtil.moveFileForOct(zipFileName, FrameCodeConstant.PATH_FILE_OCT_EXPORT_TRX_ZIP,
                zipFileName,pathExport, balanceWaterNo);//自动创建日期目录
        logger.info("压缩文件"+zipFileName+"上传至"+pathExport);
        
        //记录导出日志 20180703
        this.logExport(balanceWaterNo, balanceWaterNoSub, zipFileName);
        
        
       
        
        
        
    }
    private void logExport(String balanceWaterNo,int balanceWaterNoSub,String fileName ) throws Exception{
        OctExportAndImport vo = this.getOctExportAndImport(balanceWaterNo, balanceWaterNoSub, fileName);
        FileOctExportAndImportLog dao = new FileOctExportAndImportLog();
        dao.logFileOctExportAndImport(vo);
        
        
    }
    private OctExportAndImport getOctExportAndImport(String balanceWaterNo,int balanceWaterNoSub,String fileName){
        OctExportAndImport vo = new OctExportAndImport();
        vo.setBalanceWaterNo(balanceWaterNo);
        vo.setBalanceWaterNoSub(balanceWaterNoSub);
        vo.setDoFlag(FrameOctFileImportConstant.DO_EXPORT);
        vo.setExportFileName(fileName);
        vo.setExportFlag(FrameOctFileImportConstant.FINISH_DOING);
        vo.setImportFileName(fileName);
        vo.setImportFlag(FrameOctFileImportConstant.FINISH_NOT);
        return vo;
        
    }
    public void exportForMobileTRX(String balanceWaterNo,int balanceWaterNoSub) throws Exception {
        Vector<String> fileNames = new Vector();
        
        String fileName = this.export(balanceWaterNo,balanceWaterNoSub, ExportBase.SYS_MOBILE, ExportBase.DATA_TYPE_TRX_MOBILE,"0");//导出交易
        this.addFileName(fileNames, fileName);
        
        if (fileNames == null || fileNames.size() == 0) {
            logger.info("没有手机支付卡的消费交易，"
                    + "不需生成下发的消费文件。");
            return;
        }

        //原始文件备份移至历史
        for (String fn : fileNames) {
            FileUtil.moveFile(fn, FrameCodeConstant.MOBILE_PATH_EXPORT_TRX_FILE,
                    fn, FrameCodeConstant.MOBILE_PATH_EXPORT_TRX_HIS, balanceWaterNo);
        }
        
        //上传压缩文件至交换目录及创建控制文件
        //目录添加日期
        String pathExport =FrameCodeConstant.PATH_MOBILE_MTX;

        FileUtil.moveFileForMobile(fileName, FrameCodeConstant.MOBILE_PATH_EXPORT_TRX_FILE,
                fileName,pathExport, balanceWaterNo);//自动创建日期目录
        logger.info("手机支付消费文件"+fileName+"上传至"+pathExport);
        
       
        
        
        
    }


    public void exportForOctForSettle(String balanceWaterNo,int balanceWaterNoSub) throws Exception {
        Vector<String> fileNames = new Vector();
        String fileName = this.export(balanceWaterNo,balanceWaterNoSub, ExportBase.SYS_OCT, ExportBase.DATA_TYPE_AUB,"");//导出结算结果
        this.addFileName(fileNames, fileName);
        fileName = this.export(balanceWaterNo,balanceWaterNoSub, ExportBase.SYS_OCT, ExportBase.DATA_TYPE_ERR,"");//导出错误记录
        this.addFileName(fileNames, fileName);
        
        fileName = this.export(balanceWaterNo,balanceWaterNoSub, ExportBase.SYS_OCT, ExportBase.DATA_TYPE_BLL,"");//导出黑名单
        this.addFileName(fileNames, fileName);
        
        fileName = this.export(balanceWaterNo,balanceWaterNoSub, ExportBase.SYS_OCT, ExportBase.DATA_TYPE_AUS,"");//导出采集结果
        this.addFileName(fileNames, fileName);
        
        
        
        
        fileName = this.exportSpecial(balanceWaterNo,balanceWaterNoSub, ExportBase.SYS_OCT, ExportBase.DATA_TYPE_AUF);//导出文件审计记录
        this.addFileName(fileNames, fileName);
        if (fileNames == null || fileNames.size() == 0) {
            logger.info("没有地铁卡的结算结果、错误记录、黑名单、采集结果数据，"
                    + "不需生成下发的结算结果压缩文件。");
            return;
        }
        //生成压缩文件
        String zipFileName = this.getExportFileNameForZip(ExportBase.DATA_TYPE_SCM,
                ExportBase.SERVICER_MTR, ExportBase.PLACE_MTR, balanceWaterNo,"");
        this.zipFiles(FrameCodeConstant.PATH_FILE_OCT_EXPORT_SETTLE_ZIP, zipFileName, FrameCodeConstant.PATH_FILE_OCT_EXPORT_SETTLE_FILE,
                fileNames);
        logger.info("压缩文件"+zipFileName+"生成,放至"+FrameCodeConstant.PATH_FILE_OCT_EXPORT_SETTLE_ZIP);
        //原始文件备份移至历史
        
        for (String fn : fileNames) {
  
            FileUtil.moveFile(fn, FrameCodeConstant.PATH_FILE_OCT_EXPORT_SETTLE_FILE,
                    fn, FrameCodeConstant.PATH_FILE_OCT_EXPORT_SETTLE_FILE_HIS, balanceWaterNo);
        }

        //上传压缩文件至交换目录及创建控制文件
         String pathExport=FrameCodeConstant.PATH_FILE_OCT_DOWNLOAD; 
         /*
        String pathExport =TradeUtil.getDirForBalanceWaterNo(FrameCodeConstant.PATH_FILE_OCT_DOWNLOAD,
                FrameCodeConstant.BALANCE_WATER_NO, false);
        */
        FileUtil.moveFileForOct(zipFileName, FrameCodeConstant.PATH_FILE_OCT_EXPORT_SETTLE_ZIP,
                zipFileName,pathExport, balanceWaterNo);//自动创建日期目录
        logger.info("压缩文件"+zipFileName+"上传至"+pathExport);
        //记录及汇总上传公交黑名单 add by hejj 20160202
        this.recordBlackListUpload(balanceWaterNo);
        logger.info("清算流程号："+balanceWaterNo+" 记录上传公交黑名单成功");
        
        
        
    }
    private void  recordBlackListUpload(String balanceWaterNo) throws Exception{
        BlackListDao dao = new BlackListDao();
        ResultFromProc rp =dao.recordBlackListUpload(balanceWaterNo);
        if(rp.getRetCode() !=0)
            throw new Exception("清算流程号："+balanceWaterNo+" 记录上传公交黑名单失败");
    }
    
    @Override
    public String genExportFiles(String balanceWaterNo,int balanceWaterNoSub,String seq) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public String genExportFilesSpecial(String balanceWaterNo,int balanceWaterNoSub,String seq) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
