/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.service;

import com.goldsign.csfrm.service.IBaseService;
import com.goldsign.csfrm.vo.CallParam;
import com.goldsign.csfrm.vo.CallResult;
import com.goldsign.esmcs.exception.CommuException;
import java.io.IOException;

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
     * 操作员类型查询
     * 
     * @param callParam
     * @return
     * @throws CommuException 
     */
    CallResult loginOperator(CallParam callParam)throws CommuException;
    
    /**
     * 文件通知消息(客户端发给服务端)
     * 
     * @param callParam
     * @return
     * @throws CommuException 
     */
    CallResult uploadEsOrderFile(CallParam callParam)throws CommuException;

    /**
     * 文件通知消息(服务端发给客户端)
     *
     * @param callParam
     * @return
     * @throws CommuException
     */
    CallResult downAuditAndErrorFile(CallParam callParam) throws CommuException,IOException,Exception;
    
    /**
     * 城市代码参数查询
     * 
     * @param callParam
     * @return
     * @throws CommuException 
     */
    CallResult queryParams(CallParam callParam)throws CommuException;
    
    /**
     * 查询票价列表
     * 
     * @param callParam
     * @return
     * @throws CommuException 
     */
    CallResult queryTicketPrice(CallParam callParam)throws CommuException;
        
    /**
     * 查询票卡类型
     * 
     * @param callParam
     * @return
     * @throws CommuException 
     */
    CallResult queryCardTypes(CallParam callParam)throws CommuException;
    
    /**
     * 更改设备状态
     * 
     * @param callParam
     * @return
     * @throws CommuException 
     */
    CallResult updateDeviceStatus(CallParam callParam)throws CommuException;
    
    /**
     * 查询黑名单参数
     * 
     * @param callParam
     * @return
     * @throws CommuException 
     */
    CallResult queryBlacklist(CallParam callParam)throws CommuException;
    
    /**
     * 查询SAM卡注册参数
     * 
     * @param callParam
     * @return
     * @throws CommuException 
     */
    CallResult querySamlist(CallParam callParam)throws CommuException;
    
    /**
     * 查询记名卡资料参数
     * 
     * @param callParam
     * @return
     * @throws CommuException 
     */
    CallResult querySignCards(CallParam callParam)throws CommuException;
    
    /**
     * 查询订单
     * 
     * @param callParam
     * @return
     * @throws CommuException 
     */
    CallResult queryProduceOrders(CallParam callParam)throws CommuException;
    
    /**
     * 更改订单状态
     * 
     * @param callParam
     * @return
     * @throws CommuException 
     */
    CallResult updateOrderStatus(CallParam callParam)throws CommuException;
    
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
