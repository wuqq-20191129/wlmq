/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.csfrm.test;

import com.goldsign.csfrm.application.impl.BaseApplication;
import com.goldsign.csfrm.exception.*;

/**
 *
 * @author lenovo
 */
public class TestIApplication {

    public static void main(String[] args) throws Exception{
        
        BaseApplication baseApp = new BaseApplication();
        baseApp.run();
        //CallParam callParam = new CallParam();
       // baseApp.login(callParam);
    }
}
