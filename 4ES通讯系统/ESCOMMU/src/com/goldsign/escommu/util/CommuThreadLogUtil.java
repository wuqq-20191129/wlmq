package com.goldsign.escommu.util;

import com.goldsign.escommu.env.AppConstant;
import com.goldsign.escommu.vo.CommuHandledMessage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Vector;

public class CommuThreadLogUtil {

    public static final String unHandleName = "unHandleMsg.log";
    public static final String errName = "ErrHandleMsg.log";

    /**
     * 写未处理消息到文件
     * 
     * @param threadName
     * @param msgs 
     */
    public synchronized static void writeUnHandleMsgToFile(String threadName, Vector msgs) {
        //  if(msgs == null || msgs.isEmpty() )
        //    return;

        String fileName = AppConstant.UnHanledMsgLogDir + "/" + unHandleName;
        File f = null;
        FileOutputStream fos = null;
        byte[] data = null;
        String msgID = "";
        String head = "";
        String biValue = "";
        try {
            f = new File(fileName);
            if (!f.exists()) {
                f.createNewFile();
            }
            fos = new FileOutputStream(f, true);
            for (int i = 0; i < msgs.size(); i++) {
                data = (byte[]) ((Vector) ((CommuHandledMessage) msgs.get(i)).getReadResult()).get(1);
                msgID = "" + (char) data[0] + (char) data[1];
                head = "\r\n" + DateHelper.datetimeToYYYY_MM_DD_HH_MM_SS_ML(new Date(System.currentTimeMillis())) + "   线程" + threadName + "未处理消息 " + msgID + "\r\n";
                fos.write(head.getBytes("GBK"));
                for (int j = 0; j < data.length; j++) {
                    //value =(byte)(0x30+data[j]);
                    biValue = Integer.toHexString((int) data[j]);
                    fos.write(biValue.getBytes());
                }
                //   fos.write(data);
            }
            if (msgs == null || msgs.isEmpty()) {
                head = DateHelper.datetimeToYYYY_MM_DD_HH_MM_SS_ML(new Date(System.currentTimeMillis())) + "   线程" + threadName + "未处理消息 " + msgID + "\r\n";
                fos.write(head.getBytes("GBK"));
                fos.write(head.getBytes("GBK"));
            }

            fos.flush();

        } catch (IOException e) {
            e.printStackTrace();
            DateHelper.screenPrintForEx("写未处理消息到文件失败" + e.getMessage());

        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * 写错误处理消息到文件
     * 
     * @param threadName
     * @param msg 
     */
    public synchronized static void writeErrHandleMsgToFile(String threadName, Vector msg) {
        //  if(msgs == null || msgs.isEmpty() )
        //    return;

        String fileName = AppConstant.UnHanledMsgLogDir + "/" + errName;
        File f = null;
        FileOutputStream fos = null;
        String msgID = "";
        String head = "";
        byte[] data = (byte[]) msg.get(1);
        String biValue = "";
        try {
            f = new File(fileName);
            if (!f.exists()) {
                f.createNewFile();
            }
            fos = new FileOutputStream(f, true);

            msgID = "" + (char) data[0] + (char) data[1];
            head = "\r\n" + DateHelper.datetimeToYYYY_MM_DD_HH_MM_SS_ML(new Date(System.currentTimeMillis()))
                    + "   线程" + threadName + "未处理消息 " + msgID + "\r\n";
            fos.write(head.getBytes("GBK"));
            for (int j = 0; j < data.length; j++) {
                //value =(byte)(0x30+data[j]);
                biValue = Integer.toHexString((int) data[j]);
                fos.write(biValue.getBytes());
            }
            //  fos.write(data);

            fos.flush();

        } catch (IOException e) {
            DateHelper.screenPrintForEx("写未处理消息到文件失败");

        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
