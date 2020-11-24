/*
 * PostiveIntegerDocument.java
 *
 * Created on 2008年10月26日, 下午9:47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.goldsign.csfrm.ui.common;

import com.goldsign.csfrm.env.BaseConstant;
import java.awt.Toolkit;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author david.wang
 */
public class PostiveIntegerDocument extends PlainDocument {

    private int len;
    private int maxValue;

    /**
     * Creates a new instance of PostiveIntegerDocument
     */
    public PostiveIntegerDocument() {
    }

    public PostiveIntegerDocument(int len, int maxValue) {
        this.len = len;
        this.maxValue = maxValue;
    }

    public void insertString(int offset, String s, AttributeSet a) throws
            BadLocationException {
        char c = s.charAt(0);
        String txt = "";
        System.out.println("s=" + s + "txt=" + txt + " len=" + this.getLength());
        int iTxt;
        if (!InputUtil.isDigit(c)) {
            this.prompMsg();
            return;
        }
        if (this.getLength() > this.len - 1) {
            return;
        }


        super.insertString(offset, s, a);
        txt = this.getText(0, this.getLength());

        iTxt = Integer.parseInt(txt);
        if (iTxt > this.maxValue) {
            this.prompMsg();
            return;
        }


        this.prompMsgForNormal();

    }

    private void prompMsg() {
        Toolkit.getDefaultToolkit().beep();
    }

    private void prompMsgForNormal() {
        BaseConstant.publicPanel.setOpResult("");
    }
}
