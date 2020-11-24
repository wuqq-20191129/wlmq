/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.parser;

import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.handler.HandlerBase;
import com.goldsign.settle.realtime.frame.message.MessageBase;

/**
 *
 * @author hejj
 */
public class FileRecordParserConstantBase extends FileRecordParserBase{
    /*
    一卡通优惠缺省值
    */
    public  String DEF_OCT_DISC_YEAR_MONTH="197101";//优惠月
    public  int DEF_OCT_DISC_ACCUMUlATE_CONSUME_FEE=0;//优惠月累计消费金额
    public  int DEF_OCT_DISC_INTERVAL_BETWEEN_BUS_METRO=0;//联乘时间间隔
    public  String DEF_OCT_DISC_LAST_BUS_DEAL_DATETIME="19710101000000";//上次公交交易时间
    
    public  int DEF_OCT_DISC_ACCUMUlATE_CONSUME_NUM=0;//优惠月累计消费次数
    public  String DEF_TCT_ACTIVATE_DATETIME="19710101000000";//次票激活时间
    /*
    订单
    */
   public  String DEF_ORDER_NO="00000000000000";//订单号
    /*
    发码平台缺省
    */
   public  String DEF_ISSUE_QRCODE_PLATFORM_FLAG="00";//发码平台
    


    @Override
    public void handleMessage(MessageBase msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object parse(String line, FileRecordAddVo lineAdd) throws RecordParseForFileException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
