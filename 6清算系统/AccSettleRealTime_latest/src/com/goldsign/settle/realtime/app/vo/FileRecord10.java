/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.vo;

/**
 *
 * @author hejj
 */
public class FileRecord10 extends FileRecordNote{
    private int noteFee;//纸币面值
    private int noteNumPut;//纸币存入数量
    private int noteNumChg;//纸币找零数量
    private int noteNumBal;//纸币结存数量
    
    private int noteFeeTotalPut;//纸币存入金额
    private int noteFeeTotalChg;//纸币找零数量
    private int noteFeeTotalBal;//纸币结存数量



    /**
     * @return the noteNumPut
     */
    public int getNoteNumPut() {
        return noteNumPut;
    }

    /**
     * @param noteNumPut the noteNumPut to set
     */
    public void setNoteNumPut(int noteNumPut) {
        this.noteNumPut = noteNumPut;
    }

    /**
     * @return the noteNumChg
     */
    public int getNoteNumChg() {
        return noteNumChg;
    }

    /**
     * @param noteNumChg the noteNumChg to set
     */
    public void setNoteNumChg(int noteNumChg) {
        this.noteNumChg = noteNumChg;
    }

    /**
     * @return the noteNumBal
     */
    public int getNoteNumBal() {
        return noteNumBal;
    }

    /**
     * @param noteNumBal the noteNumBal to set
     */
    public void setNoteNumBal(int noteNumBal) {
        this.noteNumBal = noteNumBal;
    }

    /**
     * @return the noteFeeTotalPut
     */
    public int getNoteFeeTotalPut() {
        return noteFeeTotalPut;
    }

    /**
     * @param noteFeeTotalPut the noteFeeTotalPut to set
     */
    public void setNoteFeeTotalPut(int noteFeeTotalPut) {
        this.noteFeeTotalPut = noteFeeTotalPut;
    }

    /**
     * @return the noteFeeTotalChg
     */
    public int getNoteFeeTotalChg() {
        return noteFeeTotalChg;
    }

    /**
     * @param noteFeeTotalChg the noteFeeTotalChg to set
     */
    public void setNoteFeeTotalChg(int noteFeeTotalChg) {
        this.noteFeeTotalChg = noteFeeTotalChg;
    }

    /**
     * @return the noteFeeTotalBal
     */
    public int getNoteFeeTotalBal() {
        return noteFeeTotalBal;
    }

    /**
     * @param noteFeeTotalBal the noteFeeTotalBal to set
     */
    public void setNoteFeeTotalBal(int noteFeeTotalBal) {
        this.noteFeeTotalBal = noteFeeTotalBal;
    }

    /**
     * @return the noteFee
     */
    public int getNoteFee() {
        return noteFee;
    }

    /**
     * @param noteFee the noteFee to set
     */
    public void setNoteFee(int noteFee) {
        this.noteFee = noteFee;
    }
    
    
}
