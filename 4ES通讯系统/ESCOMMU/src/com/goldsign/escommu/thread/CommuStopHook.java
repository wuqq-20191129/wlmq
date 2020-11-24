package com.goldsign.escommu.thread;

import com.goldsign.escommu.Main;
import org.apache.log4j.Logger;

public class CommuStopHook extends Thread {

    private Main app;

    public CommuStopHook(Main app) {
        this.app = app;
    }
    private static Logger logger = Logger.getLogger(CommuStopHook.class.getName());

    public void run() {
        logger.info("捕获通讯服务器停止事件!");
        app.stop();
    }
}
