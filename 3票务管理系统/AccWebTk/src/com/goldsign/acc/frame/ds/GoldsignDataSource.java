/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.frame.ds;

import com.goldsign.acc.frame.constant.DBConstant;
import com.goldsign.login.util.Encryption;
import org.apache.commons.dbcp.BasicDataSource;

/**
 *
 * @author mqf
 */
public class GoldsignDataSource extends BasicDataSource {
    
    
   protected boolean usernameEncrypt; //是否对数据库用户名加密
   
   protected boolean passwordEncrypt; //是否对数据库密码加密
   
   public GoldsignDataSource() {
       
   }

    @Override
    public synchronized void setPassword(String password) {
        String result =password;
        if (passwordEncrypt) {
        
            result = Encryption.biDecrypt(password);
//            System.out.println("setPassword password:"+result);
            
            this.passwordEncrypt = false; //解密后设置为不需解密状态
        } 
        this.password = result;
        
    }
    
    @Override
    public synchronized void setUsername(String username) {
        String result =username;
        if (usernameEncrypt) {
            result = Encryption.biDecrypt(username);
//            System.out.println("username:"+result);
        } 
        this.username = result;
    }
    

    public boolean isUsernameEncrypt() {
        return usernameEncrypt;
    }

    public void setUsernameEncrypt(boolean usernameEncrypt) {
        this.usernameEncrypt = usernameEncrypt;
        
        if (usernameEncrypt) {
            String result = Encryption.biDecrypt(this.getUsername());
//            System.out.println("setUsernameEncrypt username:"+result);
            
            this.usernameEncrypt = false; //解密后设置为不需解密状态
            
            this.username = result; //需要解密才赋值username，不能使用setUsername()再调用解密一次
        } 
    }

    public boolean isPasswordEncrypt() {
        return passwordEncrypt;
    }

    public void setPasswordEncrypt(boolean passwordEncrypt) {
        this.passwordEncrypt = passwordEncrypt;
        
        if (passwordEncrypt) {
            String result = Encryption.biDecrypt(this.getPassword());
//            System.out.println("setPasswordEncrypt password:"+result);
            
            this.passwordEncrypt = false; //解密后设置为不需解密状态
            
            this.password = result; //需要解密才赋值password，不能使用setPassword()再调用解密一次
        } 
        
    }

    
    
    
    
    public static void main(String[] args) {
        String result;
        result = Encryption.biDecrypt("262C2F1B3C272B27292A");
        System.out.println(result);
        System.out.println(" w_acc_tk_app ---> "
				+ Encryption.biEncrypt("w_acc_tk_app"));
        
    }
}
