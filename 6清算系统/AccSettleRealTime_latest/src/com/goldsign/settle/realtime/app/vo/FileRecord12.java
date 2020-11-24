/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.vo;

import com.goldsign.settle.realtime.frame.vo.FileRecordBase;

/**
 *
 * @author hejj
 */
public class FileRecord12 extends FileRecordBase {

    /**
     * @return the autoBalDatetimeBegein_s
     */
    public String getAutoBalDatetimeBegein_s() {
        return autoBalDatetimeBegein_s;
    }

    /**
     * @param autoBalDatetimeBegein_s the autoBalDatetimeBegein_s to set
     */
    public void setAutoBalDatetimeBegein_s(String autoBalDatetimeBegein_s) {
        this.autoBalDatetimeBegein_s = autoBalDatetimeBegein_s;
    }

    /**
     * @return the autoBalDatetimeEnd_s
     */
    public String getAutoBalDatetimeEnd_s() {
        return autoBalDatetimeEnd_s;
    }

    /**
     * @param autoBalDatetimeEnd_s the autoBalDatetimeEnd_s to set
     */
    public void setAutoBalDatetimeEnd_s(String autoBalDatetimeEnd_s) {
        this.autoBalDatetimeEnd_s = autoBalDatetimeEnd_s;
    }

    private String autoBalDatetimeBegein;//自动结存周期开始时间
    private String autoBalDatetimeEnd;//自动结存周期结束时间
    private String autoBalDatetimeBegein_s;//自动结存周期开始时间
    private String autoBalDatetimeEnd_s;//自动结存周期结束时间
    private int sjtSaleFeeTotal1;//单程票箱1发售总金额
    private int sjtSaleFeeTotal2;//单程票箱2发售总金额
    private int chargeFeeTotal;//充值总金额
    private int noteRecvFeeTotal;//纸币接收总金额
    private int coinRecvFeeTotal;//硬币接收总金额
    private int noteRecvFeeRepTotal;//更换纸币接收钱箱累计金额
    private int coinRecvFeeRepTotal;//更换硬币接收钱箱累计金额
    private int coinBalFeeTotal;//硬币钱箱结存金额
    private int noteBalFeeTotal;//纸币接收钱箱结存金额
    private int noteChgFeePutTotal1;//存入纸币找零箱1总金额
    private int noteChgFeePutTotal2;//存入纸币找零箱2总金额
    private int coinChgFeePutTotal1;//存入硬币找零HOPPER 1总金额
    private int coinChgFeePutTotal2;//存入硬币找零HOPPER 2总金额
    private int coinChgFeePopTotal1;//硬币找零HOPPER 1找零总金额
    private int coinChgFeePopTotal2;//硬币找零HOPPER 2找零总金额
    private int noteChgFeePopTotal1;//纸币找零箱1找零总金额
    private int noteChgFeePopTotal2;//纸币找零箱2找零总金额
    private int coinChgFeeBalTotal1;//硬币找零HOPPER 1结存总金额
    private int coinChgFeeBalTotal2;//硬币找零HOPPER 2结存总金额
    private int noteChgFeeBalTotal1;//纸币找零钱箱1结存总金额
    private int noteChgFeeBalTotal2;//纸币找零钱箱2结存总金额
    private int coinChgFeeClearTotal1;//硬币找零HOPPER 1清空总金额
    private int coinChgFeeClearTotal2;//硬币找零HOPPER 2清空总金额
    private int coinChgFeeBalSumTotal1;//清币结账后，硬币找零HOPPER1结存累计金额
    private int coinChgFeeBalSumTotal2;//清币结账后，硬币找零HOPPER2结存累计金额
    private int noteReclaimChgNum;//已更换纸币找零回收箱的回收数量
    private int noteReclaimBalNum;//纸币找零回收箱回收结存数量
    private int sjtNumPutTotal1;//单程票箱1存入总数量
    private int sjtNumPutTotal2;//单程票箱2存入总数量
    private int sjtNumSaleTotal1;//单程票箱1发售数量
    private int sjtNumSaleTotal2;//单程票箱2发售数量
    private int sjtNumClearTotal1;//单程票箱1清空数量
    private int sjtNumClearTotal2;//单程票箱2清空数量
    private int sjtNumBalTotal1;//单程票箱1结存数量
    private int sjtNumBalTotal2;//单程票箱2结存数量
    private int sjtNumWasteTotal;//单程票废票数量
    private int tvmBalFeeTotal;//TVM结存金额

