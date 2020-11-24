/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.ftp;

/**
 *
 * @author lenovo
 */
public class FtpMonitor {

    private static CommuFtp commuFtp = new CommuFtp();
    
    /**
     * 测试FTP指定目录连接是否正常
     * 
     * @param server
     * @param userName
     * @param password
     * @param path
     * @return 
     */
    public static boolean testFtpPathLogin(String server,int port, String userName, String password, 
            String path){
        if(null == server || null == userName || null == password
                || null == path){
            return false;
        }
        
        return commuFtp.testFtpLogin(server,userName, password, path);
    }
    
    /**
     * 测试FTP连接是否正常
     * 
     * @param server
     * @param userName
     * @param password
     * @return 
     */
    public static boolean testFtpUserLogin(String server, String userName, String password){
        
        if(null == server || null == userName || null == password){
            return false;
        }
        
        return commuFtp.testFtpLogin(server, userName, password);
    }
}
