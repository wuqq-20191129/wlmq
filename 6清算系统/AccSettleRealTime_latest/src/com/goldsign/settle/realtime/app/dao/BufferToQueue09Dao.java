/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.dao;

import com.goldsign.settle.realtime.frame.constant.FrameDBConstant;
import com.goldsign.settle.realtime.frame.dao.BufferToQueueBaseDao;
import com.goldsign.settle.realtime.frame.vo.ResultFromProc;

/**
 *
 * @author hejj
 */
public class BufferToQueue09Dao extends BufferToQueueBaseDao{

    @Override
    public ResultFromProc bufToQueue(String balanceWaterNo, int balanceWaterNoSub,String fileName) throws Exception {
        return this.bufToQueueCommon(balanceWaterNo,balanceWaterNoSub, fileName, "up_st_trf_pbtoq_tvm_ntput");
    }
    
}
