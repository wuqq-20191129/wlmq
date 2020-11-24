/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.acc.frame.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * 文件导入导出
 * @datetime 2017-7-12 20:47:37
 * @author lind
 */
public class FileImportExport {

    public FileImportExport() {
    }
    
    /*
    导入excel
    */
    public List<ArrayList<String>> uploadImport(HttpServletRequest request) {
        List<ArrayList<String>> list = null;
        
        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        if(multipartResolver.isMultipart(request)){
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
            //将request变成多部分request
            MultipartFile multipartFile = multiRequest.getFile("file");
            //目标目录
            String source = request.getRealPath("") + this.getClass().getSimpleName() + "\\";
            File dir = new File(source);
            if(!dir.exists()){
                dir.mkdirs();
            }
            try {
                File file = new File(source+multipartFile.getOriginalFilename());
                multipartFile.transferTo(file);
                if(file.exists()){
                    ExeclUtil read = new ExeclUtil();
                    list = new ArrayList<ArrayList<String>>();
                    list = read.readExcel(file);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (IllegalStateException ex) {
                ex.printStackTrace();
            }
            
        }
        return list;
    }

}
