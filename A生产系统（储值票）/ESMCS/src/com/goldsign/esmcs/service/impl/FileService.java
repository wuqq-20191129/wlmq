package com.goldsign.esmcs.service.impl;

import com.goldsign.csfrm.exception.LoadException;
import com.goldsign.csfrm.service.impl.BaseService;
import com.goldsign.csfrm.service.impl.ConfigFileService;
import com.goldsign.csfrm.util.DateHelper;
import com.goldsign.csfrm.util.StringUtil;
import com.goldsign.csfrm.vo.CallParam;
import com.goldsign.csfrm.vo.CallResult;
import com.goldsign.csfrm.vo.ConfigParam;
import com.goldsign.esmcs.dao.IOrderDao;
import com.goldsign.esmcs.dao.IParamDao;
import com.goldsign.esmcs.dao.IPhyLogicDao;
import com.goldsign.esmcs.dao.impl.OrderDao;
import com.goldsign.esmcs.dao.impl.ParamDao;
import com.goldsign.esmcs.dao.impl.PhyLogicDao;
import com.goldsign.esmcs.env.AppConstant;
import com.goldsign.esmcs.env.ConfigConstant;
import com.goldsign.esmcs.exception.FileException;
import com.goldsign.esmcs.service.IFileService;
import com.goldsign.esmcs.vo.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 * 文件操作服务类
 * 
 * @author lenovo
 */
public class FileService extends BaseService implements IFileService{

    private static final Logger logger = Logger.getLogger(FileService.class.getName());
    
    private ConfigFileService configFileService;
    
    private IOrderDao orderDao;
    
    private IParamDao paramDao;
    
    private IPhyLogicDao phyLogicDao;
    
    private static final Object FILE_LOCK = new Object();
    
    public FileService(){
        this.configFileService = new ConfigFileService();
        this.orderDao = new OrderDao();
        this.paramDao = new ParamDao();
        this.phyLogicDao = new PhyLogicDao();
    }
    
    /**
     * 加载配置和日志文件
     * 
     * @param callParam
     * @return
     * @throws FileException 
     */
    @Override
    public CallResult loadConfigAndLogFile(CallParam callParam) throws FileException {
        
        ConfigParam configParam = new ConfigParam(ConfigConstant.CONFIG_FILE, ConfigConstant.LOG_FILE, 
                ConfigConstant.CFG_LOAD_XML_TOP_TAGS);
        try {
            return configFileService.loadConfigAndLogFile(configParam);
        } catch (LoadException ex) {
            logger.error(ex);
            throw new FileException(ex);
        }
    }

    /**
     * 取正在制卡订单
     * 
     * @param orderParam
     * @return
     * @throws FileException 
     */
    @Override
    public CallResult getMakingOrders(OrderParam orderParam) throws FileException {
        
        CallResult callResult = new CallResult();
        
        try {
            synchronized(FILE_LOCK){
                String makingOrder = orderDao.getMakingOrder(orderParam);
            
                if(null != makingOrder){
                    callResult.setObj(makingOrder);
                }
            }
        } catch (Exception ex) {
            logger.error(ex);
            throw new FileException(ex);
        }
        
        callResult.setResult(true);
        
        return callResult;
    }

    /**
     * 取已完成订单
     * 
     * @param orderParam
     * @return
     * @throws FileException 
     */
    @Override
    public CallResult getFinishOrders(OrderParam orderParam) throws FileException {
        
        CallResult callResult = new CallResult();

        try {
            synchronized(FILE_LOCK){
                List<Object[]> finishOrderVo = orderDao.getFinishOrders(orderParam);
                callResult.setObjs(finishOrderVo);
            }
        } catch (Exception ex) {
            logger.error(ex);
            throw new FileException(ex);
        }
        callResult.setResult(true);

        return callResult;
    }
    
