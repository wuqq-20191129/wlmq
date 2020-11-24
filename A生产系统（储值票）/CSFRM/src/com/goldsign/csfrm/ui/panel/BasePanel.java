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
public class BasePanel extends javax.swing.JPanel implements IBaseWindow{

    /**
     * 窗口打开时，回调此接口，可实现资源的初始化
     * 若返回失败，不打开窗口
     * 
     * @param callParam
     * @return 
     */
    @Override
    public CallResult openingEventCallBack(CallParam callParam){
        
        CallResult callResult = new CallResult();
        callResult.setResult(true);
        
        return callResult;
    }
    
    /**
     * 窗口关闭时，回调此接口，可实现资源的回收
     * 若返回失败，不关闭窗口
     *
     * @param callParam
     * @return
     */
    @Override
    public CallResult closingEventCallBack(CallParam callParam){
        
        CallResult callResult = new CallResult();
        callResult.setResult(true);
        
        return callResult;
    }
    
}
