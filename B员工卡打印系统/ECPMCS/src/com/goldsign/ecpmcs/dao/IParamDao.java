/*
 * 文件名：IParamDao
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.ecpmcs.dao;

import com.goldsign.csfrm.dao.IBaseDao;
import com.goldsign.ecpmcs.vo.CardTypeVo;
import com.goldsign.ecpmcs.vo.PubFlagVo;
import java.util.List;


/*
 * 〈参数查询接口类〉
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2014-4-10
 */

public interface IParamDao extends IBaseDao{
    
    /**
     * 查询票卡类型
     */
    List<CardTypeVo> getCardTypeVos();
    
    /**
     * 查询证件类型
     */
    List<PubFlagVo> getIdentifyTypeVos();
    
    //检测设备配置是否正确
    public Boolean isDeviceConfigureRight(String ip, String deviceType, String deviceNo);

}
