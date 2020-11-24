/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.handler;

import com.goldsign.settle.realtime.frame.message.ConstructTacMessage;
import com.goldsign.settle.realtime.frame.message.MessageBase;
import com.goldsign.settle.realtime.frame.message.MessageTac;
import com.goldsign.settle.realtime.frame.vo.FileRecordTacBase;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author hejj
 */
public class HandlerTac extends HandlerBase {

    private static Logger logger = Logger.getLogger(HandlerTac.class.getName());

    @Override
    public void handleMessage(MessageBase msg) {
        try {

            MessageTac msgTac = (MessageTac) msg;
            Vector<FileRecordTacBase> datas = msgTac.getDatas();
            this.checkTac(datas);




        } catch (Exception ex) {
        }
    }

    private void checkTac(Vector<FileRecordTacBase> datas) {
        ConstructTacMessage ctm = new ConstructTacMessage();
        ctm.constuctAndSend(datas);
       // ctm.constuctAndSend56(datas);

    }
}
