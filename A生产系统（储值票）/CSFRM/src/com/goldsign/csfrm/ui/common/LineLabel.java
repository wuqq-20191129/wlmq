/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.csfrm.ui.common;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JLabel;

/**
 *
 * @author lenovo
 */
public class LineLabel extends JLabel {

    public LineLabel() {
        super();
    }

    public LineLabel(String str) {
        super(str);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int y = getHeight() - 1;
        g.setColor(new Color(156, 154, 140));
        g.drawLine(0, y, getWidth(), y);
    }
}
