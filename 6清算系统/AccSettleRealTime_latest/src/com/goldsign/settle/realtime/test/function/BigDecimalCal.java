/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.test.function;

import java.math.BigDecimal;

/**
 *
 * @author hejj
 */
public class BigDecimalCal {
    public static void main(String[] args){
        BigDecimal m = new BigDecimal("0.00");
        BigDecimal m1 = new BigDecimal("2");
       // m.setScale(3);
        m = m.add(m1);
        System.out.println(m.toString());
        
        BigDecimalCal bc =new BigDecimalCal();
        int fen=1;
        String yuan=bc.convertFenToYuan(fen);
        System.out.println("yan="+yuan);
        
    }
    public String convertFenToYuan(int fen) {
        BigDecimal yan = new BigDecimal(fen);
        yan.setScale(2);
        BigDecimal result = yan.divide(new BigDecimal(100));
        return result.toString();
    }
    
}
