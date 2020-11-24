/*
 * 文件名：PSamIssueDll
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.kmscommu.dll.library;

import com.goldsign.kmscommu.dll.structure.*;
import com.sun.jna.Library;
import com.sun.jna.Native;


/*
 * 用户卡授权、取密钥DLL类（JNA调用C接口）
 * @author     lindaquan
 * @version    V1.0
 */

public interface CardKeyGetDll extends Library {
    
    CardKeyGetDll INSTANCE = (CardKeyGetDll) Native.loadLibrary("GET_CARD_KEY", CardKeyGetDll.class);
    
    /**
     * 授权方法
     * @param in
     * @param out
     * @return 
     */
    int get_card_key_op(AuthInInf.ByReference in, AuthOutInf.ByReference out);
    
    /**
     * 取密钥方法
     * @param in
     * @param out
     * @return 
     */
    int get_card_key_op(CardKeyInInf.ByReference in, CardKeyOutInf.ByReference out);
    
    /**
     * 取单程票密钥
     * 
     * @param in
     * @param out
     * @return 
     */
    int get_card_key_op(TokenKeyInInf.ByReference in, TokenKeyOutInf.ByReference out);
}
