/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.message;

import com.goldsign.settle.realtime.frame.exception.BaseException;
import com.goldsign.settle.realtime.frame.exception.MessageException;
import com.goldsign.settle.realtime.frame.vo.FileRecordTacBase;

/**
 *
 * @author hejj
 */
public abstract class ConstructTacMessageBase {

    /**
     * *
     * 长度
     */
    protected static final int LEN_TOTAL = 141;//TAC校验消息的总长度，值为141，字符总数
    protected static final int LEN_TOTAL_CHARGE = 145;//TAC校验消息的总长度，值为145，字符总数(充值）
    protected static final int LEN_TOTAL_56 = 93;//56TAC校验消息的总长度，值为89，字符总数
    protected static final int LEN_CHAR_NUM = 2;//发送加密机的字符数的存储长度
    protected static final int LEN_CHANNEL_NO = 1;//发送加密机的通道号的存储长度
    protected static final int LEN_MSG_TYPE = 2;//消息类型58
    protected static final int LEN_KEY_ALG_ID = 1;//密钥算法标识
    protected static final int LEN_KEY_VER = 1;//密钥版本
    protected static final int LEN_KEY_GRP_ID = 2;//密钥组标识
    protected static final int LEN_KEY_ID = 2;//密钥索引
    protected static final int LEN_KEY_LS = 16;//密钥离散值
    protected static final int LEN_TAC = 8;//需校验的TAC
    protected static final int LEN_DATA_LEN = 3;//计算TAC的数据长度，值为30
    protected static final int LEN_DATA_PAD = 16;//计算TAC的数据填充的0
    protected static final int LEN_DATA_TRD_AMOUNT = 8;//计算TAC的数据的交易金额
    protected static final int LEN_DATA_TRD_TYPE = 2;//计算TAC的数据的交易类型02:充值 09符合消费 06单次消费
    protected static final int LEN_DATA_TERM_ID = 12;//终端机编号（SAM卡号)
    protected static final int LEN_DATA_TERM_TRD_SEQ = 8;//终端机交易序号
    protected static final int LEN_DATA_TRD_TIME = 14;//交易时间
    
     protected static final int LEN_DATA_TRD_BALANCE = 8;//余额
     protected static final int LEN_DATA_TRD_CHARGE_SEQ = 4;//充值交易计数
    
    protected static final int LEN_DATA_END = 4;//结束标识
    /**
     * 值常数
     */
     protected static final String VALUE_MSG_TYPE_56 = "56";
    protected static final String VALUE_MSG_TYPE = "58";
    protected static final int VALUE_CHANNEL_NO = 0;
    
    protected static final int VALUE_MSG_CHAR_NUM_CHARGE = 143;
    protected static final int VALUE_MSG_CHAR_NUM_56 = 91;
    protected static final String VALUE_DATA_PAD = "0000000000000000";
    protected static final String VALUE_DATA_LEN = "030";
    protected static final String VALUE_DATA_LEN_CHARGE = "032";
    
    protected static final String VALUE_DATA_END = "8000";
    /*
    TAC校验相关
    */
    //消息头
    protected static final int VALUE_MSG_CHAR_NUM = 139;//消费TAC后续字节数
    protected static final int VALUE_ENCODE=0X8810;
    protected static final int VALUE_KEY_IDX=0;
    protected static final int VALUE_SEQUENCE=1;
    protected static final int VALUE_RESERVE=0;
    //消息体
    protected static final String VALUE_COMMAND="UB";//指令
    protected static final String VALUE_ALG_ID="X";//算法标识，DES计算
    protected static final String VALUE_MODE_ID="1";//模式标识，计算MAC
    protected static final String VALUE_SCHEME_ID="2";//方案ID,使用子密钥进行3DES
    protected static final String VALUE_ROOT_KEY_TYPE="109";//根密钥类型
    protected static final String VALUE_DISPERSE_COUNT="3";//离散次数
    protected static final String VALUE_DISPERSE_DATA_METRO="5572756D71694D54";//轨道交通离散数据
    protected static final String VALUE_DISPERSE_DATA_METRO_ZONE="88100001FFFFFFFF";//地区代码
    protected static final String VALUE_PAD_ID="1";//强制填充80 00。。。
    protected static final String VALUE_IV_MAC="0000000000000000";//
    protected static final String VALUE_MAC_CALCULATED_LEN="022";//MAC计算长度
     protected static final String VALUE_MAC_CALCULATED_LEN_CHARGE="024";//MAC计算长度
    protected static final String VALUE_MAC_LEN="04";//MAC长度
    
    
    /**
     * 校验结果
     */
    public static final String CHECK_SUCCESS = "00";//校验成功
    public static final String CHECK_FAILURE = "99";//校验不成功

