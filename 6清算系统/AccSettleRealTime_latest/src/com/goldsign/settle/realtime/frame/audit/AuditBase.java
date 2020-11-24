/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.audit;

import com.goldsign.settle.realtime.app.export.ExportCommon;

import com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;
import com.goldsign.settle.realtime.frame.dao.FileLccDao;
import com.goldsign.settle.realtime.frame.export.ExportBase;
import com.goldsign.settle.realtime.frame.io.FileGenericBase;
import com.goldsign.settle.realtime.frame.util.CrcUtil;
import com.goldsign.settle.realtime.frame.util.DateHelper;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Hashtable;
import java.util.Set;

import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public abstract class AuditBase extends FileGenericBase {

    private static Logger logger = Logger.getLogger(AuditBase.class.getName());
    protected final static char[] CRLF = {0x0d, 0x0a};
    protected final static String CRC_ID = "CRC:";
    protected final static int LEN_CRC = 8;
    public final static String DATA_TYPE_FTP = "FTP";
    public final static String DATA_TYPE_ERROR = "ER";
    public final static String DATA_TYPE_SDF = "SDF";
    public final static String DATA_TYPE_SDF_MOBILE = "MobileSDF";
    public final static String DATA_TYPE_TRX_MOBILE = "MobileTRX";
    public final static String DATA_TYPE_SDF_NETPAID = "NetPaidSDF";
    public final static String DATA_TYPE_SDF_QRCODE = "QrCodeSDF";
    public final static String DATA_TYPE_SDF_QRCODE_MTR = "QrCodeMtrSDF";

    /*
    LC对账
     */
    public final static String[] RECORD_VERSIONS = {"11", "12", "13","14"};//版本号
    public final static int[] NUM_FIELD_LCS = {35, 36, 37,39};//记录字段数量

    protected int getCutNum(String recordVer) {
        String ver;
        int fieldNum;
        int fieldNumCur;
        int cutNum =0;
        int len = NUM_FIELD_LCS.length;
        fieldNumCur=NUM_FIELD_LCS[len-1];
        
        for (int i=0;i<RECORD_VERSIONS.length;i++) {
            ver=RECORD_VERSIONS[i];
            if(ver.equals(recordVer)){
                fieldNum=NUM_FIELD_LCS[i];
                cutNum=fieldNumCur-fieldNum;
                break;
            }
        }
        return cutNum;
    }

    protected int[] getArrayPart(int[] datas, int cutNum) {
        int len = datas.length;
        int lenPart = len - cutNum;
        int[] datasPart = new int[lenPart];
        for (int i = 0; i < lenPart; i++) {
            datasPart[i] = datas[i];
        }
        return datasPart;
    }

    protected Vector<Object[]> getVectArrayObjectPart(Vector<Object[]> datasV, int cutNum) {
        Vector<Object[]> datasVPart = new Vector();
        Object[] obsPart;
        for (Object[] obs : datasV) {
            obsPart = this.getArrayObjectPart(obs, cutNum);
            datasVPart.add(obsPart);
        }
        return datasVPart;

    }

    protected Object[] getArrayObjectPart(Object[] datas, int cutNum) {
        int len = datas.length;
        int lenPart = len - cutNum;
        Object[] datasPart = new Object[lenPart];
        for (int i = 0; i < lenPart; i++) {
            datasPart[i] = datas[i];
        }
        return datasPart;
    }

    protected Hashtable<String, Vector> getHashtablePart(Hashtable<String, Vector> datas, int cutNum) {

        Hashtable<String, Vector> datasPart = new Hashtable();
        Vector<Object[]> records;
        Vector<Object[]> recordsPart;
        for (String key : datas.keySet()) {
            records = datas.get(key);
            recordsPart = this.getVectArrayObjectPart(records, cutNum);
            datasPart.put(key, recordsPart);

        }
        return datasPart;
    }

    public abstract Vector<String> genAuditFiles(String balanceWaterNo) throws Exception;

    protected Vector<String> writeDataToFiles(Hashtable<String, Vector> datas, String balanceWaterNo, String dataTypeName, int[] format) throws Exception {
        //String line;
        Vector v;
        String fileName = "";
        Vector<String> fileNames = new Vector();
        for (String line : datas.keySet()) {
            v = datas.get(line);
            v = this.formatRecord(v, format);
            fileName = this.getFileName(dataTypeName, line, balanceWaterNo);
            this.writeDataToFile(v, fileName);
            fileNames.add(fileName);

        }
        return fileNames;

    }

    protected Vector<String> writeDataToFilesMobile(Hashtable<String, Vector> datas, String balanceWaterNo, String filePath,
            int[] format, int[] addDirection, String[] addChars) throws Exception {
        //String line;
        Vector v;
        String fileName = "";
        Vector<String> fileNames = new Vector();
        Vector<String> datasFormatted;
        ExportCommon eb = new ExportCommon();
        for (String line : datas.keySet()) {
            v = datas.get(line);
            datasFormatted = eb.formatRecordForMobile(v, format, addDirection, addChars);

            fileName = this.getFileNameMobile(balanceWaterNo);
            //  this.writeDataToFile(datasFormatted, filePath, fileName);
            //写文件及归档
            this.writeDataToFileAndArchive(datasFormatted, fileName, filePath, FrameCodeConstant.MOBILE_PATH_EXPORT_TRX_HIS, balanceWaterNo);
            fileNames.add(fileName);

        }
        return fileNames;

    }

    protected Vector<String> writeDataToFilesMobile80(Hashtable<String, Vector> datas, String balanceWaterNo, String filePath,
            int[] format, int[] addDirection, String[] addChars, String fileSeq) throws Exception {
        //String line;
        Vector v;
        String fileName = "";
        Vector<String> fileNames = new Vector();
        Vector<String> datasFormatted;
        ExportCommon eb = new ExportCommon();
        for (String line : datas.keySet()) {
            v = datas.get(line);
            datasFormatted = eb.formatRecordForMobile(v, format, addDirection, addChars);

            fileName = this.getFileNameMobile80(balanceWaterNo, fileSeq);
            //  this.writeDataToFile(datasFormatted, filePath, fileName);
            //写文件及归档
            this.writeDataToFileAndArchive(datasFormatted, fileName, filePath, FrameCodeConstant.MOBILE_PATH_EXPORT_TRX_HIS, balanceWaterNo);
            fileNames.add(fileName);

        }
        return fileNames;

    }

    protected Vector<String> writeDataToFilesMobile81(Hashtable<String, Vector> datas, String balanceWaterNo, String filePath,
            int[] format, int[] addDirection, String[] addChars, String fileSeq) throws Exception {
        //String line;
        Vector v;
        String fileName = "";
        Vector<String> fileNames = new Vector();
        Vector<String> datasFormatted;
        ExportCommon eb = new ExportCommon();
        for (String line : datas.keySet()) {
            v = datas.get(line);
            datasFormatted = eb.formatRecordForMobile(v, format, addDirection, addChars);

            fileName = this.getFileNameMobile81(balanceWaterNo, fileSeq);
            //  this.writeDataToFile(datasFormatted, filePath, fileName);
            //写文件及归档
            this.writeDataToFileAndArchive(datasFormatted, fileName, filePath, FrameCodeConstant.MOBILE_PATH_EXPORT_TRX_HIS, balanceWaterNo);
            fileNames.add(fileName);

        }
        return fileNames;

    }

    protected Vector<String> writeDataToFilesForGeneric(Hashtable<String, Vector> datas, String balanceWaterNo, String dataTypeName,
            int[] dataTypeHead, int[] dataLenHead, int[] dataType, int[] dataLen, int[] dataTypeTail, int[] dataLenTail, String path) throws Exception {
        Vector v;
        String fileName = "";
        char[] buffer;
        Object[] datasHead;
        Vector<String> fileNames = new Vector();
        for (String line : datas.keySet()) {
            v = datas.get(line);
            datasHead = this.getFileHead(line, v.size());//获取文件头相关数据
            //获取文件的所以数据包括文件头、文件数据、文件尾
            buffer = this.getBuffer(dataTypeHead, dataLenHead, datasHead, dataType, dataLen, dataTypeTail, dataLenTail, v);
            //获取文件名称
            fileName = this.getFileNameWithoutStation(dataTypeName, line, balanceWaterNo);
            //文件数据写入文件
            this.writeDataToFileForGeneric(buffer, path, fileName);
            fileNames.add(fileName);

        }
        return fileNames;

    }

    public String getLineFromLineDate(String lineDate) {
        String[] comps = lineDate.split("#");
        return comps[0];
    }

    public String getDateFromLineDate(String lineDate) {
        String[] comps = lineDate.split("#");
        return comps[1];
    }

    protected Vector<String> writeDataToFilesForGeneric_3(Hashtable<String, Vector> datas, String balanceWaterNo, String dataTypeName,
            int[] dataTypeHead, int[] dataLenHead, int[] dataType, int[] dataLen, int[] dataTypeTail, int[] dataLenTail, String path) throws Exception {
        Vector v;
        String fileName = "";
        char[] buffer;
        Object[] datasHead;
        Vector<String> fileNames = new Vector();
        String line = "";
        String accountDate = "";
        //modify by hejj 20160623 支持一清算日多个对账文件
        for (String lineDate : datas.keySet()) {
            v = datas.get(lineDate);
            line = this.getLineFromLineDate(lineDate);
            accountDate = this.getDateFromLineDate(lineDate);

            datasHead = this.getFileHead(line, v.size());//获取文件头相关数据
            //获取文件的所以数据包括文件头、文件数据、文件尾
            buffer = this.getBuffer_3(dataTypeHead, dataLenHead, datasHead, dataType, dataLen, dataTypeTail, dataLenTail, v);
            //获取文件名称
            fileName = this.getFileNameWithoutStationFromAccountDate(dataTypeName, line, accountDate);
            //文件数据写入文件
            this.writeDataToFileForGeneric(buffer, path, fileName);
            fileNames.add(fileName);

        }
        return fileNames;

    }

    protected Vector<String> writeDataToFilesForGenericMobile_3(Hashtable<String, Vector> datas, String balanceWaterNo, String dataTypeName,
            int[] dataTypeHead, int[] dataLenHead, int[] dataType, int[] dataLen, int[] dataTypeTail, int[] dataLenTail, String path) throws Exception {
        Vector v;
        String fileName = "";
        char[] buffer;
        Object[] datasHead;
        Vector<String> fileNames = new Vector();
        String line;
        String accountDate;
        for (String lineDate : datas.keySet()) {
            line = this.getLineFromLineDate(lineDate);
            accountDate = this.getDateFromLineDate(lineDate);
            v = datas.get(lineDate);
            datasHead = this.getFileHead(line, v.size());//获取文件头相关数据
            //获取文件的所以数据包括文件头、文件数据、文件尾
            buffer = this.getBuffer_3(dataTypeHead, dataLenHead, datasHead, dataType, dataLen, dataTypeTail, dataLenTail, v);
            //获取文件名称
            fileName = this.getFileNameWithoutStationFromAccountDate(dataTypeName, line, accountDate);
            //文件数据写入文件
            this.writeDataToFileForGeneric(buffer, path, fileName);
            fileNames.add(fileName);

        }
        return fileNames;

    }

    protected Vector<String> writeDataToFilesForGenericNetPaid_3(Hashtable<String, Vector> datas, String balanceWaterNo, String dataTypeName,
            int[] dataTypeHead, int[] dataLenHead, int[] dataType, int[] dataLen, int[] dataTypeTail, int[] dataLenTail, String path, String fixLine) throws Exception {
        Vector v;
        String fileName = "";
        char[] buffer;
        Object[] datasHead;
        Vector<String> fileNames = new Vector();
        String line;
        String accountDate;
        for (String lineDate : datas.keySet()) {
            //line =this.getLineFromLineDate(lineDate);
            accountDate = this.getDateFromLineDate(lineDate);
            v = datas.get(lineDate);
            datasHead = this.getFileHead(fixLine, v.size());//获取文件头相关数据
            //获取文件的所以数据包括文件头、文件数据、文件尾
            buffer = this.getBuffer_3(dataTypeHead, dataLenHead, datasHead, dataType, dataLen, dataTypeTail, dataLenTail, v);
            //获取文件名称
            fileName = this.getFileNameWithoutStationFromAccountDate(dataTypeName, fixLine, accountDate);
            //文件数据写入文件
            this.writeDataToFileForGeneric(buffer, path, fileName);
            fileNames.add(fileName);

        }
        return fileNames;

    }

    private Object[] getFileHead(String line, int rowCount) {
        DateHelper util = new DateHelper();
        String openDate = util.dateToString(new Date());
        Object[] heads = {line, openDate, openDate, rowCount};
        return heads;

    }

    private int getLen(int[] dataLen) {
        int len = 0;
        for (int i : dataLen) {
            len += i;
        }
        return len;
    }

    private int getLenIncludeLineDelimit(int[] dataLen) {
        int len = this.getLen(dataLen);
        return len + 2;

    }

    private int getLenIncludeLineDelimit_3(int[] dataLen) {
        int len = this.getLen(dataLen);
        return len + 6;

    }

    private char[] getBuffer(int[] dataTypeHead, int[] dataLenHead, Object[] datasHead,
            int[] dataType, int[] dataLen,
            int[] dataTypeTail, int[] dataLenTail,
            Vector datas) throws Exception {
        int lenHead = this.getLenIncludeLineDelimit(dataLenHead);//文件头长度
        int lenData = this.getLenIncludeLineDelimit(dataLen) * datas.size();//数据长度
        // int lenTail = this.getLen(dataLenTail);//文件尾长度
        int lenTail = this.getLenIncludeLineDelimit(dataLenTail);//文件尾长度
        int lenTotal = lenHead + lenData + lenTail;//文件总长度
        char[] bufferForCrc = new char[lenHead + lenData];//计算CRC的数据长度包括文件头、数据
        char[] buffer = new char[lenTotal];//总数据缓存包括文件头、数据、文件尾
        int offset = 0;

        char[] bufferHead = this.getLine(datasHead, dataLenHead, dataTypeHead);//文件头
        this.addBuffer(bufferForCrc, bufferHead, offset);//文件头数据添加到计算CRC缓存
        offset += lenHead;
        char[] bufferData = this.getBufferForData(datas, dataType, dataLen);//文件数据
        this.addBuffer(bufferForCrc, bufferData, offset);//文件数据添加到计算CRC缓存
        offset += lenData;

        char[] bufferTail = this.getBufferCharForLineCrc(bufferForCrc);//文件尾数据缓存
        offset = 0;
        this.addBuffer(buffer, bufferForCrc, offset);//文件头数据及文件数据添加到总缓存
        offset = lenHead + lenData;
        this.addBuffer(buffer, bufferTail, offset);//文件尾数据添加到总缓存

        return buffer;
    }

    private char[] getBuffer_3(int[] dataTypeHead, int[] dataLenHead, Object[] datasHead,
            int[] dataType, int[] dataLen,
            int[] dataTypeTail, int[] dataLenTail,
            Vector datas) throws Exception {
        int lenHead = this.getLenIncludeLineDelimit_3(dataLenHead);//文件头长度
        int lenData = this.getLenIncludeLineDelimit_3(dataLen) * datas.size();//数据长度
        // int lenTail = this.getLen(dataLenTail);//文件尾长度
        int lenTail = this.getLenIncludeLineDelimit_3(dataLenTail);//文件尾长度
        int lenTotal = lenHead + lenData + lenTail;//文件总长度
        char[] bufferForCrc = new char[lenHead + lenData];//计算CRC的数据长度包括文件头、数据
        char[] buffer = new char[lenTotal];//总数据缓存包括文件头、数据、文件尾
        int offset = 0;

        char[] bufferHead = this.getLine_3(datasHead, dataLenHead, dataTypeHead);//文件头
        this.addBuffer(bufferForCrc, bufferHead, offset);//文件头数据添加到计算CRC缓存
        offset += lenHead;
        char[] bufferData = this.getBufferForData_3(datas, dataType, dataLen);//文件数据
        this.addBuffer(bufferForCrc, bufferData, offset);//文件数据添加到计算CRC缓存
        offset += lenData;

        char[] bufferTail = this.getBufferCharForLineCrc_3(bufferForCrc);//文件尾数据缓存
        offset = 0;
        this.addBuffer(buffer, bufferForCrc, offset);//文件头数据及文件数据添加到总缓存
        offset = lenHead + lenData;
        this.addBuffer(buffer, bufferTail, offset);//文件尾数据添加到总缓存

        return buffer;
    }

    private void addBuffer(char[] buffer, char[] bufferPart, int offset) {
        for (char c : bufferPart) {
            buffer[offset] = c;
            offset++;
        }
    }

    private char[] getBufferForData(Vector<Object[]> datas, int[] dataType, int[] dataLen) throws Exception {
        int lenData = this.getLenIncludeLineDelimit(dataLen) * datas.size();
        char[] buffer = new char[lenData];
        int offset = 0;
        char[] bufferLine;
        for (Object[] line : datas) {
            bufferLine = this.getLine(line, dataLen, dataType);
            this.addBuffer(buffer, bufferLine, offset);
            offset += bufferLine.length;

        }
        return buffer;

    }

    private char[] getBufferForData_3(Vector<Object[]> datas, int[] dataType, int[] dataLen) throws Exception {
        int lenData = this.getLenIncludeLineDelimit_3(dataLen) * datas.size();
        char[] buffer = new char[lenData];
        int offset = 0;
        char[] bufferLine;
        for (Object[] line : datas) {
            bufferLine = this.getLine_3(line, dataLen, dataType);
            this.addBuffer(buffer, bufferLine, offset);
            offset += bufferLine.length;

        }
        return buffer;

    }

    /*
     protected String writeDataToFilesForGeneric(Hashtable<String, Vector> datas, String balanceWaterNo, String dataTypeName,int[] format) throws Exception {
     //String line;
     Vector v;
     String fileName="";
     for (String line : datas.keySet()) {
     v = datas.get(line);
     v=this.formatRecord(v, format);
     fileName = this.getFileName(dataTypeName, line, balanceWaterNo);
     this.writeDataToFile(v, fileName);

     }
     return fileName;


     }
     */
    protected Vector<String> formatRecord(Vector<String[]> recV, int[] format) {
        Vector<String> result = new Vector();
        int len = format.length;
        for (String[] fields : recV) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < len; i++) {
                sb.append(formatField(fields[i], format[i]));
            }
            result.add(sb.toString());
        }
        return result;
    }

    protected String formatField(String field, int length) {
        String space = " ";
        int i = length - field.length();
        if (i <= 0) {
            field = field.substring(0, length);
        } else {
            StringBuffer sb = new StringBuffer();
            sb.append(field);
            for (int j = 0; j < i; j++) {
                sb.append(space);
            }
            field = sb.toString();
        }
        return field;

    }

    private String getFileName(String dataTypeName, String line, String balanceWaterNo) {
        return dataTypeName + line + "00" + "." + balanceWaterNo.substring(0, 8);
    }

    private String getFileNameMobile(String balanceWaterNo) {
        return "MTX" + "9900" + "." + balanceWaterNo.substring(0, 8);
    }

    private String getFileNameMobile80(String balanceWaterNo, String seq) {
        return "MTX" + "9901" + "." + balanceWaterNo.substring(0, 8) + "." + seq;
    }

    private String getFileNameMobile81(String balanceWaterNo, String seq) {
        return "MTX" + "9909" + "." + balanceWaterNo.substring(0, 8) + "." + seq;
    }

    private String getFileNameWithoutStation(String dataTypeName, String line, String balanceWaterNo) {
        return dataTypeName + line + "." + balanceWaterNo.substring(0, 8);
    }

    private String getFileNameWithoutStationFromAccountDate(String dataTypeName, String line, String accountDate) {
        return dataTypeName + line + "." + accountDate;
    }

    private String getFileNameWithoutStationMobile(String dataTypeName, String line, String balanceWaterNo) {
        return dataTypeName.substring(6, 3) + line + "." + balanceWaterNo.substring(0, 8);
    }

    protected boolean writeDataToFile(Vector<String> data, String fileName) throws Exception {
        boolean result = false;
        FileWriter out = null;
        File file = null;

        String crc;
        try {
            file = new File(FrameCodeConstant.PATH_FILE_AUDIT, fileName);
            out = new FileWriter(file);
            StringBuffer sb = new StringBuffer();
            for (String line : data) {
                out.write(line, 0, line.length());
                sb.append(line);
                out.write(CRLF);
                sb.append(CRLF);
                out.flush();
            }

            if (data.size() > 0) {
                crc = CrcUtil.getCRC32Value(sb, LEN_CRC);
                out.write(CRC_ID + crc);
                out.flush();
            } else if (data.size() == 0) {
                out.write(CRC_ID + "00000000");
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

    protected boolean writeDataToFileAndArchive(Vector<String> data, String fileName, String pathWork, String pathArchive, String balanceWaterNo) throws Exception {
        boolean result = false;
        FileWriter outWork = null;
        FileWriter outArch = null;
        File fileWork = null;
        File fileArch = null;
        String pathArchBalance = pathArchive + "/" + balanceWaterNo;

        String crc;
        try {
            File path = new File(pathArchBalance);
            if (!path.exists()) {
                path.mkdirs();
            }

            fileWork = new File(pathWork, fileName);
            outWork = new FileWriter(fileWork);

            fileArch = new File(pathArchBalance, fileName);
            outArch = new FileWriter(fileArch);

            StringBuffer sb = new StringBuffer();
            for (String line : data) {
                this.writeTwo(outWork, outArch, line, 0, line.length());
                sb.append(line);
                this.writeTwo(outWork, outArch, CRLF);
                sb.append(CRLF);
                this.flushTwo(outWork, outArch);
            }

            if (data.size() > 0) {
                crc = CrcUtil.getCRC32Value(sb, LEN_CRC);
                this.writeTwo(outWork, outArch, CRC_ID + crc);
                this.flushTwo(outWork, outArch);
            } else if (data.size() == 0) {
                this.writeTwo(outWork, outArch, CRC_ID + "00000000");
                this.flushTwo(outWork, outArch);
            }

            result = true;
        } catch (Exception e) {
            throw e;
        } finally {
            this.closeTwo(outWork, outArch);

        }
        return result;
    }

    private void flushTwo(FileWriter out, FileWriter out1) throws IOException {
        out.flush();
        out1.flush();

    }

    private void writeTwo(FileWriter out, FileWriter out1, String str) throws IOException {
        out.write(str);
        out1.write(str);

    }

    private void writeTwo(FileWriter out, FileWriter out1, String str, int start, int end) throws IOException {
        out.write(str, start, end);
        out1.write(str, start, end);

    }

    private void writeTwo(FileWriter out, FileWriter out1, char[] cs) throws IOException {
        out.write(cs);
        out1.write(cs);

    }

    protected boolean writeDataToFile(Vector<String> data, String path, String fileName) throws Exception {
        boolean result = false;
        FileWriter out = null;
        File file = null;

        String crc;
        try {
            file = new File(path, fileName);
            out = new FileWriter(file);
            StringBuffer sb = new StringBuffer();
            for (String line : data) {
                out.write(line, 0, line.length());
                sb.append(line);
                out.write(CRLF);
                sb.append(CRLF);
                out.flush();
            }

            if (data.size() > 0) {
                crc = CrcUtil.getCRC32Value(sb, LEN_CRC);
                out.write(CRC_ID + crc);
                out.flush();
            } else if (data.size() == 0) {
                out.write(CRC_ID + "00000000");
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

    public void writeDataToFileForGeneric(char[] bs, String path, String fileName) {
        String fileNameFull = path + "/" + fileName;
        File f = new File(fileNameFull);
        FileOutputStream os = null;
        DataOutputStream dos = null;
        try {
            os = new FileOutputStream(f);
            dos = new DataOutputStream(os);
            for (char c : bs) {
                dos.write((int) c);
            }
            dos.flush();
        } catch (Exception ex) {
            logger.error(ex);
        } finally {
            this.close(dos);
        }
    }

    protected void close(OutputStream os) {
        try {
            if (os != null) {
                os.close();
            }
        } catch (IOException ex) {
            logger.error(ex);
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

    private void closeTwo(FileWriter out, FileWriter out1) {

        try {
            if (out != null) {
                out.close();
            }
            if (out1 != null) {
                out.close();
            }
            //encode(paraFile);
        } catch (Exception e) {
        }

    }
}
