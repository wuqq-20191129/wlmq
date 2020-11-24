/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.csfrm.vo;

/**
 *
 * @author lenovo
 */
public class AuthenResult extends CallResult<SysUserVo> {

    public AuthenResult(boolean result, String msg) {
        super(result, msg);
    }

    public AuthenResult(String msg) {
        super(msg);
    }

    public AuthenResult(boolean result) {
        super(result);
    }

    public AuthenResult() {
    }
    
}
