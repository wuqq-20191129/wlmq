/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.fm.thread;

import com.goldsign.fm.common.DateHelper;
import com.goldsign.fm.dao.ConfigDao;
import com.goldsign.fm.dao.MoveDataDao;
import com.goldsign.fm.vo.CurrentDate;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import org.apache.log4j.Logger;



/**
 *
 * @author Administrator
 */
public class MoveDataThread extends Thread {

    private static Logger logger = Logger.getLogger(MoveDataThread.class.getName());
    public static final String NAME_RUNTIME = "runTime";
    public static final String NAME_THREAD_INTERVAL = "threadInterval";
    public static final String NAME_THREAD_INTERVAL_REFRESH = "threadIntervalRefresh";//线程运行时间刷新频率(秒)
    private CurrentDate curDate = new CurrentDate();

    public void run() {
        HashMap configs = this.getConfigs();
        Vector runtime = null;

        while (true) {
            try {
                if (this.isNewDate(curDate)) //是否新的一天，每天在固定的时间点上仅运行一次                
                    runtime = this.getRuntime(configs);//获取每天运行的时间点
                
                if (this.isTimeToRun(runtime)) 
                    this.moveDate();//数据导历史表
                
                
            } catch (Exception e) {
                e.printStackTrace();

            }
            finally{
                this.threadSleep(configs);
            }

        }
    }

    private void moveDate() {
        MoveDataDao dao = new MoveDataDao();
        try {
            dao.moveData();
        } catch (Exception ex) {
            logger.error(ex);
        }


        
    }

    private void threadSleep(HashMap configs) {
        String interval = (String) configs.get(NAME_THREAD_INTERVAL);
        try {
            Thread.sleep(Long.parseLong(interval));
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private boolean isTimeToRun(Vector runtime) {

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

    private Vector getRuntime(HashMap configs) {
        String runtime = (String) configs.get(NAME_RUNTIME);
        return this.getV(runtime);


    }

    private Vector getV(String str) {
        StringTokenizer st = new StringTokenizer(str, "#");
        Vector v = new Vector();
        String token;
        while (st.hasMoreTokens()) {
            token = st.nextToken();
            v.add(token);
        }
        return v;

    }

    private boolean isNewDate(CurrentDate curDate) {
        if (curDate.getCurDate() == null || curDate.getCurDate().length() == 0) {
            curDate.setCurDate(DateHelper.datetimeToStringOnlyDateF(new Date()));
            return true;
        }
        String tmp = DateHelper.datetimeToStringOnlyDateF(new Date());
        ;
        if (tmp.equals(curDate.getCurDate())) {
            return false;
        }

        curDate.setCurDate(tmp);
        return true;


    }

    private HashMap getConfigs() {

        HashMap configs = null;
        try {
            ConfigDao dao = new ConfigDao();
            configs = dao.getConfiguration();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return configs;
    }
    /**
     * 获取线程刷新频率（秒）
     * @return
     * @throws Exception 
     */
    public long getThreadIntervalRefresh() throws Exception{
        long refreshTime = 0;
        HashMap configs = this.getConfigs();
        try {
            refreshTime = Long.valueOf(String.valueOf(configs.get("threadIntervalRefresh")))*1000;
        }catch(NumberFormatException e){
            logger.error("未配置线程刷新频率，请联系管理员！");
            throw new Exception("未配置线程刷新频率，请联系管理员！");
        }
        return refreshTime;
    }
}
