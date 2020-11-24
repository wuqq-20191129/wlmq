/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.export;

import com.goldsign.settle.realtime.app.vo.ExportDbResult;
import com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;
import com.goldsign.settle.realtime.frame.thread.OctImportTrxThread;
import com.goldsign.settle.realtime.frame.util.CrcUtil;
import com.goldsign.settle.realtime.frame.util.FileUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Vector;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public abstract class ExportBase {
    private static Logger logger = Logger.getLogger(ExportBase.class.
            getName());
    /*
     * 导出数据格式相关
     */

    public final static char[] CRLF = {0x0d, 0x0a};
    public final static String CRLF_S = "\\r\\n";
    public final static int DIR_LEFT = 0;//左补
    public final static int DIR_RIGHT = 1;//右补
    public final static int DIR_NONE = 3;//无需补
    public final static String SPACE = " ";//补空格
    public final static String ZERO = "0";//补0
    public final static String NONE = " ";//补空格
  /*
     * 导出文件名称相关
     */
    public final static String SERVICER_MTR = "80000013";//地铁清算服务商
    public final static String PLACE_MTR = "00000001";//地铁采集点
    public final static String PLACE_OCT = "00000001";//公交采集点
    public final static String DATA_TYPE_TRX = "JY";//交易数据类型
    public final static String DATA_TYPE_TRX_MOBILE = "TRX";//交易数据类型
    public final static String DATA_TYPE_TRX_MOBILE_FILE = "MTX";//交易数据类型
    public final static String DATA_TYPE_BLA = "LK";//名单申请数据类型
    public final static String DATA_TYPE_AUF = "RZ";//文件审计数据类型
    public final static String DATA_TYPE_TCM = "XF";//交易通讯数据类型
    public final static String DATA_TYPE_SCM = "XF";//结算通讯数据类型
    
    public final static String ACC_LINE_STATION = "9900";//acc线路车站代码
    
     public final static String DATA_TYPE_TRX_CLASS = "TRX";//交易数据类型
    public final static String DATA_TYPE_BLA_CLASS = "BLA";//名单申请数据类型
    public final static String DATA_TYPE_AUF_CLASS = "AUF";//文件审计数据类型

    
    public final static String DATA_TYPE_AUB = "QS";//交易结算结果
    public final static String DATA_TYPE_ERR = "CW";//交易错误记录
    public final static String DATA_TYPE_BLL = "MD";//黑名单
    public final static String DATA_TYPE_AUS = "CJ";//采集状态
    
    /**
     * 导出数据所属系统
     */
    public static final String SYS_OCT = "Oct";//公交IC卡
    public static final char[] FIELD_DELIM = {0x09};//TAB
     public static final char[] FIELD_DELIM_MOBILE = {','};//
    
    public static final String SYS_MOBILE = "Mobile";//手机支付
    
    

    public abstract String genExportFiles(String balanceWaterNo,int balanceWaterNoSub,String seq) throws Exception;
    public abstract String genExportFilesSpecial(String balanceWaterNo,int balanceWaterNoSub,String seq) throws Exception;
    
    
    protected boolean writeDataToFile(Vector<String> data, String fileName) throws Exception {
        boolean result = false;
        FileWriter out = null;
        File file = null;

        String crc;
        try {
            file = new File(FrameCodeConstant.PATH_FILE_OCT_EXPORT_TRX_FILE, fileName);
            FileUtil.mkDir(FrameCodeConstant.PATH_FILE_OCT_EXPORT_TRX_FILE);
            out = new FileWriter(file);

            for (String line : data) {
                out.write(line, 0, line.length());
                out.write(CRLF);
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
    protected boolean writeDataToFileForMobile(Vector<String> data, String fileName,String path) throws Exception {
        boolean result = false;
        FileWriter out = null;
        File file = null;

        String crc;
        String totalLine="";
        try {
            file = new File(path, fileName);
            FileUtil.mkDir(path);
            out = new FileWriter(file);
            
            for (String line : data) {
                out.write(line, 0, line.length());
                out.write(CRLF);
                totalLine +=line+CRLF_S;
                
                out.flush();
            }
            char[] cs = totalLine.toCharArray();
             crc = CrcUtil.getCRC32ValueByChar(cs,cs.length, 8);
             crc="CRC:"+crc;
             out.write(crc);
             out.write(CRLF);
             out.flush();
            
            result = true;
        } catch (Exception e) {
            throw e;
        } finally {
            this.close(out);
        }
        return result;
    }
    
    protected boolean writeDataToFileForSettle(Vector<String> data, String fileName) throws Exception {
        boolean result = false;
        FileWriter out = null;
        File file = null;

        String crc;
        try {
            file = new File(FrameCodeConstant.PATH_FILE_OCT_EXPORT_SETTLE_FILE, fileName);
             FileUtil.mkDir(FrameCodeConstant.PATH_FILE_OCT_EXPORT_SETTLE_FILE);
            out = new FileWriter(file);

            for (String line : data) {
                out.write(line, 0, line.length());
                out.write(CRLF);
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
    

    private void close(FileWriter out) {
        if (out != null) {
            try {
                out.close();
                //encode(paraFile);
            } catch (Exception e) {
            }
        }

    }

    public Vector<String> formatRecord(Vector<String[]> recV, int[] format, int[] addDirection, String[] addChars) {
        Vector<String> result = new Vector();
        int len = format.length;
        for (String[] fields : recV) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < len; i++) {
                if (addDirection[i] == ExportBase.DIR_LEFT || addDirection[i] == ExportBase.DIR_NONE) {
                    sb.append(formatFieldForLeft(fields[i], format[i], addChars[i]));
                }
                if (addDirection[i] == ExportBase.DIR_RIGHT) {
                    sb.append(formatFieldForRight(fields[i], format[i], addChars[i]));
                }
                if (i != len - 1) {//非最后一个字段，增加字段分隔符
                    sb.append(FIELD_DELIM);
                }
            }
            result.add(sb.toString());
        }
        return result;
    }
    public Vector<String> formatRecordForMobile(Vector<String[]> recV, int[] format, int[] addDirection, String[] addChars) {
        Vector<String> result = new Vector();
        int len = format.length;
        for (String[] fields : recV) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < len; i++) {
                if (addDirection[i] == ExportBase.DIR_LEFT || addDirection[i] == ExportBase.DIR_NONE) {
                    sb.append(formatFieldForLeft(fields[i], format[i], addChars[i]));
                }
                if (addDirection[i] == ExportBase.DIR_RIGHT) {
                    sb.append(formatFieldForRight(fields[i], format[i], addChars[i]));
                }
                if (i != len - 1) {//非最后一个字段，增加字段分隔符
                    sb.append(FIELD_DELIM_MOBILE);
                }
            }
            result.add(sb.toString());
        }
        return result;
    }

    protected String formatFieldForLeft(String field, int length, String addChar) {
        // String space = " ";
        int i = length - field.length();
        if (i <= 0) {
            field = field.substring(0, length);
        } else {
            StringBuffer sb = new StringBuffer();
            sb.append(field);
            for (int j = 0; j < i; j++) {
                sb.append(addChar);
            }
            field = sb.toString();
        }
        return field;

    }

    protected String formatFieldForRight(String field, int length, String addChar) {
        //String space = " ";
        int i = length - field.length();
        if (i <= 0) {
            field = field.substring(0, length);
        } else {
            StringBuffer sb = new StringBuffer();
            for (int j = 0; j < i; j++) {
                sb.append(addChar);
            }
            sb.append(field);

            field = sb.toString();
        }
        return field;

    }

    protected String getExportFileName(String dataType, String servicer, String place, String balanceWaterNo,String seq) {
        return dataType  + servicer + place  + balanceWaterNo.substring(0, 8)+seq + ".TXT";
    }

    protected String getExportFileNameForZip(String dataType, String servicer, String place, String balanceWaterNo,String seq) {
        return dataType  + servicer  + place + balanceWaterNo.substring(0, 8)+seq + ".ZIP";
    }

    protected void zipFiles(String pathZip, String zipFileName, String pathFile, Vector<String> zipedFileNames) throws Exception {
        String fullFile = pathZip + "/" + zipFileName;
        File f = new File(fullFile);
        CRC32 crc32 = new CRC32();
        FileOutputStream fos = null;
        CheckedOutputStream cos = null;
        ZipOutputStream zos = null;
        FileUtil.mkDir(pathZip);
        try {
            fos = new FileOutputStream(f);
            cos = new CheckedOutputStream(fos, crc32);
            zos = new ZipOutputStream(cos);
            for (String zipedFileName : zipedFileNames) {
                this.zipFileEntry(zos, pathFile, zipedFileName);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.closeOut(zos);
        }


    }

    private void zipFileEntry(ZipOutputStream zos, String path, String name) throws Exception {
        String fullName = path + "/" + name;
        File f = new File(fullName);
        FileInputStream fis = null;
        ZipEntry entry;

        int n;
        try {
            fis = new FileInputStream(f);
            entry = new ZipEntry(name);
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

    public void unzipFile(String zipPath, String zipFileName,String unzipPath) throws Exception {
        String fullFile = zipPath + "/" + zipFileName;
        File f = new File(fullFile);

        FileInputStream fis = null;
        ZipInputStream zis = null;
        ZipEntry entry;
        try {
            fis = new FileInputStream(f);
            zis = new ZipInputStream(fis);
            while ((entry = zis.getNextEntry()) != null) {
                this.unzipFileEntry(zis, unzipPath, entry);
            }

        } catch (Exception e) {
            throw e;
        } finally {
            this.closeIn(zis);
        }


    }

    private void unzipFileEntry(ZipInputStream zis, String path, ZipEntry entry) throws Exception {
        String fullName;
        File f;
        FileOutputStream fos = null;

        int n;
        try {

            fullName = path + "/" + entry.getName();
            this.createUnzipPath(fullName);
            f = new File(fullName);
            fos = new FileOutputStream(f);

            while ((n = zis.read()) != -1) {
                fos.write(n);
                fos.flush();
            }
            logger.error("完成压缩文件："+fullName+"解压");
            
        } catch (Exception e) {
            throw e;
        } finally {
            this.closeOut(fos);
        }
    }

    private void createUnzipPath(String fullName) {
        int index = fullName.lastIndexOf("/");
        String pathName = fullName.substring(0, index);
        File path = new File(pathName);
        if (!path.exists()) {
            path.mkdirs();
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
