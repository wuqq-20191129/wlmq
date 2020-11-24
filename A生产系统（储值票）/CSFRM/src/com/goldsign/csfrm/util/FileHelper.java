package com.goldsign.csfrm.util;

import java.util.Date;
import org.apache.log4j.Logger;

/**
 * 文件处理工具类
 *
 * @author lenvo
 */
public class FileHelper {

    private static Logger logger = Logger.getLogger(FileHelper.class.getName());
    
    /**
     * 取得文件名
     * 
     * @param fileNamePre
     * @param deviceId
     * @return 
     */
    public static String getFileName(String fileNamePre) {

        String date = DateHelper.dateToStr8yyyyMMdd(new Date());
        String fileName = fileNamePre + "." + date;

        return fileName;
    }

}
