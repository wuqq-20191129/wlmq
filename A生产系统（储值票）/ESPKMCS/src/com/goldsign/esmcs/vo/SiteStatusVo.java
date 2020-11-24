/*
 * 文件名：SiteStatusVo
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.esmcs.vo;

import com.goldsign.esmcs.env.AppConstant;
import com.goldsign.esmcs.env.PKAppConstant;
import java.awt.Color;
import javax.swing.JCheckBox;
import javax.swing.JLabel;


/*
 * 卡位状态实体类
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-10-6
 */

public class SiteStatusVo {
    
    private JCheckBox sendBoxCB;
    private JLabel[] sites;

    public SiteStatusVo(JCheckBox sendBoxCB, JLabel[] sites){
        this.sendBoxCB = sendBoxCB;
        this.sites = sites;
    }
    
    /**
     * 设置发卡箱状态
     * 
     */
    public void setHopperStatus(String status){
        if(PKAppConstant.HOPPER_EMPTYED_CARD.equals(status)){
            sendBoxCB.setBackground(Color.RED);
        }else if(PKAppConstant.HOPPER_EMPTYING_CARD.equals(status)){
            sendBoxCB.setBackground(Color.ORANGE);
        }else if(PKAppConstant.HOPPER_HAVE_CARD.equals(status)){
            sendBoxCB.setBackground(Color.GREEN);
        }
    }
    
     /**
     * 设置工位状态
     *
     */   
    public void setSiteStatus(int site, String status){
        
         JLabel siteLbl = sites[site];
        if (PKAppConstant.SITE_NO_CARD.equals(status)) {
            siteLbl.setBackground(AppConstant.SYS_POP_COLOR);
        } else if (PKAppConstant.SITE_HAVE_CARD.equals(status)) {
            siteLbl.setBackground(Color.GRAY);
        } else if (PKAppConstant.SITE_GOOD_CARD.equals(status)) {
            siteLbl.setBackground(Color.GREEN);
        } else if (PKAppConstant.SITE_BAD_CARD.equals(status)) {
            siteLbl.setBackground(Color.red);
        }
    }
    
}
