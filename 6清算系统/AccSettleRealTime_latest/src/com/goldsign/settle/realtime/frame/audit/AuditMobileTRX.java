/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.audit;

import com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;

import com.goldsign.settle.realtime.frame.dao.FileMobileTrxDao;
import com.goldsign.settle.realtime.frame.export.ExportBase;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class AuditMobileTRX extends AuditBase {

    private static final int[] format = {10, 4, 20, 20, 16,
        14, 16, 14, 16, 14,
        2, 6, 8, 10, 10,
        4, 4, 2, 12
    };
    private static final int[] dir = {ExportBase.DIR_RIGHT, ExportBase.DIR_NONE, ExportBase.DIR_LEFT, ExportBase.DIR_LEFT, ExportBase.DIR_RIGHT,
        ExportBase.DIR_NONE, ExportBase.DIR_RIGHT, ExportBase.DIR_NONE, ExportBase.DIR_RIGHT, ExportBase.DIR_NONE,
        ExportBase.DIR_NONE, ExportBase.DIR_RIGHT, ExportBase.DIR_RIGHT, ExportBase.DIR_RIGHT, ExportBase.DIR_LEFT,
        ExportBase.DIR_NONE, ExportBase.DIR_NONE, ExportBase.DIR_NONE, ExportBase.DIR_NONE
    };
    private static final String[] addChar = {ExportBase.ZERO, ExportBase.NONE, ExportBase.SPACE, ExportBase.SPACE, ExportBase.ZERO,
        ExportBase.NONE, ExportBase.ZERO, ExportBase.NONE, ExportBase.ZERO, ExportBase.NONE,
        ExportBase.NONE, ExportBase.NONE, ExportBase.SPACE, ExportBase.ZERO, ExportBase.SPACE,
        ExportBase.NONE, ExportBase.NONE, ExportBase.NONE, ExportBase.NONE
    };

    @Override
    public Vector<String> genAuditFiles(String balanceWaterNo) throws Exception {
        FileMobileTrxDao dao = new FileMobileTrxDao();
        //地铁APP消费记录
        //更新未导出记录导出标识为正在导出 20190624
        dao.updateExportFlag(balanceWaterNo, FrameCodeConstant.APP_PLATFORM_METRO, FrameCodeConstant.EXPORT_FLAG_DOING, FrameCodeConstant.EXPORT_FLAG_NOT);
        String fileSeq = "000";//dao.getFileSeq(balanceWaterNo, FrameCodeConstant.APP_PLATFORM_METRO);//20190624 modify by hejj 
        Hashtable records80 = dao.getRecordsForAudit80(balanceWaterNo);
        if (!records80.isEmpty()) {//没有数据不生成序号
            fileSeq = dao.getFileSeq(balanceWaterNo, FrameCodeConstant.APP_PLATFORM_METRO);//20190624 modify by hejj 
        }
        Vector<String> fileNames80 = this.writeDataToFilesMobile80(records80, balanceWaterNo, FrameCodeConstant.PATH_MOBILE_MTX, format, dir, addChar, fileSeq);
        //更新未导出记录导出标识为已完成导出 20190624
        dao.updateExportFlag(balanceWaterNo, FrameCodeConstant.APP_PLATFORM_METRO, FrameCodeConstant.EXPORT_FLAG_YES, FrameCodeConstant.EXPORT_FLAG_DOING);
        /**
         * *****************************************************************
         */
        //银行APP消费记录
        dao.updateExportFlag(balanceWaterNo, FrameCodeConstant.APP_PLATFORM_BANK, FrameCodeConstant.EXPORT_FLAG_DOING, FrameCodeConstant.EXPORT_FLAG_NOT);
        
        Hashtable records81 = dao.getRecordsForAudit81(balanceWaterNo);
        if (!records81.isEmpty()) {//没有数据不生成序号
           fileSeq = dao.getFileSeq(balanceWaterNo, FrameCodeConstant.APP_PLATFORM_BANK);//20190624 modify by hejj 
        }
        Vector<String> fileNames81 = this.writeDataToFilesMobile81(records81, balanceWaterNo, FrameCodeConstant.PATH_MOBILE_MTX, format, dir, addChar, fileSeq);
        //更新未导出记录导出标识为已完成导出 20190624
        dao.updateExportFlag(balanceWaterNo, FrameCodeConstant.APP_PLATFORM_BANK, FrameCodeConstant.EXPORT_FLAG_YES, FrameCodeConstant.EXPORT_FLAG_DOING);

        Vector<String> fileNames = new Vector();
        fileNames.addAll(fileNames80);
        fileNames.addAll(fileNames81);

        return fileNames;
    }
}
