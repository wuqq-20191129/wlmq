/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.commu.commu.connection;

import com.goldsign.commu.commu.env.BaseConstant;
import com.goldsign.commu.commu.env.CommuConstant;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.log4j.Logger;

/**
 *
 * @author lenovo
 */
public class ConnectionWriter extends Thread{
    
    private static Logger logger = Logger.getLogger(ConnectionWriter.class.getName());
    
    protected CommuConnection commuConnection;
    
    protected OutputStream out;

    public ConnectionWriter(CommuConnection commuConnection, OutputStream out){
    
        this.commuConnection = commuConnection;
        this.out = out;
    }
    
    @Override
    public void run() {
    
        while(true){
            try {
                byte[] message = commuConnection.getMsgFromQuque();
                if (null != message) {
                    sendData(message);
                   
                }else{
                    //sendTestData();
                }
                
                //写消息等
                if(CommuConstant.COMMU_MODE_SYN){
                    break;
                }else{
                    Thread.sleep(BaseConstant.WriteMessageFrequency);
                }
            } catch (Exception e1) {
                logger.error(e1);
                commuConnection.closeConnection();
                break;
                //throw new CommuRunTimeException(e1);
            }
        }
    }

    private void sendData(byte[] data) throws IOException {

        int len = data.length;
        int i = 0;
        while(i < len){
            int sLen = ((len-i)<1024)?(len-i):1024;
            //System.out.println(i+":"+len);
            out.write(data, i, sLen);
            out.flush();
            i += sLen;
        }
        //out.write(data);

        //out.flush();
        //logger.info("发送数据：" + data);
    }
  
    private void sendTestData() throws IOException {

        out.write((byte)0);

        out.flush();
        //logger.info("发送测试数据..." + 0);
    }
}
