/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.dll.structure;

import com.sun.jna.NativeLong;
import com.sun.jna.Structure;

/**
 *
 * @author lenovo
 */
public class ORDERSOUT extends Structure{
    
    public byte[] cTradeType = new byte[2];				// 交易类型
    public byte[] cOrderNo = new byte[14];				// 订单编号
    public byte[] bTicketType = new byte[2];				// 票卡类型
    public byte[] cApplicationNO = new byte[10];			//记名卡申请码
    public byte[] cLogicalID = new byte[16];				// 票卡逻辑卡号
    public byte[] cPhysicalID = new byte[20];				// 票卡物理卡号
    public byte[] dtDate = new byte[14];				// 制票日期时间
    public byte[] lBalance = new byte[4];				// 票卡余额, 单位为分(默认为)
    public byte[] bStartDate = new byte[7];				// 逻辑有效期开始时间(BCD)
    public byte[] bEndDate = new byte[7];				// 逻辑有效期结束时间(BCD)
    public byte[] cSAMID = new byte[16];				// E/S SAM逻辑卡号, 默认值:’’

    public static class ByReference extends ORDERSOUT implements Structure.ByReference {
    }

    public static class ByValue extends ORDERSOUT implements Structure.ByValue {
    }
}
