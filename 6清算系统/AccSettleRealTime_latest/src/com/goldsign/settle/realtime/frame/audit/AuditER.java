/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.audit;

import com.goldsign.settle.realtime.frame.dao.FileErrorDao;
import com.goldsign.settle.realtime.frame.dao.FileLogFtpDao;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class AuditER extends AuditBase{
    private static final int[] FORMAT = {20,1
                                         
                                         };

    @Override
    public Vector<String> genAuditFiles(String balanceWaterNo) throws Exception {
       FileErrorDao dao = new FileErrorDao();
       Hashtable records= dao.getRecordsForAudit(balanceWaterNo);
       Vector<String> fileNames =this.writeDataToFiles(records, balanceWaterNo, AuditER.DATA_TYPE_ERROR,FORMAT);
       return fileNames;
    }
    
}
