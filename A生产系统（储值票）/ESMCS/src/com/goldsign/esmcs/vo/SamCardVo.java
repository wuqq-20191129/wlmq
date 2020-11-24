/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.vo;

/**
 *
 * @author lenovo
 */
public class SamCardVo {

    private String samStatus;
    
    private String samNo;

    /**
     * @return the samStatus
     */
    public String getSamStatus() {
        return samStatus;
    }

    /**
     * @param samStatus the samStatus to set
     */
    public void setSamStatus(String samStatus) {
        this.samStatus = samStatus;
    }

    /**
     * @return the samNo
     */
    public String getSamNo() {
        return samNo;
    }

    /**
     * @param samNo the samNo to set
     */
    public void setSamNo(String samNo) {
        this.samNo = samNo;
    }

    @Override
    public String toString() {
        return "SamCardVo{" + "samStatus=" + samStatus + ", samNo=" + samNo + '}';
    }
    
}
