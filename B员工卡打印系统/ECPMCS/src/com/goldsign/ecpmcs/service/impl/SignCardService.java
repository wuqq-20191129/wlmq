/*
 * 文件名：SignCardService
 * 版权：Copyright: goldsign (c) 2013
 */

package com.goldsign.ecpmcs.service.impl;

import com.goldsign.csfrm.service.impl.BaseService;
import com.goldsign.ecpmcs.dao.ISignCardDao;
import com.goldsign.ecpmcs.dao.impl.SignCardDao;
import com.goldsign.ecpmcs.service.ISignCardService;
import com.goldsign.ecpmcs.vo.SignCardVo;
import java.util.ArrayList;
import java.util.List;


/*
 * 〈记名卡信息实现类〉
 * @author     lindaquan
 * @version    V1.0
 * @createTime 2014-4-10
 */

public class SignCardService extends BaseService implements ISignCardService {
    
    private ISignCardDao signCardDao;
    
    public SignCardService(){
        signCardDao = new SignCardDao();
    }

    @Override
    public List<Object[]> getSignCardVos(SignCardVo signCardParam) {
        List<SignCardVo> signCardVoRets = signCardDao.getSignCardVo(signCardParam);
        
        List<Object[]> signCardRets = new ArrayList<Object[]>();
        for(SignCardVo signCardVoRet: signCardVoRets){
            Object[] signCardRet = new Object[]{
                signCardVoRet.getApplyName(), signCardVoRet.getIdentityTypeTxt(), 
                signCardVoRet.getIdentityId(), signCardVoRet.getApplySexTxt(),
                signCardVoRet.getAddress(), signCardVoRet.getApplyDatetime(),
                signCardVoRet.getImgDir(), signCardVoRet.getCardTypeTxt(),
                signCardVoRet.getExpiredDate()
            };
            signCardRets.add(signCardRet);
        }
        
        return signCardRets;
    }

}
