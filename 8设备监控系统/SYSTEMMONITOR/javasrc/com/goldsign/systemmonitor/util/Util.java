package com.goldsign.systemmonitor.util;

import com.goldsign.frame.constant.FrameDBConstant;
import com.goldsign.frame.util.FrameCharUtil;
import com.goldsign.systemmonitor.vo.DbConfig;
import com.goldsign.systemmonitor.vo.SqlVo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;



public class Util {

    public Util() {
        super();
        // TODO Auto-generated constructor stub
    }

    public static void setFinished(boolean isFinished) {
        synchronized (FrameDBConstant.control) {
            FrameDBConstant.control.setFinished(isFinished);
        }
    }

    public static boolean isFinished() {
        synchronized (FrameDBConstant.control) {
            return FrameDBConstant.control.isFinished();
        }
    }

    public String getControlDefaultValues(HttpServletRequest request, String controlNamesParm) {
        String result = "";
        String controlNames = request.getParameter(controlNamesParm);
        if (controlNames == null || controlNames.length() == 0) {
            return "";
        }
        StringTokenizer st = new StringTokenizer(controlNames, "#");
        String name = "";
        String value = "";
        while (st.hasMoreTokens()) {
            name = st.nextToken();
            value = request.getParameter(name);
            result += name + "#" + value + ";";
        }
        if (result.length() != 0) {
            result = result.substring(0, result.length());
        }
        return result;
    }

    public String getControlDefaultValues(HttpServletRequest request) {
        String result = "";
        String controlNames = request.getParameter("ControlNames");
        if (controlNames == null || controlNames.length() == 0) {
            return "";
        }
        StringTokenizer st = new StringTokenizer(controlNames, "#");
        String name = "";
        String value = "";
        while (st.hasMoreTokens()) {
            name = st.nextToken();
            value = request.getParameter(name);
            if (value != null) {
                value = FrameCharUtil.ChineseToIso(value);
            }
            result += name + "#" + value + ";";
        }
        if (result.length() != 0) {
            result = result.substring(0, result.length());
        }
        return result;
    }

    public boolean isAddMode(HttpServletRequest request) {
        String precommand = request.getParameter("precommand");
        String preTwoCommand = request.getParameter("preTwoCommand");
        String command = request.getParameter("command");
        //System.out.println("command="+command+" prcommand="+precommand+" preTwoCommand="+preTwoCommand);
        if (command == null) {
            return false;
        }
        if (command.equals("add")) {
            return true;
        }
        if (precommand != null && precommand.equals("add") && command.equals("modify")) {
            return true;
        }
        if (precommand != null && precommand.equals("add") && command.equals("delete")) {
            return true;
        }
        if (preTwoCommand != null && preTwoCommand.equals("add") && precommand.equals("modify") && command.equals("delete")) {
            return true;
        }
        return false;


    }

    public HashMap getQueryControlDefaultValues(String queryControlDefaultValues) {
        HashMap qv = new HashMap();
        if (queryControlDefaultValues == null || queryControlDefaultValues.trim().length() == 0) {
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
            qv.put(name, value);

        }
        return qv;
    }

