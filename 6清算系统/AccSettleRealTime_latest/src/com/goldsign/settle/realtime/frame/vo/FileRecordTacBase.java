/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.vo;

import com.goldsign.settle.realtime.app.vo.FileRecord54;
import com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;
import com.goldsign.settle.realtime.frame.constant.FrameTacConstant;
import com.goldsign.settle.realtime.frame.handler.HandlerTrx;
import com.goldsign.settle.realtime.frame.util.TradeUtil;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class FileRecordTacBase extends FileRecordBase {

    private static Logger logger = Logger.getLogger(FileRecordTacBase.class.getName());

    private int tacDealAmout;//交易金额
    private String tacDealType;//交易类型
    private String tacDealTime;//交易时间
    private String tacTerminalNo;//终端编号
    private int tacTerminalTradeSeq;//终端序号
    private String tacInRecord;//记录的TAC码
    private String tacLSKey;//离散密钥
    private String tacKeyAlg;//密钥算法
    private String tacKeyVer;//密钥版本
    private String tacKeyGroup;//密钥组
    private String tacKeyIdx;//密钥索引号
    private int tacKeyIdx_n;//密钥索引号

    private String tacTradeType;//原始交易类型
    private String tacPurseOpType;//钱包操作类型

    private int tacDealBalanceFee;//13	余额
    private int tacCardChargeSeq;//14	票卡充值交易计数

    private String tacCardLogicalId;//tac分散的卡逻辑卡号

    /**
     * @return the tacDealAmout
     */
    public int getTacDealAmout() {
        return tacDealAmout;
    }

    /**
     * @param tacDealAmout the tacDealAmout to set
     */
    public void setTacDealAmout(int tacDealAmout) {
        this.tacDealAmout = tacDealAmout;
    }

    /**
     * @return the tacDealType
     */
    public String getTacDealType() {
        return tacDealType;
    }

    /**
     * @param tacDealType the tacDealType to set
     */
    public void setTacDealType(String cardMainCode, String trdType, String purseOpType) {
        if (!TradeUtil.isCardForMetro(cardMainCode)) {
            return;
        }
        if (this.isCardForSjt(cardMainCode)) {//单乘票所有交易的TAC交易类型为05
            this.tacDealType = FrameCodeConstant.TRX_TAC_TYPE_SJT;
            return;
        }
        if (purseOpType == null) {
            purseOpType = FrameCodeConstant.TRX_PAY_MODE_DEFAULT;
        }
        String key = trdType + purseOpType;
        for (int i = 0; i < FrameCodeConstant.TRX_TYPES.length; i++) {
            if (FrameCodeConstant.TRX_TYPES[i].equals(key)) {
                this.tacDealType = FrameCodeConstant.TRX_TYPES_TAC[i];
                break;
            }

        }
        if (this.getTacDealType() == null) {
            logger.error("交易类型：" + trdType + " 支付类型：" + purseOpType + "，没有找到对应的TAC交易类型");

        }

    }

    /**
     * @param tacDealType the tacDealType to set
     */
    public void setTacDealType(String tacDealType) {

        this.tacDealType = tacDealType;
    }

    /**
     * @return the tacDealTime
     */
    public String getTacDealTime() {
        return tacDealTime;
    }

    /**
     * @param tacDealTime the tacDealTime to set
     */
    public void setTacDealTime(String tacDealTime) {
        this.tacDealTime = tacDealTime;
    }

    /**
     * @return the tacTerminalNo
     */
    public String getTacTerminalNo() {
        return tacTerminalNo;
    }

    /**
     * @param tacTerminalNo the tacTerminalNo to set
     */
    public void setTacTerminalNo(String tacTerminalNo) {
        //
        int len = tacTerminalNo.length();
        //2014/1/7 TAC码校验的终端编号，取16位SAM卡的1-4位+后面8位。 

        //   if (tacTerminalNo.length() > 12) {
        //     tacTerminalNo = tacTerminalNo.substring(len - 12, len);
        //  }
        if (tacTerminalNo.length() >= 16) {
            tacTerminalNo = tacTerminalNo.substring(len - 16, len - 12) + tacTerminalNo.substring(len - 8, len);
        }

        this.tacTerminalNo = tacTerminalNo;
    }

    /**
     * @return the tacTerminalTradeSeq
     */
    public int getTacTerminalTradeSeq() {
        return tacTerminalTradeSeq;
    }

    /**
     * @param tacTerminalTradeSeq the tacTerminalTradeSeq to set
     */
    public void setTacTerminalTradeSeq(int tacTerminalTradeSeq) {
        this.tacTerminalTradeSeq = tacTerminalTradeSeq;
    }

    /**
     * @return the tacLSKey
     */
    public String getTacLSKey() {
        return tacLSKey;
    }

    /**
     * @param tacLSKey the tacLSKey to set
     */
    public void setTacLSKey(String tacLSKey) {
        int len = tacLSKey.length();
        if (len > 16) {
            tacLSKey = tacLSKey.substring(0, 16);
        }
        this.tacLSKey = tacLSKey;
    }

    /**
     * @return the tacInRecord
     */
    public String getTacInRecord() {
        return tacInRecord;
    }

    /**
     * @param tacInRecord the tacInRecord to set
     */
    public void setTacInRecord(String tacInRecord) {
        if (tacInRecord.length() > 8) {
            tacInRecord = tacInRecord.substring(0, 8);
        }
        this.tacInRecord = tacInRecord;
    }

    /**
     * @return the tacKeyAlg
     */
    public String getTacKeyAlg() {
        return tacKeyAlg;
    }

    /**
     * @param tacKeyAlg the tacKeyAlg to set
     */
    public void setTacKeyAlg(String tacKeyAlg) {
        this.tacKeyAlg = tacKeyAlg;
    }

    /**
     * @return the tacKeyVer
     */
    public String getTacKeyVer() {
        return tacKeyVer;
    }

    /**
     * @param tacKeyVer the tacKeyVer to set
     */
    public void setTacKeyVer(String tacKeyVer) {
        this.tacKeyVer = tacKeyVer;
    }

    /**
     * @return the tacKeyIdx
     */
    public String getTacKeyIdx() {
        return tacKeyIdx;
    }

    /**
     * @param tacKeyIdx the tacKeyIdx to set
     */
    public void setTacKeyIdx(String tacKeyIdx) {
        this.tacKeyIdx = tacKeyIdx;
    }

    /**
     * @return the tacTradeType
     */
    public String getTacTradeType() {
        return tacTradeType;
    }

    /**
     * @param tacTradeType the tacTradeType to set
     */
    public void setTacTradeType(String tacTradeType) {
        this.tacTradeType = tacTradeType;
    }

    /**
     * @return the tacKeyGroup
     */
    public String getTacKeyGroup() {
        return tacKeyGroup;
    }

    /**
     * @param tacKeyGroup the tacKeyGroup to set
     */
    public void setTacKeyGroup(String tacKeyGroup) {
        this.tacKeyGroup = tacKeyGroup;
    }

    private String getKeyIndexByFlagForSjt(String flag) {
        if (flag.equals(FrameTacConstant.KEY_TEST_FLAG)) {
            return FrameTacConstant.VALUE_KEY_IDX_TEST_M1;
        }
        return FrameTacConstant.VALUE_KEY_IDX_PRODUCT_M1;

    }

    private String getKeyIndexByFlagForCPU(String flag) {
        if (flag.equals(FrameTacConstant.KEY_TEST_FLAG)) {
            return FrameTacConstant.VALUE_KEY_IDX_TEST_CPU;
        }
        return FrameTacConstant.VALUE_KEY_IDX_PRODUCT_CPU;

    }

    public void setTacKeyGroupAndInd(String cardMainCode) {
        //单程票：0102 储值类票：0204

        //  this.setTacKeyIdx(FrameTacConstant.VALUE_KEY_ID);
        String testFlag = FrameTacConstant.VALUE_KEY_TEST_FLAG;
        String keyIndex;
        if (this.isCardForSjt(cardMainCode)) {
            //this.setTacKeyGroup(FrameCodeConstant.TRX_TAC_KEY_GROUP_SJT);
            keyIndex = this.getKeyIndexByFlagForSjt(testFlag);
            this.setTacKeyIdx(keyIndex);
            return;
        }
        if (this.isCardForCpu(cardMainCode)) {
            // this.setTacKeyGroup(FrameCodeConstant.TRX_TAC_KEY_GROUP_CPU);
            keyIndex = this.getKeyIndexByFlagForCPU(testFlag);
            this.setTacKeyIdx(keyIndex);
            return;
        }
        logger.debug("票卡主类型:" + cardMainCode + "，不校验TAC，无密钥索引。");

    }

    private boolean isCardForSjt(String cardMainCode) {
        for (String tmp : FrameCodeConstant.CARD_MAIN_TYPE_SJTS) {
            if (tmp.equals(cardMainCode)) {
                return true;
            }
        }
        return false;
    }

    private boolean isCardForCpu(String cardMainCode) {
        for (String tmp : FrameCodeConstant.CARD_MAIN_TYPE_CPUS) {
            if (tmp.equals(cardMainCode)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return the tacDealBalanceFee
     */
    public int getTacDealBalanceFee() {
        return tacDealBalanceFee;
    }

    /**
     * @param tacDealBalanceFee the tacDealBalanceFee to set
     */
    public void setTacDealBalanceFee(int tacDealBalanceFee) {
        this.tacDealBalanceFee = tacDealBalanceFee;
    }

    /**
     * @return the tacCardChargeSeq
     */
    public int getTacCardChargeSeq() {
        return tacCardChargeSeq;
    }

    /**
     * @param tacCardChargeSeq the tacCardChargeSeq to set
     */
    public void setTacCardChargeSeq(int tacCardChargeSeq) {
        this.tacCardChargeSeq = tacCardChargeSeq;
    }

    /**
     * @return the tacKeyIdx_n
     */
    public int getTacKeyIdx_n() {
        return tacKeyIdx_n;
    }

    /**
     * @param tacKeyIdx_n the tacKeyIdx_n to set
     */
    public void setTacKeyIdx_n(int tacKeyIdx_n) {
        this.tacKeyIdx_n = tacKeyIdx_n;
    }

    /**
     * @return the tacPurseOpType
     */
    public String getTacPurseOpType() {
        if (this.tacPurseOpType == null || this.tacPurseOpType.length() == 0) {
            return "00";
        }
        return tacPurseOpType;
    }

    /**
     * @param tacPurseOpType the tacPurseOpType to set
     */
    public void setTacPurseOpType(String tacPurseOpType) {
        this.tacPurseOpType = tacPurseOpType;
    }

    /**
     * @return the tacCardLogicalId
     */
    public String getTacCardLogicalId() {
        return tacCardLogicalId;
    }

    /**
     * @param tacCardLogicalId the tacCardLogicalId to set
     */
    public void setTacCardLogicalId(String tacCardLogicalId) {
        this.tacCardLogicalId = tacCardLogicalId;
    }

}
