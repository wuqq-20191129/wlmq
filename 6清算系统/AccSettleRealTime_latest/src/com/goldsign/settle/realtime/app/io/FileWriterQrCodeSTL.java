/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.io;


import com.goldsign.settle.realtime.app.vo.FileRecordQrCodeSTL;
import com.goldsign.settle.realtime.frame.exception.FileWriteException;
import com.goldsign.settle.realtime.frame.io.FileWriterBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.util.Vector;

/**
 *
 * @author hejj
 */
public class FileWriterQrCodeSTL extends FileWriterBase {

    @Override
    public void writeFile(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        this.writeFileForCommon(pathBcp, fileName, tradType, records);
    }

    @Override
    public String getLine(FileRecordBase vob) {
        FileRecordQrCodeSTL vo = (FileRecordQrCodeSTL) vob;
        StringBuffer sb = new StringBuffer();
        sb.append(vo.getRecordVer()+ FileWriterBase.FIELD_DELIM);//记录版本
        sb.append(vo.getLineId() + FileWriterBase.FIELD_DELIM);//线路代码
        sb.append(vo.getStationId() + FileWriterBase.FIELD_DELIM);//站点代码

        sb.append(vo.getQrEntryum() + FileWriterBase.FIELD_DELIM);//二维码进站总数量
        sb.append(vo.getElectTkTctEntryNum()+ FileWriterBase.FIELD_DELIM);//电子票进站总数量（计次）
        sb.append(vo.getElectTkEntryNum() + FileWriterBase.FIELD_DELIM);//电子票进站总数量（计值）

        sb.append(vo.getQrDealNum() + FileWriterBase.FIELD_DELIM);//二维码钱包交易总数量
        sb.append(this.convertFenToYuan(vo.getQrDealFee()) + FileWriterBase.FIELD_DELIM);//二维码钱包交易总金额 
        sb.append(vo.getElectTkTctDealNum()+ FileWriterBase.FIELD_DELIM);//电子票计次钱包交易总数量
        sb.append(vo.getElectTkDealNum() + FileWriterBase.FIELD_DELIM);//电子票计值钱包交易总数量
        sb.append(this.convertFenToYuan(vo.getElectTkDealFee()) + FileWriterBase.FIELD_DELIM);//电子票计值钱包交易总金额 
        //20190829 hejj
        sb.append(vo.getQrMatchNum()+ FileWriterBase.FIELD_DELIM);//二维码匹配总数量
        sb.append(this.convertFenToYuan(vo.getQrMatchFee()) + FileWriterBase.FIELD_DELIM);//二维码匹配总金额
        sb.append(vo.getQrMatchNotNum() + FileWriterBase.FIELD_DELIM);//二维码不匹配总数量
        sb.append(this.convertFenToYuan(vo.getQrMatchNotFee()) + FileWriterBase.FIELD_DELIM);//二维码不匹配总金额
        
        sb.append(vo.getIssueQrcodePlatformFlag()+ FileWriterBase.FIELD_DELIM);//发码平台标识 20200706
        
        

        

        this.addCommonToBuff(sb, vo);
        sb.append(FileWriterBase.getLineDelimter());

        return sb.toString();
    }

}
