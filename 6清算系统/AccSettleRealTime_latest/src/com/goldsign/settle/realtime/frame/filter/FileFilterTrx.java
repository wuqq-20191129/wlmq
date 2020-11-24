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
public class FileFilterTrx extends FileFilterTrxBase implements FileFilter {

    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return false;
        }
        //20161226 added by hejj
        if (!this.isTimeToProcessed(f)) {
            return false;
        }

        String fileName = f.getName();
        if (fileName.length() < 5) {
            return false;
        }
        String fileId = fileName.substring(0, 3);
        String fileIdM = fileName.substring(0, 5);//add by hejj 20160109
        for (String id : FILE_DATA_TYPE_NAME) {
            if (id.equals(fileId)) {
                if (this.isTrxFileMobile(fileIdM)
                        || this.isTrxFileNetPaid(fileIdM)
                        || this.isTrxFileQrCode(fileIdM))//add by hejj 20160109 过滤空充/互联网交易文件 20190612过滤二维码平台文件
                {
                    return false;
                }

                return true;
            }
        }
        return false;

    }
}
