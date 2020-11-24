/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.handler;

import com.goldsign.settle.realtime.app.parser.FileRecordParserCrc;
import com.goldsign.settle.realtime.app.parser.FileRecordParserHead;
import com.goldsign.settle.realtime.app.parser.FileRecordParserHeadOther;
import com.goldsign.settle.realtime.app.parser.FileRecordParserHeadOtherLine;
import com.goldsign.settle.realtime.app.vo.FileLogVo;
import com.goldsign.settle.realtime.app.vo.FileRecord00;
import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;
import com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;
import com.goldsign.settle.realtime.frame.constant.FrameFileHandledConstant;
import com.goldsign.settle.realtime.frame.dao.BufferToQueueBaseDao;
import com.goldsign.settle.realtime.frame.dao.FileErrorDao;
import com.goldsign.settle.realtime.frame.dao.FileLogDao;
import com.goldsign.settle.realtime.frame.dao.FileRecordSeqDao;
import com.goldsign.settle.realtime.frame.dao.TradeBaseDao;
import com.goldsign.settle.realtime.frame.exception.FileWriteException;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFieldException;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.exception.RecordParseHeadForFileException;
import com.goldsign.settle.realtime.frame.exception.TransferException;
import com.goldsign.settle.realtime.frame.io.FileWriterBase;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.parser.FileRecordParserBase;
import com.goldsign.settle.realtime.frame.util.CrcUtil;
import com.goldsign.settle.realtime.frame.util.FileUtil;
import com.goldsign.settle.realtime.frame.util.MessageUtil;
import com.goldsign.settle.realtime.frame.util.TradeUtil;
import com.goldsign.settle.realtime.frame.vo.BcpAttribute;
import com.goldsign.settle.realtime.frame.vo.FileData;
import com.goldsign.settle.realtime.frame.vo.FileErrorVo;
import com.goldsign.settle.realtime.frame.vo.FileNameSection;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordCrc;
import com.goldsign.settle.realtime.frame.vo.FileRecordHead;
import com.goldsign.settle.realtime.frame.vo.ResultFromProc;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Vector;
import org.apache.log4j.Logger;
import java.lang.Byte;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author hejj
 */
public abstract class HandlerBase {

    private static Logger logger = Logger.getLogger(HandlerBase.class.getName());
    public static String FLAG_CRC = "CRC:";
    public static String CLASS_PARSER_PRIX = "com.goldsign.settle.realtime.app.parser.FileRecordParser";
    public static String CLASS_WRITER_PRIX = "com.goldsign.settle.realtime.app.io.FileWriter";
    public static String CLASS_PARSER_SHORT_NAME_HEAD = "Head";
    public static String CLASS_PARSER_SHORT_NAME_CRC = "Crc";
    // public static String[] TRADE_PRFIX_NAME ={"AUD"};//
    //public static String[] TRADE_PRFIX ={"2"};//由于规范定义审计数据的记录类型仅一位，为了规范定义，前面加2
    protected static String[] TRAD_SUFIX = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
    //有子记录的数据类型，包括BOM收益数据、寄存器数据、AGM审计/TVM审计、
 //TVM硬币箱数据、TVM纸币箱数据、TVM硬币清空、TVM纸币找零回收、ITM自动结存、ITM结存
    protected static String[] TRAD_TYPES_WITH_SUB = {"00", "81","82","83", "31", "32","01","02","04","11","18","19"};

    protected static String CLASS_PREFIX = "com.goldsign.settle.realtime.app.dao.BufferToQueue";
    public static String CLASS_PARSER_SHORT_NAME_MOBILE = "Mobile";//手机支付类区分标识
    public static String CLASS_PARSER_SHORT_NAME_NETPAID = "NetPaid";//互联网支付类区分标识
     public static String CLASS_PARSER_SHORT_NAME_QRCODE = "QrCode";//二维码平台类区分标识
    
    public static String[] SPECIAL_SIGNS={",","，"};

    public abstract void handleMessage(MessageBase msg);

    private char byteToChar(byte b) {
        return (char) b;
    }

    private char byteToChar(char b) {
        return b;
    }

    private int byteToInt(char b) {
        return (int) b;

    }

    private int byteToInt(byte b) {
        int i = 0;
        if (b < 0) {
            i = 256 + b;
        } else {
            i = b;
        }
        return i;
    }

    private String byte1ToBcd2(byte b) {
        int i = 0;
        if (b < 0) {
            i = 256 + b;
        } else {
            i = b;
        }
        return (new Integer(i / 16)).toString() + (new Integer(i % 16)).toString();
    }

    private String byte1ToBcd2(char b) {
        String s = Integer.toHexString((int) b);
        if (s.length() == 1) {
            s = "0" + s;
        }
        return s;

    }

    public int delete_getInt(byte[] data, int offset) {
        return byteToInt(data[offset]);
    }

    public int getInt(char[] data, int offset) {
        return byteToInt(data[offset]);
    }

