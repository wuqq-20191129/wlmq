/*
 * 文件名：CSVFileFilter
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.csfrm.util;

import java.io.File;
import javax.swing.filechooser.FileFilter;


/*
 * 检验是否为CSV文件
 * @author     lindaquan
 * @version    V1.0
 */

public class CSVFileFilter extends FileFilter{
    
    //检验是否为CSV文件
    public boolean accept( File f ){
        if( f != null ){
            if( f.isDirectory() ){
                return true;
            }
            if( getExtension().equalsIgnoreCase( getExtensionFile( f ) ) ){
                return true;
            }
        }
        return false;
    }

    public String getDescription(){
        return "csv format";
    }

    public String getExtension(){
        return "csv";
    }

    //取文件后缀
    public String getExtensionFile(File file){

        String fileName = file.getName();
        int index = fileName.lastIndexOf('.');
        String extension = "";

        if (index > 0 && index < fileName.length() - 1)
        {
            extension = fileName.substring(index + 1).toLowerCase();
        }
        return extension;
    }
}