    /**
     * 取ES订单统计信息
     * 
     * @param orderVo
     * @return 
     */
    private EsOrderStatisVo getEsOrderStatis(OrderVo orderVo){
    
        EsOrderStatisVo esOrderStatisVo = new EsOrderStatisVo();
        esOrderStatisVo.setWorkType(orderVo.getWorkType());
        esOrderStatisVo.setOrderNo(orderVo.getOrderNo());
        esOrderStatisVo.setOrderNum(StringUtil.addZeroBefore(orderVo.getOrderNum()+"", 12));
        esOrderStatisVo.setGoodCardNum(StringUtil.addZeroBefore(orderVo.getGoodCardNum()+"",12));
        esOrderStatisVo.setUnFinishNum(StringUtil.addZeroBefore(orderVo.getUnFinishNum()+"",12));
        esOrderStatisVo.setBadCardNum(StringUtil.addZeroBefore(orderVo.getBadCardNum()+"",12));
        esOrderStatisVo.setDate(DateHelper.dateToStr14yyyyMMddHHmmss(new Date()));
        esOrderStatisVo.setFinishFlag(orderVo.getStatus());
        //esOrderStatisVo.setRemark(StringUtil.addEmptyAfter(Converter.getEsOrderStatusDes(orderVo.getStatus()),40));
        esOrderStatisVo.setRemark(StringUtil.addEmptyAfter("",40));
        
        return esOrderStatisVo;
    }

    /**
     * 写完成订单
     * 
     * @param orderVo
     * @return
     * @throws FileException 
     */
    @Override
    public CallResult writeFinishOrder(OrderVo orderVo) throws FileException {
        
        CallResult callResult = new CallResult();
        try {
            synchronized(FILE_LOCK){
                orderDao.writeFinishOrder(orderVo);
                orderDao.makeEsFileOrder(getEsOrderStatis(orderVo));
                orderDao.deleteMakingOrder(orderVo);
            }
        }catch (Exception ex) {
            logger.error(ex);
            throw new FileException(ex);
        }
        callResult.setResult(true);
        
        return callResult;
    }

    /**
     * 写正在制卡文件
     * 
     * @param orderVo
     * @return
     * @throws FileException 
     */
    @Override
    public CallResult writeMakingOrder(OrderVo orderVo) throws FileException {
        
        CallResult callResult = new CallResult();
        
        try {
            synchronized(FILE_LOCK){
                orderDao.writeMakingOrder(orderVo);
            }
        }catch (IOException ex) {
            logger.error(ex);
            throw new FileException(ex);
        }
        callResult.setResult(true);
        
        return callResult;
    }

    /**
     * 写好卡订单文件
     * 
     * @param orderVo
     * @return
     * @throws FileException 
     */
    @Override
    public CallResult writeGoodOrder(EsOrderDetailVo orderVo) throws FileException {
        
        CallResult callResult = new CallResult();
        
        try {
            synchronized(FILE_LOCK){
                orderDao.writeGoodOrder(orderVo);
            }
        }catch (IOException ex) {
            logger.error(ex);
            throw new FileException(ex);
        }
        callResult.setResult(true);
        
        return callResult;
    }

    /**
     * 写坏卡订单文件
     *
     * @param orderVo
     * @return
     * @throws FileException
     */
    @Override
    public CallResult writeBadOrder(EsOrderDetailVo orderVo) throws FileException {
        
        CallResult callResult = new CallResult();
        
        try {
            synchronized(FILE_LOCK){
                orderDao.writeBadOrder(orderVo);
            }
        }catch (IOException ex) {
            logger.error(ex);
            throw new FileException(ex);
        }
        callResult.setResult(true);
        
        return callResult;
    }

    /**
     * 取未通知订单消息
     * 
     * @param callParam
     * @return
     * @throws FileException 
     */
    @Override
    public CallResult getUnNoticeOrderMsg(CallParam callParam) throws FileException {
        
        CallResult callResult = new CallResult();

        try {
            synchronized(FILE_LOCK){
                List<String> unNoticeMsgs = orderDao.getUnNoticeOrderMsg();
                callResult.setObjs(unNoticeMsgs);
            }
        } catch (Exception ex) {
            logger.error(ex);
            throw new FileException(ex);
        }
        callResult.setResult(true);

        return callResult;
    }

    /**
     * 写未知道订单消息
     * 
     * @param callParam
     * @return
     * @throws FileException 
     */
    @Override
    public CallResult writeUnNoticeOrderMsg(String file) throws FileException {
        
        CallResult callResult = new CallResult();
        try {
            synchronized(FILE_LOCK){
                orderDao.writeUnNoticeOrderMsg(file);
            }
        }catch (IOException ex) {
            logger.error(ex);
            throw new FileException(ex);
        }
        
        callResult.setResult(true);
        
        return callResult;
    }

