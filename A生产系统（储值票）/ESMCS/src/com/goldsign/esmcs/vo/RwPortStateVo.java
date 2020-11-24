/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.vo;

/**
 *
 * @author lenovo
 */
public class RwPortStateVo {

    public final String port;
    
    public final String name;
    
    public boolean state;
    
    public String samNo;

    public RwPortStateVo(String port, String name, boolean state) {
        this.port = port;
        this.name = name;
        this.state = state;
    }
}
