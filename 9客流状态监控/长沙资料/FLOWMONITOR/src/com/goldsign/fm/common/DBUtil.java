/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.fm.common;

import com.goldsign.fm.vo.PubFlagVO;
import com.goldsign.lib.db.util.DbHelper;
import java.sql.SQLException;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class DBUtil {

    private Logger logger = Logger.getLogger(DBUtil.class.getName());

    public Vector getParamTableFlags(DbHelper dbHelper, String tableName, String strTypeColName, String codeColName, String textColName) throws Exception {

        String strSql = null;
        boolean result = false;
        Vector tableFlags = new Vector();


        try {
            strSql = "select " + strTypeColName + "," + codeColName + "," + textColName + " from " + tableName + " where record_flag='0'";
            result = dbHelper.getFirstDocument(strSql);
            String codeText = ""; 

            while (result) {
                PubFlagVO pv = new PubFlagVO();
                pv.setStrType(dbHelper.getItemValue(strTypeColName));

                pv.setCode(dbHelper.getItemValue(codeColName));
                pv.setCodeText(dbHelper.getItemValue(textColName));
                tableFlags.add(pv);
                result = dbHelper.getNextDocument();
            }
        } catch (SQLException e) {
            PubUtil.handleException(e, logger);
        }


        return tableFlags;

    }

    public Vector getParamTableFlags(DbHelper dbHelper, String tableName, String codeColName, String textColName) throws Exception {

        String strSql = null;
        boolean result = false;
        Vector tableFlags = new Vector();


        try {
            strSql = "select " + codeColName + "," + textColName + " from " + tableName + " where record_flag='0'";
            result = dbHelper.getFirstDocument(strSql);

            while (result) {
                PubFlagVO pv = new PubFlagVO();
                pv.setCode(dbHelper.getItemValue(codeColName));
                pv.setCodeText(dbHelper.getItemValue(textColName));
                tableFlags.add(pv);
                result = dbHelper.getNextDocument();
            }
        } catch (SQLException e) {
            PubUtil.handleException(e, logger);
        }

        return tableFlags;

    }
    
    public String getTextByCode(String code ,Vector tableFlags) {
        PubFlagVO pv = null;
        for(int i=0;i<tableFlags.size();i++){
         pv = (PubFlagVO)tableFlags.get(i);
         if(pv.getCode().equals(code))
           return pv.getCodeText();
       }
       return code;

     }
    
    
    public String getTextByCode(String code ,String strType,Vector tableFlags ) {
       PubFlagVO pv = null;
       for(int i=0;i<tableFlags.size();i++){
        pv = (PubFlagVO)tableFlags.get(i);
        if(pv.getCode().equals(code)&&pv.getStrType().equals(strType))
          return pv.getCodeText();
      }
      return code;

    }
}
