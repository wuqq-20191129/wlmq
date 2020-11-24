/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.filter;

import java.io.File;
import java.io.FileFilter;

/**
 *
 * @author hejj
 */
public class FileFilterOther extends FileFilterTrxBase implements FileFilter{

    public static String[] FILE_DATA_TYPE_NAME = {"PRO","AUD","REG","STL"};

    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return false;
        }
         //20161226 added by hejj
        if(!this.isTimeToProcessed(f))
            return false;
        
        String fileName = f.getName();
        if (fileName.length() < 5) {
            return false;
        }
        String fileId = fileName.substring(0, 3);
        String fileIdM = fileName.substring(0, 5);//add by hejj 20160109
        for (String id : FILE_DATA_TYPE_NAME) {
            if (id.equals(fileId)) {
                if (this.isOtherFileMobile(fileIdM)
                        ||this.isOtherFileNetPaid(fileIdM)
                        ||this.isOtherFileQrCode(fileIdM))//add by hejj 20160109 过滤空充/互联网对账文件 20190612过滤二维码平台
                {
                    return false;
                }
                return true;
            }
        }
        return false;

    }
    
}
