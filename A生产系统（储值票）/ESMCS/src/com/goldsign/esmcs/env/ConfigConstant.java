/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.env;

import com.goldsign.csfrm.vo.XmlTagVo;

/**
 * 配置文件常量
 * 
 * @author lenovo
 */
public class ConfigConstant extends AppConstant{

    //配置和日志文件
    public static String CONFIG_FILE = appWorkDir+"/"+"config/CommonConfig.xml";     //配置文件
    public static String LOG_FILE = appWorkDir+"/"+"config/Log4jConfig.xml";        //日志文件
    
    //一级标签
    public static final String CommonTag = "Common";      //配置文件TAG
    public static final String EsCommuTag = "EsCommu";
    public static final String EsDeviceTag = "EsDevice";
    public static final String RwDeviceTag = "RwDevice";
    public static final String DownloadTag = "Download";
    public static final String UploadTag = "Upload";
    public static final String KmsCommuTag = "KmsCommu";
    public static final String DataConncetionPoolTag = "DataConnectionPool";
    
    //Common标签
    public static final String CommonStationIdTag = "StationId";
    public static final String CommonDeviceTypeTag = "DeviceType";
    public static final String CommonDeviceNoTag = "DeviceNo";
    
    //EsCommu标签
    public static final String EsCommuServerIpTag = "ServerIp";
    public static final String EsCommuServerPortTag = "ServerPort";
    
    //EsDevice标签
    public static final String EsDevicePtPortTag = "PtPort";
    public static final String EsDevicePtComRateTag = "PtComRate";
    
    //RwDevice标签
    public static final String ExRwPortTag = "ExRwPort";
   
    //Upload标签
    public static final String LocalFtpServerIpTag = "LocalFtpServerIp";
    public static final String LocalFtpUserNameTag = "LocalFtpUserName";
     public static final String LocalFtpServerPortTag = "LocalFtpServerPort";
    public static final String LocalFtpUserPasswordTag = "LocalFtpUserPassword";
    public static final String UploadMakingPathTag = "MakingPath";
    public static final String UploadFinishPathTag = "FinishPath";
    public static final String UploadOrderPathTag = "OrderPath";
    public static final String UploadBadCardPathTag = "BadCardPath";
    public static final String UploadGoodCardPathTag = "GoodCardPath";
    public static final String UploadAuditFileTag = "AuditFile";
    public static final String UploadErrorFileTag = "ErrorFile";    
    public static final String RecordFileTag = "RecordFile";
    
    //Download标签
    public static final String FtpServerIpTag = "FtpServerIp";
    public static final String FtpServerPortTag = "FtpServerPort";
    public static final String FtpUserNameTag = "FtpUserName";
    public static final String FtpUserPasswordTag = "FtpUserPassword";
    public static final String FtpTimeoutTag = "FtpTimeout";
    public static final String FtpRetryTimeTag = "FtpRetryTime";
    public static final String FtpRetryWaitingTag = "FtpRetryWaiting";
    public static final String FtpAuditRemotePathTag = "FtpAuditRemotePath";
    public static final String FtpParamRemotePathTag = "FtpParamRemotePath";
    public static final String FtpAuditLocalPathTag = "FtpAuditLocalPath";
    public static final String FtpParamLocalPathTag = "FtpParamLocalPath";
    public static final String FtpLocalDirTag = "FtpLocalDir";
    public static final String FtpRemoteDirTag = "FtpRemoteDir";
    public static final String FtpPhyLogicRemotePathTag = "FtpPhyLogicRemotePath";
    public static final String FtpPhyLogicLocalPathTag = "FtpPhyLogicLocalPath";
    
    //KmsCommu标签
    public static final String KmsCommuKmIp = "KmIp";   //加密机IP
    public static final String KmsCommuKmPort = "KmPort";//加密机端口
    public static final String KmsCommuKmPin = "KmPin";//加密钥密码
    
    //数据库标签
    
    public static final String DriverTag = "Driver";//驱动
    public static final String URLTag = "URL";//URL
    public static final String UserNameTag = "UserName";   //用户名
    public static final String PassWordTag = "PassWord";//密码
    public static final String MaxActiveTag = "MaxActive";//最大连接数
    public static final String MaxIdleTag = "MaxIdle";//最大空闲连接数
    public static final String MaxWaitTag = "MaxWait";//等待时间上限
 
    //加载配置文件XML一级标签
    public static final XmlTagVo[] CFG_LOAD_XML_TOP_TAGS = new XmlTagVo[]{
                    new XmlTagVo(CommonTag), new XmlTagVo(EsCommuTag),
                    new XmlTagVo(EsDeviceTag), new XmlTagVo(RwDeviceTag),
                    new XmlTagVo(DownloadTag), new XmlTagVo(UploadTag),
                    new XmlTagVo(KmsCommuTag),new XmlTagVo(DataConncetionPoolTag)};
}
