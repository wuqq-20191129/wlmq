/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.ui.panel;

import com.goldsign.csfrm.util.UIUtil;
import com.goldsign.esmcs.env.AppConstant;
import com.goldsign.esmcs.ui.dialog.PKMakeCardDialog;
import com.goldsign.esmcs.util.Validator;
import com.goldsign.esmcs.vo.OrderParam;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * 储值票制卡面板
 * 
 * @author lenovo
 */
public class PKMakeCardPanel extends MadeCardPanel{

    private static final Logger logger = Logger.getLogger(PKMakeCardPanel.class.getName());
    
    /**
     * 弹出制卡对话框
     * 
     */
    @Override
    protected void showMakeCardDialog(){
        
        makeCardDialog = new PKMakeCardDialog(this);
        makeCardDialog.setModal(true);
        UIUtil.maxWindow(makeCardDialog);
        makeCardDialog.setVisible(true);
        //makeCardDialog.setAlwaysOnTop(true);
    }
    
    /**
     * 过滤出，只属于储值票的记录
     * 
     * @param orderParam
     * @return 
     */
    @Override
    protected List<Object[]> getServerOrders(OrderParam orderParam){
        
        List<Object[]> serverObjects = super.getServerOrders(orderParam);
        List<Object[]> serverFilterObjects = new ArrayList<Object[]>();
        for(Object[] serverObject: serverObjects){
            if(serverObject.length<3){
                continue;
            }
            String cardType = (String) serverObject[2];
            String cardMainType = cardType.substring(0, 2);
            if(//!Validator.contain(AppConstant.CARD_TYPE_TOKEN, cardType)&&
                     !AppConstant.CARD_MAIN_TYPE_SK.equals(cardMainType)
                    && !AppConstant.CARD_MAIN_TYPE_PHONE.equals(cardMainType)){
                serverFilterObjects.add(serverObject);
            }
        }

        return serverFilterObjects;
    }
}
