/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs;

import com.goldsign.esmcs.application.PKApplication;

/**
 *
 * @author lenovo
 */
public class Main {

    public static void main(String[] args) throws Exception{
        PKApplication app = new PKApplication("V1.4.6");
        app.run();
        //CallParam callParam = new CallParam();
       // baseApp.login(callParam);
    }
}
