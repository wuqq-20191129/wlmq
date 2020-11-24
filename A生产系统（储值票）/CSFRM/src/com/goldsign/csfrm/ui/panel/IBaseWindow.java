/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.csfrm.ui.panel;

import com.goldsign.csfrm.vo.CallParam;
import com.goldsign.csfrm.vo.CallResult;

/**
 *
 * @author lenovo
 */
public interface IBaseWindow {

    /**
     * 打开窗口，回调事件
     * 
     * @param callParam
     * @return 
     */
    CallResult openingEventCallBack(CallParam callParam);
    
    /**
     * 关闭窗口，回调事件
     * 
     * @param callParam
     * @return 
     */
    CallResult closingEventCallBack(CallParam callParam);
}
