/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.export;

import com.goldsign.settle.realtime.app.dao.ExportOctRZDao;
import com.goldsign.settle.realtime.app.dao.ExportOctAUSDao;
import com.goldsign.settle.realtime.app.vo.ExportDbResult;
import com.goldsign.settle.realtime.frame.export.ExportBase;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class ExportOctAUS extends ExportBase {
    private static Logger logger = Logger.getLogger(ExportOctAUS.class.getName());

    private static final int[] format = {25, 2
    };
    private static final int[] dir = {DIR_NONE, DIR_NONE
    };
    private static final String[] addChar = {NONE, NONE
    };

    @Override
    public String genExportFiles(String balanceWaterNo,int balanceWaterNoSub,String seq) throws Exception {
        ExportOctAUSDao dao = new ExportOctAUSDao();
        ExportDbResult result = dao.getRecords(balanceWaterNo);
        if (result == null || result.getRecords().size() == 0) {
            logger.info("采集状态没有记录，无需生成导出文件");
            return null;
        }

        Vector<String> records = this.formatRecord(result.getRecords(), format, dir, addChar);
        String fileName = this.getExportFileName(ExportBase.DATA_TYPE_AUS,
                ExportBase.SERVICER_MTR, ExportBase.PLACE_OCT, balanceWaterNo,seq);
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
