/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.dao;

import com.goldsign.lib.db.util.DbHelper;
import java.math.BigDecimal;
import java.sql.SQLException;

/**
 *
 * @author hejj
 */
public class ExportDaoBase {
     protected String getBigDecimal(DbHelper dbHelper,String fieldName) throws SQLException{
        String value = dbHelper.getItemValue(fieldName);
        BigDecimal m =new BigDecimal("0.00");
        m = m.add(new BigDecimal(value));
        return m.toString();
    }
     
     protected String getFenFromYuan(DbHelper dbHelper,String fieldName) throws SQLException{
        String value = dbHelper.getItemValue(fieldName);
        BigDecimal m =new BigDecimal("0.00");
        
        m = m.add(new BigDecimal(value).multiply(new BigDecimal(100)));
        return Integer.toString(m.intValue());
    }
    
}
