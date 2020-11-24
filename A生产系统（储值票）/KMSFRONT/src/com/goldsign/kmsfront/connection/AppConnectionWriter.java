/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.kmsfront.struct.connection;

import com.goldsign.commu.commu.connection.CommuConnection;
import com.goldsign.commu.commu.connection.ConnectionWriter;
import java.io.OutputStream;

/**
 *
 * @author lenovo
 */
public class AppConnectionWriter extends ConnectionWriter{

    public AppConnectionWriter(CommuConnection commuConnection, OutputStream out) {
        super(commuConnection, out);
    }
    
}
