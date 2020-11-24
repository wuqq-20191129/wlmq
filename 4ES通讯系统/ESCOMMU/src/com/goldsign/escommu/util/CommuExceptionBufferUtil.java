package com.goldsign.escommu.util;

import com.goldsign.escommu.env.CommuConstant;
import com.goldsign.escommu.vo.CommuHandledMessage;
import java.util.Vector;

public class CommuExceptionBufferUtil {

    private static Vector sqlUnHandleMsgs = new Vector(3000, 100);
    private static final String retCode = CommuConstant.RESULT_CODE_DTA;

    public CommuExceptionBufferUtil() {
    }

    /**
     * 备份SQL未处理消息
     * 
     * @param id
     * @param data 
     */
    public static void setSqlUnHandleMsgs(String id, byte[] data) {

        DateHelper.screenPrintForEx("sql异常消息.消息发送方:" + id + " 消息数据长度:" + data.length);

        if (data == null || data.length == 0) {
            return;
        }
        Vector readResult = new Vector();
        readResult.add(retCode);
        readResult.add(data);
        CommuHandledMessage chm = new CommuHandledMessage(id, readResult);
        synchronized (sqlUnHandleMsgs) {
            sqlUnHandleMsgs.add(chm);
            DateHelper.screenPrintForEx("sql异常消息缓存目前有记录:" + sqlUnHandleMsgs.size() + "条");
        }
    }

    /**
     * 取得SQL未处理消息
     * 
     * @return 
     */
    public static Vector getSqlUnHandleMsgs() {
        Vector ret = new Vector(3000, 100);
        synchronized (sqlUnHandleMsgs) {
            ret.addAll(sqlUnHandleMsgs);
            sqlUnHandleMsgs.clear();
            return ret;
        }
    }
}
