/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.sammcs.vo;

import com.goldsign.csfrm.vo.CallParam;
import com.goldsign.sammcs.env.AppConstant;

/**
 *
 * @author lenovo
 */
public class OperateLogVo extends CallParam {

    private String operId;
    
//    private String oprtContent;
    
    private String oprtTime;
    
//    private String oprtResult;
    
    private String moduleId;
    
    private String operType;
            
    private String description;
    

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



    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    

 
}
