/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.io;

import com.goldsign.settle.realtime.app.vo.FileRecord54;
import com.goldsign.settle.realtime.app.vo.FileRecordMobile54;
import com.goldsign.settle.realtime.frame.exception.FileWriteException;
import com.goldsign.settle.realtime.frame.io.FileWriterBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class FileWriterMobile54 extends FileWriterBase{
     @Override
    public void writeFile(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        this.writeFileForCommon(pathBcp, fileName, tradType, records);
        /*
        String fileNameBcp = pathBcp + "/" + this.getFileNameBcp(tradType, fileName);
        FileOutputStream fos = null;
        OutputStreamWriter osr = null;
        BufferedWriter bw = null;
        FileRecord54 vo;
        String line;
        try {
            fos = new FileOutputStream(fileNameBcp);
            osr = new OutputStreamWriter(fos, FileWriterBase.CHARSET);
            bw = new BufferedWriter(osr);
            for (int i = 0; i < records.size(); i++) {
                vo = (FileRecord54) records.get(i);
                line = this.getLine(vo);
                bw.write(line);
                bw.flush();
            }

        } catch (Exception ex) {
            throw new FileWriteException(ex.getMessage());

        } finally {
            this.closeFile(fos, osr, bw);
        }
        */
    }

    @Override
    public String getLine(FileRecordBase vob) {
        FileRecordMobile54 vo =(FileRecordMobile54)vob;
        StringBuffer sb = new StringBuffer();
        sb.append(vo.getRecordVer()+ FileWriterBase.FIELD_DELIM);//记录版本
        sb.append(vo.getLineId() + FileWriterBase.FIELD_DELIM);//线路代码
        sb.append(vo.getStationId() + FileWriterBase.FIELD_DELIM);//站点代码
        sb.append(vo.getDevTypeId() + FileWriterBase.FIELD_DELIM);//设备类型
        sb.append(vo.getDeviceId() + FileWriterBase.FIELD_DELIM);//设备代码
        sb.append(vo.getSamLogicalId() + FileWriterBase.FIELD_DELIM);//SAM卡逻辑卡号
        sb.append(vo.getSamTradeSeq() + FileWriterBase.FIELD_DELIM);//SAM卡脱机交易流水号

        sb.append(vo.getDealDatetime() + FileWriterBase.FIELD_DELIM);//时间
        sb.append(vo.getCardMainId() + FileWriterBase.FIELD_DELIM);//票卡主类型
        sb.append(vo.getCardSubId() + FileWriterBase.FIELD_DELIM);//票卡子类型
        sb.append(vo.getCardLogicalId() + FileWriterBase.FIELD_DELIM);//票逻辑ID
        sb.append(vo.getCardPhysicalId() + FileWriterBase.FIELD_DELIM);//票物理ID
        sb.append(vo.getCardStatusId() + FileWriterBase.FIELD_DELIM);//票状态

        sb.append(this.convertFenToYuan(vo.getDealFee()) + FileWriterBase.FIELD_DELIM);//交易金额
        sb.append(this.convertFenToYuan(vo.getDealBalanceFee()) + FileWriterBase.FIELD_DELIM);//余额
        sb.append(vo.getCardChargeSeq() + FileWriterBase.FIELD_DELIM);//票卡充值交易计数
        sb.append(vo.getCardConsumeSeq() + FileWriterBase.FIELD_DELIM);//票卡扣款交易计数
        sb.append(vo.getPayModeId() + FileWriterBase.FIELD_DELIM);//支付类型
        sb.append(vo.getReceiptId() + FileWriterBase.FIELD_DELIM);//凭证ID
        sb.append(vo.getTac() + FileWriterBase.FIELD_DELIM);//MAC/TAC//交易认证码
        
        sb.append(vo.getEntryLineId() + FileWriterBase.FIELD_DELIM);//入口线路代码
        sb.append(vo.getEntryStationId() + FileWriterBase.FIELD_DELIM);//入口站点代码
        sb.append(vo.getEntrySamLogicalId() + FileWriterBase.FIELD_DELIM);//入口SAM逻辑卡号
        sb.append(vo.getEntryDatetime() + FileWriterBase.FIELD_DELIM);//进站日期时间
        sb.append(vo.getOperatorId() + FileWriterBase.FIELD_DELIM);//操作员代码
        sb.append(vo.getShiftId() + FileWriterBase.FIELD_DELIM);//BOM班次序号
        sb.append(vo.getLastSamLogicalId() + FileWriterBase.FIELD_DELIM);//上次交易SAM逻辑卡号
        sb.append(vo.getLastDealDatetime() + FileWriterBase.FIELD_DELIM);//上次交易日期时间
        sb.append(this.convertFenToYuan(vo.getDealNoDiscountFee()) + FileWriterBase.FIELD_DELIM);//钱包交易金额
        sb.append(vo.getCardAppFlag() + FileWriterBase.FIELD_DELIM);//卡应用标识
        sb.append(vo.getLimitMode() + FileWriterBase.FIELD_DELIM);//限制使用模式
        sb.append(vo.getLimitEntryStation() + FileWriterBase.FIELD_DELIM);//限制进站代码
        sb.append(vo.getLimitExitStation()+FileWriterBase.FIELD_DELIM );//限制出站代码
        
        sb.append(vo.getWorkMode()+FileWriterBase.FIELD_DELIM );//出站工作模式
        sb.append(vo.getBusCityCode()+FileWriterBase.FIELD_DELIM );//城市代码
        sb.append(vo.getBusBusinessCode()+FileWriterBase.FIELD_DELIM );//行业代码
        sb.append(vo.getBusTacDealType()+FileWriterBase.FIELD_DELIM );//TAC交易类型
        sb.append(vo.getBusTacDevId()+FileWriterBase.FIELD_DELIM );//TAC终端编号
        sb.append(vo.getCardAppMode()+FileWriterBase.FIELD_DELIM );//卡应用模式
        //手机支付增加字段
        sb.append(vo.getMobileNo()+FileWriterBase.FIELD_DELIM );//手机号
        sb.append(vo.getPaidChannelType()+FileWriterBase.FIELD_DELIM );//支付渠道类型
        sb.append(vo.getPaidChannelCode()+FileWriterBase.FIELD_DELIM );//支付渠道代码
        
        sb.append(vo.getLastChargeDatetime()+FileWriterBase.FIELD_DELIM );//最后一次充值时间
        //add 20180309
        sb.append(vo.getIssueUnit()+FileWriterBase.FIELD_DELIM );//发卡机构
        sb.append(vo.getKeyVersion()+FileWriterBase.FIELD_DELIM );//密钥版本
        sb.append(vo.getKeyIndex()+FileWriterBase.FIELD_DELIM );//密钥索引
        sb.append(vo.getKeyRandomNo()+FileWriterBase.FIELD_DELIM );//伪随机数
        sb.append(vo.getAlgorithmFlag()+FileWriterBase.FIELD_DELIM );//算法标识
        sb.append(vo.getHolderName()+FileWriterBase.FIELD_DELIM );//持卡人姓名
        sb.append(vo.getIdentityType()+FileWriterBase.FIELD_DELIM );//证件类型
        sb.append(vo.getIdentityId()+FileWriterBase.FIELD_DELIM );//证件号码
        sb.append(vo.getBuyTkNum()+FileWriterBase.FIELD_DELIM );//购票数量
        sb.append(this.convertFenToYuan(vo.getBuyTkUnitFee())+FileWriterBase.FIELD_DELIM );//购票单价


        
        
        this.addCommonToBuff(sb, vo);
        sb.append(FileWriterBase.getLineDelimter());


        return sb.toString();
    }
    
}
