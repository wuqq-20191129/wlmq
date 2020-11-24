package com.goldsign.ecpmcs.util;

import com.goldsign.ecpmcs.env.ConfigConstant;
import java.io.File;


public class PhotoSearcher {

    public static String findFiles(String identityType, String fileName, String imgDir) {
        
        String returnDir = "";
        //判断目录是否存在
        File baseDir = new File(imgDir);
        if (!baseDir.exists() || !baseDir.isDirectory()){
//            System.out.println("文件查找失败：" + imgDir + "不是一个目录！");
        } else {
            String[] dirList = baseDir.list();
    	    for (int i = 0; i < dirList.length; i++) {
                if(dirList[i].equals(identityType.trim())){
                    File fileDir = new File(imgDir + "\\" + identityType);
                    String[] fileList = fileDir.list();
                    for(int j = 0; j < fileList.length; j++){
                        if(fileList[j].toUpperCase().equals(fileName.trim().toUpperCase())){
                            returnDir = fileDir.getAbsolutePath() + "\\" + fileName;
                            break;
                        }
                    }
                    break;
                }
    	    }
        }
        
        return returnDir;
    }
    
    public static String fileJPG(String identityType, String identityId) {

        String returnDir = "";
        //查询文件后缀
        String suffix = ConfigUtil.getConfigValue(ConfigConstant.PhotoTag, 
                ConfigConstant.PhotoSuffixTag);
        //在此目录中找文件
        String imgDir = ConfigUtil.getConfigValue(ConfigConstant.PhotoTag, 
                ConfigConstant.PhotoImgDirTag);

        String fileName = identityId + "." + suffix; 
        returnDir = findFiles(identityType, fileName, imgDir); 
        
        return returnDir;
    }

}
