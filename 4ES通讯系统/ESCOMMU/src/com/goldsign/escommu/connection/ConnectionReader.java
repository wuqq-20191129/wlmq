package com.goldsign.escommu.connection;

import com.goldsign.escommu.env.CommuConstant;
import com.goldsign.escommu.exception.MessageException;
import com.goldsign.escommu.util.DateHelper;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Vector;

public class ConnectionReader {

    private BufferedInputStream in;
    private int serialNoShouldBe;
    private int serialNo;
    private boolean stopReader = false;
    private String resultCode = "";
    private int fromClient = -1;

    //private static Logger logger = Logger.getLogger(ConnectionReader2.class.getName());
    /**
     * 01xx	message 0100	Received DATA message. 0101	Received NODATA message.
     *
     * 10xx	Socket,IO 1001	Socket error! 1002	IO error!
     *
     * 110x	message（receive） 1101	Message start flag error! 1102	Message type
     * error! 1103	Message serial NO error! 1104	Message end flag error! 1105
     * Message read timeout!
     *
     * 115x	message（send） 1151	Message length longer than 65536 bytes. 1152
     * Message length is 0. 1153	Message is null.
     */
    public ConnectionReader(BufferedInputStream bin, int aSerialNoShouldBe) {
        in = bin;
        serialNoShouldBe = aSerialNoShouldBe;
    }

    /**
     * 读数据
     * 
     * @return 
     */
    public Vector read() {
        Vector readerResult = new Vector();
        int dataLength;
        byte[] data = null;

        try {
            //read STX
            readOneByte();
            if ((byte) fromClient == -1) {
                DateHelper.screenPrint(" - 消息读错误!");
                resultCode = "1002";
                throw new MessageException(resultCode);
            }
            if ((byte) fromClient != CommuConstant.STX_B) {
                DateHelper.screenPrint(" - 消息开始标识错误!");
                resultCode = "1101";
                throw new MessageException(resultCode);
            }

            //read message type
            readOneByte();
            if (fromClient != CommuConstant.NDT 
                    && fromClient != CommuConstant.DTA && fromClient != CommuConstant.QRY) {
                DateHelper.screenPrint(" - 消息类型错误!");
                resultCode = "1102";
                throw new MessageException(resultCode);
            }

            //read serial NO
            readOneByte();

            if (serialNoShouldBe != -1) { //for client test,serialNoShouldBe = -1 means ignore serial no check
                if (fromClient != serialNoShouldBe) {
                    DateHelper.screenPrint(" - 消息序列号错误!");

                    resultCode = "1103";
                    throw new MessageException(resultCode);
                }
            }
            serialNo = fromClient;

            //read data length
            readOneByte();
            dataLength = fromClient + in.read() * 256;

            //read data
            data = new byte[dataLength];
            this.readBytes(data, 0, data.length);
            /*
             * for (int i = 0; i < dataLength; i++) { readOneByte(); data[i] =
             * (byte) (fromClient); }
             */

            //read ETX
            readOneByte();
            if (fromClient != CommuConstant.ETX) {
                DateHelper.screenPrint(" - 消息结束标识错误!");
                resultCode = "1104";
                throw new MessageException(resultCode);
            } else {
                if (dataLength == 0) {
                    resultCode = "0101";
                } else {
                    resultCode = "0100";
                }
            }

        } catch (IOException e) {
            resultCode = "1002";
            DateHelper.screenPrintForEx("IO 异常:" + e.getMessage());
        } catch (MessageException e) {
            DateHelper.screenPrintForEx("接收的数据处理时有错,错误代码为:" + e.getMessage());
        } finally {
            readerResult.add(resultCode);
            readerResult.add(data);
            printRecBs(data);//测试使用
            readerResult.add(new Integer(serialNo));
            return readerResult;
        }
    }
    
    private void printRecBs(byte[] datas){
        if(null == datas){
            return;
        }
        System.out.println("-----------------测试 打印接收数据 START-------------------");
        for(byte b: datas){
            System.out.print(b+" ");
        }
        System.out.println();
        String str = new String(datas);
        System.out.println("数据:"+str);
        System.out.println("-----------------测试 打印接收数据  EDN-------------------");
    }

    /**
     * 停止读
     * 
     */
    public void stopReader() {
        stopReader = true;
    }

    /**
     * 读一字节
     * 
     * @throws IOException
     * @throws MessageException 
     */
    private void readOneByte() throws IOException, MessageException {
        if (!stopReader) {
            fromClient = in.read();
        } else {
            resultCode = "1105";
            throw new MessageException(resultCode);
        }
    }

    /**
     * 读多字节
     * 
     * @param buff
     * @param off
     * @param len
     * @throws IOException
     * @throws MessageException 
     */
    private void readBytes(byte[] buff, int off, int len) throws IOException, MessageException {
        int readTotal = 0;
        if (!stopReader) {
            readTotal = readTotal = in.read(buff, off, len);
            if (readTotal != len) {
                resultCode = "1201";
                throw new MessageException(resultCode);
            }

        } else {
            resultCode = "1105";
            throw new MessageException(resultCode);
        }
    }
}
