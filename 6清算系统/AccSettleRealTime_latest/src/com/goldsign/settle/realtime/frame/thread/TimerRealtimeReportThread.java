/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.thread;

import com.goldsign.settle.realtime.frame.dao.TimerDao;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class TimerRealtimeReportThread extends TimerBaseThread{
    private static Logger logger = Logger.getLogger(TimerRealtimeReportThread.class.
            getName());

    public void run() {
        Vector v;

        while (true) {
            try {
                v = this.getNeedRunTimerConfigs();
                if (!v.isEmpty()) {
                    this.timerActions(v);

                }

                this.threadSleep();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }

    private Vector getNeedRunTimerConfigs() throws Exception {
        TimerDao dao = new TimerDao();
        Vector v = dao.getTimerConfigForRealtimeReport();
        if (v.isEmpty()) {
            return new Vector();
        }
        v = this.getRunItems(v);

        return v;
    }
    
}
