/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.thread;

import com.goldsign.esmcs.service.IEsDeviceService;
import com.goldsign.esmcs.ui.dialog.MakeCardDialog;
import com.goldsign.esmcs.vo.OrderVo;
import org.apache.log4j.Logger;

/**
 *
 * @author lenovo
 */
public class MakeCardThread extends Thread{

    private static final Logger logger = Logger.getLogger(MakeCardThread.class.getName());
    
    protected OrderVo curOrderVo;//当前订单
    private IEsDeviceService esDeviceService;
    
    public MakeCardThread(MakeCardDialog makeCardDialog){
        this.curOrderVo = makeCardDialog.getCurOrderVo();
    }
    
    public void setEsDeviceService(IEsDeviceService esDeviceService){
        this.esDeviceService = esDeviceService;
    }
    
}
