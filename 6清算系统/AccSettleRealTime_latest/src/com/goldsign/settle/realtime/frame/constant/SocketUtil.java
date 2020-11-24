/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.constant;

import com.goldsign.settle.realtime.frame.message.ConstructTacMessage;
import com.goldsign.settle.realtime.frame.message.ConstructTacMessageBase;
import com.goldsign.settle.realtime.frame.vo.FileRecordTacBase;
import com.goldsign.settle.realtime.frame.vo.SocketAttribute;
import com.goldsign.settle.realtime.frame.vo.TacCheckResult;
import com.goldsign.settle.realtime.frame.vo.TacReturnResult;

import java.io.IOException;
import java.io.InputStream;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Logger;

/**
 *
 * @author hejj
 */
public class SocketUtil {

    private static String RETURN_SUCCESS = "00";

    private Logger logger = Logger.getLogger(SocketUtil.class.getName());

    public void connectServer(SocketAttribute attr) throws SocketException, IOException {
        Socket socket = new Socket();
        socket.setSoTimeout(attr.getTimeout());
        InetSocketAddress sa = new InetSocketAddress(attr.getServer(), attr.getPort());
        socket.connect(sa);
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        attr.setIn(in);
        attr.setOut(out);

    }

    public void closeConnect(InputStream in, OutputStream out) {
        try {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getStringByCharArry(byte[] bs, int offset, int len) {
        char[] cs = new char[len];

        for (int i = 0; i < len; i++) {
            cs[i] = (char) bs[offset + i];

        }
        String str = new String(cs);
        return str;
    }

    private String getStringByCharArrayHex(int b) {

        String str = Integer.toHexString(b);
        if (str.length() == 1) {
            str = "0" + str;
        }
        return str;
    }

    private byte[] getBytes(char[] cs) {
        byte[] bs = new byte[cs.length];
        int i = 0;
        StringBuffer sb = new StringBuffer();
        for (char c : cs) {
            bs[i] = (byte) c;
            sb.append((int) bs[i] + " ");
            i++;
        }
        //logger.info("传入加密机数据："+sb.toString());
        return bs;
    }

    private char[] getCharsSub(char[] cs, int offset) {
        int len = cs.length;
        char[] cs1 = new char[len - offset];
        for (int i = 0; i < cs1.length; i++) {
            cs1[i] = cs[offset + i];
        }
        return cs1;

    }

    public TacCheckResult writeMessage(char[] cs, SocketAttribute attr, FileRecordTacBase frb) throws IOException {
        OutputStream out = attr.getOut();
        InputStream in = attr.getIn();
        // byte[] retMsg = new byte[6];
        TacCheckResult result = new TacCheckResult();
        String checkResult = ConstructTacMessageBase.CHECK_FAILURE;
        String tacInEncoder;
        String tacInRecord;

        out.write(this.getBytes(cs), 0, cs.length);
        out.flush();
        TacReturnResult trr = this.readResult(in);
        //in.read(retMsg);
        if (trr.getErrCode().equals(RETURN_SUCCESS)) {
            tacInEncoder = trr.getData();
            tacInRecord = frb.getTacInRecord();
            if (tacInEncoder.equals(tacInRecord)) {
                checkResult = ConstructTacMessageBase.CHECK_SUCCESS;
            }

        }

        // result.setMsgType(this.getStringByCharArry(retMsg, 2, 2));
        result.setRetCode(checkResult);
        result.setRetCodeFromEncoder(trr.getErrCode());
        result.setTac(trr.getData());

        return result;

    }

    private TacReturnResult readResult(InputStream in) throws IOException {
        TacReturnResult trr = new TacReturnResult();
        byte[] bCount = new byte[2];
        in.read(bCount);
        int count = this.getShortByHtoL(bCount, 0);
        trr.setMsgLen(count);

        byte[] data = new byte[count];
        in.read(data);
        int offset = 0;
        //区域编码
        int len = 2;
        int encode = this.getShortByHtoL(data, offset);
        offset += len;
        trr.setEncode(encode);
        //密钥索引
        len = 2;
        int keyIndex = this.getShortByHtoL(data, offset);
        offset += len;
        trr.setKeyIndex(keyIndex);
        //报文序号
        len = 4;
        int msgSeq = this.getLong(data, offset);
        offset += len;
        trr.setMsgSeq(msgSeq);
        //预留
        int reserve = this.getLong(data, offset);
        offset += len;
        trr.setReserve(reserve);

        //响应代码
        len = 2;
        String retcode = this.getCharString(data, offset, len);
        offset += len;
        trr.setRetCode(retcode);
        //错误代码
        len = 2;
        String errCode = this.getCharString(data, offset, len);
        offset += len;
        trr.setErrCode(errCode);

        //数据
        len = 8;
        String tac = this.getCharString(data, offset, len);
        offset += len;
        trr.setData(tac);
        /*   
        if (errCode.equals(RETURN_SUCCESS)) {
            //数据长度

            len = 3;
            String sDataLen = this.getCharString(data, offset, len);
            offset += len;
            trr.setDataLen(sDataLen);
            //数据
            len = Integer.parseInt(sDataLen);
            String sData = this.getCharString(data, offset, len);
            offset += len;
            trr.setData(sData);
        }
         */

        return trr;

    }

    public TacCheckResult writeMessage56(char[] cs, SocketAttribute attr) throws IOException {
        OutputStream out = attr.getOut();
        InputStream in = attr.getIn();

        byte[] retMsg = new byte[14];
        TacCheckResult result = new TacCheckResult();
        out.write(this.getBytes(cs));
        out.flush();
        in.read(retMsg);

        result.setMsgType(this.getStringByCharArry(retMsg, 2, 2));
        result.setRetCode(this.getStringByCharArry(retMsg, 4, 2));
        result.setTac(this.getStringByCharArry(retMsg, 6, 8));

        logger.info("加密机返回：" + "代码：" + result.getRetCode() + " 消息类型：" + result.getMsgType() + " TAC:" + result.getTac());

        return result;

    }

    public int getShort(byte[] data, int offset) {
        int low = byteToInt(data[offset]);
        int high = byteToInt(data[offset + 1]);
        return high * 16 * 16 + low;
    }

    public int getShortByHtoL(byte[] data, int offset) {
        int high = byteToInt(data[offset]);
        int low = byteToInt(data[offset + 1]);

        return high * 16 * 16 + low;
    }

    public int byteToInt(byte b) {
        int i = 0;
        if (b < 0) {
            i = 256 + b;
        } else {
            i = b;
        }
        return i;
    }

    public int getLong(byte[] data, int offset) {
        int high = getShortByHtoL(data, offset);
        int low = getShortByHtoL(data, offset + 2);
        return high * 16 * 16 * 16 * 16 + low;
    }

    public int getByte(byte[] data, int offset) {
        return byteToInt(data[offset]);
    }

    public String getCharString(byte[] data, int offset, int length) throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            for (int i = 0; i < length; i++) {
                sb.append(byteToChar(data[offset + i]));
            }
        } catch (Exception e) {
            throw new IOException(" " + e);
        }
        return sb.toString();
    }

    public char byteToChar(byte b) {
        return (char) b;
    }
}
