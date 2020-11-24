/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.audit;

import com.goldsign.settle.realtime.app.vo.VersionCompareResult;
import com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;
import com.goldsign.settle.realtime.frame.dao.FileLccDao;
import com.goldsign.settle.realtime.frame.io.FileGenericBase;

import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class AuditSDF extends AuditBase {

    private int[] dataTypeHead = {FileGenericBase.T_STR, FileGenericBase.T_BCD, FileGenericBase.T_BCD, FileGenericBase.T_INT};
    private int[] dataLenHead = {2, 7, 7, 4};
    private int[] dataTypeTail = {FileGenericBase.T_STR, FileGenericBase.T_STR};
    private int[] dataLenTail = {4, 8};
    private int[] dataType = {FileGenericBase.T_STR,
        FileGenericBase.T_STR, FileGenericBase.T_INT, FileGenericBase.T_INT, FileGenericBase.T_INT, FileGenericBase.T_INT,
        FileGenericBase.T_INT, FileGenericBase.T_INT, FileGenericBase.T_INT, FileGenericBase.T_INT, FileGenericBase.T_INT,
        FileGenericBase.T_INT, FileGenericBase.T_INT, FileGenericBase.T_INT, FileGenericBase.T_INT, FileGenericBase.T_INT,
        FileGenericBase.T_INT, FileGenericBase.T_INT, FileGenericBase.T_INT, FileGenericBase.T_INT, FileGenericBase.T_INT,
        FileGenericBase.T_INT, FileGenericBase.T_INT, FileGenericBase.T_INT, FileGenericBase.T_INT, FileGenericBase.T_INT,
        FileGenericBase.T_INT, FileGenericBase.T_INT, FileGenericBase.T_INT, FileGenericBase.T_INT, FileGenericBase.T_INT,
        FileGenericBase.T_INT, FileGenericBase.T_INT, FileGenericBase.T_INT, FileGenericBase.T_INT, FileGenericBase.T_INT,
        FileGenericBase.T_INT,FileGenericBase.T_INT,FileGenericBase.T_INT
    };
    private int[] dataLen = {
        2,
        4, 4, 4, 4, 4,
        4, 4, 4, 4, 4,
        4, 4, 4, 4, 4,
        4, 4, 4, 4, 4,
        4, 4, 4, 4, 4,
        4, 4, 4, 4, 4,
        4, 4, 4, 4, 4,
        4,4,4
    };

    public Vector<String> genAuditFiles(String balanceWaterNo) throws Exception {
        FileLccDao dao = new FileLccDao();
        //从数据库中获取记录，记录按线路分开
        Hashtable records = dao.getRecordsForAudit(balanceWaterNo);
        //判断是否需要按不同版本生成不同文件。版本号是最大的不需要，版本号小于最大版本号，需要减少下发字段
        //从数据库中取版本号20191110
        VersionCompareResult compResult = this.isNeedDiffVer(balanceWaterNo);
        int cutNum;
        if(compResult.isDiff()){//需要按低版本生成
           cutNum =compResult.getCutNum();
           dataType =this.getArrayPart(dataType, cutNum);
           dataLen =this.getArrayPart(dataLen, cutNum);
           records =this.getHashtablePart(records, cutNum);
        }
        //生成下发的文件
        /*
        Vector<String> fileNames = this.writeDataToFilesForGeneric(records, balanceWaterNo, AuditER.DATA_TYPE_SDF, 
                                                          dataTypeHead,dataLenHead,dataType,dataLen,
                                                          dataTypeTail,dataLenTail,FrameCodeConstant.PATH_FILE_AUDIT_LCC);
         */
        //20151130文件分隔符由一个回车换行改为3个回车换行
        Vector<String> fileNames = this.writeDataToFilesForGeneric_3(records, balanceWaterNo, AuditER.DATA_TYPE_SDF,
                dataTypeHead, dataLenHead, dataType, dataLen,
                dataTypeTail, dataLenTail, FrameCodeConstant.PATH_FILE_AUDIT_LCC);
        return fileNames;
    }

    private VersionCompareResult isNeedDiffVer(String balanceWaterNo) throws Exception {
        FileLccDao dao = new FileLccDao();
        String recordVer = dao.getRecordsVersionForAudit(balanceWaterNo);
        VersionCompareResult result = new VersionCompareResult();
        if (recordVer == null || recordVer.length() == 0) {
            return result;
        }
        int len = RECORD_VERSIONS.length;
        String recordVerCur = RECORD_VERSIONS[len - 1];
        int cutNum = this.getCutNum(recordVer);
        if (cutNum > 0) {
            result.setDiff(true);
            result.setCutNum(cutNum);
        }
        return result;

    }

}
