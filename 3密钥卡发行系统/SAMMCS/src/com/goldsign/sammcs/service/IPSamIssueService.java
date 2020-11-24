/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.sammcs.service;

import com.goldsign.csfrm.vo.CallResult;
import com.goldsign.sammcs.vo.IssueVo;
import com.goldsign.sammcs.vo.KmsCfgParam;

/**
 *
 * @author lenovo
 */
public interface IPSamIssueService {

    CallResult author(KmsCfgParam param);

    CallResult issue(IssueVo issueVo);
    
    CallResult read();
    
    CallResult isKMSConnected(KmsCfgParam param);
}
