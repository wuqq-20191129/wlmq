/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.file;

import com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;
import com.goldsign.settle.realtime.frame.io.FileWriterBase;
import com.goldsign.settle.realtime.frame.util.DateHelper;
import com.goldsign.settle.realtime.frame.util.FileUtil;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public abstract class FileTccBase {

    protected static String CHARSET = "GBK";

    public abstract String genFileTcc(String balanceWaterNo, String fileNameBase) throws Exception;
    protected final static char[] CRLF_UNIX = {0x0a};//换行符

    protected String getFileNameTcc(String filNameBase) {
        String fileName = filNameBase + "_" + DateHelper.dateToString(new Date()) + "." + "csv";
        return fileName;
    }
    protected boolean writeDataToFile(Vector<String> data, String fileName, String balanceWaterNo) throws Exception {
        return this.writeDataToFileGBK(data, fileName, balanceWaterNo);
    }

    protected boolean writeDataToFileUTF(Vector<String> data, String fileName, String balanceWaterNo) throws Exception {
        boolean result = false;
        FileWriter out = null;
        File file = null;

        String crc;
        String dir = FrameCodeConstant.PATH_FILE_TCC_DOWNLOAD + "/" + balanceWaterNo;
        try {
            file = new File(dir, fileName);
            FileUtil.mkDir(dir);
            out = new FileWriter(file);

            for (String line : data) {
                out.write(line, 0, line.length());
                out.write(CRLF_UNIX);
                out.flush();
            }
            result = true;
        } catch (Exception e) {
            throw e;
        } finally {
            this.close(out);
        }
        return result;
    }

    protected boolean writeDataToFileGBK(Vector<String> data, String fileName, String balanceWaterNo) throws Exception {
        boolean result = false;
        FileOutputStream fos = null;
        OutputStreamWriter osr = null;
        BufferedWriter bw = null;

        String dir = FrameCodeConstant.PATH_FILE_TCC_DOWNLOAD + "/" + balanceWaterNo;
        FileUtil.mkDir(dir);
        String fileNameFull = dir + "/" + fileName;
        try {
            fos = new FileOutputStream(fileNameFull);
            osr = new OutputStreamWriter(fos, CHARSET);
            bw = new BufferedWriter(osr);
            

            for (String line : data) {
                bw.write(line, 0, line.length());
                bw.write(CRLF_UNIX);
                bw.flush();
            }
            result = true;
        } catch (Exception e) {
            throw e;
        } finally {
            this.closeFile(fos,osr,bw);
        }
        return result;
    }
    protected void closeFile(FileOutputStream fos, OutputStreamWriter osr, BufferedWriter bw) {
        try {

            if (fos != null) {
                fos.close();
            }
            if (osr != null) {
                osr.close();
            }
            if (bw != null) {
                bw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void close(FileWriter out) {
        if (out != null) {
            try {
                out.close();
                //encode(paraFile);
            } catch (Exception e) {
            }
        }

    }

}
