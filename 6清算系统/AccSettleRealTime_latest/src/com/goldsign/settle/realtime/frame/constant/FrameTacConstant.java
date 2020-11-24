/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.frame.constant;

/**
 *
 * @author hejj
 */
public class FrameTacConstant {

    /**
     * TAC校验相关
     */
    //连接
    public static int SOCKET_TIME_OUT = 3000;
    public static String SOCKET_SERVER = "";
    public static int SOCKET_PORT = 0;
    //密钥
    public static String VALUE_KEY_ALG = "0";
    public static String VALUE_KEY_VER = "2";
    public static String VALUE_KEY_GRP = "01";
    public static String VALUE_KEY_ID = "05";

    //密钥标识及密钥索引
    public static String KEY_TEST_FLAG="1";
    public static String VALUE_KEY_TEST_FLAG = "1";
    public static String VALUE_KEY_IDX_TEST_CPU = "";//cpu卡测试密钥索引
    public static String VALUE_KEY_IDX_TEST_M1 = "";//m1或单程票卡测试密钥索引
    public static String VALUE_KEY_IDX_PRODUCT_CPU = "";//cpu卡正式密钥索引
    public static String VALUE_KEY_IDX_PRODUCT_M1 = "";//m1或单程票卡正式密钥索引

}
