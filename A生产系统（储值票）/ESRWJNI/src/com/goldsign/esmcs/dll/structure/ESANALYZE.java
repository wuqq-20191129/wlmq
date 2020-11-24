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
public class ESANALYZE extends Structure{

    public byte bStatus;						// 交易状态－参见附录四
    public byte[] bTicketType = new byte[2];				// 票卡类型
    public byte[] cLogicalID = new byte[16];				// 票卡逻辑卡号
    public byte[] cPhysicalID = new byte[20];				// 票卡物理卡号
    public byte	bCharacter;                                             // 票卡物理类型OCT,UL,FM,F:其他
    public byte	bIssueStatus;                                           // 发行状态0 –未发行;1 –已发行;2 –注销
    public byte[] dtIssueDate = new byte[7];				// 制票日期时间日期时间
    public byte[] dtExpire = new byte[7];				// 物理有效期, YYYYMMDDHHMMSS，
    public byte[] lBalance = new byte[4];				// 余额, 单位为分/次(默认为)
    public byte[] lDeposite = new byte[4];				// 押金
    
    public static class ByReference extends ESANALYZE implements Structure.ByReference {
    }

    public static class ByValue extends ESANALYZE implements Structure.ByValue {
    }

}