    /**
     * @return the autoBalDatetimeBegein
     */
    public String getAutoBalDatetimeBegein() {
        return autoBalDatetimeBegein;
    }

    /**
     * @param autoBalDatetimeBegein the autoBalDatetimeBegein to set
     */
    public void setAutoBalDatetimeBegein(String autoBalDatetimeBegein) {
        this.autoBalDatetimeBegein = autoBalDatetimeBegein;
    }

    /**
     * @return the autoBalDatetimeEnd
     */
    public String getAutoBalDatetimeEnd() {
        return autoBalDatetimeEnd;
    }

    /**
     * @param autoBalDatetimeEnd the autoBalDatetimeEnd to set
     */
    public void setAutoBalDatetimeEnd(String autoBalDatetimeEnd) {
        this.autoBalDatetimeEnd = autoBalDatetimeEnd;
    }

    /**
     * @return the sjtSaleFeeTotal1
     */
    public int getSjtSaleFeeTotal1() {
        return sjtSaleFeeTotal1;
    }

    /**
     * @param sjtSaleFeeTotal1 the sjtSaleFeeTotal1 to set
     */
    public void setSjtSaleFeeTotal1(int sjtSaleFeeTotal1) {
        this.sjtSaleFeeTotal1 = sjtSaleFeeTotal1;
    }

    /**
     * @return the sjtSaleFeeTotal2
     */
    public int getSjtSaleFeeTotal2() {
        return sjtSaleFeeTotal2;
    }

    /**
     * @param sjtSaleFeeTotal2 the sjtSaleFeeTotal2 to set
     */
    public void setSjtSaleFeeTotal2(int sjtSaleFeeTotal2) {
        this.sjtSaleFeeTotal2 = sjtSaleFeeTotal2;
    }

    /**
     * @return the chargeFeeTotal
     */
    public int getChargeFeeTotal() {
        return chargeFeeTotal;
    }

    /**
     * @param chargeFeeTotal the chargeFeeTotal to set
     */
    public void setChargeFeeTotal(int chargeFeeTotal) {
        this.chargeFeeTotal = chargeFeeTotal;
    }

    /**
     * @return the noteRecvFeeTotal
     */
    public int getNoteRecvFeeTotal() {
        return noteRecvFeeTotal;
    }

    /**
     * @param noteRecvFeeTotal the noteRecvFeeTotal to set
     */
    public void setNoteRecvFeeTotal(int noteRecvFeeTotal) {
        this.noteRecvFeeTotal = noteRecvFeeTotal;
    }

    /**
     * @return the coinrecvFeeTotal
     */
 



    /**
     * @return the noteRecvFeeRepTotal
     */
    public int getNoteRecvFeeRepTotal() {
        return noteRecvFeeRepTotal;
    }

    /**
     * @param noteRecvFeeRepTotal the noteRecvFeeRepTotal to set
     */
    public void setNoteRecvFeeRepTotal(int noteRecvFeeRepTotal) {
        this.noteRecvFeeRepTotal = noteRecvFeeRepTotal;
    }

    /**
     * @return the coinRecvFeeRepTotal
     */
    public int getCoinRecvFeeRepTotal() {
        return coinRecvFeeRepTotal;
    }

    /**
     * @param coinRecvFeeRepTotal the coinRecvFeeRepTotal to set
     */
    public void setCoinRecvFeeRepTotal(int coinRecvFeeRepTotal) {
        this.coinRecvFeeRepTotal = coinRecvFeeRepTotal;
    }

    /**
     * @return the coinBalFeeTotal
     */
    public int getCoinBalFeeTotal() {
        return coinBalFeeTotal;
    }

    /**
     * @param coinBalFeeTotal the coinBalFeeTotal to set
     */
    public void setCoinBalFeeTotal(int coinBalFeeTotal) {
        this.coinBalFeeTotal = coinBalFeeTotal;
    }

    /**
     * @return the noteBalFeeTotal
     */
    public int getNoteBalFeeTotal() {
        return noteBalFeeTotal;
    }

