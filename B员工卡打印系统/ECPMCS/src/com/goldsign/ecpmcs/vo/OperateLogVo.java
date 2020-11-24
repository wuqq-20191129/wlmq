/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.ecpmcs.vo;

import com.goldsign.csfrm.vo.CallParam;
import com.goldsign.ecpmcs.env.AppConstant;

/**
 *
 * @author lenovo
 */
public class OperateLogVo extends CallParam {

    private String operId;
    
    private String oprtContent;
    
    private String oprtTime;
    
    private String oprtResult;

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
     * @return the oprtTime
     */
    public String getOprtTime() {
        return oprtTime;
    }

    /**
     * @param oprtTime the oprtTime to set
     */
    public void setOprtTime(String oprtTime) {
        this.oprtTime = oprtTime;
    }

    /**
     * @return the oprtResult
     */
    public String getOprtResult() {
        return oprtResult;
    }
    
    /**
     * @return the oprtResult
     */
    public String getOprtResultDesc() {
        if(AppConstant.OPRT_LOG_RESULT_SUC.equals(oprtResult)){
            return AppConstant.OPRT_LOG_RESULT_SUC_NAME;
        }else if(AppConstant.OPRT_LOG_RESULT_FAIL.equals(oprtResult)){
            return AppConstant.OPRT_LOG_RESULT_FAIL_NAME;
        }else{
            return "";
        }
    }

    /**
     * @param oprtResult the oprtResult to set
     */
    public void setOprtResult(String oprtResult) {
        this.oprtResult = oprtResult;
    }

 
}
