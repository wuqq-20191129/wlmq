/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.dao;

import com.goldsign.csfrm.dao.IBaseDao;
import com.goldsign.esmcs.vo.*;
import java.io.IOException;
import java.util.List;

/**
 * 订单相关文件访问接口
 * 
 * @author lenovo
 */
public interface IOrderDao extends IBaseDao{

    /**
     * 取已开始（未完成）订单
     * 
     * @param orderParam
     * @return
     * @throws IOException 
     */
    String getMakingOrder(OrderParam orderParam)throws Exception; 
    
    /**
     * 取服务器(新)订单
     * 
     * @param orderParam
     * @return
     * @throws IOException 
     */
    List<Object[]> getFinishOrders(OrderParam orderParam)throws Exception;

    /**
     * 写完成订单文件
     * 
     * @param orderVo
     * @throws IOException 
     */
    void writeFinishOrder(OrderVo orderVo)throws IOException;

    /**
     * 写未完成订单文件
     * 
     * @param orderVo
     * @throws IOException 
     */
    void writeMakingOrder(OrderVo orderVo)throws  IOException;
    
    /**
     * 删除未完成订单文件
     * 
     * @param orderVo
     * @throws IOException 
     */
    void deleteMakingOrder(OrderVo orderVo)throws IOException;

    /**
     * 写制卡好卡订单
     * 
     * @param esOrderDetailVo
     * @throws IOException 
     */
    void writeGoodOrder(EsOrderDetailVo esOrderDetailVo)throws  IOException;
    
    /**
     * 取好卡订单明细
     * 
     * @param orderParam
     * @return
     * @throws Exception 
     */
    List<Object[]> getGoodOrder(String orderNo)throws Exception;
    
     /**
     * 取坏卡订单明细
     * 
     * @param orderParam
     * @return
     * @throws Exception 
     */
    List<Object[]> getBadOrder(String orderNo)throws Exception;   

    /**
     * 写制卡坏卡订单
     * 
     * @param esOrderDetailVo
     * @throws IOException 
     */
    void writeBadOrder(EsOrderDetailVo esOrderDetailVo)throws  IOException;

    /**
     * 取通知失败订单消息，取后删除内容
     * 
     * @return
     * @throws IOException 
     */
    List<String> getUnNoticeOrderMsg()throws  Exception;
    
    /**
     * 写通知失败订单消息
     * 
     * @param fileName
     * @throws IOException 
     */
    void writeUnNoticeOrderMsg(String file)throws  IOException;
    
     /**
     * 手工上传通知后更新订单消息
     * 
     * @param fileName
     * @throws IOException 
     */
    boolean updateNoticeOrderMsg(List<String> file)throws  IOException,Exception;

    /**
     * 生成ES订单文件
     * 
     * @param orderStatisVo
     * @throws IOException 
     */
    void makeEsFileOrder(EsOrderStatisVo orderStatisVo)throws  Exception;

    /**
     * 写审核文件
     * 
     * @param orderNo
     * @throws IOException 
     */
    void writeAuditFile(String file)throws  IOException;

    /**
     * 取已通知文件
     * 
     * @param noticeParam
     * @return
     * @throws IOException 
     */
    List<Object[]> getEsNoticeFiles(NoticeParam noticeParam)throws  Exception;

    /**
     * 取未通知文件
     * 
     * @param noticeParam
     * @return
     * @throws IOException 
     */
    List<Object[]> getEsUnNoticeFiles(NoticeParam noticeParam)throws  Exception;
    
    
     /**limj
     * 记录已获取过的审计和错误文件
     * 
     * @param fileName
     * @throws IOException 
     */
    public void recordDownAuditAndErrorFile(List<String> fileName) throws IOException,Exception;
    
      /**limj
     * 获取当天下载的审计错误文件明细
     * 
     * @param noticeParam
     * @return
     * @throws IOException 
     */
    public List<String> getEsAuditContentFiles(List<String> filenames) throws Exception;
  
}
