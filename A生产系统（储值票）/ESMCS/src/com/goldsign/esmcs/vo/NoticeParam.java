/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.vo;

import com.goldsign.csfrm.vo.CallParam;

/**
 *
 * @author lenovo
 */
public class NoticeParam extends CallParam {

    private String noticeType;

    private String beginDate;
    
    private String endDate;

    /**
     * @return the noticeType
     */
    public String getNoticeType() {
        return noticeType;
    }

    /**
     * @param noticeType the noticeType to set
     */
    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
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
