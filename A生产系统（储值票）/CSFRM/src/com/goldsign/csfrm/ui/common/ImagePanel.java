/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.csfrm.ui.common;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

/**
 *
 * @author david.wang
 */
public class ImagePanel extends JPanel{
    private Image image;
    public ImagePanel(){

    }

    public ImagePanel(Image image){ //首先构建一个构造方法.传入的参数是Image的文件路径
       this.image=image;
    }
    protected void paintComponent(Graphics g) {
        if (null != image) {
            processBackground(g);
        }
    }

    public void setBackground(Image image) {
        this.image = image;
        this.repaint();
    }

    private void processBackground(Graphics g) {
        
        int cw = getWidth();
        int ch = getHeight();
        int iw = image.getWidth(this);
        int ih = image.getHeight(this);
        int x = 0;
        int y = 0;
        while (y <= ch) {
            g.drawImage(image, x, y, this);
            x += iw;
            if (x >= cw) {
                x = 0;
                y += ih;
            }
        }
    }

}
