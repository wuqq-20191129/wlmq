/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.audit;

import com.goldsign.settle.realtime.frame.dao.FileLogFtpDao;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class AuditFTP extends AuditBase{
    private static final int[] FORMAT = {20,10};

    @Override
    public Vector<String>  genAuditFiles(String balanceWaterNo) throws Exception {
       FileLogFtpDao dao = new FileLogFtpDao();
       Hashtable records= dao.getRecordsForAudit(balanceWaterNo);
       Vector<String> fileNames =this.writeDataToFiles(records, balanceWaterNo, AuditFTP.DATA_TYPE_FTP,FORMAT);
       return fileNames;
       
    }

    
}
