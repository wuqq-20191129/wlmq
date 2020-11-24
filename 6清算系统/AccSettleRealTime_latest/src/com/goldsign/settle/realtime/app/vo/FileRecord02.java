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
public class FileRecord02 extends FileRecordBase{

    /**
     * @return the putTime_s
     */
    public String getPutTime_s() {
        return putTime_s;
    }

    /**
     * @param putTime_s the putTime_s to set
     */
    public void setPutTime_s(String putTime_s) {
        this.putTime_s = putTime_s;
    }

    /**
     * @return the popTime_s
     */
    public String getPopTime_s() {
        return popTime_s;
    }

    /**
     * @param popTime_s the popTime_s to set
     */
    public void setPopTime_s(String popTime_s) {
        this.popTime_s = popTime_s;
    }

    //private String waterNo;
   // private String tradeType;
    /*
    private String lineId;//线路
    private String stationId;//车站
    private String devTypeId;//设备类型
    private String deviceId;//设备代码
    */
    private String operatorId;//操作员
    private String noteNo;//纸币箱编号
    private String putTime;//纸币箱放入时间
    private String popTime;//纸币箱取出时间
    private String putTime_s;//纸币箱放入时间
    private String popTime_s;//纸币箱取出时间
    private int waterNoOp;//流水号
    private int noteFee1;//纸币1单位金额
    private int noteFee2;//纸币2单位金额
    private int noteFee3;//纸币3单位金额
    private int noteFee4;//纸币4单位金额
    private int noteFee5;//纸币5单位金额
    private int noteFee6;//纸币6单位金额
    private int noteFee7;//纸币7单位金额
    private int noteFee8;//纸币8单位金额
    private int noteFee9;//纸币9单位金额
    private int noteFee10;//纸币10单位金额
    private int noteFee11;//纸币11单位金额
    private int noteFee12;//纸币12单位金额
    private int noteFee13;//纸币13单位金额
    private int noteNum1;//纸币1数量
    private int noteNum2;//纸币2数量
    private int noteNum3;//纸币3数量
    private int noteNum4;//纸币4数量
    private int noteNum5;//纸币5数量
    private int noteNum6;//纸币6数量
    private int noteNum7;//纸币7数量
    private int noteNum8;//纸币8数量
    private int noteNum9;//纸币9数量
    private int noteNum10;//纸币10数量
    private int noteNum11;//纸币11数量
    private int noteNum12;//纸币12数量
    private int noteNum13;//纸币13数量
    private int noteFeetotal;//钱箱金额
    private String balanceWaterNo;//
    private String fileName;//
    private String checkFlag;//











    /**
     * @return the operatorId
     */
    public String getOperatorId() {
        return operatorId;
    }

    /**
     * @param operatorId the operatorId to set
     */
    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    /**
     * @return the noteNo
     */
    public String getNoteNo() {
        return noteNo;
    }

    /**
     * @param noteNo the noteNo to set
     */
    public void setNoteNo(String noteNo) {
        this.noteNo = noteNo;
    }

    /**
     * @return the putTime
     */
    public String getPutTime() {
        return putTime;
    }

    /**
     * @param putTime the putTime to set
     */
    public void setPutTime(String putTime) {
        this.putTime = putTime;
    }

    /**
     * @return the popTime
     */
    public String getPopTime() {
        return popTime;
    }

    /**
     * @param popTime the popTime to set
     */
    public void setPopTime(String popTime) {
        this.popTime = popTime;
    }

    /**
     * @return the waterNoOp
     */
    public int getWaterNoOp() {
        return waterNoOp;
    }

    /**
     * @param waterNoOp the waterNoOp to set
     */
    public void setWaterNoOp(int waterNoOp) {
        this.waterNoOp = waterNoOp;
    }

    /**
     * @return the noteFee1
     */
    public int getNoteFee1() {
        return noteFee1;
    }

    /**
     * @param noteFee1 the noteFee1 to set
     */
    public void setNoteFee1(int noteFee1) {
        this.noteFee1 = noteFee1;
    }

