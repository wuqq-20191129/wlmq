/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.etmcs.service;

import com.goldsign.csfrm.service.IBaseService;
import com.goldsign.csfrm.vo.CallParam;
import com.goldsign.csfrm.vo.CallResult;
import com.goldsign.etmcs.exception.CommuException;


/**
 * 通讯服务接口
 * 
 * @author lenovo
 */
public interface ICommuService extends IBaseService{

    /**
     * 打开通讯
     * 
     * @param callParam 
     * @return
     * @throws CommuException 
     */
    CallResult openCommu(CallParam callParam)throws CommuException;
    
    /**
     * 是否通讯正常
     * 
     * @return
     * @throws CommuException 
     */
    boolean isCommuOk()throws CommuException;
    
    /**
     * 关闭通讯
     * 
     * @param callParam
     * @return
     * @throws CommuException 
     */
    CallResult closeCommu(CallParam callParam)throws CommuException;

    /**
     * 加解锁加密机
     * 
     * @param callParam
     * @return
     * @throws CommuException 
     */
    CallResult setKmsLock(CallParam callParam)throws CommuException;

}