    //when transform one short(two bytes) for example 0x12(low),0x34(high),run this method to get 13330
    public int delete_getShort(byte[] data, int offset) {
        int low = byteToInt(data[offset]);
        int high = byteToInt(data[offset + 1]);
        return high * 16 * 16 + low;
    }

    public int getShort(char[] data, int offset) {
        int low = byteToChar(data[offset]);
        int high = byteToChar(data[offset + 1]);
        return high * 16 * 16 + low;
    }

    //when transform one long(two shorts) for example 0x12,0x34,0x56,0x78
    public int delete_getLong(byte[] data, int offset) {
        int low = delete_getShort(data, offset);
        int high = delete_getShort(data, offset + 2);
        return high * 16 * 16 * 16 * 16 + low;
    }

    public int getLong(char[] data, int offset) {
        int low = getShort(data, offset);
        int high = getShort(data, offset + 2);
        return high * 16 * 16 * 16 * 16 + low;
    }

    public String delete_getBcdString(byte[] data, int offset, int length) throws Exception {
        StringBuffer sb = new StringBuffer();
        try {
            for (int i = 0; i < length; i++) {
                sb.append(byte1ToBcd2(data[offset + i]));
            }
        } catch (Exception e) {
            throw new Exception(" " + e);
        }
        return sb.toString();
    }

    public String getBcdString(char[] data, int offset, int length) throws Exception {
        StringBuffer sb = new StringBuffer();
        try {
            for (int i = 0; i < length; i++) {
                sb.append(byte1ToBcd2(data[offset + i]));
            }
        } catch (Exception e) {
            throw new Exception(" " + e);
        }
        return sb.toString();
    }

    public String delete_getCharString(byte[] data, int offset, int length) throws Exception {
        StringBuffer sb = new StringBuffer();
        try {
            for (int i = 0; i < length; i++) {
                sb.append(byteToChar(data[offset + i]));
            }
        } catch (Exception e) {
            throw new Exception(" " + e);
        }
        return sb.toString();
    }

    public String getCharString(char[] data, int offset, int length) throws Exception {
        StringBuffer sb = new StringBuffer();
        try {
            for (int i = 0; i < length; i++) {
                sb.append(data[offset + i]);
            }
        } catch (Exception e) {
            throw new Exception(" " + e);
        }
        return sb.toString();
    }

    public String getCharStringForSpecial(char[] data, int offset, int length) throws Exception {
        StringBuffer sb = new StringBuffer();
        try {
            for (int i = 0; i < length; i++) {
                if (data[offset + i] == 0xBF) {
                    sb.append(" ");
                    logger.error("数据存在BF值字符，使用空格代替");
                    continue;
                }
                sb.append(data[offset + i]);
            }
        } catch (Exception e) {
            throw new Exception(" " + e);
        }
        return sb.toString();
    }

    public byte[] getLineByteFromFileTest(String line) throws Exception {
        if (line == null || line.length() == 0) {
            return null;
        }
        int len = line.length();
        if (len % 2 != 0) {
            throw new Exception("一个字节使用2个16进制数值表示");
        }
        int j = 0;
        byte[] b = new byte[len / 2];
        for (int i = 0; i < len; i = i + 2) {
            b[j] = (byte) Integer.parseInt(line.substring(i, i + 2), 16);
            j++;
        }
        return b;
    }

    public byte[] delete_getLineByteFromFile(String line) throws Exception {
        if (line == null || line.length() == 0) {
            return null;
        }
        return line.getBytes();
    }

    public char[] getLineCharFromFile(String line) throws Exception {
        if (line == null || line.length() == 0) {
            return null;
        }
        //return this.getCharArray(line);
        return line.toCharArray();//中文时，非原始数据20130811
    }

    private char[] getCharArray(String line) throws Exception {
        byte[] bs = line.getBytes("GBK");
        char[] cs = new char[bs.length];
        int b;
        for (int i = 0; i < bs.length; i++) {
            b = bs[i];
            cs[i] = (char) b;
        }
        return cs;
    }

