/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.commu.commu.application;

import com.goldsign.commu.commu.exception.CommuException;
import com.goldsign.commu.commu.exception.ConfigException;
import com.goldsign.commu.commu.exception.ThreadException;
import java.io.IOException;
import java.net.BindException;

/**
 *
 * @author lenovo
 */
public interface IApplication {

    void initConfig() throws ConfigException;
    
    void startThreads()throws ThreadException;
    
    void startSocketServer()throws BindException,IOException,CommuException;
}
