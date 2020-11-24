package com.goldsign.etmcs.util;

import com.goldsign.csfrm.env.BaseConstant;
import com.goldsign.csfrm.util.MessageShowUtil;
import com.goldsign.etmcs.dao.IUtilDao;
import com.goldsign.etmcs.dao.impl.UtilDao;
import static com.goldsign.etmcs.util.CurrentConnectionStatusUtil.setDatabaseStatus;
import static com.goldsign.etmcs.util.CurrentConnectionStatusUtil.setESCommuStatus;
import static com.goldsign.etmcs.util.CurrentConnectionStatusUtil.setReaderPortStatus;
import com.goldsign.lib.db.util.DbHelper;
import java.sql.SQLException;
import java.util.Map;
import java.util.Vector;
import org.apache.log4j.Logger;
import javax.swing.JOptionPane;

public class PubUtil {

    private static Logger logger = Logger.getLogger(PubUtil.class.getName());
    private static IUtilDao iUtilDao; 

    public PubUtil() {
        super();
        iUtilDao = new UtilDao();
        // TODO Auto-generated constructor stub
    }

    public static void handleException(Exception e, Logger lg) throws Exception {
        lg.error("错误:", e);
        MessageShowUtil.errorOpMsg( "网络异常，请联系管理员，点击确认按钮后，系统3秒后自动退出！");
        JOptionPane.showMessageDialog(BaseConstant.mainFrame, "网络异常，请联系管理员，点击确认按钮后，系统3秒后自动退出！");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
        }
        System.exit(0);
        throw e;
    }
    
    public static void handleExceptionNoThrow(Exception e, Logger lg) {
        
        lg.error("错误:", e);
        MessageShowUtil.errorOpMsg( "网络异常，请联系管理员，点击确认按钮后，系统3秒后自动退出！");
        JOptionPane.showMessageDialog(BaseConstant.mainFrame, "网络异常，请联系管理员，点击确认按钮后，系统3秒后自动退出！");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
        }
        System.exit(0);
    }
    //数据库连接失败处理
    public static void handleDBNotConnected(Exception e, Logger lg) {
        lg.error("错误:", e);
        setDatabaseStatus(false);
        MessageShowUtil.errorOpMsg( "网络异常，请联系管理员，点击确认按钮后，系统3秒后自动退出！");
        JOptionPane.showMessageDialog(BaseConstant.mainFrame, "网络异常，请联系管理员，点击确认按钮后，系统3秒后自动退出！");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
        }
        System.exit(0);
    }
    //读写器连接失败处理
    public static void handleRWNotConnected(Logger lg) {
        setReaderPortStatus(false);
        MessageShowUtil.errorOpMsg( "读写器连接失败，请联系管理员，点击确认按钮后，系统3秒后自动退出！");
        JOptionPane.showMessageDialog(BaseConstant.mainFrame, "读写器连接失败，请联系管理员，点击确认按钮后，系统3秒后自动退出！");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
        }
        System.exit(0);
    }
    //ES通讯连接失败处理
    public static void handleESNotConnected(Exception e, Logger lg) {
        lg.error("错误:", e);
        setESCommuStatus(false);
        MessageShowUtil.errorOpMsg( "ES通讯连接失败，请联系管理员，点击确认按钮后，系统3秒后自动退出！");
        JOptionPane.showMessageDialog(BaseConstant.mainFrame, "ES通讯连接失败，请联系管理员，点击确认按钮后，系统3秒后自动退出！");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
        }
        System.exit(0);
    }
    public static void handleExceptionForTran(Exception e, Logger lg,
            DbHelper dbHelper) throws Exception {
        if (dbHelper != null) {
            dbHelper.rollbackTran();
        }
        lg.error("错误:", e);
        throw e;
    }

    public static void handleExceptionForTranNoThrow(Exception e, Logger lg,
            DbHelper dbHelper) {

        try {
            if (dbHelper != null) {
                dbHelper.rollbackTran();
            }
        } catch (SQLException ex) {
            lg.error("错误:", ex);
        }
        lg.error("错误:", e);
        //throw e;
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
     * 判断Map中Key对应Value是否为空，空返回Key,不为空返回Value
     * @param map
     * @param key
     * @return 
     */
    public static String getMapString(Map map,String key){
        if(key!=null && !key.equals("") && map.containsKey(key)){
            return String.valueOf(map.get(key));
        }else{
            return key;
        }
    }
    
    /**
     * 
     * @param tableName 表名
     * @return 
     */
    public static Vector getTablePubFlag(String tableName){
        return iUtilDao.getTablePubFlag(tableName);
    } 
    
     /**
     * 
     * @param tableName 表名
     * @param columnA 列1
     * @param colmunB 列2
     * @param cColumnA 条件列1
     * @param cValue 条件列1值
     * @return 
     */
    public static Map getTableColumn(String tableName, String columnA, String colmunB, String cColumnA, String cValue){
        return iUtilDao.getTableColumn(tableName, columnA, colmunB, cColumnA, cValue);
    } 

}
