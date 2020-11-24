/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.rule.util;

import com.goldsign.frame.util.FramePubUtil;
import com.goldsign.frame.util.FrameUtil;
import com.goldsign.frame.vo.User;
import com.goldsign.lib.db.util.DbHelper;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author Administrator
 */
public class Util {

    private static Logger logger = Logger.getLogger(
            Util.class.getName());
    //参数类型模块映射缓存
    private static HashMap PARAMTYPE_MODUE_MAPPING = new HashMap();
    public static Thread QUEUE_RECEIVER_THREAD = null;

    public ActionMessage operationExceptionHandle(HttpServletRequest request, String command, Exception e) throws Exception {
        ActionMessage am = null;
        if (e.getMessage() != null) {
            am = new ActionMessage("operMessage", FrameUtil.GbkToIso(e.getMessage()));
        } else {
            am = new ActionMessage("operMessage", FrameUtil.GbkToIso("操作失败"));
        }
        return am;
    }

    public static ArrayList str2ArrayBySeparator(String str, String separator) {
        if (separator.equals("")) {
            throw new NullPointerException("The separator can not be null!");
        }

        ArrayList list = new ArrayList();
        if (!str.equals("")) {
            StringBuffer buffer = new StringBuffer(str);
            while (buffer.length() > 0) {
                int pos = buffer.indexOf(separator);
                if (pos > 0) {
                    list.add(buffer.substring(0, pos));
                    buffer.delete(0, pos + 1);
                } else {
                    list.add(buffer.toString());
                    break;
                }
            }
        }

        return list;
    }

    public boolean isAddMode(HttpServletRequest request) {
        String precommand = request.getParameter("precommand");
        String preTwoCommand = request.getParameter("preTwoCommand");
        String command = request.getParameter("command");
        if (command == null) {
            return false;
        }
        if (command.equals("add")) {
            return true;
        }
        if (command.equals("modify")) {
            return true;
        }
        if (command.equals("audit")) {
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

    public String getQueryControlDefaultValue(HashMap vQueryControlDefaultValues, String name) {
        String value = (String) vQueryControlDefaultValues.get(name);
        if (value == null) {
            value = "";
        }
        return value;
    }

    //插入日志
    public void logOperation(String command, HttpServletRequest request, String opResult) throws Exception {
        String operatorID = ((User) request.getSession().getAttribute("User")).getAccount();
        String moduleID = null;
        moduleID = request.getParameter("ModuleID");
        if (command != null) {
            command = command.trim();
            if (command.equals("modify")) {
                AFCLogger.logger(operatorID, moduleID, AFCLogger.OP_MODIFY, opResult);
                return;
            }
            if (command.equals("delete")) {
                AFCLogger.logger(operatorID, moduleID, AFCLogger.OP_DELETE, opResult);
                return;
            }
            if (command.equals("add")) {
                AFCLogger.logger(operatorID, moduleID, AFCLogger.OP_ADD, opResult);
                return;
            }
            if (command.equals("query")) {
                //           ICCSLogger.logger(operatorID,moduleID,ICCSLogger.OP_QUERY,opResult);
                return;
            }
            if (command.equals("reportQuery")) {
                //            ICCSLogger.logger(operatorID,moduleID,ICCSLogger.OP_REPORTQUERY,opResult);
                return;
            }
            if (command.equals("statistic")) {
                //            ICCSLogger.logger(operatorID,moduleID,ICCSLogger.OP_STATISTIC,opResult);
                return;
            }

            if (command.equals("submit")) {
                AFCLogger.logger(operatorID, moduleID, AFCLogger.OP_SUBMIT, opResult);
                return;
            }
            if (command.equals("clone")) {
                AFCLogger.logger(operatorID, moduleID, AFCLogger.OP_CLONE, opResult);
                return;
            }
            if (command.equals("download")) {
                AFCLogger.logger(operatorID, moduleID, AFCLogger.OP_DOWNLOAD, opResult);
                return;
            }

            if (command.equals("distribute")) {
                AFCLogger.logger(operatorID, moduleID, AFCLogger.OP_DISTRIBUTE, opResult);
                return;
            }
            if (command.equals("save")) {     //盘点
                AFCLogger.logger(operatorID, moduleID, AFCLogger.OP_CHECK, opResult);
                return;
            }
            if (command.equals("import")) {       //导入
                AFCLogger.logger(operatorID, moduleID, AFCLogger.OP_IMPORT, opResult);
                return;
            }
            if (command.equals("update")) {    //确认退款
                AFCLogger.logger(operatorID, moduleID, AFCLogger.OP_UPDATE, opResult);
                return;
            }
            if (command.equals("hmd")) {   //拒绝退款
                AFCLogger.logger(operatorID, moduleID, AFCLogger.OP_HMD, opResult);
                return;
            }
            if (command.equals("audit")) {   //审核
                AFCLogger.logger(operatorID, moduleID, AFCLogger.OP_AUDIT, opResult);
                return;
            }
        }
    }

    public HashMap getParamTypeModueMapping(String fileName, HttpServletRequest request) throws Exception {
        if (!this.PARAMTYPE_MODUE_MAPPING.isEmpty()) {
            return this.PARAMTYPE_MODUE_MAPPING;
        }
        this.PARAMTYPE_MODUE_MAPPING.putAll(this.getConfigPropertiesByAppPath(request, fileName));

        return this.PARAMTYPE_MODUE_MAPPING;
    }

    public HashMap getConfigPropertiesByAppPath(HttpServletRequest req, String configFile) throws Exception {
        String appRoot = req.getSession().getServletContext().getRealPath("/");
        String fileName = appRoot + "/properties/" + configFile;
        //      InputStream in =this.getClass().getResourceAsStream(configFile);
        FileInputStream fis = new FileInputStream(fileName);
        String line = null;
        int index = -1;
        String key = null;
        String value = null;
        HashMap properties = new HashMap();
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            isr = new InputStreamReader(fis, "GBK");
            br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) {
                    continue;
                }
                index = line.indexOf("=");
                if (index == -1) {
                    continue;
                }
                key = line.substring(0, index);
                value = line.substring(index + 1);
                if (value.startsWith("${ROOT}")) {
                    value = req.getSession().getServletContext().getRealPath("/") + value.substring(7);
                }
                value = value.trim();
                properties.put(key, value);

            }

        } catch (Exception e) {
            //     e.printStackTrace();
            throw e;
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (isr != null);
                isr.close();
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return properties;
    }

