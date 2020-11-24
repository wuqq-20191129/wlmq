/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.parser;

import com.goldsign.settle.realtime.app.vo.FileRecord00;
import com.goldsign.settle.realtime.app.vo.FileRecord00DetailBase;
import com.goldsign.settle.realtime.app.vo.FileRecord00DetailResult;
import com.goldsign.settle.realtime.app.vo.FileRecord00ForAdmin;
import com.goldsign.settle.realtime.app.vo.FileRecord00ForReturn;
import com.goldsign.settle.realtime.app.vo.FileRecord00ForUpdate;

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
 *
 * @author hejj
 * 12.1. BOM班次数据
 */
public class FileRecordParser00 extends FileRecordParserBase {

    @Override
    public Object parse(String line, FileRecordAddVo lineAdd) throws RecordParseForFileException {
        FileRecord00 r = new FileRecord00();

        FileRecord00DetailResult results;
        HashMap subRecords = new HashMap();
        char[] b;
        int offset = 0;
        try {
            b = this.getLineCharFromFile(line);

            //获取公共信息
            results = this.getInfoForCommon(r, b, offset);
            offset = results.getOffsetTotal();
            //发售
            results = this.getInfoForSale(r, b, offset,lineAdd);
            offset = results.getOffsetTotal();
            this.addSubRecords(subRecords, r.getTradeType(), HandlerBase.TRAD_SUFIX[0], r.getSaleDetail());
            //充值
            results = this.getInfoForCharge(r, b, offset,lineAdd);
            offset = results.getOffsetTotal();
            this.addSubRecords(subRecords, r.getTradeType(), HandlerBase.TRAD_SUFIX[1], r.getChargeDetail());
            //更新
            results = this.getInfoForUpdate(r, b, offset,lineAdd);
            offset = results.getOffsetTotal();
            this.addSubRecords(subRecords, r.getTradeType(), HandlerBase.TRAD_SUFIX[2], r.getUpdateDetail());
            //行政处理
            results = this.getInfoForAdmin(r, b, offset,lineAdd);
            offset = results.getOffsetTotal();
            this.addSubRecords(subRecords, r.getTradeType(), HandlerBase.TRAD_SUFIX[3], r.getAdminDetail());
            //延期处理
            results = this.getInfoForDelay(r, b, offset,lineAdd);
            offset = results.getOffsetTotal();
            this.addSubRecords(subRecords, r.getTradeType(), HandlerBase.TRAD_SUFIX[4], r.getDelayDetail());
            //即时退款处理
            results = this.getInfoForReturn(r, b, offset,lineAdd);
            offset = results.getOffsetTotal();
            this.addSubRecords(subRecords, r.getTradeType(), HandlerBase.TRAD_SUFIX[5], r.getReturnDetail());
            //非即时退款处理
            results = this.getInfoForReturnNon(r, b, offset,lineAdd);
            offset = results.getOffsetTotal();
            this.addSubRecords(subRecords, r.getTradeType(), HandlerBase.TRAD_SUFIX[6], r.getReturnNonDetail());
            //非即时退款申请处理
            results = this.getInfoForReturnNonApp(r, b, offset,lineAdd);
            offset = results.getOffsetTotal();
            this.addSubRecords(subRecords, r.getTradeType(), HandlerBase.TRAD_SUFIX[7], r.getReturnNonAppDetail());
            //冲正
            results = this.getInfoForChargeUpdate(r, b, offset,lineAdd);
            offset = results.getOffsetTotal();
            this.addSubRecords(subRecords, r.getTradeType(), HandlerBase.TRAD_SUFIX[8], r.getChargeUpdateDetail());
            //解锁
            results = this.getInfoForUnlock(r, b, offset,lineAdd);
            offset = results.getOffsetTotal();
            this.addSubRecords(subRecords, r.getTradeType(), HandlerBase.TRAD_SUFIX[9], r.getUnlockDetail());
            //汇总
            results = this.getInfoForTotal(r, b, offset);
            offset = results.getOffsetTotal();

            //获取附加信息
            this.addCommonInfo(r, lineAdd);
          //  r.setBalanceWaterNo(lineAdd.getBalanceWaterNo());
            //r.setFileName(lineAdd.getFileName());
            //获取分记录信息
            r.setSubRecords(subRecords);

        } catch (Exception ex) {
            throw new RecordParseForFileException(ex.getMessage());
        }

        return r;

    }

