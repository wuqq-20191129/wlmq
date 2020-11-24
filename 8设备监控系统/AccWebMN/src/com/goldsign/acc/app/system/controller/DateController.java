/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.acc.app.system.controller;

import com.goldsign.acc.app.system.entity.Date;
import com.goldsign.acc.app.system.mapper.DateMapper;

import com.goldsign.acc.frame.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;


/**
 *
 * 文件时间判断
 *
 * @author luck
 */
@Controller
public class DateController extends BaseController {

    @Autowired
    protected DateMapper dMapper;

   
    public boolean isOldFile(String fileDate, String fileName) throws Exception {
       Date date = new Date();
       date.setFileName(fileName);
       date.setLatestTime(fileDate);
        try {
           int n =  dMapper.select(date);
           if(n > 0){
               return true;
           }
        } catch (Exception e) {
            e.printStackTrace();
            
        }
        
        return false;

    }

    public int addFile(String fileDate, String fielName) throws Exception {
        TransactionStatus status = null;
        System.out.println("*********come into addFile**********");
        int n = 0;
        Date vo = new Date();
        vo.setFileName(fielName);
        vo.setLatestTime(fileDate);
        try {         
            status = txMgr.getTransaction(this.def);
            n = dMapper.update(vo);
            System.out.println("n====>"+n);
            if (n != 1) {
                int insertIntoCur = dMapper.insertIntoCur(vo);
                System.out.println("insertIntoCur====>"+insertIntoCur);
            }
            txMgr.commit(status);
        } catch (Exception e) {
            if (txMgr != null) {
                txMgr.rollback(status);
            }
            e.printStackTrace();
//            throw e;
        } finally {
            return n;
        }
    }



}