    /**
     *
     * @param str
     * @param arr
     * @param offset
     */
    protected void addStringToCharArray(String str, char[] arr, int offset, int len) throws MessageException {
        if (str == null || str.length() == 0) {
            throw new MessageException(BaseException.EC_MSG_FIELD_LEN_NULL_NAME,
                    BaseException.EC_MSG_FIELD_LEN_NULL);
        }
        if (str.length() != len) {
            throw new MessageException(BaseException.EC_MSG_FIELD_LEN_NAME,
                    BaseException.EC_MSG_FIELD_LEN);
        }
        char[] strArr = str.toCharArray();
        for (char c : strArr) {
            arr[offset] = c;
            offset++;
        }
    }

    private char strToHexChar(String str) {
        int hex = Integer.parseInt(str, 16);
        char c = (char) hex;
        return c;
    }

    /**
     * 16进制的数字串，每2个字符存储为单字符如“2012”-》“20”“12”
     *
     * @param str
     * @param arr
     * @param offset
     */
    protected void delete_addStringHexToCharArray(String str, char[] arr, int offset) {
        char[] strArr = str.toCharArray();
        int len = str.length();
        if (len % 2 != 0) {
            str = "0" + str;
        }
        String unit;
        char c;
        for (int i = 0; i < len; i = i + 2) {
            unit = str.substring(i, i + 2);
            c = this.strToHexChar(unit);
            arr[offset++] = c;
        }
    }

    /**
     * 十进制数转成定长字符串 如 3 -》“003”
     *
     * @param n
     * @param len
     * @return
     */
    protected String intDecToFixStr(int n, int len) {
        String str = new Integer(n).toString();
        int lenStr = str.length();
        while (lenStr < 2 * len) {
            str = "0" + str;
            lenStr = str.length();
        }
        return str;
    }

    protected void addIntStrToCharArray(int n, int len, char[] arr, int offset) throws MessageException {
        String hexStr = Integer.toHexString(n).toUpperCase();
        int hexLen = hexStr.length();
        while (hexLen < len) {
            hexStr = "0" + hexStr;
            hexLen = hexStr.length();
        }
        this.addStringToCharArray(hexStr, arr, offset, len);



    }

    protected void delete_addIntStrToCharArray(int n, int len, char[] arr, int offset) {
        String nStr = this.intDecToFixStr(n, len);
        this.delete_addStringHexToCharArray(nStr, arr, offset);


    }

    protected void addIntValueToCharArray(int n, int len, char[] arr, int offset) {
        String hexStr = Integer.toHexString(n);
        int lenHex = hexStr.length();
        while (lenHex < 2 * len) {
            hexStr = "0" + hexStr;
            //hexStr =  hexStr+"0";
            lenHex = hexStr.length();
        }
        int j = 0;
        for (int i = 0; i < len; i++) {
            arr[offset + i] = (char) Integer.parseInt(hexStr.substring(j, j + 2), 16);
            j = j + 2;

        }



    }

    public abstract char[] constructTacMessage(FileRecordTacBase frt) throws MessageException;
    public abstract char[] constructTacMessage56(FileRecordTacBase frt) throws MessageException;
}
