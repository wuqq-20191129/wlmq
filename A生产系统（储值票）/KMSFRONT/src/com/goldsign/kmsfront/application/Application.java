/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.kmsfront.struct.application;

import com.goldsign.commu.commu.application.BaseApplication;
import com.goldsign.commu.commu.connection.CommuConnection;
import com.goldsign.commu.commu.connection.ConnectionReader;
import com.goldsign.commu.commu.connection.ConnectionWriter;
import com.goldsign.commu.commu.env.CommuConstant;
import com.goldsign.kmsfront.struct.connection.AppConnectionReader;
import com.goldsign.kmsfront.struct.connection.AppConnectionWriter;
import com.goldsign.kmsfront.struct.env.AppConstant;
import java.io.BufferedInputStream;
import java.io.OutputStream;

/**
 *
 * @author lenovo
 */
public class Application extends BaseApplication{

    @Override
    protected void initAppConfig() {
        super.initAppConfig();
        CommuConstant.COMMU_MODE_SYN = true;
        CommuConstant.COMM_DATA_LEN = 0;
        AppConstant.MessageClassPrefix = "com.goldsign.mjd.struct.message.";
        
    }

    @Override
    protected void startAppThreads() {
        super.startAppThreads();
    }

    @Override
    protected boolean checkIpConfig(String ip) {
        //判断是否合法
        return super.checkIpConfig(ip);
    }

    @Override
    public ConnectionReader getConnectionReader(BufferedInputStream in) {
        return new AppConnectionReader(in);
    }

    @Override
    public ConnectionWriter getConnectionWriter(CommuConnection commuConnection, 
        OutputStream out) {
        return new AppConnectionWriter(commuConnection, out);
    }
    
}
