/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.escommu.test;

import com.goldsign.escommu.dbutil.DbcpHelper;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author lenovo
 */
public class TestDS {

    public static void main(String[] args) throws ClassNotFoundException, SQLException{
        DbcpHelper dbcpHelper = new DbcpHelper("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@172.20.19.203:1521:epay1", "acc_tk", "acc_tk", 40, 20, 20000);
        Connection con = dbcpHelper.getConnection();
        System.out.println(con.toString());
        //Class.forName("oracle.jdbc.driver.OracleDriver");
        //Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@172.20.19.203:1521:epay1", "acc_tk", "acc_tk");
        //System.out.println(conn.toString());
    }
}
