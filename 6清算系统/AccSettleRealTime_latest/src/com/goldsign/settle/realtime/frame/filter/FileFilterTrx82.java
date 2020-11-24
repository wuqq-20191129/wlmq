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
public class FileFilterTrx82 extends FileFilterTrxBase implements FileFilter{
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
        String fileId = fileName.substring(0, 5);
        
        for (String id : FILE_DATA_TYPE_NAME_NETPAID) {
            if (id.equals(fileId)) {
                return true;
            }
        }
        return false;

    }
    
}
