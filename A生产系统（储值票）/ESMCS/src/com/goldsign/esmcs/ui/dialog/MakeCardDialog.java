/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.ui.dialog;

import com.goldsign.csfrm.util.DateHelper;
import com.goldsign.csfrm.util.MessageShowUtil;
import com.goldsign.csfrm.util.StringUtil;
import com.goldsign.csfrm.util.UIUtil;
import com.goldsign.csfrm.vo.CallParam;
import com.goldsign.csfrm.vo.CallResult;
import com.goldsign.csfrm.vo.KeyValueVo;
import com.goldsign.esmcs.application.Application;
import com.goldsign.esmcs.env.AppConstant;
import com.goldsign.esmcs.env.CommuConstant;
import com.goldsign.esmcs.env.ConfigConstant;
import com.goldsign.esmcs.env.FileConstant;
import com.goldsign.esmcs.exception.CommuException;
import com.goldsign.esmcs.exception.EsJniException;
import com.goldsign.esmcs.exception.FileException;
import com.goldsign.esmcs.service.ICommuService;
import com.goldsign.esmcs.service.IEsDeviceService;
import com.goldsign.esmcs.service.IFileService;
import com.goldsign.esmcs.ui.panel.MadeCardPanel;
import com.goldsign.esmcs.util.Converter;
import com.goldsign.esmcs.vo.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.swing.JDialog;
import javax.swing.JRadioButton;
import org.apache.log4j.Logger;

/**
 *
 * @author lenovo
 */
public class MakeCardDialog extends JDialog{

    private static Logger logger = Logger.getLogger(MakeCardDialog.class.getName());
    
    protected MadeCardPanel madeCardPanel;//制卡面板
    protected IFileService fileService;//文件服务
    protected ICommuService commuService;//通讯服务
    private IEsDeviceService esDeviceService;
    protected OrderVo curOrderVo;
    
    public MakeCardDialog(MadeCardPanel madeCardPanel) {
        super(AppConstant.mainFrame);
        this.madeCardPanel = madeCardPanel;
        if(null != madeCardPanel){
            this.curOrderVo = madeCardPanel.getUICurProduceOrder();
        }
        this.commuService = ((Application) AppConstant.application).getCommuService();
        this.fileService = ((Application)AppConstant.application).getFileService();
        this.esDeviceService = ((Application) AppConstant.application).getEsDeviceService();
    }
    
    public void setEsDeviceService(IEsDeviceService esDeviceService) {
        this.esDeviceService = esDeviceService;
    }

    /**
     * 更新订单状态
     * 
     * @return 
     */
    public boolean updateOrderStatus(){
        
        CallResult callResult = null;
        CallParam callParam = new CallParam();
        try {
            //更新订单状态
            logger.info("订单：" + curOrderVo);
            callParam.setParam(curOrderVo.getWorkType());
            callParam.setParam(curOrderVo.getEmployeeId());
            List<String> orders = new ArrayList<String>();
            orders.add(curOrderVo.getOrderNo());
            callParam.setParam(orders);
            callResult = commuService.updateOrderStatus(callParam);
            if(callResult.isSuccess()){
                KeyValueVo keyValueVo = (KeyValueVo) callResult.getObj();
                if(null != keyValueVo){
                    callResult.setResult(CommuConstant.UPDATE_ORDER_FLAG_SUCC.equals(keyValueVo.getValue().trim()));
                }
            }
        } catch(Exception ex){
            
            logger.info("异常:"+ex);
            return false;
        }
//        }catch (CommuException ex) {
//            MessageShowUtil.alertErrorMsg("更改订单状态失败:" + ex.getMessage());
//            return false;
//        }
        
        return callResult.isSuccess();
    }
    
        
    /**
     * 创建制卡文件
     * 
     */
    protected boolean createMakingOrder(){
        CallResult callResult = null;
        try {
            callResult = fileService.writeMakingOrder(curOrderVo);//写制卡文件
        } catch (FileException ex) {
            logger.error(ex);
            return false;
        }
        return callResult.isSuccess();
    }
    
    /**
     * 下载记名卡
     *
     * @return
     */
    protected boolean querySignCards() {

        CallResult callResult = null;
        
        CallParam callParam = new CallParam();
        try {
            //下载记名卡
            callParam.setParam(curOrderVo);
            callResult = commuService.querySignCards(callParam);
        } catch (CommuException ex) {
            MessageShowUtil.alertErrorMsg("下载记名卡资料失败:" + ex.getMessage());
            return false;
        }
        if(callResult.isSuccess()){
            AppConstant.signCardVos = (Map<String, SignCardVo>) callResult.getObj();
        }

        return callResult.isSuccess();
    }
    
    /**
     * 是否下载记名卡
     *
     * @return
     */
    protected boolean isNeedQuerySignCards(){
                
        if(StringUtil.isEmpty(curOrderVo.getBeginReqNo())
                || StringUtil.isEmpty(curOrderVo.getEndReqNo())){
            return false;
        }
        
        return true;
    }
    
