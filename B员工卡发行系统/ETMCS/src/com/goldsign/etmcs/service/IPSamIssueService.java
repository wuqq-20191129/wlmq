/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.etmcs.service;

import com.goldsign.csfrm.vo.CallResult;
import com.goldsign.etmcs.vo.KmsCfgParam;

/**
 *
 * @author lenovo
 */
public interface IPSamIssueService {

    
    CallResult isKMSConnected(KmsCfgParam param);
}
