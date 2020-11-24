/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.service;

import com.goldsign.csfrm.service.IBaseService;
import com.goldsign.csfrm.vo.CallParam;
import com.goldsign.csfrm.vo.CallResult;
import com.goldsign.esmcs.exception.FileException;
import com.goldsign.esmcs.vo.*;
import java.util.List;

/**
 * 文件服务接口
 * 
 * @author lenovo
 */
public interface IFileService extends IBaseService{

    /**
     * 加载配置和日志文件
     *
     * @param callParam
     * @return
     * @throws FileException
     */
    CallResult loadConfigAndLogFile(CallParam callParam)throws FileException;
    
    /**
     * 取本地审核或错误文件
     * 
     * @param auditParam
     * @return
     * @throws FileException 
     */
     CallResult getLocalAuditAndErrorFiles(AuditParam auditParam)throws FileException;
     
     
    /**
     * 取正在制卡订单
     *
     * @param orderParam
     * @return
     * @throws FileException
     */
    CallResult getMakingOrders(OrderParam orderParam)throws FileException;
    
    /**
     * 取已完成订单
     *
     * @param orderParam
     * @return
     * @throws FileException
     */
    CallResult getFinishOrders(OrderParam orderParam)throws FileException;

    /**
     * 写完成订单
     *
     * @param orderVo
     * @return
     * @throws FileException
     */
    CallResult writeFinishOrder(OrderVo orderVo)throws FileException;
    
    /**
     * 写正在制卡文件
     * 
     * @param orderVo
     * @return
     * @throws FileException 
     */
    CallResult writeMakingOrder(OrderVo orderVo)throws FileException;
    
    /**
     * 写好卡订单文件
     * 
     * @param orderVo
     * @return
     * @throws FileException 
     */
    CallResult writeGoodOrder(EsOrderDetailVo orderVo)throws FileException;
    
    /**
     * 写坏卡订单文件
     *
     * @param orderVo
     * @return
     * @throws FileException
     */
    CallResult writeBadOrder(EsOrderDetailVo orderVo)throws FileException;
    
    /**
     * 取未通知订单消息
     *
     * @param callParam
     * @return
     * @throws FileException
     */
    CallResult getUnNoticeOrderMsg(CallParam callParam)throws FileException;
    
    
    /**
     * 写未知道订单消息
     *
     * @param callParam
     * @return
     * @throws FileException
     */
    CallResult writeUnNoticeOrderMsg(String file)throws FileException;
    
    /**
     * 写未知道订单消息
     * 
     * @param unNoticeMsgs
     * @return
     * @throws FileException 
     */
    CallResult writeUnNoticeOrderMsgs(List<String> unNoticeMsgs)throws FileException;

    /**
     * 写审核文件
     *
     * @param orderVo
     * @return
     * @throws FileException
     */
    CallResult writeAuditFile(String file)throws FileException;
    
     /**
     * 写审核文件
     *
     * @param orderVo
     * @return
     * @throws FileException
     */
    CallResult writeAuditFiles(List<String> noticeMsgs)throws FileException;

    /**
     * 取本地参数文件明细
     * 
     * @param file
     * @return
     * @throws FileException 
     */
    CallResult getLocalParamFileDetails(String fileName, String filePath)
            throws FileException;

    /**
     * 取已通知文件
     * 
     * @param noticeParam
     * @return 
     */
    CallResult getEsNoticeFiles(NoticeParam noticeParam)throws FileException;
    
    /**
     * 取未通知文件
     * 
     * @param noticeParam
     * @return 
     */
    CallResult getEsUnNoticeFiles(NoticeParam noticeParam)throws FileException;
    
     /**
     * 更新未通知和已通知订单消息
     *
     * @param callParam
     * @return
     * @throws FileException
     */
    CallResult updateNoticeOrderMsg(CallParam callParam)throws FileException;
    
    /**
     * 取逻辑卡号
     * 
     * @param phyId
     * @return 
     */
    String findLogicId(String phyId)throws FileException;
    
    /**
     * 设置物理对照表
     * @return 
     */
    void setPhyLogicMap()throws FileException;

    /**
     * 取好卡文件
     * 
     * @param orderParam
     * @return
     * @throws FileException 
     */
    CallResult getGoodOrder(OrderParam orderParam)throws FileException;
    
    /**
     * 取坏卡文件
     *
     * @param orderParam
     * @return
     * @throws FileException
     */
    CallResult getBadOrder(OrderParam orderParam)throws FileException;
    
      /**
     * 取上传消息成功但ES通讯未取文件
     * 
     * @param noticeParam
     * @return
     * @throws FileException 
     */
    public CallResult getEsUnGetNoticeFiles() throws Exception;
}
