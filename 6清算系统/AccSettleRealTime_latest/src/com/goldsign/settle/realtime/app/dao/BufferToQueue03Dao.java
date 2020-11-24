/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.dao;

import com.goldsign.settle.realtime.frame.dao.BufferToQueueBaseDao;
import com.goldsign.settle.realtime.frame.vo.ResultFromProc;
import com.goldsign.settle.realtime.frame.vo.SynchronizedControl;

/**
 *
 * @author hejj
 */
public class BufferToQueue03Dao extends BufferToQueueBaseDao {

    private static final SynchronizedControl SYN = new SynchronizedControl();

    @Override
    public ResultFromProc bufToQueue(String balanceWaterNo,int balanceWaterNoSub, String fileName) throws Exception {
      //  synchronized (SYN) {
            return this.bufToQueueCommon(balanceWaterNo,balanceWaterNoSub, fileName, "up_st_trf_pbtoq_tvm_coput");
      //  }
    }
}
