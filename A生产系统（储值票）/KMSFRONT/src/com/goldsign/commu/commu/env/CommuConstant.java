package com.goldsign.commu.commu.env;

/**
 *
 * @author lenovo
 */
public class CommuConstant {
    
     public static final String CONN_CLOSED = "0";    //连接关闭
     public static final String CONN_OPENED = "1";    //连接打开
     
     public static final int CONN_IPCHECK_NORMAL = 0;                   //IP正常
     public static final int CONN_IPCHECK_ILLEGAL = 1;                   //IP非法
     public static final int CONN_IPCHECK_OPENED = 2;             //IP已打开
     
     public static String COMM_STX_SEP = "{";
     public static String COMM_ETX_SEP = "{";
     public static String COMM_DATA_SEP = "#";
     public static int COMM_DATA_LEN = 3;
     
     public static boolean COMMU_MODE_SYN = false;//true:同步，false：异步
    
     public static final String RESULT_CODE_NDT = "0101";   //成功，数据长度为0
     public static final String RESULT_CODE_DTA = "0100";   //成功，数据长度不为0
     public static final String RESULT_CODE_SUC = "0";   //成功

}
