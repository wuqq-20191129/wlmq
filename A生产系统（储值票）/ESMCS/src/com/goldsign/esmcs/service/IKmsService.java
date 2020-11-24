/*
 * 文件名：IRwDeviceService
 * 版权：Copyright: goldsign (c) 2013
 * 描述：RW读写器IRwDeviceService服务实例
 */

package com.goldsign.esmcs.service;

import com.goldsign.csfrm.service.IBaseService;
import com.goldsign.csfrm.vo.CallResult;
import com.goldsign.esmcs.vo.KmsCfgParam;


/*
 * @author     lindaquan
 * @version    V1.0
 */

public interface IKmsService extends IBaseService{

    /**
     * 重认证
     * 
     * @return 
     */
    CallResult reauthor();
    
    /**
     * 认证
     * 
     * @param param
     * @return 
     */
    CallResult author(KmsCfgParam param);

    /**
     * 取票卡密钥
     * 
     * @param cardNo
     * @return 
     */
    CallResult getCardKey(String cardNo);
    
    /**
     * 取单程票密钥
     * 
     * @param phyNo
     * @param logicNo
     * @return 
     */
    CallResult getTokenKey(String phyNo, String logicNo);

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