    /**
     * @return the noteFee2
     */
    public int getNoteFee2() {
        return noteFee2;
    }

    /**
     * @param noteFee2 the noteFee2 to set
     */
    public void setNoteFee2(int noteFee2) {
        this.noteFee2 = noteFee2;
    }

    /**
     * @return the noteFee3
     */
    public int getNoteFee3() {
        return noteFee3;
    }

    /**
     * @param noteFee3 the noteFee3 to set
     */
    public void setNoteFee3(int noteFee3) {
        this.noteFee3 = noteFee3;
    }

    /**
     * @return the noteFee4
     */
    public int getNoteFee4() {
        return noteFee4;
    }

    /**
     * @param noteFee4 the noteFee4 to set
     */
    public void setNoteFee4(int noteFee4) {
        this.noteFee4 = noteFee4;
    }

    /**
     * @return the noteFee5
     */
    public int getNoteFee5() {
        return noteFee5;
    }

    /**
     * @param noteFee5 the noteFee5 to set
     */
    public void setNoteFee5(int noteFee5) {
        this.noteFee5 = noteFee5;
    }

    /**
     * @return the noteFee6
     */
    public int getNoteFee6() {
        return noteFee6;
    }

    /**
     * @param noteFee6 the noteFee6 to set
     */
    public void setNoteFee6(int noteFee6) {
        this.noteFee6 = noteFee6;
    }

    /**
     * @return the noteFee7
     */
    public int getNoteFee7() {
        return noteFee7;
    }

    /**
     * @param noteFee7 the noteFee7 to set
     */
    public void setNoteFee7(int noteFee7) {
        this.noteFee7 = noteFee7;
    }

    /**
     * @return the noteFee8
     */
    public int getNoteFee8() {
        return noteFee8;
    }

    /**
     * @param noteFee8 the noteFee8 to set
     */
    public void setNoteFee8(int noteFee8) {
        this.noteFee8 = noteFee8;
    }

    /**
     * @return the noteFee9
     */
    public int getNoteFee9() {
        return noteFee9;
    }

    /**
     * @param noteFee9 the noteFee9 to set
     */
    public void setNoteFee9(int noteFee9) {
        this.noteFee9 = noteFee9;
    }

    /**
     * @return the noteFee10
     */
    public int getNoteFee10() {
        return noteFee10;
    }

    /**
     * @param noteFee10 the noteFee10 to set
     */
    public void setNoteFee10(int noteFee10) {
        this.noteFee10 = noteFee10;
    }

    /**
     * @return the noteFee11
     */
    public int getNoteFee11() {
        return noteFee11;
    }

    /**
     * @param noteFee11 the noteFee11 to set
     */
    public void setNoteFee11(int noteFee11) {
        this.noteFee11 = noteFee11;
    }

    /**
     * @return the noteFee12
     */
    public int getNoteFee12() {
        return noteFee12;
    }

    /**
     * @param noteFee12 the noteFee12 to set
     */
    public void setNoteFee12(int noteFee12) {
        this.noteFee12 = noteFee12;
    }

    /**
     * @return the noteFee13
     */
    public int getNoteFee13() {
        return noteFee13;
    }

    /**
     * @param noteFee13 the noteFee13 to set
     */
    public void setNoteFee13(int noteFee13) {
        this.noteFee13 = noteFee13;
    }

    /**
     * @return the noteNum1
     */
    public int getNoteNum1() {
        return noteNum1;
    }

    /**
     * @param noteNum1 the noteNum1 to set
     */
    public void setNoteNum1(int noteNum1) {
        this.noteNum1 = noteNum1;
    }

    /**
     * @return the noteNum2
     */
    public int getNoteNum2() {
        return noteNum2;
    }

    /**
     * @param noteNum2 the noteNum2 to set
     */
    public void setNoteNum2(int noteNum2) {
        this.noteNum2 = noteNum2;
    }

