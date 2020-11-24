/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.test.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author hejj
 */
public class FileZipUtil {

    public static void main(String[] args) {

        String path = "D:/temp";
        String zipFileName = "测试.ZIP";
        String zipedFileName = "CSACC1301_TEST.docx";
        String zipedFileName1 = "CSACC1301_TEST_1.docx";
        Vector<String> zipedFileNames= new Vector();
        zipedFileNames.add(zipedFileName);
        zipedFileNames.add(zipedFileName1);
        
        FileZipUtil util = new FileZipUtil();
        try {
            util.zipFiles(path, zipFileName, zipedFileNames);
           // util.zipFile(path, zipFileName, zipedFileName);
           // util.unzipFile(path, zipFileName);
        } catch (Exception ex) {
            Logger.getLogger(FileZipUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void zipFile(String path, String zipFileName, String zipedFileName) throws Exception {
        String fullFile = path + "/" + zipFileName;
        File f = new File(fullFile);
        CRC32 crc32 = new CRC32();
        FileOutputStream fos = null;//new FileOutputStream(f);
        CheckedOutputStream cos = null;//new CheckedOutputStream(fos, crc32);
        ZipOutputStream zos = null;//new ZipOutputStream(cos);
        try {
            fos = new FileOutputStream(f);
            cos = new CheckedOutputStream(fos, crc32);
            zos = new ZipOutputStream(cos);
            this.zipFileEntry(zos, path, zipedFileName);
        } catch (Exception e) {
            throw e;
        } finally {
            this.closeOut(zos);
        }


    }

    public void zipFiles(String path, String zipFileName, Vector<String> zipedFileNames) throws Exception {
        String fullFile = path + "/" + zipFileName;
        File f = new File(fullFile);
        CRC32 crc32 = new CRC32();
        FileOutputStream fos = null;//new FileOutputStream(f);
        CheckedOutputStream cos = null;//new CheckedOutputStream(fos, crc32);
        ZipOutputStream zos = null;//new ZipOutputStream(cos);
        try {
            fos = new FileOutputStream(f);
            cos = new CheckedOutputStream(fos, crc32);
            zos = new ZipOutputStream(cos);
            for (String zipedFileName : zipedFileNames) {
                this.zipFileEntry(zos, path, zipedFileName);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.closeOut(zos);
        }


    }

    public void unzipFile(String path, String zipFileName) throws Exception {
        String fullFile = path + "/" + zipFileName;
        File f = new File(fullFile);

        FileInputStream fis = null;
        ZipInputStream zis = null;
        ZipEntry entry;
        try {
            fis = new FileInputStream(f);
            zis = new ZipInputStream(fis);
             while(( entry = zis.getNextEntry())!=null){
                this.unzipFileEntry(zis, path,entry);
            }
            
        } catch (Exception e) {
            throw e;
        } finally {
            this.closeIn(zis);
        }


    }

    private void zipFileEntry(ZipOutputStream zos, String path, String name) throws Exception {
        String fullName = path + "/" + name;
        File f = new File(fullName);
        FileInputStream fis = null;//;
        ZipEntry entry;//= new ZipEntry(fullName);
        String temp="hejj/"+name;
        String entryName =temp;//new String(temp.getBytes(),"GBK");//temp;// new String(temp.getBytes("UTF-8"),"GBK");
        int n;
        try {
            fis = new FileInputStream(f);
            entry = new ZipEntry(entryName);
            zos.putNextEntry(entry);
            
            while ((n = fis.read()) != -1) {
                zos.write(n);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.closeIn(fis);
        }
    }
    private void createUnzipPath(String fullName){
        int index = fullName.lastIndexOf("/");
        String pathName = fullName.substring(0,index);
        File path = new File(pathName);
        if(!path.exists())
            path.mkdirs();
        
    }

    private void unzipFileEntry(ZipInputStream zis, String path,ZipEntry entry) throws Exception {
        String fullName;// = path + "/" + name;
        File f;//= new File(fullName);
        FileOutputStream fos = null;//;
        //ZipEntry entry;//= new ZipEntry(fullName);

        int n;
        try {
           
            fullName = path+"/"+entry.getName();
            this.createUnzipPath(fullName);
            f = new File(fullName);
            fos = new FileOutputStream(f);

            while ((n = zis.read()) != -1) {
                fos.write(n);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.closeOut(fos);
        }
    }

    private void closeIn(InputStream is) {
        try {
            if (is != null) {
                is.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void closeOut(OutputStream out) {
        try {
            if (out != null) {
                out.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
