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
public class EsPortParam extends CallParam {

    private short port;
    
    private int comRate;

    /**
     * @return the port
     */
    public short getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(short port) {
        this.port = port;
    }

    /**
     * @return the comRate
     */
    public int getComRate() {
        return comRate;
    }

    /**
     * @param comRate the comRate to set
     */
    public void setComRate(int comRate) {
        this.comRate = comRate;
    }
    
}
