/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.esmcs.thread;

import com.goldsign.esmcs.service.IFileService;
import com.goldsign.esmcs.service.impl.FileService;

/**
 *检查ES漏取文件，即ES上传信息成功，
 * 但通讯返回的审计和错误文件里面，都没有的文件
 * @author limj
 *
 */
public class CheckAuditThread extends Thread{
    private IFileService fileService;
    public CheckAuditThread(){
        fileService = new FileService();
    }
    public void run(){
        try{
        }catch(Exception ex){
    
    }
    }
}
