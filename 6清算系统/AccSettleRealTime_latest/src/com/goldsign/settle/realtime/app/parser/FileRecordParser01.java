/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.parser;

import com.goldsign.settle.realtime.app.vo.FileRecord00;
import com.goldsign.settle.realtime.app.vo.FileRecord00DetailBase;
import com.goldsign.settle.realtime.app.vo.FileRecord00DetailResult;
import com.goldsign.settle.realtime.app.vo.FileRecord01;
import com.goldsign.settle.realtime.app.vo.FileRecord02;
import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.handler.HandlerBase;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.parser.FileRecordParserBase;
import com.goldsign.settle.realtime.frame.util.DateHelper;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.util.HashMap;
import java.util.Vector;

/**
 *12.2. TVM硬币箱数据
 * @author hejj
 */
public class FileRecordParser01 extends FileRecordParserBase{

    @Override
    public Object parse(String line, FileRecordAddVo lineAdd) throws RecordParseForFileException {
        FileRecord01 r = new FileRecord01();

        FileRecord00DetailResult details;
        HashMap subRecords = new HashMap();
        char[] b;
        int offset = 0;
        try {
            b = this.getLineCharFromFile(line);

            //获取公共信息
            details = this.getInfoForCommon(r, b, offset);
            offset = details.getOffsetTotal();
            //硬币单位金额
           // details = this.getInfoForCoinFeeUnit(r, b, offset);
           // offset = details.getOffsetTotal();
            //硬币单位数量
           // details = this.getInfoForCoinNum(r, b, offset);
            //offset = details.getOffsetTotal();    
            details = this.getInfoForSub(r, b, offset,lineAdd);
            offset = details.getOffsetTotal();
            this.addSubRecords(subRecords, r.getTrdType(), HandlerBase.TRAD_SUFIX[0], r.getDetail());

            
            //汇总
            details = this.getInfoForCoinFeeTotal(r, b, offset);
            offset = details.getOffsetTotal();
            
            
            //获取附加信息
            this.addCommonInfo(r, lineAdd);
             //获取分记录信息
            r.setSubRecords(subRecords);


        } catch (Exception ex) {
            throw new RecordParseForFileException(ex.getMessage());
        }

        return r;
    }
/*
    private FileRecord00DetailResult getInfoForSub(FileRecord01 r, char[] b, int offset,FileRecordAddVo lineAdd) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 1;
        int count = this.getInt(b, offset);
        offset += len;

        FileRecord00DetailResult details = this.getDetailForBase(b, count, offset,lineAdd);//面值、数量
        offset = details.getOffsetTotal();
       // r.setSaleTotalNum(details.getTotalNum());//发售总数量
        r.setDetail(details.getDetails());//面值、数量明细

       // len = 4;
      //  r.setSaleTotalFee(this.getLong(b, offset));//11发售总金额
       // offset += len;

        result.setOffsetTotal(offset);
        return result;
    }
    */
    /*
    private FileRecord00DetailResult getDetailForSub(char[] b, int count, int offset,FileRecordAddVo lineAdd) throws Exception {
        return this.getDetailForBase(b, count, offset,lineAdd);
    }
    */
    /*
    private FileRecord00DetailResult getDetailForBase(char[] b, int count, int offset,FileRecordAddVo lineAdd) throws Exception {
        int len;
        FileRecord00DetailBase fb;
        Vector<FileRecord00DetailBase> v = new Vector();
        FileRecord00DetailResult result = new FileRecord00DetailResult();
        int totalNum = 0;

        for (int i = 0; i < count; i++) {
            fb = new FileRecord00DetailBase();
            this.addCommonInfo(fb, lineAdd);
            
            

            len = 2;
            fb.setFeeUnit(this.getShort(b, offset));//9面值
            offset += len;
           // totalNum += fb.getNum();

            len = 2;
            fb.setNum(this.getShort(b, offset));//数量
            offset += len;
            totalNum += fb.getNum();
            
            v.add(fb);
        }
        result.setDetails(v);
        result.setOffsetTotal(offset);
        result.setTotalNum(totalNum);
        return result;
    }
*/
    @Override
    public void handleMessage(MessageBase msg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private FileRecord00DetailResult getInfoForCommon(FileRecord01 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 1;
        r.setTrdType(this.getBcdString(b, offset, len));//交易类型
        offset += len;
        len = 2;
        r.setLineId(this.getCharString(b, offset, len));//线路代码
        offset += len;
        len = 2;
        r.setStationId(this.getCharString(b, offset, len));//站点代码
        offset += len;
        len = 2;
        r.setDevTypeId(this.getCharString(b, offset, len));//设备类型
        offset += len;
        len = 3;
        r.setDeviceId(this.getCharString(b, offset, len));//设备编号
        offset += len;
       //modify by hejj 20160622 long->char(16)  
        len = 16;
        r.setCoinBoxId(this.getCharString(b, offset,len));//硬币箱编号
        offset += len;
        

        len = 6;
        r.setOperatorId(this.getCharString(b, offset, len));//操作员工号
        offset += len;
        

        len = 7;
        r.setPutDatetime(this.getBcdString(b, offset, len));//硬币箱放入时间
        r.setPutDatetime_s(DateHelper.convertStandFormatDatetime(r.getPutDatetime()));
        offset += len;
        
        len = 7;
        r.setPopDatetime(this.getBcdString(b, offset, len));//硬币箱取出时间
        r.setPopDatetime_s(DateHelper.convertStandFormatDatetime(r.getPopDatetime()));
        offset += len;
        
        len = 2;
        r.setWaterNoOp(this.getShort(b, offset));//流水号 
        offset += len;

        result.setOffsetTotal(offset);
        return result;
    }
    private FileRecord00DetailResult getInfoForCoinFeeUnit(FileRecord01 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 2;
        r.setCoinFee1(this.getShort(b, offset));//硬币1单位金额
        offset += len;
        
        len = 2;
        r.setCoinFee2(this.getShort(b, offset));//硬币2单位金额
        offset += len;
        
        len = 2;
        r.setCoinFee3(this.getShort(b, offset));//硬币3单位金额
        offset += len;
        
        len = 2;
        r.setCoinFee4(this.getShort(b, offset));//硬币4单位金额
        offset += len;
        
        len = 2;
        r.setCoinFee5(this.getShort(b, offset));//硬币5单位金额
        offset += len;
        
        len = 2;
        r.setCoinFee6(this.getShort(b, offset));//硬币6单位金额
        offset += len;
        
        len = 2;
        r.setCoinFee7(this.getShort(b, offset));//硬币7单位金额
        offset += len;
        
        len = 2;
        r.setCoinFee8(this.getShort(b, offset));//硬币8单位金额
        offset += len;
        
        
      
        

        result.setOffsetTotal(offset);
        return result;
    }
    
    private FileRecord00DetailResult getInfoForCoinNum(FileRecord01 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 2;
        r.setCoinNum1(this.getShort(b, offset));//硬币1单位数量
        offset += len;
        
        len = 2;
        r.setCoinNum2(this.getShort(b, offset));//硬币2单位数量
        offset += len;
        
        len = 2;
        r.setCoinNum3(this.getShort(b, offset));//硬币3单位数量
        offset += len;
        
        len = 2;
        r.setCoinNum4(this.getShort(b, offset));//硬币4单位数量
        offset += len;
        
        len = 2;
        r.setCoinNum5(this.getShort(b, offset));//硬币5单位数量
        offset += len;
        
        len = 2;
        r.setCoinNum6(this.getShort(b, offset));//硬币6单位数量
        offset += len;
        
        len = 2;
        r.setCoinNum7(this.getShort(b, offset));//硬币7单位数量
        offset += len;
        
        len = 2;
        r.setCoinNum8(this.getShort(b, offset));//硬币8单位数量
        offset += len;
        
        
        

        result.setOffsetTotal(offset);
        return result;
    }
    private FileRecord00DetailResult getInfoForCoinFeeTotal(FileRecord01 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 4;
        r.setCoinFeeTotal(this.getLong(b, offset));//钱箱金额
        offset += len;
        
       
        result.setOffsetTotal(offset);
        return result;
    }

    @Override
    public boolean checkData(FileRecordBase frb) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
