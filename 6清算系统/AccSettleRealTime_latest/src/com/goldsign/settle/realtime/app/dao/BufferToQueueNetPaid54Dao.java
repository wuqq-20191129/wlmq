/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.dao;

import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
import com.goldsign.settle.realtime.frame.dao.BufferToQueueBaseDao;
import com.goldsign.settle.realtime.frame.vo.ResultFromProc;
import com.goldsign.settle.realtime.frame.vo.SynchronizedControl;

/**
 *
 * @author hejj
 */
public class BufferToQueueNetPaid54Dao extends BufferToQueueBaseDao {
    private static final SynchronizedControl SYN = new SynchronizedControl();
    @Override
    public ResultFromProc bufToQueue(String balanceWaterNo,int balanceWaterNoSub, String fileName) throws Exception {
        //synchronized (SYN) {
            return this.bufToQueueCommon(balanceWaterNo,balanceWaterNoSub, fileName, FrameDBConstant.DB_ST + "up_st_np_trf_buf_queue_deal");
       // }
        //return this.bufToQueueCommon(balanceWaterNo, fileName,"st_pck_settle_pre.pck_up_st_trf_buf_queue_deal");
    }
    
}
