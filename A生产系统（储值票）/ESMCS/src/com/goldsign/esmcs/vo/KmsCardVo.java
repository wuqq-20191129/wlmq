/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.vo;

import com.goldsign.esmcs.env.AppConstant;

/**
 *
 * @author lenovo
 */
public class KmsCardVo {

    private String cardProducerCode;//hwj add 20160224增加卡商代码
    private String mfCardTranKey;       //卡厂提供
    private String cardMainKey;
    private String cardMendKey;
    private String outAuthenMKey;
    private String appTranKey;          //同应用主密钥
    private String appMainKey;
    private String appMendKey;          //应用维护密钥
    private String consumeKey;          //消费密钥
    private String transferInKey;       //圈存密钥
    private String tranAuthenTacKey;    //交易认证TAC密钥
    private String appMendKey01;        //应用维护密钥01
    private String appMendKey02;        //应用维护密钥02
    private String appLockKey;
    private String appUnlockKey;
    private String pinUnlockKey;
    private String pinResetKey;
    private String modifyOverdrawKey;
    private String transferOutKey;
    private String outAuthenDKey;

    
    /**
     * @return the cardMainKey
     */
    public String getCardMainKey() {
        return cardMainKey;
    }

    /**
     * @param cardMainKey the cardMainKey to set
     */
    public void setCardMainKey(String cardMainKey) {
        this.cardMainKey = cardMainKey;
    }

    /**
     * @return the cardMendKey
     */
    public String getCardMendKey() {
        return cardMendKey;
    }

    /**
     * @param cardMendKey the cardMendKey to set
     */
    public void setCardMendKey(String cardMendKey) {
        this.cardMendKey = cardMendKey;
    }

    /**
     * @return the outAuthenMKey
     */
    public String getOutAuthenMKey() {
        return outAuthenMKey;
    }

    /**
     * @param outAuthenMKey the outAuthenMKey to set
     */
    public void setOutAuthenMKey(String outAuthenMKey) {
        this.outAuthenMKey = outAuthenMKey;
    }

    /**
     * @return the appMainKey
     */
    public String getAppMainKey() {
        return appMainKey;
    }

    /**
     * @param appMainKey the appMainKey to set
     */
    public void setAppMainKey(String appMainKey) {
        this.appMainKey = appMainKey;
        setAppTranKey(appMainKey);
    }

    /**
     * @return the appMendKey
     */
    public String getAppMendKey() {
        return appMendKey;
    }

    /**
     * @param appMendKey the appMendKey to set
     */
    public void setAppMendKey(String appMendKey) {
        this.appMendKey = appMendKey;
    }

    /**
     * @return the consumeKey
     */
    public String getConsumeKey() {
        return consumeKey;
    }

    /**
     * @param consumeKey the consumeKey to set
     */
    public void setConsumeKey(String consumeKey) {
        this.consumeKey = consumeKey;
    }

    /**
     * @return the transferKey
     */
    public String getTransferInKey() {
        return transferInKey;
    }

    /**
     * @param transferKey the transferKey to set
     */
    public void setTransferInKey(String transferInKey) {
        this.transferInKey = transferInKey;
    }

    /**
     * @return the tranAuthenTacKey
     */
    public String getTranAuthenTacKey() {
        return tranAuthenTacKey;
    }

    /**
     * @param tranAuthenTacKey the tranAuthenTacKey to set
     */
    public void setTranAuthenTacKey(String tranAuthenTacKey) {
        this.tranAuthenTacKey = tranAuthenTacKey;
    }

    /**
     * @return the appMendKey01
     */
    public String getAppMendKey01() {
        return appMendKey01;
    }

    /**
     * @param appMendKey01 the appMendKey01 to set
     */
    public void setAppMendKey01(String appMendKey01) {
        this.appMendKey01 = appMendKey01;
    }

    /**
     * @return the appMendKey02
     */
    public String getAppMendKey02() {
        return appMendKey02;
    }

    /**
     * @param appMendKey02 the appMendKey02 to set
     */
    public void setAppMendKey02(String appMendKey02) {
        this.appMendKey02 = appMendKey02;
    }

    /**
     * @return the appLockKey
     */
    public String getAppLockKey() {
        return appLockKey;
    }

    /**
     * @param appLockKey the appLockKey to set
     */
    public void setAppLockKey(String appLockKey) {
        this.appLockKey = appLockKey;
    }