    public String getWhereParam(HashMap params, ArrayList pStmtValues) {
        if (params == null || params.isEmpty()) {
            return "";
        }
        String strOb = null;
        String whereParams = "where ";
        Set keys = params.keySet();
        Iterator it = keys.iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            Object ob = params.get(key);
            if (ob == null) {
                continue;
            }
            if (ob.getClass().getName().equals("java.lang.String")) {
                strOb = (String) ob;
                if (strOb.trim().length() == 0) {
                    continue;
                }
            }
            pStmtValues.add(ob);
            whereParams += key + "=?" + " and ";
        }
        if (whereParams.length() == 6) {
            return "";
        }
        whereParams = whereParams.substring(0, whereParams.length() - 5);
        return whereParams;
    }

    public String getQueryControlDefaultValue(HashMap vQueryControlDefaultValues, String name) {
        String value = (String) vQueryControlDefaultValues.get(name);
        if (value == null) {
            value = "";
        }
        return value;
    }

    public ActionMessage operationExceptionHandle(HttpServletRequest request, String command, Exception e) throws Exception {


        ActionMessage am = null;
        if (e.getMessage() != null) {
            am = new ActionMessage("operMessage", FrameCharUtil.GbkToIso(e.getMessage()));
        } else {
            am = new ActionMessage("operMessage", FrameCharUtil.GbkToIso("操作失败"));
        }
        //  System.out.println(CharUtil.GbkToIso(e.getMessage()));
        return am;
    }

    public void getIDs(String strIDs, Vector IDs) {
        StringTokenizer st = new StringTokenizer(strIDs, ";");
        String ID = null;
        while (st.hasMoreTokens()) {
            ID = st.nextToken();
            IDs.add(ID);
        }

    }

    public int getConfigIntValue(String configName, String type, HashMap configs) {
        if (!configs.containsKey(type)) {
            return -1;
        }
        Vector v = (Vector) configs.get(type);
        DbConfig config;
        for (int i = 0; i < v.size(); i++) {
            config = (DbConfig) v.get(i);
            if (config.getConfigName().equals(configName)) {
                return new Integer(config.getConfigValue()).intValue();
            }
        }
        return -1;
    }

    public String[] getConfigStringArrayValue(String value) {
        Vector v = new Vector();
        StringTokenizer st = new StringTokenizer(value, "#");
        String token;
        while (st.hasMoreTokens()) {
            token = st.nextToken();
            v.add(token);
        }
        String[] ar = new String[v.size()];
        for (int i = 0; i < v.size(); i++) {
            ar[i] = (String) v.get(i);
        }
        return ar;
    }

    public String getConfigStringValue(String configName, String type, HashMap configs) {
        if (!configs.containsKey(type)) {
            return "";
        }
        Vector v = (Vector) configs.get(type);
        DbConfig config;
        for (int i = 0; i < v.size(); i++) {
            config = (DbConfig) v.get(i);
            if (config.getConfigName().equals(configName)) {
                return config.getConfigValue();
            }
        }
        return "";
    }

    private boolean isValidValue(String value) {
        if (value == null || value.length() == 0) {
            return false;
        }
        return true;
    }

    public SqlVo getSqlForUpdate(String[] values, String[] fieldNames, String tableName, String[] valuesWhere, String[] fieldNamesWhere) {
        Vector v = new Vector();
        Vector v1 = new Vector();
        SqlVo vo = new SqlVo();
        for (int i = 0; i < values.length; i++) {
            if (this.isValidValue(values[i])) {
                v.add(fieldNames[i]);
                v1.add(values[i]);
            }
        }
        String sqlField = this.formSqlForUpdate(v);
        String where = this.fromSqlForWhere(fieldNamesWhere);
        String sql = "update " + tableName + " set " + sqlField + " " + where;
        vo.setValues(v1);
        vo.addValues(valuesWhere);
        vo.setSql(sql);

        return vo;

    }

    public SqlVo getSqlForInsert(String[] values, String[] fieldNames, String tableName) {
        Vector v = new Vector();
        Vector v1 = new Vector();
        SqlVo vo = new SqlVo();
        for (int i = 0; i < values.length; i++) {

            if (this.isValidValue(values[i])) {
                v.add(fieldNames[i]);
                v1.add(values[i]);

            }
        }
        String sqlField = this.formSqlForInsert(v);
        String sqlValue = this.formSqlForInsertValue(v);
        String sql = "insert into " + tableName + "(" + sqlField + ") " + " values(" + sqlValue + ")";
        vo.setSql(sql);
        vo.setValues(v1);


        return vo;
    }

    private String fromSqlForWhere(String[] fieldNamesWhere) {
        String where = " where ";
        for (int i = 0; i < fieldNamesWhere.length; i++) {
            where += fieldNamesWhere[i] + "=?,";
        }
        if (where.length() > 7) {
            where = where.substring(0, where.length() - 1);
        }
        return where;
    }

    private String formSqlForUpdate(Vector fieldNames) {
        String stmt = "";
        for (int i = 0; i < fieldNames.size(); i++) {
            stmt += (String) fieldNames.get(i) + "=?,";
        }
        if (stmt.length() != 0) {
            stmt = stmt.substring(0, stmt.length() - 1);
        }
        return stmt;
    }

    private String formSqlForInsert(Vector fieldNames) {
        String stmt = "";
        for (int i = 0; i < fieldNames.size(); i++) {
            stmt += (String) fieldNames.get(i) + ",";
        }
        if (stmt.length() != 0) {
            stmt = stmt.substring(0, stmt.length() - 1);
        }
        return stmt;
    }

    private String formSqlForInsertValue(Vector fieldNames) {
        String stmt = "";
        for (int i = 0; i < fieldNames.size(); i++) {
            stmt += "?,";
        }
        if (stmt.length() != 0) {
            stmt = stmt.substring(0, stmt.length() - 1);
        }

        return stmt;
    }
}
