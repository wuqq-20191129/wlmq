/*
 * 文件名：PhyLogicVo
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.escommu.vo;

/*
 * 物理卡号与逻辑卡号对照表实体
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-9-12
 */

public class PhyLogicVo {
    
    private String physicNo;//物理卡号
    
    private String LogicNo;//逻辑卡号

    public String getPhysicNo() {
        return physicNo;
    }

    public void setPhysicNo(String physicNo) {
        this.physicNo = physicNo;
    }

    public String getLogicNo() {
        return LogicNo;
    }

    public void setLogicNo(String LogicNo) {
        this.LogicNo = LogicNo;
    }
    
}
