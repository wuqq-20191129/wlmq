/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.dao;

import com.goldsign.settle.realtime.frame.dao.BufferToQueueBaseDao;
import com.goldsign.settle.realtime.frame.vo.ResultFromProc;

/**
 *
 * @author hejj
 */
public class BufferToQueue19Dao extends BufferToQueueBaseDao{
    @Override
    public ResultFromProc bufToQueue(String balanceWaterNo,int balanceWaterNoSub, String fileName) throws Exception {
     //   synchronized (SYN) {
            return this.bufToQueueCommon(balanceWaterNo,balanceWaterNoSub, fileName, "up_st_trf_pbtoq_itm_bal");
      //  }
    }
    
}