    /**
     * 写未知道订单消息
     * 
     * @param unNoticeMsgs
     * @return
     * @throws FileException 
     */
    @Override
    public CallResult writeUnNoticeOrderMsgs(List<String> unNoticeMsgs) throws FileException {
        
        CallResult callResult = new CallResult();
        try {
            synchronized(FILE_LOCK){
                for(String unNoticeMsg: unNoticeMsgs){
                    //加上时间
                    String file = unNoticeMsg+AppConstant.MAO_SIGN+DateHelper.curDateToStr14yyyyMMddHHmmss();
                    orderDao.writeUnNoticeOrderMsg(file);
                }
            }
        }catch (IOException ex) {
            logger.error(ex);
            throw new FileException(ex);
        }
        
        callResult.setResult(true);
        
        return callResult;
    }
    
    /**
     * 写审核文件
     * 
     * @param orderVo
     * @return
     * @throws FileException 
     */
    @Override
    public CallResult writeAuditFile(String file) throws FileException {
        
        CallResult callResult = new CallResult();

        try {
            synchronized(FILE_LOCK){
                orderDao.writeAuditFile(file);
            }
        } catch (IOException ex) {
            logger.error(ex);
            throw new FileException(ex);
        }
        
        callResult.setResult(true);

        return callResult;
    }
    
    /**
     * 写审核文件
     * 
     * @param orderVo
     * @return
     * @throws FileException 
     */
    @Override
    public CallResult writeAuditFiles(List<String> noticeMsgs) throws FileException {
        
        CallResult callResult = new CallResult();
        try {
            synchronized(FILE_LOCK){
                for(String noticeMsg: noticeMsgs){
                    //加上时间
                    String file = noticeMsg+AppConstant.MAO_SIGN+DateHelper.curDateToStr14yyyyMMddHHmmss();
                    orderDao.writeAuditFile(file);
                }
            }
        }catch (IOException ex) {
            logger.error(ex);
            throw new FileException(ex);
        }
        
        callResult.setResult(true);
        
        return callResult;
    }

    /**
     * 取本地参数文件明细
     * 
     * @param fileName
     * @param filePath
     * @return
     * @throws FileException 
     */
    @Override
    public CallResult getLocalParamFileDetails(String fileName, String filePath) 
            throws FileException {
        
        CallResult callResult = new CallResult();

        try {
            synchronized(FILE_LOCK){
                List<Object[]> details = paramDao.getLocalParamFileDetails(fileName, filePath);
                callResult.setObjs(details);
            }
        } catch (Exception ex) {
            logger.error(ex);
            throw new FileException(ex);
        }
        
        callResult.setResult(true);

        return callResult;
    }

    /**
     * 取ES已通知订单文件
     * 
     * @param noticeParam
     * @return
     * @throws FileException 
     */
    @Override
    public CallResult getEsNoticeFiles(NoticeParam noticeParam) 
            throws FileException {
        
       CallResult callResult = new CallResult();

        try {
            synchronized(FILE_LOCK){
                List<Object[]> noticeFiles = orderDao.getEsNoticeFiles(noticeParam);
                callResult.setObjs(noticeFiles);
            }
        } catch (Exception ex) {
            logger.error(ex);
            throw new FileException(ex);
        }
        
        callResult.setResult(true);

        return callResult;
    }
    
    /**
     * 取ES未通知订单文件
     * 
     * @param noticeParam
     * @return
     * @throws FileException 
     */
    @Override
    public CallResult getEsUnNoticeFiles(NoticeParam noticeParam)
            throws FileException {
        
       CallResult callResult = new CallResult();

        try {
            synchronized(FILE_LOCK){
                List<Object[]> unNoticeFiles = orderDao.getEsUnNoticeFiles(noticeParam);
                callResult.setObjs(unNoticeFiles);
            }
        } catch (Exception ex) {
            logger.error(ex);
            throw new FileException(ex);
        }
        
        callResult.setResult(true);

        return callResult;
    }
    
      /**
     * 更新未通知和已通知订单消息
     *
     * @param callParam
     * @return
     * @throws FileException
     */
    @Override
    public CallResult updateNoticeOrderMsg(CallParam callParam) throws FileException{
        CallResult callResult = new CallResult();
        List<String> updateNotice = (List<String>) callParam.getParam(0);
        try{
            synchronized(FILE_LOCK){
                callResult.setResult(orderDao.updateNoticeOrderMsg(updateNotice));
            }
        }catch(IOException ex){
            logger.error(ex);
            throw new FileException(ex);
        } catch (Exception ex) {
            logger.error(ex);
            throw new FileException(ex);
        }
        return callResult;
    }

