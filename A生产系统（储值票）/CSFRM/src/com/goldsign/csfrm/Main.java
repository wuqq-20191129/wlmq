/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.csfrm;

import com.goldsign.csfrm.application.impl.BaseApplication;

/**
 *
 * @author lenovo
 */
public class Main {

    public static void main(String[] args) throws Exception{
        
        //BaseApplication baseApp = new BaseApplication();
        BaseApplication baseApp = new BaseApplication("V1.0.0");
        baseApp.run();
        //CallParam callParam = new CallParam();
       // baseApp.login(callParam);
    }
}
