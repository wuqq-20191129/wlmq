/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.export;

import com.goldsign.settle.realtime.app.dao.ExportOctRZDao;
import com.goldsign.settle.realtime.app.dao.ExportOctLKDao;
import com.goldsign.settle.realtime.app.dao.ExportOctJYDao;
import com.goldsign.settle.realtime.app.vo.ExportDbResult;
import com.goldsign.settle.realtime.frame.export.ExportBase;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class ExportOctLK  extends ExportBase{
     private static Logger logger = Logger.getLogger(ExportOctLK.class.getName());
     private static final int[] format = {16, 1, 14, 16,16,
                                          4,4,16
    };
    private static final int[] dir = {DIR_RIGHT, DIR_RIGHT, DIR_RIGHT, DIR_RIGHT,DIR_RIGHT,
                                      DIR_RIGHT, DIR_RIGHT, DIR_RIGHT
    };
    private static final String[] addChar = {SPACE, SPACE, SPACE, SPACE,SPACE,
                                             SPACE, SPACE, SPACE
    };
    
    

    @Override
    public String genExportFiles(String balanceWaterNo,int balanceWaterNoSub,String seq) throws Exception {
        ExportOctLKDao dao = new ExportOctLKDao();
        ExportDbResult result = dao.getRecords(balanceWaterNo);
        if (result == null || result.getRecords().size() == 0) {
            logger.info("名单申请没有记录，无需生成导出文件");
            return null;
        }
        Vector<String> records = this.formatRecord(result.getRecords(), format, dir, addChar);
        String fileName = this.getExportFileName(ExportBase.DATA_TYPE_BLA,ExportBase.SERVICER_MTR,
                ExportBase.PLACE_MTR,  balanceWaterNo,seq);
        boolean ret =this.writeDataToFile(records, fileName);
        ExportOctRZDao eod = new ExportOctRZDao();
        eod.insertTrx(fileName, result.getTotalNum(), result.getTotalFeeFen(), balanceWaterNo,balanceWaterNoSub);
        return fileName;

    }

    @Override
    public String genExportFilesSpecial(String balanceWaterNo,int balanceWaterNoSub,String seq) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
