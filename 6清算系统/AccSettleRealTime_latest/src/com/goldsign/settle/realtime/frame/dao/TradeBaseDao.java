/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.dao;

import com.goldsign.settle.realtime.frame.io.FileWriterBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordTacBase;
import java.math.BigDecimal;

/**
 *
 * @author hejj
 */
public abstract class TradeBaseDao {
    public final static String CLASS_PREX="com.goldsign.settle.realtime.app.dao.Trade";
    public final static String CLASS_Suffix="Dao";
    
    public abstract int insertError(FileRecordTacBase frb) throws Exception;
    public abstract int insertError(FileRecordBase frb,String errCode) throws Exception ;
     protected BigDecimal convertFenToYuan(int fen) {
        BigDecimal yan = new BigDecimal(fen);
        yan.setScale(2);
        BigDecimal result = yan.divide(new BigDecimal(100));
        return result;
    }
    
    
    
    
}
