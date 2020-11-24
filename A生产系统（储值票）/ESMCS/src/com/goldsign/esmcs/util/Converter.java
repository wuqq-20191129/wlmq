/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.util;

import com.goldsign.csfrm.util.CharUtil;
import com.goldsign.csfrm.util.StringUtil;
import com.goldsign.esmcs.dll.structure.ESANALYZE;
import com.goldsign.esmcs.dll.structure.ORDERSIN;
import com.goldsign.esmcs.dll.structure.ORDERSOUT;
import com.goldsign.esmcs.env.AppConstant;
import com.goldsign.esmcs.env.CommuConstant;
import com.goldsign.esmcs.env.LocalFileConstant;
import com.goldsign.esmcs.vo.EsAnayzeVo;
import com.goldsign.esmcs.vo.OrderInVo;
import com.goldsign.esmcs.vo.OrderOutVo;

/**
 * 数据转换工具类
 * 
 * @author lenovo
 */
public class Converter {
    
    /**
     * 字符串对象类型转整型
     * 
     * @param obj
     * @return 
     */
    public static int objstrToInt(Object obj){
        
        return StringUtil.getInt(String.valueOf(obj));
    }
    
    /**
     * 取CRC32值
     * 
     * @param sb
     * @return 
     */
    public static String getCRC32Value(StringBuffer sb) {

        String crcRet = "";
        if (sb.length() != 0) {
            String s = sb.toString();
            System.out.println("CRC的内容：");
            System.out.print(s);
            byte[] b = null;//new byte[2800000];
            b = s.getBytes();
            long crc32 = CharUtil.getCRC32Value(b);
            String crc = Long.toHexString(crc32);
            for (int i = crc.length(); i < 8; i++) {
                crc = "0" + crc;
            }
            crcRet = crc;
        } else {
            crcRet = "00000000";
        }
        System.out.println("CRC:"+crcRet);
        return crcRet;
    }

    /**
     * ESANALYZE.ByReference对象转EsAnayzeVo对象
     * 
     * @param pAnalyze
     * @return 
     */
    public static EsAnayzeVo structureToVo(ESANALYZE.ByReference pAnalyze) {
        
        EsAnayzeVo esAnayzeVo = new EsAnayzeVo();
        esAnayzeVo.setStatus(String.valueOf(byteToUnByte(pAnalyze.bStatus)));
        esAnayzeVo.setTicketType(new String(pAnalyze.bTicketType));
        esAnayzeVo.setLogicalID(new String(pAnalyze.cLogicalID));
        esAnayzeVo.setPhysicalID(new String(pAnalyze.cPhysicalID));
        esAnayzeVo.setCharacter(String.valueOf(pAnalyze.bCharacter));
        esAnayzeVo.setIssueStatus(String.valueOf(pAnalyze.bIssueStatus));
        esAnayzeVo.setIssueDate(bytesToBcdStr(pAnalyze.dtIssueDate));
        esAnayzeVo.setExpire(bytesToBcdStr(pAnalyze.dtExpire));
        esAnayzeVo.setBalance(bytesToInt(pAnalyze.lBalance));
        esAnayzeVo.setDeposite(bytesToInt(pAnalyze.lDeposite));
        
        return esAnayzeVo;
    }
    
    /**
     * 字节数组转整型,低位在前
     * 
     * @param b
     * @return 
     */
    public static int bytesToInt(byte[] b) {
        
        byte[] a = new byte[4];
        int i = a.length - 1;
        int j = b.length - 1;
        for (; i >= 0 ; i--,j--) {//从b的尾部(即int值的低位)开始copy数据
            if(j >= 0){
                a[i] = b[j];
            }else{
                a[i] = 0;//如果b.length不足4,则将高位补0
            }
        }
        int v0 = (a[0] & 0xff) << 0;//&0xff将byte值无差异转成int,避免Java自动类型提升后,会保留高位的符号位
        int v1 = (a[1] & 0xff) << 8;
        int v2 = (a[2] & 0xff) << 16;
        int v3 = (a[3] & 0xff) << 24;
        return v0 + v1 + v2 + v3;
    }
    
    /**
     * 整型字符串转字节数组,低位在前
     * 
     * @param str
     * @return 
     */
    public static byte[] intToBytes(String str){
        int i = StringUtil.getInt(str);
        return intToBytes(i);
    }
    
    /**
     * 整型转字节数组,低位在前
     * 
     * @param i
     * @return 
     */
    public static byte[] intToBytes(int i) {
        
        byte[] result = new byte[4];
        result[0] = (byte) (i & 0xFF);
        result[1] = (byte) ((i >> 8) & 0xFF);
        result[2] = (byte) ((i >> 16) & 0xFF);
        result[3] = (byte) ((i >> 24) & 0xFF);
        
        return result;
    }
    
