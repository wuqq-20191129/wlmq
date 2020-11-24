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
public class FileFilterOctUpload implements FileFilter{
    public static String[] FILE_DATA_TYPE_NAME = {"TRX","BLA","AUF"};

    @Override
    public boolean accept(File f) {
         if (f.isDirectory()) {
            return false;
        }
        String fileName = f.getName();
        if (fileName.length() < 3) {
            return false;
        }
        String fileId = fileName.substring(0, 3);
        for (String id : FILE_DATA_TYPE_NAME) {
            if (id.equals(fileId)) {
                return true;
            }
        }
        return false;
    }
    
}
