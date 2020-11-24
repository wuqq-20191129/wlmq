package com.goldsign.esmcs.thread;

import com.goldsign.esmcs.ui.dialog.PKSortCardDialog;
import com.goldsign.esmcs.vo.BoxStateVo;
import com.goldsign.esmcs.vo.SiteStatusVo;
import org.apache.log4j.Logger;

/**
 * 分拣卡线程类
 * 
 * @author admin
 */
public class PKSortCardThread extends PKMakeCardThread {

    private static final Logger logger = Logger.getLogger(PKSortCardThread.class.getName());

    public PKSortCardThread(PKSortCardDialog sortCardDialog, BoxStateVo[] boxStateVos, 
            SiteStatusVo siteStatusVos) {
        super(sortCardDialog, boxStateVos, siteStatusVos);
    }

    /**
     * 启动写卡线程,创建后暂停，等待制卡线程发出写卡通知才写卡
     *
     */
    @Override
    protected void startWriteThread(){
        writeCardThread = new PKReadCardThread((PKSortCardDialog)makeCardDialog, this);
        writeCardThread.start();
    }
}