    private FileRecord00DetailResult getInfoForUpdate(FileRecord00 r, char[] b, int offset,FileRecordAddVo lineAdd) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();
        int len = 1;
        int count = this.getInt(b, offset);
        offset += len;

        FileRecord00DetailResult details = this.getDetailForUpdate(b, count, offset,lineAdd);//更新明细
        offset = details.getOffsetTotal();
        r.setUpdateTotalNum(details.getTotalNum());//更新总数量
        r.setUpdateDetail(details.getDetails());//更新明细

        len = 4;
        r.setUpdateTotalFeeCash(this.getLong(b, offset));//现金更新总金额
        offset += len;

        len = 4;
        r.setUpdateTotalFee(this.getLong(b, offset));//23更新总金额
        offset += len;

        result.setOffsetTotal(offset);
        return result;
    }

    private FileRecord00DetailResult getInfoForAdmin(FileRecord00 r, char[] b, int offset,FileRecordAddVo lineAdd) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();
        int len = 1;
        int count = this.getInt(b, offset);
        offset += len;

        FileRecord00DetailResult details = this.getDetailForAdmin(b, count, offset,lineAdd);//行政处理明细
        offset = details.getOffsetTotal();
        r.setAdminTotalNum(details.getTotalNum());//行政处理总数量
        r.setAdminDetail(details.getDetails());//行政处理明细

        len = 4;
        r.setAdminTotalFeeReturn(this.getLong(b, offset));//30总行政处理退款金额
        offset += len;

        len = 4;
        r.setAdminTotalFeeIncome(this.getLong(b, offset));//31总行政处理收入金额
        offset += len;

        result.setOffsetTotal(offset);
        return result;
    }

    private FileRecord00DetailResult getInfoForDelay(FileRecord00 r, char[] b, int offset,FileRecordAddVo lineAdd) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();
        int len = 1;
        int count = this.getInt(b, offset);
        offset += len;

        FileRecord00DetailResult details = this.getDetailForDelay(b, count, offset,lineAdd);//延期处理明细
        offset = details.getOffsetTotal();
        r.setDelayTotalNum(details.getTotalNum());//延期处理总数量
        r.setDelayDetail(details.getDetails());//延期处理明细

        result.setOffsetTotal(offset);
        return result;
    }

    private FileRecord00DetailResult getInfoForTotal(FileRecord00 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();
        int len = 4;
        r.setTotalNoCashFee(this.getLong(b, offset));//班次应收非现金金额
        offset += len;

        len = 4;
        r.setTotalFee(this.getLong(b, offset));//班次应收金额
        offset += len;
        result.setOffsetTotal(offset);
        return result;

    }

    private FileRecord00DetailResult getInfoForReturn(FileRecord00 r, char[] b, int offset,FileRecordAddVo lineAdd) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();
        int len = 1;
        int count = this.getInt(b, offset);
        offset += len;

        FileRecord00DetailResult details = this.getDetailForReturn(b, count, offset,lineAdd);//即时退款处理明细
        offset = details.getOffsetTotal();
        r.setReturnTotalNum(details.getTotalNum());//即时退款处理总数量
        r.setReturnDetail(details.getDetails());//即时退款处理明细

        len = 4;
        r.setReturnTotalFee(this.getLong(b, offset));//44总退票金额
        offset += len;

        result.setOffsetTotal(offset);
        return result;
    }

    private FileRecord00DetailResult getInfoForReturnNonApp(FileRecord00 r, char[] b, int offset,FileRecordAddVo lineAdd) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();
        int len = 1;
        int count = this.getInt(b, offset);
        offset += len;

        FileRecord00DetailResult details = this.getDetailForReturnNonApp(b, count, offset,lineAdd);//非即时退款处理明细
        offset = details.getOffsetTotal();
        r.setReturnNonTotalNum(details.getTotalNum());//非即时退款处理总数量
        r.setReturnNonAppDetail(details.getDetails());//非即时退款处理明细

       // len = 4;
        //r.setReturnTotalFee(this.getLong(b, offset));//总退票金额
        //offset += len;

        result.setOffsetTotal(offset);
        return result;
    }

    private FileRecord00DetailResult getInfoForReturnNon(FileRecord00 r, char[] b, int offset,FileRecordAddVo lineAdd) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();
        int len = 1;
        int count = this.getInt(b, offset);
        offset += len;

        FileRecord00DetailResult details = this.getDetailForReturnNon(b, count, offset,lineAdd);//非即时退款处理明细
        offset = details.getOffsetTotal();
        r.setReturnNonTotalNum(details.getTotalNum());//非即时退款处理总数量
        r.setReturnNonDetail(details.getDetails());//非即时退款处理明细

        len = 4;
        r.setReturnTotalFee(this.getLong(b, offset));//总退票金额
        offset += len;

        result.setOffsetTotal(offset);
        return result;
    }

    private FileRecord00DetailResult getInfoForChargeUpdate(FileRecord00 r, char[] b, int offset,FileRecordAddVo lineAdd) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();
        int len = 1;
        int count = this.getInt(b, offset);
        offset += len;

        FileRecord00DetailResult details = this.getDetailForChargeUpdate(b, count, offset,lineAdd);//冲正明细
        offset = details.getOffsetTotal();
        r.setChargeUpdateTotalNum(details.getTotalNum());//冲正总数量
        r.setChargeUpdateDetail(details.getDetails());//冲正明细

        len = 4;
        r.setChargeUpdateTotalFee(this.getLong(b, offset));//59冲正总金额
        offset += len;

        result.setOffsetTotal(offset);
        return result;
    }

    private FileRecord00DetailResult getInfoForCharge(FileRecord00 r, char[] b, int offset,FileRecordAddVo lineAdd) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 1;
        int count = this.getInt(b, offset);
        offset += len;

        FileRecord00DetailResult details = this.getDetailForCharge(b, count, offset,lineAdd);//充值明细
        offset = details.getOffsetTotal();
        r.setChargeTotalNum(details.getTotalNum());//充值总数量
        r.setChargeDetail(details.getDetails());//充值明细

        len = 4;
        r.setChargeTotalFee(this.getLong(b, offset));//16充值总金额
        offset += len;


        result.setOffsetTotal(offset);
        return result;
    }

    private FileRecord00DetailResult getInfoForUnlock(FileRecord00 r, char[] b, int offset,FileRecordAddVo lineAdd) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();
        int len = 1;
        int count = this.getInt(b, offset);
        offset += len;

        FileRecord00DetailResult details = this.getDetailForUnlock(b, count, offset,lineAdd);//解锁明细
        offset = details.getOffsetTotal();
        r.setUnlockTotalNum(details.getTotalNum());//解锁总数量
        r.setUnlockDetail(details.getDetails());//

        result.setOffsetTotal(offset);
        return result;
    }

    private FileRecord00DetailResult getInfoForSale(FileRecord00 r, char[] b, int offset,FileRecordAddVo lineAdd) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 1;
        int count = this.getInt(b, offset);
        offset += len;

        FileRecord00DetailResult details = this.getDetailForSale(b, count, offset,lineAdd);//发售明细
        offset = details.getOffsetTotal();
        r.setSaleTotalNum(details.getTotalNum());//发售总数量
        r.setSaleDetail(details.getDetails());//发售明细

        len = 4;
        r.setSaleTotalFee(this.getLong(b, offset));//11发售总金额
        offset += len;

        result.setOffsetTotal(offset);
        return result;
    }

    private FileRecord00DetailResult getInfoForCommon(FileRecord00 r, char[] b, int offset) throws Exception {
        FileRecord00DetailResult result = new FileRecord00DetailResult();

        int len = 1;
        r.setTradeType(this.getBcdString(b, offset, len));//1交易类型
        offset += len;
        len = 2;
        r.setLineId(this.getCharString(b, offset, len));//2线路代码
        offset += len;
        len = 2;
        r.setStationId(this.getCharString(b, offset, len));//2站点代码
        offset += len;
        len = 2;
        r.setDevTypeId(this.getCharString(b, offset, len));//2设备类型
        offset += len;
        len = 3;
        r.setDeviceId(this.getCharString(b, offset, len));//2设备编号
        offset += len;

        len = 6;
        r.setOperatorId(this.getCharString(b, offset, len));//3BOM操作员工号
        offset += len;
        
        len = 1;
        r.setShiftId(this.getBcdString(b, offset, len));//4BOM班次
        offset += len;
        
        len = 7;
        r.setStartTime(this.getBcdString(b, offset, len));//5班次开始时间
        r.setStartTime_s(DateHelper.convertStandFormatDatetime(r.getStartTime()));
        offset += len;
        
        len = 7;
        r.setEndTime(this.getBcdString(b, offset, len));//6班次结束时间
        r.setEndTime_s(DateHelper.convertStandFormatDatetime(r.getEndTime()));
        offset += len;

        result.setOffsetTotal(offset);
        return result;
    }

    private FileRecord00DetailResult getDetailForUpdate(char[] b, int count, int offset,FileRecordAddVo lineAdd) throws Exception {
        int len;
        FileRecord00ForUpdate fb;
        Vector<FileRecord00ForUpdate> v = new Vector();
        FileRecord00DetailResult result = new FileRecord00DetailResult();
        int totalNum = 0;

        for (int i = 0; i < count; i++) {
            fb = new FileRecord00ForUpdate();
            this.addCommonInfo(fb, lineAdd);
            len = 1;
            fb.setCardMainId(this.getBcdString(b, offset, len));//票卡主类型
            offset += len;

            len = 1;
            fb.setCardSubId(this.getBcdString(b, offset, len));//票卡子类型
            offset += len;

            len = 1;
            fb.setUpdateReason(this.getBcdString(b, offset, len));//更新原因
            offset += len;


            len = 1;
            fb.setPayType(this.getBcdString(b, offset, len));//支付方式
            offset += len;

            len = 2;
            fb.setNum(this.getShort(b, offset));//更新数量
            offset += len;
            totalNum += fb.getNum();

            len = 4;
            fb.setFee(this.getLong(b, offset));//更新金额
            offset += len;

            v.add(fb);
        }
        result.setDetails(v);
        result.setOffsetTotal(offset);
        result.setTotalNum(totalNum);
        return result;
    }

    private FileRecord00DetailResult getDetailForReturnNon(char[] b, int count, int offset,FileRecordAddVo lineAdd) throws Exception {
        int len;
        FileRecord00ForReturn fb;
        Vector<FileRecord00ForReturn> v = new Vector();
        FileRecord00DetailResult result = new FileRecord00DetailResult();
        int totalNum = 0;

        for (int i = 0; i < count; i++) {
            fb = new FileRecord00ForReturn();
            this.addCommonInfo(fb, lineAdd);
            len = 1;
            fb.setCardMainId(this.getBcdString(b, offset, len));//票卡主类型
            offset += len;

            len = 1;
            fb.setCardSubId(this.getBcdString(b, offset, len));//票卡子类型
            offset += len;

            len = 2;
            fb.setNum(this.getShort(b, offset));//非即时退款数量
            offset += len;
            totalNum += fb.getNum();

            len = 4;
            fb.setReturnBalanceFee(this.getLong(b, offset));//非即时退款余额
            offset += len;

            len = 4;
            fb.setReturnDepositFee(this.getLong(b, offset));//即时退款押金
            offset += len;


            len = 4;
            fb.setPenaltyFee(this.getLong(b, offset));//非退款罚金
            offset += len;

            v.add(fb);
        }
        result.setDetails(v);
        result.setOffsetTotal(offset);
        result.setTotalNum(totalNum);
        return result;
    }

    private FileRecord00DetailResult getDetailForReturnNonApp(char[] b, int count, int offset,FileRecordAddVo lineAdd) throws Exception {
        int len;
        FileRecord00ForReturn fb;
        Vector<FileRecord00ForReturn> v = new Vector();
        FileRecord00DetailResult result = new FileRecord00DetailResult();
        int totalNum = 0;

        for (int i = 0; i < count; i++) {
            fb = new FileRecord00ForReturn();
            this.addCommonInfo(fb, lineAdd);
            len = 1;
            fb.setCardMainId(this.getBcdString(b, offset, len));//53票卡主类型
            offset += len;

            len = 1;
            fb.setCardSubId(this.getBcdString(b, offset, len));//53票卡子类型
            offset += len;

            len = 2;
            fb.setNum(this.getShort(b, offset));//54非即时退款申请数量
            offset += len;
            totalNum += fb.getNum();

            v.add(fb);
        }
        result.setDetails(v);
        result.setOffsetTotal(offset);
        result.setTotalNum(totalNum);
        return result;
    }

    private FileRecord00DetailResult getDetailForReturn(char[] b, int count, int offset,FileRecordAddVo lineAdd) throws Exception {
        int len;
        FileRecord00ForReturn fb;
        Vector<FileRecord00ForReturn> v = new Vector();
        FileRecord00DetailResult result = new FileRecord00DetailResult();
        int totalNum = 0;

        for (int i = 0; i < count; i++) {
            fb = new FileRecord00ForReturn();
            this.addCommonInfo(fb, lineAdd);
            len = 1;
            fb.setCardMainId(this.getBcdString(b, offset, len));//36票卡主类型
            offset += len;

            len = 1;
            fb.setCardSubId(this.getBcdString(b, offset, len));//36票卡子类型
            offset += len;

            len = 2;
            fb.setNum(this.getShort(b, offset));//37即时退款数量
            offset += len;
            totalNum += fb.getNum();

            len = 4;
            fb.setReturnBalanceFee(this.getLong(b, offset));//38即时退款余额
            offset += len;

            len = 4;
            fb.setReturnDepositFee(this.getLong(b, offset));//39即时退款押金
            offset += len;

            len = 1;
            fb.setReturnType(this.getCharString(b, offset, len));//40退款类型
            offset += len;

            len = 1;
            fb.setIsBroken(this.getCharString(b, offset, len));//41是否折损
            offset += len;

            len = 2;
            fb.setPenaltyFee(this.getShort(b, offset));//42退款罚金
            offset += len;

            len = 1;
            fb.setPenaltyReason(this.getBcdString(b, offset, len));//42罚款原因
            offset += len;


            v.add(fb);
        }
        result.setDetails(v);
        result.setOffsetTotal(offset);
        result.setTotalNum(totalNum);
        return result;
    }

    private FileRecord00DetailResult getDetailForAdmin(char[] b, int count, int offset,FileRecordAddVo lineAdd) throws Exception {
        int len;
        FileRecord00ForAdmin fb;
        Vector<FileRecord00ForAdmin> v = new Vector();
        FileRecord00DetailResult result = new FileRecord00DetailResult();
        int totalNum = 0;

        for (int i = 0; i < count; i++) {
            fb = new FileRecord00ForAdmin();
            this.addCommonInfo(fb, lineAdd);
            len = 1;
            fb.setAdminWayId(this.getBcdString(b, offset, len));//26行政处理代码
            offset += len;

            len = 2;
            fb.setNum(this.getShort(b, offset));//27行政处理数量
            offset += len;
            totalNum += fb.getNum();

            len = 4;
            fb.setFee(this.getLong(b, offset));//28行政处理金额
            offset += len;


            len = 1;
            fb.setAdminReasonId(this.getBcdString(b, offset, len));//29行政事务处理原因
            offset += len;


            v.add(fb);
        }
        result.setDetails(v);
        result.setOffsetTotal(offset);
        result.setTotalNum(totalNum);
        return result;
    }

    private FileRecord00DetailResult getDetailForCharge(char[] b, int count, int offset,FileRecordAddVo lineAdd) throws Exception {
        return this.getDetailForBaseWithPayType(b, count, offset,lineAdd);
    }

    private FileRecord00DetailResult getDetailForSale(char[] b, int count, int offset,FileRecordAddVo lineAdd) throws Exception {
        return this.getDetailForBaseWithPayType(b, count, offset,lineAdd);
    }

    private FileRecord00DetailResult getDetailForChargeUpdate(char[] b, int count, int offset,FileRecordAddVo lineAdd) throws Exception {
        return this.getDetailForBase(b, count, offset,lineAdd);
    }

    private FileRecord00DetailResult getDetailForUnlock(char[] b, int count, int offset,FileRecordAddVo lineAdd) throws Exception {
        return this.getDetailForNumBase(b, count, offset,lineAdd);
    }

    private FileRecord00DetailResult getDetailForBase(char[] b, int count, int offset,FileRecordAddVo lineAdd) throws Exception {
        int len;
        FileRecord00DetailBase fb;
        Vector<FileRecord00DetailBase> v = new Vector();
        FileRecord00DetailResult result = new FileRecord00DetailResult();
        int totalNum = 0;

        for (int i = 0; i < count; i++) {
            fb = new FileRecord00DetailBase();
            this.addCommonInfo(fb, lineAdd);
            
            len = 1;
            fb.setCardMainId(this.getBcdString(b, offset, len));//8票卡主类型
            offset += len;

            len = 1;
            fb.setCardSubId(this.getBcdString(b, offset, len));//8票卡子类型
            offset += len;

            len = 2;
            fb.setNum(this.getShort(b, offset));//9发售数量
            offset += len;
            totalNum += fb.getNum();

            len = 4;
            fb.setFee(this.getLong(b, offset));//10发售金额
            offset += len;

            v.add(fb);
        }
        result.setDetails(v);
        result.setOffsetTotal(offset);
        result.setTotalNum(totalNum);
        return result;
    }
    private FileRecord00DetailResult getDetailForBaseWithPayType(char[] b, int count, int offset,FileRecordAddVo lineAdd) throws Exception {
        int len;
        FileRecord00DetailBase fb;
        Vector<FileRecord00DetailBase> v = new Vector();
        FileRecord00DetailResult result = new FileRecord00DetailResult();
        int totalNum = 0;

        for (int i = 0; i < count; i++) {
            fb = new FileRecord00DetailBase();
            this.addCommonInfo(fb, lineAdd);
            
            len = 1;
            fb.setCardMainId(this.getBcdString(b, offset, len));//8票卡主类型
            offset += len;

            len = 1;
            fb.setCardSubId(this.getBcdString(b, offset, len));//8票卡子类型
            offset += len;
            
            len = 1;
            fb.setPayType(this.getBcdString(b, offset, len));//8支付方式
            offset += len;

            len = 2;
            fb.setNum(this.getShort(b, offset));//9发售数量
            offset += len;
            totalNum += fb.getNum();

            len = 4;
            fb.setFee(this.getLong(b, offset));//10发售金额
            offset += len;

            v.add(fb);
        }
        result.setDetails(v);
        result.setOffsetTotal(offset);
        result.setTotalNum(totalNum);
        return result;
    }
    private FileRecord00DetailResult getDetailForNumBase(char[] b, int count, int offset,FileRecordAddVo lineAdd) throws Exception {
        int len;
        FileRecord00DetailBase fb;
        Vector<FileRecord00DetailBase> v = new Vector();
        FileRecord00DetailResult result = new FileRecord00DetailResult();
        int totalNum = 0;

        for (int i = 0; i < count; i++) {
            fb = new FileRecord00DetailBase();
            this.addCommonInfo(fb, lineAdd);
            len = 1;
            fb.setCardMainId(this.getBcdString(b, offset, len));//票卡主类型
            offset += len;

            len = 1;
            fb.setCardSubId(this.getBcdString(b, offset, len));//票卡子类型
            offset += len;

            len = 2;
            fb.setNum(this.getShort(b, offset));//数量
            offset += len;
            totalNum += fb.getNum();

            //len = 4;
            //fb.setFee(this.getLong(b, offset));//10发售金额
            //offset += len;

            v.add(fb);
        }
        result.setDetails(v);
        result.setOffsetTotal(offset);
        result.setTotalNum(totalNum);
        return result;
    }

    private FileRecord00DetailResult getDetailForDelay(char[] b, int count, int offset,FileRecordAddVo lineAdd) throws Exception {
        int len;
        FileRecord00DetailBase fb;
        Vector<FileRecord00DetailBase> v = new Vector();
        FileRecord00DetailResult result = new FileRecord00DetailResult();
        int totalNum = 0;

        for (int i = 0; i < count; i++) {
            fb = new FileRecord00DetailBase();
            this.addCommonInfo(fb, lineAdd);
            len = 1;
            fb.setCardMainId(this.getBcdString(b, offset, len));//票卡主类型
            offset += len;

            len = 1;
            fb.setCardSubId(this.getBcdString(b, offset, len));//票卡子类型
            offset += len;

            len = 2;
            fb.setNum(this.getShort(b, offset));//延期数量
            offset += len;
            totalNum += fb.getNum();

            v.add(fb);
        }
        result.setDetails(v);
        result.setOffsetTotal(offset);
        result.setTotalNum(totalNum);
        return result;
    }

    @Override
    public void handleMessage(MessageBase msg) {
    }

    @Override
    public boolean checkData(FileRecordBase frb) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
