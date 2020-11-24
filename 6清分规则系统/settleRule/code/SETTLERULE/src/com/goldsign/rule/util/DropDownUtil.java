/*
 * 文件名：DropDownUtil
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.rule.util;

import com.goldsign.frame.struts.BaseAction;
import com.goldsign.frame.util.FrameDBUtil;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;


/*
 * 业务下拉数据返回页面 公共类
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2013-8-8
 */

public class DropDownUtil {
    
    private static Logger logger = Logger.getLogger(DropDownUtil.class.getName());
    
    public DropDownUtil(){
        super();
    }
    
    //取状态表 ic_cod_pub_flag
    public void saveResultPubFlag(BaseAction action, HttpServletRequest request,
            int type, String resultName) throws Exception {
        FrameDBUtil util = new FrameDBUtil(); 
        Vector pubFlags = util.getPubFlags();
        pubFlags = util.getPubFlagsByType(type, pubFlags);
        
        if (pubFlags != null) {
            action.saveActionResult(request, resultName, pubFlags);// 下拉状态
        }
    }
    
}