    /**
     * 查询逻辑卡号
     * @param phyId 物理卡号
     * @return logicId 逻辑卡号
     */
    @Override
    public String findLogicId(String phyId) throws FileException{
        try {
            return phyLogicDao.findLogicId(phyId);
        } catch (Exception ex) {
            throw new FileException(ex);
        }
    }
    
    /**
     * 设置物理对照表
     * 
     */
    @Override
    public void setPhyLogicMap() throws FileException{
        
        try {
            AppConstant.phyLogicVos = phyLogicDao.getPhyLogicMap();
        } catch (Exception ex) {
            logger.error("设置物理对照表失败，" + ex);
            throw new FileException(ex);
        }
    }
    
    /**
     * 取好卡
     * 
     * @param orderParam
     * @return
     * @throws FileException 
     */
    @Override
    public CallResult getGoodOrder(OrderParam orderParam) throws FileException {
        
        CallResult callResult = new CallResult();

        try {
            if(null == orderParam || StringUtil.isEmpty(orderParam.getOrderNo())){
                callResult.setObj(new ArrayList<Object[]>());
                return callResult;
            }
            synchronized(FILE_LOCK){
                List<Object[]> goodOrderVos = orderDao.getGoodOrder(orderParam.getOrderNo());
                callResult.setObjs(goodOrderVos);
            }
        } catch (Exception ex) {
            logger.error(ex);
            throw new FileException(ex);
        }
        
        callResult.setResult(true);

        return callResult;
    }

    /**
     * 取坏卡
     * 
     * @param orderParam
     * @return
     * @throws FileException 
     */
    @Override
    public CallResult getBadOrder(OrderParam orderParam) throws FileException {
        
        CallResult callResult = new CallResult();

        try {
            if(null == orderParam || StringUtil.isEmpty(orderParam.getOrderNo())){
                callResult.setObj(new ArrayList<Object[]>());
                return callResult;
            }
            synchronized(FILE_LOCK){
                List<Object[]> badOrderVos = orderDao.getBadOrder(orderParam.getOrderNo());
                callResult.setObjs(badOrderVos);
            }
           
        } catch (Exception ex) {
            logger.error(ex);
            throw new FileException(ex);
        }
        
        callResult.setResult(true);

        return callResult;
    }

    /**
     * 取本地审核和错误文件
     * 
     * @param auditParam
     * @return
     * @throws FileException 
     */
    @Override
    public CallResult getLocalAuditAndErrorFiles(AuditParam auditParam) 
            throws FileException {
        
        CallResult callResult = new CallResult();
        try {
            if(null == auditParam){
                callResult.setObj(new ArrayList<Object[]>());
                return callResult;
            }
            List<Object[]> files = new ArrayList<Object[]>();
            List<Object[]> auditFiles = null;
            List<Object[]> errorFiles = null;
            synchronized(FILE_LOCK){
                if(auditParam.getFileType().equals("") 
                    || auditParam.getFileType().equals(AppConstant.AUDIT_PARAM_FILE_TYPE_AUDIT)){
                    auditFiles = paramDao.getLocalAuditFiles(auditParam.getBeginDate(), auditParam.getEndDate());
                }
                if(auditParam.getFileType().equals("") 
                    || auditParam.getFileType().equals(AppConstant.AUDIT_PARAM_FILE_TYPE_ERROR)){
                    errorFiles = paramDao.getLocalErrorFiles(auditParam.getBeginDate(), auditParam.getEndDate());
                }
            }
            if(auditFiles != null && !auditFiles.isEmpty()){
                files.addAll(auditFiles);
            }
            if(errorFiles != null && !errorFiles.isEmpty()){
                files.addAll(errorFiles);
            }
            callResult.setObjs(files);
        } catch (Exception ex) {
            logger.error(ex);
            throw new FileException(ex);
        }
        
        callResult.setResult(true);
        
        return callResult;
    }

     /**limj
     * 取上传消息成功但ES通讯未取文件
     * 
     * @return
     * @throws FileException 
     */
    @Override
    public CallResult getEsUnGetNoticeFiles() 
            throws FileException {
        
       CallResult callResult = new CallResult();
//
//        try {
//            synchronized(FILE_LOCK){
//                List<String> noticeFiles = orderDao.getEsAuditContentFiles();
//                callResult.setObjs(noticeFiles);
//            }
//        } catch (Exception ex) {
//            logger.error(ex);
//            throw new FileException(ex);
//        }
//        
//        callResult.setResult(true);
//
        return callResult;
   }

}