    /**
     * @param noteBalFeeTotal the noteBalFeeTotal to set
     */
    public void setNoteBalFeeTotal(int noteBalFeeTotal) {
        this.noteBalFeeTotal = noteBalFeeTotal;
    }

    /**
     * @return the noteChgFeePutTotal1
     */
    public int getNoteChgFeePutTotal1() {
        return noteChgFeePutTotal1;
    }

    /**
     * @param noteChgFeePutTotal1 the noteChgFeePutTotal1 to set
     */
    public void setNoteChgFeePutTotal1(int noteChgFeePutTotal1) {
        this.noteChgFeePutTotal1 = noteChgFeePutTotal1;
    }

   

    /**
     * @return the coinChgFeePutTotal1
     */
    public int getCoinChgFeePutTotal1() {
        return coinChgFeePutTotal1;
    }

    /**
     * @param coinChgFeePutTotal1 the coinChgFeePutTotal1 to set
     */
    public void setCoinChgFeePutTotal1(int coinChgFeePutTotal1) {
        this.coinChgFeePutTotal1 = coinChgFeePutTotal1;
    }

    /**
     * @return the coinChgFeePutTotal2
     */
    public int getCoinChgFeePutTotal2() {
        return coinChgFeePutTotal2;
    }

    /**
     * @param coinChgFeePutTotal2 the coinChgFeePutTotal2 to set
     */
    public void setCoinChgFeePutTotal2(int coinChgFeePutTotal2) {
        this.coinChgFeePutTotal2 = coinChgFeePutTotal2;
    }



    /**
     * @return the coinChgFeePopTotal2
     */
    public int getCoinChgFeePopTotal2() {
        return coinChgFeePopTotal2;
    }

    /**
     * @param coinChgFeePopTotal2 the coinChgFeePopTotal2 to set
     */
    public void setCoinChgFeePopTotal2(int coinChgFeePopTotal2) {
        this.coinChgFeePopTotal2 = coinChgFeePopTotal2;
    }

   
   

    /**
     * @return the coinChgFeeBalTotal1
     */
    public int getCoinChgFeeBalTotal1() {
        return coinChgFeeBalTotal1;
    }

    /**
     * @param coinChgFeeBalTotal1 the coinChgFeeBalTotal1 to set
     */
    public void setCoinChgFeeBalTotal1(int coinChgFeeBalTotal1) {
        this.coinChgFeeBalTotal1 = coinChgFeeBalTotal1;
    }

    /**
     * @return the coinChgFeeBalTotal2
     */
    public int getCoinChgFeeBalTotal2() {
        return coinChgFeeBalTotal2;
    }

    /**
     * @param coinChgFeeBalTotal2 the coinChgFeeBalTotal2 to set
     */
    public void setCoinChgFeeBalTotal2(int coinChgFeeBalTotal2) {
        this.coinChgFeeBalTotal2 = coinChgFeeBalTotal2;
    }

    /**
     * @return the noteChgFeeBalTotal1
     */
    public int getNoteChgFeeBalTotal1() {
        return noteChgFeeBalTotal1;
    }

    /**
     * @param noteChgFeeBalTotal1 the noteChgFeeBalTotal1 to set
     */
    public void setNoteChgFeeBalTotal1(int noteChgFeeBalTotal1) {
        this.noteChgFeeBalTotal1 = noteChgFeeBalTotal1;
    }

    /**
     * @return the noteChgFeeBalTotal2
     */
    public int getNoteChgFeeBalTotal2() {
        return noteChgFeeBalTotal2;
    }

    /**
     * @param noteChgFeeBalTotal2 the noteChgFeeBalTotal2 to set
     */
    public void setNoteChgFeeBalTotal2(int noteChgFeeBalTotal2) {
        this.noteChgFeeBalTotal2 = noteChgFeeBalTotal2;
    }

    /**
     * @return the coinChgFeeClearTotal1
     */
    public int getCoinChgFeeClearTotal1() {
        return coinChgFeeClearTotal1;
    }

    /**
     * @param coinChgFeeClearTotal1 the coinChgFeeClearTotal1 to set
     */
    public void setCoinChgFeeClearTotal1(int coinChgFeeClearTotal1) {
        this.coinChgFeeClearTotal1 = coinChgFeeClearTotal1;
    }