    /**
     * 已选择，变为没选择
     *
     * @param src
     * @param des
     */
    protected void setBoxTypeSelected(JRadioButton src, JRadioButton des){
        
        if(src.isSelected()){
            des.setSelected(false);
        }
    }
    
    /**
     * 写完成订单文件
     *
     * @return
     */
    public boolean writeFinishOrder(String status) {

        CallParam callParam = new CallParam();
        CallResult callResult = null;
        //写文件
        try {
            curOrderVo.setStatus(status);//完成
            callParam.setParam(curOrderVo);
            callResult = fileService.writeFinishOrder(curOrderVo);
        } catch (FileException ex) {
            logger.error(ex);
            MessageShowUtil.alertErrorMsg(ex.getMessage());
            return false;
        }

        return callResult.isSuccess();

    }
    
    /**
     * 发送上传订单消息
     *
     * @return
     */
    private boolean sendUploadOrderMsg(String file) {

        CallParam callParam = new CallParam();
        CallResult callResult = null;
        List<String> orderVos = new ArrayList<String>();
        try {
            orderVos.add(file);
            callParam.setParam(orderVos);
            callResult = commuService.uploadEsOrderFile(callParam);//完成订单
        } catch (CommuException ex) {
            logger.error(ex);
            MessageShowUtil.alertErrorMsg(ex.getMessage());
            return false;
        }

        return callResult.isSuccess();
    }
    
    /**
     * 取上传订单文件名称ES+设备号+订单号
     *
     * @return
     */
    private String getUploadOrderFileName(){
        
        Hashtable commons = (Hashtable) AppConstant.configs.get(ConfigConstant.CommonTag);
        String deviceNo = (String) commons.get(ConfigConstant.CommonDeviceNoTag);
        String orderFileName = FileConstant.ES_ORDER_FILENAME_PRE + deviceNo
                + FileConstant.ES_ORDER_FILENAME_SEP + curOrderVo.getOrderNo();
        
        return orderFileName;
    }
    
    /**
     * 写上传失败消息于文件
     *
     * @return
     */
    private boolean writeOrderNoticeFailFile(String file) {

        CallResult callResult = null;
        try {
            callResult = fileService.writeUnNoticeOrderMsg(file);
            if (callResult.isSuccess()) {
                logger.error("完成制卡-发送45消息失败,文件本地备份成功！");
            }
        } catch (FileException ex) {
            logger.error(ex);
            return false;
        }

        return callResult.isSuccess();
    }
    
    /**
     * 发送成功后，写审核文件
     *
     * @return
     */
    private boolean writeOrderNoticeSuccFile(String file) {

        CallResult callResult = null;
        try {
            callResult = fileService.writeAuditFile(file);
            if (callResult.isSuccess()) {
                logger.info("完成制卡-写审核文件成功！");
            }
        } catch (FileException ex) {
            logger.error(ex);
            return false;
        }

        return callResult.isSuccess();
    }
    
    /**
     * 更新设备状态
     *
     * @return
     */
    protected boolean updateDeviceStatus(String status) {

        CallParam callParam = new CallParam();
        CallResult callResult = null;
        try {
            callParam.setParam(curOrderVo.getEmployeeId());
            callParam.setParam(status);
            callParam.setParam(Converter.getEsDeviceStatusDes(status));
            callResult = commuService.updateDeviceStatus(callParam);//更改设备状态
            if (!callResult.isSuccess()) {
                MessageShowUtil.errorOpMsg("更新设备状态-发送44消息失败");
            }
        } catch (CommuException ex) {
            logger.error(ex);
            MessageShowUtil.alertErrorMsg(ex.getMessage());
            return false;
        }

        return callResult.isSuccess();
    }
    
    /**
     * 设置按钮状态，子类复盖
     * @param btn 
     */
    public void setBtnStatus(int btn){
    
    }
       
    /**
     * 完成制卡
     *
     */
    public boolean doFinish() {

        setBtnStatus(AppConstant.MAKE_CARD_STATUS_EXIT);
        
        //写文件
        boolean result = false;
        result = writeFinishOrder(AppConstant.ES_ORDER_STATUS_FINISH);

       if (result) {
            result = finishOrderMsg();
        }
 
        return result;
    }
 
    /**
     * 完成制卡
     *
     */
    public boolean finishOrderMsg(){
        
        //发上传消息
        String orderFileName = getUploadOrderFileName();
        String file = curOrderVo.getEmployeeId() + AppConstant.MAO_SIGN + orderFileName;
        boolean result = sendUploadOrderMsg(file);

        //加上时间
        file = file + AppConstant.MAO_SIGN + DateHelper.curDateToStr14yyyyMMddHHmmss();
        if (result) {
            result = writeOrderNoticeSuccFile(file);   //成功，写审核文件
        } else {
            result = writeOrderNoticeFailFile(file);    //失败，写错误文件
        }

        //更新设备状态
        updateDeviceStatus(AppConstant.ES_DEVICE_STATUS_FINISH_MAKE);
        
        //打印小票
        if(madeCardPanel.isPrintOrder()){
            esDeviceService.printOrder(curOrderVo);
        }
        return result;
    }
    