    public boolean isParamOperation(String moduleID, HashMap paramModueMapping) {
        if (paramModueMapping.containsKey(moduleID)) {
            return true;
        }
        return false;
    }

    public String convertSingleKeyToStr(Vector keyIDs, String delim) {
        String keys = "";
        for (int i = 0; i < keyIDs.size(); i++) {
            keys += (String) keyIDs.get(i) + delim;
        }
        return keys;
    }
    
    /**
     * 查询是否存在子单
     * @param keyIDs
     * @param dbHelper
     * @param tableName
     * @param billNoName
     * @throws Exception 
     */
    public void hasRecordInDetail(Vector keyIDs, DbHelper dbHelper,
            String tableName, String billNoName) throws Exception {
        Util util = new Util();
        String keys = util.convertSingleKeyToStr(keyIDs, ";");
        String[] fields = {billNoName};
        Object[] values = {keys};
        String[] operators = {"in"};
        String sql = "select count(*) num from " + tableName + " ";
        String where = FramePubUtil.formCondition(fields, values, operators);
        boolean result = false;
        sql = sql + where;
        result = dbHelper.getFirstDocument(sql);
        if (!result) {
            return;
        }
        String count = dbHelper.getItemValue("num");
        if (count == null || count.length() == 0
                || Integer.parseInt(count) == 0) {
            return;
        }
        throw new Exception("明细表" + "还存在" + count + "条相关明细,请先删除明细.");
    }
    
    /**
     * @param HttpServletRequest request 请求对象
     * @return String 当前登陆操作员
     */
    public static String getCurrentOperator(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("User");
        String operatorID = user.getAccount();
        return operatorID;
    }
    
    //计算公式
    /**
     * （里程 - 最短里程）/最短里程 ？？ 比例阀值
     * @param distance 里程
     * @param minDistance 最短里程
     * @param threshold 比例阀值
     * @return  
     */
    public static boolean isValidDistance(String distance, String minDistance, double threshold){
        //最短里程为0时判断
        if(Double.valueOf(minDistance).equals(Double.valueOf(0))){
            return Double.valueOf(distance).equals(Double.valueOf(0))?true:false;
        }
        
        return (Double.valueOf(distance) - Double.valueOf(minDistance)) / Double.valueOf(minDistance) <= threshold;
    
    }
    
}