    /**
     * @return the coinChgFeeClearTotal2
     */
    public int getCoinChgFeeClearTotal2() {
        return coinChgFeeClearTotal2;
    }

    /**
     * @param coinChgFeeClearTotal2 the coinChgFeeClearTotal2 to set
     */
    public void setCoinChgFeeClearTotal2(int coinChgFeeClearTotal2) {
        this.coinChgFeeClearTotal2 = coinChgFeeClearTotal2;
    }

    /**
     * @return the coinChgFeeBalSumTotal1
     */
    public int getCoinChgFeeBalSumTotal1() {
        return coinChgFeeBalSumTotal1;
    }

    /**
     * @param coinChgFeeBalSumTotal1 the coinChgFeeBalSumTotal1 to set
     */
    public void setCoinChgFeeBalSumTotal1(int coinChgFeeBalSumTotal1) {
        this.coinChgFeeBalSumTotal1 = coinChgFeeBalSumTotal1;
    }

    /**
     * @return the coinChgFeeBalSumTotal2
     */
    public int getCoinChgFeeBalSumTotal2() {
        return coinChgFeeBalSumTotal2;
    }

    /**
     * @param coinChgFeeBalSumTotal2 the coinChgFeeBalSumTotal2 to set
     */
    public void setCoinChgFeeBalSumTotal2(int coinChgFeeBalSumTotal2) {
        this.coinChgFeeBalSumTotal2 = coinChgFeeBalSumTotal2;
    }

    /**
     * @return the noteReclaimChgNum
     */
    public int getNoteReclaimChgNum() {
        return noteReclaimChgNum;
    }

    /**
     * @param noteReclaimChgNum the noteReclaimChgNum to set
     */
    public void setNoteReclaimChgNum(int noteReclaimChgNum) {
        this.noteReclaimChgNum = noteReclaimChgNum;
    }

    /**
     * @return the noteReclaimBalNum
     */
    public int getNoteReclaimBalNum() {
        return noteReclaimBalNum;
    }

    /**
     * @param noteReclaimBalNum the noteReclaimBalNum to set
     */
    public void setNoteReclaimBalNum(int noteReclaimBalNum) {
        this.noteReclaimBalNum = noteReclaimBalNum;
    }

    /**
     * @return the sjtNumPutTotal1
     */
    public int getSjtNumPutTotal1() {
        return sjtNumPutTotal1;
    }

    /**
     * @param sjtNumPutTotal1 the sjtNumPutTotal1 to set
     */
    public void setSjtNumPutTotal1(int sjtNumPutTotal1) {
        this.sjtNumPutTotal1 = sjtNumPutTotal1;
    }

    /**
     * @return the sjtNumPutTotal2
     */
    public int getSjtNumPutTotal2() {
        return sjtNumPutTotal2;
    }

    /**
     * @param sjtNumPutTotal2 the sjtNumPutTotal2 to set
     */
    public void setSjtNumPutTotal2(int sjtNumPutTotal2) {
        this.sjtNumPutTotal2 = sjtNumPutTotal2;
    }

    /**
     * @return the sjtNumSaleTotal1
     */
    public int getSjtNumSaleTotal1() {
        return sjtNumSaleTotal1;
    }

    /**
     * @param sjtNumSaleTotal1 the sjtNumSaleTotal1 to set
     */
    public void setSjtNumSaleTotal1(int sjtNumSaleTotal1) {
        this.sjtNumSaleTotal1 = sjtNumSaleTotal1;
    }

    /**
     * @return the sjtNumSaleTotal2
     */
    public int getSjtNumSaleTotal2() {
        return sjtNumSaleTotal2;
    }

    /**
     * @param sjtNumSaleTotal2 the sjtNumSaleTotal2 to set
     */
    public void setSjtNumSaleTotal2(int sjtNumSaleTotal2) {
        this.sjtNumSaleTotal2 = sjtNumSaleTotal2;
    }

    /**
     * @return the sjtNumClearTotal1
     */
    public int getSjtNumClearTotal1() {
        return sjtNumClearTotal1;
    }

    /**
     * @param sjtNumClearTotal1 the sjtNumClearTotal1 to set
     */
    public void setSjtNumClearTotal1(int sjtNumClearTotal1) {
        this.sjtNumClearTotal1 = sjtNumClearTotal1;
    }

