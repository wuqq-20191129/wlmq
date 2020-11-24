/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.test;

import com.goldsign.csfrm.vo.CallResult;
import com.goldsign.esmcs.exception.RwJniException;
import com.goldsign.esmcs.service.IRwDeviceService;
import com.goldsign.esmcs.service.impl.RwDeviceService;
import com.goldsign.esmcs.vo.RwPortParam;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lenovo
 */
public class Thread03 extends Thread{

    private int i = 10;
    
    @Override
    public void run() {
        /*
       IRwDeviceService rwDeviceService = new RwDeviceService();
        RwPortParam rwPortParam = new RwPortParam();
        int port = 4;
        rwPortParam.setPort(port);
        short stationId = 2;
        rwPortParam.setStationId(stationId);
        byte deviceType = 1;
        rwPortParam.setDeviceType(deviceType);
        short deviceId = 2;
        rwPortParam.setDeviceId(deviceId);
        CallResult callResult = null;
        try {
            callResult = rwDeviceService.openRwPort(rwPortParam);
        } catch (RwJniException ex) {
            Logger.getLogger(Thread03.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            //rwDeviceService.openRwPort(rwPortParam);
            callResult = rwDeviceService.readCard(null);
            rwDeviceService.writeCard(rwPortParam);
            callResult = rwDeviceService.readCard(null);
            callResult = rwDeviceService.readCard(null);
            callResult = rwDeviceService.readCard(null);
            callResult = rwDeviceService.readCard(null);
            
            
        } catch (RwJniException ex) {
            Logger.getLogger(Thread03.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            rwDeviceService.readCard(null);
        } catch (RwJniException ex) {
            Logger.getLogger(Thread03.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(callResult.isSuccess());*/
    }
}
