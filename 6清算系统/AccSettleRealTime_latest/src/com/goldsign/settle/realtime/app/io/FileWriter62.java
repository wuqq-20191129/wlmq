/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.io;

import com.goldsign.settle.realtime.app.vo.FileRecord62;
import com.goldsign.settle.realtime.frame.exception.FileWriteException;
import com.goldsign.settle.realtime.frame.io.FileWriterBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordBase;
import java.util.Vector;

/**
 *
 * @author hejj
 */
//挂失解挂
public class FileWriter62 extends FileWriterBase {

    @Override
    public void writeFile(String pathBcp, String fileName, String tradType, Vector records) throws FileWriteException {
        this.writeFileForCommon(pathBcp, fileName, tradType, records);

    }

    public String getLine(FileRecordBase vob) {
        FileRecord62 vo = (FileRecord62) vob;
        StringBuffer sb = new StringBuffer();
        sb.append(vo.getRecordVer()+ FileWriterBase.FIELD_DELIM);//记录版本20180528
        sb.append(vo.getLineId() + FileWriterBase.FIELD_DELIM);//2线路代码
        sb.append(vo.getStationId() + FileWriterBase.FIELD_DELIM);//2站点代码
        sb.append(vo.getDevTypeId() + FileWriterBase.FIELD_DELIM);//3设备类型
        sb.append(vo.getDeviceId() + FileWriterBase.FIELD_DELIM);//4设备代码

        sb.append(vo.getApplyName() + FileWriterBase.FIELD_DELIM);//5姓名
        sb.append(vo.getApplySex() + FileWriterBase.FIELD_DELIM);//6性别
        sb.append(vo.getIdentityType() + FileWriterBase.FIELD_DELIM);//7证件类型
        sb.append(vo.getIdentityId() + FileWriterBase.FIELD_DELIM);//8证件号码
        sb.append(vo.getExpiredDate() + FileWriterBase.FIELD_DELIM);//9证件有效期
        sb.append(vo.getTelNo() + FileWriterBase.FIELD_DELIM);//10电话
        sb.append(vo.getFax() + FileWriterBase.FIELD_DELIM);//11传真
        sb.append(vo.getAddress() + FileWriterBase.FIELD_DELIM);//12通讯地址

        sb.append(vo.getOperatorId() + FileWriterBase.FIELD_DELIM);//13操作员代码
        sb.append(vo.getApplyDatetime() + FileWriterBase.FIELD_DELIM);//14申请日期
        sb.append(vo.getShiftId() + FileWriterBase.FIELD_DELIM);//15BOM班次序号
        sb.append(vo.getCardAppFlag() + FileWriterBase.FIELD_DELIM);//16卡应用标识
        sb.append(vo.getCardMainId() + FileWriterBase.FIELD_DELIM);//17卡主类
        sb.append(vo.getCardSubId() + FileWriterBase.FIELD_DELIM);//17卡子类
        sb.append(vo.getApplyBusinessType() + FileWriterBase.FIELD_DELIM);//18业务类型
        sb.append(vo.getReceiptId() + FileWriterBase.FIELD_DELIM);//19凭证ID
        sb.append(vo.getCardLogicalId()+ FileWriterBase.FIELD_DELIM);//20逻辑卡号



        this.addCommonToBuff(sb, vo);

        sb.append(FileWriterBase.getLineDelimter());


        return sb.toString();
    }
}
