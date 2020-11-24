/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.audit;

import com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;
import com.goldsign.settle.realtime.frame.dao.FileLccDao;
import com.goldsign.settle.realtime.frame.dao.FileMobileLccDao;
import com.goldsign.settle.realtime.frame.io.FileGenericBase;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class AuditMobileSDF extends AuditBase{
    private int[] dataTypeHead = {FileGenericBase.T_STR, FileGenericBase.T_BCD, FileGenericBase.T_BCD, FileGenericBase.T_INT};
    private int[] dataLenHead = {2, 7, 7, 4};
    private int[] dataTypeTail = {FileGenericBase.T_STR, FileGenericBase.T_STR};
    private int[] dataLenTail = {4, 8};
    private int[] dataType = {FileGenericBase.T_STR, FileGenericBase.T_INT, FileGenericBase.T_INT,FileGenericBase.T_STR,FileGenericBase.T_STR,
                              FileGenericBase.T_INT, FileGenericBase.T_INT,FileGenericBase.T_STR,FileGenericBase.T_STR,FileGenericBase.T_INT,
                              FileGenericBase.T_INT,FileGenericBase.T_INT,FileGenericBase.T_INT ,FileGenericBase.T_INT,FileGenericBase.T_INT ,
                              FileGenericBase.T_STR,FileGenericBase.T_STR,FileGenericBase.T_STR,FileGenericBase.T_STR,FileGenericBase.T_INT ,
                              FileGenericBase.T_INT ,FileGenericBase.T_INT ,FileGenericBase.T_STR,FileGenericBase.T_STR
    };
    private int[] dataLen = {4, 4, 4,2,4,
                             4, 4,2,4,4,
                             4,4,4,4,4,
                             2,4,2,4,4,
                             4,4,2,4
                              
                              };

    
    public Vector<String> genAuditFiles(String balanceWaterNo) throws Exception {
        FileMobileLccDao dao = new FileMobileLccDao();
        //从数据库中获取记录，记录按线路分开
        Hashtable records = dao.getRecordsForAudit(balanceWaterNo);
        //生成下发的文件
        /*
        Vector<String> fileNames = this.writeDataToFilesForGeneric(records, balanceWaterNo, AuditER.DATA_TYPE_SDF, 
                                                          dataTypeHead,dataLenHead,dataType,dataLen,
                                                          dataTypeTail,dataLenTail,FrameCodeConstant.PATH_FILE_AUDIT_LCC);
        */
        //20151130文件分隔符由一个回车换行改为3个回车换行
        Vector<String> fileNames = this.writeDataToFilesForGenericMobile_3(records, balanceWaterNo, AuditER.DATA_TYPE_SDF, 
                                                          dataTypeHead,dataLenHead,dataType,dataLen,
                                                          dataTypeTail,dataLenTail,FrameCodeConstant.PATH_FILE_AUDIT_LCC);
        return fileNames;
    }
    
}
