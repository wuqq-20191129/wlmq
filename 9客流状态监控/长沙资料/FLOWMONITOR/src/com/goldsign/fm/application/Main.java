/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.fm.application;

import com.goldsign.fm.bom.jni.KeyLock;
import com.goldsign.fm.common.AppUtil;
import com.goldsign.fm.frame.MainFrame;
import org.apache.log4j.Logger;


/**
 *
 * @author Administrator
 */
public class Main {
    private static Logger logger = Logger.getLogger(Main.class.getName());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AppUtil appUtil = new AppUtil();
        try {
            appUtil.getInitConfiguration(); //获取配置文件的配置
            logger.info("获取配置文件信息");
        } catch (Exception ex) {
            ex.printStackTrace();
           logger.error(ex);
           return;
        }
        KeyLock kl = new KeyLock();


        //测试暂时屏蔽
        kl.startKeyLock();

/*改在ICCSweb应用中作清理,业主要求
        MoveDataThread t = new MoveDataThread();
        t.start();
        logger.info("数据导历史表线程启动。");
*/
        MainFrame mf = new MainFrame();
        mf.setVisible(true);
        

    }

}
