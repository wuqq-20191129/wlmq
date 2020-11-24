/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.kmsfront.struct.util;

import com.goldsign.commu.commu.util.DateHelper;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 *
 * @author lenovo
 */
public class LogWriterUtil {

    private static FilePrintWriter getLogWriter() throws IOException{
        
        File logFile = new File("log.log");
        try {
            if (!logFile.exists()) {
                logFile.createNewFile();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        FilePrintWriter writer = new FilePrintWriter(logFile, true);
        
        return writer;
    }
    
    public static synchronized void writeStartSucc(String batchNo){
        
        FilePrintWriter writer = null;
        try {
            writer = getLogWriter();
            String log = DateHelper.timeToStringSSS(new Date()) + ":query," + batchNo + ":start";

            writer.println(log);
            writer.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (null != writer) {
                writer.close();
                writer = null;
            }
        }
    }
    
    public static synchronized void writeEndSucc(String batchNo){
        
        FilePrintWriter writer = null;
        try {
            writer = getLogWriter();
            String log = DateHelper.timeToStringSSS(new Date())+":query,"+batchNo+":0,end";

            writer.println(log);
            writer.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (null != writer) {
                writer.close();
                writer = null;
            }
        }
    }
    
    public static synchronized void writeEndFail(String batchNo, String msg){
        
        FilePrintWriter writer = null;
        try {
            writer = getLogWriter();
            String log = DateHelper.timeToStringSSS(new Date()) + ":query," + batchNo + ":-1,end:" + msg;

            writer.println(log);
            writer.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (null != writer) {
                writer.close();
                writer = null;
            }
        }
    }
    
    public static void main(String[] args){
        writeStartSucc("12345333");
        writeEndSucc("123452222");
        writeEndFail("123452332","错误啦");
    }
}
