/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.storageout.controller;

import java.math.BigInteger;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.goldsign.acc.app.storageout.entity.TicketStorageOutStorageInfo;
import com.goldsign.acc.app.storageout.mapper.TicketStorageOutWayMapper;
import com.goldsign.acc.frame.constant.InOutConstant;
import com.goldsign.acc.frame.controller.StorageOutInBaseController;
import com.goldsign.acc.frame.util.FormUtil;

/**
 * @desc:出库方式
 * @author:zhongzqi
 * @create date: 2017-8-21
 */
@Controller
public class TicketStorageOutWayController extends StorageOutInBaseController {

    @Autowired
    private TicketStorageOutWayMapper outWayMapper;

    public int calculateNumForLogicalWay(TicketStorageOutStorageInfo vo) throws Exception {
        checkLogicalNo(vo.getStartLogicalId(), vo.getEndLogicalId());
        int n = 0;
        List<TicketStorageOutStorageInfo> pos = outWayMapper.getExistLogicalSection(vo);
        //插入明细的卡号段
        BigInteger detailStart = new BigInteger(vo.getStartLogicalId());
        BigInteger detailEnd = new BigInteger(vo.getEndLogicalId());
        if (pos != null && pos.size() != 0) {
            int i = 1;
            for (TicketStorageOutStorageInfo po : pos) {
                //找到盒中的卡号段
                BigInteger boxStart = new BigInteger(po.getStartLogicalId());
                BigInteger boxEnd = new BigInteger(po.getEndLogicalId());
                if(i==1){
                  BigInteger tmp = detailStart.subtract(boxStart);
                  //第一盒开始段比插入开始段小
                  if(tmp.intValue()>0){
                      boxStart = detailStart;
                  }
                }
                //最后一盒结束逻辑卡号比插入结束段大
                if(i==pos.size()){
                    BigInteger tmp = detailEnd.subtract(boxEnd);
                    if(tmp.intValue() < 0){
                        boxEnd = detailEnd;
                    }
                }

                BigInteger num = boxEnd.subtract(boxStart);
                n += num.intValue() + 1;
                i +=1;
            }
        }
        if (n == 0) {
            throw new Exception("库中赋值区/编码区不存在，有效期："+vo.getValidDate()+"，逻辑卡号段:" + vo.getStartLogicalId() + "-" + vo.getEndLogicalId());
        }
        return n;

    }

    private static void checkLogicalNo(String startLogical, String endLogical) throws Exception {
        if (!(startLogical.length() <= InOutConstant.LEN_LOGICAL_EFFECTIVE && startLogical.length() >= InOutConstant.LEN_LOGICAL_EFFECTIVE_MIN)) {
            throw new Exception(startLogical + "长度不为16-20位");
        }
        if (!(endLogical.length() <= InOutConstant.LEN_LOGICAL_EFFECTIVE && endLogical.length() >= InOutConstant.LEN_LOGICAL_EFFECTIVE_MIN)) {
            throw new Exception(endLogical + "长度不为16-20位");
        }

    }

    public static int calculateSection(String startLogical, String endLogical) throws Exception {
        checkLogicalNo(startLogical, endLogical);
        int n = 0;
        BigInteger start = new BigInteger(startLogical);
        BigInteger end = new BigInteger(endLogical);
        BigInteger num = end.subtract(start);
        n += num.intValue() + 1;
        return n;
    }

     public void checkAreaAndCardMianType(String areaId, String icMainType) throws Exception {
        if (InOutConstant.AREA_ENCODE.equals(areaId) || InOutConstant.AREA_VALUE.equals(areaId)) {
            if (InOutConstant.AREA_ENCODE.equals(areaId) && InOutConstant.IC_MAIN_TYPE_SJT.equals(icMainType)) {
                throw new Exception("按逻辑卡号出库要求编码区（单程票除外）、赋值区");
            }
        } else {
            throw new Exception("按逻辑卡号出库要求编码区（单程票除外）、赋值区");
        }
    }
     public  void checkAudit(HttpServletRequest request) throws Exception {
    	 String recodeFlag = FormUtil.getParameter(request, "billRecordFlag");
    	 if(InOutConstant.RECORD_FLAG_BILL_EFFECT.equals(recodeFlag)) {
    		 throw new Exception("明细单已审核，无法操作");
    	 }
     }

}