    protected void delete_closeFile(FileInputStream fis, InputStreamReader isr, BufferedReader br) {
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

    protected void closeFile(FileInputStream fis) {
        try {
            if (fis != null) {
                fis.close();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected boolean delte_isFileCRC(String line) throws Exception {
        // byte[] b = this.getLineByteFromFileTest(line);
        byte[] b = this.delete_getLineByteFromFile(line);
        String crcFlag = this.delete_getCharString(b, 0, 4);
        if (crcFlag.startsWith(HandlerBase.FLAG_CRC)) {
            return true;
        }
        return false;
    }

    protected boolean isFileCRC(String line) throws Exception {
        // byte[] b = this.getLineByteFromFileTest(line);
        char[] b = this.getLineCharFromFile(line);
        String crcFlag = this.getCharString(b, 0, 4);
        if (crcFlag.startsWith(HandlerBase.FLAG_CRC)) {
            return true;
        }
        return false;
    }

    protected boolean isFileHead(int i) {
        if (i == 1) {
            return true;
        }
        return false;
    }

    protected void putMap(String trdType, Object ob, HashMap<String, Vector> hm) {
        if (!hm.containsKey(trdType)) {
            hm.put(trdType, new Vector());
        }
        Vector v = (Vector) hm.get(trdType);
        v.add(ob);
    }

    protected void putMapForLarge(String trdType, Object ob, HashMap<String, TreeSet<String>> hmSorted) {
        if (!hmSorted.containsKey(trdType)) {
            hmSorted.put(trdType, new TreeSet());
        }
        TreeSet ts = (TreeSet) hmSorted.get(trdType);
        String obstr = ((FileRecordBase) ob).getCheckUniqueKey();
        ts.add(obstr);
    }

    protected void putMap(String trdType, Vector obs, HashMap<String, Vector> hm) {
        if (!hm.containsKey(trdType)) {
            hm.put(trdType, new Vector());
        }
        Vector v = (Vector) hm.get(trdType);
        v.addAll(obs);
    }

    protected void writeFileError(String balanceWaterNo, String fileName, String errorCode, String remark) {
        FileErrorDao dao = new FileErrorDao();
        FileErrorVo vo = new FileErrorVo(balanceWaterNo, fileName, errorCode, remark);
        try {
            dao.insert(vo);
        } catch (Exception ex) {
            logger.error(ex);
        }
    }

    protected void writeFileErrorForField(String balanceWaterNo, String fileName, String errorCode, String remark, String line) {
        FileErrorDao dao = new FileErrorDao();
        FileErrorVo vo = new FileErrorVo(balanceWaterNo, fileName, errorCode, remark, line);
        try {
            dao.insertForField(vo);
        } catch (Exception ex) {
            logger.error(ex);
        }
    }

    protected void writeFileErrorForOctReturn(String balanceWaterNo, String fileName, String errorCode, String remark) {
        FileErrorDao dao = new FileErrorDao();
        FileErrorVo vo = new FileErrorVo(balanceWaterNo, fileName, errorCode, remark);
        try {
            dao.insertForOctReturn(vo);
        } catch (Exception ex) {
            logger.error(ex);
        }
    }

    protected void movFile(MessageBase msg, boolean isExistFileError) {
        if (isExistFileError) {
            logger.info("移动文件" + msg.getFileName() + "到目录：" + msg.getPathHisError());
            FileUtil.moveFile(msg.getFileName(), msg.getPath(), msg.getFileName(), msg.getPathHisError(), msg.getBalanceWaterNo());
        } else {
            logger.info("移动文件" + msg.getFileName() + "到目录：" + msg.getPathHis());
            FileUtil.moveFile(msg.getFileName(), msg.getPath(), msg.getFileName(), msg.getPathHis(), msg.getBalanceWaterNo());
        }
    }

    protected void writeFileNormal(String balanceWaterNo, String fileName) {
        FileLogDao dao = new FileLogDao();
        FileLogVo vo = new FileLogVo(fileName, balanceWaterNo, FileUtil.getFileType(fileName));
        try {
            dao.insert(vo);
        } catch (Exception ex) {
            logger.error(ex);
        }
    }

    protected void writeFileNormalForOctReturn(String balanceWaterNo, String fileName) {
        FileLogDao dao = new FileLogDao();
        FileLogVo vo = new FileLogVo(fileName, balanceWaterNo, FileUtil.getFileType(fileName));
        try {
            dao.insert(vo);
        } catch (Exception ex) {
            logger.error(ex);
        }
    }

    protected void writeFileOne(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        String classWriter = HandlerBase.CLASS_WRITER_PRIX + tradType;
        try {
            ((FileWriterBase) (Class.forName(classWriter).newInstance())).writeFile(pathBcp, fileName, tradType, records);
        } catch (Exception ex) {
            throw new FileWriteException(ex.getMessage());
        }
    }

    protected void writeFile(String pathBcp, String fileName, HashMap<String, Vector> hm) throws FileWriteException {
        String tradType;
        Vector records;
        for (Map.Entry<String, Vector> entry : hm.entrySet()) {
            tradType = entry.getKey();
            records = entry.getValue();
            this.writeFileOne(pathBcp, fileName, tradType, records);
        }
    }

    protected Vector<MessageBase> bcpFile(String fileName, Set<String> tradTypes, String balanceWaterNo) {
        return this.bcpFile(fileName, tradTypes, balanceWaterNo, null);

    }

    protected Vector<MessageBase> bcpFile(String fileName, Set<String> tradTypes, String balanceWaterNo, BcpAttribute attr) {
        MessageUtil mUtil = new MessageUtil();
        String fileNameBcp;
        MessageBase msg;
        Vector<MessageBase> msgs = new Vector();
        for (String tradType : tradTypes) {
            fileNameBcp = FileWriterBase.getFileNameBcp(tradType, fileName);
            msg = mUtil.putMessageToQueueForBcp(fileNameBcp, tradType, balanceWaterNo, attr);
            msgs.add(msg);
        }
        return msgs;
    }

    protected String[] getLinesForHex(StringBuffer sb) {
        String allLines = new String(sb);
        // char[] cDelimit = {0x0D, 0x0A};
        String delimit = FrameCodeConstant.DELIMIT_HEX;// "\\x0d\\x0a"; //new String(cDelimit);
        //String delimit = "\\u0d0a";
        String[] lines = allLines.split(delimit); //allLines.split("\r\n");
        return lines;
    }

    protected String[] getLinesForText(StringBuffer sb) {
        String allLines = new String(sb);
        // char[] cDelimit = {0x0D, 0x0A};
        String delimit = FrameCodeConstant.DELIMIT_TEXT;// "\\x0d\\x0a"; //new String(cDelimit);
        //String delimit = "\\u0d0a";
        String[] lines = allLines.split(delimit); //allLines.split("\r\n");
        return lines;
    }

    protected String[] getLinesForTrx(StringBuffer sb) {
        String allLines = new String(sb);
        // char[] cDelimit = {0x0D, 0x0A};
        String delimit = "\\x0d\\x0a"; //new String(cDelimit);
        //String delimit = "\\u0d0a";
        String[] lines = allLines.split(delimit); //allLines.split("\r\n");
        //处理行字段含有回车换行符情况
        lines = this.getLinesForTrxDelimer(lines);

        return lines;
    }

    private String getTradeTypeForTrx(String line) {
        if (line == null || line.length() < 2) {
            return "99";
        }
        return line.substring(0, 2);
    }

    private String[] getLinesForTrxDelimer(String[] lines) {
        Vector<String> v = new Vector();
        String line;
        String tradeType;
        int trxLen;
        String[] linesNew;
        for (int i = 0; i < lines.length; i++) {
            line = lines[i];
            if (i == 0 || i == lines.length - 1)//第一行及最后一行
            {
                v.add(line);
                continue;
            }

            tradeType = this.getTradeTypeForTrx(line);
            if (!TradeUtil.isLegalTradeType(tradeType)) {//非法交易类型
                v.add(line);
                continue;
            }

            if (TradeUtil.isLegalTradeLenForGreaterAndEqual(tradeType, line)) {//长度大于等于标准长度
                v.add(line);
                continue;
            }
            trxLen = TradeUtil.getTradeLen(tradeType);
            //长度小于标准长度
            int len = line.length();
            String lineNext;
            while (len < trxLen) {

                if (i == lines.length - 2)//最多查找到最后第2行，最后1行是CRC码行
                {
                    break;
                }
                i = i + 1;
                lineNext = lines[i];
                tradeType = this.getTradeTypeForTrx(lineNext);

                //下一行是合法长度记录，不处理
                if (TradeUtil.isLegalTradeType(tradeType) && TradeUtil.isLegalTradeLenForGreaterAndEqual(tradeType, lineNext)) {
                    i = i - 1;
                    break;
                }
                //下1行连接到上1行
                line = line + this.getDelimit() + lineNext;
                logger.error("行字段存在回车换行数值，下一行连接到上一行。");
                len = line.length();


            }
            v.add(line);

        }
        linesNew = TradeUtil.getVectorToStringArray(v);
        return linesNew;

    }

    private String getDelimit() {
        char[] cs = {0xd, 0xa};
        StringBuffer sb = new StringBuffer();
        sb.append(cs);
        return sb.toString();
    }

    protected FileRecordHead parseFileHead(String line, FileRecordAddVo lineAdd) throws Exception {
        FileRecordParserHead parser = new FileRecordParserHead();
        FileRecordHead frh = (FileRecordHead) parser.parse(line, lineAdd);
        return frh;
    }

    protected FileRecordHead parseFileHeadOtherLineStation(String line, FileRecordAddVo lineAdd) throws Exception {
        // FileRecordParserHeadOtherLine parser = new FileRecordParserHeadOtherLine();
        FileRecordParserHeadOther parser = new FileRecordParserHeadOther();
        FileRecordHead frh = (FileRecordHead) parser.parse(line, lineAdd);
        return frh;
    }

    protected FileRecordHead parseFileHeadOtherOnlyLine(String line, FileRecordAddVo lineAdd) throws Exception {
        FileRecordParserHeadOtherLine parser = new FileRecordParserHeadOtherLine();
        //FileRecordParserHeadOther parser =new FileRecordParserHeadOther();
        FileRecordHead frh = (FileRecordHead) parser.parse(line, lineAdd);
        return frh;
    }

    protected void addLineToBuffForHex(StringBuffer sb, String line) {
        sb.append(line);
        for (char c : FrameCodeConstant.DELIMIT_CHARS_HEX) {
            sb.append(c);
        }
        /*
         sb.append((char) 13);
         sb.append((char) 10);
         */

    }

    protected FileRecordCrc parseFileCrc(String line, FileRecordAddVo lineAdd) throws Exception {
        FileRecordParserCrc parser = new FileRecordParserCrc();
        FileRecordCrc frc = (FileRecordCrc) parser.parse(line, lineAdd);
        return frc;
    }

    private boolean isUnique(String trdType, Object ob, HashMap<String, Vector> hm) {
        if (!hm.containsKey(trdType))//集合的第一个
        {
            return true;
        }

        Vector v = hm.get(trdType);
        if (v == null || v.isEmpty())//对应交易的第一个
        {
            return true;
        }
        FileRecordBase frb;
        FileRecordBase obTmp = (FileRecordBase) ob;
        String checkUniqueKey;
        String checkUniqueKeyTmp;

        checkUniqueKeyTmp = obTmp.getCheckUniqueKey();
        //校验对象为空或为NULL时，不做校验，直接返回
        if (!this.isValidValue(checkUniqueKeyTmp)) {
            return true;
        }


        for (int i = 0; i < v.size(); i++) {
            frb = (FileRecordBase) v.get(i);
            checkUniqueKey = frb.getCheckUniqueKey();

            //集合对象主键值为NULL或为空,不比较
            if (!this.isValidValue(checkUniqueKey)) {
                continue;
            }


            if (checkUniqueKey.equals(checkUniqueKeyTmp)) {
                return false;//重复
            }
        }
        return true;



    }

    private boolean isUniqueForLarge(String trdType, Object ob, HashMap<String, TreeSet<String>> hmSorted) {
        if (!hmSorted.containsKey(trdType))//集合的第一个
        {
            return true;
        }

        // Vector v = hmSorted.get(trdType);
        TreeSet ts = hmSorted.get(trdType);
        if (ts == null || ts.isEmpty())//对应交易的第一个
        {
            return true;
        }
        FileRecordBase obTmp = (FileRecordBase) ob;
        String checkUniqueKeyTmp;

        checkUniqueKeyTmp = obTmp.getCheckUniqueKey();
        //校验对象为空或为NULL时，不做校验，直接返回
        if (!this.isValidValue(checkUniqueKeyTmp)) {
            return true;
        }
        if (ts.contains(checkUniqueKeyTmp)) {
            return false;
        }
        return true;

    }

    private boolean isValidValue(String value) {
        if (value == null || value.length() == 0) {
            return false;
        }
        return true;

    }

    protected boolean checkDataForUnique(FileRecordBase r, HashMap<String, Vector> hm) {
        boolean chkResult = false;
        TradeBaseDao dao;
        String className = TradeBaseDao.CLASS_PREX + r.getTrdType() + "Dao";
        String tradeType, errCode;
        //判断是否需要校验
        //校验设备
        try {
            dao = (TradeBaseDao) Class.forName(className).newInstance();
            tradeType = r.getTrdType();
            chkResult = this.isUnique(tradeType, r, hm);
            if (!chkResult) {
                errCode = this.getErrCodeForRepeat(tradeType);
                dao.insertError(r, errCode);//modify by hejj 20160801
                //dao.insertError(r, FrameFileHandledConstant.RECORD_ERR_REPEAT[0]);
                logger.error("交易类型" + r.getTrdType() + " 记录重复：" + " 主健值：" + r.getCheckUniqueKey() + " 重复。");
                return chkResult;
            }


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }

    private String getErrCodeForRepeat(String trdType) {
        if (trdType == null || trdType.length() == 0) {
            return FrameFileHandledConstant.RECORD_ERR_REPEAT_ONE_FILE[0];
        }
        if (trdType.equals(FrameCodeConstant.TRX_TYPE_SALE_SJT_NONE)) {
            return FrameFileHandledConstant.RECORD_ERR_REPEAT_BUSINESS[0];
        }
        return FrameFileHandledConstant.RECORD_ERR_REPEAT_ONE_FILE[0];

    }

    protected boolean checkDataForUniqueForLarge(FileRecordBase r, HashMap<String, TreeSet<String>> hmSorted) {
        boolean chkResult = false;
        TradeBaseDao dao;
        String className = TradeBaseDao.CLASS_PREX + r.getTrdType() + "Dao";
        String tradeType;
        //判断是否需要校验
        //校验设备
        try {
            dao = (TradeBaseDao) Class.forName(className).newInstance();
            tradeType = r.getTrdType();
            chkResult = this.isUniqueForLarge(tradeType, r, hmSorted);
            if (!chkResult) {
                dao.insertError(r, FrameFileHandledConstant.RECORD_ERR_REPEAT[0]);
                logger.error("交易类型" + r.getTrdType() + " 记录重复：" + " 主健值：" + r.getCheckUniqueKey() + " 重复。");
                return chkResult;
            }


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }

    protected void parseFileData(String line, HashMap<String, Vector> hm, FileRecordAddVo lineAdd, String trdType, int n) throws RecordParseForFieldException {
        String errMsg;
        try {

            Object ob;

            String className = HandlerBase.CLASS_PARSER_PRIX + trdType;
            //if (trdType.equals(FrameCodeConstant.TRX_TYPE_SALE_SJT)) {
            ob = ((FileRecordParserBase) Class.forName(className).newInstance()).parse(line, lineAdd);
            if (ob != null) {
                this.putMap(trdType, ob, hm);

            }
            // }
        } catch (Exception e) {//非文件级记录解释错误,继续下一记录，不做文件级处理
            e.printStackTrace();
            errMsg = "第" + n + "行 " + e.getMessage();
            logger.error(errMsg);
            //记录错误字段日志
            this.writeFileErrorForField(lineAdd.getBalanceWaterNo(), lineAdd.getFileName(), FrameFileHandledConstant.FILE_ERR_FILE_UNKOWN[0], errMsg, line);

        }

    }

    protected boolean isNeedCheckUnique(String trdType) {
        if (trdType == null || trdType.length() == 0) {
            return false;
        }
        for (String tmp : FrameCodeConstant.TRX_TYPE_CODES_CHK_UNIQUE) {
            if (tmp.equals(trdType)) {
                return true;
            }

        }
        return false;
    }

    protected void parseFileDataForUnique(String line, HashMap<String, Vector> hm, FileRecordAddVo lineAdd, String trdType, int n) throws RecordParseForFieldException {
        String errMsg;
        try {

            Object ob;

            String className = HandlerBase.CLASS_PARSER_PRIX + trdType;
            //if (trdType.equals(FrameCodeConstant.TRX_TYPE_SALE_SJT)) {
            ob = ((FileRecordParserBase) Class.forName(className).newInstance()).parse(line, lineAdd);
            if (ob != null) {
                //不需要检查唯一性
                if (!this.isNeedCheckUnique(trdType)) {
                    this.putMap(trdType, ob, hm);
                    return;
                }
                //校验记录唯一性
                //如果唯一性校验不通过，写入对应错误表
                if (this.checkDataForUnique((FileRecordBase) ob, hm)) {
                    this.putMap(trdType, ob, hm);
                }
            }
            // }
        } catch (Exception e) {//非文件级记录解释错误,继续下一记录，不做文件级处理
            e.printStackTrace();
            errMsg = "第" + n + "行 " + e.getMessage();
            logger.error(errMsg);
            //记录错误字段日志
            this.writeFileErrorForField(lineAdd.getBalanceWaterNo(), lineAdd.getFileName(), FrameFileHandledConstant.FILE_ERR_FILE_UNKOWN[0], errMsg, line);

        }

        /*
         catch (Exception e) {
         //throw new RecordParseForFileException("解释记录类型" + trdType + "出错：" + e.getMessage());
         throw new RecordParseForFieldException("解释记录类型" + trdType + "出错：" + e.getMessage());
         }
         */
    }

    protected void parseFileDataForUniqueForLarge(String line, HashMap<String, Vector> hm, FileRecordAddVo lineAdd, String trdType, int n, HashMap<String, TreeSet<String>> hmSorted) throws RecordParseForFieldException {
        String errMsg;
        try {

            Object ob;

            String className = HandlerBase.CLASS_PARSER_PRIX + trdType;
            //if (trdType.equals(FrameCodeConstant.TRX_TYPE_SALE_SJT)) {
            ob = ((FileRecordParserBase) Class.forName(className).newInstance()).parse(line, lineAdd);
            if (ob != null) {
                //不需要检查唯一性
                if (!this.isNeedCheckUnique(trdType)) {
                    this.putMap(trdType, ob, hm);
                    return;
                }
                //校验记录唯一性
                //如果唯一性校验不通过，写入对应错误表
                if (this.checkDataForUniqueForLarge((FileRecordBase) ob, hmSorted)) {
                    this.putMap(trdType, ob, hm);
                    this.putMapForLarge(trdType, ob, hmSorted);//校验通过的记录的键值放入对应交易的集合。

                }
            }
            // }
        } catch (Exception e) {//非文件级记录解释错误,继续下一记录，不做文件级处理
            e.printStackTrace();
            errMsg = "第" + n + "行 " + e.getMessage();
            logger.error(errMsg);
            //记录错误字段日志
            this.writeFileErrorForField(lineAdd.getBalanceWaterNo(), lineAdd.getFileName(), FrameFileHandledConstant.FILE_ERR_FILE_UNKOWN[0], errMsg, line);

        }
    }

    protected void checkFileHeadTwo(FileRecordHead fh, FileNameSection sect) throws RecordParseHeadForFileException {
        String lineStationId = fh.getLineId() + fh.getStationId();
        int line =1;
        if (!lineStationId.equals(sect.getLineStationId())) {
            throw new RecordParseHeadForFileException(FrameFileHandledConstant.FILE_ERR_FILE_HEAD_STATION[1], FrameFileHandledConstant.FILE_ERR_FILE_HEAD_STATION[0],line);
        }
        if (fh.getSeq() != sect.getSeq()) {
            throw new RecordParseHeadForFileException(FrameFileHandledConstant.FILE_ERR_FILE_HEAD_SEQ[1], FrameFileHandledConstant.FILE_ERR_FILE_HEAD_SEQ[0],line);
        }
    }

    protected void checkFileHeadOne(FileRecordHead fh, FileNameSection sect) throws RecordParseForFileException {
        String lineStationId = fh.getLineId() + fh.getStationId();
        if (!lineStationId.equals(sect.getLineStationId())) {
            throw new RecordParseForFileException(FrameFileHandledConstant.FILE_ERR_FILE_HEAD_STATION[1], FrameFileHandledConstant.FILE_ERR_FILE_HEAD_STATION[0]);
        }
        // if (fh.getSeq() != sect.getSeq()) {
        //   throw new RecordParseException(FrameFileHandledConstant.FILE_ERR_FILE_HEAD_SEQ[1], FrameFileHandledConstant.FILE_ERR_FILE_HEAD_SEQ[0]);
        //}
    }

    protected void checkFileHeadOneLine(FileRecordHead fh, FileNameSection sect) throws RecordParseHeadForFileException, RecordParseForFileException {
        String lineStationId = fh.getLineId();//+ fh.getStationId();
        int line=1; 
        if (!lineStationId.equals(sect.getLineStationId())) {
            throw new RecordParseHeadForFileException(FrameFileHandledConstant.FILE_ERR_FILE_HEAD_STATION[1], FrameFileHandledConstant.FILE_ERR_FILE_HEAD_STATION[0],line);
        }
        // if (fh.getSeq() != sect.getSeq()) {
        //   throw new RecordParseException(FrameFileHandledConstant.FILE_ERR_FILE_HEAD_SEQ[1], FrameFileHandledConstant.FILE_ERR_FILE_HEAD_SEQ[0]);
        //}
    }

    protected void checkFileRecordNum(FileRecordHead fh, int rowCount) throws RecordParseHeadForFileException {
        int line =1;
        if (rowCount != fh.getRowCount()) {
            throw new RecordParseHeadForFileException(FrameFileHandledConstant.FILE_ERR_FILE_HEAD_ROW_COUNT[1] + "文件头：" + fh.getRowCount() + " 文件：" + rowCount,
                    FrameFileHandledConstant.FILE_ERR_FILE_HEAD_ROW_COUNT[0],line);
        }
    }

    protected void checkFileCrc(FileRecordCrc frc, StringBuffer sb) throws RecordParseForFileException {
        try {
            // String crcCal = CrcUtil.getCRC32Value(sb, CrcUtil.CRC_LEN);
            String crcCal = CrcUtil.getCRC32ValueByChar(sb, CrcUtil.CRC_LEN);

            logger.info("系统计算CRC码：" + crcCal.toUpperCase() + " 文件计算的CRC:" + frc.getCrc().toUpperCase());
            if (!crcCal.equalsIgnoreCase(frc.getCrc())) {
                throw new RecordParseForFileException(FrameFileHandledConstant.FILE_ERR_FILE_CRC[1], FrameFileHandledConstant.FILE_ERR_FILE_CRC[0]);
            }
        } catch (Exception e) {
            throw new RecordParseForFileException(FrameFileHandledConstant.FILE_ERR_FILE_CRC[1], FrameFileHandledConstant.FILE_ERR_FILE_CRC[0]);
        }
    }

    protected boolean existSubRecord(FileRecordBase r) {
        if (r == null) {
            return false;
        }
        if (r.getSubRecords() == null) {
            return false;
        }
        return true;
    }

    protected void addSubRecordSeq(Vector records, Vector seqs) {
        String seq;
        FileRecord00 record;
        FileRecordBase r;
        for (int i = 0; i < records.size(); i++) {
            r = (FileRecordBase) records.get(i);
            seq = (String) seqs.get(i);
            r.setWaterNo(seq);
            if (this.existSubRecord(r)) {
                this.addSubRecordSeqUnit(r.getSubRecords(), seq);
            }
        }
    }

    private boolean isTradeTypeWithSub(String tradeType) {
        for (String str : HandlerBase.TRAD_TYPES_WITH_SUB) {
            if (str.equals(tradeType)) {
                return true;
            }
        }
        return false;
    }

    protected void addSubRecordVSeq(FileData fd) throws Exception {
        HashMap hm = fd.getContent();
        Vector v;
        FileRecordSeqDao dao = new FileRecordSeqDao();
        ResultFromProc result;
        String tradTypeStr;
        for (Object tradType : hm.keySet()) {
            v = (Vector) hm.get(tradType);
            tradTypeStr = (String) tradType;
            if (!this.isTradeTypeWithSub(tradTypeStr)) {//判断数据类型是否属于具有子类型
                continue;
            }

            result = dao.getFileRecordSeqs(tradTypeStr, v.size());
            this.addSubRecordSeq(v, result.getRetValues());
        }
    }

    protected void addSubRecordSeqUnit(HashMap subRecords, String seq) {
        Vector v;
        FileRecordBase fb;
        for (Object ob : subRecords.values()) {
            v = (Vector) ob;
            for (Object ob1 : v) {
                fb = (FileRecordBase) ob1;
                fb.setWaterNo(seq);
            }
        }
    }

    protected void addSubRecordsDetail(Vector records, HashMap hm) {
        FileRecordBase record;
        Vector v;
        HashMap subRecords;
        for (int i = 0; i < records.size(); i++) {
            record = (FileRecordBase) records.get(i); //记录
            if (this.existSubRecord(record)) {
                //存在分记录
                subRecords = record.getSubRecords();
                hm.putAll(subRecords); //分记录添加到记录的缓存中
            }
        }
    }

    protected void addSubRecordsToBuff(FileData fd) {
        HashMap hm = fd.getContent();
        HashMap hmResult = new HashMap();
        hmResult.putAll(hm);
        Vector v;
        String tradTypeStr;
        for (Object tradType : hm.keySet()) {
            v = (Vector) hm.get(tradType);
            tradTypeStr = (String) tradType;
            if (!this.isTradeTypeWithSub(tradTypeStr)) {//判断数据类型是否属于具有子类型
                continue;
            }
            this.addSubRecordsDetail(v, hmResult);
        }
        fd.setContent(hmResult);
    }

    protected void delete_printBuf(StringBuffer sb) {

        for (int i = 0; i < sb.length(); i++) {
            if ((int) sb.charAt(i) < 10) {
                System.out.print("0" + Integer.toHexString((int) sb.charAt(i)) + " ");
            } else {
                System.out.print(Integer.toHexString((int) sb.charAt(i)) + " ");
            }
            if (i != 0 && (i + 1) % 16 == 0) {
                System.out.print("\r\n");
            }
        }
    }

    protected void delete_printByteBuf(byte[] sb) {

        for (int i = 0; i < sb.length; i++) {
            if ((int) ((char) sb[i]) < 10) {
                System.out.print("0" + Integer.toHexString((int) ((char) sb[i])) + " ");
            } else {
                System.out.print(Integer.toHexString((int) ((char) sb[i])) + " ");
            }
            if (i != 0 && (i + 1) % 16 == 0) {
                System.out.print("\r\n");
            }
        }
    }

    protected boolean isFinishUnit(Vector<MessageBase> msgs) {
        if (msgs == null || msgs.isEmpty()) {
            return true;
        }
        for (MessageBase msg : msgs) {
            if (!msg.isFinished()) {
                return false;
            }
        }
        return true;
    }

    protected boolean isFinishWork(Vector<MessageBase> msgs_1, Vector<MessageBase> msgs_2) {
        boolean result = true;
        boolean result_1 = true;
        boolean result_2 = true;
        long startTime = System.currentTimeMillis();
        long endTime = 0;
        while (true) {
            result_1 = this.isFinishUnit(msgs_1);
            result_2 = this.isFinishUnit(msgs_2);
            result = result_1 & result_2;
            if (result) {
                break;
            }
            this.threadSleep(FrameCodeConstant.THREAD_IS_FINISH_INTERVAL);
        }
        endTime = System.currentTimeMillis();
        logger.info("完成处理工作耗时：" + (endTime - startTime) / 1000 + "秒");
        return result;
    }

    protected boolean isFinishWork(Vector<MessageBase> msgs_1) {
        boolean result = true;
        // boolean result_1 = true;
        //  boolean result_2 = true;
        long startTime = System.currentTimeMillis();
        long endTime = 0;
        while (true) {
            result = this.isFinishUnit(msgs_1);
            //result_2 = this.isFinishUnit(msgs_2);
            // result = result_1 ;//& result_2;
            if (result) {
                break;
            }
            this.threadSleep(FrameCodeConstant.THREAD_IS_FINISH_INTERVAL);
        }
        endTime = System.currentTimeMillis();
        logger.info("完成处理工作耗时：" + (endTime - startTime) / 1000 + "秒");
        return result;
    }

    protected void threadSleep(long interval) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
        }
    }

    private boolean isSubTrade(String tradeType) {
        for (String sufix : HandlerBase.TRAD_SUFIX) {
            if (tradeType.indexOf(sufix) != -1) {
                return true;
            }
        }
        return false;

    }

    protected void bufToQueue(String fileName, Set<String> tradTypes, String balanceWaterNo,int balanceWaterNoSub) throws TransferException {
        //交易记录进行数据库校验（重复性）及导入队列表
        String className;
        BufferToQueueBaseDao dao;
        try {
            for (String tradType : tradTypes) {
                if (this.isSubTrade(tradType)) {
                    continue;
                }
                className = HandlerTrx.CLASS_PREFIX + tradType + "Dao";
                dao = (BufferToQueueBaseDao) Class.forName(className).newInstance();
                dao.bufToQueue(balanceWaterNo,balanceWaterNoSub, fileName);
            }
        } catch (Exception e) {
            throw new TransferException(e.getMessage());
        }
    }

    protected String getTacLSKey(String cardLogicalId) {
        int len = cardLogicalId.length();
        return cardLogicalId;//cardLogicalId.substring(len - 16, len);
    }
}
