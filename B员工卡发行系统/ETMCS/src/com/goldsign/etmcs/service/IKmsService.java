/*
 * 文件名：IRwDeviceService
 * 版权：Copyright: goldsign (c) 2013
 * 描述：RW读写器IRwDeviceService服务实例
 */

package com.goldsign.etmcs.service;

import com.goldsign.csfrm.service.IBaseService;
import com.goldsign.csfrm.vo.CallResult;
import com.goldsign.etmcs.vo.KmsCfgParam;


/*
 * @author     lindaquan
 * @version    V1.0
 */

public interface IKmsService extends IBaseService{

    CallResult author(KmsCfgParam param);

    CallResult getCardKey(String cardNo);
    
    CallResult getConnectStatusKey(KmsCfgParam param);
    
    /**
     * 设置加密机锁
     * 
     * @return 
     */
    boolean setKmsLock();

    /**
     * 解加密机锁
     * 
     * @return 
     */
    boolean setKmsUnlock();
}
