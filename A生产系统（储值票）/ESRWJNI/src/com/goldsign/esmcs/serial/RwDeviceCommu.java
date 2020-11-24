/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.serial;

import com.goldsign.rwcommu.connection.RWSerialConnection;
import com.goldsign.rwcommu.exception.SerialException;
import java.io.IOException;
import org.apache.log4j.Logger;

/**
 * RW读头通讯-ES部分
 *
 * @author lenovo
 */
public class RwDeviceCommu {

    //串口句柄
    private RWSerialConnection serialConnection = null;
    private static final Logger logger = Logger.getLogger(RwDeviceCommu.class.getName());
    public RwDeviceCommu(){
       
    }
    
    /**
     * 打开串口
     * 
     * @throws SerialException 
     */
    public void open(String comNo) throws SerialException{
        
        if(null == serialConnection){
            serialConnection = new RWSerialConnection(comNo);
        }else{
            serialConnection.close();
        }
        serialConnection.open();
    }
    
    /**
     * 关闭串口
     * 
     */
    public void close(){
        serialConnection.close();
    }
    
    /**
     * 环境初始化
     * 
     * @param datas
     * @return 
     */
    public byte[] esInitDevice(byte[] bytes) throws IOException, SerialException{
        
        byte command = (byte)0x00;
        
        byte[] bytesRet = serialConnection.callSerial(command, bytes);
        
        return bytesRet;
    }
    
    /**
     * 票卡分析
     * 
     * @return 
     */
    public byte[] esAnalyze() throws IOException, SerialException{
        
    
        byte command = (byte)0x33;

        byte[] bytesRet = serialConnection.callSerial(command, null);
        
        return bytesRet;
    }
    
    /**
     * 票卡初始化
     * 
     * @param datas
     * @return 
     */
    public byte[] esInit(byte[] bytes) throws IOException, SerialException{
    
        byte command = (byte)0x34;
        
        byte[] bytesRet = serialConnection.callSerial(command, bytes);
        
        return bytesRet;
    }
    
    /**
     * 票卡预赋值
     * 
     * @param datas
     * @return 
     */
    public byte[] esEvaluate(byte[] bytes) throws IOException, SerialException{
    
        byte command = (byte)0x35;
        
        byte[] bytesRet = serialConnection.callSerial(command, bytes);
        
        return bytesRet;
    }
    
    /**
     * 票卡注销
     * 
     * @param datas
     * @return 
     */
    public byte[] esDestroy(byte[] bytes) throws IOException, SerialException{
    
        byte command = (byte)0x36;
        
        byte[] bytesRet = serialConnection.callSerial(command, bytes);
        
        return bytesRet;
    }
    
    /**
     * 票卡重编码
     * 
     * @param datas
     * @return 
     */
    public byte[] esRecode(byte[] bytes) throws IOException, SerialException{
    
        byte command = (byte)0x37;
        
        byte[] bytesRet = serialConnection.callSerial(command, bytes);
        
        return bytesRet;
    }
    
    /**
     * 记名卡制作
     * 
     * @param datas
     * @return 
     */
    public byte[] esSignCard(byte[] bytes) throws IOException, SerialException{
        
    
        byte command = (byte)0x38;
        
        byte[] bytesRet = serialConnection.callSerial(command, bytes);
        
        return bytesRet;
    }
    
    /**
     * 票卡清卡
     *
     * @param datas
     * @return
     */
    public byte[] esClear(byte[] bytes) throws IOException, SerialException{
    
        byte command = (byte)0x39;
        
        byte[] bytesRet = serialConnection.callSerial(command, bytes);
        
        return bytesRet;
    }
    
    /**
     * 设备版本号
     * 
     * @param datas
     * @return 
     */
    public byte[] esVersions(byte[] bytes) throws IOException, SerialException{
        
        byte command = (byte)0x01;
        
        byte[] bytesRet = serialConnection.callSerial(command, bytes);
        
        return bytesRet;
    }
    
    /**
     * 取SAM卡号
     *
     * @param datas
     * @return
     */
    public byte[] esSamCard(byte[] bytes) throws IOException, SerialException{
        
        byte command = (byte)0x02;
        
        byte[] bytesRet = serialConnection.callSerial(command, bytes);
        
        return bytesRet;
    }
}
