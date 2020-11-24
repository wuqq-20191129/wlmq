/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.vo;

import com.goldsign.csfrm.vo.CallParam;

/**
 *
 * @author lenovo
 */
public class WriteCardParam extends CallParam {

    private String workType;
    
    private OrderInVo orderInVo;
    
    private SignCardParam signCardParam;

    private boolean isMakedCard = false;
    
    public void setMakedCard(boolean isMakedCard){
        this.isMakedCard = isMakedCard;
    }
    
    public boolean isMakedCard(){
        return this.isMakedCard;
    }
    
    /**
     * @return the workType
     */
    public String getWorkType() {
        return workType;
    }

    /**
     * @param workType the workType to set
     */
    public void setWorkType(String workType) {
        this.workType = workType;
    }

    /**
     * @return the orderInVo
     */
    public OrderInVo getOrderInVo() {
        return orderInVo;
    }

    /**
     * @param orderInVo the orderInVo to set
     */
    public void setOrderInVo(OrderInVo orderInVo) {
        this.orderInVo = orderInVo;
    }

    /**
     * @return the signCardParam
     */
    public SignCardParam getSignCardParam() {
        return signCardParam;
    }

    /**
     * @param signCardParam the signCardParam to set
     */
    public void setSignCardParam(SignCardParam signCardParam) {
        this.signCardParam = signCardParam;
    }
    
}
