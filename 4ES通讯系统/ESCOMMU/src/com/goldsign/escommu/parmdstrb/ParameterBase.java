package com.goldsign.escommu.parmdstrb;

import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.dbutil.DbHelper;
import com.goldsign.escommu.dbutil.DbcpHelper;
import com.goldsign.escommu.exception.ParameterException;
import com.goldsign.escommu.util.CharUtil;
import com.goldsign.escommu.util.DateHelper;
import com.goldsign.escommu.vo.ParaGenDtl;
import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;
import org.apache.log4j.Logger;

public abstract class ParameterBase {

    protected final static char[] CRLF = {0x0d, 0x0a};
    //private final static String CLASSPREFIX = "com.goldsign.escommu.parmdstrb.Parameter";
    protected final static String PRM = "PRM";
    protected final static String SVER = "CRC:";
    protected DbHelper dbHelper;
    protected ParaGenDtl paraGenDtl;
    protected String paraFileName;
    protected int dbRecordNum;
    private static Logger logger = Logger.getLogger(ParameterBase.class.getName());

    private void setParaGenDtl(String deviceId) throws ParameterException {

        paraGenDtl = new ParaGenDtl();
        paraGenDtl.setDeviceId(deviceId);
        paraGenDtl.setParmTypeId(getParmType());
        String verNum = DateHelper.datetimeToYYYYMMDD(new Date()) + "01";
        paraGenDtl.setVerNum(verNum);
        paraGenDtl.setVerType("0");
    }

    protected String getParmType() throws ParameterException {
        throw new ParameterException("请重载参数类型");
    }

    public String getParFileName() {
        return this.paraFileName;
    }

    public boolean genParameterFile(String deviceId) throws ParameterException {

        boolean result = true;

        try {
            this.setParaGenDtl(deviceId);
            init(AppConstant.DATA_DBCPHELPER, paraGenDtl);
            result = formParaFile();
            release();

        } catch (ParameterException e) {

            DateHelper.screenPrint("生成参数文件错误  " + e);
            logger.error("生成参数文件错误" + e);
            return false;
        } catch (Exception e) {

            DateHelper.screenPrint("生成参数文件错误  " + e);
            logger.error("生成参数文件错误  " + e);
            return false;
        }
        return result;
    }

    public void init(DbcpHelper dbcpHelper, ParaGenDtl dtl) throws Exception {
        String thisClassName = this.getClass().getName();
        thisClassName = thisClassName.substring(thisClassName.lastIndexOf(".") + 1, thisClassName.length());
        this.dbHelper = new DbHelper(thisClassName, dbcpHelper.getConnection());
        this.paraGenDtl = dtl;
        this.paraFileName = PRM + paraGenDtl.getDeviceId() + "." + paraGenDtl.getParmTypeId() + "." + paraGenDtl.getVerNum().substring(0, 8) + "." + paraGenDtl.getVerNum().substring(8, 10);
        //   DateHelper.screenPrint("Parm"+paraGenDtl.getParmTypeId()+"  init ended!");
    }

    public void setDbRecordNum(Vector v) {
        if (v == null) {
            this.dbRecordNum = 0;
        }
        this.dbRecordNum = v.size();

    }

    private int getVSize(Vector v) {
        if (v == null) {
            return 0;
        }
        return v.size();
    }

    public void setDbRecordNum(Vector v1, Vector v2, Vector v3, Vector v4, Vector v5) {

        this.dbRecordNum = this.getVSize(v1)
                + this.getVSize(v2)
                + this.getVSize(v3)
                + this.getVSize(v4)
                + this.getVSize(v5);


    }

    public int getDbRecordNum() {
        return this.dbRecordNum;
    }

    public abstract boolean formParaFile();

    public void release() {
        try {
            dbHelper.closeConnection();
        } catch (Exception e) {
        }
    }

    protected Vector formatRecord(String type, Vector recV, int[] format) {
        Vector result = new Vector();
        Iterator it = recV.iterator();
        int len = format.length;
        while (it.hasNext()) {
            String[] fields = (String[]) (it.next());
            StringBuffer sb = new StringBuffer();
            sb.append(type + ":");
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
        //              System.out.println(CharUtil.IsoToGbk(field));
        return field;
    }

    protected boolean writeDataToFile(Vector data) {
        boolean result = false;
        FileWriter out = null;
        File paraFile = null;
        int len = -1;
        int recordLen = -1;
        try {
            String path = getPath();
            paraFile = new File(path, paraFileName);
            out = new FileWriter(paraFile);
            StringBuffer sb = new StringBuffer();
            Iterator it = data.iterator();
            while (it.hasNext()) {
                String line = (String) (it.next());

                if (line != null) {
                    len = line.length();
                    if (recordLen == -1) {
                        recordLen = len;
                    }
                    if (len != recordLen) {
                        DateHelper.screenPrintForEx("记录长度不一样:" + line);
                    }
                }

                out.write(line, 0, line.length());
                sb.append(line);
                out.write(CRLF);
                sb.append(CRLF);
                out.flush();
            }
            if (data.size() > 0) {
                String s = sb.toString();
                byte[] b = null;//new byte[2800000];
                b = s.getBytes();
                long crc32 = CharUtil.getCRC32Value(b);
                String crc = Long.toHexString(crc32);
                for (int i = crc.length(); i < 8; i++) {
                    crc = "0" + crc;
                }
                out.write(SVER + crc);
                out.flush();
            } else if (data.size() == 0) {
                out.write(SVER + "00000000");
                out.flush();
            }

            result = true;
        } catch (Exception e) {
            result = false;
        } finally {
            if (out != null) {
                try {
                    out.close();
                    //encode(paraFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    private String getPath() {

        String path = AppConstant.ParmDstrbPath;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getPath();
    }
}
