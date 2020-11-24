/*
 * 文件名：FileAction
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.frame.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.actions.DispatchAction;


/*
 * 〈一句话功能简述〉
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-11-16
 */


public class FileAction extends DispatchAction {

    //实现文件的下载
    public ActionForward downFile(HttpServletRequest request ,HttpServletResponse response) throws
            Exception {
        String path = "E:/testsave.txt";//request.getParameter("path");
        System.out.println(path+"111");
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        OutputStream fos = null;
        InputStream fis = null;
        
      //如果是从服务器上取就用这个获得系统的绝对路径方法。 String filepath = servlet.getServletContext().getRealPath("/" + path);
        String filepath=path;
        System.out.println("文件路径"+filepath);
        File uploadFile = new File(filepath);
        fis = new FileInputStream(uploadFile);
        bis = new BufferedInputStream(fis);
        fos = response.getOutputStream();
        bos = new BufferedOutputStream(fos);
        //这个就就是弹出下载对话框的关键代码
        response.setHeader("Content-disposition",
                           "attachment;filename=" +
                           URLEncoder.encode(path, "utf-8"));
        int bytesRead = 0;
        //这个地方的同上传的一样。我就不多说了，都是用输入流进行先读，然后用输出流去写，唯一不同的是我用的是缓冲输入输出流
        byte[] buffer = new byte[8192];
        while ((bytesRead = bis.read(buffer, 0, 8192)) != -1) {
            bos.write(buffer, 0, bytesRead);
        }
        bos.flush();
        fis.close();
        bis.close();
        fos.close();
        bos.close();
        return null;
    }

}


