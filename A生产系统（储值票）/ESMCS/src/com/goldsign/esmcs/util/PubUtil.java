/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.esmcs.util;

import com.goldsign.csfrm.env.BaseConstant;
import com.goldsign.csfrm.util.MessageShowUtil;
import com.goldsign.esmcs.dao.IUtilDao;
import com.goldsign.esmcs.dao.impl.UtilDao;
import com.goldsign.lib.db.util.DbHelper;
import java.sql.SQLException;
import java.util.Map;
import java.util.Vector;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;

/**
 *
 * @author limj
 */
public class PubUtil {
    private static Logger logger = Logger.getLogger(PubUtil.class.getName());
    private static IUtilDao iUtilDao;
    
       public PubUtil() {
        super();
        iUtilDao = new UtilDao();
        // TODO Auto-generated constructor stub
    }
    
    public static void handleDBNotConnected(Exception e,Logger lg){
        lg.error("错误：",e);
        MessageShowUtil.errorOpMsg("网络异常，请联系管理员，点击确认按钮后，系统3秒内退出！");
        JOptionPane.showMessageDialog(BaseConstant.mainFrame,"网络异常，请联系管理员，点击确认按钮后，系统3秒内退出！");
        try{
            Thread.sleep(3000);
        }catch(InterruptedException ex){            
        }
        System.exit(0);
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
    
    public static void finalProcess(DbHelper dbHelper){
        try{
            if(dbHelper != null){
                dbHelper.closeConnection();
            }
        }catch(SQLException e){
            logger.error("Fail to close connection",e);
        }
    }
    
    /**
     * 判断Map中Key对应Value是否为空，空返回Key,不为空返回Value
     * @param map
     * @param key
     * @return 
     */
    public static String getMapString(Map map,String key){
        if(key!=null && !key.equals("")&&map.containsKey(key)){
            return String.valueOf(map.get(key));
        }else{
            return key;
        }
    }
    
    /**
       *@param tableName 表名
       * @return
    */
    public  static Vector getTablePubFlag(String tableName){
        return iUtilDao.getTablePubFlag(tableName);
    }
    
    /** 
     * @param tableName 表名
     * @param columnA 列1
     * @param columnB 列2
     * @param cColumnA 条件列1
     * @param cValue 条件列1值
     * @return
    */
    public static Map getTableColumn(String tableName, String columnA, String colmunB, String cColumnA, String cValue){
        return iUtilDao.getTableColumn(tableName, columnA, colmunB, cColumnA, cValue);
    } 
}
