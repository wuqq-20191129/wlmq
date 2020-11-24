/*
 * LengthLimitDocument.java
 *
 * 限制输入框的最大输入长度
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
public class LengthLimitDocument extends PlainDocument {

    /**
     * 最大长度*
     */
    int maxLength = 0;
    /**
     * 是否限制输入内容*
     */
    boolean limitFlag = false;
    /**
     * 限制是否包括数字*
     */
    boolean numFlag = false;
    /**
     * 限制是否包括字母*
     */
    boolean chFlag = false;

    /**
     *
     * @param maxLen 最大长度
     *
     */
    public LengthLimitDocument(int maxLen) {
        this(maxLen, false, false);
    }

    public LengthLimitDocument(int maxLen, boolean numFlag, boolean chFlag) {
        this.maxLength = maxLen;
        this.numFlag = numFlag;
        this.chFlag = chFlag;
        this.limitFlag = numFlag || chFlag;
    }

    /**
     * @param offset 当前光标位置
     * @param s 当前字符
     * @param a
     */
    public void insertString(int offset, String s, AttributeSet a) throws
            BadLocationException {
        if (getLength() >= maxLength) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        if (s == null && s.length() == 0) {
            return;
        }
        char c = s.charAt(0);
        if (numFlag && !chFlag) {
            if (c < '0' || c > '9') {
                Toolkit.getDefaultToolkit().beep();
                return;
            }
        }
        if (!numFlag && chFlag) {
            if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z')) {
                Toolkit.getDefaultToolkit().beep();
                return;
            }
        }
        if (numFlag && chFlag) {
            if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z') && (c < '0' || c > '9')) {
                Toolkit.getDefaultToolkit().beep();
                return;
            }
        }
        super.insertString(offset, s, a);
    }
}
