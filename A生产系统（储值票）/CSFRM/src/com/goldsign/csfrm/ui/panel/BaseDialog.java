/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.csfrm.ui.panel;

import com.goldsign.csfrm.vo.CallParam;
import com.goldsign.csfrm.vo.CallResult;
import javax.swing.JDialog;

/**
 *
 * @author lenovo
 */
public class BaseDialog extends JDialog implements IBaseWindow{

    @Override
    public CallResult openingEventCallBack(CallParam callParam) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CallResult closingEventCallBack(CallParam callParam) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
