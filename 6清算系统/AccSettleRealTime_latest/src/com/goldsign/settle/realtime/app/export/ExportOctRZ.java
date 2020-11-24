/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.export;

import com.goldsign.settle.realtime.app.dao.ExportOctRZDao;
import com.goldsign.settle.realtime.app.dao.ExportOctJYDao;
import com.goldsign.settle.realtime.app.vo.ExportDbResult;
import com.goldsign.settle.realtime.frame.export.ExportBase;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class ExportOctRZ extends ExportBase{
    private static Logger logger = Logger.getLogger(ExportOctRZ.class.getName());
     private static final int[] format = {32, 10, 12
    };
    private static final int[] dir = {DIR_RIGHT, DIR_RIGHT, DIR_RIGHT
    };
    private static final String[] addChar = {SPACE, SPACE, SPACE
    };
    
    

    @Override
    public String genExportFiles(String balanceWaterNo,int balanceWaterNoSub,String seq) throws Exception {
        ExportOctRZDao dao = new ExportOctRZDao();
        ExportDbResult result = dao.getRecordsTrx(balanceWaterNo);
        if(result ==null || result.getRecords().size()==0){
            logger.info("审计文件没有记录，无需生成导出文件");
            return null;
        }
        Vector<String> records = this.formatRecord(result.getRecords(), format, dir, addChar);
        String fileName = this.getExportFileName(ExportBase.DATA_TYPE_AUF,ExportBase.SERVICER_MTR, 
                ExportBase.PLACE_MTR,  balanceWaterNo,seq);
        boolean ret =this.writeDataToFile(records, fileName);
        
        //记录添加审计文件名称
        dao.updateTrx(balanceWaterNo, fileName);
       
        
        return fileName;

    }

    @Override
    public String genExportFilesSpecial(String balanceWaterNo,int balanceWaterNoSub,String seq) throws Exception {
        ExportOctRZDao dao = new ExportOctRZDao();
        ExportDbResult result = dao.getRecordsSettle(balanceWaterNo);
        Vector<String> records = this.formatRecord(result.getRecords(), format, dir, addChar);
        String fileName = this.getExportFileName(ExportBase.DATA_TYPE_AUF,ExportBase.SERVICER_MTR,
                ExportBase.PLACE_MTR,  balanceWaterNo,seq);
        boolean ret =this.writeDataToFileForSettle(records, fileName);
        //记录添加审计文件名称
        dao.updateSettle(balanceWaterNo, fileName);
        return fileName;
    }
    
}
