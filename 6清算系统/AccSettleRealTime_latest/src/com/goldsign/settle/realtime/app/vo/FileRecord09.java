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
public class FileRecord09 extends FileRecordNote{
    
    
    private int noteFee;//纸币面值
    private int noteNum;//纸币数量
    private int noteFeeTotal;//纸币金额

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

    /**
     * @return the noteNum
     */
    public int getNoteNum() {
        return noteNum;
    }

    /**
     * @param noteNum the noteNum to set
     */
    public void setNoteNum(int noteNum) {
        this.noteNum = noteNum;
    }

    /**
     * @return the noteFeeTotal
     */
    public int getNoteFeeTotal() {
        return noteFeeTotal;
    }

    /**
     * @param noteFeeTotal the noteFeeTotal to set
     */
    public void setNoteFeeTotal(int noteFeeTotal) {
        this.noteFeeTotal = noteFeeTotal;
    }

   
    
}
