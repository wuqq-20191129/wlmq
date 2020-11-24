/*
 * 文件名：ISignCardService
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.ecpmcs.service;

import com.goldsign.csfrm.service.IBaseService;
import com.goldsign.ecpmcs.vo.SignCardVo;
import java.util.List;


/*
 * 〈记名卡信息接口类〉
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2014-4-10
 */

public interface ISignCardService extends IBaseService{
    
    List<Object[]> getSignCardVos(SignCardVo signCardParam);

}
