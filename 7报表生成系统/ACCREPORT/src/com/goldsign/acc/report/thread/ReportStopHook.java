package com.goldsign.acc.report.thread;

import com.goldsign.acc.report.application.MainApp;
import org.apache.log4j.Logger;

import com.goldsign.acc.report.handler.ReportGenerator;

public class ReportStopHook extends Thread {

    private MainApp mainApp;
    private static Logger logger = Logger.getLogger(ReportStopHook.class.getName());

    public ReportStopHook(MainApp rg) {
        this.mainApp = rg;
    }

    public void run() {
        logger.info("Catch Report Generator stop event!");
        mainApp.stop();
    }
}
