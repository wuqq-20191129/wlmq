package com.goldsign.acc.systemmonitor.handler;

import com.goldsign.acc.frame.entity.EmailContent;
import org.apache.log4j.Logger;

import java.util.Vector;


/**
 * refactor by zhongzq
 */
public class HandlerCommandTime extends HandlerCommandBase {

    static Logger logger = Logger.getLogger(HandlerCommandTime.class);

    public HandlerCommandTime() {
        super();
    }

    private Vector getLineInfoForSybasetime(Vector lines) {
        String line;
        Vector v = new Vector();
        for (int i = 0; i < lines.size(); i++) {
            line = (String) lines.get(i);
            v.add(line);
        }
        return v;
    }

    /**
     * modify by zhongziqi 20180614
     *
     * @param command
     * @param lines
     * @param fileName
     */
    @Override
    public void handleCommand(String command, Vector lines, String fileName) {
        logger.info("处理命令:" + command);
        Vector lineInfos = this.getLineInfoForSybasetime(lines);
        HandlerCommandSize.setSybaseTime(lineInfos);
    }

    @Override
    public EmailContent convertToEmailContents(Object t, String classPath) {
        return null;
    }
}
