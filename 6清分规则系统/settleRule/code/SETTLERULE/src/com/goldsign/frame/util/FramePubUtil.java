/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.frame.util;

import com.goldsign.frame.vo.TableValue;
import com.goldsign.lib.db.util.DbHelper;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class FramePubUtil {

    private static Logger logger = Logger.getLogger(FramePubUtil.class);

    public static void handleException(Exception e, Logger lg) throws Exception {
        e.printStackTrace();
        lg.error("错误:", e);
        throw e;
    }

    public static void handleExceptionNoThrow(Exception e, Logger lg)
             {
        e.printStackTrace();
        lg.error("错误:", e);

    }

    public static void handleExceptionForTran(Exception e, Logger lg,
            DbHelper dbHelper) throws Exception {
        if (dbHelper != null) {
            dbHelper.rollbackTran();
        }
        e.printStackTrace();
        lg.error("错误:", e);
        throw e;
    }

    public static void finalProcess(DbHelper dbHelper) {
        try {
            if (dbHelper != null) {
                dbHelper.closeConnection();
            }
        } catch (SQLException e) {
            logger.error("Fail to close connection", e);
        }
    }

    public static void finalProcessForTran(DbHelper dbHelper) {
        try {
            /*
             * if (dbHelper != null && !dbHelper.isConClosed() &&
             * !dbHelper.getAutoCommit()) dbHelper.setAutoCommit(true);
             */
            if (dbHelper != null) {
                dbHelper.closeConnection();
            }

        } catch (SQLException e) {
            logger.error("Fail to close connection", e);
        }
    }
    
    /**
     * 取表单数据
     * @param queryControlDefaultValues
     * @return 
     */
    public static HashMap getQueryControlDefaultValues(
            String queryControlDefaultValues) {
        HashMap qv = new HashMap();
        if (queryControlDefaultValues == null
                || queryControlDefaultValues.trim().length() == 0) {
            return qv;
        }
        StringTokenizer st = new StringTokenizer(queryControlDefaultValues, ";");
        String token = "";
        String name = "";
        String value = "";
        int index = -1;
        while (st.hasMoreTokens()) {
            token = st.nextToken();
            index = token.indexOf("#");
            if (index == -1) {
                continue;
            }
            name = token.substring(0, index);
            value = token.substring(index + 1);
            if(value.equals("null") || value == null){
                value = "";
            }
            qv.put(name, value);

        }
        return qv;
    }
    
    /**
     * <p> description:根据条件生成查询的where条件 </p>
     * @param fields 字段名称
     * @param values 字段可能取值
     * @param operators 操作符
     * @return
     */
    public static String formCondition(String[] fields, Object[] values,
            String[] operators) {
        int fLen = fields.length;
        int vLen = values.length;
        int oLen = operators.length;
        if (fLen != vLen || fLen != oLen) {
            return "";
        }
        String field;
        Object value;
        String sValue;
        String oper;
        String condition = "";
        String comp = "";

        for (int i = 0; i < fLen; i++) {
            field = fields[i];
            value = values[i];
            oper = operators[i];
            if (!isValideValue(value)) {
                continue;
            }
            sValue = getValue(value);
            if (sValue == null || sValue.length() == 0) {
                continue;
            }
            comp = field + " " + oper + " " + sValue;

            condition += " " + comp + " and";

        }
        if (condition.length() != 0) {
            condition = " where "
                    + condition.substring(0, condition.length() - 3);
        }
        return condition;
    }
    
    private static boolean isValideValue(Object value) {
        if (value == null) {
            return false;
        }
        if (value instanceof java.lang.String) {
            if (((String) value).trim().length() == 0) {
                return false;
            }
        }

        return true;
    }

    private static String getValue(Object value) {
        SimpleDateFormat f;
        if (value == null) {
            return "";
        }
        if (value instanceof java.lang.String) {
            return getValueForStr(FrameUtil.GbkToIso((String) value));
        }
        if (value instanceof java.lang.Integer) {
            return "" + value;
        }
        if (value instanceof BigDecimal) {
            return "" + value;
        }
        if (value instanceof java.util.Date) {
            f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return "'" + f.format((Date) value) + "'";
        }
        if (value instanceof TableValue) {

            return ((TableValue) value).getValue();
        }
        return "";

    }
    
    private static String getValueForStr(String value) {
        int index = -1;
        index = value.indexOf(";");
        if (index == -1) {
            return "'" + value + "'";
        }
        StringTokenizer st = new StringTokenizer(value, ";");
        String token;
        String newValue = "";
        while (st.hasMoreElements()) {
            newValue += "'" + st.nextElement() + "'" + ",";
        }
        newValue = "(" + newValue.substring(0, newValue.length() - 1) + ")";
        return newValue;

    }
    
    /**
     * 拼接where and条件SQL
     * @param valueName
     * @param colName
     * @return 
     */
    public static String sqlWhereAnd(String valueName, String colName){
        if(FrameUtil.stringIsNotEmpty(valueName)){
                return " and "+colName+" = '" + valueName + "'";
        }
        return "";
    }
}
