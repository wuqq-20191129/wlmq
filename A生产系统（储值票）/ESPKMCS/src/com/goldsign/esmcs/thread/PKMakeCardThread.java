package com.goldsign.esmcs.thread;

import com.goldsign.csfrm.util.CharUtil;
import com.goldsign.csfrm.util.MessageShowUtil;
import com.goldsign.csfrm.vo.CallParam;
import com.goldsign.csfrm.vo.CallResult;
import com.goldsign.esmcs.application.PKApplication;
import com.goldsign.esmcs.dll.structure.CardInf;
import com.goldsign.esmcs.env.AppConstant;
import com.goldsign.esmcs.env.PKAppConstant;
import com.goldsign.esmcs.env.SynLockConstant;
import com.goldsign.esmcs.exception.FileException;
import com.goldsign.esmcs.exception.PkEsJniException;
import com.goldsign.esmcs.exception.RwJniException;
import com.goldsign.esmcs.service.IPkEsDeviceService;
import com.goldsign.esmcs.service.impl.PkEsDeviceService;
import com.goldsign.esmcs.ui.dialog.PKMakeCardDialog;
import com.goldsign.esmcs.util.PKConverter;
import com.goldsign.esmcs.util.PKValidator;
import com.goldsign.esmcs.util.Validator;
import com.goldsign.esmcs.vo.*;
import org.apache.log4j.Logger;

/**
 * 制卡线程
 * 
 * @author admin
 */
public class PKMakeCardThread extends MakeCardThread {

    private static final Logger logger = Logger.getLogger(PKMakeCardThread.class.getName());
    
    protected PKMakeCardDialog makeCardDialog = null;//制卡对话框
    private IPkEsDeviceService esDeviceService;//ES设备服务
    protected PKWriteCardThread writeCardThread = null;//写卡线程
    private EsBaseInfo esBaseInfo;//ES基本信息
    public BoxStateVo[] boxStateVos;//票箱状态
    public SiteStatusVo siteStatusVos;//卡位状态
    private boolean isPause = false;    //暂停标识
    private int sendNum = 0;            //发卡(出卡)数
    private int runState = 0; //运行状态
    
    /**
     * Creates a new instance of SystemClock
     */
    public PKMakeCardThread(PKMakeCardDialog makeCardDialog, BoxStateVo[] boxStateVos, 
            SiteStatusVo siteStatusVos) {
        super(makeCardDialog);
        this.esDeviceService = ((PKApplication) PKAppConstant.application).getPkEsDeviceService();
        super.setEsDeviceService(esDeviceService);
        this.esBaseInfo = ((PkEsDeviceService)esDeviceService).getEsBaseInfo();
        this.makeCardDialog = makeCardDialog;
        this.curOrderVo = this.makeCardDialog.getCurOrderVo();  
        this.boxStateVos = boxStateVos;
        this.siteStatusVos = siteStatusVos;
        //初始化标识
        initFlag();
        //启动写卡线程,创建后暂停，等待制卡线程发出写卡通知才写卡
        startWriteThread(); 
    }
    
    /**
     * 设置运行状态
     * 
     * @param runState 
     */
    public void setRunState(int runState){
    
        synchronized(SynLockConstant.SYN_RUN_STATE_LOCK){
            this.runState = runState;
        }
    }
    
    /**
     * 取得运行状态
     * 
     * @return 
     */
    public int getRunState(){
    
        synchronized(SynLockConstant.SYN_RUN_STATE_LOCK){
            return this.runState;
        }     
    }
    
    /**
     * 运行中
     * 
     * @return 
     */
    public boolean isRunning(){
        
        synchronized(SynLockConstant.SYN_RUN_STATE_LOCK){
            return this.runState == AppConstant.MAKE_CARD_STATUS_RUN
                    || this.runState == AppConstant.MAKE_CARD_STATUS_RESUME;
        }
    }
    
