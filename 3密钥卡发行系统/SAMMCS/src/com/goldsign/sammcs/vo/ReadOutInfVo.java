
package com.goldsign.sammcs.vo;

/**
 *
 * @author mqf
 */
public class ReadOutInfVo {
    private String issueState;//响应数据(制卡状态) 1已制
    
    private String psamCardNo;//响应数据(psam卡号)
    
    private String phyNo;//响应数据(psam芯片号)/物理卡号

    public String getIssueState() {
        return issueState;
    }

    public void setIssueState(String issueState) {
        this.issueState = issueState;
    }

    public String getPsamCardNo() {
        return psamCardNo;
    }

    public void setPsamCardNo(String psamCardNo) {
        this.psamCardNo = psamCardNo;
    }

    public String getPhyNo() {
        return phyNo;
    }

    public void setPhyNo(String phyNo) {
        this.phyNo = phyNo;
    }
    
    
}
