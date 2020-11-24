/*
 * 文件名：testPSamUssueDll
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.sammcs.test;

import com.goldsign.sammcs.dll.library.PSamIssueDll;
import com.goldsign.sammcs.dll.structure.AuthInInf;
import com.goldsign.sammcs.dll.structure.AuthOutInf;
import com.goldsign.sammcs.dll.structure.IssueInInf;
import com.goldsign.sammcs.dll.structure.IssueOutInf;


/*
 * 单元测试类
 * @author     lindaquan
 * @version    V1.0
 */

public class testPSamUssueDll {
    
    public static void main(String[] args){

        testAuth();
        //testIssue();
    }
    
    public static void testAuth(){
        AuthInInf.ByReference authInInf = new AuthInInf.ByReference();
        AuthOutInf.ByReference authOutInf = new AuthOutInf.ByReference();
        //01 C0 00 01 0A C8308A0058FFFFFFFF
        authInInf.treatyVersions[0] = (byte)'0';
        authInInf.treatyVersions[1] = (byte)'1';
        
        authInInf.assistParam[0] = (byte)'C';
        authInInf.assistParam[1] = (byte)'0';
        
        authInInf.appType[0] = (byte)'0';
        authInInf.appType[1] = (byte)'0';
        
        authInInf.commType[0] = (byte)'0';
        authInInf.commType[1] = (byte)'1';
        //0A C8 30 8A
        authInInf.encryptorip[0] = (byte)'0';
        authInInf.encryptorip[1] = (byte)'A';
        authInInf.encryptorip[2] = (byte)'C';
        authInInf.encryptorip[3] = (byte)'8';
        authInInf.encryptorip[4] = (byte)'3';
        authInInf.encryptorip[5] = (byte)'0';
        authInInf.encryptorip[6] = (byte)'8';
        authInInf.encryptorip[7] = (byte)'A';
        //00 58
        authInInf.encryptorPort[0] = (byte)'0';
        authInInf.encryptorPort[1] = (byte)'0';
        authInInf.encryptorPort[2] = (byte)'5';
        authInInf.encryptorPort[3] = (byte)'8';
        authInInf.encryptorPin[0] = (byte)'F';
        authInInf.encryptorPin[1] = (byte)'F';
        authInInf.encryptorPin[2] = (byte)'F';
        authInInf.encryptorPin[3] = (byte)'F';
        authInInf.encryptorPin[4] = (byte)'F';
        authInInf.encryptorPin[5] = (byte)'F';
        authInInf.encryptorPin[6] = (byte)'F';
        authInInf.encryptorPin[7] = (byte)'F';        
        
        int ret = PSamIssueDll.INSTANCE.psam_issue_dev_op(authInInf, authOutInf);
        
        System.out.println(new String(authOutInf.response));
        
        //String inStr = "01 C0 00 02 0AC8308A 0058 000000100000000001000001410007310000000041000731000000002013100120231001";
    }
    
