/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.service;

import com.goldsign.csfrm.vo.CallParam;
import com.goldsign.csfrm.vo.CallResult;
import com.goldsign.esmcs.exception.RwJniException;
import com.goldsign.esmcs.vo.KmsCardVo;

/**
 * RW设备接口
 * 
 * @author lenovo
 */
public interface IRwDeviceService {

    /**
     * 打开读头端口
     * 
     * @param callParam
     * @return
     * @throws RwJniException 
     */
    CallResult openRwPort(CallParam callParam)throws RwJniException;
    
    /**
     * 关闭读头端口
     * 
     * @param callParam
     * @return
     * @throws RwJniException 
     */
    CallResult closeRwPort(CallParam callParam)throws RwJniException;
    
    /**
     * 读卡
     * 
     * @param callParam
     * @return
     * @throws RwJniException 
     */
    CallResult readCard(CallParam callParam)throws RwJniException;
    
    /**
     * 写卡
     * 
     * @param callParam
     * @return
     * @throws RwJniException 
     */
    CallResult writeCard(CallParam callParam)throws RwJniException;
    
    /**
     * 取读头版本
     * 
     * @return
     * @throws RwJniException 
     */
    CallResult getVersions(CallParam callParam)throws RwJniException;
    
    /**
     * 取SAM卡号
     *
     * @return
     * @throws RwJniException
     */
    CallResult getSamCard(CallParam callParam)throws RwJniException;
    
    
    /**
     * 洗卡
     * 
     * @param kmsCardVo
     * @return 
     */
    CallResult clearCard(KmsCardVo kmsCardVo)throws RwJniException;
}
