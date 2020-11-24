/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.dll.structure;

import com.sun.jna.Structure;

/**
 *
 * @author lenovo
 */
public class ORDERSIN extends Structure{
    
    public byte[] cOrderNo = new byte[14];  				// 订单号
    public byte[] cApplicationNO = new byte[10];			// 记名卡申请码
    public short wTicketType;                                           // 车票类型
    public int dwSysFlow;                                               // 系统流水（只对单程票有效，储值票无效）
    public byte[] lDeposite = new byte[4];				// 押金
    public byte[] lValue = new byte[4];                                 // 车票钱包预赋金额
    public byte[] dtStartDate = new byte[7];				// 逻辑有效期开始时间(BCD)
    public byte[] dtEndDate = new byte[7];				// 逻辑有效期结束时间(BCD)
    public byte[] dtExpire = new byte[7];				// 车票物理有效期截止时间(BCD)

    public static class ByReference extends ORDERSIN implements Structure.ByReference {
    }

    public static class ByValue extends ORDERSIN implements Structure.ByValue {
    }
}
