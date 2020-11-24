package com.goldsign.rwcommu.connection;

/* @(#)SerialConnection.java	1.6 98/07/17 SMI
 *
 * Copyright (c) 1998 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Sun grants you ("Licensee") a non-exclusive, royalty free, license
 * to use, modify and redistribute this software in source and binary
 * code form, provided that i) this copyright notice and license appear
 * on all copies of the software; and ii) Licensee does not utilize the
 * software in a manner which is disparaging to Sun.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 * ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES,
 * INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND
 * ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THE
 * SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS
 * BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES,
 * HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING
 * OUT OF THE USE OF OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * This software is not designed or intended for use in on-line control
 * of aircraft, air traffic, aircraft navigation or aircraft
 * communications; or in the design, construction, operation or
 * maintenance of any nuclear facility. Licensee represents and
 * warrants that it will not use or redistribute the Software for such
 * purposes.
 */

import com.goldsign.rwcommu.exception.SerialCRCException;
import com.goldsign.rwcommu.exception.SerialException;
import com.goldsign.rwcommu.util.CRC;
import com.goldsign.rwcommu.util.Converter;
import com.goldsign.rwcommu.vo.SerialParameters;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.ByteBuffer;

/**
A class that handles the details of a serial connection. Reads from one 
TextArea and writes to a second TextArea. 
Holds the state of the connection.
*/
public class RWSerialConnection extends SerialConnection{

    private static final byte MES_REQ_BEGIN = (byte) 0xAA;//请求协议头
    private static final byte MES_RES_BEGIN = (byte) 0xBB;//应答协议头
    private static final byte MES_NODE_NO = (byte) 0x00;//节点编号
    private static final byte MES_REQ_HEAD_LEN = 16;//请求头长度
    private static final byte MES_RES_HEAD_LEN = 7;//应答头长度
    private static final byte MES_CRE_LEN = 2;//CRC长度
    private static final byte MES_CMD_01 = (byte)0x01;//01指类类型
    private static final byte MES_PARAM_0 = (byte)0x00;//参数p0
    private static final byte MES_PARAM_1 = (byte)0x00;//参数p1
    private static final byte MES_READ_TIMEOUT = 30;//读超时，10秒
    
    public static byte MES_PARAM_BEE;//蜂鸣参数，根据应用系统“是否蜂鸣”进行设置，默认为0x00，不蜂鸣
    
    private RWSerialConnection(SerialParameters parameters) {
        super(parameters);
    }

    public RWSerialConnection(String portName) {
        
        this(new SerialParameters(portName));

    }
 
    /**
     * 发送数据
     * 
     * @param type
     * @param command
     * @param param1
     * @param param2
     * @param bytes
     * @throws IOException 
     */
    private void send(byte type, byte command, byte param1, byte param2, byte... bytes) throws IOException{
        param2 = MES_PARAM_BEE;//蜂鸣次数
        int bytesLen = (bytes != null) ? bytes.length : 0;
        ByteBuffer data = ByteBuffer.allocate(bytesLen + MES_REQ_HEAD_LEN);
        data.put(MES_REQ_BEGIN)//协议头
                .put(MES_NODE_NO)//节点编号
                .put(Converter.getNumber())//序列号 自动加1
                .put(type)//指令分类码
                .put(command)//命令码
                .put(param1)//参数P0
                .put(param2)//参数P1
                .put(Converter.hexStringToBytes(Converter.curDateToStrYYYYMMDDHHMMSS()))//时间
                .put(Converter.to2Byte(bytesLen));//长度
        if (bytes != null) {
            data.put(bytes);//数据域
        }
        ByteBuffer dataCrc = ByteBuffer.allocate(data.array().length + MES_CRE_LEN);//最终完整数据
        dataCrc.put(data.array());
        dataCrc.put(CRC.crc16Bytes(data.array()));//效验位
        System.out.println("输入1：\n"+Converter.bytesToHexString(dataCrc.array()));
        MES_PARAM_BEE = 0x00;
        os.write(dataCrc.array());
        
        os.flush();
    }
    
    /**
     * 调用串口
     * 
     * @param command 命令类型
     * @param datas 数据
     * @return
     * @throws IOException 
     */
    public byte[] callSerial(byte command, byte... datas) throws IOException, SerialException{
    
        send(MES_CMD_01, command, MES_PARAM_0, MES_PARAM_1, datas);
        try {
            return receive();
        } catch (SerialCRCException ex) {
            return new byte[0];
        }
    }
    
    /**
     * 读长度数据
     * 
     * @param datas
     * @return
     * @throws IOException 
     */
    private void read(byte[] datas) throws IOException, SerialException{
        
        int len = datas.length;
        long startTime = System.currentTimeMillis();
        
        while(is.available() < len){
            if(isReadTimeout(startTime)){
                throw new SerialException("read timeout!");
            }
        }
        is.read(datas);

    }
    
    /**
     * 读是否超时,10秒超时
     * 
     * @param startTime
     * @return 
     */
    private boolean isReadTimeout(long startTime){

        long endTime = System.currentTimeMillis();
        long inv = (endTime - startTime)/1000;
        if(inv > MES_READ_TIMEOUT){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * 接收数据
     * 
     * @return
     * @throws IOException 
     */
    private byte[] receive() throws IOException, SerialCRCException, SerialException {

        byte[] headerInfo = new byte[MES_RES_HEAD_LEN];// 信息头+长度
        byte[] bottomInfo = new byte[MES_CRE_LEN];// 效验码信息
        read(headerInfo);
        if (headerInfo[0] == MES_RES_BEGIN) {
            int dataLen = Converter.toInt(new byte[]{headerInfo[6], headerInfo[5]});
            byte[] data = new byte[dataLen];
            ByteBuffer crcInfo = ByteBuffer.allocate(dataLen + MES_RES_HEAD_LEN);
            if (dataLen > 0) {
                read(data);
                crcInfo.put(headerInfo).put(data);
            } else {
                crcInfo.put(headerInfo);
            }
            read(bottomInfo);

            ByteBuffer buff = ByteBuffer.allocate(dataLen 
                    + MES_RES_HEAD_LEN + MES_CRE_LEN);
            buff.put(headerInfo).put(data).put(bottomInfo);
            
            System.out.println("返回1：\n"+Converter.bytesToHexString(crcInfo.array()));   
            
            byte[] crc16 = CRC.crc16Bytes(crcInfo.array());
            if (bottomInfo[0] != crc16[0] || bottomInfo[1] != crc16[1]) {
                throw new SerialCRCException("crc check error!");
            }
            
            return data;
        }
        return null;
    }
}
