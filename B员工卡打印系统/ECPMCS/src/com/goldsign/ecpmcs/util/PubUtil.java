package com.goldsign.ecpmcs.util;

import com.goldsign.csfrm.env.BaseConstant;
import com.goldsign.csfrm.service.impl.ConfigFileService;
import com.goldsign.csfrm.util.DateHelper;
import com.goldsign.csfrm.util.MessageShowUtil;
import com.goldsign.csfrm.vo.XmlTagVo;
import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.ecpmcs.dao.IUtilDao;
import com.goldsign.ecpmcs.dao.impl.UtilDao;
import com.goldsign.ecpmcs.env.AppConstant;
import com.goldsign.ecpmcs.env.ConfigConstant;
import static com.goldsign.ecpmcs.util.CurrentConnectionStatusUtil.setDatabaseStatus;
import java.awt.Color;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;


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
        throw e;
    }

    public static void handleExceptionNoThrow(Exception e, Logger lg) {
        lg.error("错误:", e);

    }

    public static void handleExceptionForTran(Exception e, Logger lg,
            DbHelper dbHelper) throws Exception {
        if (dbHelper != null) {
            dbHelper.rollbackTran();
        }
        lg.error("错误:", e);
        throw e;
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
            return String.valueOf(map.get(key.trim()));
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
    
    /**
     * 检验查询时间
     * @param beginOpTime
     * @param endOpTime
     * @return 
     */
    public static boolean checkQueryTime(String beginOpTime, String endOpTime) {
        
        boolean result = true;
        String message = "";
        
        Date beginTime = DateHelper.str8yyyyMMddToDate(beginOpTime);
        Date endTime = DateHelper.str8yyyyMMddToDate(endOpTime);
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(beginTime);
        calendar.add(Calendar.MONTH,3);
        Date after3MonthDay = calendar.getTime();
        if (after3MonthDay.before(endTime)) {
            message += "请选择操作日期段为三个月内！\n";
            result = false;
        }
        if(endTime.compareTo(beginTime) < 0 ){
            message += "结束日期须不小于开始日期！\n";
            result = false;
        }
        if(!result){
            MessageShowUtil.alertErrorMsg(message);
        };
        
        return result;
    }
    
    /**
     * 设置按钮（字体颜色）
     * @param button
     * @param b 
     */
    public static void setButton(JButton button, boolean b) {
        button.setEnabled(b);
        if(b){
            button.setForeground(new Color(0,39,80));
        }else{
            button.setForeground(Color.gray);
        }
    }
            
    /**
     * 设置按钮（字体颜色）
     * @param btWriteCard
     * @param b 
     */
    public static void setJCheckBox(JCheckBox checkBox, boolean b) {
        checkBox.setEnabled(b);
        if(b){
            checkBox.setForeground(new Color(0,39,80));
        }else{
            checkBox.setForeground(Color.gray);
        }
    }
    
    /**
     * 添加图片
     * @param label
     * @param imgDir 
     */
    public static void addImg(JLabel label, String imgDir) {
        ImageIcon icon = new ImageIcon(imgDir);
        int width = 0;
        int height = 0;
        Image img = icon.getImage();
        width = label.getWidth();
        height = label.getHeight();
        img = img.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        icon.setImage(img);

        label.setIcon(icon);
    }
    
    /**
     * 去空格
     * @param x 
     */
    public static String trim(String x){
        if(x == null){
            x = "";
        }else{
            x = x.trim();
        }
        return x;
    }
    
    /**
     * 读打印错误代码文件
     * @return 
     */
    public static boolean loadPrintErrFile(){
        Boolean result = false;
        File file = new File(ConfigConstant.P_ERROR_FILE);
        
        if (!file.exists()) {
            return result;
        }
        
        BufferedReader br = null;
        try {
            AppConstant.PRINT_ERROR.clear();
            br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            while(line != null){
                String[] data = line.split(AppConstant.WELL_SIGN, -1);
                if(data.length>0){
                    AppConstant.PRINT_ERROR.put(data[0], data[1]);
                }
                line = br.readLine();
            }
            result = true;
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(null != br){
                try {
                    br.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        return result;
    }
    
    /**
     * 查询打印模板文件
     */
    public static void loadPrintTemplate() {
        String suffix = ".txt";
        String path = System.getProperty("user.dir");
        path += "/" + ConfigConstant.PRINT_TEMPLATE_PATH;
        File dir = new File(path);
        String[] list = dir.list();
        for(int i = 0; i<list.length; i++){
            if(list[i].indexOf(suffix) > -1){
                List lines = readLine(ConfigConstant.PRINT_TEMPLATE_PATH + list[i]);
                String line = (String) lines.get(0);
                String[] data = line.split(AppConstant.WELL_SIGN, -1);
                String key = data[1];
                if(!(key.isEmpty() || key==null)){
                    AppConstant.PRINT_TEMPLATE.put(key, list[i]);
                }
            }
        }
    }
    
    
    /**
     * 按行读文件
     * @return 
     */
    public static List readLine(String fileName){
        List list = new ArrayList();
        File file = new File(fileName);
        
        if (!file.exists()) {
            return list;
        }
        
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            while(line != null){
                list.add(line);
                line = br.readLine();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(null != br){
                try {
                    br.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        return list;
    }
    
    
    /**
     * 加载配置文件
     * @return
     */
    public static Hashtable loadConfigFile(String fileName) {

        XmlTagVo[] tagVos = new XmlTagVo[]{new XmlTagVo(ConfigConstant.CommonTag)};
        try {
            return new ConfigFileService().loadConfigFile(fileName,tagVos);
        } catch (Exception ex) {
            logger.error(ex);
            return null;
        }
    }
    
    /**
     * 取得配置文件的值
     * @param oneLevelTag
     * @param twoLevelTag
     * @return 
     */
    public static String getConfigValue(String oneLevelTag, String twoLevelTag, Hashtable config){
        Hashtable oneLevelValues = (Hashtable) config.get(oneLevelTag);
        String twoLevelValue = (String) oneLevelValues.get(twoLevelTag);
        return twoLevelValue;
    }
    
    /**
     * 设置读写器通讯状态 状态栏
     * @param KMSStatus 
     */
    public static void setReaderPortStatus(boolean readerPort) {
        String[] vars = new String[]{AppConstant.STATUS_BAR_READER_PORT};
        AppConstant.READER_PORT = readerPort;
        boolean[] statuses = new boolean[]{AppConstant.READER_PORT};
        //更新连接状态栏
        setSBarStatus(vars,statuses);
    }
    
    /**
     * 设置状态栏状态
     * 正常、警告
     * @param vars
     * @param statuses 
     */
    public static void setSBarStatus(String[] vars, boolean[] statuses) {
        int len = vars.length;
        for (int i = 0; i < len; i++) {
            if (statuses[i]) {
                BaseConstant.publicPanel.setOpLink(vars[i], BaseConstant.STATUS_BAR_STATUS_COLOR_ON);
            } else {
                BaseConstant.publicPanel.setOpLinkError(vars[i], BaseConstant.STATUS_BAR_STATUS_COLOR_OFF);
            }
        }
    }
}