    public static void testIssue(){
        IssueInInf.ByReference issueInInf = new IssueInInf.ByReference();
        IssueOutInf.ByReference issueOutInf = new IssueOutInf.ByReference();
        //01 C0 00 01 0A C8308A0058FFFFFFFF
        issueInInf.treatyVersions[0] = (byte)'0';
        issueInInf.treatyVersions[1] = (byte)'1';
        
        issueInInf.assistParam[0] = (byte)'C';
        issueInInf.assistParam[1] = (byte)'0';
        
        issueInInf.appType[0] = (byte)'0';
        issueInInf.appType[1] = (byte)'0';
        
        issueInInf.commType[0] = (byte)'0';
        issueInInf.commType[1] = (byte)'2';
        //0A C8 30 8A
        issueInInf.encryptorip[0] = (byte)'0';
        issueInInf.encryptorip[1] = (byte)'A';
        issueInInf.encryptorip[2] = (byte)'C';
        issueInInf.encryptorip[3] = (byte)'8';
        issueInInf.encryptorip[4] = (byte)'3';
        issueInInf.encryptorip[5] = (byte)'0';
        issueInInf.encryptorip[6] = (byte)'8';
        issueInInf.encryptorip[7] = (byte)'A';
        //00 58
        issueInInf.encryptorPort[0] = (byte)'0';
        issueInInf.encryptorPort[1] = (byte)'0';
        issueInInf.encryptorPort[2] = (byte)'5';
        issueInInf.encryptorPort[3] = (byte)'8';
             
        issueInInf.keyVerstion[0] = (byte)'0';
        issueInInf.keyVerstion[1] = (byte)'0';

        //0000100000000001
        issueInInf.psamCardNo[0] = (byte)'0';
        issueInInf.psamCardNo[1] = (byte)'0';
        issueInInf.psamCardNo[2] = (byte)'0';
        issueInInf.psamCardNo[3] = (byte)'0';
        issueInInf.psamCardNo[4] = (byte)'1';
        issueInInf.psamCardNo[5] = (byte)'0';
        issueInInf.psamCardNo[6] = (byte)'0';
        issueInInf.psamCardNo[7] = (byte)'0';
        issueInInf.psamCardNo[8] = (byte)'0';
        issueInInf.psamCardNo[9] = (byte)'0';
        issueInInf.psamCardNo[10] = (byte)'0';
        issueInInf.psamCardNo[11] = (byte)'0';
        issueInInf.psamCardNo[12] = (byte)'0';
        issueInInf.psamCardNo[13] = (byte)'0';
        issueInInf.psamCardNo[14] = (byte)'0';
        issueInInf.psamCardNo[15] = (byte)'1';
        
        issueInInf.psamCardVersion[0] = (byte)'0';
        issueInInf.psamCardVersion[1] = (byte)'0';
        
        issueInInf.psamCardType[0] = (byte)'0';
        issueInInf.psamCardType[1] = (byte)'0';
        
        issueInInf.keyIndex[0] = (byte)'0';
        issueInInf.keyIndex[1] = (byte)'1';
        
        //4100073100000000
        issueInInf.issuerId[0] = (byte)'4';
        issueInInf.issuerId[1] = (byte)'1';
        issueInInf.issuerId[2] = (byte)'0';
        issueInInf.issuerId[3] = (byte)'0';
        issueInInf.issuerId[4] = (byte)'0';
        issueInInf.issuerId[5] = (byte)'7';
        issueInInf.issuerId[6] = (byte)'3';
        issueInInf.issuerId[7] = (byte)'1';
        issueInInf.issuerId[8] = (byte)'0';
        issueInInf.issuerId[9] = (byte)'0';
        issueInInf.issuerId[10] = (byte)'0';
        issueInInf.issuerId[11] = (byte)'0';
        issueInInf.issuerId[12] = (byte)'0';
        issueInInf.issuerId[13] = (byte)'0';
        issueInInf.issuerId[14] = (byte)'0';
        issueInInf.issuerId[15] = (byte)'0';
        
        //4100073100000000
        issueInInf.receiverId[0] = (byte)'4';
        issueInInf.receiverId[1] = (byte)'1';
        issueInInf.receiverId[2] = (byte)'0';
        issueInInf.receiverId[3] = (byte)'0';
        issueInInf.receiverId[4] = (byte)'0';
        issueInInf.receiverId[5] = (byte)'7';
        issueInInf.receiverId[6] = (byte)'3';
        issueInInf.receiverId[7] = (byte)'1';
        issueInInf.receiverId[8] = (byte)'0';
        issueInInf.receiverId[9] = (byte)'0';
        issueInInf.receiverId[10] = (byte)'0';
        issueInInf.receiverId[11] = (byte)'0';
        issueInInf.receiverId[12] = (byte)'0';
        issueInInf.receiverId[13] = (byte)'0';
        issueInInf.receiverId[14] = (byte)'0';
        issueInInf.receiverId[15] = (byte)'0';
        
        //20131001
        issueInInf.startDate[0] = (byte)'2';
        issueInInf.startDate[1] = (byte)'0';
        issueInInf.startDate[2] = (byte)'1';
        issueInInf.startDate[3] = (byte)'3';
        issueInInf.startDate[4] = (byte)'1';
        issueInInf.startDate[5] = (byte)'0';
        issueInInf.startDate[6] = (byte)'0';
        issueInInf.startDate[7] = (byte)'1';
        
        //20231001
        issueInInf.validDate[0] = (byte)'2';
        issueInInf.validDate[1] = (byte)'0';
        issueInInf.validDate[2] = (byte)'2';
        issueInInf.validDate[3] = (byte)'3';
        issueInInf.validDate[4] = (byte)'1';
        issueInInf.validDate[5] = (byte)'0';
        issueInInf.validDate[6] = (byte)'0';
        issueInInf.validDate[7] = (byte)'1';
        //System.out.println("01C000020AC8308A0058000000100000000001000001410007310000000041000731000000002013100120231001".length());
        int ret = PSamIssueDll.INSTANCE.psam_issue_dev_op(issueInInf, issueOutInf);
        
        
        System.out.println(new String(issueOutInf.response));
        System.out.println(new String(issueOutInf.resultData));
        //String inStr = "01 C0 00 02 0AC8308A 0058 00 0000100000000001 00 00 01 4100073100000000 4100073100000000 20131001 20231001";
        //                01C000020AC8308A0058000000100000000001000001410007310000000041000731000000002013100120231001
    }

}