    /**
     * 字节数组转BCD字符串
     * 
     * @param bytes
     * @return 
     */
    public static String bytesToBcdStr(byte[] bytes) {
        
        StringBuffer temp = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            int h = ((bytes[i] & 0xf0) >>> 4);
            int l = (bytes[i] & 0x0f);   
            temp.append(h).append(l);
        }
        return temp.toString() ;
    }
    
    /**
     * BCD字符串转字节数组
     * 
     * @param hex
     * @return 
     */
    public static byte[] bcdStrTobytes(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }
    
    /**
     * 字符转BYTE
     * 
     * @param c
     * @return 
     */
    private static byte toByte(char c) {   
        byte b = (byte) "0123456789ABCDEF".indexOf(c);   
        return b;   
    }
    
    /**
     * 有符号BTYE转无符号BYTE
     * 
     * @param byt
     * @return 
     */
    public static int byteToUnByte(byte byt){
        return byt&0xff;
    }

    /**
     * OrderInVo对象转ORDERSIN.ByReference
     * 
     * @param order
     * @return 
     */
    public static ORDERSIN.ByReference voToStructure(OrderInVo order) {
        
        ORDERSIN.ByReference pInput = new ORDERSIN.ByReference();
        pInput.cOrderNo = order.getOrderNo().getBytes();
        pInput.cApplicationNO = order.getApplicationNO().getBytes();
        pInput.wTicketType = StringUtil.getShort(order.getTicketType());
        //pInput.dwSysFlow = StringUtil.getInt(order.getDwSysFlow());
        pInput.lDeposite = intToBytes(order.getDeposite());
        pInput.lValue = intToBytes(order.getValue());
        pInput.dtStartDate = bcdStrTobytes(order.getcStartExpire());
        pInput.dtEndDate = bcdStrTobytes(order.getcEndExpire());
        //pInput.dtExpire = bcdStrTobytes(order.getExpire());       
        
        return pInput;
    }

    /**
     * ORDERSOUT.ByReference转OrderOutVo
     * 
     * @param pOutput
     * @return 
     */
    public static OrderOutVo structureToVo(ORDERSOUT.ByReference pOutput) {
        
        OrderOutVo order = new OrderOutVo();
        order.setTradeType(new String(pOutput.cTradeType));
        order.setOrderNo(new String(pOutput.cOrderNo));
        order.setTicketType(new String(pOutput.bTicketType));
        order.setApplicationNO(new String(pOutput.cApplicationNO));
        order.setLogicalID(new String(pOutput.cLogicalID));
        order.setPhysicalID(new String(pOutput.cPhysicalID));
        order.setDate(new String(pOutput.dtDate));
        order.setBalance(bytesToInt(pOutput.lBalance)+"");
        order.setStartDate(bytesToBcdStr(pOutput.bStartDate));
        order.setEndDate(bytesToBcdStr(pOutput.bEndDate));
        order.setSamID(new String(pOutput.cSAMID));
 
        return order;
    }
    
    /**
     * 操作类型，通过代码找名称
     *
     * @param code
     * @return
     */
    public static String getEsWorkTypeDes(String code){

        if(code.equals(AppConstant.WORK_TYPE_INITI)){
            return AppConstant.WORK_TYPE_INITI_NAME;
        }
        if(code.equals(AppConstant.WORK_TYPE_HUNCH)){
            return AppConstant.WORK_TYPE_HUNCH_NAME;
        }
        if(code.equals(AppConstant.WORK_TYPE_AGAIN)){
            return AppConstant.WORK_TYPE_AGAIN_NAME;
        }
        if(code.equals(AppConstant.WORK_TYPE_LOGOUT)){
            return AppConstant.WORK_TYPE_LOGOUT_NAME;
        }
     
        return code;
    }
    
    /**
     * 订单，通过代码找名称
     *
     * @param code
     * @return
     */
    public static String getEsOrderStatusDes(String code){

        if(code.equals(AppConstant.ES_ORDER_STATUS_BEGIN_NO)){
            return AppConstant.ES_ORDER_STATUS_BEGIN_NO_DES;
        }
        if(code.equals(AppConstant.ES_ORDER_STATUS_BEGIN_YES)){
            return AppConstant.ES_ORDER_STATUS_BEGIN_YES_DES;
        }
        if(code.equals(AppConstant.ES_ORDER_STATUS_END)){
            return AppConstant.ES_ORDER_STATUS_END_DES;
        }
        if(code.equals(AppConstant.ES_ORDER_STATUS_FINISH)){
            return AppConstant.ES_ORDER_STATUS_FINISH_DES;
        }
        
        return code;
    }
    
    /**
     * 设备，通过代码找名称
     *
     * @param code
     * @return
     */
    public static String getEsDeviceStatusDes(String code){

        if(code.equals(AppConstant.ES_DEVICE_STATUS_MAKE_BEGIN)){
            return AppConstant.ES_DEVICE_STATUS_MAKE_BEGIN_DES;
        }
        if(code.equals(AppConstant.ES_DEVICE_STATUS_MAKE_STOP)){
            return AppConstant.ES_DEVICE_STATUS_MAKE_STOP_DES;
        }
        if(code.equals(AppConstant.ES_DEVICE_STATUS_SORT_BEGIN)){
            return AppConstant.ES_DEVICE_STATUS_SORT_BEGIN_DES;
        }
        if(code.equals(AppConstant.ES_DEVICE_STATUS_SORT_STOP)){
            return AppConstant.ES_DEVICE_STATUS_SORT_STOP_DES;
        }
        if (code.equals(AppConstant.ES_DEVICE_STATUS_ERROR)) {
            return AppConstant.ES_DEVICE_STATUS_ERROR_DES;
        }
        if (code.equals(AppConstant.ES_DEVICE_STATUS_OPER_LOGIN)) {
            return AppConstant.ES_DEVICE_STATUS_OPER_LOGIN_DES;
        }
        if (code.equals(AppConstant.ES_DEVICE_STATUS_OPER_EXIT)) {
            return AppConstant.ES_DEVICE_STATUS_OPER_EXIT_DES;
        }        
        return code;
    }
    
    /**
     * 操作员，通过代码找名称
     * 
     * @param code
     * @return 
     */
    public static String getCommOperTypeDesc(String code){
        
        if(code.equals(CommuConstant.OPER_TYPE_ADMIN)){
            return CommuConstant.OPER_TYPE_ADMIN_NAME;
        }
        if(code.equals(CommuConstant.OPER_TYPE_COMM)){
            return CommuConstant.OPER_TYPE_COMM_NAME;
        }
        if(code.equals(CommuConstant.OPER_TYPE_MEMD)){
            return CommuConstant.OPER_TYPE_MEMD_NAME;
        }
        if(code.equals(CommuConstant.OPER_CODE_ERROR)){
            return CommuConstant.OPER_CODE_ERROR_NAME;
        }
        if(code.equals(CommuConstant.OPER_PWD_ERROR)){
            return CommuConstant.OPER_PWD_ERROR_NAME;
        }
        if(code.equals(CommuConstant.OPER_LGININ_ERROR)){
            return CommuConstant.OPER_LOGIN_ERROR_NAME;
        }
        if(code.equals(CommuConstant.OPER_LOGOUT_OK)){
            return CommuConstant.OPER_LOGOUT_OK_NAME;
        }
        return code;
    }
    
    /**
     * 操作员，错误文件
     *
     * @param code
     * @return
     */
    public static String getEsFtpErrorFileDesc(String code){
        
        if(code.equals(LocalFileConstant.ES_FTP_ERROR_FILE_RESON_ERROR_FILE_NAME)){
            return LocalFileConstant.ES_FTP_ERROR_FILE_RESON_ERROR_FILE_NAME_DES;
        }
        if(code.equals(LocalFileConstant.ES_FTP_ERROR_FILE_RESON_ERROR_CONTENT)){
            return LocalFileConstant.ES_FTP_ERROR_FILE_RESON_ERROR_CONTENT_DES;
        }
        if(code.equals(LocalFileConstant.ES_FTP_ERROR_FILE_RESON_ERROR_FORMAT)){
            return LocalFileConstant.ES_FTP_ERROR_FILE_RESON_ERROR_FORMAT_DES;
        }
        if(code.equals(LocalFileConstant.ES_FTP_ERROR_FILE_RESON_ERROR_OTHER)){
            return LocalFileConstant.ES_FTP_ERROR_FILE_RESON_ERROR_OTHER_DES;
        }
        if(code.equals(LocalFileConstant.ES_FTP_ERROR_FILE_RESON_ERROR_FILE_NOEXITS)){
            return LocalFileConstant.ES_FTP_ERROR_FILE_RESON_ERROR_FILE_NOEXITS_DES;
        }
        if(code.equals(LocalFileConstant.ES_FTP_ERROR_FILE_RESON_ERROR_STA_DET_NOT_SAME)){
            return LocalFileConstant.ES_FTP_ERROR_FILE_RESON_ERROR_STA_DET_NOT_SAME_DES;
        }

        return code;
    }
}
