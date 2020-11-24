/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.thread;

import com.goldsign.settle.realtime.frame.constant.FrameTimerConstant;
import com.goldsign.settle.realtime.frame.dao.TimerDao;
import com.goldsign.settle.realtime.frame.timer.ActionBase;
import com.goldsign.settle.realtime.frame.util.DateHelper;
import com.goldsign.settle.realtime.frame.vo.TimerConfigVo;
import com.goldsign.settle.realtime.frame.vo.TimerValueTypeVo;
import java.util.Date;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class TimerThread extends Thread {

    private static Logger logger = Logger.getLogger(TimerThread.class.
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

    private void timerActions(Vector v) {
        TimerConfigVo vo;

        for (int i = 0; i < v.size(); i++) {
            vo = (TimerConfigVo) v.get(i);
            this.timerAction(vo.getTimerId());

        }
    }

    private void timerAction(String timerId) {
        ActionBase act;
        try {
            act = (ActionBase) Class.forName(FrameTimerConstant.ActionClassPrefix + timerId).newInstance();
            act.action();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Vector getNeedRunTimerConfigs() throws Exception {
        TimerDao dao = new TimerDao();
        Vector v = dao.getTimerConfig();
        if (v.isEmpty()) {
            return new Vector();
        }
        v = this.getRunItems(v);

        return v;
    }
     private Vector getRunItems(Vector v) {
        TimerConfigVo vo;
        String cur = DateHelper.dateToString(new Date());
        Vector vRun = new Vector();
        for (int i = 0; i < v.size(); i++) {
            vo = (TimerConfigVo) v.get(i);
            if (this.isTimeToRun(cur, vo)) {
                vRun.add(vo);
            }

        }
        return vRun;
    }

    private void threadSleep() {

        try {
            this.sleep(FrameTimerConstant.THREAD_SLEEP_TIME);
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private boolean isTimeToRun(String cur, TimerConfigVo vo) {
        String year = cur.substring(0, 4);
        String month = cur.substring(4, 6);
        String day = cur.substring(6, 8);
        String hour = cur.substring(8, 10);
        String min = cur.substring(10, 12);
        String sec = cur.substring(12, 14);//add by hejj 20130625
        boolean isTimeForYear = this.isTimeToRunForCommon(year, vo.getYears());
        boolean isTimeForMon = this.isTimeToRunForCommon(month, vo.getMonths());
        boolean isTimeForDay = this.isTimeToRunForCommon(day, vo.getDays());
        boolean isTimeForHour = this.isTimeToRunForCommon(hour, vo.getHours());
        //boolean isTimeForMin = this.isTimeToRunForCommon(min, vo.getMins());
        boolean isTimeForMin = this.isTimeToRunForCommonMin(min, sec, vo.getMins());//moify by hejj 20130625 增加设置值下一分钟作为条件判断
        //logger.error("当前分钟是否运行判断："+isTimeForMin);
        if (isTimeForYear && isTimeForMon && isTimeForDay && isTimeForHour && isTimeForMin) {
            return true;
        }
        return false;

    }
    //add by hejj 20130625

    private boolean isTimeToRunForCommonMin(String time, String timeSec, Vector vTime) {
        TimerValueTypeVo vo;
        int itime = Integer.parseInt(time);
        int nextTime;//下一分钟
        int itimeSec = Integer.parseInt(timeSec);
        int overMax = (int) (FrameTimerConstant.THREAD_SLEEP_TIME / 1000 - 60);//判断下一分钟是否合法的最大秒数

        for (int i = 0; i < vTime.size(); i++) {
            vo = (TimerValueTypeVo) vTime.get(i);

            if (vo.getValueType().equals(FrameTimerConstant.TIME_VALUE_TYPE_SIGLE)) {
                //logger.error("定时任务的定时分钟："+vo.getValueSinge()+"  当前时间分钟："+itime);
                if (itime == vo.getValueSinge()) {
                    return true;
                } else {//考虑设定值的下一分是否可作为判断条件
                    nextTime = vo.getValueSinge() + 1;
                    if (nextTime == 59) {//59分的下一分为0
                        nextTime = 0;
                    }
                    //当前的分钟等于设定值的下一分钟，且当前的秒数为0-(时间间隔-60)之间时
                    //如：时间间隔为80秒，设定值为10分钟，当如当前时间为11分：05秒，则上一时间为09分：45秒
                  //  logger.error("定时任务的定时分钟："+vo.getValueSinge()+"  下一时间分钟："+nextTime+" 最大偏差允许秒数："+overMax);
                    if (itime == nextTime && itimeSec >= 0 && itimeSec < overMax) {
                        return true;
                    }

                }
            }

        }
        return false;

    }

    private boolean isTimeToRunForCommon(String time, Vector vTime) {
        TimerValueTypeVo vo;
        int itime = Integer.parseInt(time);
        for (int i = 0; i < vTime.size(); i++) {
            vo = (TimerValueTypeVo) vTime.get(i);
            if (vo.getValueType().equals(FrameTimerConstant.TIME_VALUE_TYPE_All)) {
                return true;
            }
            if (vo.getValueType().equals(FrameTimerConstant.TIME_VALUE_TYPE_RANGE)) {
                if (itime >= vo.getValueMin() && itime <= vo.getValueMax()) {
                    return true;
                }
            }
            if (vo.getValueType().equals(FrameTimerConstant.TIME_VALUE_TYPE_SIGLE)) {
                if (itime == vo.getValueSinge()) {
                    return true;
                }
            }
        }
        return false;

    }
}
