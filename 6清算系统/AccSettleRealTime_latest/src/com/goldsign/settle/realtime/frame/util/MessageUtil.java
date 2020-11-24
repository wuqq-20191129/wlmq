/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.util;

import com.goldsign.settle.realtime.frame.constant.FrameCodeConstant;
import com.goldsign.settle.realtime.frame.constant.FrameThreadConstant;
import com.goldsign.settle.realtime.frame.factory.ThreadFactory;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.message.MessageBcp;
import com.goldsign.settle.realtime.frame.message.MessageTac;
import com.goldsign.settle.realtime.frame.vo.BcpAttribute;
import com.goldsign.settle.realtime.frame.vo.MessageAttribute;
import com.goldsign.settle.realtime.frame.vo.TaskFinishControl;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class MessageUtil {
    public void putMessageToQueueForFile(String fileName,String balanceWaterNo,int balanceWaterNoSub,TaskFinishControl tfc){
        ThreadFactory fac = new ThreadFactory();
        MessageBase  msg = new MessageBase(FrameCodeConstant.PATH_FILE_TRX,
                FrameCodeConstant.PATH_FILE_TRX_BCP,FrameCodeConstant.PATH_FILE_TRX_HIS,
                FrameCodeConstant.PATH_FILE_TRX_HIS_ERROR,
                fileName,balanceWaterNo,balanceWaterNoSub,tfc,null);
        fac.putMessageToQueue(FrameThreadConstant.THREAD_POOL_FILE_ID, msg);
        
        
    }
    public void putMessageToQueueForFileMobile(String fileName,String balanceWaterNo,int balanceWaterNoSub,TaskFinishControl tfc){
        ThreadFactory fac = new ThreadFactory();
        MessageBase  msg = new MessageBase(FrameCodeConstant.PATH_FILE_TRX,
                FrameCodeConstant.PATH_FILE_TRX_BCP,FrameCodeConstant.PATH_FILE_TRX_HIS,
                FrameCodeConstant.PATH_FILE_TRX_HIS_ERROR,
                fileName,balanceWaterNo,balanceWaterNoSub,tfc,null);
        fac.putMessageToQueue(FrameThreadConstant.THREAD_POOL_FILE_MOBILE_ID, msg);
        
        
    }
    public void putMessageToQueueForFileQrCode(String fileName,String balanceWaterNo,int balanceWaterNoSub,TaskFinishControl tfc){
        ThreadFactory fac = new ThreadFactory();
        MessageBase  msg = new MessageBase(FrameCodeConstant.PATH_FILE_TRX,
                FrameCodeConstant.PATH_FILE_TRX_BCP,FrameCodeConstant.PATH_FILE_TRX_HIS,
                FrameCodeConstant.PATH_FILE_TRX_HIS_ERROR,
                fileName,balanceWaterNo,balanceWaterNoSub,tfc,null);
        fac.putMessageToQueue(FrameThreadConstant.THREAD_POOL_FILE_QRCODE_ID, msg);
        
        
    }
    
    public void putMessageToQueueForFileNetPaid(String fileName,String balanceWaterNo,int balanceWaterNoSub,TaskFinishControl tfc){
        ThreadFactory fac = new ThreadFactory();
        MessageBase  msg = new MessageBase(FrameCodeConstant.PATH_FILE_TRX,
                FrameCodeConstant.PATH_FILE_TRX_BCP,FrameCodeConstant.PATH_FILE_TRX_HIS,
                FrameCodeConstant.PATH_FILE_TRX_HIS_ERROR,
                fileName,balanceWaterNo,balanceWaterNoSub,tfc,null);
        fac.putMessageToQueue(FrameThreadConstant.THREAD_POOL_FILE_NETPAID_ID, msg);
        
        
    }
    
    public void putMessageToQueueForFileOther(String fileName,String balanceWaterNo,int balanceWaterNoSub,TaskFinishControl tfc){
        ThreadFactory fac = new ThreadFactory();
        MessageBase  msg = new MessageBase(FrameCodeConstant.PATH_FILE_TRX,
                FrameCodeConstant.PATH_FILE_TRX_BCP,FrameCodeConstant.PATH_FILE_TRX_HIS,
                FrameCodeConstant.PATH_FILE_TRX_HIS_ERROR,
                fileName,balanceWaterNo,balanceWaterNoSub,tfc,null);
        String fileType=FileUtil.getFileType(fileName);
        msg.setFileType(fileType);
        fac.putMessageToQueue(FrameThreadConstant.THREAD_POOL_OTHER_ID, msg);
        
        
    }
    public void putMessageToQueueForFileOtherMobile(String fileName,String balanceWaterNo,int balanceWaterNoSub,TaskFinishControl tfc){
        ThreadFactory fac = new ThreadFactory();
        MessageBase  msg = new MessageBase(FrameCodeConstant.PATH_FILE_TRX,
                FrameCodeConstant.PATH_FILE_TRX_BCP,FrameCodeConstant.PATH_FILE_TRX_HIS,
                FrameCodeConstant.PATH_FILE_TRX_HIS_ERROR,
                fileName,balanceWaterNo,balanceWaterNoSub,tfc,null);
        String fileType=FileUtil.getFileType(fileName);
        msg.setFileType(fileType);
        fac.putMessageToQueue(FrameThreadConstant.THREAD_POOL_OTHER_MOBILE_ID, msg);
        
        
    }
    public void putMessageToQueueForFileOtherQrCode(String fileName,String balanceWaterNo,int balanceWaterNoSub,TaskFinishControl tfc){
        ThreadFactory fac = new ThreadFactory();
        MessageBase  msg = new MessageBase(FrameCodeConstant.PATH_FILE_TRX,
                FrameCodeConstant.PATH_FILE_TRX_BCP,FrameCodeConstant.PATH_FILE_TRX_HIS,
                FrameCodeConstant.PATH_FILE_TRX_HIS_ERROR,
                fileName,balanceWaterNo,balanceWaterNoSub,tfc,null);
        String fileType=FileUtil.getFileType(fileName);
        msg.setFileType(fileType);
        fac.putMessageToQueue(FrameThreadConstant.THREAD_POOL_OTHER_QRCODE_ID, msg);
        
        
    }
    public void putMessageToQueueForFileOtherNetPaid(String fileName,String balanceWaterNo,int balanceWaterNoSub,TaskFinishControl tfc){
        ThreadFactory fac = new ThreadFactory();
        MessageBase  msg = new MessageBase(FrameCodeConstant.PATH_FILE_TRX,
                FrameCodeConstant.PATH_FILE_TRX_BCP,FrameCodeConstant.PATH_FILE_TRX_HIS,
                FrameCodeConstant.PATH_FILE_TRX_HIS_ERROR,
                fileName,balanceWaterNo,balanceWaterNoSub,tfc,null);
        String fileType=FileUtil.getFileType(fileName);
        msg.setFileType(fileType);
        fac.putMessageToQueue(FrameThreadConstant.THREAD_POOL_OTHER_NETPAID_ID, msg);
        
        
    }
    
    public void putMessageToQueueForFileOctImportSettle(String fileName,String balanceWaterNo,int balanceWaterNoSub,TaskFinishControl tfc){
        ThreadFactory fac = new ThreadFactory();
         MessageAttribute ma = new MessageAttribute();
        ma.setSubTradeType(FrameCodeConstant.SUB_TRADE_TYPE_SETTLE);
        MessageBase  msg = new MessageBase(FrameCodeConstant.PATH_FILE_OCT_IMPORT_SETTLE_FILE,
                FrameCodeConstant.PATH_FILE_TRX_BCP,FrameCodeConstant.PATH_FILE_OCT_IMPORT_SETTLE_FILE_HIS,
                FrameCodeConstant.PATH_FILE_OCT_IMPORT_SETTLE_FILE_ERROR,
                fileName,balanceWaterNo,balanceWaterNoSub,tfc,ma);
        String fileType=FileUtil.getFileTypeForOctImport(fileName);
        msg.setFileType(fileType);
        fac.putMessageToQueue(FrameThreadConstant.THREAD_POOL_OTHER_ID, msg);
        
        
    }
    public void putMessageToQueueForFileOctImportTrx(String fileName,String balanceWaterNo,int balanceWaterNoSub,TaskFinishControl tfc){
        ThreadFactory fac = new ThreadFactory();
        MessageAttribute ma = new MessageAttribute();
        ma.setSubTradeType(FrameCodeConstant.SUB_TRADE_TYPE_TRX);
        MessageBase  msg = new MessageBase(FrameCodeConstant.PATH_FILE_OCT_IMPORT_TRX_FILE,
                FrameCodeConstant.PATH_FILE_TRX_BCP,FrameCodeConstant.PATH_FILE_OCT_IMPORT_TRX_FILE_HIS,
                FrameCodeConstant.PATH_FILE_OCT_IMPORT_TRX_FILE_ERROR,
                fileName,balanceWaterNo,balanceWaterNoSub,tfc,ma);
        String fileType=FileUtil.getFileTypeForOctImport(fileName);
        msg.setFileType(fileType);
        fac.putMessageToQueue(FrameThreadConstant.THREAD_POOL_OTHER_ID, msg);
        
        
    }
    
     public MessageBase putMessageToQueueForBcp(String fileNameBcp,String tradType,String balanceWaterNo,BcpAttribute attr){
        ThreadFactory fac = new ThreadFactory();
        MessageBase  msg = new MessageBcp(fileNameBcp,tradType,balanceWaterNo,attr);
        fac.putMessageToQueueForBcp(FrameThreadConstant.THREAD_POOL_BCP_ID, msg);
        return msg;
        
        
    }
     public MessageBase putMessageToQueueForTac(String fileName,String tradType,Vector datas){
        ThreadFactory fac = new ThreadFactory();
        MessageBase  msg = new MessageTac(fileName,tradType,datas);
        fac.putMessageToQueueForTac(FrameThreadConstant.THREAD_POOL_TAC_ID, msg);
        return msg;
        
        
    }
    
}
