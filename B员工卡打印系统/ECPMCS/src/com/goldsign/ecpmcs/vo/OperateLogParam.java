/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.ecpmcs.vo;

import com.goldsign.csfrm.vo.CallParam;

/**
 *
 * @author lenovo
 */
public class OperateLogParam extends CallParam {

    private String operId;
    
    private String oprtContent;
    
    private String beginDate;
    
    private String endDate;

    /**
     * @return the operId
     */
    public String getOperId() {
        return operId;
    }

    /**
     * @param operId the operId to set
     */
    public void setOperId(String operId) {
        this.operId = operId;
    }

    /**
     * @return the oprtContent
     */
    public String getOprtContent() {
        return oprtContent;
    }

    /**
     * @param oprtContent the oprtContent to set
     */
    public void setOprtContent(String oprtContent) {
        this.oprtContent = oprtContent;
    }

    /**
     * @return the beginDate
     */
    public String getBeginDate() {
        return beginDate;
    }

    /**
     * @param beginDate the beginDate to set
     */
    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    /**
     * @return the endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    
}