    /**
     * @return the noteNum3
     */
    public int getNoteNum3() {
        return noteNum3;
    }

    /**
     * @param noteNum3 the noteNum3 to set
     */
    public void setNoteNum3(int noteNum3) {
        this.noteNum3 = noteNum3;
    }

    /**
     * @return the noteNum4
     */
    public int getNoteNum4() {
        return noteNum4;
    }

    /**
     * @param noteNum4 the noteNum4 to set
     */
    public void setNoteNum4(int noteNum4) {
        this.noteNum4 = noteNum4;
    }

    /**
     * @return the noteNum5
     */
    public int getNoteNum5() {
        return noteNum5;
    }

    /**
     * @param noteNum5 the noteNum5 to set
     */
    public void setNoteNum5(int noteNum5) {
        this.noteNum5 = noteNum5;
    }

    /**
     * @return the noteNum6
     */
    public int getNoteNum6() {
        return noteNum6;
    }

    /**
     * @param noteNum6 the noteNum6 to set
     */
    public void setNoteNum6(int noteNum6) {
        this.noteNum6 = noteNum6;
    }

    /**
     * @return the noteNum7
     */
    public int getNoteNum7() {
        return noteNum7;
    }

    /**
     * @param noteNum7 the noteNum7 to set
     */
    public void setNoteNum7(int noteNum7) {
        this.noteNum7 = noteNum7;
    }

    /**
     * @return the noteNum8
     */
    public int getNoteNum8() {
        return noteNum8;
    }

    /**
     * @param noteNum8 the noteNum8 to set
     */
    public void setNoteNum8(int noteNum8) {
        this.noteNum8 = noteNum8;
    }

    /**
     * @return the noteNum9
     */
    public int getNoteNum9() {
        return noteNum9;
    }

    /**
     * @param noteNum9 the noteNum9 to set
     */
    public void setNoteNum9(int noteNum9) {
        this.noteNum9 = noteNum9;
    }

    /**
     * @return the noteNum10
     */
    public int getNoteNum10() {
        return noteNum10;
    }

    /**
     * @param noteNum10 the noteNum10 to set
     */
    public void setNoteNum10(int noteNum10) {
        this.noteNum10 = noteNum10;
    }

    /**
     * @return the noteNum11
     */
    public int getNoteNum11() {
        return noteNum11;
    }

    /**
     * @param noteNum11 the noteNum11 to set
     */
    public void setNoteNum11(int noteNum11) {
        this.noteNum11 = noteNum11;
    }

    /**
     * @return the noteNum12
     */
    public int getNoteNum12() {
        return noteNum12;
    }

    /**
     * @param noteNum12 the noteNum12 to set
     */
    public void setNoteNum12(int noteNum12) {
        this.noteNum12 = noteNum12;
    }

    /**
     * @return the noteNum13
     */
    public int getNoteNum13() {
        return noteNum13;
    }

    /**
     * @param noteNum13 the noteNum13 to set
     */
    public void setNoteNum13(int noteNum13) {
        this.noteNum13 = noteNum13;
    }

    /**
     * @return the noteFeetotal
     */
    public int getNoteFeetotal() {
        return noteFeetotal;
    }

    /**
     * @param noteFeetotal the noteFeetotal to set
     */
    public void setNoteFeetotal(int noteFeetotal) {
        this.noteFeetotal = noteFeetotal;
    }

    /**
     * @return the balanceWaterNo
     */
    public String getBalanceWaterNo() {
        return balanceWaterNo;
    }

    /**
     * @param balanceWaterNo the balanceWaterNo to set
     */
    public void setBalanceWaterNo(String balanceWaterNo) {
        this.balanceWaterNo = balanceWaterNo;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the checkFlag
     */
    public String getCheckFlag() {
        return checkFlag;
    }

    /**
     * @param checkFlag the checkFlag to set
     */
    public void setCheckFlag(String checkFlag) {
        this.checkFlag = checkFlag;
    }


}
