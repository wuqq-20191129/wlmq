/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.frame.util;

import com.goldsign.frame.constant.FrameCodeConstant;
import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.frame.vo.PubFlagVo;
import com.goldsign.frame.vo.User;
import com.goldsign.frame.vo.VersionVo;
import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.rule.vo.TransferStationVo;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Vector;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class FrameDBUtil {

    static private HashMap TABLE_FLAGS = new HashMap();
    static public HashMap RINIT_PARAM_FLAGS = new HashMap();
    static public HashMap RINIT_PARAM_FLAGS_WITH_TYPE = new HashMap();
    static public HashMap RINIT_PARAM_FLAGS_BY_CONDITION = new HashMap();
    static public HashMap RINIT_TABLE_FLAGS = new HashMap();
    static public HashMap PARAM_FLAGS_VILID_DATE = new HashMap();
    static public HashMap PARAM_FLAGS_WITH_TYPE_VILID_DATE = new HashMap();
    static public HashMap PARAM_FLAGS_BY_CONDITION_VILID_DATE = new HashMap();
    static public Vector PUB_FLAGS = new Vector();
    private static HashMap LOGIN_SESSIONS = new HashMap();
    private static Logger logger = Logger.getLogger(FrameDBUtil.class.getName());

    public VersionVo getCurrentVersionInfo(String paramTypeID) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        boolean result = false;
        ArrayList pStmtValues = new ArrayList();
        VersionVo versionInfo = new VersionVo();
        String beginTime = "";
        String endTime = "";

        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "select ver_num,ver_type,begin_time,end_time,parm_type_id,ver_date from para_ver where parm_type_id=? and ver_type='0'";
            pStmtValues.add(paramTypeID);
            result = dbHelper.getFirstDocument(strSql, pStmtValues.toArray());
            if (result) {
                versionInfo.setVersion_num(dbHelper.getItemValue("ver_num"));
                versionInfo.setVer_type(dbHelper.getItemValue("ver_type"));
                beginTime = dbHelper.getItemValue("begin_time");
                endTime = dbHelper.getItemValue("end_time");
                if (beginTime.length() > 10) {
                    beginTime = beginTime.substring(0, 10);
                }
                if (endTime.length() > 10) {
                    endTime = endTime.substring(0, 10);
                }

                versionInfo.setB_ava_time(beginTime);
                versionInfo.setE_ava_time(endTime);
                versionInfo.setParameter_type(dbHelper.getItemValue("parm_type_id"));
                versionInfo.setVer_date(dbHelper.getItemValue("ver_date"));
                return versionInfo;
            } else {
                return null;
            }
        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);

        }
        return null;
    }

    public boolean isNeedInitForParamTable(String paramTypeID, HashMap paramFlags, HashMap paramInitFlags, HashMap paramValidDates) {
        if (paramFlags.isEmpty()) {
            return true;
        }
        if (!paramFlags.containsKey(paramTypeID)) {
            return true;
        }
        if (!paramInitFlags.containsKey(paramTypeID)) {
            return true;
        }
        boolean result = ((Boolean) paramInitFlags.get(paramTypeID)).booleanValue();
        //System.out.println("缓存标志："+paramTypeID+"="+result);
        return result;
    }

    public Vector getParamTableFlags(String tableName, String codeColName, String textColName) throws Exception {

        DbHelper dbHelper = null;
        String strSql = null;
        boolean result = false;
        Vector tableFlags = new Vector();

        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "select " + codeColName + "," + textColName + " from " + tableName + " where record_flag=" + "'" + FrameCodeConstant.RECORD_FLAG_CURRENT + "'";
            result = dbHelper.getFirstDocument(strSql);

            while (result) {
                PubFlagVo pv = new PubFlagVo();
                pv.setCode(dbHelper.getItemValue(codeColName));
                pv.setCodeText(dbHelper.getItemValueIso(textColName));
                tableFlags.add(pv);
                result = dbHelper.getNextDocument();
            }
        } catch (NamingException e) {
            logger.error("错误:", e);
            throw e;
        } catch (SQLException e) {
            logger.error("错误:", e);
            throw e;
        } finally {
            try {
                if (dbHelper != null) {
                    dbHelper.closeConnection();
                }
            } catch (SQLException e) {
                logger.error("Fail to close connection", e);
            }
        }
        return tableFlags;

    }

    public Vector getParamTableFlags(String tableName, String strTypeColName, String codeColName, String textColName) throws Exception {

        DbHelper dbHelper = null;
        String strSql = null;
        boolean result = false;
        Vector tableFlags = new Vector();

        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "select " + strTypeColName + "," + codeColName + "," + textColName + " from " + tableName + " where record_flag=" + "'" + FrameCodeConstant.RECORD_FLAG_CURRENT + "'";;
            result = dbHelper.getFirstDocument(strSql);

            while (result) {
                PubFlagVo pv = new PubFlagVo();
                pv.setStrType(dbHelper.getItemValue(strTypeColName));

                pv.setCode(dbHelper.getItemValue(codeColName));
                pv.setCodeText(dbHelper.getItemValueIso(textColName));
                tableFlags.add(pv);
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);

        }
        return tableFlags;
    }
    
    public boolean isNeedInitForTable(String tableName, HashMap tableFlags, HashMap tableInitFlags) {
        if (tableFlags.isEmpty()) {
            return true;
        }
        if (!tableFlags.containsKey(tableName)) {
            return true;
        }
        if (!tableInitFlags.containsKey(tableName)) {
            return true;
        }
        Boolean rinit = (Boolean) tableInitFlags.get(tableName);
        return rinit.booleanValue();
    }

    public Vector getTableFlags(String tableName, String codeColName, String textColName) throws Exception {

        DbHelper dbHelper = null;
        String strSql = null;
        boolean result = false;
        Vector tableFlags = new Vector();

        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "select " + codeColName + "," + textColName + " from " + tableName;
            result = dbHelper.getFirstDocument(strSql);

            while (result) {
                PubFlagVo pv = new PubFlagVo();
                pv.setCode(dbHelper.getItemValue(codeColName));
                pv.setCodeText(dbHelper.getItemValueIso(textColName));
                tableFlags.add(pv);
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);

        }

        return tableFlags;
    }

    public Vector getPubFlagsByTypeForYear(int type, Vector pubFlags) {
        Vector typePubFlags = new Vector();
        String curYear = this.getBalanceYearMonth(FrameCodeConstant.FLAG_YEAR);
        return this.getPubFlagsByTypeForDefault(type, pubFlags, curYear);
    }

    public String getBalanceYearMonth(int flag) {
        GregorianCalendar current = new GregorianCalendar();
        if (flag == FrameCodeConstant.FLAG_YEAR || flag == FrameCodeConstant.FLAG_MONTH) {
            //if(current.get(GregorianCalendar.MONTH )==0)
            //	current.add(GregorianCalendar.YEAR,-1);
            current.add(GregorianCalendar.MONTH, -1);
        }
        int year = current.get(GregorianCalendar.YEAR);
        int month = current.get(GregorianCalendar.MONTH) + 1;
        int day = current.get(GregorianCalendar.DAY_OF_MONTH);
        int week = current.get(GregorianCalendar.WEEK_OF_MONTH);
        String sYear = Integer.toString(year);
        String sMonth = Integer.toString(month);
        String sDay = Integer.toString(day);
        String sDate = sYear + "-" + sMonth + "-" + sDay;
        String sWeek = FrameUtil.GbkToIso("第" + week + "周");
//	    System.out.println("sDate="+sDate+" sWeek="+week);

        if (sMonth.length() < 2) {
            sMonth = "0" + sMonth;
        }
        if (sDay.length() < 2) {
            sDay = "0" + sDay;
        }
        sDate = sYear + "-" + sMonth + "-" + sDay;

        if (flag == FrameCodeConstant.FLAG_YEAR) {
            return sYear;
        }
        if (flag == FrameCodeConstant.FLAG_MONTH) {
            return sMonth;
        }
        if (flag == FrameCodeConstant.FLAG_DATE) {
            return sDate;
        }
        if (flag == FrameCodeConstant.FLAG_WEEK) {
            return sWeek;
        }

        return sDate;
    }

    //取状态值
    public Vector getPubFlags() throws Exception {
//        if (!this.PUB_FLAGS.isEmpty()) {
//            return this.PUB_FLAGS;
//        }
        DbHelper dbHelper = null;
        String strSql = null;
        boolean result = false;
        Vector pubFlags = new Vector();

        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "select type_code,code,value,description from sr_params_sys where record_flag='0' order by code";
            result = dbHelper.getFirstDocument(strSql);

            while (result) {
                PubFlagVo pv = new PubFlagVo();
                pv.setStrType(dbHelper.getItemValue("type_code"));
                pv.setType(dbHelper.getItemIntValue("type_code"));
                pv.setCode(dbHelper.getItemValue("code"));
                pv.setCodeText(dbHelper.getItemValueIso("value"));
                pv.setDescription(dbHelper.getItemValueIso("description"));

                pubFlags.add(pv);
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);

        }
        this.PUB_FLAGS.clear();
        this.PUB_FLAGS.addAll(pubFlags);
        return pubFlags;

    }
    
    public Vector getPubFlagsByTypeForMonth(int type, Vector pubFlags) {
        String curMonth = this.getBalanceYearMonth(FrameCodeConstant.FLAG_MONTH);
        return this.getPubFlagsByTypeForDefault(type, pubFlags, curMonth);

    }

    public Vector getPubFlagsByTypeForDefault(int type, Vector pubFlags, String curDefValue) {
        Vector typePubFlags = new Vector();
        String defaultValue = "";
        for (int i = 0; i < pubFlags.size(); i++) {
            PubFlagVo pv = (PubFlagVo) pubFlags.get(i);
            if (pv.gettype() != type) {
                continue;
            }
            defaultValue = pv.getCode();
            pv.setIsDefaultValue(false);
            if (defaultValue != null) {
                if (curDefValue.equals(defaultValue)) {
                    pv.setIsDefaultValue(true);
                }
            }

            if (pv.gettype() == type) {
                typePubFlags.add(pv);
            }
        }
        return typePubFlags;

    }

    public Vector getPubFlagsByCardType(int type, Vector pubFlags) {
        Vector typePubFlags = new Vector();
        String code = "";
        int index = -1;
        for (int i = 0; i < pubFlags.size(); i++) {
            PubFlagVo pv = (PubFlagVo) pubFlags.get(i);
            if (pv.gettype() == type) {
                code = pv.getCode();
                index = code.indexOf("#");
                if (index != -1) {
                    pv.setStrType(code.substring(0, index));
                    pv.setCode(code.substring(index + 1));
                }
                typePubFlags.add(pv);
            }
        }
        return typePubFlags;

    }

    public Vector getPubFlagsByType(int type, Vector pubFlags) {
        Vector typePubFlags = new Vector();
        for (int i = 0; i < pubFlags.size(); i++) {
            PubFlagVo pv = (PubFlagVo) pubFlags.get(i);
            if (pv.gettype() == type) {
                typePubFlags.add(pv);
            }
        }
        return typePubFlags;

    }

    public Vector getPubFlagsByModule(String moduleID, Vector pubFlags) {
        Vector typePubFlags = new Vector();
        String code = null;
        int index = -1;

        for (int i = 0; i < pubFlags.size(); i++) {
            PubFlagVo pv = (PubFlagVo) pubFlags.get(i);

            code = pv.getCode();
            if (code.endsWith(moduleID)) {
                PubFlagVo pv1 = new PubFlagVo();
                code = code.substring(0, code.length() - moduleID.length() - 1);
                pv1.setCode(code);
                pv1.setCodeText(pv.getCodeText());
                pv1.setDescription(pv.getdescription());
                pv1.setType(pv.gettype());
                typePubFlags.add(pv1);
            }
        }
        return typePubFlags;

    }

    public String getCurrentDateBeforeOne() {
        long curInMill = System.currentTimeMillis();
        curInMill = curInMill - 24 * 3600 * 1000;

        Date cur = new Date(curInMill);
        return cur.toString();

    }

    public String getLastDateBeforeOneMonth() {
        GregorianCalendar gc = new GregorianCalendar();
        gc.add(gc.MONTH, -1);
        int day = gc.getActualMaximum(gc.DAY_OF_MONTH);
        int year = gc.get(gc.YEAR);
        int month = gc.get(gc.MONTH) + 1;
        String sMonth = Integer.toString(month);
        String sDay = Integer.toString(day);
        String sYear = Integer.toString(year);
        if (month < 10) {
            sMonth = "0" + sMonth;
        }
        if (day < 10) {
            sDay = "0" + sDay;
        }
        return sYear + "-" + sMonth + "-" + sDay;




    }

    public String getCurrentYearMonthDate(int flag) {
        GregorianCalendar current = new GregorianCalendar();
        int year = current.get(GregorianCalendar.YEAR);
        int month = current.get(GregorianCalendar.MONTH) + 1;
        int day = current.get(GregorianCalendar.DAY_OF_MONTH);
        int week = current.get(GregorianCalendar.WEEK_OF_MONTH);
        String sYear = Integer.toString(year);
        String sMonth = Integer.toString(month);
        String sDay = Integer.toString(day);
        String sDate = sYear + "-" + sMonth + "-" + sDay;
        String sWeek = FrameUtil.GbkToIso("第" + week + "周");

//    System.out.println("sDate="+sDate+" sWeek="+week);

        if (sMonth.length() < 2) {
            sMonth = "0" + sMonth;
        }
        if (sDay.length() < 2) {
            sDay = "0" + sDay;
        }
        sDate = sYear + "-" + sMonth + "-" + sDay;

        if (flag == FrameCodeConstant.FLAG_YEAR) {
            return sYear;
        }
        if (flag == FrameCodeConstant.FLAG_MONTH) {
            return sMonth;
        }
        if (flag == FrameCodeConstant.FLAG_DATE) {
            return sDate;
        }
        if (flag == FrameCodeConstant.FLAG_WEEK) {
            return sWeek;
        }

        return sDate;

    }

    public void removeUserSession(String sessionID, HttpServletRequest req, HttpServletResponse resp) {
        synchronized (LOGIN_SESSIONS) {
            if (this.LOGIN_SESSIONS.isEmpty()) {
                return;
            }
            Object ob = this.LOGIN_SESSIONS.get(sessionID);
            if (ob == null) {
                if (this.LOGIN_SESSIONS.containsKey(sessionID)) {
                    this.LOGIN_SESSIONS.remove(sessionID);
                }
                return;
            }
            HttpSession ses = (HttpSession) ob;
            try {
                ses.invalidate();
                this.LOGIN_SESSIONS.remove(sessionID);
                //   new AuthenticateFilter().inValidateAuthenticateCookie(req,resp);
            } catch (Exception e) {
                //e.printStackTrace();
                //System.out.println("会话" + sessionID + "已失效");
            }
        }

    }

    public void addUserSession(HttpSession ses) {
        synchronized (this.LOGIN_SESSIONS) {
            this.LOGIN_SESSIONS.put(ses.getId(), ses);
        }
    }

    public String getSequenceByauto(String tableName, String colName, int max) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        boolean result = false;
        Vector sequences = new Vector();
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "select " + colName + " from " + tableName + " order by " + colName;
            result = dbHelper.getFirstDocument(strSql);
            String sequence = null;

            while (result) {
                sequence = dbHelper.getItemValue(colName);
                sequences.add(sequence);
                result = dbHelper.getNextDocument();
            }

        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);

        }

        return this.getSequence(sequences, max);

    }

    public String getSequence(Vector sequences, int max) {
        String sequence = null;
        int nSeq = -1;
        int i = -1;
        for (i = 1; i <= max; i++) {
            for (int j = 0; j < sequences.size(); j++) {
                sequence = (String) sequences.get(j);
                nSeq = new Integer(sequence).intValue();
                if (i == nSeq) {
                    break;
                }
                if (nSeq > i) {
                    return this.paddingNum(i, max);
                }
            }
        }
        return this.paddingNum(i, max);
    }

    public String paddingNum(int n, int max) {
        int len = Integer.toString(max).length();
        String sn = Integer.toString(n);
        int len1 = sn.length();
        for (int i = 0; i < len - len1; i++) {
            sn = "0" + sn;
        }

        return sn;

    }

    public String getTextByCode(String code, Vector tableFlags) throws Exception {
        PubFlagVo pv = null;
        for (int i = 0; i < tableFlags.size(); i++) {
            pv = (PubFlagVo) tableFlags.get(i);
            if (pv.getCode().equals(code)) {
                return pv.getCodeText();
            }
        }
        return code;

    }

    public String getTextByCode(String code, String strType, Vector tableFlags) throws Exception {
        PubFlagVo pv = null;
        for (int i = 0; i < tableFlags.size(); i++) {
            pv = (PubFlagVo) tableFlags.get(i);
            if (pv.getCode().equals(code) && pv.getStrType().equals(strType)) {
                return pv.getCodeText();
            }
        }
        return code;

    }

    public String getTextByCode(String code, String strType, Vector tableFlags, Vector tableFlags_1) throws Exception {
        PubFlagVo pv = null;
        for (int i = 0; i < tableFlags.size(); i++) {
            pv = (PubFlagVo) tableFlags.get(i);
            if (pv.getCode().equals(code) && pv.getStrType().equals(strType)) {
                return pv.getCodeText();
            }
        }
        System.out.println("id " + FrameUtil.IsoToGbk(strType) + ":" + code);
        for (int i = 0; i < tableFlags_1.size(); i++) {
            pv = (PubFlagVo) tableFlags_1.get(i);
            System.out.println("tableFlags_1 " + pv.getStrType() + ":" + pv.getCode());
            if (pv.getCode().equals(code) && pv.getStrType().equals(strType)) {
                return pv.getCodeText();
            }
        }
        return code;

    }
    
    public String logLoginInfo(HttpServletRequest request, String operatorID) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        boolean result = false;
        String flowID = "";

        ArrayList pStmtValues = new ArrayList();
        //Timestamp current = new Timestamp(System.currentTimeMillis());
        //  Date current = new Date();
        String cur = new SimpleDateFormat("yyyy-MM-dd hh：mm：ss").format(new Date());
        String ip = request.getRemoteAddr();
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            //SELECT T.flow_id, T.sys_operator_id, T.login_time, T.logout_time, T.remark FROM dbo.op_log_user_access T
            dbHelper.setAutoCommit(false);

            strSql = "insert into op_log_user_access(flow_id,sys_operator_id,login_time,remark) values(?,?,to_date(?,'YYYY-MM-DD HH24:MI:SS'),?) ";
            flowID = new Integer(FrameDBUtil.getTableSequence("seq_op_log_user_access", dbHelper)).toString();
            pStmtValues.add(new Integer(flowID));
            pStmtValues.add(operatorID);
            pStmtValues.add(cur);
            pStmtValues.add(ip);
            dbHelper.executeUpdate(strSql, pStmtValues.toArray());


            dbHelper.commitTran();
            dbHelper.setAutoCommit(true);

        } catch (Exception e) {
            FramePubUtil.handleExceptionForTran(e, logger,dbHelper);
        } finally {
            FramePubUtil.finalProcessForTran(dbHelper);

        }
        return flowID;
    }

    public static HashMap getConfigPropertiesFromDb(HttpServletRequest req, String type) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        boolean result = false;
        String appRoot = req.getSession().getServletContext().getRealPath("/");
        Object[] values = {type};
        HashMap properties = new HashMap();
        String key = null;
        String value = null;


        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "select type_sub,config_name,config_value from op_cfg_sys where type=? order by to_number(type_sub)";
            result = dbHelper.getFirstDocument(strSql, values);
            logger.info("*********系统配置数据********************");
            while (result) {
                key = dbHelper.getItemValue("config_name");
                value = FrameDBUtil.getValue(appRoot, dbHelper.getItemValue("config_value"));
                properties.put(key, value);
                logger.info(key + "=" + value);
                result = dbHelper.getNextDocument();
            }
            logger.info("***************************************");


        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);
        }
        return properties;


    }

    private static String getValue(String appRoot, String value) {
        if (value == null || value.length() == 0) {
            return "";
        }
        if (value.startsWith("${ROOT}")) {
            value = appRoot + value.substring(7);
        }
        return value.trim();

    }

    public static int getTableSequence(String seqName, DbHelper dbHelper) throws Exception {
        String strSql = "select " + seqName + ".nextval from dual";
        dbHelper.getFirstDocument(strSql);
        return dbHelper.getItemIntValue("nextval");


    }

    public int logLogoutInfo(String flowID) throws Exception {
        if (flowID == null || flowID.trim().length() == 0) {
            return -1;
        }
        DbHelper dbHelper = null;
        String strSql = null;
        int result = -1;
        ArrayList pStmtValues = new ArrayList();
        //  Timestamp current = new Timestamp(System.currentTimeMillis());
        String cur = new SimpleDateFormat("yyyy-MM-dd hh：mm：ss").format(new Date());

        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            //SELECT T.flow_id, T.sys_operator_id, T.login_time, T.logout_time, T.remark FROM dbo.operator_log T
            strSql = "update op_log_user_access set logout_time=to_date(?,'YYYY-MM-DD HH24:MI:SS'） where flow_id=?";
            pStmtValues.add(cur);
            pStmtValues.add(new Long(flowID));
            dbHelper.executeUpdate(strSql, pStmtValues.toArray());
        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);

        }
        return result;
    }

    public int logoutHandle(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (req.getSession() == null) {
            return -1;
        }

        User user = (User) req.getSession().getAttribute("User");
        if (user == null) {
            return -1;
        }
        String operatorID = user.getAccount();
        DbHelper dbHelper = null;
        String strSql = null;
        ArrayList pStmtValues = new ArrayList();
        int result = 0;
        pStmtValues.add(operatorID);
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "update op_sys_operator set login_num=0,failed_num=0,session_id='' " + "where sys_operator_id=?";
            result = dbHelper.executeUpdate(strSql, pStmtValues.toArray());
            user.setLoginNum(0);
            this.removeUserSession(user.getSessionID(), req, resp);
            user.setSessionID("");
            user.setFailedNum(0);
        
        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);

        }
        return result;
    }
    
    public Vector getTableFlagsByCondition(String tableName, String codeColName, String textColName, String whereCond) throws Exception {
        String key = tableName + whereCond;
        if (!this.isNeedInitForTable(key, this.TABLE_FLAGS, this.RINIT_TABLE_FLAGS)) {
            return (Vector) this.TABLE_FLAGS.get(key);
        }
        DbHelper dbHelper = null;
        String strSql = null;
        boolean result = false;
        Vector tableFlags = new Vector();
        Logger logger = Logger.getLogger(PubFlagVo.class);
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "select " + codeColName + "," + textColName + " from " + tableName + " " + whereCond;
            result = dbHelper.getFirstDocument(strSql);

            while (result) {
                PubFlagVo pv = new PubFlagVo();
                pv.setCode(dbHelper.getItemValue(codeColName));
                pv.setCodeText(dbHelper.getItemValueIso(textColName));
                tableFlags.add(pv);
                result = dbHelper.getNextDocument();
            }
        } catch (NamingException e) {
            logger.error("错误:", e);
            throw e;
        } catch (SQLException e) {
            logger.error("错误:", e);
            throw e;
        } finally {
            try {
                if (dbHelper != null) {
                    dbHelper.closeConnection();
                }
            } catch (SQLException e) {
                logger.error("Fail to close connection", e);
            }
        }

        this.TABLE_FLAGS.put(key, tableFlags);
        this.RINIT_TABLE_FLAGS.put(key, new Boolean(false));
        return tableFlags;

    }
    
    
    /**
     * 取里程权重比例阀值
     * @return
     * @throws Exception 
     */
    public double getProportionThres() throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        boolean result = false;
        double threshold = 0;
        
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "select distance_thres from sr_params_threshold where record_flag='0' and rownum=1";
            result = dbHelper.getFirstDocument(strSql);

            if (result) {
                threshold = dbHelper.getItemDoubleValue("distance_thres");
            }
        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);

        }

        return threshold;
    }
    
    
    /**
     * 取换乘站
     * @return
     * @throws Exception 
     */
    public static Vector getTransferStation() throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        boolean result = false;
        Vector v = new Vector();
        
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "select line_id, station_id, transfer_line_id, transfer_station_id from op_prm_transfer_station where record_flag='0'";
            result = dbHelper.getFirstDocument(strSql);

            while(result) {
                TransferStationVo vo = new TransferStationVo();
                vo.setLineId(dbHelper.getItemValue("line_id"));
                vo.setStationId(dbHelper.getItemValue("station_id"));
                vo.setTransferLineId(dbHelper.getItemValue("transfer_line_id"));
                vo.setTransferStationId(dbHelper.getItemValue("transfer_station_id"));
                
                v.add(vo);
                
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);
        }

        return v;
    }
    
    /**
     * 取换乘站
     * @return
     * @throws Exception 
     */
    public static int getMaxId(String tableName, String recordFlag) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        boolean result = false;
        int maxId = 1;
        
        try {
            dbHelper = new DbHelper(FrameDBConstant.MAIN_DATASOURCE);
            strSql = "select nvl(max(id),0)+1 max_id from " + tableName + " where 1=1 ";
            if(FrameUtil.stringIsNotEmpty(recordFlag)){
                strSql += " and record_flag='" + recordFlag + "'"; 
            }
            result = dbHelper.getFirstDocument(strSql);

            if (result) {
                maxId = dbHelper.getItemIntValue("max_id");
            }
        } catch (Exception e) {
            FramePubUtil.handleException(e, logger);
        } finally {
            FramePubUtil.finalProcess(dbHelper);
        }

        return maxId;
    }
}