    /**
     * @return the appUnlockKey
     */
    public String getAppUnlockKey() {
        return appUnlockKey;
    }

    /**
     * @param appUnlockKey the appUnlockKey to set
     */
    public void setAppUnlockKey(String appUnlockKey) {
        this.appUnlockKey = appUnlockKey;
    }

    /**
     * @return the pinUnlockKey
     */
    public String getPinUnlockKey() {
        return pinUnlockKey;
    }

    /**
     * @param pinUnlockKey the pinUnlockKey to set
     */
    public void setPinUnlockKey(String pinUnlockKey) {
        this.pinUnlockKey = pinUnlockKey;
    }

    /**
     * @return the pinResetKey
     */
    public String getPinResetKey() {
        return pinResetKey;
    }

    /**
     * @param pinResetKey the pinResetKey to set
     */
    public void setPinResetKey(String pinResetKey) {
        this.pinResetKey = pinResetKey;
    }

    /**
     * @return the modifyOverdrawKey
     */
    public String getModifyOverdrawKey() {
        return modifyOverdrawKey;
    }

    /**
     * @param modifyOverdrawKey the modifyOverdrawKey to set
     */
    public void setModifyOverdrawKey(String modifyOverdrawKey) {
        this.modifyOverdrawKey = modifyOverdrawKey;
    }

    /**
     * @return the transferKeyKey
     */
    public String getTransferOutKey() {
        return transferOutKey;
    }

    /**
     * @param transferKeyKey the transferKeyKey to set
     */
    public void setTransferOutKey(String transferOutKey) {
        this.transferOutKey = transferOutKey;
    }

    /**
     * @return the outAuthenDKey
     */
    public String getOutAuthenDKey() {
        return outAuthenDKey;
    }

    /**
     * @param outAuthenDKey the outAuthenDKey to set
     */
    public void setOutAuthenDKey(String outAuthenDKey) {
        this.outAuthenDKey = outAuthenDKey;
    }

    /**
     * @return the mfCardTranKey
     */
    public String getMfCardTranKey() {
        
        //--------------HWJ 20160224增加卡商代码
        //8500 全F 华虹
        //8100 全0 华大
        //ELSE 全F
        if(AppConstant.CARD_PRODUCER_HUAHONG.equals(cardProducerCode)){
            return AppConstant.MfCardTranKeyAllF;
        }else if(AppConstant.CARD_PRODUCER_HUADA.equals(cardProducerCode)){
            return AppConstant.MfCardTranKeyAll0;
        }
        //---------------------------------------------
        
        return mfCardTranKey;
    }

    /**
     * @param mfCardTranKey the mfCardTranKey to set
     */
    public void setMfCardTranKey(String mfCardTranKey) {
        this.mfCardTranKey = mfCardTranKey;
    }

    /**
     * @return the appTranKey
     */
    public String getAppTranKey() {
        return appTranKey;
    }

    /**
     * @param appTranKey the appTranKey to set
     */
    public void setAppTranKey(String appTranKey) {
        this.appTranKey = appTranKey;
    }

    @Override
    public String toString() {
        return "KmsCardVo{" + "mfCardTranKey=" + mfCardTranKey + ", cardMainKey=" + cardMainKey + ", cardMendKey=" + cardMendKey + ", outAuthenMKey=" + outAuthenMKey + ", appTranKey=" + appTranKey + ", appMainKey=" + appMainKey + ", appMendKey=" + appMendKey + ", consumeKey=" + consumeKey + ", transferInKey=" + transferInKey + ", tranAuthenTacKey=" + tranAuthenTacKey + ", appMendKey01=" + appMendKey01 + ", appMendKey02=" + appMendKey02 + ", appLockKey=" + appLockKey + ", appUnlockKey=" + appUnlockKey + ", pinUnlockKey=" + pinUnlockKey + ", pinResetKey=" + pinResetKey + ", modifyOverdrawKey=" + modifyOverdrawKey + ", transferOutKey=" + transferOutKey + ", outAuthenDKey=" + outAuthenDKey + '}';
    }

    /**
     * @return the cardProducerCode
     */
    public String getCardProducerCode() {
        return cardProducerCode;
    }

    /**
     * @param cardProducerCode the cardProducerCode to set
     */
    public void setCardProducerCode(String cardProducerCode) {
        this.cardProducerCode = cardProducerCode;
    }

}
