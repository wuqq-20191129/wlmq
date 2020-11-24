package com.goldsign.sammcs.util;

import com.goldsign.csfrm.env.BaseConstant;
import com.goldsign.csfrm.util.MessageShowUtil;
import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.sammcs.env.AppConstant;
import com.goldsign.sammcs.ui.panel.MadeCardPanel;
import com.goldsign.sammcs.vo.PubFlagVo;
import java.awt.Image;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;

public class PubUtil {

    private static Logger logger = Logger.getLogger(PubUtil.class.getName());

    public PubUtil() {
        super();
        // TODO Auto-generated constructor stub
    }

    public static void handleException(Exception e, Logger lg) throws Exception {
        lg.error("错误:", e);
        MessageShowUtil.errorOpMsg( "网络异常，请联系管理员，点击确认按钮后，系统3秒后自动退出！");
        JOptionPane.showMessageDialog(BaseConstant.mainFrame, "网络异常，请联系管理员，点击确认按钮后，系统3秒后自动退出！");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            java.util.logging.Logger.getLogger(MadeCardPanel.class.getName()).log(Level.SEVERE, null, ex);
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
            java.util.logging.Logger.getLogger(MadeCardPanel.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public Vector getTableFlags(String tableName, String codeColName, String textColName) throws Exception {
        DbHelper dbHelper = null;
        String strSql = null;
        boolean result = false;
        Vector tableFlags = new Vector();

        try {
            dbHelper = new DbHelper("", AppConstant.dbcpHelper.getConnection());
            strSql = "select " + codeColName + "," + textColName + " from " + tableName;
            result = dbHelper.getFirstDocument(strSql);

            while (result) {
                PubFlagVo pv = new PubFlagVo();
                pv.setCode(dbHelper.getItemValue(codeColName));
//                pv.setCodeText(dbHelper.getItemValueIso(textColName));
                pv.setCodeText(dbHelper.getItemValue(textColName));
                tableFlags.add(pv);
                result = dbHelper.getNextDocument();
            }
        } catch (Exception e) {
            PubUtil.handleException(e, logger);
        } finally {
            PubUtil.finalProcess(dbHelper);

        }

        return tableFlags;
    }
    
     /*
    * 生成未选中时按钮icon
    *@param icon_name
    *@param width
    *@param height
    *@return icon
    */
    public static ImageIcon setButtonIcon(String icon_name, int width,int height){
        ImageIcon icon = new ImageIcon(BaseConstant.appWorkDir+"/images/menu/"+icon_name+".png");
        Image img =icon.getImage();
        img = img.getScaledInstance(width,height,Image.SCALE_DEFAULT);
        icon.setImage(img);
        return icon;
    }
}
