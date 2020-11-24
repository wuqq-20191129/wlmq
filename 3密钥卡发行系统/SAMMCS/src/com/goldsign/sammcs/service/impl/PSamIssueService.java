/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.sammcs.service.impl;

import com.goldsign.csfrm.vo.CallResult;
import com.goldsign.sammcs.env.AppConstant;
import com.goldsign.sammcs.jni.PSamIssueJni;
import com.goldsign.sammcs.service.IPSamIssueService;
import com.goldsign.sammcs.vo.CardKeyResult;
import com.goldsign.sammcs.vo.IssueVo;
import com.goldsign.sammcs.vo.KmsCfgParam;
import com.goldsign.sammcs.vo.ReadOutInfVo;

/**
 *
 * @author lenovo
 */
public class PSamIssueService implements IPSamIssueService{

    private PSamIssueJni pSamIssueJni;
    
    public PSamIssueService(){
        pSamIssueJni = new PSamIssueJni();
    }
    
    @Override
    public CallResult author(KmsCfgParam param) {
        
        CallResult callResult = new CallResult();
        
        byte[] kmIp = param.getKmsIpBs();
        byte[] kmPort = param.getKmsPortBs();
        byte[] pin = param.getKmsPinBs();
        byte[] keyVerstion = param.getKmsKeyVerstionBs();
        
        CardKeyResult cardKeyResult = pSamIssueJni.author(kmIp, kmPort, pin,keyVerstion);
        if(AppConstant.KMS_SUCCESS_CODE.equals(new String(cardKeyResult.getCode()))){
          
            callResult.setResult(true);
        }
        callResult.setCode(new String(cardKeyResult.getCode()));
        String co = new String(cardKeyResult.getCode());
        return callResult;
    }

    @Override
    public CallResult issue(IssueVo issueVo) {
        
        CallResult callResult = new CallResult();

        byte[] keyVerstion = issueVo.getKeyVerstion().getBytes();
        byte[] psamCardNo = issueVo.getPsamCardNo().getBytes();
        byte[] psamCardVersion = issueVo.getPsamCardVersion().getBytes();
        //20140413 modify by moqf 票卡类型如果是一位必须加“0”补够两位 
        if (issueVo.getPsamCardType() != null && issueVo.getPsamCardType().length()==1) {
            issueVo.setPsamCardType("0"+issueVo.getPsamCardType());
        }
        byte[] psamCardType = issueVo.getPsamCardType().getBytes();
        byte[] keyIndex = issueVo.getKeyIndex().getBytes();
        byte[] issuerId = issueVo.getIssuerId().getBytes();
        byte[] receiverId = issueVo.getReceiverId().getBytes();
        byte[] startDate = issueVo.getStartDate().getBytes();
        byte[] validDate = issueVo.getValidDate().getBytes();
        
        CardKeyResult cardKeyResult = pSamIssueJni.issue(keyVerstion, psamCardNo, psamCardVersion, 
            psamCardType, keyIndex, issuerId, receiverId, startDate, validDate);
        if (AppConstant.KMS_SUCCESS_CODE.equals(new String(cardKeyResult.getCode()))) {

//            issueVo.setPsamCardPhyNo(new String(cardKeyResult.getMsg()));
            //20160126 add by mqf 改为从数组取
            Object[] msgs = cardKeyResult.getMsgs();
            issueVo.setPsamCardPhyNo(new String((byte[])msgs[0]));
            issueVo.setCardProducerCode(new String((byte[])msgs[1]));

            callResult.setObj(issueVo);
            callResult.setResult(true);
        }
        callResult.setCode(new String(cardKeyResult.getCode()));
        String co = new String(cardKeyResult.getCode());
        return callResult;
    }
    
    @Override
    public CallResult read() {
        
        CallResult callResult = new CallResult();

        
        CardKeyResult cardKeyResult = pSamIssueJni.read();

        if (AppConstant.KMS_SUCCESS_CODE.equals(new String(cardKeyResult.getCode()))) {
            Object[] msgs = cardKeyResult.getMsgs();
            
            ReadOutInfVo readOutInfVo = new ReadOutInfVo();
            readOutInfVo.setIssueState(new String((byte[])msgs[0]));
            readOutInfVo.setPsamCardNo(new String((byte[])msgs[1]));
            readOutInfVo.setPhyNo(new String((byte[])msgs[2]));

            callResult.setObj(readOutInfVo);
            callResult.setResult(true);
        }
        callResult.setCode(new String(cardKeyResult.getCode()));
        return callResult;
    }

    @Override
    public CallResult isKMSConnected(KmsCfgParam param) {
        CallResult callResult = new CallResult();
        byte[] kmIp = param.getKmsIpBs();
        byte[] kmPort = param.getKmsPortBs();
        CardKeyResult cardKeyResult = pSamIssueJni.connectStatus(kmIp, kmPort);
        
        if (AppConstant.KMS_SUCCESS_CODE.equals(new String(cardKeyResult.getCode()))) {
            callResult.setResult(true);
        }
        callResult.setCode(new String(cardKeyResult.getCode()));
        String co = new String(cardKeyResult.getCode());
        return callResult;
        
    }
    
    public static void main(String[] args){

         PSamIssueService pSamIssueService = new PSamIssueService();
         pSamIssueService.read();
    }

    
}
