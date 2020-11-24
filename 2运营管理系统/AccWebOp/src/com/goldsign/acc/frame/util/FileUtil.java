/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author hejj
 */
public class FileUtil {
    
     public static final int BUFFER_SIZE = 4096;
    
    public HashMap getConfigPropertiesByAppPath(HttpServletRequest req, String configFile) throws Exception {
        String appRoot = req.getSession().getServletContext().getRealPath("/");
        String fileName = appRoot + "/properties/" + configFile;
        //      InputStream in =this.getClass().getResourceAsStream(configFile);
        FileInputStream fis = new FileInputStream(fileName);
        String line = null;
        int index = -1;
        String key = null;
        String value = null;
        HashMap properties = new HashMap();
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            isr = new InputStreamReader(fis, "GBK");
            br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) {
                    continue;
                }
                index = line.indexOf("=");
                if (index == -1) {
                    continue;
                }
                key = line.substring(0, index);
                value = line.substring(index + 1);
                if (value.startsWith("${ROOT}")) {
                    value = req.getSession().getServletContext().getRealPath("/") + value.substring(7);
                }
                value = value.trim();
                properties.put(key, value);

            }

        } catch (Exception e) {
            //     e.printStackTrace();
            throw e;
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (isr != null);
                isr.close();
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return properties;
    }
    
     //---------------------------------------------------------------------
    // Copy methods for java.io.File
    //---------------------------------------------------------------------
    /**
     * Copy the contents of the given input File to the given output File.
     *
     * @param in the file to copy from
     * @param out the file to copy to
     * @return the number of bytes copied
     * @throws IOException in case of I/O errors
     */
    public static int copy(File in, File out) throws IOException {
//        Assert.notNull(in, "No input File specified");
//        Assert.notNull(out, "No output File specified");
        return copy(new BufferedInputStream(new FileInputStream(in)),
                new BufferedOutputStream(new FileOutputStream(out)));
    }

    /**
     * Copy the contents of the given byte array to the given output File.
     *
     * @param in the byte array to copy from
     * @param out the file to copy to
     * @throws IOException in case of I/O errors
     */
    public static void copy(byte[] in, File out) throws IOException {
//        Assert.notNull(in, "No input byte array specified");
//        Assert.notNull(out, "No output File specified");
        ByteArrayInputStream inStream = new ByteArrayInputStream(in);
        OutputStream outStream = new BufferedOutputStream(new FileOutputStream(
                out));
        copy(inStream, outStream);
    }

    /**
     * Copy the contents of the given input File into a new byte array.
     *
     * @param in the file to copy from
     * @return the new byte array that has been copied to
     * @throws IOException in case of I/O errors
     */
    public static byte[] copyToByteArray(File in) throws IOException {
//        Assert.notNull(in, "No input File specified");
        return copyToByteArray(new BufferedInputStream(new FileInputStream(in)));
    }

    //---------------------------------------------------------------------
    // Copy methods for java.io.InputStream / java.io.OutputStream
    //---------------------------------------------------------------------
    /**
     * Copy the contents of the given InputStream to the given OutputStream.
     * Closes both streams when done.
     *
     * @param in the stream to copy from
     * @param out the stream to copy to
     * @return the number of bytes copied
     * @throws IOException in case of I/O errors
     */
    public static int copy(InputStream in, OutputStream out) throws IOException {
//        Assert.notNull(in, "No InputStream specified");
//        Assert.notNull(out, "No OutputStream specified");
        try {
            int byteCount = 0;
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = -1;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
                byteCount += bytesRead;
            }
            out.flush();
            return byteCount;
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
            }
            try {
                out.close();
            } catch (IOException ex) {
            }
        }
    }

    /**
     * Copy the contents of the given byte array to the given OutputStream.
     * Closes the stream when done.
     *
     * @param in the byte array to copy from
     * @param out the OutputStream to copy to
     * @throws IOException in case of I/O errors
     */
    public static void copy(byte[] in, OutputStream out) throws IOException {
//        Assert.notNull(in, "No input byte array specified");
//        Assert.notNull(out, "No OutputStream specified");
        try {
            out.write(in);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
            }
        }
    }

    /**
     * Copy the contents of the given InputStream into a new byte array. Closes
     * the stream when done.
     *
     * @param in the stream to copy from
     * @return the new byte array that has been copied to
     * @throws IOException in case of I/O errors
     */
    public static byte[] copyToByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream(BUFFER_SIZE);
        copy(in, out);
        return out.toByteArray();
    }

    //---------------------------------------------------------------------
    // Copy methods for java.io.Reader / java.io.Writer
    //---------------------------------------------------------------------
    /**
     * Copy the contents of the given Reader to the given Writer. Closes both
     * when done.
     *
     * @param in the Reader to copy from
     * @param out the Writer to copy to
     * @return the number of characters copied
     * @throws IOException in case of I/O errors
     */
    public static int copy(Reader in, Writer out) throws IOException {
//        Assert.notNull(in, "No Reader specified");
//        Assert.notNull(out, "No Writer specified");
        try {
            int byteCount = 0;
            char[] buffer = new char[BUFFER_SIZE];
            int bytesRead = -1;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
                byteCount += bytesRead;
            }
            out.flush();
            return byteCount;
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
            }
            try {
                out.close();
            } catch (IOException ex) {
            }
        }
    }

    /**
     * Copy the contents of the given String to the given output Writer. Closes
     * the write when done.
     *
     * @param in the String to copy from
     * @param out the Writer to copy to
     * @throws IOException in case of I/O errors
     */
    public static void copy(String in, Writer out) throws IOException {
//        Assert.notNull(in, "No input String specified");
//        Assert.notNull(out, "No Writer specified");
        try {
            out.write(in);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
            }
        }
    }

    /**
     * Copy the contents of the given Reader into a String. Closes the reader
     * when done.
     *
     * @param in the reader to copy from
     * @return the String that has been copied to
     * @throws IOException in case of I/O errors
     */
    public static String copyToString(Reader in) throws IOException {
        StringWriter out = new StringWriter();
        copy(in, out);
        return out.toString();
    }

    /**
     * 拷贝文件夹
     *
     * @param src
     * @param des
     * @throws IOException
     */
    public static void copyDirectory(String src, String des) throws IOException {
        File file1 = new File(src);
        File[] fs = file1.listFiles();
        File file2 = new File(des);
        if (!file2.exists()) {
            file2.mkdirs();
        }
        for (File f : fs) {
            if (f.isFile()) {
                copy(new File(f.getPath()), new File(des + "\\" + f.getName())); //调用文件拷贝的方法
            } else if (f.isDirectory()) {
                copyDirectory(f.getPath(), des + "\\" + f.getName());
            }
        }

    }
    
}
