/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.export;

import com.goldsign.settle.realtime.app.dao.ExportMobileTRXDao;
import com.goldsign.settle.realtime.app.vo.ExportDbResult;
import com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;
import com.goldsign.settle.realtime.frame.export.ExportBase;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class ExportMobileTRX extends ExportBase{
    private static Logger logger = Logger.getLogger(ExportMobileTRX.class.getName());

    private static final int[] format = {10, 4, 20, 20, 16,
                                         14, 16, 14, 16, 14,
                                          2, 6, 8, 10, 10,
                                          4,4,2,12
    };
    private static final int[] dir = {DIR_RIGHT, DIR_NONE, DIR_LEFT, DIR_LEFT, DIR_RIGHT,
                                      DIR_NONE, DIR_RIGHT, DIR_NONE, DIR_RIGHT, DIR_NONE,
                                      DIR_NONE, DIR_RIGHT, DIR_RIGHT, DIR_RIGHT, DIR_LEFT,
                                      DIR_NONE,DIR_NONE,DIR_NONE,DIR_NONE
    };
    private static final String[] addChar = {ZERO, NONE, SPACE, SPACE, ZERO,
                                      NONE, ZERO, NONE, ZERO, NONE,
                                      NONE, NONE, SPACE, SPACE, SPACE,
                                      NONE,NONE,NONE,NONE
    };
    
    


    @Override
    public String genExportFiles(String balanceWaterNo, int balanceWaterNoSub, String seq) throws Exception {
               ExportMobileTRXDao dao = new ExportMobileTRXDao();
        ExportDbResult result = dao.getRecords(balanceWaterNo);
        if (result == null || result.getRecords().size() == 0) {
            logger.info("交易数据没有记录，无需生成导出文件");
            return null;
        }
        Vector<String> records = this.formatRecordForMobile(result.getRecords(), format, dir, addChar);
        String fileName = this.getExportFileName(ExportBase.DATA_TYPE_TRX_MOBILE_FILE,ExportBase.ACC_LINE_STATION,  balanceWaterNo);
        boolean ret =this.writeDataToFileForMobile(records, fileName,FrameCodeConstant.MOBILE_PATH_EXPORT_TRX_FILE);
       // ExportOctAUFDao eod = new ExportOctAUFDao();
        //eod.insertTrx(fileName, result.getTotalNum(), result.getTotalFee(), balanceWaterNo);
        
        return fileName;
    }

    @Override
    public String genExportFilesSpecial(String balanceWaterNo, int balanceWaterNoSub, String seq) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
       protected String getExportFileName(String dataType,String lineStation,  String balanceWaterNo) {
        return dataType  + lineStation  +"."+ balanceWaterNo.substring(0, 8);
    }

 
    
}
