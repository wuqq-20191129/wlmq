/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.ecpmcs.ui.panel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import javax.swing.JPanel;

/**
 *
 * @author lind
 */
    
public class ImagePanel extends JPanel{
    private Image image;
    private double rotate=0;

    public double getRotate() {
        return rotate;
    }

    public void setRotate(double rotate) {
        this.rotate = rotate;
    }
    
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
        int x = 0;
        int y = 0;
        
        g.drawImage(image, x, y, this);
        
//        Graphics2D g2=(Graphics2D) g;
//        
//        if(printerSet() != PageFormat.PORTRAIT){
//            int iw = image.getWidth(this);
//            int ih = image.getHeight(this);
//            g2.fillRect(0, 0, iw, ih);  
//            g2.rotate(rotate,(int)iw/2,(int)ih/2);
//        }
//        
//        g2.drawImage(image, x, y, this);
//        g2.dispose();  
        
    }
    
    /*
     * 打印机纵向或横向判断
     */
    private int printerSet(){
        PrinterJob pj = PrinterJob.getPrinterJob();
        PageFormat pf = pj.defaultPage();
        return pf.getOrientation();
    }
    
}
