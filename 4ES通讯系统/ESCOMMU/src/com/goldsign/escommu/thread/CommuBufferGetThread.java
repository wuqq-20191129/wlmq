/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.escommu.thread;

import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.dao.InitiDao;
import com.goldsign.escommu.util.DateHelper;
import com.goldsign.escommu.vo.CurrentDate;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class CommuBufferGetThread extends Thread {

    private static Logger logger = Logger.getLogger(CommuBufferGetThread.class.getName());
    private CurrentDate curDate = new CurrentDate();

    public CommuBufferGetThread() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * 运行
     * 
     */
    public void run() {
        Vector runtime = null;

        while (true) {
            try {
                //每天获取一次运行时间
                if (this.isNewDate(this.curDate)) {//是否新的一天，每天在固定的时间点上仅运行一次
                    logger.info("新的一天:"+this.curDate.getCurDate());
                    runtime = this.getRuntime();//获取每天运行的时间点
                        
                }
                //支持多个运行时间点
                //当前时间大于运行时间点，运行完后删除当前运行时间点，如果有第二个时间点，在第二个时间点后再运行
                if (this.isTimeToRun(runtime)){
                    logger.info("开始更新缓存----------时间点："+DateHelper.curentDateToHHMM());
                     this.getBuffer();//获取缓存
                }

                this.threadSleep();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 休息
     * 
     */
    private void threadSleep() {

        try {
            sleep(AppConstant.BUFFERGET_SLEEPTIME);
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * 取得运行时间点
     * 
     * @return 
     */
    private Vector getRuntime() {
        Vector v = new Vector();
        String[] rts = AppConstant.BUFFERGET_RUN_TIME.split("#");
        v.addAll(Arrays.asList(rts));
        DateHelper.screenPrintForEx("运行时间点："+v);
        return v;
    }

    /**
     * 是否到了运行时间
     * 
     * @param runtime
     * @return 
     */
    public boolean isTimeToRun(Vector runtime) {

        String currentHour = DateHelper.curentDateToHHMM();
        String tmp;
        for (int i = 0; i < runtime.size(); i++) {
            tmp = (String) runtime.get(i);
            if (currentHour.compareTo(tmp) >= 0) {
                runtime.remove(i);

                return true;
            }
        }

        return false;
    }

    /**
     * 取得缓存
     * 
     * @throws Exception 
     */
    private void getBuffer() throws Exception {
        InitiDao dao = new InitiDao();
        AppConstant.lines=dao.getLines();
        logger.info("更新线路缓存信息...长度："+AppConstant.lines.size());
        AppConstant.stations=dao.getStations();
        logger.info("更新站点缓存信息...长度："+AppConstant.stations.size());
        AppConstant.cards=dao.getCardSubType();
        logger.info("更新票种缓存信息...长度："+AppConstant.cards.size());
        AppConstant.devIps=dao.getDevices();
        logger.info("更新设备缓存信息...长度："+AppConstant.devIps.size());
    }

    /**
     * 是否新的一天
     * 
     * @param curDate
     * @return 
     */
    private boolean isNewDate(CurrentDate curDate) {
        if (curDate.getCurDate() == null || curDate.getCurDate().length() == 0) {
            curDate.setCurDate(DateHelper.datetimeToYYYYMMDD(new Date()));
            return true;
        }
        String tmp = DateHelper.datetimeToYYYYMMDD(new Date());
        
        if (tmp.equals(curDate.getCurDate())) {
            return false;
        }

        curDate.setCurDate(tmp);
        return true;
    }
    
    public static void main(String[] args){
        Vector<String> tmps = new Vector<String>();
        String t = "a#b#c";
        String[] ts = t.split("#");
        tmps.addAll(Arrays.asList(ts));
        for(String tmp: tmps){
            System.out.println(tmp);
        }
    }
}
