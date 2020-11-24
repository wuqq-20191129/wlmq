package com.goldsign.commu.commu.thread;

import com.goldsign.commu.commu.application.BaseApplication;
import org.apache.log4j.Logger;

public class CommuStopHook extends Thread {

    private static Logger logger = Logger.getLogger(CommuStopHook.class.getName());
    
    private BaseApplication app;

    public CommuStopHook(BaseApplication app) {
        this.app = app;
    }
   
    public void run() {
        logger.info("捕获通讯服务器停止事件!");
        app.stop();
    }
}
