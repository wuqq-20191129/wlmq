/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.filter;

import com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;
import com.goldsign.settle.realtime.frame.util.FileUtil;
import java.io.File;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class FileFilterTrxBase {
    private static Logger logger = Logger.getLogger(FileFilterTrxBase.class.getName());

    public static String[] FILE_DATA_TYPE_NAME = {"TRX"};
    public static String[] FILE_DATA_TYPE_NAME_MOBILE = {"TRX80","TRX81"}; 
    public static String[] FILE_DATA_TYPE_OHTER_NAME_MOBILE = {"STL80","STL81"};
    public static String[] FILE_DATA_TYPE_NAME_QRCODE = {"TRX70","TRX71"};//20190612 20200703增加71
    public static String[] FILE_DATA_TYPE_OHTER_NAME_QRCODE = {"STL70","STL71"};//20190612 20200703增加71
    
    public static String[] FILE_DATA_TYPE_NAME_NETPAID = {"TRX82"};
    public static String[] FILE_DATA_TYPE_OHTER_NAME_NETPAID = {"STL82","ORD82","ORI82"};

    public boolean isTrxFileMobile(String fileNameId) {
        if (fileNameId == null || fileNameId.length() == 0) {
            return false;
        }
        for (String id : FILE_DATA_TYPE_NAME_MOBILE) {
            if (id.equals(fileNameId)) {

                return true;
            }
        }
        return false;

    }
    public boolean isTrxFileQrCode(String fileNameId) {
        if (fileNameId == null || fileNameId.length() == 0) {
            return false;
        }
        for (String id : FILE_DATA_TYPE_NAME_QRCODE) {
            if (id.equals(fileNameId)) {

                return true;
            }
        }
        return false;

    }
    public boolean isTimeToProcessed(File f) {
        long diff =Math.abs(System.currentTimeMillis()-f.lastModified());
        if(diff< FrameCodeConstant.FILE_PROCESSED_BEFORE){
            return false;
        }
        return true;
        
    }
    
    public boolean isTrxFileNetPaid(String fileNameId) {
        if (fileNameId == null || fileNameId.length() == 0) {
            return false;
        }
        for (String id : FILE_DATA_TYPE_NAME_NETPAID) {
            if (id.equals(fileNameId)) {

                return true;
            }
        }
        return false;

    }
    
    public boolean isOtherFileMobile(String fileNameId) {
        if (fileNameId == null || fileNameId.length() == 0) {
            return false;
        }
        for (String id : FILE_DATA_TYPE_OHTER_NAME_MOBILE) {
            if (id.equals(fileNameId)) {

                return true;
            }
        }
        return false;

    }
    public boolean isOtherFileQrCode(String fileNameId) {
        if (fileNameId == null || fileNameId.length() == 0) {
            return false;
        }
        for (String id : FILE_DATA_TYPE_OHTER_NAME_QRCODE) {
            if (id.equals(fileNameId)) {

                return true;
            }
        }
        return false;

    }
    public boolean isOtherFileNetPaid(String fileNameId) {
        if (fileNameId == null || fileNameId.length() == 0) {
            return false;
        }
        for (String id : FILE_DATA_TYPE_OHTER_NAME_NETPAID) {
            if (id.equals(fileNameId)) {

                return true;
            }
        }
        return false;

    }
}
