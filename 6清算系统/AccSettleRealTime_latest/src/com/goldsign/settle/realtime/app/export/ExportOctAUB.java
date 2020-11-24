/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.export;

import com.goldsign.settle.realtime.app.dao.ExportOctAUBDao;
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
public class ExportOctAUB extends ExportBase {
    private static Logger logger = Logger.getLogger(ExportOctAUB.class.getName());

    private static final int[] format = {16, 2, 8, 12
    };
    private static final int[] dir = {DIR_RIGHT, DIR_NONE, DIR_RIGHT, DIR_RIGHT
    };
    private static final String[] addChar = {ZERO, NONE, SPACE, SPACE
    };

    @Override
    public String genExportFiles(String balanceWaterNo,int balanceWaterNoSub,String seq) throws Exception {
        ExportOctAUBDao dao = new ExportOctAUBDao();
        ExportDbResult result = dao.getRecords(balanceWaterNo);
        Vector<String> records = this.formatRecord(result.getRecords(), format, dir, addChar);
        if(result ==null || result.getRecords().size()==0){
            logger.info("结算结果没有记录，无需生成导出文件");
            return null;
        }
        String fileName = this.getExportFileName(ExportBase.DATA_TYPE_AUB, ExportBase.SERVICER_MTR,
                ExportBase.PLACE_OCT, balanceWaterNo,seq);
        boolean ret = this.writeDataToFileForSettle(records, fileName);
        ExportOctRZDao eod = new ExportOctRZDao();
        eod.insertSettle(fileName, result.getTotalNum(), result.getTotalFee(), balanceWaterNo);

        return fileName;

    }

    @Override
    public String genExportFilesSpecial(String balanceWaterNo,int balanceWaterNoSub,String seq) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
