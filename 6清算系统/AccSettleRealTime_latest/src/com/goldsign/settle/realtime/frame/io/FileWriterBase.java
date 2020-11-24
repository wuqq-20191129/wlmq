/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.io;

import com.goldsign.settle.realtime.app.vo.FileRecord00DetailBase;
import com.goldsign.settle.realtime.app.vo.FileRecord52;
import com.goldsign.settle.realtime.app.vo.FileRecord83Detail;
import com.goldsign.settle.realtime.frame.exception.FileWriteException;
import com.goldsign.settle.realtime.frame.util.TradeUtil;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public abstract class FileWriterBase {

    protected static String FIELD_DELIM = ",";
    protected static String CHARSET = "GBK";
    protected static String FILE_NAME_BCP_PRIX = "bcp";
    protected final static char[] CRLF_1 = {0x0d, 0x0a};//换行符
    protected final static char[] CRLF_UNIX = {0x0a};//换行符
    protected final static char[] CRLF_WIN = {0x0d, 0x0a};//换行符

    public abstract void writeFile(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException;

    public static String getFileNameBcp(String tradType, String fileName) {
        return FILE_NAME_BCP_PRIX + "." + tradType + "." + fileName;
    }

    public static char[] getLineDelimter() {
        String os = System.getProperty("os.name");
        if (os.indexOf("Windows") != -1) {
            return CRLF_WIN;
        } else {
            return CRLF_UNIX;
        }
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

    protected void closeFile(FileOutputStream fos, DataOutputStream dos) {
        try {

            if (fos != null) {
                fos.close();
            }
            if (dos != null) {
                dos.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected String convertFenToYuan(int fen) {
        BigDecimal yan = new BigDecimal(fen);
        yan.setScale(2);
        BigDecimal result = yan.divide(new BigDecimal(100));
        return result.toString();
    }

    protected void addCommonToBuff(StringBuffer sb, FileRecordBase vo) {
        sb.append(vo.getBalanceWaterNo() + FileWriterBase.FIELD_DELIM); //清算流水号
        sb.append(vo.getBalanceWaterNoSub() + FileWriterBase.FIELD_DELIM); //清算流水号,子流水
        sb.append(vo.getFileName() + FileWriterBase.FIELD_DELIM); //文件
        sb.append(vo.getCheckFlag()); //校验标志
    }

    protected String getLineFor00DetailBase(FileRecord00DetailBase vo) {
        StringBuffer sb = new StringBuffer();
        sb.append(vo.getWaterNo() + FileWriterBase.FIELD_DELIM); //流水号
        sb.append(vo.getCardMainId() + FileWriterBase.FIELD_DELIM); //票卡主类型
        sb.append(vo.getCardSubId() + FileWriterBase.FIELD_DELIM); //票卡子类型
        sb.append(vo.getNum() + FileWriterBase.FIELD_DELIM); //交易数量
        sb.append(this.convertFenToYuan(vo.getFee()) + FileWriterBase.FIELD_DELIM); //交易金额

        this.addCommonToBuff(sb, vo);
        sb.append(FileWriterBase.getLineDelimter());
        return sb.toString();
    }

    protected String getLineForDetailBaseWithFeeUnit(FileRecord00DetailBase vo) {
        StringBuffer sb = new StringBuffer();
        sb.append(vo.getWaterNo() + FileWriterBase.FIELD_DELIM); //流水号
        sb.append(this.convertFenToYuan(vo.getFeeUnit()) + FileWriterBase.FIELD_DELIM); //
        sb.append(vo.getNum() + FileWriterBase.FIELD_DELIM); //面值数量

        this.addCommonToBuff(sb, vo);
        sb.append(FileWriterBase.getLineDelimter());
        return sb.toString();
    }

    protected String getLineForDetailBaseWithFeeUnitAndType(FileRecord00DetailBase vo) {
        StringBuffer sb = new StringBuffer();
        sb.append(vo.getWaterNo() + FileWriterBase.FIELD_DELIM); //流水号
        sb.append(vo.getCommonType() + FileWriterBase.FIELD_DELIM); //类型
        sb.append(this.convertFenToYuan(vo.getFeeUnit()) + FileWriterBase.FIELD_DELIM); //
        sb.append(vo.getNum() + FileWriterBase.FIELD_DELIM); //面值数量

        this.addCommonToBuff(sb, vo);
        sb.append(FileWriterBase.getLineDelimter());
        return sb.toString();
    }
    
    

    protected String getLineForDetailBaseWithCardAndType(FileRecord00DetailBase vo) {
        StringBuffer sb = new StringBuffer();
        sb.append(vo.getWaterNo() + FileWriterBase.FIELD_DELIM); //流水号
        sb.append(vo.getCardMainId() + FileWriterBase.FIELD_DELIM); //卡主类型
        sb.append(vo.getCardSubId() + FileWriterBase.FIELD_DELIM); //卡子类型
        sb.append(vo.getCommonType() + FileWriterBase.FIELD_DELIM); //类型
        sb.append(vo.getNum() + FileWriterBase.FIELD_DELIM); //数量
        sb.append(this.convertFenToYuan(vo.getFee()) + FileWriterBase.FIELD_DELIM); //金额

        this.addCommonToBuff(sb, vo);
        sb.append(FileWriterBase.getLineDelimter());
        return sb.toString();
    }

    protected String getLineFor00DetailBaseWithPayType(FileRecord00DetailBase vo) {
        StringBuffer sb = new StringBuffer();
        sb.append(vo.getWaterNo() + FileWriterBase.FIELD_DELIM); //流水号
        sb.append(vo.getCardMainId() + FileWriterBase.FIELD_DELIM); //票卡主类型
        sb.append(vo.getCardSubId() + FileWriterBase.FIELD_DELIM); //票卡子类型
        sb.append(vo.getPayType() + FileWriterBase.FIELD_DELIM); //支付类型

        sb.append(vo.getNum() + FileWriterBase.FIELD_DELIM); //交易数量
        sb.append(this.convertFenToYuan(vo.getFee()) + FileWriterBase.FIELD_DELIM); //交易金额

        this.addCommonToBuff(sb, vo);
        sb.append(FileWriterBase.getLineDelimter());
        return sb.toString();
    }

    protected String getLineFor00DetailNumBase(FileRecordBase vob) {
        FileRecord00DetailBase vo = (FileRecord00DetailBase) vob;
        StringBuffer sb = new StringBuffer();
        sb.append(vo.getWaterNo() + FileWriterBase.FIELD_DELIM); //流水号
        sb.append(vo.getCardMainId() + FileWriterBase.FIELD_DELIM); //票卡主类型
        sb.append(vo.getCardSubId() + FileWriterBase.FIELD_DELIM); //票卡子类型
        sb.append(vo.getNum() + FileWriterBase.FIELD_DELIM); //交易数量
        // sb.append(this.convertFenToYuan(vo.getFee())); //交易金额

        this.addCommonToBuff(sb, vo);
        sb.append(FileWriterBase.getLineDelimter());
        return sb.toString();
    }

    protected void writeFileF00DetailBase(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        pathBcp = TradeUtil.getDirForBalanceWaterNo(pathBcp, true);//added by hejj 20160622
        String fileNameBcp = pathBcp + "/" + this.getFileNameBcp(tradType, fileName);
        FileOutputStream fos = null;
        OutputStreamWriter osr = null;
        BufferedWriter bw = null;
        FileRecord00DetailBase vo;
        String line;
        try {
            fos = new FileOutputStream(fileNameBcp);
            osr = new OutputStreamWriter(fos, FileWriterBase.CHARSET);
            bw = new BufferedWriter(osr);
            for (int i = 0; i < records.size(); i++) {
                vo = (FileRecord00DetailBase) records.get(i);
                line = this.getLineFor00DetailBase(vo);
                bw.write(line);
                bw.flush();
            }
        } catch (Exception ex) {
            throw new FileWriteException(ex.getMessage());
        } finally {
            this.closeFile(fos, osr, bw);
        }
    }

    protected void writeFileF00DetailBaseWithPayType(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        pathBcp = TradeUtil.getDirForBalanceWaterNo(pathBcp, true);//added by hejj 20160622
        String fileNameBcp = pathBcp + "/" + this.getFileNameBcp(tradType, fileName);
        FileOutputStream fos = null;
        OutputStreamWriter osr = null;
        BufferedWriter bw = null;
        FileRecord00DetailBase vo;
        String line;
        try {
            fos = new FileOutputStream(fileNameBcp);
            osr = new OutputStreamWriter(fos, FileWriterBase.CHARSET);
            bw = new BufferedWriter(osr);
            for (int i = 0; i < records.size(); i++) {
                vo = (FileRecord00DetailBase) records.get(i);
                line = this.getLineFor00DetailBaseWithPayType(vo);
                bw.write(line);
                bw.flush();
            }
        } catch (Exception ex) {
            throw new FileWriteException(ex.getMessage());
        } finally {
            this.closeFile(fos, osr, bw);
        }
    }

    protected void writeFileDetailBaseWithFeeUnit(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        pathBcp = TradeUtil.getDirForBalanceWaterNo(pathBcp, true);//added by hejj 20160622
        String fileNameBcp = pathBcp + "/" + this.getFileNameBcp(tradType, fileName);
        FileOutputStream fos = null;
        OutputStreamWriter osr = null;
        BufferedWriter bw = null;
        FileRecord00DetailBase vo;
        String line;
        try {
            fos = new FileOutputStream(fileNameBcp);
            osr = new OutputStreamWriter(fos, FileWriterBase.CHARSET);
            bw = new BufferedWriter(osr);
            for (int i = 0; i < records.size(); i++) {
                vo = (FileRecord00DetailBase) records.get(i);
                line = this.getLineForDetailBaseWithFeeUnit(vo);
                bw.write(line);
                bw.flush();
            }
        } catch (Exception ex) {
            throw new FileWriteException(ex.getMessage());
        } finally {
            this.closeFile(fos, osr, bw);
        }
    }

    protected void writeFileDetailBaseWithFeeUnitAndType(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        pathBcp = TradeUtil.getDirForBalanceWaterNo(pathBcp, true);//added by hejj 20160622
        String fileNameBcp = pathBcp + "/" + this.getFileNameBcp(tradType, fileName);
        FileOutputStream fos = null;
        OutputStreamWriter osr = null;
        BufferedWriter bw = null;
        FileRecord00DetailBase vo;
        String line;
        try {
            fos = new FileOutputStream(fileNameBcp);
            osr = new OutputStreamWriter(fos, FileWriterBase.CHARSET);
            bw = new BufferedWriter(osr);
            for (int i = 0; i < records.size(); i++) {
                vo = (FileRecord00DetailBase) records.get(i);
                line = this.getLineForDetailBaseWithFeeUnitAndType(vo);
                bw.write(line);
                bw.flush();
            }
        } catch (Exception ex) {
            throw new FileWriteException(ex.getMessage());
        } finally {
            this.closeFile(fos, osr, bw);
        }
    }

    protected void writeFileDetailBaseWithCardAndType(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        pathBcp = TradeUtil.getDirForBalanceWaterNo(pathBcp, true);//added by hejj 20160622
        String fileNameBcp = pathBcp + "/" + this.getFileNameBcp(tradType, fileName);
        FileOutputStream fos = null;
        OutputStreamWriter osr = null;
        BufferedWriter bw = null;
        FileRecord00DetailBase vo;
        String line;
        try {
            fos = new FileOutputStream(fileNameBcp);
            osr = new OutputStreamWriter(fos, FileWriterBase.CHARSET);
            bw = new BufferedWriter(osr);
            for (int i = 0; i < records.size(); i++) {
                vo = (FileRecord00DetailBase) records.get(i);
                line = this.getLineForDetailBaseWithCardAndType(vo);
                bw.write(line);
                bw.flush();
            }
        } catch (Exception ex) {
            throw new FileWriteException(ex.getMessage());
        } finally {
            this.closeFile(fos, osr, bw);
        }
    }

    protected void writeFileF00DetailNumBase(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        pathBcp = TradeUtil.getDirForBalanceWaterNo(pathBcp, true);//add by hejj 20160622
        String fileNameBcp = pathBcp + "/" + this.getFileNameBcp(tradType, fileName);
        FileOutputStream fos = null;
        OutputStreamWriter osr = null;
        BufferedWriter bw = null;
        FileRecord00DetailBase vo;
        String line;
        try {
            fos = new FileOutputStream(fileNameBcp);
            osr = new OutputStreamWriter(fos, FileWriterBase.CHARSET);
            bw = new BufferedWriter(osr);
            for (int i = 0; i < records.size(); i++) {
                vo = (FileRecord00DetailBase) records.get(i);
                line = this.getLineFor00DetailNumBase(vo);
                bw.write(line);
                bw.flush();
            }
        } catch (Exception ex) {
            throw new FileWriteException(ex.getMessage());
        } finally {
            this.closeFile(fos, osr, bw);
        }
    }

    protected void writeFileForCommon(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        pathBcp = TradeUtil.getDirForBalanceWaterNo(pathBcp, true);
        String fileNameBcp = pathBcp + "/" + this.getFileNameBcp(tradType, fileName);
        FileOutputStream fos = null;
        DataOutputStream dos = null; // new DataOutputStream(os);
        FileRecordBase vo;
        String line;
        char[] cs;
        try {
            fos = new FileOutputStream(fileNameBcp);
            dos = new DataOutputStream(fos);
            for (int i = 0; i < records.size(); i++) {
                vo = (FileRecordBase) records.get(i);
                line = this.getLine(vo);
                cs = line.toCharArray();
                for (char c : cs) {
                    dos.write((int) c);
                }
                dos.flush();
            }
        } catch (Exception ex) {
            throw new FileWriteException(ex.getMessage());
        } finally {
            this.closeFile(fos, dos);
        }
    }
    protected void writeFileForCommonCard(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        pathBcp = TradeUtil.getDirForBalanceWaterNo(pathBcp, true);
        String fileNameBcp = pathBcp + "/" + this.getFileNameBcp(tradType, fileName);
        FileOutputStream fos = null;
        DataOutputStream dos = null; // new DataOutputStream(os);
        FileRecordBase vo;
        String line;
        char[] cs;
        try {
            fos = new FileOutputStream(fileNameBcp);
            dos = new DataOutputStream(fos);
            for (int i = 0; i < records.size(); i++) {
                vo = (FileRecordBase) records.get(i);
                line = this.getLineForCard(vo);
                cs = line.toCharArray();
                for (char c : cs) {
                    dos.write((int) c);
                }
                dos.flush();
            }
        } catch (Exception ex) {
            throw new FileWriteException(ex.getMessage());
        } finally {
            this.closeFile(fos, dos);
        }
    }
    public String getLineForCard(FileRecordBase vo) {

        StringBuffer sb = new StringBuffer();
        sb.append(vo.getWaterNo() + FileWriterBase.FIELD_DELIM);//流水号
        
        sb.append(vo.getCardMainId()+ FileWriterBase.FIELD_DELIM);//票卡主类型
         sb.append(vo.getCardSubId()+ FileWriterBase.FIELD_DELIM);//票卡子类型
        sb.append(vo.getCommonNum()+ FileWriterBase.FIELD_DELIM);//数量
        sb.append(this.convertFenToYuan(vo.getCommonFee())+ FileWriterBase.FIELD_DELIM);//金额




        this.addCommonToBuff(sb, vo);
        sb.append(FileWriterBase.getLineDelimter());


        return sb.toString();
    }
  

    public abstract String getLine(FileRecordBase vob);
    // @Override
}
