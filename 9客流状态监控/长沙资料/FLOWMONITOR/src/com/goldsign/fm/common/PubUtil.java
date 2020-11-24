/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.fm.common;

import com.goldsign.lib.db.util.DbHelper;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class PubUtil {

    private static Logger logger = Logger.getLogger(PubUtil.class.getName());

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
            if (dbHelper != null && !dbHelper.isConClosed()
            && !dbHelper.getAutoCommit()){
                //dbHelper.setAutoCommit(true);
                dbHelper.commitTran();
            }
            if (dbHelper != null) {
                dbHelper.closeConnection();
            }

        } catch (SQLException e) {
            logger.error("Fail to close connection", e);
        }
    }

    public static void handleException(Exception e, Logger lg) throws Exception {
        e.printStackTrace();
        lg.error("错误:", e);
        throw e;
    }

    public static void handleExceptionNoThrow(Exception e, Logger lg) {
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

    public int getNodeFlag(String clickNode) {
        if (clickNode == null || clickNode.length() == 0) {
            return -1;
        }
        int index = -1;
        String lineCode = "";
        String stationCode = "";
        index = clickNode.indexOf("_");
        lineCode = clickNode.substring(0, index);
        stationCode = clickNode.substring(index + 1);
        if (lineCode.equals("all")) {
            return AppConstant.FLAG_ALLLINE;
        }
        if (!lineCode.equals("all") && stationCode.equals("-1")) {
            return AppConstant.FLAG_SINGLELINE;
        }
        if (!lineCode.equals("all") && !stationCode.equals("-1")) {
            return AppConstant.FLAG_STATION;
        }
        return -1;

    }

    public void makeContainerInScreenMiddle(Container c) {
        Toolkit kit = Toolkit.getDefaultToolkit();

        Dimension screenSize = kit.getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        int w = c.getWidth();
        int h = c.getHeight();
        c.setLocation((width - w) / 2, (height - h) / 2);

    }
    public static void prompt(Container c,String msg){
        String[] options ={"确定"};

        String title ="提示消息";
        int select = JOptionPane.showOptionDialog(c,msg,title,JOptionPane.YES_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
    }
    public static void focus(Component comp){
        comp.setFocusable(true);
        comp.requestFocus();
    }
}
