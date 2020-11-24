package com.goldsign.escommu.thread;

import com.goldsign.escommu.exception.CommuException;
import com.goldsign.escommu.util.SocketUtil;
import java.io.IOException;
import java.net.BindException;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p> <p>Description: </p> <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class CommuServerThread extends Thread {

    private static Logger logger = Logger.getLogger(CommuServerThread.class.getName());
    
    public CommuServerThread() {
    }

    public void run() {
        SocketUtil util = new SocketUtil();
        try {
            util.startSocketListener();
        } catch (BindException e) {
            e.printStackTrace();
            logger.error(e);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
        } catch (CommuException e) {
            e.printStackTrace();
            logger.error(e);
        }

    }
}
