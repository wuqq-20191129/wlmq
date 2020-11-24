/*
 * 文件名：PSamIssueJni
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.sammcs.jni;

import com.goldsign.sammcs.dll.library.PSamIssueDll;
import com.goldsign.sammcs.dll.structure.AuthInInf;
import com.goldsign.sammcs.dll.structure.AuthOutInf;
import com.goldsign.sammcs.dll.structure.IssueInInf;
import com.goldsign.sammcs.dll.structure.IssueOutInf;
import com.goldsign.sammcs.dll.structure.ConnectInInf;
import com.goldsign.sammcs.dll.structure.ConnectOutInf;
import com.goldsign.sammcs.dll.structure.ReadInInf;
import com.goldsign.sammcs.dll.structure.ReadOutInf;
import com.goldsign.sammcs.vo.CardKeyResult;


/*
 * PSAM卡授权、发行JNI类，供Service层调用
 * @author     lindaquan
 * @version    V1.0
 */

public class PSamIssueJni {
    
    private static final byte[] TREATY_VERSIONS = new byte[]{(byte) '0', (byte) '1'};
//    private static final byte[] ASSIST_PARAM = new byte[]{(byte) 'C', (byte) '0'};
    //20160315 modify by mqf 辅助参数 C0：为输出日志 00：为不输出日志
    private static final byte[] ASSIST_PARAM = new byte[]{(byte) 'C', (byte) '0'};
    private static final byte[] APP_TYPE = new byte[]{(byte) '0', (byte) '0'};
    private static final byte[] COMM_TYPE_AUTHOR = new byte[]{(byte) '0', (byte) '1'};
    private static final byte[] COMM_TYPE_ISSUE = new byte[]{(byte) '0', (byte) '2'};
    private static final byte[] COMM_TYPE_READ = new byte[]{(byte) '0', (byte) '3'};
    private static final byte[] COMM_TYPE_CONNECT = new byte[]{(byte) '0', (byte) '4'};
    
    private static byte[] kmIp;
    private static byte[] kmPort;
    
    
    /**
     * 授权
     *
     * @param kmIp ASCII
     * 如10.200.48.138，则为(byte)'0'(byte)'A'(byte)'C'(byte)'8'(byte)'3'(byte)'0'(byte)'8'(byte)'A';
     * @param kmPort ASCII 如88, 则为 (byte)'0'(byte)'0'(byte)'5'(byte)'8';
     * @param pin ASCII
     * 如FFFFFFFF，则为(byte)'F'(byte)'F'(byte)'F'(byte)'F'(byte)'F'(byte)'F'(byte)'F';(byte)'F';
     * @param keyVer        (byte)'0'(byte)'0'
     * @return
     */
    public CardKeyResult author(byte[] kmIp, byte[] kmPort, byte[] pin, byte[] keyVer){
        
        AuthInInf.ByReference authInInf = new AuthInInf.ByReference();
        AuthOutInf.ByReference authOutInf = new AuthOutInf.ByReference();
        
        authInInf.treatyVersions = TREATY_VERSIONS;
        authInInf.assistParam = ASSIST_PARAM;
        authInInf.appType = APP_TYPE;
        authInInf.commType = COMM_TYPE_AUTHOR;
        
//        authInInf.encryptorPin = new byte[8];
//        authInInf.encryptorPort = new byte[4];
//        authInInf.encryptorip = new byte[8];
//        authInInf.keyVerstion = new byte[2];
//        authInInf.paramEndSign = 0x00;
//        
//        authOutInf.cardType = new byte[2];
//        authOutInf.commType = new byte[2];
//        authOutInf.response = new byte[2];
//        authOutInf.treatyVersions = new byte[2];
                    
        System.arraycopy(kmIp, 0, authInInf.encryptorip, 0, kmIp.length);
        System.arraycopy(kmPort, 0, authInInf.encryptorPort, 0, kmPort.length);
        System.arraycopy(pin, 0, authInInf.encryptorPin, 0, pin.length);
        authInInf.keyVerstion = keyVer;
        
        int ret = PSamIssueDll.INSTANCE.psam_issue_dev_op(authInInf, authOutInf);
        
        CardKeyResult result = new CardKeyResult();
        result.setCode(authOutInf.response);
        PSamIssueJni.kmIp = kmIp;
        PSamIssueJni.kmPort = kmPort;
        
        return result;
    }
    
    
    /**
     * PSAM卡发行
     * 
     * @param keyVer        (byte)'0'(byte)'0'
     * @param psamNo        0000100000000001转为byte[]
     * @param psamVer       (byte)'0'(byte)'0'
     * @param samType       (byte)'0'(byte)'0'
     * @param keyIndex      (byte)'0'(byte)'1'
     * @param appSender     4100073100000000转为byte[]
     * @param appResver     4100073100000000转为byte[]
     * @param appStartDate  20131001转为byte[]
     * @param appEffDate    20231001转为byte[]
     * @return 
     */
    public CardKeyResult issue(byte[] keyVer, byte[] psamNo, byte[] psamVer, 
            byte[] samType, byte[] keyIndex, byte[] appSender, byte[] appResver, 
            byte[] appStartDate, byte[] appEffDate){
    
        IssueInInf.ByReference issueInInf = new IssueInInf.ByReference();
        IssueOutInf.ByReference issueOutInf = new IssueOutInf.ByReference();
        
        issueInInf.treatyVersions = TREATY_VERSIONS;//协议版本2 "01"
        issueInInf.appType = APP_TYPE;//卡类标识2 "00"
        issueInInf.assistParam = ASSIST_PARAM;//辅助参数2 辅助参数"00"
        issueInInf.commType = COMM_TYPE_ISSUE;//命令标识2 "02"
        issueInInf.encryptorip = PSamIssueJni.kmIp;//8 加密机IP地址(16进制)
        issueInInf.encryptorPort = PSamIssueJni.kmPort ;//4 加密机端口
        
        
//        issueInInf.issuerId = new byte[16];
//        issueInInf.receiverId = new byte[16];
//        issueInInf.keyIndex = new byte[2];
//        issueInInf.keyVerstion = new byte[2];
//        issueInInf.psamCardNo = new byte[16];
//        issueInInf.psamCardType = new byte[2];
//        issueInInf.psamCardVersion = new byte[2];
//        issueInInf.startDate = new byte[8];
//        issueInInf.validDate = new byte[8];
//        
//        issueOutInf.cardProducerCode = new byte[4];
//        issueOutInf.cardType = new byte[2];
//        issueOutInf.commType = new byte[2];
//        issueOutInf.response = new byte[2];
//        issueOutInf.resultData = new byte[16];
//        issueOutInf.treatyVersions = new byte[2];
        
        System.arraycopy(appSender, 0, issueInInf.issuerId, 0, appSender.length);//16 应用发行者标识
        System.arraycopy(appResver, 0, issueInInf.receiverId, 0, appResver.length);//16 应用接收者标识
        System.arraycopy(keyIndex, 0, issueInInf.keyIndex, 0, keyIndex.length);//2 密钥索引号
        System.arraycopy(keyVer, 0, issueInInf.keyVerstion, 0, keyVer.length);//2 密钥版本
        System.arraycopy(psamNo, 0, issueInInf.psamCardNo, 0, psamNo.length);//16 psam卡号
        System.arraycopy(samType, 0, issueInInf.psamCardType, 0, samType.length);//2 psam类型
        System.arraycopy(psamVer, 0, issueInInf.psamCardVersion, 0, psamVer.length);//2 PSAM版本
        System.arraycopy(appStartDate, 0, issueInInf.startDate, 0, appStartDate.length);//8 应用启用日期（YYYYMMDD ）
        System.arraycopy(appEffDate, 0, issueInInf.validDate, 0, appEffDate.length);//8 应用启用日期（YYYYMMDD ）
        
        int ret =PSamIssueDll.INSTANCE.psam_issue_dev_op(issueInInf, issueOutInf);
        
        CardKeyResult result = new CardKeyResult();
        result.setCode(issueOutInf.response);
//        result.setMsg(issueOutInf.resultData);
        //20160126 add by mqf
        Object[] issueOutData = new Object[]{issueOutInf.resultData
                                            ,issueOutInf.cardProducerCode};
        result.setMsgs(issueOutData);
        
        return result;
        
    }
    
