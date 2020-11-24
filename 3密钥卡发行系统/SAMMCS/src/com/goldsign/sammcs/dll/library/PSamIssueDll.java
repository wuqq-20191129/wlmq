/*
 * 文件名：PSamIssueDll
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.sammcs.dll.library;

import com.goldsign.sammcs.dll.structure.AuthInInf;
import com.goldsign.sammcs.dll.structure.AuthOutInf;
import com.goldsign.sammcs.dll.structure.IssueInInf;
import com.goldsign.sammcs.dll.structure.IssueOutInf;
import com.goldsign.sammcs.dll.structure.ConnectInInf;
import com.goldsign.sammcs.dll.structure.ConnectOutInf;
import com.goldsign.sammcs.dll.structure.ReadInInf;
import com.goldsign.sammcs.dll.structure.ReadOutInf;
import com.sun.jna.Library;
import com.sun.jna.Native;


/*
 * PSAM卡授权、发行DLL类（JNA调用C接口）
 * @author     lindaquan
 * @version    V1.0
 */

public interface PSamIssueDll extends Library {
    
    PSamIssueDll INSTANCE = (PSamIssueDll) Native.loadLibrary("PSAM_ISSUE", PSamIssueDll.class);
    
    /**
     * 授权方法
     * @param in
     * @param out
     * @return 
     */
    int psam_issue_dev_op(AuthInInf.ByReference in, AuthOutInf.ByReference out);
    
    /**
     * 发行方法
     * @param in
     * @param out
     * @return 
     */
    int psam_issue_dev_op(IssueInInf.ByReference in, IssueOutInf.ByReference out);
    
    /**
     * 读卡
     * @param in
     * @param out
     * @return 
     */
    int psam_issue_dev_op(ReadInInf.ByReference in, ReadOutInf.ByReference out);
    
    /**
     * 获得网络状态
     * @param in
     * @param out
     * @return 
     */
    int psam_issue_dev_op(ConnectInInf.ByReference in, ConnectOutInf.ByReference out);
    
}