    /**
     * @return the sjtNumClearTotal2
     */
    public int getSjtNumClearTotal2() {
        return sjtNumClearTotal2;
    }

    /**
     * @param sjtNumClearTotal2 the sjtNumClearTotal2 to set
     */
    public void setSjtNumClearTotal2(int sjtNumClearTotal2) {
        this.sjtNumClearTotal2 = sjtNumClearTotal2;
    }

    /**
     * @return the sjtNumBalTotal1
     */
    public int getSjtNumBalTotal1() {
        return sjtNumBalTotal1;
    }

    /**
     * @param sjtNumBalTotal1 the sjtNumBalTotal1 to set
     */
    public void setSjtNumBalTotal1(int sjtNumBalTotal1) {
        this.sjtNumBalTotal1 = sjtNumBalTotal1;
    }

    /**
     * @return the sjtNumBalTotal2
     */
    public int getSjtNumBalTotal2() {
        return sjtNumBalTotal2;
    }

    /**
     * @param sjtNumBalTotal2 the sjtNumBalTotal2 to set
     */
    public void setSjtNumBalTotal2(int sjtNumBalTotal2) {
        this.sjtNumBalTotal2 = sjtNumBalTotal2;
    }

    /**
     * @return the sjtNumWasteTotal
     */
    public int getSjtNumWasteTotal() {
        return sjtNumWasteTotal;
    }

    /**
     * @param sjtNumWasteTotal the sjtNumWasteTotal to set
     */
    public void setSjtNumWasteTotal(int sjtNumWasteTotal) {
        this.sjtNumWasteTotal = sjtNumWasteTotal;
    }

    /**
     * @return the tvmBalFeeTotal
     */
    public int getTvmBalFeeTotal() {
        return tvmBalFeeTotal;
    }

    /**
     * @param tvmBalFeeTotal the tvmBalFeeTotal to set
     */
    public void setTvmBalFeeTotal(int tvmBalFeeTotal) {
        this.tvmBalFeeTotal = tvmBalFeeTotal;
    }

    /**
     * @return the coinRecvFeeTotal
     */
    public int getCoinRecvFeeTotal() {
        return coinRecvFeeTotal;
    }

    /**
     * @param coinRecvFeeTotal the coinRecvFeeTotal to set
     */
    public void setCoinRecvFeeTotal(int coinRecvFeeTotal) {
        this.coinRecvFeeTotal = coinRecvFeeTotal;
    }

    /**
     * @return the noteChgFeePutTotal2
     */
    public int getNoteChgFeePutTotal2() {
        return noteChgFeePutTotal2;
    }

    /**
     * @param noteChgFeePutTotal2 the noteChgFeePutTotal2 to set
     */
    public void setNoteChgFeePutTotal2(int noteChgFeePutTotal2) {
        this.noteChgFeePutTotal2 = noteChgFeePutTotal2;
    }

    /**
     * @return the coinChgFeePopTotal1
     */
    public int getCoinChgFeePopTotal1() {
        return coinChgFeePopTotal1;
    }

    /**
     * @param coinChgFeePopTotal1 the coinChgFeePopTotal1 to set
     */
    public void setCoinChgFeePopTotal1(int coinChgFeePopTotal1) {
        this.coinChgFeePopTotal1 = coinChgFeePopTotal1;
    }

    /**
     * @return the noteChgFeePopTotal1
     */
    public int getNoteChgFeePopTotal1() {
        return noteChgFeePopTotal1;
    }

    /**
     * @param noteChgFeePopTotal1 the noteChgFeePopTotal1 to set
     */
    public void setNoteChgFeePopTotal1(int noteChgFeePopTotal1) {
        this.noteChgFeePopTotal1 = noteChgFeePopTotal1;
    }

    /**
     * @return the noteChgFeePopTotal2
     */
    public int getNoteChgFeePopTotal2() {
        return noteChgFeePopTotal2;
    }

    /**
     * @param noteChgFeePopTotal2 the noteChgFeePopTotal2 to set
     */
    public void setNoteChgFeePopTotal2(int noteChgFeePopTotal2) {
        this.noteChgFeePopTotal2 = noteChgFeePopTotal2;
    }
}
