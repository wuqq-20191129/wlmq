/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.test.function;

import com.goldsign.settle.realtime.frame.export.ExportBase;
import com.goldsign.settle.realtime.frame.util.FileUtil;
import java.io.File;

/**
 *
 * @author hejj
 */
public class FileEmpty {
    
     public static void main(String[] args){
         String path="d:/temp/control";
         String fileName="control.txt";
         FileUtil util = new FileUtil();
         util.createControlFile(path, fileName);

         
     }
    
}
