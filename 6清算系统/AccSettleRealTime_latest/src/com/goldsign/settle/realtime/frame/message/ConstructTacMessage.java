/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.message;

import com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;
import com.goldsign.settle.realtime.frame.constant.FrameTacConstant;
import com.goldsign.settle.realtime.frame.constant.SocketUtil;
import com.goldsign.settle.realtime.frame.dao.TradeBaseDao;
import com.goldsign.settle.realtime.frame.exception.MessageException;
import com.goldsign.settle.realtime.frame.vo.FileRecordTacBase;
import com.goldsign.settle.realtime.frame.vo.SocketAttribute;
import com.goldsign.settle.realtime.frame.vo.TacCheckResult;
import java.io.IOException;
import java.net.SocketException;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class ConstructTacMessage extends ConstructTacMessageBase {

    private Logger logger = Logger.getLogger(ConstructTacMessage.class.getName());

    @Override
    public char[] constructTacMessage56(FileRecordTacBase frt) throws MessageException {
        //TAC校验消息的总长度包括2字节的数据长度说明、数据长度（95），值为97，字符总数
        char[] msg = new char[ConstructTacMessageBase.LEN_TOTAL_56];
        /**
         * 消息的数据字节数
         */
        int offset = 0;
        this.addIntValueToCharArray(ConstructTacMessageBase.VALUE_MSG_CHAR_NUM_56, ConstructTacMessageBase.LEN_CHAR_NUM, msg, offset);

        offset += ConstructTacMessageBase.LEN_CHAR_NUM;
        //this.addIntValueToCharArray(ConstructTacMessageBase.VALUE_CHANNEL_NO, ConstructTacMessageBase.LEN_CHANNEL_NO, msg, offset);
        //offset += ConstructTacMessageBase.LEN_CHANNEL_NO;
        /**
         * 消息内容
         */
        this.addStringToCharArray(ConstructTacMessageBase.VALUE_MSG_TYPE_56, msg, offset, ConstructTacMessageBase.LEN_MSG_TYPE);//消息类型58
        offset += ConstructTacMessageBase.LEN_MSG_TYPE;
        this.addStringToCharArray(FrameTacConstant.VALUE_KEY_ALG, msg, offset, ConstructTacMessageBase.LEN_KEY_ALG_ID);//密钥算法标识
        offset += ConstructTacMessageBase.LEN_KEY_ALG_ID;
        this.addStringToCharArray(FrameTacConstant.VALUE_KEY_VER, msg, offset, ConstructTacMessageBase.LEN_KEY_VER);//密钥版本
        offset += ConstructTacMessageBase.LEN_KEY_VER;
        this.addStringToCharArray(FrameTacConstant.VALUE_KEY_GRP, msg, offset, ConstructTacMessageBase.LEN_KEY_GRP_ID);//密钥组标识
        offset += ConstructTacMessageBase.LEN_KEY_GRP_ID;
        this.addStringToCharArray(FrameTacConstant.VALUE_KEY_ID, msg, offset, ConstructTacMessageBase.LEN_KEY_ID);//密钥索引
        offset += ConstructTacMessageBase.LEN_KEY_ID;

        this.addStringToCharArray(frt.getTacLSKey(), msg, offset, ConstructTacMessageBase.LEN_KEY_LS);//密钥离散值
        offset += ConstructTacMessageBase.LEN_KEY_LS;
        // this.addStringToCharArray(frt.getTacInRecord(), msg, offset, ConstructTacMessageBase.LEN_TAC);//需校验的TAC
        //offset += ConstructTacMessageBase.LEN_TAC;

        this.addStringToCharArray(ConstructTacMessageBase.VALUE_DATA_LEN, msg, offset, ConstructTacMessageBase.LEN_DATA_LEN);//计算TAC的数据长度，值为30
        offset += ConstructTacMessageBase.LEN_DATA_LEN;
        //this.addStringHexToCharArray(ConstructTacMessageBase.VALUE_DATA_PAD, msg, offset);//计算TAC的数据填充的0
        this.addStringToCharArray(ConstructTacMessageBase.VALUE_DATA_PAD, msg, offset, ConstructTacMessageBase.LEN_DATA_PAD);//计算TAC的数据填充的0
        offset += ConstructTacMessageBase.LEN_DATA_PAD;
        //计算TAC的数据的交易金额
        this.addIntStrToCharArray(frt.getTacDealAmout(), ConstructTacMessageBase.LEN_DATA_TRD_AMOUNT, msg, offset);
        offset += ConstructTacMessageBase.LEN_DATA_TRD_AMOUNT;
        this.addStringToCharArray(frt.getTacDealType(), msg, offset, ConstructTacMessageBase.LEN_DATA_TRD_TYPE);//计算TAC的数据的交易类型02:充值 09符合消费 06单次消费
        offset += ConstructTacMessageBase.LEN_DATA_TRD_TYPE;
        this.addStringToCharArray(frt.getTacTerminalNo(), msg, offset, ConstructTacMessageBase.LEN_DATA_TERM_ID);//终端机编号（SAM卡号)
        offset += ConstructTacMessageBase.LEN_DATA_TERM_ID;
        this.addIntStrToCharArray(frt.getTacTerminalTradeSeq(), ConstructTacMessageBase.LEN_DATA_TERM_TRD_SEQ, msg, offset);//终端机交易序号
        offset += ConstructTacMessageBase.LEN_DATA_TERM_TRD_SEQ;
        this.addStringToCharArray(frt.getTacDealTime(), msg, offset, ConstructTacMessageBase.LEN_DATA_TRD_TIME);//交易时间
        offset += ConstructTacMessageBase.LEN_DATA_TRD_TIME;

        this.addStringToCharArray(ConstructTacMessageBase.VALUE_DATA_END, msg, offset, ConstructTacMessageBase.LEN_DATA_END);//结束标识，值为8000
        offset += ConstructTacMessageBase.LEN_DATA_END;

        this.printCharArrayAscII(msg);
        //this.printCharArrayInt(msg);
        return msg;
    }

    @Override
    public char[] constructTacMessage(FileRecordTacBase frt) throws MessageException {
        if (this.isCharge(frt.getTacDealType())) {
            return this.constructTacMessageForCharge(frt);
        }
        return this.constructTacMessageForConsume(frt);

    }
    public char[] constructTacMessageForCharge(FileRecordTacBase frt) throws MessageException {
        //TAC校验消息的总长度包括2字节的数据长度说明、数据长度（145），不包括本身2字节
        int len = 0;
        char[] msg = new char[ConstructTacMessageBase.LEN_TOTAL_CHARGE];
        /**
         * 消息的数据字节数
         */
        //后续字节数143 =14(消息头）+2（命令）+129（命令参数）-2（自身）
        int offset = 0;
        len = 2;
        this.addIntValueToCharArray(ConstructTacMessageBase.VALUE_MSG_CHAR_NUM_CHARGE, len, msg, offset);
        // this.addStringToCharArray("005F", msg, offset);
        offset += ConstructTacMessageBase.LEN_CHAR_NUM;
        //this.addIntValueToCharArray(ConstructTacMessageBase.VALUE_CHANNEL_NO, ConstructTacMessageBase.LEN_CHANNEL_NO, msg, offset);
        //offset += ConstructTacMessageBase.LEN_CHANNEL_NO;
        /**
         * 消息头
         */
        //区域编码
        len = 2;
        this.addIntValueToCharArray(ConstructTacMessageBase.VALUE_ENCODE, len, msg, offset);
        offset += len;
        //密钥索引
        len = 2;
        this.addIntValueToCharArray(ConstructTacMessageBase.VALUE_KEY_IDX, len, msg, offset);
        offset += len;
        //报文序号
        len = 4;
        this.addIntValueToCharArray(ConstructTacMessageBase.VALUE_SEQUENCE, len, msg, offset);
        offset += len;
        //预留
        len = 4;
        this.addIntValueToCharArray(ConstructTacMessageBase.VALUE_RESERVE, len, msg, offset);
        offset += len;
        /**
         * 消息内容
         */
        //指令 值：UB
        len = 2;
        this.addStringToCharArray(ConstructTacMessageBase.VALUE_COMMAND, msg, offset, len);
        offset += len;

        //算法标识 值：x
        len = 1;
        this.addStringToCharArray(ConstructTacMessageBase.VALUE_ALG_ID, msg, offset, len);
        offset += len;

        //模式标识，计算MAC 值：1
        len = 1;
        this.addStringToCharArray(ConstructTacMessageBase.VALUE_MODE_ID, msg, offset, len);
        offset += len;

        //方案ID,使用子密钥进行DESMAC 值：2
        len = 1;
        this.addStringToCharArray(ConstructTacMessageBase.VALUE_SCHEME_ID, msg, offset, len);
        offset += len;

        //根密钥类型  值：109
        len = 3;
        this.addStringToCharArray(ConstructTacMessageBase.VALUE_ROOT_KEY_TYPE, msg, offset, len);
        offset += len;
        //根密钥  值：KXXX
        len = 4;
        this.addStringToCharArray(frt.getTacKeyIdx(), msg, offset, len);
        offset += len;

        //离散次数 值：3
        len = 1;
        this.addStringToCharArray(ConstructTacMessageBase.VALUE_DISPERSE_COUNT, msg, offset, len);
        offset += len;

        //48=离散数据轨道交通离散数据（16）+地区代码（16）+用户卡逻辑卡号（16）
        len = 48;
        this.addStringToCharArray(ConstructTacMessageBase.VALUE_DISPERSE_DATA_METRO+
                ConstructTacMessageBase.VALUE_DISPERSE_DATA_METRO_ZONE
                +frt.getTacLSKey(), msg, offset, len);//密钥离散值     
        offset += len;

        //填充标识，强制填充80 00。。。 值：1
        len = 1;
        this.addStringToCharArray(ConstructTacMessageBase.VALUE_PAD_ID, msg, offset, len);
        offset += len;

        //IV-MAC  值：16个0
        len = 16;
        this.addStringToCharArray(ConstructTacMessageBase.VALUE_IV_MAC, msg, offset, len);
        offset += len;

        //MAC计算长度 值：024
        len = 3;
        this.addStringToCharArray(ConstructTacMessageBase.VALUE_MAC_CALCULATED_LEN_CHARGE, msg, offset, len);
        offset += len;

        //MAC计算数据 交易后余额8+卡充值交易计数4+交易金额8+交易类型2+终端编号12+交易时间14
        //计算TAC的数据的钱包预额，共48字节
        len = 8;
        this.addIntStrToCharArray(frt.getTacDealBalanceFee(), len, msg, offset);
        offset += len;
        
         //卡联机充值交易计数
        len = 4;
        this.addIntStrToCharArray(frt.getTacCardChargeSeq(), len, msg, offset);
        offset += len;
        //交易金额
        len = 8;
        this.addIntStrToCharArray(frt.getTacDealAmout(), len, msg, offset);
        offset += len;
         //交易类型
        len = 2;
        this.addStringToCharArray(frt.getTacDealType(), msg, offset, len);//计算TAC的数据的交易类型02:充值 09符合消费 06单次消费
        offset += len;

        len = 12;
        this.addStringToCharArray(frt.getTacTerminalNo(), msg, offset, len);//终端机编号（SAM卡号)
        offset += len;



        len = 14;
        this.addStringToCharArray(frt.getTacDealTime(), msg, offset, len);//交易时间
        offset += len;
        /**
         * tac计算数据*********************
         */

        //
        //MAC长度
        len = 2;
        this.addStringToCharArray(ConstructTacMessageBase.VALUE_MAC_LEN, msg, offset, len);
        offset += len;

       
        this.printCharArrayAscII(msg);

        return msg;

    }

    public char[] constructTacMessageForConsume(FileRecordTacBase frt) throws MessageException {
        //TAC校验消息的总长度包括2字节的数据长度说明、数据长度（101），不包括本身2字节
        int len = 0;
        char[] msg = new char[ConstructTacMessageBase.LEN_TOTAL];
        /**
         * 消息的数据字节数
         */
        //后续字节数139 =14(消息头）+2（命令）+125（命令参数）-2（自身）
        int offset = 0;
        len = 2;
        this.addIntValueToCharArray(ConstructTacMessageBase.VALUE_MSG_CHAR_NUM, len, msg, offset);
        // this.addStringToCharArray("005F", msg, offset);
        offset += ConstructTacMessageBase.LEN_CHAR_NUM;
        //this.addIntValueToCharArray(ConstructTacMessageBase.VALUE_CHANNEL_NO, ConstructTacMessageBase.LEN_CHANNEL_NO, msg, offset);
        //offset += ConstructTacMessageBase.LEN_CHANNEL_NO;
        /**
         * 消息头
         */
        //区域编码
        len = 2;
        this.addIntValueToCharArray(ConstructTacMessageBase.VALUE_ENCODE, len, msg, offset);
        offset += len;
        //密钥索引
        len = 2;
        this.addIntValueToCharArray(ConstructTacMessageBase.VALUE_KEY_IDX, len, msg, offset);
        offset += len;
        //报文序号
        len = 4;
        this.addIntValueToCharArray(ConstructTacMessageBase.VALUE_SEQUENCE, len, msg, offset);
        offset += len;
        //预留
        len = 4;
        this.addIntValueToCharArray(ConstructTacMessageBase.VALUE_RESERVE, len, msg, offset);
        offset += len;
        /**
         * 消息内容
         */
        //指令 值：UB
        len = 2; 
        this.addStringToCharArray(ConstructTacMessageBase.VALUE_COMMAND, msg, offset, len);
        offset += len;

        //算法标识 值：X DES计算
        len = 1;
        this.addStringToCharArray(ConstructTacMessageBase.VALUE_ALG_ID, msg, offset, len);
        offset += len;

        //模式标识，计算MAC 值：1 
        len = 1;
        this.addStringToCharArray(ConstructTacMessageBase.VALUE_MODE_ID, msg, offset, len);
        offset += len;

        //方案ID,使用子密钥进行DESMAC 值：2
        len = 1;
        this.addStringToCharArray(ConstructTacMessageBase.VALUE_SCHEME_ID, msg, offset, len);
        offset += len;

        //根密钥类型 值：109
        len = 3;
        this.addStringToCharArray(ConstructTacMessageBase.VALUE_ROOT_KEY_TYPE, msg, offset, len);
        offset += len;
        //根密钥 值：KXXX
        len = 4;
        this.addStringToCharArray(frt.getTacKeyIdx(), msg, offset, len);
        offset += len;

        //离散次数 值：3
        len = 1;
        this.addStringToCharArray(ConstructTacMessageBase.VALUE_DISPERSE_COUNT, msg, offset, len);
        offset += len;

        //48=离散数据轨道交通离散数据（16）+地区代码（16）+用户卡逻辑卡号（16）
        len = 48;
        this.addStringToCharArray(ConstructTacMessageBase.VALUE_DISPERSE_DATA_METRO+
                ConstructTacMessageBase.VALUE_DISPERSE_DATA_METRO_ZONE
                +frt.getTacLSKey(), msg, offset, len);//密钥离散值     
        offset += len;

        //填充标识，强制填充80 00。。。 值：1
        len = 1;
        this.addStringToCharArray(ConstructTacMessageBase.VALUE_PAD_ID, msg, offset, len);
        offset += len;

        //IV-MAC 值：16个0
        len = 16;
        this.addStringToCharArray(ConstructTacMessageBase.VALUE_IV_MAC, msg, offset, len);
        offset += len;

        //MAC计算长度 值：022
        len = 3;
        this.addStringToCharArray(ConstructTacMessageBase.VALUE_MAC_CALCULATED_LEN, msg, offset, len);
        offset += len;

        //MAC计算数据 交易金额8+交易类型2+终端编号12+终端交易序号8+交易时间14
        //计算TAC的数据的交易金额共44字节
        len = 8;
        this.addIntStrToCharArray(frt.getTacDealAmout(), len, msg, offset);
        offset += len;

        len = 2;
        this.addStringToCharArray(frt.getTacDealType(), msg, offset, len);//计算TAC的数据的交易类型02:充值 09符合消费 06单次消费
        offset += len;

        len = 12;
        this.addStringToCharArray(frt.getTacTerminalNo(), msg, offset, len);//终端机编号（SAM卡号)
        offset += len;

        len = 8;
        this.addIntStrToCharArray(frt.getTacTerminalTradeSeq(), len, msg, offset);//终端机交易序号
        offset += len;

        len = 14;
        this.addStringToCharArray(frt.getTacDealTime(), msg, offset, len);//交易时间
        offset += len;
        /**
         * tac计算数据*********************
         */

        //
        //MAC长度
        len = 2;
        this.addStringToCharArray(ConstructTacMessageBase.VALUE_MAC_LEN, msg, offset, len);
        offset += len;

        /*
        
        
        this.addStringToCharArray(ConstructTacMessageBase.VALUE_MSG_TYPE, msg, offset, ConstructTacMessageBase.LEN_MSG_TYPE);//消息类型58
        offset += ConstructTacMessageBase.LEN_MSG_TYPE;
        this.addStringToCharArray(FrameTacConstant.VALUE_KEY_ALG, msg, offset, ConstructTacMessageBase.LEN_KEY_ALG_ID);//密钥算法标识
        offset += ConstructTacMessageBase.LEN_KEY_ALG_ID;
        this.addStringToCharArray(FrameTacConstant.VALUE_KEY_VER, msg, offset, ConstructTacMessageBase.LEN_KEY_VER);//密钥版本
        offset += ConstructTacMessageBase.LEN_KEY_VER;
        // this.addStringToCharArray(FrameTacConstant.VALUE_KEY_GRP, msg, offset, ConstructTacMessageBase.LEN_KEY_GRP_ID);//密钥组标识
        this.addStringToCharArray(frt.getTacKeyGroup(), msg, offset, ConstructTacMessageBase.LEN_KEY_GRP_ID);//密钥组标识
        offset += ConstructTacMessageBase.LEN_KEY_GRP_ID;
        // this.addStringToCharArray(FrameTacConstant.VALUE_KEY_ID, msg, offset, ConstructTacMessageBase.LEN_KEY_ID);//密钥索引
        this.addStringToCharArray(frt.getTacKeyIdx(), msg, offset, ConstructTacMessageBase.LEN_KEY_ID);//密钥索引
        offset += ConstructTacMessageBase.LEN_KEY_ID;

        this.addStringToCharArray(frt.getTacLSKey(), msg, offset, ConstructTacMessageBase.LEN_KEY_LS);//密钥离散值
        offset += ConstructTacMessageBase.LEN_KEY_LS;
        this.addStringToCharArray(frt.getTacInRecord(), msg, offset, ConstructTacMessageBase.LEN_TAC);//需校验的TAC
        offset += ConstructTacMessageBase.LEN_TAC;


        this.addStringToCharArray(ConstructTacMessageBase.VALUE_DATA_LEN, msg, offset, ConstructTacMessageBase.LEN_DATA_LEN);//计算TAC的数据长度，值为30
        offset += ConstructTacMessageBase.LEN_DATA_LEN;
        //this.addStringHexToCharArray(ConstructTacMessageBase.VALUE_DATA_PAD, msg, offset);//计算TAC的数据填充的0
        this.addStringToCharArray(ConstructTacMessageBase.VALUE_DATA_PAD, msg, offset, ConstructTacMessageBase.LEN_DATA_PAD);//计算TAC的数据填充的0
        offset += ConstructTacMessageBase.LEN_DATA_PAD;


        //计算TAC的数据的交易金额
        this.addIntStrToCharArray(frt.getTacDealAmout(), ConstructTacMessageBase.LEN_DATA_TRD_AMOUNT, msg, offset);
        offset += ConstructTacMessageBase.LEN_DATA_TRD_AMOUNT;
        this.addStringToCharArray(frt.getTacDealType(), msg, offset, ConstructTacMessageBase.LEN_DATA_TRD_TYPE);//计算TAC的数据的交易类型02:充值 09符合消费 06单次消费
        offset += ConstructTacMessageBase.LEN_DATA_TRD_TYPE;
        this.addStringToCharArray(frt.getTacTerminalNo(), msg, offset, ConstructTacMessageBase.LEN_DATA_TERM_ID);//终端机编号（SAM卡号)
        offset += ConstructTacMessageBase.LEN_DATA_TERM_ID;

        this.addIntStrToCharArray(frt.getTacTerminalTradeSeq(), ConstructTacMessageBase.LEN_DATA_TERM_TRD_SEQ, msg, offset);//终端机交易序号

        offset += ConstructTacMessageBase.LEN_DATA_TERM_TRD_SEQ;
        this.addStringToCharArray(frt.getTacDealTime(), msg, offset, ConstructTacMessageBase.LEN_DATA_TRD_TIME);//交易时间
        offset += ConstructTacMessageBase.LEN_DATA_TRD_TIME;
         */
        //  this.addStringToCharArray(ConstructTacMessageBase.VALUE_DATA_END, msg, offset, ConstructTacMessageBase.LEN_DATA_END);//结束标识
        // offset += ConstructTacMessageBase.LEN_DATA_END;
        this.printCharArrayAscII(msg);

        return msg;

    }

    public char[] delete_constructTacMessageForCharge(FileRecordTacBase frt) throws MessageException {
        //TAC校验消息的总长度包括2字节的数据长度说明、数据长度（95），值为97，字符总数

        char[] msg = new char[ConstructTacMessageBase.LEN_TOTAL_CHARGE];
        /**
         * 消息的数据字节数
         */
        int offset = 0;
        this.addIntValueToCharArray(ConstructTacMessageBase.VALUE_MSG_CHAR_NUM_CHARGE, ConstructTacMessageBase.LEN_CHAR_NUM, msg, offset);
        // this.addStringToCharArray("005F", msg, offset);
        offset += ConstructTacMessageBase.LEN_CHAR_NUM;
        //this.addIntValueToCharArray(ConstructTacMessageBase.VALUE_CHANNEL_NO, ConstructTacMessageBase.LEN_CHANNEL_NO, msg, offset);
        //offset += ConstructTacMessageBase.LEN_CHANNEL_NO;
        /**
         * 消息内容
         */
        this.addStringToCharArray(ConstructTacMessageBase.VALUE_MSG_TYPE, msg, offset, ConstructTacMessageBase.LEN_MSG_TYPE);//消息类型58
        offset += ConstructTacMessageBase.LEN_MSG_TYPE;
        this.addStringToCharArray(FrameTacConstant.VALUE_KEY_ALG, msg, offset, ConstructTacMessageBase.LEN_KEY_ALG_ID);//密钥算法标识
        offset += ConstructTacMessageBase.LEN_KEY_ALG_ID;
        this.addStringToCharArray(FrameTacConstant.VALUE_KEY_VER, msg, offset, ConstructTacMessageBase.LEN_KEY_VER);//密钥版本
        offset += ConstructTacMessageBase.LEN_KEY_VER;
        // this.addStringToCharArray(FrameTacConstant.VALUE_KEY_GRP, msg, offset, ConstructTacMessageBase.LEN_KEY_GRP_ID);//密钥组标识
        this.addStringToCharArray(frt.getTacKeyGroup(), msg, offset, ConstructTacMessageBase.LEN_KEY_GRP_ID);//密钥组标识
        offset += ConstructTacMessageBase.LEN_KEY_GRP_ID;
        // this.addStringToCharArray(FrameTacConstant.VALUE_KEY_ID, msg, offset, ConstructTacMessageBase.LEN_KEY_ID);//密钥索引
        this.addStringToCharArray(frt.getTacKeyIdx(), msg, offset, ConstructTacMessageBase.LEN_KEY_ID);//密钥索引
        offset += ConstructTacMessageBase.LEN_KEY_ID;

        this.addStringToCharArray(frt.getTacLSKey(), msg, offset, ConstructTacMessageBase.LEN_KEY_LS);//密钥离散值
        offset += ConstructTacMessageBase.LEN_KEY_LS;
        this.addStringToCharArray(frt.getTacInRecord(), msg, offset, ConstructTacMessageBase.LEN_TAC);//需校验的TAC
        offset += ConstructTacMessageBase.LEN_TAC;

        this.addStringToCharArray(ConstructTacMessageBase.VALUE_DATA_LEN_CHARGE, msg, offset, ConstructTacMessageBase.LEN_DATA_LEN);//计算TAC的数据长度，值为32
        offset += ConstructTacMessageBase.LEN_DATA_LEN;
        //this.addStringHexToCharArray(ConstructTacMessageBase.VALUE_DATA_PAD, msg, offset);//计算TAC的数据填充的0
        this.addStringToCharArray(ConstructTacMessageBase.VALUE_DATA_PAD, msg, offset, ConstructTacMessageBase.LEN_DATA_PAD);//计算TAC的数据填充的0
        offset += ConstructTacMessageBase.LEN_DATA_PAD;

        //if (this.isCharge(frt.getTacTradeType())) {//充值增加交易余额、充值交易计数
        this.addIntStrToCharArray(frt.getTacDealBalanceFee(), ConstructTacMessageBase.LEN_DATA_TRD_BALANCE, msg, offset);//交易余额
        offset += ConstructTacMessageBase.LEN_DATA_TRD_BALANCE;
        this.addIntStrToCharArray(frt.getTacCardChargeSeq(), ConstructTacMessageBase.LEN_DATA_TRD_CHARGE_SEQ, msg, offset);//充值交易计数
        offset += ConstructTacMessageBase.LEN_DATA_TRD_CHARGE_SEQ;
        //}
        //计算TAC的数据的交易金额
        this.addIntStrToCharArray(frt.getTacDealAmout(), ConstructTacMessageBase.LEN_DATA_TRD_AMOUNT, msg, offset);
        offset += ConstructTacMessageBase.LEN_DATA_TRD_AMOUNT;
        this.addStringToCharArray(frt.getTacDealType(), msg, offset, ConstructTacMessageBase.LEN_DATA_TRD_TYPE);//计算TAC的数据的交易类型02:充值 09符合消费 06单次消费
        offset += ConstructTacMessageBase.LEN_DATA_TRD_TYPE;
        this.addStringToCharArray(frt.getTacTerminalNo(), msg, offset, ConstructTacMessageBase.LEN_DATA_TERM_ID);//终端机编号（SAM卡号)
        offset += ConstructTacMessageBase.LEN_DATA_TERM_ID;
        // if (!this.isCharge(frt.getTacTradeType())) {//非充值，使用消费交易序号，充值使用充值充值交易计数是4位
        //   this.addIntStrToCharArray(frt.getTacTerminalTradeSeq(), ConstructTacMessageBase.LEN_DATA_TERM_TRD_SEQ, msg, offset);//终端机交易序号
        //}
        //offset += ConstructTacMessageBase.LEN_DATA_TERM_TRD_SEQ;
        this.addStringToCharArray(frt.getTacDealTime(), msg, offset, ConstructTacMessageBase.LEN_DATA_TRD_TIME);//交易时间
        offset += ConstructTacMessageBase.LEN_DATA_TRD_TIME;

        //  this.addStringToCharArray(ConstructTacMessageBase.VALUE_DATA_END, msg, offset, ConstructTacMessageBase.LEN_DATA_END);//结束标识
        // offset += ConstructTacMessageBase.LEN_DATA_END;
        this.printCharArrayAscII(msg);

        return msg;

    }

    private boolean isCharge(String dealType) {
        if (dealType == null || dealType.length() == 0) {
            return false;
        }
        if (dealType.equals(FrameCodeConstant.TRX_TAC_TYPE_CHARGE)) {
            return true;
        }
        return false;
    }

    private void printCharArrayAscII(char[] cs) {
        String tmp = new String(cs);

        logger.debug(tmp);
    }

    private void printCharArrayInt(char[] cs) {
        StringBuffer sb = new StringBuffer();
        for (char c : cs) {
            sb.append((int) c);
            sb.append(" ");
        }

        logger.info(sb.toString());
    }

    /**
     * 构造加密机消息格式及发送消息给加密机进行校验
     *
     * @param frbs
     */
    public void constuctAndSend(Vector<FileRecordTacBase> frbs) {
        char[] cs;
        SocketUtil util = new SocketUtil();
        SocketAttribute attr = new SocketAttribute(FrameTacConstant.SOCKET_SERVER, FrameTacConstant.SOCKET_PORT,
                FrameTacConstant.SOCKET_TIME_OUT);
        TacCheckResult result;
        try {
            util.connectServer(attr);
            for (FileRecordTacBase frb : frbs) {
                cs = this.constructTacMessage(frb);
                result = util.writeMessage(cs, attr,frb);
                if (!result.getRetCode().equals(ConstructTacMessageBase.CHECK_SUCCESS)) {
                    logger.info("业务交易类型:支付类型"+frb.getTacTradeType()+":"+frb.getTacPurseOpType()
                            +"票卡主类型：" + frb.getCardMainId() + " 票卡子类型："+frb.getCardSubId()
                            +" 交易金额：" + frb.getTacDealAmout() + " 交易类型：" + frb.getTacDealType()                         
                            + " 交易时间：" + frb.getTacDealTime()+ " 余额：" + frb.getTacDealBalanceFee()
                             + "终端号：" + frb.getTacTerminalNo() + " 终端交易序号：" + frb.getTacTerminalTradeSeq()
                            + " 充值交易计数：" + frb.getTacCardChargeSeq()
                            + "，TAC校验不通过，加密机返回代码：" + result.getRetCodeFromEncoder()
                            +",加密机计算TAC："+result.getTac()
                             + ",记录TAC:"+frb.getTacInRecord());
                    //写校验错误表
                    this.insertError(frb, frb.getTacTradeType());
                } else {
                    logger.debug("业务交易类型:支付类型"+frb.getTacTradeType()+":"+frb.getTacPurseOpType()
                            +"票卡主类型：" + frb.getCardMainId() + " 票卡子类型："+frb.getCardSubId()
                            + " 交易金额：" + frb.getTacDealAmout() + " 交易类型：" + frb.getTacDealType()                         
                            + " 交易时间：" + frb.getTacDealTime()+ " 余额：" + frb.getTacDealBalanceFee()
                             + "终端号：" + frb.getTacTerminalNo() + " 终端交易序号：" + frb.getTacTerminalTradeSeq()
                            + " 充值交易计数：" + frb.getTacCardChargeSeq()
                            + frb.getTacTerminalTradeSeq() + "，TAC校验成功");
                }

            }

        } catch (SocketException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            util.closeConnect(attr.getIn(), attr.getOut());
        }

    }

    public void constuctAndSend56(Vector<FileRecordTacBase> frbs) {
        char[] cs;
        SocketUtil util = new SocketUtil();
        SocketAttribute attr = new SocketAttribute(FrameTacConstant.SOCKET_SERVER, FrameTacConstant.SOCKET_PORT,
                FrameTacConstant.SOCKET_TIME_OUT);
        TacCheckResult result;
        try {
            util.connectServer(attr);
            for (FileRecordTacBase frb : frbs) {
                cs = this.constructTacMessage56(frb);
                result = util.writeMessage56(cs, attr);
                if (!result.getRetCode().equals(ConstructTacMessageBase.CHECK_SUCCESS)) {
                    logger.info(" 交易金额：" + frb.getTacDealAmout() + " 交易余额：" + frb.getTacDealBalanceFee()
                            +" 交易类型：" + frb.getTacDealType() + " 交易时间：" + frb.getTacDealTime()
                            + "终端号：" + frb.getTacTerminalNo() + " 终端交易序号：" + frb.getTacTerminalTradeSeq()
                             +" 卡逻辑卡号：" +frb.getTacCardLogicalId() +" 卡充值交易计数："+frb.getTacCardChargeSeq()
                            + "，TAC校验不通过，加密机返回代码：" + result.getRetCode());
                    //写校验错误表
                    this.insertError(frb, frb.getTacTradeType());
                } else {
                    logger.info(" 交易金额：" + frb.getTacDealAmout() + " 交易余额：" + frb.getTacDealBalanceFee()
                            +" 交易类型：" + frb.getTacDealType() + " 交易时间：" + frb.getTacDealTime()
                            + "终端号：" + frb.getTacTerminalNo() + " 终端交易序号：" + frb.getTacTerminalTradeSeq()
                            +" 卡逻辑卡号：" +frb.getTacCardLogicalId() +" 卡充值交易计数："+frb.getTacCardChargeSeq()
                            + frb.getTacTerminalTradeSeq() + "，TAC校验成功");
                }

            }

        } catch (SocketException ex) {
        } catch (IOException ex) {
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            util.closeConnect(attr.getIn(), attr.getOut());
        }

    }

    private String getHandleName(String tradeType) {
        return TradeBaseDao.CLASS_PREX + tradeType + TradeBaseDao.CLASS_Suffix;
    }

    private int insertError(FileRecordTacBase frb, String tradType) throws Exception {
        TradeBaseDao dao = (TradeBaseDao) Class.forName(this.getHandleName(tradType)).newInstance();
        return dao.insertError(frb);

    }
}
