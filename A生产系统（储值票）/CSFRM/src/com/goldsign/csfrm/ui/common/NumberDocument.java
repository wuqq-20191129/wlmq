/*
 * NumberDocument.java
 *
 * Created on 2007年6月20日, 下午3:07
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.goldsign.csfrm.ui.common;

import java.awt.Toolkit;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author wang
 */
public class NumberDocument extends PlainDocument {

    boolean isStr = false; //是否为字符窜数字，默认为数字
    boolean canNegative = false;//是否可以为负数
    int maxLength = 16;
    int decLength = 0;
    double minRange = -Double.MAX_VALUE;
    double maxRange = Double.MAX_VALUE;

    /**
     *
     * @param maxLen 最大长度(整数部分+小数部分)
     * @param decLen 小数部分最大长度
     */
    public NumberDocument(int maxLen, int decLen) {
        maxLength = maxLen;
        decLength = decLen;

    }

    /**
     *
     * @param maxLen 最大长度(整数部分+小数部分)
     * @param decLen 小数部分最大长度
     * @param canNegative 是否可为负数
     */
    public NumberDocument(int maxLen, int decLen, boolean canNegative) {
        maxLength = maxLen;
        decLength = decLen;
        this.canNegative = canNegative;

    }

    /**
     * @param decLen int 小数部分最大长度
     * @param maxLen int 最大长度(整数部分+小数部分)
     * @param minRange double 最小值
     * @param maxRange double 最大值
     */
    public NumberDocument(int maxLen,
            int decLen,
            double minRange,
            double maxRange) {
        this(maxLen, decLen);
        this.minRange = minRange;
        this.maxRange = maxRange;

    }

    /**
     *
     * @param maxLen 最大长度(整数部分+小数部分)
     * @param decLen 小数部分最大长度
     * @param minRange 最小值
     * @param maxRange 最大值
     * @param canNegative 是否可为负数
     */
    public NumberDocument(int maxLen,
            int decLen,
            double minRange,
            double maxRange, boolean canNegative) {
        this(maxLen, decLen);
        this.minRange = minRange;
        this.maxRange = maxRange;
        this.canNegative = canNegative;

    }

    /**
     *
     * 正整数 不可以输入01,000
     *
     * @param maxLen 最大长度(整数部分+小数部分)
     */
    public NumberDocument(int maxLen) {
        maxLength = maxLen;
    }

    /**
     *
     * 字符串不能为负数 负数不能为字符串 如果flag为true则创建一个可以为负的整数 否则创建一个正整数字符串 就是可以输入 001,0000000
     *
     * @param maxLen 最大长度
     * @param flag 标志位
     */
    public NumberDocument(int maxLen, boolean flag) {
        maxLength = maxLen;
        if (flag) {
            canNegative = true;
            isStr = false;
        } else {
            canNegative = false;
            isStr = true;
        }

    }

    /**
     *
     * 默认情况,长度16位的正整数
     */
    public NumberDocument() {
    }

    /**
     * @param offset 当前光标位置
     * @param s 当前字符
     * @param a
     */
    public void insertString(int offset, String s, AttributeSet a) throws
            BadLocationException {
        String str = getText(0, getLength());

        if ((!s.equals(".") && !s.equals("-") && (s.compareTo("0") < 0 || s.compareTo("9") > 0))
                || (!canNegative && s.equals("-"))
                //符号只能在第一个
                || (s.equals("-") && offset != 0)
                //不能连续负号
                || (offset == 0 && s.equals("-") && str.indexOf("-") >= 0)
                //字符串不能包括小数点
                || (isStr && s.equals("."))
                //整数模式不能输入小数点
                || (s.equals(".") && (offset == 0 || decLength == 0))
                //非字符串第一位是0时,第二位只能为小数点
                || (!isStr && offset == 1 && !s.equals(".") && str.substring(0, 1).equals("0"))
                || (s.equals(".") && (str.substring(0, offset).indexOf(".") > 0 || str.substring(offset).indexOf(".") > 0))) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        /*
         * if ( //不能为f,F,d,D s.equals("F") || s.equals("f") || s.equals("D") ||
         * s.equals("d") //第一位是0时,第二位只能为小数点 || (str.trim().equals("0") &&
         * !s.substring(0, 1).equals(".") && offset != 0) //整数模式不能输入小数点 ||
         * (s.equals(".") && decLength == 0) ) {
         * Toolkit.getDefaultToolkit().beep(); return;
        }
         */
        String strIntPart = "";
        String strDecPart = "";
        String strNew = str.substring(0, offset) + s + str.substring(offset, getLength());
        strNew = strNew.replaceFirst("-", ""); //控制能输入负数
        int decPos = strNew.indexOf(".");
        if (decPos > -1) {
            strIntPart = strNew.substring(0, decPos);
            strDecPart = strNew.substring(decPos + 1);
        } else {
            strIntPart = strNew;
        }
        if (strIntPart.length() > (maxLength - decLength)
                || strDecPart.length() > decLength
                || (!isStr
                && strNew.length() > 1
                && strNew.substring(0, 1).equals("0")
                && !strNew.substring(1, 2).equals("."))) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }

        try {
            if (!strNew.equals("") && !strNew.equals("-")) {//控制能输入负数
                double d = Double.parseDouble(strNew);
                if (d < minRange || d > maxRange) {
                    throw new Exception();
                }
            }
        } catch (Exception e) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        super.insertString(offset, s, a);
    }
}
