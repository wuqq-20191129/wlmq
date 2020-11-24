/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.dao;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
import com.goldsign.settle.realtime.frame.constant.FrameTimerConstant;
import com.goldsign.settle.realtime.frame.util.PubUtil;
import com.goldsign.settle.realtime.frame.vo.ConfigBcpVo;
import com.goldsign.settle.realtime.frame.vo.TimerConfigVo;
import com.goldsign.settle.realtime.frame.vo.TimerValueTypeVo;
import java.util.StringTokenizer;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class TimerDao {

    private static Logger logger = Logger.getLogger(TimerDao.class.getName());

    public Vector<TimerConfigVo> getTimerConfig() throws Exception {
        Vector<TimerConfigVo> configs = new Vector();
        String sql = "select timer_id,run_year,run_month,run_date,run_hour,run_minute,control_flag,remark "
                + "from "+FrameDBConstant.DB_PRE+"st_sys_cfg_timer  where control_flag=? order by  timer_id";
        DbHelper dbHelper = null;
        boolean result;
        String[] values = {FrameTimerConstant.CONTROL_RUN};
        TimerConfigVo vo = null;
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql,values);
            while (result) {
                vo = this.getResultRecord(dbHelper);
                configs.add(vo);
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return configs;
    }

    private TimerConfigVo getResultRecord(DbHelper dbHelper) throws Exception {
        TimerConfigVo vo = new TimerConfigVo();
        vo.setYears(this.getTime(dbHelper.getItemValue("run_year"), FrameTimerConstant.TIME_TYPE_YEAR));
        vo.setMonths(this.getTime(dbHelper.getItemValue("run_month"), FrameTimerConstant.TIME_TYPE_MONTH));
        vo.setDays(this.getTime(dbHelper.getItemValue("run_date"), FrameTimerConstant.TIME_TYPE_DAY));
        vo.setHours(this.getTime(dbHelper.getItemValue("run_hour"), FrameTimerConstant.TIME_TYPE_HOUR));
        vo.setMins(this.getTime(dbHelper.getItemValue("run_minute"), FrameTimerConstant.TIME_TYPE_MIN));
        vo.setTimerId(dbHelper.getItemValue("timer_id"));
        vo.setControlFlag(dbHelper.getItemValue("control_flag"));
        vo.setRemark(dbHelper.getItemValue("remark"));



        return vo;

    }

    private Vector getTime(String time, String timeType) {
        Vector v = new Vector();
        TimerValueTypeVo vo;
        StringTokenizer st = new StringTokenizer(time, "#");
        String timeValue, timeValueType;
        while (st.hasMoreTokens()) {
            timeValue = st.nextToken();
            timeValueType = this.getTimeValueType(timeValue);
            vo = this.getTimeValue(timeType, timeValueType, timeValue);
            v.add(vo);

        }

        return v;
    }

    private String getTimeValueType(String timeValue) {
        timeValue = timeValue.trim();
        if (timeValue.equals(FrameTimerConstant.TIME_VALUE_All)) {
            return FrameTimerConstant.TIME_VALUE_TYPE_All;
        }
        if (timeValue.indexOf(FrameTimerConstant.TIME_VALUE_RANGE) != -1) {
            return FrameTimerConstant.TIME_VALUE_TYPE_RANGE;
        }
        return FrameTimerConstant.TIME_VALUE_TYPE_SIGLE;
    }

    private TimerValueTypeVo getTimeValue(String timeType, String valueType, String timeValue) {
        TimerValueTypeVo vo = new TimerValueTypeVo();
        vo.setValueType(valueType);
        vo.setTimeType(timeType);
        if (valueType.equals(FrameTimerConstant.TIME_VALUE_TYPE_All)) {
            return vo;
        }

        if (valueType.equals(FrameTimerConstant.TIME_VALUE_TYPE_RANGE)) {
            int index = timeValue.indexOf(FrameTimerConstant.TIME_VALUE_TYPE_RANGE);
            int min = Integer.parseInt(timeValue.substring(0, index));
            int max = Integer.parseInt(timeValue.substring(index + 1));
            vo.setValueMin(min);
            vo.setValueMax(max);
            return vo;
        }

        if (valueType.equals(FrameTimerConstant.TIME_VALUE_TYPE_SIGLE)) {
            int value = Integer.parseInt(timeValue);
            vo.setValueSinge(value);
            return vo;
        }
        return vo;

    }
     public Vector<TimerConfigVo> getTimerConfigForClear() throws Exception {
        Vector<TimerConfigVo> configs = new Vector();
        String sql = "select timer_id,run_year,run_month,run_date,run_hour,run_minute,control_flag,remark "
                + "from "+FrameDBConstant.DB_ST+"w_st_sys_cfg_timer_clear where control_flag=? ";
        DbHelper dbHelper = null;
        boolean result;
        String[] values = {FrameTimerConstant.CONTROL_RUN};
        TimerConfigVo vo = null;
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql,values);
            while (result) {
                vo = this.getResultRecord(dbHelper);
                configs.add(vo);
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return configs;
    }
     public Vector<TimerConfigVo> getTimerConfigForRealtimeReport() throws Exception {
        Vector<TimerConfigVo> configs = new Vector();
        String sql = "select timer_id,run_year,run_month,run_date,run_hour,run_minute,control_flag,remark "
                + "from "+FrameDBConstant.DB_ST+"w_st_sys_cfg_timer_rltime_rpt where control_flag=? ";
        DbHelper dbHelper = null;
        boolean result;
        String[] values = {FrameTimerConstant.CONTROL_RUN};
        TimerConfigVo vo = null;
        try {
            dbHelper = new DbHelper("", FrameDBConstant.DATA_DBCPHELPER.getConnection());
            result = dbHelper.getFirstDocument(sql,values);
            while (result) {
                vo = this.getResultRecord(dbHelper);
                configs.add(vo);
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);
        }

        return configs;
    }
}
