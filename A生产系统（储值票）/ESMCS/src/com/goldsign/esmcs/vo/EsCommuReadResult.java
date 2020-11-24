/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.vo;

import com.goldsign.esmcs.commu.EsCommuReadBase;
import com.goldsign.esmcs.env.CommuConstant;

/**
 *
 * @author lenovo
 */
public class EsCommuReadResult extends EsCommuReadBase {

    private String code;
    
    public EsCommuReadResult(){}
    
    public EsCommuReadResult(String code, byte[] data){
        super(data);
        this.code = code;
    }
    
    public boolean isSuccess(){
        
        return code.startsWith(CommuConstant.RESULT_CODE_SUC);
    }
    
    public boolean isData(){
        
        return code.equals(CommuConstant.RESULT_CODE_DTA);
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }
}
