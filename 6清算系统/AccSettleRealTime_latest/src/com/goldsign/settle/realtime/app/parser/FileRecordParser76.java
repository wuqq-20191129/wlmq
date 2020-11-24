/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.parser;

import com.goldsign.settle.realtime.app.vo.FileRecord76;
import com.goldsign.settle.realtime.app.vo.FileRecord00DetailResult;

import com.goldsign.settle.realtime.app.vo.FileRecordAddVo;

import com.goldsign.settle.realtime.frame.constant.FrameCheckConstant;
import com.goldsign.settle.realtime.frame.exception.RecordParseForFileException;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.parser.FileRecordParserBase;
import static com.goldsign.settle.realtime.frame.parser.FileRecordParserBase.trim;
import com.goldsign.settle.realtime.frame.util.DateHelper;
import com.goldsign.settle.realtime.frame.util.TradeUtil;
import com.goldsign.settle.realtime.frame.vo.CheckData;

/**
 *
 * @author hejj
 */
public class FileRecordParser76 extends FileRecordParserBase{
     @Override
    public Object parse(String line, FileRecordAddVo lineAdd) throws RecordParseForFileException {
        FileRecord76 r = new FileRecord76();
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
            
            //获取二维码平台相关信息
            result = this.getInfoForQrCode(r, b, offset);
            offset = result.getOffsetTotal();



            //获取附加信息
            this.addCommonInfo(r, lineAdd);
            //校验数据合法性，如果数据不合法，数据插入错误表
            //20140519 处理读写器写更新区域乱码问题，所有非法字符统一转为单字符'A'
           // this.handleErrorForUpdateArea(r);
            
            this.setCheckInfo(r);
            if (!this.checkData(r)) {
                return null;
            }

        } catch (Exception ex) {
            throw new RecordParseForFileException(ex.getMessage());
        }

        return r;
    }
    private void handleErrorForUpdateArea(FileRecord76 r){
        if(!this.isInDefine(r.getUpdateArea(), FrameCheckConstant.CHK_UPDATE_AREA))
            r.setUpdateArea(FrameCheckConstant.CHK_UPDATE_AREA_ERROR);
    }

    private void setCheckInfo(FileRecord76 r) {


        r.setCheckDatetime(r.getUpdateDatetime());//交易时间
        r.setCheckUpdateArea(r.getUpdateArea());//更新区域
        CheckData cd = new CheckData();//使用通用对象
        cd.setUpdateArea(r.getUpdateArea());
        r.setCheckDataOther(cd);
        r.setCheckUniqueKey(this.getCheckUniqueKey(r));//唯一性校验主健
        


    }
    private String getCheckUniqueKey(FileRecord76 r){
         //唯一性校验主健
         //票卡类型+SAM逻辑卡号+SAM卡交易序号+交易时间
         String key =r.getCardMainId()+r.getCardSubId()+r.getCardLogicalId()+
                     r.getSamLogicalId()+r.getSamTradeSeq()+
                     r.getUpdateDatetime();
         return key;
     }

    @Override
    public void handleMessage(MessageBase msg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    protected FileRecord00DetailResult getInfoForQrCode(FileRecord76 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 40;
        r.setBusinessWaterNo(this.getCharString(b, offset, len));//业务流水
        offset += len;

        len = 40;
        r.setBusinessWaterNoRel(this.getCharString(b, offset, len));//相关业务流水
        offset += len;

        result.setOffsetTotal(offset);
        return result;

    }

    private FileRecord00DetailResult getInfoForDevice(FileRecord76 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 2;
        r.setTrdType(this.getCharString(b, offset, len));//1交易类型
       // r.setTrdType(getTrdTypeForQrCode(this.getCharString(b, offset, len)));//1交易类型 增加QrCode标识
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

        len = 16;
        r.setSamLogicalId(trim(this.getCharString(b, offset, len)));//5SAM卡逻辑卡号
        offset += len;

        len = 4;
        r.setSamTradeSeq(this.getLong(b, offset));//6本次交易SAM卡脱机交易流水号
        r.setSamTradeSeq_s(Long.toString(r.getSamTradeSeq()));
        offset += len;



        len = 1;
        r.setCardMainId(this.getBcdString(b, offset, len));//7票卡主类型
        offset += len;

        len = 1;
        r.setCardSubId(this.getBcdString(b, offset, len));//7票卡子类型
        offset += len;

        len = 20;
        r.setCardLogicalId(trim(this.getCharString(b, offset, len)));//8票卡逻辑卡号
        offset += len;

        len = 20;
        r.setCardPhysicalId(trim(this.getCharString(b, offset, len)));//9票卡物理卡号
        offset += len;

        len = 4;
        r.setCardConsumeSeq(this.getLong(b, offset));//10票卡扣款交易计数
        r.setCardConsumeSeq_s(Long.toString(r.getCardConsumeSeq()));
        offset += len;

        len = 1;
        r.setCardStatusId(this.getInt(b, offset));//11卡状态代码
        offset += len;




        result.setOffsetTotal(offset);
        return result;
    }

    private FileRecord00DetailResult getInfoForTrade(FileRecord76 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 1;
        r.setUpdateArea(this.getCharString(b, offset, len));//12更新区域
        offset += len;

        len = 1;
        r.setUpdateReasonId(this.getBcdString(b, offset, len));//13更新原因
        offset += len;

        len = 7;
        r.setUpdateDatetime(this.getBcdString(b, offset, len));//14更新日期时间
        r.setUpdateDatetime_s(DateHelper.convertStandFormatDatetime(r.getUpdateDatetime()));
        offset += len;
        len = 1;
        r.setPayType(this.getBcdString(b, offset, len));//15支付方式
        offset += len;

        len = 2;
        r.setPenaltyFee(this.getShort(b, offset));//16罚款金额
        r.setPenaltyFee_s(TradeUtil.convertFenToYuan(r.getPenaltyFee()).toString());
        offset += len;
        //设置通用金额
        //r.setCommonFeeByFen(r.getPenaltyFee(), 0);

        len = 4;
        r.setReceiptId(this.getCharString(b, offset, len));//17凭证ID
        offset += len;

        len = 6;
        r.setOperatorId(this.getCharString(b, offset, len));//18操作员代码
        offset += len;


        len = 1;
        r.setEntryLineId(this.getBcdString(b, offset, len));//19入口线路代码
        offset += len;

        len = 1;
        r.setEntryStationId(this.getBcdString(b, offset, len));//19入口站点代码
        offset += len;

        len = 1;
        r.setShiftId(this.getBcdString(b, offset, len));//20BOM班次序号
        offset += len;

        len = 1;
        r.setCardAppFlag(this.getCharString(b, offset, len));//21卡应用标识
        offset += len;

        len = 3;
        r.setLimitMode(this.getCharString(b, offset, len));//22限制使用模式
        offset += len;

        len = 2;
        r.setLimitEntryStation(this.getBcdString(b, offset, len));//23限制进站代码
        offset += len;

        len = 2;
        r.setLimitExitStation(this.getBcdString(b, offset, len));//24限制出站代码
        offset += len;

        len = 1;
        r.setCardAppMode(this.getCharString(b, offset, len));//25卡应用模式
        offset += len;
        
        len = 7;
        r.setTctActiveDatetime(this.getBcdString(b, offset, len));//乘车票激活日期时间
        r.setTctActiveDatetime_s(DateHelper.convertStandFormatDatetime(r.getTctActiveDatetime()));
        offset += len;

        result.setOffsetTotal(offset);
        return result;

    }
    
}
