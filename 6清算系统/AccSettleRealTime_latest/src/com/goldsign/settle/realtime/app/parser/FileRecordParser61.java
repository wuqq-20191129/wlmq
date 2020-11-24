/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.parser;

import com.goldsign.settle.realtime.app.vo.FileRecord00DetailResult;
import com.goldsign.settle.realtime.app.vo.FileRecord59;
import com.goldsign.settle.realtime.app.vo.FileRecord61;
import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.handler.HandlerBase;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.parser.FileRecordParserBase;
import com.goldsign.settle.realtime.frame.util.DateHelper;
import com.goldsign.settle.realtime.frame.util.TradeUtil;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;

/**
 *
 * @author hejj
 */
//行政处理
public class FileRecordParser61 extends FileRecordParserBase{

    @Override
    public Object parse(String line, FileRecordAddVo lineAdd) throws RecordParseForFileException {
        FileRecord61 r = new FileRecord61();
        FileRecord00DetailResult result;
        char[] b;
        int offset = 0;

        try {
            b = this.getLineCharFromFile(line);

            //获取设备相关信息
            result = this.getInfoForDevice(r, b, offset);
            offset = result.getOffsetTotal();
            //获取交易相关信息
            result = this.getInfoForTrade(r, b, offset);
            offset = result.getOffsetTotal();



            //获取附加信息
            this.addCommonInfo(r, lineAdd);
            
            //校验数据合法性，如果数据不合法，数据插入错误表
            this.setCheckInfo(r);
            if (!this.checkData(r)) {
                return null;
            }






        } catch (Exception ex) {
            throw new RecordParseForFileException(ex.getMessage());
        }

        return r;
    }
    
    private void setCheckInfo(FileRecord61 r) {
        
        r.setCheckDatetime(r.getAdminDatetime());//交易时间
        r.setCheckUniqueKey(this.getCheckUniqueKey(r));//唯一性校验主健
       
        
    }
    private String getCheckUniqueKey(FileRecord61 r){
         //唯一性校验主健
         //线路+车站+设备类型+设备ID+处理时间
         String key =r.getLineId()+r.getStationId()+
                     r.getDevTypeId()+r.getDeviceId()+
                     r.getAdminDatetime();
         return key;
     }

    @Override
    public void handleMessage(MessageBase msg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private FileRecord00DetailResult getInfoForDevice(FileRecord61 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 2;
        r.setTrdType(this.getCharString(b, offset, len));//1交易类型
        offset += len;
        
        len = 2;
        r.setRecordVer(this.getCharString(b, offset, len));//记录版本20180528 by hejj
        offset += len;
        

        len = 2;
        r.setLineId(this.getCharString(b, offset, len));//2线路代码
        offset += len;

        len = 2;
        r.setStationId(this.getCharString(b, offset, len));//2站点代码
        offset += len;
        len = 2;
        r.setDevTypeId(this.getCharString(b, offset, len));//3设备类型
        offset += len;

        len = 3;
        r.setDeviceId(this.getCharString(b, offset, len));//4设备编号
        offset += len;


        len = 7;
        r.setAdminDatetime(this.getBcdString(b, offset, len));//5日期时间
        r.setAdminDatetime_s(DateHelper.convertStandFormatDatetime(r.getAdminDatetime()));
        offset += len;
        
        len = 1;
        r.setAdminWayId(this.getBcdString(b, offset, len));//6事务代码
        offset += len;

        len = 1;
        r.setCardMainId(this.getBcdString(b, offset, len));//7票卡主类型
        offset += len;

        len = 1;
        r.setCardSubId(this.getBcdString(b, offset, len));//7票卡子类型
        offset += len;

        



        result.setOffsetTotal(offset);
        return result;
    }

    private FileRecord00DetailResult getInfoForTrade(FileRecord61 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();
        
        
        int len = 2;
        r.setReturnFee(this.getShort(b, offset));//8退给乘客的金额
        r.setReturnFee_s(TradeUtil.convertFenToYuan(r.getReturnFee()).toString());
        offset += len;

        len = 2;
        r.setPenaltyFee(this.getShort(b, offset));//9收取的罚金
        r.setPenaltyFee_s(TradeUtil.convertFenToYuan(r.getPenaltyFee()).toString());
        offset += len;
        
       

        len = 1;
        r.setAdminReasonId(trim(this.getBcdString(b, offset,len)));//10行政处理原因
        offset += len;
        
        len = 60;
        //hejj 20190103 删除特殊符号','、'，'
        r.setDescribe(this.removeSpecialSign(trim(this.getCharString(b, offset,len))));//11行政处理描述
      //  r.setDescribe(this.getCharStringForSpecial(b, offset,len));//11行政处理描述
        offset += len;


         len = 30;
          //hejj 20190103 删除特殊符号','、'，'
        r.setPassengerName(this.removeSpecialSign(trim(this.getCharString(b, offset,len))));//12乘客姓名
        offset += len;
        
        len = 6;
        r.setOperatorId(this.getCharString(b, offset, len));//13操作员代码
        offset += len;

        len = 1;
        r.setShiftId(this.getBcdString(b, offset, len));//14BOM班次序号
        offset += len;
        
        
        
        
       

        result.setOffsetTotal(offset);
        return result;

    }
    
    public static void main(String[] strs){
        FileRecordParser61 fp61 =new FileRecordParser61();
        String str="我是中国人,我爱中国，我也爱世界。";
        str =fp61.removeSpecialSign(str);
        System.out.println(str);
    }


    
    
}
