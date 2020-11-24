/*
 * 文件名：ImageUtil
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.ecpmcs.util;

import com.goldsign.ecpmcs.env.ConfigConstant;
import com.sun.media.jai.codec.BMPEncodeParam;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageEncoder;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;
import javax.swing.ImageIcon;



/*
 * 〈图像处理〉
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2014-4-20
 */

public class ImageUtil {
    
    public static String jpg2bmp(String imgDir){
        
        File filex = new File(imgDir);
        if(!filex.exists()){
            return "";
        }
        
         /*jpg转换到bmp格式*/  
        String outputFile = null;
        RenderedOp src = null;
        OutputStream os = null;
        BMPEncodeParam param = null;
        ImageEncoder enc = null;
        try {
            outputFile = imgDir.substring(0, imgDir.length()-3) + "bmp";
            String idir = ConfigUtil.getConfigValue(ConfigConstant.PhotoTag, 
                ConfigConstant.PhotoImgDirTag);
            String bdir = ConfigUtil.getConfigValue(ConfigConstant.PhotoTag, 
                ConfigConstant.PhotoBmpDirTag);
            //String idir = "D:\\photo";
            //String bdir = "D:\\bmp";
            //切换BMP保存路径
            outputFile = outputFile.replace(idir, bdir);
            File file = new File(outputFile);
            File parent = new File(file.getParent());
            if(!parent.exists()){
                parent.mkdirs();
            }
            if(file.exists()){
                file.delete();
            }
            src = JAI.create("fileload", imgDir);   
            os = new FileOutputStream(outputFile);   
            param = new BMPEncodeParam();   
            enc = ImageCodec.createImageEncoder("BMP", os, param);
            enc.encode(src);
            
            src = null;
            param = null;
            enc = null;
            os.flush();
            os.close();
            os = null;
        } catch (Exception e) {
            new Exception("相片转换失败");
            e.printStackTrace();
        }

        if (os != null) {
            src = null;
            enc = null;
            param = null;
            try {
                os.close(); //关闭流
            } catch (Exception e) {
                e.fillInStackTrace();
            }
            os = null;
        }
        //删除原文件
//        filex.delete();
        return outputFile;
    }

    public static byte[] image2Bytes(String imagePath) {
        ImageIcon ima = new ImageIcon(imagePath);
        BufferedImage bu = new BufferedImage(ima.getImage().getWidth(null), 
        ima.getImage().getHeight(null), BufferedImage.TYPE_INT_RGB);
        ByteArrayOutputStream imageStream = new ByteArrayOutputStream();
        try {
            //把这个jpg图像写到这个流中去,这里可以转变图片的编码格式
            ImageIO.write(bu, "jpg", imageStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] tagInfo = imageStream.toByteArray();

        return tagInfo;
    }

    public static void main(String[] args) {
//            byte[]info= image2Bytes("C:/Users/Administrator/Pictures/desktop.jpg");
//
//            try {
//                FileWriter file=new FileWriter("d:\\text.txt");
//                for(byte in:info){
//                        file.write(String.valueOf((int)in));
//                }
//                file.flush();
//            }catch (Exception e) {
//                    e.printStackTrace();
//            }

       jpg2bmp("D:/bmp/bmp.jpg");
        //File f = new File("C:/Users/Administrator/Pictures/scp/1/43010319360620201X.jPg");
        //f.exists();
    }
}


