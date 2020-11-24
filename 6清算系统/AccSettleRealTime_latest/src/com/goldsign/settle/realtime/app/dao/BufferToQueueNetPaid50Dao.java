/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.dao;

import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
import com.goldsign.settle.realtime.frame.dao.BufferToQueueBaseDao;
import com.goldsign.settle.realtime.frame.vo.ResultFromProc;
import com.goldsign.settle.realtime.frame.vo.SynchronizedControl;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class BufferToQueueNetPaid50Dao extends BufferToQueueBaseDao{
     private static Logger logger = Logger.getLogger(BufferToQueueNetPaid50Dao.class.getName());
    private static final SynchronizedControl SYN = new SynchronizedControl();
    @Override
    public ResultFromProc bufToQueue(String balanceWaterNo,int balanceWaterNoSub, String fileName) throws Exception {
       // synchronized (SYN) {
            return this.bufToQueueCommon(balanceWaterNo,balanceWaterNoSub, fileName,  FrameDBConstant.DB_ST +"up_st_np_trf_buf_queue_sale");
       // }

    }
    
}
