/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.login;

import com.goldsign.lib.db.util.DbHelper;
import com.goldsign.login.bo.ILoginBo;
import com.goldsign.login.bo.LoginBo;
import com.goldsign.login.result.AuthResult;
/**
 * ≤‚ ‘¿‡
 * @author oywl
 */
public class Test {
    public static void main(String[] args) {
        ILoginBo loginBo = new LoginBo();
        try {
            DbHelper dbHelper = new DbHelper("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@10.2.99.3:1521:ACC", "acc_st", "acc_st");
            AuthResult ar = loginBo.authorization("lindq", "iccs88", "07", dbHelper);
            
            if (ar.getReturnCode().equals(loginBo.SUCCESS_AUTH)) {
                System.out.println("success login");
                if (ar.getModules() != null) {
                    System.out.println("find modules num is " + ar.getModules().size());
                }
            } else {
                System.out.println(ar.getMsg());
            }
        } catch (Exception e) {
        }
    }
}
