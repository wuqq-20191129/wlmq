/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.settle.realtime.app.vo;

import com.goldsign.settle.realtime.frame.vo.FileRecordBase;

/**
 *
 * @author hejj
 */
public class FileRecord59 extends FileRecordBase{

    /**
     * @return the lockDatetime_s
     */
    public String getLockDatetime_s() {
        return lockDatetime_s;
    }

    /**
     * @param lockDatetime_s the lockDatetime_s to set
     */
    public void setLockDatetime_s(String lockDatetime_s) {
        this.lockDatetime_s = lockDatetime_s;
    }
    private String lockFlag;//11	加解锁标志
    private String lockDatetime;//12	时间
    
    private String lockDatetime_s;//12	时间
    
     //模式相关
    
    private String cardAppMode;//卡应用模式

    /**
     * @return the lockFlag
     */
    public String getLockFlag() {
        return lockFlag;
    }

    /**
     * @param lockFlag the lockFlag to set
     */
    public void setLockFlag(String lockFlag) {
        this.lockFlag = lockFlag;
    }

    /**
     * @return the lockDatetime
     */
    public String getLockDatetime() {
        return lockDatetime;
    }

    /**
     * @param lockDatetime the lockDatetime to set
     */
    public void setLockDatetime(String lockDatetime) {
        this.lockDatetime = lockDatetime;
    }

    /**
     * @return the cardAppMode
     */
    public String getCardAppMode() {
        return cardAppMode;
    }

    /**
     * @param cardAppMode the cardAppMode to set
     */
    public void setCardAppMode(String cardAppMode) {
        this.cardAppMode = cardAppMode;
    }
    
}
