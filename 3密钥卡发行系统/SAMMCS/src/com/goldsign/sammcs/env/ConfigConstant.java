/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.sammcs.env;

import com.goldsign.sammcs.util.ConfigUtil;

/**
 * 配置文件常量
 * 
 * @author lenovo
 */
public class ConfigConstant extends AppConstant{

    //配置和日志文件
    public static final String CONFIG_FILE = "config/CommonConfig.xml";     //配置文件
    public static final String LOG_FILE = "config/Log4jConfig.xml";        //日志文件
    
    //一级标签
    public static final String CommonTag = "Common";      //配置文件TAG
    public static final String DataConnectionPoolTag = "DataConnectionPool";
//    public static final String RwDeviceTag = "RwDevice";
//    public static final String DownloadTag = "Download";
//    public static final String UploadTag = "Upload";
//    public static final String LogTag = "Log";
    public static final String BackupTag = "Backup";
    public static final String KmsCommuTag = "KmsCommu";
    public static final String SamIssueTag = "SamIssue";
    
    
    
    //Common标签
    public static final String CommonStationIdTag = "StationId";
    public static final String CommonDeviceTypeTag = "DeviceType";
    public static final String CommonDeviceNoTag = "DeviceNo";
    public static final String CommonSystemFlagTag = "SystemFlag";
    
    //DataConnectionPool标签
    public static final String DataConnectionPoolDriverTag = "Driver";
    public static final String DataConnectionPoolURLTag = "URL";
    public static final String DataConnectionPoolUserTag = "User";
    public static final String DataConnectionPoolPasswordTag = "Password";
    public static final String DataConnectionPoolMaxActiveTag = "MaxActive";
    public static final String DataConnectionPoolMaxIdleTag = "MaxIdle";
    public static final String DataConnectionPoolMaxWaitTag = "MaxWait";

    //RwDevice标签
//    public static final String RwDeviceRwPortTag = "RwPort";
    
    //Upload标签
//    public static final String UploadMakeCardTag = "MakeCard";//发卡、退卡
    
    //日志标签
//    public static final String LogMakeCardTag = "MakeCard";
    //备份标签
    public static final String BackupMakeCardTag = "MakeCard";
    
    //Download标签
    
    //KmsCommu标签
    public static final String KmsCommuKmIp = "KmIp";   //加密机IP
    public static final String KmsCommuKmPort = "KmPort";//加密机端口
    public static final String KmsCommuKmPin = "KmPin";//加密钥密码
    public static final String KmsCommuKmVersion = "KmVersion";//加密机版本
    
    //SamIssue标签
    public static final String SamIssueKeyVerstion = "KeyVerstion";
    public static final String SamIssuePsamCardVersion = "PsamCardVersion";
    public static final String SamIssueKeyIndex = "KeyIndex";
    public static final String SamIssueIssuerId = "IssuerId";
    public static final String SamIssueReceiverId = "ReceiverId";
    public static final String SamIssueStartDate = "StartDate";
    public static final String SamIssueValidDate = "ValidDate";
    
}