    /**
     * 初始化各标识
     * 
     */
    private void initFlag(){

        isPause = false;
        sendNum = 0;
        setRunState(AppConstant.MAKE_CARD_STATUS_INIT);
    }
    
    /**
     * 启动写卡线程,创建后暂停，等待制卡线程发出写卡通知才写卡
     * 
     */
    protected void startWriteThread(){
        writeCardThread = new PKWriteCardThread(makeCardDialog, this);
        writeCardThread.start();
    }
    
    /**
     * 判断线程是否已真正暂停
     * 
     * @return 
     */
    public boolean isPause(){
        
        synchronized(SynLockConstant.SYN_PAUSE_LOCK){
            return this.isPause;
        }
    }
    
    /**
     * 设置暂停
     * 
     * @param isPause 
     */
    public boolean setPause(boolean isPause){
        
        while(isPause && writeCardThread.isWritingCard()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                logger.error(ex);
            }
        }
        synchronized(SynLockConstant.SYN_PAUSE_LOCK){
            if(this.isPause != isPause){
                makeCardDialog.setPause(isPause);
                setEsPauseOrContinueState(isPause);//设置ES机暂停或继续
            }
            this.isPause = isPause;
        }
        
        return isPause;
    }
    
    /**
     * 设置ES机暂停或继续
     * 
     * @param pause 
     */
    private void setEsPauseOrContinueState(boolean pause){
        
        try {
            if (pause) {
                esDeviceService.channelPause();//暂停
            } else {
                esDeviceService.channelContinue();//继续
            }
        } catch (PkEsJniException ex) {
            logger.error(ex);
        }
    }
 
    /**
     * 设置发卡数量
     * 
     */
    public void setSendNum(int sendNum){
        this.sendNum = sendNum;
    }
    
    /**
     * 取得发卡数量
     * 
     * @return 
     */
    public int getSendNum(){
        return this.sendNum;
    }
    
    /**
     * 发卡箱空
     * 
     * @return 
     */
    private boolean isSendBoxEmpty() throws PkEsJniException{

        return !isEsStatusPosBitSet(3, 4);
    }
    
    /**
     * 发卡，参数为卡号
     * 
     * @throws PkEsJniException 
     */
    private int cardNo = 0;
    private boolean sendCard() throws PkEsJniException{
        
        CallParam callParam = new CallParam();
        CallResult callResult = null;       
        cardNo++;
        logger.info("发卡："+cardNo);
        callParam.setParam(cardNo);
        callResult = esDeviceService.sendCard(callParam);   //发卡
        if (!callResult.isSuccess()) {
            runError(callResult);
        }else{
            sendNum++;
            makeCardDialog.writeInfoMsg("出卡成功...序号："+cardNo);
        }
        setSiteStatus();//更新卡位状态颜色
        
        return callResult.isSuccess();
    }
    
    /**
     * 回收线上所有的卡,包括走卡和写卡
     * 
     * @throws InterruptedException
     * @throws PkEsJniException 
     */
    public void recvLineCards() throws PkEsJniException{
        
        CallResult callResult = null;
        int errCount = 0;
        while (isLineHaveCard()) {
            //走卡
            callResult = moveCard();   //走卡
            if (!callResult.isSuccess()) {
                runError(callResult);
                if(++errCount>15){
                    break;
                }
                //makeCardDialog.writeInfoMsg("收卡失败，已暂停！");
                //break;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                logger.error(ex);
            }
        }
    }
    
    /**
     * 异步回收卡， 包括走卡和写卡
     * 
     * @throws InterruptedException
     * @throws PkEsJniException 
     */
    private boolean recvCard(boolean syn) throws InterruptedException, PkEsJniException, 
            RwJniException, FileException {

        CallResult callResult = null;
        
        //走卡
        waitUntilWriteCardFinish();   //写卡结束，才能走卡
        callResult = moveCard();   //走卡
        if (!callResult.isSuccess()) {
            runError(callResult);
        } //else {
            writeCards(syn);//写卡  
        //}
        
        return callResult.isSuccess();
    }
    
    /**
     * 写卡
     * 
     */
    private void writeCards(boolean syn) throws RwJniException, FileException, 
            InterruptedException, PkEsJniException{
        
        if (syn) {
            writeCardThread.writeCards();
        } else {
            noticeWriteThreadToWrite(); //通知写卡
        }
        
        setSiteStatus();//更新卡位状态颜色
    }
    
    /**
     * 走卡
     * 
     * @return 
     */
    private CallResult moveCard() throws PkEsJniException{
        
        CallResult callResult = esDeviceService.moveCard(null);   //走卡
        logger.info("走卡...");
        setSiteStatus();//更新卡位状态颜色
        
        return callResult;
    }
    
    /**
     * 线程循环运行，遇到错误就停止
     * 
     */
    @Override
    public void run() {

        try{
            //logger.info("开始运行...");
            setRunState(AppConstant.MAKE_CARD_STATUS_RUN);//运行
            while (true) {
                //logger.info("运行中...");
                try {
                    //logger.info("step1...");
                    //更新状态位
                    setSiteStatus();
                    //logger.info("step2...");
                    CallResult callResult = checkBoxPhyStatus();
                    if(!callResult.isSuccess()) {
                        if(!isPause()){
                            MessageShowUtil.alertInfoMsg(callResult.getMsg()+" 已暂停！");
                            setPause(true);
                            setRunState(AppConstant.MAKE_CARD_STATUS_PAUSE);//暂停
                        }
                        Thread.sleep(100);
                        continue;
                    }
                    //是否已完成订单
                    if(curOrderVo.isFinish()){
                        makeCardDialog.doFinish();//处理完成订单
                        //写完卡后
                        MessageShowUtil.alertInfoMsg("完成制卡成功"); 
                        recvLineCards();//回收传输道上的所有卡
                        //logger.info("停止中...");
                        setRunState(AppConstant.MAKE_CARD_STATUS_STOP);//停止
                        break;
                    }
                    //logger.info("step3...");
                    //暂停制卡  
                    if(makeCardDialog.isPause()){
                        if(setPause(true)){//暂停
                            recvLineCards();
                        }
                        setRunState(AppConstant.MAKE_CARD_STATUS_PAUSE);//暂停
                        Thread.sleep(100);
                        //logger.info("暂停中...");
                        continue;
                    }else{
                        //logger.info("继续中...");
                        setPause(false);//继续
                        setRunState(AppConstant.MAKE_CARD_STATUS_RESUME);//继续
                    }
                    //logger.info("step4...");
                    //是否满，如果还是满，继续暂停 
                    if(isAllBoxFull()){
                        MessageShowUtil.alertInfoMsg("收卡箱满，已暂停！");
                        if(setPause(true)){//暂停
                            recvLineCards();
                        }
                        continue;
                    }
                    //logger.info("step5...");
                    //卡位1没卡，就发卡
                    if (!isSendSiteHaveCard()) {
                        if(isSendBoxEmpty()){//发卡箱空，否则暂停
                            MessageShowUtil.alertInfoMsg("发卡箱空，已暂停！");
                            if(setPause(true)){//暂停
                                recvLineCards();
                            }
                            continue;
                        }
                        if(!sendCard()){
                            MessageShowUtil.alertInfoMsg("发卡失败，已暂停！");
                            if(setPause(true)){//暂停
                                recvLineCards();
                            }
                            continue;
                        }
                    }
                    //logger.info("step6...");
                    //走卡
                    boolean recvCardResult = false;
                    if(isOrderFinishingOrBoxFulling()){
                        recvCardResult = recvCard(true);//最后2张卡时，同步进行
                        logger.info("同步走......");
                    }else{
                        recvCardResult = recvCard(false);//异步进行
                        logger.info("异步走......");
                    }
                    if(!recvCardResult){
                        MessageShowUtil.alertInfoMsg("收卡失败，已暂停！");
                        if (setPause(true)) {//暂停
                            recvLineCards();
                        }
                        continue;
                    }
                } catch(Exception ex){
                    logger.error(ex);
                    MessageShowUtil.alertErrorMsg("制卡异常，已退出！");
                    //makeCardDialog.writeInfoMsg(ex.getMessage());
                    makeCardDialog.setBtnStatus(PKAppConstant.MAKE_CARD_STATUS_EXIT);
                    setRunState(AppConstant.MAKE_CARD_STATUS_STOP);//停止
                    //logger.info("停止中...");
                    break;
                }
            }
        }finally {
            logger.info("关闭写卡线程.");
            stopWriteCardThread();
            //logger.info("退出中...");
            setRunState(AppConstant.MAKE_CARD_STATUS_EXIT);//退出
        }    
    }
    
    /**
     * 关闭写卡线程
     * 
     */
    protected void stopWriteCardThread(){
        
        while(writeCardThread != null && writeCardThread.isWritingCard()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                logger.error(ex);
            }
        }
        if(null != writeCardThread){
            writeCardThread.stop();//.interrupt();
        }
    }
    
    /**
     * 卡位1是否有卡
     *
     * @return
     */
    public boolean isSendSiteHaveCard() {

        boolean result = false;
        for (int i = 0; i < PKAppConstant.ES_CARD_SITE_NUM; i++) {
            if (esBaseInfo.cardInfs[i].CurrSite == 1) {
                logger.info("发卡工位有卡...");
                result = true;
                break;
            }
        }
        return result;
    }
    
    /**
     * 线上是否有卡
     *
     * @return
     */
    public boolean isLineHaveCard() {

        boolean result = false;

        for (CardInf cardInfo : esBaseInfo.cardInfs) {
            if (cardInfo.CurrSite != 0) {
                result = true;
                break;
            }
        }

        return result;
    }
    
    /**
     * 是否处于写卡工位
     *
     * @param i
     * @return
     */
    public boolean isSiteWriteCard(int i) {

        if (esBaseInfo.cardInfs[i].CurrSite == 2
                && esBaseInfo.cardInfs[i].TagSite == 2) {
            return true;
        } else {
            return false;
        }

    }
    
    /**
     * 更新回收箱
     * 
     * @param site
     * @param result
     * @return 
     */
    public int updateRecvBox(int site, boolean result){
        
        //清除回收回卡状态
        clearRecyCardState(site);

        //设置目标工位
        byte boxNo = getRecvBoxNo(result);
        
        return boxNo;
    }
    
    /**
     * 取回收票箱号
     *
     * @param type
     * @return
     */
    public byte getRecvBoxNo(boolean type) {

        byte boxNo = 1;
        if (type) {
            boxNo = esBaseInfo.getNextNormalBoxNo(boxStateVos); //好卡箱

            //logger.info("好卡箱：" + boxNo);
        } else {

            boxNo = esBaseInfo.getNextInvalBoxNo(boxStateVos); //坏卡箱

            //logger.info("坏卡箱：" + boxNo);
        }

        return boxNo;
    }
    
    /**
     * 清除回收回卡状态
     *
     * @param i
     */
    public void clearRecyCardState(int site) {

        synchronized (SynLockConstant.SYN_ES_CARD_SITE_LOCK) {
            if (esBaseInfo.cardInfs[site].State == 2) {
                //logger.info("回收箱 " + esBaseInfo.cardInfs[i].RecyBox + "回收卡成功！");
                //清除回收态信息
                esBaseInfo.cardInfs[site].State = 0;
                esBaseInfo.cardInfs[site].RecyBox = 0;
                esBaseInfo.cardInfs[site].CardNo = 0;
            }
        }
    }
    
    /**
     * 根据条件，设置目标工位 1、工位 2、类型
     *
     * @param site
     * @param result
     * @return
     */
    public void recvCard(int boxNo, int site, boolean isMakedCard) {

        //设置目标收工位
        synchronized (SynLockConstant.SYN_ES_CARD_SITE_LOCK) {
            byte tagSite = (byte) (boxNo + 2);
            esBaseInfo.cardInfs[site].TagSite = tagSite;
        }
        //logger.info("目标工位:" + tagSite);

        //更新界面
        makeCardDialog.setUICurProduceOrder((byte)boxNo, isMakedCard);
    }

    /**
     * 获得ES各状态
     *
     * @return
     */
    private byte[] getEsStatus() throws PkEsJniException{
        
        CallResult callResult = esDeviceService.channelGetACCStatus();
        byte[] status = (byte[]) callResult.getObj();

        return status;
    }
    
    /**
     * 取状态指定的位置的BYTE值
     * 
     * @param pos
     * @return
     * @throws TkEsJniException 
     */
    private byte getEsStatusByte(int pos) throws PkEsJniException{
        
        byte[] status = getEsStatus();
        return status[pos];
    }
    
    /**
     * 取状态指定的位置的字符串值
     * 
     * @param pos
     * @return
     * @throws TkEsJniException 
     */
    private String getEsStatusStr(int pos) throws PkEsJniException{
        
        byte statusByte = getEsStatusByte(pos);
        String statusStr = CharUtil.byteToBinReverse8bStr(statusByte);
        System.out.println("位置["+pos+"]:"+statusStr);
        
        return statusStr;
    }
    
    /**
     * 指定字节、指定位是否置位
     * 
     * @param bytePos
     * @param bitPos
     * @return
     * @throws TkEsJniException 
     */
    private boolean isEsStatusPosBitSet(int bytePos, int bitPos) throws PkEsJniException{
        
        String status = getEsStatusStr(bytePos);
        char b = status.charAt(bitPos);
        boolean result = PKValidator.isBitSet(b);
        
        return result;
    }
    
    /**
     * 打印工位
     * 
     */
    public void printSites() {
        int i = 1;
        for (CardInf cardInf : esBaseInfo.cardInfs) {
            logger.info("i=" + i + "，当前工位：" + cardInf.CurrSite + "，目标工位" + cardInf.TagSite);
        }
    }

    /**
     * 好或坏票箱是否满(因为不知下一张卡是否写成功，所以当前票箱数比容量少一张也算满)
     *
     * @return
     */
    public boolean isOrderFinishingOrBoxFulling() {

        synchronized(SynLockConstant.SYN_FULL_LOCK){
            if (curOrderVo.getUnFinishNum()<=2){
                return true;
            }
            if(getBoxUnFullNum()<=2){
                return true;
            }
            return false;
        }
    }

    /**
     * 取票箱未满数量
     * 
     * @return 
     */
    private int getBoxUnFullNum(){
        
        int totalNumBad = 0;
        int boxTotalNumBad = 0;
        int totalNumGood = 0;
        int boxTotalNumGood = 0;
        for (BoxStateVo boxStateVo : boxStateVos) {
            if (boxStateVo.isInval()) {
                totalNumBad += boxStateVo.getBoxTotalNum();
                boxTotalNumBad += boxStateVo.getBoxCurrentNum();
            }
            if(boxStateVo.isNormal()){
                totalNumGood += boxStateVo.getBoxTotalNum();
                boxTotalNumGood += boxStateVo.getBoxCurrentNum();
            }
        }
        int unFullNumBad = totalNumBad-boxTotalNumBad;
        int unFullNumGood = totalNumGood-boxTotalNumGood;
        int unFullNum = unFullNumBad-unFullNumGood<0?unFullNumBad:unFullNumGood;
        
        return unFullNum;
    }

    /**
     * 好或坏票箱是否满
     *
     * @return
     */
    public boolean isAllBoxFull() {

        synchronized(SynLockConstant.SYN_FULL_LOCK){
            return isGoodBoxFull() || isBadBoxFull();
        }
    }

    /**
     * 好票箱是否满
     *
     * @return
     */
    public boolean isGoodBoxFull() {

        for (BoxStateVo boxStateVo : boxStateVos) {
            if (boxStateVo.isNormal() && !boxStateVo.isFull()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 坏票箱是否满
     *
     * @return
     */
    public boolean isBadBoxFull() {

        for (BoxStateVo boxStateVo : boxStateVos) {
            if (boxStateVo.isInval() && !boxStateVo.isFull()) {
                return false;
            }
        }

        return true;
    }
        
    /**
     * 通知写卡线程，写卡
     * 
     * @throws InterruptedException 
     */
    private void noticeWriteThreadToWrite() throws InterruptedException{

        synchronized (SynLockConstant.SYN_WRITE_CARD_LOCK) {
            logger.info("通知写卡线程.");
            writeCardThread.setWritingCard(true);
            SynLockConstant.SYN_WRITE_CARD_LOCK.notifyAll();
        }
    }
    
    /**
     * 等待写卡线程完成写卡
     * 
     * @throws InterruptedException 
     */
    private void waitUntilWriteCardFinish() throws InterruptedException{
        
        synchronized(SynLockConstant.SYN_WRITE_CARD_LOCK){
            if(writeCardThread.isWritingCard()){
                logger.info("制卡线暂停，等待写卡线程通知.");
                SynLockConstant.SYN_WRITE_CARD_LOCK.wait(); 
            }
        }
    }
    
    /**
     * 跑卡错误
     * 如果为发卡时，发卡箱为空，发卡失败次数加1，如连续大于10次，停止;
     * 其它错误，直接停止
     * 
     * @param callResult
     * @throws PkEsJniException 
     */
    private void runError(CallResult callResult) throws PkEsJniException{

        short errCode = (Short) callResult.getObj();

        String errCodeDes = "";
        if (isCardRunError(errCode)) {
            CallResult cardRunErrorResut = esDeviceService.getCardRunError(null);
            LineStateVo lineStateVo = (LineStateVo) cardRunErrorResut.getObj();
            errCodeDes = PKConverter.getLineStateError(lineStateVo);
        } else {
            errCodeDes = PKConverter.getChannelErrorCodeDes(errCode);
        }
        
        makeCardDialog.writeInfoMsg(errCodeDes);
       
    }
    
    /**
     * 是否卡运行错误
     * 
     * @param errCode
     * @return 
     */
    private boolean isCardRunError(int errCode){
        return errCode == -22;
    }
    
    /**
     * 设置卡工位状态
     *
     */
    private void setSiteStatus() throws PkEsJniException {

        byte[] hopperStatus = getEsStatus();
        setHopperStatus(hopperStatus[3]);
        setRwAndBoxSiteStatus();

    }
    
    /**
     * 设置分币器工位
     *
     * @param hopperStatus
     */
    private void setHopperStatus(byte hopperStatus){
        boolean haveCard = Validator.isStatusBytePosBitSet(hopperStatus, 3);
        if (haveCard) {
            siteStatusVos.setHopperStatus(PKAppConstant.HOPPER_HAVE_CARD); 
            //logger.info("发卡箱有卡.");
        }
        boolean havingCard = Validator.isStatusBytePosBitSet(hopperStatus, 4);
        if (!haveCard && havingCard) {
            siteStatusVos.setHopperStatus(PKAppConstant.HOPPER_EMPTYING_CARD);
            //logger.info("发卡箱快没卡.");
        }
        if (!haveCard && !havingCard) {
            siteStatusVos.setHopperStatus(PKAppConstant.HOPPER_EMPTYED_CARD);
            //logger.info("发卡箱没卡.");
        }
    }
 
    /**
     * 设置卡工位状态
     * 
     * @param cardInfs 
     */
    private void setRwAndBoxSiteStatus(){
        
        for(int i=0; i<PKAppConstant.ES_CARD_SITE_NUM; i++){
            //没卡
            int site = i+1;
            if(!isSiteHaveCard(site)){
                siteStatusVos.setSiteStatus(i, PKAppConstant.SITE_NO_CARD);
                continue;
            }
            //有卡
            CardInf cardInf = getCurCardInf(site);
            byte curSite = cardInf.CurrSite;
            byte tagSite = cardInf.TagSite;
            //logger.info("当前工位：" + curSite + ",目标工位：" + tagSite);
            if (tagSite <= 2) {//有卡
                siteStatusVos.setSiteStatus(i, PKAppConstant.SITE_HAVE_CARD);
            } else {
                if (boxStateVos[tagSite-3].isNormal()) {//好卡
                    siteStatusVos.setSiteStatus(i, PKAppConstant.SITE_GOOD_CARD);
                } else {//坏卡
                    siteStatusVos.setSiteStatus(i, PKAppConstant.SITE_BAD_CARD);
                }
            }
        }
    }
    
    /**
     * 当前卡位是否有卡
     * 
     * @param curSite
     * @return 
     */
    private boolean isSiteHaveCard(int curSite){
        CardInf[] cardInfs = esBaseInfo.cardInfs;
        for (CardInf cardInf : cardInfs) {
            if(cardInf.CurrSite == curSite){
                return true;
            }
        }
        return false;
    }
    
    /**
     * 取当前卡位卡
     * 
     * @param curSite
     * @return 
     */
    private CardInf getCurCardInf(int curSite){
        CardInf[] cardInfs = esBaseInfo.cardInfs;
        for (CardInf cardInf : cardInfs) {
            if(cardInf.CurrSite == curSite){
                return cardInf;
            }
        }
        return null;
    }
    
        
    /**
     * 检测票箱物理状态
     *
     * @return
     */
    public CallResult checkBoxPhyStatus(){
        
        CallParam callParam = new CallParam();
        CallResult callResult = new CallResult();

        boolean result = true;
        
        StringBuilder msg = new StringBuilder();
        try {
            callResult = esDeviceService.getBoxSensorState(callParam);
            if(!callResult.isSuccess()){
                return callResult;
            }
            BoxSensorVo[] boxSensorVoArr = (BoxSensorVo[]) callResult.getObj(1);
            int i=0;
            for(BoxSensorVo boxSensorVo: boxSensorVoArr){
                i++;
                if(!boxStateVos[i-1].isStart()){
                    continue;
                }
                if (Validator.isBitSet(boxSensorVo.getS1())){
                    msg.append(".票箱 ").append(i).append(" 已满.");
                    result = false;
                    continue;
                }else if(Validator.isBitSet(boxSensorVo.getS2())){
                    msg.append(".票箱 ").append(i).append(" 将满.");
                    result = false;
                    continue;
                }else if(Validator.isBitSet(boxSensorVo.getS3())){
                    msg.append(".票箱 ").append(i).append(" 到达上限位.");
                    result = false;
                    continue;
                /*}else if(Validator.isBitSet(boxSensorVo.getS4())){
                    msg.append(".票箱 ").append(i).append(" 无进票空间.");
                    result = false;
                    continue;*/
                }else if(Validator.isBitSet(boxSensorVo.getS5())){
                    msg.append(".票箱 ").append(i).append(" 锁没关上.");
                    result = false;
                    continue;
                }
            }
            
        } catch (PkEsJniException ex) {
            logger.error(ex);
            result = false;
            msg.append("取票箱传感器状态异常.");
        }
        callResult.setResult(result);
        callResult.resetMsg(msg.toString());
            
        return callResult;
    }
}
