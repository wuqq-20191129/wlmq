/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.test.file;

import com.goldsign.settle.realtime.app.vo.FileRecord50;
import com.goldsign.settle.realtime.frame.util.ByteGenUtil;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hejj
 */
public class FileTrx {

    public static int T_STR = 1;
    public static int T_BCD = 2;
    public static int T_INT = 3;
    public static char[] CRLF={0xD,0xA};

    public static void main(String[] arg) {
        FileTrx ft = new FileTrx();
        String path="D:/en_work/dir_prog_work/settle/模拟数据/程序生成";
        String fileName="TRX0101.20130530.01";
        try {
            char[] buffer = ft.getBufferCharForTrx();
            ft.writeFile(buffer, path, fileName);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(FileTrx.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void close(OutputStream os) {

        try {
            if (os != null) {
                os.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(FileTrx.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private FileRecord50 get50() {
        FileRecord50 fr = new FileRecord50();
        /*
         *  private int waterNo;//流水号
         /**
         * 设备相关
         */

        //private String lineId;//线路ID
        //private String stationId;//车站ID
        //private String devTypeId;//设备类型ID
        //private String deviceId;//设备ID
        //private String samLogicalId;//SAM卡逻辑卡号
        fr.setLineId("01");
        fr.setStationId("01");
        fr.setDevTypeId("04");
        fr.setDeviceId("001");
        fr.setSamLogicalId("000000008A654672");
        

        /**
         * 卡相关
         */
        //private String cardMainId;//card_main_id
        //private String cardSubId;//card_sub_id
        //private String tkLogicNo;//票卡逻辑卡号
        //private String tkPhyNo;//票卡物理卡号
        //private String zoneId;//区段代码ID
        //private String appFlag;//应用标识
        fr.setCardMainId("01");
        fr.setCardSubId("01");
        fr.setCardLogicalId("0101011234567010");
        fr.setCardPhysicalId("8A563201");
        fr.setZoneId("00");
        fr.setCardAppFlag("0");
        /**
         * 交易
         */
        //private int valueAmount;//金额
        //private String saleTime;//日期时间
        //private String amountType;//成本押金类型
        //private int amount;//成本押金金额
        //private int cardStatusId;//票卡状态ID
        //private String paymodeId;//支付类型
        //private String cardLogicId;//支付卡逻辑卡号
        //private int auxiExpense;//手续费
        //private String trdType;//交易类型
        fr.setSaleFee(200);
        fr.setSaleTime("20130514104300");
        fr.setDepositType("02");
        fr.setDepositFee(0);
        fr.setCardStatusId(132);
        fr.setPayType("01");
        fr.setCardLogicIdPay("00000000000000000000");
        fr.setAuxiFee(0);
        fr.setTrdType("50");
        /**
         * 校验
         */
        //private int tkSeqNo;//票卡序列号
        //private int samTrSeq;//SAM卡脱机交易流水号  
        //private String tac;//tac
        // private int countUsed;//使用次数
        fr.setSamTradeSeq(100);
        fr.setSamTradeSeq(100);
        fr.setTac("5AD43781");
        fr.setCardCountUsed(10);
        /**
         * 操作
         */
        //private String operatorId;//操作员ID
        //private String shiftId;//BOM班次序号
        fr.setOperatorId("000001");
        fr.setShiftId("99");
        /**
         * 系统处理
         */
        //private String balanceWaterNo;//清算流水号
        //private String fileName;//文件名
        // private String checkFlag;//校验标志
        return fr;


    }

    public byte[] getBufferForTrx() {
        byte[] bs = null;
        FileRecord50 rd50 = new FileRecord50();

        return bs;
    }

    public char[] getBufferCharForTrx() throws UnsupportedEncodingException {
        char[] bs = null;
        /******************文件头***********************/
        int[] data_type_head ={T_STR,T_BCD,T_BCD,T_INT,T_INT};
        int[] data_len_head ={4,7,7,2,4};
        Object[] data_head ={"0101","20130530100000","20130530100002",1,1};
        
        /*********************************************/
        /******************文件尾***********************/
        int[] data_type_tail ={T_STR,T_STR};
        int[] data_len_tail ={4,8};
        Object[] data_tail ={"CRC:","037067c5"};
        /*********************************************/

        /*******************发售数据**************/
        int[] data_type = {
            T_STR, T_STR, T_STR, T_STR, T_STR,
            T_INT, T_BCD, T_BCD, T_STR, T_INT,
            T_STR, T_STR, T_INT, T_INT, T_BCD,
            T_BCD, T_STR, T_BCD, T_INT, T_STR,
            T_BCD, T_INT, T_STR
        };
        int[] data_len = {
            2, 4, 2, 3, 16,
            4, 7, 1, 20, 4,
            20, 20, 1, 2, 2,
            1, 10, 1, 2, 6,
            1, 4, 1
        };
        Object[] datas = {
            "50", "0101", "04", "001", "000000008A654672",
            100, "20130514104300", "01", "00000000000000000000", 10,
            "0101011234567010", "8A563201", 132, 50, "0101",
            "00", "5AD43781", "02", 0, "000001",
            "99", 0, "0"
        };
/****************************************************************************************/

      //  FileRecord50 rd50 = new FileRecord50();
        int blen1 = this.getBufferLen(data_len_head);//长度+回车换行符
        int blen2 = this.getBufferLen(data_len);
        int blen3 = this.getBufferLen(data_len_tail);
        int offset = 0;
        bs = new char[blen1+blen2+blen3];//总字符数
        
        //文件头
        char[] cs = this.getLine(data_head, data_len_head, data_type_head);
        this.addCharArrayToBuffer(bs, cs, offset);
        offset +=blen1;
         //文件记录
        cs = this.getLine(datas, data_len, data_type);
        this.addCharArrayToBuffer(bs, cs, offset);
        offset +=blen2;
         //文件尾
         cs = this.getLine(data_tail, data_len_tail, data_type_tail);
        this.addCharArrayToBuffer(bs, cs, offset);
        offset +=blen3;
        
        

        return bs;
    }
    private char[] getLine(Object[] datas,int[] data_len,int[] data_type) throws UnsupportedEncodingException{
        int blen = this.getBufferLen(data_len);
        char[] bs = new char[blen];
        Object obj;
        int dLen;
        int dType;
        char[] cs;
        int offset = 0;
        for (int i = 0; i < datas.length; i++) {
            obj = datas[i];
            dLen = data_len[i];
            dType = data_type[i];
            cs = this.getData(obj, dLen, dType);
            this.addCharArrayToBuffer(bs, cs, offset);
            offset += dLen;
        }
        this.addCharArrayToBuffer(bs, CRLF, offset);
        
        return bs;
    }

    private char[] getData(Object obj, int len, int type) throws UnsupportedEncodingException {
        char[] cs = null;
        if (type == T_STR) {
            cs = ByteGenUtil.stringToChar((String) obj, len);
            return cs;
        }
        if (type == T_BCD) {
            cs = ByteGenUtil.stringBcdToChar((String) obj,len);
            return cs;
        }
        if (type == T_INT) {
            cs = ByteGenUtil.intToChar(((Integer) obj).intValue(), len);
            return cs;
        }
        return cs;
    }

    private int getBufferLen(int[] dataLen) {
        int n = 0;
        for (int i : dataLen) {
            n += i;
        }
        n=n+2;
        return n;
    }
   

    private void addCharArrayToBuffer(char[] buf, char[] ca, int offset) {
        for (char c : ca) {
            buf[offset] = c;
            offset++;
        }
    }

    public void writeFile(char[] bs, String path, String fileName) {
        String fileNameFull = path + "/" + fileName;
        File f = new File(fileNameFull);
        FileOutputStream os = null;
        DataOutputStream dos = null;
        try {
            os = new FileOutputStream(f);
            dos = new DataOutputStream(os);
            for (char c : bs) {
                dos.write((int)c);
            }

            dos.flush();


        } catch (Exception ex) {
            Logger.getLogger(FileTrx.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.close(dos);
        }
    }
}
