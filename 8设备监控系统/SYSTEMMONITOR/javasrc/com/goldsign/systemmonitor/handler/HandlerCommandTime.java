package com.goldsign.systemmonitor.handler;

import com.goldsign.frame.constant.FrameDBConstant;
import java.util.Vector;
import java.util.HashMap;
import org.apache.log4j.Logger;



public class HandlerCommandTime extends HandlerCommandBase {

    static Logger logger = Logger.getLogger(HandlerCommandTime.class);

    public HandlerCommandTime() {
        super();
    }

    private Vector getLineInfoForSybasetime(Vector lines) {
        String line;
        Vector v = new Vector();
        String[] values = new String[2];
        int[] indexs = {0, 1};
        String sybaseTime;
        Vector v1 = new Vector();
        for (int i = 0; i < lines.size(); i++) {
            line = (String) lines.get(i);
            //values = this.getLineFields(line, indexs, FrameDBConstant.Command_seperator_space);
            //sybaseTime = values[0] + " " + values[1];
            v.add(line);
        }
        if (v.size() == 3) {
            values = new String[2];
            values[0] = (String) v.get(0);
            values[1] = (String) v.get(1);
            v1.add(values);

            values = new String[2];
            values[0] = (String) v.get(1);
            values[1] = (String) v.get(2);
            v1.add(values);
        }
        //return v1;
        return v;
    }

    public void handleCommand(String command, Vector lines, String fileName) {
        logger.info("处理命令:" + command);
        this.setParameter(command, lines);
        Vector lineInfos = this.getLineInfoForSybasetime(lines);
        HandlerCommandSize.setSybaseTime(lineInfos);
    }
}
