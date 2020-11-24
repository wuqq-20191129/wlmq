/*
 * TopBottomLineBorder.java
 *
 * Created on 2007年7月24日, 下午11:31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.goldsign.csfrm.ui.common.refer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.border.AbstractBorder;

/**
 *
 * @author
 */
public class TopBottomLineBorder extends AbstractBorder {

    private Color lineColor;

    public TopBottomLineBorder(Color color) {
        lineColor = color;
    }

    public void paintBorder(Component c, Graphics g, int x, int y,
            int width, int height) {
        g.setColor(lineColor);
        g.drawLine(0, 0, c.getWidth(), 0);
        g.drawLine(0, c.getHeight() - 1, c.getWidth(),
                c.getHeight() - 1);
    }
}
