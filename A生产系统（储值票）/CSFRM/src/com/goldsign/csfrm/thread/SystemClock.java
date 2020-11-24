package com.goldsign.csfrm.thread;

import com.goldsign.csfrm.env.BaseConstant;
import java.awt.Color;
import java.text.SimpleDateFormat;
import javax.swing.JLabel;
import org.apache.log4j.Logger;

/**
 *
 * @author admin
 * 
 * 时钟线程类
 * 
 */
public class SystemClock extends Thread {

    private static final Logger logger = Logger.getLogger(SystemClock.class.getName());
    
    private JLabel label;
    
    /**
     * Creates a new instance of SystemClock
     */
    public SystemClock(JLabel label) {

        this.label = label;
    }

    public void run() {
        while (true) {
            try {
                this.setSysDateTime();
                this.sleep(1000);
            } catch (Exception ex) {
                //this.interrupt();
                //logger.error(ex);
                break;
            }
        }
    }
    
    /**
     * 设置当前时间,格式yyyy-MM-dd HH:mm:ss
     */
    private void setSysDateTime(){

        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        label.setText(f.format(new java.util.Date()));
        //label.setForeground(BaseConstant.SYS_DFT_COLOR);
    }
}