    /**
     * 取当前订单
     *
     * @return
     */
    public OrderVo getCurOrderVo(){
        
        return this.curOrderVo;
    }
    
    /**
     * 写提示或出错信息
     * 
     * @param msg 
     */ 
    public void writeInfoMsg(String msg){
        logger.info(msg);
    }
    
    /**
     * 检查订单的合法性
     * 
     * @param callParam
     * @return 
     */
    protected CallResult checkOrderIlligal(OrderVo orderVo){
    
        CallResult callResult = new CallResult();
        if(!checkOrderTicketType(orderVo)){
            callResult.setMsg("非法票卡类型！");
            return callResult;
        }
        /*
        if(!checkOrderTicketPrice(orderVo)){
            callResult.setMsg("非法票价金额！");
            return callResult;
        }*/
        
        callResult.setResult(true);
        return callResult;
    }
    
    /**
     * 检查卡类型
     * 
     * @return 
     */
    private boolean checkOrderTicketType(OrderVo orderVo){
    
        boolean result = false;
        
        String ticketTypeCode = orderVo.getCardTypeCode();
        if(null == ticketTypeCode || ticketTypeCode.trim().equals("")){
            return false;
        }
        for(TicketTypeVo ticketTypeVo: AppConstant.ticketTypeVos){
            if(ticketTypeVo.getCardCode().equals(ticketTypeCode)){
                result = true;
                break;
            }
        }
        
        return result;
    }
    
    /**
     * 检查票价
     * 
     * @return 
     */
    private boolean checkOrderTicketPrice(OrderVo orderVo){
    
        boolean result = false;

        String ticketPrice = orderVo.getPrintMoney(); 
        if(null == ticketPrice){
            return true;
        }
        ticketPrice = ticketPrice.trim();
        if(ticketPrice.equals("")){
            return true;
        }
        if(!StringUtil.isInt(ticketPrice)){
            return false;
        }
        int ticketPriceDouble = StringUtil.getInt(ticketPrice);
        if(0 == ticketPriceDouble){
            return true;
        }
        for(TicketPriceVo ticketPriceVo: AppConstant.ticketPriceVos){
            if(StringUtil.getInt(ticketPriceVo.getCardPrice()) == ticketPriceDouble){
                result = true;
                break;
            }
        }

        return result;
    }
    
    /**
     * 制卡结果明细
     * 
     */
    protected void showMakeCardDetail(boolean makeCardResult){
        MakeCardDetailDialog makeCardDetailDialog = null;
        makeCardDetailDialog = new MakeCardDetailDialog(makeCardResult, curOrderVo);
        UIUtil.makeContainerInScreenMiddle(makeCardDetailDialog);
        makeCardDetailDialog.setModal(true);
        makeCardDetailDialog.setAlwaysOnTop(true);
        makeCardDetailDialog.setVisible(true);
    }
    
    /**
     * 分拣结果明细
     *
     */
    private SortCardDetailDialog sortCardDetailDialog = new SortCardDetailDialog();//分拣明细
    protected void showSortCardDetail(){
        UIUtil.makeContainerInScreenMiddle(sortCardDetailDialog);
        sortCardDetailDialog.setModal(true);
        sortCardDetailDialog.setAlwaysOnTop(true);
        sortCardDetailDialog.setVisible(true);
    }
    
    /**
     * 设置分拣明细
     *
     * @param result
     * @param analyzeVo
     */
    public void setSortCardDetail(boolean result, AnalyzeVo analyzeVo){
        sortCardDetailDialog.setSortCardDetail(result, analyzeVo);
    }
    
    /**
     * 加载已制卡
     * 
     * @param curOrderVo
     * @return 
     */
    protected CallResult loadMakedCards(OrderVo curOrderVo){
    
        CallResult callResult = null;
        
        OrderParam orderParam = new OrderParam();
        orderParam.setOrderNo(curOrderVo.getOrderNo());
        
        try {
            //清除卡
            curOrderVo.clearCards();
            //加载好卡
            callResult = fileService.getGoodOrder(orderParam);
            if(!callResult.isSuccess()){
                return callResult;
            }
            List<Object[]> goodOrderVos = callResult.getObjs();
            for(Object[] objArr: goodOrderVos){
                if(null == objArr || objArr.length<7){
                    continue;
                }
                String phyNo = ((String)objArr[6]).trim();
                curOrderVo.addGoodCard(phyNo);
            }
      
            //加载坏卡
            callResult = fileService.getBadOrder(orderParam);
            if(!callResult.isSuccess()){
                return callResult;
            }
            List<Object[]> badOrderVos = callResult.getObjs();
            for(Object[] objArr: badOrderVos){
                if(null == objArr || objArr.length<7){
                    continue;
                }
                String phyNo = ((String)objArr[6]).trim();
                curOrderVo.addBadCard(phyNo);
            }
            
            callResult.setResult(true);
        } catch (FileException ex) {
            logger.error("加载已制卡失败："+ex);
            callResult.resetMsg("加载已制卡失败："+ex.getMessage());
            callResult.setResult(false);
        }
        
        return callResult;
    }
}