     public CardKeyResult read(){
    
        ReadInInf.ByReference readInInf = new ReadInInf.ByReference();
        ReadOutInf.ByReference readOutInf = new ReadOutInf.ByReference();
        
        readInInf.treatyVersions = TREATY_VERSIONS;//协议版本2 "01"
        readInInf.appType = APP_TYPE;//卡类标识2 "00"
        readInInf.assistParam = ASSIST_PARAM;//辅助参数2 辅助参数"00"
        readInInf.commType = COMM_TYPE_READ;//命令标识2 "03"
        
        int ret =PSamIssueDll.INSTANCE.psam_issue_dev_op(readInInf, readOutInf);
        
        CardKeyResult result = new CardKeyResult();
        result.setCode(readOutInf.response);
        Object[] readOutData = new Object[]{readOutInf.issueState
                                            ,readOutInf.psamCardNo
                                            ,readOutInf.phyNo};
        
        result.setMsgs(readOutData);
        return result;
        
    }
     
     /**
     * 获取网络状态
     *
     * @param kmIp ASCII
     * 如10.200.48.138，则为(byte)'0'(byte)'A'(byte)'C'(byte)'8'(byte)'3'(byte)'0'(byte)'8'(byte)'A';
     * @param kmPort ASCII 如88, 则为 (byte)'0'(byte)'0'(byte)'5'(byte)'8';
     * @param pin ASCII
     * 如FFFFFFFF，则为(byte)'F'(byte)'F'(byte)'F'(byte)'F'(byte)'F'(byte)'F'(byte)'F';(byte)'F';
     * @param keyVer        (byte)'0'(byte)'0'
     * @return
     */
    public CardKeyResult connectStatus(byte[] kmIp, byte[] kmPort){
        
        ConnectInInf.ByReference authInInf = new ConnectInInf.ByReference();
        ConnectOutInf.ByReference authOutInf = new ConnectOutInf.ByReference();
        
        authInInf.treatyVersions = TREATY_VERSIONS;
        authInInf.assistParam = ASSIST_PARAM;
        authInInf.appType = APP_TYPE;
        authInInf.commType = COMM_TYPE_CONNECT;
        
        System.arraycopy(kmIp, 0, authInInf.MKSIp, 0, kmIp.length);
        System.arraycopy(kmPort, 0, authInInf.KMSPort, 0, kmPort.length);
        
        int ret = PSamIssueDll.INSTANCE.psam_issue_dev_op(authInInf, authOutInf);
        
        CardKeyResult result = new CardKeyResult();
        result.setCode(authOutInf.response);
        
        return result;
    }
   
}
