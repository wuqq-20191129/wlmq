/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.env;

import com.goldsign.esmcs.vo.RwPortStateVo;

/**
 * 储值票应用常量
 * 
 * @author lenovo
 */
public class PKAppConstant extends AppConstant{

    public static final String APP_CODE = "PK";
    public static final String APP_NAME = "储值票生产系统";
    
    public static final String STATUS_BAR_BOX_PORT = "boxPort";
    public static final String STATUS_BAR_BOX_PORT_NAME = "  ES票箱:";
    public static boolean BOX_PORT = false;
    public static final String STATUS_BAR_CHANNEL_PORT = "channelPort";
    public static final String STATUS_BAR_CHANNEL_PORT_NAME = "ES通道:";
    public static boolean CHANNEL_PORT = false;
    //public static final String STATUS_BAR_READER_PORT = "readerPort";
    //public static final String STATUS_BAR_READER_PORT_NAME = "读写器:";
    //public static boolean READER_PORT = false;
    public static RwPortStateVo STATUS_BAR_RW_PORT_STATE = new RwPortStateVo("readerPort", "读写器:", false);
    
    public static final short ES_CARD_BOX_TOTAL_NUM = 500;//回收箱默认容量卡数量
    public static final short ES_CARD_SITE_NUM = 7;      //卡位数量
    
    public static final byte[] BOX_RUN_STATE_OKS = new byte[]{(byte)4, (byte)5}; //票箱已安装，处于运行状态(状态OK),腾出接票空间(状态OK)

}
