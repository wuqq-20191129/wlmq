/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.ecpmcs.service;

import com.goldsign.csfrm.service.IBaseService;
import java.util.List;

/**
 *
 * @author lenovo
 */
public interface IParamService extends IBaseService{

    /**
     * 查询票卡类型
     */
    List<Object[]> getCardType();
    
    /**
     * 查询证件类型 
     */
    List<Object[]> getIdentifyType();
    
}
